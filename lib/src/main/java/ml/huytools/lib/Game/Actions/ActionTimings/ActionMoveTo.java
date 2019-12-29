package ml.huytools.lib.Game.Actions.ActionTimings;

import ml.huytools.lib.Math.Vector2D;

public class ActionMoveTo extends ActionTiming {
    private Vector2D positionEnd;
    private Vector2D positionStart;
    private Vector2D distance;
    float x, y;

    public static ActionMoveTo create(Vector2D to, int time){
        ActionMoveTo actionMoveTo = new ActionMoveTo(time);
        actionMoveTo.positionEnd = to.clone();
        return actionMoveTo;
    }

    protected ActionMoveTo(int time) {
        super(time);
    }


    @Override
    protected void OnActionRestart() {
        super.OnActionRestart();
        positionStart = node.getPosition().clone();
        distance = positionEnd.sub(positionStart);
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
}
