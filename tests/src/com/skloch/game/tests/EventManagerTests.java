package com.skloch.game.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.skloch.game.scoring.Achievement;
import com.skloch.game.gamelogic.Energy;
import com.skloch.game.gamelogic.EventManager;
import com.skloch.game.screens.GameScreen;
import com.skloch.game.gamelogic.Player;
import com.skloch.game.scoring.Score;
import com.skloch.game.gamelogic.Time;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs unit tests on the core EventManager class.
 */
@FixMethodOrder
@RunWith(GdxTestRunner.class)
public class EventManagerTests {
  Achievement achievements = Achievement.getInstance();
  Score score = Score.getInstance();

  GameScreen game = mock(GameScreen.class);
  Viewport viewport = mock(FitViewport.class);
  Energy energy = new Energy(viewport);
  Time time = new Time(game);
  Player player = mock(Player.class);
  EventManager eventManager = new EventManager(game, energy, time, player);

  /**
   * Tests that the tree event will give the appropriate achievement and score increase only once.
   */
  @Test
  public void testTreeEvent() {
    score.resetScores();
    eventManager.event("tree");
    assertTrue(achievements.checkAchievement(1));
    eventManager.event("tree");
    assertEquals(score.getTotalScore(), 100);
  }

  /**
   * Tests that the friends event will not activate when the player doesn't have enough energy.
   * This is tested by checking if the time moves.
   */
  @Test
  public void testFriendsEventNoEnergy() {
    energy.setEnergy(0);
    time.daySeconds = (8 * 60);
    eventManager.event("friends");
    assertEquals(time.getSeconds(), 8*60, 1e-15);
  }

  /**
   * Tests that the friends event, called without a second topic arg, will not activate the main
   * event, as it should only generate a random topics list for the player to choose from.
   */
  @Test
  public void testFriendsEventWithoutTopic() {
    energy.setEnergy(100);
    time.daySeconds = (8 * 60);
    score.resetScores();
    eventManager.event("friends");
    assertFalse(time.getSeconds() > 8*60);
    assertFalse(energy.getEnergy() < 100);
    assertFalse(score.getTotalScore() > 0);
  }

  /**
   * Tests that the friends event with a topic will correctly activate with enough energy, this
   * should move time forward, reduce energy and increment the score.
   */
  @Test
  public void testFriendsEventWithTopic() {
    energy.setEnergy(100);
    time.daySeconds = (8 * 60);
    score.resetScores();
    eventManager.event("friends-exams");
    assertTrue(time.getSeconds() > 8*60);
    assertTrue(energy.getEnergy() < 100);
    assertTrue(score.getTotalScore() > 0);
  }

  /**
   * Tests that Ron Cooke event correctly activates and updates the score, time and energy values.
   */
  @Test
  public void testRonCookeEvent() {
    energy.setEnergy(100);
    time.daySeconds = (8 * 60);
    score.resetScores();
    eventManager.event("ron_cooke");
    assertTrue(time.getSeconds() > 8*60);
    assertTrue(energy.getEnergy() < 100);
    assertTrue(score.getTotalScore() > 0);
  }

  /**
   * Tests that Ron Cooke event will not activate if the player doesn't have enough energy. We do
   * this by checking the time and score do not move.
   */
  @Test
  public void testRonCookeEventNoEnergy() {
    energy.setEnergy(0);
    time.daySeconds = (8 * 60);
    score.resetScores();
    eventManager.event("ron_cooke");
    assertFalse(time.getSeconds() > 8*60);
    assertFalse(score.getTotalScore() > 0);
  }

  /**
   * Tests that Piazza event will not activate if the player doesn't have enough energy. We do
   * this by checking the time and score do not move.
   */
  @Test
  public void testPiazzaEventNoEnergy() {
    energy.setEnergy(0);
    time.daySeconds = (8 * 60);
    score.resetScores();
    eventManager.event("piazza");
    assertFalse(time.getSeconds() > 8*60);
    assertFalse(score.getTotalScore() > 0);
  }

  /**
   * Tests that Piazza event correctly activates and updates the score, time and energy values.
   */
  @Test
  public void testPiazzaEvent() {
    energy.setEnergy(100);
    time.daySeconds = (8 * 60);
    score.resetScores();
    eventManager.event("piazza");
    assertTrue(time.getSeconds() > 8*60);
    assertTrue(energy.getEnergy() < 100);
    assertTrue(score.getTotalScore() > 0);
  }

  /**
   * Tests that accommodation event correctly activates and updates the score, time and energy
   * values.
   */
  @Test
  public void testAccomEvent() {
    time.daySeconds = (8 * 60);
    eventManager.event("accomodation");
  }
}
