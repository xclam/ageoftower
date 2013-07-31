package com.nsx.ageoftower.utils;


import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.nsx.ageoftower.AgeOfTower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Foe extends Group{

	private int _life;
	private float _speed;
	private float _armor;
	private float _actualLife;
	private Texture _texture;
	private ArrayList<Point> _path;
	private Image _enemieimg;
	private String _imagePath;
	
	public Foe(){}

	public Foe(int _life, float _speed, float _armor) {
		this._life = _life;
		this._speed = _speed;
		this._armor = _armor;
		this._actualLife = _life;
		_path = new ArrayList<Point>();
		_imagePath = "data/enemi-frog_1.png";
	}
	
	public void init(){
		_texture = new Texture(Gdx.files.internal(_imagePath));
		_enemieimg = new Image(_texture);
		this.addActor(_enemieimg);
	}

	public int get_life() {
		return _life;
	}

	public void set_life(int _life) {
		this._life = _life;
	}

	public float get_speed() {
		return _speed;
	}

	public void set_speed(float _speed) {
		this._speed = _speed;
	}

	public float get_armor() {
		return _armor;
	}

	public void set_armor(float _armor) {
		this._armor = _armor;
	}

	public float get_actualLife() {
		return _actualLife;
	}

	public void set_actualLife(float _actualLife) {
		this._actualLife = _actualLife;
	}
	

	public void setPath(ArrayList<Point> path){
		this._path = path;
	}

	public void start(Stage stage) {
		/*_texture = new Texture(Gdx.files.internal("data/enemi-frog_1.png"));
		enemieimg = new Image(_texture);
		enemieimg.setPosition(20, 20);
		Gdx.app.log( AgeOfTower.LOG, "Adding the Foe to the Stage" );
		this.toFront();
		this.setPosition(20, 20);
		stage.addActor(this);
		*/
		//this.addAction(Actions.moveTo(200, 200, 100));
	}
	
	public void act(float delta){
		Gdx.app.log( AgeOfTower.LOG, ""+this.getX() );
		Gdx.app.log( AgeOfTower.LOG, ""+this.isVisible() );
		super.act(delta);
	}
	
}


