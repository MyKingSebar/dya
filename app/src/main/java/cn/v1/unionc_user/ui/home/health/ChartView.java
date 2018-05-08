package cn.v1.unionc_user.ui.home.health;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * 2017年3月17日
 */
public class ChartView extends View {
    private final int lastXTextPosition;
    public int XPoint;// 原点的X坐标
    public int YPoint; // 原点的Y坐标
    public int XScale; // X的刻度长度
    public int YScale; // Y的刻度长度
    public int XLength; // X轴的长度
    public int YLength; // Y轴的长度
    public String[] XLabel; // X的刻度
    public String[] YLabel = {"30", "90", "150"}; // Y的刻度
    public String[] Data; // 数据
    private int textsize;// 轴文字大小
    private int YstopX;// Y轴刻度x方向结束偏移量
    private int YtextX;// Y轴文字x坐标
    private int XtextY;// X轴文字Y坐标
    private int circleradius;// 圆圈大小
    private float paint1Width;
    private Paint paint;
    private Paint paint1;
    private int Y30;
    private Context context;

    public ChartView(Activity context) {
        super(context);
        this.context = context;
        XPoint = dip2px(context, 5);
        YPoint = dip2px(context, 120);
        XLength = context.getWindowManager().getDefaultDisplay().getWidth();
        YLength = dip2px(context, 120);
        textsize = dip2px(context, 14);
        YstopX = dip2px(context, 5);
        YtextX = dip2px(context, 18);
        XtextY = dip2px(context, 19);
        Y30 = dip2px(context, 60);
        lastXTextPosition = dip2px(context, 30);
        circleradius = dip2px(context, 1.5f);
        paint1Width = dip2px(context, 1f);
    }

    /**
     * 设置数据
     *
     * @param xnum    x轴数据个数
     * @param XLabels x轴数据坐标
     * @param AllData 数据
     */
    public void setInfo(int xnum, String[] XLabels, String[] AllData) {
        XScale = XLength / xnum;
        YScale = YLength / 2;
        XLabel = XLabels;
        Data = AllData;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);// 重写onDraw方法

        //canvas.drawColor(Color.WHITE);//设置背景颜色
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);// 去锯齿
        paint.setColor(Color.parseColor("#999999"));// 颜色
        paint.setTextSize(textsize); // 设置轴文字大小

        paint1 = new Paint();
        paint1.setStyle(Paint.Style.FILL);
        paint1.setAntiAlias(true);// 去锯齿
        paint1.setColor(Color.parseColor("#82B83F"));
        paint1.setStrokeWidth(paint1Width);

        // 设置Y轴
//        canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint); // 轴线
//        for (int i = 0; i * YScale < YLength; i++) {
//            canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + YstopX, YPoint - i * YScale, paint); // 刻度
//            try {
//                canvas.drawText(YLabel[i], XPoint - YtextX, YPoint - i * YScale + 5, paint); // 文字
//            } catch (Exception e) {
//            }
//        }

        // 设置X轴
        canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, paint); // 轴线
        canvas.drawLine(XPoint, YPoint - Y30, XPoint + XLength, YPoint - Y30, paint); // 平均线
        for (int i = 0; i * XScale < XLength; i++) {
            // canvas.drawLine(XPoint + i * XScale, YPoint, XPoint + i * XScale, YPoint - YstopX, paint); // 刻度
            try {
//                if (i == 0) {
//                    canvas.drawText(XLabel[i], XPoint + i * XScale, YPoint + XtextY, paint); // 文字
//                }
//                if(i == Data.length - 1){
//                    canvas.drawText(XLabel[XLabel.length - 1], XLength -lastXTextPosition, YPoint + XtextY, paint); // 文字
//                }
                // 数据值.
                if (i > 0 && YCoord(Data[i - 1]) != -999 && YCoord(Data[i]) != -999) {// 保证有效数据
                    canvas.drawLine(XPoint + (i - 1) * XScale, YCoord(Data[i - 1]), XPoint + i * XScale,
                            YCoord(Data[i]), paint1);
                }
                if (YCoord(Data[i]) != -999) {
                    canvas.drawCircle(XPoint + i * XScale, YCoord(Data[i]), circleradius, paint1);// 圆圈
                }
            } catch (Exception e) {
            }
        }
    }

    /*
     *计算绘制时的Y坐标，无数据时返回-999
     */
    private int YCoord(String y0) {
        int y;
        try {
            y = Integer.parseInt(y0);
        } catch (Exception e) {
            return -999; // 出错则返回-999
        }
        try {
            return YPoint - y * YScale / Integer.parseInt(YLabel[1]);
        } catch (Exception e) {
        }
        return y;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
