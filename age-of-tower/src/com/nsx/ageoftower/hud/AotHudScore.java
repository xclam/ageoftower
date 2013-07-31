package com.nsx.ageoftower.hud;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.nsx.ageoftower.screen.AbstractScreen;

public class AotHudScore extends WidgetGroup  {
	Skin _skin;
	
	public AotHudScore(Skin sk,int life, int[] goalLife, int time, int[] goalTime) {
		
		_skin = sk;
				
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
		}else if(point >= 4){
			star = new Image(_skin.get("star_silver",TextureRegion.class));
		}else if(point >= 2){
			star = new Image(_skin.get("star_bronze",TextureRegion.class));
		}else{
			star = new Image(_skin.get("star",TextureRegion.class));
			
		}
		
		Label gold = new Label("6/6",_skin.get("JUNEBUG_16",LabelStyle.class));
		Label silver= new Label("4/6",_skin.get("JUNEBUG_16",LabelStyle.class));
		Label bronze = new Label("2/6",_skin.get("JUNEBUG_16",LabelStyle.class));
		
		this.addActor(gold);
		this.addActor(silver);
		this.addActor(bronze);
		
		this.addActor(star);
		
		Label titlelbl = new Label("SCORE:"+point,_skin.get("JUNEBUG_32",LabelStyle.class));
		Label goalslbl = new Label("GOALS",_skin.get("JUNEBUG_32",LabelStyle.class));
		
		//-- le score
		this.setSize(titlelbl.getWidth()+100+goalslbl.getWidth(),goalslbl.getHeight()+gStar.getHeight()*3+lifelbl.getHeight());
		this.setPosition((AbstractScreen.GAME_VIEWPORT_WIDTH-this.getWidth())/2,
				(AbstractScreen.GAME_VIEWPORT_HEIGHT-this.getHeight())/2);
		this.addActor(titlelbl);
		titlelbl.setPosition(0, star.getHeight()+35+lifelbl.getHeight());
		star.setPosition(30, 50);
		this.addActor(goalslbl);
		
		//-- les goals
		goalslbl.setPosition(titlelbl.getWidth()+100, titlelbl.getY());
		this.addActor(gStar);
		gStar.setPosition(titlelbl.getWidth()+100, goalslbl.getY()-5-gStar.getHeight());
		gold.setPosition(titlelbl.getWidth()+100+gStar.getWidth()+5, goalslbl.getY()-5-gStar.getHeight()+5);
		this.addActor(sStar);
		sStar.setPosition(titlelbl.getWidth()+100, goalslbl.getY()-(5+sStar.getHeight())*2);
		silver.setPosition(titlelbl.getWidth()+100+sStar.getWidth()+5, goalslbl.getY()-(5+sStar.getHeight())*2+5);
		this.addActor(bStar);
		bStar.setPosition(titlelbl.getWidth()+100, goalslbl.getY()-(5+bStar.getHeight())*3);
		bronze.setPosition(titlelbl.getWidth()+100+bStar.getWidth()+5, goalslbl.getY()-(5+bStar.getHeight())*3+5);
		
		//-- le detail life & time
		this.addActor(lifelbl);
		lifelbl.setPosition(0, 25);
		this.addActor(timelbl);
		timelbl.setPosition(0, 0);
		
		this.addActor(lifegoal);
		lifegoal.setPosition(titlelbl.getWidth()+100, 25);
		this.addActor(timegoal);
		timegoal.setPosition(titlelbl.getWidth()+100, 0);
	}

}
