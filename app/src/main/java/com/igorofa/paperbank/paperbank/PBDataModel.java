package com.igorofa.paperbank.paperbank;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.igorofa.paperbank.paperbank.exceptions.FileNotDownloadedException;
import com.igorofa.paperbank.paperbank.models.Paper;
import com.igorofa.paperbank.paperbank.network.RestService;

import java.io.File;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by laz on 15/03/17.
 */

public class PBDataModel {
//    File mFileToBeLoaded;

    public PBDataModel(){

    }

    // also needs to be converted to observable
    public Observable<FileExists> doesTheFileExist(@NonNull final File fileToBeLoaded) {
        final boolean doesFileExist = fileToBeLoaded.exists();
//        return Observable.just(new FileExists(fileToBeLoaded, doesFileExist));

        return Observable.create(e -> {
            FileExists fileExists= new FileExists(fileToBeLoaded, doesFileExist);
            if (fileExists.isFileExists()){
                e.onNext(fileExists);
                e.onComplete();
            }else {
                e.onError(new FileNotDownloadedException(fileToBeLoaded));
            }

        });
    }

    public Observable<Paper> getLocalPapers(){
        return new DatabaseModel().getLocalPapers();
    }

    public Completable getFileDownloading(@NonNull Paper paperDownload, PublishSubject<Integer> progressPublish){
        File file = createFile(paperDownload.getId());
        if (file.exists()){
            return Completable.fromAction(() -> paperDownload.setLocalFileUrl(file.getAbsolutePath()))
                    .subscribeOn(Schedulers.io());
        }else {
            return RestService.getInstance().getFile(paperDownload.getOnlinePaperLink(), file, progressPublish)
                    .subscribeOn(Schedulers.io())
                    .doOnComplete(() -> paperDownload.setLocalFileUrl(file.getAbsolutePath()));
        }
    }

    private File createFile(long paperId) {
        String fileName = Long.toHexString(paperId).concat(".pdf");

        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
    }

    /**
     * part of the data model used to get data from the local database
     */
    class DatabaseModel{

        /**
         *
         * @return Observable of local papers from the local database
         */
        Observable<Paper> getLocalPapers(){
            return Observable.create(new ObservableOnSubscribe<Paper>() {
                @Override
                public void subscribe(ObservableEmitter<Paper> e) throws Exception {

                }
            });
        }
    }

//    /**
//     * part of the data model that initiates communication with network
//     */
//    class NetworkModel{
//
//    }

    public class FileExists{
        boolean isFileExists;
        File mFile;

        public FileExists(File file, boolean isFileExists) {
            this.isFileExists = isFileExists;
            mFile = file;
        }

        public boolean isFileExists() {
            return isFileExists;
        }

        public File getFile() {
            return mFile;
        }
    }
}
