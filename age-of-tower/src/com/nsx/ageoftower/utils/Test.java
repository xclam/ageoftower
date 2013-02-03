package com.nsx.ageoftower.utils;

import java.util.Vector;

import com.badlogic.gdx.utils.Json;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Foe fa = new Foe(100,10,0);
		Foe fb = new Foe(100,10,0);
		Foe fc = new Foe(100,10,0);
		Foe fd = new Foe(120,10,2);
		Vector<Foe> vf = new Vector<Foe>();
		vf.add(fa);
		vf.add(fb);
		vf.add(fc);
		vf.add(fd);
		
		Wave w1 = new Wave(vf);
		Wave w2 = new Wave(vf);
		Vector<Wave> vw = new Vector<Wave>();
		vw.add(w1);
		vw.add(w2);
		
		int[] a = {10,20,30};
		int[] b = {20,18,15};
		Level lvl = new Level("Level1",false,2,vw,a,b);
		
		Json json = new Json();
		System.out.println(json.toJson(lvl));
	}

}
