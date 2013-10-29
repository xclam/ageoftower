package com.nsx.ageoftower.collision;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
/**
 * @deprecated
 * @author Admin
 *
 */
public class CollisionManager {
	private static final Integer RECTANGLE = 1;
	private static final Integer CIRCLE = 1;
	
	public static boolean isActorsCollide(Actor actor1, Actor actor2) {
		if (Intersector.overlapCircles(getCircleOfActor(actor1),
				getCircleOfActor(actor2))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Rectangle getRectangleOfActor(Actor actor) {
		return new Rectangle(actor.getX(), actor.getY(), actor.getWidth(),
				actor.getHeight());
	}
	
	public static Circle getCircleOfActor(Actor actor) {
		return new Circle(actor.getX(), actor.getY(), actor.getWidth());
	}
}
