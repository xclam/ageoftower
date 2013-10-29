package com.nsx.ageoftower.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CollisionDetector {

	/**
	 * Check collision from actor's rectangles
	 * */
	public static boolean isActorsCollide(Actor actor1, Actor actor2) {
		if (Intersector.overlapCircles(
				getCircleOfActor(actor1),
				getCircleOfActor(actor2))) {
			logCollision1(actor1, actor2);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @todo Il faut passer par une ellipse pour etre au plus pres de l'actor
	 * @param actor
	 * @return
	 */
	private static Circle getCircleOfActor(Actor actor) {
		Vector2 v = new Vector2();
		actor.localToStageCoordinates(v);
		Circle c = new Circle(v.x+actor.getWidth()/2, v.y+actor.getHeight()/2, actor.getWidth()/2);
		return c;
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
		System.out.println("Collision detected: Actor (Name: " + a1
						+ ") and Actor (Name: " + a2 + ")");
	}

	private static void logCollision2(Actor a1) {
		System.out.println("Collision detected on touch point: Actor (Name: "
						+ a1 + ")");
	}
}