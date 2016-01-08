package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * Created by alberto on 1/1/16.
 */
public class StatsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showStats();
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

    public void showStats()
    {
        ListView list = (ListView)(findViewById(R.id.statsListView));

        User u = UserResource.INSTANCE.getUser();

        String[] titles = {"Aciertos Totales", "Fallos Totales", "Partidas Completas Jugadas" };
        String[] descs = { Integer.toString(u.getCorrectQuestions()), Integer.toString(u.getFailedQuestions()), Integer.toString(u.getTimesPlayed()) };

        Integer[] images = { R.drawable.correct_icon, R.drawable.fails_icon, R.drawable.total_played_times_icon };

        list.setAdapter(new MyImageListAdapter(this, titles, descs, images));

        /*
        String[] fromMapKey = new String[] {"text1", "text2"};
        int[] toLayoutId = new int[] {android.R.id.text1, android.R.id.text2};

        List<Map<String, String> > l = new ArrayList<Map<String, String> >();
        Map<String, String> items = new HashMap<String, String>();

        items.put("text1", "Aciertos");
        items.put("text2", Integer.toString(u.getCorrectQuestions()));
        l.add(items);

        items = new HashMap<String, String>();
        items.put("text1", "Fallos");
        items.put("text2", Integer.toString(u.getFailedQuestions()));
        l.add(items);

        items = new HashMap<String, String>();
        items.put("text1", "Partidas Completas Jugadas");
        items.put("text2", Integer.toString(u.getTimesPlayed()));
        l.add(items);

        list.setAdapter(new SimpleAdapter(this, l, android.R.layout.simple_list_item_2, fromMapKey, toLayoutId));

        /*
        l.add(Integer.toString(u.getCorrectQuestions()) );
        l.add(Integer.toString(u.getFailedQuestions()));
        l.add(Integer.toString(u.getTimesPlayed()));
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, l));
        */
    }

}
