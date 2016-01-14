package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

/**
 * Created by alberto on 1/1/16.
 */
public class User {

    int id;
    int correctQuestions;
    int failedQuestions;
    int timesPlayed;

    int actualGameCorrects;
    int actualGameFails;

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
        actualGameCorrects++;
    }
    public int getCorrectQuestions() {
        return correctQuestions;
    }

    public void addFail()
    {
        failedQuestions++;
        actualGameFails++;
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

    public int getActualGameCorrects() {
        return actualGameCorrects;
    }

    public int getActualGameFails() {
        return actualGameFails;
    }

    public void resetActualGame()
    {
        actualGameFails = 0;
        actualGameCorrects = 0;
    }
}
