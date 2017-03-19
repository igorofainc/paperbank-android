package com.igorofa.paperbank.paperbank;

import android.app.Application;
import android.os.SystemClock;

import com.igorofa.paperbank.paperbank.viewModels.PBMainActivityViewModel;
import com.igorofa.paperbank.paperbank.viewModels.PBRecentActivityViewModel;

import java.util.concurrent.TimeUnit;

/**
 * Created by x4b1d on 21/02/17.
 */

public class PaperBankApp extends Application {
    private PBDataModel mDataModel;

    public PaperBankApp(){
        mDataModel = new PBDataModel();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //This is just so cold launches take some time
         SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));

    }

    private PBDataModel getDataModel() {
        return mDataModel;
    }

    public PBMainActivityViewModel getMainActivityViewModel() {
        return new PBMainActivityViewModel(getDataModel());
    }

    public PBRecentActivityViewModel getRecentActivityViewModel() {
        return new PBRecentActivityViewModel(getDataModel());
    }
}
