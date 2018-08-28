package com.zbensoft.mmsmp.sms.ra.sgip.client;

import com.zbensoft.mmsmp.sms.ra.sgip.pkg.SgipPackage;

import java.io.*;
import java.net.Socket;

public class SocketUtil {
    Socket socket = null;

    public SocketUtil(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
    }

    public void sentSocket(SgipPackage socketPacket) throws Exception {

        OutputStream output = socket.getOutputStream();
        output.write(socketPacket.pack("UTF-8"));
        //获得服务端返回的数据
        InputStream input = socket.getInputStream();
        // 从服务端程序接收数据
        InputStreamReader ipsr = new InputStreamReader(input);
        BufferedReader br = new BufferedReader(ipsr);
        String s = "";
        while((s = br.readLine()) != null)
            System.out.println(s);

        input.close();
        output.close();
        socket.close();

    }

    public byte[] streamToBytes(InputStream inputStream, int len) {
        /**
         * inputStream.read(要复制到得字节数组,起始位置下标,要复制的长度)
         * 该方法读取后input的下标会自动的后移，下次读取的时候还是从上次读取后移动到的下标开始读取
         * 所以每次读取后就不需要在制定起始的下标了
         */
        byte[] bytes = new byte[len];
        try {
            inputStream.read(bytes, 0, len);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }
}
