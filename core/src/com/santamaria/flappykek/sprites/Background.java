package com.santamaria.flappykek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by speedking on 26/11/17.
 */

public class Background {
    private Texture bg;
    private Vector2 position;

    private int speed;
    private int width;
    private static final int cameraHeight = 960;

    public Background (String texture, int speed, int multiplier) {
        bg = new Texture(texture);
        bg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        this.speed = speed;

        width = (cameraHeight * bg.getWidth()) / bg.getHeight();

        position = new Vector2(multiplier*width, 0);
        System.out.println(multiplier*width);
    }

    public Background (String texture, int speed, int multiplier, int width) {
        this.width = width;
        this.speed = speed;

        bg = new Texture(texture);
        bg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        position = new Vector2(multiplier*width, 0);
    }

    private void reposition () {
        position.x += width * 2;
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
        return bg;
    }

    public int getWidth () {
        return width;
    }

    public void dispose () {
        bg.dispose();
    }
}
