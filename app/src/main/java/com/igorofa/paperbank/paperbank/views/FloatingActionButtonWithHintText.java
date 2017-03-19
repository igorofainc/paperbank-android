package com.igorofa.paperbank.paperbank.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.igorofa.paperbank.paperbank.R;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by laz on 13/03/17.
 */

public class FloatingActionButtonWithHintText extends LinearLayout {
    private HintTextBoxView mHintTextBoxView;
    private FloatingActionButton mFloatingActionButton;

    private String hintText;

    // OnClickListener for the floating action Button
    PublishSubject<View> hintFabClickListener;


    public FloatingActionButtonWithHintText(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public FloatingActionButtonWithHintText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public FloatingActionButtonWithHintText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FloatingActionButtonWithHintText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingActionButtonWithHintText);

        if (!a.hasValue(R.styleable.FloatingActionButtonWithHintText_srcActionCompat)
                || !a.hasValue(R.styleable.FloatingActionButtonWithHintText_hintText)){
            throw new InflateException();
        }

        setOrientation(LinearLayout.HORIZONTAL);

        hintText = a.getString(R.styleable.FloatingActionButtonWithHintText_hintText);
//        imageSrcRes = a.getDrawable(R.styleable.FloatingActionButtonWithHintText_srcActionCompat);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_floating_action_button_with_text, this, true);

        mHintTextBoxView = (HintTextBoxView) getChildAt(0);
        mHintTextBoxView.setHintText(hintText);

        mFloatingActionButton = (FloatingActionButton) getChildAt(1);
//        mFloatingActionButton.setImageDrawable(imageSrcRes);
//        mFloatingActionButton.setImageResource(R.styleable.FloatingActionButtonWithHintText_srcActionCompat);

        a.recycle();

        hintFabClickListener = PublishSubject.create();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mFloatingActionButton.setOnClickListener(v -> hintFabClickListener.onNext(v));
    }

    // use this observable to subscribe to the onclick listener
    public Observable<View> getFabOnClickObservable(){
        return hintFabClickListener;
    }

    public HintTextBoxView getHintTextBoxView() {
        return mHintTextBoxView;
    }

    public FloatingActionButton getFloatingActionButton() {
        return mFloatingActionButton;
    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
        mHintTextBoxView.setHintText(this.hintText);
    }

    public void setImageSrcRes(@DrawableRes int drawableRes) {
        mFloatingActionButton.setImageResource(drawableRes);
    }
}
