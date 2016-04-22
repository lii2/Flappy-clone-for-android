package com.lii2.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.lii2.game.FlappyDemo;
import com.lii2.game.sprites.Bird;
import com.lii2.game.sprites.Tube;

/**
 * Created by Lii2 on 2016/4/20.
 */
public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_HEIGHT = -40;

    private Bird bird;
    private Texture bg, ground;
    Vector2 groundPosition1, groundPosition2;

    private int playerScore;
    private String scoreName;
    BitmapFont bitmapFont;

    private Array<Tube> tubes; //need to dispose of the tubes

    public PlayState(GameStateManager gsm) {
        super(gsm);

        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);

        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPosition1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_HEIGHT);
        groundPosition2 = new Vector2(cam.position.x - cam.viewportWidth / 2 + ground.getWidth(), GROUND_HEIGHT);

        bird = new Bird(50, 250);

        tubes = new Array<Tube>();

        //needs to be 1 because i can't equal to zero, and therefore the boolean must be <=
        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }

        playerScore = 0;
        scoreName = "0";

        bitmapFont = new BitmapFont();
        bitmapFont.getData().setScale(2f);
        bitmapFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }

    }

    @Override
    public void update(float deltaTime) {
        handleInput();

        //update cam position
        cam.position.x = bird.getPosition().x + 80;

        bird.update(deltaTime);//moves bird

        //handle repositioning of tubes
        for (int i = 0; i < TUBE_COUNT; i++) {
            Tube tube = tubes.get(i);

            tube.update(deltaTime);

            if (cam.position.x - (cam.viewportWidth / 2) >
                    tube.getPosTopTube().x + tube.getTopTube().getWidth()) {

                tube.reposition(tube.getPosTopTube().x +
                        ((tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));

                playerScore++;
                scoreName = String.format("%d", playerScore);

            }

            if (tube.collides(bird.getBounds())) {
                checkHighScore();
                gsm.set(new MenuState(gsm));
            }


        }

        cam.update();
        updateGround(deltaTime);

        if (bird.getPosition().y <= ground.getHeight() + GROUND_HEIGHT) {
            checkHighScore();
            gsm.set(new MenuState(gsm));
        }

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();

        spriteBatch.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        spriteBatch.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

        for (Tube tube : tubes) {
            spriteBatch.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            spriteBatch.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        spriteBatch.draw(ground, groundPosition1.x, groundPosition1.y);
        spriteBatch.draw(ground, groundPosition2.x, groundPosition2.y);

        bitmapFont.draw(spriteBatch, scoreName,
                cam.position.x - 10, cam.position.y + cam.viewportHeight / 2);

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
        }
        ground.dispose();

        System.out.println("Playstate disposed");
    }

    public void updateGround(float deltaTime) {
        groundPosition1.x = groundPosition1.x - 100 * deltaTime;
        groundPosition2.x = groundPosition2.x - 100 * deltaTime;

        if (cam.position.x - (cam.viewportWidth / 2) >
                groundPosition1.x + ground.getWidth()) {
            groundPosition1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) >
                groundPosition2.x + ground.getWidth()) {
            groundPosition2.add(ground.getWidth() * 2, 0);
        }
    }

    public void checkHighScore() {
        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        int HighScore = prefs.getInteger("highScore");
        if (playerScore > HighScore) {
            prefs.putInteger("highScore", playerScore);
            prefs.flush();
        }
    }

}
