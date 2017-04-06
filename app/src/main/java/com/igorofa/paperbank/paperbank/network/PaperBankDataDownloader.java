package com.igorofa.paperbank.paperbank.network;

import com.igorofa.paperbank.paperbank.models.PapersDataBaseWrapper;

import java.util.concurrent.Executors;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by laz on 3/04/17.
 */

public final class PaperBankDataDownloader {
    private final Scheduler mSingleScheduer;
    private final BaseNetworkService mBaseNetworkService;

    public PaperBankDataDownloader(){

        mBaseNetworkService = BaseNetworkService.getNetworkService();
        mSingleScheduer = Schedulers.from(Executors.newSingleThreadExecutor());

    }

    private Retrofit acquireReConfiguredRetrofit(){
        return mBaseNetworkService.getBaseRetrofit().newBuilder()
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Completable getPapers(){
        return acquireReConfiguredRetrofit().create(PaperBankApi.class)
                .getPapers()
                .flatMapCompletable(PapersDataBaseWrapper.getThePaperBankDataBaseWrapper()::storeToDataBase)
                .subscribeOn(mSingleScheduer);
    }

}
