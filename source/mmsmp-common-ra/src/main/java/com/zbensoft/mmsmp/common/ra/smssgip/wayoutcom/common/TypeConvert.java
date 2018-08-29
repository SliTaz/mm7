package com.zbensoft.mmsmp.common.ra.smssgip.wayoutcom.common;

public class TypeConvert {
    public TypeConvert() {
    }

    public static int byte2int(byte[] b, int offset) {
        return b[offset + 3] & 255 | (b[offset + 2] & 255) << 8 | (b[offset + 1] & 255) << 16 | (b[offset] & 255) << 24;
    }

    public static int byte2int(byte[] b) {
        int len = b.length;
        byte[] bb = (byte[])null;
        if (len < 4) {
            bb = new byte[4];
            System.arraycopy(b, 0, bb, 4 - len, len);
            b = bb;
        }

        return b[3] & 255 | (b[2] & 255) << 8 | (b[1] & 255) << 16 | (b[0] & 255) << 24;
    }

    public static long byte2long(byte[] b) {
        int len = b.length;
        byte[] bb = (byte[])null;
        if (len < 8) {
            bb = new byte[8];
            System.arraycopy(b, 0, bb, 8 - len, len);
            b = bb;
        }

        return (long)b[7] & 255L | ((long)b[6] & 255L) << 8 | ((long)b[5] & 255L) << 16 | ((long)b[4] & 255L) << 24 | ((long)b[3] & 255L) << 32 | ((long)b[2] & 255L) << 40 | ((long)b[1] & 255L) << 48 | (long)b[0] << 56;
    }

    public static long byte2long(byte[] b, int offset) {
        return (long)b[offset + 7] & 255L | ((long)b[offset + 6] & 255L) << 8 | ((long)b[offset + 5] & 255L) << 16 | ((long)b[offset + 4] & 255L) << 24 | ((long)b[offset + 3] & 255L) << 32 | ((long)b[offset + 2] & 255L) << 40 | ((long)b[offset + 1] & 255L) << 48 | (long)b[offset] << 56;
    }

    public static byte[] int2byte(int n) {
        byte[] b = new byte[]{(byte)(n >> 24), (byte)(n >> 16), (byte)(n >> 8), (byte)n};
        return b;
    }

    public static void int2byte(int n, byte[] buf, int offset) {
        buf[offset] = (byte)(n >> 24);
        buf[offset + 1] = (byte)(n >> 16);
        buf[offset + 2] = (byte)(n >> 8);
        buf[offset + 3] = (byte)n;
    }

    public static byte[] short2byte(int n) {
        byte[] b = new byte[]{(byte)(n >> 8), (byte)n};
        return b;
    }

    public static void short2byte(int n, byte[] buf, int offset) {
        buf[offset] = (byte)(n >> 8);
        buf[offset + 1] = (byte)n;
    }

    public static byte[] long2byte(long n) {
        byte[] b = new byte[]{(byte)((int)(n >> 56)), (byte)((int)(n >> 48)), (byte)((int)(n >> 40)), (byte)((int)(n >> 32)), (byte)((int)(n >> 24)), (byte)((int)(n >> 16)), (byte)((int)(n >> 8)), (byte)((int)n)};
        return b;
    }

    public static void long2byte(long n, byte[] buf, int offset) {
        buf[offset] = (byte)((int)(n >> 56));
        buf[offset + 1] = (byte)((int)(n >> 48));
        buf[offset + 2] = (byte)((int)(n >> 40));
        buf[offset + 3] = (byte)((int)(n >> 32));
        buf[offset + 4] = (byte)((int)(n >> 24));
        buf[offset + 5] = (byte)((int)(n >> 16));
        buf[offset + 6] = (byte)((int)(n >> 8));
        buf[offset + 7] = (byte)((int)n);
    }

    public static byte[] toBCD(int num) {
        byte[] data = new byte[10];
        int next = num;
        int count = 0;

        int i;
        for(i = 0; next != 0; ++i) {
            int index = i / 2;
            byte yu = (byte)(next % 10);
            if (i % 2 == 0) {
                data[index] = yu;
            } else {
                int tmp = yu << 4;
                tmp += data[index];
                data[index] = (byte)tmp;
            }

            ++count;
            next /= 10;
        }

        i = count % 2 == 0 && count != 0 ? count / 2 : count / 2 + 1;
        byte[] result = new byte[i];
        System.arraycopy(data, 0, result, 0, i);
        return result;
    }

    public static int BCDtoINT(byte[] num) {
        if (num == null) {
            return 0;
        } else {
            int len = num.length;
            byte[] bcd = new byte[len];
            System.arraycopy(num, 0, bcd, 0, len);
            int result = 0;
            int exp = 1;

            for(int i = 0; i < len; ++i) {
                int low = bcd[i] & 15;
                result += low * exp;
                exp *= 10;
                int hi = (bcd[i] & 240) >>> 4;
                result += hi * exp;
                exp *= 10;
            }

            return result;
        }
    }

    public static void main(String[] arg) {
        byte[] bcd = toBCD(123456);

        for(int i = 0; i < bcd.length; ++i) {
            System.out.println(bcd[i]);
        }

    }
}
