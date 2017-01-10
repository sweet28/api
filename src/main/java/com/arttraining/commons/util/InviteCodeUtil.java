package com.arttraining.commons.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import com.arttraining.api.dao.InviteCodeMapper;
import com.arttraining.api.pojo.InviteCode;
import com.arttraining.api.service.impl.InviteCodeService;

public class InviteCodeUtil {

	/**
	 * 邀请码生成器，算法原理：<br/>
	 * 1) 获取id: 1127738 <br/>
	 * 2) 使用自定义进制转为：gpm6 <br/>
	 * 3) 转为字符串，并在后面加'o'字符：gpm6o <br/>
	 * 4）在后面随机产生若干个随机数字字符：gpm6o7 <br/>
	 * 转为自定义进制后就不会出现o这个字符，然后在后面加个'o'，这样就能确定唯一性。最后在后面产生一些随机字符进行补全。<br/>
	 */

	/** 自定义进制(0,1没有加入,容易与o,l混淆) */
	private static final char[] r = new char[] { 'q', 'w', 'e', '8', 'a', 's',
			'2', 'd', 'z', 'x', '9', 'c', '7', 'p', '5', 'i', 'k', '3', 'm',
			'j', 'u', 'f', 'r', '4', 'v', 'y', 't', 'n', '6', 'b', 'g',
			'h' };

	/** (不能与自定义进制有重复) */
	private static final char b = 'o';

	/** 进制长度 */
	private static final int binLen = r.length;

	/** 序列最小长度 */
	private static final int s = 7;

	/**
	 * 根据ID生成7位随机码
	 * 
	 * @param id
	 *            ID
	 * @return 随机码
	 */
	public static String toSerialCode(long id) {
		char[] buf = new char[32];
		int charPos = 32;

		while ((id / binLen) > 0) {
			int ind = (int) (id % binLen);
			// System.out.println(num + "-->" + ind);
			buf[--charPos] = r[ind];
			id /= binLen;
		}
		buf[--charPos] = r[(int) (id % binLen)];
		String str = new String(buf, charPos, (32 - charPos));
		// 不够长度的自动随机补全
		if (str.length() < s) {
			StringBuilder sb = new StringBuilder();
			sb.append(b);
			Random rnd = new Random();
			for (int i = 1; i < s - str.length(); i++) {
				sb.append(r[rnd.nextInt(binLen)]);
			}
			str += sb.toString();
		}
		return str;
	}
	
	/**
	 * 根据ID生成s位随机码
	 * @return 随机码
	 */
	public static String toSerialCode(long id, int s) {
		char[] buf = new char[32];
		int charPos = 32;

		while ((id / binLen) > 0) {
			int ind = (int) (id % binLen);
			// System.out.println(num + "-->" + ind);
			buf[--charPos] = r[ind];
			id /= binLen;
		}
		buf[--charPos] = r[(int) (id % binLen)];
		String str = new String(buf, charPos, (32 - charPos));
		// 不够长度的自动随机补全
		if (str.length() < s) {
			StringBuilder sb = new StringBuilder();
			sb.append(b);
			Random rnd = new Random();
			for (int i = 1; i < s - str.length(); i++) {
				sb.append(r[rnd.nextInt(binLen)]);
			}
			str += sb.toString();
		}
		return str;
	}
	
	/**
	 * 根据ID生成type类型的s位随机码
	 * @return 随机码
	 */
	public static String toSerialCode(long id, int s, String type) {
		char[] buf = new char[32];
		int charPos = 32;

		while ((id / binLen) > 0) {
			int ind = (int) (id % binLen);
			// System.out.println(num + "-->" + ind);
			buf[--charPos] = r[ind];
			id /= binLen;
		}
		buf[--charPos] = r[(int) (id % binLen)];
		String str = new String(buf, charPos, (32 - charPos));
		// 不够长度的自动随机补全
		if (str.length() < s) {
			StringBuilder sb = new StringBuilder();
			sb.append(b);
			Random rnd = new Random();
			for (int i = 1; i < s - str.length(); i++) {
				sb.append(r[rnd.nextInt(binLen)]);
			}
			str += sb.toString();
		}
		return type+str;
	}

	public static String makeInviteCode() {
		String inviteCode = "";

		return inviteCode;
	}

	public static void main(String[] args) {
//		Set<Integer> m = new HashSet<Integer>();
//		for (int i = 0; i < 100; i++) {
//			int a;
//			do {
//				a = (int) (Math.random() * 1000000);
//			} while (m.contains(a));
//			m.add(a);
//			System.out.println(a + ":" + m + ":"
//					+ RC4.HloveyRC4(a + "", ConfigUtil.MD5_PWD_STR));
//			InviteCode icode = new InviteCode();
//			icode.setInviteCode(a + "");
//			icode.setCreateTime(TimeUtil.getTimeStamp());
//			icode.setIsDeleted(0);
//			icode.setIsUsed(0);
//		}

		for(int i = 1; i <= 100; i++){
			System.out.println(toSerialCode(i,6,"@"));
		}
	}

	/**
	 * 获取当前 jvm 的内存信息
	 *
	 * @return
	 */
	public static String toMemoryInfo() {

		Runtime currRuntime = Runtime.getRuntime();
		int nFreeMemory = (int) (currRuntime.freeMemory() / 1024 / 1024);
		int nTotalMemory = (int) (currRuntime.totalMemory() / 1024 / 1024);
		return nFreeMemory + "M/" + nTotalMemory + "M(free/total)";
	}
}
