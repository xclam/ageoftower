package com.nsx.ageoftower.utils;

import java.util.Vector;

public class Wave {

	private Vector<Foe> _foes;
	
	public Wave(){
		_foes = new Vector<Foe>();
	}
	
	public Wave(Vector<Foe> foes){
		_foes = foes;
	}
	
}
