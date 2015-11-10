package md.pickhack.rulethegalaxy;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import md.pickhack.rulethegalaxy.Graphics.Engine;
import md.pickhack.rulethegalaxy.Graphics.Scenes.GalacticScene;
import md.pickhack.rulethegalaxy.Graphics.StateMachine;
import md.pickhack.rulethegalaxy.Logic.Messaging;
import md.pickhack.rulethegalaxy.Logic.User;
import md.pickhack.rulethegalaxy.Server.Login;

public class MainActivity extends Activity {
    public static Engine graphics;
    public static Login login;
    public static MainActivity MainScreen;
    public static User user;
    public static Messaging messaging;
    @Override
    public void onCreate(Bundle s){
        super.onCreate(s);
        startGame();
        //graphics = new Engine(this);
        //setContentView(graphics);
        MainScreen = this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        /*setContentView(R.layout.login);
        login = new Login((EditText)findViewById(R.id.editText),
                (EditText)findViewById(R.id.editText2),
                (Button)findViewById(R.id.button),
                (Button)findViewById(R.id.button2),
                (TextView)findViewById(R.id.textView6));*/

    }

    @Override
    public void onBackPressed(){
        switch(graphics.state){
            case StateMachine.MAINSCENE: super.onBackPressed(); System.exit(0);break;
            case StateMachine.GALACTICSCENE: graphics.Galacticscene.hide(graphics.Mainscene); break;
            case StateMachine.PLANETSCENE: graphics.Planetscene.hide(graphics.Mainscene); break;
        }
    }

    public void startGame(){
        user = new User("Jorik123","12345bababa");
        messaging = new Messaging(user);
        graphics = new Engine(this);
        setContentView(graphics);
    }

}
