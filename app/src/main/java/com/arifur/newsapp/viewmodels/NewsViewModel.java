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
public class NewsViewModel extends ViewModel {


    private final NewsDataRepository mNewsDataRepository;
    //private NewsApiClient mNewsApiClient;

    public NewsViewModel() {
        mNewsDataRepository = NewsDataRepository.getInstance();
    }


    public LiveData<List<Article>> getWorldNewsHeadlinesArticle(){
        return mNewsDataRepository.getArticles();
    }
    public LiveData<List<Article>> getAllNewsArticle(){
        return mNewsDataRepository.getAllNews();
    }

    public LiveData<List<Article>> getQureiedNewsArticles(String q){
        return mNewsDataRepository.getQueriedNews(q);
    }
    public void getNewsHeadlines(String sources){
        mNewsDataRepository.getNewsHeadlines(sources);

    }
    public void getAllNews(){
        mNewsDataRepository.getAllNewsArticle();

    }
    public void getQueriedArticles(String q){
        mNewsDataRepository.getQueriedArticles(q);
    }
}
