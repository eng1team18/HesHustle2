package com.skloch.game.tests;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.skloch.game.screens.GameScreen;
import com.skloch.game.gamelogic.NPC;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(GdxTestRunner.class)
public class NPCTests {
    private NPC npc = mock(NPC.class);
    @Test
    public void testGetCurrentFrame(){
        assertTrue(npc.getCurrentFrame() == npc.currentFrame);
    }

    @Test
    public void checkGetX(){
        assertTrue(npc.getX() == npc.centreX);
    }

    @Test
    public void checkGetY(){
        assertTrue(npc.getY() == npc.centreY);
    }
    @Test
    public void checkGetDir(){
        assertTrue(npc.getDirection() == npc.direction);
    }

    @Test
    public void checkSetters(){
        npc.setDirection(0);
        assertTrue(npc.direction == 0);
    }
}
