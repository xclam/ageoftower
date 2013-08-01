package com.nsx.ageoftower.hud;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.nsx.ageoftower.event.AotEvent;


public class AotHudLaunchButton extends WidgetGroup implements  EventListener{
	public static final int STATE_DISABLE = 0;
	public static final int STATE_ENABLE = 1;
	public static final int STEP_TIME_INCREMENTING = 5;
	
	Skin _skin;
	Actor _img;
	private boolean _clicked;
	AotHudTimer _timer;
	int _state;
	
	public AotHudLaunchButton(Skin sk){
		_skin = sk;
		_timer = new AotHudTimer("", 0, _skin.get("JUNEBUG_16",LabelStyle.class));
		_img = new Image(_skin.get("circle",TextureRegion.class));
		_img.setSize(75, 75);
		this.setSize(_img.getWidth(),_img.getHeight());
		_img.addListener(this);
		_img.fire(new Event());
		_timer.setPosition(_img.getWidth()/2-7,
							_img.getHeight()/2-_timer.getHeight()/2);
		_clicked = false;
		this.addActor(_img);
		this.addActor(_timer);
	}

	@Override
	public boolean handle(Event event) {
		if (!(event instanceof InputEvent)) return false;
		if(((InputEvent)event).getButton() == 0){
			if( !_clicked){
				System.out.println(" AotHudLaunchButton new event:"+((InputEvent)event).getButton());	
				this.fire(new AotEvent(AotEvent.Type.launchButtonPressed,this));
				_clicked = true;
			}else{
				_clicked = false;
			}
		}
		return true;
	}
	
	public void setTimer(int val){
		_timer.setSeconds(val);
		_timer.start();
		bumpUp();
	}
	
	public void bumpNeg(){
		SequenceAction sequence = new SequenceAction();
		sequence.addAction(Actions.moveBy(5, 0, 0.05f));
		sequence.addAction(Actions.moveBy(-5, 0, 0.05f));
		sequence.addAction(Actions.moveBy(5, 0, 0.05f));
		sequence.addAction(Actions.moveBy(-5, 0, 0.05f));
		_img.addAction(sequence);
	}
	
	public void bumpUp(){
		SequenceAction sequence = new SequenceAction();
		sequence.addAction(Actions.moveBy(0, 5, 0.10f));
		sequence.addAction(Actions.moveBy(0, -5, 0.10f));
		_img.addAction(sequence);
	}
	
	
}
