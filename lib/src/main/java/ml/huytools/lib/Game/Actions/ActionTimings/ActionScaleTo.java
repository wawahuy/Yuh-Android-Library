package ml.huytools.lib.Game.Actions.ActionTimings;

import ml.huytools.lib.Math.Vector2D;

public class ActionScaleTo extends ActionTiming {
    private Vector2D magnitudeScale;
    private Vector2D scaleEnd;
    private Vector2D scaleStart;

    float x, y;

    protected ActionScaleTo(int time) {
        super(time);
    }

    public static ActionScaleTo create(Vector2D scaleEnd, int time){
        ActionScaleTo actionScaleTo = new ActionScaleTo(time);
        actionScaleTo.scaleEnd = scaleEnd.clone();
        return actionScaleTo;
    }

    @Override
    protected boolean OnActionUpdateWithPercent(float per) {
        x = scaleStart.x + per*magnitudeScale.x;
        y = scaleStart.y + per*magnitudeScale.y;
        node.setScale(x, y);
        return false;
    }

    @Override
    protected void OnActionSetup() {
    }

    @Override
    protected void OnActionRestart() {
        super.OnActionRestart();
        scaleStart = node.getScale().clone();
        magnitudeScale = scaleEnd.sub(scaleStart);
    }
}