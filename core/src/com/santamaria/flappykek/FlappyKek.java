package com.santamaria.flappykek;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.santamaria.flappykek.states.GameStateManager;
import com.santamaria.flappykek.states.SplashScreen;


public class FlappyKek extends ApplicationAdapter {
	private GameStateManager gsm;
	private SpriteBatch batch;

	public static final int displayWidth = 1080;
	public static final int displayHeight = 1920;

	public static Music music;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(0, 0, 0, 1);

		music = Gdx.audio.newMusic(Gdx.files.internal("ramses.mp3"));
        music.setLooping(true);

        gsm.push(new SplashScreen(gsm));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}

