package md.pickhack.rulethegalaxy.Graphics.UI;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by ExcalibuR on 8/11/2015.
 */
public class FriendList {
    /*public static float x, y, w, h;
    public float cx, cy, sx, sy;
    public float slideY = 0f;
    public float ScrollLimit;
    public static Bitmap roller;
    public static float rollersize, rollscroll;
    public static ArrayList<Event> events;
    public static int state;
    public FriendList(float x, float y, float w, float h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        Friend.item = textures.get("item");
        Friend.item2 = textures.get("item2");
        Friend.template = textures.get("image_template");
        Friend.invite = textures.get("invite");
        Pointer.on = textures.get("pointon");
        Pointer.off = textures.get("pointoff");
        roller = textures.get("roller");
        events = new ArrayList<Event>();
    }
    public void processTouch(float mx, float my, int state){
        mx /= Graphics.Width;
        my /= Graphics.Height;
        if (mx < x || mx > x + w || my < y || my > y + h) return;
        switch(state){
            case MotionEvent.ACTION_DOWN: startScroll(mx, my); break;
            case MotionEvent.ACTION_MOVE: processScroll(mx, my); break;
            case MotionEvent.ACTION_UP: endScroll(mx, my); break;
        }
    }

    public void setState(int state){
        this.state = state;
        slideY = 0f;
        switch(state){
            case 0: if (ServerSync.friends.size()>3)
                ScrollLimit = -0.16f*(ServerSync.friends.size()-3);
            else
                ScrollLimit = 0; break;
            case 1: if (events.size()>3)
                ScrollLimit = -0.16f*(events.size()-3);
            else
                ScrollLimit = 0; break;
        }
        if (slideY>-0.01f)
            rollscroll = 0f;
        else
            rollscroll = (slideY/ScrollLimit)*(0.494f-rollersize);
    }
    public void startScroll(float mx, float my){
        cx = sx = mx;
        cy = sy = my;
        if (state == 0)
        for (Friend i: ServerSync.friends)
            i.processClick(mx,my, MotionEvent.ACTION_DOWN);
        else
            for (Event i: events)
                i.processClick(mx,my, MotionEvent.ACTION_DOWN);
        switch(state){
            case 0: if (ServerSync.friends.size()>3)
                ScrollLimit = -0.16f*(ServerSync.friends.size()-3);
            else
                ScrollLimit = 0; break;
            case 1: if (events.size()>3)
                ScrollLimit = -0.16f*(events.size()-3);
            else
                ScrollLimit = 0; break;
        }

    }
    public void processScroll(float mx, float my){
        if (state ==0)
        for (Friend i: ServerSync.friends)
            i.processClick(mx,my, MotionEvent.ACTION_MOVE);
        else
            for (Event i: events)
                i.processClick(mx,my, MotionEvent.ACTION_MOVE);
        if (Math.abs(cy - my)>0.015f) {
            slideY -= (cy - my);
            cy = my;
            cx = mx;
            if (slideY>0) slideY = 0;
            else
            if (slideY<ScrollLimit) slideY = ScrollLimit;
            if (slideY>-0.01f)
                rollscroll = 0f;
            else
            rollscroll = (slideY/ScrollLimit)*(0.494f-rollersize);
        }

    }
    public void endScroll(float mx, float my){
        if (Math.abs(my - sy)<0.015f)
            switch(state){
                case 0: for (Friend i: ServerSync.friends)
                    i.processClick(mx,my, MotionEvent.ACTION_UP); break;
                case 1:  for (Event i: events)
                    i.processClick(mx,my, MotionEvent.ACTION_UP); break;
            }

    }
    public void draw(Canvas canvas, Paint paint) {
        switch(state){
            case 0: if (ServerSync.friends.size()<3) rollersize = 0.494f; else rollersize = 0.494f*(3f/(ServerSync.friends.size())); break;
            case 1: if (events.size()<3) rollersize = 0.494f; else rollersize = 0.494f*(3f/(events.size())); break;
        }
        if (state ==0) {
            float y = this.y + slideY;
            for (Friend i : ServerSync.friends) {
                i.draw(x, y, w, 0.15f, canvas, paint);
                y += 0.16f;
            }
        }
        else {
            float y = this.y + slideY;
            for (Event i : events) {
                i.draw(x, y, w, 0.15f, canvas, paint);
                y += 0.16f;
            }
        }
        Graphics.drawImage(canvas, FriendScene.back, 0f, 0f, 1f, 1f, paint);
        Graphics.drawRectangle(canvas, paint, 0.9245f, 0.3965f + rollscroll, 0.02f, rollersize, Color.YELLOW, 255);
        Graphics.drawText(canvas, "" + ServerSync.highscore, 0.25f, 0.167f, paint, 30, Paint.Align.LEFT, Color.WHITE);
        Graphics.drawText(canvas, "" + ServerSync.balance, 0.595f, 0.167f, paint, 30, Paint.Align.LEFT, Color.WHITE);
        Graphics.drawText(canvas, "Games: "+ServerSync.games+", Wins: "+ServerSync.wins,0.5f,0.24f,paint, 30, Paint.Align.CENTER, Color.rgb(167, 115, 54));
    }*/
}

