package com.igorofa.paperbank.paperbank.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by laz on 15/03/17.
 */

public class RestService {
    private static RestService sRestService;
    private final long CONNECTION_TIMEOUT = 40;
    private final String TEST_PDF_URL = "https://paperbankdo.s3.amazonaws.com/uploads/Physics_2013-paperbank.pdf";
    //    private final String TEST_PDF_URL = "http://www.pdf995.com/samples/pdf.pdf";
//    private final String TEST_PDF_URL = "http://www.google.com";
    private final String BASE_URL = "http://paperdev.herokuapp.com/";
    Scheduler mScheduler;

    private RestService() {

    }

    public static synchronized RestService getInstance() {
        if (sRestService == null) {
            sRestService = new RestService();
        }
        return sRestService;
    }

    Retrofit getDownloadsRetrofit(@Nullable PublishSubject<Integer> trackProgress) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(trackProgress))
                .build();
    }

//    public Observable<FileProgress> startPdfDownload(@NonNull final String url, @NonNull final File file) {
//        Observable<Integer> fileObservable = getDownloadsRetrofit().create(PaperBankApi.class)
//                .downloadPdfFile(url)
//                .subscribeOn(Schedulers.io())
////                .retry()
//                .flatMap(bodyResponse -> saveFile(bodyResponse, file));
////                .flatMap(this::saveFile);
//        Observable<File> destFileObservable = Observable.range(1, Integer.MAX_VALUE)
//                .map(number -> file);
//
//        return Observable.zip(destFileObservable, fileObservable, FileProgress::new)
//                .observeOn(AndroidSchedulers.mainThread());
//    }

//    public void downloadVsSaveFile() {
//        getDownloadsRetrofit().create(PaperBankApi.class)
//                .downloadPdfFile(TEST_PDF_URL)
//                .subscribeOn(Schedulers.io())
//                .retry()
//                .subscribe(responseBodyResponse -> {
//                    try {
//                        String fileName = "laz.pdf";
//                        Log.d(RestService.class.getSimpleName(), responseBodyResponse.code() + "");
//
//                        File file = new File(
//                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsoluteFile(), fileName);
//                        BufferedSink sink = Okio.buffer(Okio.sink(file));
//                        // you can access body of response
//                        sink.writeAll(responseBodyResponse.body().source());
//                        sink.close();
//                    } catch (IOException e) {
//                        Log.d(RestService.class.getSimpleName(), "Problem when writing to file");
//                    }
//                }, throwable -> {
//                    Log.d(RestService.class.getSimpleName(), "Error during download");
//                    Log.d(RestService.class.getSimpleName(), throwable.getMessage());
//                });
//    }

    private Observable<Integer> saveFile(final Response<ResponseBody> response, @NonNull final File file) {

//        // you can access headers of response
//        String header = response.headers().get("Content-Disposition");
//        // this is specific case, it's up to you how you want to save your file
//        // if you are not downloading file from direct link, you might be lucky to obtain file name from header
//        String fileName = header.replace("attachment; filename=", "");
//        // will create file in global Music directory, can be any other directory, just don't forget to handle permissions
        return Observable.create(e -> {
            BufferedSink sink = null;
            BufferedSource source = null;
            try {
                ResponseBody responseBody = response.body();
                long contentLength = responseBody.contentLength();

                sink = Okio.buffer(Okio.sink(file));
                source = responseBody.source();
                Buffer sinkBuffer = sink.buffer();

                long totalBytesRead = 0;
                long bytesRead;
                int bufferSize = 8 * 1024;
                // you can access body of response
                while ((bytesRead = source.read(sinkBuffer, bufferSize)) != -1) {
                    sink.emit();
                    totalBytesRead += bytesRead;

                    int progress = (int) ((totalBytesRead / contentLength) * 100);
                    e.onNext(progress);
                }

                sink.flush();
            }finally {
                Util.closeQuietly(sink);
                Util.closeQuietly(source);
            }
//            sink.writeAll(response.body().source());
//            sink.close();
        });
    }

    private Observable<File> saveWholeFile(Response<ResponseBody> response, File file){
        return Observable.create(e -> {
            BufferedSink sink = Okio.buffer(Okio.sink(file));
            // you can access body of response
            sink.writeAll(response.body().source());
            sink.close();
            e.onNext(file);
            e.onComplete();
        });
    }

    public Completable getFile(@NonNull final String url, @NonNull final File file, @NonNull PublishSubject<Integer> progress) {
        Log.d(RestService.class.getSimpleName(), url);
        return getDownloadsRetrofit(progress).create(PaperBankApi.class)
                .downloadPdfFile(url)
                .subscribeOn(Schedulers.io())
                .flatMap(bodyResponse -> saveWholeFile(bodyResponse, file))
                .map(file1 -> Log.d(Thread.currentThread().getName(), file.getAbsolutePath())) // purely for testing file download is on what thread
                .ignoreElements()
                .observeOn(AndroidSchedulers.mainThread());
    }

    private OkHttpClient getOkHttpClient(@Nullable final PublishSubject<Integer> tracker) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(chain -> {
            okhttp3.Response response = chain.proceed(chain.request());
            return response.newBuilder()
                    .body(new ProgressResponseBody(chain.proceed(chain.request()).body(), tracker))
                    .build();
        });

        clientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);

        return clientBuilder.build();
    }

    public class FileProgress {
        File downloadingFile;
        int currentProgress;

        public FileProgress(File downloadingFile, int currentProgress) {
            this.downloadingFile = downloadingFile;
            this.currentProgress = currentProgress;
        }

        public File getDownloadingFile() {
            return downloadingFile;
        }

        public int getCurrentProgress() {
            return currentProgress;
        }
    }
}
