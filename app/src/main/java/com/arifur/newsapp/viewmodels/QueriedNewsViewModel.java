package com.arifur.newsapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.arifur.newsapp.model.Article;
import com.arifur.newsapp.repository.NewsDataRepository;
import com.arifur.newsapp.util.Resource;

import java.util.List;

/**
 * @author : Arif
 * @date : 27-February-2021 12:53 AM
 * @package : com.arifur.newsapp.viewmodels
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class QueriedNewsViewModel extends AndroidViewModel {
    private static final String TAG = "QueriedNewsViewModel";

    private NewsDataRepository mNewsDataRepository;
    private MediatorLiveData<Resource<List<Article>>> queriedNews = new MediatorLiveData<>();

    public QueriedNewsViewModel(@NonNull Application application) {
        super(application);
        mNewsDataRepository = NewsDataRepository.getInstance(application);
    }


    public LiveData<Resource<List<Article>>> getQueriedNewsArticle() {
        return queriedNews;
    }


    public void getQueriedNews(String query) {
        LiveData<Resource<List<Article>>> allQueriedNewsResource = mNewsDataRepository.getQueriedNewsApi(query);
        queriedNews.addSource(allQueriedNewsResource, new Observer<Resource<List<Article>>>() {
            @Override
            public void onChanged(Resource<List<Article>> listResource) {
                if (listResource != null) {
                    if (listResource.status == Resource.Status.SUCCESS) {
                        if (listResource.data != null) {
                            Log.d(TAG, "onChanged: news" + listResource.data);
                            queriedNews.setValue(listResource);
                        }
                    }
                } else {
                    Log.d(TAG, "onChanged: Error Message" + listResource.message);
                }
            }
        });


    }
}
