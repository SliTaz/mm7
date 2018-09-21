package com.zbensoft.mmsmp.ftp.service;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IDownload {
    @WebMethod
    void dir(@WebParam(name = "directories")String var1, @WebParam(name = "var2")String var2);
}

