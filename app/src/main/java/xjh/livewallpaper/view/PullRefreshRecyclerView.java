package xjh.livewallpaper.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import xjh.livewallpaper.R;

import static xjh.livewallpaper.view.PullRefreshRecyclerView.ScrollStatus.LOADING;
import static xjh.livewallpaper.view.PullRefreshRecyclerView.ScrollStatus.REFRESHING;
import static xjh.livewallpaper.view.PullRefreshRecyclerView.ScrollStatus.STATUS_DEFAULE;

/**
 * Created by lovexujh on 2017/6/6
 */

public abstract class PullRefreshRecyclerView extends LinearLayout implements SwipeRefreshLayout.OnRefreshListener {


    protected RecyclerView recyclerView;
    private OnRefreshLoadMoreListener onRefreshLoadMoreListener;
    private ScrollStatus scrollStatus = STATUS_DEFAULE;
    private SwipeRefreshLayout swipeRefreshLayout;

    public PullRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public PullRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRecyclerView();
        initSwipeRefreshLayout();

    }


    private void initRecyclerView() {
        swipeRefreshLayout = (SwipeRefreshLayout)(LayoutInflater.from(getContext()).inflate(R.layout.pull_refresh_recycler_layout, this, true).findViewById(R.id.SwipeRefreshLayout));
        recyclerView = (RecyclerView) swipeRefreshLayout.findViewById(R.id.recycler_view);

        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new RecyclerScrollerListener());
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.c_0066cc,
                R.color.c_00ba50,
                R.color.c_ff0000,
                R.color.c_ffa800
        );

    }

    public void setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener onRefreshLoadMoreListener) {
        if (onRefreshLoadMoreListener == null) {
            throw new IllegalArgumentException("OnRefreshLoadMoreListener not be null !");
        }
        this.onRefreshLoadMoreListener = onRefreshLoadMoreListener;
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        if (onRefreshLoadMoreListener != null && scrollStatus != REFRESHING) {
            scrollStatus = REFRESHING;
            swipeRefreshLayout.setRefreshing(true);
            onRefreshLoadMoreListener.onRefresh();
        }
    }

    public interface OnRefreshLoadMoreListener {
        void onRefresh();

        void onLoadMore();
    }

    /**
     * must be invoke this function after refresh finish !
     *
     * @param scrollStatus
     */
    public void setScrollStatus(ScrollStatus scrollStatus) {
        this.scrollStatus = scrollStatus;
        switch (scrollStatus) {
            case REFRESHING:
                swipeRefreshLayout.setRefreshing(true);
                break;
            default:
                swipeRefreshLayout.setRefreshing(false);
        }

    }

    public enum ScrollStatus {

        REFRESHING(1), REFRESH_FINISH(2), LOADING(4), LOAD_FINISH(5), STATUS_DEFAULE(6);

        private int value;

        ScrollStatus(int value) {
            this.value = value;
        }
    }

    public class RecyclerScrollerListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int itemCount = recyclerView.getAdapter().getItemCount();
            if (getLastVisibleItem() == itemCount - 1) {
                if (onRefreshLoadMoreListener != null && scrollStatus != LOADING) {
                    scrollStatus = LOADING;
                    onRefreshLoadMoreListener.onLoadMore();
                }
            }
        }

    }

    public abstract int getLastVisibleItem();

}
