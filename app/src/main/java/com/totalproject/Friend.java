package com.totalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

public class Friend {

    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;

    private int minX;
    private int minY;
    private Rect detectCollision;

    public Friend(Context context, int screenX, int screenY) {
        Log.d("RRR screenX",Integer.toString(screenX));

        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.friend);
        this.maxX = screenX;
        this.maxY = screenY - bitmap.getHeight();
        this.minX = 0;
        this.minY = 0;
        Random r = new Random();
        speed = r.nextInt(6);
        x = screenX;
        y = r.nextInt(this.maxY);

        Random generator = new Random();
        speed = generator.nextInt(15);

        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(int playerSpeed) {
        x -= playerSpeed;
        x -= speed;
        if (x < 0) {
            x = maxX;
            Random generator = new Random();
            y = generator.nextInt(maxY);
            speed = generator.nextInt(15);
        }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }
    public void changeX(){x = -bitmap.getWidth();}

    public Rect getRect(){ return detectCollision;}

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
