package com.igorofa.paperbank.paperbank.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.igorofa.paperbank.paperbank.R;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by x4b1d on 21/02/17.
 */

public class PaperBankGetStartedActivity extends AppCompatActivity {

    /*
     * rx subject that receives only the subsequent items after subscription
     * subscribers to click events will only receive subsequent clicks after subscription
     */
    PublishSubject<View> getStartedTextObservable = PublishSubject.create();
    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_get_started_paper_bank);

        findViewById(R.id.get_started_text_view)
                .setOnClickListener(v -> getStartedTextObservable.onNext(v));
    }

    @Override
    protected void onStart() {
        super.onStart();
        bind();
    }

    void bind(){
        mCompositeDisposable.add(getStartedTextObservable.subscribe(view -> {
            startActivity(new Intent(view.getContext(), PaperBankMainActivity.class));
            finish();
        }));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbind();
    }

    void unbind(){
        mCompositeDisposable.clear();
    }
}
