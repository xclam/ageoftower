package com.nsx.ageoftower.hud;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.nsx.ageoftower.AgeOfTower;
import com.nsx.ageoftower.utils.AotGameEngine;

public class AotHudSound extends WidgetGroup implements EventListener {

	private Skin _skin;
	private Image _sound_on,_sound_off;
	private boolean _mute;
	
	public boolean isMute() {
		return _mute;
	}

	public AotHudSound (Skin s){
		_skin = s;
		_sound_on = new Image(_skin.get("sound_on",TextureRegion.class));
		_sound_off = new Image(_skin.get("sound_off",TextureRegion.class));
		_sound_on.setVisible(false);
		_sound_off.setVisible(false);
		this.setSize( _sound_on.getWidth(), _sound_on.getHeight());
		this.setSize( _sound_off.getWidth(), _sound_off.getHeight());
		_sound_on.setPosition(750, 10);
		_sound_off.setPosition(750, 10);
		_sound_on.addListener(this);
		_sound_off.addListener(this);
		this.addActor(_sound_on);
		this.addActor(_sound_off);
	}
	
	public void init(){
			
	}

	public void setSound(boolean musicEnabled) {
		if (musicEnabled){
			_sound_on.setVisible(true);
			_sound_off.setVisible(false);
			_mute = false;
		}else{
			_sound_on.setVisible(false);
			_sound_off.setVisible(true);
			_mute = true;
		}
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
	}
	
	@Override
	public boolean handle(Event event) {
		if (!(event instanceof InputEvent)) return false;
		
		if (((InputEvent)event).getType() == InputEvent.Type.touchDown){
			if (this.isMute()){
				System.out.println(" unmute ");
				setSound(true);
				AotGameEngine.getInstance().get_aot().getMusicManager().mute(false);
			}else{
				System.out.println(" mute ");
				setSound(false);
				AotGameEngine.getInstance().get_aot().getMusicManager().mute(true);
			}
		}
		return true;
	}
}
