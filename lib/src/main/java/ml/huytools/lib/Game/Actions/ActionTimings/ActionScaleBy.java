package ml.huytools.lib.Game.Actions.ActionTimings;

import ml.huytools.lib.Math.Vector2D;

public class ActionScaleBy extends ActionTiming {
    private Vector2D magnitudeScale;
    private Vector2D scaleStart;

    float x, y;

    protected ActionScaleBy(int time) {
        super(time);
    }

    public static ActionScaleBy create(Vector2D scale, int time){
        ActionScaleBy actionScaleBy = new ActionScaleBy(time);
        actionScaleBy.magnitudeScale = scale.clone();
        return actionScaleBy;
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
    }
}