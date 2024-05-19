package com.skloch.game.tests;

import static java.nio.file.Files.deleteIfExists;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.skloch.game.GameObject;
import com.skloch.game.HustleGame;
import com.skloch.game.LeaderboardScreenGame;
import com.skloch.game.Player;
import java.io.IOException;
import java.nio.file.Paths;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs unit tests on the core Player class.
 */
@FixMethodOrder
@RunWith(GdxTestRunner.class)
public class PlayerTests {
  Player player = new Player("avatar1");

  @Test
  public void testSetPos() {
    player.setPos(20, 30);
    assertEquals(player.getX(), 20f, 1e-15);
    assertEquals(player.getCentreX(), player.sprite.getX() + player.sprite.getWidth() / 2, 1e-15);
    assertEquals(player.getY(), 30f, 1e-15);
    assertEquals(player.getCentreY(), player.sprite.getY() + player.sprite.getHeight() / 2, 1e-15);

    Vector3 vector = new Vector3(20, 30, 0);
    assertEquals(vector, player.getPosAsVec3());
  }
  @Test
  public void testSetFrozen() {
    player.setFrozen(true);
    assertTrue(player.isFrozen());
  }

  @Test
  public void testSetUnfrozen() {
    player.setFrozen(false);
    assertFalse(player.isFrozen());
  }

  @Test
  public void testGetCurrentFrame() {
    assertEquals(player.getCurrentFrame(), player.currentFrame);
  }

  @Test
  public void testSetMoving() {
    player.setMoving(true);
    assertTrue(player.isMoving());
  }

  @Test
  public void testSetRunning() {
    player.setRunning(true);
    assertTrue(player.isRunning());
  }

  @Test
  public void testAddCollidable() {
    assertFalse(player.nearObject());
    GameObject building = new GameObject(30, 30, 30, 30);
    building.put("event", "ducks");
    player.addCollidable(building);
    player.setFrozen(false);
    player.setPos(25,25);
    player.move(2f);
    assertNotNull(player.getClosestObject());
    assertTrue(player.nearObject());
  }

  @Test
  public void testOverTopBound() {
    player.setBounds(new Rectangle(0, 0, 100, 100));
    player.setFrozen(false);
    player.setPos(0, 200);
    player.move(30f);
    assertEquals(player.getY(), player.bounds.getHeight() - player.feet.getHeight(), 1e-15);
  }

  @Test
  public void testUnderBottomBound() {
    player.setBounds(new Rectangle(0, 0, 100, 100));
    player.setFrozen(false);
    player.setPos(0, -200);
    player.move(30f);
    assertEquals(player.getY(), player.bounds.getY(), 1e-15);
  }

  @Test
  public void testLeftOfLeftBound() {
    player.setBounds(new Rectangle(0, 0, 100, 100));
    player.setFrozen(false);
    player.setPos(-200, 0);
    player.move(30f);
    assertEquals(player.getX(), player.bounds.getX() - 4 * player.scale, 1e-15);
  }

  @Test
  public void testRightOfRightBound() {
    player.setBounds(new Rectangle(0, 0, 100, 100));
    player.setFrozen(false);
    player.setPos(200, 0);
    player.move(30f);
    assertEquals(player.getX(), (player.bounds.getWidth() - player.feet.getWidth()) -
        (4 * player.scale), 1e-15);
  }
}
