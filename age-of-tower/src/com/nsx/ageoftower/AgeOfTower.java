package com.nsx.ageoftower;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.nsx.ageoftower.screen.SplashScreen;
import com.nsx.ageoftower.utils.AotPreferences;
import com.nsx.ageoftower.utils.MusicManager;
import com.nsx.ageoftower.utils.ProfileStateHolder;

public class AgeOfTower extends Game {	
	//-- constant useful for logging
	public static final String LOG = AgeOfTower.class.getSimpleName();
	//-- objet contenant les informations l'état du jeux.
	private ProfileStateHolder _profile;
	private AotPreferences _preferences;
	private MusicManager _musicManager;
	
	public AgeOfTower(){
		_preferences = new AotPreferences();
	}
	
	public void setProfile(String name){
		Json json = new Json();
		json.setOutputType(OutputType.minimal);
		FileHandle pFileHandle = Gdx.files.local("data/Profiles/"+name+".prf");
		//-- chargement du profile
		if(pFileHandle.exists()){
			_profile = json.fromJson(ProfileStateHolder.class, pFileHandle);
		}else{
			//-- le fichier n'existe pas, on creer donc un ProfileStateHolder manuellement
			_profile = new ProfileStateHolder(name);
			String text = json.prettyPrint(_profile);
			pFileHandle.writeString(text, false);
		}
	}
	
	public void setProfile(ProfileStateHolder _profile) {
		this._profile = _profile;
	}

	public ProfileStateHolder getProfile() {
		return _profile;
	}
	
	@Override
	public void create() {
		this.setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public AotPreferences getPreferences() {
		return _preferences;
	}

}
