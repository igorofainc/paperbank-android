package com.igorofa.paperbank.paperbank.viewModels;

import android.os.Environment;
import android.util.Log;

import com.igorofa.paperbank.paperbank.mock.MockPapers;
import com.igorofa.paperbank.paperbank.models.ClickedPaper;
import com.igorofa.paperbank.paperbank.models.Paper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by laz on 14/03/17.
 * Main Activity's view model
 * If file not found tells view that it is trying to download and relays progress to the main activity
 */

public class PBMainActivityViewModel implements IViewModel {
    List<File> listCurrentDownloadingFiles;
    static int lo;

    private BehaviorSubject<Boolean> isDownloading;
    private PublishSubject<Integer> progressDownloadingSubject;

    public PBMainActivityViewModel() {

        listCurrentDownloadingFiles = new ArrayList<>();
    }


    @Override
    public Completable getFile(ClickedPaper paper) {

        lo++;
        Log.d(PBMainActivityViewModel.class.getSimpleName(), "lo = " +lo);

        isDownloading = BehaviorSubject.create();
        progressDownloadingSubject = PublishSubject.create();

        isDownloading.onNext(true);
//        return mPapersDataBaseWrapper.getFileDownloading(paper.getPaper(), progressDownloadingSubject)
//                .doAfterTerminate(() -> {
//                    setIsDownloading(false);
//                    progressDownloadingSubject.onComplete();
//                    isDownloading.onComplete();
//
//                })
//                .observeOn(AndroidSchedulers.mainThread());

        return Completable.complete(); // REMOVE THIS
    }

    @Override
    public List<Paper> getPapers() {
        return MockPapers.setUpMockPapers();
    }

    /**
     * This method is purely for testing MUST remove it later
     * @param paper
     * @return
     */
    private void checkPaperFile(Paper paper) {
        String fileName = Long.toHexString(paper.getId()).concat(".pdf");
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

        boolean isFileCurrentDownloading;
        Log.d(PBMainActivityViewModel.class.getSimpleName(), " " + listCurrentDownloadingFiles.size());
        if (!listCurrentDownloadingFiles.contains(file)){
            listCurrentDownloadingFiles.add(file);
            isFileCurrentDownloading = false;
        }else {
            isFileCurrentDownloading = true;
        }

        Log.d(PBMainActivityViewModel.class.getSimpleName(), "" + isFileCurrentDownloading);
    }

    public Observable<Boolean> getISDownloading() {
        return isDownloading;

    }

    public PublishSubject<Integer> getProgressDownloadingSubject() {
        return progressDownloadingSubject;
    }

    private void setIsDownloading(boolean downloading) {
        isDownloading.onNext(downloading);
    }

    public class PaperProgress {
        int index, progress;

        PaperProgress(int index, int progress) {

        }

        public int getIndex() {
            return index;
        }

        public int getProgress() {
            return progress;
        }
    }
}
