package com.santamaria.flappykek.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Nicolò Santamaria on 13/02/17.
 */

public class Text {
    private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Anton.ttf"));
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    private BitmapFont font;
    private GlyphLayout layout;
//    private String text;

    public Text (int dimensions, String textIn) {
        String chars = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789:.,;-!? ";
        parameter.size = dimensions;
        parameter.characters = chars;
        font = generator.generateFont(parameter);
        font.setColor(1f, 1f, 1f, 1f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        generator.dispose();

        layout = new GlyphLayout();
        layout.setText(font, textIn);
    }

    public Text (int dimensions, String textIn, String chars) {
//        text = textIn;
        parameter.size = dimensions;
        parameter.characters = chars;
        font = generator.generateFont(parameter);
        font.setColor(1f, 1f, 1f, 1f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        generator.dispose();

        layout = new GlyphLayout();
        layout.setText(font, textIn);
    }

    public void setText (String textIn) {
        layout.setText(font, textIn);
    }

//    public void setDimensions (int dimensions) {
//        parameter.size = dimensions;
//    }

    public GlyphLayout getLayout () {
        return layout;
    }

    public BitmapFont getFont () {
        return font;
    }

    public void dispose () {
        font.dispose();
    }
}
