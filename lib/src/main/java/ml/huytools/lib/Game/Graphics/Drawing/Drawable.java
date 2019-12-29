package ml.huytools.lib.Game.Graphics.Drawing;

import android.graphics.Paint;

import ml.huytools.lib.Game.Graphics.Color;
import ml.huytools.lib.Game.Graphics.Texture;
import ml.huytools.lib.Game.Scenes.Node;

public abstract class Drawable extends Node {
    public enum Style { STROKE, FILL };

    private Color color;
    private Texture texture;
    private int strokeWidth;
    private Style style;
    protected Paint paint;

    protected Drawable(){
        super();
        color = new Color(0, 0,0, 0);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        paint.setARGB(color.a, color.r, color.g, color.b);
        this.color = color;
    }

    public void setColor(int a, int r, int g, int b) {
        this.color.a = a;
        this.color.r = r;
        this.color.g = g;
        this.color.b = b;
        paint.setARGB(a, r, g, b);
        hasUpdateDraw = true;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        paint.setShader(texture.getBitmapShader());
        this.texture = texture;
        hasUpdateDraw = true;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        paint.setStrokeWidth(strokeWidth);
        this.strokeWidth = strokeWidth;
        hasUpdateDraw = true;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style type) {
        paint.setStyle(type == Style.STROKE ? Paint.Style.STROKE : Paint.Style.FILL);
        this.style = type;
        hasUpdateDraw = true;
    }

}
