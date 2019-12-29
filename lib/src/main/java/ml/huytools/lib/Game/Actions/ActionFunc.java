package ml.huytools.lib.Game.Actions;

import ml.huytools.lib.Game.Scenes.Node;

public class ActionFunc extends Action {
    private Callback callback;

    public interface Callback {
        /**
         *
         * @param node
         * @return
         *      true - yêu cầu vẽ lại
         */
        boolean OnCallback(Node node);
    }

    public static ActionFunc create(Callback callback){
        ActionFunc actionFunc = new ActionFunc();
        actionFunc.callback = callback;
        return actionFunc;
    }

    @Override
    protected void OnActionSetup() {
    }

    @Override
    protected void OnActionRestart() {
    }

    @Override
    protected boolean OnActionUpdate() {
        setFinish(true);
        return callback.OnCallback(node);
    }
}
