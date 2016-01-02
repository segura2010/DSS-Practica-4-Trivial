package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alberto on 2/1/16.
 */
public class GameResultsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameresults_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get intent and data
        Intent i = getIntent();

        int corrects = i.getIntExtra("corrects", 0);
        int fails = i.getIntExtra("fails", 0);

        showStats(corrects, fails);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return true;
        }
    }

    public void showStats(int corrects, int fails)
    {
        ListView list = (ListView)(findViewById(R.id.endGameListView));

        User u = UserResource.INSTANCE.getUser();

        String[] fromMapKey = new String[] {"text1", "text2"};
        int[] toLayoutId = new int[] {android.R.id.text1, android.R.id.text2};

        List<Map<String, String> > l = new ArrayList<Map<String, String> >();
        Map<String, String> items = new HashMap<String, String>();

        items.put("text1", "Aciertos de esta partida");
        items.put("text2", Integer.toString(corrects));
        l.add(items);

        items = new HashMap<String, String>();
        items.put("text1", "Fallos de esta partida");
        items.put("text2", Integer.toString(fails));
        l.add(items);

        items = new HashMap<String, String>();
        items.put("text1", "Aciertos Totales");
        items.put("text2", Integer.toString(u.getCorrectQuestions()));
        l.add(items);

        items = new HashMap<String, String>();
        items.put("text1", "Fallos Totales");
        items.put("text2", Integer.toString(u.getFailedQuestions()));
        l.add(items);

        items = new HashMap<String, String>();
        items.put("text1", "Partidas Completas Jugadas");
        items.put("text2", Integer.toString(u.getTimesPlayed()));
        l.add(items);

        list.setAdapter(new SimpleAdapter(this, l, android.R.layout.simple_list_item_2, fromMapKey, toLayoutId));

    }

}
