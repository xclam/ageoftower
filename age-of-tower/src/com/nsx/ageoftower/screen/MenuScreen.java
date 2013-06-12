package com.nsx.ageoftower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.nsx.ageoftower.AgeOfTower;

public class MenuScreen extends AbstractScreen {

	 // setup the dimensions of the menu buttons
    private static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 60f;
    private static final float BUTTON_SPACING = 10f;
    
	public MenuScreen(AgeOfTower aot) {
		super(aot);
		// TODO Auto-generated constructor stub
	}

	 public void show()
	    {
	        super.show();

	        // retrieve the default table actor
	        Table table = super.getTable();
	        Gdx.app.log( AgeOfTower.LOG, "prepare to add label" );
	        table.add( "Welcome to Age of Tower for Android!" ).spaceBottom( 50 );
	        table.row();

	        // register the button "start game"
	        Gdx.app.log( AgeOfTower.LOG, "prepare to add Start game button" );
	        TextButton startGameButton = new TextButton( "Start game", getSkin() );
	        startGameButton.addListener( new ClickListener() {
				@Override
				public void clicked(InputEvent arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
						Gdx.app.log( AgeOfTower.LOG, "click" );
				}
	        	/*@Override
				public boolean touchDown(InputEvent event,float x,float y,int pointer,int button){
					Gdx.app.log( Space.LOG, "touchDown" );
					return true;					
				}*/
				@Override
	            public void touchUp(InputEvent event,float x,float y,int pointer,int button ){
	            	Gdx.app.log( AgeOfTower.LOG, "touchUp" );
	                _mGame.setScreen( new LevelSelectorScreen( _mGame ) );
	            }

	        } );
	        table.add( startGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
	    }
}
