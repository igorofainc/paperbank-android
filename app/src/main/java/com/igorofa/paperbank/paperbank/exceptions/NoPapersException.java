package com.igorofa.paperbank.paperbank.exceptions;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by laz on 16/03/17.
 */

public class NoPapersException extends Exception {
    public NoPapersException() {
        super();
    }

    public NoPapersException(String message) {
        super(message);
    }

    public NoPapersException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPapersException(Throwable cause) {
        super(cause);
    }

    @RequiresApi (Build.VERSION_CODES.N)
    protected NoPapersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
