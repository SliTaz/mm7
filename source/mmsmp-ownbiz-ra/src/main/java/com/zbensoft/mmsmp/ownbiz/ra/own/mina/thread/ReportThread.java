package com.zbensoft.mmsmp.ownbiz.ra.own.mina.thread;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.zbensoft.mmsmp.common.ra.common.message.MO_ReportMessage;
import com.zbensoft.mmsmp.ownbiz.ra.own.report.ReportToSpSender;

public class ReportThread implements Runnable {
    private static final Logger _log = Logger.getLogger(ReportThread.class);
    private static LinkedList<MO_ReportMessage> repotList = new LinkedList();

    public ReportThread() {
    }

    public static LinkedList<MO_ReportMessage> getRepotList() {
        LinkedList var0 = repotList;
        synchronized(repotList) {
            if (repotList == null) {
                repotList = new LinkedList();
            }

            return repotList;
        }
    }

    public void run() {
        while(true) {
            try {
                MO_ReportMessage morepot = null;
                if (getRepotList().size() > 0) {
                    morepot = (MO_ReportMessage)getRepotList().removeFirst();
                }

                if (morepot == null) {
                    Thread.sleep(100L);
                } else {
                    ReportToSpSender.getInstance().sendReportToSp(morepot);
                }
            } catch (Exception var2) {
                _log.error(var2.getMessage(), var2);
            }
        }
    }
}
