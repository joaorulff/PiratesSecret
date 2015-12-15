package com.monmouth.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.monmouth.game.PirateGame;
import com.monmouth.screens.PlayScreen;

/**
 * Created by Kaka on 12/10/2015.
 */
public class Life extends Actor {

    private float lifePositionX;
    private float lifePositionY;
    private Texture texture;
    private boolean toBeDeleted = false;
    public Life(float x, float y){
        texture = new Texture(Gdx.files.internal("heart.png"));

        this.lifePositionX = x;
        this.lifePositionY = y;


    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture,lifePositionX,lifePositionY,16,16);
    }

    public boolean isToBeDeleted() {
        return toBeDeleted;
    }

    public void setToBeDeleted(boolean toBeDeleted) {
        this.toBeDeleted = toBeDeleted;
    }
}
