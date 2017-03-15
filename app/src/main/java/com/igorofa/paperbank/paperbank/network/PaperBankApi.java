package com.igorofa.paperbank.paperbank.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by laz on 14/03/17.
 */

public interface PaperBankApi {

    // used String because we intend to manually parse the json to our Paper model
    @GET
    Observable <Response<ResponseBody>> downloadPdfFile(@Url String fileName);

}
