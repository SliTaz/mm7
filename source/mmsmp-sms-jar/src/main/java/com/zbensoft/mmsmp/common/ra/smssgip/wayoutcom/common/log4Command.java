package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common;

import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cmppapi.CMPP_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.cngpapi.CNGP_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Report;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi.SGIP_Submit;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Deliver;
import com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smgpapi.SMGP_Submit;

public class log4Command {
    private String content;
    private Object comm;
    private Object[] command;
    private boolean flag;

    public log4Command(Object comm) {
        this.comm = comm;
        this.flag = false;
    }

    public log4Command(Object[] comm) {
        this.command = comm;
        this.flag = true;
    }

    private StringBuffer formContext(SGIP_Submit comm) {
        StringBuffer buffer = new StringBuffer();
        comm.readbody();
        buffer.append("seq1:");
        buffer.append(comm.getSeqno_1());
        buffer.append(" seq2:");
        buffer.append(comm.getSeqno_2());
        buffer.append(" seq3:");
        buffer.append(comm.getSeqno_3());
        buffer.append(" FeeType:");
        buffer.append(comm.getFeeType());
        buffer.append(" FeeValue:");
        buffer.append(comm.getFeeValue());
        buffer.append(" SPNumber:");
        buffer.append(comm.getSPNumber());
        buffer.append(" UserNumber:");
        String[] num = comm.getUserNumber();

        for(int i = 0; i < num.length; ++i) {
            buffer.append(num[i]);
            buffer.append("|");
        }

        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append("\n");
        return buffer;
    }

    private StringBuffer formContext(SGIP_Report comm) {
        StringBuffer buffer = new StringBuffer();
        comm.readbody();
        buffer.append("seq1:");
        buffer.append(comm.getSeqno_1());
        buffer.append(" seq2:");
        buffer.append(comm.getSeqno_2());
        buffer.append(" seq3:");
        buffer.append(comm.getSeqno_3());
        buffer.append(" cseq1:");
        buffer.append(comm.getSeq_1());
        buffer.append(" cseq2:");
        buffer.append(comm.getSeq_2());
        buffer.append(" cseq3:");
        buffer.append(comm.getSeq_3());
        buffer.append(" UserNumber:");
        buffer.append(comm.getUserNumber());
        buffer.append("\n");
        return buffer;
    }

    private StringBuffer formContext(SGIP_Deliver comm) {
        StringBuffer buffer = new StringBuffer();
        comm.readbody();
        buffer.append("seq1:");
        buffer.append(comm.getSeqno_1());
        buffer.append(" seq2:");
        buffer.append(comm.getSeqno_2());
        buffer.append(" seq3:");
        buffer.append(comm.getSeqno_3());
        buffer.append(" SPNumber:");
        buffer.append(comm.getSPNumber());
        buffer.append(" UserNumber:");
        buffer.append(comm.getUserNumber());
        buffer.append(" Message:");
        buffer.append(comm.getMessageContent());
        buffer.append("\n");
        return buffer;
    }

    private StringBuffer formContext(SMGP_Submit comm) {
        StringBuffer buffer = new StringBuffer();
        int seq = comm.getSeqID();
        buffer.append("seq:");
        buffer.append(seq);
        buffer.append("\n");
        return buffer;
    }

    private StringBuffer formContext(SMGP_Deliver comm) {
        StringBuffer buffer = new StringBuffer();
        int seq = comm.getSeqID();
        buffer.append("seq:");
        buffer.append(seq);
        buffer.append("\n");
        return buffer;
    }

    private StringBuffer formContext(CNGP_Submit comm) {
        StringBuffer buffer = new StringBuffer(comm.toString());
        buffer.append("\n");
        return buffer;
    }

    private StringBuffer formContext(CNGP_Deliver comm) {
        StringBuffer buffer = new StringBuffer(comm.toString());
        buffer.append("\n");
        return buffer;
    }

    private StringBuffer formContext(CMPP_Submit comm) {
        StringBuffer buffer = new StringBuffer();
        int seq = comm.getSeqID();
        buffer.append("seq:");
        buffer.append(seq);
        buffer.append("\n");
        return buffer;
    }

    private StringBuffer formContext(CMPP_Deliver comm) {
        StringBuffer buffer = new StringBuffer();
        int seq = comm.getSeqID();
        buffer.append("seq:");
        buffer.append(seq);
        buffer.append("\n");
        return buffer;
    }

    public String toLog() throws Exception {
        StringBuffer buffer = new StringBuffer();
        if (this.flag) {
            int i;
            if (this.command instanceof SGIP_Submit[]) {
                for(i = 0; i < this.command.length; ++i) {
                    buffer.append(this.formContext((SGIP_Submit)this.command[i]));
                }
            } else if (this.command instanceof SGIP_Report[]) {
                for(i = 0; i < this.command.length; ++i) {
                    buffer.append(this.formContext((SGIP_Report)this.command[i]));
                }
            } else if (this.command instanceof SGIP_Deliver[]) {
                for(i = 0; i < this.command.length; ++i) {
                    buffer.append(this.formContext((SGIP_Deliver)this.command[i]));
                }
            } else if (this.command instanceof SMGP_Submit[]) {
                for(i = 0; i < this.command.length; ++i) {
                    buffer.append(this.formContext((SMGP_Submit)this.command[i]));
                }
            } else if (this.command instanceof SMGP_Deliver[]) {
                for(i = 0; i < this.command.length; ++i) {
                    buffer.append(this.formContext((SMGP_Deliver)this.command[i]));
                }
            } else if (this.command instanceof CNGP_Submit[]) {
                for(i = 0; i < this.command.length; ++i) {
                    buffer.append(this.formContext((CNGP_Submit)this.command[i]));
                }
            } else if (this.command instanceof CNGP_Deliver[]) {
                for(i = 0; i < this.command.length; ++i) {
                    buffer.append(this.formContext((CNGP_Deliver)this.command[i]));
                }
            } else if (this.command instanceof CMPP_Submit[]) {
                for(i = 0; i < this.command.length; ++i) {
                    buffer.append(this.formContext((CMPP_Submit)this.command[i]));
                }
            } else {
                if (!(this.command instanceof CMPP_Deliver[])) {
                    throw new Exception("Unsupported Command[]");
                }

                for(i = 0; i < this.command.length; ++i) {
                    buffer.append(this.formContext((CMPP_Deliver)this.command[i]));
                }
            }

            this.content = buffer.toString();
        } else {
            if (this.comm instanceof SGIP_Submit) {
                buffer.append(this.formContext((SGIP_Submit)this.comm));
            } else if (this.comm instanceof SGIP_Report) {
                buffer.append(this.formContext((SGIP_Report)this.comm));
            } else {
                if (!(this.comm instanceof SGIP_Deliver)) {
                    throw new Exception("Unsupported SGIP_Command");
                }

                buffer.append(this.formContext((SGIP_Deliver)this.comm));
            }

            this.content = buffer.toString();
        }

        return this.content;
    }
}
