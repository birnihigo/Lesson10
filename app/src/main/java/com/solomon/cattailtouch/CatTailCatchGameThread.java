package com.solomon.cattailtouch;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

/**
 * Created by user on 12/8/2015.
 */

public class CatTailCatchGameThread extends Thread
{
    private CatTailCatchGameView view;
    private boolean running = false;

    public CatTailCatchGameThread(CatTailCatchGameView viewIn)
    {
        this.view = viewIn;
    }

    public void setRunning(boolean run)
    {
        running = run;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run()
    {
        while (running)
        {
            Canvas c = null;
            try
            {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder())
                {
                    view.onDraw(c);
                }
            }
            finally
            {
                if (c != null)
                {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }
}
