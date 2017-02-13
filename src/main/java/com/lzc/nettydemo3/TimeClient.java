package com.lzc.nettydemo3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeClient {

	public void connect(int port, String host) throws Exception {

		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.option(ChannelOption.TCP_NODELAY, true);
		try {
			b.group(group).channel(NioSocketChannel.class);
			b.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel arg) throws Exception {
					arg.pipeline().addLast(new LineBasedFrameDecoder(500));
					arg.pipeline().addLast(new StringDecoder());
					arg.pipeline().addLast(new TimeClientHandler());

				}
			});

			ChannelFuture f = b.connect(host, port);
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}

	}

	public static void main(String[] args) {
		System.out.println(" *******************客户端*******************");
		int port = 9090;
		try {
			new TimeClient().connect(port, "localhost");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
