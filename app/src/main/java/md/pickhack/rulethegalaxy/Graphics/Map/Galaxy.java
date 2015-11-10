package md.pickhack.rulethegalaxy.Graphics.Map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import md.pickhack.rulethegalaxy.Graphics.Engine;
import md.pickhack.rulethegalaxy.Graphics.UI.UIElement;
import static md.pickhack.rulethegalaxy.MainActivity.*;
/**
 * Created by alexialt on 25.10.15.
 */
public class Galaxy extends UIElement {
    private static Paint paint = new Paint();
    private Bitmap screen, stars;
    private float scale;
    private float angle;
    private float prevX1, prevY1, prevX2, prevY2, prevR;
    private float moveX, moveY;
    private SolarSystem selected;
    private static float proportion;
    private boolean drawingOnce = true;
    private Bitmap texture;

    private ArrayList<Ring> rings;
    public Galaxy(Bitmap screen, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.screen = screen;
        proportion = graphics.Width/graphics.Height;
        scale = 1f;
        angle = 0f;
        screen = Engine.textures.get("galaxy_view");
        rings = new ArrayList<Ring>();
        fakeFill();
        moveX = 0f;
        moveY = 0f;
        selected = null;
    }


    public void fakeFill(){
        float radius = 0.13f;
        float angle = 0f;
        rings.add(new Ring(radius, angle));
        int i = 0;
        while (i<300){
            while (rings.get(rings.size()-1).addSystem());
            radius += 0.035f;
            angle += 5f;
            rings.add(new Ring(radius,angle));
            i += 15;
        }
    }

    @Override
    public void draw(Canvas canvas){
        /*if (drawingOnce){
            graphics.drawImageCenter(canvas,texture,0.5f + moveX * scale,0.5f + moveY * scale,);
        }*/
        float scale = this.scale;
        float moveX = this.moveX;
        float moveY = this.moveY;
        canvas.drawColor(Color.BLACK);
        int alpha = (int)(255 - scale*90.5f);
        if (alpha>255) alpha = 255;
        if (alpha<0) alpha = 0;
        //Log.d("ALPHA",alpha+" "+proportion+" "+scale+" "+(scale)*2.55f);
        paint.setAlpha(alpha);
        graphics.drawImageCenter(canvas,screen,0.5f + moveX * scale,0.5f + moveY * scale,3f*scale,3f*proportion*scale,paint);
        if (scale>=29f ) {
            if (selected != null) {
                selected.drawSingle(canvas, paint, scale, moveX, moveY);
                return;
            }
            else
                selectSystem();
        }
        else{
            selected = null;
        }
        //graphics.drawImage(canvas,stars,0f,0f,1f,1f,paint);
        graphics.drawCircle(canvas, paint, 0.5f + moveX * scale, 0.5f + moveY * scale, 0.1f * scale, Color.WHITE);
        for (Ring i: rings)
            i.draw(canvas,paint,scale,moveX,moveY);
    }

    public void onSingleTouchDown(float mx, float my){
        prevX1 = mx;
        prevY1 = my;
    }

    public void onSingleTouchUp(float mx, float my){

    }

    public void onSingleTouchMove(float mx, float my){
        if (Math.abs(prevX1 - mx)>0.0025f && Math.abs(prevX1 - mx)<0.1f) {
            if (scale<1)
            moveX -= (prevX1 - mx)*scale;
            else
                moveX -= (prevX1 - mx)/scale;
        }
        if (Math.abs(prevY1 - my)>0.0025f && Math.abs(prevY1 - my)<0.1f) {
            if (scale<1)
            moveY -= (prevY1 - my)*scale;
            else
                moveY -= (prevY1 - my)/scale;
        }
        prevX1 = mx;
        prevY1 = my;
    }

    public void processMoveFirst(float mx, float my){
        prevX1 = mx;
        prevY1 = my;
    }

    public void processMoveSecond(float mx, float my){
        float R = (float) Math.sqrt((prevX1-prevX2)*(prevX1-prevX2)+(prevY1-prevY2)*(prevY1-prevY2));
        if (Math.abs(prevR)<0.0001f) {
            prevR = R;
            return;
        }
        //Log.d("POINTER","R = "+R+" PREV_R = "+prevR);
        if (prevR - R > 0.01f) scaleOut();
        else
            if (prevR - R < -0.01f) scaleIn();
        prevR = R;
        prevX2 = mx;
        prevY2 = my;

    }

    public void createBitmap(){
        texture = Bitmap.createBitmap((int)(graphics.Width*scale), (int)(graphics.Height*scale), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(texture);
        draw(canvas);
    }

    public void selectSystem(){
        selected = null;
        ArrayList<SolarSystem> accepted = new ArrayList<SolarSystem>();
        //Log.d("SINGLE","TRYING TO SELECT A SYSTEM");
        for (Ring i: rings) {
            i.getVisible(accepted);

        }
        if (accepted.size()>0){
            selected = accepted.get(0);
            float min = selected.calcDimensionTo(0.5f,0.5f);
            float temp;
            for (SolarSystem i: accepted){
                temp = i.calcDimensionTo(0.5f,0.5f);
                Log.d("DIM",""+temp+" system: "+i.getNum());
                if (temp<min) {
                    selected = i;
                    min = temp;
                }

            }
            Log.d("DIM","FINALLY SELECTED SYSTEM "+selected.getNum());
        }
    }

    public void scaleOut(){
        if (scale>0.35f)
            if (scale<29f)
            scale -= 0.025f*scale;
            else{
                scale -= 0.025f*(1f+scale-29.1f);;
            }
        else
            return;
    }
    public void scaleIn(){
        if (scale<35f) {
            //Log.d("SCALE ","scale: "+scale);
            if (scale<29f) {
                scale += 0.025f * scale;
                if (scale > 29.1f)
                    scale = 29.1f;
            }
            else
                scale += 0.025f*(1f+scale-29.1f);
        }
        else
            return;
    }
    public void rotate(){
    }

}
