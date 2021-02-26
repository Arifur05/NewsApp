package com.arifur.newsapp.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arifur.newsapp.R;
import com.arifur.newsapp.adapters.AllNewsAdapter;
import com.arifur.newsapp.adapters.OnNewsListener;
import com.arifur.newsapp.model.Article;
import com.arifur.newsapp.util.Resource;
import com.arifur.newsapp.viewmodels.QueriedNewsViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


public class TechnologyFragment extends Fragment implements OnNewsListener {

    private static final String TAG = "TechnologyFragment";
    final String q = "technology";
    private RecyclerView mQueriedNewsRV;
    private AllNewsAdapter mAllNewsAdapter;
    QueriedNewsViewModel mQueriedNewsViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueriedNewsViewModel = new ViewModelProvider(this).get(QueriedNewsViewModel.class);
        subscribeObservers();
        getQueriedArticles(q);

    }

    private void subscribeObservers() {
        mQueriedNewsViewModel.getQueriedNewsArticle().observe(this, new Observer<Resource<List<Article>>>() {
            @Override
            public void onChanged(Resource<List<Article>> listResource) {
                if (listResource.data != null) {
                    mAllNewsAdapter.setArticle(listResource.data);
                } else {
                    Log.d(TAG, "onChanged: Queried Articles" + listResource.message);
                }
            }
        });
    }

    public void getQueriedArticles(String q) {

        mQueriedNewsViewModel.getQueriedNews(q);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_technology, container, false);
        mQueriedNewsRV = view.findViewById(R.id.tech_rv);
        initRecyclerView();
        return view;
    }

    public void initRecyclerView() {
        mAllNewsAdapter = new AllNewsAdapter(this, initGlide());
        mQueriedNewsRV.setAdapter(mAllNewsAdapter);
        mQueriedNewsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void onNewsClick(int position) {
        Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
        //intent.putExtra("headline",mWorldNewsHeadlinesAdapter.getSelectedArticle(position));
        intent.putExtra("article", mAllNewsAdapter.getSelectedArticle(position));
        startActivity(intent);
    }

    private RequestManager initGlide() {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }
}