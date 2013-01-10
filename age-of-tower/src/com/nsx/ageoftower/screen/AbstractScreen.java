package com.nsx.ageoftower.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nsx.ageoftower.AgeOfTower;

public abstract class AbstractScreen implements Screen {
	// the fixed viewport dimensions (ratio: 1.6)
    public static final int GAME_VIEWPORT_WIDTH = Gdx.app.getGraphics().getWidth();
    public static final int GAME_VIEWPORT_HEIGHT = Gdx.app.getGraphics().getHeight();
    public static final int MENU_VIEWPORT_WIDTH = 800, MENU_VIEWPORT_HEIGHT = 400;
    
    private BitmapFont font;
	protected SpriteBatch batch;
	protected AgeOfTower _mGame;
	protected Stage _mStage;
	protected Skin _mSkin;
	private Table _mTable;
	private TextureAtlas atlas;
	
	public AbstractScreen(AgeOfTower space) {
		// TODO Auto-generated constructor stub
        this._mGame = space;
        int width = ( isGameScreen() ? GAME_VIEWPORT_WIDTH : MENU_VIEWPORT_WIDTH );
        int height = ( isGameScreen() ? GAME_VIEWPORT_HEIGHT : MENU_VIEWPORT_HEIGHT );
        this._mStage = new Stage( width, height, true ); 	
	}

	protected boolean isGameScreen(){
        return false;
    }
	
	@Override
	public void render(float delta) {
		// the following code clears the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        
        _mStage.act( delta );
        _mStage.draw();
	}

	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		// resize the stage
        _mStage.setViewport( width, height, true );
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
        Gdx.app.log( AgeOfTower.LOG, "Showing com.nsx.ageoftower.screen: " + getName() );
        
        // set the stage as the input processor
        Gdx.input.setInputProcessor(_mStage);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
		_mStage.dispose();
	}
	
	protected Skin getSkin()
    {
        if( _mSkin == null ) {
            FileHandle skinFile = Gdx.files.internal( "skin/uiskin.json" );
            _mSkin = new Skin( skinFile );
        }
        return _mSkin;
    }

    protected Table getTable()
    {
        if( _mTable == null ) {
            _mTable = new Table( getSkin() );
            _mTable.setFillParent( true );
           // if( Space.DEV_MODE ) {
           //     _mTable.debug();
           // }
            _mStage.addActor( _mTable );
        }
        return _mTable;
    }

    protected String getName(){
        return getClass().getSimpleName();
    }
    
    public BitmapFont getFont()
    {
        if( font == null ) {
            font = new BitmapFont();
        }
        return font;
    }
    
    public SpriteBatch getBatch()
    {
        if( batch == null ) {
            batch = new SpriteBatch();
        }
        return batch;
    }
    
    public TextureAtlas getAtlas()
    {
        if( atlas == null ) {
            atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages.atlas" ) );
        }
        return atlas;
    }
    
    public Stage getStage(){
    	return _mStage;
    }

	public void render() {
		// TODO Auto-generated method stub
		
	}
}
