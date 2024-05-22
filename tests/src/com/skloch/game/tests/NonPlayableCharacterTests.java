package com.skloch.game.tests;

import com.skloch.game.gamelogic.NonPlayableCharacter;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(GdxTestRunner.class)
public class NonPlayableCharacterTests {

  private NonPlayableCharacter npc = mock(NonPlayableCharacter.class);

  @Test
  public void testGetCurrentFrame() {
    assertTrue(npc.getCurrentFrame() == npc.currentFrame);
  }

  @Test
  public void checkGetX() {
    assertTrue(npc.getX() == npc.centreX);
  }

  @Test
  public void checkGetY() {
    assertTrue(npc.getY() == npc.centreY);
  }

  @Test
  public void checkGetDir() {
    assertTrue(npc.getDirection() == npc.direction);
  }

  @Test
  public void checkSetters() {
    npc.setDirection(0);
    assertTrue(npc.direction == 0);
  }
}
