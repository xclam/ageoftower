package com.nsx.ageoftower.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

/**
 * A service that manages the background music.
 * Only one music may be playing at a given time.
 */
public class MusicManager implements Disposable {
	
	/**
     * The available music files.
     */
    public enum AotMusic
    {
        MENU( "music/menu.ogg" ),
        LEVEL( "music/level.mp3" );

        private final String fileName;

        private AotMusic(String fileName ){
            this.fileName = fileName;
        }

        public String getFileName(){
            return fileName;
        }
    }
    
    private Music musicBeingPlayed;
	private float volume = 1f;
	private boolean enabled = true;
	
	public boolean isEnabled() {
		return enabled;
	}

	public MusicManager(){}
	
	public void play(AotMusic music){
		 // check if the music is enabled
        if( ! enabled ) return;

        // start streaming the new music
        FileHandle musicFile = Gdx.files.internal( music.getFileName() );
        musicBeingPlayed = Gdx.audio.newMusic( musicFile );
        musicBeingPlayed.setVolume( volume );
        musicBeingPlayed.setLooping( true );
        musicBeingPlayed.play();
	}
	
	public void stop(){
		if( musicBeingPlayed != null ) {
            musicBeingPlayed.stop();
            musicBeingPlayed.dispose();
        }
	}
	
	public void setVolume(float volume){
		this.volume = volume;
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

	public void mute(boolean mute){
		if (mute)
			musicBeingPlayed.setVolume( 0 );
		else
			musicBeingPlayed.setVolume( volume );
	}
	
}
