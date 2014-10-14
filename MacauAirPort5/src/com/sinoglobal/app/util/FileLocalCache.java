package com.sinoglobal.app.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Map;

import android.content.Context;
import android.os.Environment;

import com.sinoglobal.app.activity.FlyApplication;
import com.sinoglobal.app.util.constants.Constants;


/**
 * 缓存
 * @author ty
 *
 */
public class FileLocalCache {
	public static final String CACHE_DIR = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ? 
			getExternalCacheDir(FlyApplication.context).getPath() : FlyApplication.context.getCacheDir().getPath();
			/**
			 * 检查文件是否存在
			 * @return
			 */
			public static boolean checkDir(String filePath) {
				File f = new File(filePath);
				if (!f.exists()) {
					return f.mkdirs();
				}
				return true;
			}
			/**
			 * 获取程序外部的缓存目录
			 * @param context
			 * @return
			 */
			public static File getExternalCacheDir(Context context) {
				final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
				File file=new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
				if (!file.exists()) {
					file.mkdirs();
				}
				return file;
			}
			/**
			 * 返回md5后的值
			 * @param url
			 * @return
			 */
			public static String md5(String url) {
				try {
					MessageDigest md5 = java.security.MessageDigest.getInstance("MD5");
					md5.update(url.getBytes("UTF-8"));
					byte messageDigest[] = md5.digest();

					StringBuffer hexString = new StringBuffer();
					for (int i = 0; i < messageDigest.length; i++) {
						String t = Integer.toHexString(0xFF & messageDigest[i]);
						if (t.length() == 1) {
							hexString.append("0" + t);
						} else {
							hexString.append(t);
						}
					}
					return hexString.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			/**
			 * 清除SD卡中的数据缓存
			 */
			/*	public static void clearCache1() {
		File f = new File(FancusApplication.CACHE_DIR);
		if (f.exists() && f.isDirectory()) {
			File flist[] = f.listFiles();
			for (int i = 0; flist != null && i < flist.length; i++) {
				flist[i].delete();
			}
		}
	}*/

			/**
			 * 缓存文件大小
			 * @return
			 */
			public static String getCacheSize() {
				long size = 0;
				File f = new File(FlyApplication.CACHE_DIR_SD);
				if (f.exists() && f.isDirectory()) {
					File flist[] = f.listFiles();
					for (int i = 0; flist != null && i < flist.length; i++) {
						size = size + flist[i].length();
					}
				}
				if (size < 1000) {
					return "0KB";
				} else if (size < 1000000) {
					return size / 1000 + "KB";
				} else {
					return size / 1000000 + "M";
				}
			}

			/**
			 * 从缓存中读取缓存数据，条件是在一小时之内有效
			 * @param url
			 * @return
			 * @throws Exception
			 */
			public static String load2(String url)throws Exception {
				String md5 = md5(url);
				File f = new File(FlyApplication.CACHE_DIR_SD + md5);
				long expiredTime = 600000; 
				//数据在10分钟有效内
				if (f.exists() && System.currentTimeMillis() - f.lastModified() < expiredTime) {
					FileInputStream fstream = new FileInputStream(f);
					long length = f.length();
					byte[] bytes = new byte[(int) length];
					int offset = 0;
					int numRead = 0;
					while (offset < bytes.length && 
							(numRead = fstream.read(bytes, offset, bytes.length- offset)) >= 0) {
						offset += numRead;
					}
					return new String(bytes, "UTF-8");
				}
				return null;
			}

			/**
			 * 将读取的String写入缓存中
			 * @param url
			 * @param c
			 */
			public static void store(String url, String c) {
				if(FlyApplication.IS_EXIST_SDCARD){
					String md5 = md5(url);
					File f = new File(FlyApplication.CACHE_DIR_SD + md5);
					try {
						FileOutputStream out;
						out = new FileOutputStream(f);
						out.write(c.getBytes("UTF-8"));
						out.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			/**
			 * 关闭输入流
			 * @param in
			 */
			public static void closeInputStream(InputStream in){
				try {
					if(in!=null){
						in.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			/**
			 * 关闭输入流
			 * @param in BufferedReader
			 */
			public static void closeBufferedReader(BufferedReader in){
				try {
					if(in!=null){
						in.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			/**
			 * 关闭输出流
			 * @param in
			 */
			public static void closeOutputStream(OutputStream out){
				try {
					if(out!=null){
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			/**
			 * 
			 * @author zhourihu
			 * @createdate 2014年7月6日 上午9:57:47
			 * @Description: (用一句话描述该方法做什么) 保存于缓存文件
			 * @param fileName 
			 * @param content
			 * @throws IOException
			 *
			 */
			public static void saveFile(String fileName,String content) throws IOException{
				//		if(!LogUtil.FLAG){  //为true(生产环境)，不保证加载数据
				//			return;
				//		}
				File temp = new File(CACHE_DIR + File.separator + fileName);
				DataOutputStream dos = null;
				try {
					dos = new DataOutputStream(new FileOutputStream(temp));
					dos.writeUTF(content);
					LogUtil.i("write cache sucess!");
				} catch (Exception e) {
					LogUtil.i("write cache failed!");
					e.printStackTrace();
				} finally {
					try {
						dos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			public static String loadFileCache(String fileName) {
				File temp = new File(CACHE_DIR, fileName);
				String content="";
				if (temp.exists()) {
					LogUtil.i("read cache sucess!");
					try {
						content = new DataInputStream(new FileInputStream(temp))
								.readUTF();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						LogUtil.i("read cache failed!");
						e.printStackTrace();
					}
					return content;
				} else {
					LogUtil.i("no cache");
					return content;
				}
			}
			/**
			 * 取得序列化数据
			 * @param type 缓存文件在SD卡中，还是在SYSTEM中,Constants.CACHE_DIR_SD,Constants.CACHE_DIR_SYSTEM
			 * @param fileName 
			 * @return
			 */
			public static Object getSerializableData(int type,String fileName) {
				String dir;
				if(Constants.CACHE_DIR_SD==type){  //缓存在SD卡中
					dir=FlyApplication.CACHE_DIR_SD;
				}else{  //缓存在SYSTEM文件中
					dir=FlyApplication.CACHE_DIR_SYSTEM;
				}
				Object obj = null;
				try {
					File d = new File(dir);
					if (!d.exists()){
						d.mkdirs();
					}
					File f = new File(dir +md5(fileName));
					if (!f.exists()){
						f.createNewFile();
					}
					if (f.length() == 0){
						return null;
					}
					FileInputStream byteOut = new FileInputStream(f);
					ObjectInputStream out = new ObjectInputStream(byteOut);
					obj=out.readObject();
					out.close();
					closeInputStream(byteOut);
				} catch (StreamCorruptedException e) {
					e.printStackTrace();
				} catch (OptionalDataException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return obj;
			}

			/**
			 * 取得序列化数据
			 * @param type 缓存文件在SD卡中，还是在SYSTEM中Constants.CACHE_DIR_SD,Constants.CACHE_DIR_SYSTEM
			 * @param fileName 
			 * @param 缓存文件有效时间
			 * @return
			 */
			public static Object getSerializableData(int type,String fileName,long time) {
				String dir;
				if(Constants.CACHE_DIR_SD==type){  //缓存在SD卡中
					dir=FlyApplication.CACHE_DIR_SD;
				}else{  //缓存在SYSTEM文件中
					dir=FlyApplication.CACHE_DIR_SYSTEM;
				}
				Object obj = null;
				try {
					File d = new File(dir);
					if (!d.exists()){
						d.mkdirs();
					}
					File f = new File(dir +md5(fileName));
					if (!f.exists()){
						f.createNewFile();
					}
					if (f.length() == 0){
						return null;
					}
					long lastTime=f.lastModified();
					long nowTime=System.currentTimeMillis();
					if(nowTime-lastTime>time){
						return null;
					}
					FileInputStream byteOut = new FileInputStream(f);
					ObjectInputStream out = new ObjectInputStream(byteOut);
					obj=out.readObject();
					out.close();
					closeInputStream(byteOut);
				} catch (StreamCorruptedException e) {
					e.printStackTrace();
				} catch (OptionalDataException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				return obj;
			}

			/**
			 * 进行序列化
			 * @param type 缓存文件在SD卡中，还是在SYSTEM中Constants.CACHE_DIR_SD,Constants.CACHE_DIR_SYSTEM
			 * @param cookies
			 * @param fileName 
			 */
			public static void setSerializableData(int type,Object obj,String fileName) {
				String dir;
				if(Constants.CACHE_DIR_SD==type){  //缓存在SD卡中
					dir=FlyApplication.CACHE_DIR_SD;
				}else{  //缓存在SYSTEM文件中
					dir=FlyApplication.CACHE_DIR_SYSTEM;
				}
				try {
					FileOutputStream bytetOut = new FileOutputStream(new File(dir + md5(fileName)));
					ObjectOutputStream outer = new ObjectOutputStream(bytetOut);
					outer.writeObject(obj);
					outer.flush();
					outer.close();
					bytetOut.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			/**
			 * @author miaoxin.ye
			 * @createdate 2012-10-11 上午10:05:29
			 * @Description: 删除序列化数据
			 * @param type
			 * @param fileName
			 */
			public static void delSerializableData(int type,String fileName){
				String dir;
				if(Constants.CACHE_DIR_SD==type){  //缓存在SD卡中
					dir=FlyApplication.CACHE_DIR_SD;
				}else{  //缓存在SYSTEM文件中
					dir=FlyApplication.CACHE_DIR_SYSTEM;
				}
				File d = new File(dir);
				if (!d.exists()){
					d.mkdirs();
				}
				File f = new File(dir +md5(fileName));
				if (f.exists()){
					f.delete();
				}
			}

			public static String getFileName(String url, Map<String, Object> params) {
				StringBuffer buffer = new StringBuffer(url);
				if (params != null) {
					for (String key : params.keySet()) {
						buffer.append(key);
						buffer.append(params.get(key) == null ? null : params.get(key)
								.toString());
					}
				}
				return md5(buffer.toString());
			}
}
