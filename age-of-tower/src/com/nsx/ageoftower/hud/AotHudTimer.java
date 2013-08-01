package com.nsx.ageoftower.hud;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class AotHudTimer extends Label {
	//-- constante partique
	public static final int STATE_STOPED = 0;
	public static final int STATE_RUNNING = 1;
	
	int _secondsLeft;
	int _secondsTotal;
	int _state;
	float _secondsSpent;
	String _text;
	LabelStyle _labelStyle;
	
	
	public AotHudTimer(String text, int total, LabelStyle labelStyle){
		super(text, labelStyle);
		_labelStyle = labelStyle;
		_state = STATE_STOPED;
		_text = text;
		this.setSeconds(total);
	}
	
	public void setSeconds(int sec){
		_secondsTotal = sec;
		reset();
	}
	
	public void reset(){
		_secondsLeft = _secondsTotal;
		_secondsSpent = 0;
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
		if((_secondsTotal-(int)_secondsSpent)>=0){
			_secondsLeft =  (_secondsTotal-(int)_secondsSpent);
		}else{
			_state = STATE_STOPED;
		}
		if(_secondsTotal==-1){
			this.setText("--");
		}else{
			this.setText(_text+_secondsLeft);
		}
		super.act(delta);
	}

	
	public void setFontSize(LabelStyle lbls){
		this.setStyle(lbls);
	}
	public void setFontScale(float fontScaleX,float fontScaleY){
		this.setFontScale(fontScaleX, fontScaleY);
	}
	
}
