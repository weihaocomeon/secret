package com.ztgeo.secret;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Hex;

public class DES3 {

	private static String reqStr ="需要被加密内容!!";
	
	public static void main(String[] args) {
				jdk3des();
	}
	
	public static void jdk3des(){
		//jdk的三重des和des实现都是类似的 我们直接copy代码  
		try {
			//生成秘钥材料
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
			//指定秘钥长度 3DES是168位
			//keyGenerator.init(168);
			//当然 如果不想记什么算法多少位  可以使用根据getInstance对字符串变能量获得默认长度的秘钥  
			keyGenerator.init(new SecureRandom());
			SecretKey key = keyGenerator.generateKey();
			//获得程序产生的key
			byte[] keyByte = key.getEncoded();
			
			
			//当然 可以自己指byte数组类型的秘钥 但目前报错 原因未知
			//byte[] keyByte = "1234123412341234".getBytes();      //秘钥
			
			
			//秘钥材料转换 
			DESedeKeySpec desKeySpec = new DESedeKeySpec(keyByte);
			
			//生成真正的key
			//实例化秘钥工厂(指定加密方式)
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
			Key convertSecretKey = factory.generateSecret(desKeySpec);
		
			//加密 (需要填充加解密的算法/工作方式/填充方式) 
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			//加密模式ENCRYPT_MODE 和转换可以使用的key
			cipher.init(cipher.ENCRYPT_MODE, convertSecretKey);
			//执行加密操作
			byte[] result = cipher.doFinal(reqStr.getBytes());
			System.out.println("使用JDK加密3DES:"+Hex.encodeHexString(result));
			
			//解密操作
			//依然使用cipher进行解密 需要的秘钥 也需要秘钥转换 
			cipher.init(cipher.DECRYPT_MODE, convertSecretKey);
			//执行加密操作 结果转成string
			result = cipher.doFinal(result);
			System.out.println("使用JDK解密3DES:"+new String(result));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
