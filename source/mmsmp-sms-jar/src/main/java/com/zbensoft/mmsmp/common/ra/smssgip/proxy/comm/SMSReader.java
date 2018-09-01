package com.zbensoft.mmsmp.common.ra.smssgip.proxy.comm;

import java.io.IOException;

public interface SMSReader {
    Object read() throws IOException, SMSException, Exception;
}
