package com.lg.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServerStarter {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("启动netty websocket服务....");
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup childGroup=new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup,childGroup);
		serverBootstrap.channel(NioServerSocketChannel.class);
		serverBootstrap.childHandler(new MyWebSocketServerInitialize());
		ChannelFuture future = serverBootstrap.bind(8088).sync();
		future.channel().closeFuture().sync();
		bossGroup.shutdownGracefully();
		childGroup.shutdownGracefully();
	}
}
