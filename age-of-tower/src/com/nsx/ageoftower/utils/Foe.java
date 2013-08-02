package com.nsx.ageoftower.utils;


import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.nsx.ageoftower.event.AotEvent;
import com.nsx.ageoftower.screen.AbstractScreen;


public class Foe extends Group{

	private int _life;
	private float _speed;
	private float _armor;
	private float _actualLife;
	private Texture _texture;
	private ArrayList<Vector2> _path;
	private Image _enemieimg;
	private String _imagePath;
	
	public Foe(){}

	public Foe(int _life, float _speed, float _armor) {
		this._life = _life;
		this._speed = _speed;
		this._armor = _armor;
		this._actualLife = _life;
		_path = new ArrayList<Vector2>();
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
	

	public void set_path(ArrayList<Vector2> path){
		this._path = path;
	}

	public void start(Stage stage) {

	}
	
	public void act(float delta){
		
		super.act(delta);
		
		Vector2 v = new Vector2(0,0);
		
		if (this.getActions().size == 0 && !_path.isEmpty()){
			float x=0,y=0;
			
			x = this.getX();
			y = this.getY();
			
			v = _path.remove(0);
			v.x = v.x * 32;
			v.y = v.y * 32;
			
			this.addAction(Actions.moveTo(v.x, v.y,v.dst(x,y)/32));
		}else if(_path.isEmpty()){
			//Gdx.app.log( AgeOfTower.LOG, "Fin" );
			AotEvent event = new AotEvent(AotEvent.Type.exit,this);
			this.fire(event);
		}
	}
	
	public void setStartPosition(){
		Vector2 v = _path.remove(0);
		v.x = v.x * 32;
		v.y = v.y * 32;
		this.setPosition(v.x, v.y);
	}
}


