package com.zbensoft.mmsmp.ftp.util;

import com.zbensoft.mmsmp.ftp.sysc.productInfoFtp.ProductInfo;
import com.zbensoft.mmsmp.ftp.sysc.productInfoFtp.ProductInfoDAO;
import org.junit.Test;

public class ProductDaoTest {
    @Test
    public void testSave(){

        ProductInfoDAO dao = new ProductInfoDAO();
        ProductInfo productInfo = new ProductInfo();
        productInfo.updateType=1;
        productInfo.chargeDes="1";
        productInfo.productType=1;
        dao.saveProductInfo(productInfo);
    }
}
