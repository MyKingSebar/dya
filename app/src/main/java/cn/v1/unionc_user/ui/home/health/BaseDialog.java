package cn.v1.unionc_user.ui.home.health;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import cn.v1.unionc_user.R;

public class BaseDialog extends Dialog {

	protected View RootView;
	private LayoutInflater mInflater;
	boolean isShow;
	protected Context mContext;
	
	public BaseDialog(Context context) {
		super(context, R.style.noTitleDialog);
		mContext=context;
		mInflater= LayoutInflater.from(context);
	}
	
	public BaseDialog(Context context, int theme) {
		super(context,theme);
		mContext=context;
		mInflater= LayoutInflater.from(context);
	}
	
	@Override
	public void show() {
		super.show();
		setShow(true);
	};

	@Override
	public void dismiss() {
		super.dismiss();
		setShow(false);
	}

	public View getRootView() {
		return RootView;
	}

	public void setRootView(int layoutID) {
		RootView = mInflater.inflate(layoutID, null);
	}
	public boolean isShow() {
		return isShow;
	}

	private void setShow(boolean isShow) {
		this.isShow = isShow;
	}
	
	protected void gone(View view)
	{
		view.setVisibility(View.GONE);
	}
	
	protected void visibale(View view){
		view.setVisibility(View.VISIBLE);
	}
	
	//提示确认弹出确认框取消按钮
	public static final int ALARMDIALOGCANCEL=-1;
	//提示确认弹出确认框确定按钮
	public static final int ALARMDIALOGOK=0;
	//串法对话框确认按钮
	public static final int CHUANFADIALOGOK=1;
	//串法对话框取消按钮
	public static final int CHUANFADIALOGCANCEL=2;
	//串法对话框中选择串法的按钮
	public static final int CHUANFADIALOGSEL=3;
	//选择操作对话框
	public static final int XUANZECAOZUO=4;
	//选择操作对话框取消按钮
	public static final int XUANZECAOZUOQUXIAO=5;
	//选择操作对话框取消按钮
	public static final int XUANZECAOZUOOK=9;
	//合买保密类型对话框确认按钮
	public static final int SECRECYDIALOGOK=6;
	//保密类型对话框取消按钮
	public static final int SECRECYDIALOGCANCEL=7;
	//保密类型对话框保密按钮
	public static final int SECRECYDIALOGBTN=8;
	//代购保密类型对话框确认按钮
	public static final int SECRECYDIALOGDAIGOUOK=14;
	//选择操作对话框
	public static final int XUANZECAOZUOMORECHOICE=10;
	//对话框确认按钮
	public static final int COMMON_OK = 11;
	//对话框 取消按钮 
	public static final int COMMON_CANCLE = 12;
	
	//好声音回调
	public static final int GOODVOICE=11;
	public static final int GOODVOICEDELETE=12;
	public static final int GOODVOICEUPDATE=13;
	public static int ALL1=-1;
	public static int ALL2=-2; 
	public static Integer ITEM1=0;
	public static Integer ITEM2=1;
}

