package com.chenbit.demo.test.event;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.chenbit.demo.R;

public class EventTestActivity extends AppCompatActivity {

    private static final String TAG = EventTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_test);
        findViewById(R.id.btnTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventTestActivity.this,"fffuuuccc", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG,"==onTouchEvent==");
        return super.onTouchEvent(event);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG,"==onAttachedToWindow==");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG,"==onDetachedFromWindow==");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG,"==onBackPressed==");
    }
}
