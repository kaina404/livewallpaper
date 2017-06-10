package xjh.livewallpaper.player;


import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

import xjh.livewallpaper.Application.BaseApplication;
import xjh.livewallpaper.common.Contants;
import xjh.livewallpaper.service.VideoWallpaperService;

/**
 * Created by lovexujh on 2017/5/24
 */

public class MyEngineManager {

    private VideoWallpaperService.VideoEngine videoEngine;
    private SharedPreferences sharedPreferences;
    private MediaPlayer player;
    private boolean start;

    public MyEngineManager(VideoWallpaperService.VideoEngine videoEngine, SharedPreferences sharedPreferences) {
        this.videoEngine = videoEngine;
        this.sharedPreferences = sharedPreferences;
        if (player == null) {
            player = new MediaPlayer();
            player.setLooping(true);
        }
    }

    private void initMedia() {
        if (this.player != null) {
            initPlayerUrl();
        }
    }

    private void initPlayerUrl() {
        if (this.sharedPreferences != null) {
            String str = this.sharedPreferences.getString(Contants.SpUtils.SP_WALLPAPER_URL, "");
            if (!this.start) {
                if (this.player.isPlaying()) {
                    this.player.pause();
                } else {
                    this.player.stop();
                    this.player.reset();
                }
            }
            try {
                this.player.setDataSource(BaseApplication.getInstance(), Uri.parse(str));
                this.player.setLooping(true);
                this.player.prepare();
                this.player.seekTo(0);
                this.player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        MyEngineManager.this.player.start();
                    }
                });
                this.start = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void onVisibilityChanged(boolean visible) {
        if (!visible) {
            if (this.player != null) {
                this.player.pause();
            }
        } else if ((this.player != null) && (!this.start)) {
            this.player.start();
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (Contants.SpUtils.SP_WALLPAPER_URL.equalsIgnoreCase(key)) {
            initMedia();
        } else if (Contants.SpUtils.SP_SOUND_ON.equals(key)) {
            if (this.player != null)
                this.player.setVolume(0.0f, 1.0f);
        } else if (Contants.SpUtils.SP_SOUND_OFF.equalsIgnoreCase(key)) {
            if (this.player != null)
                this.player.setVolume(0.0f, 0.0f);
        }


    }


    public void onSurfaceDestroyed() {
        if (this.player != null) {
            this.player.release();
            this.player = null;
        }
    }

    public void onSurfaceCreated() {
        this.player.setSurface(this.videoEngine.getSurfaceHolder().getSurface());
        initMedia();
    }

    public void onDestroy() {

    }
}
