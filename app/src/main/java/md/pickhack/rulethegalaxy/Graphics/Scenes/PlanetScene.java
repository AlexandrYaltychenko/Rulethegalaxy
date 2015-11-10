package md.pickhack.rulethegalaxy.Graphics.Scenes;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.Random;

import md.pickhack.rulethegalaxy.Graphics.PlanetView.PlanetCell;
import md.pickhack.rulethegalaxy.Graphics.PlanetView.PlanetMap;
import md.pickhack.rulethegalaxy.Graphics.Scene;
import md.pickhack.rulethegalaxy.Graphics.StateMachine;
import md.pickhack.rulethegalaxy.Graphics.UI.TopPanel;

import static md.pickhack.rulethegalaxy.MainActivity.*;
import static md.pickhack.rulethegalaxy.Graphics.Engine.*;
/**
 * Created by Alexandr on 07.11.15.
 */
public class PlanetScene implements Scene {
    private TopPanel panel;
    private boolean isReady;
    private boolean isClosing;
    private Scene next;
    private PlanetMap planetMap;
    @Override
    public void show(int parameter) {
        isReady = false;
        new Thread() {
            @Override
            public void run() {
                PlanetScene.this.panel = graphics.Mainscene.getPanel();
                loadTextures();
                createInterface();
                graphics.state = StateMachine.PLANETSCENE;
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
        textures.removePrefix("geo");
        isClosing = false;
        next.show(0);
    }

    @Override
    public void render(Canvas canvas) {
        if (isReady == false) {
            graphics.Loadingscene.render(canvas);
            return;
        }
        canvas.drawColor(Color.BLACK);
        planetMap.draw(canvas);
        panel.draw(canvas);
        if (isClosing)
            releaseTextures();
    }

    @Override
    public void loadTextures() {
        textures.addTextures("geo_0","geo_1","geo_2","contur");
    }

    @Override
    public void createInterface() {
        Random rand = new Random();
        planetMap = new PlanetMap(Math.abs(rand.nextInt()%1000000)+1000000);
    }

    @Override
    public void processTouch(float mx, float my, int state) {
        planetMap.processTouch(mx,my,state);
    }
}
