package xjh.livewallpaper.common;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import xjh.livewallpaper.service.VideoWallpaperService;

/**
 * Created by lovexujh on 2017/5/22
 */

public class VideoTool {
    public static final String VIDEO_PARAMS_CONTROL_ACTION = "xjh.livewallpaper";
    public static final String KEY_ACTION = "action";
    public static final int ACTION_VOICE_SILENCE = 110;
    public static final int ACTION_VOICE_NORMAL = 111;

    public static void voiceSilence(Context context) {
        Intent intent = new Intent(VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(KEY_ACTION, ACTION_VOICE_SILENCE);
        context.sendBroadcast(intent);
    }

    public static void voiceNormal(Context context) {
        Intent intent = new Intent(VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(KEY_ACTION, ACTION_VOICE_NORMAL);
        context.sendBroadcast(intent);
    }

    public static void setUrl(Context context, String url) {
        Intent intent = new Intent(VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(KEY_ACTION, ACTION_VOICE_NORMAL);
        intent.putExtra(IntentKeys.M3U8_PATH, url);
        context.sendBroadcast(intent);
    }

    public static void setToWallPaper(Context context) {
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(context, VideoWallpaperService.class));
        context.startActivity(intent);
    }
}
