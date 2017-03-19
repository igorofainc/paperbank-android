package com.igorofa.paperbank.paperbank.network;

import io.reactivex.subjects.PublishSubject;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by laz on 18/03/17.
 */

class ProgressResponseBody extends ResponseBody {
    private ResponseBody responseBody;
    private PublishSubject<Integer> progressListener;
    private BufferedSource bufferedSource;

    ProgressResponseBody(ResponseBody body, PublishSubject<Integer> tracker) {
        responseBody = body;
        progressListener = tracker;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws java.io.IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                progressListener.onNext((int) (100 * ((double)totalBytesRead / contentLength())));
//                Log.d(ProgressResponseBody.class.getSimpleName(), " " + totalBytesRead);
//                Log.d(ProgressResponseBody.class.getSimpleName(), " " + totalBytesRead / contentLength());
                return bytesRead;
            }
        };
    }
}
