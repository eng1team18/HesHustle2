package com.skloch.game.tests;

import static org.junit.Assert.assertTrue;

import com.skloch.game.gamelogic.GameObject;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs unit tests on the core GameObject class.
 */
@RunWith(GdxTestRunner.class)
public class GameObjectTests {

  private GameObject gameObject = new GameObject(1, 1, 1, 1);

  @Test
  public void testPut() {
    gameObject.put("name1", "name2");
    assertTrue(gameObject.properties.containsKey("name1"));
  }

  @Test
  public void testGet() {
    gameObject.put("name1", "name2");
    assertTrue(gameObject.get("name1") == "name2");
  }

  @Test
  public void containKey() {
    gameObject.put("name1", "name2");
    assertTrue(gameObject.containsKey("name1"));
  }

  @Test
  public void testCentreY() {
    gameObject.setCentreY(5);
    assertTrue(gameObject.centreY == 5);
  }

  @Test
  public void testCentreX() {
    gameObject.setCentreX(5);
    assertTrue(gameObject.centreX == 5);
  }

  @Test
  public void testSuper() {
    GameObject gamey = new GameObject(1, 1, 1, 1);
    assertTrue(gamey.centreX == 1.5);
    assertTrue(gamey.centreY == 1.5);
  }
}
