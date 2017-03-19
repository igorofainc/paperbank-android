package com.igorofa.paperbank.paperbank.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.igorofa.paperbank.paperbank.PaperAdapter;
import com.igorofa.paperbank.paperbank.PaperBankApp;
import com.igorofa.paperbank.paperbank.R;
import com.igorofa.paperbank.paperbank.viewModels.PBMainActivityViewModel;
import com.igorofa.paperbank.paperbank.views.FloatingActionButtonWithHintText;
import com.igorofa.paperbank.paperbank.views.ToggleFloatingActionButton;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class PaperBankMainActivity extends AbstractToolbarActivity {
    private PBMainActivityViewModel mMainActivityViewModel;

    // Observer that publishes click events on the fab button
    PublishSubject<View> fabClicksPublish;
    CompositeDisposable mCompositeDisposable;
    ToggleFloatingActionButton mainFloatingActionButton;

//    FloatingActionButton uploadFileFloatingActionButton;
    FloatingActionButtonWithHintText recentFilesFloatingActionButton;

    PaperAdapter mPaperAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_main_paper_bank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivityViewModel = ((PaperBankApp)getApplicationContext()).getMainActivityViewModel();

        mPaperAdapter = new PaperAdapter(this, mMainActivityViewModel,mMainActivityViewModel.getPapers());
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

//        mCompositeDisposable.add(mPaperAdapter.getItemClickedSubject()
//                .map(clickedPaper -> mMainActivityViewModel.getFile(clickedPaper))
//                .subscribe(file -> {
//                    // tell the view model the paper item has been clicked
////                    RestService.getInstance().downloadVsSaveFile();
////                    try {
////                        File pdf = mMainActivityViewModel.getFile(clickedPaper);
////                        Intent intent = new Intent(Intent.ACTION_VIEW);
//////                        intent.setDataAndType(Uri.fromFile(pdf), MimeTypeMap.)
////                    }catch (FileNotDownloadedException e){
////                        Log.d(PaperBankMainActivity.class.getSimpleName(), e.getMessage());
////                    }
//                }));
    }

    private void unbind(){
        mCompositeDisposable.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbind();
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
}
