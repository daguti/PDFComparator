/*     */ package com.itextpdf.text.pdf.codec.wmf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.BaseFont;
/*     */ import com.itextpdf.text.pdf.PdfContentByte;
/*     */ import com.itextpdf.text.pdf.codec.BmpImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class MetaDo
/*     */ {
/*     */   public static final int META_SETBKCOLOR = 513;
/*     */   public static final int META_SETBKMODE = 258;
/*     */   public static final int META_SETMAPMODE = 259;
/*     */   public static final int META_SETROP2 = 260;
/*     */   public static final int META_SETRELABS = 261;
/*     */   public static final int META_SETPOLYFILLMODE = 262;
/*     */   public static final int META_SETSTRETCHBLTMODE = 263;
/*     */   public static final int META_SETTEXTCHAREXTRA = 264;
/*     */   public static final int META_SETTEXTCOLOR = 521;
/*     */   public static final int META_SETTEXTJUSTIFICATION = 522;
/*     */   public static final int META_SETWINDOWORG = 523;
/*     */   public static final int META_SETWINDOWEXT = 524;
/*     */   public static final int META_SETVIEWPORTORG = 525;
/*     */   public static final int META_SETVIEWPORTEXT = 526;
/*     */   public static final int META_OFFSETWINDOWORG = 527;
/*     */   public static final int META_SCALEWINDOWEXT = 1040;
/*     */   public static final int META_OFFSETVIEWPORTORG = 529;
/*     */   public static final int META_SCALEVIEWPORTEXT = 1042;
/*     */   public static final int META_LINETO = 531;
/*     */   public static final int META_MOVETO = 532;
/*     */   public static final int META_EXCLUDECLIPRECT = 1045;
/*     */   public static final int META_INTERSECTCLIPRECT = 1046;
/*     */   public static final int META_ARC = 2071;
/*     */   public static final int META_ELLIPSE = 1048;
/*     */   public static final int META_FLOODFILL = 1049;
/*     */   public static final int META_PIE = 2074;
/*     */   public static final int META_RECTANGLE = 1051;
/*     */   public static final int META_ROUNDRECT = 1564;
/*     */   public static final int META_PATBLT = 1565;
/*     */   public static final int META_SAVEDC = 30;
/*     */   public static final int META_SETPIXEL = 1055;
/*     */   public static final int META_OFFSETCLIPRGN = 544;
/*     */   public static final int META_TEXTOUT = 1313;
/*     */   public static final int META_BITBLT = 2338;
/*     */   public static final int META_STRETCHBLT = 2851;
/*     */   public static final int META_POLYGON = 804;
/*     */   public static final int META_POLYLINE = 805;
/*     */   public static final int META_ESCAPE = 1574;
/*     */   public static final int META_RESTOREDC = 295;
/*     */   public static final int META_FILLREGION = 552;
/*     */   public static final int META_FRAMEREGION = 1065;
/*     */   public static final int META_INVERTREGION = 298;
/*     */   public static final int META_PAINTREGION = 299;
/*     */   public static final int META_SELECTCLIPREGION = 300;
/*     */   public static final int META_SELECTOBJECT = 301;
/*     */   public static final int META_SETTEXTALIGN = 302;
/*     */   public static final int META_CHORD = 2096;
/*     */   public static final int META_SETMAPPERFLAGS = 561;
/*     */   public static final int META_EXTTEXTOUT = 2610;
/*     */   public static final int META_SETDIBTODEV = 3379;
/*     */   public static final int META_SELECTPALETTE = 564;
/*     */   public static final int META_REALIZEPALETTE = 53;
/*     */   public static final int META_ANIMATEPALETTE = 1078;
/*     */   public static final int META_SETPALENTRIES = 55;
/*     */   public static final int META_POLYPOLYGON = 1336;
/*     */   public static final int META_RESIZEPALETTE = 313;
/*     */   public static final int META_DIBBITBLT = 2368;
/*     */   public static final int META_DIBSTRETCHBLT = 2881;
/*     */   public static final int META_DIBCREATEPATTERNBRUSH = 322;
/*     */   public static final int META_STRETCHDIB = 3907;
/*     */   public static final int META_EXTFLOODFILL = 1352;
/*     */   public static final int META_DELETEOBJECT = 496;
/*     */   public static final int META_CREATEPALETTE = 247;
/*     */   public static final int META_CREATEPATTERNBRUSH = 505;
/*     */   public static final int META_CREATEPENINDIRECT = 762;
/*     */   public static final int META_CREATEFONTINDIRECT = 763;
/*     */   public static final int META_CREATEBRUSHINDIRECT = 764;
/*     */   public static final int META_CREATEREGION = 1791;
/*     */   public PdfContentByte cb;
/*     */   public InputMeta in;
/*     */   int left;
/*     */   int top;
/*     */   int right;
/*     */   int bottom;
/*     */   int inch;
/* 140 */   MetaState state = new MetaState();
/*     */ 
/*     */   public MetaDo(InputStream in, PdfContentByte cb) {
/* 143 */     this.cb = cb;
/* 144 */     this.in = new InputMeta(in);
/*     */   }
/*     */ 
/*     */   public void readAll() throws IOException, DocumentException {
/* 148 */     if (this.in.readInt() != -1698247209) {
/* 149 */       throw new DocumentException(MessageLocalization.getComposedMessage("not.a.placeable.windows.metafile", new Object[0]));
/*     */     }
/* 151 */     this.in.readWord();
/* 152 */     this.left = this.in.readShort();
/* 153 */     this.top = this.in.readShort();
/* 154 */     this.right = this.in.readShort();
/* 155 */     this.bottom = this.in.readShort();
/* 156 */     this.inch = this.in.readWord();
/* 157 */     this.state.setScalingX((this.right - this.left) / this.inch * 72.0F);
/* 158 */     this.state.setScalingY((this.bottom - this.top) / this.inch * 72.0F);
/* 159 */     this.state.setOffsetWx(this.left);
/* 160 */     this.state.setOffsetWy(this.top);
/* 161 */     this.state.setExtentWx(this.right - this.left);
/* 162 */     this.state.setExtentWy(this.bottom - this.top);
/* 163 */     this.in.readInt();
/* 164 */     this.in.readWord();
/* 165 */     this.in.skip(18);
/*     */ 
/* 169 */     this.cb.setLineCap(1);
/* 170 */     this.cb.setLineJoin(1);
/*     */     while (true) {
/* 172 */       int lenMarker = this.in.getLength();
/* 173 */       int tsize = this.in.readInt();
/* 174 */       if (tsize < 3)
/*     */         break;
/* 176 */       int function = this.in.readWord();
/* 177 */       switch (function) {
/*     */       case 0:
/* 179 */         break;
/*     */       case 247:
/*     */       case 322:
/*     */       case 1791:
/* 183 */         this.state.addMetaObject(new MetaObject());
/* 184 */         break;
/*     */       case 762:
/* 187 */         MetaPen pen = new MetaPen();
/* 188 */         pen.init(this.in);
/* 189 */         this.state.addMetaObject(pen);
/* 190 */         break;
/*     */       case 764:
/* 194 */         MetaBrush brush = new MetaBrush();
/* 195 */         brush.init(this.in);
/* 196 */         this.state.addMetaObject(brush);
/* 197 */         break;
/*     */       case 763:
/* 201 */         MetaFont font = new MetaFont();
/* 202 */         font.init(this.in);
/* 203 */         this.state.addMetaObject(font);
/* 204 */         break;
/*     */       case 301:
/* 208 */         int idx = this.in.readWord();
/* 209 */         this.state.selectMetaObject(idx, this.cb);
/* 210 */         break;
/*     */       case 496:
/* 214 */         int idx = this.in.readWord();
/* 215 */         this.state.deleteMetaObject(idx);
/* 216 */         break;
/*     */       case 30:
/* 219 */         this.state.saveState(this.cb);
/* 220 */         break;
/*     */       case 295:
/* 223 */         int idx = this.in.readShort();
/* 224 */         this.state.restoreState(idx, this.cb);
/* 225 */         break;
/*     */       case 523:
/* 228 */         this.state.setOffsetWy(this.in.readShort());
/* 229 */         this.state.setOffsetWx(this.in.readShort());
/* 230 */         break;
/*     */       case 524:
/* 232 */         this.state.setExtentWy(this.in.readShort());
/* 233 */         this.state.setExtentWx(this.in.readShort());
/* 234 */         break;
/*     */       case 532:
/* 237 */         int y = this.in.readShort();
/* 238 */         Point p = new Point(this.in.readShort(), y);
/* 239 */         this.state.setCurrentPoint(p);
/* 240 */         break;
/*     */       case 531:
/* 244 */         int y = this.in.readShort();
/* 245 */         int x = this.in.readShort();
/* 246 */         Point p = this.state.getCurrentPoint();
/* 247 */         this.cb.moveTo(this.state.transformX(p.x), this.state.transformY(p.y));
/* 248 */         this.cb.lineTo(this.state.transformX(x), this.state.transformY(y));
/* 249 */         this.cb.stroke();
/* 250 */         this.state.setCurrentPoint(new Point(x, y));
/* 251 */         break;
/*     */       case 805:
/* 255 */         this.state.setLineJoinPolygon(this.cb);
/* 256 */         int len = this.in.readWord();
/* 257 */         int x = this.in.readShort();
/* 258 */         int y = this.in.readShort();
/* 259 */         this.cb.moveTo(this.state.transformX(x), this.state.transformY(y));
/* 260 */         for (int k = 1; k < len; k++) {
/* 261 */           x = this.in.readShort();
/* 262 */           y = this.in.readShort();
/* 263 */           this.cb.lineTo(this.state.transformX(x), this.state.transformY(y));
/*     */         }
/* 265 */         this.cb.stroke();
/* 266 */         break;
/*     */       case 804:
/* 270 */         if (!isNullStrokeFill(false))
/*     */         {
/* 272 */           int len = this.in.readWord();
/* 273 */           int sx = this.in.readShort();
/* 274 */           int sy = this.in.readShort();
/* 275 */           this.cb.moveTo(this.state.transformX(sx), this.state.transformY(sy));
/* 276 */           for (int k = 1; k < len; k++) {
/* 277 */             int x = this.in.readShort();
/* 278 */             int y = this.in.readShort();
/* 279 */             this.cb.lineTo(this.state.transformX(x), this.state.transformY(y));
/*     */           }
/* 281 */           this.cb.lineTo(this.state.transformX(sx), this.state.transformY(sy));
/* 282 */           strokeAndFill();
/* 283 */         }break;
/*     */       case 1336:
/* 287 */         if (!isNullStrokeFill(false))
/*     */         {
/* 289 */           int numPoly = this.in.readWord();
/* 290 */           int[] lens = new int[numPoly];
/* 291 */           for (int k = 0; k < lens.length; k++)
/* 292 */             lens[k] = this.in.readWord();
/* 293 */           for (int j = 0; j < lens.length; j++) {
/* 294 */             int len = lens[j];
/* 295 */             int sx = this.in.readShort();
/* 296 */             int sy = this.in.readShort();
/* 297 */             this.cb.moveTo(this.state.transformX(sx), this.state.transformY(sy));
/* 298 */             for (int k = 1; k < len; k++) {
/* 299 */               int x = this.in.readShort();
/* 300 */               int y = this.in.readShort();
/* 301 */               this.cb.lineTo(this.state.transformX(x), this.state.transformY(y));
/*     */             }
/* 303 */             this.cb.lineTo(this.state.transformX(sx), this.state.transformY(sy));
/*     */           }
/* 305 */           strokeAndFill();
/* 306 */         }break;
/*     */       case 1048:
/* 310 */         if (!isNullStrokeFill(this.state.getLineNeutral()))
/*     */         {
/* 312 */           int b = this.in.readShort();
/* 313 */           int r = this.in.readShort();
/* 314 */           int t = this.in.readShort();
/* 315 */           int l = this.in.readShort();
/* 316 */           this.cb.arc(this.state.transformX(l), this.state.transformY(b), this.state.transformX(r), this.state.transformY(t), 0.0F, 360.0F);
/* 317 */           strokeAndFill();
/* 318 */         }break;
/*     */       case 2071:
/* 322 */         if (!isNullStrokeFill(this.state.getLineNeutral()))
/*     */         {
/* 324 */           float yend = this.state.transformY(this.in.readShort());
/* 325 */           float xend = this.state.transformX(this.in.readShort());
/* 326 */           float ystart = this.state.transformY(this.in.readShort());
/* 327 */           float xstart = this.state.transformX(this.in.readShort());
/* 328 */           float b = this.state.transformY(this.in.readShort());
/* 329 */           float r = this.state.transformX(this.in.readShort());
/* 330 */           float t = this.state.transformY(this.in.readShort());
/* 331 */           float l = this.state.transformX(this.in.readShort());
/* 332 */           float cx = (r + l) / 2.0F;
/* 333 */           float cy = (t + b) / 2.0F;
/* 334 */           float arc1 = getArc(cx, cy, xstart, ystart);
/* 335 */           float arc2 = getArc(cx, cy, xend, yend);
/* 336 */           arc2 -= arc1;
/* 337 */           if (arc2 <= 0.0F)
/* 338 */             arc2 += 360.0F;
/* 339 */           this.cb.arc(l, b, r, t, arc1, arc2);
/* 340 */           this.cb.stroke();
/* 341 */         }break;
/*     */       case 2074:
/* 345 */         if (!isNullStrokeFill(this.state.getLineNeutral()))
/*     */         {
/* 347 */           float yend = this.state.transformY(this.in.readShort());
/* 348 */           float xend = this.state.transformX(this.in.readShort());
/* 349 */           float ystart = this.state.transformY(this.in.readShort());
/* 350 */           float xstart = this.state.transformX(this.in.readShort());
/* 351 */           float b = this.state.transformY(this.in.readShort());
/* 352 */           float r = this.state.transformX(this.in.readShort());
/* 353 */           float t = this.state.transformY(this.in.readShort());
/* 354 */           float l = this.state.transformX(this.in.readShort());
/* 355 */           float cx = (r + l) / 2.0F;
/* 356 */           float cy = (t + b) / 2.0F;
/* 357 */           float arc1 = getArc(cx, cy, xstart, ystart);
/* 358 */           float arc2 = getArc(cx, cy, xend, yend);
/* 359 */           arc2 -= arc1;
/* 360 */           if (arc2 <= 0.0F)
/* 361 */             arc2 += 360.0F;
/* 362 */           ArrayList ar = PdfContentByte.bezierArc(l, b, r, t, arc1, arc2);
/* 363 */           if (!ar.isEmpty())
/*     */           {
/* 365 */             float[] pt = (float[])ar.get(0);
/* 366 */             this.cb.moveTo(cx, cy);
/* 367 */             this.cb.lineTo(pt[0], pt[1]);
/* 368 */             for (int k = 0; k < ar.size(); k++) {
/* 369 */               pt = (float[])ar.get(k);
/* 370 */               this.cb.curveTo(pt[2], pt[3], pt[4], pt[5], pt[6], pt[7]);
/*     */             }
/* 372 */             this.cb.lineTo(cx, cy);
/* 373 */             strokeAndFill(); } 
/* 374 */         }break;
/*     */       case 2096:
/* 378 */         if (!isNullStrokeFill(this.state.getLineNeutral()))
/*     */         {
/* 380 */           float yend = this.state.transformY(this.in.readShort());
/* 381 */           float xend = this.state.transformX(this.in.readShort());
/* 382 */           float ystart = this.state.transformY(this.in.readShort());
/* 383 */           float xstart = this.state.transformX(this.in.readShort());
/* 384 */           float b = this.state.transformY(this.in.readShort());
/* 385 */           float r = this.state.transformX(this.in.readShort());
/* 386 */           float t = this.state.transformY(this.in.readShort());
/* 387 */           float l = this.state.transformX(this.in.readShort());
/* 388 */           float cx = (r + l) / 2.0F;
/* 389 */           float cy = (t + b) / 2.0F;
/* 390 */           float arc1 = getArc(cx, cy, xstart, ystart);
/* 391 */           float arc2 = getArc(cx, cy, xend, yend);
/* 392 */           arc2 -= arc1;
/* 393 */           if (arc2 <= 0.0F)
/* 394 */             arc2 += 360.0F;
/* 395 */           ArrayList ar = PdfContentByte.bezierArc(l, b, r, t, arc1, arc2);
/* 396 */           if (!ar.isEmpty())
/*     */           {
/* 398 */             float[] pt = (float[])ar.get(0);
/* 399 */             cx = pt[0];
/* 400 */             cy = pt[1];
/* 401 */             this.cb.moveTo(cx, cy);
/* 402 */             for (int k = 0; k < ar.size(); k++) {
/* 403 */               pt = (float[])ar.get(k);
/* 404 */               this.cb.curveTo(pt[2], pt[3], pt[4], pt[5], pt[6], pt[7]);
/*     */             }
/* 406 */             this.cb.lineTo(cx, cy);
/* 407 */             strokeAndFill(); } 
/* 408 */         }break;
/*     */       case 1051:
/* 412 */         if (!isNullStrokeFill(true))
/*     */         {
/* 414 */           float b = this.state.transformY(this.in.readShort());
/* 415 */           float r = this.state.transformX(this.in.readShort());
/* 416 */           float t = this.state.transformY(this.in.readShort());
/* 417 */           float l = this.state.transformX(this.in.readShort());
/* 418 */           this.cb.rectangle(l, b, r - l, t - b);
/* 419 */           strokeAndFill();
/* 420 */         }break;
/*     */       case 1564:
/* 424 */         if (!isNullStrokeFill(true))
/*     */         {
/* 426 */           float h = this.state.transformY(0) - this.state.transformY(this.in.readShort());
/* 427 */           float w = this.state.transformX(this.in.readShort()) - this.state.transformX(0);
/* 428 */           float b = this.state.transformY(this.in.readShort());
/* 429 */           float r = this.state.transformX(this.in.readShort());
/* 430 */           float t = this.state.transformY(this.in.readShort());
/* 431 */           float l = this.state.transformX(this.in.readShort());
/* 432 */           this.cb.roundRectangle(l, b, r - l, t - b, (h + w) / 4.0F);
/* 433 */           strokeAndFill();
/* 434 */         }break;
/*     */       case 1046:
/* 438 */         float b = this.state.transformY(this.in.readShort());
/* 439 */         float r = this.state.transformX(this.in.readShort());
/* 440 */         float t = this.state.transformY(this.in.readShort());
/* 441 */         float l = this.state.transformX(this.in.readShort());
/* 442 */         this.cb.rectangle(l, b, r - l, t - b);
/* 443 */         this.cb.eoClip();
/* 444 */         this.cb.newPath();
/* 445 */         break;
/*     */       case 2610:
/* 449 */         int y = this.in.readShort();
/* 450 */         int x = this.in.readShort();
/* 451 */         int count = this.in.readWord();
/* 452 */         int flag = this.in.readWord();
/* 453 */         int x1 = 0;
/* 454 */         int y1 = 0;
/* 455 */         int x2 = 0;
/* 456 */         int y2 = 0;
/* 457 */         if ((flag & 0x6) != 0) {
/* 458 */           x1 = this.in.readShort();
/* 459 */           y1 = this.in.readShort();
/* 460 */           x2 = this.in.readShort();
/* 461 */           y2 = this.in.readShort();
/*     */         }
/* 463 */         byte[] text = new byte[count];
/*     */ 
/* 465 */         for (int k = 0; k < count; k++) {
/* 466 */           byte c = (byte)this.in.readByte();
/* 467 */           if (c == 0)
/*     */             break;
/* 469 */           text[k] = c;
/*     */         }
/*     */         String s;
/*     */         try {
/* 473 */           s = new String(text, 0, k, "Cp1252");
/*     */         }
/*     */         catch (UnsupportedEncodingException e) {
/* 476 */           s = new String(text, 0, k);
/*     */         }
/* 478 */         outputText(x, y, flag, x1, y1, x2, y2, s);
/* 479 */         break;
/*     */       case 1313:
/* 483 */         int count = this.in.readWord();
/* 484 */         byte[] text = new byte[count];
/*     */ 
/* 486 */         for (int k = 0; k < count; k++) {
/* 487 */           byte c = (byte)this.in.readByte();
/* 488 */           if (c == 0)
/*     */             break;
/* 490 */           text[k] = c;
/*     */         }
/*     */         String s;
/*     */         try {
/* 494 */           s = new String(text, 0, k, "Cp1252");
/*     */         }
/*     */         catch (UnsupportedEncodingException e) {
/* 497 */           s = new String(text, 0, k);
/*     */         }
/* 499 */         count = count + 1 & 0xFFFE;
/* 500 */         this.in.skip(count - k);
/* 501 */         int y = this.in.readShort();
/* 502 */         int x = this.in.readShort();
/* 503 */         outputText(x, y, 0, 0, 0, 0, 0, s);
/* 504 */         break;
/*     */       case 513:
/* 507 */         this.state.setCurrentBackgroundColor(this.in.readColor());
/* 508 */         break;
/*     */       case 521:
/* 510 */         this.state.setCurrentTextColor(this.in.readColor());
/* 511 */         break;
/*     */       case 302:
/* 513 */         this.state.setTextAlign(this.in.readWord());
/* 514 */         break;
/*     */       case 258:
/* 516 */         this.state.setBackgroundMode(this.in.readWord());
/* 517 */         break;
/*     */       case 262:
/* 519 */         this.state.setPolyFillMode(this.in.readWord());
/* 520 */         break;
/*     */       case 1055:
/* 523 */         BaseColor color = this.in.readColor();
/* 524 */         int y = this.in.readShort();
/* 525 */         int x = this.in.readShort();
/* 526 */         this.cb.saveState();
/* 527 */         this.cb.setColorFill(color);
/* 528 */         this.cb.rectangle(this.state.transformX(x), this.state.transformY(y), 0.2F, 0.2F);
/* 529 */         this.cb.fill();
/* 530 */         this.cb.restoreState();
/* 531 */         break;
/*     */       case 2881:
/*     */       case 3907:
/* 535 */         int rop = this.in.readInt();
/* 536 */         if (function == 3907) {
/* 537 */           this.in.readWord();
/*     */         }
/* 539 */         int srcHeight = this.in.readShort();
/* 540 */         int srcWidth = this.in.readShort();
/* 541 */         int ySrc = this.in.readShort();
/* 542 */         int xSrc = this.in.readShort();
/* 543 */         float destHeight = this.state.transformY(this.in.readShort()) - this.state.transformY(0);
/* 544 */         float destWidth = this.state.transformX(this.in.readShort()) - this.state.transformX(0);
/* 545 */         float yDest = this.state.transformY(this.in.readShort());
/* 546 */         float xDest = this.state.transformX(this.in.readShort());
/* 547 */         byte[] b = new byte[tsize * 2 - (this.in.getLength() - lenMarker)];
/* 548 */         for (int k = 0; k < b.length; k++)
/* 549 */           b[k] = ((byte)this.in.readByte());
/*     */         try {
/* 551 */           ByteArrayInputStream inb = new ByteArrayInputStream(b);
/* 552 */           Image bmp = BmpImage.getImage(inb, true, b.length);
/* 553 */           this.cb.saveState();
/* 554 */           this.cb.rectangle(xDest, yDest, destWidth, destHeight);
/* 555 */           this.cb.clip();
/* 556 */           this.cb.newPath();
/* 557 */           bmp.scaleAbsolute(destWidth * bmp.getWidth() / srcWidth, -destHeight * bmp.getHeight() / srcHeight);
/* 558 */           bmp.setAbsolutePosition(xDest - destWidth * xSrc / srcWidth, yDest + destHeight * ySrc / srcHeight - bmp.getScaledHeight());
/* 559 */           this.cb.addImage(bmp);
/* 560 */           this.cb.restoreState();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 568 */       this.in.skip(tsize * 2 - (this.in.getLength() - lenMarker));
/*     */     }
/* 570 */     this.state.cleanup(this.cb);
/*     */   }
/*     */ 
/*     */   public void outputText(int x, int y, int flag, int x1, int y1, int x2, int y2, String text) {
/* 574 */     MetaFont font = this.state.getCurrentFont();
/* 575 */     float refX = this.state.transformX(x);
/* 576 */     float refY = this.state.transformY(y);
/* 577 */     float angle = this.state.transformAngle(font.getAngle());
/* 578 */     float sin = (float)Math.sin(angle);
/* 579 */     float cos = (float)Math.cos(angle);
/* 580 */     float fontSize = font.getFontSize(this.state);
/* 581 */     BaseFont bf = font.getFont();
/* 582 */     int align = this.state.getTextAlign();
/* 583 */     float textWidth = bf.getWidthPoint(text, fontSize);
/* 584 */     float tx = 0.0F;
/* 585 */     float ty = 0.0F;
/* 586 */     float descender = bf.getFontDescriptor(3, fontSize);
/* 587 */     float ury = bf.getFontDescriptor(8, fontSize);
/* 588 */     this.cb.saveState();
/* 589 */     this.cb.concatCTM(cos, sin, -sin, cos, refX, refY);
/* 590 */     if ((align & 0x6) == 6)
/* 591 */       tx = -textWidth / 2.0F;
/* 592 */     else if ((align & 0x2) == 2)
/* 593 */       tx = -textWidth;
/* 594 */     if ((align & 0x18) == 24)
/* 595 */       ty = 0.0F;
/* 596 */     else if ((align & 0x8) == 8)
/* 597 */       ty = -descender;
/*     */     else {
/* 599 */       ty = -ury;
/*     */     }
/* 601 */     if (this.state.getBackgroundMode() == 2) {
/* 602 */       BaseColor textColor = this.state.getCurrentBackgroundColor();
/* 603 */       this.cb.setColorFill(textColor);
/* 604 */       this.cb.rectangle(tx, ty + descender, textWidth, ury - descender);
/* 605 */       this.cb.fill();
/*     */     }
/* 607 */     BaseColor textColor = this.state.getCurrentTextColor();
/* 608 */     this.cb.setColorFill(textColor);
/* 609 */     this.cb.beginText();
/* 610 */     this.cb.setFontAndSize(bf, fontSize);
/* 611 */     this.cb.setTextMatrix(tx, ty);
/* 612 */     this.cb.showText(text);
/* 613 */     this.cb.endText();
/* 614 */     if (font.isUnderline()) {
/* 615 */       this.cb.rectangle(tx, ty - fontSize / 4.0F, textWidth, fontSize / 15.0F);
/* 616 */       this.cb.fill();
/*     */     }
/* 618 */     if (font.isStrikeout()) {
/* 619 */       this.cb.rectangle(tx, ty + fontSize / 3.0F, textWidth, fontSize / 15.0F);
/* 620 */       this.cb.fill();
/*     */     }
/* 622 */     this.cb.restoreState();
/*     */   }
/*     */ 
/*     */   public boolean isNullStrokeFill(boolean isRectangle) {
/* 626 */     MetaPen pen = this.state.getCurrentPen();
/* 627 */     MetaBrush brush = this.state.getCurrentBrush();
/* 628 */     boolean noPen = pen.getStyle() == 5;
/* 629 */     int style = brush.getStyle();
/* 630 */     boolean isBrush = (style == 0) || ((style == 2) && (this.state.getBackgroundMode() == 2));
/* 631 */     boolean result = (noPen) && (!isBrush);
/* 632 */     if (!noPen) {
/* 633 */       if (isRectangle)
/* 634 */         this.state.setLineJoinRectangle(this.cb);
/*     */       else
/* 636 */         this.state.setLineJoinPolygon(this.cb);
/*     */     }
/* 638 */     return result;
/*     */   }
/*     */ 
/*     */   public void strokeAndFill() {
/* 642 */     MetaPen pen = this.state.getCurrentPen();
/* 643 */     MetaBrush brush = this.state.getCurrentBrush();
/* 644 */     int penStyle = pen.getStyle();
/* 645 */     int brushStyle = brush.getStyle();
/* 646 */     if (penStyle == 5) {
/* 647 */       this.cb.closePath();
/* 648 */       if (this.state.getPolyFillMode() == 1) {
/* 649 */         this.cb.eoFill();
/*     */       }
/*     */       else
/* 652 */         this.cb.fill();
/*     */     }
/*     */     else
/*     */     {
/* 656 */       boolean isBrush = (brushStyle == 0) || ((brushStyle == 2) && (this.state.getBackgroundMode() == 2));
/* 657 */       if (isBrush) {
/* 658 */         if (this.state.getPolyFillMode() == 1)
/* 659 */           this.cb.closePathEoFillStroke();
/*     */         else
/* 661 */           this.cb.closePathFillStroke();
/*     */       }
/*     */       else
/* 664 */         this.cb.closePathStroke();
/*     */     }
/*     */   }
/*     */ 
/*     */   static float getArc(float xCenter, float yCenter, float xDot, float yDot)
/*     */   {
/* 670 */     double s = Math.atan2(yDot - yCenter, xDot - xCenter);
/* 671 */     if (s < 0.0D)
/* 672 */       s += 6.283185307179586D;
/* 673 */     return (float)(s / 3.141592653589793D * 180.0D);
/*     */   }
/*     */ 
/*     */   public static byte[] wrapBMP(Image image) throws IOException {
/* 677 */     if (image.getOriginalType() != 4) {
/* 678 */       throw new IOException(MessageLocalization.getComposedMessage("only.bmp.can.be.wrapped.in.wmf", new Object[0]));
/*     */     }
/* 680 */     byte[] data = null;
/* 681 */     if (image.getOriginalData() == null) {
/* 682 */       InputStream imgIn = image.getUrl().openStream();
/* 683 */       ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 684 */       int b = 0;
/* 685 */       while ((b = imgIn.read()) != -1)
/* 686 */         out.write(b);
/* 687 */       imgIn.close();
/* 688 */       data = out.toByteArray();
/*     */     }
/*     */     else {
/* 691 */       data = image.getOriginalData();
/* 692 */     }int sizeBmpWords = data.length - 14 + 1 >>> 1;
/* 693 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/*     */ 
/* 695 */     writeWord(os, 1);
/* 696 */     writeWord(os, 9);
/* 697 */     writeWord(os, 768);
/* 698 */     writeDWord(os, 36 + sizeBmpWords + 3);
/* 699 */     writeWord(os, 1);
/* 700 */     writeDWord(os, 14 + sizeBmpWords);
/* 701 */     writeWord(os, 0);
/*     */ 
/* 703 */     writeDWord(os, 4);
/* 704 */     writeWord(os, 259);
/* 705 */     writeWord(os, 8);
/*     */ 
/* 707 */     writeDWord(os, 5);
/* 708 */     writeWord(os, 523);
/* 709 */     writeWord(os, 0);
/* 710 */     writeWord(os, 0);
/*     */ 
/* 712 */     writeDWord(os, 5);
/* 713 */     writeWord(os, 524);
/* 714 */     writeWord(os, (int)image.getHeight());
/* 715 */     writeWord(os, (int)image.getWidth());
/*     */ 
/* 717 */     writeDWord(os, 13 + sizeBmpWords);
/* 718 */     writeWord(os, 2881);
/* 719 */     writeDWord(os, 13369376);
/* 720 */     writeWord(os, (int)image.getHeight());
/* 721 */     writeWord(os, (int)image.getWidth());
/* 722 */     writeWord(os, 0);
/* 723 */     writeWord(os, 0);
/* 724 */     writeWord(os, (int)image.getHeight());
/* 725 */     writeWord(os, (int)image.getWidth());
/* 726 */     writeWord(os, 0);
/* 727 */     writeWord(os, 0);
/* 728 */     os.write(data, 14, data.length - 14);
/* 729 */     if ((data.length & 0x1) == 1) {
/* 730 */       os.write(0);
/*     */     }
/*     */ 
/* 747 */     writeDWord(os, 3);
/* 748 */     writeWord(os, 0);
/* 749 */     os.close();
/* 750 */     return os.toByteArray();
/*     */   }
/*     */ 
/*     */   public static void writeWord(OutputStream os, int v) throws IOException {
/* 754 */     os.write(v & 0xFF);
/* 755 */     os.write(v >>> 8 & 0xFF);
/*     */   }
/*     */ 
/*     */   public static void writeDWord(OutputStream os, int v) throws IOException {
/* 759 */     writeWord(os, v & 0xFFFF);
/* 760 */     writeWord(os, v >>> 16 & 0xFFFF);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.wmf.MetaDo
 * JD-Core Version:    0.6.2
 */