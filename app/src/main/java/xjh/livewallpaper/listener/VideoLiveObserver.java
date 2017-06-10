package xjh.livewallpaper.listener;

import java.util.Observable;

/**
 * Created by lovexujh on 2017/5/21
 */

public class VideoLiveObserver extends Observable {

    private VideoLiveObserver() {

    }

    public static VideoLiveObserver getInstance() {
        return ThisIntance.instance;
    }

    private static class ThisIntance {
        public static final VideoLiveObserver instance = new VideoLiveObserver();
    }

    private String url;

    public String getData() {
        return url;
    }

    public void setData(String url) {
        this.url = url;
        setChanged();
        notifyObservers(url);
    }
}
