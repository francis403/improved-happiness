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
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.SENSOR_SERVICE;


public class SingleTouchEventView extends View implements SensorEventListener{

    private Paint paint = new Paint();
    private Path path = new Path();
    private ArrayList<Pair<Path,Paint>> paths = new ArrayList<>();
    private GestureDetectorCompat mDetector;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast;
    private boolean touched;
    int previousColor;
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
        setPaintSettings(paint);
    }

    public void setPaintSettings(Paint paint){
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*
        Paint paint2 = paint;
        Path path2 = path;
        paths.add(new Pair<Path, Paint>(path2,paint2));
        for(Pair<Path, Paint> pair : paths){
            canvas.drawPath(pair.first,pair.second);
        }
           */
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        this.mDetector.onTouchEvent(event);
        boolean result = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX, eventY);
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
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            if (mAccel >= 20) {
                //clear everything
                path.reset();
                invalidate();
            }
            else if(mAccel >= 12){
                //apagar apenas a ultima linha
            }
        }
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(mSensor!= null){
            float dist = se.values[0];
            if(dist == 0) {
                touched = true;
                previousColor = getDrawingCacheBackgroundColor();
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
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDoubleTapEvent(MotionEvent event) {
            paint.setColor(switchColor());
            return true;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            setBackgroundColor(switchColor());
        }
    }

}
