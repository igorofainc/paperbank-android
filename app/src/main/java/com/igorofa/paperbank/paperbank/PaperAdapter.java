package com.igorofa.paperbank.paperbank;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;


/**
 * Created by x4b1d on 22/02/17.
 */

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.PaperViewHolder> {
    Context mContext;
    PublishSubject<ClickedPaper> itemClickedSubject = PublishSubject.create();

    public PaperAdapter(Context context) {
        super();
        mContext = context;
    }

    @Override
    public PaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View adapterItemView = LayoutInflater.from(mContext).inflate(R.layout.paper_view_holder, parent, false);
        return new PaperViewHolder(adapterItemView);
    }

    @Override
    public void onBindViewHolder(PaperViewHolder holder, int position) {
        holder.setTextsView();
    }

    @Override
    public int getItemCount() {
//        return 0;
        return 10; // jus for testing
    }

    public Observable<ClickedPaper> getItemClickedSubject(){
        return itemClickedSubject;
    }

    class PaperViewHolder extends RecyclerView.ViewHolder {

        TextView paperTitle, paperTag;
        AppCompatImageView paperLinkImage;

        PaperViewHolder(View itemView) {
            super(itemView);

            paperTitle = (TextView) itemView.findViewById(R.id.paper_title);
            paperTag = (TextView) itemView.findViewById(R.id.paper_tag);

            paperLinkImage = (AppCompatImageView) itemView.findViewById(R.id.holder_paper_view_image);

            setListeners();
        }

        private void setListeners() {
            paperLinkImage.setOnClickListener(v -> {
                itemClickedSubject.onNext(new ClickedPaper(getAdapterPosition()));
            });
        }

        void setTextsView() {
            paperTitle.setText(R.string.s6_physics_2017);
            paperTag.setText(R.string.physics);
        }
    }

    public class ClickedPaper {
        int positionInAdapter;
        long paperId;

        ClickedPaper(int positionInAdapter){
            this.positionInAdapter = positionInAdapter;
        }

        ClickedPaper(int positionInAdapter, long paperId){
            this.positionInAdapter = positionInAdapter;
            this.paperId = paperId;
        }

        public int getPositionInAdapter() {
            return positionInAdapter;
        }

        public long getPaperId() {
            return paperId;
        }
    }
}