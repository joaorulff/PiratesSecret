package com.monmouth.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.monmouth.game.PirateGame;
import com.monmouth.screens.PlayScreen;

/**
 * Created by joaolucasrulffdacosta on 10/31/15.
 */
public class Star extends Sprite{

    private World mainWorld;
    public Body starBody;
    private Ninja ninja;
    private PlayScreen playScreen;
    private FixtureDef starFixtureDef;



    //private Texture starTexture;
    public Star (World world, PlayScreen screen, float x, Ninja ninja){
        //super(new Texture(Gdx.files.internal("ninjastar.png")));
        //this.setSize(100f/PirateGame.PPM,100f/PirateGame.PPM);
        this.mainWorld = world;
        this.ninja = ninja;
        this.playScreen = screen;
        this.defineStar(x);

    }

    public void defineStar(float x){
        BodyDef starBodyDef = new BodyDef();
        starBodyDef.position.x = (float) (x + 0.3);
        starBodyDef.position.y = ninja.getNinjaBodyY();

        //starBodyDef.position.set(x, 50/ PirateGame.PPM);

        starBodyDef.type = BodyDef.BodyType.DynamicBody;

        starBody = mainWorld.createBody(starBodyDef);
        starBody.setUserData(this);
        starFixtureDef = new FixtureDef();
        CircleShape starShape = new CircleShape();
        starShape.setRadius(4 / PirateGame.PPM);


        starFixtureDef.shape = starShape;
        starFixtureDef.filter.categoryBits = playScreen.CATEGORY_STAR;
        starFixtureDef.filter.maskBits = (short) (playScreen.CATEGORY_PIRATE|playScreen.CATEGORY_WORLD);
        starBody.createFixture(starFixtureDef);

        starBody.setLinearVelocity(5,0);
        starBody.setGravityScale(0);

    }

    public void destroy() {
        this.starBody.getWorld().destroyBody(this.starBody);

    }

    public float getStarBodyX() {
        return this.starBody.getPosition().x;
    }
    public float getStarBodyY() {
        return this.starBody.getPosition().x;
    }


}
