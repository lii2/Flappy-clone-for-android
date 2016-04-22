package com.lii2.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Lii2 on 2016/4/20.
 *
 * Never to be instantiated
 */
public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;

    protected State (GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput();

    //delta time is the difference between one frame rendered and another
    public abstract void update(float deltaTime);

    //a spritebatch is just a container for everything you need to render,
    //so it basically just renders everything in one big blob
    public abstract void render(SpriteBatch spriteBatch);

    public abstract void dispose();

}
