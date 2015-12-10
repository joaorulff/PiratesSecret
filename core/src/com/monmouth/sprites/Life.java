package com.monmouth.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.monmouth.game.PirateGame;
import com.monmouth.screens.PlayScreen;

/**
 * Created by Kaka on 12/10/2015.
 */
public class Life extends Sprite {

    private PlayScreen playScreen;
    private float lifePositionX;
    private boolean toBeDeleted = false;
    public Life(PlayScreen screen, float x){
        super(new Texture(Gdx.files.internal("heart.png")));
        this.playScreen = screen;
        this.lifePositionX = x;
        this.defineLife();
    }

    public void defineLife(){
        this.setSize(170/(PirateGame.PPM*2),150/(PirateGame.PPM*2));
        this.setPosition(playScreen.getGamecamera().position.x, playScreen.getGamecamera().position.y);

    }
    public void update(){
        this.setPosition(playScreen.getGamecamera().position.x, playScreen.getGamecamera().position.y);
    }

}
