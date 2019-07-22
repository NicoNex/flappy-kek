package com.santamaria.flappykek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
//import java.lang.Math;

/**
 * Created by Nicolò Santamaria on 11/04/17.
 */

public class Skin {
    private int x;
    private static final int y = 255;
    private static final byte SPEED = 36;
//    private static final byte TOTAL_STEPS = 15;
//    public static byte step = 0;

//    public boolean isShowing;
    public boolean isSelected = false;
    public int unlockScore;
    public int frogNumber;

    private Texture skin;
    private Vector2 position;

    @Deprecated
    public Skin (int skinX, Texture pepe) {
        x = skinX;

        skin = pepe;
        skin.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        position = new Vector2(x, y);
    }

    public Skin (int skinX, Texture pepe, int unlockScore, int frogNumber) {
        x = skinX;

        skin = pepe;
        skin.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        position = new Vector2(x, y);

        this.unlockScore = unlockScore;
        this.frogNumber = frogNumber;
    }

    public void update (float dt, boolean isForward) {
        if (isForward)
            position.sub(SPEED, 0);

        else
            position.add(SPEED, 0);

//        step++;
//
//        if (step > TOTAL_STEPS)
//            step = 0;
    }

    public Texture getTexture() {
        return skin;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void dispose() {
        skin.dispose();
    }
}
