package com.example.android.onsitetask1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;
import java.util.Random;
import java.util.Vector;

public class GameActivity extends AppCompatActivity {
    float width, height;
    int cols = MainActivity.l, rows = MainActivity.z;
    float cellWidth, cellHeight, margin;
    float cx, cy;
    int tx, ty, t;
    int[] list;
    Frame frame;
    RelativeLayout gamelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        gamelay = findViewById(R.id.gamelay);
        frame = new Frame(this);
        list = new int[rows * cols];
        list2 = new int[rows * cols];
        for (int i=0;i<rows*cols;i++)
        {
            Random random = new Random();
            int r = random.nextInt(2);
            if(r==1)
            {
                frame.inv(i);
                list2[i]++;
                list2[i]%=2;
            }
        }
        final View view = gamelay;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                width = view.getWidth();
                height = view.getHeight();
                cellHeight = height / rows;
                cellWidth = width / cols;
                margin = 2 * getResources().getDisplayMetrics().density;
                makeframe();
            }
        }, 10);
    }
    public class Frame extends View {
        Paint black, white;

        public Frame(Context context) {
            super(context);
            black = new Paint();
            white = new Paint();
            black.setColor(Color.BLACK);
            white.setColor(Color.WHITE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            for (int j = 0; j < rows; j++) {
                for (int i = 0; i < cols; i++) {
                    if (list[j*cols+i]==1)
                        canvas.drawRect(i * cellWidth + margin, j * cellHeight + margin,
                                (i + 1) * cellWidth - margin, (j + 1) * cellHeight - margin, white);
                    else
                        canvas.drawRect(i * cellWidth + margin, j * cellHeight + margin,
                                (i + 1) * cellWidth - margin, (j + 1) * cellHeight - margin, black);
                }
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN&&donebysolver==0) {
                cx = event.getX();
                cy = event.getY();
                tx = (int) Math.floor(cx / cellWidth);
                ty = (int) Math.floor(cy / cellHeight);
                t = ty * cols + tx;
                for (int i = 0; i < rows; i++) {
                    list[i * cols + tx]++;
                    list[i * cols + tx]%=2;
                }
                for (int i = 0; i < cols; i++) {
                    list[ty * cols + i]++;
                    list[ty * cols + i]%=2;
                }
                list[t]++;
                list[t]%=2;
                list2[t]++;
                list2[t]%=2;
                invalidate();
            }
            return true;
        }
        public void inv(int t){
            for (int i = 0; i < rows; i++) {
                list[i * cols + t%cols]++;
                list[i * cols + t%cols]%=2;
            }
            for (int i = 0; i < cols; i++) {
                list[t-t%cols + i]++;
                list[t-t%cols + i]%=2;
            }
            list[t]++;
            list[t]%=2;
            invalidate();
        }

    }
    int[] list2;
    int donebysolver=0;
    public void solve(View view) {
        donebysolver=1;
        solver();
    }
    int g=0;
    public void solver()
    {
        if(g<rows*cols){
            if(list2[g]==1){
                Toast.makeText(getApplicationContext(),"row : " + (g/cols)+ " col : "+g%cols,Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.inv(g);
                        g++;
                        solver();
                    }
                },2000);
            }
            else
            {
                g++;
                solver();
            }
        }
    }
    public void makeframe() {
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        frame.setLayoutParams(param);
        gamelay.addView(frame);
    }
}
