package cn.v1.unionc_user.ui.home.health;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;

public class HtmlFontUtil {
	
	public Spannable toHtmlFormat(String htmlStr)
	{
		return htmlFormat(htmlStr);
	}
	
	public Spannable toHtmlFormat(String color, int fontsize, String charsquence)
	{
		String htmlStr=getHtmlStr(color,fontsize,charsquence);
		return htmlFormat(htmlStr);
	}

	public Spannable toHtmlFormat(int fontsize, String charsquence){
		String htmlStr=getHtmlStr(fontsize,charsquence);
		return htmlFormat(htmlStr);
	}
	
	public Spannable htmlFormat(String htmlStr)
	{
		ArrayList<HtmlStrUnit> liststrUnit=new ArrayList<HtmlStrUnit>();
		String[] spableOp1=htmlStr.split("<style>");
		if(spableOp1.length!=0)
		{
			int op1Length=spableOp1.length;
			for (int i = 0; i < op1Length; i++) {
				String[] spableOp2=spableOp1[i].split("</style>");
				if(spableOp2.length!=0)
				{
					int op2Length=spableOp2.length;
					for (int j = 0; j < op2Length; j++) {
						if(spableOp2[j].indexOf("@")!=-1)
						{
							HtmlStrUnit strUnit=new HtmlStrUnit();
							String[] spableOp3=spableOp2[j].split("@");
							if(spableOp3.length>=2)
							{
								strUnit.setText(spableOp3[1]);
								String ColorAndSize=spableOp3[0];
								//说明颜色和字体只有一个
								if(ColorAndSize.indexOf("^")==-1)
								{
									if(ColorAndSize.indexOf("#")==-1)
									{
										strUnit.setSize(ColorAndSize);
									}
									else{
										strUnit.setColor(ColorAndSize);
									}
								}
								//说明颜色和字体都有
								else{
									String[] spableOp4=ColorAndSize.split("\\^");
									if(spableOp4.length>=2)
									{
										strUnit.setColor(spableOp4[0]);
										strUnit.setSize(spableOp4[1]);
									}
								}
							}
							liststrUnit.add(strUnit);
						}
						else {
							liststrUnit.add(new HtmlStrUnit(spableOp2[j]));
						}
					}
				}
			}
		}
		return getFormatSpannable(liststrUnit);
	}
	
	private Spannable getFormatSpannable(ArrayList<HtmlStrUnit> liststrUnit)
	{
		StringBuffer buffContext=new StringBuffer();
		if(liststrUnit.size()!=0)
		{
			for (int i = 0; i < liststrUnit.size(); i++) {
				buffContext.append(liststrUnit.get(i).getText());
			}
		}
		   SpannableStringBuilder sb = new SpannableStringBuilder(buffContext);
			if(liststrUnit.size()!=0)
			{
				int startIndex=0;
				for (int i = 0; i < liststrUnit.size(); i++) {
					HtmlStrUnit strUtil=liststrUnit.get(i);
					if(strUtil.getColor()!=null&&!strUtil.getColor().equals(""))
					{
						   ForegroundColorSpan fcs = new ForegroundColorSpan(new Color().parseColor(strUtil.getColor()));
						   sb.setSpan(fcs, startIndex,startIndex+strUtil.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					}
					if(strUtil.getSize()!=null&&!strUtil.getSize().equals(""))
					{
						AbsoluteSizeSpan sizespan=new AbsoluteSizeSpan(Integer.parseInt(strUtil.getSize()));
						sb.setSpan(sizespan, startIndex,startIndex+strUtil.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // make them also bold
					}
					if(strUtil.getText()!=null&&strUtil.getText().length()!=0)startIndex+=strUtil.getText().length();
				}
			}
			return sb;
	}
	
	/**
	 * @param color 字体颜色
	 * @param fontsize 字体大小
	 * @param charsquence 字体内容
	 * @return 处理后的字体
	 */
	public String getHtmlStr(String color, int fontsize, String charsquence)
	{
		StringBuffer sbHtmlStr=new StringBuffer();
		sbHtmlStr.append("<style>");
		sbHtmlStr.append(color+"^"+fontsize+"@");
		sbHtmlStr.append(charsquence);
		sbHtmlStr.append("</style>");
		return sbHtmlStr.toString();
	}

	public String getHtmlStr(String color, int fontsize, String charsquence, String underline)
	{
		StringBuffer sbHtmlStr=new StringBuffer();
		sbHtmlStr.append("<u>"+"<style>");
		sbHtmlStr.append(color+"^"+fontsize+"@");
		sbHtmlStr.append(charsquence);
		sbHtmlStr.append("</style>"+"</u>");
		return sbHtmlStr.toString();
	}
	
	public String getHtmlStr(String color, int fontsize, CharSequence charsquence)
	{
		StringBuffer sbHtmlStr=new StringBuffer();
		sbHtmlStr.append("<style>");
		sbHtmlStr.append(color+"^"+fontsize+"@");
		sbHtmlStr.append(charsquence);
		sbHtmlStr.append("</style>");
		return sbHtmlStr.toString();
	}

	public String getHtmlStr(int fontsize, CharSequence charsquence)
	{
		StringBuffer sbHtmlStr=new StringBuffer();
		sbHtmlStr.append("<style>");
		sbHtmlStr.append(fontsize+"@");
		sbHtmlStr.append(charsquence);
		sbHtmlStr.append("</style>");
		return sbHtmlStr.toString();
	}
	
	/**
	 * HTML元素单元
	 * @author Administrator
	 *
	 */
	class HtmlStrUnit
	{
		String Color;
		String Size;
		String Text;
		
		public HtmlStrUnit(String color, String size, String text)
		{
			setColor(color);
			setSize(size);
			setText(text);
		}
		
		public HtmlStrUnit(String text)
		{
			setText(text);
		}
		public HtmlStrUnit(){}
		
		
		public String getColor() {
			return Color;
		}
		public void setColor(String color) {
			Color = color;
		}
		public String getSize() {
			return Size;
		}
		public void setSize(String size) {
			Size = size;
		}
		public String getText() {
			return Text;
		}
		public void setText(String text) {
			Text = text;
		}
	}
	
	
}
