package xjh.livewallpaper.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import xjh.livewallpaper.LogUtil;
import xjh.livewallpaper.R;
import xjh.livewallpaper.adapter.MainContentAdapter;
import xjh.livewallpaper.common.Contants;
import xjh.livewallpaper.common.SpUtils;
import xjh.livewallpaper.common.Tool;
import xjh.livewallpaper.data.Bean;
import xjh.livewallpaper.listener.StaggeredGridPullRefreshView;
import xjh.livewallpaper.manager.TabListViewManager;
import xjh.livewallpaper.net.INetCall;
import xjh.livewallpaper.net.NetId;
import xjh.livewallpaper.net.NetManager;
import xjh.livewallpaper.view.ImageTxtView;
import xjh.livewallpaper.view.PullRefreshRecyclerView;
import xjh.livewallpaper.view.TabListItemBean;
import xjh.livewallpaper.view.TabListView;

public class MainActivity extends BaseActivity implements INetCall, TabListView.OnItemClickListener, ImageTxtView.OnSelectListener {

    private MainContentAdapter recyclerViewAdapter;
    private String category_id;
    private List<Bean.DataBean.VideoListBean> allData = new ArrayList<>();
    private int currentPage;
    private StaggeredGridPullRefreshView staggeredGridPullRefreshView;
    private ImageTxtView ivSound;
    private ImageTxtView ivMenu;
    private ImageTxtView ivFile;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
    }

    private void initView() {
        initSwipeRefreshLayout2();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ivSound = (ImageTxtView) findViewById(R.id.imageTv_sound);
        ivMenu = (ImageTxtView) findViewById(R.id.imageTv_menu);
        ivFile = (ImageTxtView) findViewById(R.id.imageTv_file);

        ivSound.setOnSelectListener(this);
        ivMenu.setOnSelectListener(this);
        ivFile.setOnSelectListener(this);
    }

    private void initSwipeRefreshLayout2() {
        staggeredGridPullRefreshView = (StaggeredGridPullRefreshView) findViewById(R.id.staggeredGridPullRefreshView);
        staggeredGridPullRefreshView.setStaggeredGridLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        staggeredGridPullRefreshView.setOnRefreshLoadMoreListener(new PullRefreshRecyclerView.OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh() {
                NetManager.questPageData(MainActivity.this, category_id, 1);
            }

            @Override
            public void onLoadMore() {
                NetManager.questPageData(MainActivity.this, category_id, ++currentPage);
            }
        });
        recyclerViewAdapter = new MainContentAdapter(this);
        recyclerViewAdapter.setData(null);
        staggeredGridPullRefreshView.setAdapter(recyclerViewAdapter);
    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onResponse(int requestId, String response) {
        LogUtil.d("onResponse", "onResponse");

        switch (requestId) {
            case NetId.QUESTPREDATATEST:
                handlerQuestCategory(response);
                break;
        }

    }


    private void handlerQuestCategory(String response) {
        Bean bean = JSON.parseObject(response, Bean.class);
        List<Bean.DataBean.VideoListBean> video_list;
        if (bean == null || bean.getData() == null || Tool.isEmpty(video_list = bean.getData().getVideo_list())) {
            video_list = new ArrayList<>();
        }
        if (bean.getData().getPage() == 1) {
            allData.clear();
            staggeredGridPullRefreshView.setScrollStatus(PullRefreshRecyclerView.ScrollStatus.REFRESH_FINISH);
        } else {
            staggeredGridPullRefreshView.setScrollStatus(PullRefreshRecyclerView.ScrollStatus.LOAD_FINISH);
        }
        allData.addAll(video_list);
        recyclerViewAdapter.setData(allData);

    }

    @Override
    public void onFailure(int requestId, Object errObj) {
        LogUtil.d("onFailure", "onFailure");
        currentPage--;
        staggeredGridPullRefreshView.setScrollStatus(PullRefreshRecyclerView.ScrollStatus.STATUS_DEFAULE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean request = false;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                if (!request) {
                    TabListViewManager tabListViewManager = new TabListViewManager(MainActivity.this);
                    tabListViewManager.requestData(MainActivity.this);
                    request = true;
                }
            } else {
                //用户不同意，向用户展示该权限作用
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setMessage("加载数据需要赋予访问存储的权限，不开启将无法正常工作！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, permissions, 100);
                            }
                        })
                        .setNegativeButton("取消", null).create();
                dialog.show();
            }
        }

    }

    @Override
    public void onItemClick(TabListItemBean clickBean) {
        this.category_id = (String) clickBean.getTag();
        allData.clear();
        currentPage = 1;
        NetManager.questPageData(MainActivity.this, (String) clickBean.getTag(), currentPage);
    }

    @Override
    public void onSelect(boolean select, View view) {
        switch (view.getId()){
            case R.id.imageTv_sound:
                SpUtils spUtils = new SpUtils();
                if(select){
                    spUtils.setString(Contants.SpUtils.SP_SOUND_ON, UUID.randomUUID()+"");
                }else {
                    spUtils.setString(Contants.SpUtils.SP_SOUND_OFF, UUID.randomUUID()+"");
                }
                break;
            case R.id.imageTv_menu:
                drawer.openDrawer(GravityCompat.START);
                break;

        }
    }

}
