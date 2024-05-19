package com.skloch.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

// Changes
//
// - GameOverScreen no longer takes the hoursStudied, hoursRecreational, hoursSlept with Score
//   as new replacement
// - Line 60: GameOverScreen buttons height and padding has been modified to support the new
//   Score, Achievements and Leaderboard button
// - Achievements button added along with a listener component
// - Leaderboard button added along with a listener component
// - getTotalScore() functions get call from Score class to obtain the Final total score
// - Line 71: Implement ScrollTable for Score breakdown
// - Removed gameOverTable and replaced it with scrollable table and a button table
// - Line 146: Changed gameOverWindow.setSize(1000, 600); from 600 > 1000
// - Added scoreToPass which is the minimum score require to pass the exam (winning the game)
// - Line 89: Added You passed/failed exam message depending on your score
//

/**
 * A screen that displays the player's stats at the end of the game. Currently doesn't calculate a
 * score
 */
public class GameOverScreen implements Screen {

  private HustleGame game;
  Stage gameOverStage;
  Viewport viewport;
  OrthographicCamera camera;
  private final Score score;
  private int scoreToPass = 10000;

  /**
   * A screen to display a 'Game Over' screen when the player finishes their exams. Currently, does
   * not calculate a score, just shows the player's stats to them, as requested in assessment 1.
   * Tracking them now will make win conditions easier to implement for assessment 2.
   *
   * @param game An instance of HustleGame
   */
  public GameOverScreen(final HustleGame game) {
    this.game = game;
    gameOverStage = new Stage(new FitViewport(game.width, game.height));
    Gdx.input.setInputProcessor(gameOverStage);

    camera = new OrthographicCamera();
    viewport = new FitViewport(game.width, game.height, camera);
    camera.setToOrtho(false, game.width, game.height);

    // Create the window
    Window gameOverWindow = new Window("", game.skin);
    gameOverStage.addActor(gameOverWindow);

    // Table for UI elements in window
    Table gameOverTable = new Table();
    gameOverWindow.add(gameOverTable);

    Table scoreTable = new Table();
    gameOverWindow.add(scoreTable).prefHeight(600);

    // Table for things inside the scrollable widget
    Table scrollTable = new Table();

    // Scrollable widget
    com.badlogic.gdx.scenes.scene2d.ui.ScrollPane scrollWindow = new ScrollPane(scrollTable,
        game.skin);
    scrollWindow.setFadeScrollBars(false);
    // scrollWindow.setDebug(true);

    // scrollWindow.setFillParent(true);
    scoreTable.add(scrollWindow).padTop(5).height(530).width(550);

    // Title
    Label title = new Label("Game Over!", game.skin, "button");
    Label winlose;
    if (Score.getInstance().getTotalScore() >= scoreToPass) {
      winlose = new Label("You passed the exam!", game.skin, "button");
    } else {
      winlose = new Label("You failed the exam :(", game.skin, "button");
    }
    scrollTable.add(title).padTop(10);
    scrollTable.row();
    scrollTable.add(winlose).padTop(10);
    scrollTable.row();

    // Display scores
    this.score = Score.getInstance();
    int totalScore = score.getTotalScore();

    scrollTable.add(new Label("Total Score", game.skin, "interaction")).padBottom(5);
    scrollTable.row();
    scrollTable.add(new Label(String.valueOf(totalScore), game.skin, "button")).padBottom(20);
    scrollTable.row();

    Label text = new Label(Score.getInstance().getUserScores(), game.skin, "interaction");
    text.setWrap(true);
    scrollTable.add(text).width(500f).padLeft(15);

    Table buttonTable = new Table();
    scoreTable.add(buttonTable).padTop(0).height(530).width(300).padLeft(25);

    TextButton achievementsButton = new TextButton("Achievements", game.skin);
    buttonTable.add(achievementsButton).width(300).padTop(10);
    ;
    buttonTable.row();
    TextButton leaderboardButton = new TextButton("Leaderboard", game.skin);
    buttonTable.add(leaderboardButton).width(300).padTop(10);
    buttonTable.row();
    TextButton exitButton = new TextButton("Main Menu", game.skin);
    buttonTable.add(exitButton).width(300).padTop(10);

    exitButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.soundManager.playButton();
        game.soundManager.overworldMusic.stop();
        dispose();
        game.setScreen(new MenuScreen(game));
        score.resetScores();
        Achievement.getInstance().resetAllAchievements();
      }
    });

    leaderboardButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.soundManager.playButton();
        dispose();
        game.setScreen(new LeaderboardScreenGame(game));
      }
    });

    Screen thisScreen = this;
    achievementsButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.soundManager.playButton();
        game.setScreen(new AchievementScreen(game));
      }
    });

    gameOverWindow.pack();

    gameOverWindow.setSize(1000, 600);

    // Centre the window
    gameOverWindow.setX((viewport.getWorldWidth() / 2) - (gameOverWindow.getWidth() / 2));
    gameOverWindow.setY((viewport.getWorldHeight() / 2) - (gameOverWindow.getHeight() / 2));
  }


  /**
   * Renders the screen and the background each frame.
   *
   * @param delta The time in seconds since the last render.
   */
  @Override
  public void render(float delta) {
    ScreenUtils.clear(0, 0, 0, 1);

    game.blueBackground.draw();

    gameOverStage.act(delta);
    gameOverStage.draw();

    camera.update();

  }


  /**
   * Correctly resizes the onscreen elements when the window is resized.
   *
   * @param width New width of window
   * @param height New height of window
   */
  @Override
  public void resize(int width, int height) {
    gameOverStage.getViewport().update(width, height);
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
