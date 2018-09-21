package com.zbensoft.mmsmp.ftp.service.impl;


import com.zbensoft.mmsmp.ftp.service.IDownload;
import com.zbensoft.mmsmp.ftp.util.FtpUtil;

import javax.jws.WebService;

@WebService(targetNamespace="http://impl.service.ftp.mmsmp.zbensoft.com/",endpointInterface = "com.zbensoft.mmsmp.ftp.service.IDownload")
public class DownloadImpl implements IDownload {
    private FtpUtil ftpUtil;

    public DownloadImpl() {
    }

    public FtpUtil getFtpUtil() {
        return this.ftpUtil;
    }

    public void setFtpUtil(FtpUtil ftpUtil) {
        this.ftpUtil = ftpUtil;
    }

    public void dir(String files, String regex) {
        if (this.ftpUtil.connect()) {
            try {
                this.ftpUtil.downloadDir(files.split(","), regex);
                this.ftpUtil.disconnect();
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

    }

}
