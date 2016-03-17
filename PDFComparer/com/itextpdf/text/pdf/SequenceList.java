/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ public class SequenceList
/*     */ {
/*     */   protected static final int COMMA = 1;
/*     */   protected static final int MINUS = 2;
/*     */   protected static final int NOT = 3;
/*     */   protected static final int TEXT = 4;
/*     */   protected static final int NUMBER = 5;
/*     */   protected static final int END = 6;
/*     */   protected static final char EOT = 'ð¿¿';
/*     */   private static final int FIRST = 0;
/*     */   private static final int DIGIT = 1;
/*     */   private static final int OTHER = 2;
/*     */   private static final int DIGIT2 = 3;
/*     */   private static final String NOT_OTHER = "-,!0123456789";
/*     */   protected char[] text;
/*     */   protected int ptr;
/*     */   protected int number;
/*     */   protected String other;
/*     */   protected int low;
/*     */   protected int high;
/*     */   protected boolean odd;
/*     */   protected boolean even;
/*     */   protected boolean inverse;
/*     */ 
/*     */   protected SequenceList(String range)
/*     */   {
/*  89 */     this.ptr = 0;
/*  90 */     this.text = range.toCharArray();
/*     */   }
/*     */ 
/*     */   protected char nextChar() {
/*     */     while (true) {
/*  95 */       if (this.ptr >= this.text.length)
/*  96 */         return 65535;
/*  97 */       char c = this.text[(this.ptr++)];
/*  98 */       if (c > ' ')
/*  99 */         return c;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void putBack() {
/* 104 */     this.ptr -= 1;
/* 105 */     if (this.ptr < 0)
/* 106 */       this.ptr = 0;
/*     */   }
/*     */ 
/*     */   protected int getType() {
/* 110 */     StringBuffer buf = new StringBuffer();
/* 111 */     int state = 0;
/*     */     while (true) {
/* 113 */       char c = nextChar();
/* 114 */       if (c == 65535) {
/* 115 */         if (state == 1) {
/* 116 */           this.number = Integer.parseInt(this.other = buf.toString());
/* 117 */           return 5;
/*     */         }
/* 119 */         if (state == 2) {
/* 120 */           this.other = buf.toString().toLowerCase();
/* 121 */           return 4;
/*     */         }
/* 123 */         return 6;
/*     */       }
/* 125 */       switch (state) {
/*     */       case 0:
/* 127 */         switch (c) {
/*     */         case '!':
/* 129 */           return 3;
/*     */         case '-':
/* 131 */           return 2;
/*     */         case ',':
/* 133 */           return 1;
/*     */         }
/* 135 */         buf.append(c);
/* 136 */         if ((c >= '0') && (c <= '9'))
/* 137 */           state = 1;
/*     */         else
/* 139 */           state = 2;
/* 140 */         break;
/*     */       case 1:
/* 142 */         if ((c >= '0') && (c <= '9')) {
/* 143 */           buf.append(c);
/*     */         } else {
/* 145 */           putBack();
/* 146 */           this.number = Integer.parseInt(this.other = buf.toString());
/* 147 */           return 5;
/*     */         }
/*     */         break;
/*     */       case 2:
/* 151 */         if ("-,!0123456789".indexOf(c) < 0) {
/* 152 */           buf.append(c);
/*     */         } else {
/* 154 */           putBack();
/* 155 */           this.other = buf.toString().toLowerCase();
/* 156 */           return 4;
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void otherProc() {
/* 164 */     if ((this.other.equals("odd")) || (this.other.equals("o"))) {
/* 165 */       this.odd = true;
/* 166 */       this.even = false;
/*     */     }
/* 168 */     else if ((this.other.equals("even")) || (this.other.equals("e"))) {
/* 169 */       this.odd = false;
/* 170 */       this.even = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean getAttributes() {
/* 175 */     this.low = -1;
/* 176 */     this.high = -1;
/* 177 */     this.odd = (this.even = this.inverse = 0);
/* 178 */     int state = 2;
/*     */     while (true) {
/* 180 */       int type = getType();
/* 181 */       if ((type == 6) || (type == 1)) {
/* 182 */         if (state == 1)
/* 183 */           this.high = this.low;
/* 184 */         return type == 6;
/*     */       }
/* 186 */       switch (state) {
/*     */       case 2:
/* 188 */         switch (type) {
/*     */         case 3:
/* 190 */           this.inverse = true;
/* 191 */           break;
/*     */         case 2:
/* 193 */           state = 3;
/* 194 */           break;
/*     */         default:
/* 196 */           if (type == 5) {
/* 197 */             this.low = this.number;
/* 198 */             state = 1;
/*     */           }
/*     */           else {
/* 201 */             otherProc(); } break;
/* 202 */         }break;
/*     */       case 1:
/* 206 */         switch (type) {
/*     */         case 3:
/* 208 */           this.inverse = true;
/* 209 */           state = 2;
/* 210 */           this.high = this.low;
/* 211 */           break;
/*     */         case 2:
/* 213 */           state = 3;
/* 214 */           break;
/*     */         default:
/* 216 */           this.high = this.low;
/* 217 */           state = 2;
/* 218 */           otherProc();
/* 219 */         }break;
/*     */       case 3:
/* 223 */         switch (type) {
/*     */         case 3:
/* 225 */           this.inverse = true;
/* 226 */           state = 2;
/* 227 */           break;
/*     */         case 2:
/* 229 */           break;
/*     */         case 5:
/* 231 */           this.high = this.number;
/* 232 */           state = 2;
/* 233 */           break;
/*     */         case 4:
/*     */         default:
/* 235 */           state = 2;
/* 236 */           otherProc();
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static List<Integer> expand(String ranges, int maxNumber)
/*     */   {
/* 251 */     SequenceList parse = new SequenceList(ranges);
/* 252 */     LinkedList list = new LinkedList();
/* 253 */     boolean sair = false;
/* 254 */     while (!sair) {
/* 255 */       sair = parse.getAttributes();
/* 256 */       if ((parse.low != -1) || (parse.high != -1) || (parse.even) || (parse.odd))
/*     */       {
/* 258 */         if (parse.low < 1)
/* 259 */           parse.low = 1;
/* 260 */         if ((parse.high < 1) || (parse.high > maxNumber))
/* 261 */           parse.high = maxNumber;
/* 262 */         if (parse.low > maxNumber) {
/* 263 */           parse.low = maxNumber;
/*     */         }
/*     */ 
/* 266 */         int inc = 1;
/*     */         ListIterator it;
/* 267 */         if (parse.inverse) {
/* 268 */           if (parse.low > parse.high) {
/* 269 */             int t = parse.low;
/* 270 */             parse.low = parse.high;
/* 271 */             parse.high = t;
/*     */           }
/* 273 */           for (it = list.listIterator(); it.hasNext(); ) {
/* 274 */             int n = ((Integer)it.next()).intValue();
/* 275 */             if (((!parse.even) || ((n & 0x1) != 1)) && (
/* 277 */               (!parse.odd) || ((n & 0x1) != 0)))
/*     */             {
/* 279 */               if ((n >= parse.low) && (n <= parse.high))
/* 280 */                 it.remove();
/*     */             }
/*     */           }
/*     */         }
/* 284 */         else if (parse.low > parse.high) {
/* 285 */           inc = -1;
/* 286 */           if ((parse.odd) || (parse.even)) {
/* 287 */             inc--;
/* 288 */             if (parse.even)
/* 289 */               parse.low &= -2;
/*     */             else
/* 291 */               parse.low -= ((parse.low & 0x1) == 1 ? 0 : 1);
/*     */           }
/* 293 */           for (int k = parse.low; k >= parse.high; k += inc)
/* 294 */             list.add(Integer.valueOf(k));
/*     */         }
/*     */         else {
/* 297 */           if ((parse.odd) || (parse.even)) {
/* 298 */             inc++;
/* 299 */             if (parse.odd)
/* 300 */               parse.low |= 1;
/*     */             else
/* 302 */               parse.low += ((parse.low & 0x1) == 1 ? 1 : 0);
/*     */           }
/* 304 */           for (int k = parse.low; k <= parse.high; k += inc) {
/* 305 */             list.add(Integer.valueOf(k));
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 313 */     return list;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.SequenceList
 * JD-Core Version:    0.6.2
 */