package com.hazem.mobilespeed;

/**
 * Created by hp on 04/12/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class DeviceMovingSpeed extends Activity {// implements Runnable

    static TextView t;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = (TextView) findViewById(R.id.t);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/DIGITALDREAM.ttf");
        t.setTypeface(tf);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Toast.makeText(getBaseContext(), "HELLO", Toast.LENGTH_SHORT).show();
//                takeScreenshot();
                Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);
            }

        });

        Log.v("Debug", "Activity started..");
        Intent myIntent = new Intent(this, MyService.class);
        startService(myIntent);
    /*TODO
    1- start in design pages>> 1 tabs speed
    2- send notification when speed > 50
    3- when click in notification show his speed while changing
    4- share speed with screen shot from application with his speed and map overview

    * */

    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }

        String text = "Look at my awesome picture";
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TITLE, text);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imagePath));  //optional//use this when you want to send an image
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "send"));

//
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("image/*");
//        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
//        Uri uri = Uri.fromFile(imagePath);
//        shareIntent.putExtra(Intent.EXTRA_STREAM, uri.toString());
//        startActivity(Intent.createChooser(shareIntent, "Share image using"));


//        Uri pictureUri = Uri.parse(Environment.getExternalStorageDirectory() + "/screenshot.png");
//        Intent shareIntent = new Intent();
//        shareIntent.setAction(Intent.ACTION_SEND);
//
//        shareIntent.putExtra(Intent.EXTRA_STREAM, pictureUri);
//        shareIntent.setType("image/*");
//        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivity(Intent.createChooser(shareIntent, "Share images..."));
    }

}
