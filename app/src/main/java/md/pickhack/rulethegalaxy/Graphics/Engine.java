package md.pickhack.rulethegalaxy.Graphics;

/**
 * Created by alexialt on 20.10.15.
 */


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RatingBar;

import md.pickhack.rulethegalaxy.Graphics.Scenes.GalacticScene;
import md.pickhack.rulethegalaxy.Graphics.Scenes.LoadingScene;
import md.pickhack.rulethegalaxy.Graphics.Scenes.MainScene;
import md.pickhack.rulethegalaxy.Graphics.Scenes.PlanetScene;
import md.pickhack.rulethegalaxy.Logic.Planet;

public class Engine extends SurfaceView implements SurfaceHolder.Callback {
    public static TexturePack textures;
    DrawThread drawThread;
    public float Width, Height, Proportion;
    public int state = 0;
    static Context context;
    static Typeface font;
    TextPaint mTextPaint=new TextPaint();
    StaticLayout mTextLayout;
    public MainScene Mainscene;
    public GalacticScene Galacticscene;
    public Scene Loadingscene, Planetscene;

    public Engine(Context context) {
        super(context);
        this.context = context;
        font = Typeface.createFromAsset(getContext().getAssets(),"xirod.ttf");
        textures = new TexturePack(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX()/Width;
        float y = event.getY()/Height;
        switch(state){
            case StateMachine.MAINSCENE: Mainscene.processTouch(x,y,event.getAction()); break;
            case StateMachine.GALACTICSCENE: Galacticscene.processTouch(event,event.getActionMasked()); break;
            case StateMachine.LOADINGSCENE: Loadingscene.processTouch(x,y,event.getAction()); break;
            case StateMachine.PLANETSCENE: Planetscene.processTouch(x,y,event.getAction()); break;
        }
        return true;
    }

    public void makeThread() {
        drawThread = new DrawThread(this.getHolder());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas(null);
            if (canvas == null)
                return;
            Engine.this.Width = canvas.getWidth();
            Engine.this.Height = canvas.getHeight();
            Proportion = Height/768.0f;
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
                postInvalidate();
            }
        }
        /*Создание сцен*/
        Mainscene = new MainScene();
        Galacticscene = new GalacticScene();
        Loadingscene = new LoadingScene();
        Planetscene = new PlanetScene();
        Mainscene.show(0);
        //TODO show
        makeThread();
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.setRunning(false);
    }

    class DrawThread extends Thread {

        private boolean running = false;
        private SurfaceHolder surfaceHolder;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            while(running){
                Canvas canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    if (canvas == null)
                        continue;
                    Width = canvas.getWidth();
                    Height = canvas.getHeight();
                    Proportion = Height/768.0f;
                    switch(state){
                        case StateMachine.MAINSCENE: Mainscene.render(canvas); break;
                        case StateMachine.GALACTICSCENE: Galacticscene.render(canvas); break;
                        case StateMachine.LOADINGSCENE: Loadingscene.render(canvas); break;
                        case StateMachine.PLANETSCENE: Planetscene.render(canvas); break;
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        postInvalidate();
                    }
                }
            }
        }

    }
    public void drawImage(Canvas canvas,Bitmap bmp, float left, float top,float width, float height, Paint paint){
        left *= Width;
        top *= Height;
        width *= Width;
        height *= Height;
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setTranslate(left, top);
        matrix.preScale(width / bmp.getWidth(), height / bmp.getHeight());
        canvas.drawBitmap(bmp, matrix, paint);
    }
    public void drawImage(Canvas canvas,Bitmap bmp, float left, float top,float width, float height, float angle, float ax, float ay, Paint paint){
        left *= Width;
        top *= Height;
        width *= Width;
        height *= Height;
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.postScale(width / bmp.getWidth(), height / bmp.getHeight());
        matrix.postRotate(angle, width * ax, height * ay);
        matrix.postTranslate(left, top);
        canvas.drawBitmap(bmp, matrix, paint);
    }
    public void drawImageCenter(Canvas canvas, Bitmap bmp, float x, float y, float w, float h, Paint paint){
        x *= Width;
        y *= Height;
        w *= Width;
        h *= Height;
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setTranslate(x - w / 2, y - h / 2);
        matrix.preScale(w / bmp.getWidth(), h / bmp.getHeight());
        canvas.drawBitmap(bmp, matrix, paint);
    }
    public void drawImageCenter(Canvas canvas, Bitmap bmp, float x, float y, float w, float h, float angle, float ax, float ay, Paint paint){
        x *= Width;
        y *= Height;
        w *= Width;
        h *= Height;
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.postScale(w / bmp.getWidth(), h / bmp.getHeight());
        matrix.postRotate(angle, w * ax, h * ay);
        matrix.postTranslate(x - w / 2, y - h / 2);
        canvas.drawBitmap(bmp, matrix, paint);
    }
    public void drawText(Canvas canvas, String text, float x, float y, Paint paint, int size, Paint.Align l, int color){
        int c = paint.getColor();
        paint.setTypeface(font);
        paint.setColor(color);
        paint.setTextSize(size * Proportion);
        paint.setTextAlign(l);
        canvas.drawText(text, Width * x, Height * y, paint);
        paint.setColor(c);
    }
    public void drawTextCenter(Canvas canvas, String text, float x, float y, Paint paint, int size, int color){
        int c = paint.getColor();
        Rect bounds = new Rect();
        paint.setTypeface(font);
        paint.setTextSize(size * Proportion);
        paint.getTextBounds(text, 0, text.length(), bounds);
        y += bounds.height()/Height/2f;
        paint.setColor(color);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, Width * x, Height * y, paint);
        paint.setColor(c);
    }
    public void drawTextCenter(Canvas canvas, String text, float x, float y, Paint paint, int size, int color, int alpha){
        int c = paint.getColor();
        Rect bounds = new Rect();
        paint.setTypeface(font);
        paint.setTextSize(size * Proportion);
        paint.getTextBounds(text, 0, text.length(), bounds);
        y += bounds.height()/Height/2f;
        paint.setColor(color);
        paint.setAlpha(alpha);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, Width * x, Height * y, paint);
        paint.setColor(c);
    }

    public void drawTextWrap(Canvas canvas, String text, float x, float y, float width, int color, int size){
        mTextPaint.setColor(color);
        mTextPaint.setTypeface(font);
        mTextPaint.setTextSize(size*Proportion);
        StaticLayout mTextLayout = new StaticLayout(text, mTextPaint, (int)(width*Width), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        canvas.save();
// calculate x and y position where your text will be placed
        float textX = x*Width;
        float textY = y * Height;
        canvas.translate(textX, textY);
        mTextLayout.draw(canvas);
        canvas.restore();
    }

    public void drawCircle(Canvas canvas, Paint paint, float cx, float cy, float radius, int color){
        paint.setColor(color);
        canvas.drawCircle(cx * Width, cy * Height, radius * Width, paint);
    }

    public void drawCircle(Canvas canvas, Paint paint, float cx, float cy, float radius, int color, int alpha){
        paint.setColor(color);
        paint.setAlpha(alpha);
        canvas.drawCircle(cx*Width, cy*Height, radius*Width, paint);
    }

    public float getTextWidth(String text, Paint paint, int size){
        Rect bounds = new Rect();
        paint.setTextSize(size * Proportion);
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width()/Width;
    }
    public float getTextHeight(String text, Paint paint, int size){
        Rect bounds = new Rect();
        paint.setTextSize(size * Proportion);
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height()/Height;
    }

    public void drawRectangle(Canvas canvas, Paint paint, float x, float y, float w, float h, int color, int alpha){
        paint.setColor(color);
        paint.setAlpha(alpha);
        canvas.drawRect(x * Width, y * Height, (x + w) * Width, (y + h) * Height, paint);

    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showInetError(Context context){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("No Network connections available!");
        alert.setTitle("Please, connect to the Internet and try again...");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value
            }
        });



        alert.show();
    }


}
