package ml.huytools.lib.Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ml.huytools.lib.Math.Vector2D;

/***
 *
 */
public class Renderer implements SurfaceHolder.Callback {

    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    IGameObject gameObject;
    Callback callback;
    boolean flagUpdate;
    boolean flagCreated;

    public Renderer(SurfaceView surfaceView, IGameObject gameObject) {
        this.surfaceView = surfaceView;
        this.gameObject = gameObject;
        this.surfaceHolder = surfaceView.getHolder();
    }

    public void update(){
        flagUpdate = gameObject.update();
    }

    public void render(){
        if(flagUpdate){
            Canvas canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder){
                /// Clear
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
                /// Draw
                gameObject.draw(canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean isFlagUpdate() {
        return flagUpdate;
    }

    public void enableAutoRegisterDirector(Callback callback) {
        this.callback = callback;
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        GameDirector.getInstance().registration(this);

        // transparent
        surfaceView.setZOrderOnTop(true);
        surfaceView.getHolder().setFormat(PixelFormat.RGBA_8888);

        // clear background transparent
        Canvas canvas = surfaceHolder.lockCanvas();
        synchronized (surfaceHolder){
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        }
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Vector2D size = new Vector2D(i1, i2);
        if(!flagCreated){
            callback.OnCreate(size);
            flagCreated = true;
        }
        callback.OnResume(size);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        GameDirector.getInstance().cancelRegistration(this);
        callback.OnDestroy();
    }

    public interface Callback {
        void OnCreate(Vector2D size);
        void OnResume(Vector2D size);
        void OnDestroy();
    }
}
