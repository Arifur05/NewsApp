package com.arifur.newsapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.arifur.newsapp.R;

/**
 * @author : Arif
 * @date : 23-January-2021 03:05 AM
 * @package : com.arifur.newsapp.adapters
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class WorldNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView mArticleTitleTV;
    AppCompatImageView mArticleImage;
    public WorldNewsViewHolder(@NonNull View itemView) {
        super(itemView);
        mArticleTitleTV= itemView.findViewById(R.id.article_title);
        mArticleImage= itemView.findViewById(R.id.article_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
