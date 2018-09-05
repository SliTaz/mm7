

package com.zbensoft.mmsmp.ownbiz.ra.mina;

import com.zbensoft.mmsmp.common.ra.common.message.MO_SMMessage;
import com.zbensoft.mmsmp.ownbiz.ra.base.MinaClient;
import org.junit.Before;
import org.junit.Test;

public class CoreModelTest {
    private MinaClient client;

    public CoreModelTest() {
    }

    @Before
    public void init() {
        this.client = new MinaClient("127.0.0.1", 8010);
    }

    public void send() {
    }

    @Test
    public void moSmMessageTest() {
        MO_SMMessage msg = new MO_SMMessage();
        msg.setVasId("112");
        this.client.send(msg);
    }
}
