package com.lzc.serizable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 二进制数据要转成字符串来传输是需要经过编码(例如BASE64)处理才可以的;
 * 
 * 1.在发送端用BASE64Encoder将二进制数据编码成字符串后再发送; byte[] bt = <bt是读取到的图片的二制数据>; String
 * temp = new sun.misc.BASE64Encoder().encodeBuffer(bt);
 * 
 * 2.在接收端用BASE64Decoder对接收到的字符串解码成二进制数据;再输出生成图片; byte[] bt = new
 * sun.misc.BASE64Decoder().decodeBuffer(temp);
 * 
 * @author LeiZhicheng
 * 
 * @date：2017-1-23
 */
public class UserSerizableHelper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ObjectOutputStream obj = null;
		User user = new User();
		user.setName("lzc");
		user.setPwd("04324108");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			obj = new ObjectOutputStream(out);
			obj.writeObject(user);
			System.out.println(out.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] x = null;
		byte[] b = null;
		try {
			b = out.toByteArray();
			String s = new BASE64Encoder().encode(b);
			System.out.println(s);

			x = new BASE64Decoder().decodeBuffer(s);
		} catch (Exception e) {
			// TODO: handle exception
		}
		ObjectInputStream obj1 = null;
		InputStream in = new ByteArrayInputStream(x);
		try {
			obj1 = new ObjectInputStream(in);
			User user1 = (User) obj1.readObject();
			System.out.println(user1.getPwd());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
