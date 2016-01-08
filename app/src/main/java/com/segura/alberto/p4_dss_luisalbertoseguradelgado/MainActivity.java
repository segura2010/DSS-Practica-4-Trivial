package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set buttons listeners
        setListeners();

        db = new DBHelper(getApplicationContext());
        db.setUpTables();

        QuestionResource.INSTANCE.setDB(db);
        QuestionResource.INSTANCE.getNewQuestions();

        UserResource.INSTANCE.setDB(db);
        UserResource.INSTANCE.loadUser();
    }


    // Set listeners
    private void setListeners()
    {
        Button play = (Button)(findViewById(R.id.playBtn));
        // Set onclick listener for button
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), QuestionActivity.class);

                startActivity(i);
            }
        });

        Button statsBtn = (Button)(findViewById(R.id.statsBtn));
        // Set onclick listener for button
        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), StatsActivity.class);

                startActivity(i);
            }
        });

        Button moreGamesBtn = (Button)(findViewById(R.id.moreGamesBtn));
        // Set onclick listener for button
        moreGamesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MoreGamesActivity.class);

                startActivity(i);
            }
        });

        Button deleteBtn = (Button)(findViewById(R.id.deleteBD));
        // Set onclick listener for button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Eliminar datos")
                        .setMessage("¿Estás seguro de que quieres eliminar tus datos de juego completamente?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteTables();
                                db.setUpTables();
                                UserResource.INSTANCE.loadUser();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

}
