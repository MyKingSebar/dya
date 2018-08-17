package cn.v1.unionc_user.ui.home.BloodPressure.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

import cn.v1.unionc_user.ui.home.BloodPressure.ScrollViewListener;

/**
 * Created by An4 on 2016/6/14.
 */
public class TzShowHideTitleScrollView extends ScrollView {

    ScrollViewListener scrollViewListener;
    private int downX;
    private int downY;
    private int mTouchSlop;


    int lastScrollY=0;

    public TzShowHideTitleScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public TzShowHideTitleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;

    }


    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            int scrollY = TzShowHideTitleScrollView.this.getScrollY();

            //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
            if (lastScrollY != scrollY) {
                lastScrollY = scrollY;
//                handler.sendMessageDelayed(handler.obtainMessage(), 100);
                scrollViewListener.onScroll(scrollY);
            }
            if (scrollViewListener != null) {
                scrollViewListener.onScroll(scrollY);
            }

        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        lastScrollY = this.getScrollY();
        if (scrollViewListener != null) {
            scrollViewListener.onScroll(lastScrollY );
//            handler.sendMessageDelayed(handler.obtainMessage(), 10);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                handler.sendMessageDelayed(handler.obtainMessage(), 10);
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 滑动事件
     */
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 2);//这里设置滑动的速度
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        lastScrollY = this.getScrollY();
//        if (scrollViewListener != null) {
//            scrollViewListener.onScroll(lastScrollY);
//        }

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
//                handler.sendMessageDelayed(handler.obtainMessage(), 10);
                break;
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                downY = (int) ev.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }
}
