package com.example.cyyze.screenshotapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

public class ShotClass {
    private static class SingleHolder {
        static final ShotClass shot = new ShotClass();
    }

    public static ShotClass getInstance() {
        return SingleHolder.shot;
    }

    public void createShot(Context context, String path, String name) {
        Intent intent = new Intent(context, ScreenShotActivity.class);
        intent.putExtra("path", path);
        intent.putExtra("name", name);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

