package com.nsx.ageoftower.utils;

import java.util.ArrayList;

public class Level {
	
	// The name of the stage should be the same as .tmx 
	private String _name; 
	private boolean _locked;
	private int nbWave;
	private ArrayList<Wave> waves;
	private int goalTime[];
	private int goalLife[];
 

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


}