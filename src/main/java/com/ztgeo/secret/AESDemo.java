package com.ztgeo.secret;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESDemo {

	private static String reqStr ="需要被加密内容!!";
	
	public static void main(String[] args) {
		jdkAes();
	}

	
	public static void jdkAes(){
		try {
			//生成key
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			SecretKey sekey = keyGenerator.generateKey();
			//获得程序产生的key
			byte[] keyByte = sekey.getEncoded();
			System.out.println("秘钥数组1:"+keyByte);
			
			
			/*KeyGenerator keyGenerator1 = KeyGenerator.getInstance("AES");
			keyGenerator1.init(128);
			SecretKey sekey1 = keyGenerator1.generateKey();
			//获得程序产生的key
			byte[] keyByte1 = sekey1.getEncoded();
			System.out.println("秘钥数组2:"+keyByte1);*/
			
			
			
			//转换key
			Key key  = new SecretKeySpec(keyByte, "AES");
			
			//加密  填充
			Cipher  cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] resultByte = cipher.doFinal(reqStr.getBytes());
			System.out.println("AES加密后的内容(base64之后):"+Base64.encodeBase64String(resultByte));
			
			
			
			//解密
			
			cipher.init(Cipher.DECRYPT_MODE, key);
			resultByte = cipher.doFinal(resultByte);
			System.out.println("使用JDK解密AES操作:"+new String(resultByte));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
