package ml.huytools.lib.Game.Graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import ml.huytools.lib.App;
import ml.huytools.lib.Math.Vector2D;

public class Image {

    Bitmap bitmap;

    private Image(){
    }

    public static Image LoadByResource(int id){
        Image image = new Image();
        image.bitmap = (Bitmap) BitmapFactory.decodeResource(App.getInstance().getResources() , id);
        return image;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public Image createCrop(Vector2D position, Vector2D size){
        Image image = new Image();
        Bitmap resultBmp = Bitmap.createBitmap((int)size.x, (int)size.y, Bitmap.Config.ARGB_8888);
        new Canvas(resultBmp).drawBitmap(bitmap, -position.x, -position.y, null);
        image.bitmap = resultBmp;
        return image;
    }

    /**
     *
     * @param scale
     *          [1, 1] normal
     * @return
     */
    public Image createScale(Vector2D scale){
        Image image = new Image();
        float w = bitmap.getWidth()*scale.x;
        float h = bitmap.getHeight()*scale.y;
        image.bitmap = Bitmap.createScaledBitmap(bitmap, (int)w, (int)h, false);
        return image;
    }

    public void free(){
        bitmap.recycle();
        bitmap = null;
    }
}
