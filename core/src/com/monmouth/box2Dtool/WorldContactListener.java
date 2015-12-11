package com.monmouth.box2Dtool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.monmouth.screens.PlayScreen;
import com.monmouth.sprites.Pirate;
import com.monmouth.sprites.Star;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.ArrayList;

/**
 * Created by joaolucasrulffdacosta on 10/31/15.
 */
public class WorldContactListener implements ContactListener{
    private PlayScreen screen;
    private Array<Body> starsToBeDeleted =  new Array<Body>();
    private Array<Body> piratesToBeDeleted = new Array<Body>();
    public boolean isNinjaHittingPirate = false;
    public WorldContactListener(PlayScreen screen) {
        this.screen = screen;
    }

    public Array<Body> getPiratesToBeDeleted() {
        return piratesToBeDeleted;
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
        }
        if(b == screen.CATEGORY_NINJA) {
            if(a == screen.CATEGORY_PIRATE) {
                isNinjaHittingPirate = true;
                Pirate tempPirate = (Pirate)contact.getFixtureA().getBody().getUserData();
                tempPirate.setToBeDeleted(true);

                piratesToBeDeleted.add((Body)contact.getFixtureA().getBody());
            }
        }

    }

    public Array<Body> getStarsToBeDeleted() {
        return starsToBeDeleted;
    }

    @Override
    public void endContact(Contact contact) {
        //Gdx.app.log("End Contact", "");

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
