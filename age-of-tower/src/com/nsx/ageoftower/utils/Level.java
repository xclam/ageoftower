package com.nsx.ageoftower.utils;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nsx.ageoftower.AgeOfTower;


public class Level {
	
	// The name of the stage should be the same as .tmx 
	private String _name; 
	private boolean _locked;
	private int nbWave;
	private ArrayList<Wave> waves;
	private int goalTime[];
	private int goalLife[];
 
	private ArrayList<Point> _path;
	private Stage _stage;

	public Level(){
		waves = new ArrayList<Wave>();
		nbWave = 0;
		goalTime = new int[3];
		goalLife = new int[3];
	}
	 
	public Level(String name, Boolean lck){
		_name = name;
		_locked = lck;
		nbWave = 0;
		goalTime = new int[3];
		goalLife = new int[3];
	}
	
	public Level(String name, Boolean lck, int nbWave, ArrayList<Wave> waves, int gTime[], int gLife[]){
		_name = name;
		_locked = lck;
		this.nbWave = nbWave;
		this.waves = waves;
		this.goalTime = gTime;
		this.goalLife = gLife;
		//
		_path = new ArrayList<Point>();
		_path.add(new Point(0,0));
		_path.add(new Point(10,10));
		_path.add(new Point(50,50));
	}
	
	public boolean isLocked(){
		return _locked;
	}
	
	public int getNbWave() {
		return nbWave;
	}

	public void setNbWave(int nbWave) {
		this.nbWave = nbWave;
	}

	public ArrayList<Wave> getWaves() {
		return waves;
	}

	public void setWaves(ArrayList<Wave> waves) {
		this.waves = waves;
	}

	public int[] get_goalTime() {
		return goalTime;
	}

	public void setGoalTime(int[] goalTime) {
		this.goalTime = goalTime;
	}

	public int[] getGoalLife() {
		return goalLife;
	}

	public void setGoalLife(int[] goalLife) {
		this.goalLife = goalLife;
	}

	public String getName(){
		return this._name;
	}

	public void setStage(Stage stage){
		this._stage = stage;
	}
	
	/**
	 * Launch the game with the start of the first wave
	 */
	public void start(Stage stage){
		if (!waves.isEmpty()){
			Wave w = waves.remove(0);
			Gdx.app.log( AgeOfTower.LOG, "Start a new wave" );
			w.start(stage);
		}
	}
	

}
