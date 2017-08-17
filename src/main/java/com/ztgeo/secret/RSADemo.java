package com.ztgeo.secret;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

import com.sun.org.apache.xml.internal.security.encryption.CipherData;

public class RSADemo {
	
	private static String reqStr = "需要被加解密的字符串!!12345689";
	
	public static void main(String[] args) {
		jdkRSA();
	}
	
	public static void jdkRSA(){
		try {
		//1.初始化秘钥  
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(512);
			//公钥 私钥
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			RSAPublicKey rsaPublicKey = (RSAPublicKey)keyPair.getPublic();
			RSAPrivateKey rasPrivateKey = (RSAPrivateKey)keyPair.getPrivate();
			System.out.println("公钥:"+Base64.encodeBase64String(rsaPublicKey.getEncoded()));
			System.out.println("私钥:"+Base64.encodeBase64String(rasPrivateKey.getEncoded()));
		//2.使用私钥加密
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rasPrivateKey.getEncoded());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);//私钥加密
			byte[] result = cipher.doFinal(reqStr.getBytes());
			System.out.println("RSA私钥加密后的结果:"+Base64.encodeBase64String(result));
			
		//3.发送发 发送公钥 接收方使用公钥解密 密文  
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
			keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			result = cipher.doFinal(result);
			System.out.println("公钥解密的结果是:"+new String(result));
			
			
			
		//4.可以使用公钥加密
			
		//5.使用私钥解密
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
