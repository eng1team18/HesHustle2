package com.skloch.game;

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
