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

import java.util.List;

/**
 * @author : Arif
 * @date : 24-January-2021 02:42 AM
 * @package : com.arifur.newsapp.adapters
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class AllNewsAdapter extends RecyclerView.Adapter<AllNewsAdapter.AllNewsViewHolder> {
    private Context mContext;
    private List<Article> mArticles;

    public AllNewsAdapter(Context context, List<Article> articles) {
        mContext = context;
        mArticles = articles;
    }

    @NonNull
    @Override
    public AllNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_news_card,parent,false);
        return new AllNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllNewsViewHolder holder, int position) {
        holder.mTitle.setText(mArticles.get(position).getTitle());
        holder.mDescription.setText(mArticles.get(position).getDescription());
        Glide.with(((AllNewsViewHolder)holder).mAppCompatImageView)
                .load(mArticles.get(position).getUrlToImage())
                .into(((AllNewsViewHolder)holder).mAppCompatImageView);

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public class AllNewsViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView mAppCompatImageView;
        TextView mTitle, mDescription;
        public AllNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mAppCompatImageView= itemView.findViewById(R.id.article_image);
            mTitle= itemView.findViewById(R.id.article_title);
            mDescription= itemView.findViewById(R.id.article_description);
        }
    }
}
