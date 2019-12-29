package ml.huytools.lib.Game.Graphics;

import android.graphics.Matrix;

import ml.huytools.lib.Math.Vector2D;


/**
 * Xây dựng biến đổi đối tượng được vẽ thông qua
 *      + Vị trí
 *      + Trọng tâm
 *      + Scale
 *      + Xoay
 *
 */
public class Transformable {

    protected Vector2D position;
    protected Vector2D origin;
    protected Vector2D scale;
    protected float rotate;
    protected boolean needUpdateMatrix;
    private   Matrix matrix;

    protected Transformable(){
        position = new Vector2D();
        origin = new Vector2D();
        scale = new Vector2D(1.0f, 1.0f);
        rotate = 0;
        needUpdateMatrix = false;
        matrix = new Matrix();
    }


    public final Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        setPosition(position.x, position.y);
        needUpdateMatrix = true;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
        needUpdateMatrix = true;
    }

    public final Vector2D getOrigin() {
        return origin;
    }

    public void setOrigin(Vector2D origin) {
        setOrigin(origin.x, origin.y);
        needUpdateMatrix = true;
    }

    public void setOrigin(float x, float y) {
        this.origin.x = x;
        this.origin.y = y;
        needUpdateMatrix = true;
    }

    public Vector2D getScale() {
        return scale;
    }

    public void setScale(Vector2D scale) {
        setScale(scale.x, scale.y);
        needUpdateMatrix = true;
    }

    public void setScale(float x, float y) {
        this.scale.x = x;
        this.scale.y = y;
        needUpdateMatrix = true;
    }

    public float getRotate() {
        return rotate;
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
        needUpdateMatrix = true;
    }

    public void rotate(float rotate){
        this.rotate += rotate;
        needUpdateMatrix = true;
    }

    public void translate(Vector2D position) {
        this.position.x += position.x;
        this.position.y += position.y;
        needUpdateMatrix = true;
    }

    public void translate(float x, float y) {
        this.position.x = x;
        this.position.y = y;
        needUpdateMatrix = true;
    }

    public void scale(Vector2D scale) {
        this.scale.x += scale.x;
        this.scale.y += scale.y;
        needUpdateMatrix = true;
    }

    public void scale(float x, float y) {
        this.scale.x += x;
        this.scale.y += y;
        needUpdateMatrix = true;
    }


    /**
     * Live Data
     */
    public void setLiveDataPosition(Vector2D position){
        this.position = position;
        needUpdateMatrix = true;
    }

    public void setLiveDataScale(Vector2D scale){
        this.scale = scale;
        needUpdateMatrix = true;
    }

    public void setLiveDataOrigin(Vector2D origin){
        this.origin = origin;
        needUpdateMatrix = true;
    }

    /**
     * Xây dưng & lấy ma trận biến đổi
     * @return
     */
    public Matrix getMatrix() {
        // Update Matrix
        if(needUpdateMatrix){
            matrix.reset();
            matrix.postTranslate(-origin.x, -origin.y);
            matrix.postScale(scale.x, scale.y);
            matrix.postRotate(rotate);
            matrix.postTranslate(position.x, position.y);
            needUpdateMatrix = false;
        }
        return matrix;
    }

}
