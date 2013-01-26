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
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.nsx.ageoftower.screen.AbstractScreen;

public class AotHud extends WidgetGroup{
	AotTimer _timer;
	HudLayoutHolder _hl;
	Skin _skin;
	WidgetGroup _leftColumn;
	WidgetGroup _centerColumn;
	WidgetGroup _rightColumn;
	AotWidgetGroup _tmpPool;
	Label _life;
	Label _wave;

	public AotHud(){
		_skin = new Skin(Gdx.files.internal("data/HUD/HUD.skin"));	
		
		_leftColumn = new WidgetGroup();
		_centerColumn = new WidgetGroup();
		_rightColumn = new WidgetGroup();
		
		_timer = new AotTimer("TIME LEFT",80,_skin);
		_timer.setName("timer");
		_life = new Label("life:",_skin.get("JUNEBUG_16",LabelStyle.class));
		_life.setName("life");
		_wave = new Label("Wave:",_skin.get("JUNEBUG_16",LabelStyle.class));
		_wave.setName("wave");
		
		_tmpPool = new AotWidgetGroup();
		_tmpPool.addActor(_timer);
		_tmpPool.addActor(_life);
		_tmpPool.addActor(_wave);
		
		LoadFromLayoutFile("data/HUD/default.layout");
			
		this.addActor(_leftColumn);
		this.addActor(_centerColumn);
		this.addActor(_rightColumn);
		
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
		FileHandle lFileHandle = Gdx.files.local("data/HUD/default.layout");
		//-- chargement du profile
		if(lFileHandle.exists()){
			_hl = json.fromJson(HudLayoutHolder.class, lFileHandle);
		}else{
			//-- le fichier n'existe pas, on creer donc un ProfileStateHolder manuellement
			_hl = new HudLayoutHolder();
			String text = json.prettyPrint(_hl);
			lFileHandle.writeString(text, false);
		}
		//-- colonne aligné à gauche
		_leftColumn.setPosition(0,0);
		_leftColumn.setSize(_hl._leftColumn._width, AbstractScreen.GAME_VIEWPORT_HEIGHT);
		
		//-- colonne centré
		_centerColumn.setPosition((AbstractScreen.GAME_VIEWPORT_HEIGHT-_hl._centerColumn._width)/2,0);
		_centerColumn.setSize(_hl._centerColumn._width, AbstractScreen.GAME_VIEWPORT_HEIGHT);
		
		//-- colonne a droite
		_rightColumn.setPosition(AbstractScreen.GAME_VIEWPORT_WIDTH-_hl._rightColumn._width,0);
		_rightColumn.setSize(_hl._rightColumn._width, AbstractScreen.GAME_VIEWPORT_HEIGHT);
		
		
		Iterator<String> itr = _hl._rightColumn._elements.iterator();
		String tmpName;
		Actor tmpAct;
		while(itr.hasNext()){
			tmpName = itr.next();
			if(_tmpPool.hasChild(tmpName)){
				tmpAct = _tmpPool.getChild(tmpName);
				_rightColumn.addActor(tmpAct);
			}
		}
		
		itr = _hl._centerColumn._elements.iterator();
		while(itr.hasNext()){
			tmpName = itr.next();
			if(_tmpPool.hasChild(tmpName)){
				tmpAct = _tmpPool.getChild(tmpName);
				_centerColumn.addActor(tmpAct);
			}
		}
		
		itr = _hl._leftColumn._elements.iterator();
		while(itr.hasNext()){
			tmpName = itr.next();
			if(_tmpPool.hasChild(tmpName)){
				tmpAct = _tmpPool.getChild(tmpName);
				_leftColumn.addActor(tmpAct);
			}
		}

		/*
		_timer.setFontSize(_hl._timerLabelFontSize[0]);
		_timer.setPosition(
				AbstractScreen.GAME_VIEWPORT_WIDTH*_hl._launchButtonPosition[0]/100+_hl._padding[0],
				AbstractScreen.GAME_VIEWPORT_HEIGHT*_hl._launchButtonPosition[1]/100-_timer.getHeight()-_hl._padding[0]
				);
		_life.setStyle( _skin.get("JUNEBUG_"+_hl._lifeLabelFontSize[0],LabelStyle.class));
		_life.setPosition(
				AbstractScreen.GAME_VIEWPORT_WIDTH*_hl._lifeLabelPosition[0]/100+_hl._padding[0],
				AbstractScreen.GAME_VIEWPORT_HEIGHT*_hl._lifeLabelPosition[1]/100-_timer.getHeight()-_hl._padding[0]
				);
		_wave.setStyle( _skin.get("JUNEBUG_"+_hl._waveLabelFontSize[0],LabelStyle.class));
		_wave.setPosition(
				AbstractScreen.GAME_VIEWPORT_WIDTH*_hl._waveLabelPosition[0]/100+_hl._padding[0],
				AbstractScreen.GAME_VIEWPORT_HEIGHT*_hl._waveLabelPosition[1]/100-_timer.getHeight()-_hl._padding[0]
				);
				*/
	}
	public Skin getSkin() {
		return _skin;
	}
	
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
	
	@Override
	public void addActor(Actor a){
		super.addActor(a);
		a.setPosition(
				_nextWidgetPosition[0],
				_nextWidgetPosition[1]
				);
		switch(_stackMethod){
			case STACK_METHOD_START_LEFT:
				_nextWidgetPosition[1]+=a.getWidth();
				break;
			case STACK_METHOD_START_TOP:
				_nextWidgetPosition[0]+=a.getHeight();
				break;
		}
	}
}
