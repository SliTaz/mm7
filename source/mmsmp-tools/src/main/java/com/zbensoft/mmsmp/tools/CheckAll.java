package com.zbensoft.mmsmp.tools;

import com.zbensoft.mmsmp.tools.app.AppBuyerPayErrorCodeCheck;
import com.zbensoft.mmsmp.tools.app.AppSellerPayErrorCodeCheck;
import com.zbensoft.mmsmp.tools.json.BossCheckJson;
import com.zbensoft.mmsmp.tools.json.ConsumerCheckJson;
import com.zbensoft.mmsmp.tools.json.GovCheckJson;
import com.zbensoft.mmsmp.tools.json.MainCheckJson;
import com.zbensoft.mmsmp.tools.json.MerchantCheckJson;
import com.zbensoft.mmsmp.tools.pay.ConsumerErrorCode;
import com.zbensoft.mmsmp.tools.pay.MerchantErrorCode;

/**
 * Hello world!
 *
 */
public class CheckAll 
{
    public static void main( String[] args )
    {
        AppBuyerPayErrorCodeCheck.check();
        AppSellerPayErrorCodeCheck.check();
        
        BossCheckJson.check();
        ConsumerCheckJson.check();
        GovCheckJson.check();
        MainCheckJson.check();
        MerchantCheckJson.check();
        
        ConsumerErrorCode.check();
        MerchantErrorCode.check();
    }
}
