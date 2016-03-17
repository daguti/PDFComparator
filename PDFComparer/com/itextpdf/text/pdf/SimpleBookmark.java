/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.xml.XMLUtil;
/*     */ import com.itextpdf.text.xml.simpleparser.IanaEncodings;
/*     */ import com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler;
/*     */ import com.itextpdf.text.xml.simpleparser.SimpleXMLParser;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Stack;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public final class SimpleBookmark
/*     */   implements SimpleXMLDocHandler
/*     */ {
/*     */   private ArrayList<HashMap<String, Object>> topList;
/* 111 */   private final Stack<HashMap<String, Object>> attr = new Stack();
/*     */ 
/*     */   private static List<HashMap<String, Object>> bookmarkDepth(PdfReader reader, PdfDictionary outline, IntHashtable pages, boolean processCurrentOutlineOnly)
/*     */   {
/* 118 */     ArrayList list = new ArrayList();
/* 119 */     while (outline != null) {
/* 120 */       HashMap map = new HashMap();
/* 121 */       PdfString title = (PdfString)PdfReader.getPdfObjectRelease(outline.get(PdfName.TITLE));
/* 122 */       map.put("Title", title.toUnicodeString());
/* 123 */       PdfArray color = (PdfArray)PdfReader.getPdfObjectRelease(outline.get(PdfName.C));
/* 124 */       if ((color != null) && (color.size() == 3)) {
/* 125 */         ByteBuffer out = new ByteBuffer();
/* 126 */         out.append(color.getAsNumber(0).floatValue()).append(' ');
/* 127 */         out.append(color.getAsNumber(1).floatValue()).append(' ');
/* 128 */         out.append(color.getAsNumber(2).floatValue());
/* 129 */         map.put("Color", PdfEncodings.convertToString(out.toByteArray(), null));
/*     */       }
/* 131 */       PdfNumber style = (PdfNumber)PdfReader.getPdfObjectRelease(outline.get(PdfName.F));
/* 132 */       if (style != null) {
/* 133 */         int f = style.intValue();
/* 134 */         String s = "";
/* 135 */         if ((f & 0x1) != 0)
/* 136 */           s = s + "italic ";
/* 137 */         if ((f & 0x2) != 0)
/* 138 */           s = s + "bold ";
/* 139 */         s = s.trim();
/* 140 */         if (s.length() != 0)
/* 141 */           map.put("Style", s);
/*     */       }
/* 143 */       PdfNumber count = (PdfNumber)PdfReader.getPdfObjectRelease(outline.get(PdfName.COUNT));
/* 144 */       if ((count != null) && (count.intValue() < 0))
/* 145 */         map.put("Open", "false");
/*     */       try {
/* 147 */         PdfObject dest = PdfReader.getPdfObjectRelease(outline.get(PdfName.DEST));
/* 148 */         if (dest != null) {
/* 149 */           mapGotoBookmark(map, dest, pages);
/*     */         }
/*     */         else {
/* 152 */           PdfDictionary action = (PdfDictionary)PdfReader.getPdfObjectRelease(outline.get(PdfName.A));
/* 153 */           if (action != null) {
/* 154 */             if (PdfName.GOTO.equals(PdfReader.getPdfObjectRelease(action.get(PdfName.S)))) {
/* 155 */               dest = PdfReader.getPdfObjectRelease(action.get(PdfName.D));
/* 156 */               if (dest != null) {
/* 157 */                 mapGotoBookmark(map, dest, pages);
/*     */               }
/*     */             }
/* 160 */             else if (PdfName.URI.equals(PdfReader.getPdfObjectRelease(action.get(PdfName.S)))) {
/* 161 */               map.put("Action", "URI");
/* 162 */               map.put("URI", ((PdfString)PdfReader.getPdfObjectRelease(action.get(PdfName.URI))).toUnicodeString());
/*     */             }
/* 164 */             else if (PdfName.JAVASCRIPT.equals(PdfReader.getPdfObjectRelease(action.get(PdfName.S)))) {
/* 165 */               map.put("Action", "JS");
/* 166 */               map.put("Code", PdfReader.getPdfObjectRelease(action.get(PdfName.JS)).toString());
/*     */             }
/* 168 */             else if (PdfName.GOTOR.equals(PdfReader.getPdfObjectRelease(action.get(PdfName.S)))) {
/* 169 */               dest = PdfReader.getPdfObjectRelease(action.get(PdfName.D));
/* 170 */               if (dest != null) {
/* 171 */                 if (dest.isString()) {
/* 172 */                   map.put("Named", dest.toString());
/* 173 */                 } else if (dest.isName()) {
/* 174 */                   map.put("NamedN", PdfName.decodeName(dest.toString()));
/* 175 */                 } else if (dest.isArray()) {
/* 176 */                   PdfArray arr = (PdfArray)dest;
/* 177 */                   StringBuffer s = new StringBuffer();
/* 178 */                   s.append(arr.getPdfObject(0).toString());
/* 179 */                   s.append(' ').append(arr.getPdfObject(1).toString());
/* 180 */                   for (int k = 2; k < arr.size(); k++)
/* 181 */                     s.append(' ').append(arr.getPdfObject(k).toString());
/* 182 */                   map.put("Page", s.toString());
/*     */                 }
/*     */               }
/* 185 */               map.put("Action", "GoToR");
/* 186 */               PdfObject file = PdfReader.getPdfObjectRelease(action.get(PdfName.F));
/* 187 */               if (file != null) {
/* 188 */                 if (file.isString()) {
/* 189 */                   map.put("File", ((PdfString)file).toUnicodeString());
/* 190 */                 } else if (file.isDictionary()) {
/* 191 */                   file = PdfReader.getPdfObject(((PdfDictionary)file).get(PdfName.F));
/* 192 */                   if (file.isString())
/* 193 */                     map.put("File", ((PdfString)file).toUnicodeString());
/*     */                 }
/*     */               }
/* 196 */               PdfObject newWindow = PdfReader.getPdfObjectRelease(action.get(PdfName.NEWWINDOW));
/* 197 */               if (newWindow != null)
/* 198 */                 map.put("NewWindow", newWindow.toString());
/*     */             }
/* 200 */             else if (PdfName.LAUNCH.equals(PdfReader.getPdfObjectRelease(action.get(PdfName.S)))) {
/* 201 */               map.put("Action", "Launch");
/* 202 */               PdfObject file = PdfReader.getPdfObjectRelease(action.get(PdfName.F));
/* 203 */               if (file == null)
/* 204 */                 file = PdfReader.getPdfObjectRelease(action.get(PdfName.WIN));
/* 205 */               if (file != null) {
/* 206 */                 if (file.isString()) {
/* 207 */                   map.put("File", ((PdfString)file).toUnicodeString());
/* 208 */                 } else if (file.isDictionary()) {
/* 209 */                   file = PdfReader.getPdfObjectRelease(((PdfDictionary)file).get(PdfName.F));
/* 210 */                   if (file.isString())
/* 211 */                     map.put("File", ((PdfString)file).toUnicodeString());
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */       }
/* 221 */       PdfDictionary first = (PdfDictionary)PdfReader.getPdfObjectRelease(outline.get(PdfName.FIRST));
/* 222 */       if (first != null) {
/* 223 */         map.put("Kids", bookmarkDepth(reader, first, pages, false));
/*     */       }
/* 225 */       list.add(map);
/* 226 */       if (!processCurrentOutlineOnly)
/* 227 */         outline = (PdfDictionary)PdfReader.getPdfObjectRelease(outline.get(PdfName.NEXT));
/*     */       else
/* 229 */         outline = null;
/*     */     }
/* 231 */     return list;
/*     */   }
/*     */ 
/*     */   private static void mapGotoBookmark(HashMap<String, Object> map, PdfObject dest, IntHashtable pages)
/*     */   {
/* 236 */     if (dest.isString())
/* 237 */       map.put("Named", dest.toString());
/* 238 */     else if (dest.isName())
/* 239 */       map.put("Named", PdfName.decodeName(dest.toString()));
/* 240 */     else if (dest.isArray())
/* 241 */       map.put("Page", makeBookmarkParam((PdfArray)dest, pages));
/* 242 */     map.put("Action", "GoTo");
/*     */   }
/*     */ 
/*     */   private static String makeBookmarkParam(PdfArray dest, IntHashtable pages)
/*     */   {
/* 247 */     StringBuffer s = new StringBuffer();
/* 248 */     PdfObject obj = dest.getPdfObject(0);
/* 249 */     if (obj.isNumber())
/* 250 */       s.append(((PdfNumber)obj).intValue() + 1);
/*     */     else
/* 252 */       s.append(pages.get(getNumber((PdfIndirectReference)obj)));
/* 253 */     s.append(' ').append(dest.getPdfObject(1).toString().substring(1));
/* 254 */     for (int k = 2; k < dest.size(); k++)
/* 255 */       s.append(' ').append(dest.getPdfObject(k).toString());
/* 256 */     return s.toString();
/*     */   }
/*     */ 
/*     */   private static int getNumber(PdfIndirectReference indirect)
/*     */   {
/* 267 */     PdfDictionary pdfObj = (PdfDictionary)PdfReader.getPdfObjectRelease(indirect);
/* 268 */     if ((pdfObj.contains(PdfName.TYPE)) && (pdfObj.get(PdfName.TYPE).equals(PdfName.PAGES)) && (pdfObj.contains(PdfName.KIDS)))
/*     */     {
/* 270 */       PdfArray kids = (PdfArray)pdfObj.get(PdfName.KIDS);
/* 271 */       indirect = (PdfIndirectReference)kids.getPdfObject(0);
/*     */     }
/* 273 */     return indirect.getNumber();
/*     */   }
/*     */ 
/*     */   public static List<HashMap<String, Object>> getBookmark(PdfReader reader)
/*     */   {
/* 284 */     PdfDictionary catalog = reader.getCatalog();
/* 285 */     PdfObject obj = PdfReader.getPdfObjectRelease(catalog.get(PdfName.OUTLINES));
/* 286 */     if ((obj == null) || (!obj.isDictionary()))
/* 287 */       return null;
/* 288 */     PdfDictionary outlines = (PdfDictionary)obj;
/* 289 */     return getBookmark(reader, outlines, false);
/*     */   }
/*     */ 
/*     */   public static List<HashMap<String, Object>> getBookmark(PdfReader reader, PdfDictionary outline, boolean includeRoot)
/*     */   {
/* 302 */     PdfDictionary catalog = reader.getCatalog();
/* 303 */     if (outline == null)
/* 304 */       return null;
/* 305 */     IntHashtable pages = new IntHashtable();
/* 306 */     int numPages = reader.getNumberOfPages();
/* 307 */     for (int k = 1; k <= numPages; k++) {
/* 308 */       pages.put(reader.getPageOrigRef(k).getNumber(), k);
/* 309 */       reader.releasePage(k);
/*     */     }
/* 311 */     if (includeRoot) {
/* 312 */       return bookmarkDepth(reader, outline, pages, true);
/*     */     }
/* 314 */     return bookmarkDepth(reader, (PdfDictionary)PdfReader.getPdfObjectRelease(outline.get(PdfName.FIRST)), pages, false);
/*     */   }
/*     */ 
/*     */   public static void eliminatePages(List<HashMap<String, Object>> list, int[] pageRange)
/*     */   {
/* 326 */     if (list == null)
/* 327 */       return;
/* 328 */     for (Iterator it = list.listIterator(); it.hasNext(); ) {
/* 329 */       HashMap map = (HashMap)it.next();
/* 330 */       boolean hit = false;
/* 331 */       if ("GoTo".equals(map.get("Action"))) {
/* 332 */         String page = (String)map.get("Page");
/* 333 */         if (page != null) {
/* 334 */           page = page.trim();
/* 335 */           int idx = page.indexOf(' ');
/*     */           int pageNum;
/*     */           int pageNum;
/* 337 */           if (idx < 0)
/* 338 */             pageNum = Integer.parseInt(page);
/*     */           else
/* 340 */             pageNum = Integer.parseInt(page.substring(0, idx));
/* 341 */           int len = pageRange.length & 0xFFFFFFFE;
/* 342 */           for (int k = 0; k < len; k += 2) {
/* 343 */             if ((pageNum >= pageRange[k]) && (pageNum <= pageRange[(k + 1)])) {
/* 344 */               hit = true;
/* 345 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 350 */       List kids = (List)map.get("Kids");
/* 351 */       if (kids != null) {
/* 352 */         eliminatePages(kids, pageRange);
/* 353 */         if (kids.isEmpty()) {
/* 354 */           map.remove("Kids");
/* 355 */           kids = null;
/*     */         }
/*     */       }
/* 358 */       if (hit)
/* 359 */         if (kids == null) {
/* 360 */           it.remove();
/*     */         } else {
/* 362 */           map.remove("Action");
/* 363 */           map.remove("Page");
/* 364 */           map.remove("Named");
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void shiftPageNumbers(List<HashMap<String, Object>> list, int pageShift, int[] pageRange)
/*     */   {
/* 382 */     if (list == null)
/* 383 */       return;
/* 384 */     for (Iterator it = list.listIterator(); it.hasNext(); ) {
/* 385 */       HashMap map = (HashMap)it.next();
/* 386 */       if ("GoTo".equals(map.get("Action"))) {
/* 387 */         String page = (String)map.get("Page");
/* 388 */         if (page != null) {
/* 389 */           page = page.trim();
/* 390 */           int idx = page.indexOf(' ');
/*     */           int pageNum;
/*     */           int pageNum;
/* 392 */           if (idx < 0)
/* 393 */             pageNum = Integer.parseInt(page);
/*     */           else
/* 395 */             pageNum = Integer.parseInt(page.substring(0, idx));
/* 396 */           boolean hit = false;
/* 397 */           if (pageRange == null) {
/* 398 */             hit = true;
/*     */           } else {
/* 400 */             int len = pageRange.length & 0xFFFFFFFE;
/* 401 */             for (int k = 0; k < len; k += 2) {
/* 402 */               if ((pageNum >= pageRange[k]) && (pageNum <= pageRange[(k + 1)])) {
/* 403 */                 hit = true;
/* 404 */                 break;
/*     */               }
/*     */             }
/*     */           }
/* 408 */           if (hit) {
/* 409 */             if (idx < 0)
/* 410 */               page = Integer.toString(pageNum + pageShift);
/*     */             else
/* 412 */               page = pageNum + pageShift + page.substring(idx);
/*     */           }
/* 414 */           map.put("Page", page);
/*     */         }
/*     */       }
/* 417 */       List kids = (List)map.get("Kids");
/* 418 */       if (kids != null)
/* 419 */         shiftPageNumbers(kids, pageShift, pageRange);
/*     */     }
/*     */   }
/*     */ 
/*     */   static void createOutlineAction(PdfDictionary outline, HashMap<String, Object> map, PdfWriter writer, boolean namedAsNames) {
/*     */     try {
/* 425 */       String action = (String)map.get("Action");
/* 426 */       if ("GoTo".equals(action))
/*     */       {
/*     */         String p;
/* 428 */         if ((p = (String)map.get("Named")) != null) {
/* 429 */           if (namedAsNames)
/* 430 */             outline.put(PdfName.DEST, new PdfName(p));
/*     */           else
/* 432 */             outline.put(PdfName.DEST, new PdfString(p, null));
/*     */         }
/* 434 */         else if ((p = (String)map.get("Page")) != null) {
/* 435 */           PdfArray ar = new PdfArray();
/* 436 */           StringTokenizer tk = new StringTokenizer(p);
/* 437 */           int n = Integer.parseInt(tk.nextToken());
/* 438 */           ar.add(writer.getPageReference(n));
/* 439 */           if (!tk.hasMoreTokens()) {
/* 440 */             ar.add(PdfName.XYZ);
/* 441 */             ar.add(new float[] { 0.0F, 10000.0F, 0.0F });
/*     */           }
/*     */           else {
/* 444 */             String fn = tk.nextToken();
/* 445 */             if (fn.startsWith("/"))
/* 446 */               fn = fn.substring(1);
/* 447 */             ar.add(new PdfName(fn));
/* 448 */             for (int k = 0; (k < 4) && (tk.hasMoreTokens()); k++) {
/* 449 */               fn = tk.nextToken();
/* 450 */               if (fn.equals("null"))
/* 451 */                 ar.add(PdfNull.PDFNULL);
/*     */               else
/* 453 */                 ar.add(new PdfNumber(fn));
/*     */             }
/*     */           }
/* 456 */           outline.put(PdfName.DEST, ar);
/*     */         }
/*     */       }
/* 459 */       else if ("GoToR".equals(action))
/*     */       {
/* 461 */         PdfDictionary dic = new PdfDictionary();
/*     */         String p;
/* 462 */         if ((p = (String)map.get("Named")) != null) {
/* 463 */           dic.put(PdfName.D, new PdfString(p, null));
/* 464 */         } else if ((p = (String)map.get("NamedN")) != null) {
/* 465 */           dic.put(PdfName.D, new PdfName(p));
/* 466 */         } else if ((p = (String)map.get("Page")) != null) {
/* 467 */           PdfArray ar = new PdfArray();
/* 468 */           StringTokenizer tk = new StringTokenizer(p);
/* 469 */           ar.add(new PdfNumber(tk.nextToken()));
/* 470 */           if (!tk.hasMoreTokens()) {
/* 471 */             ar.add(PdfName.XYZ);
/* 472 */             ar.add(new float[] { 0.0F, 10000.0F, 0.0F });
/*     */           }
/*     */           else {
/* 475 */             String fn = tk.nextToken();
/* 476 */             if (fn.startsWith("/"))
/* 477 */               fn = fn.substring(1);
/* 478 */             ar.add(new PdfName(fn));
/* 479 */             for (int k = 0; (k < 4) && (tk.hasMoreTokens()); k++) {
/* 480 */               fn = tk.nextToken();
/* 481 */               if (fn.equals("null"))
/* 482 */                 ar.add(PdfNull.PDFNULL);
/*     */               else
/* 484 */                 ar.add(new PdfNumber(fn));
/*     */             }
/*     */           }
/* 487 */           dic.put(PdfName.D, ar);
/*     */         }
/* 489 */         String file = (String)map.get("File");
/* 490 */         if ((dic.size() > 0) && (file != null)) {
/* 491 */           dic.put(PdfName.S, PdfName.GOTOR);
/* 492 */           dic.put(PdfName.F, new PdfString(file));
/* 493 */           String nw = (String)map.get("NewWindow");
/* 494 */           if (nw != null) {
/* 495 */             if (nw.equals("true"))
/* 496 */               dic.put(PdfName.NEWWINDOW, PdfBoolean.PDFTRUE);
/* 497 */             else if (nw.equals("false"))
/* 498 */               dic.put(PdfName.NEWWINDOW, PdfBoolean.PDFFALSE);
/*     */           }
/* 500 */           outline.put(PdfName.A, dic);
/*     */         }
/*     */       }
/* 503 */       else if ("URI".equals(action)) {
/* 504 */         String uri = (String)map.get("URI");
/* 505 */         if (uri != null) {
/* 506 */           PdfDictionary dic = new PdfDictionary();
/* 507 */           dic.put(PdfName.S, PdfName.URI);
/* 508 */           dic.put(PdfName.URI, new PdfString(uri));
/* 509 */           outline.put(PdfName.A, dic);
/*     */         }
/*     */       }
/* 512 */       else if ("JS".equals(action)) {
/* 513 */         String code = (String)map.get("Code");
/* 514 */         if (code != null) {
/* 515 */           outline.put(PdfName.A, PdfAction.javaScript(code, writer));
/*     */         }
/*     */       }
/* 518 */       else if ("Launch".equals(action)) {
/* 519 */         String file = (String)map.get("File");
/* 520 */         if (file != null) {
/* 521 */           PdfDictionary dic = new PdfDictionary();
/* 522 */           dic.put(PdfName.S, PdfName.LAUNCH);
/* 523 */           dic.put(PdfName.F, new PdfString(file));
/* 524 */           outline.put(PdfName.A, dic);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Object[] iterateOutlines(PdfWriter writer, PdfIndirectReference parent, List<HashMap<String, Object>> kids, boolean namedAsNames) throws IOException
/*     */   {
/* 535 */     PdfIndirectReference[] refs = new PdfIndirectReference[kids.size()];
/* 536 */     for (int k = 0; k < refs.length; k++)
/* 537 */       refs[k] = writer.getPdfIndirectReference();
/* 538 */     int ptr = 0;
/* 539 */     int count = 0;
/* 540 */     for (Iterator it = kids.listIterator(); it.hasNext(); ptr++) {
/* 541 */       HashMap map = (HashMap)it.next();
/* 542 */       Object[] lower = null;
/* 543 */       List subKid = (List)map.get("Kids");
/* 544 */       if ((subKid != null) && (!subKid.isEmpty()))
/* 545 */         lower = iterateOutlines(writer, refs[ptr], subKid, namedAsNames);
/* 546 */       PdfDictionary outline = new PdfDictionary();
/* 547 */       count++;
/* 548 */       if (lower != null) {
/* 549 */         outline.put(PdfName.FIRST, (PdfIndirectReference)lower[0]);
/* 550 */         outline.put(PdfName.LAST, (PdfIndirectReference)lower[1]);
/* 551 */         int n = ((Integer)lower[2]).intValue();
/* 552 */         if ("false".equals(map.get("Open"))) {
/* 553 */           outline.put(PdfName.COUNT, new PdfNumber(-n));
/*     */         }
/*     */         else {
/* 556 */           outline.put(PdfName.COUNT, new PdfNumber(n));
/* 557 */           count += n;
/*     */         }
/*     */       }
/* 560 */       outline.put(PdfName.PARENT, parent);
/* 561 */       if (ptr > 0)
/* 562 */         outline.put(PdfName.PREV, refs[(ptr - 1)]);
/* 563 */       if (ptr < refs.length - 1)
/* 564 */         outline.put(PdfName.NEXT, refs[(ptr + 1)]);
/* 565 */       outline.put(PdfName.TITLE, new PdfString((String)map.get("Title"), "UnicodeBig"));
/* 566 */       String color = (String)map.get("Color");
/* 567 */       if (color != null)
/*     */         try {
/* 569 */           PdfArray arr = new PdfArray();
/* 570 */           StringTokenizer tk = new StringTokenizer(color);
/* 571 */           for (int k = 0; k < 3; k++) {
/* 572 */             float f = Float.parseFloat(tk.nextToken());
/* 573 */             if (f < 0.0F) f = 0.0F;
/* 574 */             if (f > 1.0F) f = 1.0F;
/* 575 */             arr.add(new PdfNumber(f));
/*     */           }
/* 577 */           outline.put(PdfName.C, arr);
/*     */         } catch (Exception e) {
/*     */         }
/* 580 */       String style = (String)map.get("Style");
/* 581 */       if (style != null) {
/* 582 */         style = style.toLowerCase();
/* 583 */         int bits = 0;
/* 584 */         if (style.indexOf("italic") >= 0)
/* 585 */           bits |= 1;
/* 586 */         if (style.indexOf("bold") >= 0)
/* 587 */           bits |= 2;
/* 588 */         if (bits != 0)
/* 589 */           outline.put(PdfName.F, new PdfNumber(bits));
/*     */       }
/* 591 */       createOutlineAction(outline, map, writer, namedAsNames);
/* 592 */       writer.addToBody(outline, refs[ptr]);
/*     */     }
/* 594 */     return new Object[] { refs[0], refs[(refs.length - 1)], Integer.valueOf(count) };
/*     */   }
/*     */ 
/*     */   public static void exportToXMLNode(List<HashMap<String, Object>> list, Writer out, int indent, boolean onlyASCII)
/*     */     throws IOException
/*     */   {
/* 610 */     String dep = "";
/* 611 */     if (indent != -1) {
/* 612 */       for (int k = 0; k < indent; k++)
/* 613 */         dep = dep + "  ";
/*     */     }
/* 615 */     for (HashMap map : list) {
/* 616 */       String title = null;
/* 617 */       out.write(dep);
/* 618 */       out.write("<Title ");
/* 619 */       List kids = null;
/* 620 */       for (Map.Entry entry : map.entrySet()) {
/* 621 */         String key = (String)entry.getKey();
/* 622 */         if (key.equals("Title")) {
/* 623 */           title = (String)entry.getValue();
/*     */         }
/* 626 */         else if (key.equals("Kids")) {
/* 627 */           kids = (List)entry.getValue();
/*     */         }
/*     */         else
/*     */         {
/* 631 */           out.write(key);
/* 632 */           out.write("=\"");
/* 633 */           String value = (String)entry.getValue();
/* 634 */           if ((key.equals("Named")) || (key.equals("NamedN")))
/* 635 */             value = SimpleNamedDestination.escapeBinaryString(value);
/* 636 */           out.write(XMLUtil.escapeXML(value, onlyASCII));
/* 637 */           out.write("\" ");
/*     */         }
/*     */       }
/* 640 */       out.write(">");
/* 641 */       if (title == null)
/* 642 */         title = "";
/* 643 */       out.write(XMLUtil.escapeXML(title, onlyASCII));
/* 644 */       if (kids != null) {
/* 645 */         out.write("\n");
/* 646 */         exportToXMLNode(kids, out, indent == -1 ? indent : indent + 1, onlyASCII);
/* 647 */         out.write(dep);
/*     */       }
/* 649 */       out.write("</Title>\n");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void exportToXML(List<HashMap<String, Object>> list, OutputStream out, String encoding, boolean onlyASCII)
/*     */     throws IOException
/*     */   {
/* 682 */     String jenc = IanaEncodings.getJavaEncoding(encoding);
/* 683 */     Writer wrt = new BufferedWriter(new OutputStreamWriter(out, jenc));
/* 684 */     exportToXML(list, wrt, encoding, onlyASCII);
/*     */   }
/*     */ 
/*     */   public static void exportToXML(List<HashMap<String, Object>> list, Writer wrt, String encoding, boolean onlyASCII)
/*     */     throws IOException
/*     */   {
/* 698 */     wrt.write("<?xml version=\"1.0\" encoding=\"");
/* 699 */     wrt.write(XMLUtil.escapeXML(encoding, onlyASCII));
/* 700 */     wrt.write("\"?>\n<Bookmark>\n");
/* 701 */     exportToXMLNode(list, wrt, 1, onlyASCII);
/* 702 */     wrt.write("</Bookmark>\n");
/* 703 */     wrt.flush();
/*     */   }
/*     */ 
/*     */   public static List<HashMap<String, Object>> importFromXML(InputStream in)
/*     */     throws IOException
/*     */   {
/* 713 */     SimpleBookmark book = new SimpleBookmark();
/* 714 */     SimpleXMLParser.parse(book, in);
/* 715 */     return book.topList;
/*     */   }
/*     */ 
/*     */   public static List<HashMap<String, Object>> importFromXML(Reader in)
/*     */     throws IOException
/*     */   {
/* 725 */     SimpleBookmark book = new SimpleBookmark();
/* 726 */     SimpleXMLParser.parse(book, in);
/* 727 */     return book.topList;
/*     */   }
/*     */ 
/*     */   public void endDocument()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endElement(String tag) {
/* 735 */     if (tag.equals("Bookmark")) {
/* 736 */       if (this.attr.isEmpty()) {
/* 737 */         return;
/*     */       }
/* 739 */       throw new RuntimeException(MessageLocalization.getComposedMessage("bookmark.end.tag.out.of.place", new Object[0]));
/*     */     }
/* 741 */     if (!tag.equals("Title"))
/* 742 */       throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.end.tag.1", new Object[] { tag }));
/* 743 */     HashMap attributes = (HashMap)this.attr.pop();
/* 744 */     String title = (String)attributes.get("Title");
/* 745 */     attributes.put("Title", title.trim());
/* 746 */     String named = (String)attributes.get("Named");
/* 747 */     if (named != null)
/* 748 */       attributes.put("Named", SimpleNamedDestination.unEscapeBinaryString(named));
/* 749 */     named = (String)attributes.get("NamedN");
/* 750 */     if (named != null)
/* 751 */       attributes.put("NamedN", SimpleNamedDestination.unEscapeBinaryString(named));
/* 752 */     if (this.attr.isEmpty()) {
/* 753 */       this.topList.add(attributes);
/*     */     } else {
/* 755 */       HashMap parent = (HashMap)this.attr.peek();
/* 756 */       List kids = (List)parent.get("Kids");
/* 757 */       if (kids == null) {
/* 758 */         kids = new ArrayList();
/* 759 */         parent.put("Kids", kids);
/*     */       }
/* 761 */       kids.add(attributes);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void startDocument() {
/*     */   }
/*     */ 
/*     */   public void startElement(String tag, Map<String, String> h) {
/* 769 */     if (this.topList == null) {
/* 770 */       if (tag.equals("Bookmark")) {
/* 771 */         this.topList = new ArrayList();
/* 772 */         return;
/*     */       }
/*     */ 
/* 775 */       throw new RuntimeException(MessageLocalization.getComposedMessage("root.element.is.not.bookmark.1", new Object[] { tag }));
/*     */     }
/* 777 */     if (!tag.equals("Title"))
/* 778 */       throw new RuntimeException(MessageLocalization.getComposedMessage("tag.1.not.allowed", new Object[] { tag }));
/* 779 */     HashMap attributes = new HashMap(h);
/* 780 */     attributes.put("Title", "");
/* 781 */     attributes.remove("Kids");
/* 782 */     this.attr.push(attributes);
/*     */   }
/*     */ 
/*     */   public void text(String str) {
/* 786 */     if (this.attr.isEmpty())
/* 787 */       return;
/* 788 */     HashMap attributes = (HashMap)this.attr.peek();
/* 789 */     String title = (String)attributes.get("Title");
/* 790 */     title = title + str;
/* 791 */     attributes.put("Title", title);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.SimpleBookmark
 * JD-Core Version:    0.6.2
 */