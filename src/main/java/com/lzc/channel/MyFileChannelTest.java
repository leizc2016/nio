package com.lzc.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * 文件流
 * 
 * @author LeiZhicheng
 * 
 * @date：2017-1-10
 */
public class MyFileChannelTest {

	public static String getString(ByteBuffer buffer) {
		Charset charset = null;
		CharsetDecoder decoder = null;
		CharBuffer charBuffer = null;
		try {
			charset = Charset.forName("utf-8");
			decoder = charset.newDecoder();
			charBuffer = decoder.decode(buffer);
			return charBuffer.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			String path = new MyFileChannelTest().getClass().getResource("").getPath();
			System.out.println(path);
			RandomAccessFile randomAccessFile = new RandomAccessFile(path + "filedata.txt", "rw");
			FileChannel inChannel = randomAccessFile.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(48);
			int bytesRead = inChannel.read(buf);
			while (bytesRead != -1) {
				System.out.println("Read " + bytesRead);
				buf.flip();
				
				byte[] b = new byte[buf.limit()] ;
				buf.get(b);
				/*while (buf.hasRemaining()) {

					System.out.print(buf.asCharBuffer());
				}*/
				System.out.print(new String(b,"UTF-8"));

				buf.clear();
				bytesRead = inChannel.read(buf);
			}
			randomAccessFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
