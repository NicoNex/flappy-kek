package com.santamaria.flappykek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.santamaria.flappykek.FlappyKek;
import com.santamaria.flappykek.tools.GameButton;
import com.santamaria.flappykek.tools.Text;
import com.badlogic.gdx.Preferences;

/**
 * Created by Nicolò Santamaria on 28/01/17.
 */

// TODO: uncomment the lines related to customize variable
public class MenuState extends State {
    private static Texture background;
//    private static Texture customize;
    private static Texture settings;

    private Text currentScore;
    private Text bscore;
    private Text tsText;

    private static Preferences data = Gdx.app.getPreferences("data");
    private static GameButton settingsButton;

    private int score;
    private int bestScore;
    private int totalScore;
    private boolean newBest;

// TODO: for development uncomment the noTutorial related lines

    public MenuState(GameStateManager gsm, int score, int bestScore, boolean newBest) {
        super(gsm);
        camera.setToOrtho(false, FlappyKek.displayWidth / 2, FlappyKek.displayHeight / 2);

        String bestText;
        if (newBest)
            bestText = "NEW BEST!";
        else
            bestText = "BEST: " + Integer.toString(bestScore);

        totalScore = data.getInteger("passed_columns");

        currentScore = new Text(80, Integer.toString(score), "0123456789");
        bscore = new Text(40, bestText, "0123456789NWBEST!:");
        tsText = new Text(40, "Total Score: " + Integer.toString(totalScore), "0123456789TtoalScre:");

        background = new Texture("scoreboard.jpg");
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

//        if (!noTutorial) {
//            customize = new Texture("customize.png");
//            customize.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//        }

        settings = new Texture("gear_256.png");
        settings.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        this.score = score;
        this.bestScore = bestScore;
        this.newBest = newBest;

        settingsButton = new GameButton(470, 0, 70, 70, camera.viewportWidth, camera.viewportHeight);

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {


//            if (!noTutorial) {
//                data.putBoolean("tutorial", true);
//                data.flush();
//            }

            if (settingsButton.isPressed(Gdx.input.getX(), Gdx.input.getY())) {
                Gdx.input.vibrate(80);
                gsm.push(new CustomState(gsm, totalScore));
            }

            else
                gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - camera.viewportWidth/2, 0, camera.viewportWidth, camera.viewportHeight);
        currentScore.getFont().draw(sb, currentScore.getLayout(), camera.viewportWidth/2 - currentScore.getLayout().width/2, camera.viewportHeight - 175);
        bscore.getFont().draw(sb, bscore.getLayout(), camera.viewportWidth/2 - bscore.getLayout().width/2, camera.viewportHeight - 260);
        tsText.getFont().draw(sb, tsText.getLayout(), camera.viewportWidth/2 - tsText.getLayout().width/2, camera.viewportHeight - 330);

        sb.draw(settings, camera.viewportWidth-70, 0, 70, 70);

//        if (!noTutorial)
//            sb.draw(customize, 0, 0, camera.viewportWidth, camera.viewportHeight);

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        currentScore.dispose();
        bscore.dispose();
        tsText.dispose();
        settings.dispose();

//        if (!noTutorial)
//            customize.dispose();
    }
}
