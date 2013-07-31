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
import com.nsx.ageoftower.utils.Level;
//import com.nsx.ageoftower.actors.Enemies;
import com.nsx.ageoftower.hud.AotHud;




public class GameScreen implements Screen{
	private OrthographicCamera camera;
	private SpriteBatch batch;
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

	//scene2d
	Stage stage ;
	Image img;
	World world;

	//pathfinder

	//private GameMap map = new GameMap();
	long lastMoveTime;
	private Integer currentLvl;

	
	/**
	 * 
	 * @param aot
	 * @param lvl
	 */
	public GameScreen(AgeOfTower aot, Level lvl){
		_mAot = aot ;
		_level = lvl;
		
		stage = new Stage();
		
		//-- a supprimer lorsque tou sera rassemblé, issue:15
		TextureAtlas hudAtlas = new TextureAtlas(Gdx.files.internal("HUD/hud.pack"));
		stage.addActor(new AotHud(new Skin(Gdx.files.internal("skin/default2.skin"),hudAtlas )));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
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
		
		_level.start(stage);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		int TileTouch;
		int i;

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		tileMapRenderer.render(camera);
		camera.update();

		//fpsLogger.log();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.draw(batch);

		if(Gdx.input.justTouched()) {  
			Vector3 touchPos = new Vector3(); 
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	
			TileTouch= (int)(touchPos.x/32) + ((int)((touchPos.y/32))*25);
	
			System.out.println("x: " + (int)(touchPos.x/32)) ;
			System.out.println("y: " + (int)((touchPos.y/32))) ;
			System.out.println("Indice Tableau " + TileTouch + ": Valeur tuile tableau " + TilePos[TileTouch]);
			
			
			if ( (TilePos[TileTouch]) == 12  )
				TowerPos[TileTouch]= 1 ;
		}
	
		for (i=0 ; i<600 ; i++){
			if ( TowerPos[i] == 1)
				batch.draw(Tower1Image, (i%25)*32, (14*32)- ((int)i/25)*32,32,32);  
		}

		batch.end();

		stage.act(delta);
        stage.draw();
        world.step(1/60f, i, i) ;
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		world = new World(new Vector2(0f, -1), true);		
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