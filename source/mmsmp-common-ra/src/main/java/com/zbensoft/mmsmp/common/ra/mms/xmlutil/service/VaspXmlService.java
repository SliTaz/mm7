package com.zbensoft.mmsmp.common.ra.mms.xmlutil.service;

import java.io.IOException;
import org.dom4j.DocumentException;

public abstract interface VaspXmlService {

	public abstract String getIP(String paramString) throws DocumentException;

	public abstract void saveOrUpdate(String paramString1, String paramString2, String paramString3) throws DocumentException, IOException;

	public abstract void delete(String paramString) throws DocumentException, IOException;

	public abstract void delete(String[] paramArrayOfString) throws DocumentException, IOException;

	public abstract String[] getSp(String paramString) throws DocumentException, IOException;
}
