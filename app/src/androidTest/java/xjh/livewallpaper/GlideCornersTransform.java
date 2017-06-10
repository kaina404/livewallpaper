package xjh.livewallpaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by lovexujh on 2017/6/1
 */

public class GlideCornersTransform extends BitmapTransformation {

    private int radius;

    public GlideCornersTransform(Context context, int radius) {
        super(context);
        this.radius = radius;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {

        return cornerBitmap(pool, toTransform);
    }

    private Bitmap cornerBitmap(BitmapPool pool, Bitmap toTransform) {
        if (toTransform == null) {
            return null;
        }
        Bitmap result = pool.get(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        RectF rect = new RectF(0, 0, toTransform.getWidth(), toTransform.getHeight());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        canvas.drawRoundRect(rect, radius, radius, paint);
        return null;
    }

    @Override
    public String getId() {
        return null;
    }
}
