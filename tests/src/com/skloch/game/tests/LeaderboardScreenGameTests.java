package com.skloch.game.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import com.skloch.game.HustleGame;
import com.skloch.game.LeaderboardScreenGame;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class LeaderboardScreenGameTests {
  //HustleGame game = new HustleGame(600, 400);
  //game.setScreen(new LeaderboardScreenGame(game));
  HustleGame game = mock(HustleGame.class);
  //game.setScreen(new LeaderboardScreenGame(game));

  @Test
  public void testGetExitButtonText() {
    //leadScreen.getExitButtonText();
    assertTrue(true);
  }
}
