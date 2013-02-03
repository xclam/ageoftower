package com.nsx.ageoftower.utils;

import java.sql.Time;
import java.util.Vector;

public class Level{
	String _name;
	boolean _locked;
	private int _nbWave;
	private Vector<Wave> _waves;
	private Time _goalTime[];
	private int _goalLife[];
	
	public Level(){
		_waves = new Vector<Wave>();
		_nbWave = 0;
		_goalTime = new Time[3];
		_goalLife = new int[3];
	}
	
	public Level(String name, Boolean lck){
		_name = name;
		_locked = lck;
		_nbWave = 0;
		_goalTime = new Time[3];
		_goalLife = new int[3];
	}
	
	public Level(String name, Boolean lck, int nbWave, Time gTime[], int gLife[]){
		_name = name;
		_locked = lck;
		_nbWave = nbWave;
		_goalTime = gTime;
		_goalLife = gLife;
	}
	
	public boolean isLocked(){
		return _locked;
	}
}
