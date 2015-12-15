package com.monmouth.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.monmouth.game.PirateGame;
import com.monmouth.screens.PlayScreen;

/**
 * Created by matheussber on 12/15/15.
 */
public class Life extends Sprite {
    private World mainWorld;
    public Body lifeBody;
    private PlayScreen playScreen;
    private FixtureDef lifeFixtureDef;
    private boolean toBeDeleted = false;
    public Life(World world, PlayScreen playScreen, float x, float y) {
        super(new Texture(Gdx.files.internal("heart.png")));
        this.mainWorld = world;
        this.playScreen = playScreen;
        this.setPosition(x,y);
        this.setSize(16/PirateGame.PPM, 16/PirateGame.PPM);

    }
    public void defineLife() {
        BodyDef lifeDef = new BodyDef();
        lifeDef.position.set(this.getX(), this.getY());
        lifeDef.type = BodyDef.BodyType.StaticBody;
        this.lifeBody = this.mainWorld.createBody(lifeDef);
        this.lifeBody.setUserData(this);
        lifeFixtureDef = new FixtureDef();
        CircleShape lifeShape = new CircleShape();
        lifeShape.setRadius(8 / PirateGame.PPM);
        lifeFixtureDef.shape = lifeShape;
        lifeFixtureDef.filter.categoryBits = playScreen.CATEGORY_LIFE;
        lifeFixtureDef.filter.maskBits =(short) (playScreen.CATEGORY_NINJA | playScreen.CATEGORY_WORLD);

        lifeBody.createFixture(lifeFixtureDef);
    }
    public void delete() {this.lifeBody.getWorld().destroyBody(lifeBody);}
    public boolean isToBeDeleted() {
        return toBeDeleted;
    }
    public void setToBeDeleted(boolean toBeDeleted) {
        this.toBeDeleted = toBeDeleted;
    }
}
