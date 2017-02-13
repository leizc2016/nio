package com.lzc.nettydemo4;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;  

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class HttpFileServerHandler extends  SimpleChannelInboundHandler<FullHttpRequest>{

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		 if (!request.decoderResult().isSuccess()) {  
	            sendError(ctx, BAD_REQUEST);  
	            return;  
	        }  
		 
		 ByteBuf buf =  request.content();  
		 byte [] req = new byte[buf.readableBytes()];
		 buf.readBytes(req);    
		 String uri = request.uri();
		 test(ctx, uri);
	     //String xml = new String(req,"UTF-8"); 
	     //resp(ctx, xml);
	}
	
	 private void test(ChannelHandlerContext ctx, String uri){ 
		 StringBuffer bf =new StringBuffer();
		 bf.append("<html><body>");
		 bf.append("<ul><li>"+uri+"</li></ul>");
		 bf.append("</body></html>");
		    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,  
		    		HttpResponseStatus.OK);  
	        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
	        ByteBuf buf = Unpooled.copiedBuffer(bf.toString(),CharsetUtil.UTF_8);
	        response.content().writeBytes(buf);
			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE); 
	 }
	
	/** 
     *  
     * @param xml 
     */  
    private void resp(ChannelHandlerContext ctx, String xml){  
        String transCode = Dom4JUtil.header(xml, "transcode");  
        String retUrl = "D:\\workspaces\\eclipse-huifu\\emulator\\xml\\error.xml";  
        String retCtt = null;  
        if(equal(transCode, Constants.TC_DZZH)){//电子账户  
              
            retUrl = "D:\\workspaces\\eclipse-huifu\\emulator\\xml\\account\\manage\\resp.xml";  
          
        }else if(equal(transCode, Constants.TC_YHKBD)){//银行卡绑定  
              
        }  
          
        try {  
            retCtt = FileUtils.readFileToString(new File(retUrl));  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,  
                HttpResponseStatus.FOUND, Unpooled.copiedBuffer(retCtt, CharsetUtil.UTF_8));  
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");  
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);  
          
    }  
      
      
    public static boolean equal(String var, String cons){  
        return isNotEmpty(var) && cons.equals(var);       
    }  
      
    private static boolean isNotEmpty(String s){  
        return (null != s && !"".equals(s));  
    }  
	
	  /** 
     * 错误处理 
     * @param ctx 
     * @param status 
     */  
    private static void sendError(ChannelHandlerContext ctx,  
            HttpResponseStatus status) {  
        String ret =  null;  
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,  
                status, Unpooled.copiedBuffer(ret, CharsetUtil.UTF_8));  
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/xml; charset=UTF-8");  
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);  
    }  
  

}
