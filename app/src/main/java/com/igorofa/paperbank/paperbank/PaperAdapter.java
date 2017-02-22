package com.igorofa.paperbank.paperbank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by x4b1d on 22/02/17.
 */

public class PaperAdapter extends RecyclerView.Adapter<PaperViewHolder> {
    Context mContext;
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

}

class PaperViewHolder extends RecyclerView.ViewHolder{
    TextView paperTitle, paperTag;
    PaperViewHolder(View itemView) {
        super(itemView);

        paperTitle = (TextView) itemView.findViewById(R.id.paper_title);
        paperTag = (TextView) itemView.findViewById(R.id.paper_tag);
    }

    void setTextsView(){
        paperTitle.setText(R.string.s6_physics_2017);
        paperTag.setText(R.string.physics);
    }
}