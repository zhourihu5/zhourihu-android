package com.sinoglobal.app.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DESedeUtils {

	/**
	 * 3DES 加密算法
	 * 
	 * @param src
	 *            需要加密的文本
	 * @param enKey
	 *            密钥
	 * @return 以byte[]形式返回加密后文本
	 */
	public static byte[] encrypt(byte[] src, byte[] enKey) {
		byte[] encryptedData = null;
		try {
			// 从原始密匙数据创建一个DESKeySpec对象
			DESedeKeySpec dks = new DESedeKeySpec(enKey);
			// 创建一个密匙工厂，然后用它把DESedeKeySpec对象转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey key = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DESede");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, key);
			encryptedData = cipher.doFinal(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedData;
	}

	/**
	 * DES 解密算法
	 * 
	 * @param text
	 *            加密的十六进制形式文本
	 * @param deKey
	 *            密钥
	 * @return 解密后的文本
	 */

	public static String deCrypt(byte[] text, byte[] deKey) {
		String strDe = null;
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("DESede");
			DESedeKeySpec dks = new DESedeKeySpec(deKey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey sKey = keyFactory.generateSecret(dks);
			cipher.init(Cipher.DECRYPT_MODE, sKey);
			byte ciphertext[] = cipher.doFinal(text);
			strDe = new String(ciphertext);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strDe;
	}

	/**
	 * 生成mac
	 * 
	 * @param sByte
	 *            原数据
	 * @param k
	 *            密钥
	 * @return mac值
	 */

	public static byte[] getMac(byte[] sByte, byte[] k) {
		try {
			int len = sByte.length % 8 != 0 ? (sByte.length / 8 + 1) * 8 : sByte.length;
			byte pByte[] = new byte[len];
			System.arraycopy(sByte, 0, pByte, 0, sByte.length);
			byte mac[] = new byte[8];
			for (int i = 0; i < len / 8; i++) {
				for (int j = 0; j < 8; j++) {
					int macInt = mac[j] ^ pByte[i * 8 + j];
					Integer macIntO = new Integer(macInt);
					mac[j] = macIntO.byteValue();
				}
				// byte[] aab = new byte[8];
				byte[] aab = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
				IvParameterSpec iv = new IvParameterSpec(aab);
				Cipher cp = Cipher.getInstance("DESede/CBC/NoPadding");
				SecretKeySpec key = new SecretKeySpec(k, "DESede");
				cp.init(1, key, iv);
				mac = cp.doFinal(mac);
			}
			return mac;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] GetBodyNoMac(byte[] body, int len) {
		byte[] bodyNoMac = new byte[body.length - len];
		System.arraycopy(body, 0, bodyNoMac, 0, bodyNoMac.length);
		return bodyNoMac;
	}

	/**
	 * 对数据包进行mac校验,mac在包体最后占8个字节
	 * 
	 * @param body
	 *            源数据
	 * @param k
	 *            3des密钥
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean getMacCheck(byte[] body, byte[] k, List reqlist) {
		// 取得除消息类型和bit的请求包长度。
		int len = 0;
		for (int i = 0; i < reqlist.size(); i++) {
			BitMap b = (BitMap) reqlist.get(i);
			if (b.getVariable() > 0) {
				len += b.getVariable() + b.getDat().length;
			} else {
				len += b.getLen();
			}
		}

		// 获得请求包body用了计算mac1
		byte[] bodyNoMac = new byte[20 + len - 8];
		System.arraycopy(body, 0, bodyNoMac, 0, bodyNoMac.length);
		// 获得请求包的mac2
		byte[] mac = new byte[8];
		System.arraycopy(body, 20 + len - 8, mac, 0, mac.length);
		// 验证mac
		if (new String(mac).equals(new String(getMac(bodyNoMac, k)))) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String test = "2091220200008022";
		// 加密
		byte[] arr = encrypt(test.getBytes(), "123456781234567812345678".getBytes());//进行加密，
		System.out.println(arr.length);
		System.out.println(new String(arr, "iso8859-1"));
		String str = StrBinaryUtils.StrToBinstr(new String(arr, "iso8859-1"));
		// 二进制形式输出
		System.out.println("str:" + str);
		System.out.println(str.replace(" ", "").length());
		String str2 = UUID.randomUUID().toString().replace("-", "").toString();
		System.out.println("str2=" + str2);
		System.out.println(str2.length());
		// str2 = str2.replace("-","");
		// String str4 = Long.parseLong(str2,16)+"";
		// // System.out.println(str4.length());
		// @SuppressWarnings("unused")
		// String s = "e394410f7ca04cc0b07c70d4cb7d5fa5";
		// // s = s.toUpperCase();
		// // System.out.println(s);
		// //
		// // Long i = Long.valueOf(s, 16);
		// // System.out.println(i);
		// for (int i = 0;i < 100;i++ )
		// {
		// try
		// {
		// Thread.sleep (100);
		// }catch (InterruptedException e)
		// {
		// e.printStackTrace ();
		// }
		// Long id = null;
		// id = System.currentTimeMillis ();
		// System.out.println (id);
		// System.out.println ((id + "").length ());
		// }
	}

}
