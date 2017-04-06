package com.igorofa.paperbank.paperbank;

import android.app.Application;
import android.os.SystemClock;

import com.igorofa.paperbank.paperbank.models.PapersDataBaseWrapper;
import com.igorofa.paperbank.paperbank.viewModels.PBMainActivityViewModel;
import com.igorofa.paperbank.paperbank.viewModels.PBRecentActivityViewModel;
import com.squareup.leakcanary.LeakCanary;

import java.util.concurrent.TimeUnit;

/**
 * Created by x4b1d on 21/02/17.
 */

public final class PaperBankApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Leak Canary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        LeakCanary.install(this); // Normal app init code...

        PapersDataBaseWrapper.initializeDataBaseWrapper(PaperBankApp.this);

        //This is just so cold launches take some time
         SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));

    }


    public PBMainActivityViewModel getMainActivityViewModel() {
        return new PBMainActivityViewModel();
    }

    public PBRecentActivityViewModel getRecentActivityViewModel() {
        return new PBRecentActivityViewModel();
    }
}
