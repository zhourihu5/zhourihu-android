package com.sinoglobal.app.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import com.sinoglobal.app.activity.FlyApplication;
import com.aims.mia.R;

/**
 * @author ty
 * @createdate 2013-5-17 上午11:02:38
 * @Description: 校验工具类
 */
public class ValidUtil {

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-9-21 下午2:12:34
	 * @Description: 校验手机号码
	 * @return
	 */
	public static String validPhone(String phone) {
		String message = "";
		if (TextUtil.stringIsNull(phone.replace(" ", ""))) {
			message = "请输入手机号码！";
		} else if (phone.length() < 11) {
			message = "请输入11位手机号码！";
		} else {
			/*
			 * if("14527060199".equals(phone)){ //测试所用 return message; }
			 */
			// String phoneRule = "^[1]+[3,5,8]+[0-9]{9}";
			// String phoneRule =
			// "^((14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
			String phoneRule = "^((14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
			Pattern p = Pattern.compile(phoneRule);
			Matcher match = p.matcher(phone);
			if (!match.matches()) {
				message = "请输入正确的手机号码！";
			}
		}
		return message;
	}
	public static String validFlightNo(String flightNo) {
		String message = "";
		if (TextUtil.stringIsNull(flightNo)) {
			message =FlyApplication.context.getString(R.string.not_valid_flight_number);
		} else {
			/*
			 * if("14527060199".equals(phone)){ //测试所用 return message; }
			 */
			// String phoneRule = "^[1]+[3,5,8]+[0-9]{9}";
			// String phoneRule =
			// "^((14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
			String phoneRule = "^\\w{2}[0-9]{3,4}+$";
			Pattern p = Pattern.compile(phoneRule);
			Matcher match = p.matcher(flightNo);
			if (!match.matches()) {
				message =FlyApplication.context.getString(R.string.not_valid_flight_number);
			}
		}
		return message;
	}

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-9-21 下午2:16:47
	 * @Description: 校验密码
	 * @return
	 */
	public static String validPassword(String pwd) {
		String message = "";
		if (pwd.contains(" ")) {
			message = "密码中不能含有空格！";
			return message;
		}
		if (TextUtil.stringIsNull(pwd)) {
			message = "请输入密码！";
		} else if (pwd.length() < 6) {
			message = "请输入6-20位密码！";
		} else if (pwd.length() > 20) {
			message = "请输入6-20位密码！";
		}
		return message;
	}

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-9-21 下午8:53:12
	 * @Description: 校验验证码
	 * @param checkCode
	 * @return
	 */
	public static String validCheckCode(String checkCode) {
		String message = "";
		if (TextUtil.stringIsNull(checkCode)) {
			message = "请输入验证码！";
		} else if (checkCode.length() < 4) {
			message = "验证码请输入4位有效数字！";
		}
		return message;
	}

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-9-23 下午1:11:27
	 * @Description: 校验邮箱
	 * @param email
	 * @return
	 */
	public static String validEmail(String email) {
		String message = "";
		String emailRule = "^\\w+@\\w+\\.\\w+$";
		Matcher match = Pattern.compile(emailRule).matcher(email);
		if (!match.matches()) {
			message = "请输入正确的邮箱！";
		}
		return message;
	}
	

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-9-23 下午1:43:56
	 * @Description: 校验姓名 中文，英文，中英文，中文少两个字符，纯英文 至少三个字符
	 * @param name
	 * @return
	 */
	public static String validName(String name) {
		String message = "";
		if (name.contains(" ")) {
			message = "联系人中不能含有空格！";
			return message;
		}
		String englishNameRule = "^[A-Za-z]{3,}|[\u4e00-\u9fa5]{1,}[A-Za-z]+$";
		String chineseNameRule = "^[\u4e00-\u9fa5]{2,}$";
		Matcher mat = Pattern.compile(englishNameRule).matcher(name.replace(" ", ""));
		Matcher match = Pattern.compile(chineseNameRule).matcher(name.replace(" ", ""));
		if (TextUtil.stringIsNotNull(name)) {
			if (!mat.matches() && !match.matches()) {
				message = "请输入真实的联系人姓名！";
			} else if (match.matches() && name.length() > 30) {
				message = "联系人姓名最长30个汉字！";
			} else if (mat.matches() && name.length() > 60) {
				message = "联系人姓名最长60个字符！";
			}
		} else {
			message = "请填写联系人姓名！";
		}
		return message;
	}

	/**
	 * @author ty
	 * @createdate 2012-11-15 下午2:22:42
	 * @Description: 邮寄地址联系人姓名校验
	 * @param name
	 * @return
	 */
	public static String validPostName(String name) {
		String message = "";
		if (name.contains(" ")) {
			message = "收件人姓名中不能含有空格！";
			return message;
		}
		String englishNameRule = "[A-Za-z]{2,}|[\u4e00-\u9fa5]{1,}[A-Za-z]+$";
		String chineseNameRule = "^[\u4e00-\u9fa5]{2,}$";
		Matcher mat = Pattern.compile(englishNameRule).matcher(name.replace(" ", ""));
		Matcher match = Pattern.compile(chineseNameRule).matcher(name.replace(" ", ""));
		if (TextUtil.stringIsNotNull(name)) {
			if (!mat.matches() && !match.matches()) {
				message = "请输入正确的收件人姓名！";
			} else if (mat.matches() && !match.matches()) {
				if (name.replace("/", "").trim().length() < 3) {
					message = "英文字母位数至少为三位！";
				}
			}
		} else {
			message = "请输入姓名！";
		}
		return message;
	}

	/**
	 * @author ty
	 * @createdate 2012-11-15 下午2:13:55
	 * @Description: 乘机人姓名校验
	 * @param name
	 * @return
	 */
	public static String validPassengersName(String name) {
		String message = "";
		if (name.contains(" ")) {
			message = "乘机人姓名中不能含有空格！";
			return message;
		}
		String chineseNameRule = "^[\u4e00-\u9fa5]{2,}$";
//		String englishNameRule = "[A-Za-z]{2,}|[\u4e00-\u9fa5]{1,}[A-Za-z]+$";		//原版
		String englishNameRule = "[A-Za-z]{2,}[/][A-Za-z]+$";
		Matcher mat = Pattern.compile(englishNameRule).matcher(name.replace(" ", ""));
		Matcher match = Pattern.compile(chineseNameRule).matcher(name.replace(" ", ""));
		if (TextUtil.stringIsNotNull(name)) {
			if (match.matches() || mat.matches()) {
				int count = 0;
				String regEx = "[\\u4e00-\\u9fa5]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(name.replace(" ", ""));
				while (m.find()) {
					for (int i = 0; i <= m.groupCount(); i++) {
						count = count + 1;
					}
				}
				if (count > 11) {
					message = "乘机人姓名不能超过11个汉字！";
				}
			}
			if (!mat.matches() && !match.matches()) {
				message = "请输入正确的乘机人姓名！";
			} else if (mat.matches() && !match.matches()) {
				if (name.replace("/", "").length() < 3) {
					message = "英文字母位数至少为三位！";
				}
			}
		} else {
			message = "请填写乘机人姓名！";
		}
		return message;
	}

	public static String validUserIdCodeChild(String birthday) {
		String message = "";
		if (TextUtil.stringIsNull(birthday)) {
			message = "请选择出生日期！";
			return message;
		}
		return message;
	}
		/**
		 * 如果是成人判断身份证号是否比去程大于2年
		 * @param selectDay
		 * @param birthday
		 * @return
		 */
	public static String validUserIdCodePerson(String selectDay,String birthday) {
		System.out.println(selectDay+"    "+ birthday);
		String nian = birthday.substring(6, 10);
		String yue = birthday.substring(10,12);
		String ri = birthday.substring(12,14);
		birthday = ""+nian+"-"+yue+"-"+ri;
		String message = "";
		if (TextUtil.stringIsNull(birthday)) {
			message = "请选择出生日期！";
			return message;
		}
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
//		String currTime = dateFormat.format(new Date()); 
		
		//将当前时间yyyy-MM-dd HH:mm格式转换成yyyy-MM-dd格式
		String date = TimeUtil.parseDateToString(TimeUtil.sdf1, TimeUtil.parseDate(TimeUtil.df, selectDay));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(TimeUtil.parseStringToDate(date));
		calendar.add(Calendar.YEAR, -2);//当前日期年+2年
		calendar.add(Calendar.DAY_OF_MONTH, -1); //起飞日期减一天
		String d11 = TimeUtil.parseDateToString(TimeUtil.sdf1, calendar.getTime());
		//两岁的时间节点
//		String d2 = TimeUtil.parseDateToString(TimeUtil.sdf1, TimeUtil.parseDate(TimeUtil.df, currTime));
//		calendar.add(Calendar.YEAR, -10);//当前日期年+12年
//		calendar.add(Calendar.DAY_OF_MONTH, -1); //起飞日期减一天
//		//12岁的时间节点
//		String d12 = TimeUtil.parseDateToString(TimeUtil.sdf1, calendar.getTime());
		//儿童的生日
		Date dd = TimeUtil.parseDate(TimeUtil.sdf1, birthday);
		Date dd2 = TimeUtil.parseDate(TimeUtil.sdf1, d11);//2
//		Date dd12 = TimeUtil.parseDate(TimeUtil.sdf1, d12);//12
//		if(dd.before(dd12)){
//			message ="乘客不符合航空公司购买儿童票的标准，请编辑该乘机人类型为成人后再预订机票！";
//		}
		if(dd.after(dd2)){
			message ="乘客不符合航空公司购买儿童票的标准，如果需要购买婴儿票请拨打预订热线400-818-9008.";
		}
		return message;
	}
		
	public static String validUserIdCodeChild1(String selectDay,String birthday) {
		String message = "";
		if (TextUtil.stringIsNull(birthday)) {
			message = "请选择出生日期！";
			return message;
		}
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
//		String currTime = dateFormat.format(new Date()); 
		
		//将当前时间yyyy-MM-dd HH:mm格式转换成yyyy-MM-dd格式
		String date = TimeUtil.parseDateToString(TimeUtil.sdf1, TimeUtil.parseDate(TimeUtil.df, selectDay));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(TimeUtil.parseStringToDate(date));
		calendar.add(Calendar.YEAR, -2);//当前日期年+2年
//		calendar.add(Calendar.DAY_OF_MONTH, -1); //起飞日期减一天
		String d11 = TimeUtil.parseDateToString(TimeUtil.sdf1, calendar.getTime());
		//两岁的时间节点
//		String d2 = TimeUtil.parseDateToString(TimeUtil.sdf1, TimeUtil.parseDate(TimeUtil.df, currTime));
		calendar.add(Calendar.YEAR, -10);//当前日期年+12年
		calendar.add(Calendar.DAY_OF_MONTH, +1); //起飞日期减一天
		//12岁的时间节点
		String d12 = TimeUtil.parseDateToString(TimeUtil.sdf1, calendar.getTime());
		//儿童的生日
		Date dd = TimeUtil.parseDate(TimeUtil.sdf1, birthday);
		Date dd2 = TimeUtil.parseDate(TimeUtil.sdf1, d11);//2
		Date dd12 = TimeUtil.parseDate(TimeUtil.sdf1, d12);//12
		if(dd.before(dd12)){
			message ="乘客不符合航空公司购买儿童票的标准，请编辑该乘机人类型为成人后再预订机票！";
		}else if(dd.after(dd2)){
			message ="乘客不符合航空公司购买儿童票的标准，如果需要购买婴儿票请拨打预订热线400-818-9008.";
		}
		return message;
	}
/**
 * 验证身份证是否是
 * @param userIdCode
 * @return
 */
	public static String validUserLong(String userIdCode){
		String message = "";
		String subNumber = userIdCode.substring(6, 10);
		int parseInt = Integer.parseInt(subNumber);
		if (parseInt > 2001) {
			message = "儿童请选择儿童";
		}
		return message;
	}
	/**
	 * @author miaoxin.ye
	 * @createdate 2012-9-23 下午3:33:38
	 * @Description: 校验身份证号码
	 * @param userIdCode
	 * @return
	 */
	public static String validUserIdCode(String userIdCode) {
		String message = "";
		if (userIdCode.contains(" ")) {
			message = "证件号码中不能含有空格！";
			return message;
		}
		if (userIdCode.contains(" ")) {
			message = "证件号码中不能含有空格！";
			return message;
		}
		String rule;
		if (TextUtil.stringIsNull(userIdCode)) {
			message = "请填写证件号码！";
			return message;
		}
		String subId = "";
		if (userIdCode.length() != 18) {
			message = "请输入正确的身份证号码！";
			return message;
		} else {
			rule = "^\\d{17}(\\d|x|X)$";
			Matcher match = Pattern.compile(rule).matcher(userIdCode);
			if (!match.matches()) {
				message = "请输入正确的身份证号码！";
				return message;
			} else {
				subId = userIdCode.substring(0, 17);
				message = validDistricAndDate(message, subId);
				if (TextUtil.stringIsNotNull(message)) {
					return message;
				} else {
					// 校验码验证
					String[] valCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" }; // 验证码数组
					String[] wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" }; // 加权因子数组

					int TotalmulAiWi = 0;
					for (int i = 0; i < 17; i++) {
						TotalmulAiWi += Integer.parseInt(String.valueOf(subId.charAt(i))) * Integer.parseInt(wi[i]);
					}
					String strVerifyCode = valCodeArr[TotalmulAiWi % 11]; // 得到验证码
					subId += strVerifyCode;
					if (!subId.equals(userIdCode.toLowerCase())) {
						message = "请输入正确的身份证号码！";
						return message;
					}
				}
			}
		}
//		String subNumber = userIdCode.substring(6, 10);
//		int parseInt = Integer.parseInt(subNumber);
//		if (parseInt > 2001) {
//			message = "儿童请选择儿童";
//		}

		return message;
	}

	/**
	 * 
	 * @autor bo.wang
	 * @createdate 2012-12-6 下午03:38:24
	 * @Description 验证信用卡证件号码
	 */
	public static String validCardNumber(String userIdCode) {
		String message = "";
		String rule;
		if (TextUtil.stringIsNull(userIdCode)) {
			message = "请输入证件号码";
			return message;
		}

		if (userIdCode.length() != 15 && userIdCode.length() != 18) {
			message = "请输入正确的证件号码";
			return message;
		}
		String subId = "";
		if (userIdCode.length() == 15) {
			rule = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
			Matcher match = Pattern.compile(rule).matcher(userIdCode);
			if (!match.matches()) {
				message = "请输入正确的证件号码";
				return message;
			} else {
				subId = userIdCode.substring(0, 6) + "19" + userIdCode.substring(6, 15);
				message = validDistricAndDate(message, subId);
				return message;
			}
		}
		if (userIdCode.length() == 18) {
			rule = "^\\d{17}(\\d|x|X)$";
			Matcher match = Pattern.compile(rule).matcher(userIdCode);
			if (!match.matches()) {
				message = "请输入正确的证件号码";
				return message;
			} else {
				subId = userIdCode.substring(0, 17);
				message = validDistricAndDate(message, subId);
				if (TextUtil.stringIsNotNull(message)) {
					message = "请输入正确的证件号码";
					return message;
				} else {
					// 校验码验证
					String[] valCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" }; // 验证码数组
					String[] wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" }; // 加权因子数组

					int TotalmulAiWi = 0;
					for (int i = 0; i < 17; i++) {
						TotalmulAiWi += Integer.parseInt(String.valueOf(subId.charAt(i))) * Integer.parseInt(wi[i]);
					}
					String strVerifyCode = valCodeArr[TotalmulAiWi % 11]; // 得到验证码
					subId += strVerifyCode;
					if (!subId.equals(userIdCode.toLowerCase())) {
						message = "请输入正确的证件号码";
						return message;
					}
				}

			}
		}

		return message;
	}

	/**
	 * @author ty
	 * @createdate 2012-11-14 下午5:19:12
	 * @Description: 身份证号码中的地区和生日验证
	 * @param message
	 * @param subId
	 * @return
	 */
	private static String validDistricAndDate(String message, String subId) {
		if (subId.contains(" ")) {
			message = "证件号码中不能含有空格！";
			return message;
		}
		// 地区验证
		Map<String, String> map = GetAreaCode();
		if (map.get(subId.substring(0, 2)) == null) {
			message = "请输入正确的身份证号码！";
			return message;
		}
		// 日期验证
		String srtDate = subId.substring(6, 10) + "-" + subId.substring(10, 12) + "-" + subId.substring(12, 14);
		long time = TimeUtil.parseStringToLong(TimeUtil.sdf1, srtDate);
		Date date = new Date(time);
		String srtDate2 = TimeUtil.parseDateToString(TimeUtil.sdf1, date);
		if (!srtDate.equals(srtDate2)) {
			message = "请输入正确的身份证号码！";
			return message;

		}
		return message;
	}

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-9-27 下午3:34:17
	 * @Description: 校验邮编
	 * @param postcode
	 * @return
	 */
	public static String validPostcode(String postcode) {
		String message = "";
		if (TextUtil.stringIsNull(postcode)) {
			message = "请输入邮政编码！";
		} else if (postcode.length() != 6) {
			message = "请输入6位邮政编码！";
		}
		return message;
	}

	/**
	 * @author ty
	 * @createdate 2012-10-12 下午4:47:40
	 * @Description: 校验地址
	 * @param address
	 * @return
	 */
	public static String validAddress(String address) {
		String message = "";
		String rule;
		if (TextUtil.stringIsNotNull(address)) {
			// 地址只能输入汉字、字母和数字和标点符号
			rule = "^[(A-Za-z0-9)*(\u4e00-\u9fa5)*(,|\\.|，|。|\\:|;|：|；|!|！|\\*|\\×|\\(|\\)|\\（|\\）|#|#|\\$|&|\\^|@|＠|＆|\\￥|\\……)*]+$";
			Matcher match = Pattern.compile(rule).matcher(address);
			if (!match.matches()) {
				message = "邮寄地址只能输入汉字、字母、数字和标点符号！";
			}
		} else {
			message = "请输入邮寄地址！";
		}
		return message;
	}

	public static String validOther(String other) {
		String message = "";
		String rule;
		if (TextUtil.stringIsNotNull(other.replace(" ", ""))) {
			// 其他证件只能输入数字、字母和"-"
			rule = "^[(A-Za-z0-9)]*+(-)*+[(A-Za-z0-9)]*+$";
			Matcher match = Pattern.compile(rule).matcher(other);
			if (!match.matches()) {
				message = "只能输入数字、字母和'-'！";
			}
		} else {
			message = "请输入证件号码！";
		}
		return message;
	}

	/**
	 * @author ty
	 * @createdate 2012-11-15 上午10:06:29
	 * @Description:设置身份证号中前两位的地区编码
	 * @return
	 */
	private static Map<String, String> GetAreaCode() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("11", "北京");
		map.put("12", "天津");
		map.put("13", "河北");
		map.put("14", "山西");
		map.put("15", "内蒙古");
		map.put("21", "辽宁");
		map.put("22", "吉林");
		map.put("23", "黑龙江");
		map.put("31", "上海");
		map.put("32", "江苏");
		map.put("33", "浙江");
		map.put("34", "安徽");
		map.put("35", "福建");
		map.put("36", "江西");
		map.put("37", "山东");
		map.put("41", "河南");
		map.put("42", "湖北");
		map.put("43", "湖南");
		map.put("44", "广东");
		map.put("45", "广西");
		map.put("46", "海南");
		map.put("50", "重庆");
		map.put("51", "四川");
		map.put("52", "贵州");
		map.put("53", "云南");
		map.put("54", "西藏");
		map.put("61", "陕西");
		map.put("62", "甘肃");
		map.put("63", "青海");
		map.put("64", "宁夏");
		map.put("65", "新疆");
		map.put("71", "台湾");
		map.put("81", "香港");
		map.put("82", "澳门");
		map.put("91", "国外");
		return map;
	}

	public static String validTrainNumber(String trainNumber) {
		String message = "";
		String rule;
		if (TextUtil.stringIsNotNull(trainNumber)) {
			// 其他证件只能输入数字、字母
			rule = "^[(A-Za-z0-9)]+$";
			Matcher match = Pattern.compile(rule).matcher(trainNumber);
			if (!match.matches()) {
				message = "只能输入字母或数字！";
			}
		} else {
			message = "请输入车次！";
		}
		return message;
	}

	/**
	 * 判断号码是联通，移动，电信中的哪个, 在使用本方法前，请先验证号码的合法性 规则：
	 * 
	 * 中国移动拥有号码段为:139,138,137,136,135,134,147,159,158,157(3G),151,152,150,182(3G
	 * ),188(3G),187(3G);16个号段
	 * 中国联通拥有号码段为:130,131,132,145,155,156(3G),186(3G),185(3G);8个号段
	 * 中国电信拥有号码段为:133,1349,153,189(3G),180(3G);5个号码段
	 * 
	 * @param mobile要判断的号码
	 * @return 返回相应类型：1代表联通；2代表移动；3代表电信
	 */
	public static String getMobileType(String mobile) {

		if (mobile.startsWith("0") || mobile.startsWith("+860")) {
			mobile = mobile.substring(mobile.indexOf("0") + 1, mobile.length());
		}
		List chinaUnicom = Arrays.asList(new String[] { "130", "131", "132", "145", "155", "156", "186", "185" });
		List chinaMobile1 = Arrays.asList(new String[] { "135", "136", "137", "138", "139", "147", "150", "151", "152", "157", "158",
				"159", "182", "187", "188" });
		List chinaMobile2 = Arrays.asList(new String[] { "1340", "1341", "1342", "1343", "1344", "1345", "1346", "1347", "1348" });

		boolean bolChinaUnicom = (chinaUnicom.contains(mobile.substring(0, 3)));
		boolean bolChinaMobile1 = (chinaMobile1.contains(mobile.substring(0, 3)));
		boolean bolChinaMobile2 = (chinaMobile2.contains(mobile.substring(0, 4)));
		if (bolChinaUnicom)
			return "1";// 联通
		if (bolChinaMobile1 || bolChinaMobile2)
			return "2"; // 移动
		return "3"; // 其他为电信

	}
}
