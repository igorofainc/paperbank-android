package com.igorofa.paperbank.paperbank.models;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import io.objectbox.BoxStore;
import io.reactivex.Maybe;

/**
 * Created by laz on 15/03/17.
 *
 * TODO: Strongly need to change some parts of this class....Especialy on features that are closely tied to the android system;;;Improve testing
 * Called only after ObjectBox has been initialized
 */

public final class PapersDataBaseWrapper {
    private static PapersDataBaseWrapper THE_PAPER_BANK_DATA_BASE_WRAPPER;
    final BoxStore paperBankBoxStore;

    public static void initializeDataBaseWrapper(Context context){
        THE_PAPER_BANK_DATA_BASE_WRAPPER = new PapersDataBaseWrapper(context);
    }

    private PapersDataBaseWrapper(Context appContext){
        paperBankBoxStore = MyObjectBox.builder().androidContext(appContext).build();
    }

    public static PapersDataBaseWrapper getThePaperBankDataBaseWrapper(){
        if (THE_PAPER_BANK_DATA_BASE_WRAPPER == null){
            throw new UnsupportedOperationException("The Class has not been initialized");
        }
        return THE_PAPER_BANK_DATA_BASE_WRAPPER;
    }

    /**
     * Load papers from the database
     * and return list of itmes or null
     * @return Maybe<List<Paper>>
     */
    public Maybe<List<Paper>> loadPapers(){
        return Maybe.empty(); // TODO: load the papers from ObjectBox
    }

    /**
     * Given an item id should return the respective item from database or null
     * @param itemId
     * @return Maybe<Paper>
     */
    public Maybe<Paper> loadPaperItem(@NonNull Long itemId){
        return Maybe.empty(); // TODO: load single item from ObjectBox
    }

    /**
     * Given condition parameters should query database for conditions and return list of items or null
     * @return Maybe<List<Paper>>
     */
    public Maybe<List<Paper>> queryPapers(){
        return Maybe.empty(); //TODO: query ObjectBox using given conditions from parameter
    }

}
