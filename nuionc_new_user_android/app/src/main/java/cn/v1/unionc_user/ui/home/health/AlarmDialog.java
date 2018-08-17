package cn.v1.unionc_user.ui.home.health;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.v1.unionc_user.R;


public class AlarmDialog extends BaseDialog implements View.OnClickListener{

	public static final int CANCELANDOK=1,ONLYOK=2;
	private int dialogType=CANCELANDOK;
	private String title,message,smallmessage;
	public String getSmallmessage() {
		return smallmessage;
	}
	public void setSmallmessage(String smallmessage) {
		this.smallmessage = smallmessage;
	}
	private IRespCallBack mCallBack;
	private Button btnOK,btnCancel;
	private TextView xbp_view_line;
    private View title_line;
	public TextView tvTitle,tvMessage,tvSmallMessage;
    private LinearLayout title_ll;
	private ScrollView sv;
	public LinearLayout control_alarmdialog_lin,control_content_ll;
//	private CheckBox dialogMissionSetting;
//	private TextView dialogMissionSettingTv;
	ArrayList<String> ButtonName;
	private String str_okbtn,str_cancelbtn;
	private boolean isShowMissionSetting = false;
	public AlarmDialog(Context context, IRespCallBack callback) {
		this(context, CANCELANDOK,callback,"","");
	}
	public AlarmDialog(Context context, int DialogType, IRespCallBack callback, String title, String message) {
		super(context);
		setCanceledOnTouchOutside(false);
		setRootView(R.layout.control_alarmdialog);
		setContentView(getRootView());
		mCallBack=callback;
		setTitle(title);
		setMessage(message);
		this.dialogType=DialogType;
		init(dialogType);
	}
	public AlarmDialog(Context context, int DialogType, IRespCallBack callback, String title, String message, String left, String right) {
		super(context);
		setCanceledOnTouchOutside(false);
		setRootView(R.layout.control_alarmdialog);
		setContentView(getRootView());
		mCallBack=callback;
		setTitle(title);
		setMessage(message);
		setStr_okbtn(left);
		setStr_cancelbtn(right);
		this.dialogType=DialogType;
		init(dialogType);
	}
	private void init(int dialogType){
        title_line = findViewById(R.id.title_line);
        title_ll = (LinearLayout) findViewById(R.id.title_ll);
		btnOK=(Button)findViewById(R.id.control_alarmdialog_ok);
		btnCancel=(Button)findViewById(R.id.control_alarmdialog_cancel);
		xbp_view_line = (TextView)findViewById(R.id.xbp_view_line);
		sv=(ScrollView)findViewById(R.id.control_alarmdialog_scrollview);
		if(dialogType==CANCELANDOK){
			visibale(btnCancel);
		}
		else {
			gone(btnCancel);
			gone(xbp_view_line);
			btnOK.setBackgroundResource(R.drawable.newdialog_bottombtn_bg);
		}
		btnOK.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		tvTitle=(TextView)findViewById(R.id.control_alarmdialog_title);
		tvMessage=(TextView)findViewById(R.id.control_alarmdialog_message);
		control_alarmdialog_lin=(LinearLayout)findViewById(R.id.control_alarmdialog_lin);
        control_content_ll = (LinearLayout)findViewById(R.id.control_content_ll);
		tvSmallMessage=(TextView)findViewById(R.id.control_alarmdialog_smallmessage);
		
	}
	
	public int getDialogType() {
		return dialogType;
	}
	public void setDialogType(int dialogType) {
		this.dialogType = dialogType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setGravity(int gr){
        this.tvMessage.setGravity(gr);
    }
    public void setContentLLLeft(int gr){
        this.control_content_ll.setGravity(gr);
    }

	public void setView(View v){
		if(isShowMissionSetting){
//			boolean isChecked = CaiboSetting.getBooleanAttr(mContext, CaiboSetting.KEY_IS_SHOW_MISSION, true);//获取开关状态，给CheckBox设置状态
//			dialogMissionSetting = (CheckBox) v.findViewById(R.id.pop_mission_setting_checkbox);
//			dialogMissionSettingTv = (TextView) v.findViewById(R.id.pop_mission_setting_tv);
//			if(null != dialogMissionSetting){
//				dialogMissionSetting.setOnClickListener(this);
//				dialogMissionSettingTv.setOnClickListener(this);
//				dialogMissionSetting.setChecked(isChecked);
//			}
		}
		sv.setVisibility(View.GONE);
		control_alarmdialog_lin.addView(v);
	}
	
	public void setIsShowMissionSetting(){
		isShowMissionSetting = true;
	}
	
	public String getStr_okbtn() {
		return str_okbtn;
	}
	public void setStr_okbtn(String str_okbtn) {
		this.str_okbtn = str_okbtn;
	}
	public String getStr_cancelbtn() {
		return str_cancelbtn;
	}
	public void setStr_cancelbtn(String str_cancelbtn) {
		this.str_cancelbtn = str_cancelbtn;
	}
	@Override
	public void show() {
		super.show();
//		if(getTitle()!=null&&getTitle().length()>8)tvTitle.setTextSize(13);
//		else {
//			tvTitle.setTextSize(17);
//		}
		tvTitle.setText(getTitle());
        if(getTitle().equals("")){
            title_ll.setVisibility(View.GONE);
            title_line.setVisibility(View.GONE);
        }
		tvMessage.setText(getMessage());
		if (TextUtils.isEmpty(getMessage())){
			control_content_ll.setVisibility(View.GONE);
		}else{
			control_content_ll.setVisibility(View.VISIBLE);
		}
		if(!StringUtil.checkNull(getSmallmessage()))tvSmallMessage.setText(getSmallmessage());
		else {
			tvSmallMessage.setVisibility(View.GONE);
		}
		if(!StringUtil.checkNull(getStr_okbtn()))btnOK.setText(getStr_okbtn());
		if(!StringUtil.checkNull(getStr_cancelbtn()))btnCancel.setText(getStr_cancelbtn());
	};
	@Override
	public void onClick(View v) {
		if(v.equals(btnOK)){
			if(mCallBack.callback(ALARMDIALOGOK))dismiss();
//			else if(mCallBack.callback(ALARMDIALOGOK, dialogMissionSetting != null && dialogMissionSetting.isChecked()))
//			dismiss();
		} else if (v.equals(btnCancel)) {
			if(mCallBack.callback(ALARMDIALOGCANCEL))dismiss();
		}
	}
}
