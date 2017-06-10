package xjh.livewallpaper.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import xjh.livewallpaper.R;

/**
 * Created by lovexujh on 2017/6/6
 */

public class MainContentViewHolder extends RecyclerView.ViewHolder  {

    public MainContentViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

    public ImageView imageView;
}
