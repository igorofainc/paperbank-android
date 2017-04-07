package com.igorofa.paperbank.paperbank.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.igorofa.paperbank.paperbank.models.Paper;
import com.igorofa.paperbank.paperbank.viewModels.IAdapterViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by laz on 6/04/17.
 */

public class PaperRecentFilesAdapter extends
        RecyclerView.Adapter<PaperRecentFilesAdapter.PaperRecentViewHolder> {

    List<Paper> mPapers;
    final IAdapterViewModel mHolderVModel;
    final CompositeDisposableWrapper mDisposableWrapper;
    final CompositeDisposable mCompositeDisposable;

    public PaperRecentFilesAdapter(IAdapterViewModel adapter_view_model){

        mHolderVModel = adapter_view_model;
        mDisposableWrapper = new CompositeDisposableWrapper();
        mCompositeDisposable = mDisposableWrapper.getM_COMPOSITE_DISPOSABLE();
    }

    @Override
    public PaperRecentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PaperRecentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void release() {
        mDisposableWrapper.release();
    }

    class PaperRecentViewHolder extends RecyclerView.ViewHolder{
        public PaperRecentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
