package md.pickhack.rulethegalaxy.Graphics.PlanetView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import md.pickhack.rulethegalaxy.Logic.Planet;

import static md.pickhack.rulethegalaxy.MainActivity.*;
import static md.pickhack.rulethegalaxy.Graphics.Engine.*;
/**
 * Created by Alexandr on 07.11.15.
 */
public class PlanetCell {
    private float x, y,w,h;
    private float nowX, nowY;
    private Bitmap icon;
    private int num;
    private int type;
    private static Bitmap contur;

    public PlanetCell(float x, float y, float w, float h, int num, int type){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.num = num;
        switch (type) {
            case 0:
                this.icon = textures.get("geo_0");
                break;
            case 1:
                this.icon = textures.get("geo_1");
                break;
            case 2:
                this.icon = textures.get("geo_2");
                break;
        }
        if (contur == null)
            contur = textures.get("contur");
    }

    public boolean isAtScreen(float moveX){
        if (x+moveX>1f || x+moveX+w<0f) return false;
        else
            return true;
    }


    public void draw(Canvas canvas, Paint paint, float moveX, float moveY){
        nowY = y + moveY;
        if (isAtScreen(moveX)) {
            nowX = x + moveX;
        }
        else if (isAtScreen(moveX + PlanetGenerator.Width)){
            nowX = x + moveX + PlanetGenerator.Width;
        }
        else {
            if (isAtScreen(moveX - PlanetGenerator.Width)){
            nowX = x +moveX - PlanetGenerator.Width;}
            else
                nowX = x +moveX - 2f*PlanetGenerator.Width;
        }
        graphics.drawImage(canvas, icon, nowX, nowY, w, h, paint);
        graphics.drawImage(canvas, contur, nowX, nowY, w, h, paint);
        graphics.drawTextCenter(canvas,""+num,nowX+0.5f*w,nowY+0.5f*h,paint,24, Color.WHITE);
    }
}
