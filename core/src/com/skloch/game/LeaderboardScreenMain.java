package com.skloch.game;

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
