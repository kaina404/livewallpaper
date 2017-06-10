package xjh.livewallpaper.Application;

import android.app.Application;

/**
 * Created by lovexujh on 2017/5/20
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;
//    public static String appUrl = "";
    private String url;



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static BaseApplication getInstance(){
        return instance;
    }
}
