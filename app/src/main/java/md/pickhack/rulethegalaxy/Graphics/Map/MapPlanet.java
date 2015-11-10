package md.pickhack.rulethegalaxy.Graphics.Map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

import md.pickhack.rulethegalaxy.Graphics.UI.UIElement;

import static md.pickhack.rulethegalaxy.Graphics.Engine.*;
import static md.pickhack.rulethegalaxy.MainActivity.graphics;

/**
 * Created by Alexandr on 30.10.15.
 */
public class MapPlanet extends UIElement{
    private float angle;
    private float radius;
    private Bitmap icon;
    private String name;
    private static final int VOLCANO = 0;
    private static final int GEO = 1;
    private static final int ROCK = 2;
    private int type;
    private static float proportion;

    public MapPlanet(float radius, float angle, int type, String name){
        super();
        this.name = name;
        this.type = type;
        this.radius = radius;
        this.angle = angle;
        proportion = graphics.Width/graphics.Height;
        this.x = (float)(radius*Math.cos(angle*3.14f/180f));
        this.y = (float)(radius*Math.sin(angle*3.14f/180f)*proportion);
        this.w = 0.011f;
        this.h = 0.011f*proportion;
        switch(type){
            case VOLCANO: icon = textures.get("planet_volcano_small"); break;
            case GEO: icon = textures.get("planet_geo_small"); break;
            case ROCK: icon = textures.get("planet_rock_small"); break;
        }


    }


    public void draw(Canvas canvas, Paint paint, float scale, float x, float y, int alpha){
        float ax = this.x*scale+x;
        float ay = this.y*scale+y;
        //Log.d("SINGLE", "PLANET " + ax + " " + ay);
        paint.setStyle(Paint.Style.STROKE);
        graphics.drawCircle(canvas, paint, x, y, radius*scale, Color.DKGRAY,alpha);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAlpha(alpha);
        graphics.drawImageCenter(canvas,icon,ax,ay,w*scale,h*scale,paint);
        graphics.drawTextCenter(canvas,name,ax,ay+h*scale,paint,(int)(3*scale),Color.WHITE,alpha);
        //graphics.drawCircle(canvas,paint,ax,ay,0.05f*scale, Color.GREEN, alpha);
    }
}
