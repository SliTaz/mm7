package com.zbensoft.mmsmp.common.ra.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;

public abstract interface FtpServerInterface
{
  public abstract void connect(String paramString1, String paramString2)
    throws SocketException, IOException;

  public abstract void dir(String paramString)
    throws IOException;

  public abstract void changeDir(String paramString)
    throws IOException;

  public abstract void changeToParentDir()
    throws IOException;

  public abstract void makedir(String paramString)
    throws IOException;

  public abstract void download(String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4)
    throws IOException, FileNotFoundException;

  public abstract void downloadDirectory(String paramString1, String paramString2)
    throws IOException;

  public abstract void retrieveDirectory(String paramString1, String paramString2)
    throws IOException;

  public abstract boolean deleteFile(String paramString)
    throws IOException;

  public abstract void deleteDirectory(String paramString)
    throws IOException;

  public abstract void move(String paramString1, String paramString2)
    throws IOException;

  public abstract void logout()
    throws IOException;
}

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.util.FtpServerInterface
 * JD-Core Version:    0.6.0
 */