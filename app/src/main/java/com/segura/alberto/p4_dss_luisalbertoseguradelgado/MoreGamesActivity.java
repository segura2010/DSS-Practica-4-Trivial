package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

        loadWeb();
    }

    private void loadWeb()
    {
        WebView wv = (WebView)(findViewById(R.id.webView));

        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl("http://play.google.com/store/apps/category/GAME");
    }
}
