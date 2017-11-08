package com.example.gesturetest;

/**
 * Created by franc on 06/11/2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;


public class SingleTouchEventView extends View implements SensorEventListener{

    private ArrayList<Pair<Path,Paint>> paths = new ArrayList<>();

    private GestureDetectorCompat mDetector;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast;
    private boolean touched;
    private int previousColor;
    private int lineColor = Color.BLACK;
    //constant for defining the time duration between the click that can be considered as double-tap
    static final int MAX_DURATION = 500;

    public SingleTouchEventView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        mDetector = new GestureDetectorCompat(context, new MyGestureListener());
    }

    public void setPaintSettings(Paint paint){
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(lineColor);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for(Pair<Path, Paint> pair : paths){
            canvas.drawPath(pair.first,pair.second);
        }

        //canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        this.mDetector.onTouchEvent(event);
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                Path path2 = new Path();
                Paint paint2 = new Paint();
                setPaintSettings(paint2);
                path2.moveTo(eventX,eventY);
                paths.add(new Pair<Path,Paint>(path2,paint2));


                return true;
            case MotionEvent.ACTION_MOVE:

                paths.get(paths.size()-1).first.lineTo(eventX,eventY);
                //super.onTouchEvent(event);
                break;
            default:
                super.onTouchEvent(event);
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }

    private int switchColor(){
        Random rg = new Random();
        int color = rg.nextInt(6);
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
            case 5:
                return Color.WHITE;
            default:
                return Color.BLACK;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent se) {

        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if(mSensor != null) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel >= 35) {
                //clear everything

                paths.clear();
                invalidate();
            }
            else if(mAccel >= 20){
                //apagar apenas a ultima linha
                if(!paths.isEmpty())
                    paths.remove(paths.size() - 1);
                invalidate();
            }
        }
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(mSensor!= null){
            float dist = se.values[0];
            if(dist == 0) {
                touched = true;
                //previousColor = getDrawingCacheBackgroundColor();
                setBackgroundColor(Color.BLACK);
            }
            if(touched && dist > 3){
                setBackgroundColor(previousColor);
                touched = false;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDoubleTapEvent(MotionEvent event) {
            lineColor = switchColor();

            paths.get(paths.size()-1).second.setColor(lineColor);

            return true;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            previousColor = switchColor();
            setBackgroundColor(previousColor);
        }
    }

}
