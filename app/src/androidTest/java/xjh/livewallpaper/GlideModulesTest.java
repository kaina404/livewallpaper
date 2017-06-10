package xjh.livewallpaper;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by lovexujh on 2017/6/1
 */

public class GlideModulesTest implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        //设置加载的图片质量
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //根据已经得到的加载的图片的内存策略进行更改
        MemorySizeCalculator memorySizeCalculator = new MemorySizeCalculator(context);
        int bitmapPoolSize = memorySizeCalculator.getBitmapPoolSize();
        int memoryCacheSize = memorySizeCalculator.getMemoryCacheSize();

        builder.setMemoryCache(new LruResourceCache((int) (memoryCacheSize * 1.1)));
        builder.setBitmapPool(new LruBitmapPool((int) (bitmapPoolSize * 1.1)));
        //更改磁盘缓存路径
        int size = 10 * 1024 * 1024;
        //1.1私有缓存data/data/应用包名/cache/mydir
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "mydir", size));//10MB
        //1.2外部缓存SDCard/Android/data/应用包名/cache/mydir
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "mydir", size));
        //1.3自定义缓存路径
        builder.setDiskCache(new DiskLruCacheFactory("folder", "cacheName", size));

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
