package com.monmouth.box2Dtool;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.monmouth.game.PirateGame;
import com.monmouth.screens.PlayScreen;

/**
 * Created by joaolucasrulffdacosta on 10/31/15.
 */
public class Box2DCreator {


    public Box2DCreator(PlayScreen screen){

        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        //Temp Box2d variables
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject mapObject : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){

            Rectangle rect = ((RectangleMapObject)mapObject).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2)/ PirateGame.PPM, (rect.getY() + rect.getHeight() / 2)/ PirateGame.PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox((rect.getWidth()/2)/ PirateGame.PPM, (rect.getHeight()/2)/ PirateGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);


        }

        for(MapObject mapObject : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){

            Rectangle rect = ((RectangleMapObject)mapObject).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2)/ PirateGame.PPM, (rect.getY() + rect.getHeight() / 2)/ PirateGame.PPM);

            body = world.createBody(bodyDef);

            shape.setAsBox((rect.getWidth()/2)/ PirateGame.PPM, (rect.getHeight()/2)/ PirateGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);


        }


    }
}
