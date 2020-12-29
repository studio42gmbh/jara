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
package de.s42.jara.enitites;

import de.s42.jara.Configuration;
import de.s42.jara.core.Vector3;
import de.s42.jara.tracer.RayContext;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Benjamin.Schiller
 */
public class Camera extends Entity
{
	private final static Logger log = LogManager.getLogger(Camera.class.getName());
	private final static double DOF_SIZE = Configuration.getCameraDofSize();

	public final Vector3 lookAt = new Vector3();
	public final Vector3 up = new Vector3();
	public double fieldOfView;
	public double aspectRatio;

	private double sinFovHalf;

	public Camera(Vector3 position, Vector3 lookAt, Vector3 up, double fieldOfView, double aspectRatio)
	{
		super(position);

		assert lookAt != null;
		assert up != null;
		assert fieldOfView > 0;
		assert aspectRatio > 0;

		this.up.copy(up).normalize();
		this.lookAt.copy(lookAt);
		this.fieldOfView = fieldOfView;
		sinFovHalf = Math.sin(fieldOfView * 0.5);
		this.aspectRatio = aspectRatio;
	}

	public void getRayFromScreenCoords(RayContext context, double screenX, double screenY)
	{
		assert context != null;

		context.origin.copy(position);

		context.direction
			.copy(lookAt)
			.subtract(context.origin)
			.normalize();

		Vector3 planeRight
			= context.direction.copy()
				.cross(up)
				.normalize();

		Vector3 planeUp
			= context.direction.copy()
				.cross(planeRight)
				.normalize();

		planeRight
			.multiply(screenX * sinFovHalf);

		planeUp
			.multiply(-screenY * sinFovHalf / aspectRatio);

		context.direction.add(planeRight).add(planeUp).normalize();

		//@todo JARA fix dof -> jitter in camera plain -> still tilt shift style -> fix movement relative to lookat not only direction?
		ThreadLocalRandom random = ThreadLocalRandom.current();
		context.direction.add(context.origin);

		planeRight
			.multiply((random.nextDouble() - 0.5) * DOF_SIZE);

		planeUp
			.multiply((random.nextDouble() - 0.5) * DOF_SIZE);

		context.origin.add(planeRight).add(planeUp);
		context.direction.subtract(context.origin).normalize();
	}

	public void autoFocus(Scene scene)
	{
		assert scene != null;

		RayContext context = new RayContext(null);
		context.reset(1);
		context.origin.copy(position);
		context.direction.add(lookAt).subtract(position).normalize();

		if (scene.intersect(context)) {
			lookAt.copy(context.origin);

			log.info("Autofocus point: " + lookAt);
		}
	}

	public Vector3 getLookAt()
	{
		return lookAt;
	}

	public void setLookAt(Vector3 lookAt)
	{
		assert lookAt != null;

		this.lookAt.copy(lookAt);
	}

	public double getFieldOfView()
	{
		return fieldOfView;
	}

	public void setFieldOfView(double fieldOfView)
	{
		assert fieldOfView > 0;

		this.fieldOfView = fieldOfView;
		sinFovHalf = Math.sin(fieldOfView * 0.5);
	}

	public double getAspectRatio()
	{
		return aspectRatio;
	}

	public void setAspectRatio(double aspectRatio)
	{
		assert aspectRatio > 0;

		this.aspectRatio = aspectRatio;
	}
}
