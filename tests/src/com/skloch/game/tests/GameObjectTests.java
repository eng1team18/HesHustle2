package com.skloch.game.tests;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.skloch.game.gamelogic.GameObject;
import com.skloch.game.screens.GameScreen;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

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
