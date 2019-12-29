package ml.huytools.lib.Game.Actions.ActionDrawings;

import ml.huytools.lib.Exceptions.DoNotDrawableException;
import ml.huytools.lib.Game.Actions.ActionTimings.ActionTiming;
import ml.huytools.lib.Game.Graphics.Color;
import ml.huytools.lib.Game.Graphics.Drawing.Drawable;

public class ActionColorTo extends ActionTiming {
    private Drawable drawable;
    private Color colorStart;
    private Color colorEnd;
    private Color colorDistances;
    int r, g, b, a;

    protected ActionColorTo(int time) {
        super(time);
    }

    public static ActionColorTo create(Color color, int time){
        ActionColorTo actionColorTo = new ActionColorTo(time);
        actionColorTo.time = time;
        actionColorTo.colorEnd = color.clone();
        return actionColorTo;
    }

    @Override
    protected boolean OnActionUpdateWithPercent(float per) {
        /// compute
        a = (int)(colorStart.a + colorDistances.a*per);
//        r = getColorOfDegradateCalculation(colorStart.r, colorEnd.r, per);
//        g = getColorOfDegradateCalculation(colorStart.g, colorEnd.g, per);;
//        b = getColorOfDegradateCalculation(colorStart.b, colorEnd.b, per);;
        r = (int)(colorStart.r + colorDistances.r*per);
        g = (int)(colorStart.g + colorDistances.g*per);
        b = (int)(colorStart.b + colorDistances.b*per);
        drawable.setColor(a, r, g, b);
        return false;
    }

    @Override
    protected void OnActionSetup() {
        if(!(node instanceof Drawable)){
            throw new DoNotDrawableException();
        }
        drawable = (Drawable)node;
    }

    @Override
    protected void OnActionRestart() {
        super.OnActionRestart();
        colorStart =  drawable.getColor().clone();
        colorDistances = new Color(
                colorEnd.a - colorStart.a,
                colorEnd.r - colorStart.r,
                colorEnd.g - colorStart.g,
                colorEnd.b - colorStart.b
        );
    }
}
