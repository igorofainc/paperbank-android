package com.igorofa.paperbank.paperbank.network;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by laz on 14/03/17.
 */

interface PaperBankApi {

    // used String because we intend to manually parse the json to our Paper model
    @Streaming // passes on the response immediately,,good for downloading large files
    @GET
    Observable <Response<ResponseBody>> downloadPdfFile(@Url HttpUrl resourceUrl);

    @GET("papers")
    <T> Single<List<T>> getPapers();
}
