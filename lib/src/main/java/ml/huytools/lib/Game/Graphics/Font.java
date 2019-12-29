package ml.huytools.lib.Game.Graphics;

import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import ml.huytools.lib.App;

public class Font {
    Typeface typeface;

    private Font(){
    }

    public static Font loadResource(int res){
        Font font = new Font();
        font.typeface = ResourcesCompat.getFont(App.getInstance().getContextApplication(), res);
        return font;
    }

    public Typeface getTypeFace(){
        return typeface;
    }
}
