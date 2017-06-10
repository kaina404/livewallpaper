package xjh.livewallpaper.manager;

import android.app.Activity;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import xjh.livewallpaper.LogUtil;
import xjh.livewallpaper.R;
import xjh.livewallpaper.activity.BaseActivity;
import xjh.livewallpaper.data.InitAPPBean;
import xjh.livewallpaper.net.INetCall;
import xjh.livewallpaper.net.NetManager;
import xjh.livewallpaper.net.NetRequest;
import xjh.livewallpaper.view.TabListItemBean;
import xjh.livewallpaper.view.TabListView;

import static xjh.livewallpaper.net.NetId.INITLIST;
import static xjh.livewallpaper.net.NetId.QUESTPREDATATEST;

/**
 * Created by lovexujh on 2017/5/31
 */

public class TabListViewManager implements INetCall {


    private BaseActivity activity;
    private TabListView.OnItemClickListener listener;

    private void setResponse(String response, Activity activity) {
        ArrayList<TabListItemBean> tabListItemBeenList = new ArrayList<>();
        InitAPPBean bean = JSON.parseObject(response, InitAPPBean.class);
        List<InitAPPBean.DataBean.CategoryIdsBean> category_ids = bean.getData().getCategory_ids();
        final List<String> iconList = new ArrayList<>();

        for (int i = 0; i < category_ids.size(); i++) {
            InitAPPBean.DataBean.CategoryIdsBean categoryIdsBean = category_ids.get(i);
            TabListItemBean itemBean = new TabListItemBean();
            itemBean.setText(categoryIdsBean.getName());
            itemBean.setSelectedUrl(categoryIdsBean.getIcon_on());
            itemBean.setUnSelectUrl(categoryIdsBean.getIcon_off());
            itemBean.setSelectedColor(R.color.c_ff4834);
            itemBean.setUnSelectColor(R.color.c_F8F8FF);
            itemBean.setTag(categoryIdsBean.getCategory_id());
            tabListItemBeenList.add(itemBean);
            iconList.add(categoryIdsBean.getIcon_on());
            iconList.add(categoryIdsBean.getIcon_off());
        }
        //这里下载所有图标
        NetRequest request = new NetRequest();
        request.downloadFile(this, iconList);


        TabListView tabListView = (TabListView) activity.findViewById(R.id.tab_list_view);
        tabListView.initView(tabListItemBeenList);
        tabListView.setOnItemClickListener(listener);
        tabListView.defaultDisplayItem(0);
    }

    public TabListViewManager(BaseActivity activity) {
        this.activity = activity;
    }

    public void requestData(TabListView.OnItemClickListener listener) {
        this.listener = listener;
        NetManager.initList(this);
    }


    @Override
    public void onResponse(int requestId, String response) {
        switch (requestId) {
            case INITLIST:
                setResponse(response, activity);
                break;
            case QUESTPREDATATEST:

                break;
        }

    }


    @Override
    public void onFailure(int requestId, Object errObj) {
        LogUtil.d("TabListViewManager#onFailure" + errObj);
    }
}
