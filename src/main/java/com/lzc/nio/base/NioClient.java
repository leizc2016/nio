package com.lzc.nio.base;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioClient {

	private Selector selector;

	public void init() {

		try {
			// 获得一个Socket通道
			SocketChannel channel = SocketChannel.open();
			// 设置通道为非阻塞
			channel.configureBlocking(false);

			// 获得一个通道管理器
			this.selector = Selector.open();

			// 客户端连接服务器,其实方法执行并没有实现连接，需要在listen（）方法中调
			// 用channel.finishConnect();才能完成连接
			channel.connect(new InetSocketAddress("127.0.0.1", 8181));
			// 将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_CONNECT事件。
			channel.register(selector, SelectionKey.OP_CONNECT);
			System.out.println("客户端初始化");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void listen() {
		System.out.println("客户端监听中...");
		while (true) {
			try {
				selector.select();
				Iterator ite = this.selector.selectedKeys().iterator();
				while (ite.hasNext()) {
					SelectionKey key = (SelectionKey) ite.next();
					// 删除已选的key,以防重复处理
					ite.remove();
					if (key.isConnectable()) {
						SocketChannel channel = (SocketChannel) key.channel();
						channel.finishConnect();
						// 在这里可以给服务端发送信息哦
						channel.write(ByteBuffer.wrap(new String("i am client...").getBytes()));
						channel.register(selector, SelectionKey.OP_READ);
					} else if (key.isReadable()) {
						read(key);
					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void read(SelectionKey key) {
		// 服务器可读取消息:得到事件发生的Socket通道
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		try {
			channel.read(byteBuffer);
			byteBuffer.flip();
			byte[] bytes = new byte[byteBuffer.remaining()];
			byteBuffer.get(bytes);
			String body = new String(bytes, "UTF-8");
			System.out.println("客户端收到服务端：" + body);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		NioClient client = new NioClient();
		client.init();
		client.listen();
	}
}
