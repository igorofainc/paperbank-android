package com.igorofa.paperbank.paperbank.network;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by laz on 15/03/17.
 */

public class RestService {
    private final long CONNECTION_TIMEOUT = 40;
    private final String TEST_PDF_URL = "https://paperbankdo.s3.amazonaws.com/uploads/Physics_2013-paperbank.pdf";
    private final String BASE_URL = "http://paperdev.herokuapp.com/";

    private static RestService sRestService;

    private RestService(){

    }

    public static RestService getInstance() {
        if (sRestService == null){
            sRestService = new RestService();
        }
        return sRestService;
    }

    public Retrofit getDownloadsRetrofit(){
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
                .build();
    }

    public Observable<File> startPdfDownload(){
        return getDownloadsRetrofit().create(PaperBankApi.class)
                .downloadPdfFile(TEST_PDF_URL)
                .flatMap(this::saveFile);
    }

    public void downloadVsSaveFile(){
        getDownloadsRetrofit().create(PaperBankApi.class)
                .downloadPdfFile(TEST_PDF_URL)
                .subscribeOn(Schedulers.io())
                .subscribe(responseBodyResponse -> {
                    try {
                        String fileName = "laz.pdf";
                        Log.d(RestService.class.getSimpleName(), responseBodyResponse.code() + "");

                        File file = new File(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsoluteFile(), fileName);
                        BufferedSink sink = Okio.buffer(Okio.sink(file));
                        // you can access body of response
                        sink.writeAll(responseBodyResponse.body().source());
                        sink.close();
                    }catch (IOException e){
                        Log.d(RestService.class.getSimpleName(), "Problem when writing to file");
                    }
                }, throwable -> {
                    Log.d(RestService.class.getSimpleName(), "Error during download");
                    Log.d(RestService.class.getSimpleName(), throwable.getMessage());
                });
    }

    private Observable<File> saveFile(Response<ResponseBody> response){
        return  Observable.create(e -> {
            try {

                // you can access headers of response
                String header = response.headers().get("Content-Disposition");
                // this is specific case, it's up to you how you want to save your file
                // if you are not downloading file from direct link, you might be lucky to obtain file name from header
                String fileName = header.replace("attachment; filename=", "");
                // will create file in global Music directory, can be any other directory, just don't forget to handle permissions
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsoluteFile(), fileName);

                BufferedSink sink = Okio.buffer(Okio.sink(file));
                // you can access body of response
                sink.writeAll(response.body().source());
                sink.close();
                e.onNext(file);
                e.onComplete();
            }catch (IOException exception){
                exception.printStackTrace();
                e.onError(exception);
            }
        });
    }

    private OkHttpClient getOkHttpClient(){
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        clientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);

        return clientBuilder.build();
    }
}
