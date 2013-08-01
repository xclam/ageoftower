package com.nsx.ageoftower.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Vector2> path = new ArrayList<Vector2>();
		
		path.add(new Vector2(10,280));
		path.add(new Vector2(180,280));
		path.add(new Vector2(240,300));
		path.add(new Vector2(500,300));
		
		Foe fa = new Foe(100,10,0);
		Foe fb = new Foe(100,10,0);
		Foe fc = new Foe(100,10,0);
		Foe fd = new Foe(120,10,2);
	
		
		ArrayList<Foe> vf = new ArrayList<Foe>();
		
		vf.add(fa);
		vf.add(fb);
		vf.add(fc);
		vf.add(fd);
		
		
		Wave w1 = new Wave(vf);
		Wave w2 = new Wave(vf);
		ArrayList<Wave> vw = new ArrayList<Wave>();
		vw.add(w1);
		vw.add(w2);
		
		
		int[] a = {40,50,60};
		int[] b = {20,18,15};
		Level lvl = new Level("level1",false,2,vw,a,b);
		lvl.set_path(path);
		
		Json json = new Json();
		
		String text = json.prettyPrint(lvl);
		//System.out.println(text);
		
		
		Level ww2 = json.fromJson(Level.class, text);
		System.out.println(json.prettyPrint(ww2));
		
		System.out.println(ww2.getNbWave());
	}

}
