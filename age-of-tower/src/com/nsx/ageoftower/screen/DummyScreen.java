package com.nsx.ageoftower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.nsx.ageoftower.AgeOfTower;
import com.nsx.ageoftower.hud.AotHud;
import com.nsx.ageoftower.utils.AotGameEngine;
import com.nsx.ageoftower.utils.Level;

public class DummyScreen extends AbstractScreen {
	
	public static final int PHASE_PREPARE = 0;
	public static final int PHASE_WAVE_PROGRESSING = 1;
	public static final int PHASE_WAVE_END = 2;

	
	int currentLvl = 9999;
	Label _lbl;
	AgeOfTower _aot;
	int _state;
	AotGameEngine _engine;
	
	public int getState() {
		return _state;
	}

	public void setState(int _state) {
		this._state = _state;
	}

	public DummyScreen(AgeOfTower aot) {
		super(aot);
		_aot = aot;
		_state = PHASE_PREPARE;
		
		//-- a supprimer lorsque tou sera rassemblé, issue:15
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("HUD/hud.pack"));
		this._mSkin =  new Skin(Gdx.files.internal("skin/default2.skin"),atlas);
		
		_lbl = new Label("", _mSkin.get("labelstyle",LabelStyle.class));
		_mStage.addActor(_lbl);
		//_mStage.addListener(new DsDragListener( )); 
		AotHud hud = new AotHud(this.getSkin());
		_mStage.addActor(hud);
		_mStage.addListener(hud);
		//_engine = new AotGameEngine(hud,"level1",_mStage,_aot);
	}
	
	public void setLvl(int lvl){
		currentLvl = lvl;
		update();
	}
	
	public void setLvl(String lvl){
		currentLvl = new Integer(lvl);
		update();
	}
	
	@Override
	public void renderStarted(){
		//-- apparition a partir de la guche
		_lbl.addAction(Actions.moveBy(-GAME_VIEWPORT_WIDTH, 0,0.3f));
		
		_engine.setState(AotGameEngine.STATE_LEVEL_DONE);
	}
	
	public void update(){
		_lbl.setText("LEVEL : "+currentLvl);
		_lbl.setPosition(
				GAME_VIEWPORT_WIDTH+GAME_VIEWPORT_WIDTH/2-150, 
				GAME_VIEWPORT_HEIGHT/2-_lbl.getHeight());
	}
}

	/*
	//-- capteur d'u=interuption pour le scrolling
	private class DsDragListener extends DragListener{
		
		@Override
		public boolean touchDown(InputEvent event,
                float x,
                float y,
                int pointer,
                int button){
			return false;
			//-- si le label est encore present
			/*
			if (_mStage.getRoot().isAscendantOf(_lbl)){
				SequenceAction sequence = new SequenceAction();
				sequence.addAction(Actions.moveBy(-GAME_VIEWPORT_WIDTH*2, 0,0.3f));
				sequence.addAction(Actions.removeActor());
				_lbl.addAction(sequence);
			}else{
					Gdx.app.exit();
			}
			return true;*/


