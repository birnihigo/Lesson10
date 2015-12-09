package com.solomon.cattailtouch;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;

public class MainActivity extends Activity
{
    private CatTailCatchGameView gpv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        gpv = new CatTailCatchGameView(this);

        FrameLayout outerLayout = new FrameLayout(this);
        outerLayout.addView(gpv);

        setContentView(outerLayout);
    }

    @Override
    protected void onPause()
    {
        SharedPreferences prefs = getSharedPreferences("CatTailCatchData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String tmpScore = Integer.toString(gpv.getScore());
        editor.putString("GameScore", tmpScore);

        editor.commit();

        super.onPause();
        gpv.killThread();
    }
    @Override
    protected void onResume(){
        SharedPreferences prefs = getSharedPreferences("CatTailCatchData", MODE_PRIVATE);
        String tmpScore = prefs.getString("GameScore", "1");

        gpv.setScore(Integer.valueOf(tmpScore));

        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        gpv.onDestroy();
    }
}