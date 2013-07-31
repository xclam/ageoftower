package com.nsx.ageoftower.hud;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;



public class AotHudGold extends WidgetGroup {
	public static final int STATE_INCREMENTING= 0;
	public static final int STATE_NOT_INCREMENTING= 1;
	public static final int STEP_TIME_INCREMENTING = 5;
	public static final int STEP_AMOUT_INCREMENTING = 3;
	
	Label _label;
	Skin _skin;
	Image _img;
	int _value;
	int _state;
	float _time;
	
	public AotHudGold(Skin sk){
		_state = STATE_NOT_INCREMENTING;
		_skin = sk;
		_label = new Label(""+_value,_skin.get("JUNEBUG_16",LabelStyle.class));
		_img = new Image(_skin.get("gold",TextureRegion.class));
		_img.setVisible(true);
		_img.setSize(_label.getHeight()-5, _label.getHeight()-5);
		_label.setPosition(_img.getWidth()+5, 0);
		this.setSize( _label.getWidth()+_img.getWidth(), _label.getHeight());
		this.addActor(_label);
		this.addActor(_img);
	}
	
	@Override
	public void act(float delta){
		if(_state == STATE_INCREMENTING){
			_time += delta;
			if(_time>STEP_TIME_INCREMENTING){
				_value += STEP_AMOUT_INCREMENTING;
				_time = 0;
			}
		}
		
		_label.setText(""+_value);
		super.act(delta);
	}
	
	public void addGold(int value){
		_value += value;
	}
	
	public void setGold(int value){
		_value = value;
	}

	public void startInc() {
		_state = STATE_INCREMENTING;
	}

	public void stopInc() {
		_state = STATE_NOT_INCREMENTING;
	}
}
