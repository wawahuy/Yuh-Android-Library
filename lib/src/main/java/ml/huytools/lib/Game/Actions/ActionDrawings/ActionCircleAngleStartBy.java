package ml.huytools.lib.Game.Actions.ActionDrawings;

import ml.huytools.lib.Exceptions.DoNotDrawableException;
import ml.huytools.lib.Game.Actions.ActionTimings.ActionTiming;
import ml.huytools.lib.Game.Graphics.Drawing.CircleShape;
import ml.huytools.lib.Game.Graphics.Drawing.Drawable;

public class ActionCircleAngleStartBy extends ActionTiming {
    private CircleShape circleShape;
    private int angleEStart;
    private int angleMagnitude;

    protected ActionCircleAngleStartBy(int time) {
        super(time);
    }

    public static ActionCircleAngleStartBy create(int angleEBy, int time){
        ActionCircleAngleStartBy actionCircleAngleSBy = new ActionCircleAngleStartBy(time);
        actionCircleAngleSBy.angleMagnitude = angleEBy;
        return actionCircleAngleSBy;
    }

    @Override
    protected void OnActionRestart() {
        super.OnActionRestart();
        angleEStart = circleShape.getAngleSwept();
    }

    @Override
    protected boolean OnActionUpdateWithPercent(float per) {
        circleShape.setStartAngle(angleEStart + (int)(angleMagnitude*per));
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
