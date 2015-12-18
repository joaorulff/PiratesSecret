package com.monmouth.scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.monmouth.actors.Life;
import com.monmouth.game.PirateGame;
import com.monmouth.screens.PlayScreen;

/**
 * Created by matheussber on 12/18/15.
 */
public class LevelUpHUD implements Disposable {
    public Stage stage;
    private Viewport hudViewPort;
    private PlayScreen screen;
    public Label pirateLabel;
    public Image background;

    public Stage getStage() {
        return stage;
    }


    public LevelUpHUD(SpriteBatch spriteBatch, PlayScreen screen ){

        this.screen = screen;
        hudViewPort = new FitViewport(PirateGame.V_WIDTH, PirateGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(hudViewPort);



        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("lastninja.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.genMipMaps = true;
        parameter.minFilter = Texture.TextureFilter.Linear.MipMapLinearNearest;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.size = 40;
        BitmapFont font = generator.generateFont(parameter);
        Texture imgPirate = new Texture(Gdx.files.internal("ninja-on-white-background.jpg"));
        background = new Image(imgPirate);
        background.setPosition(0,0);
        background.setSize(800,480);
        pirateLabel = new Label("LEVEL UP!", new Label.LabelStyle(font, Color.BLACK));
        pirateLabel.setPosition(400-(pirateLabel.getWidth()/2),240-(pirateLabel.getHeight()/2));

        stage.addActor(background);
        stage.addActor(pirateLabel);

    }


    @Override
    public void dispose() {
        this.stage.dispose();
    }
}
