package com.nsx.ageoftower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nsx.ageoftower.AgeOfTower;

public class DummyScreen extends AbstractScreen {
	int currentLvl = 9999;
	BitmapFont font;
	SpriteBatch batch;
	
	public DummyScreen(AgeOfTower space) {
		super(space);
		font = new BitmapFont();
		batch = new SpriteBatch();
		// TODO Auto-generated constructor stub
	}

	public void setLvl(int lvl){
		currentLvl = lvl;
	}
	public void setLvl(String lvl){
		currentLvl = new Integer(lvl);
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		// the following code clears the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        
        batch.begin();
        
        font.draw(batch, "Level:"+currentLvl, 100, 100);
        
        batch.end();
	}
}
