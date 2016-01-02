package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import java.util.ArrayList;

/**
 * Created by alberto on 31/12/15.
 */
public class Question {

    String question;

    ArrayList<String> answers;

    int correctAnswer;

    QuestionType type;

    int audio;

    Question(String q, String a1, String a2, String a3, String a4, int c, QuestionType t, int au)
    {
        question = q;

        answers = new ArrayList<String>();
        answers.add(a1);
        answers.add(a2);
        answers.add(a3);
        answers.add(a4);

        correctAnswer = c;

        type = t;

        audio = au;
    }

    int getCorrectAnswer()
    {
        return correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public String getAnswer(int i) {
        return answers.get(i);
    }

    public QuestionType getType() {
        return type;
    }

    public int getAudio() {
        return audio;
    }
}
