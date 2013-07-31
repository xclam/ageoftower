package com.nsx.ageoftower.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import java.lang.reflect.Field;  

public class AotTiledMap extends TiledMap{
	TileAtlas _atlas;

	private AotTiledMap(){
		super();
	}
	
	//-- Constructeur pour palier au TiledLoader.createMap(fileHandle) retournant forcement unne TileMap et non une AoeTiledMap
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private AotTiledMap(TiledMap createMap) {
		Field fields[];
		Class  curClass = createMap.getClass();  
        if (!curClass.isAssignableFrom(this.getClass())){  
            throw new IllegalArgumentException  
            ("New object must be the same class or a subclass of original");  
        }  
        //-- Parcours des variable d'instance  
        do{  
            fields = curClass.getDeclaredFields();  
            for (int i = 0; i < fields.length; i++){  
                try {
                	fields[i].setAccessible(true);
					fields[i].set(this, fields[i].get(createMap));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}  
            }  
            curClass = curClass.getSuperclass();  
        } while (curClass != null);  
	}

	public AotTiledMap(FileHandle fileHandle, FileHandle fileHandle2) {
		this(TiledLoader.createMap(fileHandle));
		_atlas = new TileAtlas(this, fileHandle2);
		//-- Chargement des slots pour tourelle
		
	}
	
	public TileAtlas getAtlas() {
		return _atlas;
	}

	public void setAtlas(TileAtlas _atlas) {
		this._atlas = _atlas;
	}
}