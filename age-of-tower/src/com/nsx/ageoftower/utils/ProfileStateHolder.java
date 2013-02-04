package com.nsx.ageoftower.utils;

import java.util.ArrayList;

//-- class permettant l'enregistrement de l'état du jeu selon un profile choisi
public class ProfileStateHolder {
	private String _profileName;
	private ArrayList<Level> _lvlList;

	public ProfileStateHolder(){
	}
	
	public String getProfileName() {
		return _profileName;
	}

	public void setProfileName(String _profileName) {
		this._profileName = _profileName;
	}

	public ArrayList<Level> getLvlList() {
		return _lvlList;
	}

	public void setLvlList(ArrayList<Level> _lvlList) {
		this._lvlList = _lvlList;
	}
	
	public ProfileStateHolder(String name){	
		_profileName = name;
		_lvlList = new ArrayList<Level>();
		_lvlList.add(new Level("1",false)); 
		_lvlList.add(new Level("2",true));
		_lvlList.add(new Level("3",true));
		_lvlList.add(new Level("4",true));
		_lvlList.add(new Level("5",true));
		_lvlList.add(new Level("6",true));
		_lvlList.add(new Level("7",true));
		_lvlList.add(new Level("8",true));
	}
}
