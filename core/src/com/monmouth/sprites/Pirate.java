package com.monmouth.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.monmouth.game.PirateGame;
import com.monmouth.screens.PlayScreen;

/**
 * Created by matheussber on 11/16/15.
 */

public class Pirate extends Sprite {

    protected World mainWorld;
    private PlayScreen playScreen;
    public Body pirateBody;
    private boolean toBeDeleted = false;

    public boolean isToBeDeleted() {
        return toBeDeleted;
    }

    public void setToBeDeleted(boolean toBeDeleted) {
        this.toBeDeleted = toBeDeleted;
    }

    private float stateTime;
    private Animation pirateAnimation;
    private Array<TextureRegion> frames;

    private Vector2 piratePosition;


    public Pirate(PlayScreen screen, float x, float y) {

        this.mainWorld = screen.getWorld();
        this.playScreen = screen;
        frames = new Array<TextureRegion>();


        frames.add(new TextureRegion(screen.getPirateAtlas().findRegion("pirateShrink"), 0, 0, 31, 48));
        frames.get(0).flip(true, false);
        frames.add(new TextureRegion(screen.getPirateAtlas().findRegion("pirateShrink"), 41, 0, 31, 48));
        frames.get(1).flip(true, false);

        pirateAnimation = new Animation(0.4f, frames);
        stateTime = 0;

        setBounds(0, 0, 35 / PirateGame.PPM, 35 / PirateGame.PPM);


        this.setPosition(x, y);
        this.definePirate();

    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        setPosition(pirateBody.getPosition().x - this.getWidth() / 2, pirateBody.getPosition().y - this.getHeight() / 2);
        this.setRegion(pirateAnimation.getKeyFrame(stateTime, true));
        this.pirateBody.setLinearVelocity(2f,0f);
    }

    private void definePirate() {

        BodyDef pirateBodyDef = new BodyDef();
        //pirateBodyDef.position.set(32 / PirateGame.PPM, 32 / PirateGame.PPM);
        pirateBodyDef.position.set(this.getX(), this.getY());
        pirateBodyDef.type = BodyDef.BodyType.DynamicBody;

        this.pirateBody = this.mainWorld.createBody(pirateBodyDef);
        this.pirateBody.setUserData(this);
        FixtureDef pirateFixtureDef = new FixtureDef();
        CircleShape pirateShape = new CircleShape();
        pirateShape.setRadius(12 / PirateGame.PPM);


        pirateFixtureDef.shape = pirateShape;
        pirateFixtureDef.filter.categoryBits = playScreen.CATEGORY_PIRATE;
        pirateFixtureDef.filter.maskBits = (short) (playScreen.CATEGORY_NINJA | playScreen.CATEGORY_WORLD | playScreen.CATEGORY_STAR);

        pirateBody.createFixture(pirateFixtureDef);
    }
    public void remove() {
        this.pirateBody.getWorld().destroyBody(pirateBody);
    }
}
