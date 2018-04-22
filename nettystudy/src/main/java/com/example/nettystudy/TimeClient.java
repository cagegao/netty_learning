package com.example.nettystudy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {

	public static void main(String[] args) throws Exception {
		String host = "localhost";
		int port = 7788;
		
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup)
			 .channel(NioSocketChannel.class)
			 .option(ChannelOption.SO_KEEPALIVE, true)
			 .handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// 1.
					// ch.pipeline().addLast(new TimeClientHandler());
					// 2.
					// ch.pipeline().addLast(new TimeClientHandler2());
					// 3.
					// ch.pipeline().addLast(new TimeDecoder(), new TimeClientHandler());
					// 4.
					ch.pipeline().addLast(new TimeDecoder2(), new TimeClientHandler3());
				}
			});
			// 启动客户端
			ChannelFuture f = b.connect(host, port).sync();
			// 等待连接关闭
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
