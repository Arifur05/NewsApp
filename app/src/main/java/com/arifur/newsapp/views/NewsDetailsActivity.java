package com.arifur.newsapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.arifur.newsapp.R;
import com.arifur.newsapp.model.Article;
import com.bumptech.glide.Glide;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String TAG = "NewsDetailsActivity";

    private Article mArticle;
    private WebView superSafeWebView;
    private boolean safeBrowsingIsInitialized;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        superSafeWebView = (WebView) findViewById(R.id.webview);


        getIncomingIntent();
        WebSettings webSettings = superSafeWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        WebViewClient webViewClient = new WebViewClientImpl(this);
        superSafeWebView.setWebViewClient(webViewClient);

        superSafeWebView.loadUrl(mArticle.getUrl());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("article")) {
            mArticle = getIntent().getParcelableExtra("article");
            Log.d(TAG, "getIncomingIntent: " + mArticle.getTitle());
            Log.d(TAG, "getIncomingContent: " + mArticle.getContent());

        }
    }
}