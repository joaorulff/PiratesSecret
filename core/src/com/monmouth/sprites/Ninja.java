package com.monmouth.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.monmouth.game.PirateGame;
import com.monmouth.screens.PlayScreen;

/**
 * Created by joaolucasrulffdacosta on 10/30/15.
 */
public class Ninja extends Sprite{

    public enum State {RUNNING, IDLE, JUMPING};
    public State currentState;
    public State previousState;


    private World mainWorld;
    public Body ninjaBody;

    private TextureRegion idleNinja;

    //Animation
    private Animation ninjaRunning;

    //Direction of the ninja. It is not necessary
    public boolean runningRight;

    //Time during each state
    private float stateTimer;

    public Ninja(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("Run_"));

        this.mainWorld = world;

        this.currentState = State.IDLE;
        this.previousState = State.IDLE;

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
        ninjaBodyDef.position.set(32 / PirateGame.PPM, 32 / PirateGame.PPM);
        ninjaBodyDef.type = BodyDef.BodyType.DynamicBody;

        ninjaBody = mainWorld.createBody(ninjaBodyDef);

        FixtureDef ninjaFixtureDef = new FixtureDef();
        CircleShape ninjaShape = new CircleShape();
        ninjaShape.setRadius(12 / PirateGame.PPM);


        ninjaFixtureDef.shape = ninjaShape;
        ninjaBody.createFixture(ninjaFixtureDef);


    }
}
