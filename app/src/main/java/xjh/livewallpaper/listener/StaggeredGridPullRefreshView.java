package xjh.livewallpaper.listener;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import xjh.livewallpaper.common.Tool;
import xjh.livewallpaper.view.PullRefreshRecyclerView;

/**
 * Created by lovexujh on 2017/6/6
 */

public class StaggeredGridPullRefreshView extends PullRefreshRecyclerView {


    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public StaggeredGridPullRefreshView(Context context) {
        super(context);
    }

    public StaggeredGridPullRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setStaggeredGridLayoutManager(StaggeredGridLayoutManager staggeredGridLayoutManager){
        this.staggeredGridLayoutManager = staggeredGridLayoutManager;
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public int getLastVisibleItem() {
        return Tool.getMax(staggeredGridLayoutManager.findLastVisibleItemPositions(null));
    }
}
