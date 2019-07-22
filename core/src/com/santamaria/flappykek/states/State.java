package com.santamaria.flappykek.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Nicolò Santamaria on 28/01/17.
 */

public abstract class State {
    protected OrthographicCamera camera;
    protected GameStateManager gsm;




    public State (GameStateManager gsm) {
        this.gsm = gsm;
        camera = new OrthographicCamera();

    }

    protected abstract void handleInput();
    protected abstract void update (float deltaTime);
    public abstract void render (SpriteBatch sb);
    public abstract void dispose();
}
