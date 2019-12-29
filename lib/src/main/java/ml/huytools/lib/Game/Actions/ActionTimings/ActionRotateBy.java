package ml.huytools.lib.Game.Actions.ActionTimings;

public class ActionRotateBy extends ActionTiming {
    private float magnitudeRotate;
    private float angleStart;

    protected ActionRotateBy(int time) {
        super(time);
    }

    public static ActionRotateBy create(int rotate, int time){
        ActionRotateBy actionRotateBy = new ActionRotateBy(time);
        actionRotateBy.magnitudeRotate = rotate;
        return actionRotateBy;
    }

    @Override
    protected boolean OnActionUpdateWithPercent(float per) {
        node.setRotate(angleStart + per*magnitudeRotate);
        return false;
    }

    @Override
    protected void OnActionSetup() {
    }

    @Override
    protected void OnActionRestart() {
        super.OnActionRestart();
        angleStart = node.getRotate();
    }
}
