package com.lzc.serizable;

import java.io.IOException;

import org.msgpack.MessagePack;

public class MessagePackTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		User user = new User();
		user.setName("lzc");
		user.setPwd("04324108");
		
		MessagePack pack =new MessagePack();
		try {
			byte[] bs=pack.write(user);
			String s = new String(bs);
			System.out.println(bs);
			User u=pack.read(bs, User.class);
			System.out.println(u.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
