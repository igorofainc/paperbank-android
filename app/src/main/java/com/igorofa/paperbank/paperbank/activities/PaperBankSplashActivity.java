package com.igorofa.paperbank.paperbank.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by x4b1d on 21/02/17.
 */

public class PaperBankSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, PaperBankGetStartedActivity.class);
        Log.d(this.getLocalClassName(), "laoding");
        startActivity(intent);
        finish();
    }
}
