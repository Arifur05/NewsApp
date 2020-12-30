package com.arifur.newsapp.requests.response;

import com.arifur.newsapp.model.Article;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author : Arif
 * @date : 31-December-2020 02:09 AM
 * @package : com.arifur.newsapp.requests.response
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
public class NewsResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("totalResults")
    @Expose
    private String totalResults;

    @SerializedName("articles")
    @Expose
    private Article article;

    public String getStatus() {
        return status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public Article getArticle() {
        return article;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "status='" + status + '\'' +
                ", totalResults='" + totalResults + '\'' +
                ", article=" + article +
                '}';
    }
}
