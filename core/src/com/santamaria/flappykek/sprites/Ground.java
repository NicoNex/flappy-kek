package com.santamaria.flappykek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Nicolò Santamaria on 26/02/17.
 */

public class Ground {
    private Vector2 position;
    private static Texture ground;
    private static Rectangle groundBox;

    public Ground (int x, int y, String texture ,float screenWidth, float screenHeight) {
        ground = new Texture(texture);
        ground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        groundBox = new Rectangle(0, 0, screenWidth, screenHeight/15);
        position = new Vector2(x, y);
    }

    private void reposition () {
//        position.x = groundBox.getWidth()*2 + shift;
        position.x += groundBox.getWidth()*2;
    }

    public void update (float shift, float cameraWidth) {
        groundBox.x = shift - cameraWidth/2;

        if (position.x <= shift - groundBox.getWidth())
//                position.x = groundBox.getWidth()*2 + position.x;
            reposition();

    }

    public Vector2 getPosition () {
        return position;
    }

    public Texture getTexture () {
        return ground;
    }

    public boolean collides (Rectangle frog) {
        return frog.overlaps(groundBox);
    }

    public void dispose () {
        ground.dispose();
    }
}
