package com.arifur.newsapp.adapters;

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
public class WorldNewsHeadlinesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TOP_HEADLINES = 1;
    private static final int ALL_NEWS = 2;
    private List<Article> mArticles;
    private OnNewsListener mOnNewsListener;

    public WorldNewsHeadlinesAdapter(OnNewsListener onNewsListener) {
        mOnNewsListener = onNewsListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.headline_card,parent,false);

        return new WorldNewsViewHolder(view,mOnNewsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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
