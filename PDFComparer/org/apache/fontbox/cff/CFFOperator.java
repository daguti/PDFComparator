/*     */ package org.apache.fontbox.cff;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class CFFOperator
/*     */ {
/*  31 */   private Key operatorKey = null;
/*  32 */   private String operatorName = null;
/*     */ 
/* 200 */   private static Map<Key, CFFOperator> keyMap = new LinkedHashMap();
/* 201 */   private static Map<String, CFFOperator> nameMap = new LinkedHashMap();
/*     */ 
/*     */   private CFFOperator(Key key, String name)
/*     */   {
/*  36 */     setKey(key);
/*  37 */     setName(name);
/*     */   }
/*     */ 
/*     */   public Key getKey()
/*     */   {
/*  46 */     return this.operatorKey;
/*     */   }
/*     */ 
/*     */   private void setKey(Key key)
/*     */   {
/*  51 */     this.operatorKey = key;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  60 */     return this.operatorName;
/*     */   }
/*     */ 
/*     */   private void setName(String name)
/*     */   {
/*  65 */     this.operatorName = name;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  73 */     return getName();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  81 */     return getKey().hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/*  89 */     if ((object instanceof CFFOperator))
/*     */     {
/*  91 */       CFFOperator that = (CFFOperator)object;
/*  92 */       return getKey().equals(that.getKey());
/*     */     }
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   private static void register(Key key, String name)
/*     */   {
/*  99 */     CFFOperator operator = new CFFOperator(key, name);
/* 100 */     keyMap.put(key, operator);
/* 101 */     nameMap.put(name, operator);
/*     */   }
/*     */ 
/*     */   public static CFFOperator getOperator(Key key)
/*     */   {
/* 111 */     return (CFFOperator)keyMap.get(key);
/*     */   }
/*     */ 
/*     */   public static CFFOperator getOperator(String name)
/*     */   {
/* 121 */     return (CFFOperator)nameMap.get(name);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 206 */     register(new Key(0), "version");
/* 207 */     register(new Key(1), "Notice");
/* 208 */     register(new Key(12, 0), "Copyright");
/* 209 */     register(new Key(2), "FullName");
/* 210 */     register(new Key(3), "FamilyName");
/* 211 */     register(new Key(4), "Weight");
/* 212 */     register(new Key(12, 1), "isFixedPitch");
/* 213 */     register(new Key(12, 2), "ItalicAngle");
/* 214 */     register(new Key(12, 3), "UnderlinePosition");
/* 215 */     register(new Key(12, 4), "UnderlineThickness");
/* 216 */     register(new Key(12, 5), "PaintType");
/* 217 */     register(new Key(12, 6), "CharstringType");
/* 218 */     register(new Key(12, 7), "FontMatrix");
/* 219 */     register(new Key(13), "UniqueID");
/* 220 */     register(new Key(5), "FontBBox");
/* 221 */     register(new Key(12, 8), "StrokeWidth");
/* 222 */     register(new Key(14), "XUID");
/* 223 */     register(new Key(15), "charset");
/* 224 */     register(new Key(16), "Encoding");
/* 225 */     register(new Key(17), "CharStrings");
/* 226 */     register(new Key(18), "Private");
/* 227 */     register(new Key(12, 20), "SyntheticBase");
/* 228 */     register(new Key(12, 21), "PostScript");
/* 229 */     register(new Key(12, 22), "BaseFontName");
/* 230 */     register(new Key(12, 23), "BaseFontBlend");
/* 231 */     register(new Key(12, 30), "ROS");
/* 232 */     register(new Key(12, 31), "CIDFontVersion");
/* 233 */     register(new Key(12, 32), "CIDFontRevision");
/* 234 */     register(new Key(12, 33), "CIDFontType");
/* 235 */     register(new Key(12, 34), "CIDCount");
/* 236 */     register(new Key(12, 35), "UIDBase");
/* 237 */     register(new Key(12, 36), "FDArray");
/* 238 */     register(new Key(12, 37), "FDSelect");
/* 239 */     register(new Key(12, 38), "FontName");
/*     */ 
/* 242 */     register(new Key(6), "BlueValues");
/* 243 */     register(new Key(7), "OtherBlues");
/* 244 */     register(new Key(8), "FamilyBlues");
/* 245 */     register(new Key(9), "FamilyOtherBlues");
/* 246 */     register(new Key(12, 9), "BlueScale");
/* 247 */     register(new Key(12, 10), "BlueShift");
/* 248 */     register(new Key(12, 11), "BlueFuzz");
/* 249 */     register(new Key(10), "StdHW");
/* 250 */     register(new Key(11), "StdVW");
/* 251 */     register(new Key(12, 12), "StemSnapH");
/* 252 */     register(new Key(12, 13), "StemSnapV");
/* 253 */     register(new Key(12, 14), "ForceBold");
/* 254 */     register(new Key(12, 15), "LanguageGroup");
/* 255 */     register(new Key(12, 16), "ExpansionFactor");
/* 256 */     register(new Key(12, 17), "initialRandomSeed");
/* 257 */     register(new Key(19), "Subrs");
/* 258 */     register(new Key(20), "defaultWidthX");
/* 259 */     register(new Key(21), "nominalWidthX");
/*     */   }
/*     */ 
/*     */   public static class Key
/*     */   {
/* 130 */     private int[] value = null;
/*     */ 
/*     */     public Key(int b0)
/*     */     {
/* 138 */       this(new int[] { b0 });
/*     */     }
/*     */ 
/*     */     public Key(int b0, int b1)
/*     */     {
/* 148 */       this(new int[] { b0, b1 });
/*     */     }
/*     */ 
/*     */     private Key(int[] value)
/*     */     {
/* 153 */       setValue(value);
/*     */     }
/*     */ 
/*     */     public int[] getValue()
/*     */     {
/* 162 */       return this.value;
/*     */     }
/*     */ 
/*     */     private void setValue(int[] value)
/*     */     {
/* 167 */       this.value = value;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 175 */       return Arrays.toString(getValue());
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 183 */       return Arrays.hashCode(getValue());
/*     */     }
/*     */ 
/*     */     public boolean equals(Object object)
/*     */     {
/* 191 */       if ((object instanceof Key))
/*     */       {
/* 193 */         Key that = (Key)object;
/* 194 */         return Arrays.equals(getValue(), that.getValue());
/*     */       }
/* 196 */       return false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.CFFOperator
 * JD-Core Version:    0.6.2
 */