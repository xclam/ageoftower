package com.nsx.ageoftower.utils;

public class LevelStats {
	private String _name; 
	private boolean _locked = false;
	private String _score = ""; 
	
	public LevelStats(){
		
	}
	public LevelStats(String name){
		_name = name;
	}
	public LevelStats(String name, boolean locked) {
		_name = name;
		_locked = locked;
	}
	public String getName() {
		return _name;
	}
	public void unlock() {
		_locked = false;
	}
	public boolean isLocked() {
		return _locked;
	}
	public void setScore(String score){
		_score=score;
	}
	public String getScore(){
		return _score;
	}
}
