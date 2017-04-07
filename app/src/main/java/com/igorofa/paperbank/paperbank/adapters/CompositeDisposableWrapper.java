package com.igorofa.paperbank.paperbank.adapters;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by laz on 6/04/17.
 */

final class CompositeDisposableWrapper {
    final CompositeDisposable M_COMPOSITE_DISPOSABLE = new CompositeDisposable();

    void release(){
        M_COMPOSITE_DISPOSABLE.clear();
    }

    public CompositeDisposable getM_COMPOSITE_DISPOSABLE() {
        return M_COMPOSITE_DISPOSABLE;
    }
}
