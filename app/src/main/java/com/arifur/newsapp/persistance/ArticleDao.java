package com.arifur.newsapp.persistance;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.arifur.newsapp.model.Article;

import java.util.List;


import static androidx.room.OnConflictStrategy.IGNORE;
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

    @Insert(onConflict = IGNORE)
    long[] insertArticles(List<Article> articles);

    @Insert(onConflict = REPLACE)
    void insertArticles(Article article);

    @Query("SELECT * FROM news WHERE source_id= :source ORDER BY publishedAt DESC")
    LiveData<List<Article>> getHeadlines(String source);

    //@Query("UPDATE news SET title= :")

    @Query("SELECT * FROM news WHERE category=:category ORDER BY publishedAt DESC")
    LiveData<List<Article>> getArticleByCategory(String category);
}
