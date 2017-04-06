package com.igorofa.paperbank.paperbank.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by laz on 6/04/17.
 */

public class PaperRecentFilesAdapter extends
        RecyclerView.Adapter<PaperRecentFilesAdapter.PaperRecentViewHolder> implements IPaperBankAdapter{

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

    @Override
    public void release() {
        M_COMPOSITE_DISPOSABLE.clear();
    }

    class PaperRecentViewHolder extends RecyclerView.ViewHolder{
        public PaperRecentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
