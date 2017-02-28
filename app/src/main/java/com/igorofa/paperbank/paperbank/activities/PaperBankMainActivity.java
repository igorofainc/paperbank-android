package com.igorofa.paperbank.paperbank.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.igorofa.paperbank.paperbank.PaperAdapter;
import com.igorofa.paperbank.paperbank.R;

public class PaperBankMainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    PaperAdapter mPaperAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_paper_bank);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        TextView titleText = (TextView) toolbar.findViewById(R.id.toolbar_paper_bank_title);
        titleText.setText(R.string.app_name);
        Typeface zwodrei = Typeface.createFromAsset(getAssets(), "fonts/zwodrei_bold_demo.ttf");
        titleText.setTypeface(zwodrei);

        mRecyclerView = (RecyclerView) findViewById(R.id.paper_bank_main_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPaperAdapter = new PaperAdapter(this);
        mRecyclerView.setAdapter(mPaperAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaperBankMainActivity.this, PaperBankFilesActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paper_bank_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
