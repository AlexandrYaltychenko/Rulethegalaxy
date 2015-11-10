package md.pickhack.rulethegalaxy.Interface;

import android.app.ProgressDialog;
import static md.pickhack.rulethegalaxy.MainActivity.*;
/**
 * Created by alexialt on 23.10.15.
 */
public abstract class Rehandler {
    public static final int SHOW_LOADING = 0;
    public static final int HIDE_LOADING = 1;
    private static ProgressDialog PD;
    public static void processMessage(int msg){
        if (msg == SHOW_LOADING){
            if (PD != null) return;
                PD = ProgressDialog.show(MainScreen, "", "Loading...");
        }
        if (msg == HIDE_LOADING){
            if (PD != null) {
                PD.dismiss();
                PD = null;
            }
        }
    }
}
