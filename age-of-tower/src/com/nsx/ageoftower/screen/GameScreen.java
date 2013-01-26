package com.nsx.ageoftower.screen;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.nsx.ageoftower.AgeOfTower;
import com.nsx.ageoftower.utils.AotTiledMap;


public class GameScreen extends AbstractScreen {
	private Texture _texture;
	private Sprite _sprite;
	Texture _tower1Image; 
	
	AgeOfTower _mAot ;
	OrthographicCamera _camera;
	
	//-- tiled map
	TileMapRenderer _tileMapRenderer;     
	AotTiledMap _map;
	
	int _tilePos[] ;
	int _towerPos[] ;
	
	public GameScreen(AgeOfTower ageOfTower ){
		super(ageOfTower);
		
		_mAot = ageOfTower ; 
		_camera = (OrthographicCamera) _mStage.getCamera();
		_camera.setToOrtho(false, GAME_VIEWPORT_WIDTH, GAME_VIEWPORT_HEIGHT);
		_texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		_texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		_tower1Image = new Texture(Gdx.files.internal("data/tower1.png"));
		
		TextureRegion region = new TextureRegion(_texture, 0, 0, 512, 275);
		_sprite = new Sprite(region);
		_sprite.setSize(0.9f, 0.9f * _sprite.getHeight() / _sprite.getWidth());
		_sprite.setOrigin(_sprite.getWidth()/2, _sprite.getHeight()/2);
		_sprite.setPosition(-_sprite.getWidth()/2, -_sprite.getHeight()/2);    
		
		//lecture tiled map
		String line= "";
		String file= "data/packer/level2.tmx";
		
		int i=0 ; 
		
		_tilePos = new int[600];
		_towerPos = new int[600];
		
		System.out.println("=== Lecture tmx ===");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(Gdx.files.internal(file).read()));
			System.out.println(br.readLine());
			System.out.println("Fichier tmx OK");
			do {
				System.out.println(" Lecture ligne");
				
				line = br.readLine();
				if (line != null) {
					if ( line.contains("<tile gid"))
					{
						StringTokenizer fileT= new StringTokenizer(line ,"\"");
					try {
						fileT.nextToken();
						_tilePos[i]=Integer.parseInt(fileT.nextToken());
						System.out.println("Valeur Tuile: " + _tilePos[i]);
						} catch (Exception e) {
						} 
						i++ ;
					}
				}
				System.out.println("next lecture tiled map");
			} while (line != null);
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		//-- Tiled map		
		_map = new AotTiledMap(
				Gdx.files.internal("data/packer/level2.tmx"),
				Gdx.files.internal("data/packer")
				); 
		//-- Create the renderer      
		_tileMapRenderer = new TileMapRenderer(_map, _map.getAtlas(), 1, 1, 24, 28);
		
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
			
		//-- render the map
		_tileMapRenderer.render(_camera);
		
		/*
		int TileTouch;
		int i;
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.draw(batch);

		if(Gdx.input.justTouched()) {  
			Vector3 touchPos = new Vector3(); 
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			
			TileTouch= (int)(touchPos.x/24) + ((int)((touchPos.y/28))*33);
			
			System.out.println("x: " + (int)(touchPos.x/24)) ;
			System.out.println("y: " + (int)((touchPos.y/28))) ;
			System.out.println("Indice Tableau " + TileTouch + ": Valeur tuile tableau " + TilePos[TileTouch]);
			if ( (TilePos[TileTouch]) == 230 ){
				TowerPos[TileTouch]= 1 ;
			}
		}
		
		for (i=0 ; i<600 ; i++){
			if ( TowerPos[i] == 1)
				batch.draw(Tower1Image, (i%33)*24, (16*28)- ((int)i/33)*28,24,28);  
		}
		batch.end();
		*/
	}
}
