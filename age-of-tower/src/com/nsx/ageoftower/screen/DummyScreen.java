package com.nsx.ageoftower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.nsx.ageoftower.AgeOfTower;

public class DummyScreen extends AbstractScreen {
	int currentLvl = 9999;
	Label _lbl;
	Skin _skin;
	AgeOfTower _aot;
	
	public DummyScreen(AgeOfTower aot) {
		super(aot);
		_aot = aot;
		_skin = new Skin(Gdx.files.internal("data/DummyScreen/DummyScreen.skin"));	
		_lbl = new Label("", _skin.get("labelstyle",LabelStyle.class));		
		_mStage.addActor(_lbl);
		_mStage.addListener(new DsDragListener( )); 
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
	}
	
	public void update(){
		_lbl.setText("LEVEL : "+currentLvl);
		_lbl.setPosition(
				GAME_VIEWPORT_WIDTH+GAME_VIEWPORT_WIDTH/2-150, 
				GAME_VIEWPORT_HEIGHT/2-_lbl.getHeight());
	}
	
	//-- capteur d'u=interuption pour le scrolling
	private class DsDragListener extends DragListener{
		
		@Override
		public boolean touchDown(InputEvent event,
                float x,
                float y,
                int pointer,
                int button){
			//-- si le label est encore present
			if (_mStage.getRoot().isAscendantOf(_lbl)){
				SequenceAction sequence = new SequenceAction();
				sequence.addAction(Actions.moveBy(-GAME_VIEWPORT_WIDTH*2, 0,0.3f));
				sequence.addAction(Actions.removeActor());
				_lbl.addAction(sequence);
			}else{
				Gdx.app.exit();
			}
			return true;
		}
	}
}
