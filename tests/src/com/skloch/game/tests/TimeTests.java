package com.skloch.game.tests;


import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.skloch.game.screens.GameScreen;
import com.skloch.game.gamelogic.Time;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Runs unit tests on the core Time class.
 */
@RunWith(GdxTestRunner.class)
public class TimeTests {
    GameScreen game = mock(GameScreen.class);
    private final Time time = new Time(game);

    @Test
    public void testFormat(){
        assertEquals(time.formatTime(0), "12:00am");
        assertEquals(time.formatTime(600), "10:00am");
        assertEquals(time.formatTime(1040), "5:20pm");
        assertEquals(time.formatTime(1439), "11:59pm");
    }

    @Test
    public void testGetMeal(){
        assertEquals(time.getMeal(), "food");
        time.daySeconds = 600;
        assertEquals(time.getMeal(), "breakfast");
        time.daySeconds = 800;
        assertEquals(time.getMeal(), "lunch");
        time.daySeconds = 1200;
        assertEquals(time.getMeal(), "dinner");
    }

    @Test
    public void testPassTime(){
        time.daySeconds=0;
        time.passTime(100);
        assertTrue(time.daySeconds == 100);
        time.passTime(800);
        time.passTime(550);
        assertTrue(time.day == 2);
        assertTrue(time.daySeconds == 10);
    }

    @Test
    public void testGetters(){
        assertTrue(time.getSeconds() == time.daySeconds);
        time.hoursRecreational = 0;
        time.addRecreationalHours(10);
        assertTrue(time.hoursRecreational == 10);
        time.hoursSlept = 0;
        time.addSleptHours(10);
        assertTrue(time.hoursSlept == 10);
        time.hoursStudied = 0;
        time.addStudyHours(10);
        assertTrue(time.hoursStudied == 10);
    }
}