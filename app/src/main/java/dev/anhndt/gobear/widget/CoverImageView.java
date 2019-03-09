package dev.anhndt.gobear.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


public class CoverImageView extends ImageView {

    public CoverImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CoverImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CoverImageView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth() * 9 / 16);
    }
}
