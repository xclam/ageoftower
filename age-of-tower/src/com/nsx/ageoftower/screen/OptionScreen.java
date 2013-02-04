package com.nsx.ageoftower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nsx.ageoftower.AgeOfTower;

public class OptionScreen extends AbstractScreen {

	private AgeOfTower _aot;
	
	public OptionScreen(AgeOfTower aot) {
		super(aot);
		_aot = aot;
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	public void show() {
		super.show();
		Gdx.app.log( AgeOfTower.LOG, "show opt" );
		Table table = super.getTable();
		
		// Music checkbox
		final CheckBox musicCheckbox = new CheckBox( "Music", getSkin() );
		musicCheckbox.setChecked(_aot.getPreferences().isMusicEnabled() );
		musicCheckbox.addListener( 
        	new ClickListener() {
        		@Override
        		public void clicked(InputEvent arg0, float arg1, float arg2 ){
	                boolean enabled = musicCheckbox.isChecked();
	                _aot.getPreferences().setMusicEnabled( enabled );
	            }
        	} 
        );    

		// Sound effect checkbox
        final CheckBox soundEffectsCheckbox = new CheckBox( "Effets speciaux",  getSkin() );
        soundEffectsCheckbox.setChecked( _aot.getPreferences().isSoundEffectsEnabled() );   
        soundEffectsCheckbox.addListener( 
        	new ClickListener() {
        		@Override
        		public void clicked(InputEvent arg0, float arg1, float arg2 ){
	                boolean enabled = soundEffectsCheckbox.isChecked();
	                _aot.getPreferences().setSoundEffectsEnabled( enabled );
	            }
        	} 
        );     
        
        // Sound slider
        // range is [0.0,1.0]; step is 0.1f
        final Slider volumeSlider = new Slider( 0f, 1f, 0.1f, false, getSkin());
        volumeSlider.setValue( _aot.getPreferences().getVolume() );
        volumeSlider.addListener( 
        	new ChangeListener() {
	        	@Override
				public void changed(ChangeEvent event, Actor actor) {
					// TODO Auto-generated method stub
					_aot.getPreferences().setVolume( volumeSlider.getValue() );
	                //updateVolumeLabel();
				}
        	} 
        );        
        
     // register the back button
        TextButton backButton = new TextButton( "Retour", getSkin() );
        backButton.addListener( 
        	new ClickListener() {
        		@Override
        		public void clicked(InputEvent arg0, float arg1, float arg2 ){
        			_aot.setScreen( new MenuScreen( _mGame ) );
        		}
        	}
        );
        
        table.add( musicCheckbox ).uniform().spaceBottom( 10 );
		table.row();
		table.add( soundEffectsCheckbox ).size(400, 60).uniform().spaceBottom( 10 );
		table.row();
		table.add( volumeSlider ).size( 300, 60 ).uniform().spaceBottom( 10 );;
		table.row();
		table.add( backButton ).uniform().spaceBottom( 10 );;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
