package com.nsx.ageoftower.screen;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import com.nsx.ageoftower.AgeOfTower;
import com.nsx.ageoftower.hud.AotHud;
import com.nsx.ageoftower.utils.AotStage;
import com.nsx.ageoftower.utils.Level;
import com.nsx.ageoftower.utils.AotGameEngine;
import com.nsx.ageoftower.utils.Level;
import com.nsx.ageoftower.utils.Wave;




public class GameScreen extends AbstractScreen{
	//private OrthographicCamera camera;
	private Texture texture;
	private Sprite sprite;
	Texture Tower1Image; 

	AgeOfTower _mAot ;
	
	//xclam
	private Level _level;

	//tiled map
	TileMapRenderer tileMapRenderer;     
	TiledMap map;        
	TileAtlas atlas; 
	int TilePos[] ;
	int TowerPos[] ;
	int EnemiePos ;
	int newX ;
	int newY ;
	
	Image img;

	long lastMoveTime;

	
	/**
	 * 
	 * @param aot
	 * @param lvl
	 */
	public GameScreen(AgeOfTower aot, Level lvl){
		super(aot);
		_mAot = aot ;
		_level = lvl;
		this._mStage = new AotStage( AbstractScreen.GAME_VIEWPORT_WIDTH, AbstractScreen.GAME_VIEWPORT_HEIGHT, true,lvl );
		//camera = new OrthographicCamera();
		//camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		texture = new Texture(Gdx.files.internal("data/title.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		Tower1Image = new Texture(Gdx.files.internal("data/tower1.png"));
		TowerPos= new int[600];
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);

		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		// Tiled Map
		map = TiledLoader.createMap(Gdx.files.internal("data/packer/"+this._level.getName()+".tmx")); 
		atlas = new TileAtlas(map, Gdx.files.internal("data/packer"));     
		// Create the renderer      
		tileMapRenderer = new TileMapRenderer(map, atlas, 1, 1, 32,32);
		
		

		//lecture tiled map
		String line= "";
		String file= "data/packer/level1.tmx";

		int i=0 ; 

		TilePos= new int[600];
		TowerPos= new int[600];

		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(Gdx.files.internal(file).read()));

			if (br == null)
				throw new FileNotFoundException("File not found: "	+ file);

			do {
				line = br.readLine();
				if (line != null) {
					if ( line.contains("<tile gid")){
						StringTokenizer fileT= new StringTokenizer(line ,"\"");
					try {
						fileT.nextToken();
						TilePos[i]=Integer.parseInt(fileT.nextToken());
						} catch (Exception e) {} 
						i++ ;
					}
				}
			} while (line != null);
			br.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		map = TiledLoader.createMap(Gdx.files.internal("data/packer/level1.tmx")); 
		atlas = new TileAtlas(map, Gdx.files.internal("data/packer"));     
		// Create the renderer      
		tileMapRenderer = new TileMapRenderer(map, atlas, 1, 1, 32,32);

	}

	@Override
	public void render(float delta) {

		OrthographicCamera camera = (OrthographicCamera) _mStage.getCamera();
		tileMapRenderer.render(camera);
        
        super.render(delta);
	}

}