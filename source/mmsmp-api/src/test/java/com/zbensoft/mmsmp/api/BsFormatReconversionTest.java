package com.zbensoft.mmsmp.api;

import org.junit.Test;

import com.zbensoft.mmsmp.api.common.BsFormatReconversion;

import java.text.DecimalFormat;
import java.util.FormatFlagsConversionMismatchException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BsFormatReconversionTest {

    @Test(expected = NullPointerException.class)
    public void testReconversion() throws Exception {
        BsFormatReconversion.reconversion("");
    }

    @Test
    public void testReconversion0() throws Exception {
        //  0
        double result = BsFormatReconversion.reconversion("0");
        assertEquals(0, result, 0.0);

        //0.01  ->  0
        result = BsFormatReconversion.reconversion("1");
        assertEquals(0, result, 0.0);

        // 0.1   ->  0
        result = BsFormatReconversion.reconversion("10");
        assertEquals(0, result, 0.0);

        //1.00  ->  0.01
        result = BsFormatReconversion.reconversion("100");
        assertEquals(0.01, result, 0.1);

        //1.00  ->  0.01
        result = BsFormatReconversion.reconversion("100");
        assertEquals(0.01, result, 0.01);

        //15.00 ->  0.02
        result = BsFormatReconversion.reconversion("1500");
        assertEquals(0.02, result, 0.0);

        //100.00 ->  0.10
        result = BsFormatReconversion.reconversion("10000");
        assertEquals(0.10, result, 0.0);

        //105.00 ->  0.11
        result = BsFormatReconversion.reconversion("1000");
        assertEquals(0.01, result, 0.0);

        //1000.00 ->  1.00
        result = BsFormatReconversion.reconversion("10500");
        assertEquals(0.11, result, 0.0);

        //1005.00 ->  1.01
        result = BsFormatReconversion.reconversion("100000");
        assertEquals(1.00, result, 0.0);

        //10000.00 ->  10.00
        result = BsFormatReconversion.reconversion("1000000");
        assertEquals(10.00, result, 0.0);

        //100000.00 ->  100.00
        result = BsFormatReconversion.reconversion("10000000");
        assertEquals(100.00, result, 0.0);

        //1000000.00 ->  100.00
        result = BsFormatReconversion.reconversion("100000000");
        assertEquals(1000.00, result, 0.0);

        //1000000.00 ->  100.00
        result = BsFormatReconversion.reconversion("100000000");
        assertEquals(1000.00, result, 0.0);

        //10000000.00 ->  100.00
        result = BsFormatReconversion.reconversion("1000000000");
        assertEquals(10000.00, result, 0.0);

        //100000000008.00 ->  100000000.01
        result = BsFormatReconversion.reconversion("10000000800");
        assertEquals(100000.01, result, 0.0);

//        result = BsFormatReconversion.reconversion("1000000000800");
//        assertEquals(10000000.02, result, 0.01);



    }

    @Test
    public void testReconversioExample() throws Exception {
        double result = BsFormatReconversion.reconversion("27999900");
        assertEquals(280.00, result, 0.0);

        //现网最大金额
        result = BsFormatReconversion.reconversion("7287500000");
        assertEquals(72875, result, 0.0);

        result = BsFormatReconversion.reconversion("4245208");
        assertEquals(42.45, result, 0.0);

        result = BsFormatReconversion.reconversion("4245808");
        assertEquals(42.46, result, 0.0);

        result = BsFormatReconversion.reconversion("841500");
        assertEquals(8.42, result, 0.0);

        result = BsFormatReconversion.reconversion("9841500");
        assertEquals(98.42, result, 0.0);

        result = BsFormatReconversion.reconversion("99841500");
        assertEquals(998.42, result, 0.0);



    }

    @Test
    public  void testDoubleToString(){
        Double aa = 10000.1;
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(aa).replace(".",""));

    }

}
