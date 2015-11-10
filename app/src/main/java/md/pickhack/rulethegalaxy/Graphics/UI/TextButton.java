package md.pickhack.rulethegalaxy.Graphics.UI;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import md.pickhack.rulethegalaxy.Graphics.UI.Listeners.ButtonListener;

import static md.pickhack.rulethegalaxy.Graphics.Engine.*;
import static md.pickhack.rulethegalaxy.MainActivity.*;
/**
 * Created by alexialt on 25.10.15.
 */
public class TextButton extends UIElement{
    private String caption;
    private static Paint paint = new Paint();
    private static Bitmap sprite, pressed;
    private int text_size = 40;
    private float text_h,text_y;
    private boolean isPressed;
    private ButtonListener listener;

    public void setOnClickListener(ButtonListener listener){
        this.listener = listener;
    }
    public TextButton(String text, float x, float y, float w, float h){
        super(x,y,w,h);
        text_h = graphics.getTextHeight(text,paint,text_size);
        text_y = y +(h+text_h)/2;
        caption = text;
        if (sprite == null)
        {
            sprite = textures.get("btn_text");
            pressed = textures.get("btn_text2");
        }

    }
    public TextButton(String text, float x, float y, float w, float h, int text_size){
        super(x,y,w,h);
        text_h = graphics.getTextHeight(text,paint,text_size);
        text_y = y +(h+text_h)/2;
        caption = text;
        this.text_size = text_size;
        if (sprite == null)
        {
            sprite = textures.get("btn_text");
            pressed = textures.get("btn_text2");
        }

    }
    @Override
    public void draw(Canvas canvas){
        graphics.drawImage(canvas, isPressed?pressed:sprite, x, y, w, h, paint);
        graphics.drawText(canvas,caption,x+w/2,text_y,paint,text_size, Paint.Align.CENTER, Color.WHITE);
    }
    @Override
    public void onTouchDown(float mx, float my){
        if (isAt(mx,my))
            isPressed = true;
    }
    @Override
    public void onTouchUp(float mx, float my){
        if (isPressed)
            if (listener != null)
            listener.onClick(this);
        isPressed = false;
    }
}
