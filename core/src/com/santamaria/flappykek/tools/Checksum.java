package com.santamaria.flappykek.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Base64Coder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



/**
 * Created by Nicolò Santamaria on 05/09/17.
 */

public class Checksum {

    private static Preferences uuid = Gdx.app.getPreferences("uuid");
    private static final String SALT = "BpQIZQ5szqb0cBExidg33P0MUpg9uD6d";

    public Checksum () {}

    private String genHash (String text) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte hashByte : hash)
                sb.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return null;
    }

    private String genHash (String text, String hashing_algorithm) {

        try {
            MessageDigest md = MessageDigest.getInstance(hashing_algorithm);
            byte[] hash = md.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte hashByte : hash)
                sb.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return null;
    }

    public String genKekHash(int num, boolean useSalt) {
        String encodedText = Base64Coder.encodeString(Integer.toString(num));
        if (useSalt)
            return genHash("4nPzwqeKT8EyKErK8v962TiPffANWkLQWU0k7GYy6j2ZDsATg0oMdymjEVqWMgHh" + encodedText + uuid.getString("uuid") + SALT);
        else
            return genHash("4nPzwqeKT8EyKErK8v962TiPffANWkLQWU0k7GYy6j2ZDsATg0oMdymjEVqWMgHh" + encodedText + uuid.getString("uuid"));
    }

    public String genKekHash (String text) {
        return genHash("4nPzwqeKT8EyKErK8v962TiPffANWkLQWU0k7GYy6j2ZDsATg0oMdymjEVqWMgHh" + text + uuid.getString("uuid"));
    }

    public String genKekLordHash (String text) {
        return genHash(text + "ttlVgTKfFY4lobFEmUCnsxV680DCYbT9VG8fNiukoJR6ncsD59APJW7i1Z5CwIhh" + text + uuid.getString("uuid"));
    }
}
