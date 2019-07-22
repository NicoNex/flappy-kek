package com.santamaria.flappykek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Base64Coder;
import com.santamaria.flappykek.FlappyKek;
import com.santamaria.flappykek.sprites.Background;
import com.santamaria.flappykek.sprites.Frog;
import com.santamaria.flappykek.sprites.Ground;
import com.santamaria.flappykek.sprites.Obstacle;
import com.santamaria.flappykek.tools.EasterEgg;
import com.santamaria.flappykek.tools.Text;
import com.santamaria.flappykek.tools.Checksum;


/**
 * Created by Nicolò Santamaria on 29/01/17.
 */

public class PlayState extends State {
    private static final int COLUMNS_SPACING = 390;
    private static final byte COLUMNS_NUMBER = 2;

    private int score = 0;
    private int savedScore;

    private boolean collided = false;
    private boolean tutorial = true;
    private boolean newBest = false;
    private static boolean canPlay;
    private boolean saved = false;

    private static final float delay = 1;
    private float counter = 0;
    private float preventFpsDrop = 0;
    private float volume;

//    private static Preferences scoreFile = Gdx.app.getPreferences("Scores");
    private static Preferences data = Gdx.app.getPreferences("data");

    private static Frog pepe;
    private static Sound frogSound;
    private static Sound hitSound;
    private static Sound scoreSound;

    private Text text;

    private Array<Obstacle> columns;
    private Array<Ground> grounds;
    private Array<Background> backgrounds;
//    private Array<Background> pyramids;

    private static ParticleEffect particleEffect;
    private static Checksum cs;

    public PlayState (GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, FlappyKek.displayWidth / 2, FlappyKek.displayHeight / 2);

        cs = new Checksum();

        pepe = new Frog(camera.viewportWidth / 4, camera.viewportHeight + 10);
        text = new Text(55, "Tap anywhere to jump!", "0123456789Tapnywhertojum!");
//        background = new Texture("1.jpg");
//        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        columns = new Array<Obstacle>();
        grounds = new Array<Ground>();
        backgrounds = new Array<Background>();
//        pyramids = new Array<Background>();

        for (int g = 0; g < 2; g++)
            grounds.add(new Ground(g * (int) camera.viewportWidth, 0, "ground.png", camera.viewportWidth, camera.viewportHeight));

        for (int i = 0; i < 2; i++)
            backgrounds.add(new Background("background.png", 210, i));


        for (int i = 0; i < 2; i++)
//            pyramids.add(new Background("leyer_2Obelisco.png", 180, i));

        canPlay = data.getBoolean("sound");

        if (canPlay) {
            hitSound = Gdx.audio.newSound(Gdx.files.internal("punch.ogg"));
            frogSound = Gdx.audio.newSound(Gdx.files.internal(("frog.ogg")));
            scoreSound = Gdx.audio.newSound(Gdx.files.internal("collect_point.ogg"));
        }

        EasterEgg egg = new EasterEgg();
        String trailFile = "trail_04.p";

        if (egg.isXmas())
            trailFile = "xmas.p";

        // default trail_04.p
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particles/"+trailFile), Gdx.files.internal("particles"));
        particleEffect.start();

        savedScore = Integer.parseInt(Base64Coder.decodeString(data.getString("score")));

        volume = data.getFloat("sfx_volume");
    }

    private void placeColumns (float shift) {
        int initial_gap = (int)camera.viewportWidth + (int)shift;
        for (int i = 1; i <= COLUMNS_NUMBER; i++) {
            columns.add(new Obstacle(initial_gap + i * COLUMNS_SPACING, (int)camera.viewportHeight, COLUMNS_SPACING, COLUMNS_NUMBER));
        }
    }


    private void setCollided () {
        if (canPlay)
            hitSound.play(2*volume);
        Gdx.input.vibrate(80);
//        particleEffect.allowCompletion();
        collided = true;
    }

    private void saveData () {
        String encodedScore = Base64Coder.encodeString(Integer.toString(score));
        int pColumns = data.getInteger("passed_columns") + score;
        data.putInteger("passed_columns", pColumns);
        data.putString("pcolumns_sig", cs.genKekHash(pColumns, true));

        if (score > savedScore) {
            data.putString("score", encodedScore);
            data.putString("hash", cs.genKekHash(encodedScore));
            newBest = true;
        }
        data.flush();
        saved = true;
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched() && !collided) {
            if (tutorial)
                placeColumns(camera.position.x);

            if (canPlay)
                frogSound.play(volume);
            tutorial = false;
            pepe.jump(camera.viewportHeight);
        }
    }


    @Override
    protected void update(float deltaTime) {

        if (preventFpsDrop > 0.3)
            handleInput();

        pepe.update(deltaTime, camera.viewportHeight, collided);
        camera.position.set(pepe.getPosition().x + 135, camera.viewportHeight/2, 0);
        particleEffect.setPosition(pepe.getPosition().x + pepe.getDimensions()[0]/4, pepe.getPosition().y + pepe.getDimensions()[1]/2);
        particleEffect.update(deltaTime);

        if (!collided) {
            float shift = camera.position.x - (camera.viewportWidth/2);
            for (Ground ground : grounds) {
                if (ground.collides(pepe.getFrogBox())) {
                    setCollided();
                    break;
                }
                ground.update(shift, camera.viewportWidth);
            }

            for (Background bg : backgrounds)
                bg.update(deltaTime, shift);

//            for (Background prm : pyramids)
//                prm.update(deltaTime, shift);

        }

        // TODO include this into the if above
        if (!collided && !tutorial) {
            for (Obstacle column : columns) {
//
                if ((column.getPositionTop().x < pepe.getPosition().x) && column.active && !Float.isNaN(pepe.getPosition().y)) {
                    if (canPlay)
                        scoreSound.play(volume);

                    score++;
                    column.active = false;
                }

                if (column.collides(pepe.getFrogBox()) || Float.isNaN(pepe.getPosition().y)) {
                    setCollided();
                    break;
                }
                column.update(camera.position.x - (camera.viewportWidth/2));
            }
            text.setText(Integer.toString(score));
        }

        else if (collided || Float.isNaN(pepe.getPosition().y)) {
            if (pepe.getPosition().y < camera.viewportHeight/15)
                particleEffect.allowCompletion();

            if (!saved)
                saveData();

            counter += deltaTime;
            if (counter >= delay + 0.5 || Float.isNaN(pepe.getPosition().y)) {
                gsm.set(new MenuState(gsm, score, Integer.parseInt(Base64Coder.decodeString(data.getString("score"))), newBest));
            }
        }

        else if (tutorial) {
            preventFpsDrop += deltaTime;
            if (pepe.getPosition().y <= camera.viewportHeight/2 - 128)
                pepe.jump(camera.viewportHeight);
        }
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
//        sb.draw(sky, camera.position.x - (camera.viewportWidth/2), 0, camera.viewportWidth, camera.viewportHeight);

        for (Background bg : backgrounds)
            sb.draw(bg.getTexture(), bg.getPosition().x, bg.getPosition().y, bg.getWidth(), camera.viewportHeight);

//        for (Background prm : pyramids)
//            sb.draw(prm.getTexture(), prm.getPosition().x, prm.getPosition().y, prm.getWidth(), camera.viewportHeight);

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

    @Override
    public void dispose() {
        pepe.dispose();

        if (canPlay) {
            hitSound.dispose();
            frogSound.dispose();
            scoreSound.dispose();
        }
        particleEffect.dispose();
        text.dispose();

        for (Obstacle column : columns)
            column.dispose();

        for (Ground ground : grounds)
            ground.dispose();

        for (Background bg : backgrounds)
            bg.dispose();

//        for (Background prm : pyramids)
//            prm.dispose();
    }
}
