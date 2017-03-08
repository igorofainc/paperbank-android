package com.igorofa.paperbank.paperbank.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(PaperBankMainActivity.this, PaperBankFilesActivity.class));
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_paper_bank_main, menu);

        SearchView toolbarSearchView = (SearchView) findViewById(R.id.toolbar_search_view);

//        int search_plate_id =  toolbarSearchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
//        Log.d(PaperBankMainActivity.class.getSimpleName(), "" + search_plate_id);
        View search_plate = toolbarSearchView.findViewById(R.id.search_plate);
        if (search_plate != null){
            Log.d(PaperBankMainActivity.class.getSimpleName(), "Is search_plate really not null");
            search_plate.setBackgroundColor(Color.DKGRAY);

            int searchTextId = search_plate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) search_plate.findViewById(searchTextId);
            if (searchText!=null) {
                searchText.setTextColor(Color.WHITE);
                searchText.setHintTextColor(Color.WHITE);
            }
        }

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
