package com.skloch.game;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Achievement {
    private Map<Integer, AchievementEntry> achievements;
    private static final Achievement instance = new Achievement();

    public Achievement() {
        achievements = new HashMap<>();
        addAchievement(1, "Bookworm");
    }

    public static Achievement getInstance() {
        return instance;
    }

    private void addAchievement(int id, String name) {
        achievements.put(id, new AchievementEntry(id, name));
    }

    public void giveAchievement(int id) {
        AchievementEntry achievement = achievements.get(id);
        if (achievement != null && !achievement.isAchieved()) {
            achievement.setAchieved(true);
        }
    }

    public void resetAllAchievements() {
        for (AchievementEntry achievement : achievements.values()) {
            achievement.setAchieved(false);
        }
    }

    public String getUserAchievements() {
        return achievements.values().stream()
                .filter(AchievementEntry::isAchieved)
                .map(AchievementEntry::getName)
                .map(name -> "- " + name + "\n")
                .collect(Collectors.joining());
    }

    public class AchievementEntry {
        private int id;
        private String name;
        private boolean achieved;

        public AchievementEntry(int id, String name) {
            this.id = id;
            this.name = name;
            this.achieved = false;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public boolean isAchieved() {
            return achieved;
        }

        public void setAchieved(boolean achieved) {
            this.achieved = achieved;
        }
    }
}
