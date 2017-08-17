package com.ztgeo.secret;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

public class MacDemo {     
//mac安全散列算法的实现(有秘钥)
	
	private static String reqStr="需要被mac摘要的数据";
	
	public static void main(String[] args) {
		jdkHmacMD5();
		bHmacMD5();
	}
	
	public static void jdkHmacMD5(){
		
		try {
			//获得秘钥工厂  
			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
			
			//一下两步是是随机生产key  但实际情况 需要制定key
			/*//生成秘钥  
			SecretKey key = keyGenerator.generateKey();
			//获得秘钥
			byte[] keyByte = key.getEncoded();*/
			
			byte[] keyByte = org.apache.commons.codec.binary.Hex.decodeHex(new char[]{'a','a','a','a','a','a','a','a','a','a'});
			
			//还原秘钥 数组秘钥 通过类进行还原
			SecretKey restoreKey = new SecretKeySpec(keyByte, "HmacMD5");
			//实例化MAC 使用秘钥还原
			Mac mac = Mac.getInstance(restoreKey.getAlgorithm());
			//初始化mac
			mac.init(restoreKey);
			//执行加密(摘要) 
			byte[] HmacMd5Bytes = mac.doFinal(reqStr.getBytes());
			//输出摘要的结果
			System.out.println("jkd hmacMD5结果:"+Hex.toHexString(HmacMd5Bytes));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//bc实现mac md5算法  
	public static void bHmacMD5(){
		//实例化mac
		HMac hmac = new HMac(new MD5Digest());
		//初始化mac
		hmac.init(new KeyParameter(Hex.decode("aaaaaaaaaa")));
		//读入流
		hmac.update(reqStr.getBytes(),0,reqStr.getBytes().length);
		
		//准备容器 
		byte[] hmacMD5Bytes = new byte[hmac.getMacSize()];
		
		//放置输出结果
		hmac.doFinal(hmacMD5Bytes, 0);
		
		//输出结果
		System.out.println("bcHmacMD5结果:"+Hex.toHexString(hmacMD5Bytes));
		
	}

}
