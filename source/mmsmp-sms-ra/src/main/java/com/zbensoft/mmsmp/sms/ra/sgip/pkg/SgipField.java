package com.zbensoft.mmsmp.sms.ra.sgip.pkg;

public class SgipField {

    public SgipField(String name, int length, char fillChar, FillSide fillSide) {
        this.name = name;
        this.length = length;
        this.fillChar = fillChar;
        this.fillSide = fillSide;
    }

    public enum FillSide {
        LEFT, RIGHT
    }

    /**
     * 域名称
     */
    private String name;
    /**
     * 长度
     */
    private int length;
    /**
     * 填充字符
     */
    private char fillChar;
    /**
     * 填充位置
     */
    private FillSide fillSide;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public char getFillChar() {
        return fillChar;
    }

    public void setFillChar(char fillChar) {
        this.fillChar = fillChar;
    }

    public FillSide getFillSide() {
        return fillSide;
    }

    public void setFillSide(FillSide fillSide) {
        this.fillSide = fillSide;
    }

}
