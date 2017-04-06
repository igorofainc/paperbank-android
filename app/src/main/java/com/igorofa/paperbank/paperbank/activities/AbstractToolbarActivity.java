package com.igorofa.paperbank.paperbank.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.igorofa.paperbank.paperbank.R;

import io.reactivex.disposables.CompositeDisposable;

/**
 * This is abstract activity class that sets up the toolbar and recyclerview for their child classes
 * Created by laz on 9/03/17.
 */

public abstract class AbstractToolbarActivity extends AppCompatActivity {
    CompositeDisposable mCompositeDisposable;

    /**
     * Child activities that extend this abstract class have to implement this method
     * and return the layout file which has a toolbar (R.id.toolbar) and recyclerview (R.id.paper_bank_main_recycler_view)
     * @return int
     */
    @LayoutRes
    public abstract int getLayout();

    Toolbar mToolbar;
    RecyclerView mRecyclerView;


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

        mCompositeDisposable = new CompositeDisposable();

    }

    @Override
    protected void onStop() {
        super.onStop();
        unbind();
    }

    private void unbind(){
        mCompositeDisposable.clear();
    }
}
