package com.igorofa.paperbank.paperbank;

import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

/**
 * Created by x4b1d on 21/02/17.
 */

public class PaperBankApp extends Application {
    private static PaperBankApp sPaperBankApp;

    public PaperBankApp(){
        sPaperBankApp = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //This is just so cold launches take some time
         SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
    }

    public static PaperBankApp getInstance(){
        return sPaperBankApp;
    }
}
