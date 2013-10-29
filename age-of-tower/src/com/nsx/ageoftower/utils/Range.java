package com.nsx.ageoftower.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * La classe ne sert à rien car filnalement on passe par localToStageCoordinates
 * pour obtenir les coordonnées réelles de l'actor.
 * A remettre dans Tower
 * @author Admin
 *
 */
public class Range extends Image{

	private Image _iRange;
	private float _radius;
	
	public Range(){
		//-- the range
		super(new TextureRegion(
			new Texture(Gdx.files.internal("data/range.png")),
			0, 
			0, 
			128, 
			128
		));
		this.setSize(140,140);
		this.setVisible(true);
	}
	
	public Range(float radius){
		//-- the range
		super(new TextureRegion(
			new Texture(Gdx.files.internal("data/range.png")),
			0, 
			0, 
			128, 
			128
		));
		this._radius = radius;
		this.setSize(_radius,_radius);
		this.setVisible(true);
	}
}
