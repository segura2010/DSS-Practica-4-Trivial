package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

/**
 * Created by alberto on 1/1/16.
 */
public class User {

    int id;
    int correctQuestions;
    int failedQuestions;
    int timesPlayed;

    User(int i, int c, int f, int t)
    {
        id = i;
        correctQuestions = c;
        failedQuestions = f;
        timesPlayed = t;
    }

    public void addCorrect()
    {
        correctQuestions++;
    }
    public int getCorrectQuestions() {
        return correctQuestions;
    }

    public void addFail()
    {
        failedQuestions++;
    }
    public int getFailedQuestions() {
        return failedQuestions;
    }

    public void addGame()
    {
        timesPlayed++;
    }
    public int getTimesPlayed() {
        return timesPlayed;
    }

    public int getId() {
        return id;
    }
}
