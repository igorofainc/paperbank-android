package com.igorofa.paperbank.paperbank.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.igorofa.paperbank.paperbank.R;

/**
 * Created by x4b1d on 21/02/17.
 */

public class PaperBankGetStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_get_started_paper_bank);

        findViewById(R.id.get_started_text_view)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(PaperBankGetStartedActivity.this, PaperBankMainActivity.class));
                        finish();
                    }
                });
    }
}
