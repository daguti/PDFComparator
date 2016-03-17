/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ public class PdfTransition
/*     */ {
/*     */   public static final int SPLITVOUT = 1;
/*     */   public static final int SPLITHOUT = 2;
/*     */   public static final int SPLITVIN = 3;
/*     */   public static final int SPLITHIN = 4;
/*     */   public static final int BLINDV = 5;
/*     */   public static final int BLINDH = 6;
/*     */   public static final int INBOX = 7;
/*     */   public static final int OUTBOX = 8;
/*     */   public static final int LRWIPE = 9;
/*     */   public static final int RLWIPE = 10;
/*     */   public static final int BTWIPE = 11;
/*     */   public static final int TBWIPE = 12;
/*     */   public static final int DISSOLVE = 13;
/*     */   public static final int LRGLITTER = 14;
/*     */   public static final int TBGLITTER = 15;
/*     */   public static final int DGLITTER = 16;
/*     */   protected int duration;
/*     */   protected int type;
/*     */ 
/*     */   public PdfTransition()
/*     */   {
/* 127 */     this(6);
/*     */   }
/*     */ 
/*     */   public PdfTransition(int type)
/*     */   {
/* 136 */     this(type, 1);
/*     */   }
/*     */ 
/*     */   public PdfTransition(int type, int duration)
/*     */   {
/* 146 */     this.duration = duration;
/* 147 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public int getDuration()
/*     */   {
/* 152 */     return this.duration;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/* 157 */     return this.type;
/*     */   }
/*     */ 
/*     */   public PdfDictionary getTransitionDictionary() {
/* 161 */     PdfDictionary trans = new PdfDictionary(PdfName.TRANS);
/* 162 */     switch (this.type) {
/*     */     case 1:
/* 164 */       trans.put(PdfName.S, PdfName.SPLIT);
/* 165 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 166 */       trans.put(PdfName.DM, PdfName.V);
/* 167 */       trans.put(PdfName.M, PdfName.O);
/* 168 */       break;
/*     */     case 2:
/* 170 */       trans.put(PdfName.S, PdfName.SPLIT);
/* 171 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 172 */       trans.put(PdfName.DM, PdfName.H);
/* 173 */       trans.put(PdfName.M, PdfName.O);
/* 174 */       break;
/*     */     case 3:
/* 176 */       trans.put(PdfName.S, PdfName.SPLIT);
/* 177 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 178 */       trans.put(PdfName.DM, PdfName.V);
/* 179 */       trans.put(PdfName.M, PdfName.I);
/* 180 */       break;
/*     */     case 4:
/* 182 */       trans.put(PdfName.S, PdfName.SPLIT);
/* 183 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 184 */       trans.put(PdfName.DM, PdfName.H);
/* 185 */       trans.put(PdfName.M, PdfName.I);
/* 186 */       break;
/*     */     case 5:
/* 188 */       trans.put(PdfName.S, PdfName.BLINDS);
/* 189 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 190 */       trans.put(PdfName.DM, PdfName.V);
/* 191 */       break;
/*     */     case 6:
/* 193 */       trans.put(PdfName.S, PdfName.BLINDS);
/* 194 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 195 */       trans.put(PdfName.DM, PdfName.H);
/* 196 */       break;
/*     */     case 7:
/* 198 */       trans.put(PdfName.S, PdfName.BOX);
/* 199 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 200 */       trans.put(PdfName.M, PdfName.I);
/* 201 */       break;
/*     */     case 8:
/* 203 */       trans.put(PdfName.S, PdfName.BOX);
/* 204 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 205 */       trans.put(PdfName.M, PdfName.O);
/* 206 */       break;
/*     */     case 9:
/* 208 */       trans.put(PdfName.S, PdfName.WIPE);
/* 209 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 210 */       trans.put(PdfName.DI, new PdfNumber(0));
/* 211 */       break;
/*     */     case 10:
/* 213 */       trans.put(PdfName.S, PdfName.WIPE);
/* 214 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 215 */       trans.put(PdfName.DI, new PdfNumber(180));
/* 216 */       break;
/*     */     case 11:
/* 218 */       trans.put(PdfName.S, PdfName.WIPE);
/* 219 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 220 */       trans.put(PdfName.DI, new PdfNumber(90));
/* 221 */       break;
/*     */     case 12:
/* 223 */       trans.put(PdfName.S, PdfName.WIPE);
/* 224 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 225 */       trans.put(PdfName.DI, new PdfNumber(270));
/* 226 */       break;
/*     */     case 13:
/* 228 */       trans.put(PdfName.S, PdfName.DISSOLVE);
/* 229 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 230 */       break;
/*     */     case 14:
/* 232 */       trans.put(PdfName.S, PdfName.GLITTER);
/* 233 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 234 */       trans.put(PdfName.DI, new PdfNumber(0));
/* 235 */       break;
/*     */     case 15:
/* 237 */       trans.put(PdfName.S, PdfName.GLITTER);
/* 238 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 239 */       trans.put(PdfName.DI, new PdfNumber(270));
/* 240 */       break;
/*     */     case 16:
/* 242 */       trans.put(PdfName.S, PdfName.GLITTER);
/* 243 */       trans.put(PdfName.D, new PdfNumber(this.duration));
/* 244 */       trans.put(PdfName.DI, new PdfNumber(315));
/*     */     }
/*     */ 
/* 247 */     return trans;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfTransition
 * JD-Core Version:    0.6.2
 */