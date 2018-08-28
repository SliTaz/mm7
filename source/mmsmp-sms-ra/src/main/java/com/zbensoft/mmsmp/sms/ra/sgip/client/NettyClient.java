package com.zbensoft.mmsmp.sms.ra.sgip.client;

import com.zbensoft.mmsmp.sms.ra.sgip.pkg.MsgId;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipHead;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipPackage;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.body.BindBody;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;

public class NettyClient {
    private int port;
    private String host;
    public SocketChannel socketChannel;
    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);

    public NettyClient(int port, String host) {
        this.port = port;
        this.host = host;
        start();
    }

    private void start() {
        ChannelFuture future = null;
        try {
            EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.group(eventLoopGroup);
            bootstrap.remoteAddress(host, port);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new IdleStateHandler(20, 10, 0));
                    socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
                    socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                    socketChannel.pipeline().addLast(new SgipHandler());
                }
            });
            future = bootstrap.connect(host, port).sync();
            if (future.isSuccess()) {
                socketChannel = (SocketChannel) future.channel();
                System.out.println("connect server  成功---------");
            } else {
                System.out.println("连接失败！");
                System.out.println("准备重连！");
                start();
            }
        } catch (Exception e) {

        } finally {
//    		if(null != future){
//    			if(null != future.channel() && future.channel().isOpen()){
//    				future.channel().close();
//    			}
//    		}
//    		System.out.println("准备重连！");
//    		start();
        }
    }

    public static void main(String[] args) throws Exception {
        NettyClient bootstrap = new NettyClient(8801, "192.168.1.8");


        SgipHead head = new SgipHead();

        head.setCommandId(MsgId.BusinessId.SGIP_BIND);
        head.setMessageLength(4);
        head.setSequenceNumber(12);

        BindBody body = new BindBody();
        body.setLoginName("test");
        body.setLoginPassword("test");
        body.setLoginType(1);

        SgipPackage packagee = new SgipPackage();
        packagee.setT1(head);
        packagee.setT2(body);
        bootstrap.socketChannel.writeAndFlush("AAA");
        bootstrap.socketChannel.writeAndFlush(packagee.pack("UTF-8"));
    }

}
