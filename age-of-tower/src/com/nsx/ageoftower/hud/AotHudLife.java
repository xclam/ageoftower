package com.nsx.ageoftower.hud;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;



public class AotHudLife extends WidgetGroup {
	Label _label;
	Skin _skin;
	Image _img;
	int _value;
	
	public AotHudLife(Skin sk){
		_value = 0;
		_skin = sk;
		_label = new Label(""+_value,_skin.get("JUNEBUG_16",LabelStyle.class));
		_label.setVisible(true);
		_img = new Image(_skin.get("life",TextureRegion.class));
		_img.setVisible(true);
		_img.setSize(_label.getHeight()-5, _label.getHeight()-5);
		_label.setPosition(_img.getWidth()+5, 0);
		this.setSize( _label.getWidth()+_img.getWidth(), _label.getHeight());
		this.addActor(_label);
		this.addActor(_img);
	}

	@Override
	public void act(float delta){
		_label.setText(""+_value);
		super.act(delta);
	}
	
	public int get_value() {
		return _value;
	}

	public void set_value(int _value) {
		this._value = _value;
	}
	
}
