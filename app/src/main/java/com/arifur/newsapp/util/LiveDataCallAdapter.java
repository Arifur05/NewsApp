package com.arifur.newsapp.util;

import android.os.Parcelable;

import androidx.lifecycle.LiveData;

import com.arifur.newsapp.requests.response.ApiResponse;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Arif
 * @date : 14-February-2021 02:17 AM
 * @package : com.arifur.newsapp.util
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {
    private Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(final Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            @Override
            protected void onActive() {
                super.onActive();
                final ApiResponse apiResponse = new ApiResponse();
                if (!call.isExecuted()) {
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(Call<R> call, Response<R> response) {
                            postValue(apiResponse.create(response));
                        }

                        @Override
                        public void onFailure(Call<R> call, Throwable t) {
                            postValue(apiResponse.create(t));
                        }
                    });
                }
            }
        };
    }
}
