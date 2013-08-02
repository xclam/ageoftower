package com.nsx.ageoftower.event;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;

public class AotEvent extends Event{
	private Type type;
	private Actor relatedActor;
	
	public AotEvent(Type t, Actor a) {
		type = t;
		relatedActor = a;
	}

	public AotEvent() {
		// TODO Auto-generated constructor stub
	}

	/** The type of input event. */
	public Type getType () {
		return type;
	}

	public void setType (Type type) {
		this.type = type;
	}
	
	/** The actor related to the event. Valid for: enter and exit. For enter, this is the actor being exited, or null. For exit,
	 * this is the actor being entered, or null. */
	public Actor getRelatedActor () {
		return relatedActor;
	}

	/** @param relatedActor May be null. */
	public void setRelatedActor (Actor relatedActor) {
		this.relatedActor = relatedActor;
	}
	
	public String toString () {
		return type.toString();
	}
	
	static public enum Type {
		/** A new touch for a pointer on the stage was detected */
		die,
		/** A pointer has stopped touching the stage. */
		exit,
		towerClicked,
		nextLevelButtonClicked,
		launchButtonPressed,
		goToLevelSelection,
		restartLevel
	}
}
