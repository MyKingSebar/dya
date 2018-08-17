package cn.v1.unionc_user.ui.home.BloodPressure.views;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;


/**
 * 垂直刻度尺
 */
@SuppressLint("ClickableViewAccessibility")
public class VerticalTuneWheel extends View {

    public interface OnValueChangeListener {
        public void onValueChange(float value);
    }

    public static final int MOD_TYPE_HALF = 2;
    public static final int MOD_TYPE_ONE = 10;
    public static final int MOD_TYPE_FIVE = 5;

    private static final int ITEM_HALF_DIVIDER = 40;
    private static final int ITEM_ONE_DIVIDER = 10;

    private static final int ITEM_MAX_HEIGHT = 40;
    private static final int ITEM_MIN_HEIGHT = 25;
    private static final int ITEM_MID_HEIGHT = 32;

    private static final int TEXT_SIZE = 18;

    private float mDensity;
    private int mValue = 50, mMaxValue = 100, mModType = MOD_TYPE_ONE, mLineDivider = ITEM_ONE_DIVIDER;
    // private int mValue = 50, mMaxValue = 500, mModType = MOD_TYPE_ONE,
    // mLineDivider = ITEM_ONE_DIVIDER;

    private int mLastY, mMove;
    private int mWidth, mHeight;

    private int mMinVelocity;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    private OnValueChangeListener mListener;

    @SuppressWarnings("deprecation")
    public VerticalTuneWheel(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScroller = new Scroller(getContext());
        mDensity = getContext().getResources().getDisplayMetrics().density;

        mMinVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();

        // setBackgroundResource(R.drawable.bg_wheel);
    }

    private GradientDrawable createBackground() {
        float strokeWidth = 1 * mDensity; // 边框宽度
        int strokeColor = Color.parseColor("#42000000");// 边框颜色
//         int fillColor = Color.parseColor("#00000000");// 内部填充颜色

        setPadding((int) strokeWidth, (int) strokeWidth, (int) strokeWidth, 0);

        int colors[] = {0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF};// 分别为开始颜色，中间夜色，结束颜色
        GradientDrawable bgDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);// 创建drawable
        // bgDrawable.setColor(fillColor);
        bgDrawable.setStroke((int) strokeWidth, strokeColor);
        // setBackgroundDrawable(gd);
        return bgDrawable;
    }

    /**
     * 考虑可扩展，但是时间紧迫，只可以支持两种类型效果图中两种类型
     *
     * @param defaultValue 初始值
     * @param maxValue     最大值
     * @param model        刻度盘精度：
     */
    public void initViewParam(int defaultValue, int maxValue, int model) {
        switch (model) {
            case MOD_TYPE_HALF:
                mModType = MOD_TYPE_HALF;
                mLineDivider = ITEM_HALF_DIVIDER;
                mValue = defaultValue * 2;
                mMaxValue = maxValue * 2;
                break;
            case MOD_TYPE_ONE:
                mModType = MOD_TYPE_ONE;
                mLineDivider = ITEM_ONE_DIVIDER;
                mValue = defaultValue;
                mMaxValue = maxValue;
                break;

            default:
                break;
        }
        invalidate();

        mLastY = 0;
        mMove = 0;
        notifyValueChange();
    }

    /**
     * 设置用于接收结果的监听器
     *
     * @param listener
     */
    public void setValueChangeListener(OnValueChangeListener listener) {
        mListener = listener;
    }

    /**
     * 获取当前刻度值
     *
     * @return
     */
    public float getValue() {
        return mValue;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mWidth = getWidth();
        mHeight = getHeight();
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawScaleLine(canvas);
        // drawWheel(canvas);
        drawMiddleLine(canvas);
        setBackgroundDrawable(createBackground());
    }


    /**
     * 从中间往两边开始画刻度线
     *
     * @param canvas
     */
    private void drawScaleLine(Canvas canvas) {
        String color = "#42000000";
        canvas.save();

        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(2);
        linePaint.setColor(Color.parseColor(color));

        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(TEXT_SIZE * mDensity);
        textPaint.setColor(Color.BLACK);
        textPaint.setTypeface(Typeface.DEFAULT);

        int width = mWidth, height = mHeight, drawCount = 0;
        float xPosition = 0,
                yPosition = 0,
                textWidth = Layout.getDesiredWidth("0.0", textPaint),
                textWidthIntege = Layout.getDesiredWidth("0", textPaint);

        for (int i = 0; drawCount <= 4 * height; i++) {
            int numSize = String.valueOf((mValue + i) / 10).length();

            yPosition = (height / 2 - mMove) + i * mLineDivider * mDensity;
            if (yPosition + getPaddingBottom() < mHeight) {
                if ((mValue + i) % mModType == 0) {
                    canvas.drawLine(getPaddingLeft(), yPosition, mDensity * ITEM_MAX_HEIGHT, yPosition, linePaint);

                    if (mValue + i <= mMaxValue) {
                        switch (mModType) {
                            case MOD_TYPE_HALF:
                                canvas.drawText(String.valueOf((mValue + i) / 2), getWidth() - 15 * mDensity - textWidth, countLeftStart(mValue + i, yPosition, textWidth, textWidthIntege), textPaint);
                                break;
                            case MOD_TYPE_ONE:
                                if (mValue + i <= 99)
                                    canvas.drawText(String.valueOf((mValue + i)), getWidth() - 15 * mDensity - textWidth, yPosition + textWidth / 4, textPaint);
                                else
                                    canvas.drawText(String.valueOf((mValue + i) ) , getWidth() - 15 * mDensity - textWidth, yPosition + textWidth / 4, textPaint);
                                break;
                            default:
                                break;
                        }
                    }
                } else {
                    if ((mValue + i) % MOD_TYPE_FIVE == 0)
                        canvas.drawLine(getPaddingLeft(), yPosition, mDensity * ITEM_MID_HEIGHT, yPosition, linePaint);
                    else
                        canvas.drawLine(getPaddingLeft(), yPosition, mDensity * ITEM_MIN_HEIGHT, yPosition, linePaint);

                }
            }

            yPosition = (height / 2 - mMove) - i * mLineDivider * mDensity;
            if (yPosition > getPaddingTop()) {
                if ((mValue - i) % mModType == 0) {
                    canvas.drawLine(getPaddingLeft(), yPosition, mDensity * ITEM_MAX_HEIGHT, yPosition, linePaint);

                    if (mValue - i >= 0) {
                        switch (mModType) {
                            case MOD_TYPE_HALF:
                                canvas.drawText(String.valueOf((mValue - i) / 2), getWidth() - 15 * mDensity - textWidth, countLeftStart(mValue - i, yPosition, textWidth, textWidthIntege), textPaint);
                                break;
                            case MOD_TYPE_ONE:
                                if (mValue - i <= 99)
                                    canvas.drawText(String.valueOf((mValue - i)) , getWidth() - 15 * mDensity - textWidth, yPosition + textWidth / 4, textPaint);
                                else
                                    canvas.drawText(String.valueOf((mValue - i) ) , getWidth() - 15 * mDensity - textWidth, yPosition + textWidth / 4, textPaint);
                                break;

                            default:
                                break;
                        }
                    }
                } else {
                    if ((mValue - i) % MOD_TYPE_FIVE == 0)
                        canvas.drawLine(getPaddingLeft(), yPosition, mDensity * ITEM_MID_HEIGHT, yPosition, linePaint);
                    else
                        canvas.drawLine(getPaddingLeft(), yPosition, mDensity * ITEM_MIN_HEIGHT, yPosition, linePaint);
                }
            }

            drawCount += 2 * mLineDivider * mDensity;
        }

        canvas.restore();
    }

    /**
     * 计算没有数字显示位置的辅助方法
     *
     * @param value
     * @param yPosition
     * @param textWidth
     * @return
     */
    private float countLeftStart(int value, float yPosition, float textWidth, float textWidthIntege) {
        float yp = 0f;
        if (value < 20) {
            yp = yPosition - textWidth / 2;
        } else {
            yp = yPosition - (textWidth + textWidthIntege) / 2;
        }
        return yPosition + textWidth / 4;
    }

    /**
     * 画中间的绿色指示线
     *
     * @param canvas
     */
    private void drawMiddleLine(Canvas canvas) {
        int indexWidth = 4;
        String color = "#ED1B24";
        canvas.save();
        Paint redPaint = new Paint();
        redPaint.setStrokeWidth(indexWidth);
        redPaint.setColor(Color.parseColor(color));
        canvas.drawLine(0, mHeight / 2, mDensity * ITEM_MAX_HEIGHT, mHeight / 2, redPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int yPosition = (int) event.getY();

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                mScroller.forceFinished(true);

                mLastY = yPosition;
                mMove = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                mMove += (mLastY - yPosition);
                changeMoveAndValue();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                countMoveEnd();
                countVelocityTracker(event);
                return false;
            // break;
            default:
                break;
        }

        mLastY = yPosition;
        return true;
    }

    private void countVelocityTracker(MotionEvent event) {
        mVelocityTracker.computeCurrentVelocity(1000);
        float yVelocity = mVelocityTracker.getYVelocity();
        if (Math.abs(yVelocity) > mMinVelocity) {
            mScroller.fling(0, 0, (int) yVelocity, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
        }
    }

    private void changeMoveAndValue() {
        int tValue = (int) (mMove / (mLineDivider * mDensity));
        if (Math.abs(tValue) > 0) {
            mValue += tValue;
            mMove -= tValue * mLineDivider * mDensity;
            if (mValue <= 0 || mValue > mMaxValue) {
                mValue = mValue <= 0 ? 0 : mMaxValue;
                mMove = 0;
                mScroller.forceFinished(true);
            }
            notifyValueChange();
        }
        postInvalidate();
    }

    private void countMoveEnd() {
        int roundMove = Math.round(mMove / (mLineDivider * mDensity));
        mValue = mValue + roundMove;
        mValue = mValue <= 0 ? 0 : mValue;
        mValue = mValue > mMaxValue ? mMaxValue : mValue;

        mLastY = 0;
        mMove = 0;

        notifyValueChange();
        postInvalidate();
    }

    private void notifyValueChange() {
        if (null != mListener) {
            if (mModType == MOD_TYPE_ONE) {
                mListener.onValueChange(mValue);
            }
            if (mModType == MOD_TYPE_HALF) {
                mListener.onValueChange(mValue / 2f);
            }
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            if (mScroller.getCurrY() == mScroller.getFinalY()) { // over
                countMoveEnd();
            } else {
                int yPosition = mScroller.getCurrY();
                mMove += (mLastY - yPosition);
                changeMoveAndValue();
                mLastY = yPosition;
            }
        }
    }
}
