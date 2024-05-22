package com.skloch.game.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.skloch.game.gamelogic.Energy;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Runs unit tests on the core Energy class.
 */
@RunWith(GdxTestRunner.class)
public class EnergyTests {

  Viewport viewport = mock(FitViewport.class);
  Energy energyBar = new Energy(viewport);


  @Test
  public void testGetEnergy() {
    assertEquals(energyBar.getEnergy(), 100);
  }

  @Test
  public void testSetEnergy() {
    energyBar.setEnergy(16);
    assertEquals(energyBar.getEnergy(), 16);

    energyBar.setEnergy(1000);
    assertEquals(energyBar.getEnergy(), 100);
  }

  @Test
  public void testDecreaseEnergy() {
    energyBar.setEnergy(80);
    energyBar.decreaseEnergy(20);
    assertEquals(energyBar.getEnergy(), 60);

    energyBar.decreaseEnergy(200);
    assertEquals(energyBar.getEnergy(), 0);
  }
}

