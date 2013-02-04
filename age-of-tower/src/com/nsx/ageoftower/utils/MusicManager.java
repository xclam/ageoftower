package com.nsx.ageoftower.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

/**
 * A service that manages the background music.
 * Only one music may be playing at a given time.
 */
public class MusicManager implements Disposable {
	
	/**
     * The available music files.
     */
    public enum TyrianMusic
    {
        MENU( "music/menu.ogg" ),
        LEVEL( "music/level.ogg" );

        private final String fileName;

        private TyrianMusic(String fileName ){
            this.fileName = fileName;
        }

        public String getFileName(){
            return fileName;
        }
    }
    
    private Music musicBeingPlayed;
	private float volume = 1f;
	private boolean enabled = true;
	
	public MusicManager(){}
	
	public void play(){
		
	}
	
	public void stop(){
		
	}
	
	public void setVolume(float volume){
		
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
		
		if( ! enabled )
			stop();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	
}
