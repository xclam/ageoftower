package com.nsx.ageoftower.utils;

import java.util.ArrayList;

public class Wave {

	private ArrayList<Foe> _foes;
	
	public Wave(){
		_foes = new ArrayList<Foe>();
	}
	
	public Wave(ArrayList<Foe> foes){
		_foes = foes;
	}

	public ArrayList<Foe> get_foes() {
		return _foes;
	}

	public void set_foes(ArrayList<Foe> _foes) {
		this._foes = _foes;
	}
	
	
}
