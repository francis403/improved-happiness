package com.example.gesturetest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by franc on 07/11/2017.
 */

public class CansasExample extends View implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private static Paint paint = new Paint();
    private Path path = new Path();
    private GestureDetectorCompat mDetector;

    public CansasExample(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDetector = new GestureDetectorCompat(getContext(),this);
        mDetector.setOnDoubleTapListener(this);

        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        /*
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                clickCount++;
                startTime = System.currentTimeMillis();
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                long time = System.currentTimeMillis() - startTime; //up time
                duration=  duration + time; //duration of pressed

                //check if double click
                if(clickCount == 2)
                {
                    if(duration <= MAX_DURATION) {
                        paint.setColor(switchColor());
                    }
                    clickCount = 0;
                    duration = 0;
                    break;
                }
                if(duration > MAX_DURATION && clickCount == 1) {
                    setBackgroundColor(switchColor());
                    clickCount = 0;
                    duration = 0;
                }
                clickCount = 0;
                duration = 0;
                break;
            default:
                return false;
        }
        */
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        this.setBackgroundColor(Color.BLACK);
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        float eventX = e1.getX();
        float eventY = e2.getY();
        path.moveTo(eventX, eventY);
        eventX = e2.getX();
        eventY = e2.getY();
        path.lineTo(eventX, eventY);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    private int switchColor(){
        Random rg = new Random();
        int color = rg.nextInt(5);
        switch (color){
            case 0:
                return Color.BLUE;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.CYAN;
            case 3:
                return Color.BLACK;
            case 4:
                return Color.RED;
            default:
                return Color.BLACK;
        }
    }

}
