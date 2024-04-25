package com.skloch.game.tests;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.skloch.game.Energy;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(GdxTestRunner.class)
public class EnergyTests {
    @Test
    public void testDecreaseEnergy() {
        Viewport viewport = new Viewport() {
            @Override
            public void apply() {
                super.apply();
            }
        };
        Energy energyBar = new Energy(viewport);
        int energyPoints = energyBar.getEnergy();

        energyBar.decreaseEnergy(20);
        assertEquals(energyPoints - 20, energyBar.getEnergy());
    }
}

