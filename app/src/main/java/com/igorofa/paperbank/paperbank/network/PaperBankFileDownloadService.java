package com.igorofa.paperbank.paperbank.network;

import android.support.annotation.NonNull;

import com.igorofa.paperbank.paperbank.exceptions.FileNotDownloadedException;

import java.io.File;
import java.util.logging.Logger;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by laz on 15/03/17.
 */

public final class PaperBankFileDownloadService {

    private final String TEST_PDF_URL = "https://paperbankdo.s3.amazonaws.com/uploads/Physics_2013-paperbank.pdf";

    private final BaseNetworkService mBaseNetworkService;
    private final OkHttpClient mClient;
    private final Scheduler mScheduler;
    private final Retrofit mRetrofit;
    private final Subject<Integer> progressSubject;

    private final File mSavingFile;

    public PaperBankFileDownloadService(@NonNull File savingFile) {
        mSavingFile = savingFile;

        mBaseNetworkService = BaseNetworkService.getNetworkService();
        mScheduler = mBaseNetworkService.getNetworkScheduler();
        mRetrofit = mBaseNetworkService.getBaseRetrofit();
        mClient = mBaseNetworkService.getBaseOkHttpClient();

        final PublishSubject<Integer> progressPublishSubject = PublishSubject.create();
        progressSubject = progressPublishSubject.toSerialized();

    }

    private Retrofit getTrackProgressRetrofit() {
//        final long fileBytesLength = mSavingFile.length();

        OkHttpClient progressClient = mClient.newBuilder()
                .addInterceptor(chain -> {
                    okhttp3.Response response = chain.proceed(chain.request());
                    return response.newBuilder()
                            .body(new ProgressResponseBody(chain.proceed(chain.request()).body(), progressSubject))
                            .build();
                })
                .build();
        return mRetrofit.newBuilder()
                .client(progressClient)
                .build();
    }

    private Completable saveWholeFile(@NonNull final Response<ResponseBody> response) {

        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }

        if (!response.isSuccessful())
            return Completable.error(new FileNotDownloadedException(mSavingFile));
        else return Completable.fromCallable(() -> {

            BufferedSink sink = Okio.buffer(Okio.sink(mSavingFile));
            Source responseBodySource = response.body().source();

            Logger.getLogger(PaperBankFileDownloadService.class.getName())
                    .info(Long.toString(response.body().contentLength()));

            try {

                long bytes_read = sink.writeAll(responseBodySource);

                Logger.getLogger(PaperBankFileDownloadService.class.getName())
                        .info(Long.toString(bytes_read));
                return mSavingFile;
            } finally {
                responseBodySource.close();
                sink.close();
            }
        });
    }

    public Completable getFile(@NonNull final HttpUrl url, boolean trackProgress) {
//        Log.d(RestService.class.getSimpleName(), url);
        PaperBankApi paperBankApi;

        if (trackProgress){
            paperBankApi = getTrackProgressRetrofit().create(PaperBankApi.class);
        }else{
            paperBankApi = mRetrofit.create(PaperBankApi.class);
        }

        return paperBankApi
                .downloadPdfFile(url)
                .subscribeOn(mScheduler)
                .flatMapCompletable(this::saveWholeFile);
    }

    public Flowable<Integer> getProgressUpdates(){
        return progressSubject
                .subscribeOn(mScheduler)
                .toFlowable(BackpressureStrategy.LATEST);
    }
}
