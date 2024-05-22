package com.skloch.game.screens;

import com.skloch.game.HustleGame;

/**
 * Creates the screen for leaderboard to be viewed from the main menu.
 */
public class LeaderboardScreenMain extends LeaderboardScreen {

  public LeaderboardScreenMain(HustleGame game) {
    super(game);
  }

  @Override
  protected void onExitPressed() {
    game.soundManager.playButton();
    dispose();
    game.setScreen(new MenuScreen(game));
  }

  @Override
  protected String getExitButtonText() {
    return "Main Menu";
  }
}
