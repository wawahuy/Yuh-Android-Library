package ml.huytools.lib;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class App {
    private static final App ourInstance = new App();
    private Resources resources;
    private String package_name;

    public static App getInstance() {
        return ourInstance;
    }

    private App() {
    }

    public void init(Context context){
        resources = context.getResources();
        package_name = context.getPackageName();
    }

    public Resources getResources() {
        return resources;
    }

    public String getPackageName() {
        return package_name;
    }


    /***
     * https://stackoverflow.com/questions/4605527/converting-pixels-to-dp
     * @param px
     * @return
     */
    public static float convertPixelsToDp(float px){
        return px / ((float) App.getInstance().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    /***
     * https://stackoverflow.com/questions/4605527/converting-pixels-to-dp
     * @param dp
     * @return
     */
    public static float convertDpToPixel(float dp){
        return dp * ((float) App.getInstance().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    /**
     * App sẽ chạy trên một Thread riêng biệc tách biệt với MainThread
     *
     */
    class AppLooper extends Thread {


        @Override
        public void run() {
            super.run();
        }
    }

}
