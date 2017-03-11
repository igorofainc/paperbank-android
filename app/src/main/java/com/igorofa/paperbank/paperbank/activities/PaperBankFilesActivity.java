package com.igorofa.paperbank.paperbank.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.igorofa.paperbank.paperbank.R;

/**
 * Created by laz on 25/02/17.
 */

public class PaperBankFilesActivity extends AbstractToolbarActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_files_paper_bank;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null){
            TextView filesTitle = (TextView) mToolbar.findViewById(R.id.toolbar_paper_bank_title);
            filesTitle.setText(R.string.files);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
}
