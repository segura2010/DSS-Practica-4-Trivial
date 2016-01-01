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
        db.execSQL("CREATE TABLE questions IF NOT EXISTS (id integer primary key, question text, a1 text, a2 text, a3 text, a4 text, correct integer, type integer)");

        if( getQuestionsNumber() <= 0 )
        {
            fillQuestions();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public int getQuestionsNumber()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "questions");
        return numRows;
    }

    private void fillQuestions()
    {   // Add questions to DB
        addQuestion( new Question( "¿Cuál es el país de origen del fútbol?", "España", "Inglaterra", "China", "Italia", 2, QuestionType.TEXT ) );
        addQuestion( new Question( "¿Qué otro deporte usaba en sus inicios una pelota de fútbol?", "Tenis", "Balonmano", "Waterpolo", "Baloncesto", 3, QuestionType.TEXT ) );
        addQuestion( new Question( "¿Quién inventó la expresión 'jogo bonito'?", "Maradona", "Pelé", "Ronaldinho", "Ronaldo", 3, QuestionType.TEXT ) );
        addQuestion( new Question( "¿Dónde se fabrican la mayoría de balones de fútbol?", "China", "Pakistan", "India", "Tailandia", 1, QuestionType.TEXT ) );
        addQuestion( new Question( "¿Cuántas veces desapareció el trofeo del Mundial?", "Dos", "Una", "Cuatro", "Ninguna", 0, QuestionType.TEXT ) );
        addQuestion( new Question( "¿Qué equipo no encajó ni un solo gol en toda la temporada?", "AC Milan (Serie A)", "Liverpool (Premier League)", "Athletic de Bilbao (Liga Española)", "Perugia (Serie A)", 3, QuestionType.TEXT ) );
        addQuestion( new Question( "¿De qué equipo era aficionado Bin Laden?", "Real Madrid (Liga Española)", "Liverpool (Premier League)", "FC Barcelona (Liga Española)", "Arsenal (Premier League)", 3, QuestionType.TEXT ) );
    }

    private void addQuestion(Question q)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("question", q.getQuestion());
        contentValues.put("a1", q.getAnswer(0));
        contentValues.put("a2", q.getAnswer(0));
        contentValues.put("a3", q.getAnswer(0));
        contentValues.put("a4", q.getAnswer(0));
        contentValues.put("correct", q.getCorrectAnswer());
        contentValues.put("type", q.getType().ordinal());

        db.insert("contacts", null, contentValues);
    }

    public ArrayList<Question> getAllQuestions()
    {
        ArrayList<Question> questions = new ArrayList<Question>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from questions", null );
        res.moveToFirst();

        while(res.isAfterLast() == false)
        {
            Question q = new Question( res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getInt(6), QuestionType.values()[res.getInt(7)] );
            questions.add( q );
            res.moveToNext();
        }
        return questions;
    }

}
