package com.monmouth.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.monmouth.game.PirateGame;

/**
 * Created by joaolucasrulffdacosta on 10/30/15.
 */
public class Ninja extends Sprite{

    private World mainWorld;
    public Body ninjaBody;


    public Ninja(World world){
        this.mainWorld = world;

        this.defineNinja();
    }

    public void defineNinja(){

        BodyDef ninjaBodyDef = new BodyDef();
        ninjaBodyDef.position.set(32 / PirateGame.PPM, 32/ PirateGame.PPM);
        ninjaBodyDef.type = BodyDef.BodyType.DynamicBody;

        ninjaBody = mainWorld.createBody(ninjaBodyDef);

        FixtureDef ninjaFixtureDef = new FixtureDef();
        CircleShape ninjaShape = new CircleShape();
        ninjaShape.setRadius(5 / PirateGame.PPM);


        ninjaFixtureDef.shape = ninjaShape;
        ninjaBody.createFixture(ninjaFixtureDef);
    }
}
