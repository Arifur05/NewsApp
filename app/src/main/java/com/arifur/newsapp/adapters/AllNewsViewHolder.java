package com.arifur.newsapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.arifur.newsapp.R;

/**
 * @author : Arif
 * @date : 26-January-2021 06:55 PM
 * @package : com.arifur.newsapp.adapters
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class AllNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    AppCompatImageView mAppCompatImageView;
    TextView mTitle, mDescription;
    OnNewsListener mOnNewsListener;
    Boolean isAllnewsItemClick= false;
    public AllNewsViewHolder(@NonNull View itemView,OnNewsListener mOnNewsListener) {
        super(itemView);
        this.mOnNewsListener=mOnNewsListener;
        mAppCompatImageView= itemView.findViewById(R.id.article_image);
        mTitle= itemView.findViewById(R.id.article_title);
        mDescription= itemView.findViewById(R.id.article_description);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            mOnNewsListener.onNewsClick(getAdapterPosition());
            isAllnewsItemClick= true;
    }
}
