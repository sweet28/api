package com.arttraining.commons.util;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import com.arttraining.api.dao.InviteCodeMapper;
import com.arttraining.api.pojo.InviteCode;
import com.arttraining.api.service.impl.InviteCodeService;

public class InviteCodeUtil {
	public static String makeInviteCode() {
		String inviteCode = "";

		return inviteCode;
	}

	public static void main(String[] args) {
		Set<Integer> m = new HashSet<Integer>();
		for (int i = 0; i < 100; i++) {
			int a;
			do {
				a = (int) (Math.random() * 1000000);
			} while (m.contains(a));
			m.add(a);
			System.out.println(a + ":" + m + ":"
					+ RC4.HloveyRC4(a + "", ConfigUtil.MD5_PWD_STR));
			InviteCode icode = new InviteCode();
			icode.setInviteCode(a + "");
			icode.setCreateTime(TimeUtil.getTimeStamp());
			icode.setIsDeleted(0);
			icode.setIsUsed(0);

		}
		
		System. out .println( " 内存信息 :" + toMemoryInfo ());
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
