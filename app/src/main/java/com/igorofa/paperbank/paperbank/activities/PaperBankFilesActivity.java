package com.igorofa.paperbank.paperbank.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.igorofa.paperbank.paperbank.PaperAdapter;
import com.igorofa.paperbank.paperbank.PaperBankApp;
import com.igorofa.paperbank.paperbank.R;
import com.igorofa.paperbank.paperbank.viewModels.PBRecentActivityViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by laz on 25/02/17.
 */

public class PaperBankFilesActivity extends AbstractToolbarActivity {
    CompositeDisposable mCompositeDisposable;
    private PBRecentActivityViewModel mRecentActivityViewModel;

    PaperAdapter mPaperAdapter;
    @Override
    public int getLayout() {
        return R.layout.activity_files_paper_bank;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecentActivityViewModel = ((PaperBankApp)getApplicationContext()).getRecentActivityViewModel();

        mPaperAdapter = new PaperAdapter(this, mRecentActivityViewModel);
        mRecyclerView.setAdapter(mPaperAdapter);

        if (getSupportActionBar() != null){
            TextView filesTitle = (TextView) mToolbar.findViewById(R.id.toolbar_paper_bank_title);
            filesTitle.setText(R.string.files);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbind();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bind();
    }

    private void bind(){
        mCompositeDisposable.add(mPaperAdapter.getItemClickedSubject()
                .subscribe(clickedPaper -> {
                    // tell the view model a paper has been clicked
                }));
    }

    private void unbind(){
        mCompositeDisposable.clear();
    }
}
