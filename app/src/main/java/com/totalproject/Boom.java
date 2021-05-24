package com.totalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Boom {
    private Bitmap bitmap;
    private int x, y;

    public Boom(Context context) {
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.boom);
        this.x = -bitmap.getWidth();
        this.y = -bitmap.getHeight();
    }
    public void change(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void changeover(){
        this.x = -bitmap.getWidth();
        this.y = -bitmap.getHeight();
    }


    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
