package com.lg.netty;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{
	
	private static ChannelGroup group=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	protected void channelRead0(ChannelHandlerContext handlerContext, TextWebSocketFrame msg) throws Exception {
		String text = msg.text();
		//群发消息
		group.writeAndFlush(new TextWebSocketFrame(text));
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		super.handlerAdded(ctx);
		group.add(ctx.channel());
		System.out.println("新增链接:"+ctx.channel().id().asLongText());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		group.remove(ctx.channel());
		System.out.println("删除链接:"+ctx.channel().id().asLongText());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		ctx.channel().close();
		group.remove(ctx.channel());
	}
	
	
	
	
}
