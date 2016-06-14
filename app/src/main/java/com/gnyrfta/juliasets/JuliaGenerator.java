package com.gnyrfta.juliasets;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by david on 2016-05-27.
 *
 * For generating Julia Sets.
 */
public class JuliaGenerator {
    public double[] c = {1.0,0.2};
    double[] z;
    int width=MainActivity.width;
    int height=MainActivity.height;
    int scale = 6;
    int largestValue;
    String iterationName="sine";
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
    public int checkIfBelongsToSetSine(double x, double y)
    {
        //x is the real part, y is the imaginary part.
        //For z_n= c*sin(z_n-1)
        Log.d("checkifBelongsToSetSine","checkifBelongsToSetSine");

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
          //  Log.d("x: "+x,"y"+y);
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
        width=MainActivity.width;
        height=MainActivity.height;
        int [][] result = new int[MainActivity.width][MainActivity.height];
        double factor = Math.sqrt(Math.pow((width/2),2)+Math.pow((height/2),2));
        //Fills them with coordinates
        Log.d("Mainactivity.width: "+MainActivity.width,"MainActivity.width: "+MainActivity.width);
        ArrayList<Integer> xArray = fillArrays(width);
        ArrayList<Integer> yArray = fillArrays(height);
        int i;
        largestValue=0;
        for(i=0;i<xArray.size();i++) {
            int x=xArray.get(i);
            Log.d("x directly fetched: "+xArray.get(i),"x directly fetched"+xArray.get(i));
            Log.d("x"+x, "x:"+x);
            Log.d("i: "+i,"i: "+i);
            Log.d("c[0]: "+c[0],"c[0]: "+c[0]);
            for (int j=0;j<yArray.size();j++)
            {
                int y=yArray.get(j);
                Log.d("y:"+y,"y:"+y);
                if(MainActivity.iterationType.equals("sine"))
                {
                    scale=10;
                }
                else if(MainActivity.iterationType.equals("cosine"))
                {
                    scale=3;
                }
                else if(MainActivity.iterationType.equals("quadratic"))
                {
                    scale=2;
                }
                double real = (x / factor) * scale;
                double imaginary = (y / factor) * scale;
                Log.d("real: "+real,"rel: "+real);
                Log.d("imaginary: "+imaginary,"imaginary: "+imaginary);
                if(MainActivity.iterationType.equals("sine"))
                {
                    Log.d("iteration.sine","iteration.sine");
                    result[i][j] = checkIfBelongsToSetSine(real, imaginary);

                }
                else if (MainActivity.iterationType.equals("cosine"))
                {
                    Log.d("iteration cosine","iteration cosine");
                   result[i][j] = checkIfBelongsToSetCosine(real, imaginary);
                }
                else if(MainActivity.iterationType.equals("quadratic"))
                {
                    result[i][j] = checkIfBelongsToSetQuadratic(real, imaginary);
                }
                Log.d("result[i][j]"+result[i][j],"i j :"+i+""+j+"");
                if(result[i][j]>largestValue)
                {
                    largestValue=result[i][j];
                }
            }
        }
        return result;
    }
    public void setZ(double a, double b)
    {
        c[0]=a;
        c[1]=b;
    }
    public void setIteration(int a)
    {
        switch(a) {

            case 1:iterationName="sine";
            case 2:iterationName="cosine";
            case 3:iterationName="quadratic";
        }
    }
    public int checkIfBelongsToSetCosine(double x, double y)
    {
        // For cosine:
        // c = 1.0 - 0.5j        # probably has good colors
        // c = pi/2*(1.0 + 0.6j) # sort of cool - dendrites
        // c = pi/2*(1.0 + 0.4j) # same deal
        // c = pi/2*(2.0 + 0.25j)  # fuzzy spots
        // c = pi/2*(1.5 + 0.05j)  # batlef
        //
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
            double realPart = Math.cos(x) * Math.cosh(y);
            double imaginaryPart = (-1)*(Math.sin(x) * Math.sinh(y));
            double temp1 = c[0] * realPart;
            double temp2 = c[1] * imaginaryPart;
            x = temp1 - temp2;
            double temp3 = c[0] * imaginaryPart;
            double temp4 = c[1] * realPart;
            y = temp3 + temp4;
            //  Log.d("x: "+x,"y"+y);
            returnValue=i;
        }
        Log.d("returnvalue: "+returnValue,"returnvalue"+returnValue);
        return returnValue;
    }
    public int checkIfBelongsToSetQuadratic(double x, double y)
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

            if(Math.sqrt(Math.pow(x,2)+Math.pow(y,2))>2)
            {
                returnValue=i;
                break outerloop;
            }
            double realPart = Math.pow(x,2)-Math.pow(y,2);
            double imaginaryPart = 2*x*y;
            //double temp1 = c[0] * realPart;
            //double temp2 = c[1] * imaginaryPart;
            x = realPart+c[0];
            //double temp3 = c[0] * imaginaryPart;
            //double temp4 = c[1] * realPart;
            y = imaginaryPart + c[1];
            //  Log.d("x: "+x,"y"+y);
            returnValue=i;
        }
        Log.d("returnvalue: "+returnValue,"returnvalue"+returnValue);
        return returnValue;
    }

}

