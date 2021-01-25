package com.arifur.newsapp.util;

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
            Executors.newScheduledThreadPool(5);
    public ScheduledExecutorService getNewtworkIO(){
        return mNewtworkIO;
    }
}
