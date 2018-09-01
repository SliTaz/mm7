package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.dbAccess;

public class AppInfo {
    private String name;
    private int id;
    private String clientIP;
    private String serverIP;
    private int serverPort;

    public AppInfo() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setClientIP(String ip) {
        this.clientIP = ip;
    }

    public void setServerIP(String ip) {
        this.serverIP = ip;
    }

    public void setServerPort(int port) {
        this.serverPort = port;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.id;
    }

    public String getClientIP() {
        return this.clientIP;
    }

    public String getServerIP() {
        return this.serverIP;
    }

    public int getServerPort() {
        return this.serverPort;
    }
}

