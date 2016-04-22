package com.lii2.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lii2.game.states.GameStateManager;
import com.lii2.game.states.MenuState;

public class FlappyDemo extends ApplicationAdapter {
	//need global variables for width and height of screen
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    public static final String TITLE= "Flappy Bird";

    private GameStateManager gsm;
	private SpriteBatch batch; //only create one

    private Music music;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        gsm = new GameStateManager();
        music = Gdx.audio.newMusic(Gdx.files.internal("A Night Of Dizzy Spells.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        gsm.push(new MenuState(gsm));
    }

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);

	}

    @Override
    public void dispose() {
        super.dispose();
        music.dispose();
    }

}
