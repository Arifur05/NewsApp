package com.arifur.newsapp.adapters;

import android.content.Context;
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
    private static final int TOP_HEADLINES = 1;
    private static final int ALL_NEWS = 2;
    private Context mContext;
    private List<Article> mArticles;
    private OnNewsListener mOnNewsListener;

    public WorldNewsHeadlinesAdapter(Context context, List<Article> articles) {
        mContext = context;
        mArticles = articles;
    }


    @NonNull
    @Override
    public WorldNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.headline_card, parent, false);

        return new WorldNewsViewHolder(view, mOnNewsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WorldNewsViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_launcher_background);
        ((WorldNewsViewHolder) holder).mArticleTitleTV.setText(mArticles.get(position).getTitle());
        Glide.with(((WorldNewsViewHolder)holder).itemView)
                .setDefaultRequestOptions(options)
                .load(mArticles.get(position).getUrlToImage())
                .into(((WorldNewsViewHolder)holder).mArticleImage);
    }

    @Override
    public int getItemCount() {
        if (mArticles != null) {
            return mArticles.size();
        }
        return 0;
    }



    public static class WorldNewsViewHolder extends RecyclerView.ViewHolder {
        TextView mArticleTitleTV;
        AppCompatImageView mArticleImage;
        OnNewsListener mOnNewsListener;

        public WorldNewsViewHolder(@NonNull View itemView, OnNewsListener mOnNewsListener) {
            super(itemView);
            this.mOnNewsListener = mOnNewsListener;
            mArticleTitleTV = itemView.findViewById(R.id.article_title);
            mArticleImage = itemView.findViewById(R.id.article_image);


        }
    }
}
