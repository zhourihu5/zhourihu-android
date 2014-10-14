package com.sinoglobal.app.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

import com.aims.mia.R;


public class Common {
	// 手机号码正则表达式
	public static String MOBILE_NUMBER_REG = "^((13)|(15)|(18))\\d{9}";

	public static long getNextRunTimeInMills(int startHour, int endHour, int runMin, int intervalInHour) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);

		if (hour < startHour || (hour == startHour && min < runMin)) {
			calendar.set(Calendar.HOUR_OF_DAY, startHour);
			calendar.set(Calendar.MINUTE, runMin);
		} else if (hour == endHour && min < runMin) {
			calendar.set(Calendar.HOUR_OF_DAY, endHour);
			calendar.set(Calendar.MINUTE, runMin);
		} else if (hour > endHour || (hour == endHour && min > runMin)) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, startHour);
			calendar.set(Calendar.MINUTE, runMin);
		} else {
			for (int i = startHour; i < endHour; i += intervalInHour) {
				if (i > hour) {
					calendar.set(Calendar.HOUR_OF_DAY, i);
					calendar.set(Calendar.MINUTE, runMin);
					break;
				} else if (i == hour && min < runMin) {
					calendar.set(Calendar.MINUTE, runMin);
					break;
				}
			}
		}
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static String translateUriToJson(Context context, Uri uri) {
		String result = "";
		InputStream inputStream = null;
		ByteArrayOutputStream content = null;
		try {
			inputStream = context.getContentResolver().openInputStream(uri);
			content = new ByteArrayOutputStream();

			// Read response into a buffered stream
			int readBytes = 0;
			byte[] sBuffer = new byte[512];
			while ((readBytes = inputStream.read(sBuffer)) != -1) {
				content.write(sBuffer, 0, readBytes);
			}
			// Return result from buffered stream
			result = new String(content.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {

				}
			}

			if (content != null) {
				try {
					content.close();
				} catch (IOException e) {

				}
			}
		}
		return result;
	}

	public static boolean isUsingWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo.State wifiState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (wifiState == NetworkInfo.State.CONNECTED) {
			return true;
		}
		return false;
	}

	public static String getTimeStamp() {

		return System.currentTimeMillis() + "";
	}

	public static String getNetworkType(Context context) {
		String networkType = "unknown";

		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo.State wifiState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (wifiState == NetworkInfo.State.CONNECTED) {
			networkType = "wifi";
		} else {
			Cursor cr = null;
			try {
				// cr =
				// context.getContentResolver().query(Uri.parse(PREFER_APN_URI),
				// null, null, null, null);
				while (cr != null && cr.moveToNext()) {
					networkType = cr.getString(cr.getColumnIndex("apn"));
				}
			} finally {
				if (cr != null) {
					cr.close();
				}
			}

		}
		return networkType;
	}

	public static void hideSoftInput(Context context, IBinder windowToken) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(windowToken, 0);
	}

	// 判读是否存在快捷方式
	public static boolean hasShortcut(Context context) {
		PackageManager packageManager = context.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);

		List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		ResolveInfo info = null;
		String uri = null;

		// List<String> launcherPackageList =
		// Arrays.asList(LAUNCHER_PACKAGENAMES);

		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			info = list.get(i);
			// index =
			// launcherPackageList.indexOf(info.activityInfo.packageName);
			if (index < 0) {
				// 如果是未知的Launcher
				continue;
			} else {
				// launcher已知
				if (info.activityInfo.packageName.equalsIgnoreCase("com.android.launcher") && Build.VERSION.SDK_INT < 8) {
					uri = "content://com.android.launcher.settings/favorites?notify=true";
				} else {
					// uri = "content://" + LAUNCHER_SETTINGS[index] +
					// "/favorites?notify=true";
				}

				ContentResolver resolver = context.getContentResolver();
				Cursor cursor = null;
				try {
					cursor = resolver.query(Uri.parse(uri), null, "title=?", new String[] { context.getString(R.string.app_name) }, null);
					if (cursor != null && cursor.getCount() > 0) {
						return true;
					}
				} finally {
					if (cursor != null) {
						cursor.close();
					}
				}

			}
		}

		return false;
	}

	// 在home创建快捷方式
	// public static void createShortcut(Context context) {
	// Intent intent = new
	// Intent("com.android.launcher.action.INSTALL_SHORTCUT");
	// intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
	// Intent.ShortcutIconResource.fromContext(context,
	// R.drawable.action_logo_normal));
	// intent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
	// context.getString(R.string.app_name));
	// // 不允许重复创建
	// intent.putExtra("duplicate", false);
	//
	// // 加入action和category后, 程序卸载时桌面快捷方式也会一同卸载掉
	// // Intent sIntent = new Intent(Intent.ACTION_MAIN);
	// String action = "com.android.action.test";
	// Intent sIntent = new Intent(context, SplashActivity.class);
	// sIntent.setAction(action);
	// // sIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	// // sIntent.setClass(context, SplashActivity.class);
	// intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, sIntent);
	// context.sendBroadcast(intent);
	// }

	// 删除快捷方式
	// public static void deleteShorcut(Context context) {
	// Intent intent = new
	// Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
	// intent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
	// context.getString(R.string.app_name));
	//
	// Intent sIntent = new Intent(Intent.ACTION_MAIN);
	// sIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	// sIntent.setClass(context, SplashActivity.class);
	// intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, sIntent);
	//
	// context.sendBroadcast(intent);
	//
	// // 删除tx首发版, 用户自己创建的快捷方式
	// /*
	// * Intent intent2 = new
	// * Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
	// * intent2.putExtra(Intent.EXTRA_SHORTCUT_NAME,
	// * context.getString(R.string.app_name)); Intent sIntent2 = new
	// * Intent(Intent.ACTION_MAIN);
	// * sIntent2.addCategory(Intent.CATEGORY_LAUNCHER);
	// * sIntent2.setClass(context, TxStartActivity.class);
	// * intent2.putExtra(Intent.EXTRA_SHORTCUT_INTENT, sIntent2);
	// * context.sendBroadcast(intent2);
	// */
	// }

	// 临时获得一个可写的路径
	public static File getTempWritablePath(Context context) {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.endsWith(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// 如果外部存储可用, 则优先使用
			// return Environment.getExternalStorageDirectory();
			File result = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/." + context.getPackageName());
			if (!result.exists()) { // 确保目录正确创建
				if (!result.mkdirs()) {
					// 如果创建目录失败, 使用内部存储
					return context.getFilesDir();
				}
			}
			return result;
		} else {
			// 否则使用内部存储
			return context.getFilesDir();
		}
	}

	// 校验昵称的有效性
	public static boolean isNicknameValid(String nickname) {
		return (nickname.length() >= 4 && nickname.length() <= 20) ? true : false;
	}

	// 校验手机号码的有效性
	public static boolean isMobileValid(String phoneNumber) {
		return phoneNumber.matches(MOBILE_NUMBER_REG);
	}

	// 校验手机号的位数的有效性
	public static boolean isNumber(String num) {
		if (num.length() != 11) {
			return false;
		}
		return true;
	}

	// 校验密码的有效性
	public static boolean isPasswordValid(String password) {
		if (password.length() < 6 | password.length() > 20) {
			return false;
		}
		return true;
	}

	// 判断一个文件是否是可接受的图片文件
	public static boolean isImage(File file) {
		byte[] b = new byte[10];
		int len = -1;
		try {
			FileInputStream fis = new FileInputStream(file);
			len = fis.read(b);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (len == 10) {
			if (b[1] == (byte) 'P' && b[2] == (byte) 'N' && b[3] == (byte) 'G') {
				return true;
			} else if (b[6] == (byte) 'J' && b[7] == (byte) 'F' && b[8] == (byte) 'I' && b[9] == (byte) 'F') {
				return true;
			}
		}

		return false;
	}

	public static String obtainLowPriceString(String raw) {
		String basePrice = raw.split("\\-")[0];
		int dotIndex = basePrice.indexOf(".");
		return dotIndex > 0 ? basePrice.substring(0, dotIndex) : basePrice;
	}

	public static String trimYuanLow(String raw) {
		int dotIndex = raw.indexOf(".");
		return dotIndex > 0 ? raw.substring(0, dotIndex) : raw;
	}

	/**
	 * 将形如"￥80-￥100.00"的字符串转换为"80"
	 * 
	 * @param raw
	 * @return
	 */
	public static String trimRmb(String raw) {
		return obtainLowPriceString(raw).replaceAll("\\D", "");
	}

	/**
	 * 获得以GBK编码的字符串长度
	 * 
	 * @param content
	 * @return
	 */
	public static int getGbkLength(String content) {
		try {
			BigDecimal bd = new BigDecimal(content.getBytes("GBK").length);
			bd = bd.divide(new BigDecimal(2), BigDecimal.ROUND_HALF_UP);

			return bd.intValue();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 根据字符串变量, 生成指定长度的字符串
	 * 
	 * @param max
	 *            字符串最大长度
	 * @param format
	 *            strings.xml中的字符串格式
	 * @param lenVariableString
	 *            可变长度的字符串
	 * @param allStrings
	 *            替换"字符串格式"中变量部分的字符串数组
	 * @return
	 */
	public static String format(int max, String format, String lenVariableString, String... allStrings) {
		int lenVariableStringIndex = 0;
		for (int i = 0; i < allStrings.length; i++) {
			if (lenVariableString.equals(allStrings[i])) {
				lenVariableStringIndex = i;
				allStrings[lenVariableStringIndex] = "";
				break;
			}
		}

		int variableStringLen = DEFAULT_VARIABLE_STR_LENGTH;
		try {
			// 这里使用gbk编码计算长度(国内的sns使用gbk计算内容长度)
			int fixedSectionLength = String.format(format, allStrings).getBytes("GBK").length / 2;
			variableStringLen = max - fixedSectionLength;

			if ((lenVariableString.getBytes("GBK").length / 2) > variableStringLen) {
				// TODO 使用substring遇到字母数字时, 会损失一些长度(长度暂时没有完全利用)
				allStrings[lenVariableStringIndex] = lenVariableString.substring(0, variableStringLen - EXCEED_ELLIPSIS_LENGTH)
						+ EXCEED_ELLIPSIS_STR;
			} else {
				allStrings[lenVariableStringIndex] = lenVariableString;
			}

		} catch (UnsupportedEncodingException e) {
			// TODO
			allStrings[lenVariableStringIndex] = lenVariableString.substring(0, DEFAULT_VARIABLE_STR_LENGTH);
		}

		return String.format(format, allStrings);
	}

	// 可变字符串的默认长度
	private static final int DEFAULT_VARIABLE_STR_LENGTH = 10;
	private static final int EXCEED_ELLIPSIS_LENGTH = 2;
	private static final String EXCEED_ELLIPSIS_STR = "...";

}
