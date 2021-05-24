package com.totalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    private Thread gameThread = null;
    private Player player;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private ArrayList<Star> stars = new ArrayList<Star>();
    private ArrayList<Friend> friends = new ArrayList<Friend>();
    private ArrayList<BadFriend> badfriends = new ArrayList<BadFriend>();

    private Boom boom;

    int screenX;
    int countMisses;

    private boolean isGameOver;
    public static int score;

    SharedPreferences sharedPreferences;

    final MediaPlayer killedEnemysound;
    final MediaPlayer gameOversound;
    private Rect rect;

    Context context;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        boom = new Boom(context);
        player = new Player(context, screenX, screenY);

        surfaceHolder = getHolder();
        paint = new Paint();

        int starNums = 1000;
        for (int i = 0; i < starNums; i++) {
            Star s = new Star(screenX, screenY);
            stars.add(s);
        }

        int friendNums = 3;
        for (int i = 0; i < friendNums; i++) {
            Friend friend = new Friend(context, screenX, screenY);
            friends.add(friend);
            BadFriend badfriend = new BadFriend(context, screenX, screenY);
            badfriends.add(badfriend);
        }

        this.screenX = screenX;
        countMisses = 0;
        isGameOver = false;


        score = 0;
        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE);
        this.context = context;

        killedEnemysound = MediaPlayer.create(context,R.raw.killedenemy);
        gameOversound = MediaPlayer.create(context,R.raw.gameover);

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;
        }

        if(isGameOver){
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                context.startActivity(new Intent(context,MainActivity.class));
            }
        }
        return true;
    }

    @Override
    public void run() {
        while (playing) {
            try {
                update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            draw();
            control();
        }
    }

    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);


            paint.setColor(Color.WHITE);
            paint.setTextSize(20);

            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            canvas.drawBitmap(boom.getBitmap(), boom.getX(), boom.getY(), paint);


            paint.setTextSize(30);
            canvas.drawText("Очки: "+score,100,50,paint);

            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            for (Friend f : friends) {
                canvas.drawBitmap(
                        f.getBitmap(),
                        f.getX(),
                        f.getY(),
                        paint);
            }

            for (BadFriend f : badfriends) {
                canvas.drawBitmap(
                        f.getBitmap(),
                        f.getX(),
                        f.getY(),
                        paint);
            }

            if(isGameOver){
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);
                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Конец игры",canvas.getWidth()/2,yPos,paint);

            }

            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }

    private void update() throws InterruptedException {
        score++;

        player.update();

        for (Friend f : friends) {
            f.update(player.getSpeed());
            if(Rect.intersects(player.getRect(), f.getRect())){
                killedEnemysound.start();
                score += 1000;
                boom.change( f.getX(), f.getY());
                f.changeX();
            }
        }

        for (BadFriend f : badfriends) {
            f.update(player.getSpeed());
            if(Rect.intersects(player.getRect(), f.getRect())){
                killedEnemysound.start();
                score -= 1000;
                if(score<0) isGameOver = false;
                boom.change( f.getX(), f.getY());
                f.changeX();
            }
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}