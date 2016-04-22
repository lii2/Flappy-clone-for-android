package com.lii2.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lii2.game.FlappyDemo;

/**
 * Created by Lii2 on 2016/4/20.
 */
public class MenuState extends State{
    private Texture backgroundTexture, playBtn;
    BitmapFont bitmapFont;
    int highScore;
    Preferences prefs = Gdx.app.getPreferences("My Preferences");

    public MenuState(GameStateManager gsm) {
        super(gsm);

        bitmapFont = new BitmapFont();
        bitmapFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        highScore = prefs.getInteger("highScore");

        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);

        backgroundTexture = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));//no need to dispose because gsm handles it when it's being popped off
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        //we start drawing images from the bottom left corner
        spriteBatch.draw(backgroundTexture, 0,0);

        //need to offset the width of the play button
        spriteBatch.draw(playBtn, (cam.position.x) - (playBtn.getWidth()/2),
                cam.position.y);

        bitmapFont.draw(spriteBatch, String.format("High Score: %d", highScore),
                cam.position.x - 10, cam.position.y + cam.viewportHeight / 2);


        spriteBatch.end();
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        playBtn.dispose();
        System.out.println("MenuState disposed");
    }
}
