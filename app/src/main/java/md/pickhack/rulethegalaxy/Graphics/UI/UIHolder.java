package md.pickhack.rulethegalaxy.Graphics.UI;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by alexialt on 25.10.15.
 */
public class UIHolder {
    private ArrayList<UIElement> elements = new ArrayList<UIElement>();
    public UIHolder(){

    }
    public UIHolder(UIElement...array){
        for (int i = 0; i<array.length; i++)
            elements.add(array[i]);
    }

    public void draw(Canvas canvas, float x, float y, float w, float h){
        for (UIElement i: elements)
            i.draw(canvas,x,y,w,h);
    }

    public void draw(Canvas canvas){
        for (UIElement i: elements)
            i.draw(canvas);
    }

    public void processTouch(float mx, float my, int state){
        for (UIElement i: elements)
        switch (state){
            case MotionEvent.ACTION_UP: i.onTouchUp(mx,my); break;
            case MotionEvent.ACTION_MOVE: i.onTouchMove(mx,my); break;
            case MotionEvent.ACTION_DOWN: i.onTouchDown(mx,my); break;
        }
    }

}
