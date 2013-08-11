package com.nsx.ageoftower.hud;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.nsx.ageoftower.event.AotEvent;
import com.nsx.ageoftower.screen.AbstractScreen;

public class AotHudScore extends WidgetGroup implements  EventListener {
	Skin _skin;
	Actor _continueButon;
	boolean _clicked;
	String _score;
	
	public AotHudScore(Skin sk,int life, int[] goalLife, int time, int[] goalTime) {
		_clicked = false;
		_skin = sk;
				
		_continueButon = new Label(">> CONTINUE <<",_skin.get("JUNEBUG_32",LabelStyle.class)); 
		
		Label lifelbl = new Label("LIFE: "+life,_skin.get("JUNEBUG_16",LabelStyle.class));
		Label timelbl = new Label("TIME: "+time,_skin.get("JUNEBUG_16",LabelStyle.class));
		Label timegoal;
		Label lifegoal = new Label("TIME: "+time,_skin.get("JUNEBUG_16",LabelStyle.class));
		Image gStar = new Image(_skin.get("star_gold",TextureRegion.class));
		gStar.setSize(40, 40);
		Image sStar = new Image(_skin.get("star_silver",TextureRegion.class));
		sStar.setSize(40, 40);
		Image bStar = new Image(_skin.get("star_bronze",TextureRegion.class));
		bStar.setSize(40, 40);
		Image star ;
		
		int pointl = 0;
		if(life >= goalLife[0] ){
			pointl += 3;
		}else if(life >= goalLife[1]){
			pointl += 2;
		}else if(life >= goalLife[2]){
			pointl += 1;
		}
		
		lifegoal = new Label(pointl+"/3 ("+goalLife[0]+"/"+goalLife[1]+"/"+goalLife[2]+")",
				_skin.get("JUNEBUG_16",LabelStyle.class));
		
		int pointt = 0;
		if(time <= goalTime[0] ){
			pointt += 3;
		}else if(time <= goalTime[1]){
			pointt += 2;
		}else if(time <= goalTime[2]){
			pointt += 1;
		}
		
		timegoal= new Label(pointt+"/3 ("+goalTime[0]+"/"+goalTime[1]+"/"+goalTime[2]+")",
				_skin.get("JUNEBUG_16",LabelStyle.class));
		
		int point = pointl+pointt;
		
		if(point == 6){
			star = new Image(_skin.get("star_gold",TextureRegion.class));
			_score = "gold";
		}else if(point >= 4){
			star = new Image(_skin.get("star_silver",TextureRegion.class));
			_score = "silver";
		}else if(point >= 2){
			star = new Image(_skin.get("star_bronze",TextureRegion.class));
			_score = "bronze";
		}else{
			star = new Image(_skin.get("star",TextureRegion.class));
			_score = "";
			
		}
		
		Label gold = new Label("6/6",_skin.get("JUNEBUG_16",LabelStyle.class));
		Label silver= new Label("4/6",_skin.get("JUNEBUG_16",LabelStyle.class));
		Label bronze = new Label("2/6",_skin.get("JUNEBUG_16",LabelStyle.class));
		
		Label titlelbl = new Label("SCORE:"+point,_skin.get("JUNEBUG_32",LabelStyle.class));
		Label goalslbl = new Label("GOALS",_skin.get("JUNEBUG_32",LabelStyle.class));
		
		//-- le score
		this.setSize(titlelbl.getWidth()+100+goalslbl.getWidth(),goalslbl.getHeight()+gStar.getHeight()*3+lifelbl.getHeight());
		this.setPosition((AbstractScreen.GAME_VIEWPORT_WIDTH-this.getWidth())/2,
				(AbstractScreen.GAME_VIEWPORT_HEIGHT-this.getHeight())/2);
		
		titlelbl.setPosition(0, star.getHeight()+35+lifelbl.getHeight());
		star.setPosition(30, 50);
		
		//-- les goals
		goalslbl.setPosition(titlelbl.getWidth()+100, titlelbl.getY());
		gStar.setPosition(titlelbl.getWidth()+100, goalslbl.getY()-5-gStar.getHeight());
		gold.setPosition(titlelbl.getWidth()+100+gStar.getWidth()+5, goalslbl.getY()-5-gStar.getHeight()+5);
		
		sStar.setPosition(titlelbl.getWidth()+100, goalslbl.getY()-(5+sStar.getHeight())*2);
		silver.setPosition(titlelbl.getWidth()+100+sStar.getWidth()+5, goalslbl.getY()-(5+sStar.getHeight())*2+5);
		bStar.setPosition(titlelbl.getWidth()+100, goalslbl.getY()-(5+bStar.getHeight())*3);
		bronze.setPosition(titlelbl.getWidth()+100+bStar.getWidth()+5, goalslbl.getY()-(5+bStar.getHeight())*3+5);
		
		//-- le detail life & time
		lifelbl.setPosition(0, 25);
		timelbl.setPosition(0, 0);
		lifegoal.setPosition(titlelbl.getWidth()+100, 25);
		timegoal.setPosition(titlelbl.getWidth()+100, 0);
		
		_continueButon.setPosition((this.getWidth()-_continueButon.getWidth())/2, -_continueButon.getHeight()-30);
		_continueButon.addListener(this);
		
		this.addActor(lifegoal);
		this.addActor(timegoal);
		this.addActor(bronze);
		this.addActor(bStar);
		this.addActor(silver);
		this.addActor(sStar);
		this.addActor(gold);
		this.addActor(gStar);
		this.addActor(goalslbl);
		this.addActor(timelbl);
		this.addActor(lifelbl);
		this.addActor(star);
		this.addActor(titlelbl);
		this.addActor(_continueButon);
		
		//-- on deplace tout vers le haut pour l'animation
		for(Actor a:this.getChildren()){
			a.addAction(Actions.moveBy(0, +AbstractScreen.GAME_VIEWPORT_HEIGHT, 0));
		}
	}
	
	//-- on deplace tout vers le bas avec un delay
	public void showAnimate(){
		int i = 0;
		for(Actor a:this.getChildren()){
			i++;
			SequenceAction sequence = new SequenceAction();
			sequence.addAction(Actions.delay(0.1f*(float)i));
			sequence.addAction(Actions.moveBy(0, -AbstractScreen.GAME_VIEWPORT_HEIGHT, 0.25f));
			a.addAction(sequence);
		}

	}

	//-- on deplace tout vers le bas avec un delay
	public void hideAnimate(){
		int i = 0;
		for(Actor a:this.getChildren()){
			i++;
			SequenceAction sequence = new SequenceAction();
			sequence.addAction(Actions.delay(0.05f*(float)i));
			sequence.addAction(Actions.moveBy(0, -AbstractScreen.GAME_VIEWPORT_HEIGHT, 0.12f));
			a.addAction(sequence);
		}
	}

	@Override
	public boolean handle(Event event) {
		if (!(event instanceof InputEvent)) return false;
			if(((InputEvent)event).getButton() == 0){
				//-- click sur le bouton continue
				if(event.getTarget()==_continueButon){
					if( !_clicked){
						this.fire(new AotEvent(AotEvent.Type.nextLevelButtonClicked,this));
						_clicked = true;
					}else{
						_clicked = false;
					}
				}
				return true;
		}
		return false;
	}

	public String getScore() {
		return _score;
	}
}
