/*     */ package org.apache.fontbox.cff;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class CharStringCommand
/*     */ {
/*  33 */   private Key commandKey = null;
/*     */   public static final Map<Key, String> TYPE1_VOCABULARY;
/* 305 */   public static final Map<Key, String> TYPE2_VOCABULARY = Collections.unmodifiableMap(map);
/*     */ 
/*     */   public CharStringCommand(int b0)
/*     */   {
/*  42 */     setKey(new Key(b0));
/*     */   }
/*     */ 
/*     */   public CharStringCommand(int b0, int b1)
/*     */   {
/*  53 */     setKey(new Key(b0, b1));
/*     */   }
/*     */ 
/*     */   public CharStringCommand(int[] values)
/*     */   {
/*  63 */     setKey(new Key(values));
/*     */   }
/*     */ 
/*     */   public Key getKey()
/*     */   {
/*  72 */     return this.commandKey;
/*     */   }
/*     */ 
/*     */   private void setKey(Key key)
/*     */   {
/*  77 */     this.commandKey = key;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     return getKey().toString();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  93 */     return getKey().hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 101 */     if ((object instanceof CharStringCommand))
/*     */     {
/* 103 */       CharStringCommand that = (CharStringCommand)object;
/* 104 */       return getKey().equals(that.getKey());
/*     */     }
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 216 */     Map map = new LinkedHashMap();
/* 217 */     map.put(new Key(1), "hstem");
/* 218 */     map.put(new Key(3), "vstem");
/* 219 */     map.put(new Key(4), "vmoveto");
/* 220 */     map.put(new Key(5), "rlineto");
/* 221 */     map.put(new Key(6), "hlineto");
/* 222 */     map.put(new Key(7), "vlineto");
/* 223 */     map.put(new Key(8), "rrcurveto");
/* 224 */     map.put(new Key(9), "closepath");
/* 225 */     map.put(new Key(10), "callsubr");
/* 226 */     map.put(new Key(11), "return");
/* 227 */     map.put(new Key(12), "escape");
/* 228 */     map.put(new Key(12, 0), "dotsection");
/* 229 */     map.put(new Key(12, 1), "vstem3");
/* 230 */     map.put(new Key(12, 2), "hstem3");
/* 231 */     map.put(new Key(12, 6), "seac");
/* 232 */     map.put(new Key(12, 7), "sbw");
/* 233 */     map.put(new Key(12, 12), "div");
/* 234 */     map.put(new Key(12, 16), "callothersubr");
/* 235 */     map.put(new Key(12, 17), "pop");
/* 236 */     map.put(new Key(12, 33), "setcurrentpoint");
/* 237 */     map.put(new Key(13), "hsbw");
/* 238 */     map.put(new Key(14), "endchar");
/* 239 */     map.put(new Key(21), "rmoveto");
/* 240 */     map.put(new Key(22), "hmoveto");
/* 241 */     map.put(new Key(30), "vhcurveto");
/* 242 */     map.put(new Key(31), "hvcurveto");
/*     */ 
/* 244 */     TYPE1_VOCABULARY = Collections.unmodifiableMap(map);
/*     */ 
/* 254 */     Map map = new LinkedHashMap();
/* 255 */     map.put(new Key(1), "hstem");
/* 256 */     map.put(new Key(3), "vstem");
/* 257 */     map.put(new Key(4), "vmoveto");
/* 258 */     map.put(new Key(5), "rlineto");
/* 259 */     map.put(new Key(6), "hlineto");
/* 260 */     map.put(new Key(7), "vlineto");
/* 261 */     map.put(new Key(8), "rrcurveto");
/* 262 */     map.put(new Key(10), "callsubr");
/* 263 */     map.put(new Key(11), "return");
/* 264 */     map.put(new Key(12), "escape");
/* 265 */     map.put(new Key(12, 3), "and");
/* 266 */     map.put(new Key(12, 4), "or");
/* 267 */     map.put(new Key(12, 5), "not");
/* 268 */     map.put(new Key(12, 9), "abs");
/* 269 */     map.put(new Key(12, 10), "add");
/* 270 */     map.put(new Key(12, 11), "sub");
/* 271 */     map.put(new Key(12, 12), "div");
/* 272 */     map.put(new Key(12, 14), "neg");
/* 273 */     map.put(new Key(12, 15), "eq");
/* 274 */     map.put(new Key(12, 18), "drop");
/* 275 */     map.put(new Key(12, 20), "put");
/* 276 */     map.put(new Key(12, 21), "get");
/* 277 */     map.put(new Key(12, 22), "ifelse");
/* 278 */     map.put(new Key(12, 23), "random");
/* 279 */     map.put(new Key(12, 24), "mul");
/* 280 */     map.put(new Key(12, 26), "sqrt");
/* 281 */     map.put(new Key(12, 27), "dup");
/* 282 */     map.put(new Key(12, 28), "exch");
/* 283 */     map.put(new Key(12, 29), "index");
/* 284 */     map.put(new Key(12, 30), "roll");
/* 285 */     map.put(new Key(12, 34), "hflex");
/* 286 */     map.put(new Key(12, 35), "flex");
/* 287 */     map.put(new Key(12, 36), "hflex1");
/* 288 */     map.put(new Key(12, 37), "flex1");
/* 289 */     map.put(new Key(14), "endchar");
/* 290 */     map.put(new Key(18), "hstemhm");
/* 291 */     map.put(new Key(19), "hintmask");
/* 292 */     map.put(new Key(20), "cntrmask");
/* 293 */     map.put(new Key(21), "rmoveto");
/* 294 */     map.put(new Key(22), "hmoveto");
/* 295 */     map.put(new Key(23), "vstemhm");
/* 296 */     map.put(new Key(24), "rcurveline");
/* 297 */     map.put(new Key(25), "rlinecurve");
/* 298 */     map.put(new Key(26), "vvcurveto");
/* 299 */     map.put(new Key(27), "hhcurveto");
/* 300 */     map.put(new Key(28), "shortint");
/* 301 */     map.put(new Key(29), "callgsubr");
/* 302 */     map.put(new Key(30), "vhcurveto");
/* 303 */     map.put(new Key(31), "hvcurveto");
/*     */   }
/*     */ 
/*     */   public static class Key
/*     */   {
/* 115 */     private int[] keyValues = null;
/*     */ 
/*     */     public Key(int b0)
/*     */     {
/* 124 */       setValue(new int[] { b0 });
/*     */     }
/*     */ 
/*     */     public Key(int b0, int b1)
/*     */     {
/* 135 */       setValue(new int[] { b0, b1 });
/*     */     }
/*     */ 
/*     */     public Key(int[] values)
/*     */     {
/* 145 */       setValue(values);
/*     */     }
/*     */ 
/*     */     public int[] getValue()
/*     */     {
/* 155 */       return this.keyValues;
/*     */     }
/*     */ 
/*     */     private void setValue(int[] value)
/*     */     {
/* 160 */       this.keyValues = value;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 168 */       return Arrays.toString(getValue());
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 176 */       if (this.keyValues[0] == 12)
/*     */       {
/* 178 */         if (this.keyValues.length > 1)
/*     */         {
/* 180 */           return this.keyValues[0] ^ this.keyValues[1];
/*     */         }
/*     */       }
/* 183 */       return this.keyValues[0];
/*     */     }
/*     */ 
/*     */     public boolean equals(Object object)
/*     */     {
/* 191 */       if ((object instanceof Key))
/*     */       {
/* 193 */         Key that = (Key)object;
/* 194 */         if ((this.keyValues[0] == 12) && (that.keyValues[0] == 12))
/*     */         {
/* 196 */           if ((this.keyValues.length > 1) && (that.keyValues.length > 1))
/*     */           {
/* 198 */             return this.keyValues[1] == that.keyValues[1];
/*     */           }
/*     */ 
/* 201 */           return this.keyValues.length == that.keyValues.length;
/*     */         }
/* 203 */         return this.keyValues[0] == that.keyValues[0];
/*     */       }
/* 205 */       return false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.CharStringCommand
 * JD-Core Version:    0.6.2
 */