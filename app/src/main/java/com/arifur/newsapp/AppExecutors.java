package com.arifur.newsapp;

import androidx.lifecycle.MutableLiveData;

import com.arifur.newsapp.model.Article;
import com.arifur.newsapp.requests.NewsApiClient;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author : Arif
 * @date : 11-January-2021 03:16 AM
 * @package : com.arifur.newsapp
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class AppExecutors {
    private static AppExecutors instance;
    //private MutableLiveData<List<Article>> mArticle;
    public static AppExecutors getInstance(){
        if (instance== null){
            instance= new AppExecutors();
        }
        return instance;
    }

    private final ScheduledExecutorService mNewtworkIO=
            Executors.newScheduledThreadPool(3);
    public ScheduledExecutorService getNewtworkIO(){
        return mNewtworkIO;
    }
}
