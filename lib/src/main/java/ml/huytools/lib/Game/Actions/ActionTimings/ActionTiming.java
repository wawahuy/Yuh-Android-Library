package ml.huytools.lib.Game.Actions.ActionTimings;

import ml.huytools.lib.Game.Actions.Action;

public abstract class ActionTiming extends Action {
    protected float time;
    protected long timeStart;

    private long dt;
    private float per;

    protected ActionTiming(int time){
        this.time = time;
    }

    public float getTime() {
        return time;
    }

    @Override
    protected void OnActionRestart() {
        timeStart = getTimeCurrent();
    }

    @Override
    protected boolean OnActionUpdate() {
        dt  = getTimeCurrent() - timeStart;
        if(dt > time){
            dt = (long)time;
        }

        per = time == 0 ? 1 : dt / time;
        if(per >= 1){
            setFinish(true);
        }
        return updateWithPercent(per);
    }

    protected abstract boolean OnActionUpdateWithPercent(float per);

    public boolean updateWithPercent(float per){
        return OnActionUpdateWithPercent(per);
    }
}
