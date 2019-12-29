package ml.huytools.lib.Game.Actions;

import ml.huytools.lib.Game.Scenes.Node;

public abstract class Action {
    protected Node node;
    protected boolean finish;

    protected Action(){
        finish = false;
    }

    protected abstract void OnActionSetup();
    protected abstract void OnActionRestart();
    protected abstract boolean OnActionUpdate();

    public void setup(Node node){
        this.node = node;
        OnActionSetup();
        restart();
    }

    public void restart(){
        finish = false;
        OnActionRestart();
    }

    public boolean update(){
        if(finish){
            return false;
        }
        return OnActionUpdate();
    }

    public boolean isFinish() {
        return finish;
    }

    public void finish(){
        finish = true;
    }

    protected void setFinish(boolean finish) {
        this.finish = finish;
    }

    protected long getTimeCurrent(){
        return System.currentTimeMillis();
    }
}
