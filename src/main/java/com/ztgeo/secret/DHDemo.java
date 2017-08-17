package com.ztgeo.secret;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Base64;

//非对称加密 
public class DHDemo {
	private static String reqStr = "需要被加密的数据123456";

	public static void main(String[] args) {
		try {
		//1.初始化发送方的秘钥	
			
			//钥匙对工厂产生钥匙对
			KeyPairGenerator sendKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			sendKeyPairGenerator.initialize(512);
			//钥匙对 用来存放公钥和私钥
			KeyPair sendKeyPair =sendKeyPairGenerator.generateKeyPair();
			//获得公钥载体 发送给接收方 (网络,文件...)
			byte[] sendPublicKeyEnc = sendKeyPair.getPublic().getEncoded();
			
			
		//2.初始化接收方秘钥 推算出 公钥 
			KeyFactory reciverKeyFactory  = KeyFactory.getInstance("DH");
			//秘钥标准
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(sendPublicKeyEnc);
			//通过标准和 公钥载体 获得公钥
			PublicKey reciverPublicKey = reciverKeyFactory.generatePublic(x509EncodedKeySpec);
			//获得公钥参数 用来构建钥匙对
			DHParameterSpec dhParameterSpec = ((DHPublicKey)reciverPublicKey).getParams();
			
			//从发送方载体中获得了公钥参数 可以构建 参数对了
			KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			receiverKeyPairGenerator.initialize(dhParameterSpec);
			KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();
			//接收方 公钥载体
			byte[] receiverPublicKeyEnc = receiverKeyPair.getPublic().getEncoded();
			
		//3.秘钥构建(私钥)  具体可分为 发送方的私钥构建 和接收方的 私钥构建
			//接受方
			KeyAgreement receviverKeyAgreement = KeyAgreement.getInstance("DH");
			//使用私钥进行初始化
			receviverKeyAgreement.init(receiverKeyPair.getPrivate());
			receviverKeyAgreement.doPhase(reciverPublicKey, true);
			//本地私钥产生 并发送
			SecretKey receiverDesKey = receviverKeyAgreement.generateSecret("DES");
		
			//发送方(根据接收方 第一次返回的公钥载体进行生成私钥 并反推成本地秘钥)
			KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
			x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
			PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
			KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
			senderKeyAgreement.init(sendKeyPair.getPrivate());
			senderKeyAgreement.doPhase(senderPublicKey, true);
			SecretKey senderDesKey = senderKeyAgreement.generateSecret("DES");
		
			//接收方 根据发送方私钥产生了 本地秘钥  
			//发送方 也根据接收方传过来的 公钥 产生了本地秘钥 
			
			//验证 双方本地秘钥是否相同  
			if(Objects.equals(receiverDesKey, senderDesKey)){
				System.out.println("验证成功,双方产生的本地秘钥相同!!");
			}
			
		//4.加密数据 
			//发送方根据 本地 私钥 加密数据  
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
			byte[] result = cipher.doFinal(reqStr.getBytes());
			System.out.println("使用DH加密的数据是:"+Base64.encodeBase64String(result));
			
		//5.客户端解密数据  
			//接收方根据 本地秘钥进行解密
			cipher.init(Cipher.DECRYPT_MODE, receiverDesKey);
		    result = cipher.doFinal(result);
		    System.out.println("使用DH解密后的数据是:"+new String(result));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

}
