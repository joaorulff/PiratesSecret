package com.monmouth.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.monmouth.game.PirateGame;



/**
 * Created by joaolucasrulffdacosta on 10/29/15.
 */
public class HUD implements Disposable{


    public Stage stage;

    private Viewport hudViewPort;

    private Integer worldTimer;
    private float timeCounter;
    private static Integer score;

    Label countDownLabel;
    static Label scoreLabel;
    Label livesLabel;
    Label levelLabel;
    Label worldLabel;
    Label pirateLabel;

    public HUD(SpriteBatch spriteBatch){


        timeCounter = 0;
        score = 0;

        hudViewPort = new FitViewport(PirateGame.V_WIDTH, PirateGame.V_HEIGHT, new OrthographicCamera());

        stage = new Stage(hudViewPort);

        Table hudStageTable = new Table();
        hudStageTable.top();

        hudStageTable.setFillParent(true);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("lastninja.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        BitmapFont font = generator.generateFont(parameter);
        //countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(font, Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(font, Color.WHITE));
        livesLabel = new Label("LIVES", new Label.LabelStyle(font, Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(font, Color.WHITE));
        //worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        pirateLabel = new Label("SCORE", new Label.LabelStyle(font, Color.WHITE));


        hudStageTable.add(pirateLabel).expandX().padTop(10);
        //hudStageTable.add(worldLabel).expandX().padTop(10);
        hudStageTable.add(livesLabel).expandX().padTop(10);

        hudStageTable.row();
        hudStageTable.add(scoreLabel).expandX();
        //hudStageTable.add(levelLabel).expandX();
        hudStageTable.add(countDownLabel).expandX();


        stage.addActor(hudStageTable);

    }





    public static void updateScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }




    @Override
    public void dispose() {
        this.stage.dispose();
    }
}
