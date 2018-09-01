package com.zbensoft.mmsmp.api.common;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * a）当重新表达的金额的第三位小数等于或大于五（5）时，第二位小数将增加一个单位。
 * b）当重新表达的金额的第三位小于五（5）时，第二位小数保持不变。
 * 上述四舍五入将仅适用一次，以便将货物和服务的单个价格或价值带至两（2）位小数
 */
public class BsFormatReconversion {

    private final static int index = 3;

    /**
     * 1.102.670,96  ->  1.102,67
     *
     * @param 110267096
     * @return
     * @throws Exception
     */
    public static double reconversion(String sourceMoney) throws Exception {

        if (sourceMoney == null || "".equals(sourceMoney)) {
            throw new NullPointerException("Source Money is null");
        }

        if (0D == Double.valueOf(sourceMoney)) {
            return 0;
        }

        if (sourceMoney.length() < 5) {
            return 0;
        }

        Double targetPre = Double.valueOf(sourceMoney) / 10000000;
        targetPre += 0.00000001;
        return getDoubleNumber(targetPre);
    }

    private static double getDoubleNumber(double number) {
        return getDoubleNumber("#0.00",number);
    }

    private static double getDoubleNumber(String reg, double number) {
        try {
            return Double.parseDouble(new DecimalFormat(reg).format(number));
        } catch (Exception e) {
            return 0.0;
        }
    }
}