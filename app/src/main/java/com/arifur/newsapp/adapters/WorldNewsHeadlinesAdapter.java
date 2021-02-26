package com.arifur.newsapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.arifur.newsapp.R;
import com.arifur.newsapp.model.Article;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * @author : Arif
 * @date : 23-January-2021 03:03 AM
 * @package : com.arifur.newsapp.adapters
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class WorldNewsHeadlinesAdapter extends RecyclerView.Adapter<WorldNewsHeadlinesAdapter.WorldNewsViewHolder> {

    private final List<Article> mArticles;
    private static final String TAG = "WorldNewsHeadlinesAdapt";
    private RequestManager requestManager;

    public WorldNewsHeadlinesAdapter(List<Article> articles, RequestManager requestManager) {
        mArticles = articles;
        this.requestManager = requestManager;
    }


    @NonNull
    @Override
    public WorldNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.headline_card, parent, false);

        return new WorldNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorldNewsViewHolder holder, int position) {
        if (mArticles != null) {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);
            ((WorldNewsViewHolder) holder).mArticleTitleTV.setText(mArticles.get(position).getTitle());
            Glide.with(((WorldNewsViewHolder) holder).itemView)
                    .setDefaultRequestOptions(options)
                    .load(mArticles.get(position).getUrlToImage())
                    .into(((WorldNewsViewHolder) holder).mArticleImage);
        } else {
            Log.d(TAG, "onBindViewHolder: " + mArticles.size());
        }
    }

    @Override
    public int getItemCount() {
        if (mArticles != null) {
            Log.d(TAG, "getItemCount: " + mArticles.size());
            return mArticles.size();
        }
        return 0;
    }


    public static class WorldNewsViewHolder extends RecyclerView.ViewHolder {
        TextView mArticleTitleTV;
        AppCompatImageView mArticleImage;


        public WorldNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mArticleTitleTV = itemView.findViewById(R.id.article_title);
            mArticleImage = itemView.findViewById(R.id.article_image);


        }
    }
}
