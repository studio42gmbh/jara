/*
 * The MIT License
 *
 * Copyright 2020 Studio 42 GmbH (https://www.s42m.de).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.s42.jara.materials;

import de.s42.jara.Configuration;
import de.s42.jara.core.Vector3;
import de.s42.jara.core.Color;
import de.s42.jara.core.JaraMath;
import de.s42.jara.tracer.RayContext;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Benjamin.Schiller
 */
public class Material
{
	private final static int[] DIFFUSE_SUBSAMPLES = Configuration.getDiffuseSubsamples();
	private final static int[] SPECULAR_SUBSAMPLES = Configuration.getSpecularSubsamples();
	private final static int[] REFRACTION_SUBSAMPLES = Configuration.getRefractionSubsamples();

	//find more on https://pixelandpoly.com/ior.html
	public final static double IOR_PLASTIC = 1.460;
	public final static double IOR_GLASS = 1.500;
	public final static double IOR_IRON = 2.950;
	public final static double IOR_STEEL = 2.500;
	public final static double IOR_STONE = 1.518;
	public final static double IOR_WOOD = 1.520;
	public final static double IOR_RUBBER = 1.519;
	public final static double IOR_CAR_PAINT = 1.895; //might be a little higher or lower depending on quality and freshness of paint
	public final static double IOR_AIR = 1.000;

	public Color emissive;
	public Color albedo;
	public double metalness;
	public double roughness;
	public double transparency;
	public double ior;
	public boolean doubleSided = true;
	public double clearCoat;
	public double clearCoatRoughness;

	public Material(Color emissive, Color albedo, double metalness, double roughness, double ior)
	{
		assert emissive != null;
		assert albedo != null;

		this.emissive = emissive.copy();
		this.albedo = albedo.copy();
		this.metalness = metalness;
		this.roughness = roughness;
		this.ior = ior;
	}

	public Material(Material toCopy)
	{
		assert toCopy != null;

		emissive = toCopy.emissive.copy();
		albedo = toCopy.albedo.copy();
		metalness = toCopy.metalness;
		roughness = toCopy.roughness;
		ior = toCopy.ior;
	}

	public void copy(Material toCopy)
	{
		assert toCopy != null;

		emissive = toCopy.emissive.copy();
		albedo = toCopy.albedo.copy();
		metalness = toCopy.metalness;
		roughness = toCopy.roughness;
		ior = toCopy.ior;
	}

	public void compute(RayContext context)
	{
		assert context != null;

		//if just current depth left emissive is the only aspect that could contribute to the color -> the rest of evals will end up black
		if (context.depth <= 1) {
			context.color.copyRGB(computeEmissive(context));
			return;
		}

		ThreadLocalRandom random = ThreadLocalRandom.current();
		int subsamples = context.subSampleIndex;
		int nextSubsamples = subsamples + 1;
		int depth = context.depth;
		int nextDepth = depth - 1;
		computeTexturePosition(context);
		double rough = computeRoughness(context);
		double metal = computeMetalness(context);
		Vector3 normal = computeNormal(context);
		Vector3 origin = context.origin.copy();
		Vector3 direction = context.direction.copy();
		Vector3 lastDirection = context.lastDirection.copy();
		Color result = context.tempColor.setRGBZero();
		result.a = 1.0;

		boolean contextInbound = context.inbound;
		double contextIor = context.ior;
		double materialIor = computeIor(context);
		double iorRatio = contextIor / materialIor;
		double refractIor = iorRatio;

		if (!contextInbound) {
			refractIor = materialIor / context.baseIor;
			contextIor = context.baseIor;
			normal.invert();
		}

		//diffuse
		Color diffuse = new Color();
		double diffuseWeight = 0.0;
		for (int dc = 0; dc < DIFFUSE_SUBSAMPLES[subsamples]; ++dc) {

			double dot = random.nextDouble(1.0);

			Vector3 diffuseOut = Vector3.createSphereRingRandomVector(normal, dot);

			if (diffuseOut.dot(normal) > 0) {

				context.origin.copy(origin);
				context.direction.copy(diffuseOut);
				context.ior = contextIor;
				context.subSampleIndex = nextSubsamples;
				context.depth = nextDepth;

				if (context.raytracer.raytrace(context)) {

					diffuseWeight += 1.0;
					diffuse.addRGB(context.color);
				}
			}
		}

		if (diffuseWeight > 0) {
			diffuse.divideRGB(diffuseWeight);
		}

		//specular
		Color specular = new Color();
		double specularWeight = 0.0;
		Vector3 reflect = lastDirection.copy().reflect(normal);
		//@todo JARA correct scaling of rough?
		double specularSpread = Math.pow(rough, 4.0);
		for (int dc = 0; dc < SPECULAR_SUBSAMPLES[subsamples]; ++dc) {

			double dot = (specularSpread < JaraMath.EPSILON) ? 1.0 : (1.0 - specularSpread) + random.nextDouble(specularSpread);

			Vector3 specularOut = Vector3.createSphereRingRandomVector(reflect, dot);

			if (specularOut.dot(normal) > 0) {

				context.origin.copy(origin);
				context.direction.copy(specularOut);
				context.ior = contextIor;
				context.subSampleIndex = nextSubsamples;
				context.depth = nextDepth;

				if (context.raytracer.raytrace(context)) {

					specularWeight += 1.0;
					specular.addRGB(context.color);
				}
			}
		}

		if (specularWeight > 0.0) {
			specular.divideRGB(specularWeight);
		}

		//refraction
		Color refraction = new Color();
		double refractionWeight = 0.0;
		if (transparency > 0.0) {

			Vector3 refract = lastDirection.copy().refract(normal, refractIor);
			//@todo JARA correct scaling of rough?
			double refractionSpread = Math.pow(rough, 4.0);
			for (int dc = 0; dc < REFRACTION_SUBSAMPLES[subsamples]; ++dc) {

				double dot = (refractionSpread < JaraMath.EPSILON) ? 1.0 : (1.0 - refractionSpread) + random.nextDouble(refractionSpread);

				Vector3 refractionOut = Vector3.createSphereRingRandomVector(refract, dot);

				/*if (contextInbound) {
					context.ior = materialIor;
				}
				else {
					context.ior = contextIor;
				}*/
				context.ior = contextIor;
				context.origin.copy(origin);
				context.direction.copy(refractionOut);
				context.subSampleIndex = nextSubsamples;
				context.depth = nextDepth;

				if (context.raytracer.raytrace(context)) {

					refractionWeight += 1.0;
					refraction.addRGB(context.color);
				}
			}

			if (refractionWeight > 0.0) {
				refraction.divideRGB(refractionWeight);
			}
		}

		//clearCoat
		Color clearCoatCol = new Color();
		double clearCoatWeight = 0.0;
		if (clearCoat > 0.0) {
			Vector3 reflectClearCoat = lastDirection.copy().reflect(normal);
			//@todo JARA correct scaling of rough?
			double clearCoatSpread = Math.pow(clearCoatRoughness, 4.0);
			for (int dc = 0; dc < SPECULAR_SUBSAMPLES[subsamples]; ++dc) {

				double dot = (clearCoatSpread < JaraMath.EPSILON) ? 1.0 : (1.0 - clearCoatSpread) + random.nextDouble(clearCoatSpread);

				Vector3 clearCoatOut = Vector3.createSphereRingRandomVector(reflectClearCoat, dot);

				if (clearCoatOut.dot(normal) > 0) {

					context.origin.copy(origin);
					context.direction.copy(clearCoatOut);
					context.ior = contextIor;
					context.subSampleIndex = nextSubsamples;
					context.depth = nextDepth;

					if (context.raytracer.raytrace(context)) {

						clearCoatWeight += 1.0;
						clearCoatCol.addRGB(context.color);
					}
				}
			}

			if (clearCoatWeight > 0.0) {
				clearCoatCol.divideRGB(clearCoatWeight);
			}
		}

		context.origin.copy(origin);
		context.direction.copy(direction);

		//apply brightness
		result.copyRGB(diffuse);

		//metalness
		if (specularWeight > 0.0) {

			result.multiplyRGB(1.0 - metal);

			Color spec = specular.copy();

			spec.multiplyRGB(metal);

			result.addRGB(spec);
		}

		//albedo
		result.multiplyRGB(computeAlbedo(context));

		//transparency
		if (refractionWeight > 0.0) {

			result.blendRGB(refraction, transparency);
		}

		//specular with fresnel
		double fresnel = computeFresnel(lastDirection.dot(normal), iorRatio);

		if (specularWeight > 0.0) {

			Color spec = specular.copy();

			spec.multiplyRGB(fresnel).multiplyRGB(1.0 - metal);

			result.addRGB(spec);
		}

		if (clearCoatWeight > 0.0) {

			clearCoatCol.multiplyRGB(fresnel).multiplyRGB(clearCoat);

			result.addRGB(clearCoatCol);
		}

		//emissive
		result.addRGB(computeEmissive(context));

		//@debug albedo
		//result.copy(computeAlbedo(context));
		//@debug brightness
		//result.copy(diffuse);
		//@debug specular brightness
		//result.copy(specular);
		//@debug emissive
		//result.copy(computeEmissive(context));
		//@debug transparency
		/*if (transparency > 0.0 && refractionWeight > 0.0) {
			result.copy(refraction);
		}*/
		//@debug specular
		//result.copy(specular).multiply(metal).add(specular.copy().multiply(fresnel).multiply(1.0 - rough));
		//@debug diffuse
		//result.copy(diffuse).multiplyRGB(computeAlbedo(context)).addRGB(computeEmissive(context));
		//@debug roughness
		//result.set(rough, rough, rough);
		//@debug metalness
		//result.set(metal, metal, metal);
		//@debug fresnel
		//result.set(fresnel, fresnel, fresnel);		
		//@debug normal in opengl colors
		//result.set(normal.x * 0.5 + 0.5, normal.y * 0.5 + 0.5, normal.z * 0.5 + 0.5);
		context.color.copy(result);
	}

	protected double computeFresnel(double dotDirectionNormal, double currentIor)
	{
		double cosi = dotDirectionNormal;
		double etai = 1.0;
		double etat = currentIor;
		if (dotDirectionNormal < 0.0) {
			double temp = etai;
			etai = etat;
			etat = temp;
		}

		double sint = etai / etat * Math.sqrt(Math.max(0.0, 1.0 - cosi * cosi));

		if (sint >= 1) {
			return 1.0;
		}
		else {
			double cost = Math.sqrt(Math.max(0.0, 1.0 - sint * sint));
			cosi = Math.abs(cosi);
			double Rs = ((etat * cosi) - (etai * cost)) / ((etat * cosi) + (etai * cost));
			double Rp = ((etai * cosi) - (etat * cost)) / ((etai * cosi) + (etat * cost));
			return (Rs * Rs + Rp * Rp) / 2.0;
		}
	}

	public Vector3 computeTexturePosition(RayContext context)
	{
		assert context != null;

		return context.texturePosition.copy();
	}

	public Vector3 computeNormal(RayContext context)
	{
		assert context != null;

		return context.direction.copy();
	}

	public Color computeEmissive(RayContext context)
	{
		return emissive;
	}

	public Color computeAlbedo(RayContext context)
	{
		return albedo;
	}

	public double computeMetalness(RayContext context)
	{
		return metalness;
	}

	public double computeRoughness(RayContext context)
	{
		return roughness;
	}

	public double computeIor(RayContext context)
	{
		return ior;
	}

	@Override
	public String toString()
	{
		return "[ albedo: " + albedo + ", emissive: " + emissive + ", metalness: " + metalness + ", roughness: " + roughness + " ]";
	}
}
