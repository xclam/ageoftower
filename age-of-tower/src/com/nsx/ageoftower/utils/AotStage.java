package com.nsx.ageoftower.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.nsx.ageoftower.hud.AotHud;

public class AotStage extends Stage {
	
	AotGameEngine _myRoot;
	Group _towers;
	
	public AotStage(int width, int height, boolean b,Level level, TiledMap map) {
		super(width,height,b);
		//-- a supprimer lorsque tout sera rassemblé, issue:15
		TextureAtlas hudAtlas = new TextureAtlas(Gdx.files.internal("HUD/hud.pack"));
		AotHud _hud = new AotHud(new Skin(Gdx.files.internal("skin/default2.skin"),hudAtlas ));
		_myRoot = new AotGameEngine(_hud,level,this);
		super.addActor(_myRoot);
		
		_towers = extractTower(map);
		this.addActor(_towers);
	}
	
	private Group extractTower(TiledMap map) {
		Group grp = new Group();
		
		//-- extraction des positions dans le layer "towers" de la map
		for(TiledLayer tl :map.layers){
			if(tl.name.equals("towers")){
				for(int i=0;i<tl.getWidth();i++){
					for(int j=0;j<tl.getHeight();j++){
						if(tl.tiles[j][i]!=0){
							grp.addActor(new Tower(map.tileWidth,map.tileHeight,i,j));
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
}
