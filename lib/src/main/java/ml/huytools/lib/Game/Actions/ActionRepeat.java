package ml.huytools.lib.Game.Actions;

public class ActionRepeat extends Action {
    Action action;
    int n;
    int pos;

    public static ActionRepeat create(Action action, int n){
        ActionRepeat actionRepeat = new ActionRepeat();
        actionRepeat.action = action;
        actionRepeat.n = n;
        return actionRepeat;
    }

    @Override
    protected void OnActionSetup() {
        action.setup(node);
    }

    @Override
    protected void OnActionRestart() {
        pos = 0;
        action.restart();
    }

    @Override
    protected boolean OnActionUpdate() {
        boolean b = action.update();
        if(action.isFinish()){
            /// cái đắt lại node theo vị trí hiện tại
            action.restart();
            progressFinish();
        }
        return b;
    }

    public void progressFinish(){
        pos++;
        if(pos >= n){
            setFinish(true);
        }
    }
}
