package md.pickhack.rulethegalaxy.Server;

import android.util.Log;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by alexialt on 24.10.15.
 */
public abstract class Server {
    public static String sendPost(String script, String param){
        URL url;
        HttpURLConnection conn = null;
        String response = "";
        try{
            url=new URL("http://caritatea.16mb.com/"+script);
            conn=(HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.close();
            Scanner inStream = new Scanner(conn.getInputStream());
            Log.d("SERVER", "START READING");
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
}
