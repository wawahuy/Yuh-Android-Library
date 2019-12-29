package ml.huytools.lib.Game.Graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import ml.huytools.lib.Game.Scenes.Node;

public class Sprite extends Node {
    Texture texture;
    Paint paint;
    Rect rect;

    public Sprite(){
        rect = new Rect(0, 0, 0, 0);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public Sprite(Texture texture){
        rect = new Rect(0, 0, 0, 0);
        paint = new Paint();
        paint.setAntiAlias(true);
        setTexture(texture);
    }

    public void setTexture(Texture texture){
        this.texture = texture;
        this.paint.setShader(texture.getBitmapShader());
        rect.right = (int)texture.getSize().x;
        rect.bottom = (int)texture.getSize().y;
        hasUpdateDraw = true;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setRect(Rect rect){
        this.rect.top = rect.top;
        this.rect.left = rect.left;
        this.rect.right = rect.right;
        this.rect.bottom = rect.bottom;
        hasUpdateDraw = true;
    }

    public final Rect getRect(Rect rect){
        return rect;
    }

    public void centerOrigin(){
        /// (right - left)*0.5f , (bottom - top)*0.5f
        setOrigin(rect.right*0.5f - rect.left*0.5f, rect.bottom*0.5f - rect.top*0.5f);
        hasUpdateDraw = true;
    }

    @Override
    public void OnDraw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }
}
