package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by alberto on 31/12/15.
 */
public class QuestionActivity extends ActionBarActivity {

    int fails = 0;
    int corrects = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);

        // Prepare event listeners for answer question
        Button a1Btn = (Button)(findViewById(R.id.a1Btn));
        Button a2Btn = (Button)(findViewById(R.id.a2Btn));
        Button a3Btn = (Button)(findViewById(R.id.a3Btn));
        Button a4Btn = (Button)(findViewById(R.id.a4Btn));

        Button exitBtn = (Button)(findViewById(R.id.exitBtn));

        a1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseQuestion(0);
            }
        });

        a2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseQuestion(1);
            }
        });

        a3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseQuestion(2);
            }
        });

        a4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseQuestion(3);
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ask if user really wants to leave
                new AlertDialog.Builder(v.getContext())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Salir")
                .setMessage("¿Realmente quieres salir?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        QuestionResource.INSTANCE.getNewQuestions();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
            }
        });

        corrects = 0; fails = 0;

        QuestionResource.INSTANCE.shuffleQuestions();

        // Prepare first question
        nextQuestion();
    }

    private void showQuestion(Question q)
    {
        // Get GUI elements
        TextView questionText = (TextView)(findViewById(R.id.questionTxt));

        Button a1Btn = (Button)(findViewById(R.id.a1Btn));
        Button a2Btn = (Button)(findViewById(R.id.a2Btn));
        Button a3Btn = (Button)(findViewById(R.id.a3Btn));
        Button a4Btn = (Button)(findViewById(R.id.a4Btn));

        // Put question on GUI elements
        questionText.setText(q.getQuestion());

        a1Btn.setText(q.getAnswer(0));
        a2Btn.setText(q.getAnswer(1));
        a3Btn.setText(q.getAnswer(2));
        a4Btn.setText(q.getAnswer(3));
    }

    private void nextQuestion()
    {
        Question q = QuestionResource.INSTANCE.getOneQuestion();

        if( q == null )
        {   // Game Finished!
            Toast.makeText(getApplicationContext(), "Game Finished! \n Correct Answers: " + corrects + " \n Failed Answers: " + fails, Toast.LENGTH_SHORT).show();

            UserResource.INSTANCE.getUser().addGame();
            UserResource.INSTANCE.saveUser();

            finish();
            return;
        }

        showQuestion(q);
    }

    private void chooseQuestion(int i)
    {
        Question q = QuestionResource.INSTANCE.getActualQuestion();

        if( q.getCorrectAnswer() == i )
        {   // it's correct!
            Toast.makeText(getApplicationContext(), "Correct!!", Toast.LENGTH_SHORT).show();
            UserResource.INSTANCE.getUser().addCorrect();
            corrects++;
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Incorrect!!", Toast.LENGTH_SHORT).show();
            UserResource.INSTANCE.getUser().addFail();
            fails++;
        }

        nextQuestion();
    }

    // Disable back button
    @Override
    public void onBackPressed() {
    }

}
