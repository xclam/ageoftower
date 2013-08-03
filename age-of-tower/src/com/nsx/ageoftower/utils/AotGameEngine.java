package com.nsx.ageoftower.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;
import com.nsx.ageoftower.AgeOfTower;
import com.nsx.ageoftower.event.AotEvent;
import com.nsx.ageoftower.hud.AotHud;
import com.nsx.ageoftower.screen.LevelSelectorScreen;

public final class AotGameEngine extends Group implements EventListener{
	
	private static AotGameEngine instance;
	
	public static final int STATE_BEFORE_FIRST_LAUNCH = 1;
	public static final int STATE_AUTOLAUNCH_WAVE = 2;
	public static final int STATE_GAMEOVER = 3;
	public static final int STATE_LEVEL_DONE = 4;
	
	public static final int TIME_BETWEEN_LAUNCH = 20;
	public static final int LIFE_AT_START = 21;
	
	int _state;
	int _currentWave;
	int _life;
	float _time;
	float _timeSinceLastLaunch;
	AotHud _hud;
	Level _level;
	AotStage _stage;
	AgeOfTower _aot;
	String _levelName;
	Json _json;
	
	public static AotGameEngine getInstance() {
	    if (null == instance) {
	        System.out.println("AotGameEngine asn t been initialized");
	    }
	    return instance;
	}

	public AotGameEngine(AotHud hud, String levelName, AotStage s, AgeOfTower aot){
		_aot = aot;
		instance = this;
		_levelName = levelName;
		_stage = s;
		_hud = hud;
		_json = new Json();
		_level = _json.fromJson(Level.class,Gdx.files.internal("data/"+_levelName+".json"));
		_life = LIFE_AT_START;
		this.addListener(this);
		this.addActor(_hud);
		setState(STATE_BEFORE_FIRST_LAUNCH);
	}

	
	public void setState(int state) {
		switch(state){
			case STATE_BEFORE_FIRST_LAUNCH:
				_state = STATE_BEFORE_FIRST_LAUNCH;
				_level = _json.fromJson(Level.class,Gdx.files.internal("data/"+_levelName+".json"));
				_stage.getEnnemies().clear();
				_time = 0;
				_timeSinceLastLaunch = 0;
				_currentWave = 0;
				_life = LIFE_AT_START;
				_hud.init();
				_hud.goldSetGold(0);
				_hud.goldStopIncrement();
				_hud.clockReset();
				_hud.clockStart();
				_hud.lifeSetLife(LIFE_AT_START);
				_hud.waveLaunchButtonSetTimer(TIME_BETWEEN_LAUNCH-1);
				_hud.waveLaunchButtonEnableButton();
				break;
			case STATE_AUTOLAUNCH_WAVE :
				_state = STATE_AUTOLAUNCH_WAVE;
				_hud.goldStartIncrement();
				_hud.waveLaunchButtonSetTimer(TIME_BETWEEN_LAUNCH-1);
				_hud.waveLaunchButtonStartTimer();
				break;
			case STATE_GAMEOVER:
				_state = STATE_GAMEOVER;
				AotEvent ae = new AotEvent(AotEvent.Type.gameOver,this);
				this.fire(ae);
				_hud.goldStopIncrement();
				_hud.showGameOver();
				break;
			case STATE_LEVEL_DONE:
				_state = STATE_LEVEL_DONE;
				//_hud.message("LEVEL DONE!", 0.7f);
				_hud.goldStopIncrement();
				_hud.waveLaunchButtonDisableButton();
				_hud.showScore(_life,_level.getGoalLife(),_time,_level.get_goalTime());
				break;
		}
	}

	@Override
	public void act(float delta) {
		_time += delta;
		switch(_state){
		case STATE_AUTOLAUNCH_WAVE:
			_timeSinceLastLaunch += delta;
			if(_timeSinceLastLaunch>TIME_BETWEEN_LAUNCH){
				launchNextWave();
				_timeSinceLastLaunch = 0;
			}
			if(_stage instanceof AotStage){
				AotStage s = ((AotStage) _stage);
				//-- checkif ennemies are still in the screen or in comming waves
				if(s.getEnnemies()!=null && s.getEnnemies().getChildren().size==0 && _level.getWaves().size()<=_currentWave ){
					setState(STATE_LEVEL_DONE);
				}
			}
			break;
		}			
		super.act(delta);
	}


	private void launchNextWave() {
		if(_level.getWaves().size()>_currentWave){
			//-- ajout des feo (acteur ennemies)
			ArrayList<Foe> foes = _level.getWaves().get(_currentWave).get_foes();
			
			for(Foe f:foes){
				f.init();
				((AotStage)(_stage)).addFoe(f);
				f.setStartPosition();
			}
			
			//-- maj variables gameplay et message 
			_currentWave+=1;
			_timeSinceLastLaunch = 0;
			_hud.message("LAUNCHING WAVE "+_currentWave+"!", (float) 1.5);
			this.setState(STATE_AUTOLAUNCH_WAVE);
		}
		if(_level.getWaves().size()<=_currentWave){
			_hud.waveLaunchButtonSetTimer(-1);
		}
	}

	/**
	 * This overriding organizes drawing of the sceene
	 * Wee want the hud to be drawn at the end
	 */
	public void draw (SpriteBatch batch, float parentAlpha) {
		if (isTransform()) applyTransform(batch, computeTransform());
		_stage.getTowers().draw(batch, parentAlpha);
		_stage.getEnnemies().draw(batch, parentAlpha);
		_hud.draw(batch, parentAlpha);
		if (isTransform()) resetTransform(batch);
	}
	
	public boolean handle(Event event) {
		if(event instanceof AotEvent){
			switch(((AotEvent) event).getType()){
				case towerClicked:
					((Tower)(((AotEvent) event).getRelatedActor())).setState(Tower.STATE_ENABLE);
					break;
				case nextLevelButtonClicked:
					setState(STATE_BEFORE_FIRST_LAUNCH);
					break;
				case exit:
					//-- an ennemie went out!
					dropLife();
					((AotEvent) event).getRelatedActor().remove();
					break;
				case launchButtonPressed:
					switch(_state){
						case STATE_BEFORE_FIRST_LAUNCH:
							this.setState(STATE_AUTOLAUNCH_WAVE);
							launchNextWave();
							break;
						case STATE_AUTOLAUNCH_WAVE:
							launchNextWave();
							break;
					}
					break;
				case goToLevelSelection:
					_aot.setScreen(new LevelSelectorScreen(_aot));
					break;
				case restartLevel:
					setState(STATE_BEFORE_FIRST_LAUNCH);
					break;
			}
			System.out.println("AotEvent receieved! !:"+event);
		}
		return false;
	}

	private void dropLife() {	
		if(_life>1){
			_life-=1;
			_hud.lifeSetLife(_life);
		}else{
			if(_state != STATE_GAMEOVER)	
				setState(STATE_GAMEOVER);
		}
	}
}

