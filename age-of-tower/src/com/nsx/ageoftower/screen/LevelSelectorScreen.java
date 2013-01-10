package com.nsx.ageoftower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.nsx.ageoftower.AgeOfTower;

public class LevelSelectorScreen extends AbstractScreen   {
	//-- espacement entres les boutons
	public static final int LAYOUT_BUTTON_PADDING = 5;
	//-- seuil de declanchement du slide des panneaux
	public static final float FLINGING_VELOCITY_DETECTION_THRESHOLD = 400;
	//-- constant de redction du mouvement de l'image de fond par raport au premier plan
	public static final float FLINGING_BG_REDUCER = 0.2f;
	//-- vitesse de slide des panneau
	public static final float FLINGING_SPEED = 0.3f;
	//-- constante de gestion de l'interactviti�
	public static final int STATE_IDLE = 0;
	public static final int STATE_FLINGING_RIGHT = 1;
	public static final int STATE_FLINGING_LEFT = 2;
	public static final int STATE_DRAGING = 3;
	
	WidgetGroup _buttonPage1;
	WidgetGroup _buttonPage2;
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
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/LevelSelectorMedia/LevelSelector.pack"));
		_lssSkin = new Skin(Gdx.files.internal("data/LevelSelectorMedia/LevelSelectorMedia.skin"),atlas);
		
		//-- fond du panneau 1 (page 1)
		Actor panel_bg = new Image( _lssSkin.get("p_background",TextureRegion.class));
		panel_bg.setSize(GAME_VIEWPORT_HEIGHT, GAME_VIEWPORT_HEIGHT);
		panel_bg.setPosition(0, 0);
		
		//-- fond du panneau 2 (page 2)
		Actor panel_bg2 = new Image( _lssSkin.get("p_background",TextureRegion.class));
		panel_bg2.setSize(GAME_VIEWPORT_HEIGHT, GAME_VIEWPORT_HEIGHT);
		panel_bg2.setPosition(0, 0);
		
		//-- constant pratique
		_targetOffsetPos = GAME_VIEWPORT_WIDTH/2-panel_bg.getWidth()/2;
		_startTargetOffsetPos = GAME_VIEWPORT_WIDTH/2-panel_bg.getWidth()/2;
		_bg_offset = -_targetOffsetPos*FLINGING_BG_REDUCER;
		
		//-- image de fond
		_backGround = new Image( _lssSkin.get("background",TextureRegion.class));
		_backGround.setSize(_backGround.getWidth()*GAME_VIEWPORT_HEIGHT/_backGround.getHeight(), GAME_VIEWPORT_HEIGHT);
		_backGround.setPosition(_targetOffsetPos*FLINGING_BG_REDUCER+_bg_offset, 0);
		
		//-- Page 1 ------
		_buttonPage1 = new WidgetGroup();
		_buttonPage1.addActor(panel_bg);
		//-- bouton 1
		ImageButton lvl1 = new ImageButton(_lssSkin.get("lvl1", ImageButtonStyle.class));
		lvl1.setName("1");
		lvl1.addListener(new LssButtonListener());
		_buttonPage1.addActor(lvl1);
		lvl1.setSize(GAME_VIEWPORT_HEIGHT/3/lvl1.getHeight()*lvl1.getWidth(),GAME_VIEWPORT_HEIGHT/3);
		float buttonLeftOffset = (panel_bg.getWidth()-(3*lvl1.getWidth()+2*LAYOUT_BUTTON_PADDING))/2;
		lvl1.setPosition(buttonLeftOffset, GAME_VIEWPORT_HEIGHT/2+LAYOUT_BUTTON_PADDING/2);		
		//-- bouton 2
		ImageButton lvl2 = new ImageButton(_lssSkin.get("lvl2", ImageButtonStyle.class));
		lvl2.setName("2");
		lvl2.addListener(new LssButtonListener());
		_buttonPage1.addActor(lvl2);
		lvl2.setSize(GAME_VIEWPORT_HEIGHT/3/lvl2.getHeight()*lvl2.getWidth(),GAME_VIEWPORT_HEIGHT/3);
		lvl2.setPosition(buttonLeftOffset+lvl2.getWidth()+LAYOUT_BUTTON_PADDING, GAME_VIEWPORT_HEIGHT/2+LAYOUT_BUTTON_PADDING/2);
		//-- bouton 3
		ImageButton lvl3 = new ImageButton(_lssSkin.get("lvl3", ImageButtonStyle.class));
		lvl3.setName("3");
		lvl3.addListener(new LssButtonListener());
		_buttonPage1.addActor(lvl3);
		lvl3.setSize(GAME_VIEWPORT_HEIGHT/3/lvl3.getHeight()*lvl3.getWidth(),GAME_VIEWPORT_HEIGHT/3);
		lvl3.setPosition(buttonLeftOffset+(lvl3.getWidth()+LAYOUT_BUTTON_PADDING)*2, GAME_VIEWPORT_HEIGHT/2+LAYOUT_BUTTON_PADDING/2);
		//-- bouton 4
		ImageButton lvl4 = new ImageButton(_lssSkin.get("lvl4", ImageButtonStyle.class));
		lvl4.setName("4");
		lvl4.addListener(new LssButtonListener());
		_buttonPage1.addActor(lvl4);
		lvl4.setSize(GAME_VIEWPORT_HEIGHT/3/lvl4.getHeight()*lvl4.getWidth(),GAME_VIEWPORT_HEIGHT/3);
		lvl4.setPosition(buttonLeftOffset, GAME_VIEWPORT_HEIGHT/2-LAYOUT_BUTTON_PADDING/2-lvl4.getHeight());
		//-- bouton 5
		ImageButton lvl5 = new ImageButton(_lssSkin.get("lvl5", ImageButtonStyle.class));
		lvl5.setName("5");
		lvl5.addListener(new LssButtonListener());
		_buttonPage1.addActor(lvl5);
		lvl5.setSize(GAME_VIEWPORT_HEIGHT/3/lvl5.getHeight()*lvl5.getWidth(),GAME_VIEWPORT_HEIGHT/3);
		lvl5.setPosition(buttonLeftOffset+lvl5.getWidth()+LAYOUT_BUTTON_PADDING, GAME_VIEWPORT_HEIGHT/2-LAYOUT_BUTTON_PADDING/2-lvl5.getHeight());
		//-- bouton 6
		ImageButton lvl6 = new ImageButton(_lssSkin.get("lvl6", ImageButtonStyle.class));
		lvl6.setName("6");
		lvl6.addListener(new LssButtonListener());
		_buttonPage1.addActor(lvl6);
		lvl6.setSize(GAME_VIEWPORT_HEIGHT/3/lvl6.getHeight()*lvl6.getWidth(),GAME_VIEWPORT_HEIGHT/3);
		lvl6.setPosition(buttonLeftOffset+(lvl6.getWidth()+LAYOUT_BUTTON_PADDING)*2, GAME_VIEWPORT_HEIGHT/2-LAYOUT_BUTTON_PADDING/2-lvl6.getHeight());		
		
		_buttonPage1.setPosition(0, 0);
		_pageNumber+=1;
		
		//-- Page 2 ------
		_buttonPage2 = new WidgetGroup();
		_buttonPage2.addActor(panel_bg2);
		//-- bouton 7
		ImageButton lvl7 = new ImageButton(_lssSkin.get("lvl7", ImageButtonStyle.class));
		lvl7.setName("7");
		lvl7.addListener(new LssButtonListener());
		_buttonPage2.addActor(lvl7);
		lvl7.setSize(GAME_VIEWPORT_HEIGHT/3/lvl7.getHeight()*lvl7.getWidth(),GAME_VIEWPORT_HEIGHT/3);
		lvl7.setPosition(buttonLeftOffset, GAME_VIEWPORT_HEIGHT/2+LAYOUT_BUTTON_PADDING/2);		
		//-- bouton 8
		ImageButton lvl8 = new ImageButton(_lssSkin.get("lvl8", ImageButtonStyle.class));
		lvl8.setName("8");
		lvl8.addListener(new LssButtonListener());
		_buttonPage2.addActor(lvl8);
		lvl8.setSize(GAME_VIEWPORT_HEIGHT/3/lvl8.getHeight()*lvl8.getWidth(),GAME_VIEWPORT_HEIGHT/3);
		lvl8.setPosition(buttonLeftOffset+lvl8.getWidth()+LAYOUT_BUTTON_PADDING, GAME_VIEWPORT_HEIGHT/2+LAYOUT_BUTTON_PADDING/2);

		_buttonPage2.setPosition(GAME_VIEWPORT_WIDTH, 0);
		_pageNumber+=1;
		
		//-- ensemble des n pannels
		_buttonPages = new Group();
		_buttonPages.addActor(_buttonPage1);
		_buttonPages.addActor(_buttonPage2);
		_buttonPages.setPosition(_targetOffsetPos, 0);
		
		_mStage.addActor(_backGround);
		_mStage.addActor(_buttonPages);
			    
		_currentPage = 1;
		_state = STATE_IDLE;

		_mStage.addListener(new LssActorGestureListener());
		_mStage.addListener(new LssDragListener());
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
				DummyScreen ds = new DummyScreen(_aot);
				ds.setLvl(event.getTarget().getName());
				_aot.setScreen(ds);
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
