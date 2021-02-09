package com.arifur.newsapp.util;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.arifur.newsapp.requests.response.ApiResponse;

/**
 * @author : Arif
 * @date : 10-February-2021 02:02 AM
 * @package : com.arifur.newsapp.util
 * -------------------------------------------
 * Copyright (C) 2021 - All Rights Reserved
 **/
// ResultType: Type for the Resource database cache.
// RequestType: Type for the API response(network request).
abstract class NetworkBoundResource<CacheObject, RequestObject> {
    private static final String TAG = "NetworkBoundResource";
    private AppExecutors mAppExecutors;
    private MediatorLiveData<Resource<CacheObject>> results = new MediatorLiveData<>();

    public NetworkBoundResource(AppExecutors appExecutors) {
        mAppExecutors = appExecutors;
    }

    private void init() {

        // update Livedata for Loading Status
        results.setValue((Resource<CacheObject>) Resource.loading(null));

        //observe LiveData source from Local db
        final LiveData<CacheObject> dbSource = loadFromDb();

        results.addSource(dbSource, new Observer<CacheObject>() {
            @Override
            public void onChanged(CacheObject cacheObject) {
                results.removeSource(dbSource);
                if (shouldFetch(cacheObject)) {
                    // get data from network
                } else {
                    results.addSource(dbSource, new Observer<CacheObject>() {
                        @Override
                        public void onChanged(CacheObject cacheObject) {
                            setValue(Resource.success(cacheObject));
                        }
                    });
                }
            }
        });
    }

    /**
     * 1) observe local db
     * 2) if <condition/> query the network
     * 3) stop observing the local db
     * 4) insert new data into local db
     * 5) begin observing local db again to see the refreshed data from network
     * @param dbSource
     */
    private void fetchFromNetwork(final LiveData<CacheObject> dbSource){
        Log.d(TAG, "fetchFromNetwork: called");

        results.addSource(dbSource, new Observer<CacheObject>() {
            @Override
            public void onChanged(CacheObject cacheObject) {
                setValue(Resource.loading(cacheObject));
            }
        });

        final LiveData<ApiResponse<RequestObject>> apiResponse= createCall();

        results.addSource(apiResponse, new Observer<ApiResponse<RequestObject>>() {
            @Override
            public void onChanged(ApiResponse<RequestObject> requestObjectApiResponse) {
                results.removeSource(dbSource);
                results.removeSource(apiResponse);

                /*
                    3 cases:
                       1) ApiSuccessResponse
                       2) ApiErrorResponse
                       3) ApiEmptyResponse
                 */

                if(requestObjectApiResponse instanceof ApiResponse.ApiSuccessResponse){
                    Log.d(TAG, "onChanged: ApiSuccessResponse.");

                    mAppExecutors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {

                            // save the response to the local db
                            saveCallResult((RequestObject) processResponse((ApiResponse.ApiSuccessResponse)requestObjectApiResponse));

                            mAppExecutors.mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    results.addSource(loadFromDb(), new Observer<CacheObject>() {
                                        @Override
                                        public void onChanged(@Nullable CacheObject cacheObject) {
                                            setValue(Resource.success(cacheObject));
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
                else if(requestObjectApiResponse instanceof ApiResponse.ApiEmptyResponse){
                    Log.d(TAG, "onChanged: ApiEmptyResponse");
                    mAppExecutors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            results.addSource(loadFromDb(), new Observer<CacheObject>() {
                                @Override
                                public void onChanged(@Nullable CacheObject cacheObject) {
                                    setValue(Resource.success(cacheObject));
                                }
                            });
                        }
                    });
                }
                else if(requestObjectApiResponse instanceof ApiResponse.ApiErrrorResponse){
                    Log.d(TAG, "onChanged: ApiErrorResponse.");
                    results.addSource(dbSource, new Observer<CacheObject>() {
                        @Override
                        public void onChanged(@Nullable CacheObject cacheObject) {
                            setValue(
                                    Resource.error(
                                            ((ApiResponse.ApiErrrorResponse) requestObjectApiResponse).getErrormessage(),
                                            cacheObject
                                    )
                            );
                        }
                    });
                }
            }
        });
    }

    private CacheObject processResponse(ApiResponse.ApiSuccessResponse response){
        return (CacheObject) response.getBody();
    }

    private void setValue(Resource<CacheObject> newCacheValue) {
        if (results.getValue() != newCacheValue) {
            results.setValue(newCacheValue);
        }
    }

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestObject item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable CacheObject data);

    // Called to get the cached data from the database.
    @NonNull
    @MainThread
    protected abstract LiveData<CacheObject> loadFromDb();

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestObject>> createCall();

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    public final LiveData<Resource<CacheObject>> getAsLiveData() {
        return results;
    }
}
