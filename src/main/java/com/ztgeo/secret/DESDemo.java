package com.ztgeo.secret;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;

public class DESDemo {

	private static String reqStr ="需要被加密内容!!";
	
	public static void main(String[] args) {
			jdkDES();
	}

	public static void jdkDES(){
		
		try {
			//生成秘钥材料
			/*KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
			//指定秘钥长度  DES是56位
			keyGenerator.init(56);
			SecretKey key = keyGenerator.generateKey();
			//获得程序产生的key
			byte[] keyByte = key.getEncoded();*/
			
			
			//当然 可以自己指byte数组类型的秘钥
			byte[] keyByte = "18361552497".getBytes();      //秘钥
			
			
			//秘钥材料转换 
			DESKeySpec desKeySpec = new DESKeySpec(keyByte);
			
			//生成真正的key
			//实例化秘钥工厂(指定加密方式)
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
			Key convertSecretKey = factory.generateSecret(desKeySpec);
		
			//加密 (需要填充加解密的算法/工作方式/填充方式) 
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			//加密模式ENCRYPT_MODE 和转换可以使用的key
			cipher.init(cipher.ENCRYPT_MODE, convertSecretKey);
			//执行加密操作
			byte[] result = cipher.doFinal(reqStr.getBytes());
			System.out.println("使用JDK加密DES:"+Hex.encodeHexString(result));
			
			//解密操作
			//依然使用cipher进行解密 需要的秘钥 也需要秘钥转换 
			cipher.init(cipher.DECRYPT_MODE, convertSecretKey);
			//执行加密操作 结果转成string
			result = cipher.doFinal(result);
			System.out.println("使用JDK解密DES:"+new String(result));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//bc的DES实现 可以通过secret添加provide进行实现 实现方式和jdk类似 这里不做赘述 
	
}
