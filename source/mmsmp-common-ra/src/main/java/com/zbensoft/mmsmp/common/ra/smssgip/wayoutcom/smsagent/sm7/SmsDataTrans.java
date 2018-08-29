package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.smsagent.sm7;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Properties;
import java.util.Vector;

public class SmsDataTrans {
    protected ByteBuffer bodyBytes;
    protected int TotalLength;
    public static int nSeqNo = 1;
    public static int nSockChannelStatus = 0;
    public static int nServerChannelStatus = 0;
    public static int nSocketCloseFlag = 0;
    public static Vector vTelnetSC = new Vector(0);
    public static int ntelnetCmd = 0;
    public static int nTelnetPort = 8805;
    public static boolean CMD_TELNET_TRACE = false;
    public static boolean CMD_TELNET_TEST = false;
    public static boolean CMD_TELNET_HELP = false;
    public static boolean CMD_TELNET_EXIT = false;
    public static String strCharName = "GBK";
    public static String strCorpID = "74076";
    public static String strSPNumber = "8888";
    public static String strRegMax = null;
    public static String strValidNumber = "133";
    public static String strCommFee = "0";
    public static String strLogPath = null;
    public static int nLogSize = 2097152;
    public static int nLogCount = 10;
    public static String SV_Reg = "xlzc";
    public static String SV_Unreg = "xlzx";
    public static String SV_Active = "xljh";
    public static String SV_Deactive = "xlzt";
    public static String SV_CustomFee = "xldz";
    public static String SV_DownFee = "xlxz";
    public static String SV_RentFee = "xlyz";
    public static String SV_FindPwd = "xlmm";
    public static String SV_CommonMsg = "xlmsg";
    public static String strDetail = null;
    public static String strRegOK = null;
    public static String strReReg = null;
    public static String strRegFail = null;
    public static String strWebRegOK = null;
    public static String strRegPwd = null;
    public static String strUnRegOk = null;
    public static String strUnRegFail = null;
    public static String strReUnReg = null;
    public static String strNoUnReg = null;
    public static String strActiveOK = null;
    public static String strActiveFail = null;
    public static String strReActive = null;
    public static String strNoActive = null;
    public static String strDeactiveOK = null;
    public static String strDeactiveFail = null;
    public static String strReDeactive = null;
    public static String strNoDeactive = null;
    public static String strCRBTAll = null;
    public static String strFindPwd = null;
    public static String strFindPwdFail = null;
    public static String strCustomOK = null;
    public static String strCustomFail = null;
    public static String strDownOK = null;
    public static String strDownFail = null;
    public static String strNoRing = null;
    public static String strNotFavRing = null;
    public static String strRentOK = null;
    public static String strCustomFee = null;
    public static String strRentFee = null;
    public static String LOG_TYPE_INFO = "INFO";
    public static String LOG_TYPE_SEVERE = "SEVERE";
    public static String LOG_TYPE_WARNING = "WARNING";
    public static final int STAT_SC_DISCONNECTED = 0;
    public static final int STAT_SC_ISCONNECTED = 1;
    public static final int LEN_MSG_HEADER = 8;
    public static final int LEN_MSG_MSGBODY = 197;
    public static final int LEN_MSG_PHONE = 21;
    public static final int LEN_MSG_CONTENT = 160;
    public static final int LEN_MSG_SEQNO = 4;
    public static final int LEN_MSG_REPORTFLAG = 4;
    public static final int LEN_MSG_PWD = 10;
    public static final int LEN_MSG_LEGNTH = 205;
    public static final int CMD_MSG_SEND = 1;
    public static final int CMD_MSG_SEND_RESP = 11;
    public static final int CMD_MSG_RECEIVE = 2;
    public static final int CMD_MSG_RECEIVE_RESP = 12;
    public static final int CMD_MSG_REPORT = 3;
    public static final int CMD_MSG_REPORT_RESP = 13;
    public static final int CMD_MSG_RESPONSE = 4;
    public static final int CMD_MSG_RESPONSE_RESP = 14;
    public static final int REPORT_FLAG_TRUE = 1;
    public static final int REPORT_FLAG_FALSE = 0;
    public static final String OP_MSG_REGISTER = "xlzc";
    public static final String OP_MSG_UNREGISTER = "xlzx";
    public static final String OP_MSG_ACTIVE = "xljh";
    public static final String OP_MSG_DEACTIVE = "xlzt";
    public static final String OP_MSG_QUERY = "xlcx";
    public static final String OP_MSG_OFF = "0000";
    public static final String OP_MSG_DOWNLOAD = "xlxz";
    public static final String OP_MSG_CUSTOM = "xldz";
    public static final String OP_MSG_FINDPWD = "xlmm";
    public static final int FEE_TYPE_CUSTOM = 0;
    public static final int FEE_TYPE_DOWNLOAD = 1;
    public static final int FEE_TYPE_RENTAL = 3;
    public static final int FEE_TYPE_COMMON = 2;
    public static final int WEB_TYPE_REGISTER = 4;
    public static final int WEB_TYPE_FINDPWD = 5;
    public static final int WEB_TYPE_ACTIVE = 6;
    public static final int RP_TYPE_ERROR = 0;
    public static final int RP_TYPE_ALL = 1;
    public static final int RP_TYPE_NONE = 2;
    public static final int RP_TYPE_RENTAL = 3;
    public static final String OP_RESULT_OK = "OK";
    public static final String OP_RESULT_FAIL = "FAIL";
    public static final String OP_RESULT_INVALID = "INVALID_STATE";
    public static final String OP_RESULT_REPEAT = "DUP_CUST";
    public static final String OP_RESULT_NOEXIST = "NO_CUST";
    public static final String OP_RESULT_MAXCUST = "MAX_CUST";
    public static final String OP_RESULT_NORING = "NO_RING";
    public static final int SMGP_MSGTYPE_SUBMIT = 1;
    public static final int SMGP_MSGTYPE_RESPONSE = 2;
    public static final int LEN_SMGP_HEADER = 20;
    public static final int LEN_SMGP_BIND = 41;
    public static final int LEN_SMGP_BIND_RESP = 9;
    public static final int LEN_SMGP_UNBIND = 0;
    public static final int LEN_SMGP_UNBIND_RESP = 0;
    public static final int LEN_SMGP_SUBMIT = 123;
    public static final int LEN_SMGP_SUBMIT_RESP = 9;
    public static final int LEN_SMGP_DELIVER = 57;
    public static final int LEN_SMGP_DELIVER_RESP = 9;
    public static final int LEN_SMGP_REPORT = 44;
    public static final int LEN_SMGP_REPORT_RESP = 9;
    public static final int LEN_SMGP_USERRPT = 51;
    public static final int LEN_SMGP_USERRPT_RESP = 9;
    public static final int LEN_SMGP_TRACE = 41;
    public static final int LEN_SMGP_TRACE_RESP = 48;
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
    public static final int ID_SM7_BIND = 1;
    public static final int ID_SM7_BIND_RESP = -2147483647;
    public static final int ID_SM7_UNBIND = 6;
    public static final int ID_SM7_UNBIND_RESP = -2147483642;
    public static final int ID_SM7_SUBMIT = 2;
    public static final int ID_SM7_SUBMIT_RESP = -2147483646;
    public static final int ID_SM7_DELIVER = 3;
    public static final int ID_SM7_DELIVER_RESP = -2147483645;
    public static final int ID_SM7_ACTIVE_TEST = 4;
    public static final int ID_SM7_ACTIVE_TEST_RESP = -2147483644;
    public static final int ID_SMGP_USERRPT = 17;
    public static final int ID_SMGP_USERRPT_RESP = -2147483631;
    public static final int ID_SMGP_TRACE = 4096;
    public static final int ID_SMGP_TRACE_RESP = -2147479552;
    public static final int MSG_TYPE_ASCII = 0;
    public static final int MSG_TYPE_WRITECARD = 3;
    public static final int MSG_TYPE_BINARY = 4;
    public static final int MSG_TYPE_UCS2 = 8;
    public static final int MSG_TYPE_CHINESE = 15;
    public static final int READ_SMGP_INVALID = 0;
    public static final int READ_SMGP_DELIVER = 1;
    public static final int READ_SMGP_REPORT = 2;
    public static final int READ_SMGP_USERRPT = 3;
    public static final int READ_SMGP_UNBIND = 4;
    public static final int SM7_IS_REPORT = 1;
    public static final int SM7_IS_DELIVER = 0;
    private static Properties _properties = null;

    public SmsDataTrans() {
    }

    public static String getProperty(String key) {
        if (_properties == null) {
            try {
                InputStream ins = SmsDataTrans.class.getResourceAsStream("/app.properties");
                _properties = new Properties();
                _properties.load(ins);
            } catch (Exception var3) {
                _properties = null;
            }
        }

        return _properties.getProperty(key);
    }

    public static String stringEncoder(String str, String charname) {
        try {
            return str;
        } catch (Exception var3) {
            System.out.println("string encoder" + var3.getMessage());
            var3.printStackTrace();
            return null;
        }
    }

    public static String cmdUtfToString(String strUtf) {
        String str = null;

        try {
            byte[] abyte0 = strUtf.getBytes();
            byte[] x = new byte[]{abyte0[1], abyte0[3], abyte0[5], abyte0[7]};
            str = new String(x);
        } catch (Exception var4) {
            System.out.println("encode string: " + str);
        }

        return str;
    }

    public static String unicodeToGB(String strIn) {
        String strOut = null;
        if (strIn != null && !strIn.trim().equals("")) {
            try {
                byte[] b = strIn.getBytes("GBK");
                strOut = new String(b, "ISO8859_1");
            } catch (UnsupportedEncodingException var4) {
                var4.printStackTrace();
            }

            return strOut;
        } else {
            return strIn;
        }
    }

    public static String GBToUnicode(String strIn) {
        String strOut = null;
        if (strIn != null && !strIn.trim().equals("")) {
            try {
                byte[] b = strIn.getBytes("ISO8859_1");
                strOut = new String(b, "GBK");
            } catch (Exception var3) {
                ;
            }

            return strOut;
        } else {
            return strIn;
        }
    }

    public static boolean IsStringNumber(String number) {
        if (number != null && number.length() > 0) {
            for(int i = 0; i < number.length(); ++i) {
                char tempChar = number.charAt(i);
                if (tempChar < '0' || tempChar > '9') {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static int generateSeqNo() {
        return nSeqNo < 2147483647 ? nSeqNo++ : 1;
    }

    public int ReadBody(SocketChannel sc, ByteBuffer bodyBytes) {
        try {
            sc.read(bodyBytes);
            return 0;
        } catch (IOException var4) {
            var4.printStackTrace();
            return -1;
        }
    }

    public static void playAudio() {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File("OperateFailed.wav"));
            AudioFormat format = stream.getFormat();
            if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), format.getSampleSizeInBits() * 2, format.getChannels(), format.getFrameSize() * 2, format.getFrameRate(), true);
                stream = AudioSystem.getAudioInputStream(format, stream);
            }

            DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat(), (int)stream.getFrameLength() * format.getFrameSize());
            Clip clip = (Clip)AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        } catch (MalformedURLException var5) {
            System.out.println("Play Audio, MalformedURLException");
            var5.printStackTrace();
        } catch (IOException var6) {
            System.out.println("Play Audio, IOException");
            var6.printStackTrace();
        } catch (LineUnavailableException var7) {
            System.out.println("Play Audio, LineUnavailableException");
            var7.printStackTrace();
        } catch (UnsupportedAudioFileException var8) {
            System.out.println("Play Audio, UnsupportedAudioFileException");
            var8.printStackTrace();
        }

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
