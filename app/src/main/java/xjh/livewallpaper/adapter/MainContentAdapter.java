package xjh.livewallpaper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import xjh.livewallpaper.R;
import xjh.livewallpaper.activity.PlayerActivity;
import xjh.livewallpaper.common.IntentKeys;
import xjh.livewallpaper.data.Bean;
import xjh.livewallpaper.viewholder.MainContentViewHolder;

/**
 * Created by lovexujh on 2017/5/18
 */

public class MainContentAdapter extends RecyclerView.Adapter<MainContentViewHolder> {

    private List<Bean.DataBean.VideoListBean> beans = new ArrayList<>();
    private Context context;

    public MainContentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Bean.DataBean.VideoListBean> listBeen) {
        if (listBeen == null) {
            beans = new ArrayList<>();
        } else {
            beans.clear();
            beans.addAll(listBeen);
        }
        notifyDataSetChanged();
    }

    @Override
    public MainContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MainContentViewHolder MainContentViewHolder = new MainContentViewHolder(LayoutInflater.from(context).inflate(R.layout.prelistview_item, parent, false));
        return MainContentViewHolder;
    }

    @Override
    public void onBindViewHolder(MainContentViewHolder holder, final int position) {
        final Bean.DataBean.VideoListBean bean = beans.get(position);

        Glide.with(context)
                .load(bean.getThumb())
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean != null) {
                    Intent intent = new Intent(context, PlayerActivity.class);
                    intent.putExtra(IntentKeys.M3U8_PATH, bean.getPreview());
                    intent.putExtra(IntentKeys.THUMB_URL, bean.getThumb());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return beans.size();
    }

}
