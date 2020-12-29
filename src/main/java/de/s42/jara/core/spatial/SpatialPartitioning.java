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
package de.s42.jara.core.spatial;

import de.s42.jara.Configuration;
import de.s42.jara.core.Matrix3;
import de.s42.jara.core.Vector3;
import de.s42.jara.core.spatial.SpatialNode.Axis;
import de.s42.jara.enitites.PhysicalEntity;
import de.s42.jara.tracer.RayContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Benjamin.Schiller
 */
public class SpatialPartitioning
{
	private final static Logger log = LogManager.getLogger(SpatialPartitioning.class.getName());
	public final List<PhysicalEntity> entities = Collections.synchronizedList(new ArrayList());

	public final SpatialNode root = new SpatialNode();
	public long nodes = 1;

	public void add(PhysicalEntity entity)
	{
		assert entity != null;

		entities.add(entity);

		root.entities.add(entity);
	}

	public void add(List<? extends PhysicalEntity> entities)
	{
		assert entities != null;

		for (PhysicalEntity entity : entities) {
			add(entity);
		}
	}

	/**
	 *
	 * @param node
	 * @param below
	 * @param above
	 * @param axis
	 * @return a rating of the split between 0.0 (no effect) and 1.0 (perfect)
	 */
	protected double splitNodeAlongAxis(SpatialNode node, SpatialNode below, SpatialNode above, Axis axis)
	{
		assert node != null;
		assert below != null;
		assert above != null;
		assert axis != null;

		int aIndex = axis.index;
		int entityCount = node.entities.size();
		double entityCountHalf = entityCount * 0.5;
		double min = node.bounds.min.get(aIndex);
		double max = node.bounds.max.get(aIndex);
		double deltaHalf = (max - min) * 0.5;
		double posMid = min + deltaHalf;

		//log.debug("splitNodeAlongAxis", axis, entityCount, min, max);
		List<PhysicalEntity> ent = new ArrayList(node.entities);

		//pre sort list by max of axis
		ent.sort((o1, o2) -> {
			int c = (int) Math.signum(o1.bounds.max.get(aIndex) - o2.bounds.max.get(aIndex));

			if (c == 0) {
				return (int) Math.signum(o1.bounds.min.get(aIndex) - o2.bounds.min.get(aIndex));
			}

			return c;
		});

		//calculate the median vector		
		Vector3 median = new Vector3();
		for (PhysicalEntity entity : ent) {
			median.add(entity.bounds.center);
		}
		median.divide(ent.size());

		//@todo JARA find a smart point instead of median
		/*
		for (PhysicalEntity entity : ent) {
			//log.debug("ENT", entity.bounds.min.get(aIndex), entity.bounds.max.get(aIndex), entity);
		}

		List<Pair<PhysicalEntity, Pair<Integer, Double>>> filtered = new ArrayList<>(entityCount);

		int i = 0;
		for (PhysicalEntity entity : ent) {

			//remove old entry contained within bound box of entity
			Iterator<Pair<PhysicalEntity, Pair<Integer, Double>>> filIt = filtered.iterator();
			while (filIt.hasNext()) {

				Pair<PhysicalEntity, Pair<Integer, Double>> p = filIt.next();

				//dont allow entries which are within the bounds of the newly added entry
				if (p.a.bounds.max.get(aIndex) > entity.bounds.min.get(aIndex) /*|| p.a.bounds.max.get(aIndex) == entity.bounds.max.get(aIndex)* /) {
					filIt.remove();
				}
			}

			double ratingPosition = (deltaHalf - Math.abs(posMid - entity.bounds.max.get(aIndex))) / deltaHalf;
			double ratingSplitRatio = (entityCountHalf - Math.abs(entityCountHalf - i)) / entityCountHalf;

			double rating = (ratingPosition + ratingSplitRatio) * 0.5;

			filtered.add(new Pair<>(entity, new Pair<>(i, rating)));

			i++;
		}

		//sort entries of filtered by rating
		filtered.sort((o1, o2) -> {
			return (int) Math.signum(o2.b.b - o1.b.b);
		});

		//@debug
		for (Pair<PhysicalEntity, Pair<Integer, Double>> p : filtered) {
			//log.debug("FILT", p.a.bounds.max.get(aIndex), p.a, p.b.a, p.b.b);
		}
		 */
		//split entity list into below and above along split
		double split = median.get(aIndex);//posMid;//filtered.get(0).a.bounds.max.get(aIndex);
		below.entities.clear();
		above.entities.clear();
		below.bounds.set(new Vector3(Double.MAX_VALUE), new Vector3(-Double.MAX_VALUE));
		above.bounds.set(new Vector3(Double.MAX_VALUE), new Vector3(-Double.MAX_VALUE));
		for (PhysicalEntity entity : ent) {

			if (entity.bounds.max.get(aIndex) < split) {
				below.entities.add(entity);
				below.bounds.join(entity.bounds);
			}
			else {
				above.entities.add(entity);
				above.bounds.join(entity.bounds);
			}
		}

		//calculate rating part volume -> the less the better
		double ratingVolume = 1.0 / (below.bounds.volume + above.bounds.volume);

		//calculate split ratio -> the more even the better
		double ratingSplit = (double) (node.entities.size() - Math.abs(below.entities.size() - above.entities.size())) / (double) node.entities.size();//filtered.get(0).b.b;

		return ratingVolume * ratingSplit;
	}

	protected void splitNode(SpatialNode node, int depth)
	{
		assert node != null;
		assert depth >= 0;

		//if bucket small enough ofr recursion deep enough -> break
		if (node.entities.size() < Configuration.getSpatialTreeSplitNodeSize() || depth >= Configuration.getSpatialTreeMaxDepth()) {

			node.cachedEntities = node.entities.toArray(new PhysicalEntity[]{});
			return;
		}

		node.bounds.set(new Vector3(Double.MAX_VALUE), new Vector3(-Double.MAX_VALUE));
		for (PhysicalEntity entity : node.entities) {
			node.bounds.join(entity.bounds);
		}

		SpatialNode below = new SpatialNode();
		below.bounds.copy(node.bounds);

		SpatialNode above = new SpatialNode();
		above.bounds.copy(node.bounds);

		double ratingX = splitNodeAlongAxis(node, below, above, Axis.X);
		double ratingY = splitNodeAlongAxis(node, below, above, Axis.Y);
		double ratingZ = splitNodeAlongAxis(node, below, above, Axis.Z);

		if (ratingX > ratingY && ratingX > ratingZ) {
			splitNodeAlongAxis(node, below, above, Axis.X);
		}
		else if (ratingY > ratingZ) {
			splitNodeAlongAxis(node, below, above, Axis.Y);
		}
		else {
			splitNodeAlongAxis(node, below, above, Axis.Z);
		}

		node.entities.clear();
		node.cachedEntities = new PhysicalEntity[]{};

		if (above.entities.size() > 0) {
			node.above = above;
			nodes++;
			splitNode(node.above, depth + 1);
		}

		if (below.entities.size() > 0) {
			node.below = below;
			nodes++;
			splitNode(node.below, depth + 1);
		}
	}

	public void prepareForRendering()
	{
		splitNode(root, 0);

		log.debug("Prepared " + nodes + " nodes for " + entities.size() + " entities");
	}

	protected double intersect(RayContext context, SpatialNode node, double closestDistanceHit)
	{
		assert context != null;
		assert node != null;
		assert closestDistanceHit >= 0.0;

		PhysicalEntity hitEntity = null;
		boolean hitInbound = false;
		Vector3 hitTexturePosition = new Vector3();
		Matrix3 hitNormalMatrix = new Matrix3();

		Vector3 origin = context.origin.copy();
		Vector3 direction = context.direction.copy();

		//@todo JARA as the bounds can currently overlap i can not just depth sort the boxes and not test the behind box if the infront one hits
		if (node.below != null && node.below.intersects(origin, direction)) {
			double subClosestDistanceHit = intersect(context, node.below, closestDistanceHit);
			if (subClosestDistanceHit > 0.0 && subClosestDistanceHit < closestDistanceHit) {
				hitEntity = context.entity;
				hitInbound = context.inbound;
				hitTexturePosition.copy(context.texturePosition);
				hitNormalMatrix.copy(context.normalMatrix);
				context.tempOrigin.copy(context.origin);
				context.tempDirection.copy(context.direction);
				closestDistanceHit = subClosestDistanceHit;
			}

			context.origin.copy(origin);
			context.direction.copy(direction);
		}

		if (node.above != null && node.above.intersects(origin, direction)) {
			double subClosestDistanceHit = intersect(context, node.above, closestDistanceHit);
			if (subClosestDistanceHit > 0.0 && subClosestDistanceHit < closestDistanceHit) {
				hitEntity = context.entity;
				hitInbound = context.inbound;
				hitTexturePosition.copy(context.texturePosition);
				hitNormalMatrix.copy(context.normalMatrix);
				context.tempOrigin.copy(context.origin);
				context.tempDirection.copy(context.direction);
				closestDistanceHit = subClosestDistanceHit;
			}
		}

		for (PhysicalEntity entity : node.cachedEntities) {

			context.origin.copy(origin);
			context.direction.copy(direction);

			if (entity.intersect(context)) {

				double dns = context.origin.distanceSquared(origin);

				if (dns < closestDistanceHit) {
					hitEntity = entity;
					hitInbound = context.inbound;
					hitTexturePosition.copy(context.texturePosition);
					hitNormalMatrix.copy(context.normalMatrix);
					context.tempOrigin.copy(context.origin);
					context.tempDirection.copy(context.direction);
					closestDistanceHit = dns;
				}
			}
		}

		if (hitEntity != null) {

			context.entity = hitEntity;
			context.direction.copy(context.tempDirection);
			context.origin.copy(context.tempOrigin);
			context.texturePosition.copy(hitTexturePosition);
			context.normalMatrix.copy(hitNormalMatrix);
			context.inbound = hitInbound;

			return closestDistanceHit;
		}

		return -1.0;
	}

	public boolean intersect(RayContext context)
	{
		assert context != null;

		context.entity = null;

		double r = intersect(context, root, Double.MAX_VALUE);

		return r > 0.0;
	}
}
