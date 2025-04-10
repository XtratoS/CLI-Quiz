package com.sumerge.quiz;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Leaderboard {
    private List<LeaderboardEntry> entries;

    public Leaderboard() {
        this.entries = new ArrayList<>();
    }

    public void readFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.entries = mapper.readValue(new File("config/leaderboard.json"), Leaderboard.class).entries;
        } catch (IOException e) {
            System.err.println("Configuration file not found at config/leaderboard.json");
            throw new RuntimeException(e);
        }
    }

    public void writeToFile() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new MinimalPrettyPrinter());
        try {
            writer.writeValue(new File("config/leaderboard.json"), this);
        } catch (IOException e) {
            System.err.println("Failed to write leaderboard to config/leaderboard.json");
            throw new RuntimeException(e);
        }
    }

    public boolean addOrIgnoreEntry(String name, int score) {
        if (this.entries.size() < 10) {
            this.entries.add(new LeaderboardEntry(name, score));
            return true;
        }
        int minimumScore = entries.getFirst().getScore();
        for (LeaderboardEntry entry: this.entries) {
            int entryScore = entry.getScore();
            if (entryScore < minimumScore) {
                minimumScore = entryScore;
            }
        }
        if (score > minimumScore) {
            this.entries.add(new LeaderboardEntry(name, score));
            return true;
        }
        return false;
    }

    public List<LeaderboardEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LeaderboardEntry> entries) {
        this.entries = entries;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.entries.sort((o1, o2) -> o2.getScore() - o1.getScore());
        int i = 1;
        for (LeaderboardEntry entry: this.entries) {
            builder.append(String.format("%d. %s: %d\n", i++, entry.getName(), entry.getScore()));
        }
        return builder.toString();
    }
}
