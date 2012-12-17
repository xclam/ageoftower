package com.nsx.ageoftower.screen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.nsx.ageoftower.AgeOfTower;

public class LevelSelectorScreen implements Screen, GestureListener  {
	public static final float FLINGING_SPEED = 2000;
	public static final float FLINGING_ACCEL = 1;
	public static final float FLINGING_BG_REDUCER = 0.2f;
	public static final int STATE_IDLE = 0;
	public static final int STATE_FLINGING_RIGHT = 1;
	public static final int STATE_FLINGING_LEFT = 2;
	
	Texture backgroundTxt;
	Texture panel1Txt;
	Texture panel2Txt;
	Sprite backgroundSprit;
	Sprite panel1Sprit;
	Sprite panel2Sprit;
	List<Sprite> panelList;
	OrthographicCamera camera;
    SpriteBatch batch;
	private float screenWidth;
	private float screenHeight;
	AgeOfTower _mAot;
	float bgPositionOffset;
	float panel1PositionOffset;
	float panel1PositionOffsetTarget;
	float panelCenterOffset;
	List<Rectangle> ButtonList;
	
	float currentPage;
	int state;
	
	
	public LevelSelectorScreen(AgeOfTower aot) {		
		_mAot = aot;
		
		backgroundTxt = new Texture(Gdx.files.internal("data/LevelSelectorMedia/background.png"));
		backgroundSprit = new Sprite(backgroundTxt,2048,706);

		panel1Txt = new Texture(Gdx.files.internal("data/LevelSelectorMedia/panel1.png"));
		panel1Sprit = new Sprite(panel1Txt);

		panel2Txt = new Texture(Gdx.files.internal("data/LevelSelectorMedia/panel2.png"));
		panel2Sprit = new Sprite(panel2Txt);
		
		panelList = new ArrayList<Sprite>();
		panelList.add(panel1Sprit);
		panelList.add(panel2Sprit);
		
		ButtonList = new ArrayList<Rectangle>();
		
		camera = new OrthographicCamera();
	    camera.setToOrtho(false);
		
	    batch = new SpriteBatch();
	    
	    bgPositionOffset = 0;
	    panel1PositionOffset = 0;
	    panel1PositionOffsetTarget = 0;
	    currentPage = 1;
	    
	    state = STATE_IDLE;
	    Gdx.input.setInputProcessor(new  GestureDetector(this));
	}
	
	@Override
	public void render(float delta) {
		camera.update();
		camera.apply(Gdx.gl10);
		batch.setProjectionMatrix(camera.combined);
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		//-- gestion du passage d'une page à un autre
		switch(this.state){
			case STATE_IDLE:
				break;
			case STATE_FLINGING_RIGHT:
				panel1PositionOffset -= delta*FLINGING_SPEED;
				if(panel1PositionOffset < panel1PositionOffsetTarget){
					panel1PositionOffset = panel1PositionOffsetTarget;
					currentPage += 1;
					state = STATE_IDLE;
				}
				break;
			case STATE_FLINGING_LEFT:
				panel1PositionOffset += delta*FLINGING_SPEED;
				if(panel1PositionOffset > panel1PositionOffsetTarget){
					panel1PositionOffset = panel1PositionOffsetTarget;
					currentPage -= 1;
					state = STATE_IDLE;
				}
				break;
		}
		
		batch.begin();
		
		backgroundSprit.setPosition(panel1PositionOffset*FLINGING_BG_REDUCER, 0);
		backgroundSprit.draw(batch);
		panel1Sprit.setPosition(panel1PositionOffset+panelCenterOffset, 0);
		panel1Sprit.draw(batch);
		panel2Sprit.setPosition(panel1PositionOffset+panelCenterOffset+this.screenWidth, 0);
		panel2Sprit.draw(batch);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		System.out.println("resize cllaed");
		this.screenWidth = width;
		this.screenHeight = height;
		
		float newWidth = (this.screenHeight*backgroundSprit.getWidth())/backgroundSprit.getHeight();
		backgroundSprit.setBounds(0,0,newWidth, this.screenHeight);
		
		newWidth = (this.screenHeight*panel1Sprit.getWidth())/panel1Sprit.getHeight();
		panel1Sprit.setBounds(0,0,newWidth, this.screenHeight);
		
		newWidth = (this.screenHeight*panel2Sprit.getWidth())/panel2Sprit.getHeight();
		panel2Sprit.setBounds(0,0,newWidth, this.screenHeight);
		
		panelCenterOffset = this.screenWidth/2 - panel1Sprit.getWidth()/2;
		
		//-- on defini en dur 6 bouton au centre de l'ecran, en face panel
		ButtonList.clear();
		//-- bouton 1: 
		ButtonList.add(new Rectangle(this.screenWidth/2 - panel1Sprit.getWidth()/2,
					0,
					panel1Sprit.getWidth()/3,
					this.screenHeight/2));
		//-- bouton 2
		ButtonList.add(new Rectangle(this.screenWidth/2 - panel1Sprit.getWidth()/2 +panel1Sprit.getWidth()/3,
					0,
					panel1Sprit.getWidth()/3,
					this.screenHeight/2));
		//-- bouton 3
		ButtonList.add(new Rectangle(this.screenWidth/2 - panel1Sprit.getWidth()/2 +2*panel1Sprit.getWidth()/3,
					0,
					panel1Sprit.getWidth()/3,
					this.screenHeight/2));
		//-- bouton 4
		ButtonList.add(new Rectangle(this.screenWidth/2 - panel1Sprit.getWidth()/2 ,
					this.screenHeight/2,
					panel1Sprit.getWidth()/3,
					this.screenHeight/2));
		//-- bouton 5
		ButtonList.add(new Rectangle(this.screenWidth/2 - panel1Sprit.getWidth()/2 +panel1Sprit.getWidth()/3,
					this.screenHeight/2,
					panel1Sprit.getWidth()/3,
					this.screenHeight/2));
		//-- bouton 6
		ButtonList.add(new Rectangle(this.screenWidth/2 - panel1Sprit.getWidth()/2 +2*panel1Sprit.getWidth()/3,
				this.screenHeight/2,
					panel1Sprit.getWidth()/3,
					this.screenHeight/2));
	}

	@Override
	public void show() {		
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		System.out.println("current page :"+currentPage);
		if (state == STATE_IDLE ){
			if (velocityX<0 && panelList.size()> currentPage){
				state = STATE_FLINGING_RIGHT;
				panel1PositionOffsetTarget = panel1PositionOffset-this.screenWidth;
			}else if( velocityX> 0 && currentPage>1){
				state = STATE_FLINGING_LEFT;
				panel1PositionOffsetTarget = panel1PositionOffset+this.screenWidth;
			}
		}
		return false;
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		//Iterator<Rectangle> i = ButtonList.iterator();
		for(int i1=0;i1<ButtonList.size();i1++){
			if(ButtonList.get(i1).contains(x,y)){
				System.out.println("Button pressed:"+i1);
				_mAot.anOtherScreen.setLvl((int) (i1+1+(currentPage-1)*6));
				_mAot.setScreen(_mAot.anOtherScreen);
			}			
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
}
