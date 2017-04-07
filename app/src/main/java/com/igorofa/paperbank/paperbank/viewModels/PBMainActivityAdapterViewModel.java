package com.igorofa.paperbank.paperbank.viewModels;

import android.support.annotation.NonNull;

import com.igorofa.paperbank.paperbank.models.Paper;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by laz on 14/03/17.
 * Main Activity's view model
 * If file not found tells view that it is trying to download and relays progress to the main activity
 */

public class PBMainActivityAdapterViewModel implements IAdapterViewModel {

    @Override
    public Single<List<Paper>> getPapers() {
        return null;
    }


    @Override
    public Single<String> getFilePath(@NonNull long paperId) {
        return null;
    }
}
