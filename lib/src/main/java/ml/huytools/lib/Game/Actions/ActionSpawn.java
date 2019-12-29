package ml.huytools.lib.Game.Actions;

import ml.huytools.lib.LinkedListQueue;

/***
 * Thực hiện các hành động song song
 *      |Action 1| ------>   |
 *      |Action 2| --------->|
 *      |Action 3| ---->     |
 *        |                  --> Finish --
 *        | (Nếu sử dụng ActionRepeat)    |
 *        --------------------------------|
 *
 */
public class ActionSpawn extends Action {
    protected Action[] actions;
    protected LinkedListQueue<Action> actionsCurrent;

    public static ActionSpawn create(Action... actions){
        ActionSpawn actionSpawn = new ActionSpawn();
        actionSpawn.actions = actions;
        actionSpawn.actionsCurrent = new LinkedListQueue<>();
        return actionSpawn;
    }

    @Override
    protected void OnActionSetup() {
        for(Action action:actions){
            action.setup(node);
        }
    }

    @Override
    protected void OnActionRestart() {
        actionsCurrent.clear();
        for(Action action:actions){
            action.restart();
            actionsCurrent.addQueue(action);
        }
    }

    @Override
    protected boolean OnActionUpdate() {
        synchronized (actionsCurrent) {
            for (Action action : actionsCurrent) {
                action.update();
                if (action.isFinish()) {
                    actionsCurrent.removeQueue(action);
                }
            }
        }
        actionsCurrent.updateQueue();
        
        if(actionsCurrent.size() == 0){
            setFinish(true);
        }
        return false;
    }
}
