package com.monmouth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.monmouth.game.PirateGame;

/**
 * Created by matheussber on 11/17/15.
 */
public class StartScreen implements Screen {
    final PirateGame game;
    OrthographicCamera camera;
    public StartScreen(final PirateGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "A - Throw a star\n\nArrow Key UP - Jump\n\nP - Pause\n", 100, 150);
        game.font.draw(game.batch, "\nPress 1 to start the game and save ninja from pirates!", 100, 100);
        game.batch.end();

        if (Gdx.input.isKeyPressed(8)) {
            game.setScreen(new PlayScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
