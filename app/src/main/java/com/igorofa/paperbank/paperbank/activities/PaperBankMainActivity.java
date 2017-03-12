package com.igorofa.paperbank.paperbank.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.igorofa.paperbank.paperbank.R;
import com.igorofa.paperbank.paperbank.views.ToggleFloatingActionButton;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class PaperBankMainActivity extends AbstractToolbarActivity {

    // Observer that publishes click events on the fab button
    PublishSubject<View> fabClicksPublish;
    CompositeDisposable mCompositeDisposable;
    ToggleFloatingActionButton mainFloatingActionButton;

    FloatingActionButton uploadFileFloatingActionButton, recentFilesFloatingActionButton;

    @Override
    public int getLayout() {
        return R.layout.activity_main_paper_bank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            TextView titleText = (TextView) mToolbar.findViewById(R.id.toolbar_paper_bank_title);
            titleText.setText(R.string.app_name);
            Typeface zwodrei = Typeface.createFromAsset(getAssets(), "fonts/zwodrei_bold_demo.ttf");
            titleText.setTypeface(zwodrei);
        }
        fabClicksPublish = PublishSubject.create();

        mCompositeDisposable = new CompositeDisposable();

        mainFloatingActionButton = (ToggleFloatingActionButton) findViewById(R.id.floating_button);

        uploadFileFloatingActionButton = (FloatingActionButton) findViewById(R.id.upload_file_floating_button);
        recentFilesFloatingActionButton = (FloatingActionButton) findViewById(R.id.recent_files_floating_button);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bind();
    }

    private void bind(){
        if (mainFloatingActionButton != null){
            mCompositeDisposable.add(mainFloatingActionButton.getStateOpenChangeObservable()
                    .subscribe(aBoolean -> {
                        if (aBoolean){
                            Toast.makeText(this, "The Fab is Open", Toast.LENGTH_SHORT).show();
                            uploadFileFloatingActionButton.setVisibility(View.VISIBLE);
                            recentFilesFloatingActionButton.setVisibility(View.VISIBLE);
                        }else {
                            uploadFileFloatingActionButton.setVisibility(View.GONE);
                            recentFilesFloatingActionButton.setVisibility(View.GONE);
                        }
                    }));
        }
    }

    private void unbind(){
        mCompositeDisposable.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbind();
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
