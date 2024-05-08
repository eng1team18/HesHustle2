package com.skloch.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * A scene2d window consisting of a title, a scrollable widget and an exit button. Used to display
 * the top 10 Leaderboard
 */
public abstract class LeaderboardScreen implements Screen {

  protected HustleGame game;
  protected Stage leaderboardStage;
  protected OrthographicCamera camera;
  protected Viewport viewport;

  /**
   * A scene2d window consisting of a title, a scrollable list of Top 10 Leaderboard and a main menu
   * button.
   *
   * @param game An instance of the HustleGame class
   */
  public LeaderboardScreen(final HustleGame game) {

    // Basically all the same code as the settings menu
    this.game = game;
    leaderboardStage = new Stage(new FitViewport(game.WIDTH, game.HEIGHT));
    Gdx.input.setInputProcessor(leaderboardStage);

    camera = new OrthographicCamera();
    viewport = new FitViewport(game.WIDTH, game.HEIGHT, camera);
    camera.setToOrtho(false, game.WIDTH, game.HEIGHT);

    // Create the window
    Window leaderboardMenu = new Window("", game.skin);
    leaderboardStage.addActor(leaderboardMenu);
    leaderboardMenu.setModal(true);

    // Table for UI elements in window
    Table leaderboardTable = new Table();
    leaderboardMenu.add(leaderboardTable).prefHeight(600);

    // Title
    Label title = new Label("Leaderboard", game.skin, "button");
    leaderboardTable.add(title).padTop(10);
    leaderboardTable.row();

    // Table for things inside the scrollable widget
    Table scrollTable = new Table();

    // Scrollable widget
    ScrollPane scrollWindow = new ScrollPane(scrollTable, game.skin);
    scrollWindow.setFadeScrollBars(false);
    // scrollWindow.setDebug(true);

    // scrollWindow.setFillParent(true);
    leaderboardTable.add(scrollWindow).padTop(20).height(350);
    leaderboardTable.row();

    Label text = new Label(Leaderboard.getInstance().getFormattedTopScores(), game.skin,
        "interaction");
    text.setWrap(true);
    scrollTable.add(text).width(520f).padLeft(15);

    // Exit button
    TextButton exitButton = new TextButton(getExitButtonText(), game.skin);
    leaderboardTable.add(exitButton).bottom().width(300).padTop(10);

    leaderboardMenu.pack();

    leaderboardMenu.setSize(600, 600);

    // Centre the window
    leaderboardMenu.setX((viewport.getWorldWidth() / 2) - (leaderboardMenu.getWidth() / 2));
    leaderboardMenu.setY((viewport.getWorldHeight() / 2) - (leaderboardMenu.getHeight() / 2));

    // Listener for the exit button
    exitButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        onExitPressed();
      }
    });
  }

  protected abstract void onExitPressed();
  protected abstract String getExitButtonText();

  /**
   * Renders the Leaderboard window
   *
   * @param delta The time in seconds since the last render.
   */
  @Override
  public void render(float delta) {
    ScreenUtils.clear(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    game.blueBackground.draw();

    leaderboardStage.act(delta);
    leaderboardStage.draw();

    camera.update();
  }


  /**
   * Correctly resizes the onscreen elements when the window is resized
   *
   * @param width
   * @param height
   */
  @Override
  public void resize(int width, int height) {
    leaderboardStage.getViewport().update(width, height);
    viewport.update(width, height);
  }

  // Other required methods from Screen
  @Override
  public void show() {
  }

  @Override
  public void hide() {
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void dispose() {
  }
}
