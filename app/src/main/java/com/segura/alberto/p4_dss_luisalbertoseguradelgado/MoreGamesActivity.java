package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by alberto on 2/1/16.
 */
public class MoreGamesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moregames_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadWeb();
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

    private void loadWeb()
    {
        WebView wv = (WebView)(findViewById(R.id.webView));

        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl("http://play.google.com/store/apps/category/GAME");
    }
}
