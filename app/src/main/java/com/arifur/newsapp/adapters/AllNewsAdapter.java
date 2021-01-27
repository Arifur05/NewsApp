package com.arifur.newsapp.adapters;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Arif
 * @date : 24-January-2021 02:42 AM
 * @package : com.arifur.newsapp.adapters
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class AllNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Article> mArticles;
    private OnNewsListener mOnNewsListener;

    public AllNewsAdapter(OnNewsListener onNewsListener) {
        mOnNewsListener = onNewsListener;
        mArticles= new ArrayList<>();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_news_card,parent,false);
        return new AllNewsViewHolder(view,mOnNewsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((AllNewsViewHolder) holder).mTitle.setText(mArticles.get(position).getTitle());
        ((AllNewsViewHolder) holder).mDescription.setText(mArticles.get(position).getDescription());

        Glide.with(((AllNewsViewHolder)holder).mAppCompatImageView)
                .load(mArticles.get(position).getUrlToImage())
                .into(((AllNewsViewHolder)holder).mAppCompatImageView);
    }



    @Override
    public int getItemCount() {
        if (mArticles!=null){
            return mArticles.size();
        }
        return 0;
    }
    public void setArticle(List<Article> articleList){
        mArticles = articleList;
        notifyDataSetChanged();
    }

    public Article getSelectedArticle(int position){
        if(mArticles != null){
            if(mArticles.size()> 0){
                return mArticles.get(position);
            }
        }
        return null;
    }
}
