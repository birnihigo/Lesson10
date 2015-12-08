package com.solomon.cattailtouch;

/**
 * Created by user on 12/8/2015.
 */
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CatTailCatchGameView extends SurfaceView
{
    private SurfaceHolder holder;

    private CatTailCatchGameThread catThread = null;
    private Bitmap cat;
    private float catX;
    private float catY;
    private int score = 0;
    private Paint scorePaint;
    private Random catRandomizer;
    private long catTimer;


    public CatTailCatchGameView(Context context)
    {
        super(context);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
                // TODO Auto-generated method stub

            }

            @Override
            public void surfaceCreated(SurfaceHolder holder)
            {
                cat = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
                catRandomizer = new Random();
                moveCat();

                scorePaint = new Paint();
                scorePaint.setTextSize(50.0f);
                scorePaint.setColor(Color.BLACK);
                makeThread();
                catThread.setRunning(true);
                catThread.start();

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub

            }});
    }// end of constructor


    public void moveCat()
    {
        catX = (float)catRandomizer.nextInt(getWidth() - 100);
        catY = (float)catRandomizer.nextInt(getHeight() - 175);
        catY += 100.0f;
        catTimer = System.currentTimeMillis();
    }

    public void makeThread()
    {
        catThread = new CatTailCatchGameThread(this);

    }

    public void killThread()
    {
        boolean retry = true;
        catThread.setRunning(false);
        while(retry)
        {
            try
            {
                catThread.join();
                retry = false;
            }
            catch (InterruptedException e){}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        float touchedX = e.getX();
        float touchedY = e.getY();

        //This is just the cat tail not the whole cat picture
        float tailX = catX + 170.0f;
        float tailY = catY + 45.0f;
        if(  touchedX >= tailX
                && touchedX <= tailX + 30.0f
                && touchedY >= tailY
                && touchedY <= tailY + 35.0f )
        {
            score++;
            moveCat();
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        canvas.drawText("Score: " + String.valueOf(score), 10.0f, 50.0f, scorePaint);
        if(System.currentTimeMillis() > catTimer + 1000) moveCat();
        canvas.drawBitmap(cat, catX, catY, null);
    }

    public void onDestroy()
    {
        cat.recycle();
        cat=null;
        System.gc();
    }
}