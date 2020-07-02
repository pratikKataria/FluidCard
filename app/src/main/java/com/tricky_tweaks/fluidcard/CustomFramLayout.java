package com.tricky_tweaks.fluidcard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomFramLayout extends FrameLayout {


    private float cx;
    private float cy;
    private Paint backgroundPaint;

    public CustomFramLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomFramLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomFramLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomFramLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        setLayerType(LAYER_TYPE_HARDWARE, null);

        backgroundPaint = new Paint();
        backgroundPaint.setXfermode(new PorterDuffXfermode(
                PorterDuff.Mode.CLEAR
        ));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        cx = event.getX();
        cy = event.getY();
        postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        if (cx >= 0 && cy >= 0) {
            canvas.drawCircle(
                    cx,
                    cy,
                    100,
                    backgroundPaint
            );
        }

    }
}
