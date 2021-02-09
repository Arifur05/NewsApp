package com.arifur.newsapp.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


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
    public static AppExecutors getInstance() {
        if (instance == null) {
            instance = new AppExecutors();
        }
        return instance;
    }

    private final Executor mDiskIO= Executors.newCachedThreadPool();

    private final Executor mMainThreadExecutor= new MainThreadExecutor();

    public Executor diskIO(){
        return mDiskIO;
    }

    public Executor mainThread(){
        return mMainThreadExecutor;
    }

    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler= new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }


}
