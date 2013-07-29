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
import com.nsx.ageoftower.actors.Enemies;
import com.nsx.ageoftower.utils.AotHud;



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
	int EnemiePos ;
	int newX ;
	int newY ;

	//scene2d
	Stage stage ;
	Image img;
	Enemies enemies ;
	World world;

	//pathfinder

	//private GameMap map = new GameMap();
	long lastMoveTime;
	private Integer currentLvl;

	public GameScreen(AgeOfTower ageOfTower ){

		_mAot = ageOfTower ; 
		stage = new Stage();
		//-- a supprimer lorsque tou sera rassemblé, issue:15
		TextureAtlas hudAtlas = new TextureAtlas(Gdx.files.internal("skin/default2.atlas"));
		stage.addActor(new AotHud(new Skin(Gdx.files.internal("skin/default2.skin"),hudAtlas )));



		//float w = Gdx.graphics.getWidth();
		//float h = Gdx.graphics.getHeight();

		//camera = new OrthographicCamera(1, h/w);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		texture = new Texture(Gdx.files.internal("data/title.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		Tower1Image = new Texture(Gdx.files.internal("data/tower1.png"));

		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);

		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);

		//Gdx.app.log( AgeOfTower.LOG, "Creating game" );
        //fpsLogger = new FPSLogger();
       

		// pathfinder
		//finder = new AStarPathFinder(basedmap, 500, true);


		//lecture tiled map
		String line= "";
		String file= "data/packer/level1.tmx";

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

		System.out.println("XXXXXXXXXXX fin lecture tiled map XXXXXXXXXXX");

		//fin lecture tiled map



    //  tiled map


    		map = TiledLoader.createMap(Gdx.files.internal("data/packer/level1.tmx")); 
    		atlas = new TileAtlas(map, Gdx.files.internal("data/packer"));     
    		// Create the renderer      
    		tileMapRenderer = new TileMapRenderer(map, atlas, 1, 1, 32,32);

    	
    		
    	  		
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

		 for (i=0 ; i<600 ; i++)
		 {
			 if ( TowerPos[i] == 1)
				  batch.draw(Tower1Image, (i%25)*32, (14*32)- ((int)i/25)*32,32,32);  

		 }

		batch.end();



		stage.act(delta);
        stage.draw();
        world.step(1/60f, i, i) ;

		enemies.nb= 0;
        
	for (Actor enemietmp : enemies.getChildren()) {


		EnemiePos= ((int)(enemietmp.getY()/28))*33 + (int)enemietmp.getX()/24 ;

		Vector2 coords= new Vector2(enemietmp.getX(), enemietmp.getY());


		enemietmp.localToStageCoordinates(coords);
		enemietmp.getStage().stageToScreenCoordinates(coords);


		System.out.println("Enemie X: " + enemietmp.getX() ) ;
		System.out.println("Enemie Y: " + enemietmp.getY()) ;
		System.out.println("Enemie NoeudPos: " + enemies.NoeudPos) ;
		/*if ( (TilePos[EnemiePos+1]) == 181)
			enemie.setX(enemie.getX() + 1) ;
		else
			enemie.setY(enemie.getY() - 1) ;
		*/

		System.out.println("Enemie nb Action: " + enemietmp.getActions().size ) ;
		if (enemietmp.getActions().size == 0)
		{	
			System.out.println("Noeud X : " + enemies.NoeudPos + " = " + enemies.getNoeud()*32  ) ;
			newX=enemies.getNoeud()*32;
			enemies.NoeudPos++ ;
			System.out.println("Noeud Y : " + enemies.NoeudPos + " = " + enemies.getNoeud()*32  ) ;
			newY=enemies.getNoeud()*32;

			enemietmp.addAction(parallel (
											Actions.moveTo(newX,newY, 2)
																					)
							);


			enemies.NoeudPos-- ;


			System.out.println("Test  enemies.nb enemies.maxenemies: "+ enemies.nb+ "  "+( enemies.maxenemies-1)) ;
			if  (enemies.nb < (enemies.maxenemies -1))
			{
				enemies.nb ++ ;
				System.out.println("incrementation numero enemie : " + enemies.nb ) ;
			}

			else
				{
					enemies.nb= 0 ;
					System.out.println("incrementation Position Noeud : " + enemies.NoeudPos) ;

					if (enemies.NoeudPos < 30)
						enemies.NoeudPos= enemies.NoeudPos  + 2;
					else
						enemies.NoeudPos= 0;

				}



		}



		}


	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		world = new World(new Vector2(0f, -1), true);
		enemies= new Enemies(world) ;
		
		stage.addActor(enemies);
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