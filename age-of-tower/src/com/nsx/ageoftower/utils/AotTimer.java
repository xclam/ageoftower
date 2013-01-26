package com.nsx.ageoftower.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class AotTimer extends Group {
	//-- constante partique
	public static final int STATE_STOPED = 0;
	public static final int STATE_RUNNING = 1;
	
	int _secondsLeft;
	int _secondsTotal;
	int _state;
	float _secondsSpent;
	String _text;
	Label _lbl;
	Skin _skin;
	
	
	public AotTimer(String text, int total, Skin skin){
		super();
		_skin = skin;
		_state = STATE_STOPED;
		_text = text;
		this.setSeconds(total);
		
		_lbl = new Label("", _skin.get("JUNEBUG_32",LabelStyle.class));
		this.addActor(_lbl);
	}
	
	public void setSeconds(int sec){
		_secondsTotal = sec;
		reset();
	}
	
	public void reset(){
		_secondsLeft = _secondsTotal;
	}
	
	public void start(){
		_state = STATE_RUNNING;
	}
	
	public void stop(){
		_state = STATE_STOPED;
	}
	
	public boolean isRunning(){
		return _state == STATE_RUNNING;
	}
	
	@Override
	public void act(float delta){
		if(_state == STATE_RUNNING){
			_secondsSpent += delta;
		}
		_secondsLeft =  (_secondsTotal-(int)_secondsSpent); 
		_lbl.setText(_text+" : "+_secondsLeft);
		super.act(delta);
	}
	
	@Override
	public float getWidth(){
		return _lbl.getWidth();
	}
	@Override
	public float getHeight(){
		return _lbl.getHeight();
	}

	
	public void setFontSize(int size){
		_lbl.setStyle( _skin.get("JUNEBUG_"+size,LabelStyle.class));
	}
	public void setFontScale(float fontScaleX,float fontScaleY){
		_lbl.setFontScale(fontScaleX, fontScaleY);
	}
	
}
