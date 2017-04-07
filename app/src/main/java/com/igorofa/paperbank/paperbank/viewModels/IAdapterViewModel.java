package com.igorofa.paperbank.paperbank.viewModels;

import android.support.annotation.NonNull;

import com.igorofa.paperbank.paperbank.models.Paper;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by laz on 18/03/17.
 */

public interface IAdapterViewModel {

    Single<List<Paper>> getPapers();

    Single<String> getFilePath(@NonNull long paperId);
}
