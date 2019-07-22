package com.santamaria.flappykek.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Nicolò Santamaria on 29/01/17.
 */

public class Obstacle {
    private static final int COLUMN_GAP = 210;
    private static final int LOWEST_POINT = 150;
    private static final int columnWidth = 120;
    private static final int columnHeight = 720;
    private static final int[] columnDimensions = {columnWidth, columnHeight};
    private static int displayHeight;
    public boolean active = true;

    private Rectangle boxTop, boxBottom;

    private Vector2 positionTop, positionBottom;
    private Random randomHeight;

    private static Texture column;
    private static int columnSpacing;
    private static byte columnsNumber;

    public Obstacle (int x ,int dh, int cs, byte cn) {
        randomHeight = new Random();

        column = new Texture("columns.png");
        column.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        displayHeight = dh;

        positionTop = new Vector2(x, randomHeight.nextInt(displayHeight-COLUMN_GAP-LOWEST_POINT-125) + COLUMN_GAP+LOWEST_POINT);
        positionBottom = new Vector2(x, positionTop.y - COLUMN_GAP - columnHeight);

        boxTop = new Rectangle(positionTop.x, positionTop.y, columnWidth, columnHeight);
        boxBottom = new Rectangle(positionBottom.x, positionBottom.y, columnWidth, columnHeight);
        columnSpacing = cs;
        columnsNumber = cn;
    }

    private void reposition (float x, int displayHeight) {
//        positionTop = new Vector2(x, randomHeight.nextInt(displayHeight-COLUMN_GAP-LOWEST_POINT-250) + COLUMN_GAP+LOWEST_POINT);
//        positionBottom = new Vector2(x, positionTop.y - COLUMN_GAP - columnHeight);
        positionTop.x += x;
        positionTop.y = randomHeight.nextInt(displayHeight-COLUMN_GAP-LOWEST_POINT-250) + COLUMN_GAP+LOWEST_POINT;
        positionBottom.x = positionTop.x;
        positionBottom.y = positionTop.y - COLUMN_GAP - columnHeight;
        boxTop.setPosition(positionTop.x, positionTop.y);
        boxBottom.setPosition(positionBottom.x, positionBottom.y);
    }

    public void update (float shift) {
        if (positionTop.x <= shift - columnWidth) {
            reposition(2 * columnSpacing, displayHeight);
            active = true;
        }
    }

    public Vector2 getPositionTop() {
        return positionTop;
    }

    public Vector2 getPositionBottom() {
        return positionBottom;
    }

    public Texture getTexture() {
        return column;
    }

    public int[] getColumnDimensions() {
        return columnDimensions;
    }

    public boolean collides (Rectangle frog) {
        return frog.overlaps(boxTop) || frog.overlaps(boxBottom);
    }

    public void dispose () {
        column.dispose();
    }
}
