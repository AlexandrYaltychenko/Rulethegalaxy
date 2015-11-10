package md.pickhack.rulethegalaxy.Graphics;

import android.graphics.Canvas;

/**
 * Created by ExcalibuR on 7/2/2015.
 */
public interface Scene {
    void show(int parameter);
    void hide(Scene toShowNext);
    void releaseTextures();
    void render(Canvas canvas);
    void loadTextures();
    void createInterface();
    void processTouch(float mx, float my, int state);
}
