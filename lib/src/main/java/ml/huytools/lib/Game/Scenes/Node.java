package ml.huytools.lib.Game.Scenes;

import android.graphics.Canvas;

import ml.huytools.lib.Game.Actions.Action;
import ml.huytools.lib.Game.Graphics.Transformable;
import ml.huytools.lib.Game.IGameObject;

public abstract class Node extends Transformable implements IGameObject {
    protected boolean hasUpdateDraw = false;
    private boolean visible;
    private Action action;
    private NodeGroup nodeGroup;

    private int zOrder;
    private int zOrderNodeInc;
    private Node zOrderNode;

    protected Node(){
        visible = true;
        zOrder = 0;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        if(visible){
            hasUpdateDraw = false;
        }
        this.visible = visible;
    }

    public Action getAction() {
        return action;
    }

    public void runAction(Action action) {
        this.action = action;
        this.action.setup(this);
    }

    protected void setGroupNode(NodeGroup nodeGroup){
        this.nodeGroup = nodeGroup;
    }

    public NodeGroup getGroupNode(){
        return nodeGroup;
    }

    public int getZOrder(){
        return zOrderNode == null ? zOrder : zOrderNode.getZOrder() + zOrderNodeInc;
    }

    public void setZOrder(int zIndex){
        zOrderNode = null;
        zOrder = zIndex;
        if(nodeGroup != null){
            nodeGroup.updateZIndexSort();
        }
    }

    public void setZOrderUnder(Node node){
        zOrderNode = node;
        zOrderNodeInc = -1;
        if(nodeGroup != null){
            nodeGroup.updateZIndexSort();
        }
    }

    public void setZOrderUpper(Node node){
        zOrderNode = node;
        zOrderNodeInc = 1;
        if(nodeGroup != null){
            nodeGroup.updateZIndexSort();
        }
    }

    /***
     * Các đối tượng sẽ được vẽ kèm theo ma trận biến đổi bởi Transformable
     * Nên vẽ các đối tượng ở góc tọa độ và di chuyển đến trọng tâm
     * Hoặc vẽ các đối tượng sao cho nó được đặt ở trọng tâm
     * Để có thể nhận được các phép quy và kéo giản quanh trọng tâm
     *
     * [x-draw, y-draw] * Matrix Transformable * Camera Matrix
     *
     * @param canvas
     */
    protected abstract void OnDraw(Canvas canvas);

    @Override
    public void draw(Canvas canvas) {
        if(!visible){
            return;
        }
        canvas.save();
        /// [0,0] * [ Matrix Model (Transformable) ] * [.... Camera Matrix On Scene ....]
        canvas.concat(getMatrix());
        OnDraw(canvas);
        canvas.restore();
        hasUpdateDraw = false;
    }

    @Override
    public boolean update(){
        boolean hasActionUpdate = action != null && action.update();
        return needUpdateMatrix || hasUpdateDraw || hasActionUpdate;
    }
}
