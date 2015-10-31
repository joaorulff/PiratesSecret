package com.monmouth.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.monmouth.game.PirateGame;
import com.monmouth.screens.PlayScreen;

/**
 * Created by joaolucasrulffdacosta on 10/30/15.
 */
public class Ninja extends Sprite{

    private World mainWorld;
    public Body ninjaBody;

    private TextureRegion idleNinja;


    public Ninja(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("Game Boy Advance - Fairly OddParents Clash with the Anti-World - Ninja Timmy"));

        this.mainWorld = world;

        this.defineNinja();

        this.idleNinja = new TextureRegion(getTexture(), 0, 70, 35, 35);
        setBounds(0,0, 35/PirateGame.PPM, 35/PirateGame.PPM);
        setRegion(idleNinja);
    }

    public void update(float deltaTime){
        setPosition(ninjaBody.getPosition().x - getWidth() /2 , ninjaBody.getPosition().y - getHeight() /2);
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
