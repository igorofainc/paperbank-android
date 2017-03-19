package com.igorofa.paperbank.paperbank;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.igorofa.paperbank.paperbank.models.ClickedPaper;
import com.igorofa.paperbank.paperbank.models.Paper;
import com.igorofa.paperbank.paperbank.viewModels.IViewModel;
import com.igorofa.paperbank.paperbank.viewModels.PBMainActivityViewModel;
import com.igorofa.paperbank.paperbank.views.DownloadingForegroundImageView;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;


/**
 * Created by x4b1d on 22/02/17.
 */

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.PaperViewHolder> {
    Context mContext;
    List<Paper> mPapers;
    PublishSubject<ClickedPaper> itemClickedSubject = PublishSubject.create();
    IViewModel mViewModel;
    CompositeDisposable mCompositeDisposable;

    public PaperAdapter(Context context, @NonNull IViewModel viewModel, List<Paper> papers) {
        super();

        mPapers = papers;
        mContext = context;
        mViewModel = viewModel;

        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public PaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View adapterItemView = LayoutInflater.from(mContext).inflate(R.layout.view_holder_main_paper, parent, false);
        return new PaperViewHolder(adapterItemView);
    }

    @Override
    public void onBindViewHolder(PaperViewHolder holder, int position) {
        holder.setTextsView(mPapers.get(position));
    }

    @Override
    public int getItemCount() {
//        return 0;
        return mPapers.size(); // jus for testing
    }

    public Observable<ClickedPaper> getItemClickedSubject() {
        return itemClickedSubject;
    }

    class PaperViewHolder extends RecyclerView.ViewHolder {

        TextView paperTitle, paperTag;
        DownloadingForegroundImageView paperLinkImage;

        PaperViewHolder(View itemView) {
            super(itemView);

            paperTitle = (TextView) itemView.findViewById(R.id.paper_title);
            paperTag = (TextView) itemView.findViewById(R.id.paper_tag);

            paperLinkImage = (DownloadingForegroundImageView) itemView.findViewById(R.id.holder_paper_view_image);

            setListeners();
            setUp();
        }

        void setUp(){
            if (mViewModel instanceof PBMainActivityViewModel){
//                Toast.makeText(mContext, "MainActivityViewModel", Toast.LENGTH_SHORT).show();
//                mCompositeDisposable.add(((PBMainActivityViewModel) mViewModel).getFileDownloadingProgress()
//                        .subscribe(paperProgress -> {
//                            setViewProgress(paperProgress.getProgress());
//                            Toast.makeText(mContext, " " + paperProgress.getProgress() , Toast.LENGTH_SHORT).show();
//                        }, throwable -> Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show())
//                );

            }
        }

        private void setListeners() {
            paperLinkImage.setOnClickListener(v -> {
//                itemClickedSubject.onNext(new ClickedPaper(getAdapterPosition()));
                final Paper oneClickedPaper = mPapers.get(getAdapterPosition());
                if (oneClickedPaper.getLocalFileUrl() != null){
                    openPaperPdf(new File(oneClickedPaper.getLocalFileUrl()));
                }else {


                        mCompositeDisposable.add(mViewModel.getFile(new ClickedPaper(getAdapterPosition(), mPapers.get(getAdapterPosition())))
                                .subscribe(() -> {
                                            openPaperPdf(new File(oneClickedPaper.getLocalFileUrl()));
                                            Toast.makeText(mContext, getAdapterPosition() + " has downloaded", Toast.LENGTH_SHORT).show();
                                        },
                                        throwable -> {
                                            Log.d(PaperAdapter.class.getSimpleName(), throwable.getMessage());
                                            Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        }));
                        mCompositeDisposable.add(((PBMainActivityViewModel) mViewModel).getISDownloading()
                                .retry()
                                .subscribe(aBoolean -> paperLinkImage.setInDownload(aBoolean)));
                        mCompositeDisposable.add(((PBMainActivityViewModel) mViewModel).getProgressDownloadingSubject()
                                .retry()
                                .subscribe(integer -> {
                                    Log.d(PaperAdapter.class.getSimpleName(), integer + " percent");
                                    setViewProgress(integer);
                                })
                        );

                }
            });

        }

        private void openPaperPdf(File file){
//            Toast.makeText(paperLinkImage.getContext(), file.getPath(), Toast.LENGTH_SHORT).show();
            try{
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                paperLinkImage.getContext().startActivity(intent);
            }catch (ActivityNotFoundException e){
                e.printStackTrace();
                Toast.makeText(paperLinkImage.getContext(), "No Application Available to View PDF", Toast.LENGTH_SHORT).show();
            }
        }

        void setTextsView(Paper paper) {
            paperTitle.setText(paper.getPaperTitle());
            paperTag.setText(paper.getPaperTag());
        }

        void setViewProgress(int progress){
            paperLinkImage.setProgress(progress);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mCompositeDisposable.clear();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}