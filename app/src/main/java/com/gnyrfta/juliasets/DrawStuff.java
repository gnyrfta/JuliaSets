package com.gnyrfta.juliasets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

/**
 * Created by david on 2016-05-28.
 */
public class DrawStuff extends View {
 Paint p = new Paint();
 boolean[][] condivArray = new boolean[MainActivity.width][MainActivity.height];

    public DrawStuff(Context context)
    {
        super(context);
    }
    protected void onDraw(Canvas canvas)
    {
        p.setColor(Color.BLACK);
        //canvas.drawRect(new RectF(0, 10, 30, 20), p);
        canvas.drawText("width: "+MainActivity.width+"height"+MainActivity.height+"",20,60,p);
        int x=0;
        int y=0;
        String temp="";
         Log.d("entering For loop", "entering For loop");
        //fillArray();
        Color c = new Color();
        //greyscale colours:
        int gs1 = c.rgb(255,255,255);
        int gs2 = c.rgb(224,224,224);
        int gs3 = c.rgb(192,192,192);
        int gs4 = c.rgb(160,160,160);
        int gs5 = c.rgb(128,128,128);
        int gs6 = c.rgb(96,96,96);
        int gs7 = c.rgb(64,64,64);
        int gs8 = c.rgb(32,32,32);
        int gs9 = c.rgb(0,0,0);
        int [][] testArray = MainActivity.thisArray;
        int max = MainActivity.jg.largestValue;
        int increment = (int)(max/9);
        for(x=0;x<MainActivity.width-2;x++)//119 199
        {
            for(y=0;y<MainActivity.height-2;y++)
            {
                if(MainActivity.thisArray[x][y]<=increment)
                {
                    p.setColor(gs9);
                }
                else if(testArray[x][y]<=(increment*2))
                {
                    p.setColor(gs8);
                }
                else if(testArray[x][y]<=(increment*3))
                {
                    p.setColor(gs7);
                }
                else if(testArray[x][y]<=(increment*4))
                {
                    p.setColor(gs6);
                }
                else if(testArray[x][y]<=(increment*5))
                {
                    p.setColor(gs5);
                }
                else if(testArray[x][y]<=(increment*6))
                {
                    p.setColor(gs4);
                }
                else if(testArray[x][y]<=(increment*7))
                {
                    p.setColor(gs3);
                }
                else if(testArray[x][y]<=(increment*8))
                {
                    p.setColor(gs2);
                }
                else
                {
                    p.setColor(gs1);
                }
                canvas.drawRect(new RectF(x,y,x+1,y+1),p);
               // Log.d("in for-loop, X: " + x + "y: " + y + "", "x: " + x + " y: " + y + "");
                if(testArray[x][y]>=1)
                {
                    temp="true";
                    Log.d("in first if", "in first if");
                }
                else if(testArray[x][y]==0)
                {
                    temp="false";
                    Log.d("in else", "in else");
                }
                Log.d("value of pixel"+temp+"","value of pixel"+temp+"");
            }
        }

    }
    public void setArray(boolean[][] array)
    {
        condivArray=array;
    }
    public void fillArray()
    {

        for(int x=0;x<MainActivity.width;x++)
        {
            for(int y=0;y<MainActivity.height;y++)
            {
                condivArray[x][y]=true;
            }
        }
    }

}
