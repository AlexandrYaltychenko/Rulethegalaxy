package md.pickhack.rulethegalaxy.Server;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import md.pickhack.rulethegalaxy.MainActivity;
import md.pickhack.rulethegalaxy.R;
import static md.pickhack.rulethegalaxy.MainActivity.*;
/**
 * Created by alexialt on 21.10.15.
 */
public class Login {
    private EditText login;
    private EditText password;
    private Button sign;
    private Button reg;
    private TextView error_display;
    private boolean login_edited;
    private boolean password_edited;
    public Login(EditText login, EditText pass, Button sign, Button reg, final TextView error_display){
        this.login = login;
        this.password = pass;
        this.sign = sign;
        this.reg = reg;
        this.error_display = error_display;
        this.login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (login_edited == false) {
                    Login.this.login.setText("");
                    login_edited = true;
                }

            }
        });
        this.password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (password_edited == false) {
                    Login.this.password.setText("");
                    password_edited = true;
                }

            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SERVER", "CLICKED");
                String r = makeLogin(Login.this.login.getText().toString(), Login.this.password.getText().toString());
                if (r.contains("NO"))
                {
                    error_display.setText("Invalid nickname or password");
                    error_display.setVisibility(View.VISIBLE);
                }
                else {
                    MainScreen.startGame();
                }
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog(null);
            }
        });
    }

    public static String makeLogin(String nickname, String password){
        URL url;
        HttpURLConnection conn = null;
        String response = "";
        try{
            url=new URL("http://caritatea.16mb.com/login.php");
            String param="nickname=" + URLEncoder.encode(nickname,"UTF-8")+
            "&password="+URLEncoder.encode(password, "UTF-8");
            conn=(HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.close();
            Scanner inStream = new Scanner(conn.getInputStream());
            Log.d("SERVER","START READING");
            while (inStream.hasNextLine()) {
                response += (inStream.nextLine());
                break;
            }
        }
        catch(Exception e){
            Log.d("SERVER","ERROR "+e.getMessage());
            response = "NO";
        }
        return response;

    }

    public static String makeRegister(String nickname, String password, String email){
        String pattern= "^[a-zA-Z0-9]*$";
        if(nickname.matches(pattern) == false)
            return "NICKNAME";
        if(password.matches(pattern) == false)
            return "PASSWORD";
        if (email == null || email.length()<3)
            return "EMAIL";
        URL url;
        HttpURLConnection conn = null;
        String response = "";
        try{
            url=new URL("http://caritatea.16mb.com/registration.php");
            String param="nickname=" + URLEncoder.encode(nickname,"UTF-8")+
                    "&password="+URLEncoder.encode(password, "UTF-8")+
                    "&email="+URLEncoder.encode(email, "UTF-8");
            conn=(HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.close();
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                response += (inStream.nextLine());
                break;
            }
        }
        catch(Exception e){
            response = "NO";
        }
        return response;
    }

    public static void showRegisterDialog(String error){
        final AlertDialog.Builder alert = new AlertDialog.Builder(MainScreen);
        alert.setMessage("Please complete the fields below");
        alert.setTitle("Registration");
        View linearlayout = MainScreen.getLayoutInflater().inflate(R.layout.register, null);
        alert.setView(linearlayout);
        final EditText nickname = (EditText) linearlayout.findViewById(R.id.editText1);
        final EditText password = (EditText) linearlayout.findViewById(R.id.editText2);
        final EditText email = (EditText) linearlayout.findViewById(R.id.editText3);
        if (error!=null) {
            TextView emsg = (TextView)linearlayout.findViewById(R.id.textView3);
            emsg.setText(error);
            emsg.setVisibility(View.VISIBLE);
        }
        alert.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String r = makeRegister(nickname.getText().toString(),password.getText().toString(),email.getText().toString());
                Log.d("SERVER","REG "+r);
                switch(r){
                    case "NO": showRegisterDialog("The nickname you entered is busy"); break;
                    case "NICKNAME": showRegisterDialog("Nickname can contain only numbers and letters"); break;
                    case "PASSWORD": showRegisterDialog("Password can contain only numbers and letters"); break;
                    case "EMAIL": showRegisterDialog("The e-mail you entered is invalid"); break;
                }
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        alert.show();
    }


}
