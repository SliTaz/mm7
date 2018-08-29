package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

public class SGIP_TraceResp extends SGIP_Command {
    public int Count;
    public int Result;
    public String NodeId;
    public String ReceiveTime;
    public String SendTime;
    public String Reserve;

    public SGIP_TraceResp() {
    }

    public SGIP_TraceResp(SGIP_Command sgip_command) {
        super(sgip_command);
    }

    public int getCount() {
        return this.Count;
    }

    public int getResult() {
        return this.Result;
    }

    public String getNodeId() {
        return this.NodeId;
    }

    public String getReceiveTime() {
        return this.ReceiveTime;
    }

    public String SendTime() {
        return this.SendTime;
    }

    public int readbody() {
        this.byteBody.clear();
        this.Count = this.byteBody.get(0);
        this.Result = this.byteBody.get(1);
        this.NodeId = new String(this.byteBody.array(), 2, 6);
        this.ReceiveTime = new String(this.byteBody.array(), 8, 16);
        this.SendTime = new String(this.byteBody.array(), 24, 16);
        return 0;
    }
}
