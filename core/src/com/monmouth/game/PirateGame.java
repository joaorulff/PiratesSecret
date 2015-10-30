package com.monmouth.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.monmouth.screens.PlayScreen;

/**
 * Created by joaolucasrulffdacosta on 10/28/15.
 */
public class PirateGame extends Game {


    public SpriteBatch batch;

    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;


    public void create (){

        batch = new SpriteBatch();
        setScreen(new PlayScreen(this));

    }

    public void render(){

        super.render();

    }


}
