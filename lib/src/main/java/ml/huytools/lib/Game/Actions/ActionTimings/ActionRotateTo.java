package ml.huytools.lib.Game.Actions.ActionTimings;

public class ActionRotateTo extends ActionTiming {
    private float magnitudeRotate;
    private float angleEnd;
    private float angleStart;

    protected ActionRotateTo(int time) {
        super(time);
    }

    public static ActionRotateTo create(int angleEnd, int time){
        ActionRotateTo actionRotateTo = new ActionRotateTo(time);
        actionRotateTo.angleEnd = angleEnd;
        return actionRotateTo;
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
        magnitudeRotate = angleEnd - angleStart;
    }
}