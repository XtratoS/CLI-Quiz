package com.sumerge.quiz;

import java.util.List;
import java.util.Random;

public class Question {
    private String subject;
    private String questionText;
    private List<String> answers;
    private int correctAnswer;

    public Question() {}

    public Question(String subject, String questionText, List<String> answers, int correctAnswer) {
        this.subject = subject;
        this.questionText = questionText;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public void scrambleAnswers() {
        int length = this.answers.size();
        Random random = new Random();
        for (int i=0; i<length; i++) {
            int newIndex = random.nextInt(length);
            String answer = this.answers.get(i);
            String newAnswer = this.answers.get(newIndex);
            this.answers.set(newIndex, answer);
            this.answers.set(i, newAnswer);
            // Swap correct answer if needed
            if (this.correctAnswer == i) {
                this.correctAnswer = newIndex;
            } else if (this.correctAnswer == newIndex) {
                this.correctAnswer = i;
            }
        }
    }

    private String displayAnswers() {
        StringBuilder stringBuilder = new StringBuilder();
        int length = this.answers.size();
        for (int i=0; i<length; i++) {
            String answer = this.answers.get(i);
            stringBuilder.append((char)(i+65));
            stringBuilder.append(". ");
            stringBuilder.append(answer);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String toString() {
        return String.join("\n", this.questionText, this.displayAnswers());
    }
}