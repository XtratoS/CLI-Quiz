package com.sumerge.quiz;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class QuestionsJSON {
    private List<Question> easy;
    private List<Question> medium;
    private List<Question> hard;

    public static QuestionsJSON readFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("config/questions.json"), QuestionsJSON.class);
        } catch (IOException e) {
            System.err.println("Configuration file not found at config/questions.json");
            throw new RuntimeException(e);
        }
    }

    public List<Question> getEasy() {
        return easy;
    }

    public void setEasy(List<Question> easy) {
        this.easy = easy;
    }

    public List<Question> getMedium() {
        return medium;
    }

    public void setMedium(List<Question> medium) {
        this.medium = medium;
    }

    public List<Question> getHard() {
        return hard;
    }

    public void setHard(List<Question> hard) {
        this.hard = hard;
    }
}
