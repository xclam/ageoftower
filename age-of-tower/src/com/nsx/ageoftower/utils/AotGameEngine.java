package com.nsx.ageoftower.utils;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.nsx.ageoftower.hud.AotHud;
import com.nsx.ageoftower.screen.AbstractScreen;

public final class AotGameEngine {
	
	private static AotGameEngine instance;
	
	public static final int STATE_BEFORE_FIRST_LAUNCH = 1;
	public static final int STATE_AUTOLAUNCH_WAVE = 2;
	public static final int STATE_GAMEOVER = 3;
	public static final int STATE_LEVEL_DONE = 4;
	
	public static final int TIME_BETWEEN_LAUNCH = 20;
	public static final int LIFE_AT_START = 20;
	
	int _state;
	int _currentWave;
	float _time;
	float _timeSinceLastLaunch;
	AotHud _hud;
	Level _level;
	Stage _stage;
	
	public static AotGameEngine getInstance() {
	    if (null == instance) {
	        System.out.println("AotGameEngine asn t been initialized");
	    }
	    return instance;
	}

	public AotGameEngine(AotHud hud, Level level, Stage s){
		instance = this;
		_stage = s;
		_hud = hud;
		_level = level;
		setState(STATE_BEFORE_FIRST_LAUNCH);
	}


	public void setState(int state) {
		switch(state){
			case STATE_BEFORE_FIRST_LAUNCH:
				_hud.goldSetGold(0);
				_hud.goldStopIncrement();
				_hud.clockReset();
				_hud.clockStart();
				_hud.lifeSetLife(LIFE_AT_START);
				_hud.waveLaunchButtonEnableButton();
				_state = STATE_BEFORE_FIRST_LAUNCH;
				break;
			case STATE_AUTOLAUNCH_WAVE :
				_hud.goldStartIncrement();
				_hud.enableLaunchWaveButton();
				_hud.waveLaunchButtonSetTimer(TIME_BETWEEN_LAUNCH-1);
				_state = STATE_AUTOLAUNCH_WAVE;
				break;
			case STATE_GAMEOVER:
				
				break;
			case STATE_LEVEL_DONE:
				_hud.goldStopIncrement();
				_hud.waveLaunchButtonDisableButton();
				break;
		}
		
	}


	public void update(float delta) {
		_time += delta;
		switch(_state){
		case STATE_AUTOLAUNCH_WAVE:
			_timeSinceLastLaunch += delta;
			if(_timeSinceLastLaunch>TIME_BETWEEN_LAUNCH){
				_timeSinceLastLaunch = 0;
				launchNextWave();
			}
			break;
		}
		
	}


	private void launchNextWave() {
		if(_level.getWaves().size()>_currentWave){
			//-- ajout des feo (acteur ennemies)
			ArrayList<Foe> feo = _level.getWaves().get(_currentWave).get_foes();
			
			int i=0;
			
			for(Foe f:feo){
				i++;
				f.init();
				_stage.addActor(f);
				System.out.println(""+i);
				f.setPosition(10, 280+25*i);
			}
			
			//-- maj variables gameplay et message 
			_currentWave+=1;
			_hud.message("LAUNCHING WAVE "+_currentWave+"!", (float) 1.5);
			//-- si une vage est a suivre on relance le chrono sinon stop
			if(_level.getWaves().size()>_currentWave){
				_hud.waveLaunchButtonSetTimer(TIME_BETWEEN_LAUNCH-1);
			}else{
				_hud.waveLaunchButtonSetTimer(-1);
			}
			_timeSinceLastLaunch = 0;
		}else{
			_hud.waveLaunchButtonSetTimer(-1);
		}
		
	}

	public void launchButtonPressed() {
		switch(_state){
		case STATE_BEFORE_FIRST_LAUNCH:
			this.setState(STATE_AUTOLAUNCH_WAVE);
			launchNextWave();
			break;
		case STATE_AUTOLAUNCH_WAVE:
			launchNextWave();
			break;
		}
	}
}

