package com.arifur.newsapp.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.arifur.newsapp.model.Article;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * @author : Arif
 * @date : 11-February-2021 03:05 AM
 * @package : com.arifur.newsapp.repository
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/

@Dao
public interface ArticleDao {

    @Insert(onConflict = REPLACE)
    void insertArticles(List<Article> article);

    @Query("DELETE FROM articles WHERE category= :category")
    void deleteArticleByCategory(String category);

    @Query("SELECT * FROM articles WHERE category=:category")
    LiveData<List<Article>> getArticleByCategory(String category);
}
