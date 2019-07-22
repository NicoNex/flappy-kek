package com.santamaria.flappykek.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.santamaria.flappykek.sprites.Background;
import com.santamaria.flappykek.sprites.Frog;
import com.santamaria.flappykek.sprites.Ground;
import com.santamaria.flappykek.sprites.Obstacle;

/**
 * Created by Nicolò Santamaria on 13/12/17.
 */

public class RenderEngine implements Runnable {

    private SpriteBatch sb;
    private OrthographicCamera camera;

    private Texture sky;
    private Array<Background> pyramids;
    private Array<Obstacle> columns;
    private Array<Ground> grounds;
    private ParticleEffect particleEffect;
    private Frog pepe;
    private Text text;

    public RenderEngine (OrthographicCamera camera) {
        this.camera = camera;
    }

    public void update (SpriteBatch sb, Texture sky, Array<Background> pyramids, Array<Obstacle> columns, Array<Ground> grounds, ParticleEffect particleEffect, Frog pepe, Text text) {
        this.sb = sb;
        this.sky = sky;
        this.pyramids = pyramids;
        this.columns = columns;
        this.grounds = grounds;
        this.particleEffect = particleEffect;
        this.pepe = pepe;
        this.text = text;


    }

    @Override
    public void run() {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(sky, camera.position.x - (camera.viewportWidth/2), 0, camera.viewportWidth, camera.viewportHeight);

        for (Background pyramid : pyramids)
            sb.draw(pyramid.getTexture(), pyramid.getPosition().x, pyramid.getPosition().y, camera.viewportWidth, camera.viewportHeight);

        for (Obstacle column : columns) {
            if (column.getPositionBottom().x <= camera.viewportWidth + camera.position.x) {
                sb.draw(column.getTexture(), column.getPositionTop().x, column.getPositionTop().y, column.getColumnDimensions()[0], column.getColumnDimensions()[1]);
                sb.draw(column.getTexture(), column.getPositionBottom().x, column.getPositionBottom().y, column.getColumnDimensions()[0], column.getColumnDimensions()[1]);
            }
        }

        for (Ground ground : grounds)
            sb.draw(ground.getTexture(), ground.getPosition().x, 0, camera.viewportWidth, camera.viewportHeight/15);

        particleEffect.draw(sb);
        sb.draw(pepe.getTexture(), pepe.getPosition().x, pepe.getPosition().y, pepe.getDimensions()[0], pepe.getDimensions()[1]);
        text.getFont().draw(sb, text.getLayout(), camera.position.x - text.getLayout().width/2, camera.viewportHeight-110);
        sb.end();
    }
}
