package com.carpi.filter;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arttraining.commons.util.ConfigUtil;
import com.arttraining.commons.util.RSAEncrypt;
import com.arttraining.commons.util.RSASignature;
import com.arttraining.commons.util.RSAsecurity;
import com.mysql.fabric.xmlrpc.base.Data;

public class AntiSqlInjectionfilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	public void doFilter(ServletRequest args0, ServletResponse args1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) args0;
		HttpServletResponse res = (HttpServletResponse) args1;

		chain.doFilter(new XSSRequestWrapper((HttpServletRequest) args0), args1);
	}

//	public void doFilter(ServletRequest args0, ServletResponse args1, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest) args0;
//		HttpServletResponse res = (HttpServletResponse) args1;
//		// 获得所有请求参数名
//		Enumeration params = req.getParameterNames();
//
//		System.out.println("url::::" + params);
//		String sql = "";
//		while (params.hasMoreElements()) {
//			// 得到参数名
//			String name = params.nextElement().toString();
//			System.out.println("name===========================" + name + "--");
//			if (name.equals("imgUrl")) {
//				break;
//			}
//			// 得到参数对应值
//			String[] value = req.getParameterValues(name);
//			for (int i = 0; i < value.length; i++) {
//				sql = sql + value[i];
//			}
//		}
//		System.out.println("============================SQL" + sql);
//		// 有sql关键字，跳转到error.html
//		if (sqlValidate(sql)) {
//			throw new IOException("您发送请求中的参数中含有非法字符");
//			// String ip = req.getRemoteAddr();
//		} else {
//
//			// 做url鉴权过滤：token、时间戳、sign
//
//			chain.doFilter(new XSSRequestWrapper((HttpServletRequest) args0), args1);
////			chain.doFilter(args0, args1);
//		}
//	}

	// 效验
	protected static boolean sqlValidate(String str) {
		System.out.println("0000000000000000");
		str = str.toLowerCase();// 统一转为小写
//		String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|"
//				+ "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|"
//				+ "table|from|grant|use|group_concat|column_name|"
//				+ "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|"
//				+ "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";// 过滤掉的sql关键字，可以手动添加
		
		String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|"
				+ "char|declare|sitename|net user|xp_cmdshell|;|or|,|like'|and|exec|execute|insert|create|drop|"
				+ "table|from|grant|use|group_concat|column_name|"
				+ "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|"
				+ "chr|mid|master|truncate|char|declare|or|;|,|like|%|#";
		String[] badStrs = badStr.split("\\|");
		for (int i = 0; i < badStrs.length; i++) {
			if (str.indexOf(badStrs[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

	public void doFilter2222222222(ServletRequest args0, ServletResponse args1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) args0;
		HttpServletResponse res = (HttpServletResponse) args1;
		// 获得所有请求参数名
		Enumeration params = req.getParameterNames();

		if (params.hasMoreElements()) {

			System.out.println("url::::" + params);
			String sql = "";
			boolean flag = false;
			String str = "";
			String tmp = "";
			String rad = "";
			String token = "";
			String signStr = "";
			while (params.hasMoreElements()) {
				// 得到参数名
				String name = params.nextElement().toString();
				System.out.println("name===========================" + name + "--");
				if (name.equals("imgUrl")) {
					flag = true;
				}

				if (name.equals("tmp")) {
					tmp = req.getParameterValues(name).toString();
				}

				if (name.equals("rad")) {
					rad = req.getParameterValues(name).toString();
				}

				if (name.equals("tom")) {
					token = req.getParameterValues(name).toString();
				}

				if (name.equals("token")) {
					signStr = req.getParameterValues(name).toString();
					continue;
				}

				// 得到参数对应值
				String[] value = req.getParameterValues(name);

				str = name + "=" + value;
			}

			System.out.println("request str:::" + str);

			if (flag) {
				chain.doFilter(args0, args1);
			} else if (!tmp.isEmpty() && !token.isEmpty() && !rad.isEmpty()) {
				// 做url鉴权过滤：token、时间戳、sign
				Date dt = new Date();
				dt.getTime();

				String sign = RSAsecurity.signByPublic(str, ConfigUtil.RSA_PUBLIC_KEY);

				if (sign.equals(signStr)) {
					if (RSAsecurity.doCheckByPrivate(str, signStr, ConfigUtil.RSA_PRIVATE_KEY)) {
						System.out.println("----------------yes-------------");
						chain.doFilter(args0, args1);
					} else {
						System.out.println("前端公钥加密数据解密不对。");
					}
				} else {
					System.out.println("公钥加密传输前端后端同样加密后，对比不一致");
				}

			} else {
				System.out.println();
			}
		} else {
			chain.doFilter(args0, args1);
		}
	}

	public static void main(String[] args) throws Exception {
		String ts = "m123456789";

		System.out.println("---------------私钥签名过程------------------");
		String content = "ihep_这是用于签名的原始数据";
		String signstr = RSASignature.encryptBASE64(RSASignature.sign(content, ConfigUtil.RSA_PRIVATE_KEY));
		System.out.println("签名原串：" + content);
		System.out.println("签名串：" + signstr);
		System.out.println();
		//
		System.out.println("---------------公钥校验签名------------------");
		System.out.println("签名原串：" + content);
		System.out.println("签名串：" + signstr);

		System.out.println("验签结果：" + RSASignature.doCheck(content, signstr, ConfigUtil.RSA_PUBLIC_KEY));

		System.out.println("---------------公钥加密过程------------------");
		String content2 = "ihep_这是用于签名的原始数据";

		RSAPublicKey pubKey = RSAEncrypt.loadPublicKeyByStr(ConfigUtil.RSA_PUBLIC_KEY);

		String signstr2 = RSAEncrypt.encryptBASE64(RSAEncrypt.encrypt(pubKey, RSAEncrypt.decryptBASE64(content2)));
		System.out.println("签名原串：" + content2);
		System.out.println("签名串：" + signstr2);
		System.out.println();
		//
		System.out.println("---------------私钥解密过程------------------");
		System.out.println("签名原串：" + content2);
		System.out.println("签名串：" + signstr2);

		RSAPrivateKey priKey = RSAEncrypt.loadPrivateKeyByStr(ConfigUtil.RSA_PRIVATE_KEY);
		System.out.println(
				"验签结果：" + RSAEncrypt.encryptBASE64(RSAEncrypt.decrypt(priKey, RSAEncrypt.decryptBASE64(signstr2))));

		System.out.println(new String(RSAEncrypt.decrypt(priKey, RSAEncrypt.encrypt(pubKey, content2.getBytes()))));

	}
}