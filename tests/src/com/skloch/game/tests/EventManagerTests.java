package com.skloch.game.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import com.skloch.game.Achievement;
import com.skloch.game.Energy;
import com.skloch.game.EventManager;
import com.skloch.game.GameScreen;
import com.skloch.game.HustleGame;
import com.skloch.game.Player;
import com.skloch.game.Score;
import com.skloch.game.Time;
import jdk.jfr.Event;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs unit tests on the core EventManager class.
 */
@RunWith(GdxTestRunner.class)
public class EventManagerTests {
  Achievement achievements = Achievement.getInstance();
  Score score = Score.getInstance();

  GameScreen game = mock(GameScreen.class);
  Energy energy = mock(Energy.class);
  Time time = mock(Time.class);
  Player player = mock(Player.class);
  EventManager eventManager = new EventManager(game, energy, time, player);

  @Test
  public void testTreeEvent() {
    //eventManager.event("tree");
    //assertTrue(achievements.checkAchievement(1));
    //eventManager.event("tree");
    //assertEquals(score.getTotalScore(), 100);
    ;
  }
}
