package com.arttraining.commons.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

/**
 * MD5加密工具类
 *
 */
public final class MD5 {

    private static MessageDigest md5;

    /**
     * 将字符串进行md5加密
     *
     * @param string:待加密字符串
     * @param encoding:字符编码(默认编码:utf-8)
     * @return
     * @throws RuntimeException
     */
    public static final String encodeString(String string, String encoding) throws RuntimeException {
        return String.valueOf(Hex.encodeHex(digestString(string, encoding)));
    }

    /**
     * 按utf-8编码取出字符串进行加密
     *
     * @param string
     * @return
     * @throws RuntimeException
     */
    public static final String encodeString(String string) throws RuntimeException {
        return encodeString(string, null);
    }

    /**
     * 将输入字符串与原有已加密串比对
     *
     * @param string
     * @param encodedString
     * @param encoding
     * @return
     * @throws RuntimeException
     */
    public static final boolean check(String string, String encodedString, String encoding) throws RuntimeException {
        String str = encodeString(string, encoding);
        return StringUtils.equals(str, encodedString);
    }

    public static final boolean check(String string, String encodedString) throws RuntimeException {
        return check(string, encodedString, null);
    }

    private static byte[] digestString(String string, String encoding) throws RuntimeException {
        byte[] data;
        if (encoding == null) {
            encoding = "utf-8";
        }
        try {
            data = string.getBytes(encoding);
        } catch (UnsupportedEncodingException x) {
            throw new RuntimeException(x.toString());
        }
        return digestBytes(data);
    }

    private static final byte[] digestBytes(byte[] data) throws RuntimeException {
        synchronized (MD5.class) {
            if (md5 == null) {
                try {
                    md5 = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e.toString());
                }
            }
            return md5.digest(data);
        }
    }
    
    public static final String encodeStringDouble(String string) throws RuntimeException {
    	String nowTime = "yunhuyi_arttraining_ychzzhglqh";
		
		return MD5.encodeString(MD5.encodeString(string+nowTime));
    }

    public static void main(String[] args) {
    	
        String str1 = "a12345" + ConfigUtil.MD5_PWD_STR;
        String pwd = MD5.encodeString(str1);
        String enStr = "62d67707752f3bac090e0ea6bf09e138";
        System.out.println(enStr.length());
        System.out.println(pwd);
        System.out.println(MD5.check(str1, enStr));
        
        String enStr11 = "41b17bcc1b6dc0ff601a68d878bb2f12";
        System.out.println(MD5.check((MD5.encodeString(str1)+ConfigUtil.MD5_PWD_STR), enStr11));
        
        String cpaPWD = MD5.encodeString(
				MD5.encodeString("Cpa2018carpi" + ConfigUtil.MD5_PWD_STR) + ConfigUtil.MD5_PWD_STR);
        
        System.out.println(cpaPWD);
    }
}