package com.nsx.ageoftower.hud;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.nsx.ageoftower.screen.AbstractScreen;

public class AotHudMessage extends WidgetGroup{
	Label _label;
	Skin _skin;
	float _displayTime;
	float _time;
	float _targetX;
	float _targetY;
	
	public AotHudMessage(String string, float d, Skin sk){
		_skin = sk;
		_displayTime = d;
		_label = new Label(string,_skin.get("JUNEBUG_32",LabelStyle.class));
		_label.setVisible(true);
		this.setSize(_label.getWidth(), _label.getHeight());
		this.addActor(_label);
	}
	
	@Override
	public void act(float delta){
		_time += delta;
		if(_time>_displayTime+0.40f){
			this.remove();
		}
		super.act(delta);
	}

	public void setTargetPosition(float x, float y) {
		_targetX = x;
		_targetY = y;
	}

	public void animate() {
		this.setPosition(_targetX,_targetY+AbstractScreen.GAME_VIEWPORT_HEIGHT);
		SequenceAction sequence = new SequenceAction();
		sequence.addAction(Actions.moveBy(0, -AbstractScreen.GAME_VIEWPORT_HEIGHT, 0.20f));
		sequence.addAction(Actions.delay(_displayTime));
		sequence.addAction(Actions.moveBy(0, -AbstractScreen.GAME_VIEWPORT_HEIGHT, 0.20f));
		this.addAction(sequence);
	}
}
