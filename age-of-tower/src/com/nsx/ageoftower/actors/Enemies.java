package com.nsx.ageoftower.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SnapshotArray;
import com.nsx.ageoftower.Assets;
//import com.nsx.ageoftower.Assets;
import com.nsx.ageoftower.GameItem;



public class Enemies extends Group {
	
	
	//final int gameUnitConst = 32;
	final int gameUnitConst = 1;
	private World world;
	private Texture textureEnemies;
	public int [][] Noeud ;
	public int NoeudPos ;
	public int nb= 0;
	public int maxenemies= 0;
	private int k= 0;
	
	public Enemies(World world) {
		
		this.world = world;
		Noeud= new int [32][32];
		FileHandle file =  Gdx.files.internal("data/level1.json");
		Json json = new Json();
		@SuppressWarnings("unchecked")
		Array<GameItem> items = json.fromJson(Array.class, GameItem.class, file);	
		
		
		for (GameItem item : items) {
			if (item.getItemType().equals("enemies")) {
				Vector2 position = new Vector2(item.getX()/gameUnitConst, item.getY()/gameUnitConst);
				//int blocks = (int) (item.getWidth() / gameUnitConst);
				
				textureEnemies = new Texture(Gdx.files.internal("data/" + item.sprite));
				
				//Image enemie = new Image(Assets.enemies);
				Image enemieimg = new Image(textureEnemies);
				enemieimg.setPosition(item.getX(), item.getY());
				//enemie.setWidth(blocks);
				
				
				for(k=0; k<32 ;k ++)
				{
					System.out.println("k: " + k + " and nb:  " + nb  ) ;
					System.out.println("item path: " + item.path[k]  ) ;
					Noeud[nb][k]= item.path[k] ;
									
					}
				nb++ ;
				
				createEnemiesBody(position.x, position.y, enemieimg.getWidth(), enemieimg.getHeight());
				//createEnemiesBody(10, 20, enemie.getWidth(), enemie.getHeight());
				addActor(enemieimg) ;
			}
		}
		maxenemies= nb ;
		System.out.println("max enemies: " + maxenemies  ) ;
	}


@Override
public void draw(SpriteBatch batch, float parentAlpha) {
	// TODO Auto-generated method stub
	super.draw(batch, parentAlpha);
}

public int getNoeud() {
	return Noeud[this.nb][NoeudPos];
}

private void createEnemiesBody(float x, float y, float width, float height) {
	// Create our body definition
	BodyDef groundBodyDef = new BodyDef();
	// Set its world position
//	groundBodyDef.position.set(position);// treba da se sredi jer je ovo pozicija u sredini

	groundBodyDef.position.set(x + width/2, y + height/2);
	
	// Create a body from the defintion and add it to the world
	Body enemiesBody = world.createBody(groundBodyDef);

	// Create a polygon shape
	PolygonShape groundBox = new PolygonShape();
	// Set the polygon shape as a box which is twice the size of our view
	// port and 10 high
	groundBox.setAsBox(width/2, 0.5f);
	// Create a fixture from our polygon shape and add it to our ground body
	enemiesBody.createFixture(groundBox, 0.0f);
}



}