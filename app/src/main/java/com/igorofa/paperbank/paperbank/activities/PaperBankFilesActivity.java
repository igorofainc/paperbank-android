package com.igorofa.paperbank.paperbank.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.igorofa.paperbank.paperbank.PaperBankApp;
import com.igorofa.paperbank.paperbank.R;
import com.igorofa.paperbank.paperbank.adapters.PaperMainViewAdapter;
import com.igorofa.paperbank.paperbank.viewModels.PBRecentActivityViewModel;

/**
 * Created by laz on 25/02/17.
 */

public class PaperBankFilesActivity extends AbstractToolbarActivity {
    private PBRecentActivityViewModel mRecentActivityViewModel;

    PaperMainViewAdapter mPaperAdapter;
    @Override
    public int getLayout() {
        return R.layout.activity_files_paper_bank;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecentActivityViewModel = ((PaperBankApp)getApplicationContext()).getRecentActivityViewModel();

        mPaperAdapter = new PaperMainViewAdapter();
        mRecyclerView.setAdapter(mPaperAdapter);

        if (getSupportActionBar() != null){
            TextView filesTitle = (TextView) mToolbar.findViewById(R.id.toolbar_paper_bank_title);
            filesTitle.setText(R.string.files);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        bind();
    }

    private void bind(){
//        mCompositeDisposable.add(mPaperAdapter.getItemClickedSubject()
//                .subscribe(clickedPaper -> {
//                    // tell the view model a paper has been clicked
//                }));
    }

}
