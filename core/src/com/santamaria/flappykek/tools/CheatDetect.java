package com.santamaria.flappykek.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Base64Coder;
import com.santamaria.flappykek.FlappyKek;

import java.util.UUID;

/**
 * Created by Nicolò Santamaria on 14/09/17.
 */

public class CheatDetect implements Runnable {
    private static Preferences score = Gdx.app.getPreferences("Scores");
    private static Preferences data = Gdx.app.getPreferences("data");

    private static boolean hasRun;

    public boolean hasFinished = false;

    public CheatDetect() {
        hasRun = data.getBoolean("f");
    }

    private void startMusic () {
        if (data.getBoolean("music")) {
            FlappyKek.music.setVolume(3 * data.getFloat("music_volume"));
            FlappyKek.music.play();
        }
    }

    public void run () {
        Checksum cs = new Checksum();

        if (!hasRun) {
            String oldScore;
            String uuid = UUID.randomUUID().toString();
            oldScore = Integer.toString(score.getInteger("score"));

            data.putBoolean("f", true);
            data.putString("uuid", uuid);
            data.putBoolean("sound", true);
            data.putBoolean("music", true);
            data.putString("score", Base64Coder.encodeString(oldScore));
            data.putString("hash", cs.genKekHash(Base64Coder.encodeString(oldScore)));
            data.putString("froginuse", Base64Coder.encodeString("0"));
            data.putString("hashp", cs.genKekLordHash(Base64Coder.encodeString("0")));
            data.putInteger("passed_columns", 0);
            data.putString("pcolumns_sig", cs.genKekHash(0, true));
            data.putFloat("music_volume", 0.3f);
            data.putFloat("sfx_volume", 0.3f);
            data.flush();
        }


        if (!cs.genKekHash(data.getString("score")).equals(data.getString("hash")) && hasRun) {
            data.putString("score", Base64Coder.encodeString("0"));
            data.putString("hash", cs.genKekHash(data.getString("score")));
            data.flush();
        }

        if (!cs.genKekLordHash(data.getString("froginuse")).equals(data.getString("hashp")) && hasRun) {
            data.putString("froginuse", Base64Coder.encodeString("0"));
            data.putString("hashp", cs.genKekLordHash("0"));
            data.flush();
        }

        if (!cs.genKekHash(data.getInteger("passed_columns"), true).equals(data.getString("pcolumns_sig")) && hasRun) {
            data.putInteger("passed_columns", 0);
            data.putString("pcolumns_sig", cs.genKekHash(0, true));
            data.flush();
        }

        startMusic();
        hasFinished = true;
    }

//    public static boolean hasFinished () {
//        return hasFinished;
//    }

}
