package com.lii2.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Lii2 on 2016/4/20.
 */
public class Bird {
    public static final int GRAVITY = -15;
    public static final int JUMP_SPEED = 250;

    private Vector3 position;
    private Vector3 velocity;

    private Rectangle bounds;

    private Animation birdAnimation;
    Texture animationTexture;

    private Sound flap;

    public Bird(int x, int y) {
        animationTexture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(animationTexture), 3, 0.5f);

        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        bounds = new Rectangle(x, y, animationTexture.getWidth()/3, animationTexture.getHeight());

        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    public Vector3 getPosition() {

        return position;
    }

    public void update(float deltaTime) {
        birdAnimation.update(deltaTime);
        if (position.y >= 0) {
            velocity.add(0, GRAVITY, 0);
            position.add(velocity.x*deltaTime, velocity.y*deltaTime, 0);

        }else{
            position.y = 0;
        }

        bounds.setPosition(position.x, position.y);
    }

    public void jump() {
        velocity.y = JUMP_SPEED;
        flap.play();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        flap.dispose();
        animationTexture.dispose();
    }
}
