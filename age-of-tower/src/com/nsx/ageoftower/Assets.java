package com.nsx.ageoftower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class Assets {
	public static TextureRegion enemies;
	public static Animation enemiesAnim;
	public static Texture backgroundTexture;
	public static Skin skin;
	
	public static void load () {
		
		TextureAtlas textureAtlas = new TextureAtlas("data/PigTest.pack");
		enemiesAnim = new Animation(0.2f, textureAtlas.findRegion("falling1"), textureAtlas.findRegion("falling2"));
		enemies = textureAtlas.findRegion("platform");
		backgroundTexture = new Texture(Gdx.files.internal("data/back.jpg"));
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
	}
}