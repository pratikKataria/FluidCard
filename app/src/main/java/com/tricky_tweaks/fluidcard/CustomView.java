package com.tricky_tweaks.fluidcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

public class CustomView extends View {

    private Rect rect;
    private Paint paint;

    private Paint circlePaint;

    private Bitmap image;

    private int color;
    private int size;
    private float radius = 50;
    private float cx;
    private float cy;

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(AttributeSet set) {
        rect = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.RED);

        image = BitmapFactory.decodeResource(getResources(), R.drawable.sample_image);


        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int padding = 100;
                image = getResizeBitmap(image, 95, 95);


//                new Timer().scheduleAtFixedRate(new TimerTask() {
//                    @Override
//                    public void run() {
//                        int newWidth = image.getWidth() - 50;
//                        int newHeight = image.getHeight() -50;
//
//                        if (newWidth <= 0 || newHeight <=0 ) {
//                            cancel();
//                            return;
//                        }
//
//                        image =getResizeBitmap(image, newWidth, newHeight);
//                        postInvalidate();
//
//                    }
//                }, 2000, 500);

            }
        });

        if (set == null)
            return;

        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.CustomView);

        color = ta.getColor(R.styleable.CustomView_square_color, Color.GREEN);
        size = ta.getDimensionPixelOffset(R.styleable.CustomView_square_size, 150);

        paint.setColor(color);

        ta.recycle();
    }

    private Bitmap getResizeBitmap(Bitmap image, int width, int height) {
        Matrix matrix = new Matrix();
        RectF src = new RectF(0, 0, image.getWidth(), image.getHeight());
        RectF dst = new RectF(0, 0, width, height);

        matrix.setRectToRect(src, dst, Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rect.top = size;
        rect.left = size;
        rect.bottom = size + rect.top;
        rect.right = size + rect.left;

        int startPos = (getWidth() / 2);

        canvas.drawRect(
                getLeft() + (getRight() - getLeft()) /3,
                getTop() + (getBottom() - getTop()) / 3,
                getRight() - (getRight() - getLeft()) / 3,
                getBottom() - (getBottom() - getTop()) / 3
                , paint);

        if (cx == 0F || cy == 0F) {
            cx = getWidth() / 2;
            cy = getWidth() / 2;
        }

        canvas.drawCircle(cx, cy, radius, circlePaint);

        canvas.drawBitmap(image, getWidth() / 2, getHeight() / 2, null);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean value = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();

                double dx = Math.pow(x - cx, 2);
                double dy = Math.pow(y - cy, 2);

                float topRight = rect.right;

                boolean draw = !(y > rect.top - radius && y < rect.bottom + radius && (x >= rect.left - radius && x <= rect.right + radius));

                if (draw) {
                    cx = x;
                    cy = y;

                    postInvalidate();
                } else {

                }
//
//                if (dx + dy  < Math.pow(radius, 2)) {
//
//                    cx = x;
//                    cy = y;
//
//                    postInvalidate();
//                }

                return true;
        }

        return value;
    }
}
