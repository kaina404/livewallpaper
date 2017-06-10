package xjh.livewallpaper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import xjh.livewallpaper.R;
import xjh.livewallpaper.common.Tool;

/**
 * Created by lovexujh on 2017/6/9
 */

public class ImageTxtView extends View {

    private String txtString;
    private int imageSelected_id;
    private Paint paintDrable;
    private int txtColor;
    private Paint paintTxt;
    private int txtHeight;
    private int txtWidth;
    private float txtSize;
    private float imagePadding;
    private int finalBitmapHeight;
    private int finalBitmapWidth;
    private float imagePaddingBottom;
    private Bitmap finalImageBitmap;
    private int imageNoSeceted_id;
    private boolean canTranslateImage;
    private int currentDrawBitmapId = -1;
    private OnSelectListener onSelectListener;

    public ImageTxtView(Context context) {
        this(context, null);
    }

    public ImageTxtView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTxtView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs, context);
        initPaint();
        initListener();
    }

    private void initPaint() {
        paintDrable = new Paint();
        paintDrable.setAntiAlias(true);

        paintTxt = new Paint();
        paintTxt.setAntiAlias(true);
        paintTxt.setColor(txtColor);
        paintTxt.setStrokeWidth(5);
        paintTxt.setTextSize(txtSize);//PX
        paintTxt.setStyle(Paint.Style.FILL);
    }

    private void initAttrs(AttributeSet attrs, Context context) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ImageTxtView);
        txtString = array.getString(R.styleable.ImageTxtView_text);
        imageSelected_id = array.getResourceId(R.styleable.ImageTxtView_image_selected, R.mipmap.ic_launcher);
        imageNoSeceted_id = array.getResourceId(R.styleable.ImageTxtView_image_no_selected, 0);
        txtColor = array.getColor(R.styleable.ImageTxtView_text_color, getResources().getColor(android.R.color.white));
        txtSize = array.getDimension(R.styleable.ImageTxtView_text_size, 50);
        imagePadding = array.getDimension(R.styleable.ImageTxtView_image_padding, 0);
        imagePaddingBottom = array.getDimension(R.styleable.ImageTxtView_image_padding_bottom, 0);
        currentDrawBitmapId = imageSelected_id;

        array.recycle();
    }

    private void initListener() {
        if (imageNoSeceted_id > 0 && imageSelected_id > 0) {
            canTranslateImage = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (canTranslateImage && event.getAction() == MotionEvent.ACTION_UP) {
            currentDrawBitmapId = currentDrawBitmapId == imageSelected_id ? imageNoSeceted_id : imageSelected_id;
            invalidate();
        }
        if (onSelectListener != null) {
            onSelectListener.onSelect(currentDrawBitmapId == imageSelected_id, this);
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureTxt();
        measureFinalBitmapSize();
        //默认包裹
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(finalBitmapWidth, MeasureSpec.getMode(widthMeasureSpec)), MeasureSpec.makeMeasureSpec((int) (txtHeight + finalBitmapHeight + imagePaddingBottom + imagePadding), MeasureSpec.getMode(heightMeasureSpec)));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        measureFinalBitmapSize();
        drawBitmap(canvas);
        drawText(canvas);

    }

    private void drawText(Canvas canvas) {
        canvas.drawText(txtString, ((float) finalBitmapWidth - (float) txtWidth) / 2, txtHeight + finalBitmapHeight + imagePaddingBottom + imagePadding, paintTxt);
    }

    private void drawBitmap(Canvas canvas) {
        canvas.drawBitmap(finalImageBitmap, 0, 0, paintDrable);
    }


    private void measureTxt() {
        Rect rect = new Rect();
        paintTxt.getTextBounds(txtString, 0, txtString.length(), rect);
        txtWidth = rect.width();
        txtHeight = rect.height();
    }

    private void measureFinalBitmapSize() {
        finalImageBitmap = Tool.upImageSize(BitmapFactory.decodeResource(getResources(), currentDrawBitmapId), txtWidth + (int) imagePadding, txtHeight + (int) imagePadding);
        finalBitmapHeight = finalImageBitmap.getHeight();
        finalBitmapWidth = finalImageBitmap.getWidth();
    }


    public interface OnSelectListener {
        void onSelect(boolean select, View view);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }
}
