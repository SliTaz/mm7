package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

public class SGIP_Common {
    public static long nSeqNo = 0L;
    public static final int LEN_SGIP_HEADER = 20;
    public static final int LEN_SGIP_BIND = 41;
    public static final int LEN_SGIP_BIND_RESP = 9;
    public static final int LEN_SGIP_UNBIND = 0;
    public static final int LEN_SGIP_UNBIND_RESP = 0;
    public static final int LEN_SGIP_SUBMIT = 123;
    public static final int LEN_SGIP_SUBMIT_RESP = 9;
    public static final int LEN_SGIP_DELIVER = 57;
    public static final int LEN_SGIP_DELIVER_RESP = 9;
    public static final int LEN_SGIP_REPORT = 44;
    public static final int LEN_SGIP_REPORT_RESP = 9;
    public static final int LEN_SGIP_USERRPT = 51;
    public static final int LEN_SGIP_USERRPT_RESP = 9;
    public static final int LEN_SGIP_TRACE = 41;
    public static final int LEN_SGIP_TRACE_RESP = 48;
    public static final int LEN_MAX_CONTENT = 160;
    public static final int TIMEOUT = 60000;
    public static final int SUBMIT_OK = 0;
    public static final int SUBMIT_ERROR_STRUCTURE = 1;
    public static final int SUBMIT_ERROR_COMMANDTYPE = 2;
    public static final int SUBMIT_ERROR_SEQ_DUPLICATE = 3;
    public static final int SUBMIT_ERROR_MSG_LENGTH = 4;
    public static final int SUBMIT_ERROR_FEECODE = 5;
    public static final int SUBMIT_ERROR_CONTENT_LENGTH_EXCEED = 6;
    public static final int SUBMIT_ERROR_SERVERID = 7;
    public static final int SUBMIT_ERROR_FLOW_CONTROL = 8;
    public static final int SUBMIT_ERROR_OTHERS = 9;
    public static final int ID_SGIP_BIND = 1;
    public static final int ID_SGIP_BIND_RESP = -2147483647;
    public static final int ID_SGIP_UNBIND = 2;
    public static final int ID_SGIP_UNBIND_RESP = -2147483646;
    public static final int ID_SGIP_SUBMIT = 3;
    public static final int ID_SGIP_SUBMIT_RESP = -2147483645;
    public static final int ID_SGIP_DELIVER = 4;
    public static final int ID_SGIP_DELIVER_RESP = -2147483644;
    public static final int ID_SGIP_REPORT = 5;
    public static final int ID_SGIP_REPORT_RESP = -2147483643;
    public static final int ID_SGIP_USERRPT = 17;
    public static final int ID_SGIP_USERRPT_RESP = -2147483631;
    public static final int ID_SGIP_TRACE = 4096;
    public static final int ID_SGIP_TRACE_RESP = -2147479552;
    public static final int MSG_TYPE_ASCII = 0;
    public static final int MSG_TYPE_WRITECARD = 3;
    public static final int MSG_TYPE_BINARY = 4;
    public static final int MSG_TYPE_UCS2 = 8;
    public static final int MSG_TYPE_CHINESE = 15;
    public static final int READ_SGIP_INVALID = 0;
    public static final int READ_SGIP_DELIVER = 1;
    public static final int READ_SGIP_REPORT = 2;
    public static final int READ_SGIP_USERRPT = 3;
    public static final int READ_SGIP_UNBIND = 4;

    public SGIP_Common() {
    }

    public static void MsgTrace(String str) {
        System.out.println(str);
    }

    protected static int ByteToInt(byte byte0) {
        return byte0;
    }

    protected static byte IntToByte(int i) {
        return (byte)i;
    }

    protected static int BytesToInt(byte[] abyte0) {
        return ((255 & abyte0[0]) << 8) + abyte0[1];
    }

    protected static byte[] IntToBytes(int i) {
        byte[] abyte0 = new byte[]{(byte)(('\uff00' & i) >> 8), (byte)(255 & i)};
        return abyte0;
    }

    protected static byte[] IntToBytes4(int i) {
        byte[] abyte0 = new byte[]{(byte)((-16777216 & i) >> 24), (byte)((16711680 & i) >> 16), (byte)(('\uff00' & i) >> 8), (byte)(255 & i)};
        return abyte0;
    }

    protected static byte[] LongToBytes4(long l) {
        byte[] abyte0 = new byte[]{(byte)((int)((-16777216L & l) >> 24)), (byte)((int)((16711680L & l) >> 16)), (byte)((int)((65280L & l) >> 8)), (byte)((int)(255L & l))};
        return abyte0;
    }

    protected static void LongToBytes4(long l, byte[] abyte0) {
        abyte0[3] = (byte)((int)(255L & l));
        abyte0[2] = (byte)((int)((65280L & l) >> 8));
        abyte0[1] = (byte)((int)((16711680L & l) >> 16));
        abyte0[0] = (byte)((int)((-16777216L & l) >> 24));
    }

    protected static void IntToBytes(int i, byte[] abyte0) {
        abyte0[1] = (byte)(255 & i);
        abyte0[0] = (byte)(('\uff00' & i) >> 8);
    }

    protected static void IntToBytes4(int i, byte[] abyte0) {
        abyte0[3] = (byte)(255 & i);
        abyte0[2] = (byte)(('\uff00' & i) >> 8);
        abyte0[1] = (byte)((16711680 & i) >> 16);
        abyte0[0] = (byte)((int)((-16777216L & (long)i) >> 24));
    }

    protected static int Bytes4ToInt(byte[] abyte0) {
        return (255 & abyte0[0]) << 24 | (255 & abyte0[1]) << 16 | (255 & abyte0[2]) << 8 | 255 & abyte0[3];
    }

    protected static long Bytes4ToLong(byte[] abyte0) {
        return (255L & (long)abyte0[0]) << 24 | (255L & (long)abyte0[1]) << 16 | (255L & (long)abyte0[2]) << 8 | 255L & (long)abyte0[3];
    }

    protected static void BytesCopy(byte[] abyte0, byte[] abyte1, int i, int j, int k) {
        int i1 = 0;

        for(int l = i; l <= j; ++l) {
            abyte1[k + i1] = abyte0[l];
            ++i1;
        }

    }
}

