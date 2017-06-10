package xjh.livewallpaper.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import xjh.livewallpaper.R;
import xjh.livewallpaper.common.Tool;

/**
 * Created by lovexujh on 2017/5/31
 */

public class TabListView extends ScrollView implements View.OnClickListener {

    private List<TabListItemBean> beans = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private LinearLayout contentView;

    public TabListView(Context context) {
        this(context, null);
    }

    public TabListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        contentView = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(layoutParams);
        contentView.setOrientation(LinearLayout.VERTICAL);
        this.addView(contentView);
    }

    public void initView(List<TabListItemBean> beanList) {

        this.beans = beanList;
        for (int i = 0; i < beanList.size(); i++) {
            TabListItemBean bean = beanList.get(i);
            View view = initItemView(bean);
            view.setTag(bean);
            view.setOnClickListener(this);
            contentView.addView(view);
        }

    }


    private View initItemView(TabListItemBean bean) {
        View view = inflate(getContext(), R.layout.tab_list_item_view, null);
        ImageView iv = (ImageView) view.findViewById(R.id.tab_list_item_iv);
        TextView tv = (TextView) view.findViewById(R.id.tab_list_item_tv);
        if (bean.getUnSelectBitmap() != null) {
            iv.setImageBitmap(bean.getUnSelectBitmap());
        }
        if (!Tool.isEmpty(bean.getText())) {
            tv.setText(bean.getText());
        }
        if (!Tool.isEmpty(bean.getUnSelectUrl())) {
            displayIv(bean.getUnSelectUrl(), iv);
        }
        if (bean.getUnSelectColor() > 0) {
            tv.setTextColor(getResources().getColor(bean.getUnSelectColor()));
        }
        return view;
    }

    private void displayIv(String url, ImageView iv) {
        String localIcon = Tool.getDownloadFileAbsPath(url);
        if (!Tool.isEmpty(localIcon)) {
            Glide.with(getContext()).load(localIcon).error(R.mipmap.ic_launcher).dontAnimate().into(iv);
        } else {
            Glide.with(getContext()).load(url).error(R.mipmap.ic_launcher).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().into(iv);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getTag() != null && v.getTag() instanceof TabListItemBean) {
            TabListItemBean bean = (TabListItemBean) v.getTag();
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(bean);
            }
            for (int i = 0; i < contentView.getChildCount(); i++) {
                bean = beans.get(i);
                View view = contentView.getChildAt(i);
                ImageView iv = (ImageView) view.findViewById(R.id.tab_list_item_iv);
                TextView tv = (TextView) view.findViewById(R.id.tab_list_item_tv);
                if (v == view) {//select
                    if (bean.getSelectedBitmap() != null) {
                        iv.setImageBitmap(bean.getSelectedBitmap());
                    }
                    if (!Tool.isEmpty(bean.getSelectedUrl())) {
                        displayIv(bean.getSelectedUrl(), iv);
                    }
                    if (!Tool.isEmpty(bean.getText())) {
                        tv.setText(bean.getText());
                    }
                    if (bean.getSelectedColor() > 0) {
                        tv.setTextColor(getResources().getColor(bean.getSelectedColor()));
                    }
                } else {
                    if (bean.getUnSelectBitmap() != null) {
                        iv.setImageBitmap(bean.getUnSelectBitmap());
                    }
                    if (!Tool.isEmpty(bean.getUnSelectUrl())) {
                        displayIv(bean.getUnSelectUrl(), iv);
                    }
                    if (!Tool.isEmpty(bean.getText())) {
                        tv.setText(bean.getText());
                    }
                    if (bean.getUnSelectColor() > 0) {
                        tv.setTextColor(getResources().getColor(bean.getUnSelectColor()));
                    }
                }
            }
        }


    }

    public interface OnItemClickListener {
        void onItemClick(TabListItemBean clickBean);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 用来默认显示第几个条目
     * @param position
     */
    public void defaultDisplayItem(int position) {
        if (position >= 0 && position < contentView.getChildCount()) {
            contentView.getChildAt(position).performClick();
        }
    }

}
