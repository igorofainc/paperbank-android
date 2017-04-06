package com.igorofa.paperbank.paperbank.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.igorofa.paperbank.paperbank.adapters.PaperMainViewAdapter;
import com.igorofa.paperbank.paperbank.PaperBankApp;
import com.igorofa.paperbank.paperbank.R;
import com.igorofa.paperbank.paperbank.viewModels.PBMainActivityViewModel;
import com.igorofa.paperbank.paperbank.views.FloatingActionButtonWithHintText;
import com.igorofa.paperbank.paperbank.views.ToggleFloatingActionButton;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class PaperBankMainActivity extends AbstractToolbarActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    private PBMainActivityViewModel mMainActivityViewModel;

    // Observer that publishes click events on the fab button
    PublishSubject<View> fabClicksPublish;
    ToggleFloatingActionButton mainFloatingActionButton;

//    FloatingActionButton uploadFileFloatingActionButton;
    FloatingActionButtonWithHintText recentFilesFloatingActionButton;

    PaperMainViewAdapter mPaperAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_main_paper_bank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showRequestPermission();

        mMainActivityViewModel = ((PaperBankApp)getApplicationContext()).getMainActivityViewModel();

        mPaperAdapter = new PaperMainViewAdapter();
        mRecyclerView.setAdapter(mPaperAdapter);

        if (getSupportActionBar() != null) {
            TextView titleText = (TextView) mToolbar.findViewById(R.id.toolbar_paper_bank_title);
            titleText.setText(R.string.app_name);
            Typeface zwodrei = Typeface.createFromAsset(getAssets(), "fonts/zwodrei_bold_demo.ttf");
            titleText.setTypeface(zwodrei);
        }
        fabClicksPublish = PublishSubject.create();

        mCompositeDisposable = new CompositeDisposable();

        mainFloatingActionButton = (ToggleFloatingActionButton) findViewById(R.id.floating_button);

//        uploadFileFloatingActionButton = (FloatingActionButton) findViewById(R.id.upload_file_floating_button);
        recentFilesFloatingActionButton = (FloatingActionButtonWithHintText) findViewById(R.id.recent_files_floating_button);
        recentFilesFloatingActionButton.setImageSrcRes(R.drawable.ic_recent_files); // set image src manually...setting through xml had issues

        mRecyclerView.setOnTouchListener((v, event) -> {

            mainFloatingActionButton.setStateOpen(false);
            return false;
        });
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
//                            uploadFileFloatingActionButton.setVisibility(View.VISIBLE);
                            recentFilesFloatingActionButton.setVisibility(View.VISIBLE);
                        }else {
//                            uploadFileFloatingActionButton.setVisibility(View.GONE);
                            recentFilesFloatingActionButton.setVisibility(View.GONE);
                        }
                    }));
        }

        mCompositeDisposable.add(recentFilesFloatingActionButton.getFabOnClickObservable()
                .subscribe(view -> startActivity(new Intent(PaperBankMainActivity.this, PaperBankFilesActivity.class)))
        );

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_paper_bank_main, menu);
//
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    private void showRequestPermission(){
        Log.d(PaperBankMainActivity.class.getSimpleName(), "called");

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            Log.d(PaperBankMainActivity.class.getSimpleName(), "no permission");

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "App needs storage permission to download papers, You can still change it in Apps settings", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
