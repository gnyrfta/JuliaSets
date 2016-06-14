package com.gnyrfta.juliasets;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    static int width;
    static int height;
    static JuliaGenerator jg = new JuliaGenerator();
    public static double real=0;
    public static double complex=0;
    public static int[][] thisArray;
    static String iterationType="sine";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final EditText et1 = (EditText)findViewById(R.id.real);
        final EditText et2 = (EditText)findViewById(R.id.complex);
        RadioButton sine = (RadioButton)findViewById(R.id.sine);
        RadioButton cosine = (RadioButton)findViewById(R.id.cosine);
        RadioButton quadratic = (RadioButton)findViewById(R.id.quadratic);
        Button b = (Button)findViewById(R.id.launch);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        final DrawStuff ds = new DrawStuff(this);
        //final Intent i = new Intent(this,DrawStuff.class);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                real = Double.parseDouble(et1.getText().toString());
                Log.d("this is real: "+real,"this is real: "+real);
                complex = Double.parseDouble(et2.getText().toString());
                Log.d("this is complex: "+complex,"this is complex: "+complex);
                jg.setZ(real,complex);
                thisArray=jg.generateDataFile();
              //  Log.d("thisArray[0]: "+thisArray[0],"length"+thisArray[0]);
               // Log.d("thisArray[1]: "+thisArray[1],""+thisArray[1]);
               // ds.setArray(jg.generateDataFile());
                setContentView(ds);
                //hopefully draw to image at root of filesystem.
                //hopefully draw to image at root of filesystem.
                try {
                    ds.setDrawingCacheEnabled(true);
                  //  Bitmap b = ds.getDrawingCache();
                    File sdCard = Environment.getExternalStorageDirectory();
                    String fileName = "JuliaIteration_"+iterationType+"("+real+","+complex+"j)"+".png";
                    File file = new File(sdCard,fileName);
                    FileOutputStream fos = new FileOutputStream(file);
                    loadBitmapFromView(ds).compress(Bitmap.CompressFormat.PNG, 80, fos);
                    fos.flush();
                    fos.close();
                }
                catch (IOException e){e.printStackTrace();/*i think a file not found exception would be thrown here.*/}
            }
        });
        sine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jg.setIteration(1);
                iterationType="sine";
                Log.d("sine iteration set","sine iteration set");
            }
        });
        cosine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jg.setIteration(2);
                iterationType="cosine";
            }
        });
        quadratic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jg.setIteration(3);
                iterationType="quadratic";
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int longDuration=10;

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_app) {
            Toast.makeText(getApplicationContext(),getString(R.string.infoApp),Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),getString(R.string.infoApp),Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),getString(R.string.infoApp),Toast.LENGTH_LONG).show();

            return true;
        }
        else if(id==R.id.about_constants)
        {
            Toast.makeText(getApplicationContext(),getString(R.string.infoConstants),Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),getString(R.string.infoConstants),Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),getString(R.string.infoConstants),Toast.LENGTH_LONG).show();
            return true;
        }
        else if(id==R.id.about_julia)
        {
            Toast.makeText(getApplicationContext(),getString(R.string.infoJulia),Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),getString(R.string.infoJulia),Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),getString(R.string.infoJulia),Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Bitmap loadBitmapFromView(View view) {

        // width measure spec
        int widthSpec = View.MeasureSpec.makeMeasureSpec(
                view.getMeasuredWidth(), View.MeasureSpec.AT_MOST);
        // height measure spec
        int heightSpec = View.MeasureSpec.makeMeasureSpec(
                view.getMeasuredHeight(), View.MeasureSpec.AT_MOST);
        // measure the view
        view.measure(widthSpec, heightSpec);
        // set the layout sizes
        view.layout(view.getLeft(), view.getTop(), view.getMeasuredWidth() + view.getLeft(), view.getMeasuredHeight() + view.getTop());
        // create the bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // create a canvas used to get the view's image and draw it on the bitmap
        Canvas c = new Canvas(bitmap);
        // position the image inside the canvas
        c.translate(-view.getScrollX(), -view.getScrollY());
        // get the canvas
        view.draw(c);

        return bitmap;
    }

}
