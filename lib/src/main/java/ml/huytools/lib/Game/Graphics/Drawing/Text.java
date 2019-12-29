package ml.huytools.lib.Game.Graphics.Drawing;

import android.graphics.Canvas;
import android.graphics.Typeface;

import ml.huytools.lib.Game.Graphics.Font;

public class Text extends Drawable {

    enum Type { BOLD, };

    private String text;
    private boolean center;
    private int size;

    private Font font;
    private Typeface typefaceObject;
    private int style;

    public Text(){
        text = "";
        size = 20;
    }


    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
        initWithTypeface(font.getTypeFace());
    }

    private void initWithTypeface(Typeface typeface){
        typefaceObject = typeface;
        paint.setTypeface(typefaceObject);
        computeOrigin();
    }

    public int getTextStyle() {
        return style;
    }

    public void setTextStyle(int style) {
        this.style = style;
        if(font == null){
            initWithTypeface(Typeface.defaultFromStyle(style));
        } else {
            initWithTypeface(Typeface.create(font.getTypeFace(), style));
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        paint.setTextSize(size);
        this.size = size;
        computeOrigin();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        computeOrigin();
    }

    public void centerOrigin(boolean status){
        center = status;
        computeOrigin();
    }

    private void computeOrigin(){
        if(center){
            //        ---- Text Width -------
            float x = paint.measureText(text) / 2 ;

            // -- Top -> Ascent -> Descent -> Bottom , Text Bound (Ascent -> Descent)
            // => Ascent + (Descent - Ascent)*0.5
            // => Ascent*0.5 + Descent*0.5 = (Ascent + Descent)/2
            float y = (paint.descent() + paint.ascent())/2;
            setOrigin(x, y);
        }
    }

    @Override
    protected void OnDraw(Canvas canvas) {
        canvas.drawText(text, 0, 0, paint);
    }
}
