package com.igorofa.paperbank.paperbank.network;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by laz on 27/03/17.
 */

final class BaseNetworkService {
    private static BaseNetworkService sInstance;

    private static final long CONNECTION_TIMEOUT = 40;

    private static final String BASE_URL = "http://paperdev.herokuapp.com/";

    private final Scheduler mScheduler;
    private static ReadWriteLock mReadWriteLock = new ReentrantReadWriteLock();

    private final OkHttpClient mOkHttpClient;

    private BaseNetworkService(){
        int threadCount = Runtime.getRuntime().availableProcessors();
        ExecutorService threadPoolExecutors = Executors.newFixedThreadPool(threadCount);
        mScheduler = Schedulers.from(threadPoolExecutors);

        mOkHttpClient = getClientBuilder().build();
    }

    @NonNull
    private OkHttpClient.Builder getClientBuilder() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        okBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        okBuilder.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        return okBuilder;
    }

    /**
     * Used a ReadWriteLock
     * we would only have one instance of the BaseNetworkService
     * Use of keyword synchronized would be too heavy for this usecase
     * @return Scheduler from Executor class
     */
    static BaseNetworkService getNetworkService() {
        mReadWriteLock.readLock().lock();
        if (sInstance == null) {
            mReadWriteLock.readLock().unlock();

            mReadWriteLock.writeLock().lock();

            sInstance = new BaseNetworkService();

            mReadWriteLock.writeLock().unlock();
            mReadWriteLock.readLock().lock();
        }

        try {
            return sInstance;
        }finally {
            mReadWriteLock.readLock().unlock();
        }
    }

    Scheduler getNetworkScheduler(){
        return mScheduler;
    }

    Retrofit getBaseRetrofit() {
        HttpUrl base_url = HttpUrl.parse(BASE_URL);

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getBaseOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    OkHttpClient getBaseOkHttpClient() {
        return mOkHttpClient;
    }
}
