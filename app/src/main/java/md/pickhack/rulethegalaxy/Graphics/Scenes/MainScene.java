package md.pickhack.rulethegalaxy.Graphics.Scenes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import md.pickhack.rulethegalaxy.Graphics.Scene;
import md.pickhack.rulethegalaxy.Graphics.StateMachine;
import md.pickhack.rulethegalaxy.Graphics.UI.Listeners.ButtonListener;
import md.pickhack.rulethegalaxy.Graphics.UI.TextButton;
import md.pickhack.rulethegalaxy.Graphics.UI.TopPanel;
import md.pickhack.rulethegalaxy.Graphics.UI.UIElement;
import md.pickhack.rulethegalaxy.Graphics.UI.UIHolder;
import md.pickhack.rulethegalaxy.MainActivity;

import static md.pickhack.rulethegalaxy.Graphics.Engine.*;
import static md.pickhack.rulethegalaxy.MainActivity.*;
/**
 * Created by alexialt on 20.10.15.
 */
public class MainScene implements Scene {
    private Bitmap background, template;
    private Paint paint;
    private float angle = 0;
    private float scale = 1.0f;
    private boolean scaling = true;
    private boolean isClosing;
    private Scene next;
    private TopPanel panel;
    private TextButton back,view;
    private UIHolder buttons;
    public MainScene(){
        paint = new Paint();
    }
    @Override
    public void show(int parameter) {
        graphics.state = StateMachine.MAINSCENE;
        loadTextures();
        createInterface();
    }

    @Override
    public void hide(Scene toShowNext) {
        isClosing = true;
        next = toShowNext;
    }

    @Override
    public void releaseTextures() {
        if (next == null) {
            isClosing = false;
            return;
        }
        isClosing = false;
        next.show(0);
        next = null;
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawColor(Color.rgb(0, 0, 0));
        graphics.drawImageCenter(canvas, background, 0.5f, 0.5f, 1.2f, 0.7f, angle, 0.5f, 0.5f, paint);
        buttons.draw(canvas);
        panel.draw(canvas);
        if (isClosing)
            releaseTextures();
    }

    @Override
    public void loadTextures() {
        textures.addTextures("galaxy_view", "template", "panel", "btn_text", "btn_text2");
    }

    @Override
    public void createInterface() {
        background = textures.get("galaxy_view");
        template = textures.get("template");
        panel = new TopPanel(user,0f,0f,0.6f,0.1f);
        back = new TextButton("Back",0.1f,0.85f,0.4f,0.1f);
        back.setOnClickListener(new ButtonListener(){
            @Override
            public void onClick(UIElement el){
                MainActivity.graphics.Mainscene.hide(MainActivity.graphics.Planetscene);
            }
        });
        view = new TextButton("View",0.5f,0.85f,0.4f,0.1f);
        view.setOnClickListener(new ButtonListener(){
            @Override
            public void onClick(UIElement el){
                MainActivity.graphics.Mainscene.hide(MainActivity.graphics.Galacticscene);
            }
        });
        buttons = new UIHolder(back,view);
    }

    @Override
    public void processTouch(float mx, float my, int state) {
        buttons.processTouch(mx,my,state);
    }

    public TopPanel getPanel(){
        return panel;
    }
}
