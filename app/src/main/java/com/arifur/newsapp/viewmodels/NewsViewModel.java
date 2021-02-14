package com.arifur.newsapp.viewmodels;


import android.app.Application;
import android.util.Log;

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
 * @date : 29-December-2020 09:32 PM
 * @package : com.arifur.newsapp.ViewModels
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
public class NewsViewModel extends AndroidViewModel {
    private static final String TAG = "NewsViewModel";

    private NewsDataRepository mNewsDataRepository;

    private MediatorLiveData<Resource<List<Article>>> articles = new MediatorLiveData<>();

    public NewsViewModel(Application application) {
        super(application);
        mNewsDataRepository = NewsDataRepository.getInstance(application);
    }


    public LiveData<Resource<List<Article>>> getWorldNewsHeadlinesArticle() {
        return articles;
    }

    public LiveData<List<Article>> getAllNewsArticle() {
        return null;
    }

    public LiveData<List<Article>> getQureiedNewsArticles(String q) {
        return null;
    }

    public void getNewsHeadlines() {
        final LiveData<Resource<List<Article>>> headlinesResource = mNewsDataRepository.getHeadlinesApi();
        articles.addSource(headlinesResource, new Observer<Resource<List<Article>>>() {
            @Override
            public void onChanged(Resource<List<Article>> listResource) {
                if (listResource != null) {
                    if (listResource.status == Resource.Status.SUCCESS) {
                        if (listResource.data != null) {
                            Log.d(TAG, "onChanged: " + listResource.data.size());
                            articles.setValue(listResource);
                        } else {
                            Log.d(TAG, "onChanged: " + listResource.message);
                        }
                    }
                }
            }
        });

    }

    public void getAllNews() {


    }


}
