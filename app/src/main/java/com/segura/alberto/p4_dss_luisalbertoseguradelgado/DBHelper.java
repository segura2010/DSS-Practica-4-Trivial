package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by alberto on 1/1/16.
 */
public class DBHelper extends SQLiteOpenHelper{
    // DB Helper Class

    public static final String DB_NAME = "my.sqlite";

    DBHelper(Context c)
    {
        super(c, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create question table if not exists
        db.execSQL("CREATE TABLE IF NOT EXISTS questions (id integer primary key, question text, a1 text, a2 text, a3 text, a4 text, correct integer, type integer)");
        db.execSQL("CREATE TABLE IF NOT EXISTS users (id integer primary key, correctQuestions integer, failedQuestions integer, timesPlayed integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public void setUpTables()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("CREATE TABLE IF NOT EXISTS questions (id integer primary key, question text, a1 text, a2 text, a3 text, a4 text, correct integer, type integer, audio integer)");
        db.execSQL("CREATE TABLE IF NOT EXISTS users (id integer primary key, correctQuestions integer, failedQuestions integer, timesPlayed integer)");

        if( getQuestionsNumber() <= 0 )
        {
            fillQuestions();
        }

        if( getUsersNumber() <= 0 )
        {
            addUser( new User(0, 0, 0, 0) );
        }
    }

    public void deleteTables()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Create question table if not exists
        db.execSQL("DROP TABLE IF EXISTS questions");

        db.execSQL("DROP TABLE IF EXISTS users");
    }


    public int getQuestionsNumber()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "questions");
        return numRows;
    }

    public int getUsersNumber()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "users");
        return numRows;
    }

    private void fillQuestions()
    {   // Add questions to DB

        // TEXT Questions
        addQuestion( new Question( "¿Cuál es el país de origen del fútbol?", "España", "Inglaterra", "China", "Italia", 2, QuestionType.TEXT, 0 ) );
        addQuestion( new Question( "¿Qué otro deporte usaba en sus inicios una pelota de fútbol?", "Tenis", "Balonmano", "Waterpolo", "Baloncesto", 3, QuestionType.TEXT, 0 ) );
        addQuestion( new Question( "¿Quién inventó la expresión 'jogo bonito'?", "Maradona", "Pelé", "Ronaldinho", "Ronaldo", 1, QuestionType.TEXT, 0 ) );
        addQuestion( new Question( "¿Dónde se fabrican la mayoría de balones de fútbol?", "China", "Pakistan", "India", "Tailandia", 1, QuestionType.TEXT, 0 ) );
        addQuestion( new Question( "¿Cuántas veces desapareció el trofeo del Mundial?", "Dos", "Una", "Cuatro", "Ninguna", 0, QuestionType.TEXT, 0 ) );
        addQuestion( new Question( "¿Qué equipo no encajó ni un solo gol en toda la temporada?", "AC Milan (Serie A)", "Liverpool (Premier League)", "Athletic de Bilbao (Liga Española)", "Perugia (Serie A)", 3, QuestionType.TEXT, 0 ) );
        addQuestion( new Question( "¿De qué equipo era aficionado Bin Laden?", "Real Madrid (Liga Española)", "Liverpool (Premier League)", "FC Barcelona (Liga Española)", "Arsenal (Premier League)", 3, QuestionType.TEXT, 0 ) );

        // IMAGE Questions
        addQuestion( new Question( "¿Quien es Messi?", Integer.toString(R.drawable.arbeloa), Integer.toString(R.drawable.messi), Integer.toString(R.drawable.pique), Integer.toString(R.drawable.cr), 1, QuestionType.IMAGE, 0 ) );
        addQuestion( new Question( "¿Quien es Cristiano Ronaldo?", Integer.toString(R.drawable.pique), Integer.toString(R.drawable.arbeloa), Integer.toString(R.drawable.cr), Integer.toString(R.drawable.messi), 2, QuestionType.IMAGE, 0 ) );
    }

    private void addQuestion(Question q)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("question", q.getQuestion());
        contentValues.put("a1", q.getAnswer(0));
        contentValues.put("a2", q.getAnswer(1));
        contentValues.put("a3", q.getAnswer(2));
        contentValues.put("a4", q.getAnswer(3));
        contentValues.put("correct", q.getCorrectAnswer());
        contentValues.put("type", q.getType().ordinal());

        contentValues.put("audio", q.getAudio());

        db.insert("questions", null, contentValues);
    }

    public ArrayList<Question> getAllQuestions()
    {
        ArrayList<Question> questions = new ArrayList<Question>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from questions", null );
        res.moveToFirst();

        while(res.isAfterLast() == false)
        {
            Question q = new Question( res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getInt(6), QuestionType.values()[res.getInt(7)], res.getInt(8) );
            questions.add( q );
            res.moveToNext();
        }
        return questions;
    }


    public void addUser(User u)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", u.getId());
        contentValues.put("correctQuestions", u.getCorrectQuestions());
        contentValues.put("failedQuestions", u.getFailedQuestions());
        contentValues.put("timesPlayed", u.getTimesPlayed());


        db.insert("users", null, contentValues);
    }

    public void updateUser(User u)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("correctQuestions", u.getCorrectQuestions());
        contentValues.put("failedQuestions", u.getFailedQuestions());
        contentValues.put("timesPlayed", u.getTimesPlayed());

        db.update("users", contentValues, "id = ? ", new String[]{Integer.toString(u.getId())});
    }

    public User getUser()
    {
        User u = null;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users", null );
        res.moveToFirst();

        while(res.isAfterLast() == false)
        {
            u = new User( res.getInt(0), res.getInt(1), res.getInt(2), res.getInt(3) );
            res.moveToNext();
        }
        return u;
    }

}
