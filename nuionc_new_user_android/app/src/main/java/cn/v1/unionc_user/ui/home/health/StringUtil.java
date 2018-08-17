package cn.v1.unionc_user.ui.home.health;


import android.text.Editable;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import cn.v1.unionc_user.UnioncApp;

public class StringUtil {
	private static final String TAG = "StringUtil";
	// 手机号的正则
	private static String cellphonePatternStr = "^1\\d{10}$";
	// 电子邮箱正则
	private static String emailPatternStr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	// 身份证正则表达式(15位)
	private static String isIDCard15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
	// 身份证正则表达式(18位)
	private static String isIDCard18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";
	//是否是一个姓名校验
	private static String trueNamePattern="^[\\u4E00-\\u9FA5A-Za-z.·]+$";
	// 验证是否是数字的校验
	private static String numberPatternStr = "\\d+(.\\d+)?|-\\d+(.\\d+)?$";
	//香港身份证正则表达式
	private static String isIdCardHongKong = "[A-Za-z][0-9]{6}(（[0-9A-Za-z]）|\\([0-9A-Za-z]\\))";
	//澳门身份证正则表达式
	private static String isIdCardMacao = "[157][0-9]{6}(（[0-9]）|\\([0-9]\\))";
	//台湾身份证正则表达式
	private static String isIdCardTaiwan = "[A-Z][0-9]{9}";

	public static final int MAN=1,WOMEN=0,EMPTY=-1;
	private static final char SPACE = 0x20; //Android 代表空格的ASC_II
	private static final char ENTER = 0x0d; //Android 代表空格的ASC_II
	private static final char CHANGRLINE = 0x0a; //Android 代表空格的ASC_II

	/**
	 * 校验是否是一个真实的数字
	 * @return
	 */
	public static boolean verifyTrueNumber(String validateNum){
		Pattern regexphone = Pattern.compile(numberPatternStr);
		Matcher matcherphone = regexphone.matcher(validateNum);
		boolean isMatchedPhone = matcherphone.matches();
		return isMatchedPhone;
	}
	
	/**
	 * 校验是否是一个真实的数字(多个)
	 * @return
	 */
	public static boolean verifyTrueNumber(String... validateNums){
		boolean isMatchedPhone=false;
		Pattern regexphone = Pattern.compile(numberPatternStr);
		int length=validateNums.length;
		for (int i = 0; i < length; i++) {
			Matcher matcherphone = regexphone.matcher(validateNums[i]);
			isMatchedPhone=matcherphone.matches();
			if(!isMatchedPhone)return false;
		}
		return isMatchedPhone;
	}
	
	// 验证手机号
	public static boolean verifyPhone(String phoneNum) {
		Pattern regexphone = Pattern.compile(cellphonePatternStr);
		Matcher matcherphone = regexphone.matcher(phoneNum);
		boolean isMatchedPhone = matcherphone.matches();
		return isMatchedPhone;
	}

	// 验证邮箱
	public static boolean verifyEmail(String Email) {
		Pattern regexphone = Pattern.compile(emailPatternStr);
		Matcher matchermail = regexphone.matcher(Email);
		boolean isMatchedmail = matchermail.matches();
		return isMatchedmail;
	}

	public static boolean compare_date(String DATE1, String DATE2) {

		String DATEone = DATE1;

		if(DATE1.split(":").length==2){
			DATEone = DATE1+":00";
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(DATEone);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() >= dt2.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}
	
	//验证真实姓名
	public static boolean verifyTrueName(String truename)
	{
		Pattern regexphone = Pattern.compile(trueNamePattern);
		Matcher matchermail = regexphone.matcher(truename);
		boolean isMatchedmail = matchermail.matches();
		return isMatchedmail;
	}
	
	//根据身份证号获取星座
	public static String getConstellByIDCard(String idcard)
	{
		String monthAndDay="";
		if(idcard.length()==18){
			//10-14位为月+日
			monthAndDay=idcard.substring(10,14);
		}
		else if (idcard.length()==15) {
			//8-12位为月+日
			monthAndDay=idcard.substring(8, 12);
		}
		monthAndDay=getConstellByBirthday(monthAndDay);
		if(checkNull(monthAndDay))return "";
		return monthAndDay;
	}
	
	//获取名字长度(中文2个英文1个)
	public static int getNameLength(String name)
	{
		int length=0;
		for (int i = 0; i < name.length(); i++) {
	       	if(!Util.isChinese(name.charAt(i)))
	       	{
	       		length++;
	       	}
	       	else {
				length+=2;
			}
		}
		return length;
	}
	
	public static String subStringChinese(String resStr, int maxLength){
		int length=0;
		for (int i = 0; i < resStr.length(); i++) {
	       	if(!Util.isChinese(resStr.charAt(i)))
	       	{
	       		length++;
	       	}
	       	else {
				length+=2;
			}
	       	if(length>=maxLength)return resStr.substring(0,i+1);
		}
		return resStr;
	}
	
	/**
	 * 处理超出长度的字符串
	 * 注意:====differenceChinese 如果不是必须要区分中文的,请设置成false,否则会影响效率
	 * @param resStr  原字符串
	 * @param differenceChinese 是否区分中英文(true:区分-中文两个长度,英文一个长度 false:不区分-中文英文都是一个长度)
	 * @param maxLength 允许的最大长度
	 * @return  处理后的字符串(如果超过maxLength则返回原字符串的前maxLength个字符加上"...")
	 */
	public static String getEllipsizeStr(String resStr, boolean differenceChinese, int maxLength){
		try {
			int size=differenceChinese?getNameLength(resStr):resStr.length();
			if(size>maxLength){
				if(differenceChinese){
					return subStringChinese(resStr, maxLength)+"...";
				}
				else {
					return resStr.substring(0,maxLength)+"...";				
				}
			}
		} catch (Exception e) {
		}
		return resStr;
	}
	
	public static String getConstellByBirthday(String monthAndDay)
	{
		if(checkNull(monthAndDay))return "";
		int birthdayInt= Integer.parseInt(monthAndDay);
		if(birthdayInt>=120&&birthdayInt<=218)return "水瓶座";
		else if(birthdayInt>=219&&birthdayInt<=320) return"双鱼座";
		else if(birthdayInt>=321&&birthdayInt<=419) return"白羊座";
		else if(birthdayInt>=420&&birthdayInt<=520) return"金牛座";
		else if(birthdayInt>=521&&birthdayInt<=621) return"双子座";
		else if(birthdayInt>=622&&birthdayInt<=722) return"巨蟹座";
		else if(birthdayInt>=723&&birthdayInt<=822) return"狮子座";
		else if(birthdayInt>=823&&birthdayInt<=922) return"处女座";
		else if(birthdayInt>=923&&birthdayInt<=1023) return"天秤座";
		else if(birthdayInt>=1024&&birthdayInt<=1122) return"天蝎座";
		else if(birthdayInt>=1123&&birthdayInt<=1221) return"射手座";
		else  return"魔羯座";
	}
	
	//根据身份证获取性别
	public static int getSexByIDCard(String idcard)
	{
		String sex="";
		if(idcard.length()==18){
			//17位为性别 男单女双
			sex=idcard.substring(16,17);
		}
		else if (idcard.length()==15) {
			//8-12位为月+日
			sex=idcard.substring(14);
		}
		if(checkNull(sex))return EMPTY;
		if(Integer.parseInt(sex)%2==0)return WOMEN;
		return MAN;
	}
	
	public static String getBirthdayByIDCard(String idcard)
	{
		String birthday="";
		if(idcard.length()==18){
			//6-14位为生日yyyymmdd格式生日
			birthday=idcard.substring(6,14);
		}
		else if (idcard.length()==15) {
			//6-12位为yymmdd格式生日
			birthday="19"+idcard.substring(6, 12);
		}
		if(checkNull(birthday))return "";
		String year=birthday.substring(0, 4);
		String month=birthday.substring(4, 6);
		String day=birthday.substring(6, 8);
		//月份从1开始,但在程序中1月是0
		return year+"-"+(Integer.parseInt(month)-1)+"-"+day;
	}
	
	//校验身份证
	public static boolean verifyIdentity(String identityCardId)
	{
		Pattern regexcard15 = Pattern.compile(isIDCard15);
		Matcher matchercard15= regexcard15.matcher(identityCardId);
		identityCardId=identityCardId.toLowerCase();
		if(identityCardId.length()==18)
		{
			if(checkXIdentityCard(identityCardId,identityCardId.substring(17)))
			{
				identityCardId=identityCardId.replace('x', '2');
			}
			else
			{
				return false;
			}
		}
		Pattern regexcard18 = Pattern.compile(isIDCard18);
		Matcher matchercard18 = regexcard18.matcher(identityCardId);
		boolean isMatchedcard15 = matchercard15.matches();
		boolean isMatchedcard18 = matchercard18.matches();
		if(isMatchedcard15||isMatchedcard18)return true;
		return false;
	}
	
	//校验身份证末尾的X是否合格
	public static boolean checkXIdentityCard(String identityCard, String endNum)
	{
		int [] CoefficientNum={7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
		int Summation=0;
		char[] chararray=identityCard.substring(0,identityCard.length()-1).toCharArray();
		for (int i = 0; i < 17; i++) {
			Summation+= Integer.parseInt(chararray[i]+"")*CoefficientNum[i];
		}
		int yu=Summation%11;
		if(yu==0&&endNum.equals("1"))return true;
		if(yu==1&&endNum.equals("0"))return true;
		if(yu==2&&endNum.equals("x"))return true;
		if(yu==3&&endNum.equals("9"))return true;
		if(yu==4&&endNum.equals("8"))return true;
		if(yu==5&&endNum.equals("7"))return true;
		if(yu==6&&endNum.equals("6"))return true;
		if(yu==7&&endNum.equals("5"))return true;
		if(yu==8&&endNum.equals("4"))return true;
		if(yu==9&&endNum.equals("3"))return true;
		if(yu==10&&endNum.equals("2"))return true;
		return false;
		
	}

	public static boolean checkIsEighteenYears(String systemTime, String mId) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = df.parse(systemTime);
			int year = date.getYear()+1900;
			int month = date.getMonth()+1;
			int day = date.getDate();
			if (mId.length() == 15) {
				int mYear = 1900 + Integer.parseInt(mId.substring(6, 8));
				int mMonth = Integer.parseInt(mId.substring(8, 10));
				int mDay = Integer.parseInt(mId.substring(10, 12));
				int years = year - mYear;
				int months = month - mMonth;
				int days = day - mDay;
				return compareYMD(years, months, days);
			}
			if (mId.length() == 18) {
				int mYear = Integer.parseInt(mId.substring(6, 10));
				int mMonth = Integer.parseInt(mId.substring(10, 12));
				int mDay = Integer.parseInt(mId.substring(12, 14));
				int years = year - mYear;
				int months = month - mMonth;
				int days = day - mDay;
				return compareYMD(years, months, days);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean compareYMD(int years,int months,int days){
		if(years < 18){
			return false;
		}else if (years == 18) {
			if(months > 0 ){
				return true;
			}else if (months == 0) {
				if(days >= 0 ){
					return true;
				}else{
					return false;
				}
			}else if (months < 0) {
				return false;
			}
		}else if(years >18 ){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param beforeReplacestr 被替换的字符串
	 * @param beforeIndex  显示这个字符串之前的多少个字符
	 * @param endindex     显示这个字符串最后的多少个字符
	 * @return  处理后的字符串
	 */
	public static String getReplaceString(String beforeReplacestr, int beforeIndex, int endindex)
	{
		if(beforeReplacestr==null||beforeReplacestr.equals(""))return "";
	    if(beforeReplacestr.length()<=endindex)return beforeReplacestr;
	    StringBuffer buffer=new StringBuffer();
	    char [] replaceChar=beforeReplacestr.toCharArray();
	    int lenth=replaceChar.length;
	    for (int i = 0; i <lenth; i++) {
	    	char c=replaceChar[i];
			if(i>beforeIndex&&i<(lenth-endindex))
			{
				c='*';
			}
			buffer.append(c);
		}
	    return buffer.toString();
	}
	
	public static boolean checkNull(Object objectstr)
	{
		if(objectstr==null)return true;
		if(objectstr.toString().length()==0)return true;
		if(objectstr.toString().equals(""))return true;
		if(objectstr.toString().trim().toLowerCase().equals("null"))return true;
		return false;
	}

	/**
	 * 如果小于10，则前面加上0
	 * @param num
	 * @return
	 */
	public static String getStrNumWithZero(int num)
	{
		if(num>=0&&num<10)return "0"+num;
		return String.valueOf(num);
	}
	
	public static String getStrNum(int num)
	{
		return String.valueOf(num);
	}
	
	public static String getStr(String str, int maxlength){
		return str.length()>maxlength?str.substring(0,maxlength):str;
	}
	
	/**
	 * 对buffer增加新的字符串和分隔符(如果这个串不为空的话)
	 * @param buffer  待添加的buffer
	 * @param addStr 添加入的str
	 * @param splitSign 添加完成后结尾的分隔符
	 */
	public static void AppendNotEmptyStr(StringBuffer buffer, String addStr, String splitSign) {
		if (addStr != null && addStr.length() != 0 && !addStr.equals("")) {
			buffer.append(addStr + splitSign);
		}
	}

	/**
	 * 去除结尾的某个字符串
	 * @param buffer
	 * @param splitSign
	 */
	public static  void removeLastSplitSign(StringBuffer buffer, String splitSign) {
		if (buffer.length() != 0 && buffer.lastIndexOf(splitSign) == buffer.length() - splitSign.length()) {
			buffer = buffer.replace(buffer.length() - splitSign.length(), buffer.length(), "");
		}
	}
	
	
	
	public static String getVerticalHTMLStr(String str, boolean isRed){
		char[]textChar=str.toCharArray();
		String t="<font color='#ffffff'>";
		for (int i = 0; i < textChar.length; i++) {
			t+=textChar[i]+"<br/>";
			if(isNumICON(textChar[i])){
				t+="</font>";
				t=t.replace("<br/>"+textChar[i]+"", "");
				if(isRed)t+="<font color='#ffa0a0'>"+textChar[i]+"</font>";
				else t+="<font color='#bff1ff'>"+textChar[i]+"</font>";
			}
		}
		if(t.indexOf("</font>")==-1)t+="</font>";
		return t;
	}
	
	
	
	private static boolean isNumICON(char c){
		char[] charCount={'①','②','③','④','⑤','⑥','⑦','⑧','⑨','⑩'};
		for (int i = 0; i < charCount.length; i++) {
			if(c==charCount[i])return true;
		}
		return false;
	}
	
	//由于修改了字体文件ttf,对应的索引文件也有所变化,所以需要此方法进行转义
	public static String replaceFontStr(String str){
		str=str.replace('1', '①');
		str=str.replace('2', '②');
		str=str.replace('3', '③');
		str=str.replace('4', '④');
		str=str.replace('5', '⑤');
		str=str.replace('6', '⑥');
		str=str.replace('7', '⑦');
		str=str.replace('8', '⑧');
		str=str.replace('9', '⑨');
		str=str.replace('0', '⑩');
		str=str.replace(':', ';');
		str=str.replace('胜', '{');
		str=str.replace('平', '|');
		str=str.replace('负', '}');
		return str;
	}
	
	//验证购彩大厅的列表显示格式正确性
	public static boolean checkGcHallStr(String gcHallStr){
		if(checkNull(gcHallStr))return false;
		if (gcHallStr.indexOf("1:") == -1 || gcHallStr.indexOf("2:") == -1 || gcHallStr.indexOf("@") == -1)return false;
		return true;
	}
	//验证开奖大厅列表显示格式正确性
	public static boolean checkKaiJiangStr(String kaiJiangStr){
		String str = kaiJiangStr;
		if(checkNull(str)){
			return false;
		}else if (str.indexOf("1:") == -1 || str.indexOf("2:") == -1 || str.indexOf("@") == -1 || str.indexOf("3:") == -1){
			return false;
		}else{
			int count=0;
			while(str.indexOf("@")!=-1){
				count++;  
				str = str.substring(str.indexOf("@")+1);
			}
			if(count != 2){
				return false;
			}else{
				return true;
			}
		}
	}
	
	public static String showMoney(String money){
		DecimalFormat df = new DecimalFormat("0.00");
		return (df.format(Double.parseDouble(money)));
	}
	
	public static String showDxds(int num) {
		String type = "";
		String nums = String.valueOf(num);
		if ("56789".contains(nums)) {
			type = "大";
		} else {
			type = "小";
		}
		if ("02468".contains(nums)) {
			type += "双";
		} else {
			type += "单";
		}
		return type;
	}

	/** 
	 * 去除字符串中所有空格 
	 *  
	 * @param s 
	 *            字符串 
	 * @return 字符串 
	 *  
	 * 注意：String类的trim()只可以去除两端的空格 
	 * */
	public static String removeAllSpace(String s) {

		String endString = "";
		StringBuilder builder = new StringBuilder(endString);
		int len = s.length();  
		for (int i = 0; i <len ; i++) {  
			// 获得字符   
			char c = s.charAt(i);  
			// 如果该字符不是空格   
			if (c != SPACE&&c!= ENTER&&c!=CHANGRLINE) {  
				builder.append(String.valueOf(c));
			}  
		}  
		return builder.toString();  
	}  
	
	public static int getNums(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			if ((s.charAt(i) <= '9' && s.charAt(i) >= '0')) {
				str += s.substring(i, i + 1);
			}
		}
		if (str.equals("")) {
			return 0;
		}
		int d = 0;
		try {
			d = Integer.parseInt(str);
		} catch (Exception e) {
		}
		return d;
	}
	
	public static String ms2FormatDate(long ms){
		String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat mDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
		return mDateFormat.format(ms);
	}
	

	/**
	 * 以splitchar符号分割字符串txt
	 * 
	 * @param txt
	 * @param splitchar
	 * @return vector
	 */
	public static Vector SplitString(String txt, String splitchar) {
		Vector v = new Vector();
		while (txt != null && txt.length() > 0) {
			int index = txt.indexOf(splitchar);
			if (index >= 0) {
				String str = getSubString(txt, 0, index);
				v.addElement(str);
				txt = txt.substring(index + splitchar.length());
			} else {
				break;
			}
		}
		if (txt != null && txt.length() > 0) {
			v.addElement(txt);
		}
		return v;
	}

	/**
	 * 将字符串str中的oldStr用newStr替换
	 * 
	 * @param str
	 * @param oldStr
	 * @param newStr
	 * @return String
	 */
	public static String replace(String str, String oldStr, String newStr) {
		int startIndex = 0;
		int index = str.indexOf(oldStr, startIndex);
		if (index != -1) {
			StringBuffer buf = new StringBuffer();
			while (index >= 0) {
				if (index > startIndex)
					buf.append(getSubString(str, startIndex, index));
				buf.append(newStr);
				startIndex = index + oldStr.length();
				index = str.indexOf(oldStr, startIndex);
			}
			if (startIndex <= str.length() - 1)
				buf.append(getSubString(str, startIndex, str.length()));
			return buf.toString();
		}
		return str;
	}


	/**将投注号码字符拆分成数字数组
	 * @param lotteryNum
	 * @param splitNum
	 * @return luo
	 */
	public static ArrayList<String> StringToNumList(String lotteryNum, int splitNum, String splitchar, String playWay) {
		ArrayList<String> ve = new ArrayList<String>();

		StringBuffer numberBuffer = new StringBuffer();
		
		if(playWay != null && playWay.equals("大小单双")){
			Pattern reg = Pattern.compile("\\d+");//如果大小单双是数字显示，则如下处理
			Matcher matcher = reg.matcher(lotteryNum);
			while (matcher.find())
				numberBuffer.append(matcher.group());

			int size = numberBuffer.toString().length();
			for(int i=0;i<size;i=i+splitNum){
				String subStr = getSubString(numberBuffer.toString(), i, i+splitNum);
				if(subStr.equals("0"))
					subStr = "小";
				else if(subStr.equals("1"))
					subStr = "单";
				else if(subStr.equals("2"))
					subStr = "双";
				else if(subStr.equals("9"))
					subStr = "大";
				ve.add(subStr);
			}

			if(ve.size() == 0){//如果大小单双是中文显示，则如下处理
				ve.addAll(SplitString(lotteryNum, ","));
			}

			return ve;
		}

		if(splitchar != null){
			int plusIndex = lotteryNum.indexOf(splitchar);
			if(plusIndex != -1){
				ArrayList<String> ve1 = new ArrayList<String>();

				String redLotteryNum = lotteryNum.substring(0, plusIndex);
				ArrayList<String> v = StringToNumList(redLotteryNum, splitNum, null, playWay);
//				PalLog.d(TAG, "is repeated?");
				ve1.addAll(v);
				String blueLotteryNum = lotteryNum.substring(plusIndex+1);
				ve1.addAll(StringToNumList(blueLotteryNum, splitNum, null, playWay));

				ve1.add(String.valueOf(v.size()));//获取绿球的位置
				return ve1;
			}
		}else {

			Pattern reg = Pattern.compile("\\d+");
			Matcher matcher = reg.matcher(lotteryNum);

			while (matcher.find())
				numberBuffer.append(matcher.group());

			int size = numberBuffer.toString().length();
			for(int i=0;i<size;i=i+splitNum){
				String subStr = getSubString(numberBuffer.toString(), i, i+splitNum);
				ve.add(subStr);
			}
		}

		return ve;
	}
	
	/**将投注号码字符拆分成数字数组
	 * @param lotteryNum
	 * @param splitNum
	 * @return luo
	 */
	public static ArrayList<String> StringToNumListNew(String lotteryNum, int splitNum, String splitchar, String playWay) {
		ArrayList<String> ve = new ArrayList<String>();

		StringBuffer numberBuffer = new StringBuffer();
		
		if(playWay != null && playWay.equals("大小单双")){
			Pattern reg = Pattern.compile("[\\d+\\|]");//如果大小单双是数字显示，则如下处理
			Matcher matcher = reg.matcher(lotteryNum);
			while (matcher.find())
				numberBuffer.append(matcher.group());

			int size = numberBuffer.toString().length();
			for(int i=0;i<size;i=i+splitNum){
				String subStr = getSubString(numberBuffer.toString(), i, i+splitNum);
				if(subStr.equals("0"))
					subStr = "小";
				else if(subStr.equals("1"))
					subStr = "单";
				else if(subStr.equals("2"))
					subStr = "双";
				else if(subStr.equals("9"))
					subStr = "大";
				else if(subStr.equals("|"))
					subStr = "|";
				ve.add(subStr);
			}

			if(ve.size() == 0){//如果大小单双是中文显示，则如下处理
				ve.addAll(SplitString(lotteryNum, ","));
			}

			return ve;
		}

		if(splitchar != null){
			int plusIndex = lotteryNum.indexOf(splitchar);
			if(plusIndex != -1){
				ArrayList<String> ve1 = new ArrayList<String>();

				String redLotteryNum = lotteryNum.substring(0, plusIndex);
				ArrayList<String> v = StringToNumListNew(redLotteryNum, splitNum, null, playWay);
//				PalLog.d(TAG, "is repeated?");
				ve1.addAll(v);
				String blueLotteryNum = lotteryNum.substring(plusIndex+1);
				ve1.addAll(StringToNumListNew(blueLotteryNum, splitNum, null, playWay));

				ve1.add(String.valueOf(v.size()));//获取绿球的位置
				return ve1;
			}
		}else {

			Pattern reg = Pattern.compile("[\\d+\\|]");
			Matcher matcher = reg.matcher(lotteryNum);

			while (matcher.find()){
				if(matcher.group().equals("|")){
					numberBuffer.append("|");
					for(int i = 0;i<splitNum-1;i++){
						numberBuffer.append(" ");	
					}
				}else
					numberBuffer.append(matcher.group());
			}

			int size = numberBuffer.toString().length();
			for(int i=0;i<size;i=i+splitNum){
				String subStr = getSubString(numberBuffer.toString(), i, i+splitNum);
				ve.add(subStr);
			}
		}

		return ve;
	}
	
	//获取快3的投注号码
	public static ArrayList<String> getKuaiSanBetNum(String betNum, String playWay){
		ArrayList<String> list = new ArrayList<String>();
		if(betNum.contains("-")){
			betNum = betNum.substring(0, betNum.indexOf("-"));
		}
		
			if(betNum.contains("|")){
				String[] numarr = betNum.split("\\|");
				list.addAll(Arrays.asList(numarr[0].split("\\,")));
				list.add("|");
				list.addAll(Arrays.asList(numarr[1].split("\\,")));
		}else
			list.addAll(Arrays.asList(betNum.split("\\,")));
		return list;
	}

	public static ArrayList<String> getPaisanHezhiBetBum(String betNum){
		ArrayList<String> list = new ArrayList<String>();
		if(betNum.contains("-")){
			betNum = betNum.substring(0, betNum.indexOf("-"));
		}
		list.add(betNum);
		return list;
	}
	public static ArrayList<String> getHappyTenCaiBetBum(String betNum){
		ArrayList<String> list = new ArrayList<String>();
		if(betNum.contains("00")){
			betNum = "全数";
		}else if(betNum.contains("-")){
			betNum = Integer.parseInt(betNum.substring(0, betNum.indexOf("-")))+"";
		}
		list.add(betNum);
		return list;
	}
	
	/**
	 * 取出str中索引startIndex到stopIndex-1的字符串
	 * 
	 * @param str
	 * @param startIndex
	 * @param stopIndex
	 * @return
	 */
	public static String getSubString(String str, int startIndex, int stopIndex) {
		if (str == null)
			return "";
		if (startIndex > str.length())
			return "";
		if (stopIndex > str.length())
			return str.substring(startIndex);
		return str.substring(startIndex, stopIndex);
	}

	/**
     * 将InputStream转换成String
     * @param in InputStream
     * @return String
     * @throws Exception
     *
     */
    public static String InputStreamToString(InputStream in) throws Exception {

        int BUFFER_SIZE = 4096;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);
        data = null;
        return new String(outStream.toByteArray(),"UTF-8");
    }

    public static String replacefaxq(String spanStr){
        String[] https = StringUtil.getFaxqLink(spanStr);
        String[] id = StringUtil.getID(spanStr);
        for(int i =0;i<https.length;i++){
            if(!TextUtils.isEmpty(https[i]))
                spanStr = spanStr.replace(https[i], "<a href="+"\'"+"faxq://"+id[i]+"\'"+"><font color='blue'>" + "方案详情" + "</font></a>");
        }
        return spanStr;
    }


    /**
     * 截取方案详情的链接
     * @param s
     * @return
     */
    public static String[]  getFaxqLink(String s) {
        String b = "http://caipiao365.com/faxq=";
        int count = getOccur(s, b);//记录"="出现的次数
        int[] equalsPosition = new int[count];//存储等号的所有下标位置
        int[] blankPosition = new int[count];//存储等号后面第一个空格的所有下标位置
        int sLength = s.length();
        for(int i = 1; i < count+1; i++){
            int n = getCharacterPosition(s,b,i);
            equalsPosition[i-1] = n;
            //获得等号后的第一个空格的位置
            for(int j = n; j < sLength; j++){
                if(s.charAt(j) == ' '){
                    blankPosition[i-1] = j;
                    break;
                }
            }
        }
        String[] allString = new String[count];
        //截取字符串
        for(int i = 0; i < count; i++){
            if(blankPosition[i] > equalsPosition[i]){
                String childString = s.substring(equalsPosition[i],blankPosition[i]);
                allString[i] = childString;
            }
        }
        return allString;
    }

    /**
     *
     * 截去方案详情ID号
     * @param s
     * @return
     */
    public static String[]  getID(String s) {
        String b = "http://caipiao365.com/faxq=";
        int count = getOccur(s, b);//记录"="出现的次数
        int[] equalsPosition = new int[count];//存储等号的所有下标位置
        int[] blankPosition = new int[count];//存储等号后面第一个空格的所有下标位置
        int sLength = s.length();
        for(int i = 1; i < count+1; i++){
            int n = getCharacterPosition(s,"=",i);
            equalsPosition[i-1] = n;
            //获得等号后的第一个空格的位置
            for(int j = n; j < sLength; j++){
                if(s.charAt(j) == ' '){
                    blankPosition[i-1] = j;
                    break;
                }
            }
        }
        String[] allString = new String[count];
        //截取字符串
        for(int i = 0; i < count; i++){
            if(blankPosition[i] > equalsPosition[i]+1){
                String childString = s.substring(equalsPosition[i]+1,blankPosition[i]);
                allString[i] = childString;
            }
        }
        return allString;
    }

    //获取字符串第N次出现的位置
    public static int getCharacterPosition(String string, String sub, int n){
        //这里是获取"="符号的位置
        Matcher slashMatcher = Pattern.compile(sub).matcher(string);
        int mIdx = 0;
        while(slashMatcher.find()) {
            mIdx++;
            //当"="符号第二次出现的位置
            if(mIdx == n){
                break;
            }
        }
        return slashMatcher.start();
    }

    /**
     * 获取字符串中某个子字符串的个数
     * @param src
     * @param find
     * @return
     */
    public static int getOccur(String src, String find){
        int o = 0;
        int index=-1;
        while((index=src.indexOf(find,index))>-1){
            ++index;
            ++o;
        }
        return o;
    }

	//在字符串sr中搜索字符串ar出现的次数方法
	public static ArrayList<Integer> search(String sr, String ar){
		int temp = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		while((sr.length()-temp) >= ar.length()){
			int  num = sr.indexOf(ar,temp);              //在字符串sr中，从temp个开始搜索ar，返回ar第一次出现的位置
			if(num == -1){
				temp = sr.length();
			}
			else{
				list.add(num);
				temp = num + ar.length();
			}
		}
		return list;
	}
    public static String[] getDateTime(String mSystemtime){
        String[] dates = new String[2];
        try {
            String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六"};
            String[]times=mSystemtime.split(" ");
            String[]datastr=times[0].split("-");
            Date dt = new Date(Integer.parseInt(datastr[0])-1900, Integer.parseInt(datastr[1])-1, Integer.parseInt(datastr[2])); // 新建一个日期
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0) {
                w = 0;
            }
            String week = weekDays[w];
            dates[0] = times[0];
            dates[1] = week;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }



	public static String changeWeekstoNum(String week){
		if(week.contains("周一")){
			week = StringUtil.replace(week,"周一","1");
		}else if(week.contains("周二")){
			week = StringUtil.replace(week,"周二","2");
		}else if(week.contains("周三")){
			week = StringUtil.replace(week,"周三","3");
		}else if(week.contains("周四")){
			week = StringUtil.replace(week,"周四","4");
		}else if(week.contains("周五")){
			week = StringUtil.replace(week,"周五","5");
		}else if(week.contains("周六")){
			week = StringUtil.replace(week,"周六","6");
		}else if(week.contains("周日")){
			week = StringUtil.replace(week,"周日","7");
		}
		return week;
	}

	//设置textview 前后 字体不一样
	public static Spannable showDiffSizeString(String allStr, String changedStr, String color, int textSp){
		String before = "";
		String after = "";
		if (allStr.contains(changedStr)) {
			if (allStr.endsWith(changedStr)) {
				before = allStr.replace(changedStr, "");
			} else if (allStr.startsWith(changedStr)) {
				after = allStr.replace(changedStr, "");
			} else {
				before = allStr.split(changedStr)[0];
				after = allStr.split(changedStr)[1];
			}
			return new HtmlFontUtil().toHtmlFormat(before + new HtmlFontUtil().getHtmlStr(color, (int) (UnioncApp.getContext().getResources().getDisplayMetrics().scaledDensity*textSp), changedStr) + after);
		}else{
			return new HtmlFontUtil().toHtmlFormat(allStr);
		}
	}
	//09月01日 10:12
	public static String showMonthDayTM(String str){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfMD = new SimpleDateFormat("MM月dd日 HH:mm");
		Date data = null;
		try {
			data = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		return sdfMD.format(data);
	}

	//去掉10.0后边的0
	public static String getPriceDisplayStr(String data){
		String result = "";
		if (!checkNull(data)) {
			double d = Double.parseDouble(data);
//		int consult = (int) (Math.round(d*100)) / 100;
//		int remainder =(int) (Math.round(d*100)) % 100;
//		if (remainder > 0){
//			if (remainder == 10){
//				result = consult + "." + "1";
//			}else if (remainder > 10){
//				result = consult + "." + remainder;
//			}else{
//				result = consult + "." + "0" + remainder;
//			}
//		}else{
//			result = consult + "";
//		}
			DecimalFormat df = new DecimalFormat("####0.##");
			result = df.format(d);
		}
		return result;
	}

	//保留两位小数
	public static String getKeeTwoDecimalplaces(String money){
		if (!checkNull(money)) {
			if (money.endsWith("元")) {
				money = money.substring(0, money.length() - 1);
			}
			Double double_value = Double.parseDouble(money);
			DecimalFormat df = new DecimalFormat("##0.00");
			return df.format(double_value);
		}else{
			return "";
		}
	}

	public static void setPricePoint(final EditText editText) {
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
				if (s.toString().contains(".")) {
					if (s.length() - 1 - s.toString().indexOf(".") > 2) {
						s = s.toString().subSequence(0,
								s.toString().indexOf(".") + 3);
						editText.setText(s);
						editText.setSelection(s.length());
					}
				}
				if (s.toString().trim().substring(0).equals(".")) {
					s = "0" + s;
					editText.setText(s);
					editText.setSelection(2);
				}

				if (s.toString().startsWith("0")
						&& s.toString().trim().length() > 1) {
					if (!s.toString().substring(1, 2).equals(".")) {
						editText.setText(s.subSequence(0, 1));
						editText.setSelection(1);
						return;
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

		});
	}

	public static String getDateyyyyMMdd(String date){
		String newDate = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date endDate1 = format.parse(date);
			newDate = format1.format(endDate1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

	public static String readTextFromStream(InputStream is) throws Exception {
		InputStreamReader reader = new InputStreamReader(is);
		BufferedReader bufferedReader = new BufferedReader(reader);
		StringBuffer buffer = new StringBuffer("");
		String str;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
			buffer.append("\n");
		}
		return buffer.toString();
	}

	//校验港澳台身份证是否合格
	public static boolean verifyHongkongIdentity(String identityCardId)
	{
		Pattern regexcardHongKong = Pattern.compile(isIdCardHongKong);
		Matcher matchercardHongKong= regexcardHongKong.matcher(identityCardId);
		return matchercardHongKong.matches();
//		if(identityCardId.length()==10)
//		{
//			if (matchercardHongKong.matches()) {
//				return true;
//			} else {
//				return false;
//			}
//		}else{
//			return false;
//		}
	}

	//校验港澳台身份证是否合格
	public static boolean verifyMacaoIdentity(String identityCardId)
	{
		Pattern regexcardHongKong = Pattern.compile(isIdCardMacao);
		Matcher matchercardHongKong= regexcardHongKong.matcher(identityCardId);
		return matchercardHongKong.matches();
	}

	//校验港澳台身份证是否合格
	public static boolean verifyTaiwanIdentity(String identityCardId)
	{
		Pattern regexcardHongKong = Pattern.compile(isIdCardTaiwan);
		Matcher matchercardHongKong= regexcardHongKong.matcher(identityCardId);
		return matchercardHongKong.matches();
	}
	//套餐价格“元”分割前后字体颜色不同
	public static Spannable showDiffSizeStringNew(String str, String textColor, int textSp){
		String befor = str;
		String after = " ";
		if(str.contains("元/")){
			befor = str.split("元/")[0];
			after = "元/"+ str.split("元/")[1];
			return new HtmlFontUtil().toHtmlFormat(befor + new HtmlFontUtil().getHtmlStr(textColor, (int) (UnioncApp.getContext().getResources().getDisplayMetrics().scaledDensity*textSp), after));
		}
		else{
			return new HtmlFontUtil().toHtmlFormat(befor);
		}
	}

	//判断金额是不是为0 返回true 就是 0  false 不为0
	public static boolean isZero(String money){
		if (!TextUtils.isEmpty(money)){
			if (money.equals("0") || money.equals("0.0") || money.equals("0.00") || money.equals(".0") || money.equals(".00")){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}

	public static String getWeekTitle(Date date){
		String week = "";
		try {
			String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五","周六"};
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (w < 0) {
				w = 0;
			}
			week = weekDays[w];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return week;
	}

	public static String confineTrueName(String str) throws PatternSyntaxException {
		// 只允许字母、和空格和汉字
		if (!checkNull(str)) {
			String endStr = "";
			endStr = str.substring(str.length() - 1, str.length());
			return endStr.matches(trueNamePattern) ? str : str.substring(0,str.length() - 1);
		}
		return "";
	}
}
