package com.monmouth.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.monmouth.game.PirateGame;
import com.monmouth.screens.PlayScreen;

/**
 * Created by matheussber on 11/16/15.
 */
public class Pirate extends Sprite {
    private World mainWorld;
    private FixtureDef pirateFixtureDef;
    private Vector2 piratePosition;
    public Body pirateBody;

    public Pirate(World world, PlayScreen screen, Vector2 position) {
        this.mainWorld = world;
        this.definePirate(position);
    }
    private void definePirate(Vector2 position) {
        BodyDef pirateBodyDef = new BodyDef();
        pirateBodyDef.type = BodyDef.BodyType.DynamicBody;
        pirateBody = mainWorld.createBody(pirateBodyDef);

        piratePosition = this.spawn(pirateBody, pirateBodyDef, position);

        pirateFixtureDef = new FixtureDef();
        CircleShape pirateShape = new CircleShape();
        pirateShape.setRadius(8 / PirateGame.PPM);

        pirateFixtureDef.shape = pirateShape;
        pirateBody.createFixture(pirateFixtureDef);
    }
    private Vector2 spawn(Body pirateBody, BodyDef pirateBodyDef, Vector2 position) {
        pirateBodyDef.position.set(position);
        //pirateBody.setLinearVelocity(-2,0);
        return position;
    }
}
