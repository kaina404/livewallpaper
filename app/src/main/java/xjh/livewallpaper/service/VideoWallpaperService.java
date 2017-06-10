package xjh.livewallpaper.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import xjh.livewallpaper.LogUtil;
import xjh.livewallpaper.player.MyEngineManager;

import static xjh.livewallpaper.common.Contants.SpUtils.SP_WALLPAPER_NAME;

/**
 * Created by lovexujh on 2017/5/17
 */

public class VideoWallpaperService extends WallpaperService {

    @Override
    public void onDestroy() {
//        super.onDestroy();
        LogUtil.d("VideoWallpaperService#onDestroy");
    }

    @Override
    public Engine onCreateEngine() {
        LogUtil.d("VideoWallpaperService#onCreateEngine");
        return new VideoEngine(getApplicationContext());
    }


    public class VideoEngine extends Engine implements SharedPreferences.OnSharedPreferenceChangeListener {

        private final SharedPreferences sharedPreferences;
        private MyEngineManager myEngine;

        public VideoEngine(Context context) {
            super();
            LogUtil.d("VideoWallpaperService#VideoEngine()");
            this.sharedPreferences = context.getSharedPreferences(SP_WALLPAPER_NAME, MODE_PRIVATE);
            this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);
            this.myEngine = new MyEngineManager(this, sharedPreferences);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            this.myEngine.onVisibilityChanged(visible);
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            this.myEngine.onSurfaceCreated();
        }


        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.myEngine.onSurfaceDestroyed();
        }


        @Override
        public void onDestroy() {
            super.onDestroy();
            this.myEngine.onDestroy();
        }


        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            this.myEngine.onSharedPreferenceChanged(sharedPreferences, key);
        }
    }

}
