package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.sgipapi;

public class SGIP_Bind extends SGIP_Command {
    private static final int CommandLength = 41;
    private static final int CommandID = 1;
    private int LoginType;
    private int flag;
    private String LoginName;
    private String LoginPassword;

    public SGIP_Bind(long lSrcNodeID) {
        super(lSrcNodeID, 41, 1);
        this.flag = 1;
    }

    public SGIP_Bind() {
        super(41, 1);
        this.flag = 1;
    }

    public SGIP_Bind(SGIP_Command sgip_command) {
        super(sgip_command);
        this.flag = 1;
    }

    public int GetFlag() {
        return this.flag;
    }

    public int GetLoginType() {
        return this.LoginType;
    }

    public void SetLoginType(char nLoginType) {
        this.LoginType = nLoginType;
        this.byteBody.position(0);
        this.byteBody.put((byte)nLoginType);
    }

    public String GetLoginName() {
        return this.LoginName;
    }

    public void SetLoginName(String strLoginName) {
        this.LoginName = strLoginName;
        this.byteBody.position(1);
        this.byteBody.put(strLoginName.getBytes());
    }

    public String GetLoginPassword() {
        return this.LoginPassword;
    }

    public void SetLoginPassword(String strLoginPwd) {
        this.LoginPassword = strLoginPwd;
        this.byteBody.position(17);
        this.byteBody.put(strLoginPwd.getBytes());
    }

    public SGIP_Bind(int nLoginType, String loginName, String loginPwd) {
        this(0L, nLoginType, loginName, loginPwd);
    }

    public SGIP_Bind(long lSrcNodeID, int nLoginType, String strLoginName, String strLoginPwd) {
        super(lSrcNodeID, 41, 1);
        this.flag = 1;
        this.LoginType = (char)nLoginType;
        this.LoginName = strLoginName;
        this.LoginPassword = strLoginPwd;
        this.byteBody.position(0);
        this.byteBody.put((byte)this.LoginType);
        this.byteBody.position(1);
        this.byteBody.put(strLoginName.getBytes());
        this.byteBody.position(17);
        this.byteBody.put(strLoginPwd.getBytes());
    }

    public int readbody() {
        this.byteBody.clear();
        this.LoginType = this.byteBody.get(0);
        this.LoginName = (new String(this.byteBody.array(), 1, 16)).trim();
        this.LoginPassword = (new String(this.byteBody.array(), 17, 16)).trim();
        this.flag = 0;
        return 0;
    }

    public String toString() {
        return "SGIP_Bind:seq3=" + super.getSeqno_3() + " LoginType=" + this.LoginType + " LoginName=" + this.LoginName + " Password=" + this.LoginPassword;
    }
}
