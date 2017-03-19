package com.igorofa.paperbank.paperbank.viewModels;

import com.igorofa.paperbank.paperbank.models.ClickedPaper;

import io.reactivex.Completable;

/**
 * Created by laz on 18/03/17.
 */

public interface IViewModel {
    Completable getFile(ClickedPaper paper) ;
}
