package com.igorofa.paperbank.paperbank.viewModels;

import android.os.Environment;

import com.igorofa.paperbank.paperbank.PBDataModel;
import com.igorofa.paperbank.paperbank.models.ClickedPaper;

import java.io.File;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by laz on 14/03/17.
 * A view model for the recent activity's view
 * It is slightly different from main activity's view model in that if a file is not found it should tell
 * the view to display error
 */

public class PBRecentActivityViewModel implements IViewModel{
    private PBDataModel mPBDataModel;


    public PBRecentActivityViewModel(PBDataModel pbDataModel) {
        mPBDataModel = pbDataModel;
    }


    @Override
    public Completable getFile(ClickedPaper paper) {
        File file = createFile(paper.getPaper().getId());

        return mPBDataModel.doesTheFileExist(file)
                .subscribeOn(Schedulers.io())
                .map(PBDataModel.FileExists::getFile)
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements();
    }



    private File createFile(long paperId){
        String fileName = Long.toHexString(paperId).concat(".pdf");

        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
    }
}
