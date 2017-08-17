package com.ztgeo.secret;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;

//SHA的算法实现  
public class SHADemo {
	private static String reqStr = "实现SHA的加密";
	public static void main(String[] args) {
		jdkSHA1();
		bcSHA1();
		bcSHA224();
		ccSHA();
	}
	
	//jdk原生算法
	public static void jdkSHA1(){
		try {
			//依然使用jdk的消息摘要类 其中sha代表了sha1
			MessageDigest md = MessageDigest.getInstance("SHA");
			//相当于读流
			md.update(reqStr.getBytes());
			System.out.println("JDK实现SHA1:"+Hex.encodeHexString(md.digest()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		
	}
	
	//bc中实现sha
	public static void bcSHA1(){
		Digest digest = new SHA1Digest();
		digest.update(reqStr.getBytes(),0,reqStr.getBytes().length);
		//读完流之后找容器接受 
		byte[] sha1byte = new byte[digest.getDigestSize()];
		digest.doFinal(sha1byte, 0);
		System.out.println("bc实现sha1:"+org.bouncycastle.util.encoders.Hex.toHexString(sha1byte));
		
	
	}
	
	//bc的hha224的算法(jdk和cc都不提供)
	public static void bcSHA224(){
		Digest digest = new SHA224Digest();
		digest.update(reqStr.getBytes(),0,reqStr.getBytes().length);
		//读完流之后找容器接受 
		byte[] sha1byte = new byte[digest.getDigestSize()];
		digest.doFinal(sha1byte, 0);
		System.out.println("bc实现sha224:"+org.bouncycastle.util.encoders.Hex.toHexString(sha1byte));
		
		
	}
	
	//使用cc实现sha算法  
	public static void ccSHA(){
		System.out.println("cc实现SHA1:"+DigestUtils.sha1Hex(reqStr.getBytes()));
		System.out.println("cc实现SHA2-256:"+DigestUtils.sha256Hex(reqStr.getBytes()));
	}

}
