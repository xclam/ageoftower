package com.nsx.ageoftower.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.nsx.ageoftower.AgeOfTower;
import com.nsx.ageoftower.hud.AotHud;

/*
 * This class let us catch event in our engine, a cutom node just after the real stage root
 * We have overridden the addActor() method to catch all actors
 */
public class AotStage extends Stage {
	
	AotGameEngine _myRoot;
	Group _towers;
	Group _ennemies;
	TiledMap _map;
	
	public AotStage(int width, int height, boolean b,String levelName, TiledMap map, AgeOfTower aot) {
		super(width,height,b);
		
		_ennemies = new Group();
		_towers =  new Group();
		_map = map;
		
		//-- a supprimer lorsque tout sera rassemblé, issue:15
		TextureAtlas hudAtlas = new TextureAtlas(Gdx.files.internal("HUD/hud.pack"));
		AotHud _hud = new AotHud(new Skin(Gdx.files.internal("skin/default2.skin"),hudAtlas ));
		_myRoot = new AotGameEngine(_hud,levelName,this,aot);
		super.addActor(_myRoot);
		
		_towers.clear();
		_towers = extractTower(_map);
		
		this.addActor(_towers);
		this.addActor(_ennemies);
	}
	
	

	/*
	 * This method extracts tiles from the layer "tower" in the tiledmap
	 */
	private Group extractTower(TiledMap map) {
		Group grp = new Group();
		
		//-- extraction des positions dans le layer "towers" de la map
		for(TiledLayer tl :map.layers){
			if(tl.name.equals("towers")){
				for(int i=0;i<tl.getWidth();i++){
					for(int j=0;j<tl.getHeight();j++){
						if(tl.tiles[j][i]!=0){
							Tower t = new Tower(map.tileWidth,map.tileHeight,i,j);
							_myRoot.addListener(t);
							grp.addActor(t);
						}
					}
				}
			}
		}
		return grp;
	}

	@Override
	public void addActor (Actor actor) {
		_myRoot.addActor(actor);
	}

	public void addFoe(Actor foe){
		_ennemies.addActor(foe);
	}

	public Group getEnnemies() {
		return _ennemies;
	}
	
	public Group getTowers() {
		return _towers;
	}

	public void setTowers(Group _towers) {
		this._towers = _towers;
	}
	
	/**
	 * Look for all actor if they collide
	 */
	public void act(float delta){
		super.act(delta);
		for (Actor at : _towers.getChildren()){
			if (((Tower)at).getState() == Tower.STATE_ENABLE){
				for (Actor af : _ennemies.getChildren()){
					CollisionDetector.isActorsCollide(((Tower)at).getRange(), af);
				}
			}
		}
	}
}
