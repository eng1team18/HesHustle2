package com.skloch.game;

/**
 * Counter to track the number of time you perform various activities within the game.
 * This includes the number of time you eat, sleep, study, and doing activities.
 */
public class Score {
    private int totalScore;

    private static final Score instance = new Score();

    private Score() {}

    public static Score getInstance() {
        return instance;
    }

    public int getTotalScore() { return totalScore; }

    public void incrementTotalScore(int value) { this.totalScore += value; }

    public void setTotalScore(int value) { this.totalScore = value; }

    /**
     * Resets the scores to 0
     */
    public void resetScore() {
        totalScore = 0;
    }
}
