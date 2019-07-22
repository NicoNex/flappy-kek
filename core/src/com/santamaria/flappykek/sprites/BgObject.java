package com.santamaria.flappykek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by speedking on 21/11/17.
 */

public class BgObject {
    private static Texture object;
    private Vector2 position;

    private int speed;
    private static final int width = 540;
//    private static final int cameraHeight = 960;
    private Random random;

    private static String[] pyramids = {"bg_pyramid_01.png", "bg_pyramid_02.png"};

    public BgObject (int speed) {
        random = new Random();
        object = new Texture(pyramids[random.nextInt(1)]);
        object.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        this.speed = speed;

//        width = (cameraHeight * object.getWidth()) / object.getHeight();

        position = new Vector2(random.nextInt(1080), 0);

    }

    private void reposition () {
        position.x += random.nextInt(1080) + 540*2;
    }

    public void update (float dt, float shift) {
        position.add(speed * dt, 0);

        if (position.x + width <= shift)
            reposition();
    }

    public Vector2 getPosition () {
        return position;
    }

    public Texture getTexture () {
        return object;
    }

    public int getWidth () {
        return width;
    }

    public void dispose () {
        object.dispose();
    }
}
