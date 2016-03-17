/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.awt.geom.AffineTransform;
/*      */ import com.itextpdf.text.AccessibleElementId;
/*      */ import com.itextpdf.text.BaseColor;
/*      */ import com.itextpdf.text.Rectangle;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ 
/*      */ public class PdfAnnotation extends PdfDictionary
/*      */   implements IAccessibleElement
/*      */ {
/*   69 */   public static final PdfName HIGHLIGHT_NONE = PdfName.N;
/*      */ 
/*   71 */   public static final PdfName HIGHLIGHT_INVERT = PdfName.I;
/*      */ 
/*   73 */   public static final PdfName HIGHLIGHT_OUTLINE = PdfName.O;
/*      */ 
/*   75 */   public static final PdfName HIGHLIGHT_PUSH = PdfName.P;
/*      */ 
/*   77 */   public static final PdfName HIGHLIGHT_TOGGLE = PdfName.T;
/*      */   public static final int FLAGS_INVISIBLE = 1;
/*      */   public static final int FLAGS_HIDDEN = 2;
/*      */   public static final int FLAGS_PRINT = 4;
/*      */   public static final int FLAGS_NOZOOM = 8;
/*      */   public static final int FLAGS_NOROTATE = 16;
/*      */   public static final int FLAGS_NOVIEW = 32;
/*      */   public static final int FLAGS_READONLY = 64;
/*      */   public static final int FLAGS_LOCKED = 128;
/*      */   public static final int FLAGS_TOGGLENOVIEW = 256;
/*      */   public static final int FLAGS_LOCKEDCONTENTS = 512;
/*   99 */   public static final PdfName APPEARANCE_NORMAL = PdfName.N;
/*      */ 
/*  101 */   public static final PdfName APPEARANCE_ROLLOVER = PdfName.R;
/*      */ 
/*  103 */   public static final PdfName APPEARANCE_DOWN = PdfName.D;
/*      */ 
/*  105 */   public static final PdfName AA_ENTER = PdfName.E;
/*      */ 
/*  107 */   public static final PdfName AA_EXIT = PdfName.X;
/*      */ 
/*  109 */   public static final PdfName AA_DOWN = PdfName.D;
/*      */ 
/*  111 */   public static final PdfName AA_UP = PdfName.U;
/*      */ 
/*  113 */   public static final PdfName AA_FOCUS = PdfName.FO;
/*      */ 
/*  115 */   public static final PdfName AA_BLUR = PdfName.BL;
/*      */ 
/*  117 */   public static final PdfName AA_JS_KEY = PdfName.K;
/*      */ 
/*  119 */   public static final PdfName AA_JS_FORMAT = PdfName.F;
/*      */ 
/*  121 */   public static final PdfName AA_JS_CHANGE = PdfName.V;
/*      */ 
/*  123 */   public static final PdfName AA_JS_OTHER_CHANGE = PdfName.C;
/*      */   public static final int MARKUP_HIGHLIGHT = 0;
/*      */   public static final int MARKUP_UNDERLINE = 1;
/*      */   public static final int MARKUP_STRIKEOUT = 2;
/*      */   public static final int MARKUP_SQUIGGLY = 3;
/*      */   protected PdfWriter writer;
/*      */   protected PdfIndirectReference reference;
/*      */   protected HashSet<PdfTemplate> templates;
/*  143 */   protected boolean form = false;
/*  144 */   protected boolean annotation = true;
/*      */ 
/*  147 */   protected boolean used = false;
/*      */ 
/*  150 */   private int placeInPage = -1;
/*      */ 
/*  152 */   protected PdfName role = null;
/*  153 */   protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
/*  154 */   private AccessibleElementId id = null;
/*      */ 
/*      */   public PdfAnnotation(PdfWriter writer, Rectangle rect)
/*      */   {
/*  158 */     this.writer = writer;
/*  159 */     if (rect != null)
/*  160 */       put(PdfName.RECT, new PdfRectangle(rect));
/*      */   }
/*      */ 
/*      */   public PdfAnnotation(PdfWriter writer, float llx, float lly, float urx, float ury, PdfString title, PdfString content)
/*      */   {
/*  175 */     this.writer = writer;
/*  176 */     put(PdfName.SUBTYPE, PdfName.TEXT);
/*  177 */     put(PdfName.T, title);
/*  178 */     put(PdfName.RECT, new PdfRectangle(llx, lly, urx, ury));
/*  179 */     put(PdfName.CONTENTS, content);
/*      */   }
/*      */ 
/*      */   public PdfAnnotation(PdfWriter writer, float llx, float lly, float urx, float ury, PdfAction action)
/*      */   {
/*  193 */     this.writer = writer;
/*  194 */     put(PdfName.SUBTYPE, PdfName.LINK);
/*  195 */     put(PdfName.RECT, new PdfRectangle(llx, lly, urx, ury));
/*  196 */     put(PdfName.A, action);
/*  197 */     put(PdfName.BORDER, new PdfBorderArray(0.0F, 0.0F, 0.0F));
/*  198 */     put(PdfName.C, new PdfColor(0, 0, 255));
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createScreen(PdfWriter writer, Rectangle rect, String clipTitle, PdfFileSpecification fs, String mimeType, boolean playOnDisplay)
/*      */     throws IOException
/*      */   {
/*  214 */     PdfAnnotation ann = new PdfAnnotation(writer, rect);
/*  215 */     ann.put(PdfName.SUBTYPE, PdfName.SCREEN);
/*  216 */     ann.put(PdfName.F, new PdfNumber(4));
/*  217 */     ann.put(PdfName.TYPE, PdfName.ANNOT);
/*  218 */     ann.setPage();
/*  219 */     PdfIndirectReference ref = ann.getIndirectReference();
/*  220 */     PdfAction action = PdfAction.rendition(clipTitle, fs, mimeType, ref);
/*  221 */     PdfIndirectReference actionRef = writer.addToBody(action).getIndirectReference();
/*      */ 
/*  223 */     if (playOnDisplay)
/*      */     {
/*  225 */       PdfDictionary aa = new PdfDictionary();
/*  226 */       aa.put(new PdfName("PV"), actionRef);
/*  227 */       ann.put(PdfName.AA, aa);
/*      */     }
/*  229 */     ann.put(PdfName.A, actionRef);
/*  230 */     return ann;
/*      */   }
/*      */ 
/*      */   public PdfIndirectReference getIndirectReference()
/*      */   {
/*  238 */     if (this.reference == null) {
/*  239 */       this.reference = this.writer.getPdfIndirectReference();
/*      */     }
/*  241 */     return this.reference;
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createText(PdfWriter writer, Rectangle rect, String title, String contents, boolean open, String icon)
/*      */   {
/*  254 */     PdfAnnotation annot = new PdfAnnotation(writer, rect);
/*  255 */     annot.put(PdfName.SUBTYPE, PdfName.TEXT);
/*  256 */     if (title != null)
/*  257 */       annot.put(PdfName.T, new PdfString(title, "UnicodeBig"));
/*  258 */     if (contents != null)
/*  259 */       annot.put(PdfName.CONTENTS, new PdfString(contents, "UnicodeBig"));
/*  260 */     if (open)
/*  261 */       annot.put(PdfName.OPEN, PdfBoolean.PDFTRUE);
/*  262 */     if (icon != null) {
/*  263 */       annot.put(PdfName.NAME, new PdfName(icon));
/*      */     }
/*  265 */     return annot;
/*      */   }
/*      */ 
/*      */   protected static PdfAnnotation createLink(PdfWriter writer, Rectangle rect, PdfName highlight)
/*      */   {
/*  276 */     PdfAnnotation annot = new PdfAnnotation(writer, rect);
/*  277 */     annot.put(PdfName.SUBTYPE, PdfName.LINK);
/*  278 */     if (!highlight.equals(HIGHLIGHT_INVERT))
/*  279 */       annot.put(PdfName.H, highlight);
/*  280 */     return annot;
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createLink(PdfWriter writer, Rectangle rect, PdfName highlight, PdfAction action)
/*      */   {
/*  292 */     PdfAnnotation annot = createLink(writer, rect, highlight);
/*  293 */     annot.putEx(PdfName.A, action);
/*  294 */     return annot;
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createLink(PdfWriter writer, Rectangle rect, PdfName highlight, String namedDestination)
/*      */   {
/*  306 */     PdfAnnotation annot = createLink(writer, rect, highlight);
/*  307 */     annot.put(PdfName.DEST, new PdfString(namedDestination, "UnicodeBig"));
/*  308 */     return annot;
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createLink(PdfWriter writer, Rectangle rect, PdfName highlight, int page, PdfDestination dest)
/*      */   {
/*  321 */     PdfAnnotation annot = createLink(writer, rect, highlight);
/*  322 */     PdfIndirectReference ref = writer.getPageReference(page);
/*  323 */     dest.addPage(ref);
/*  324 */     annot.put(PdfName.DEST, dest);
/*  325 */     return annot;
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createFreeText(PdfWriter writer, Rectangle rect, String contents, PdfContentByte defaultAppearance)
/*      */   {
/*  337 */     PdfAnnotation annot = new PdfAnnotation(writer, rect);
/*  338 */     annot.put(PdfName.SUBTYPE, PdfName.FREETEXT);
/*  339 */     annot.put(PdfName.CONTENTS, new PdfString(contents, "UnicodeBig"));
/*  340 */     annot.setDefaultAppearanceString(defaultAppearance);
/*  341 */     return annot;
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createLine(PdfWriter writer, Rectangle rect, String contents, float x1, float y1, float x2, float y2)
/*      */   {
/*  356 */     PdfAnnotation annot = new PdfAnnotation(writer, rect);
/*  357 */     annot.put(PdfName.SUBTYPE, PdfName.LINE);
/*  358 */     annot.put(PdfName.CONTENTS, new PdfString(contents, "UnicodeBig"));
/*  359 */     PdfArray array = new PdfArray(new PdfNumber(x1));
/*  360 */     array.add(new PdfNumber(y1));
/*  361 */     array.add(new PdfNumber(x2));
/*  362 */     array.add(new PdfNumber(y2));
/*  363 */     annot.put(PdfName.L, array);
/*  364 */     return annot;
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createSquareCircle(PdfWriter writer, Rectangle rect, String contents, boolean square)
/*      */   {
/*  376 */     PdfAnnotation annot = new PdfAnnotation(writer, rect);
/*  377 */     if (square)
/*  378 */       annot.put(PdfName.SUBTYPE, PdfName.SQUARE);
/*      */     else
/*  380 */       annot.put(PdfName.SUBTYPE, PdfName.CIRCLE);
/*  381 */     annot.put(PdfName.CONTENTS, new PdfString(contents, "UnicodeBig"));
/*  382 */     return annot;
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createMarkup(PdfWriter writer, Rectangle rect, String contents, int type, float[] quadPoints) {
/*  386 */     PdfAnnotation annot = new PdfAnnotation(writer, rect);
/*  387 */     PdfName name = PdfName.HIGHLIGHT;
/*  388 */     switch (type) {
/*      */     case 1:
/*  390 */       name = PdfName.UNDERLINE;
/*  391 */       break;
/*      */     case 2:
/*  393 */       name = PdfName.STRIKEOUT;
/*  394 */       break;
/*      */     case 3:
/*  396 */       name = PdfName.SQUIGGLY;
/*      */     }
/*      */ 
/*  399 */     annot.put(PdfName.SUBTYPE, name);
/*  400 */     annot.put(PdfName.CONTENTS, new PdfString(contents, "UnicodeBig"));
/*  401 */     PdfArray array = new PdfArray();
/*  402 */     for (int k = 0; k < quadPoints.length; k++)
/*  403 */       array.add(new PdfNumber(quadPoints[k]));
/*  404 */     annot.put(PdfName.QUADPOINTS, array);
/*  405 */     return annot;
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createStamp(PdfWriter writer, Rectangle rect, String contents, String name)
/*      */   {
/*  417 */     PdfAnnotation annot = new PdfAnnotation(writer, rect);
/*  418 */     annot.put(PdfName.SUBTYPE, PdfName.STAMP);
/*  419 */     annot.put(PdfName.CONTENTS, new PdfString(contents, "UnicodeBig"));
/*  420 */     annot.put(PdfName.NAME, new PdfName(name));
/*  421 */     return annot;
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createInk(PdfWriter writer, Rectangle rect, String contents, float[][] inkList) {
/*  425 */     PdfAnnotation annot = new PdfAnnotation(writer, rect);
/*  426 */     annot.put(PdfName.SUBTYPE, PdfName.INK);
/*  427 */     annot.put(PdfName.CONTENTS, new PdfString(contents, "UnicodeBig"));
/*  428 */     PdfArray outer = new PdfArray();
/*  429 */     for (int k = 0; k < inkList.length; k++) {
/*  430 */       PdfArray inner = new PdfArray();
/*  431 */       float[] deep = inkList[k];
/*  432 */       for (int j = 0; j < deep.length; j++)
/*  433 */         inner.add(new PdfNumber(deep[j]));
/*  434 */       outer.add(inner);
/*      */     }
/*  436 */     annot.put(PdfName.INKLIST, outer);
/*  437 */     return annot;
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createFileAttachment(PdfWriter writer, Rectangle rect, String contents, byte[] fileStore, String file, String fileDisplay)
/*      */     throws IOException
/*      */   {
/*  453 */     return createFileAttachment(writer, rect, contents, PdfFileSpecification.fileEmbedded(writer, file, fileDisplay, fileStore));
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createFileAttachment(PdfWriter writer, Rectangle rect, String contents, PdfFileSpecification fs)
/*      */     throws IOException
/*      */   {
/*  465 */     PdfAnnotation annot = new PdfAnnotation(writer, rect);
/*  466 */     annot.put(PdfName.SUBTYPE, PdfName.FILEATTACHMENT);
/*  467 */     if (contents != null)
/*  468 */       annot.put(PdfName.CONTENTS, new PdfString(contents, "UnicodeBig"));
/*  469 */     annot.put(PdfName.FS, fs.getReference());
/*  470 */     return annot;
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createPopup(PdfWriter writer, Rectangle rect, String contents, boolean open)
/*      */   {
/*  482 */     PdfAnnotation annot = new PdfAnnotation(writer, rect);
/*  483 */     annot.put(PdfName.SUBTYPE, PdfName.POPUP);
/*  484 */     if (contents != null)
/*  485 */       annot.put(PdfName.CONTENTS, new PdfString(contents, "UnicodeBig"));
/*  486 */     if (open)
/*  487 */       annot.put(PdfName.OPEN, PdfBoolean.PDFTRUE);
/*  488 */     return annot;
/*      */   }
/*      */ 
/*      */   public static PdfAnnotation createPolygonPolyline(PdfWriter writer, Rectangle rect, String contents, boolean polygon, PdfArray vertices)
/*      */   {
/*  502 */     PdfAnnotation annot = new PdfAnnotation(writer, rect);
/*  503 */     if (polygon)
/*  504 */       annot.put(PdfName.SUBTYPE, PdfName.POLYGON);
/*      */     else
/*  506 */       annot.put(PdfName.SUBTYPE, PdfName.POLYLINE);
/*  507 */     annot.put(PdfName.CONTENTS, new PdfString(contents, "UnicodeBig"));
/*  508 */     annot.put(PdfName.VERTICES, new PdfArray(vertices));
/*  509 */     return annot;
/*      */   }
/*      */ 
/*      */   public void setDefaultAppearanceString(PdfContentByte cb) {
/*  513 */     byte[] b = cb.getInternalBuffer().toByteArray();
/*  514 */     int len = b.length;
/*  515 */     for (int k = 0; k < len; k++) {
/*  516 */       if (b[k] == 10)
/*  517 */         b[k] = 32;
/*      */     }
/*  519 */     put(PdfName.DA, new PdfString(b));
/*      */   }
/*      */ 
/*      */   public void setFlags(int flags) {
/*  523 */     if (flags == 0)
/*  524 */       remove(PdfName.F);
/*      */     else
/*  526 */       put(PdfName.F, new PdfNumber(flags));
/*      */   }
/*      */ 
/*      */   public void setBorder(PdfBorderArray border) {
/*  530 */     put(PdfName.BORDER, border);
/*      */   }
/*      */ 
/*      */   public void setBorderStyle(PdfBorderDictionary border) {
/*  534 */     put(PdfName.BS, border);
/*      */   }
/*      */ 
/*      */   public void setHighlighting(PdfName highlight)
/*      */   {
/*  544 */     if (highlight.equals(HIGHLIGHT_INVERT))
/*  545 */       remove(PdfName.H);
/*      */     else
/*  547 */       put(PdfName.H, highlight);
/*      */   }
/*      */ 
/*      */   public void setAppearance(PdfName ap, PdfTemplate template) {
/*  551 */     PdfDictionary dic = (PdfDictionary)get(PdfName.AP);
/*  552 */     if (dic == null)
/*  553 */       dic = new PdfDictionary();
/*  554 */     dic.put(ap, template.getIndirectReference());
/*  555 */     put(PdfName.AP, dic);
/*  556 */     if (!this.form)
/*  557 */       return;
/*  558 */     if (this.templates == null)
/*  559 */       this.templates = new HashSet();
/*  560 */     this.templates.add(template);
/*      */   }
/*      */ 
/*      */   public void setAppearance(PdfName ap, String state, PdfTemplate template) {
/*  564 */     PdfDictionary dicAp = (PdfDictionary)get(PdfName.AP);
/*  565 */     if (dicAp == null) {
/*  566 */       dicAp = new PdfDictionary();
/*      */     }
/*      */ 
/*  569 */     PdfObject obj = dicAp.get(ap);
/*      */     PdfDictionary dic;
/*      */     PdfDictionary dic;
/*  570 */     if ((obj != null) && (obj.isDictionary()))
/*  571 */       dic = (PdfDictionary)obj;
/*      */     else
/*  573 */       dic = new PdfDictionary();
/*  574 */     dic.put(new PdfName(state), template.getIndirectReference());
/*  575 */     dicAp.put(ap, dic);
/*  576 */     put(PdfName.AP, dicAp);
/*  577 */     if (!this.form)
/*  578 */       return;
/*  579 */     if (this.templates == null)
/*  580 */       this.templates = new HashSet();
/*  581 */     this.templates.add(template);
/*      */   }
/*      */ 
/*      */   public void setAppearanceState(String state) {
/*  585 */     if (state == null) {
/*  586 */       remove(PdfName.AS);
/*  587 */       return;
/*      */     }
/*  589 */     put(PdfName.AS, new PdfName(state));
/*      */   }
/*      */ 
/*      */   public void setColor(BaseColor color) {
/*  593 */     put(PdfName.C, new PdfColor(color));
/*      */   }
/*      */ 
/*      */   public void setTitle(String title) {
/*  597 */     if (title == null) {
/*  598 */       remove(PdfName.T);
/*  599 */       return;
/*      */     }
/*  601 */     put(PdfName.T, new PdfString(title, "UnicodeBig"));
/*      */   }
/*      */ 
/*      */   public void setPopup(PdfAnnotation popup) {
/*  605 */     put(PdfName.POPUP, popup.getIndirectReference());
/*  606 */     popup.put(PdfName.PARENT, getIndirectReference());
/*      */   }
/*      */ 
/*      */   public void setAction(PdfAction action) {
/*  610 */     put(PdfName.A, action);
/*      */   }
/*      */ 
/*      */   public void setAdditionalActions(PdfName key, PdfAction action)
/*      */   {
/*  615 */     PdfObject obj = get(PdfName.AA);
/*      */     PdfDictionary dic;
/*      */     PdfDictionary dic;
/*  616 */     if ((obj != null) && (obj.isDictionary()))
/*  617 */       dic = (PdfDictionary)obj;
/*      */     else
/*  619 */       dic = new PdfDictionary();
/*  620 */     dic.put(key, action);
/*  621 */     put(PdfName.AA, dic);
/*      */   }
/*      */ 
/*      */   public boolean isUsed()
/*      */   {
/*  628 */     return this.used;
/*      */   }
/*      */ 
/*      */   public void setUsed()
/*      */   {
/*  634 */     this.used = true;
/*      */   }
/*      */ 
/*      */   public HashSet<PdfTemplate> getTemplates() {
/*  638 */     return this.templates;
/*      */   }
/*      */ 
/*      */   public boolean isForm()
/*      */   {
/*  645 */     return this.form;
/*      */   }
/*      */ 
/*      */   public boolean isAnnotation()
/*      */   {
/*  652 */     return this.annotation;
/*      */   }
/*      */ 
/*      */   public void setPage(int page) {
/*  656 */     put(PdfName.P, this.writer.getPageReference(page));
/*      */   }
/*      */ 
/*      */   public void setPage() {
/*  660 */     put(PdfName.P, this.writer.getCurrentPage());
/*      */   }
/*      */ 
/*      */   public int getPlaceInPage()
/*      */   {
/*  667 */     return this.placeInPage;
/*      */   }
/*      */ 
/*      */   public void setPlaceInPage(int placeInPage)
/*      */   {
/*  676 */     this.placeInPage = placeInPage;
/*      */   }
/*      */ 
/*      */   public void setRotate(int v) {
/*  680 */     put(PdfName.ROTATE, new PdfNumber(v));
/*      */   }
/*      */ 
/*      */   PdfDictionary getMK() {
/*  684 */     PdfDictionary mk = (PdfDictionary)get(PdfName.MK);
/*  685 */     if (mk == null) {
/*  686 */       mk = new PdfDictionary();
/*  687 */       put(PdfName.MK, mk);
/*      */     }
/*  689 */     return mk;
/*      */   }
/*      */ 
/*      */   public void setMKRotation(int rotation) {
/*  693 */     getMK().put(PdfName.R, new PdfNumber(rotation));
/*      */   }
/*      */ 
/*      */   public static PdfArray getMKColor(BaseColor color) {
/*  697 */     PdfArray array = new PdfArray();
/*  698 */     int type = ExtendedColor.getType(color);
/*  699 */     switch (type) {
/*      */     case 1:
/*  701 */       array.add(new PdfNumber(((GrayColor)color).getGray()));
/*  702 */       break;
/*      */     case 2:
/*  705 */       CMYKColor cmyk = (CMYKColor)color;
/*  706 */       array.add(new PdfNumber(cmyk.getCyan()));
/*  707 */       array.add(new PdfNumber(cmyk.getMagenta()));
/*  708 */       array.add(new PdfNumber(cmyk.getYellow()));
/*  709 */       array.add(new PdfNumber(cmyk.getBlack()));
/*  710 */       break;
/*      */     case 3:
/*      */     case 4:
/*      */     case 5:
/*  715 */       throw new RuntimeException(MessageLocalization.getComposedMessage("separations.patterns.and.shadings.are.not.allowed.in.mk.dictionary", new Object[0]));
/*      */     default:
/*  717 */       array.add(new PdfNumber(color.getRed() / 255.0F));
/*  718 */       array.add(new PdfNumber(color.getGreen() / 255.0F));
/*  719 */       array.add(new PdfNumber(color.getBlue() / 255.0F));
/*      */     }
/*  721 */     return array;
/*      */   }
/*      */ 
/*      */   public void setMKBorderColor(BaseColor color) {
/*  725 */     if (color == null)
/*  726 */       getMK().remove(PdfName.BC);
/*      */     else
/*  728 */       getMK().put(PdfName.BC, getMKColor(color));
/*      */   }
/*      */ 
/*      */   public void setMKBackgroundColor(BaseColor color) {
/*  732 */     if (color == null)
/*  733 */       getMK().remove(PdfName.BG);
/*      */     else
/*  735 */       getMK().put(PdfName.BG, getMKColor(color));
/*      */   }
/*      */ 
/*      */   public void setMKNormalCaption(String caption) {
/*  739 */     getMK().put(PdfName.CA, new PdfString(caption, "UnicodeBig"));
/*      */   }
/*      */ 
/*      */   public void setMKRolloverCaption(String caption) {
/*  743 */     getMK().put(PdfName.RC, new PdfString(caption, "UnicodeBig"));
/*      */   }
/*      */ 
/*      */   public void setMKAlternateCaption(String caption) {
/*  747 */     getMK().put(PdfName.AC, new PdfString(caption, "UnicodeBig"));
/*      */   }
/*      */ 
/*      */   public void setMKNormalIcon(PdfTemplate template) {
/*  751 */     getMK().put(PdfName.I, template.getIndirectReference());
/*      */   }
/*      */ 
/*      */   public void setMKRolloverIcon(PdfTemplate template) {
/*  755 */     getMK().put(PdfName.RI, template.getIndirectReference());
/*      */   }
/*      */ 
/*      */   public void setMKAlternateIcon(PdfTemplate template) {
/*  759 */     getMK().put(PdfName.IX, template.getIndirectReference());
/*      */   }
/*      */ 
/*      */   public void setMKIconFit(PdfName scale, PdfName scalingType, float leftoverLeft, float leftoverBottom, boolean fitInBounds) {
/*  763 */     PdfDictionary dic = new PdfDictionary();
/*  764 */     if (!scale.equals(PdfName.A))
/*  765 */       dic.put(PdfName.SW, scale);
/*  766 */     if (!scalingType.equals(PdfName.P))
/*  767 */       dic.put(PdfName.S, scalingType);
/*  768 */     if ((leftoverLeft != 0.5F) || (leftoverBottom != 0.5F)) {
/*  769 */       PdfArray array = new PdfArray(new PdfNumber(leftoverLeft));
/*  770 */       array.add(new PdfNumber(leftoverBottom));
/*  771 */       dic.put(PdfName.A, array);
/*      */     }
/*  773 */     if (fitInBounds)
/*  774 */       dic.put(PdfName.FB, PdfBoolean.PDFTRUE);
/*  775 */     getMK().put(PdfName.IF, dic);
/*      */   }
/*      */ 
/*      */   public void setMKTextPosition(int tp) {
/*  779 */     getMK().put(PdfName.TP, new PdfNumber(tp));
/*      */   }
/*      */ 
/*      */   public void setLayer(PdfOCG layer)
/*      */   {
/*  787 */     put(PdfName.OC, layer.getRef());
/*      */   }
/*      */ 
/*      */   public void setName(String name)
/*      */   {
/*  796 */     put(PdfName.NM, new PdfString(name));
/*      */   }
/*      */ 
/*      */   public void applyCTM(AffineTransform ctm)
/*      */   {
/*  801 */     PdfArray origRect = getAsArray(PdfName.RECT);
/*  802 */     if (origRect != null)
/*      */     {
/*      */       PdfRectangle rect;
/*      */       PdfRectangle rect;
/*  804 */       if (origRect.size() == 4) {
/*  805 */         rect = new PdfRectangle(origRect.getAsNumber(0).floatValue(), origRect.getAsNumber(1).floatValue(), origRect.getAsNumber(2).floatValue(), origRect.getAsNumber(3).floatValue());
/*      */       }
/*      */       else {
/*  808 */         rect = new PdfRectangle(origRect.getAsNumber(0).floatValue(), origRect.getAsNumber(1).floatValue());
/*      */       }
/*  810 */       put(PdfName.RECT, rect.transform(ctm));
/*      */     }
/*      */   }
/*      */ 
/*      */   public void toPdf(PdfWriter writer, OutputStream os)
/*      */     throws IOException
/*      */   {
/*  999 */     PdfWriter.checkPdfIsoConformance(writer, 13, this);
/* 1000 */     super.toPdf(writer, os);
/*      */   }
/*      */ 
/*      */   public PdfObject getAccessibleAttribute(PdfName key) {
/* 1004 */     if (this.accessibleAttributes != null) {
/* 1005 */       return (PdfObject)this.accessibleAttributes.get(key);
/*      */     }
/* 1007 */     return null;
/*      */   }
/*      */ 
/*      */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/* 1011 */     if (this.accessibleAttributes == null)
/* 1012 */       this.accessibleAttributes = new HashMap();
/* 1013 */     this.accessibleAttributes.put(key, value);
/*      */   }
/*      */ 
/*      */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/* 1017 */     return this.accessibleAttributes;
/*      */   }
/*      */ 
/*      */   public PdfName getRole() {
/* 1021 */     return this.role;
/*      */   }
/*      */ 
/*      */   public void setRole(PdfName role) {
/* 1025 */     this.role = role;
/*      */   }
/*      */ 
/*      */   public AccessibleElementId getId() {
/* 1029 */     if (this.id == null)
/* 1030 */       this.id = new AccessibleElementId();
/* 1031 */     return this.id;
/*      */   }
/*      */ 
/*      */   public void setId(AccessibleElementId id) {
/* 1035 */     this.id = id;
/*      */   }
/*      */ 
/*      */   public boolean isInline() {
/* 1039 */     return false;
/*      */   }
/*      */ 
/*      */   public static class PdfImportedLink
/*      */   {
/*      */     float llx;
/*      */     float lly;
/*      */     float urx;
/*      */     float ury;
/*  863 */     HashMap<PdfName, PdfObject> parameters = new HashMap();
/*  864 */     PdfArray destination = null;
/*  865 */     int newPage = 0;
/*      */     PdfArray rect;
/*      */ 
/*      */     PdfImportedLink(PdfDictionary annotation)
/*      */     {
/*  869 */       this.parameters.putAll(annotation.hashMap);
/*      */       try {
/*  871 */         this.destination = ((PdfArray)this.parameters.remove(PdfName.DEST));
/*      */       } catch (ClassCastException ex) {
/*  873 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("you.have.to.consolidate.the.named.destinations.of.your.reader", new Object[0]));
/*      */       }
/*  875 */       if (this.destination != null) {
/*  876 */         this.destination = new PdfArray(this.destination);
/*      */       }
/*  878 */       PdfArray rc = (PdfArray)this.parameters.remove(PdfName.RECT);
/*  879 */       this.llx = rc.getAsNumber(0).floatValue();
/*  880 */       this.lly = rc.getAsNumber(1).floatValue();
/*  881 */       this.urx = rc.getAsNumber(2).floatValue();
/*  882 */       this.ury = rc.getAsNumber(3).floatValue();
/*      */ 
/*  884 */       this.rect = new PdfArray(rc);
/*      */     }
/*      */ 
/*      */     public Map<PdfName, PdfObject> getParameters() {
/*  888 */       return new HashMap(this.parameters);
/*      */     }
/*      */ 
/*      */     public PdfArray getRect() {
/*  892 */       return new PdfArray(this.rect);
/*      */     }
/*      */ 
/*      */     public boolean isInternal() {
/*  896 */       return this.destination != null;
/*      */     }
/*      */ 
/*      */     public int getDestinationPage() {
/*  900 */       if (!isInternal()) return 0;
/*      */ 
/*  904 */       PdfIndirectReference ref = this.destination.getAsIndirectObject(0);
/*      */ 
/*  906 */       PRIndirectReference pr = (PRIndirectReference)ref;
/*  907 */       PdfReader r = pr.getReader();
/*  908 */       for (int i = 1; i <= r.getNumberOfPages(); i++) {
/*  909 */         PRIndirectReference pp = r.getPageOrigRef(i);
/*  910 */         if ((pp.getGeneration() == pr.getGeneration()) && (pp.getNumber() == pr.getNumber())) return i;
/*      */       }
/*  912 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("page.not.found", new Object[0]));
/*      */     }
/*      */ 
/*      */     public void setDestinationPage(int newPage) {
/*  916 */       if (!isInternal()) throw new IllegalArgumentException(MessageLocalization.getComposedMessage("cannot.change.destination.of.external.link", new Object[0]));
/*  917 */       this.newPage = newPage;
/*      */     }
/*      */ 
/*      */     public void transformDestination(float a, float b, float c, float d, float e, float f) {
/*  921 */       if (!isInternal()) throw new IllegalArgumentException(MessageLocalization.getComposedMessage("cannot.change.destination.of.external.link", new Object[0]));
/*  922 */       if (this.destination.getAsName(1).equals(PdfName.XYZ)) {
/*  923 */         float x = this.destination.getAsNumber(2).floatValue();
/*  924 */         float y = this.destination.getAsNumber(3).floatValue();
/*  925 */         float xx = x * a + y * c + e;
/*  926 */         float yy = x * b + y * d + f;
/*  927 */         this.destination.set(2, new PdfNumber(xx));
/*  928 */         this.destination.set(3, new PdfNumber(yy));
/*      */       }
/*      */     }
/*      */ 
/*      */     public void transformRect(float a, float b, float c, float d, float e, float f) {
/*  933 */       float x = this.llx * a + this.lly * c + e;
/*  934 */       float y = this.llx * b + this.lly * d + f;
/*  935 */       this.llx = x;
/*  936 */       this.lly = y;
/*  937 */       x = this.urx * a + this.ury * c + e;
/*  938 */       y = this.urx * b + this.ury * d + f;
/*  939 */       this.urx = x;
/*  940 */       this.ury = y;
/*      */     }
/*      */ 
/*      */     public PdfAnnotation createAnnotation(PdfWriter writer) {
/*  944 */       PdfAnnotation annotation = new PdfAnnotation(writer, new Rectangle(this.llx, this.lly, this.urx, this.ury));
/*  945 */       if (this.newPage != 0) {
/*  946 */         PdfIndirectReference ref = writer.getPageReference(this.newPage);
/*  947 */         this.destination.set(0, ref);
/*      */       }
/*  949 */       if (this.destination != null) annotation.put(PdfName.DEST, this.destination);
/*  950 */       annotation.hashMap.putAll(this.parameters);
/*  951 */       return annotation;
/*      */     }
/*      */ 
/*      */     public String toString()
/*      */     {
/*  961 */       StringBuffer buf = new StringBuffer("Imported link: location [");
/*  962 */       buf.append(this.llx);
/*  963 */       buf.append(' ');
/*  964 */       buf.append(this.lly);
/*  965 */       buf.append(' ');
/*  966 */       buf.append(this.urx);
/*  967 */       buf.append(' ');
/*  968 */       buf.append(this.ury);
/*  969 */       buf.append("] destination ");
/*  970 */       buf.append(this.destination);
/*  971 */       buf.append(" parameters ");
/*  972 */       buf.append(this.parameters);
/*  973 */       if (this.parameters != null) {
/*  974 */         appendDictionary(buf, this.parameters);
/*      */       }
/*      */ 
/*  977 */       return buf.toString();
/*      */     }
/*      */ 
/*      */     private void appendDictionary(StringBuffer buf, HashMap<PdfName, PdfObject> dict) {
/*  981 */       buf.append(" <<");
/*  982 */       for (Map.Entry entry : dict.entrySet()) {
/*  983 */         buf.append(entry.getKey());
/*  984 */         buf.append(":");
/*  985 */         if ((entry.getValue() instanceof PdfDictionary))
/*  986 */           appendDictionary(buf, ((PdfDictionary)entry.getValue()).hashMap);
/*      */         else
/*  988 */           buf.append(entry.getValue());
/*  989 */         buf.append(" ");
/*      */       }
/*      */ 
/*  992 */       buf.append(">> ");
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfAnnotation
 * JD-Core Version:    0.6.2
 */