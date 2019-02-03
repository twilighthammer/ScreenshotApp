package com.example.cyyze.screenshotapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MediaProjectionManager mediaProjectionManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        mediaProjectionManager = (MediaProjectionManager) getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), 1);

        final Button button = findViewById(R.id.bt_screenshot);
        final ImageView imageView = findViewById(R.id.img_display);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.mediaProjection != null) {
                    MyApplication.time = System.currentTimeMillis();
                    Bitmap bitmap = CreateBitMap.getInstance(MainActivity.this, true, null, generateName()).getBitmap();
                    if (bitmap == null) {
                        Log.i("img", "bitmap is empty");
                    } else {
                        imageView.setForeground(new BitmapDrawable(null, bitmap));
                    }
                } else {
                    Log.i("img", "permission denied");
                }
            }
        });

    }


    private String generateName() {
        String temp = "img_screenshot";
        temp= temp+Math.random()*(10000000);
        return temp;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyApplication.mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
    }

    public void checkPermission() {
        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
            }

            if (permissionsList.size() != 0) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        100);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            } else {
                Log.i("img", "permission denied");
            }
        }///instant-run
    }
}