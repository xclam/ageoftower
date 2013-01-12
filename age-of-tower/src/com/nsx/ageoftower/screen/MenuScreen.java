package com.nsx.ageoftower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.nsx.ageoftower.AgeOfTower;

public class MenuScreen extends AbstractScreen {
    private AgeOfTower _aot;
	
	public MenuScreen(AgeOfTower aot) {
		super(aot);
		_aot = aot;
	}

	public void show(){
		super.show();

		// retrieve the default table actor
		Table table = super.getTable();
		Gdx.app.log( AgeOfTower.LOG, "prepare to add label" );
		table.add( "Welcome to Age of Tower for Android!" ).spaceBottom( 50 );
		table.row();

		// register the button "start game"
		Gdx.app.log( AgeOfTower.LOG, "prepare to add Start game button" );
		TextButton startGameButton = new TextButton( "Start game", getSkin() );
		startGameButton.addListener(
			new ClickListener() {
				@Override
				public void clicked(InputEvent arg0, float arg1, float arg2) {
					Gdx.app.log( AgeOfTower.LOG, "click" );
				}
				@Override
				public void touchUp(InputEvent event,float x,float y,int pointer,int button ){
					Gdx.app.log( AgeOfTower.LOG, "touchUp" );
					//-- chargement du profile
					_aot.setProfile("default");
					_aot.setScreen( new LevelSelectorScreen( _mGame ) );
				}
			}
		);
	        	
		table.add( startGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
	}
}
