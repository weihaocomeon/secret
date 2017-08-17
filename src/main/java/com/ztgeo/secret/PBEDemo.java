package com.ztgeo.secret;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;


public class PBEDemo {

	private static String reqStr = "需要被加密的内容";
	
	public static void main(String[] args) {
			jdkPBE();
	}
	
	public static void jdkPBE(){
		try {
			//初始化盐 盐可以理解为随机数  
			SecureRandom random = new SecureRandom();
			//用8位 产生盐
			byte[] salt =  random.generateSeed(8);

			
			//口令转秘钥
			String password = "12345687";
			//密码转换成口令秘钥  
			PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
			Key key = factory.generateSecret(pbeKeySpec);
			
			//加密
			
			//将盐构成params
			PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, 100);
			Cipher cipher  = Cipher.getInstance("PBEWITHMD5andDES");
			cipher.init(Cipher.ENCRYPT_MODE, key,parameterSpec);
			byte[] byteResult = cipher.doFinal(reqStr.getBytes());
			System.out.println("jdk使用PBE加密后的结果:"+ Base64.encodeBase64String(byteResult));
			
			//解密
			
			cipher.init(Cipher.DECRYPT_MODE,key,parameterSpec);//秘钥 盐
			byteResult = cipher.doFinal(byteResult);
			System.out.println("jdk使用PBE解密后的结果:"+ new String(byteResult));
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}

}
