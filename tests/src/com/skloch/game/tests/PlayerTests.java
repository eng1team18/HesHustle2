package com.skloch.game.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import com.skloch.game.HustleGame;
import com.skloch.game.LeaderboardScreenGame;
import com.skloch.game.Player;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs unit tests on the core Player class.
 */
@RunWith(GdxTestRunner.class)
public class PlayerTests {
  Player player = new Player("avatar1");

  @Test
  public void testGetX() {
    assertEquals(player.getX(), 0f, 1e-15);
  }

  @Test
  public void testGetY() {
    assertEquals(player.getY(), 0f, 1e-15);
  }
}
