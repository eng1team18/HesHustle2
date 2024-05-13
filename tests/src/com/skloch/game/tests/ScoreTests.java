package com.skloch.game.tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.skloch.game.Achievement;
import com.skloch.game.Score;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

/**
 * Runs unit tests on the core Score class.
 */
@RunWith(GdxTestRunner.class)
public class ScoreTests {
    Score score = Score.getInstance();

    @Test
    public void testAllActivitiesExist(){
        //System.out.println(Arrays.asList(score.getActivities()));
        assertTrue(score.getActivities().containsKey(1));
        assertTrue(score.getActivities().containsKey(2));
        assertTrue(score.getActivities().containsKey(3));
        assertTrue(score.getActivities().containsKey(4));
        assertTrue(score.getActivities().containsKey(5));
    }
    @Test
    public void testNonExist(){
        //System.out.println(Arrays.asList(score.getActivities()));
        assertFalse(score.getActivities().containsKey(0));
        assertFalse(score.getActivities().containsKey(6));
        assertFalse(score.getActivities().containsKey(999999));
        assertFalse(score.getActivities().containsKey(-87));
        assertFalse(score.getActivities().containsKey("B"));
    }

    @Test
    public void testAddandReadScore(){
        score.incrementTotalScore(1, 5);
        score.incrementTotalScore(2, 10);
        score.incrementTotalScore(3, 15);
        score.incrementTotalScore(4, 20);
        score.incrementTotalScore(5, 25);
        assertEquals(5, score.getScoreByActivity(1));
        assertEquals(10, score.getScoreByActivity(2));
        assertEquals(15, score.getScoreByActivity(3));
        assertEquals(20, score.getScoreByActivity(4));
        assertEquals(25, score.getScoreByActivity(5));
        assertEquals(75, score.getTotalScore());
    }

    @Test
    public void testResetScore(){
        score.incrementTotalScore(1, 10);
        score.incrementTotalScore(2, 10);
        score.incrementTotalScore(3, 10);
        score.incrementTotalScore(4, 10);
        score.incrementTotalScore(5, 10);
        score.resetScores();
        assertEquals(0, score.getTotalScore());
    }

}
