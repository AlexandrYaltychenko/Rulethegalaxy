package md.pickhack.rulethegalaxy.Graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.ArrayList;

public class TexturePack {
    private ArrayList<Texture> Textures = new ArrayList<Texture>();
    private Context context;
    TexturePack(Context context){
        this.context=context;
    }
    synchronized public Bitmap get(String src){
        for (Texture i:Textures){
            if (i.getID().equals(src))
                return i.getBitmap();
        }
        return null;
    }
    public Bitmap get(int index){
        if (index<Textures.size())
            return Textures.get(index).getBitmap();
        return null;
    }
    public void addTextures(String...src){
        for (String i:src){
            Textures.add(new Texture(i, BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier(i, "drawable", context.getPackageName()))));
        }
    }
    public void removeTexture(String src){
        for (int i=0; i<Textures.size();i++){
            if (Textures.get(i).getID()==src)
                Textures.remove(i);
            break;
        }
    }
    public void removePrefix(String src){
        int i = 0;
        while(i<Textures.size()){
            if (Textures.get(i).getID().contains(src)){
                Textures.get(i).getBitmap().recycle();
                Textures.remove(i);
            }
            else
                i++;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }




}
