package com.monmouth.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.monmouth.screens.PlayScreen;
import com.monmouth.screens.StartScreen;

/**
 * Created by joaolucasrulffdacosta on 10/28/15.
 */

public class PirateGame extends Game {


    public SpriteBatch batch;
    public BitmapFont font;
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;




    public static AssetManager assetManager;

    public void create () {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("lastninja.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 12;
        font = generator.generateFont(parameter);
        batch = new SpriteBatch();
        assetManager = new AssetManager();
        assetManager.load("audio/music/pirateMusic.mp3", Music.class);
        assetManager.load("audio/sounds/pirateJump.wav", Sound.class);
        assetManager.finishLoading();
        this.setScreen(new StartScreen(this));

    }

    public void render(){

        super.render();

        //assetManager.dispose();
        //batch.dispose();

    }


}
