package md.pickhack.rulethegalaxy.Graphics.Scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import md.pickhack.rulethegalaxy.Graphics.Map.Galaxy;
import md.pickhack.rulethegalaxy.Graphics.Scene;
import md.pickhack.rulethegalaxy.Graphics.StateMachine;
import md.pickhack.rulethegalaxy.Graphics.UI.Listeners.ButtonListener;
import md.pickhack.rulethegalaxy.Graphics.UI.TextButton;
import md.pickhack.rulethegalaxy.Graphics.UI.TopPanel;
import md.pickhack.rulethegalaxy.Graphics.UI.UIElement;
import md.pickhack.rulethegalaxy.Graphics.UI.UIHolder;

import static md.pickhack.rulethegalaxy.MainActivity.graphics;
import static md.pickhack.rulethegalaxy.Graphics.Engine.*;
/**
 * Created by alexialt on 25.10.15.
 */
public class GalacticScene implements Scene {
    private boolean isClosing;
    private boolean isReady;
    private TopPanel panel;
    private Galaxy galaxy;
    private Scene next;
    private TextButton plus,minus;
    private UIHolder holder;
    @Override
    public void show(int parameter) {

        isReady = false;
        new Thread() {
            @Override
            public void run() {
                GalacticScene.this.panel = graphics.Mainscene.getPanel();
                loadTextures();
                createInterface();
                graphics.state = StateMachine.GALACTICSCENE;
                try{
                    sleep(500);
                } catch (Exception e) { }
                isReady = true;
            }
        }.start();
    }

    @Override
    public void hide(Scene toShowNext) {
        isClosing = true;
        next = toShowNext;
    }

    @Override
    public void releaseTextures() {
        textures.removePrefix("galaxy_view");
        textures.removePrefix("stars");
        isClosing = false;
        next.show(0);
        next = null;
    }

    @Override
    public void render(Canvas canvas) {
        if (isReady == false) {
            graphics.Loadingscene.render(canvas);
            return;
        }
        canvas.drawColor(Color.BLACK);
        galaxy.draw(canvas);
        panel.draw(canvas);
        if (isClosing)
            releaseTextures();
        holder.draw(canvas);
    }

    @Override
    public void loadTextures() {
        if (textures.get("galaxy_view") == null)
            textures.addTextures("galaxy_view");
        textures.addTextures("planet_geo_small","planet_rock_small","planet_volcano_small");
    }

    @Override
    public void createInterface() {
        galaxy = new Galaxy(textures.get("galaxy_view"),0f,0f,1.25f,1f);
        plus = new TextButton("+",0.1f,0.9f,0.2f,0.06f,20);
        plus.setOnClickListener(new ButtonListener(){
            @Override
            public void onClick(UIElement el){
                galaxy.scaleIn();
            }
        });
        minus = new TextButton("-",0.8f,0.9f,0.2f,0.06f,20);
        minus.setOnClickListener(new ButtonListener(){
            @Override
            public void onClick(UIElement el){
                galaxy.scaleOut();
            }
        });
        holder = new UIHolder(plus,minus);
    }

    @Override
    public void processTouch(float mx, float my, int state) {

    }

    public void processTouch(MotionEvent event, int state) {
        float mx = 0, my = 0;
        boolean flag = false;
        int pointerCount = event.getPointerCount();
        if (pointerCount>=2) {
        if (state == MotionEvent.ACTION_MOVE) {
            for (int i = 0; i < pointerCount; ++i) {
                int pointerIndex = i;
                int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == 0)
                    galaxy.processMoveFirst(event.getX(0) / graphics.Width, event.getY(0) / graphics.Height);
                if (pointerId == 1)
                    galaxy.processMoveSecond(event.getX(1) / graphics.Width, event.getY(1) / graphics.Height);
            }

            }

        }
        else
        {
            mx = event.getX()/graphics.Width;
            my = event.getY()/graphics.Height;
            holder.processTouch(mx,my,state);
            switch(state){
                case MotionEvent.ACTION_UP: galaxy.onSingleTouchUp(mx,my); break;
                case MotionEvent.ACTION_DOWN: galaxy.onSingleTouchDown(mx, my); break;
                case MotionEvent.ACTION_MOVE: galaxy.onSingleTouchMove(mx,my); break;
            }
        }

    }
}
