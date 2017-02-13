package com.lzc.nettydemo3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter {

	private String content = null;

	public TimeClientHandler() {
		content = "ABC";

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		for (int i = 1; i < 101; i++) {
			String current = "[ " + i + " ]" + content+"L";
			byte[] req = current.getBytes();
			System.out.println(new String(req,"UTF-8"));
			ByteBuf firstMessage = Unpooled.buffer(req.length);
			firstMessage.writeBytes(req);
			ctx.writeAndFlush(firstMessage);
			System.out.println("发送完毕");
		}
	}

	@Override
	@Skip
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println(cause);
		ctx.close();
	}

	@Override
	@Skip
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//ByteBuf buf = (ByteBuf) msg;
		//byte[] req = new byte[buf.readableBytes()];
		//buf.readBytes(req);
		//String body = new String(req, "UTF-8");
		String body =(String) msg;
		System.out.println("get****:" + body);
	}

}
