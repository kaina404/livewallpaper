package xjh.livewallpaper.manager;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileOutputStream;

import xjh.livewallpaper.common.Tool;

/**
 * Created by lovexujh on 2017/6/1
 */

public class ImageManager {

    public void downLoadImage(final Context context, final String url) {

        Glide.with(context).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    saveFileToSD(context, Tool.getUrlName(url), resource);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void saveFileToSD(Context context, String filename, byte[] bytes) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = Tool.getDefaultPath() + "icon";
            File dir1 = new File(filePath);
            if (!dir1.exists()) {
                dir1.mkdirs();
            }
            filename = filePath + "/" + filename;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(bytes);
            //将bytes写入到输出流中
            output.close();
        }
    }

}
