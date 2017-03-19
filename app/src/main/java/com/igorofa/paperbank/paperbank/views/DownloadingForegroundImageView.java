package com.igorofa.paperbank.paperbank.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.igorofa.paperbank.paperbank.R;

/**
 * Created by laz on 18/03/17.
 */

public class DownloadingForegroundImageView extends AppCompatImageView {
    private static final String ARGS_SUPER = "super_state";
    private static final String ARGS_IS_DOWNLOADING = "is_downloading";

    private boolean isInDownload;
    private int progress;
    private TextPaint progressPaint;

    private Paint mPaint;

    public DownloadingForegroundImageView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public DownloadingForegroundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DownloadingForegroundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyAttr){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DownloadingForegroundImageView);
        try{
            isInDownload = a.getBoolean(R.styleable.DownloadingForegroundImageView_isInDownload, false);
            setInDownload(isInDownload);

            progress = a.getInteger(R.styleable.DownloadingForegroundImageView_progress, 0);
            setProgress(progress);
        }finally {
            a.recycle();
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#42000000"));

        progressPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(Color.WHITE);
        progressPaint.setTextSize(Math.round(24f * getContext().getResources().getDisplayMetrics().scaledDensity));
    }

    public boolean isInDownload() {
        return isInDownload;
    }

    public void setInDownload(boolean inDownload) {
        isInDownload = inDownload;
        if (isInDownload) {
            setClickable(false);
        }else {
            setClickable(true);
        }
        postInvalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInDownload) {
            float radius = getWidth() / 2;
            canvas.drawCircle(radius, radius, radius, mPaint);

            String progressText = String.valueOf(progress) + "%";
            final float baselineY = Math.round(canvas.getHeight() * 0.6f);
            final float textWidth = progressPaint.measureText(progressText);
            final float textX = Math.round(canvas.getWidth() * 0.5f - textWidth * 0.5f);

            canvas.drawText(progressText, textX, baselineY, progressPaint);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle saveState = new Bundle();
        saveState.putParcelable(ARGS_SUPER, super.onSaveInstanceState());
        saveState.putBoolean(ARGS_IS_DOWNLOADING, isInDownload);

        return saveState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state != null){
            Bundle outState = (Bundle) state;

            state = outState.getParcelable(ARGS_SUPER);
            isInDownload = outState.getBoolean(ARGS_IS_DOWNLOADING);

            setInDownload(isInDownload);
        }
        super.onRestoreInstanceState(state);
    }
}
