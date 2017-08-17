package com.ztgeo.secret;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.crypto.digests.MD5Digest;

public class MDDemo {
	
//md家族的加解密算法
	
	private static String reqStr= "MD5需要加密的数据";
	
	public static void main(String[] args) {
		secretMD5();
		//BCMD4();
		BCMD5();
		ccMD5();
	}
	
	
	public static void secretMD5(){
		try {
			//使用原生jdk就可以进行md5的加密
			MessageDigest md = MessageDigest.getInstance("MD5");
			//进行消息摘要(md5加密)
			byte[] md5Bytes =md.digest(reqStr.getBytes());
			System.out.println("md5加密后未经转换的byte[]后的结果:"+md5Bytes);	
			System.out.println("md5加密后转换成string后的结果:"+new String(md5Bytes));	
			//转换后的长度没有128 只有11位 就算转换成string也是如下乱码:����'�?9�O�s4
			//所以md5加密后的数据 不能直接使用 应该使用16进制转换后的结果作为加密结果  
			
			//恰巧bc和cc中都有该功能的方法 一般选择cc中方法  
			
			System.out.println("转换成16进制之后的加密数组:"+Hex.encodeHexString(md5Bytes));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void BCMD4(){
		//使用bc包实现md4的算法 加密  
		Digest digest = new MD4Digest();
		//预处理 md4需要加密的字符串(类似读取 输入流)
		digest.update(reqStr.getBytes(), 0,reqStr.getBytes().length);
		//使用byte数组去接收结果 相当于处理完了 用容器去承载结果  
		byte[] md4Byte = new byte[digest.getDigestSize()];
		///读出到输入流
		digest.doFinal(md4Byte, 0);
		//这个时候结果容器被装满  使用bc自带的64位转码进行转码  
		System.out.println("BC加密后的MD4加密结果:"+org.bouncycastle.util.encoders.Hex.toHexString(md4Byte));
	
	}
	
	//bc包也提供了md5的加解密方法 和md4的实现大同小异 
	private static void BCMD5(){
		//使用bc包实现md4的算法 加密  
		Digest digest = new MD5Digest();
		//预处理 md4需要加密的字符串(类似读取 输入流)
		digest.update(reqStr.getBytes(), 0,reqStr.getBytes().length);
		//使用byte数组去接收结果 相当于处理完了 用容器去承载结果  
		byte[] md4Byte = new byte[digest.getDigestSize()];
		///读出到输入流
		digest.doFinal(md4Byte, 0);
		//这个时候结果容器被装满  使用bc自带的64位转码进行转码  
		System.out.println("BC加密后的MD5加密结果:"+org.bouncycastle.util.encoders.Hex.toHexString(md4Byte));
	
	}

	//使用cc实现md的加密
	public static void ccMD5(){
		//这里需要提的而是 cc实现 md5的方法中 有个方法简化了hex转化64位的操作
		//如果看源码会发现 实际上cc并没有去自己写md的实现 只是引用来了jdk的md相应实现
		System.out.println("CCmd5的简单实现:"+DigestUtils.md5Hex(reqStr.getBytes()));
	}
	
}
