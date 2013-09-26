package com.nsx.ageoftower.utils;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CollisionDetector {

	/**
	 * Check collision from actor's rectangles
	 * */
	public static boolean isActorsCollide(Actor actor1, Actor actor2) {
		if (Intersector.overlapRectangles(
				getRectangleOfActor(actor1),
				getRectangleOfActor(actor2))) {
			logCollision1(actor1, actor2);
			return true;
		} else {
			return false;
		}
	}

	private static Rectangle getRectangleOfActor(Actor actor) {
		return new Rectangle(actor.getX(), actor.getY(), actor.getWidth(),
				actor.getHeight());
	}

	/**
	 * Very precise point detection in a box, think as virtual box in the actual
	 * box with padding as precision amount
	 * */
	public static boolean isTouchPointCollide(float touchX, float touchY,
			float posX, float posY, float width, float height,
			float precisionAmount) {
		if (touchX > (posX + precisionAmount)
				&& touchX < (posX + width - precisionAmount)
				&& touchY > (posY + precisionAmount)
				&& touchY < (posY + height - precisionAmount)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Very precise point detection in an actor, think as virtual box in the
	 * actual box with margin as precision amount
	 * */
	public static boolean isTouchPointCollide(float touchX, float touchY,
			Actor actor, float precisionAmount) {
		if (touchX > (actor.getX() + precisionAmount)
				&& touchX < (actor.getX() + actor.getWidth() - precisionAmount)
				&& touchY > (actor.getY() + precisionAmount)
				&& touchY < (actor.getY() + actor.getHeight() - precisionAmount)) {
			logCollision2(actor);
			return true;
		} else {
			return false;
		}
	}

	private static void logCollision1(Actor a1, Actor a2) {
		System.out.println("Collision detected: Actor (Name: " + a1.getName()
						+ ") and Actor (Name: " + a2.getName() + ")");
	}

	private static void logCollision2(Actor a1) {
		System.out.println("Collision detected on touch point: Actor (Name: "
						+ a1.getName() + ")");
	}
}