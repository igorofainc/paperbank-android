package com.igorofa.paperbank.paperbank.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.igorofa.paperbank.paperbank.PaperAdapter;
import com.igorofa.paperbank.paperbank.R;

/**
 * Created by laz on 9/03/17.
 */

public abstract class AbstractToolbarActivity extends AppCompatActivity {

    @LayoutRes
    public abstract int getLayout();

    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    PaperAdapter mPaperAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayout());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        mRecyclerView = (RecyclerView) findViewById(R.id.paper_bank_main_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPaperAdapter = new PaperAdapter(this);
        mRecyclerView.setAdapter(mPaperAdapter);
    }
}
