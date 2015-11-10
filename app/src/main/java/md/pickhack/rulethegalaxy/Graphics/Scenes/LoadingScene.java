package md.pickhack.rulethegalaxy.Graphics.Scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import md.pickhack.rulethegalaxy.Graphics.Scene;
import md.pickhack.rulethegalaxy.Graphics.StateMachine;

import static md.pickhack.rulethegalaxy.Graphics.Engine.*;
import static md.pickhack.rulethegalaxy.MainActivity.*;
/**
 * Created by alexialt on 25.10.15.
 */
public class LoadingScene implements Scene {
    private boolean isReady;
    private Paint paint;

    public LoadingScene(){
        paint = new Paint();
        loadTextures();
        createInterface();
        isReady = true;
    }

    @Override
    public void show(int parameter) {
        graphics.state = StateMachine.LOADINGSCENE;
    }

    @Override
    public void hide(Scene toShowNext) {

    }

    @Override
    public void releaseTextures() {

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        if (isReady == false) return;
        graphics.drawText(canvas,"LOADING...",0.5f,0.5f,paint,48, Paint.Align.CENTER, Color.WHITE);
    }

    @Override
    public void loadTextures() {
        textures.addTextures("Loading");
    }

    @Override
    public void createInterface() {
        Log.d("IFACE", "INTERFACE CREATED");
    }

    @Override
    public void processTouch(float mx, float my, int state) {

    }
}
