/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.Chunk;
/*     */ import com.itextpdf.text.Document;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Font;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.ListBody;
/*     */ import com.itextpdf.text.ListItem;
/*     */ import com.itextpdf.text.ListLabel;
/*     */ import com.itextpdf.text.Paragraph;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*     */ import com.itextpdf.text.pdf.interfaces.IPdfStructureElement;
/*     */ import com.itextpdf.text.pdf.internal.PdfVersionImp;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class PdfStructureElement extends PdfDictionary
/*     */   implements IPdfStructureElement
/*     */ {
/*     */   private PdfStructureElement parent;
/*     */   private PdfStructureTreeRoot top;
/*     */   private PdfIndirectReference reference;
/*     */   private PdfName structureType;
/*     */ 
/*     */   public PdfStructureElement(PdfStructureElement parent, PdfName structureType)
/*     */   {
/*  84 */     this.top = parent.top;
/*  85 */     init(parent, structureType);
/*  86 */     this.parent = parent;
/*  87 */     put(PdfName.P, parent.reference);
/*  88 */     put(PdfName.TYPE, PdfName.STRUCTELEM);
/*     */   }
/*     */ 
/*     */   public PdfStructureElement(PdfStructureTreeRoot parent, PdfName structureType)
/*     */   {
/*  97 */     this.top = parent;
/*  98 */     init(parent, structureType);
/*  99 */     put(PdfName.P, parent.getReference());
/* 100 */     put(PdfName.TYPE, PdfName.STRUCTELEM);
/*     */   }
/*     */ 
/*     */   protected PdfStructureElement(PdfDictionary parent, PdfName structureType) {
/* 104 */     if ((parent instanceof PdfStructureElement)) {
/* 105 */       this.top = ((PdfStructureElement)parent).top;
/* 106 */       init(parent, structureType);
/* 107 */       this.parent = ((PdfStructureElement)parent);
/* 108 */       put(PdfName.P, ((PdfStructureElement)parent).reference);
/* 109 */       put(PdfName.TYPE, PdfName.STRUCTELEM);
/* 110 */     } else if ((parent instanceof PdfStructureTreeRoot)) {
/* 111 */       this.top = ((PdfStructureTreeRoot)parent);
/* 112 */       init(parent, structureType);
/* 113 */       put(PdfName.P, ((PdfStructureTreeRoot)parent).getReference());
/* 114 */       put(PdfName.TYPE, PdfName.STRUCTELEM);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PdfName getStructureType()
/*     */   {
/* 121 */     return this.structureType;
/*     */   }
/*     */ 
/*     */   private void init(PdfDictionary parent, PdfName structureType) {
/* 125 */     if (!this.top.getWriter().getStandardStructElems().contains(structureType)) {
/* 126 */       PdfDictionary roleMap = this.top.getAsDict(PdfName.ROLEMAP);
/* 127 */       if ((roleMap == null) || (!roleMap.contains(structureType))) {
/* 128 */         throw new ExceptionConverter(new DocumentException(MessageLocalization.getComposedMessage("unknown.structure.element.role.1", new Object[] { structureType.toString() })));
/*     */       }
/* 130 */       this.structureType = roleMap.getAsName(structureType);
/*     */     } else {
/* 132 */       this.structureType = structureType;
/*     */     }
/* 134 */     PdfObject kido = parent.get(PdfName.K);
/* 135 */     PdfArray kids = null;
/* 136 */     if (kido == null) {
/* 137 */       kids = new PdfArray();
/* 138 */       parent.put(PdfName.K, kids);
/* 139 */     } else if ((kido instanceof PdfArray)) {
/* 140 */       kids = (PdfArray)kido;
/*     */     } else {
/* 142 */       kids = new PdfArray();
/* 143 */       kids.add(kido);
/* 144 */       parent.put(PdfName.K, kids);
/*     */     }
/* 146 */     if (kids.size() > 0) {
/* 147 */       if (kids.getAsNumber(0) != null)
/* 148 */         kids.remove(0);
/* 149 */       if (kids.size() > 0) {
/* 150 */         PdfDictionary mcr = kids.getAsDict(0);
/* 151 */         if ((mcr != null) && (PdfName.MCR.equals(mcr.getAsName(PdfName.TYPE)))) {
/* 152 */           kids.remove(0);
/*     */         }
/*     */       }
/*     */     }
/* 156 */     kids.add(this);
/* 157 */     put(PdfName.S, structureType);
/* 158 */     this.reference = this.top.getWriter().getPdfIndirectReference();
/*     */   }
/*     */ 
/*     */   public PdfDictionary getParent()
/*     */   {
/* 166 */     return getParent(false);
/*     */   }
/*     */ 
/*     */   public PdfDictionary getParent(boolean includeStructTreeRoot) {
/* 170 */     if ((this.parent == null) && (includeStructTreeRoot)) {
/* 171 */       return this.top;
/*     */     }
/* 173 */     return this.parent;
/*     */   }
/*     */ 
/*     */   void setPageMark(int page, int mark) {
/* 177 */     if (mark >= 0)
/* 178 */       put(PdfName.K, new PdfNumber(mark));
/* 179 */     this.top.setPageMark(page, this.reference);
/*     */   }
/*     */ 
/*     */   void setAnnotation(PdfAnnotation annot, PdfIndirectReference currentPage) {
/* 183 */     PdfArray kArray = getAsArray(PdfName.K);
/* 184 */     if (kArray == null) {
/* 185 */       kArray = new PdfArray();
/* 186 */       PdfObject k = get(PdfName.K);
/* 187 */       if (k != null) {
/* 188 */         kArray.add(k);
/*     */       }
/* 190 */       put(PdfName.K, kArray);
/*     */     }
/* 192 */     PdfDictionary dict = new PdfDictionary();
/* 193 */     dict.put(PdfName.TYPE, PdfName.OBJR);
/* 194 */     dict.put(PdfName.OBJ, annot.getIndirectReference());
/* 195 */     if (annot.getRole() == PdfName.FORM)
/* 196 */       dict.put(PdfName.PG, currentPage);
/* 197 */     kArray.add(dict);
/*     */   }
/*     */ 
/*     */   public PdfIndirectReference getReference()
/*     */   {
/* 206 */     return this.reference;
/*     */   }
/*     */ 
/*     */   public PdfObject getAttribute(PdfName name)
/*     */   {
/* 215 */     PdfDictionary attr = getAsDict(PdfName.A);
/* 216 */     if ((attr != null) && 
/* 217 */       (attr.contains(name))) {
/* 218 */       return attr.get(name);
/*     */     }
/* 220 */     PdfDictionary parent = getParent();
/* 221 */     if ((parent instanceof PdfStructureElement))
/* 222 */       return ((PdfStructureElement)parent).getAttribute(name);
/* 223 */     if ((parent instanceof PdfStructureTreeRoot)) {
/* 224 */       return ((PdfStructureTreeRoot)parent).getAttribute(name);
/*     */     }
/* 226 */     return new PdfNull();
/*     */   }
/*     */ 
/*     */   public void setAttribute(PdfName name, PdfObject obj)
/*     */   {
/* 234 */     PdfDictionary attr = getAsDict(PdfName.A);
/* 235 */     if (attr == null) {
/* 236 */       attr = new PdfDictionary();
/* 237 */       put(PdfName.A, attr);
/*     */     }
/* 239 */     attr.put(name, obj);
/*     */   }
/*     */ 
/*     */   public void writeAttributes(IAccessibleElement element) {
/* 243 */     if (this.top.getWriter().getPdfVersion().getVersion() < '7')
/* 244 */       return;
/* 245 */     if ((element instanceof ListItem))
/* 246 */       writeAttributes((ListItem)element);
/* 247 */     else if ((element instanceof Paragraph))
/* 248 */       writeAttributes((Paragraph)element);
/* 249 */     else if ((element instanceof Chunk))
/* 250 */       writeAttributes((Chunk)element);
/* 251 */     else if ((element instanceof Image))
/* 252 */       writeAttributes((Image)element);
/* 253 */     else if ((element instanceof com.itextpdf.text.List))
/* 254 */       writeAttributes((com.itextpdf.text.List)element);
/* 255 */     else if ((element instanceof ListLabel))
/* 256 */       writeAttributes((ListLabel)element);
/* 257 */     else if ((element instanceof ListBody))
/* 258 */       writeAttributes((ListBody)element);
/* 259 */     else if ((element instanceof PdfPTable))
/* 260 */       writeAttributes((PdfPTable)element);
/* 261 */     else if ((element instanceof PdfPRow))
/* 262 */       writeAttributes((PdfPRow)element);
/* 263 */     else if ((element instanceof PdfPHeaderCell))
/* 264 */       writeAttributes((PdfPHeaderCell)element);
/* 265 */     else if ((element instanceof PdfPCell))
/* 266 */       writeAttributes((PdfPCell)element);
/* 267 */     else if ((element instanceof PdfPTableHeader))
/* 268 */       writeAttributes((PdfPTableHeader)element);
/* 269 */     else if ((element instanceof PdfPTableFooter))
/* 270 */       writeAttributes((PdfPTableFooter)element);
/* 271 */     else if ((element instanceof PdfPTableBody))
/* 272 */       writeAttributes((PdfPTableBody)element);
/* 273 */     else if ((element instanceof PdfDiv))
/* 274 */       writeAttributes((PdfDiv)element);
/* 275 */     else if ((element instanceof PdfTemplate))
/* 276 */       writeAttributes((PdfTemplate)element);
/* 277 */     else if ((element instanceof Document)) {
/* 278 */       writeAttributes((Document)element);
/*     */     }
/* 280 */     if (element.getAccessibleAttributes() != null)
/* 281 */       for (PdfName key : element.getAccessibleAttributes().keySet())
/* 282 */         if ((key.equals(PdfName.LANG)) || (key.equals(PdfName.ALT)) || (key.equals(PdfName.ACTUALTEXT)) || (key.equals(PdfName.E)))
/* 283 */           put(key, element.getAccessibleAttribute(key));
/*     */         else
/* 285 */           setAttribute(key, element.getAccessibleAttribute(key));
/*     */   }
/*     */ 
/*     */   private void writeAttributes(Chunk chunk)
/*     */   {
/* 292 */     if (chunk != null)
/* 293 */       if (chunk.getImage() != null) {
/* 294 */         writeAttributes(chunk.getImage());
/*     */       } else {
/* 296 */         HashMap attr = chunk.getAttributes();
/* 297 */         if (attr != null) {
/* 298 */           setAttribute(PdfName.O, PdfName.LAYOUT);
/*     */ 
/* 300 */           if (attr.containsKey("UNDERLINE")) {
/* 301 */             setAttribute(PdfName.TEXTDECORATIONTYPE, PdfName.UNDERLINE);
/*     */           }
/* 303 */           if (attr.containsKey("BACKGROUND")) {
/* 304 */             Object[] back = (Object[])attr.get("BACKGROUND");
/* 305 */             BaseColor color = (BaseColor)back[0];
/* 306 */             setAttribute(PdfName.BACKGROUNDCOLOR, new PdfArray(new float[] { color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F }));
/*     */           }
/*     */ 
/* 310 */           IPdfStructureElement parent = (IPdfStructureElement)getParent(true);
/* 311 */           PdfObject obj = parent.getAttribute(PdfName.COLOR);
/* 312 */           if ((chunk.getFont() != null) && (chunk.getFont().getColor() != null)) {
/* 313 */             BaseColor c = chunk.getFont().getColor();
/* 314 */             setColorAttribute(c, obj, PdfName.COLOR);
/*     */           }
/* 316 */           PdfObject decorThickness = parent.getAttribute(PdfName.TEXTDECORATIONTHICKNESS);
/* 317 */           PdfObject decorColor = parent.getAttribute(PdfName.TEXTDECORATIONCOLOR);
/* 318 */           if (attr.containsKey("UNDERLINE")) {
/* 319 */             Object[][] unders = (Object[][])attr.get("UNDERLINE");
/* 320 */             Object[] arr = unders[(unders.length - 1)];
/* 321 */             BaseColor color = (BaseColor)arr[0];
/* 322 */             float[] floats = (float[])arr[1];
/* 323 */             float thickness = floats[0];
/*     */ 
/* 325 */             if ((decorThickness instanceof PdfNumber)) {
/* 326 */               float t = ((PdfNumber)decorThickness).floatValue();
/* 327 */               if (Float.compare(thickness, t) != 0)
/* 328 */                 setAttribute(PdfName.TEXTDECORATIONTHICKNESS, new PdfNumber(thickness));
/*     */             }
/*     */             else
/*     */             {
/* 332 */               setAttribute(PdfName.TEXTDECORATIONTHICKNESS, new PdfNumber(thickness));
/*     */             }
/*     */ 
/* 335 */             if (color != null) {
/* 336 */               setColorAttribute(color, decorColor, PdfName.TEXTDECORATIONCOLOR);
/*     */             }
/*     */           }
/*     */ 
/* 340 */           if (attr.containsKey("LINEHEIGHT")) {
/* 341 */             float height = ((Float)attr.get("LINEHEIGHT")).floatValue();
/* 342 */             PdfObject parentLH = parent.getAttribute(PdfName.LINEHEIGHT);
/* 343 */             if ((parentLH instanceof PdfNumber)) {
/* 344 */               float pLH = ((PdfNumber)parentLH).floatValue();
/* 345 */               if (Float.compare(pLH, height) != 0)
/* 346 */                 setAttribute(PdfName.LINEHEIGHT, new PdfNumber(height));
/*     */             }
/*     */             else
/*     */             {
/* 350 */               setAttribute(PdfName.LINEHEIGHT, new PdfNumber(height));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   private void writeAttributes(Image image) {
/* 358 */     if (image != null) {
/* 359 */       setAttribute(PdfName.O, PdfName.LAYOUT);
/* 360 */       if (image.getWidth() > 0.0F) {
/* 361 */         setAttribute(PdfName.WIDTH, new PdfNumber(image.getWidth()));
/*     */       }
/* 363 */       if (image.getHeight() > 0.0F) {
/* 364 */         setAttribute(PdfName.HEIGHT, new PdfNumber(image.getHeight()));
/*     */       }
/* 366 */       PdfRectangle rect = new PdfRectangle(image, image.getRotation());
/* 367 */       setAttribute(PdfName.BBOX, rect);
/* 368 */       if (image.getBackgroundColor() != null) {
/* 369 */         BaseColor color = image.getBackgroundColor();
/* 370 */         setAttribute(PdfName.BACKGROUNDCOLOR, new PdfArray(new float[] { color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F }));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeAttributes(PdfTemplate template) {
/* 376 */     if (template != null) {
/* 377 */       setAttribute(PdfName.O, PdfName.LAYOUT);
/* 378 */       if (template.getWidth() > 0.0F) {
/* 379 */         setAttribute(PdfName.WIDTH, new PdfNumber(template.getWidth()));
/*     */       }
/* 381 */       if (template.getHeight() > 0.0F) {
/* 382 */         setAttribute(PdfName.HEIGHT, new PdfNumber(template.getHeight()));
/*     */       }
/* 384 */       PdfRectangle rect = new PdfRectangle(template.getBoundingBox());
/* 385 */       setAttribute(PdfName.BBOX, rect);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeAttributes(Paragraph paragraph) {
/* 390 */     if (paragraph != null) {
/* 391 */       setAttribute(PdfName.O, PdfName.LAYOUT);
/*     */ 
/* 393 */       if (Float.compare(paragraph.getSpacingBefore(), 0.0F) != 0)
/* 394 */         setAttribute(PdfName.SPACEBEFORE, new PdfNumber(paragraph.getSpacingBefore()));
/* 395 */       if (Float.compare(paragraph.getSpacingAfter(), 0.0F) != 0) {
/* 396 */         setAttribute(PdfName.SPACEAFTER, new PdfNumber(paragraph.getSpacingAfter()));
/*     */       }
/*     */ 
/* 399 */       IPdfStructureElement parent = (IPdfStructureElement)getParent(true);
/* 400 */       PdfObject obj = parent.getAttribute(PdfName.COLOR);
/* 401 */       if ((paragraph.getFont() != null) && (paragraph.getFont().getColor() != null)) {
/* 402 */         BaseColor c = paragraph.getFont().getColor();
/* 403 */         setColorAttribute(c, obj, PdfName.COLOR);
/*     */       }
/* 405 */       obj = parent.getAttribute(PdfName.TEXTINDENT);
/* 406 */       if (Float.compare(paragraph.getFirstLineIndent(), 0.0F) != 0) {
/* 407 */         boolean writeIndent = true;
/* 408 */         if (((obj instanceof PdfNumber)) && 
/* 409 */           (Float.compare(((PdfNumber)obj).floatValue(), new Float(paragraph.getFirstLineIndent()).floatValue()) == 0)) {
/* 410 */           writeIndent = false;
/*     */         }
/* 412 */         if (writeIndent)
/* 413 */           setAttribute(PdfName.TEXTINDENT, new PdfNumber(paragraph.getFirstLineIndent()));
/*     */       }
/* 415 */       obj = parent.getAttribute(PdfName.STARTINDENT);
/* 416 */       if ((obj instanceof PdfNumber)) {
/* 417 */         float startIndent = ((PdfNumber)obj).floatValue();
/* 418 */         if (Float.compare(startIndent, paragraph.getIndentationLeft()) != 0)
/* 419 */           setAttribute(PdfName.STARTINDENT, new PdfNumber(paragraph.getIndentationLeft()));
/*     */       }
/* 421 */       else if (Math.abs(paragraph.getIndentationLeft()) > 1.4E-45F) {
/* 422 */         setAttribute(PdfName.STARTINDENT, new PdfNumber(paragraph.getIndentationLeft()));
/*     */       }
/*     */ 
/* 425 */       obj = parent.getAttribute(PdfName.ENDINDENT);
/* 426 */       if ((obj instanceof PdfNumber)) {
/* 427 */         float endIndent = ((PdfNumber)obj).floatValue();
/* 428 */         if (Float.compare(endIndent, paragraph.getIndentationRight()) != 0)
/* 429 */           setAttribute(PdfName.ENDINDENT, new PdfNumber(paragraph.getIndentationRight()));
/*     */       }
/* 431 */       else if (Float.compare(paragraph.getIndentationRight(), 0.0F) != 0) {
/* 432 */         setAttribute(PdfName.ENDINDENT, new PdfNumber(paragraph.getIndentationRight()));
/*     */       }
/*     */ 
/* 435 */       setTextAlignAttribute(paragraph.getAlignment());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeAttributes(com.itextpdf.text.List list) {
/* 440 */     if (list != null) {
/* 441 */       setAttribute(PdfName.O, PdfName.LIST);
/* 442 */       if (list.isAutoindent()) {
/* 443 */         if (list.isNumbered()) {
/* 444 */           if (list.isLettered()) {
/* 445 */             if (list.isLowercase())
/* 446 */               setAttribute(PdfName.LISTNUMBERING, PdfName.LOWERROMAN);
/*     */             else
/* 448 */               setAttribute(PdfName.LISTNUMBERING, PdfName.UPPERROMAN);
/*     */           }
/*     */           else {
/* 451 */             setAttribute(PdfName.LISTNUMBERING, PdfName.DECIMAL);
/*     */           }
/*     */ 
/*     */         }
/* 455 */         else if (list.isLettered()) {
/* 456 */           if (list.isLowercase())
/* 457 */             setAttribute(PdfName.LISTNUMBERING, PdfName.LOWERALPHA);
/*     */           else
/* 459 */             setAttribute(PdfName.LISTNUMBERING, PdfName.UPPERALPHA);
/*     */         }
/*     */       }
/* 462 */       PdfObject obj = this.parent.getAttribute(PdfName.STARTINDENT);
/* 463 */       if ((obj instanceof PdfNumber)) {
/* 464 */         float startIndent = ((PdfNumber)obj).floatValue();
/* 465 */         if (Float.compare(startIndent, list.getIndentationLeft()) != 0)
/* 466 */           setAttribute(PdfName.STARTINDENT, new PdfNumber(list.getIndentationLeft()));
/*     */       }
/* 468 */       else if (Math.abs(list.getIndentationLeft()) > 1.4E-45F) {
/* 469 */         setAttribute(PdfName.STARTINDENT, new PdfNumber(list.getIndentationLeft()));
/*     */       }
/*     */ 
/* 472 */       obj = this.parent.getAttribute(PdfName.ENDINDENT);
/* 473 */       if ((obj instanceof PdfNumber)) {
/* 474 */         float endIndent = ((PdfNumber)obj).floatValue();
/* 475 */         if (Float.compare(endIndent, list.getIndentationRight()) != 0)
/* 476 */           setAttribute(PdfName.ENDINDENT, new PdfNumber(list.getIndentationRight()));
/*     */       }
/* 478 */       else if (Float.compare(list.getIndentationRight(), 0.0F) != 0) {
/* 479 */         setAttribute(PdfName.ENDINDENT, new PdfNumber(list.getIndentationRight()));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeAttributes(ListItem listItem) {
/* 485 */     if (listItem != null) {
/* 486 */       PdfObject obj = this.parent.getAttribute(PdfName.STARTINDENT);
/* 487 */       if ((obj instanceof PdfNumber)) {
/* 488 */         float startIndent = ((PdfNumber)obj).floatValue();
/* 489 */         if (Float.compare(startIndent, listItem.getIndentationLeft()) != 0)
/* 490 */           setAttribute(PdfName.STARTINDENT, new PdfNumber(listItem.getIndentationLeft()));
/*     */       }
/* 492 */       else if (Math.abs(listItem.getIndentationLeft()) > 1.4E-45F) {
/* 493 */         setAttribute(PdfName.STARTINDENT, new PdfNumber(listItem.getIndentationLeft()));
/*     */       }
/*     */ 
/* 496 */       obj = this.parent.getAttribute(PdfName.ENDINDENT);
/* 497 */       if ((obj instanceof PdfNumber)) {
/* 498 */         float endIndent = ((PdfNumber)obj).floatValue();
/* 499 */         if (Float.compare(endIndent, listItem.getIndentationRight()) != 0)
/* 500 */           setAttribute(PdfName.ENDINDENT, new PdfNumber(listItem.getIndentationRight()));
/*     */       }
/* 502 */       else if (Float.compare(listItem.getIndentationRight(), 0.0F) != 0) {
/* 503 */         setAttribute(PdfName.ENDINDENT, new PdfNumber(listItem.getIndentationRight()));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeAttributes(ListBody listBody) {
/* 509 */     if (listBody != null);
/*     */   }
/*     */ 
/*     */   private void writeAttributes(ListLabel listLabel)
/*     */   {
/* 515 */     if (listLabel != null) {
/* 516 */       PdfObject obj = this.parent.getAttribute(PdfName.STARTINDENT);
/* 517 */       if ((obj instanceof PdfNumber)) {
/* 518 */         float startIndent = ((PdfNumber)obj).floatValue();
/* 519 */         if (Float.compare(startIndent, listLabel.getIndentation()) != 0)
/* 520 */           setAttribute(PdfName.STARTINDENT, new PdfNumber(listLabel.getIndentation()));
/*     */       }
/* 522 */       else if (Math.abs(listLabel.getIndentation()) > 1.4E-45F) {
/* 523 */         setAttribute(PdfName.STARTINDENT, new PdfNumber(listLabel.getIndentation()));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeAttributes(PdfPTable table) {
/* 529 */     if (table != null)
/*     */     {
/* 531 */       if (Float.compare(table.getSpacingBefore(), 0.0F) != 0) {
/* 532 */         setAttribute(PdfName.SPACEBEFORE, new PdfNumber(table.getSpacingBefore()));
/*     */       }
/* 534 */       if (Float.compare(table.getSpacingAfter(), 0.0F) != 0) {
/* 535 */         setAttribute(PdfName.SPACEAFTER, new PdfNumber(table.getSpacingAfter()));
/*     */       }
/* 537 */       if (table.getTotalHeight() > 0.0F) {
/* 538 */         setAttribute(PdfName.HEIGHT, new PdfNumber(table.getTotalHeight()));
/*     */       }
/* 540 */       if (table.getTotalWidth() > 0.0F)
/* 541 */         setAttribute(PdfName.WIDTH, new PdfNumber(table.getTotalWidth()));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeAttributes(PdfPRow row)
/*     */   {
/* 547 */     if (row != null)
/* 548 */       setAttribute(PdfName.O, PdfName.TABLE);
/*     */   }
/*     */ 
/*     */   private void writeAttributes(PdfPCell cell)
/*     */   {
/* 553 */     if (cell != null) {
/* 554 */       setAttribute(PdfName.O, PdfName.TABLE);
/* 555 */       if (cell.getColspan() != 1) {
/* 556 */         setAttribute(PdfName.COLSPAN, new PdfNumber(cell.getColspan()));
/*     */       }
/* 558 */       if (cell.getRowspan() != 1) {
/* 559 */         setAttribute(PdfName.ROWSPAN, new PdfNumber(cell.getRowspan()));
/*     */       }
/* 561 */       if (cell.getHeaders() != null) {
/* 562 */         PdfArray headers = new PdfArray();
/* 563 */         ArrayList list = cell.getHeaders();
/* 564 */         for (PdfPHeaderCell header : list) {
/* 565 */           if (header.getName() != null)
/* 566 */             headers.add(new PdfString(header.getName()));
/*     */         }
/* 568 */         if (!headers.isEmpty()) {
/* 569 */           setAttribute(PdfName.HEADERS, headers);
/*     */         }
/*     */       }
/* 572 */       if (cell.getFixedHeight() > 0.0F) {
/* 573 */         setAttribute(PdfName.HEIGHT, new PdfNumber(cell.getFixedHeight()));
/*     */       }
/*     */ 
/* 576 */       if (cell.getWidth() > 0.0F) {
/* 577 */         setAttribute(PdfName.WIDTH, new PdfNumber(cell.getWidth()));
/*     */       }
/*     */ 
/* 580 */       if (cell.getBackgroundColor() != null) {
/* 581 */         BaseColor color = cell.getBackgroundColor();
/* 582 */         setAttribute(PdfName.BACKGROUNDCOLOR, new PdfArray(new float[] { color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F }));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeAttributes(PdfPHeaderCell headerCell) {
/* 588 */     if (headerCell != null) {
/* 589 */       if (headerCell.getScope() != 0) {
/* 590 */         switch (headerCell.getScope()) { case 1:
/* 591 */           setAttribute(PdfName.SCOPE, PdfName.ROW); break;
/*     */         case 2:
/* 592 */           setAttribute(PdfName.SCOPE, PdfName.COLUMN); break;
/*     */         case 3:
/* 593 */           setAttribute(PdfName.SCOPE, PdfName.BOTH);
/*     */         }
/*     */       }
/* 596 */       if (headerCell.getName() != null)
/* 597 */         setAttribute(PdfName.NAME, new PdfName(headerCell.getName()));
/* 598 */       writeAttributes(headerCell);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeAttributes(PdfPTableHeader header) {
/* 603 */     if (header != null)
/* 604 */       setAttribute(PdfName.O, PdfName.TABLE);
/*     */   }
/*     */ 
/*     */   private void writeAttributes(PdfPTableBody body)
/*     */   {
/* 609 */     if (body != null);
/*     */   }
/*     */ 
/*     */   private void writeAttributes(PdfPTableFooter footer)
/*     */   {
/* 615 */     if (footer != null);
/*     */   }
/*     */ 
/*     */   private void writeAttributes(PdfDiv div)
/*     */   {
/* 621 */     if (div != null)
/*     */     {
/* 623 */       if (div.getBackgroundColor() != null) {
/* 624 */         setColorAttribute(div.getBackgroundColor(), null, PdfName.BACKGROUNDCOLOR);
/*     */       }
/*     */ 
/* 627 */       setTextAlignAttribute(div.getTextAlignment());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeAttributes(Document document) {
/* 632 */     if (document != null);
/*     */   }
/*     */ 
/*     */   private boolean colorsEqual(PdfArray parentColor, float[] color)
/*     */   {
/* 638 */     if (Float.compare(color[0], parentColor.getAsNumber(0).floatValue()) != 0) {
/* 639 */       return false;
/*     */     }
/* 641 */     if (Float.compare(color[1], parentColor.getAsNumber(1).floatValue()) != 0) {
/* 642 */       return false;
/*     */     }
/* 644 */     if (Float.compare(color[2], parentColor.getAsNumber(2).floatValue()) != 0) {
/* 645 */       return false;
/*     */     }
/* 647 */     return true;
/*     */   }
/*     */ 
/*     */   private void setColorAttribute(BaseColor newColor, PdfObject oldColor, PdfName attributeName) {
/* 651 */     float[] colorArr = { newColor.getRed() / 255.0F, newColor.getGreen() / 255.0F, newColor.getBlue() / 255.0F };
/* 652 */     if ((oldColor != null) && ((oldColor instanceof PdfArray))) {
/* 653 */       PdfArray oldC = (PdfArray)oldColor;
/* 654 */       if (colorsEqual(oldC, colorArr))
/*     */       {
/* 656 */         setAttribute(attributeName, new PdfArray(colorArr));
/*     */       }
/*     */       else
/* 659 */         setAttribute(attributeName, new PdfArray(colorArr));
/*     */     }
/*     */     else {
/* 662 */       setAttribute(attributeName, new PdfArray(colorArr));
/*     */     }
/*     */   }
/*     */ 
/* 666 */   private void setTextAlignAttribute(int elementAlign) { PdfName align = null;
/* 667 */     switch (elementAlign) {
/*     */     case 0:
/* 669 */       align = PdfName.START;
/* 670 */       break;
/*     */     case 1:
/* 672 */       align = PdfName.CENTER;
/* 673 */       break;
/*     */     case 2:
/* 675 */       align = PdfName.END;
/* 676 */       break;
/*     */     case 3:
/* 678 */       align = PdfName.JUSTIFY;
/*     */     }
/*     */ 
/* 681 */     PdfObject obj = this.parent.getAttribute(PdfName.TEXTALIGN);
/* 682 */     if ((obj instanceof PdfName)) {
/* 683 */       PdfName textAlign = (PdfName)obj;
/* 684 */       if ((align != null) && (!textAlign.equals(align)))
/* 685 */         setAttribute(PdfName.TEXTALIGN, align);
/*     */     }
/* 687 */     else if ((align != null) && (!PdfName.START.equals(align))) {
/* 688 */       setAttribute(PdfName.TEXTALIGN, align);
/*     */     } }
/*     */ 
/*     */   public void toPdf(PdfWriter writer, OutputStream os)
/*     */     throws IOException
/*     */   {
/* 694 */     PdfWriter.checkPdfIsoConformance(writer, 16, this);
/* 695 */     super.toPdf(writer, os);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfStructureElement
 * JD-Core Version:    0.6.2
 */