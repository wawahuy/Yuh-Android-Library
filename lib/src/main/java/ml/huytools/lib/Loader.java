package ml.huytools.lib;

import android.os.Handler;
import android.os.Looper;
import android.telecom.Call;

/***
 * APIProvider.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 16/11/2019
 * Update: 24/11/2019
 *
 */
public class Loader<T> extends Thread {

    public interface Callback<T> {
        void OnBackgroundLoad(Loader loader);
        void OnChangeLoad(Loader loader, T... message);
        void OnFinishLoad(Loader loader);
    }

    Thread thread;
    Callback callback;
    boolean isStop;
    protected Handler handler;


    protected Loader(){
    }

    protected Loader(Callback callback){
        Init(callback);
    }

    protected void Init(Callback callback){
        this.callback = callback;
        this.handler = new Handler(Looper.myLooper());
    }

    public static void Create(Callback callback){
        new Loader(callback).start();
    }

    public void change(final T... message){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.OnChangeLoad(Loader.this, message);
            }
        });
    }

    public void restart(int delay){
        isStop = true;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Loader.Create(Loader.this.callback);
            }
        }, delay);
    }

    public void restart(){
        restart(1);
    }

    @Override
    public synchronized void start() {
        this.isStop = false;
        super.start();
    }

    @Override
    public void run() {
        callback.OnBackgroundLoad(this);

        if(isStop){
            return;
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.OnFinishLoad(Loader.this);
            }
        });
    }

}
