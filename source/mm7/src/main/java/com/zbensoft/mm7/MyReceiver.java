package com.zbensoft.mm7;

import com.cmcc.mm7.vasp.common.MMConstants;
import com.cmcc.mm7.vasp.conf.MM7Config;
import com.cmcc.mm7.vasp.message.MM7DeliverReq;
import com.cmcc.mm7.vasp.message.MM7DeliverRes;
import com.cmcc.mm7.vasp.message.MM7VASPRes;
import com.cmcc.mm7.vasp.service.MM7Receiver;
import com.cmcc.mm7.vasp.service.MM7Sender;

public class MyReceiver extends MM7Receiver {
    //定义一个MM7DeliverReq，以便得到MMSC发过来的Deliver消息。（必备）
    public static MM7DeliverReq deliverReq = new MM7DeliverReq();

    //Main方法
    public static void main(String[] args) throws Exception {
//初始化VASP
        MM7Config mm7Config = new MM7Config("mm7Config.xml");
//设置ConnConfig.xml文件的路径
        mm7Config.setConnConfigName("ConnConfig.xml");  //必备
//构造MyReceiver
        MyReceiver receiver = new MyReceiver();
        receiver.setConfig(mm7Config);   //必备
//创建MM7消息发送接口
        MM7Sender mm7Sender = new MM7Sender(mm7Config);
//启动接收器
        receiver.start();
        for (; ; ) ;
    }

    public MM7VASPRes doDeliver(MM7DeliverReq request) {
		/*接收从MMSC发过来的传送消息，以便取出其中的部分值构造提交消息或得到一些有用
信息，如MMSC的标识符等*/
        deliverReq = request;
        System.out.println("收到手机" + request.getSender()
                + "提交的消息，标题为：" + request.getSubject());
        System.out.println("MMSC的标识符为：" + request.getMMSRelayServerID());
        //SP需要进行一些处理，例如构建MM7DeliverRes消息，设置ServiceCode或StatusCode
        MM7DeliverRes mm7DeliverRes = new MM7DeliverRes();
        mm7DeliverRes.setServiceCode("服务代码"); //设置ServiceCode，可选
        mm7DeliverRes.setStatusCode(MMConstants.RequestStatus.SUCCESS);/*设置请求完成状
态，必备，以便表明SP已经接收到传送消息。一般设1000。*/
        mm7DeliverRes.setStatusText("所用状态文本说明");  /*设置所用状态的文本说明，
应限定请求状态，可选*/
        //返回给MM7 API，以便API将MM7DeliverRes返回给MMSC。
        return (MM7VASPRes) mm7DeliverRes;
    }

}
