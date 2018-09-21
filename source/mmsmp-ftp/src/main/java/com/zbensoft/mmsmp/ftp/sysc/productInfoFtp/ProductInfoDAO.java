package com.zbensoft.mmsmp.ftp.sysc.productInfoFtp;


import com.zbensoft.mmsmp.ftp.util.HttpHelper;
import com.zbensoft.mmsmp.ftp.util.PropertiesHelper;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProductInfoDAO {
    private static final Logger logger = Logger.getLogger(ProductInfoDAO.class);
    private String ftp_product_tree_id = PropertiesHelper.getString("ftp_product_tree_id");

    public ProductInfoDAO() {
    }

    public void saveProductInfo(List<ProductInfo> ors) {
        Iterator iterator = ors.iterator();

        while (iterator.hasNext()) {
            ProductInfo productInfo = (ProductInfo) iterator.next();
            this.saveProductInfo(productInfo);
        }

    }


    private String getIntFromString(String str) {
        if (str == null) {
            return "0";
        } else {
            Pattern pd = Pattern.compile("[\\d.]+");
            Matcher m = pd.matcher(str);
            if (m.find()) {
                System.out.println("fee is :" + m.group());
            }

            return m.group();
        }
    }

    public void saveProductInfo(ProductInfo productInfo) {
        int updateType = productInfo.updateType;
        int productType = productInfo.productType;
        String chargeDes = productInfo.chargeDes;
        double charge = Double.parseDouble(this.getIntFromString(chargeDes)) * 100.0D;

        if (updateType == 1 || updateType == 2) {
            HttpHelper.saveOrUpdateProductInfo(productInfo, productType, charge);
        }

        if (updateType == 3) {
            HttpHelper.deleteProductInfo(productInfo, productType, charge);
        }


    }


}

