package com.arttraining.commons.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
  
import javax.crypto.Cipher;
  
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
  
/*RSA 工具类。提供加密，解密，生成密钥对等方法。
RSA加密原理概述  
RSA的安全性依赖于大数的分解，公钥和私钥都是两个大素数（大于100的十进制位）的函数。
据猜测，从一个密钥和密文推断出明文的难度等同于分解两个大素数的积  
密钥的产生：  
 1.选择两个大素数 p,q ,计算 n=p*q;  
 2.随机选择加密密钥 e ,要求 e 和 (p-1)*(q-1)互质  
 3.利用 Euclid 算法计算解密密钥 d , 使其满足 e*d = 1(mod(p-1)*(q-1)) (其中 n,d 也要互质)  
 4:至此得出公钥为 (n,e) 私钥为 (n,d)  
 RSA速度  
 * 由于进行的都是大数计算，使得RSA最快的情况也比DES慢上100倍，无论 是软件还是硬件实现。  
 * 速度一直是RSA的缺陷。一般来说只用于少量数据 加密。*/
public class RSAsecurity {
  
    public static String src = "AccessKey=access&home=world&name=hello&work=java&timestamp=now&nonce=152098761";
  
    public void priENpubDE() {
  
        try {
            // 初始化秘钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            // 秘钥长度
            keyPairGenerator.initialize(1024);
            // 初始化秘钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 公钥
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            // 私钥
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
  
            // 2.私钥加密，公钥解密----加密
            // 生成私钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            // Cipher类为加密和解密提供密码功能，通过getinstance实例化对象
            Cipher cipher = Cipher.getInstance("RSA");
            // 初始化加密
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("私钥加密，公钥解密----加密:" + Base64.encode(result));
  
            // 3.私钥加密，公钥解密----解密
            // 生成公钥
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            // 初始化解密
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            result = cipher.doFinal(result);
            System.out.println("私钥加密，公钥解密----解密:" + new String(result));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  
    }
  
    public void pubENpriDE() {
        try {
            // 1.初始化秘钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            // 秘钥长度
            keyPairGenerator.initialize(512);
            // 初始化秘钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 公钥
            RSAPublicKey rsaPublicKey = (RSAPublicKey)getPublicKey(ConfigUtil.RSA_PUBLIC_KEY); //keyPair.getPublic();
            // 私钥
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)getPrivateKey(ConfigUtil.RSA_PRIVATE_KEY); //keyPair.getPrivate();
  
            // 2.公钥加密，私钥解密----加密
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            // 初始化加密
            // Cipher类为加密和解密提供密码功能，通过getinstance实例化对象
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 加密字符串
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("公钥加密，私钥解密----加密:" + Base64.encode(result));
  
            // 3.公钥加密，私钥解密-----解密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            // 初始化解密
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 解密字符串
            result = cipher.doFinal(result);
            System.out.println("公钥加密，私钥解密-----解密:" + new String(result));
  
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
  
    }
    
    /**
	 * RSA签名
	 * 
	 * @param content
	 *            待签名数据
	 * @param publicKeyStr
	 *            公钥串
	 * @return 签名值
	 */
	public static String signByPublic(String content, String publicKeyStr) {
		
		RSAPublicKey rsaPublicKey;
		try {
			rsaPublicKey = (RSAPublicKey)getPublicKey(publicKeyStr);
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
	        // 初始化加密
	        // Cipher类为加密和解密提供密码功能，通过getinstance实例化对象
	        Cipher cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        // 加密字符串
	        byte[] result = cipher.doFinal(content.getBytes());
	        return Base64.encode(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} //keyPair.getPublic();
	}
    
    /**
	 * RSA验签名检查
	 * 
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param privateKeyStr
	 *            私钥串
	 * @return 布尔值
	 */
	public static boolean doCheckByPrivate(String content, String sign, String privateKeyStr) {
		
		try {
            // 私钥
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)getPrivateKey(ConfigUtil.RSA_PRIVATE_KEY); //keyPair.getPrivate();
  
            // 3.公钥加密，私钥解密-----解密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            // 初始化解密
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 解密字符串
            byte[] result = cipher.doFinal(Base64.decode(sign));
            System.out.println("公钥加密，私钥解密-----解密:" + new String(result));
            
            if(content.equals(new String(result))){
            	return true;
            }else{
            	return false;
            }
  
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		return false;
	}
    
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decode(key);//(new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
  }
    
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decode(key);//(new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
  }
    
    
    public static void main(String[] args) {
        RSAsecurity rsAsecurity = new RSAsecurity();
        System.out.println("私钥加密公钥解密例：");
        rsAsecurity.priENpubDE();
        System.out.println("公钥加密私钥解密例：");
        rsAsecurity.pubENpriDE();
        
        String signStr = signByPublic("AccessKey=access&home=world&name=hello&work=java&timestamp=now&nonce=152098761", ConfigUtil.RSA_PUBLIC_KEY);
        System.out.println("signStr:"+signStr);
        
        String signStrByJS = "hvyYGQT2fNpeXMF65le4kbluWBYEKLwFe4BKa/NHlwiNpa8gErzLVO/IXYHV7O3P0X9rXF3P9xW5AbF50pvjZdD/OfqUyesYJ+z9dwqXZW7EJHlH2GwXxBqP0rrWVp5p2gqMEpimQY6ODilmMBhYC5hY";
        
        boolean flag = doCheckByPrivate("AccessKey=access&home=world&name=hello&work=java&timestamp=now&nonce=152098761", signStrByJS, ConfigUtil.RSA_PRIVATE_KEY);
        System.out.println(flag);
    }
}
