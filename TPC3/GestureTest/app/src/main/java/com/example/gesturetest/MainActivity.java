package com.example.gesturetest;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static String DEBUG_TAG = "DEBUG";
    private GestureDetectorCompat mDetector;
    private VelocityTracker mVelocityTracker = null;

    private TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(new SingleTouchEventView(this, null));
    }
    /**
    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    **/

    /**
    @Override
    public boolean onTouchEvent(MotionEvent event){

        // get pointer index from the event object
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();

        int action = MotionEventCompat.getActionMasked(event);

        switch(maskedAction) {
            case MotionEvent.ACTION_POINTER_DOWN:
                // TODO use data
                break;
            case MotionEvent.ACTION_MOVE: // a pointer was moved
                // TODO use data
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                // TODO use data
                break;
            default:
                return super.onTouchEvent(event);
        }
    }
    **/

}
