package com.igorofa.paperbank.paperbank.adapters;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.igorofa.paperbank.paperbank.models.Paper;
import com.igorofa.paperbank.paperbank.viewModels.IAdapterViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by x4b1d on 22/02/17.
 */

public class PaperMainViewAdapter extends RecyclerView.Adapter<PaperMainViewAdapter.PaperViewHolder> {

    List<Paper> mPapers = new ArrayList<>();

    final IAdapterViewModel mHolderVModel;
    final CompositeDisposableWrapper mDisposableWrapper;
    final CompositeDisposable mCompositeDisposable;

    public PaperMainViewAdapter(IAdapterViewModel adapter_view_model){

        mHolderVModel = adapter_view_model;
        mDisposableWrapper = new CompositeDisposableWrapper();
        mCompositeDisposable = mDisposableWrapper.getM_COMPOSITE_DISPOSABLE();

        setPapersSubscription();
    }

    private void setPapersSubscription(){
        mCompositeDisposable.add(mHolderVModel.getPapers()
        .doOnSuccess(papers -> {
            if (!mPapers.isEmpty()) {
                mPapers = papers;
                notifyDataSetChanged();
            }else mPapers = papers;
        })
        .subscribe());
    }

    @Override
    public PaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PaperViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void release() {
        mDisposableWrapper.release();
    }

    class PaperViewHolder extends RecyclerView.ViewHolder {

        PaperViewHolder(View itemView) {
            super(itemView);

        }

        private void openPaperPdf(File file){
//            Toast.makeText(paperLinkImage.getContext(), file.getPath(), Toast.LENGTH_SHORT).show();
            try{
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

//                paperLinkImage.getContext().startActivity(intent);
            }catch (ActivityNotFoundException e){
                e.printStackTrace();
//                Toast.makeText(paperLinkImage.getContext(), "No Application Available to View PDF", Toast.LENGTH_SHORT).show();
            }
        }
    }
}