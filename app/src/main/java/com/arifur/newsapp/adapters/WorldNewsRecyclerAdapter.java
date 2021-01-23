package com.arifur.newsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.arifur.newsapp.R;
import com.arifur.newsapp.model.Article;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Arif
 * @date : 23-January-2021 03:03 AM
 * @package : com.arifur.newsapp.adapters
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class WorldNewsRecyclerAdapter extends RecyclerView.Adapter<WorldNewsRecyclerAdapter.WorldNewsViewHolder> {
    private static final int TOP_HEADLINES = 1;
    private static final int ALL_NEWS = 2;
    private List<Article> mArticles;
    private OnNewsListener mOnNewsListener;

    public WorldNewsRecyclerAdapter(List<Article> articles) {
        mArticles = articles;
    }


    @NonNull
    @Override
    public WorldNewsRecyclerAdapter.WorldNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_example1,parent,false);

        return new WorldNewsViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull WorldNewsRecyclerAdapter.WorldNewsViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_launcher_background);
        ((WorldNewsViewHolder) holder).mArticleTitleTV.setText(mArticles.get(position).getTitle());
        Glide.with(((WorldNewsViewHolder)holder).itemView)
                .load(mArticles.get(position).getUrlToImage())
                .into(((WorldNewsViewHolder)holder).mArticleImage);
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }


    public class WorldNewsViewHolder extends RecyclerView.ViewHolder {
        TextView mArticleTitleTV;
        AppCompatImageView mArticleImage;
        public WorldNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mArticleTitleTV= itemView.findViewById(R.id.article_title);
            mArticleImage= itemView.findViewById(R.id.article_image);

            //itemView.setOnClickListener(this);
        }


    }
}
