package com.nsx.ageoftower.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.nsx.ageoftower.hud.AotHud;

public class AotStage extends Stage {
	
	AotGameEngine _myRoot;
	
	public AotStage(int width, int height, boolean b,Level level) {
		super(width,height,b);
		//-- a supprimer lorsque tou sera rassemblé, issue:15
		TextureAtlas hudAtlas = new TextureAtlas(Gdx.files.internal("HUD/hud.pack"));
		AotHud _hud = new AotHud(new Skin(Gdx.files.internal("skin/default2.skin"),hudAtlas ));
		_myRoot = new AotGameEngine(_hud,level,this);
		super.addActor(_myRoot);
	}
	
	@Override
	public void addActor (Actor actor) {
		_myRoot.addActor(actor);
	}
}
