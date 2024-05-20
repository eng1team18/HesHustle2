package com.skloch.game.screens;

import com.skloch.game.HustleGame;
import com.skloch.game.screens.GameOverScreen;
import com.skloch.game.screens.LeaderboardScreen;

public class LeaderboardScreenGame extends LeaderboardScreen {

  public LeaderboardScreenGame(HustleGame game) {
    super(game);
  }

  @Override
  protected void onExitPressed() {
    game.soundManager.playButton();
    dispose();
    game.setScreen(new GameOverScreen(game));
  }

  @Override
  protected String getExitButtonText() {
    return "Back";
  }
}
