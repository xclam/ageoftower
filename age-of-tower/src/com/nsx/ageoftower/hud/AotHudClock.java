package com.nsx.ageoftower.hud;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;



public class AotHudClock extends WidgetGroup {
	//-- constante partique
	public static final int STATE_STOPED = 0;
	public static final int STATE_RUNNING = 1;
	
	Label _label;
	Skin _skin;
	Image _img;
	int _state;
	float _seconds;
	
	
	public AotHudClock(Skin sk){
		_skin = sk;
		_seconds = 0;
		_label = new Label(toTime(_seconds),_skin.get("JUNEBUG_16",LabelStyle.class));
		_img = new Image(_skin.get("Clock",TextureRegion.class));
		_img.setVisible(true);
		_img.setSize(_label.getHeight()-5, _label.getHeight()-5);
		_label.setPosition(_img.getWidth()+5, 0);
		this.setSize( _label.getWidth()+_img.getWidth(), _label.getHeight());
		this.addActor(_label);
		this.addActor(_img);
		
		_state = STATE_STOPED;
	}
	
	@Override
	public void act(float delta){
		if(_state == STATE_RUNNING){
			_seconds += delta;
		}
		_label.setText(toTime(_seconds));
		super.act(delta);
	}

	private String toTime(float second) {
		String result;
		int min = (int)(second/60);
		int sec = (int)((int)(second)%60);
		result = String.format("%02d",min)+":"+String.format("%02d", sec);;
		return result;
	}
	
	public void start(){
		_state = STATE_RUNNING;
	}
	public void stop(){
		_state = STATE_STOPED;
	}
	
	public void reset(){
		_seconds = 0;
	}
}
