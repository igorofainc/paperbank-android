package com.igorofa.paperbank.paperbank.viewModels;

import android.os.Environment;

import com.igorofa.paperbank.paperbank.mock.MockPapers;
import com.igorofa.paperbank.paperbank.models.ClickedPaper;
import com.igorofa.paperbank.paperbank.models.Paper;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;

/**
 * Created by laz on 14/03/17.
 * A view model for the recent activity's view
 * It is slightly different from main activity's view model in that if a file is not found it should tell
 * the view to display error
 */

public class PBRecentActivityViewModel implements IViewModel{

    public PBRecentActivityViewModel() {

    }


    @Override
    public Completable getFile(ClickedPaper paper) {
        File file = createFile(paper.getPaper().getId());

        return Completable.complete(); // TODO
    }

    @Override
    public List<Paper> getPapers() {
        List<Paper> paperList = MockPapers.setUpMockPapers();

        assert paperList != null;

        return paperList;
    }


    private File createFile(long paperId){
        String fileName = Long.toHexString(paperId).concat(".pdf");

        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
    }
}
