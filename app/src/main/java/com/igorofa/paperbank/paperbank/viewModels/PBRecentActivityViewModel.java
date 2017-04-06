package com.igorofa.paperbank.paperbank.viewModels;

import com.igorofa.paperbank.paperbank.models.Paper;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by laz on 14/03/17.
 * A view model for the recent activity's view
 * It is slightly different from main activity's view model in that if a file is not found it should tell
 * the view to display error
 */

public class PBRecentActivityViewModel implements IViewModel{

    public PBRecentActivityViewModel() {

    }

    @Override
    public Single<List<Paper>> getPapers() {
        return null;
    }
}
