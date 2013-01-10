package com.nsx.ageoftower.screen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.nsx.ageoftower.AgeOfTower;

public class SplashScreen extends AbstractScreen{

	private Texture _mSplashTexture;

	public SplashScreen(AgeOfTower aot) {
		super(aot);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		_mStage.clear();
		
		// in the image atlas, our splash image begins at (0,0) of the
		// upper-left corner and has a dimension of 512x301
		TextureRegion splashRegion = new TextureRegion( _mSplashTexture, 0, 0, 512, 301 );
		
		// here we create the splash image actor and set its size
		Image splashImage = new Image( splashRegion);
		splashImage.setWidth(width);
		splashImage.setHeight(height);
		
		// this is needed for the fade-in effect to work correctly; we're just
		// making the image completely transparent
		splashImage.getColor().a = 0f;
        
		// configure the fade-in/out effect on the splash image
		//Fade-in in 0.75 seconds
		//Wait for 1.75 seconds
		//Fade-out in 0.75 seconds
		splashImage.addAction( 
			sequence( 
				fadeIn( 0.75f ), 
				delay( 1.75f ), 
				fadeOut( 0.75f ),
				new Action() {
					@Override
					public boolean act(float delta ){
						// the last action will move to the next screen
						_mGame.setScreen( new MenuScreen( _mGame ) );
						return true;
					}
				}
			)
            	);

		// and finally we add the actor to the stage
		_mStage.addActor( splashImage );

		// and finally we add the actors to the stage, on the correct order
		_mStage.addActor( splashImage );
	}

	@Override
	public void show() {
		super.show();
		
		// load the splash image and create the texture region
		_mSplashTexture = new Texture( "data/splashscreen.png" );
		// we set the linear texture filter to improve the stretching
		_mSplashTexture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
	}

	@Override
	public void dispose() {
		super.dispose();
		_mSplashTexture.dispose();
	}
}
