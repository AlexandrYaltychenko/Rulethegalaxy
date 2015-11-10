package md.pickhack.rulethegalaxy.Graphics.Map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import static md.pickhack.rulethegalaxy.MainActivity.*;
import md.pickhack.rulethegalaxy.Graphics.UI.UIElement;

/**
 * Created by Alexandr on 30.10.15.
 */
public class SolarSystem{
    private static Random rand = new Random();
    private ArrayList<MapPlanet> planets;
    private float radius,angle;
    private float x,y;
    private float ax,ay,ar;
    private int num;
    private static float proportion;

    public SolarSystem(float radius, float angle){
        num = rand.nextInt()%1000000;
        planets = new ArrayList<MapPlanet>();
        proportion = graphics.Height/graphics.Width;
        this.radius = radius;
        this.angle = angle;
        float proportion = graphics.Width/graphics.Height;
        this.x = (float)(radius*Math.cos(angle*3.14f/180f));
        this.y = (float)(radius*Math.sin(angle*3.14f/180f)*proportion);
        fakeFill();

    }

    public void fakeFill(){
        Random rand = new Random();
        int count = 9;
        float angle = 0f;
        float radius = 0.05f;
        String name = null;
        while (planets.size()<count){
            int ran = Math.abs(rand.nextInt()%3);
            switch(ran){
                case 0: name = "A"+Math.abs(rand.nextInt()%10000); break;
                case 1: name = "B"+Math.abs(rand.nextInt()%10000); break;
                default: name = "C"+Math.abs(rand.nextInt()%10000); break;
            }
            planets.add(new MapPlanet(radius,angle,ran,name));
            angle+=40;
            radius += 0.01f;
        }
    }

    public boolean isAtScreen(){
        if (ax<-0.5f || ax>1.5f || ay>1.5f || ay<-0.5f) return false;
            return true;
    }

    public void draw (Canvas canvas, Paint paint, float scale, float moveX, float moveY, int alpha){
        //Log.d("DRAW",x+" - "+y );
        ax = 0.5f+this.x*scale+moveX*scale;
        ay = 0.5f+this.y*scale+moveY*scale;
        ar = 0.005f*scale;
        if (isAtScreen())
        graphics.drawCircle(canvas,paint,ax,ay,ar,Color.WHITE,alpha);
    }

    public void drawSingle(Canvas canvas, Paint paint, float scale, float moveX, float moveY){
        //Log.d("SINGLE","DRAWING "+ax+" "+ay);
        //System.exit(0);
        paint.setAlpha(255);
        float single_scale = scale - 28f;
        ax = 0.5f+this.x*scale+moveX*scale;
        ay = 0.5f+this.y*scale+moveY*scale;
        int alpha = (int)((single_scale-1f)*255f);
        if (alpha>255) alpha = 255;
        //Log.d("SCALE", "ALPHA "+alpha);
        for (MapPlanet i: planets)
            i.draw(canvas,paint,single_scale,ax,ay, alpha);
        if (alpha<255)
            graphics.drawCircle(canvas, paint, ax, ay, ar * single_scale, Color.WHITE,255-alpha);
        graphics.drawCircle(canvas, paint, ax, ay, 0.025f * single_scale, Color.WHITE,alpha);
    }

    public float calcDimensionTo(float x, float y){
        return Math.abs((float)Math.sqrt((ax-x)*(ax-x)+(ay-y)*(ay-y)));
    }

    public int getNum(){
        return num;
    }
}
