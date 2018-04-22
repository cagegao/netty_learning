package com.example.nettystudy;

import java.net.SocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		SocketAddress remoteAddr = ctx.channel().remoteAddress();
		System.out.println(remoteAddr.toString() + ":");
		System.out.println("\t" + buf.toString(CharsetUtil.UTF_8));

		int count = 0;
		while (count < 3) {
			String response = "this a message from Server.";
			ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
			count++;
		}
	}

	// @Override
	// public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	// ctx.flush();
	// }

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
