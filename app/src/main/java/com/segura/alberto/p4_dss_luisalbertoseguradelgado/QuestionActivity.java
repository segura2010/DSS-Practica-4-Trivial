package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

        Button exitBtn = (Button)(findViewById(R.id.exitBtn));

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

        Button pauseAudioBtn = (Button)(findViewById(R.id.pauseAudioBtn));
        pauseAudioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioController.INSTANCE.stop();
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

        // Put question on GUI elements
        questionText.setText(q.getQuestion());

        ListView list = (ListView)(findViewById(R.id.answersListView));

        // Audio Buttons
        Button playAudioBtn = (Button)(findViewById(R.id.playAudioBtn));
        Button pauseAudioBtn = (Button)(findViewById(R.id.pauseAudioBtn));
        // Hide buttons
        playAudioBtn.setVisibility(View.GONE);
        pauseAudioBtn.setVisibility(View.GONE);

        if( q.type == QuestionType.TEXT )
        {
            list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, q.getAnswers()));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    chooseQuestion(position);
                }
            });
        }
        else if( q.type == QuestionType.IMAGE )
        {
            String[] text = { "Imagen 1", "Imagen 2", "Imagen 3", "Imagen 4" };

            Integer[] images = {0,0,0,0};
            for(int i=0;i<q.getAnswers().size();i++)
            {
                images[i] = Integer.parseInt( q.getAnswer(i) );
            }

            list.setAdapter(new ImageList(this, text, images));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    chooseQuestion(position);
                }
            });
        }
        else if( q.type == QuestionType.AUDIO )
        {
            playAudioBtn.setVisibility(View.VISIBLE);
            pauseAudioBtn.setVisibility(View.VISIBLE);

            playAudioBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AudioController.INSTANCE.playSong(v.getContext(), QuestionResource.INSTANCE.getActualQuestion().getAudio());
                }
            });

            list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, q.getAnswers()));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    chooseQuestion(position);
                }
            });
        }
    }

    private void nextQuestion()
    {
        AudioController.INSTANCE.stop();

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
