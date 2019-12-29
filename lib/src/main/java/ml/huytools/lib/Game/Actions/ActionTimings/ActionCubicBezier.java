package ml.huytools.lib.Game.Actions.ActionTimings;

import ml.huytools.lib.Game.Actions.Action;
import ml.huytools.lib.Math.CubicBezier;

public class ActionCubicBezier extends Action {
    protected float time;
    protected long timeStart;
    protected ActionTiming actionTiming;
    protected CubicBezier cubicBezier;

    private long dt;
    private float per, perDt;

    public static ActionCubicBezier create(ActionTiming actionTiming, CubicBezier cubicBezier){
        ActionCubicBezier actionCubicBezier = new ActionCubicBezier();
        actionCubicBezier.actionTiming = actionTiming;
        actionCubicBezier.cubicBezier = cubicBezier;
        actionCubicBezier.time = actionTiming.getTime();
        return actionCubicBezier;
    }

    public static ActionCubicBezier Ease(ActionTiming actionTiming){
        return create(actionTiming, CubicBezier.Ease);
    }

    public static ActionCubicBezier EaseIn(ActionTiming actionTiming){
        return create(actionTiming, CubicBezier.EaseIn);
    }

    public static ActionCubicBezier EaseInOut(ActionTiming actionTiming){
        return create(actionTiming, CubicBezier.EaseInOut);
    }

    public static ActionCubicBezier EaseOut(ActionTiming actionTiming){
        return create(actionTiming, CubicBezier.EaseOut);
    }

    @Override
    protected void OnActionSetup() {
        actionTiming.setup(node);
    }

    @Override
    protected void OnActionRestart() {
        timeStart = getTimeCurrent();
        actionTiming.restart();
    }

    @Override
    protected boolean OnActionUpdate() {
        dt    = getTimeCurrent() - timeStart;
        if(dt > time){
            dt = (long)time;
        }

        perDt = time == 0 ? 1 : dt / time;
        per   = cubicBezier.computeProgressionOnY(perDt);
        if( perDt >= 1 ){
            setFinish(true);
        }
        return actionTiming.updateWithPercent(per);
    }
}
