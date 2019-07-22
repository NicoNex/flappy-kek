package com.santamaria.flappykek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.santamaria.flappykek.FlappyKek;
import com.santamaria.flappykek.tools.GameButton;

/**
 * Created by speedking on 20/12/17.
 */

public class CreditsState extends State {
    private static Texture background;
    private static Texture overlay;
    private static Texture exitTexture;

    private static GameButton exitButton;

    public CreditsState (GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, FlappyKek.displayWidth / 2, FlappyKek.displayHeight / 2);

        background = new Texture("customize_bg.jpg");
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        exitTexture = new Texture("exit.png");
        exitTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        overlay = new Texture("credits.png");
        overlay.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        exitButton = new GameButton(470, 0, 70, 70, camera.viewportWidth, camera.viewportHeight);

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (exitButton.isPressed(Gdx.input.getX(), Gdx.input.getY())) {
                Gdx.input.vibrate(80);
//                gsm.set(new CustomState(gsm, score, bestScore, newBest, totalScore));
                gsm.pop();
            }
        }

    }

    @Override
    protected void update(float deltaTime) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        sb.draw(overlay, 0, 0, camera.viewportWidth, camera.viewportHeight);
        sb.draw(exitTexture, 470, 0, 70, 70);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        exitTexture.dispose();
        overlay.dispose();
    }
}
