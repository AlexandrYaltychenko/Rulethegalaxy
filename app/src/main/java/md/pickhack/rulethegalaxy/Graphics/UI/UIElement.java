package md.pickhack.rulethegalaxy.Graphics.UI;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by alexialt on 25.10.15.
 */
public class UIElement {
    protected float x,y,w,h;
    public UIElement(float x, float y, float w, float h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public UIElement(){

    }
    public void draw(Canvas canvas){

    }

    public void draw(Canvas canvas, float x, float y, float w, float h){

    }

    public void onTouchDown(float x, float y){

    }
    public void onTouchUp(float x, float y){

    }
    public void onTouchMove(float x, float y){

    }

    public boolean isAt(float mx, float my){
        if (mx<x || mx> x +w || my<y || my>y+h) return false;
        else
            return true;
    }

}
