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
 * Created by joaolucasrulffdacosta on 10/30/15.
 */
public class Ninja extends Sprite{

    public enum State {RUNNING, IDLE,  JUMPING};
    public State currentState;
    public State previousState;


    private World mainWorld;
    public Body ninjaBody;
    private TextureRegion idleNinja;
    private PlayScreen playScreen;
    //Animation
    private Animation ninjaRunning;

    //Direction of the ninja. It is not necessary
    public boolean runningRight;

    //Time during each state
    private float stateTimer;

    public Ninja(PlayScreen screen){

        super(screen.getAtlas().findRegion("Run_"));

        this.mainWorld = screen.getWorld();

        this.currentState = State.IDLE;
        this.previousState = State.IDLE;
        this.playScreen = screen;

        this.stateTimer = 0;

        this.defineNinja();

        this.idleNinja = new TextureRegion(getTexture(), 0, 0, 330, 425);
        setBounds(0,0, 35/PirateGame.PPM, 35/PirateGame.PPM);
        setRegion(idleNinja);


        //Animation
        Array<TextureRegion> runningAnimationFrames = new Array<TextureRegion>();
        runningAnimationFrames.add(new TextureRegion(getTexture(), 0, 0, 340, 425));
        runningAnimationFrames.add(new TextureRegion(getTexture(), 340, 0, 340, 425));
        //runningAnimationFrames.add(new TextureRegion(getTexture(), 660, 0, 330, 425));
        //runningAnimationFrames.add(new TextureRegion(getTexture(), 990, 0, 330, 425));
        //runningAnimationFrames.add(new TextureRegion(getTexture(), 1320, 0, 330, 425));
        ninjaRunning = new Animation(0.1f, runningAnimationFrames);
        runningAnimationFrames.clear();
        //End Animation



    }

    public void update(float deltaTime){
        setPosition(ninjaBody.getPosition().x - getWidth() /2 , ninjaBody.getPosition().y - getHeight() /2);
        setRegion(getFrame(deltaTime));

    }

    public TextureRegion getFrame(float deltaTime){
        currentState = this.getState();

        TextureRegion currentTextureRegion = null;

        switch(currentState){
            case RUNNING:
                currentTextureRegion = this.ninjaRunning.getKeyFrame(stateTimer, true);
                break;
            case IDLE:
                currentTextureRegion = this.idleNinja;
                break;

        }

        if(currentState == previousState) stateTimer +=deltaTime;
        else stateTimer = 0;

        previousState = currentState;

    return currentTextureRegion;

    }

    //Implement the jumping animation
    public State getState(){
        if (ninjaBody.getLinearVelocity().y != 0){
            return State.IDLE;
        }
        if(ninjaBody.getLinearVelocity().x > 0){
            return State.RUNNING;
        }

        return State.IDLE;
    }

    public float getNinjaBodyY() {
        return this.ninjaBody.getPosition().y;
    }
    public float getNinjaBodyX() {
        return this.ninjaBody.getPosition().x;
    }

    public void defineNinja() {

        BodyDef ninjaBodyDef = new BodyDef();
        ninjaBodyDef.position.set(100/PirateGame.PPM, 100 / PirateGame.PPM);
        ninjaBodyDef.type = BodyDef.BodyType.DynamicBody;

        ninjaBody = mainWorld.createBody(ninjaBodyDef);

        FixtureDef ninjaFixtureDef = new FixtureDef();
        CircleShape ninjaShape = new CircleShape();
        ninjaShape.setRadius(12/PirateGame.PPM);

        PolygonShape sensorShape= new PolygonShape();
        sensorShape.setAsBox(6/PirateGame.PPM, 1f/PirateGame.PPM, new Vector2(0,-12/PirateGame.PPM), 0);
        FixtureDef sensorFixtureDef = new FixtureDef();
        sensorFixtureDef.shape = sensorShape;
        sensorFixtureDef.isSensor = true;
        sensorFixtureDef.filter.categoryBits = playScreen.CATEGORY_SENSORLIFE;
        sensorFixtureDef.filter.maskBits = playScreen.CATEGORY_WORLD;
        ninjaFixtureDef.shape = ninjaShape;
        ninjaFixtureDef.filter.categoryBits = playScreen.CATEGORY_NINJA;
        ninjaFixtureDef.filter.maskBits = (short) (playScreen.CATEGORY_WORLD|playScreen.CATEGORY_PIRATE | playScreen.CATEGORY_FINISH);
        ninjaBody.createFixture(ninjaFixtureDef);
        ninjaBody.createFixture(sensorFixtureDef);

    }
}
