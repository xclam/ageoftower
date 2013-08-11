package com.nsx.ageoftower.hud;

import java.util.Iterator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.nsx.ageoftower.screen.AbstractScreen;

/**
 * @author Admin
 *
 */
public class AotHud extends WidgetGroup implements EventListener{
	AotHudTimer _timer;
	Skin _skin;
	AotWidgetGroup _tmpPool;
	AotWidgetGroup _columnPool; 
	AotHudLife _life;
	AotHudGold _gold;
	AotHudClock _clock;
	AotHudSound _sound;
	Label _wave;
	Image _bottomImage;
	Image _background;
	AotHudScore _ahs;
	AotHudGameOver _gameover;
	
	AotHudLaunchButton _launchButton;
	
	ListWidgetGroup _left;
	ListWidgetGroup _right;
	ListWidgetGroup _center;
	ListWidgetGroup _bottomeLine;
	
	
	public AotHud(Skin sk){
		_skin = sk;	
		
		_background = new Image(_skin.get("black_pixel",TextureRegion.class));
		_background.setSize(AbstractScreen.GAME_VIEWPORT_WIDTH, AbstractScreen.GAME_VIEWPORT_HEIGHT);	
		//_background.addAction(Actions.fadeOut(0));
		_background.addAction(Actions.alpha(0f,0));
		this.addActor(_background);
		
		_timer = new AotHudTimer("TIME LEFT",15,_skin.get("JUNEBUG_16",LabelStyle.class));
		_timer.setName("timer");
		_life = new AotHudLife(_skin);
		_life.setName("life");
		_gold = new AotHudGold(_skin);
		_gold.setName("gold");
		_clock = new AotHudClock(_skin);
		_clock.setName("clock");
		_wave = new Label("WAVE:",_skin.get("JUNEBUG_16",LabelStyle.class));
		_wave.setName("wave");
		
		_bottomImage = new Image(_skin.get("banner",TextureRegion.class));		
		_bottomImage.setName("banner");
		_bottomImage.setSize(
				AbstractScreen.GAME_VIEWPORT_WIDTH,
				(AbstractScreen.GAME_VIEWPORT_WIDTH/_bottomImage.getWidth())*_bottomImage.getHeight());
		
		//-- ajout dans la pool temporaire 
		_tmpPool = new AotWidgetGroup();
		_tmpPool.addActor(_timer);
		_tmpPool.addActor(_life);
		_tmpPool.addActor(_wave);
		_tmpPool.addActor(_bottomImage);
		_tmpPool.addActor(_gold);
		_tmpPool.addActor(_clock);
		
		//-- paratique lors de la lecture du layout
		_columnPool = new AotWidgetGroup();
		_columnPool.addActor(new ListWidgetGroup("left"));
		_columnPool.addActor(new ListWidgetGroup("center"));
		_columnPool.addActor(new ListWidgetGroup("right"));
		_columnPool.addActor(new ListWidgetGroup("bottomLine"));
		
		LoadFromLayoutFile("GameScreenMedia/HUD/default.layout");
		
		_launchButton = new AotHudLaunchButton(_skin);
		
		_sound = new AotHudSound(_skin);
		_sound.setName("sound");
		_sound.setSound(true);
		
		
		//this.addListener(_launchButton);
		this.addActor(_columnPool);
		this.addActor(_launchButton);
		this.addActor(_sound);
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
		FileHandle layoutFileHandle = Gdx.files.internal(string);
		//-- chargement du profile
		@SuppressWarnings("unchecked")
		Array<Array<Object>> hl_ = json.fromJson(Array.class,layoutFileHandle);
		
		Iterator<Array<Object>> itr = hl_.iterator();
		Actor tmp;
		while(itr.hasNext()){
			Array<Object> tmp1 = itr.next();
			// recuperation de l'element dans la pool temporaire
			tmp =_tmpPool.getChild((String)tmp1.get(0));
			
			if(tmp!=null){
				this.addActorTo(tmp,(String)tmp1.get(1),(String)tmp1.get(2));
			}else if(tmp1.get(0).equals("padding")){
				_columnPool.setPadding(
						(Float)(tmp1.get(1)),
						(Float)(tmp1.get(2)),
						(Float)(tmp1.get(3)),
						(Float)(tmp1.get(4)));
						
			}else{
				System.out.println("Element :"+tmp1.get(0)+" unknown!");
			}
		}
	}
	
	private void addActorTo(Actor a, String colname, String glu) {
		System.out.println("    Adding Element:"+a.getName()+" colname:"+colname+" glu:"+glu);
		ListWidgetGroup col = (ListWidgetGroup) (_columnPool.getChild(colname));
		System.out.println(col.getName());
		col.addActor(a,glu);
	}

	public void clockReset(){
		_clock.reset();
	}
	
	public void clockStop(){
		_clock.stop();
	}
	
	public void clockStart(){
		_clock.start();
	}
	
	public void goldAdd(int gold){
		_gold.addGold(gold);
	}
	
	public void goldSetGold(int gold){
		_gold.setGold(gold);
	}
	
	public void goldStartIncrement(){
		_gold.startInc();
	}

	public void goldStopIncrement(){
		_gold.stopInc();
	}
	
	public void lifeSetLife(int val){
		_life.set_value(val);
	}

	public void waveLaunchButtonEnableButton() {
		// TODO Auto-generated method stub
	}

	public void waveLaunchButtonSetTimer(int i) {
		_launchButton.setTimer(i);
	}

	public void waveLaunchButtonStartTimer() {
		_launchButton.startTimer();
	}

	/**
	 * @param message : Message to show
	 * @param time : Seconds during the message will stay on the hud  
	 */
	public void message(String message, float time) {
		System.out.println("New message:"+ message);
		AotHudMessage m = new AotHudMessage(message,time,_skin);
		m.setTargetPosition(AbstractScreen.GAME_VIEWPORT_WIDTH/2- m.getWidth()/2, 
				      AbstractScreen.GAME_VIEWPORT_HEIGHT/2-m.getHeight()/2);
		this.addActor(m);
		m.animate();
	}

	public void waveLaunchButtonDisableButton() {
		// TODO Auto-generated method stub
	}
	
	public void showScore(int life, int[] goalLife, float time,
			int[] goalTime) {
		
		_columnPool.setVisible(false);
		_launchButton.setVisible(false);
		_sound.setVisible(false);

		_background.addAction(Actions.fadeIn( 0.25f));		
		_ahs = new AotHudScore(_skin,life,goalLife,(int)time,goalTime);
		this.addActor(_ahs);
		_ahs.showAnimate();
	}
	
	public String getScore() {
		return _ahs.getScore();
	}
	
	public void showGameOver() {
		_columnPool.setVisible(false);
		_launchButton.setVisible(false);
		_sound.setVisible(false);
		
		_background.addAction(Actions.fadeIn( 0.25f));
		_gameover = new AotHudGameOver(_skin);
		this.addActor(_gameover);
		_gameover.showAnimate();
	}
	
	public void init(){
		System.out.println("hud init called");
		if (_gameover!=null)
			_gameover.hideAnimate();
		if(_ahs!=null)
			_ahs.hideAnimate();
		_background.addAction(Actions.fadeOut( 0.25f));	
		_columnPool.setVisible(true);
		_launchButton.setVisible(true);
	}
	

	@Override
	public boolean handle(Event event) {
		System.out.println(" AotHudLaunchButton new event:"+event);
		return false;
	}
	
	
	class AotWidgetGroup  extends WidgetGroup{
		float _padTop;
		float _padRight;
		float _padBot;
		float _padLeft;
		
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
		
		public void setPadding(float top, float right, float bot, float left) {
			_padTop   = top;
			_padRight = right;
			_padBot   = bot;
			_padLeft  = left;
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
			this.rePack();
					
			_nextTopPosition = this.getHeight();
			_nextBottomPosition = 0;
		}

		
		private void rePack() {
			AotWidgetGroup tmp;
			if(this.getParent()!=null){
				tmp = (AotWidgetGroup) this.getParent();
				System.out.println("PArents found");
			}else{
				tmp = new AotWidgetGroup();
			}
			
			System.out.println("tmp._padLeft:"+tmp._padLeft);
			
			if(this.getName().equals("left")){
				//-- colonne de gauche 1 tiers de large, toute la hauteur - la hauteur de bottomline
				this.setPosition(5, AbstractScreen.GAME_VIEWPORT_HEIGHT*1/6+tmp._padBot);
				this.setSize(AbstractScreen.GAME_VIEWPORT_WIDTH*1/3-tmp._padLeft, AbstractScreen.GAME_VIEWPORT_HEIGHT*5/6-tmp._padTop-tmp._padBot);
			}else if(this.getName().equals("center")){
				//-- colonne du centre 1 tiers de large, toute la hauteur - la hauteur de bottomline
				this.setPosition(AbstractScreen.GAME_VIEWPORT_WIDTH*1/3, AbstractScreen.GAME_VIEWPORT_HEIGHT*1/6+tmp._padBot);
				this.setSize(AbstractScreen.GAME_VIEWPORT_WIDTH*1/3, AbstractScreen.GAME_VIEWPORT_HEIGHT*5/6-tmp._padTop-tmp._padBot);
			}else if(this.getName().equals("right")){
				//-- colonne de gauche 1 tiers de large, toute la hauteur - la hauteur de bottomline
				this.setPosition(AbstractScreen.GAME_VIEWPORT_WIDTH*7/8, AbstractScreen.GAME_VIEWPORT_HEIGHT*1/6+tmp._padBot);
				this.setSize(AbstractScreen.GAME_VIEWPORT_WIDTH*1/3-tmp._padRight, AbstractScreen.GAME_VIEWPORT_HEIGHT*5/6-tmp._padTop-tmp._padBot);
			}else if(this.getName().equals("bottomLine")){
				//-- Ligne du bas
				this.setPosition(0, 0);
				this.setSize(AbstractScreen.GAME_VIEWPORT_WIDTH, AbstractScreen.GAME_VIEWPORT_HEIGHT*1/6);
			}
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
