package com.sumerge.quiz;

import java.util.*;

public class QuizApplication {
    static int QUESTIONS_PER_LEVEL = 4;
    Leaderboard leaderboard;
    Quiz quiz;
    int questionCount;
    int currentQuestionIndex;
    User user;
    Scanner scanner;
    char response;

    public QuizApplication() {
        this.leaderboard = new Leaderboard();
        this.leaderboard.readFromFile();
        this.scanner = new Scanner(System.in);
        this.user = new User();
    }

    public void createQuiz() {
        QuestionsJSON questions = QuestionsJSON.readFromFile();
        List<Question> easyQuestions = questions.getEasy();
        List<Question> mediumQuestions = questions.getMedium();
        List<Question> hardQuestions = questions.getHard();
        // Shuffle questions
        Collections.shuffle(easyQuestions);
        Collections.shuffle(mediumQuestions);
        Collections.shuffle(hardQuestions);
        // Pick 4 questions from every set
        List<Question> randomQuestions = new ArrayList<>();
        randomQuestions.addAll(easyQuestions.subList(0, QUESTIONS_PER_LEVEL));
        randomQuestions.addAll(mediumQuestions.subList(0, QUESTIONS_PER_LEVEL));
        randomQuestions.addAll(hardQuestions.subList(0, QUESTIONS_PER_LEVEL));
        randomQuestions.forEach(Question::scrambleAnswers);
        // Create quiz
        this.quiz = new Quiz(randomQuestions);
        this.questionCount = this.quiz.getQuestions().size();
        this.currentQuestionIndex = 0;
    }

    private void registerUser() {
        char confirmation;
        String username;
        do {
            System.out.print("Please enter your username: ");
            username = this.scanner.next();

            System.out.printf("Are you sure you want to proceed as %s (Y/N): ", username);
            confirmation = this.scanner.next().toLowerCase().charAt(0);
        } while (confirmation != 'y');
        this.user.setName(username);
    }

    public void introduceUser() {
        System.out.printf("""
                Welcome to my quiz application.
                This quiz will consist of %d questions, with increasing difficulty.
                The progress will be shown after every question, and the leaderboard will be shown at the end.
                You can write 'q' to exit the program at any point (your progress will be lost)
                """, this.questionCount);
    }

    public void showProgress() {
        System.out.printf("Question (%d/%d)%n", this.currentQuestionIndex + 1, this.questionCount);
    }

    public void showLeaderboard() {
        System.out.println("== Leaderboard ==");
        System.out.println(this.leaderboard.toString());
    }

    public void askCurrentQuestionAndWaitForAnswer() {
        if (this.currentQuestionIndex >= this.questionCount) {
            return;
        }
        Question currentQuestion = this.quiz.getQuestion(this.currentQuestionIndex);
        this.showProgress();
        System.out.print(currentQuestion.toString());
        this.response = Character.toLowerCase(this.scanner.next().charAt(0));
        if (this.response >= 'a' && this.response <= 'd') {
            int answerIndex = this.response - 'a';
            this.user.addResponse(answerIndex);
            this.currentQuestionIndex++;
        } else if (this.response == 'q') {
            return;
        } else {
            System.out.println("Please enter one of the answers; A, B, C or D");
        }
        this.askCurrentQuestionAndWaitForAnswer();
    }

    public void Start() {
        this.introduceUser();
        this.registerUser();
        this.createQuiz();
        this.askCurrentQuestionAndWaitForAnswer();

        int score = this.quiz.calculateUserScore(user.getResponses());
        boolean added = this.leaderboard.addOrIgnoreEntry(user.getName(), score);
        if (added) {
            System.out.printf("You've achieved a new high score (%d) hence you'll be added to the leaderboard!\n", score);
            this.leaderboard.writeToFile();
        }
        this.showLeaderboard();
        System.out.println("Would you like to take another quiz? (Y for yes, any other key to quit)");
        this.response = Character.toLowerCase(this.scanner.next().charAt(0));
        if (this.response == 'y') {
            this.Start();
        }
    }
}
