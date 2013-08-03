package com.nsx.ageoftower.screen;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.nsx.ageoftower.AgeOfTower;
import com.nsx.ageoftower.utils.AotStage;
import com.nsx.ageoftower.utils.Level;

public class GameScreen extends AbstractScreen{

	AgeOfTower _mAot ;

	//tiled map
	TileMapRenderer tileMapRenderer;     
	TiledMap map;        
	TileAtlas atlas;
	
	/**
	 * 
	 * @param aot
	 * @param string
	 */
	public GameScreen(AgeOfTower aot, String levelName){
		super(aot);
		_mAot = aot ;
		
		// Tiled Map
		map = TiledLoader.createMap(Gdx.files.internal("GameScreenMedia/levels/"+levelName+"/"+levelName+".tmx"));
		
		// Create the map renderer      
		atlas = new TileAtlas(map, Gdx.files.internal("GameScreenMedia/levels/"+levelName));     
		tileMapRenderer = new TileMapRenderer(map, atlas, 1, 1, 32,32);
		
		this._mStage = new AotStage( AbstractScreen.GAME_VIEWPORT_WIDTH, AbstractScreen.GAME_VIEWPORT_HEIGHT, true,levelName,map,_mAot);

	}

	@Override
	public void render(float delta) {
		OrthographicCamera camera = (OrthographicCamera) _mStage.getCamera();
		tileMapRenderer.render(camera);
        
        super.render(delta);
	}

}