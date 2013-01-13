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
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.nsx.ageoftower.AgeOfTower;


public class GameScreen implements Screen{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	Texture Tower1Image; 
	
	AgeOfTower _mAot ;
	
	//tiled map
	TileMapRenderer tileMapRenderer;     
	TiledMap map;        
	TileAtlas atlas; 
	int TilePos[] ;
	int TowerPos[] ;
	
	public GameScreen(AgeOfTower ageOfTower ){
		
		_mAot = ageOfTower ; 
		
		//float w = Gdx.graphics.getWidth();
		//float h = Gdx.graphics.getHeight();
		
		//camera = new OrthographicCamera(1, h/w);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		Tower1Image = new Texture(Gdx.files.internal("data/tower1.png"));
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		//Gdx.app.log( AgeOfTower.LOG, "Creating game" );
        //fpsLogger = new FPSLogger();
       
   
		//lecture tiled map
		String line= "";
		String file= "data/packer/level2.tmx";
		
		int i=0 ; 
		
		TilePos= new int[600];
		TowerPos= new int[600];
		
		
		System.out.println("=== Lecture tmx ===");
		BufferedReader br;
		try {
			//br = new BufferedReader(new FileReader(new File(file)));
			br = new BufferedReader(new InputStreamReader(Gdx.files.internal(file).read()));

			System.out.println(br.readLine());
			if (br == null)
				throw new FileNotFoundException("File not found: "	+ file);
			
			System.out.println("Fichier tmx OK");
			do {
				System.out.println(" Lecture ligne");
				
				line = br.readLine();
				if (line != null) {
					if ( line.contains("<tile gid"))
					{
						
						StringTokenizer fileT= new StringTokenizer(line ,"\"");
						//System.out.println("Nombre de mots:" + fileT.countTokens());
					try {

						fileT.nextToken();
						//System.out.println(fileT.nextToken());
						
						TilePos[i]=Integer.parseInt(fileT.nextToken());
						System.out.println("Valeur Tuile: " + TilePos[i]);
						} catch (Exception e) {} 

						i++ ;
					}
						
				}
				System.out.println("next lecture tiled map");
			} while (line != null);
			br.close();
			//System.out.println("\n");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("fin lecture tiled map");
		
		//fin lecture tiled map
		
		
		
    //  tiled map
		
		
    		map = TiledLoader.createMap(Gdx.files.internal("data/packer/level2.tmx")); 
    		atlas = new TileAtlas(map, Gdx.files.internal("data/packer"));     
    		// Create the renderer      
    		tileMapRenderer = new TileMapRenderer(map, atlas, 1, 1, 24, 28);

    	
    		// fin tiled map
        
		
		
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		
		int TileTouch;
		int i;
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		tileMapRenderer.render(camera);
		//camera.update();
		
		//fpsLogger.log();
		
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
		   
		   
		   if ( (TilePos[TileTouch]) == 230  )
		   {
			   TowerPos[TileTouch]= 1 ;
			   
			   
		   }
		 }
		
		 for (i=0 ; i<600 ; i++)
		 {
			 if ( TowerPos[i] == 1)
				  batch.draw(Tower1Image, (i%33)*24, (16*28)- ((int)i/33)*28,24,28);  
			 
		 }
		 
		batch.end();
		
		
		
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
