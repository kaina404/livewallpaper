package xjh.livewallpaper;

/**
 * Created by lovexujh on 2017/5/17
 */

public class LogUtil {
    private static final String sTag = "logg";
    private static final boolean debug = true;

    public static void d(String msg, Object... params) {
        if (debug) {
            android.util.Log.d(sTag, String.format(msg, params));
        }
    }
}
