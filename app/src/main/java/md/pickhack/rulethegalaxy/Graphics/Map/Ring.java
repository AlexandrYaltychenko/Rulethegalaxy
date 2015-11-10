package md.pickhack.rulethegalaxy.Graphics.Map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

import static md.pickhack.rulethegalaxy.MainActivity.graphics;

/**
 * Created by Alexandr on 03.11.15.
 */
public class Ring {
    private ArrayList<SolarSystem> systems;
    private float radius,angle;
    private float x,y;

    public Ring(float radius, float angle){
        systems = new ArrayList<SolarSystem>();
        this.radius = radius;
        this.angle = angle;
        this.x = (float)(radius*Math.cos(angle*3.14f/180f));
        this.y = (float)(radius*Math.sin(angle*3.14f/180f));

    }

    public void draw (Canvas canvas, Paint paint, float scale, float moveX, float moveY){
        //Log.d("DRAW",x+" - "+y );
        int alpha = (int)((scale-0.3f)*255f);
        if (alpha>255) alpha = 255;
        if (alpha<0) alpha = 0;
        for (SolarSystem i: systems)
            i.draw(canvas,paint,scale,moveX,moveY,alpha);
    }

    public boolean addSystem(){
        if (getCount()<24){
            Log.d("GENERING","radius = "+radius+" angle = "+angle);
            systems.add(new SolarSystem(radius,angle));
            angle+=15;
            return true;
        }
        else
            return false;
    }

    public int getCount(){
        return systems.size();
    }

    public void getVisible(ArrayList<SolarSystem> systems){
        for (SolarSystem i: this.systems)
            if (i.isAtScreen()) {
                systems.add(i);
               // Log.d("DIM","ADDED");
            }
    }
}