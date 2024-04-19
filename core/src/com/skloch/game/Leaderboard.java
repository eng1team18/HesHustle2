package com.skloch.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Array;

public class Leaderboard {
    private static final String SCORE_FILE = "leaderboard.json";
    private Json json = new Json();
    private Array<ScoreEntry> scores = new Array<>();
    private static final Leaderboard instance = new Leaderboard();

    public static Leaderboard getInstance() {
        return instance;
    }

    public void saveScore(String playerName, int score) {
        FileHandle file = Gdx.files.local(SCORE_FILE);

        // Check if file exist and load if does
        if (file.exists()) {
            String scoresJson = file.readString();
            scores = json.fromJson(Array.class, ScoreEntry.class, scoresJson);
        }

        scores.add(new ScoreEntry(playerName, score));

        scores.sort();

        if (scores.size > 10) {
            scores.truncate(10);
        }

        file.writeString(json.toJson(scores, Array.class, ScoreEntry.class), false);
    }

    public Array<ScoreEntry> getTopScores() {
        FileHandle file = Gdx.files.local(SCORE_FILE);
        if (file.exists()) {
            String scoresJson = file.readString();
            return json.fromJson(Array.class, ScoreEntry.class, scoresJson);
        }
        return new Array<>();
    }

    public String getFormattedTopScores() {
        StringBuilder sb = new StringBuilder();
        Array<ScoreEntry> topScores = getTopScores();
        for (int i = 0; i < topScores.size; i++) {
            sb.append((i + 1) + ". " + topScores.get(i).playerName + " : " + topScores.get(i).score + "\n");
        }
        return sb.toString();
    }

    public static class ScoreEntry implements Comparable<ScoreEntry> {
        public String playerName;
        public int score;

        // Do not delete, ScoreEntry gets run when LeaderboardScreen is being displayed
        public ScoreEntry() {
        }

        public ScoreEntry(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }

        @Override
        public int compareTo(ScoreEntry other) {
            return Integer.compare(other.score, this.score);
        }
    }
}
