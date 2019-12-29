package ml.huytools.lib.Game.Graphics;

import android.graphics.BitmapShader;
import android.graphics.Shader;

import ml.huytools.lib.Math.Vector2D;

public class Texture {
    private Vector2D size;
    private BitmapShader bitmapShader;

    public Texture(Image image){
        set(image, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
    }

    public Texture(Image image, Shader.TileMode tileModeX, Shader.TileMode tileModeY){
        set(image, tileModeX, tileModeY);
    }

    public void set(Image image, Shader.TileMode tileModeX, Shader.TileMode tileModeY){
        size = new Vector2D();
        size.x = image.getBitmap().getWidth();
        size.y = image.getBitmap().getHeight();
        bitmapShader = new BitmapShader(image.getBitmap(), tileModeX, tileModeY);
    }

    public BitmapShader getBitmapShader(){
        return bitmapShader;
    }

    public Vector2D getSize(){
        return size;
    }
}
