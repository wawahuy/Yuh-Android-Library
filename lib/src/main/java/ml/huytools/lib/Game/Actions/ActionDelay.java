package ml.huytools.lib.Game.Actions;

public class ActionDelay extends Action {
    long timeStart;
    int timeDelay;

    public static ActionDelay create(int delay){
        ActionDelay actionDelay = new ActionDelay();
        actionDelay.timeDelay = delay;
        return actionDelay;
    }

    @Override
    protected void OnActionSetup() {
    }

    @Override
    protected void OnActionRestart() {
        timeStart = getTimeCurrent();
    }

    @Override
    protected boolean OnActionUpdate() {
        if(getTimeCurrent() - timeStart >= timeDelay){
            setFinish(true);
        }
        return false;
    }
}
