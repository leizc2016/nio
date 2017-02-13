package com.lzc.nettydemo3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Skip;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// super.exceptionCaught(ctx, cause);
		System.out.println(cause);
		ctx.close();
	}
	
	

	@Override
	@Skip
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive");
		super.channelActive(ctx);
	}



	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body =(String) msg;
		//ByteBuf buf = (ByteBuf) msg;
		//byte[] req = new byte[buf.readableBytes()];
		//buf.readBytes(req);
		//String body = new String(req, "UTF-8");
		System.out.println("get>>>：" + body);
		body = body+"\n";
		ByteBuf resp = Unpooled.copiedBuffer(body.getBytes());
		ctx.writeAndFlush(resp);
		
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("server flush");
		ctx.flush();
	}

}
