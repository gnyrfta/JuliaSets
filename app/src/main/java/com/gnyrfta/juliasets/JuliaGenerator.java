package com.gnyrfta.juliasets;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by david on 2016-05-27.
 * For generating Julia Sets.
 */
public class JuliaGenerator {
    public double[] c = {1.0,0.2};
    double[] z;
    int width=MainActivity.width;
    int height=MainActivity.height;
    int scale = 6;
    int largestValue;
    //Every z in a -2 -> 2 square is a seed for testing if it goes to 'infinity' or not.
    //if it does, the original z is not part of the set.
    //if not, colour it black.
    //additional colouring? -> after how fast it converges to infinity!
    //ArrayList<Double> xArray = fillArrays();
    //ArrayList<Double> yArray = fillArrays();

    double m=-2;
    boolean datsTrue=false;
    public ArrayList<Integer> fillArrays(int length)
    {
        ArrayList<Integer> array = new ArrayList<Integer>();
        int m=(int)(length/2);
        for(int i=-m;i<m;i++)
        {
            array.add(i);
        }
        Log.d("length: "+length,"length :"+length);
        Log.d("this is array size: "+array.size(),"this is array size: "+array.size());
        //array size is zero.
        return array;
    }
    public int checkIfBelongsToSet(double x, double y)
    {
        //x is the real part, y is the imaginary part.
        //For z_n= c*sin(z_n-1)
        int returnValue=1;
        int bailout=1000;
        int largestValue=0;
        //Loop this a certain amount of times, then check for some cutoff value if it seems to go to infinity:
        outerloop:
        for(int i=0;i<bailout;i++) {
            //Log.d("first x: "+x,"first y"+y);

            if(Math.sqrt(Math.pow(x,2)+Math.pow(y,2))>50)
            {
                returnValue=i;
                break outerloop;
            }
            double realPart = Math.sin(x) * Math.cosh(y);
            double imaginaryPart = Math.cos(x) * Math.sinh(y);
            double temp1 = c[0] * realPart;
            double temp2 = c[1] * imaginaryPart;
            x = temp1 - temp2;
            double temp3 = c[0] * imaginaryPart;
            double temp4 = c[1] * realPart;
            y = temp3 + temp4;
    //        Log.d("x: "+x,"y"+y);

            returnValue=i;
        }
        Log.d("returnvalue: "+returnValue,"returnvalue"+returnValue);
        return returnValue;
    }
    //New class that takes matrix as arguments and draws black squares in a grid.
    //Base it on drawing class in Chessboard.
    public int[][] generateDataFile()
    {
        //check out system.arraycopy later, if necessary.
        int [][] result = new int[MainActivity.width][MainActivity.height];

        double factor = Math.sqrt(Math.pow((width/2),2)+Math.pow((height/2),2));
        //Fills them with coordinates
        width=MainActivity.width;
        height=MainActivity.height;
        Log.d("Mainactivity.width: "+MainActivity.width,"MainActivity.width: "+MainActivity.width);
        ArrayList<Integer> xArray = fillArrays(width);
        ArrayList<Integer> yArray = fillArrays(height);
        int i;
        largestValue=0;
        for(i=0;i<xArray.size();i++) {
            double x=xArray.get(i);
            Log.d("x"+x, "x:"+x);
            for (int j=0;j<yArray.size();j++)
            {
                double y=yArray.get(j);
                Log.d("y:"+y,"y:"+y);
               // Log.d("in second for", "in second for");
                double real = (x / factor) * scale;
                double imaginary = (y / factor) * scale;
                result[i][j]=checkIfBelongsToSet(real,imaginary);
                Log.d("result[i][j]"+result[i][j],"i j :"+i+""+j+"");
                if(result[i][j]>largestValue)
                {
                    largestValue=result[i][j];
                }
                // Log.d("real: " + real, "real: " + real);
                // Log.d("imaginary: " + imaginary, "imaginary: " + imaginary);
                //fw.append("value: "+result[x][y]+"/n");
            }
        }
     //   Log.d("result[0].length: "+result.length,"result[0].length"+result.length);
        return result;
    }
}
