package md.pickhack.rulethegalaxy.Graphics.PlanetView;

import java.util.ArrayList;
import static md.pickhack.rulethegalaxy.MainActivity.*;
/**
 * Created by Alexandr on 07.11.15.
 */
public abstract class PlanetGenerator {
    final static int width = 4;
    final static int height = 6;
    public static float Width;
    private static int next;
    private static float proportion;
    private static String key;
    public static ArrayList<PlanetCell> generate(int key){
        next = 0;
        PlanetGenerator.key = ""+key;
        proportion = graphics.Width/graphics.Height;
        ArrayList<PlanetCell> cells = new ArrayList<PlanetCell>();
        float x = 0f;
        float y = 0.1f;
        float w = 0.2f;
        float h = proportion*w;
        Width = height*0.2f;
        int num = 0;
        for (int i = 0; i<width; i++){
            x = 0.5f*(i%2)*w;
            for (int j = 0; j<height; j++){
                cells.add(new PlanetCell(x,y,w,h, num, nextType()));
                num++;
                x += w;
            }
            y += 0.75*h;
        }
        return cells;
    }
    private static int nextType(){
        next = next<key.length()-1?next+1:0;
        return Integer.parseInt(""+key.charAt(next))%3;
    }

}
