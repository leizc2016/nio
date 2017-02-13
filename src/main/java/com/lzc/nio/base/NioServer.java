package com.lzc.nio.base;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer {

	private ServerSocketChannel serverChannel = null;

	private Selector selector;

	public void init() {
		try {
			// 用于监听客户端连接
			serverChannel = ServerSocketChannel.open();
			serverChannel.bind(new InetSocketAddress(8181), 1024);
			serverChannel.configureBlocking(false);

			selector = Selector.open();

			// 我的当前reactor【selector】对于serverChannel而言，只对客户端连接事件感兴趣
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			System.out.println("服务端初始化");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listen() {
		System.out.println("服务端监听中...");
		try {

			while (true) {
				selector.select(1000);
				// 获得selector中选中的项的迭代器，选中的项为注册的事件
				Iterator ite = this.selector.selectedKeys().iterator();
				while (ite.hasNext()) {
					SelectionKey key = (SelectionKey) ite.next();
					// 删除已选的key,以防重复处理
					ite.remove();

					

					if ( key.isAcceptable()) {
						ServerSocketChannel servChanel = (ServerSocketChannel) key.channel();
						// 获得和客户端连接的通道
						SocketChannel channel = servChanel.accept();

						// 设置成非阻塞
						channel.configureBlocking(false);

						channel.register(selector, SelectionKey.OP_READ);
					} else if (key.isReadable()) {
						read(key);
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
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
			System.out.println("服务端收到客户务端：" + body);

			ByteBuffer outBuffer = ByteBuffer.wrap(bytes);
			channel.write(outBuffer);// 将消息回送给客户端
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		NioServer server = new NioServer();
		server.init();

		server.listen();
	}

}
