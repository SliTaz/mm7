package com.zbensoft.mmsmp.db.domain;

public class ProductPackageKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_package.package_id
     *
     * @mbg.generated Sat Sep 15 13:38:43 CST 2018
     */
    private String packageId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_package.product_info_id
     *
     * @mbg.generated Sat Sep 15 13:38:43 CST 2018
     */
    private String productInfoId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_package.package_id
     *
     * @return the value of product_package.package_id
     *
     * @mbg.generated Sat Sep 15 13:38:43 CST 2018
     */
    public String getPackageId() {
        return packageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_package.package_id
     *
     * @param packageId the value for product_package.package_id
     *
     * @mbg.generated Sat Sep 15 13:38:43 CST 2018
     */
    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_package.product_info_id
     *
     * @return the value of product_package.product_info_id
     *
     * @mbg.generated Sat Sep 15 13:38:43 CST 2018
     */
    public String getProductInfoId() {
        return productInfoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_package.product_info_id
     *
     * @param productInfoId the value for product_package.product_info_id
     *
     * @mbg.generated Sat Sep 15 13:38:43 CST 2018
     */
    public void setProductInfoId(String productInfoId) {
        this.productInfoId = productInfoId;
    }
}