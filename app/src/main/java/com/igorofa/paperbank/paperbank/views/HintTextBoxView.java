package com.igorofa.paperbank.paperbank.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.igorofa.paperbank.paperbank.R;

/**
 * Created by laz on 13/03/17.
 */

public class HintTextBoxView extends View {
    private final float DEFAULT_TEXT_SIZE_SP = 14;
    private final float PADDING_LEFT_RIGHT_DP = 4;
    private final float PADDING_TOP_BOTTOM_DP = 2;
    private static final int DEFAULT_RECT_RADIUS = 3;

    private float mCenterX;
    private float mCenterY;
    private float mRadius;

    private float padding_left_px;
    private float padding_top_px;

    private float textSize;

    private TextPaint hintTextPaint;
    private String hintText = "Hello";


    public HintTextBoxView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public HintTextBoxView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public HintTextBoxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HintTextBoxView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes){
        TypedArray atp = context.obtainStyledAttributes(attrs, R.styleable.HintTextBoxView);
        float defaultTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                DEFAULT_TEXT_SIZE_SP, getResources().getDisplayMetrics());

        padding_left_px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, PADDING_LEFT_RIGHT_DP, getResources().getDisplayMetrics());
        padding_top_px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, PADDING_TOP_BOTTOM_DP, getResources().getDisplayMetrics());

        if (atp.hasValue(R.styleable.HintTextBoxView_text)){
            hintText = atp.getString(R.styleable.HintTextBoxView_text);
        }
        if (atp.hasValue(R.styleable.HintTextBoxView_textSize)){
            textSize = atp.getDimensionPixelSize(R.styleable.HintTextBoxView_textSize, (int) defaultTextSize);
        }else {
            textSize = defaultTextSize;
        }
        atp.recycle();

        setBackgroundResource(R.drawable.round_rect);
        hintTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        hintTextPaint.setColor(Color.WHITE);
        hintTextPaint.setTextSize(textSize);
        hintTextPaint.setTextAlign(Paint.Align.CENTER);

        mRadius = DEFAULT_RECT_RADIUS * context.getResources().getDisplayMetrics().density;

    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
        postInvalidate();
        requestLayout();
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        hintTextPaint.setTextSize(textSize);
        invalidate();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float paddingWidth = padding_left_px + padding_left_px + getPaddingRight() + getPaddingLeft();
        float paddingHeight = padding_top_px + padding_top_px + getPaddingTop() + getPaddingBottom();

        float textWidth = hintTextPaint.measureText(hintText);
        float textHeight = hintTextPaint.getFontMetrics().bottom + hintTextPaint.getFontMetrics().top * -1;

        float width = textWidth + paddingWidth;
        float height = textHeight + paddingHeight;

        setMeasuredDimension(resolveSize((int) width, widthMeasureSpec), resolveSize((int) height, heightMeasureSpec));

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        float startX = padding_left_px;
        float startX = padding_left_px + getPaddingLeft() + hintTextPaint.measureText(hintText) * 0.5f;
        float startY =  padding_top_px + getPaddingTop() + hintTextPaint.getFontMetrics().ascent * -1;

        canvas.drawText(hintText, startX, startY, hintTextPaint);
    }
}
