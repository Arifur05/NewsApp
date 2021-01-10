package com.arifur.newsapp.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.arifur.newsapp.model.Article;
import com.arifur.newsapp.repository.NewsDataRepository;
import com.arifur.newsapp.requests.NewsApiClient;

import java.util.List;

/**
 * @author : Arif
 * @date : 29-December-2020 09:32 PM
 * @package : com.arifur.newsapp.ViewModels
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
public class WorldNewsViewModel extends ViewModel {


    private NewsDataRepository mNewsDataRepository;
    private NewsApiClient mNewsApiClient;

    public WorldNewsViewModel() {
        mNewsDataRepository = NewsDataRepository.getInstance();
    }


    public LiveData<List<Article>> getWorldNewsArticle(){
        return mNewsDataRepository.getArticles();
    }
}
