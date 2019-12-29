package ml.huytools.lib.Game.Actions;

public class ActionVisible extends Action {
    boolean visible;
    boolean counterWork;

    public static ActionVisible create(boolean visible){
        ActionVisible actionVisible = new ActionVisible();
        actionVisible.visible = visible;
        return actionVisible;
    }

    public static ActionVisible createCounterWork(){
        ActionVisible actionVisible = new ActionVisible();
        actionVisible.counterWork = true;
        return actionVisible;
    }


    @Override
    protected void OnActionSetup() {
    }

    @Override
    protected void OnActionRestart() {
        if(counterWork){
            visible = !node.isVisible();
        }
    }

    @Override
    protected boolean OnActionUpdate() {
        node.setVisible(visible);
        setFinish(true);
        return false;
    }
}
