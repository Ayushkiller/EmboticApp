package com.theSelectors.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends Activity {

    private WebView webView;
    private static final String DASH_URL = "http://192.168.3.13:3000/login";
    private static final String PREFERENCES_NAME = "theSelectorsAppPreferences";
    private static final String KEY_LAST_URL = "lastUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);

        // Set a custom WebViewClient without SSL handling
        webView.setWebViewClient(new WebViewClient());

        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setClickable(true);

        // Load the last visited URL or default to DASH_URL
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        String lastUrl = prefs.getString(KEY_LAST_URL, DASH_URL);
        webView.loadUrl(lastUrl);

        // Set up button listener to switch URL
        Button btnDash = findViewById(R.id.btnDash);
        btnDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(DASH_URL);
                saveLastUrl(DASH_URL);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void saveLastUrl(String url) {
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_LAST_URL, url);
        editor.apply();
    }
}
