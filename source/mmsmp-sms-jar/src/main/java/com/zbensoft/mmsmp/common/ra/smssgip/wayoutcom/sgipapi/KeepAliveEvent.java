package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.TimerTask;

public class KeepAliveEvent extends TimerTask {
    private Socket sc = null;
    private ByteBuffer byteAlive = null;
    private SGIP_Bind alive_Bind = null;

    public KeepAliveEvent(Socket sc) {
        this.sc = sc;
    }

    public void run() {
        this.alive_Bind = new SGIP_Bind(374076L, 11, "sungoalcrbt", "crbtsungoal");

        try {
            this.alive_Bind.write(this.sc);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        System.out.print(".");
    }
}