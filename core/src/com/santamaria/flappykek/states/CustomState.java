package com.santamaria.flappykek.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Base64Coder;
import com.santamaria.flappykek.FlappyKek;
import com.santamaria.flappykek.sprites.Skin;
import com.santamaria.flappykek.tools.Checksum;
import com.santamaria.flappykek.tools.GameButton;
import com.santamaria.flappykek.tools.Text;
import com.santamaria.flappykek.tools.EasterEgg;


/**
 * Created by Nicolò Santamaria on 02/03/17.
 */

public class CustomState extends State {
    private static Texture background;
    private static Texture exit;
    private static Texture arrows;
    private static Texture checkboxOn;
    private static Texture checkboxOff;
    private static Texture line;
    private static Texture horusEye;

    private static ParticleEffect particleEffect;
    private static Array<Skin> skins;
    private static Skin tmp;

    private static final int skinWidth = 288 - 37;
    private static final int skinHeight = 234 - 30;
    private static final byte xOffset = 10;
    private static final byte adOffset = 50;

    private static Text musicText;
    private static Text sfx;
    private static Text low;
    private static Text medium;
    private static Text high;
    private static Text skinsText;
    private static Text unlockText;
//    private static Text particlesText;

    private static String[] pepes = {
            "skin_00.png",
            "skin_01.png",
            "skin_02.png",
            "skin_04.png",
            "skin_03.png",
            "skin_05.png",
            "skin_06.png",
            "skin_07.png",
            "skin_08.png",
            "skin_09.png"
    };


    private int selectedFrogNumber;
    private int goalX;

    private byte multiplier = 0;

    private boolean isForward;
    private boolean buttonActive = true;

    private static Preferences data = Gdx.app.getPreferences("data");

    private static Checksum cs;

    private static GameButton leftArrow;
    private static GameButton rightArrow;
    private static GameButton frogButton;
    private static GameButton exitButton;
    private static GameButton musicButton;
    private static GameButton sfxButton;
    private static GameButton creditsButton;

    private static GameButton musicLow;
    private static GameButton musicMedium;
    private static GameButton musicHigh;

    private static GameButton sfxLow;
    private static GameButton sfxMedium;
    private static GameButton sfxHigh;

    private boolean canSfx;
    private boolean canMusic;

    private static final int sfxLineY = 612;
    private static final int musicLineY = 752;

    private int musicLineX;
    private int sfxLineX;
    private int totalScore;
//    private int musicValue;
//    private int sfxValue;
    private float musicVolume;
    private float sfxVolume;

    public CustomState (GameStateManager gsm, int totalScore) {
        super(gsm);
        camera.setToOrtho(false, FlappyKek.displayWidth / 2, FlappyKek.displayHeight / 2);

        background = new Texture("customize_bg.jpg");
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        exit = new Texture("exit.png");
        exit.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        arrows = new Texture("arrows.png");
        arrows.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        checkboxOn = new Texture("checkbox_on.png");
        checkboxOn.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        checkboxOff = new Texture("checkbox_off.png");
        checkboxOff.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        line = new Texture("line.png");
        line.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        horusEye = new Texture("eye_horus.png");
        horusEye.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        skins = new Array<Skin>();
        cs = new Checksum();

        selectedFrogNumber = Integer.parseInt(Base64Coder.decodeString(data.getString("froginuse")));

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particles/selection.p"), Gdx.files.internal("particles"));

        EasterEgg egg = new EasterEgg();

        if (egg.isXmas())
            pepes[0] = "skin_xmas.png";

        // TODO: remember to set the constant always back to 500, and the unlockScore back to "multiplier"
        multiplier = 0;
        for (String pepe : pepes) {
            if (multiplier*500 > totalScore)
                skins.add(new Skin(((int)camera.viewportWidth/2 - skinWidth/2) + (multiplier * (int)camera.viewportWidth), new Texture("pepe_locked.png"), multiplier*500, multiplier));

            else
                skins.add(new Skin(((int)camera.viewportWidth/2 - skinWidth/2) + (multiplier * (int)camera.viewportWidth), new Texture(pepe), multiplier*500, multiplier));

            if (multiplier == selectedFrogNumber) {
                skins.get(multiplier).isSelected = true;
                particleEffect.setPosition(skins.get(multiplier).getPosition().x + skinWidth/2, skins.get(multiplier).getPosition().y + skinHeight/2); // camera.viewportHeight/2
            }

            multiplier++;
        }

//        skins.get(selectedFrogNumber).isSelected = true;
        tmp = skins.get(0);
        goalX = (int)camera.viewportWidth/2 - skinWidth/2;

        rightArrow = new GameButton(0, 70, 320, 150, camera.viewportWidth, camera.viewportHeight);
        leftArrow = new GameButton(320, 70, 320, 150, camera.viewportWidth, camera.viewportHeight);
        frogButton = new GameButton(0, 255, 640, skinHeight, camera.viewportWidth, camera.viewportHeight);
        exitButton = new GameButton(470, 0, 70, 70, camera.viewportWidth, camera.viewportHeight);
        creditsButton = new GameButton(0, 0, 70, 70, camera.viewportWidth, camera.viewportHeight);
        musicButton = new GameButton(530-45, 860-40, 45, 45, camera.viewportWidth, camera.viewportHeight);
        sfxButton = new GameButton(530-45, 720-40, 45, 45, camera.viewportWidth, camera.viewportHeight);

        musicLow = new GameButton(0, 755, 180, 35, camera.viewportWidth, camera.viewportHeight);
        musicMedium = new GameButton(180, 755, 180, 35, camera.viewportWidth, camera.viewportHeight);
        musicHigh = new GameButton(360, 755, 180, 35, camera.viewportWidth, camera.viewportHeight);

        sfxLow = new GameButton(0, 615, 180, 35, camera.viewportWidth, camera.viewportHeight);
        sfxMedium = new GameButton(180, 615, 180, 35, camera.viewportWidth, camera.viewportHeight);
        sfxHigh = new GameButton(360, 615, 180, 35, camera.viewportWidth, camera.viewportHeight);


        musicText = new Text(45, "MUSIC", "MUSIC");
        sfx = new Text(45, "SFX", "SFX");
        skinsText = new Text(45, "SKINS", "SKIN");
//        particlesText = new Text(45, "PARTICLES", "PARTICLES");

        low = new Text(30, "LOW", "LOW");
        medium = new Text(30, "MIDDLE", "MIDLE");
        high = new Text(30, "HIGH", "HIG");

        unlockText = new Text(30, "UNLOCKED", "UNLOCKED/0123456789");

        canSfx = data.getBoolean("sound");
        canMusic = data.getBoolean("music");

        musicVolume = data.getFloat("music_volume");
        sfxVolume = data.getFloat("sfx_volume");

        musicLineX = 180 * ((int)(musicVolume * 10) - 1);
        sfxLineX = 180 * ((int)(sfxVolume * 10) - 1);

        this.totalScore = totalScore;


        particleEffect.start();
    }


    private void updateMusic () {
        if (canMusic && !FlappyKek.music.isPlaying())
            FlappyKek.music.play();

        else if (!canMusic && FlappyKek.music.isPlaying())
            FlappyKek.music.pause();
    }

    private void deselectFrogs () {
        for (Skin skin : skins) {
            if (skin.frogNumber != selectedFrogNumber)
                skin.isSelected = false;
        }
    }

    private void saveData () {
        data.putBoolean("sound", canSfx);
        data.putBoolean("music", canMusic);
        data.putFloat("music_volume", musicVolume);
        data.putFloat("sfx_volume", sfxVolume);
        data.flush();
    }

    @Override
    public void handleInput () {
        if (Gdx.input.justTouched()) {

            // TODO: add sfxButton and musicButton

            if (exitButton.isPressed(Gdx.input.getX(), Gdx.input.getY())) {
                saveData();
                Gdx.input.vibrate(80);
                gsm.pop();
            }

            // verso sinistra
            else if (leftArrow.isPressed(Gdx.input.getX(), Gdx.input.getY()) && buttonActive && goalX >= -((multiplier-2)*540)) {
                goalX -= 540;
//                System.out.println(goalX);
                buttonActive = false;
                isForward = true;
                Gdx.input.vibrate(80);
            }

            // verso destra
            else if (rightArrow.isPressed(Gdx.input.getX(), Gdx.input.getY()) && buttonActive && goalX <= 0) {
                goalX += 540;
//                System.out.println(goalX);
                buttonActive = false;
                isForward = false;
                Gdx.input.vibrate(80);
            }

            else if (musicButton.isPressed(Gdx.input.getX(), Gdx.input.getY())) {
                canMusic = !canMusic;
                updateMusic();
                Gdx.input.vibrate(80);
            }

            else if (sfxButton.isPressed(Gdx.input.getX(), Gdx.input.getY())) {
                canSfx = !canSfx;
                Gdx.input.vibrate(80);
            }


            // MUSIC VOLUME //
            else if (musicLow.isPressed(Gdx.input.getX(), Gdx.input.getY())) {
                musicLineX = 0;
                musicVolume = 0.1f;
                FlappyKek.music.setVolume(3*musicVolume);
                Gdx.input.vibrate(80);
            }

            else if (musicMedium.isPressed(Gdx.input.getX(), Gdx.input.getY())) {
                musicLineX = 180;
                musicVolume = 0.2f;
                FlappyKek.music.setVolume(3*musicVolume);
                Gdx.input.vibrate(80);
            }

            else if (musicHigh.isPressed(Gdx.input.getX(), Gdx.input.getY())) {
                musicLineX = 360;
                musicVolume = 0.3f;
                FlappyKek.music.setVolume(3*musicVolume);
                Gdx.input.vibrate(80);
            }


            // SFX VOLUME //
            else if (sfxLow.isPressed(Gdx.input.getX(), Gdx.input.getY())) {
                sfxLineX = 0;
                sfxVolume = 0.1f;
                Gdx.input.vibrate(80);
            }

            else if (sfxMedium.isPressed(Gdx.input.getX(), Gdx.input.getY())) {
                sfxLineX = 180;
                sfxVolume = 0.2f;
                Gdx.input.vibrate(80);
            }

            else if (sfxHigh.isPressed(Gdx.input.getX(), Gdx.input.getY())) {
                sfxLineX = 360;
                sfxVolume = 0.3f;
                Gdx.input.vibrate(80);
            }

            else if (creditsButton.isPressed(Gdx.input.getX(), Gdx.input.getY())) {
                Gdx.input.vibrate(80);
                gsm.push(new CreditsState(gsm));
            }


            // TODO: after testing revert back to: if (skin.getPosition().x > 0 && skin.getPosition().x < camera.viewportWidth && bestScore >= skin.unlockScore*11)
            // selects a frog
            else if (frogButton.isPressed(Gdx.input.getX(), Gdx.input.getY())) {
                for (Skin skin : skins) {
//                    skin.isSelected = false;
                        // DEBUG ONLY //
//                    if (skin.getPosition().x > 0 && skin.getPosition().x < camera.viewportWidth) {
                    if (skin.getPosition().x > 0 && skin.getPosition().x < camera.viewportWidth && totalScore >= skin.unlockScore) {
                        String chosenFrog = Base64Coder.encodeString(Integer.toString(skin.frogNumber));
                        data.putString("froginuse", chosenFrog);
                        data.putString("hashp", cs.genKekLordHash(chosenFrog));
                        data.flush();
                        skin.isSelected = true;
                        particleEffect.setPosition(skin.getPosition().x + skinWidth/2, skin.getPosition().y + skinHeight/2);
                        selectedFrogNumber = skin.frogNumber;
                        Gdx.input.vibrate(80);
//                        break;
                    }
                }
                deselectFrogs();
            }

        }
    }

    @Override
    public void update (float dt) {
        handleInput();

        particleEffect.update(dt);
        if (!buttonActive) {

            for (Skin skin : skins) {
                skin.update(dt, isForward);

                if (skin.isSelected) {
                    // TODO: add a feedback
                    particleEffect.setPosition(skin.getPosition().x + skinWidth/2, skin.getPosition().y + skinHeight/2);
                }

                if (skin.getPosition().x < camera.viewportWidth/2) {
                    if (totalScore >= skin.unlockScore)
                        unlockText.setText("UNLOCKED");

                    else
                        unlockText.setText(Integer.toString(totalScore) + "/" + Integer.toString(skin.unlockScore));
                }
            }
            if (tmp.getPosition().x <= goalX && isForward)
                buttonActive = true;

            else if (tmp.getPosition().x >= goalX && !isForward)
                buttonActive = true;
        }
    }

    @Override
    public void render (SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        particleEffect.draw(sb);

        for (Skin whichSkin : skins)
            if (whichSkin.getPosition().x <= camera.viewportWidth && whichSkin.getPosition().x >= 0 - skinWidth)
                sb.draw(whichSkin.getTexture(), whichSkin.getPosition().x, whichSkin.getPosition().y, skinWidth, skinHeight);

        // DRAWS ALL THE TEXT //
        musicText.getFont().draw(sb, musicText.getLayout(), xOffset, camera.viewportHeight - adOffset - 50);
        low.getFont().draw(sb, low.getLayout(), camera.viewportWidth/6 - low.getLayout().width/2, camera.viewportHeight - adOffset- 120);
        medium.getFont().draw(sb, medium.getLayout(), (camera.viewportWidth/6)*3 - medium.getLayout().width/2, camera.viewportHeight - adOffset - 120);
        high.getFont().draw(sb, high.getLayout(), (camera.viewportWidth/6)*5 - high.getLayout().width/2, camera.viewportHeight - adOffset - 120);

        sfx.getFont().draw(sb, sfx.getLayout(), xOffset, camera.viewportHeight - adOffset - 190);
        low.getFont().draw(sb, low.getLayout(), camera.viewportWidth/6 - low.getLayout().width/2, camera.viewportHeight - adOffset- 260);
        medium.getFont().draw(sb, medium.getLayout(), (camera.viewportWidth/6)*3 - medium.getLayout().width/2, camera.viewportHeight - adOffset - 260);
        high.getFont().draw(sb, high.getLayout(), (camera.viewportWidth/6)*5 - high.getLayout().width/2, camera.viewportHeight - adOffset - 260);

//        particlesText.getFont().draw(sb, particlesText.getLayout(), xOffset, camera.viewportHeight - adOffset - 330);
        skinsText.getFont().draw(sb, skinsText.getLayout(), camera.viewportWidth/2 - skinsText.getLayout().width/2, camera.viewportHeight - adOffset - 390);

        unlockText.getFont().draw(sb, unlockText.getLayout(), camera.viewportWidth/2 - unlockText.getLayout().width/2, 87);

        if (canMusic)
            sb.draw(checkboxOn, 530 - 45, 860-40, 45, 45);
        else
            sb.draw(checkboxOff, 530 - 45, 860-40, 45, 45);

        if (canSfx)
            sb.draw(checkboxOn, 530 - 45, 720-40, 45, 45);
        else
            sb.draw(checkboxOff, 530 - 45, 720-40, 45, 45);

        sb.draw(line, musicLineX, musicLineY, 180, line.getHeight());
        sb.draw(line, sfxLineX, sfxLineY, 180, line.getHeight());

        sb.draw(arrows, 0, 110, camera.viewportWidth, 87);
        sb.draw(exit, 470, 0, 70, 70);
        sb.draw(horusEye, 0, 0, 70, 70);
        sb.end();
    }

    @Override
    public void dispose () {
        background.dispose();
        exit.dispose();
        arrows.dispose();
        for (Skin whichSkin : skins)
            whichSkin.dispose();
        particleEffect.dispose();
        musicText.dispose();
        sfx.dispose();
        low.dispose();
        medium.dispose();
        high.dispose();
        skinsText.dispose();
//        particlesText.dispose();
        checkboxOn.dispose();
        checkboxOff.dispose();
        line.dispose();
        unlockText.dispose();
        horusEye.dispose();
    }

}
