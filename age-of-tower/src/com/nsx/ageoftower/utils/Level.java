package com.nsx.ageoftower.utils;


import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class Level {
	
	// The name of the stage should be the same as .tmx 
	private String _name; 
	private boolean _locked;
	private int nbWave;
	private ArrayList<Wave> waves;
	private int goalTime[];
	private int goalLife[];
	private ArrayList<Vector2> _path;
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
		set_path(new ArrayList<Vector2>());
		get_path().add(new Vector2(0,0));
		get_path().add(new Vector2(10,10));
		get_path().add(new Vector2(50,50));

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
	
	public int[] getGoalTime() {
		return goalTime;
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
	 * Initialize
	 */
	public void init(){
		for (Wave wave : this.getWaves()){
			for (Foe f : wave.get_foes()){
				f.set_path(this.get_path());
			}
		}
	}
	
	public void start(Stage stage){

	}

	public ArrayList<Vector2> get_path() {
		return _path;
	}

	public void set_path(ArrayList<Vector2> _path) {
		this._path = _path;
	}

	public void unlock() {
		_locked = false;
	}
	

}


