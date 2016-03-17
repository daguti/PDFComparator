/*     */ package com.itextpdf.text.pdf.hyphenation;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Stack;
/*     */ 
/*     */ public class TernaryTree
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5313366505322983510L;
/*     */   protected char[] lo;
/*     */   protected char[] hi;
/*     */   protected char[] eq;
/*     */   protected char[] sc;
/*     */   protected CharVector kv;
/*     */   protected char root;
/*     */   protected char freenode;
/*     */   protected int length;
/*     */   protected static final int BLOCK_SIZE = 2048;
/*     */ 
/*     */   TernaryTree()
/*     */   {
/* 116 */     init();
/*     */   }
/*     */ 
/*     */   protected void init() {
/* 120 */     this.root = '\000';
/* 121 */     this.freenode = '\001';
/* 122 */     this.length = 0;
/* 123 */     this.lo = new char[2048];
/* 124 */     this.hi = new char[2048];
/* 125 */     this.eq = new char[2048];
/* 126 */     this.sc = new char[2048];
/* 127 */     this.kv = new CharVector();
/*     */   }
/*     */ 
/*     */   public void insert(String key, char val)
/*     */   {
/* 140 */     int len = key.length() + 1;
/*     */ 
/* 142 */     if (this.freenode + len > this.eq.length) {
/* 143 */       redimNodeArrays(this.eq.length + 2048);
/*     */     }
/* 145 */     char[] strkey = new char[len--];
/* 146 */     key.getChars(0, len, strkey, 0);
/* 147 */     strkey[len] = '\000';
/* 148 */     this.root = insert(this.root, strkey, 0, val);
/*     */   }
/*     */ 
/*     */   public void insert(char[] key, int start, char val) {
/* 152 */     int len = strlen(key) + 1;
/* 153 */     if (this.freenode + len > this.eq.length) {
/* 154 */       redimNodeArrays(this.eq.length + 2048);
/*     */     }
/* 156 */     this.root = insert(this.root, key, start, val);
/*     */   }
/*     */ 
/*     */   private char insert(char p, char[] key, int start, char val)
/*     */   {
/* 163 */     int len = strlen(key, start);
/* 164 */     if (p == 0)
/*     */     {
/* 168 */       p = this.freenode++;
/* 169 */       this.eq[p] = val;
/* 170 */       this.length += 1;
/* 171 */       this.hi[p] = '\000';
/* 172 */       if (len > 0) {
/* 173 */         this.sc[p] = 65535;
/* 174 */         this.lo[p] = ((char)this.kv.alloc(len + 1));
/*     */ 
/* 176 */         strcpy(this.kv.getArray(), this.lo[p], key, start);
/*     */       } else {
/* 178 */         this.sc[p] = '\000';
/* 179 */         this.lo[p] = '\000';
/*     */       }
/* 181 */       return p;
/*     */     }
/*     */ 
/* 184 */     if (this.sc[p] == 65535)
/*     */     {
/* 188 */       char pp = this.freenode++;
/* 189 */       this.lo[pp] = this.lo[p];
/* 190 */       this.eq[pp] = this.eq[p];
/* 191 */       this.lo[p] = '\000';
/* 192 */       if (len > 0) {
/* 193 */         this.sc[p] = this.kv.get(this.lo[pp]);
/* 194 */         this.eq[p] = pp;
/*     */         char tmp214_212 = pp;
/*     */         char[] tmp214_209 = this.lo; tmp214_209[tmp214_212] = ((char)(tmp214_209[tmp214_212] + '\001'));
/* 196 */         if (this.kv.get(this.lo[pp]) == 0)
/*     */         {
/* 198 */           this.lo[pp] = '\000';
/* 199 */           this.sc[pp] = '\000';
/* 200 */           this.hi[pp] = '\000';
/*     */         }
/*     */         else {
/* 203 */           this.sc[pp] = 65535;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 208 */         this.sc[pp] = 65535;
/* 209 */         this.hi[p] = pp;
/* 210 */         this.sc[p] = '\000';
/* 211 */         this.eq[p] = val;
/* 212 */         this.length += 1;
/* 213 */         return p;
/*     */       }
/*     */     }
/* 216 */     char s = key[start];
/* 217 */     if (s < this.sc[p])
/* 218 */       this.lo[p] = insert(this.lo[p], key, start, val);
/* 219 */     else if (s == this.sc[p]) {
/* 220 */       if (s != 0) {
/* 221 */         this.eq[p] = insert(this.eq[p], key, start + 1, val);
/*     */       }
/*     */       else
/* 224 */         this.eq[p] = val;
/*     */     }
/*     */     else {
/* 227 */       this.hi[p] = insert(this.hi[p], key, start, val);
/*     */     }
/* 229 */     return p;
/*     */   }
/*     */ 
/*     */   public static int strcmp(char[] a, int startA, char[] b, int startB)
/*     */   {
/* 236 */     for (; a[startA] == b[startB]; startB++) {
/* 237 */       if (a[startA] == 0)
/* 238 */         return 0;
/* 236 */       startA++;
/*     */     }
/*     */ 
/* 241 */     return a[startA] - b[startB];
/*     */   }
/*     */ 
/*     */   public static int strcmp(String str, char[] a, int start)
/*     */   {
/* 248 */     int len = str.length();
/* 249 */     for (int i = 0; i < len; i++) {
/* 250 */       int d = str.charAt(i) - a[(start + i)];
/* 251 */       if (d != 0) {
/* 252 */         return d;
/*     */       }
/* 254 */       if (a[(start + i)] == 0) {
/* 255 */         return d;
/*     */       }
/*     */     }
/* 258 */     if (a[(start + i)] != 0) {
/* 259 */       return -a[(start + i)];
/*     */     }
/* 261 */     return 0;
/*     */   }
/*     */ 
/*     */   public static void strcpy(char[] dst, int di, char[] src, int si)
/*     */   {
/* 266 */     while (src[si] != 0) {
/* 267 */       dst[(di++)] = src[(si++)];
/*     */     }
/* 269 */     dst[di] = '\000';
/*     */   }
/*     */ 
/*     */   public static int strlen(char[] a, int start) {
/* 273 */     int len = 0;
/* 274 */     for (int i = start; (i < a.length) && (a[i] != 0); i++) {
/* 275 */       len++;
/*     */     }
/* 277 */     return len;
/*     */   }
/*     */ 
/*     */   public static int strlen(char[] a) {
/* 281 */     return strlen(a, 0);
/*     */   }
/*     */ 
/*     */   public int find(String key) {
/* 285 */     int len = key.length();
/* 286 */     char[] strkey = new char[len + 1];
/* 287 */     key.getChars(0, len, strkey, 0);
/* 288 */     strkey[len] = '\000';
/*     */ 
/* 290 */     return find(strkey, 0);
/*     */   }
/*     */ 
/*     */   public int find(char[] key, int start)
/*     */   {
/* 295 */     char p = this.root;
/* 296 */     int i = start;
/*     */ 
/* 299 */     while (p != 0) {
/* 300 */       if (this.sc[p] == 65535) {
/* 301 */         if (strcmp(key, i, this.kv.getArray(), this.lo[p]) == 0) {
/* 302 */           return this.eq[p];
/*     */         }
/* 304 */         return -1;
/*     */       }
/*     */ 
/* 307 */       char c = key[i];
/* 308 */       int d = c - this.sc[p];
/* 309 */       if (d == 0) {
/* 310 */         if (c == 0) {
/* 311 */           return this.eq[p];
/*     */         }
/* 313 */         i++;
/* 314 */         p = this.eq[p];
/* 315 */       } else if (d < 0) {
/* 316 */         p = this.lo[p];
/*     */       } else {
/* 318 */         p = this.hi[p];
/*     */       }
/*     */     }
/* 321 */     return -1;
/*     */   }
/*     */ 
/*     */   public boolean knows(String key) {
/* 325 */     return find(key) >= 0;
/*     */   }
/*     */ 
/*     */   private void redimNodeArrays(int newsize)
/*     */   {
/* 330 */     int len = newsize < this.lo.length ? newsize : this.lo.length;
/* 331 */     char[] na = new char[newsize];
/* 332 */     System.arraycopy(this.lo, 0, na, 0, len);
/* 333 */     this.lo = na;
/* 334 */     na = new char[newsize];
/* 335 */     System.arraycopy(this.hi, 0, na, 0, len);
/* 336 */     this.hi = na;
/* 337 */     na = new char[newsize];
/* 338 */     System.arraycopy(this.eq, 0, na, 0, len);
/* 339 */     this.eq = na;
/* 340 */     na = new char[newsize];
/* 341 */     System.arraycopy(this.sc, 0, na, 0, len);
/* 342 */     this.sc = na;
/*     */   }
/*     */ 
/*     */   public int size() {
/* 346 */     return this.length;
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 351 */     TernaryTree t = new TernaryTree();
/* 352 */     t.lo = ((char[])this.lo.clone());
/* 353 */     t.hi = ((char[])this.hi.clone());
/* 354 */     t.eq = ((char[])this.eq.clone());
/* 355 */     t.sc = ((char[])this.sc.clone());
/* 356 */     t.kv = ((CharVector)this.kv.clone());
/* 357 */     t.root = this.root;
/* 358 */     t.freenode = this.freenode;
/* 359 */     t.length = this.length;
/*     */ 
/* 361 */     return t;
/*     */   }
/*     */ 
/*     */   protected void insertBalanced(String[] k, char[] v, int offset, int n)
/*     */   {
/* 372 */     if (n < 1) {
/* 373 */       return;
/*     */     }
/* 375 */     int m = n >> 1;
/*     */ 
/* 377 */     insert(k[(m + offset)], v[(m + offset)]);
/* 378 */     insertBalanced(k, v, offset, m);
/*     */ 
/* 380 */     insertBalanced(k, v, offset + m + 1, n - m - 1);
/*     */   }
/*     */ 
/*     */   public void balance()
/*     */   {
/* 390 */     int i = 0; int n = this.length;
/* 391 */     String[] k = new String[n];
/* 392 */     char[] v = new char[n];
/* 393 */     Iterator iter = new Iterator();
/* 394 */     while (iter.hasMoreElements()) {
/* 395 */       v[i] = iter.getValue();
/* 396 */       k[(i++)] = iter.nextElement();
/*     */     }
/* 398 */     init();
/* 399 */     insertBalanced(k, v, 0, n);
/*     */   }
/*     */ 
/*     */   public void trimToSize()
/*     */   {
/* 420 */     balance();
/*     */ 
/* 423 */     redimNodeArrays(this.freenode);
/*     */ 
/* 426 */     CharVector kx = new CharVector();
/* 427 */     kx.alloc(1);
/* 428 */     TernaryTree map = new TernaryTree();
/* 429 */     compact(kx, map, this.root);
/* 430 */     this.kv = kx;
/* 431 */     this.kv.trimToSize();
/*     */   }
/*     */ 
/*     */   private void compact(CharVector kx, TernaryTree map, char p)
/*     */   {
/* 436 */     if (p == 0) {
/* 437 */       return;
/*     */     }
/* 439 */     if (this.sc[p] == 65535) {
/* 440 */       int k = map.find(this.kv.getArray(), this.lo[p]);
/* 441 */       if (k < 0) {
/* 442 */         k = kx.alloc(strlen(this.kv.getArray(), this.lo[p]) + 1);
/* 443 */         strcpy(kx.getArray(), k, this.kv.getArray(), this.lo[p]);
/* 444 */         map.insert(kx.getArray(), k, (char)k);
/*     */       }
/* 446 */       this.lo[p] = ((char)k);
/*     */     } else {
/* 448 */       compact(kx, map, this.lo[p]);
/* 449 */       if (this.sc[p] != 0) {
/* 450 */         compact(kx, map, this.eq[p]);
/*     */       }
/* 452 */       compact(kx, map, this.hi[p]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Enumeration<String> keys()
/*     */   {
/* 458 */     return new Iterator();
/*     */   }
/*     */ 
/*     */   public void printStats()
/*     */   {
/* 638 */     System.out.println("Number of keys = " + Integer.toString(this.length));
/* 639 */     System.out.println("Node count = " + Integer.toString(this.freenode));
/*     */ 
/* 641 */     System.out.println("Key Array length = " + Integer.toString(this.kv.length()));
/*     */   }
/*     */ 
/*     */   public class Iterator
/*     */     implements Enumeration<String>
/*     */   {
/*     */     int cur;
/*     */     String curkey;
/*     */     Stack<Item> ns;
/*     */     StringBuffer ks;
/*     */ 
/*     */     public Iterator()
/*     */     {
/* 505 */       this.cur = -1;
/* 506 */       this.ns = new Stack();
/* 507 */       this.ks = new StringBuffer();
/* 508 */       rewind();
/*     */     }
/*     */ 
/*     */     public void rewind() {
/* 512 */       this.ns.removeAllElements();
/* 513 */       this.ks.setLength(0);
/* 514 */       this.cur = TernaryTree.this.root;
/* 515 */       run();
/*     */     }
/*     */ 
/*     */     public String nextElement() {
/* 519 */       String res = this.curkey;
/* 520 */       this.cur = up();
/* 521 */       run();
/* 522 */       return res;
/*     */     }
/*     */ 
/*     */     public char getValue() {
/* 526 */       if (this.cur >= 0) {
/* 527 */         return TernaryTree.this.eq[this.cur];
/*     */       }
/* 529 */       return '\000';
/*     */     }
/*     */ 
/*     */     public boolean hasMoreElements() {
/* 533 */       return this.cur != -1;
/*     */     }
/*     */ 
/*     */     private int up()
/*     */     {
/* 540 */       Item i = new Item();
/* 541 */       int res = 0;
/*     */ 
/* 543 */       if (this.ns.empty()) {
/* 544 */         return -1;
/*     */       }
/*     */ 
/* 547 */       if ((this.cur != 0) && (TernaryTree.this.sc[this.cur] == 0)) {
/* 548 */         return TernaryTree.this.lo[this.cur];
/*     */       }
/*     */ 
/* 551 */       boolean climb = true;
/*     */ 
/* 553 */       while (climb) {
/* 554 */         i = (Item)this.ns.pop();
/*     */         Item tmp76_75 = i; tmp76_75.child = ((char)(tmp76_75.child + '\001'));
/* 556 */         switch (i.child) {
/*     */         case '\001':
/* 558 */           if (TernaryTree.this.sc[i.parent] != 0) {
/* 559 */             res = TernaryTree.this.eq[i.parent];
/* 560 */             this.ns.push(i.clone());
/* 561 */             this.ks.append(TernaryTree.this.sc[i.parent]);
/*     */           }
/*     */           else
/*     */           {
/*     */             Item tmp180_179 = i; tmp180_179.child = ((char)(tmp180_179.child + '\001'));
/* 564 */             this.ns.push(i.clone());
/* 565 */             res = TernaryTree.this.hi[i.parent];
/*     */           }
/* 567 */           climb = false;
/* 568 */           break;
/*     */         case '\002':
/* 571 */           res = TernaryTree.this.hi[i.parent];
/* 572 */           this.ns.push(i.clone());
/* 573 */           if (this.ks.length() > 0) {
/* 574 */             this.ks.setLength(this.ks.length() - 1);
/*     */           }
/* 576 */           climb = false;
/* 577 */           break;
/*     */         default:
/* 580 */           if (this.ns.empty()) {
/* 581 */             return -1;
/*     */           }
/* 583 */           climb = true;
/*     */         }
/*     */       }
/*     */ 
/* 587 */       return res;
/*     */     }
/*     */ 
/*     */     private int run()
/*     */     {
/* 594 */       if (this.cur == -1) {
/* 595 */         return -1;
/*     */       }
/*     */ 
/* 598 */       boolean leaf = false;
/*     */       do
/*     */       {
/* 601 */         while (this.cur != 0) {
/* 602 */           if (TernaryTree.this.sc[this.cur] == 65535) {
/* 603 */             leaf = true;
/* 604 */             break;
/*     */           }
/* 606 */           this.ns.push(new Item((char)this.cur, '\000'));
/* 607 */           if (TernaryTree.this.sc[this.cur] == 0) {
/* 608 */             leaf = true;
/* 609 */             break;
/*     */           }
/* 611 */           this.cur = TernaryTree.this.lo[this.cur];
/*     */         }
/* 613 */         if (leaf)
/*     */         {
/*     */           break;
/*     */         }
/* 617 */         this.cur = up();
/* 618 */       }while (this.cur != -1);
/* 619 */       return -1;
/*     */ 
/* 624 */       StringBuffer buf = new StringBuffer(this.ks.toString());
/* 625 */       if (TernaryTree.this.sc[this.cur] == 65535) {
/* 626 */         int p = TernaryTree.this.lo[this.cur];
/* 627 */         while (TernaryTree.this.kv.get(p) != 0) {
/* 628 */           buf.append(TernaryTree.this.kv.get(p++));
/*     */         }
/*     */       }
/* 631 */       this.curkey = buf.toString();
/* 632 */       return 0;
/*     */     }
/*     */ 
/*     */     private class Item
/*     */       implements Cloneable
/*     */     {
/*     */       char parent;
/*     */       char child;
/*     */ 
/*     */       public Item()
/*     */       {
/* 478 */         this.parent = '\000';
/* 479 */         this.child = '\000';
/*     */       }
/*     */ 
/*     */       public Item(char p, char c) {
/* 483 */         this.parent = p;
/* 484 */         this.child = c;
/*     */       }
/*     */ 
/*     */       public Item clone()
/*     */       {
/* 489 */         return new Item(TernaryTree.Iterator.this, this.parent, this.child);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.hyphenation.TernaryTree
 * JD-Core Version:    0.6.2
 */