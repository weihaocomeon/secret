package com.ztgeo.secret;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

//加密解密的实现
public class Base64Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//jdkBase64();
		//commonsCodesBase64();
		boundcyCastleBase64();
		//base64的方法对比优势
		//使用原生的jdk需要new  而使用bc或者cc的方法 直接使用(静态方法	)
	}
	
	//使用原生的jdk实现base64
	public static void jdkBase64(){
		String reqStr = "BASE64加密信息(jdk原生方式)";
		
		//加密
		BASE64Encoder base64 = new BASE64Encoder();
		String resultStr = base64.encode(reqStr.getBytes());
		System.out.println("Base64位之后的数据:"+resultStr);
		
		try {
			//解密
			BASE64Decoder decode = new BASE64Decoder();
			String secretStr =new String(decode.decodeBuffer(resultStr));
			System.out.println("Base64位明文:"+secretStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	//使用阿帕奇commonscodec jar包实现 (cc) 需要引入jar包
	public static void commonsCodesBase64(){
		String reqStr = "BASE64加密信息(apache commonsCodec方式)";
		byte[] encodeByte =  Base64.encodeBase64(reqStr.getBytes());
		System.out.println("加密后的结果:"+new String(encodeByte));
		
		//解密 传入数组
		byte[] decodeByte = Base64.decodeBase64(encodeByte);
		System.out.println("解密后的数据是:"+new String(decodeByte));
	}

	
	//使用boundcyCastleBase64 (bc)的方式 需要引入jar包
	public static void boundcyCastleBase64(){
		String reqStr = "BASE64加密信息(boundcyCastleBase64方式)";
		//加密
		byte[] encodeByte = org.bouncycastle.util.encoders.Base64.encode(reqStr.getBytes());
		System.out.println("加密后的结果:"+new String(encodeByte));
		//这里的优势在于可以直接传入 string 避免了调用getByte()函数
		byte[] decodeByte =org.bouncycastle.util.encoders.Base64.decode(encodeByte);
		System.out.println("解密后的数据是:"+new String(decodeByte));
		}
}
