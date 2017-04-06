package com.igorofa.paperbank.paperbank.adapters;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by laz on 6/04/17.
 */

public interface IPaperBankAdapter {
    CompositeDisposable M_COMPOSITE_DISPOSABLE = new CompositeDisposable();

    void release();
}
