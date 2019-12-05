package ml.huytools.lib;

import android.content.Context;

/***
 * Presenter.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 15/11/2019
 * Update: 20/11/2019
 *
 */
public abstract class Presenter<T> {

    protected Context context;
    protected T view;

    public Presenter(Context view){
        this.context = view;
        this.view = (T)view;
    }

    public void Start(){
        this.OnStart();
    }

    protected abstract void OnStart();
}
