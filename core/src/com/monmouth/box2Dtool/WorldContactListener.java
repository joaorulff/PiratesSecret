package com.monmouth.box2Dtool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.monmouth.screens.PlayScreen;
import com.monmouth.sprites.LifeSprite;
import com.monmouth.sprites.Pirate;
import com.monmouth.sprites.Star;

import javax.swing.plaf.basic.BasicPanelUI;
import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.ArrayList;

/**
 * Created by joaolucasrulffdacosta on 10/31/15.
 */
public class WorldContactListener implements ContactListener{
    private PlayScreen screen;
    private Array<Body> starsToBeDeleted =  new Array<Body>();
    private Array<Body> piratesToBeDeleted = new Array<Body>();
    private Array<Body> livesToBeDeled = new Array<Body>();
    public boolean isNinjaHittingPirate = false;
    public boolean isNinjaOnGround = true;
    public WorldContactListener(PlayScreen screen) {
        this.screen = screen;
    }

    public Array<Body> getPiratesToBeDeleted() {
        return piratesToBeDeleted;
    }

    public boolean isNinjaHittingPirate() {
        return isNinjaHittingPirate;
    }

    public void setNinjaHittingPirate(boolean ninjaHittingPirate) {
        isNinjaHittingPirate = ninjaHittingPirate;
    }

    @Override

    public void beginContact(Contact contact) {

        Short a = contact.getFixtureA().getFilterData().categoryBits;
        Short b = contact.getFixtureB().getFilterData().categoryBits;

        //CHECKING IF STAR IS COLLIDING.
        if(a == screen.CATEGORY_STAR) {
            //System.out.println("star colliding!A");
            starsToBeDeleted.add((Body)contact.getFixtureA().getBody());
            Star tempStar = (Star) contact.getFixtureA().getBody().getUserData();

            tempStar.setToBeDeleted(true);
            if(b == screen.CATEGORY_PIRATE) {
                System.out.println("Bateu no pirataB");

                Pirate tempPirate = (Pirate)contact.getFixtureB().getBody().getUserData();
                tempPirate.setToBeDeleted(true);

                piratesToBeDeleted.add((Body)contact.getFixtureB().getBody());
            }

        }
        if(b == screen.CATEGORY_STAR) {
            //System.out.println("star colliding!B");
            starsToBeDeleted.add((Body)contact.getFixtureB().getBody());
            Star tempStar = (Star) contact.getFixtureB().getBody().getUserData();
            tempStar.setToBeDeleted(true);
            if(a == screen.CATEGORY_PIRATE) {
                System.out.println("Bateu no pirataA");
                Pirate tempPirate = (Pirate)contact.getFixtureA().getBody().getUserData();
                tempPirate.setToBeDeleted(true);

                piratesToBeDeleted.add((Body)contact.getFixtureA().getBody());
            }
        }
        if(a == screen.CATEGORY_NINJA) {
            if(b == screen.CATEGORY_PIRATE) {
                isNinjaHittingPirate = true;
                Pirate tempPirate = (Pirate)contact.getFixtureB().getBody().getUserData();
                tempPirate.setToBeDeleted(true);

                piratesToBeDeleted.add((Body)contact.getFixtureB().getBody());
            }
            if(b == screen.CATEGORY_LIFE) {
                LifeSprite tempLife = (LifeSprite)contact.getFixtureB().getBody().getUserData();
                tempLife.setToBeDeleted(true);

                livesToBeDeled.add((Body)contact.getFixtureB().getBody());
            }

        }
        if(b == screen.CATEGORY_NINJA) {
            if(a == screen.CATEGORY_PIRATE) {
                isNinjaHittingPirate = true;
                Pirate tempPirate = (Pirate)contact.getFixtureA().getBody().getUserData();
                tempPirate.setToBeDeleted(true);

                piratesToBeDeleted.add((Body)contact.getFixtureA().getBody());
            }
            if(a == screen.CATEGORY_LIFE) {
                LifeSprite tempLife = (LifeSprite)contact.getFixtureA().getBody().getUserData();
                tempLife.setToBeDeleted(true);

                livesToBeDeled.add((Body)contact.getFixtureA().getBody());
            }

        }


        if(a == screen.CATEGORY_SENSOR) {
            if(b == screen.CATEGORY_WORLD)
                isNinjaOnGround = true;
        }
        if(b == screen.CATEGORY_SENSOR) {
            if(a == screen.CATEGORY_WORLD)
                isNinjaOnGround = true;

        }

    }

    public Array<Body> getStarsToBeDeleted() {
        return starsToBeDeleted;
    }

    public Array<Body> getLivesToBeDeled() {
        return livesToBeDeled;
    }

    @Override
    public void endContact(Contact contact) {
        //Gdx.app.log("End Contact", "");
        Short a = contact.getFixtureA().getFilterData().categoryBits;
        Short b = contact.getFixtureB().getFilterData().categoryBits;
        if(a == screen.CATEGORY_SENSOR) {
            if(b == screen.CATEGORY_WORLD)
                isNinjaOnGround = false;
        }
        if(b == screen.CATEGORY_SENSOR) {
            if(a == screen.CATEGORY_WORLD)
                isNinjaOnGround = false;

        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
