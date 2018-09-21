package com.zbensoft.mmsmp.ftp.util;

public class DataUtil {
    public DataUtil() {
    }

    public static int getProductStatus(int status) {
        if (status != 1 && status != 2) {
            if (status == 3) {
                return 3;
            } else if (status == 4) {
                return 2;
            } else if (status != 5 && status != 6) {
                return status == 7 ? 4 : 0;
            } else {
                return 5;
            }
        } else {
            return 1;
        }
    }

    public static String getServiceCapabilityStatus(String status) {
        if (status.equals("0")) {
            return "2";
        } else if (status.equals("1")) {
            return "1";
        } else if (status.equals("2")) {
            return "3";
        } else if (status.equals("3")) {
            return "3";
        } else if (status.equals("4")) {
            return "5";
        } else {
            return status.equals("5") ? "4" : null;
        }
    }

    public static String getServiceType(String type) {
        if (type.equals("90")) {
            return "s";
        } else {
            return type.equals("31") ? "m" : type;
        }
    }

    public static String getServiceTypeName(String type) {
        if (type.equals("90")) {
            return "短信";
        } else {
            return type.equals("31") ? "彩信" : "其他";
        }
    }

    public static String getServiceTypeForOrder(String type) {
        if (type.equals("90")) {
            return "1";
        } else {
            return type.equals("31") ? "2" : "0";
        }
    }

    public static int getSpStatus(int status) {
        if (status == 2) {
            return 1;
        } else {
            return status == 3 ? 0 : 0;
        }
    }

    public static String generateServiceAccessNumber(String spExpandCode, String tempAccessNumber) {
        System.out.println("spExpandCode:" + spExpandCode + "     tempAccessNumber" + tempAccessNumber);
        if (spExpandCode != null) {
            if (tempAccessNumber.equals("00")) {
                return spExpandCode + "0" + (Integer.parseInt(tempAccessNumber) + 1);
            } else {
                return Integer.parseInt(tempAccessNumber.replace(spExpandCode, "")) < 9 ? spExpandCode + "0" + (Integer.parseInt(tempAccessNumber.replace(spExpandCode, "")) + 1) : spExpandCode + (Integer.parseInt(tempAccessNumber.replace(spExpandCode, "")) + 1);
            }
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(2147483647);
        String num = generateServiceAccessNumber("10655565009", "1065556500901");
        System.out.println(num);
    }
}
