package xjh.livewallpaper.activity;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import xjh.livewallpaper.Application.BaseApplication;
import xjh.livewallpaper.LogUtil;
import xjh.livewallpaper.R;
import xjh.livewallpaper.common.IntentKeys;
import xjh.livewallpaper.common.ProgressResponseListenerHelper;
import xjh.livewallpaper.common.SpUtils;
import xjh.livewallpaper.common.Tool;
import xjh.livewallpaper.net.INetCall;
import xjh.livewallpaper.net.NetRequest;
import xjh.livewallpaper.player.MyMediaPlayer;
import xjh.livewallpaper.service.VideoWallpaperService;
import xjh.livewallpaper.view.ProgressBarView;

import static xjh.livewallpaper.common.Contants.SpUtils.SP_WALLPAPER_NAME;
import static xjh.livewallpaper.common.Contants.SpUtils.SP_WALLPAPER_URL;

public class PlayerActivity extends BaseActivity implements INetCall, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private SurfaceView surfaceView;//这个玩意运行在自己的线程中，他不是一个UI线程
    private MyMediaPlayer player;
    private String url;
    private String thumbUrl;
    private ImageView iv;
    private TextView downLoadTv;
    private String curVideoName;
    private CheckBox playCb;
    private ImageView ivPlayBg;
    private ImageView ivSurfaceViewTop;
    private ProgressBarView progressBar;
    private boolean downloadFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initView();
        handlerIntent(getIntent());
        initData();
        //关键在于这个Service，要提前/一直处于开启状态
        startWallpaperService();
    }

    private void startWallpaperService() {
        Intent serverIntent = new Intent(this, VideoWallpaperService.class);
        startService(serverIntent);
    }

    private void initData() {
        // TODO: 2017/6/1  使用ContentProvider
        File file = new File(Tool.getMP4DownloadFilePath());
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().endsWith(curVideoName)) {
                    downLoadTv.setText("设置壁纸");
                    downloadFinish = true;
                    break;
                }
            }
        }

    }


    private void initView() {
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        playCb = (CheckBox) findViewById(R.id.cb_play);
        playCb.setOnCheckedChangeListener(this);
        downLoadTv = (TextView) findViewById(R.id.download_tv);
        downLoadTv.setOnClickListener(this);
        ivPlayBg = (ImageView) findViewById(R.id.iv_play_bg);
        ivSurfaceViewTop = (ImageView) findViewById(R.id.iv_surface_top);
        progressBar = (ProgressBarView) findViewById(R.id.progress_bar_view);
    }


    private void handlerIntent(Intent intent) {
        url = intent.getStringExtra(IntentKeys.M3U8_PATH);
        thumbUrl = intent.getStringExtra(IntentKeys.THUMB_URL);
        curVideoName = Tool.getUrlName(Tool.splitSpecMp4Url(url));
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Glide.with(this)
                .load(thumbUrl)
                .dontAnimate()
                .bitmapTransform(new BlurTransformation(this, 14, 3))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(ivPlayBg);

        Glide.with(this)
                .load(thumbUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(ivSurfaceViewTop);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(downloadFinish){
            progressBar.fillView();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.progress_bar_view:
                if (downloadFinish) {
                    if (player != null && player.isPlaying()) {
                        player.pause();
                        player.destroy();
                    }
                    setToWallpaper(Tool.getMP4DownloadFilePath() + "/" + Tool.getUrlName(Tool.splitSpecMp4Url(url)));
                } else if (progressBar.getProgressStatus() == ProgressBarView.ProgressStatus.NO_LOAD) {
                    NetRequest netRequest = new NetRequest();
                    Toast.makeText(this, "开始下载", Toast.LENGTH_LONG).show();
                    netRequest.downloadFile(Tool.splitSpecMp4Url(url), this, new ProgressResponseListenerHelper() {
                                @Override
                                public void onUIResponseProgress(double read, double all) {
                                    if (read == all) {
                                        downloadFinish = true;
                                    }
                                    progressBar.updateView(read, all);
                                }
                            }
                    );
                }
                break;
        }

    }


    public void setToWallpaper(String url) {
//        SpUtils spUtils = new SpUtils();
//        spUtils.setString(SP_WALLPAPER_URL, BaseApplication.appUrl);
        BaseApplication.getInstance().getSharedPreferences(SP_WALLPAPER_NAME, MODE_PRIVATE).edit().putString(SP_WALLPAPER_URL, url).commit();


        Intent paramView;
        paramView = new Intent();
        paramView.setAction("android.service.wallpaper.CHANGE_LIVE_WALLPAPER");
        paramView.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", new ComponentName(getPackageName(), VideoWallpaperService.class.getCanonicalName()));
        startActivityForResult(paramView, 8008);

       /* if (!Tool.serviceIsRunning(this, VideoWallpaperService.class.getCanonicalName()))//如果当前服务未打开，就打开服务
        {
            paramView = new Intent();
            paramView.setAction("android.service.wallpaper.CHANGE_LIVE_WALLPAPER");
            paramView.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", new ComponentName(getPackageName(), VideoWallpaperService.class.getCanonicalName()));
            startActivityForResult(paramView, 8008);
        } else {
            Toast.makeText(this, "设为桌面", Toast.LENGTH_LONG).show();
            if (spUtils.getBoolean("VideoEngine=VideoEngine")) {
                spUtils.putBoolean("VideoEngine=VideoEngine", false);
                paramView = new Intent();
                paramView.setAction("android.service.wallpaper.CHANGE_LIVE_WALLPAPER");
                paramView.putExtra("android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT", new ComponentName(getPackageName(), VideoWallpaperService.class.getCanonicalName()));
                startActivityForResult(paramView, 8008);
            } else {
                finish();
                paramView = new Intent("android.intent.action.MAIN");
                paramView.setFlags(67108864);
                paramView.addCategory("android.intent.category.HOME");
                startActivity(paramView);
            }
        }*/
    }


    public void setToWallPaper(String url) {
        SpUtils spUtils = new SpUtils();
        spUtils.setString(SP_WALLPAPER_URL, url);
        LogUtil.d("Player Activity sp url = " + spUtils.getString(SP_WALLPAPER_URL, "----"));


        /*LogUtil.d("playerActivity 发送广播");
        //发送广播并更改Engigne中得URL
        Intent sendBroadcastIntent = new Intent(getPackageName());
        sendBroadcastIntent.putExtra(getPackageName(), url);
        sendBroadcast(sendBroadcastIntent);*/

        LogUtil.d("在playerActivity中开启VideoLiveWallpaperService");
//        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(getPackageName(), VideoWallpaperService.class.getCanonicalName()));
        startActivityForResult(intent, 8008);
    }


    @Override//当设置过桌面后回调
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d("playerActivity onActivityResult requestCode = " + requestCode + " resultCode = " + resultCode);
//        if (requestCode == 8008) {
//
//            Intent paramIntent = new Intent("android.intent.action.MAIN");
//            paramIntent.setFlags(67108864);//FLAG_ACTIVITY_CLEAR_TOP
//            paramIntent.addCategory("android.intent.category.HOME");
//            startActivity(paramIntent);
//            finish();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.pause();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.destroy();

        }
    }

    @Override
    public void onResponse(int requestId, String response) {
        Toast.makeText(this, "下载完成， 路径是" + response, Toast.LENGTH_LONG).show();
        downLoadTv.setText("设置壁纸");
    }

    @Override
    public void onFailure(int requestId, Object errObj) {
        Toast.makeText(this, "下载失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) {
            if (player != null) {
                player.destroy();
            }
            player = new MyMediaPlayer(surfaceView, url);
            player.play();

        } else {
            if (player != null && player.isPlaying()) {
                player.pause();
            }
        }
        if (player != null && !player.isPlaying()) {
            ivSurfaceViewTop.setVisibility(View.GONE);
        } else {
            ivSurfaceViewTop.setVisibility(View.VISIBLE);
        }
    }

}
