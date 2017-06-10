package xjh.livewallpaper.common;

import android.widget.Toast;

import xjh.livewallpaper.Application.BaseApplication;

/**
 * Created by lovexujh on 2017/6/1
 */

public class ToastUtils {

    public static void showToast(String url){
        Toast.makeText(BaseApplication.getInstance().getApplicationContext(), url, Toast.LENGTH_SHORT).show();
    }

    public static void showDialog(String url){

    }
}
