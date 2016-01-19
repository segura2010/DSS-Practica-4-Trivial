package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
                                UserResource.INSTANCE.saveUser();
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


        // Prepare first question
        int lastQ = QuestionResource.INSTANCE.getLastQuestion();
        if(lastQ < 0)
        {   // First time
            UserResource.INSTANCE.getUser().resetActualGame();
            QuestionResource.INSTANCE.shuffleQuestions();
            nextQuestion();
        }
        else
        {
            Question q = QuestionResource.INSTANCE.getActualQuestion();
            showQuestion(q);
        }
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
        Question q = QuestionResource.INSTANCE.getOneQuestion();

        if( q == null )
        {   // Game Finished!
            Toast.makeText(getApplicationContext(), "Game Finished! \n Correct Answers: " + corrects + " \n Failed Answers: " + fails, Toast.LENGTH_SHORT).show();

            UserResource.INSTANCE.getUser().addGame();
            UserResource.INSTANCE.saveUser();

            Intent i = new Intent(this, GameResultsActivity.class);
            i.putExtra("corrects", UserResource.INSTANCE.getUser().getActualGameCorrects());
            i.putExtra("fails", UserResource.INSTANCE.getUser().getActualGameFails());
            startActivity(i);

            finish();
            return;
        }

        showQuestion(q);
    }

    private void chooseQuestion(int i)
    {
        Question q = QuestionResource.INSTANCE.getActualQuestion();

        String correct = "";
        if( q.getType() == QuestionType.IMAGE )
        {
            correct = "Imagen " + Integer.toString(q.getCorrectAnswer()+1);
        }
        else
        {
            correct = q.getAnswer(q.getCorrectAnswer());
        }

        if( q.getCorrectAnswer() == i )
        {   // it's correct!
            AudioController.INSTANCE.playSong(this, R.raw.grito);
            UserResource.INSTANCE.getUser().addCorrect();
            showCorrectQuestionAlert(correct);
        }
        else
        {
            AudioController.INSTANCE.playSong(this, R.raw.pitido);
            UserResource.INSTANCE.getUser().addFail();
            showIncorrectQuestionAlert(correct);
        }
    }

    public void showIncorrectQuestionAlert(String correctAnswer)
    {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Has fallado")
                .setMessage("La respuesta correcta era " + correctAnswer + " :( \n ¿Quieres seguir jugando o volver al menú?")
                .setPositiveButton("Seguir Jugando", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nextQuestion();
                    }

                })
                .setNegativeButton("Volver al Menú", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserResource.INSTANCE.saveUser();
                        QuestionResource.INSTANCE.getNewQuestions();
                        finish();
                    }
                })
                .show();
    }

    public void showCorrectQuestionAlert(String correctAnswer)
    {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Correcto!!")
                .setMessage("La respuesta correcta era " + correctAnswer + " :D \n ¿Quieres seguir jugando o volver al menú?")
                .setPositiveButton("Seguir Jugando", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nextQuestion();
                    }

                })
                .setNegativeButton("Volver al Menú", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserResource.INSTANCE.saveUser();
                        QuestionResource.INSTANCE.getNewQuestions();
                        finish();
                    }
                })
                .show();
    }

    // Disable back button
    @Override
    public void onBackPressed() {
    }

}
