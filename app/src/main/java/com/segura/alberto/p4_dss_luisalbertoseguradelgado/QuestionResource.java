package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by alberto on 31/12/15.
 */
public enum QuestionResource {
    // Singleton
    INSTANCE;

    // Questions arraylist retrieved from the DB
    ArrayList<Question> questions;

    DBHelper db;

    int lastQuestion;

    QuestionResource()
    {
    }

    public void setDB(DBHelper bd)
    {
        db = bd;
    }

    public void getNewQuestions()
    {   // Get new questions from DB
        lastQuestion = -1;
        questions = db.getAllQuestions();
    }

    public void shuffleQuestions()
    {
        Collections.shuffle(questions);
    }

    public Question getOneQuestion()
    {
        lastQuestion++;

        if( lastQuestion >= questions.size() )
        {   lastQuestion = -1;
            return null;
        }

        return questions.get(lastQuestion);
    }

    public Question getActualQuestion()
    {
        return questions.get(lastQuestion);
    }

}
