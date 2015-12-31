package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import java.util.ArrayList;

/**
 * Created by alberto on 31/12/15.
 */
public enum QuestionResource {
    // Singleton
    INSTANCE;

    // Questions arraylist retrieved from the DB
    ArrayList<Question> questions;

    int lastQuestion;

    QuestionResource()
    {
        // get questions from DB
        getNewQuestions();
    }

    public void getNewQuestions()
    {   // Get new questions from DB
        lastQuestion = 0;
    }

    public Question getOneQuestion()
    {
        lastQuestion++;
        return questions.get(lastQuestion);
    }

    public Question getActualQuestion()
    {
        return questions.get(lastQuestion);
    }

}
