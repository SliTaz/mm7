/*     */ package com.zbensoft.mmsmp.common.ra.vas.commons.xml4j.converter;
/*     */ 
/*     */ public class DefaultConverter
/*     */ {
/*     */   public Boolean parseBoolean(String value)
/*     */   {
/*   6 */     if (value == null)
/*   7 */       return null;
/*   8 */     if ("true".compareToIgnoreCase(value) == 0)
/*   9 */       return new Boolean(true);
/*  10 */     return new Boolean(false);
/*     */   }
/*     */ 
/*     */   public boolean parseBooleanPrimative(String value) {
/*  14 */     if (value == null) {
/*  15 */       return false;
/*     */     }
/*  17 */     return "true".compareToIgnoreCase(value) == 0;
/*     */   }
/*     */ 
/*     */   public Byte parseByte(String value)
/*     */   {
/*  22 */     Byte result = null;
/*     */     try {
/*  24 */       result = new Byte(value);
/*     */     } catch (NumberFormatException nfe) {
/*  26 */       nfe.printStackTrace();
/*     */     }
/*  28 */     return result;
/*     */   }
/*     */ 
/*     */   public byte parseBytePrimative(String value) {
/*  32 */     byte result = 0;
/*     */     try {
/*  34 */       result = Byte.parseByte(value);
/*     */     } catch (NumberFormatException nfe) {
/*  36 */       nfe.printStackTrace();
/*  37 */       result = 0;
/*     */     }
/*  39 */     return result;
/*     */   }
/*     */ 
/*     */   public Character parseChar(String value) {
/*  43 */     Character result = null;
/*  44 */     if ((value == null) || (value.length() != 1))
/*  45 */       return null;
/*  46 */     result = new Character(value.charAt(0));
/*  47 */     return result;
/*     */   }
/*     */ 
/*     */   public char parseCharPrimative(String value) {
/*  51 */     char result = '\000';
/*  52 */     if ((value == null) || (value.length() != 1))
/*  53 */       return result;
/*  54 */     result = value.charAt(0);
/*  55 */     return result;
/*     */   }
/*     */ 
/*     */   public Double parseDouble(String value) {
/*  59 */     Double result = null;
/*     */     try {
/*  61 */       result = new Double(value);
/*     */     } catch (NumberFormatException localNumberFormatException) {
/*     */     }
/*  64 */     return result;
/*     */   }
/*     */ 
/*     */   public double parseDoublePrimative(String value) {
/*  68 */     double result = 0.0D;
/*     */     try {
/*  70 */       result = Double.parseDouble(value);
/*     */     } catch (NumberFormatException nfe) {
/*  72 */       nfe.printStackTrace();
/*  73 */       result = 0.0D;
/*     */     }
/*  75 */     return result;
/*     */   }
/*     */ 
/*     */   public Float parseFloat(String value) {
/*  79 */     Float result = null;
/*     */     try {
/*  81 */       result = new Float(value);
/*     */     } catch (NumberFormatException nfe) {
/*  83 */       nfe.printStackTrace();
/*     */     }
/*  85 */     return result;
/*     */   }
/*     */ 
/*     */   public float parseFloatPrimative(String value) {
/*  89 */     float result = 0.0F;
/*     */     try {
/*  91 */       result = Float.parseFloat(value);
/*     */     } catch (NumberFormatException nfe) {
/*  93 */       nfe.printStackTrace();
/*  94 */       result = 0.0F;
/*     */     }
/*  96 */     return result;
/*     */   }
/*     */ 
/*     */   public Integer parseInteger(String value) {
/* 100 */     Integer result = null;
/*     */     try {
/* 102 */       result = new Integer(value);
/*     */     } catch (NumberFormatException nfe) {
/* 104 */       nfe.printStackTrace();
/*     */     }
/* 106 */     return result;
/*     */   }
/*     */ 
/*     */   public int parseIntegerPrimative(String value) {
/* 110 */     int result = 0;
/*     */     try {
/* 112 */       result = Integer.parseInt(value);
/*     */     } catch (NumberFormatException nfe) {
/* 114 */       result = 0;
/*     */     }
/* 116 */     return result;
/*     */   }
/*     */ 
/*     */   public Long parseLong(String value) {
/* 120 */     Long result = null;
/*     */     try {
/* 122 */       result = new Long(value);
/*     */     } catch (NumberFormatException localNumberFormatException) {
/*     */     }
/* 125 */     return result;
/*     */   }
/*     */ 
/*     */   public long parseLongPrimative(String value) {
/* 129 */     long result = 0L;
/*     */     try {
/* 131 */       result = Long.parseLong(value);
/*     */     } catch (NumberFormatException nfe) {
/* 133 */       nfe.printStackTrace();
/* 134 */       result = 0L;
/*     */     }
/* 136 */     return result;
/*     */   }
/*     */ 
/*     */   public Short parseShort(String value) {
/* 140 */     Short result = null;
/*     */     try {
/* 142 */       result = new Short(value);
/*     */     } catch (NumberFormatException nfe) {
/* 144 */       nfe.printStackTrace();
/*     */     }
/* 146 */     return result;
/*     */   }
/*     */ 
/*     */   public short parseShortPrimative(String value) {
/* 150 */     short result = 0;
/*     */     try {
/* 152 */       result = Short.parseShort(value);
/*     */     } catch (NumberFormatException nfe) {
/* 154 */       nfe.printStackTrace();
/* 155 */       result = 0;
/*     */     }
/* 157 */     return result;
/*     */   }
/*     */ 
/*     */   public String parseString(String value) {
/* 161 */     return value;
/*     */   }
/*     */ 
/*     */   public String printBoolean(Boolean value) {
/* 165 */     if (value == null)
/* 166 */       return null;
/* 167 */     return value.toString();
/*     */   }
/*     */ 
/*     */   public String printBooleanPrimative(boolean value) {
/* 171 */     if (value)
/* 172 */       return "true";
/* 173 */     return "false";
/*     */   }
/*     */ 
/*     */   public String printByte(Byte value) {
/* 177 */     if (value == null)
/* 178 */       return null;
/* 179 */     return value.toString();
/*     */   }
/*     */ 
/*     */   public String printBytePrimative(byte value) {
/* 183 */     return String.valueOf(value);
/*     */   }
/*     */ 
/*     */   public String printCharPrimative(byte value) {
/* 187 */     return String.valueOf(value);
/*     */   }
/*     */ 
/*     */   public String printChar(Character value) {
/* 191 */     if (value == null)
/* 192 */       return null;
/* 193 */     return value.toString();
/*     */   }
/*     */ 
/*     */   public String printDouble(Double value) {
/* 197 */     if (value == null)
/* 198 */       return null;
/* 199 */     return value.toString();
/*     */   }
/*     */ 
/*     */   public String printDoublePrimative(double value) {
/* 203 */     return String.valueOf(value);
/*     */   }
/*     */ 
/*     */   public String printFloat(Float value) {
/* 207 */     if (value == null)
/* 208 */       return null;
/* 209 */     return value.toString();
/*     */   }
/*     */ 
/*     */   public String printFloatPrimative(float value) {
/* 213 */     return String.valueOf(value);
/*     */   }
/*     */ 
/*     */   public String printInteger(Integer value) {
/* 217 */     if (value == null)
/* 218 */       return null;
/* 219 */     return value.toString();
/*     */   }
/*     */ 
/*     */   public String printIntegerPrimative(int value) {
/* 223 */     return String.valueOf(value);
/*     */   }
/*     */ 
/*     */   public String printLong(Long value) {
/* 227 */     if (value == null)
/* 228 */       return null;
/* 229 */     return value.toString();
/*     */   }
/*     */ 
/*     */   public String printLongPrimative(long value) {
/* 233 */     return String.valueOf(value);
/*     */   }
/*     */ 
/*     */   public String printShort(Short value) {
/* 237 */     if (value == null)
/* 238 */       return null;
/* 239 */     return value.toString();
/*     */   }
/*     */ 
/*     */   public String printShortPrimative(short value) {
/* 243 */     return String.valueOf(value);
/*     */   }
/*     */ 
/*     */   public String printString(String value) {
/* 247 */     value = value.replaceAll("&", "&amp;");
/* 248 */     value = value.replaceAll("<", "&lt;");
/* 249 */     value = value.replaceAll(">", "&gt;");
/* 250 */     return value;
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\xml4j.jar
 * Qualified Name:     com.aceway.vas.xml4j.converter.DefaultConverter
 * JD-Core Version:    0.6.0
 */