package com.santamaria.flappykek.tools;

import com.badlogic.gdx.Gdx;

/**
 * Created by Nicolò Santamaria on 09/10/17.
 */

public class GameButton {
    private int x;
    private int y;
    private int width;
    private int height;

    private static int screenWidth = Gdx.graphics.getWidth();
    private static int screenHeight = Gdx.graphics.getHeight();


    public GameButton (int x, int y, int buttonWidth, int buttonHeight, float viewpoerWidth, float viewportHeight) {
        this.x = (int)((x * screenWidth) / viewpoerWidth);
        this.y = screenHeight - (int)(y * (screenHeight / viewportHeight));

        width = (int)(buttonWidth * (screenWidth / viewpoerWidth));
        height = (int)(buttonHeight * (screenHeight / viewportHeight));
    }

    public boolean isPressed (int inputX, int inputY) {
        return (inputX >= x && inputX <= (x + width)) && (inputY <= y && inputY >= (y - height));
    }

//    @Deprecated
//    public int[] debugButton () {
//        int[] findProblem = {x, y, width, height};
//        return findProblem;
//    }
}
