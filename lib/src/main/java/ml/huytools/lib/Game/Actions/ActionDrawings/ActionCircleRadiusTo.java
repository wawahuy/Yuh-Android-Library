package ml.huytools.lib.Game.Actions.ActionDrawings;

import ml.huytools.lib.Exceptions.DoNotDrawableException;
import ml.huytools.lib.Game.Actions.ActionTimings.ActionTiming;
import ml.huytools.lib.Game.Graphics.Drawing.CircleShape;
import ml.huytools.lib.Game.Graphics.Drawing.Drawable;

public class ActionCircleRadiusTo extends ActionTiming {
    private CircleShape circleShape;
    private int radiusStart;
    private int radiusEnd;
    private int radiusMagnitude;

    protected ActionCircleRadiusTo(int time) {
        super(time);
    }

    public static ActionCircleRadiusTo create(int radiusTo, int time){
        ActionCircleRadiusTo actionCircleRadiusTo = new ActionCircleRadiusTo(time);
        actionCircleRadiusTo.radiusEnd = radiusTo;
        return actionCircleRadiusTo;
    }

    @Override
    protected void OnActionRestart() {
        super.OnActionRestart();
        radiusStart = circleShape.getRadius();
        radiusMagnitude = radiusEnd - radiusStart;
    }

    @Override
    protected boolean OnActionUpdateWithPercent(float per) {
        circleShape.setRadius(radiusStart + (int)(radiusMagnitude*per));
        return false;
    }

    @Override
    protected void OnActionSetup() {
        if(!(node instanceof Drawable)){
            throw new DoNotDrawableException();
        }
        circleShape = (CircleShape) node;
    }
}
