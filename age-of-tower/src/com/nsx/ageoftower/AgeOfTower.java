package com.nsx.ageoftower;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nsx.ageoftower.screen.AbstractScreen;
import com.nsx.ageoftower.screen.DummyScreen;
import com.nsx.ageoftower.screen.LevelSelectorScreen;
import com.nsx.ageoftower.screen.SplashScreen;

public class AgeOfTower extends Game {
	public LevelSelectorScreen lvlSelScreen;
	public DummyScreen anOtherScreen;
	
	// constant useful for logging
    public static final String LOG = AgeOfTower.class.getSimpleName();
 
    // a libgdx helper class that logs the current FPS each second
    private FPSLogger fpsLogger;
	
	@Override
	public void create() {        
        /*lvlSelScreen = new LevelSelectorScreen(this);
        anOtherScreen = new DummyScreen(this);
        this.setScreen(lvlSelScreen);*/
		this.setScreen(new SplashScreen(this));
	}
	
	
	@Override
	public void dispose() {

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
