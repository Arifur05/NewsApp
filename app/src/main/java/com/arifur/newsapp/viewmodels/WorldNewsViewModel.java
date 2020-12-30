package com.arifur.newsapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.arifur.newsapp.model.Article;

import java.util.List;

/**
 * @author : Arif
 * @date : 29-December-2020 09:32 PM
 * @package : com.arifur.newsapp.ViewModels
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
public class WorldNewsViewModel extends AndroidViewModel {


    private MutableLiveData<List<Article>> mArticle = new MutableLiveData<>();

    public WorldNewsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Article>> getWorldNewsArticle(){
        return mArticle;
    }
}
