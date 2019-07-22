package com.santamaria.flappykek.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Base64Coder;
import com.santamaria.flappykek.tools.EasterEgg;

/**
 * Created by Nicolò Santamaria on 29/01/17.
 */

public class Frog {
    private static Preferences data = Gdx.app.getPreferences("data");
    private static final byte frogWidth = 64;
    private static final byte frogHeight = 52;
    private static final byte[] dimensions = {frogWidth, frogHeight};
    private static final byte GRAVITY = -49;
    private static final short SPEEDEX = 300;

    private Vector2 position;
    private  Vector2 velocity;

    private Rectangle frogBox;

    private static Texture frog;

    private static String[] pepes = {
            "pepe.png",
            "pepe_custom_01.png",
            "pepe_custom_02.png",
            "pepe_custom_04.png",
            "pepe_custom_03.png",
            "pepe_custom_05.png",
            "pepe_custom_06.png",
            "pepe_custom_07.png",
            "pepe_custom_08.png",
            "pepe_custom_09.png"
    };

    public Frog (float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);

        EasterEgg egg = new EasterEgg();
        if (egg.isXmas())
            pepes[0] = "pepe_custom_xmas.png";

        frog = new Texture(pepes[Integer.parseInt(Base64Coder.decodeString(data.getString("froginuse")))]);
        frog.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        frogBox = new Rectangle(x, y, frogWidth, frogHeight);
    }

    public void update (float dt, float sh, boolean collided) {
        if (position.y > sh/15) {
            velocity.add(0, GRAVITY);
            velocity.scl(dt);
            if (!collided)
                position.add(SPEEDEX * dt, velocity.y);
            else
                position.add(0, velocity.y);

            velocity.scl(1 / dt);

        }
        frogBox.setPosition(position.x, position.y);
//        System.out.println(velocity);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getFrogBox () {
        return frogBox;
    }

    public Texture getTexture() {
        return frog;
    }

    public byte[] getDimensions () {
        return dimensions;
    }

    public void jump (float displayHeight) {
        if (position.y < displayHeight)
            velocity.y = 817;
    }

    public void dispose () {
        frog.dispose();
    }
}
