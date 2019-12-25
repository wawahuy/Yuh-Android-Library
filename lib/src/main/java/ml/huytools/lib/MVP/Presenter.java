package ml.huytools.lib.MVP;

import android.app.Activity;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.lang.ref.WeakReference;

/***
 * Presenter.java
 * Author: Nguyen Gia Huy
 * Project: https://github.com/wawahuy/YCNAnswerAndroid
 * Start: 15/11/2019
 * Update: 20/11/2019
 *
 */
public abstract class Presenter<T> {

    protected WeakReference<Activity> activity;
    protected T view;
    private Model dataSaved;

    protected Presenter(){
    }

    protected Presenter(Activity activity){
        set(activity);
    }

    public void set(Activity activity){
        this.activity = new WeakReference<>(activity);
        this.view = (T)activity;
    }


    /// Được gọi khi presenter lần đầu được khởi tạo
    protected abstract void OnCreate();

    /// Chỉ được gọi khi presenter được tạo lại
    /// Khi activity bị xoay hay cấu hình bị thay đổi
    protected void OnResume(Model dataSaved){ }


    /// new data save
    public void postDataSaved(Model model){
        this.dataSaved = model;
    }


    public static<T, V extends Presenter<T>> V of(AppCompatActivity activity, Class<V> clazz){
        try {
            /// Sử dụng ViewModel của Android
            /// Khắc phục tình trạng presenter được khởi tạo lại khi activity có thay đổi
            final PresenterSaved presenterSaved = ViewModelProviders.of(activity).get(PresenterSaved.class);

            if(presenterSaved.presenter == null){
                presenterSaved.presenter = clazz.newInstance();
                presenterSaved.presenter.set(activity);
                presenterSaved.presenter.OnCreate();
                Log.v("Log", "Presenter Create On Activity: "+activity.toString());
            } else {
                presenterSaved.presenter.set(activity);

                /// Run Resume on Main Thread
//                new Handler(Looper.myLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        presenterSaved.presenter.OnResume(presenterSaved.presenter.dataSaved);
//                    }
//                });
                presenterSaved.presenter.OnResume(presenterSaved.presenter.dataSaved);

            }

            return (V) presenterSaved.presenter;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

    /// ViewModel
    public static class PresenterSaved<T, V extends Presenter<T>> extends ViewModel {
        public V presenter;
    }
}
