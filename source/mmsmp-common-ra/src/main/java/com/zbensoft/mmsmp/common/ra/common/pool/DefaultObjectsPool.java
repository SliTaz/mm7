/*     */ package com.zbensoft.mmsmp.common.ra.common.pool;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class DefaultObjectsPool
/*     */   implements ObjectsPool
/*     */ {
/*  17 */   private int poolSize = 100;
/*  18 */   private Map<Object, Object> pool = new HashMap();
/*  19 */   private Object[] poolKeys = new Object[this.poolSize * 5];
/*  20 */   private int startPos = this.poolSize * 5; private int endPos = -1; private int length = 0;
/*  21 */   private Map<Object, Integer> keyPosMap = new HashMap();
/*     */   private static final int SAVE_EXISTED = 1;
/*     */   private static final int SAVE_NOEXISTED = 2;
/*     */ 
/*     */   public Object[] getPoolKeys()
/*     */   {
/*  28 */     Object[] result = new Object[this.length];
/*  29 */     int i = 0;
/*     */ 
/*  31 */     for (int index = 0; index < this.poolKeys.length; index++)
/*     */     {
/*  33 */       if (this.poolKeys[index] == null) {
/*     */         continue;
/*     */       }
/*  36 */       i++; result[i] = this.poolKeys[index];
/*     */ 
/*  38 */       if (i >= this.length) {
/*  39 */         return result;
/*     */       }
/*     */     }
/*  42 */     return result;
/*     */   }
/*     */ 
/*     */   public void removeObj(Object key)
/*     */   {
/*  51 */     Integer pos = (Integer)this.keyPosMap.get(key);
/*     */ 
/*  53 */     if ((pos == null) || (pos.intValue() < 0)) {
/*  54 */       return;
/*     */     }
/*  56 */     this.pool.remove(this.poolKeys[pos.intValue()]);
/*  57 */     this.poolKeys[pos.intValue()] = null;
/*  58 */     this.keyPosMap.remove(key);
/*  59 */     this.length -= 1;
/*     */ 
/*  61 */     while ((this.endPos >= 0) && (this.poolKeys[this.endPos] == null))
/*  62 */       this.endPos -= 1;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/*  73 */     this.pool.clear();
/*  74 */     this.keyPosMap.clear();
/*  75 */     this.length = 0;
/*  76 */     this.endPos = -1;
/*  77 */     this.startPos = (this.poolSize * 5);
/*  78 */     this.poolKeys = new Object[this.poolSize * 5];
/*     */   }
/*     */ 
/*     */   public DefaultObjectsPool()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DefaultObjectsPool(int size)
/*     */   {
/*  91 */     setPoolSize(size);
/*     */   }
/*     */ 
/*     */   private void initKeys()
/*     */   {
/*  99 */     Object[] newValue = new Object[this.poolSize * 5];
/*     */ 
/* 101 */     int index = this.poolSize * 5;
/* 102 */     this.keyPosMap.clear();
/* 103 */     for (int i = this.poolKeys.length - 1; i >= 0; i--) {
/* 104 */       if (this.poolKeys[i] != null) {
/* 105 */         index--; newValue[index] = this.poolKeys[i];
/* 106 */         this.keyPosMap.put(this.poolKeys[i], KeyPos.pos[index]);
/*     */       }
/*     */     }
/*     */ 
/* 110 */     this.poolKeys = newValue;
/* 111 */     this.startPos = index;
/*     */ 
/* 113 */     if (this.startPos >= this.poolSize * 5)
/* 114 */       this.endPos = -1;
/*     */     else
/* 116 */       this.endPos = (this.poolSize * 5 - 1);
/*     */   }
/*     */ 
/*     */   private void accessKey(Object key, int type)
/*     */   {
/* 130 */     if (type == 1)
/*     */     {
/* 132 */       if (this.startPos <= 0) {
/* 133 */         initKeys();
/*     */       }
/* 135 */       Integer pos = (Integer)this.keyPosMap.get(key);
/* 136 */       if (pos == null) {
/* 137 */         return;
/*     */       }
/* 139 */       int index = pos.intValue();
/* 140 */       this.poolKeys[(--this.startPos)] = this.poolKeys[index];
/* 141 */       this.poolKeys[index] = null;
/* 142 */       this.keyPosMap.put(key, KeyPos.pos[this.startPos]);
/*     */       do
/*     */       {
/* 145 */         this.endPos -= 1;
/*     */ 
/* 144 */         if (this.endPos < 0) break; 
/* 144 */       }while (this.poolKeys[this.endPos] == null);
/*     */     }
/*     */     else
/*     */     {
/* 148 */       if (this.startPos <= 0) {
/* 149 */         initKeys();
/*     */       }
/* 151 */       if (this.length >= this.poolSize) {
/* 152 */         this.keyPosMap.remove(this.poolKeys[this.endPos]);
/* 153 */         this.pool.remove(this.poolKeys[this.endPos]);
/* 154 */         this.poolKeys[this.endPos] = null;
/* 155 */         while ((this.endPos >= 0) && (this.poolKeys[this.endPos] == null)) {
/* 156 */           this.endPos -= 1;
/*     */         }
/* 158 */         this.length -= 1;
/*     */       }
/* 160 */       this.poolKeys[(--this.startPos)] = key;
/* 161 */       if (this.endPos == -1) {
/* 162 */         this.endPos = this.startPos;
/*     */       }
/* 164 */       this.keyPosMap.put(key, KeyPos.pos[this.startPos]);
/*     */ 
/* 166 */       this.length += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void saveObj(Object key, Object obj)
/*     */   {
/* 175 */     this.pool.put(key, obj);
/* 176 */     if (this.pool.get(key) != null)
/*     */     {
/* 178 */       accessKey(key, 1);
/*     */     }
/*     */     else
/*     */     {
/* 182 */       accessKey(key, 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getObj(Object key)
/*     */   {
/* 190 */     return this.pool.get(key);
/*     */   }
/*     */ 
/*     */   public boolean exists(Object key)
/*     */   {
/* 197 */     return this.pool.containsKey(key);
/*     */   }
/*     */ 
/*     */   public void setPoolSize(int size)
/*     */   {
/* 204 */     KeyPos.setSize(size * 5);
/* 205 */     this.poolSize = size;
/* 206 */     initKeys();
/*     */   }
/*     */ 
/*     */   public synchronized void inspect(PrintStream out, InputStream in)
/*     */   {
/* 212 */     out.println("poolSize = " + this.poolSize);
/* 213 */     out.println("pool.size = " + this.pool.size());
/* 214 */     out.println("poolKeys.length = " + this.poolKeys.length);
/* 215 */     out.println("startPos = " + this.startPos);
/* 216 */     out.println("endPos = " + this.endPos);
/* 217 */     out.println("length = " + this.length);
/* 218 */     out.println("keyPosMap.size = " + this.keyPosMap.size());
/*     */ 
/* 223 */     Set temp = new HashSet();
/* 224 */     int num = 0;
/* 225 */     for (int i = 0; i < this.poolKeys.length; i++) {
/* 226 */       if (this.poolKeys[i] != null) {
/* 227 */         num++;
/* 228 */         if (temp.contains(this.poolKeys[i])) {
/* 229 */           out.println("发现重复的KEY：" + this.poolKeys[i]);
/*     */         }
/* 231 */         temp.add(this.poolKeys[i]);
/* 232 */         if (this.pool.get(this.poolKeys[i]) == null) {
/* 233 */           out.println("无法在缓存中找到：" + this.poolKeys[i]);
/*     */         }
/* 235 */         Integer pos = (Integer)this.keyPosMap.get(this.poolKeys[i]);
/* 236 */         if (pos == null) {
/* 237 */           out.println("无法在KeyPosMap中找到：" + this.poolKeys[i]);
/*     */         }
/* 239 */         if (pos.intValue() != i) {
/* 240 */           out.println("实际位置不符合：" + this.poolKeys[i]);
/*     */         }
/*     */       }
/*     */     }
/* 244 */     if (num != this.length) {
/* 245 */       out.println("poolKeys实际的KEY个数和length不符合");
/*     */     }
/* 247 */     if (this.keyPosMap.size() != this.length) {
/* 248 */       out.println("keyPosMap实际的KEY个数和length不符合");
/*     */     }
/* 250 */     if (this.pool.size() != this.length)
/* 251 */       out.println("pool实际的KEY个数和length不符合");
/*     */   }
/*     */ }

/* Location:           E:\项目\2018-MMSMP-中国联调彩信\彩信管理平台MMSMP_20180802\开发参考文档\抓包\home\mmsmp\agent\vac_agent\WEB-INF\lib\mmsms_common.jar
 * Qualified Name:     com.aceway.common.pool.DefaultObjectsPool
 * JD-Core Version:    0.6.0
 */