package xjh.livewallpaper.player;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by lovexujh on 2017/5/17
 */

public class MyMediaPlayer {

    private String filePath;
    private SurfaceView surfaceView;
    private MediaPlayer player;


    public MyMediaPlayer(final SurfaceView surfaceView, String filePath) {
        this.surfaceView = surfaceView;
        this.filePath = filePath;
        if (player == null) {
            player = new MediaPlayer(); //创建MediaPlayer对象
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置声音播放模式
        }
        //设置显示位置
        player.setDisplay(surfaceView.getHolder());
        //设置播放文件的地址
        try {
            player.setDataSource(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlaying() {
        return player != null && player.isPlaying();
    }

    public void play() {
        if (player != null) {
            try {
                player.prepare();
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }

                });
            } catch (Exception e) {

            }
        }
    }

    public void pause() {
        if (player != null) {
            player.pause();
        }
    }

    public void destroy() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    public void release() {
        if (player != null) {
            player.release();
        }
    }
}
