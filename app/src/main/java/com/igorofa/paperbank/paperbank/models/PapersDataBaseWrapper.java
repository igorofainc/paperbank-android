package com.igorofa.paperbank.paperbank.models;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by laz on 15/03/17.
 *
 * TODO: Strongly need to change some parts of this class....Especialy on features that are closely tied to the android system;;;Improve testing
 * Called only after ObjectBox has been initialized
 */

public final class PapersDataBaseWrapper {
    private static PapersDataBaseWrapper THE_PAPER_BANK_DATA_BASE_WRAPPER;
    private final BoxStore paperBankBoxStore;
    private final Box<Paper> paperBox;
    private final QueryBuilder<Paper> paperQueryBuilder;

    public static void initializeDataBaseWrapper(Context context){
        THE_PAPER_BANK_DATA_BASE_WRAPPER = new PapersDataBaseWrapper(context);
    }

    private PapersDataBaseWrapper(Context appContext){
        paperBankBoxStore = MyObjectBox.builder().androidContext(appContext).build();

        paperBox = paperBankBoxStore.boxFor(Paper.class);
        paperQueryBuilder = paperBox.query();
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
        return Maybe.fromAction(paperBox::getAll); // TODO: load the papers from ObjectBox
    }

    /**
     * Given an item id should return the respective item from database or null
     * @param itemId
     * @return Maybe<Paper>
     */
    public Maybe<Paper> loadPaperItem(@NonNull Long itemId){

        return Maybe.fromAction(() ->
                paperQueryBuilder.equal(Paper_.paperId, itemId)
                        .build()
                        .findUnique()); // TODO: load single item from ObjectBox
    }

    /**
     * Given condition parameters should query database for conditions and return list of items or null
     * @return Maybe<List<Paper>>
     */
    public Maybe<List<Paper>> queryPapers(){
        return Maybe.empty(); //TODO: query ObjectBox using given conditions from parameter
    }

    public Completable storeToDataBase(@NonNull List<Paper> paperItems){
        Completable storeCompletable =  Completable.fromAction(() -> paperBox.put(paperItems));

        // set the date of each paper to the current date and then put in database
        return Completable.fromAction(() -> {
            for (Paper paper: paperItems) {
                paper.setDatePaper(new Date(System.currentTimeMillis()));
            }
        })
                .andThen(storeCompletable);
    }

    public QueryBuilder<Paper> getPaperQueryBuilder() {
        return paperQueryBuilder;
    }

    /**
     * Standard query for search using "" string
     * Returns query that can be overwritten using setParameter to change text to be queried
     * e.g getSearchQuery().setParameter(Paper_.name, "physics").find()
     * @return Query<Paper>
     */
    public Query<Paper> getSearchQuery(){
        return paperQueryBuilder.contains(Paper_.name, "").build();
    }

    public Single<Long> getCount(){
        return Single.just(paperBox.count());
    }
}
