package com.igorofa.paperbank.paperbank.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.igorofa.paperbank.paperbank.R;

/**
 * Created by laz on 25/02/17.
 */

public class PaperBankFilesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files_paper_bank);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView filesTitle = (TextView) findViewById(R.id.toolbar_paper_bank_title);
            filesTitle.setText(R.string.files);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
}
