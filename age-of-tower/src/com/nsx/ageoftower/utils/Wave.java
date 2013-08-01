package com.nsx.ageoftower.utils;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nsx.ageoftower.AgeOfTower;

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


	public void start(Stage stage) {
		if (!_foes.isEmpty()){
			Foe f = _foes.remove(0);
			Gdx.app.log( AgeOfTower.LOG, "Start a new foe" );
			f.start(stage);
		}
	}
	
	
}

