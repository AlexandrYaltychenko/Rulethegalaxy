package md.pickhack.rulethegalaxy.Graphics.UI;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import md.pickhack.rulethegalaxy.Logic.User;
import static md.pickhack.rulethegalaxy.MainActivity.*;
import static md.pickhack.rulethegalaxy.Graphics.Engine.*;
/**
 * Created by alexialt on 24.10.15.
 */
public class TopPanel extends UIElement {
    private User user;
    private Bitmap sprite;
    private static Paint paint = new Paint();
    private float text_y;
    public TopPanel(User user, float x, float y, float w, float h){
        super(x,y,w,h);
        this.user = user;
        this.sprite = textures.get("panel");
        this.text_y = 0.035f+graphics.getTextHeight(user.getNickname(),paint,12)/2;
    }

    @Override
    public void draw(Canvas canvas) {
        graphics.drawImage(canvas,sprite,x,y,w,h,paint);
        String nick = user.getNickname();
        if (nick.length()>6)
            nick = nick.substring(0,5) + "..";
        graphics.drawText(canvas, nick, 0.12f, text_y, paint, 12, Paint.Align.LEFT, Color.WHITE);
        graphics.drawText(canvas,"322",0.47f,text_y,paint,12, Paint.Align.CENTER,Color.WHITE);
        graphics.drawText(canvas,"222",0.71f,text_y,paint,12, Paint.Align.CENTER,Color.WHITE);
        graphics.drawText(canvas,"92",0.94f,text_y,paint,12, Paint.Align.CENTER,Color.WHITE);
    }
}
