package md.pickhack.rulethegalaxy.Server;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import md.pickhack.rulethegalaxy.Logic.Planet;

/**
 * Created by alexialt on 24.10.15.
 */
public abstract class PlanetGetter {
    public static ArrayList<Planet> getPlanets(String private_id){
        ArrayList<Planet> planets = new ArrayList<Planet>();
        try {
            String param = "private_id=" + URLEncoder.encode(private_id, "UTF-8");
            String result = Server.sendPost("getInfo",param);
            Log.d("SERVER",result);
            JSONArray ja = null;
            String id = null;
            ja = new JSONArray(result);
            String timedate, out_date;
            Calendar calendar = Calendar.getInstance();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = (JSONObject) ja.get(i);
                planets.add(new Planet(jo.getString("UN"),jo.getString("PN"),jo.getString("PBID"),jo.getString("PRID"),jo.getString("C1"),jo.getString("C2"),jo.getString("BD"),jo.getString("RS")));
            }

        } catch (Exception e){ }
        return planets;
    }
}
