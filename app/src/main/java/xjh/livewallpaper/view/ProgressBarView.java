package xjh.livewallpaper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import xjh.livewallpaper.R;
import xjh.livewallpaper.common.Tool;

import static xjh.livewallpaper.common.Tool.dp2px;

/**
 * Created by lovexujh on 2017/6/6
 */

public class ProgressBarView extends View {


    private Paint paintBlack;
    private int width;
    private int height;
    private Paint paintRed;
    private int radius = 0;
    private double current;
    private float layoutHeightD;
    private int background_color;
    private int progress_color;
    private Paint paintText;
    private String percent = "0%";
    private String finishTxt = "设为桌面";
    private ProgressStatus progressStatus = ProgressStatus.NO_LOAD;
    private boolean fillView;


    public ProgressBarView(Context context) {
        this(context, null);
    }

    public ProgressBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressBarView);
        layoutHeightD = array.getDimension(R.styleable.ProgressBarView_layout_height, 100);
        background_color = array.getColor(R.styleable.ProgressBarView_background_color, getResources().getColor(R.color.c_55000000));
        progress_color = array.getColor(R.styleable.ProgressBarView_progress_color, getResources().getColor(R.color.colorAccent));

        array.recycle();
    }

    private void initPaint() {
        paintBlack = new Paint();
        paintBlack.setAntiAlias(true);
        paintBlack.setColor(background_color);
        paintBlack.setStyle(Paint.Style.FILL);

        paintRed = new Paint();
        paintRed.setAntiAlias(true);
        paintRed.setColor(progress_color);
        paintRed.setStyle(Paint.Style.FILL);

        paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setColor(Color.WHITE);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextSize(50);
        paintText.setStrokeWidth(5);
    }

    public void updateView(double current, double all) {
        fillView = false;
        if (current >= 0 && current < all) {
            this.current = (int) (Double.valueOf(Tool.divide(current + "", all + "").toString()) * width);
            percent = (int) (Double.valueOf(Tool.divide(current + "", all + "").toString().substring(0, 4)) * 100) + "%";
        } else if (current == all) {
            this.current = width;
            percent = finishTxt;
        }
        handler.sendEmptyMessage(0);
    }

    public ProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public void fillView() {
        fillView = true;
    }


    public enum ProgressStatus {
        NO_LOAD(1), LOADING(2), FINISH(3);

        ProgressStatus(int tmp) {
        }
    }

    public Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        this.height = (int) layoutHeightD;
        radius = height / 2;
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(dp2px(getContext(), this.height), MeasureSpec.getMode(heightMeasureSpec)));

        if (fillView) {
            this.current = width;
            percent = finishTxt;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (current == 0) {
            progressStatus = ProgressStatus.NO_LOAD;
        } else if (current > 0 && current < width) {
            progressStatus = ProgressStatus.LOADING;
        } else {
            progressStatus = ProgressStatus.FINISH;
        }
        drawLeft(canvas, (float) current);
        drawText(canvas, percent);
    }


    private void drawLeft(Canvas canvas, float length) {

        RectF rectFLeft = new RectF(0, 0, this.height, this.height);
        RectF rectFRight = new RectF(width - height, 0, width - height + height, height);

        Path path = new Path();
        int angle;
        if (length < radius) {
            double value = ((double) radius - length) / ((double) radius);
            angle = (int) Math.ceil(Math.acos(value) / Math.PI * 180);
            path.addArc(rectFLeft, 180 - angle, angle * 2);
            canvas.drawPath(path, paintRed);
            path.addArc(rectFLeft, 180 + angle, 90 - angle);
            path.arcTo(rectFRight, 270, 180);
            path.arcTo(rectFLeft, 90, 90 - angle);
            path.close();
            canvas.drawPath(path, paintBlack);
        } else if (length >= radius && length < width - radius) {
            path.addArc(rectFLeft, 90, 180);
            path.lineTo(length, 0);
            path.lineTo(length, height);
            path.lineTo(radius, height);
            canvas.drawPath(path, paintRed);
            path.moveTo(length, 0);
            path.arcTo(rectFRight, 270, 180);
            path.lineTo(length, height);
            path.close();

            canvas.drawPath(path, paintBlack);
        } else if (length < width) {
            path.addArc(rectFLeft, 90, 180);

            double value = ((double) (radius - (width - length))) / ((double) radius);
            angle = (int) Math.ceil(Math.acos(value) / Math.PI * 180);
            path.arcTo(rectFRight, 270, 90 - angle);
            path.arcTo(rectFRight, angle, 90 - angle);
            path.lineTo(radius, height);
            canvas.drawPath(path, paintRed);

            path.addArc(rectFRight, 360 - angle, angle * 2);
            canvas.drawPath(path, paintBlack);
        } else {
            path.addArc(rectFLeft, 90, 180);
            path.arcTo(rectFRight, 270, 180);
            path.close();
            canvas.drawPath(path, paintRed);
        }

    }


    private void drawText(Canvas canvas, String text) {
        int mTextWidth, mTextHeight;
        Rect textBounds = new Rect();
        paintText.getTextBounds(text, 0, text.length(), textBounds);
        mTextWidth = textBounds.width();
        mTextHeight = textBounds.height();

        canvas.translate((width - mTextWidth) / 2, (height - mTextHeight) / 2 + mTextHeight);
        canvas.drawText(text, 0, 0, paintText);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }

}
