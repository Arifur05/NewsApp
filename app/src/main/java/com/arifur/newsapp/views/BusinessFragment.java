package com.arifur.newsapp.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arifur.newsapp.R;
import com.arifur.newsapp.adapters.WorldNewsHeadlinesAdapter;
import com.arifur.newsapp.model.Article;
import com.arifur.newsapp.requests.NewsApi;
import com.arifur.newsapp.requests.ServiceGenerator;
import com.arifur.newsapp.requests.response.NewsResponse;
import com.arifur.newsapp.viewmodels.NewsViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.arifur.newsapp.util.Constants.API_KEY;


public class BusinessFragment extends Fragment {
    NewsViewModel mNewsViewModel;
    private static final String TAG = "BusinessFragment";
    String q="business";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);


        //subscribeObservers();
        //getQueriedArticles(q);
        //testApiCall();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        return view;
    }

    private void subscribeObservers() {

    }

    public void getQueriedArticles(String q) {


    }
}