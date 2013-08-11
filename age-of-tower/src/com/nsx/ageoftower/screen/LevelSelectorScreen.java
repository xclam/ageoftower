package com.nsx.ageoftower.screen;

import java.util.ArrayList;
import java.util.Iterator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Json;
import com.nsx.ageoftower.AgeOfTower;
import com.nsx.ageoftower.utils.Level;
import com.nsx.ageoftower.utils.LevelStats;
import com.nsx.ageoftower.screen.GameScreen;


public class LevelSelectorScreen extends AbstractScreen   {
	//-- espacement entres les boutons
	public static final int LAYOUT_BUTTON_PADDING = 5;
	//-- seuil de declanchement du slide des panneaux
	public static final float FLINGING_VELOCITY_DETECTION_THRESHOLD = 400;
	//-- constant de redction du mouvement de l'image de fond par raport au premier plan
	public static final float FLINGING_BG_REDUCER = 0.2f;
	//-- vitesse de slide des panneau
	public static final float FLINGING_SPEED = 0.3f;
	//-- taille du cadenas
	public static final float CADENAS_SIZE = 40;
	//-- constante de gestion de l'interactvitiï¿½
	public static final float LAST_UNLOCKED_LVL_ALPHA = 0.8f;
	
	public static final int STATE_IDLE = 0;
	public static final int STATE_FLINGING_RIGHT = 1;
	public static final int STATE_FLINGING_LEFT = 2;
	public static final int STATE_DRAGING = 3;
	
	Group _buttonPages; 
	Actor _backGround;
	Skin _lssSkin;
	AgeOfTower _aot;
	float _targetOffsetPos;
	float _startTargetOffsetPos;
	float _currentPage;
	float _bg_offset;
	int _state;
	int _pageNumber;
	
	public LevelSelectorScreen(AgeOfTower aot) {		
		super(aot);
		_aot = aot;
		
		//-- chargement du pack de texture
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("LevelSelectorMedia/LevelSelector.pack"));
		_lssSkin = new Skin(Gdx.files.internal("LevelSelectorMedia/LevelSelectorMedia.skin"),atlas);

		//-- constant pratique
		_targetOffsetPos = GAME_VIEWPORT_WIDTH/2-GAME_VIEWPORT_HEIGHT/2;
		_startTargetOffsetPos = GAME_VIEWPORT_WIDTH/2-GAME_VIEWPORT_HEIGHT/2;
		_bg_offset = -_targetOffsetPos*FLINGING_BG_REDUCER;

		//-- image de fond
		_backGround = new Image( _lssSkin.get("background",TextureRegion.class));
		_backGround.setSize(_backGround.getWidth()*GAME_VIEWPORT_HEIGHT/_backGround.getHeight(), GAME_VIEWPORT_HEIGHT);
		_backGround.setPosition(_targetOffsetPos*FLINGING_BG_REDUCER+_bg_offset, 0);
		
		_buttonPages = new Group();
		
		//-- dernier lvl deverouillé
		ImageButton lastUnlockedLevel = null;
		
		//-- liste des pages
		ArrayList<WidgetGroup> pageList = new ArrayList<WidgetGroup>();
				
		ArrayList<LevelStats> lvlList = _aot.getProfile().getLvlList();
		Iterator<LevelStats> itr = lvlList.iterator();
		int ligne = 0;
		int colone = 0;
		int page = 0;
		int i = 0;
		LevelStats currLvl;
		
		while(itr.hasNext()){
			currLvl = itr.next();
			ImageButton imgBut;
			WidgetGroup currWidgetPage;
			
			//-- creation de la page courrante
			if(pageList.size()<page+1){
				//-- nouvell page
				currWidgetPage =  new WidgetGroup();
				
				//-- fond du panneau 1 (page 1)
				Actor panel_bg = new Image( _lssSkin.get("p_background",TextureRegion.class));
				panel_bg.setSize(GAME_VIEWPORT_HEIGHT, GAME_VIEWPORT_HEIGHT);
				panel_bg.setPosition(0, 0);
				
				currWidgetPage.setPosition(GAME_VIEWPORT_WIDTH*page, 0);
				
				currWidgetPage.addActor(panel_bg);
				_buttonPages.addActor(currWidgetPage);
				_pageNumber+=1;
				pageList.add(currWidgetPage);
			}else{
				currWidgetPage =  pageList.get(page);
			}
			
			//-- bouton, verouillé skin different: lvl*bw () black and white / lvl*
			if(currLvl.isLocked()){
				imgBut = new ImageButton(_lssSkin.get("lvl"+new Integer(i+1)+"bw", ImageButtonStyle.class));
				imgBut.setSize(GAME_VIEWPORT_HEIGHT/3/imgBut.getHeight()*imgBut.getWidth(),GAME_VIEWPORT_HEIGHT/3);
				Image img = new Image( _lssSkin.get("cadenas",TextureRegion.class));
				img.setSize(CADENAS_SIZE, CADENAS_SIZE);
				imgBut.addActor(img);
			}else{
				imgBut = new ImageButton(_lssSkin.get("lvl"+new Integer(i+1), ImageButtonStyle.class));
				imgBut.setSize(GAME_VIEWPORT_HEIGHT/3/imgBut.getHeight()*imgBut.getWidth(),GAME_VIEWPORT_HEIGHT/3);
				imgBut.addListener(new LssButtonListener());
				//-- image de l'étile dernier score
				if(currLvl.getScore()!=null && currLvl.getScore()!=""){
					Actor star = new Image( _lssSkin.get("star_"+currLvl.getScore(),TextureRegion.class));
					star.setSize(32, 32);
					imgBut.addActor(star);
					star.setPosition((imgBut.getWidth()-star.getWidth())/2, 5);
				}
				//-- recuperation du dernier niveau deverouillé
				lastUnlockedLevel = imgBut;
			}
			
			imgBut.setName(currLvl.getName());
			
			currWidgetPage.addActor(imgBut);
			
			
			
			float buttonLeftOffset = (GAME_VIEWPORT_HEIGHT-((imgBut.getWidth())*3+LAYOUT_BUTTON_PADDING*2))/2;
			
			imgBut.setPosition(
					buttonLeftOffset+(imgBut.getWidth()+LAYOUT_BUTTON_PADDING)*colone, 
					GAME_VIEWPORT_HEIGHT/2+LAYOUT_BUTTON_PADDING/2-(LAYOUT_BUTTON_PADDING+imgBut.getHeight())*(ligne)
					);
			i++;
			colone = i%3;
			ligne = (i/3)%2;
			page = i/6;
		}
		
		//-- mise en evidence du dernier niveau atteint (clignottement)
		SequenceAction sequence = new SequenceAction();
		sequence.addAction(Actions.alpha(LAST_UNLOCKED_LVL_ALPHA, 1));
		sequence.addAction(Actions.alpha(1, 1));		
		lastUnlockedLevel.addAction(Actions.forever( sequence));
		
		_buttonPages.setPosition(_targetOffsetPos, 0);
		
		_mStage.addActor(_backGround);
		_mStage.addActor(_buttonPages);
			    
		_currentPage = 1;
		_state = STATE_IDLE;

		_mStage.addListener(new LssActorGestureListener());
		_mStage.addListener(new LssDragListener( )); 
	}

	public void slideLeft() {
		if(_currentPage<_pageNumber){
			_targetOffsetPos -= GAME_VIEWPORT_WIDTH;
			_buttonPages.addAction(Actions.moveTo(_targetOffsetPos, 0,FLINGING_SPEED));
			_backGround.addAction(Actions.moveTo(_targetOffsetPos*FLINGING_BG_REDUCER+_bg_offset, 0,FLINGING_SPEED));
			_currentPage+=1;
		}
	}
	
	public void slideRight() {
		if(_currentPage>1){
			_targetOffsetPos += GAME_VIEWPORT_WIDTH;
			_buttonPages.addAction(Actions.moveTo(_targetOffsetPos, 0,FLINGING_SPEED));
			_backGround.addAction(Actions.moveTo(_targetOffsetPos*FLINGING_BG_REDUCER+_bg_offset, 0,FLINGING_SPEED));
			_currentPage-=1;
		}
	}
	
	public void mySetPosition(float x){
		if(x<_startTargetOffsetPos && x>-(_pageNumber-1)*GAME_VIEWPORT_WIDTH+_startTargetOffsetPos){
			_buttonPages.setPosition(x, 0);
			_backGround.setPosition(x*FLINGING_BG_REDUCER+_bg_offset, 0);
		}
	}
	
	
	//-- capteur d'interuption sur les boutons
	private class LssButtonListener implements EventListener{
		
		@Override
		public boolean handle(Event event) {
			
			if (event.getTarget().getName()!=null && 
					!event.isHandled() &&
					_state==STATE_IDLE
					){
				
				GameScreen GScreen = new GameScreen (_aot,event.getTarget().getName()) ;
				
				_aot.setScreen(GScreen);
				event.handle();
				
			}
			return false;
		}
	}
	
	
	//-- capteur d'u=interuption pour le scrolling
	private class LssDragListener extends DragListener{
		float _offset = 0;
		
		@Override
		public void drag(InputEvent event,
                float x,
                float y,
                int pointer){
			_state = STATE_DRAGING;
			mySetPosition(x+_offset);
		}
		
		@Override
		public boolean touchDown(InputEvent event,
                float x,
                float y,
                int pointer,
                int button){
			_offset = _buttonPages.getX()-x;
			return super.touchDown(event,x,y,pointer,button);
		}
		
		@Override
		public void touchUp(InputEvent event,
                float x,
                float y,
                int pointer,
                int button){
			_state = STATE_IDLE;
			super.touchUp(event,x,y,pointer,button);
		}
	}
	
	
	//-- capteur d'u=interuption pour le flinging
	private class LssActorGestureListener extends ActorGestureListener{
		@Override
		public void fling(InputEvent event,float velocityX,float velocityY, int button){
			if(velocityX<FLINGING_VELOCITY_DETECTION_THRESHOLD && velocityX<0){
				slideLeft();
				event.handle();
			}else if(velocityX>FLINGING_VELOCITY_DETECTION_THRESHOLD && velocityX>0){
				slideRight();
				event.handle();
			}
		}
	}
}
