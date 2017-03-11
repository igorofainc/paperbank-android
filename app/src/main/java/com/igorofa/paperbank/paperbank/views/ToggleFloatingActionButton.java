package com.igorofa.paperbank.paperbank.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.igorofa.paperbank.paperbank.R;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by laz on 11/03/17.
 */

public class ToggleFloatingActionButton extends FloatingActionButton {
    private static final String ARGS_SUPER = "super_state";
    private static final String ARGS_STATE_OPEN = "state_open";

    private PublishSubject<View> onClickObservable;

    // notifies consumers of change in the state stateOpen
    private BehaviorSubject<Boolean> stateOpenChangeObservable;

    private Disposable onClickDisposable;

    private boolean stateOpen;

    public ToggleFloatingActionButton(Context context) {
        this(context, null);
    }

    public ToggleFloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToggleFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ToggleFloatingActionButton);
        stateOpen = a.getBoolean(R.styleable.ToggleFloatingActionButton_stateOpened, false);
        a.recycle();

        onClickObservable = PublishSubject.create();
        stateOpenChangeObservable = BehaviorSubject.create();
        setStateOpen(stateOpen);

    }

    public void setStateOpen(boolean stateOpen) {
        this.stateOpen = stateOpen;
        stateOpenChangeObservable.onNext(this.stateOpen);
        if (stateOpen){
            setImageResource(R.drawable.ic_close);
        }else{
            setImageResource(R.drawable.ic_add_white);
        }
    }

    public BehaviorSubject<Boolean> getStateOpenChangeObservable() {
        return stateOpenChangeObservable;
    }

    public boolean isStateOpen() {
        return stateOpen;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setOnClickListener(v -> onClickObservable.onNext(v));

        onClickDisposable = onClickObservable.subscribe(view -> {
            boolean stateOpened = ((ToggleFloatingActionButton)view).isStateOpen();
            setStateOpen(!stateOpened);
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (onClickDisposable != null){
            onClickDisposable.dispose();
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle saveState = new Bundle();
        saveState.putParcelable(ARGS_SUPER, super.onSaveInstanceState());
        saveState.putBoolean(ARGS_STATE_OPEN, stateOpen);

        return saveState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state != null){
            Bundle outState = (Bundle) state;

            state = outState.getParcelable(ARGS_SUPER);
            stateOpen = outState.getBoolean(ARGS_STATE_OPEN);

            setStateOpen(stateOpen);
        }
        super.onRestoreInstanceState(state);
    }
}
