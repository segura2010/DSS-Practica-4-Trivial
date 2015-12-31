package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by alberto on 31/12/15.
 */
public class QuestionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);

        // Prepare event listeners for answer question
        Button a1Btn = (Button)(findViewById(R.id.a1Btn));
        Button a2Btn = (Button)(findViewById(R.id.a2Btn));
        Button a3Btn = (Button)(findViewById(R.id.a3Btn));
        Button a4Btn = (Button)(findViewById(R.id.a4Btn));

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
        showQuestion(QuestionResource.INSTANCE.getOneQuestion());
    }

    private void chooseQuestion(int i)
    {}

}
