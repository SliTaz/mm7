package com.zbensoft.mmsmp.sms.ra.pkg;

import com.zbensoft.mmsmp.sms.ra.sgip.client.SocketUtil;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.MsgId;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipHead;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipPackage;
import com.zbensoft.mmsmp.sms.ra.sgip.pkg.body.BindBody;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

public class SendTest {
    @Test
    public void testBind() throws Exception {
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


        SocketUtil util = new SocketUtil("192.168.1.116",8801);
        util.sentSocket(packagee);
        System.out.println(new String(packagee.pack("UTF-8"), "UTF-8"));

//        SgipPackage packagee2 = new SgipPackage();
//        String str = "name      000000true0000000111http://asd000012.121content             ";
//        packagee2.unPack(str.getBytes("GBK"), "GBK");
    }

    @Test
    public void testBind2() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect("192.168.1.116", 8801).sync(); // (5)
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();

            if (f.isSuccess()) {
                SocketChannel socketChannel = (SocketChannel)f.channel();

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

                socketChannel.writeAndFlush(packagee.pack("UTF-8"));
                System.out.println("connect server  成功---------");
            }else{
                System.out.println("连接失败！");
            }
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}

class TimeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg; // (1)
        try {
            System.out.println("AAAAAAAAAAAAAAAAAAAAA");
            ctx.close();
        } finally {
            m.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
