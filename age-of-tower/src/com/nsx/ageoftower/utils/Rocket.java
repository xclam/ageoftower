package com.nsx.ageoftower.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Rocket extends Image {

	private Type _type;
	private Vector2 _target;
	
	public Rocket(Type t){
		//Texture T  = new Texture(Gdx.files.internal("data/arrow.png"));
		super(new TextureRegion(
				new Texture(Gdx.files.internal("data/"+t+".png")),
				0,	
				0,
				128,
				16
			));
		_target = new Vector2();
		_type = t;
	}
	
	static public enum Type {
		arrow,
		firearrow
	}

	public void move(float x, float y, float x2, float y2) {
		// TODO Auto-generated method stub
		this.setPosition(x, y);
		this.setVisible(true);
		_target.x = x2;
		_target.y = y2;
		System.out.println("Rocket shoot"+this);
		this.addAction(Actions.moveTo(x2, y2, 5));
	}
	
	public void act(float delta){
		if (this.getX() == _target.x &&
				this.getY() == _target.y){
			//this.remove();
		}
	}
}
