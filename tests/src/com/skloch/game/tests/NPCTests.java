package com.skloch.game.tests;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.skloch.game.screens.GameScreen;
import com.skloch.game.gamelogic.NPC;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
public class NPCTests {
    private NPC npc = mock(NPC.class);

    @Test
    public void testGetCurrentFrame(){
        assertTrue(npc.getCurrentFrame() == npc.currentFrame);
    }

    @Test
    public void checkGetters(){
        assertTrue(npc.getX() == npc.centreX);
        assertTrue(npc.getY() == npc.centreY);
        assertTrue(npc.getDirection() == npc.direction);
    }

    @Test
    public void checkSetters(){
        npc.setX(5);
        assertTrue(npc.getX() == npc.centreX);
    }
}
