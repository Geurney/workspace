package com.example.dq.surfaceviewexample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


public class MainActivity extends Activity implements View.OnTouchListener {
    OurView v;
    Bitmap robot;
    private int color;
    private float x,y;
    private int red,green,blue;
    static {
        System.loadLibrary("MyJni");
    }
    public native static int getIntegerFromNative();
    public native static void setIntegerFromJava(int i);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = new OurView(this);
        v.setOnTouchListener(this);
        robot = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        x = y = 0;
        red = 198;
        green =98;
        blue = 9;
        setContentView(v);
    }

    @Override
    protected void onPause() {
        super.onPause();
        v.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        v.resume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
/*        try {
            Thread.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }*/

        switch(event.getAction()){
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                setIntegerFromJava(color);
                color=getIntegerFromNative();
                if(++green > 255)
                    green = 0;
                if(++blue > 255)
                    blue = 0;
                break;
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    public class OurView extends SurfaceView implements Runnable{
        Thread t = null;
        SurfaceHolder holder;
        boolean isItOK = false;

        public OurView(Context context) {
            super(context);
            holder = getHolder();
        }

        @Override
        public void run() {
            while(isItOK == true){
                if(!holder.getSurface().isValid()){
                    continue;
                }
                Canvas c = holder.lockCanvas();

                c.drawARGB(255,100,color,100);
               // c.drawARGB(255,red,green,blue);
                c.drawBitmap(robot,x-(robot.getWidth()/2),y-(robot.getHeight()/2),null);
                holder.unlockCanvasAndPost(c);

            }
        }

        public void pause(){
            isItOK = false;
            while(true){
                try {
                    t.join();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                break;
            }
            t = null;
        }
        public void resume(){
            isItOK = true;
            t = new Thread(this);
            t.start();
        }
    }

}
