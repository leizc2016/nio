package com.lzc.nettydemo4;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {

	public void bind(int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup);
		b.channel(NioServerSocketChannel.class);
		b.option(ChannelOption.SO_BACKLOG, 1024);
		try {
			b.childHandler(new ChildChannelHandler());

			ChannelFuture f = b.bind(port).sync();

			f.channel().closeFuture().sync();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel arg) throws Exception {
			// 服务端，对请求解码
			arg.pipeline().addLast("http-decoder", new HttpRequestDecoder());
			// 聚合器，把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
			arg.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
			// 服务端，对响应编码
			arg.pipeline().addLast("http-encoder", new HttpResponseEncoder());

			// 块写入处理器
			arg.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
			
			 // 自定义服务端处理器  
            arg.pipeline().addLast("fileServerHandler",  
                    new HttpFileServerHandler()); 

		}

	}
	
	
	public static void main(String[] args) {
		System.out.println("******************服务端-**************");
		int port = 9090;
		try {
			new HttpFileServer().bind(port);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
