package com.sumerge.quiz;

import java.util.List;

public class Quiz {
    private List<Question> questions;
    private int score;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.score = 0;
    }

    public int calculateUserScore(List<Integer> responses) {
        int score = 0;
        int questionCount = this.questions.size();
        for (int i=0; i<questionCount; i++) {
            if (responses.get(i) == this.questions.get(i).getCorrectAnswer()) {
                score++;
            }
        }
        return score;
    }

    public Question getQuestion(int index) {
        return this.questions.get(index);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

