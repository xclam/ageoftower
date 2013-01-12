package com.nsx.ageoftower.utils;

public class Level{
	String _name;
	boolean _locked;
	
	public Level(){
	}
	
	public Level(String name, Boolean lck){
		_name = name;
		_locked = lck;
	}
	
	public boolean isLocked(){
		return _locked;
	}
}
