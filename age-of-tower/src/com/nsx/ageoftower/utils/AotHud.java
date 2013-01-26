package com.nsx.ageoftower.utils;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.nsx.ageoftower.screen.AbstractScreen;

public class AotHud extends WidgetGroup{
	AotTimer _timer;
	Skin _skin;
	AotWidgetGroup _tmpPool;
	AotWidgetGroup _columnPool; 
	Label _life;
	Label _wave;
	
	ListWidgetGroup _left;
	ListWidgetGroup _right;
	ListWidgetGroup _center;
	ListWidgetGroup _bottomeLine;
	
	public AotHud(Skin sk){
		_skin = sk;	
		
		_timer = new AotTimer("TIME LEFT",80,_skin.get("JUNEBUG_16",LabelStyle.class));
		_timer.setName("timer");
		_life = new Label("LIFE:",_skin.get("JUNEBUG_16",LabelStyle.class));
		_life.setName("life");
		_wave = new Label("WAVE:",_skin.get("JUNEBUG_16",LabelStyle.class));
		_wave.setName("wave");
		
		_tmpPool = new AotWidgetGroup();
		_tmpPool.addActor(_timer);
		_tmpPool.addActor(_life);
		_tmpPool.addActor(_wave);
		
		
		//-- paratique lors de la lecture du layout
		_columnPool = new AotWidgetGroup();
		_columnPool.addActor(new ListWidgetGroup("left"));
		_columnPool.addActor(new ListWidgetGroup("center"));
		_columnPool.addActor(new ListWidgetGroup("right"));
		_columnPool.addActor(new ListWidgetGroup("bottomLine"));
		
		LoadFromLayoutFile("data/HUD/default.layout");
		
		this.addActor(_columnPool);
		_timer.start();
	}

	/*
	 * On definit trois zone dans le hud, gauche, centre et droit
	 * On empile ensuite les elements du hud dans ces zone
	 * On pourra préciser plusieur type de gestion de l'empilage.
	 *    - Haut 
	 *    - bas
	 * Et de la'aligneent
	 *    - gauche
	 *    - droit
	 *    - centre
	 */
	private void LoadFromLayoutFile(String string) {
		Json json = new Json();
		json.setOutputType(OutputType.minimal);
		FileHandle layoutFileHandle = Gdx.files.local(string);
		//-- chargement du profile
		Array<Array<String>> hl_ = json.fromJson(Array.class,layoutFileHandle);
		
		Iterator<Array<String>> itr = hl_.iterator();
		Actor tmp;
		while(itr.hasNext()){
			Array<String> tmp1 = itr.next();
			// recuperation de l'element dans la pool temporaire
			tmp =_tmpPool.getChild(tmp1.get(0));
			if(tmp!=null){
				this.addActorTo(tmp,tmp1.get(1),tmp1.get(2));
			}else{
				System.out.println("Element :"+tmp1.get(0)+" unknown!");
			}
		}
	}
	private void addActorTo(Actor a, String colname, String glu) {
		ListWidgetGroup col = (ListWidgetGroup) (_columnPool.getChild(colname));
		System.out.println(col.getName());
		col.addActor(a,glu);
	}

	
	

	class AotWidgetGroup  extends WidgetGroup{
		public Actor getChild(String name){
			Actor tmp = null;
			
			SnapshotArray<Actor> list = this.getChildren();
			Iterator<Actor> itr = list.iterator();
			while(itr.hasNext()){
				Actor tmp2 = itr.next();
				if(tmp2.getName().equals(name)){
					tmp = tmp2;
				}
			}
			return tmp;
		}
		
		public boolean hasChild(String name){
			boolean result = false;
			SnapshotArray<Actor> list = this.getChildren();
			Iterator<Actor> itr = list.iterator();
			while(itr.hasNext() && !result){
				Actor tmp2 = itr.next();
				if(tmp2.getName().equals(name)){
					result = true;
				}
			}
			return result;
		}
	}

	class ListWidgetGroup extends AotWidgetGroup{
		float _nextTopPosition;
		float _nextBottomPosition;
		
		public ListWidgetGroup(String name){
			super();
			this.setName(name);
			
			if(name.equals("left")){
				//-- colonne de gauche 1 tiers de large, toute la hauteur - la hauteur de bottomline
				this.setPosition(0, AbstractScreen.GAME_VIEWPORT_HEIGHT*1/6);
				this.setSize(AbstractScreen.GAME_VIEWPORT_WIDTH*1/3, AbstractScreen.GAME_VIEWPORT_HEIGHT*5/6);
			}else if(name.equals("center")){
				//-- colonne du centre 1 tiers de large, toute la hauteur - la hauteur de bottomline
				this.setPosition(AbstractScreen.GAME_VIEWPORT_WIDTH*1/3, AbstractScreen.GAME_VIEWPORT_HEIGHT*1/6);
				this.setSize(AbstractScreen.GAME_VIEWPORT_WIDTH*1/3, AbstractScreen.GAME_VIEWPORT_HEIGHT*5/6);
			}else if(name.equals("right")){
				//-- colonne de gauche 1 tiers de large, toute la hauteur - la hauteur de bottomline
				this.setPosition(AbstractScreen.GAME_VIEWPORT_WIDTH*2/3, AbstractScreen.GAME_VIEWPORT_HEIGHT*1/6);
				this.setSize(AbstractScreen.GAME_VIEWPORT_WIDTH*1/3, AbstractScreen.GAME_VIEWPORT_HEIGHT*5/6);
			}else if(name.equals("bottomLine")){
				//-- Ligne du bas
				this.setPosition(0, 0);
				this.setSize(AbstractScreen.GAME_VIEWPORT_WIDTH, AbstractScreen.GAME_VIEWPORT_HEIGHT*1/6);
			}
			
			_nextTopPosition = this.getHeight();
			_nextBottomPosition = 0;
		}

		
		public void addActor(Actor a, String glu){
			super.addActor(a);
			if(glu.equals("top")){
				a.setPosition(0, _nextTopPosition-a.getHeight());
				_nextTopPosition = _nextTopPosition - a.getHeight();
			}else{
				a.setPosition(0, _nextBottomPosition);
				_nextBottomPosition = a.getHeight();
			}
		}
	}
}
