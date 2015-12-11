package com.monmouth.scenes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.monmouth.box2Dtool.WorldContactListener;
import com.monmouth.game.PirateGame;
import com.monmouth.screens.PlayScreen;
import com.monmouth.sprites.Life;


/**
 * Created by joaolucasrulffdacosta on 10/29/15.
 */
public class HUD implements Disposable{


    public Stage stage;

    private Viewport hudViewPort;

    private static Integer score;
    private PlayScreen screen;

    static Label scoreLabel;
    Label livesLabel;
    Label pirateLabel;
    public Array<Life> lives;

    public HUD(SpriteBatch spriteBatch, PlayScreen screen ){

        this.screen = screen;
        score = 0;

        hudViewPort = new FitViewport(PirateGame.V_WIDTH, PirateGame.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(hudViewPort);



        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("lastninja.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        BitmapFont font = generator.generateFont(parameter);


        pirateLabel = new Label("SCORE", new Label.LabelStyle(font, Color.WHITE));
        pirateLabel.setPosition(0+pirateLabel.getWidth(),203-pirateLabel.getHeight());

        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(font, Color.WHITE));
        scoreLabel.setPosition(pirateLabel.getX(),190-scoreLabel.getHeight());

        livesLabel = new Label("LIVES", new Label.LabelStyle(font, Color.WHITE));
        livesLabel.setPosition(350-livesLabel.getWidth(),203-livesLabel.getHeight());

        lives = new Array<Life>();
        for(int i = 0; i<5 ; i++) {
            Life life = new Life(livesLabel.getX()+(i-1)+16*(i),livesLabel.getY()-21);
            lives.add(life);
            stage.addActor(life);
        }
        stage.addActor(pirateLabel);
        stage.addActor(scoreLabel);
        stage.addActor(livesLabel);

    }






    public static void updateScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    public void updateLife() {

        if(this.screen.getContactListener().isNinjaHittingPirate || this.screen.isNinjaFell()) {
            if(this.screen.getContactListener().isNinjaHittingPirate)
                this.screen.getNinja().ninjaBody.setTransform(this.screen.getNinja().ninjaBody.getPosition().x, this.screen.getNinja().ninjaBody.getPosition().y + 5, 0);
            if(this.screen.isNinjaFell())
                this.screen.getNinja().ninjaBody.setTransform(this.screen.getNinja().ninjaBody.getPosition().x, this.screen.getNinja().ninjaBody.getPosition().y + 5, 0);
            lives.get(lives.size-1).remove();
            lives.removeIndex(lives.size-1);

            //callback of actions to do on playscreen
            this.screen.getContactListener().isNinjaHittingPirate = false;
            this.screen.setNinjaFell(false);
        }
    }


    @Override
    public void dispose() {
        this.stage.dispose();
    }
}
