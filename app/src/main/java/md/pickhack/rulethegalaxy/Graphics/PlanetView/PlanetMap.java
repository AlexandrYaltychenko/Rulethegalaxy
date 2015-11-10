package md.pickhack.rulethegalaxy.Graphics.PlanetView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import md.pickhack.rulethegalaxy.Logic.Planet;

/**
 * Created by Alexandr on 07.11.15.
 */
public class PlanetMap {
    private ArrayList<PlanetCell> cells;
    private float moveX, moveY, x, y, prevX1, prevY1;
    private static Paint paint = new Paint();
    private float width;
    public PlanetMap(int key){
        cells  = PlanetGenerator.generate(key);
        width = PlanetGenerator.height * 0.2f;
        moveX = moveY = x = y = 0f;
    }

    public void draw(Canvas canvas){
        float moveX = this.moveX;
        float moveY = this.moveY;
        for (PlanetCell i: cells)
            i.draw(canvas, paint, moveX, moveY);
    }

    public void processTouch(float mx, float my, int state){
        switch (state){
            case MotionEvent.ACTION_DOWN:
                prevX1 = mx;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(prevX1 - mx)>0.0025f && Math.abs(prevX1 - mx)<0.1f) {
                        moveX -= (prevX1 - mx);
                }
                prevX1 = mx;
                break;
        }
        //Log.d("MOVE","x = "+moveX);
        if (moveX > width)
            moveX -= width;
        if (moveX < -width)
            moveX += width;
    }


}
