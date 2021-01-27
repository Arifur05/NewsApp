package com.arifur.newsapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.arifur.newsapp.R;
import com.arifur.newsapp.model.Article;
import com.bumptech.glide.Glide;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String TAG = "NewsDetailsActivity";
    Article mArticle;
    TextView mTitle, mContent;
    AppCompatImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        initViews();
        getIncomingIntent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initViews(){
        mImage = findViewById(R.id.article_image);
        mTitle= findViewById(R.id.article_title);
        mContent= findViewById(R.id.article_content);
    }
    private void getIncomingIntent() {
        if (getIntent().hasExtra("article")) {
            mArticle = getIntent().getParcelableExtra("article");
            Log.d(TAG, "getIncomingIntent: " + mArticle.getTitle());
            Log.d(TAG, "getIncomingContent: "+mArticle.getContent());
            Glide.with(mImage).load(mArticle.getUrlToImage()).into(mImage);
            mTitle.setText(mArticle.getTitle());
            mContent.setText(mArticle.getContent());
        }
    }
}