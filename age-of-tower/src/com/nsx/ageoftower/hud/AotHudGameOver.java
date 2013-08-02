package com.nsx.ageoftower.hud;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.nsx.ageoftower.event.AotEvent;
import com.nsx.ageoftower.screen.AbstractScreen;

public class AotHudGameOver extends WidgetGroup implements  EventListener{
	Skin _skin;
	boolean _clicked = false;
	Label _retry;
	Label _lvlselect;
	
	public AotHudGameOver(Skin sk){
		_skin = sk;
		Label gameover = new Label("GAME OVER",_skin.get("JUNEBUG_32",LabelStyle.class));
		_retry = new Label(">> RETRY <<",_skin.get("JUNEBUG_16",LabelStyle.class));
		_lvlselect = new Label(">> LEVEL SELECTION <<",_skin.get("JUNEBUG_16",LabelStyle.class));
		
		_retry.setPosition(0, 0);
		_lvlselect.setPosition(_retry.getWidth()+100, 0);
		
		this.setWidth(_retry.getWidth()+_lvlselect.getWidth()+100);
		
		gameover.setPosition((this.getWidth()-gameover.getWidth())/2,_retry.getHeight()+100 );
		
		this.setHeight(_retry.getHeight()+gameover.getHeight()+100);
		
		addActor(gameover);
		addActor(_retry);
		addActor(_lvlselect);
		
		this.setPosition((AbstractScreen.GAME_VIEWPORT_WIDTH-this.getWidth())/2, 
						 (AbstractScreen.MENU_VIEWPORT_HEIGHT-this.getHeight())/2-100);
		
		_lvlselect.addListener(this);
		_retry.addListener(this);
		
	}
	
	public void showAnimate() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean handle(Event event) {
		if (!(event instanceof InputEvent)) return false;
		if(((InputEvent)event).getButton() == 0){
			if( !_clicked){
				if (event.getTarget()==_lvlselect){
					this.fire(new AotEvent(AotEvent.Type.goToLevelSelection,this));
				}else if(event.getTarget()==_retry){
					this.fire(new AotEvent(AotEvent.Type.restartLevel,this));
				}
				_clicked = true;
			}else{
				_clicked = false;
			}
		}
		return true;
	}

	public void hideAnimate() {
		this.setVisible(false);
	}



}
