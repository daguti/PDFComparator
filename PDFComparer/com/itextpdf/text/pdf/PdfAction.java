/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.collection.PdfTargetDictionary;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class PdfAction extends PdfDictionary
/*     */ {
/*     */   public static final int FIRSTPAGE = 1;
/*     */   public static final int PREVPAGE = 2;
/*     */   public static final int NEXTPAGE = 3;
/*     */   public static final int LASTPAGE = 4;
/*     */   public static final int PRINTDIALOG = 5;
/*     */   public static final int SUBMIT_EXCLUDE = 1;
/*     */   public static final int SUBMIT_INCLUDE_NO_VALUE_FIELDS = 2;
/*     */   public static final int SUBMIT_HTML_FORMAT = 4;
/*     */   public static final int SUBMIT_HTML_GET = 8;
/*     */   public static final int SUBMIT_COORDINATES = 16;
/*     */   public static final int SUBMIT_XFDF = 32;
/*     */   public static final int SUBMIT_INCLUDE_APPEND_SAVES = 64;
/*     */   public static final int SUBMIT_INCLUDE_ANNOTATIONS = 128;
/*     */   public static final int SUBMIT_PDF = 256;
/*     */   public static final int SUBMIT_CANONICAL_FORMAT = 512;
/*     */   public static final int SUBMIT_EXCL_NON_USER_ANNOTS = 1024;
/*     */   public static final int SUBMIT_EXCL_F_KEY = 2048;
/*     */   public static final int SUBMIT_EMBED_FORM = 8196;
/*     */   public static final int RESET_EXCLUDE = 1;
/*     */ 
/*     */   public PdfAction()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PdfAction(URL url)
/*     */   {
/* 124 */     this(url.toExternalForm());
/*     */   }
/*     */ 
/*     */   public PdfAction(URL url, boolean isMap)
/*     */   {
/* 133 */     this(url.toExternalForm(), isMap);
/*     */   }
/*     */ 
/*     */   public PdfAction(String url)
/*     */   {
/* 143 */     this(url, false);
/*     */   }
/*     */ 
/*     */   public PdfAction(String url, boolean isMap)
/*     */   {
/* 153 */     put(PdfName.S, PdfName.URI);
/* 154 */     put(PdfName.URI, new PdfString(url));
/* 155 */     if (isMap)
/* 156 */       put(PdfName.ISMAP, PdfBoolean.PDFTRUE);
/*     */   }
/*     */ 
/*     */   PdfAction(PdfIndirectReference destination)
/*     */   {
/* 165 */     put(PdfName.S, PdfName.GOTO);
/* 166 */     put(PdfName.D, destination);
/*     */   }
/*     */ 
/*     */   public PdfAction(String filename, String name)
/*     */   {
/* 176 */     put(PdfName.S, PdfName.GOTOR);
/* 177 */     put(PdfName.F, new PdfString(filename));
/* 178 */     put(PdfName.D, new PdfString(name));
/*     */   }
/*     */ 
/*     */   public PdfAction(String filename, int page)
/*     */   {
/* 188 */     put(PdfName.S, PdfName.GOTOR);
/* 189 */     put(PdfName.F, new PdfString(filename));
/* 190 */     put(PdfName.D, new PdfLiteral("[" + (page - 1) + " /FitH 10000]"));
/*     */   }
/*     */ 
/*     */   public PdfAction(int named)
/*     */   {
/* 198 */     put(PdfName.S, PdfName.NAMED);
/* 199 */     switch (named) {
/*     */     case 1:
/* 201 */       put(PdfName.N, PdfName.FIRSTPAGE);
/* 202 */       break;
/*     */     case 4:
/* 204 */       put(PdfName.N, PdfName.LASTPAGE);
/* 205 */       break;
/*     */     case 3:
/* 207 */       put(PdfName.N, PdfName.NEXTPAGE);
/* 208 */       break;
/*     */     case 2:
/* 210 */       put(PdfName.N, PdfName.PREVPAGE);
/* 211 */       break;
/*     */     case 5:
/* 213 */       put(PdfName.S, PdfName.JAVASCRIPT);
/* 214 */       put(PdfName.JS, new PdfString("this.print(true);\r"));
/* 215 */       break;
/*     */     default:
/* 217 */       throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.named.action", new Object[0]));
/*     */     }
/*     */   }
/*     */ 
/*     */   public PdfAction(String application, String parameters, String operation, String defaultDir)
/*     */   {
/* 232 */     put(PdfName.S, PdfName.LAUNCH);
/* 233 */     if ((parameters == null) && (operation == null) && (defaultDir == null)) {
/* 234 */       put(PdfName.F, new PdfString(application));
/*     */     } else {
/* 236 */       PdfDictionary dic = new PdfDictionary();
/* 237 */       dic.put(PdfName.F, new PdfString(application));
/* 238 */       if (parameters != null)
/* 239 */         dic.put(PdfName.P, new PdfString(parameters));
/* 240 */       if (operation != null)
/* 241 */         dic.put(PdfName.O, new PdfString(operation));
/* 242 */       if (defaultDir != null)
/* 243 */         dic.put(PdfName.D, new PdfString(defaultDir));
/* 244 */       put(PdfName.WIN, dic);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static PdfAction createLaunch(String application, String parameters, String operation, String defaultDir)
/*     */   {
/* 260 */     return new PdfAction(application, parameters, operation, defaultDir);
/*     */   }
/*     */ 
/*     */   public static PdfAction rendition(String file, PdfFileSpecification fs, String mimeType, PdfIndirectReference ref)
/*     */     throws IOException
/*     */   {
/* 272 */     PdfAction js = new PdfAction();
/* 273 */     js.put(PdfName.S, PdfName.RENDITION);
/* 274 */     js.put(PdfName.R, new PdfRendition(file, fs, mimeType));
/* 275 */     js.put(new PdfName("OP"), new PdfNumber(0));
/* 276 */     js.put(new PdfName("AN"), ref);
/* 277 */     return js;
/*     */   }
/*     */ 
/*     */   public static PdfAction javaScript(String code, PdfWriter writer, boolean unicode)
/*     */   {
/* 291 */     PdfAction js = new PdfAction();
/* 292 */     js.put(PdfName.S, PdfName.JAVASCRIPT);
/* 293 */     if ((unicode) && (code.length() < 50)) {
/* 294 */       js.put(PdfName.JS, new PdfString(code, "UnicodeBig"));
/*     */     }
/* 296 */     else if ((!unicode) && (code.length() < 100))
/* 297 */       js.put(PdfName.JS, new PdfString(code));
/*     */     else {
/*     */       try
/*     */       {
/* 301 */         byte[] b = PdfEncodings.convertToBytes(code, unicode ? "UnicodeBig" : "PDF");
/* 302 */         PdfStream stream = new PdfStream(b);
/* 303 */         stream.flateCompress(writer.getCompressionLevel());
/* 304 */         js.put(PdfName.JS, writer.addToBody(stream).getIndirectReference());
/*     */       }
/*     */       catch (Exception e) {
/* 307 */         js.put(PdfName.JS, new PdfString(code));
/*     */       }
/*     */     }
/* 310 */     return js;
/*     */   }
/*     */ 
/*     */   public static PdfAction javaScript(String code, PdfWriter writer)
/*     */   {
/* 321 */     return javaScript(code, writer, false);
/*     */   }
/*     */ 
/*     */   static PdfAction createHide(PdfObject obj, boolean hide)
/*     */   {
/* 331 */     PdfAction action = new PdfAction();
/* 332 */     action.put(PdfName.S, PdfName.HIDE);
/* 333 */     action.put(PdfName.T, obj);
/* 334 */     if (!hide)
/* 335 */       action.put(PdfName.H, PdfBoolean.PDFFALSE);
/* 336 */     return action;
/*     */   }
/*     */ 
/*     */   public static PdfAction createHide(PdfAnnotation annot, boolean hide)
/*     */   {
/* 346 */     return createHide(annot.getIndirectReference(), hide);
/*     */   }
/*     */ 
/*     */   public static PdfAction createHide(String name, boolean hide)
/*     */   {
/* 356 */     return createHide(new PdfString(name), hide);
/*     */   }
/*     */ 
/*     */   static PdfArray buildArray(Object[] names) {
/* 360 */     PdfArray array = new PdfArray();
/* 361 */     for (int k = 0; k < names.length; k++) {
/* 362 */       Object obj = names[k];
/* 363 */       if ((obj instanceof String))
/* 364 */         array.add(new PdfString((String)obj));
/* 365 */       else if ((obj instanceof PdfAnnotation))
/* 366 */         array.add(((PdfAnnotation)obj).getIndirectReference());
/*     */       else
/* 368 */         throw new RuntimeException(MessageLocalization.getComposedMessage("the.array.must.contain.string.or.pdfannotation", new Object[0]));
/*     */     }
/* 370 */     return array;
/*     */   }
/*     */ 
/*     */   public static PdfAction createHide(Object[] names, boolean hide)
/*     */   {
/* 380 */     return createHide(buildArray(names), hide);
/*     */   }
/*     */ 
/*     */   public static PdfAction createSubmitForm(String file, Object[] names, int flags)
/*     */   {
/* 391 */     PdfAction action = new PdfAction();
/* 392 */     action.put(PdfName.S, PdfName.SUBMITFORM);
/* 393 */     PdfDictionary dic = new PdfDictionary();
/* 394 */     dic.put(PdfName.F, new PdfString(file));
/* 395 */     dic.put(PdfName.FS, PdfName.URL);
/* 396 */     action.put(PdfName.F, dic);
/* 397 */     if (names != null)
/* 398 */       action.put(PdfName.FIELDS, buildArray(names));
/* 399 */     action.put(PdfName.FLAGS, new PdfNumber(flags));
/* 400 */     return action;
/*     */   }
/*     */ 
/*     */   public static PdfAction createResetForm(Object[] names, int flags)
/*     */   {
/* 410 */     PdfAction action = new PdfAction();
/* 411 */     action.put(PdfName.S, PdfName.RESETFORM);
/* 412 */     if (names != null)
/* 413 */       action.put(PdfName.FIELDS, buildArray(names));
/* 414 */     action.put(PdfName.FLAGS, new PdfNumber(flags));
/* 415 */     return action;
/*     */   }
/*     */ 
/*     */   public static PdfAction createImportData(String file)
/*     */   {
/* 424 */     PdfAction action = new PdfAction();
/* 425 */     action.put(PdfName.S, PdfName.IMPORTDATA);
/* 426 */     action.put(PdfName.F, new PdfString(file));
/* 427 */     return action;
/*     */   }
/*     */ 
/*     */   public void next(PdfAction na)
/*     */   {
/* 434 */     PdfObject nextAction = get(PdfName.NEXT);
/* 435 */     if (nextAction == null) {
/* 436 */       put(PdfName.NEXT, na);
/* 437 */     } else if (nextAction.isDictionary()) {
/* 438 */       PdfArray array = new PdfArray(nextAction);
/* 439 */       array.add(na);
/* 440 */       put(PdfName.NEXT, array);
/*     */     }
/*     */     else {
/* 443 */       ((PdfArray)nextAction).add(na);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static PdfAction gotoLocalPage(int page, PdfDestination dest, PdfWriter writer)
/*     */   {
/* 454 */     PdfIndirectReference ref = writer.getPageReference(page);
/* 455 */     dest.addPage(ref);
/* 456 */     PdfAction action = new PdfAction();
/* 457 */     action.put(PdfName.S, PdfName.GOTO);
/* 458 */     action.put(PdfName.D, dest);
/* 459 */     return action;
/*     */   }
/*     */ 
/*     */   public static PdfAction gotoLocalPage(String dest, boolean isName)
/*     */   {
/* 469 */     PdfAction action = new PdfAction();
/* 470 */     action.put(PdfName.S, PdfName.GOTO);
/* 471 */     if (isName)
/* 472 */       action.put(PdfName.D, new PdfName(dest));
/*     */     else
/* 474 */       action.put(PdfName.D, new PdfString(dest, "UnicodeBig"));
/* 475 */     return action;
/*     */   }
/*     */ 
/*     */   public static PdfAction gotoRemotePage(String filename, String dest, boolean isName, boolean newWindow)
/*     */   {
/* 487 */     PdfAction action = new PdfAction();
/* 488 */     action.put(PdfName.F, new PdfString(filename));
/* 489 */     action.put(PdfName.S, PdfName.GOTOR);
/* 490 */     if (isName)
/* 491 */       action.put(PdfName.D, new PdfName(dest));
/*     */     else
/* 493 */       action.put(PdfName.D, new PdfString(dest, "UnicodeBig"));
/* 494 */     if (newWindow)
/* 495 */       action.put(PdfName.NEWWINDOW, PdfBoolean.PDFTRUE);
/* 496 */     return action;
/*     */   }
/*     */ 
/*     */   public static PdfAction gotoEmbedded(String filename, PdfTargetDictionary target, String dest, boolean isName, boolean newWindow)
/*     */   {
/* 507 */     if (isName) {
/* 508 */       return gotoEmbedded(filename, target, new PdfName(dest), newWindow);
/*     */     }
/* 510 */     return gotoEmbedded(filename, target, new PdfString(dest, "UnicodeBig"), newWindow);
/*     */   }
/*     */ 
/*     */   public static PdfAction gotoEmbedded(String filename, PdfTargetDictionary target, PdfObject dest, boolean newWindow)
/*     */   {
/* 522 */     PdfAction action = new PdfAction();
/* 523 */     action.put(PdfName.S, PdfName.GOTOE);
/* 524 */     action.put(PdfName.T, target);
/* 525 */     action.put(PdfName.D, dest);
/* 526 */     action.put(PdfName.NEWWINDOW, new PdfBoolean(newWindow));
/* 527 */     if (filename != null) {
/* 528 */       action.put(PdfName.F, new PdfString(filename));
/*     */     }
/* 530 */     return action;
/*     */   }
/*     */ 
/*     */   public static PdfAction setOCGstate(ArrayList<Object> state, boolean preserveRB)
/*     */   {
/* 556 */     PdfAction action = new PdfAction();
/* 557 */     action.put(PdfName.S, PdfName.SETOCGSTATE);
/* 558 */     PdfArray a = new PdfArray();
/* 559 */     for (int k = 0; k < state.size(); k++) {
/* 560 */       Object o = state.get(k);
/* 561 */       if (o != null)
/*     */       {
/* 563 */         if ((o instanceof PdfIndirectReference)) {
/* 564 */           a.add((PdfIndirectReference)o);
/* 565 */         } else if ((o instanceof PdfLayer)) {
/* 566 */           a.add(((PdfLayer)o).getRef());
/* 567 */         } else if ((o instanceof PdfName)) {
/* 568 */           a.add((PdfName)o);
/* 569 */         } else if ((o instanceof String)) {
/* 570 */           PdfName name = null;
/* 571 */           String s = (String)o;
/* 572 */           if (s.equalsIgnoreCase("on"))
/* 573 */             name = PdfName.ON;
/* 574 */           else if (s.equalsIgnoreCase("off"))
/* 575 */             name = PdfName.OFF;
/* 576 */           else if (s.equalsIgnoreCase("toggle"))
/* 577 */             name = PdfName.TOGGLE;
/*     */           else
/* 579 */             throw new IllegalArgumentException(MessageLocalization.getComposedMessage("a.string.1.was.passed.in.state.only.on.off.and.toggle.are.allowed", new Object[] { s }));
/* 580 */           a.add(name);
/*     */         }
/*     */         else {
/* 583 */           throw new IllegalArgumentException(MessageLocalization.getComposedMessage("invalid.type.was.passed.in.state.1", new Object[] { o.getClass().getName() }));
/*     */         }
/*     */       }
/*     */     }
/* 585 */     action.put(PdfName.STATE, a);
/* 586 */     if (!preserveRB)
/* 587 */       action.put(PdfName.PRESERVERB, PdfBoolean.PDFFALSE);
/* 588 */     return action;
/*     */   }
/*     */ 
/*     */   public void toPdf(PdfWriter writer, OutputStream os) throws IOException
/*     */   {
/* 593 */     PdfWriter.checkPdfIsoConformance(writer, 14, this);
/* 594 */     super.toPdf(writer, os);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfAction
 * JD-Core Version:    0.6.2
 */