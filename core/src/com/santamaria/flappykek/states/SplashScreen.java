package com.santamaria.flappykek.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.santamaria.flappykek.FlappyKek;
import com.santamaria.flappykek.tools.CheatDetect;

/**
 * Created by Nicolò Santamaria on 04/02/17.
 */

public class SplashScreen extends State {
    private static Texture splash;
    private static final byte delay = 2;
    private float start = 0;
    private CheatDetect cd;


    public SplashScreen (GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, FlappyKek.displayWidth/2, FlappyKek.displayHeight/2);

        splash = new Texture("splashscreen.jpg");
        splash.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        cd = new CheatDetect();
        Thread thread = new Thread(cd);
        thread.start();
    }


    @Override
    protected void handleInput () {}

    @Override
    protected void update (float dt) {
        start += dt;
        if (start >= delay && cd.hasFinished)
            gsm.set(new PlayState(gsm));
    }

    @Override
    public void render (SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(splash, 0, 0, camera.viewportWidth, camera.viewportHeight);
        sb.end();
    }

    @Override
    public void dispose () {
        splash.dispose();
    }
}
