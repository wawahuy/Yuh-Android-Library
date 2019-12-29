package ml.huytools.lib.Game.Actions.ActionTimings;

import ml.huytools.lib.Math.Vector2D;

public class ActionMoveBy extends ActionTiming {
    private Vector2D distance;
    private Vector2D positionStart;
    float x, y;

    protected ActionMoveBy(int time) {
        super(time);
    }

    public static ActionMoveBy create(Vector2D distance, int time){
        ActionMoveBy actionMoveBy = new ActionMoveBy(time);
        actionMoveBy.distance = distance.clone();
        return actionMoveBy;
    }

    @Override
    protected boolean OnActionUpdateWithPercent(float per) {
        x = positionStart.x + distance.x*per;
        y = positionStart.y + distance.y*per;
        node.setPosition(x, y);
        return false;
    }

    @Override
    protected void OnActionSetup() {
    }

    @Override
    protected void OnActionRestart() {
        super.OnActionRestart();
        positionStart = node.getPosition().clone();
    }
}
