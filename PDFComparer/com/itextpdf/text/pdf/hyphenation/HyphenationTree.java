/*     */ package com.itextpdf.text.pdf.hyphenation;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class HyphenationTree extends TernaryTree
/*     */   implements PatternConsumer
/*     */ {
/*     */   private static final long serialVersionUID = -7763254239309429432L;
/*     */   protected ByteVector vspace;
/*     */   protected HashMap<String, ArrayList<Object>> stoplist;
/*     */   protected TernaryTree classmap;
/*     */   private transient TernaryTree ivalues;
/*     */ 
/*     */   public HyphenationTree()
/*     */   {
/*  58 */     this.stoplist = new HashMap(23);
/*  59 */     this.classmap = new TernaryTree();
/*  60 */     this.vspace = new ByteVector();
/*  61 */     this.vspace.alloc(1);
/*     */   }
/*     */ 
/*     */   protected int packValues(String values)
/*     */   {
/*  74 */     int n = values.length();
/*  75 */     int m = (n & 0x1) == 1 ? (n >> 1) + 2 : (n >> 1) + 1;
/*  76 */     int offset = this.vspace.alloc(m);
/*  77 */     byte[] va = this.vspace.getArray();
/*  78 */     for (int i = 0; i < n; i++) {
/*  79 */       int j = i >> 1;
/*  80 */       byte v = (byte)(values.charAt(i) - '0' + 1 & 0xF);
/*  81 */       if ((i & 0x1) == 1)
/*  82 */         va[(j + offset)] = ((byte)(va[(j + offset)] | v));
/*     */       else {
/*  84 */         va[(j + offset)] = ((byte)(v << 4));
/*     */       }
/*     */     }
/*  87 */     va[(m - 1 + offset)] = 0;
/*  88 */     return offset;
/*     */   }
/*     */ 
/*     */   protected String unpackValues(int k) {
/*  92 */     StringBuffer buf = new StringBuffer();
/*  93 */     byte v = this.vspace.get(k++);
/*  94 */     while (v != 0) {
/*  95 */       char c = (char)((v >>> 4) - 1 + 48);
/*  96 */       buf.append(c);
/*  97 */       c = (char)(v & 0xF);
/*  98 */       if (c == 0) {
/*     */         break;
/*     */       }
/* 101 */       c = (char)(c - '\001' + 48);
/* 102 */       buf.append(c);
/* 103 */       v = this.vspace.get(k++);
/*     */     }
/* 105 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public void loadSimplePatterns(InputStream stream) {
/* 109 */     SimplePatternParser pp = new SimplePatternParser();
/* 110 */     this.ivalues = new TernaryTree();
/*     */ 
/* 112 */     pp.parse(stream, this);
/*     */ 
/* 116 */     trimToSize();
/* 117 */     this.vspace.trimToSize();
/* 118 */     this.classmap.trimToSize();
/*     */ 
/* 121 */     this.ivalues = null;
/*     */   }
/*     */ 
/*     */   public String findPattern(String pat)
/*     */   {
/* 126 */     int k = super.find(pat);
/* 127 */     if (k >= 0) {
/* 128 */       return unpackValues(k);
/*     */     }
/* 130 */     return "";
/*     */   }
/*     */ 
/*     */   protected int hstrcmp(char[] s, int si, char[] t, int ti)
/*     */   {
/* 138 */     for (; s[si] == t[ti]; ti++) {
/* 139 */       if (s[si] == 0)
/* 140 */         return 0;
/* 138 */       si++;
/*     */     }
/*     */ 
/* 143 */     if (t[ti] == 0) {
/* 144 */       return 0;
/*     */     }
/* 146 */     return s[si] - t[ti];
/*     */   }
/*     */ 
/*     */   protected byte[] getValues(int k) {
/* 150 */     StringBuffer buf = new StringBuffer();
/* 151 */     byte v = this.vspace.get(k++);
/* 152 */     while (v != 0) {
/* 153 */       char c = (char)((v >>> 4) - 1);
/* 154 */       buf.append(c);
/* 155 */       c = (char)(v & 0xF);
/* 156 */       if (c == 0) {
/*     */         break;
/*     */       }
/* 159 */       c = (char)(c - '\001');
/* 160 */       buf.append(c);
/* 161 */       v = this.vspace.get(k++);
/*     */     }
/* 163 */     byte[] res = new byte[buf.length()];
/* 164 */     for (int i = 0; i < res.length; i++) {
/* 165 */       res[i] = ((byte)buf.charAt(i));
/*     */     }
/* 167 */     return res;
/*     */   }
/*     */ 
/*     */   protected void searchPatterns(char[] word, int index, byte[] il)
/*     */   {
/* 196 */     int i = index;
/*     */ 
/* 198 */     char sp = word[i];
/* 199 */     char p = this.root;
/*     */ 
/* 201 */     while ((p > 0) && (p < this.sc.length)) {
/* 202 */       if (this.sc[p] == 65535) {
/* 203 */         if (hstrcmp(word, i, this.kv.getArray(), this.lo[p]) == 0) {
/* 204 */           byte[] values = getValues(this.eq[p]);
/* 205 */           int j = index;
/* 206 */           for (byte value : values) {
/* 207 */             if ((j < il.length) && (value > il[j])) {
/* 208 */               il[j] = value;
/*     */             }
/* 210 */             j++;
/*     */           }
/*     */         }
/* 213 */         return;
/*     */       }
/* 215 */       int d = sp - this.sc[p];
/* 216 */       if (d == 0) {
/* 217 */         if (sp == 0) {
/*     */           break;
/*     */         }
/* 220 */         sp = word[(++i)];
/* 221 */         p = this.eq[p];
/* 222 */         char q = p;
/*     */ 
/* 226 */         while ((q > 0) && (q < this.sc.length) && 
/* 227 */           (this.sc[q] != 65535))
/*     */         {
/* 230 */           if (this.sc[q] == 0) {
/* 231 */             byte[] values = getValues(this.eq[q]);
/* 232 */             int j = index;
/* 233 */             for (byte value : values) {
/* 234 */               if ((j < il.length) && (value > il[j])) {
/* 235 */                 il[j] = value;
/*     */               }
/* 237 */               j++;
/*     */             }
/* 239 */             break;
/*     */           }
/* 241 */           q = this.lo[q];
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 251 */       p = d < 0 ? this.lo[p] : this.hi[p];
/*     */     }
/*     */   }
/*     */ 
/*     */   public Hyphenation hyphenate(String word, int remainCharCount, int pushCharCount)
/*     */   {
/* 268 */     char[] w = word.toCharArray();
/* 269 */     return hyphenate(w, 0, w.length, remainCharCount, pushCharCount);
/*     */   }
/*     */ 
/*     */   public Hyphenation hyphenate(char[] w, int offset, int len, int remainCharCount, int pushCharCount)
/*     */   {
/* 310 */     char[] word = new char[len + 3];
/*     */ 
/* 313 */     char[] c = new char[2];
/* 314 */     int iIgnoreAtBeginning = 0;
/* 315 */     int iLength = len;
/* 316 */     boolean bEndOfLetters = false;
/* 317 */     for (int i = 1; i <= len; i++) {
/* 318 */       c[0] = w[(offset + i - 1)];
/* 319 */       int nc = this.classmap.find(c, 0);
/* 320 */       if (nc < 0) {
/* 321 */         if (i == 1 + iIgnoreAtBeginning)
/*     */         {
/* 323 */           iIgnoreAtBeginning++;
/*     */         }
/*     */         else {
/* 326 */           bEndOfLetters = true;
/*     */         }
/* 328 */         iLength--;
/*     */       }
/* 330 */       else if (!bEndOfLetters) {
/* 331 */         word[(i - iIgnoreAtBeginning)] = ((char)nc);
/*     */       } else {
/* 333 */         return null;
/*     */       }
/*     */     }
/*     */ 
/* 337 */     int origlen = len;
/* 338 */     len = iLength;
/* 339 */     if (len < remainCharCount + pushCharCount)
/*     */     {
/* 341 */       return null;
/*     */     }
/* 343 */     int[] result = new int[len + 1];
/* 344 */     int k = 0;
/*     */ 
/* 347 */     String sw = new String(word, 1, len);
/* 348 */     if (this.stoplist.containsKey(sw))
/*     */     {
/* 350 */       ArrayList hw = (ArrayList)this.stoplist.get(sw);
/* 351 */       int j = 0;
/* 352 */       for (i = 0; i < hw.size(); i++) {
/* 353 */         Object o = hw.get(i);
/*     */ 
/* 356 */         if ((o instanceof String)) {
/* 357 */           j += ((String)o).length();
/* 358 */           if ((j >= remainCharCount) && (j < len - pushCharCount))
/* 359 */             result[(k++)] = (j + iIgnoreAtBeginning);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 365 */       word[0] = '.';
/* 366 */       word[(len + 1)] = '.';
/* 367 */       word[(len + 2)] = '\000';
/* 368 */       byte[] il = new byte[len + 3];
/* 369 */       for (i = 0; i < len + 1; i++) {
/* 370 */         searchPatterns(word, i, il);
/*     */       }
/*     */ 
/* 377 */       for (i = 0; i < len; i++) {
/* 378 */         if (((il[(i + 1)] & 0x1) == 1) && (i >= remainCharCount) && (i <= len - pushCharCount))
/*     */         {
/* 380 */           result[(k++)] = (i + iIgnoreAtBeginning);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 386 */     if (k > 0)
/*     */     {
/* 388 */       int[] res = new int[k];
/* 389 */       System.arraycopy(result, 0, res, 0, k);
/* 390 */       return new Hyphenation(new String(w, offset, origlen), res);
/*     */     }
/* 392 */     return null;
/*     */   }
/*     */ 
/*     */   public void addClass(String chargroup)
/*     */   {
/* 409 */     if (chargroup.length() > 0) {
/* 410 */       char equivChar = chargroup.charAt(0);
/* 411 */       char[] key = new char[2];
/* 412 */       key[1] = '\000';
/* 413 */       for (int i = 0; i < chargroup.length(); i++) {
/* 414 */         key[0] = chargroup.charAt(i);
/* 415 */         this.classmap.insert(key, 0, equivChar);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addException(String word, ArrayList<Object> hyphenatedword)
/*     */   {
/* 429 */     this.stoplist.put(word, hyphenatedword);
/*     */   }
/*     */ 
/*     */   public void addPattern(String pattern, String ivalue)
/*     */   {
/* 443 */     int k = this.ivalues.find(ivalue);
/* 444 */     if (k <= 0) {
/* 445 */       k = packValues(ivalue);
/* 446 */       this.ivalues.insert(ivalue, (char)k);
/*     */     }
/* 448 */     insert(pattern, (char)k);
/*     */   }
/*     */ 
/*     */   public void printStats()
/*     */   {
/* 453 */     System.out.println("Value space size = " + Integer.toString(this.vspace.length()));
/*     */ 
/* 455 */     super.printStats();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.hyphenation.HyphenationTree
 * JD-Core Version:    0.6.2
 */