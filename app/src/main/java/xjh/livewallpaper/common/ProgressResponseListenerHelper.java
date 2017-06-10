package xjh.livewallpaper.common;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

import xjh.livewallpaper.listener.ProgressResponseListener;

/**
 * Created by lovexujh on 2017/6/6
 */

public abstract class ProgressResponseListenerHelper implements ProgressResponseListener {

    private final MyHandler handler = new MyHandler(Looper.getMainLooper(), this);
    private double all;


    @Override
    public void onResponseProgress(double read, double all) {
        this.all = all;
        Message message = Message.obtain();
        message.obj = read;
        handler.sendMessage(message);
    }

    private static class MyHandler extends Handler{

        private final WeakReference<ProgressResponseListenerHelper> weakReference;
        private final ProgressResponseListenerHelper progressResponseListenerHelper;

        public MyHandler(Looper mainLooper, ProgressResponseListenerHelper progressResponseListenerHelper) {
            super(mainLooper);
            this.weakReference = new WeakReference<ProgressResponseListenerHelper>(progressResponseListenerHelper);
            this.progressResponseListenerHelper = this.weakReference.get();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressResponseListenerHelper.onResponseProgress((double)msg.obj);
        }
    }

    private void onResponseProgress(double read){
        onUIResponseProgress(read, all);
    }


    public abstract void onUIResponseProgress(double read, double all);
}
