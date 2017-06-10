package xjh.livewallpaper.net;

/**
 * Created by lovexujh on 2017/5/20
 */

public interface INetCall {
    void onResponse(int requestId, String response);

    void onFailure(int requestId, Object errObj);
}
