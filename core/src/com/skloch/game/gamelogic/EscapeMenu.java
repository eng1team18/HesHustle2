package com.skloch.game.gamelogic;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.skloch.game.HustleGame;
import com.skloch.game.screens.GameScreen;
import com.skloch.game.screens.MenuScreen;
import com.skloch.game.screens.SettingsScreen;

/**
 * The class that manages the escape menu window that displays when pressing 'escape'. It pauses the
 * game, and displays options to access different screens.
 */
public class EscapeMenu {

  private GameScreen gameScreen;
  private HustleGame game;
  public static Window escapeMenu;
  private Viewport viewport;
  private Stage uiStage;

  public EscapeMenu(HustleGame game, Viewport viewport, GameScreen gameScreen, Stage uiStage) {
    this.game = game;
    this.viewport = viewport;
    this.gameScreen = gameScreen;
    this.uiStage = uiStage;
    setupEscapeMenu(uiStage);
  }

  /**
   * Configures everything needed to display the escape menu window when the player presses
   * 'escape'. Doesn't return anything as the variable escapeMenu is used to store the window takes
   * a table already added to the uiStage.
   *
   * @param interfaceStage The stage that the escapeMenu should be added to
   */
  public void setupEscapeMenu(Stage interfaceStage) {
    // Configures an escape menu to display when hitting 'esc'
    // Escape menu
    escapeMenu = new Window("", game.skin);
    interfaceStage.addActor(escapeMenu);
    escapeMenu.setModal(true);

    Table escapeTable = new Table();
    escapeTable.setFillParent(true);

    escapeMenu.add(escapeTable);

    TextButton resumeButton = new TextButton("Resume", game.skin);
    escapeTable.add(resumeButton).pad(60, 80, 10, 80).width(300);
    escapeTable.row();
    TextButton settingsButton = new TextButton("Settings", game.skin);
    escapeTable.add(settingsButton).pad(10, 50, 10, 50).width(300);
    escapeTable.row();
    TextButton exitButton = new TextButton("Exit", game.skin);
    escapeTable.add(exitButton).pad(10, 50, 60, 50).width(300);

    escapeMenu.pack();

    // escapeMenu.setDebug(true);

    // Centre
    escapeMenu.setX(((viewport.getWorldWidth() / 2) - (escapeMenu.getWidth() / 2)) - 275);
    escapeMenu.setY(((viewport.getWorldHeight() / 2) - (escapeMenu.getHeight() / 2)) - 150);

    // Create button listeners

    resumeButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        if (escapeMenu.isVisible()) {
          game.soundManager.playButton();
          game.soundManager.playOverworldMusic();
          escapeMenu.setVisible(false);
        }
      }
    });

    // SETTINGS BUTTON
    // I assign this object to a new var 'thisScreen' since the changeListener overrides 'this'
    // I wasn't sure of a better solution
    Screen thisScreen = gameScreen;
    settingsButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        if (escapeMenu.isVisible()) {
          game.soundManager.playButton();
          game.setScreen(new SettingsScreen(game, thisScreen));
        }
      }
    });

    exitButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        if (escapeMenu.isVisible()) {
          game.soundManager.playButton();
          game.soundManager.stopOverworldMusic();
          gameScreen.dispose();
          game.setScreen(new MenuScreen(game));
        }
      }
    });

    escapeMenu.setVisible(false);

  }
}
