/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.Chunk;
/*      */ import com.itextpdf.text.DocumentException;
/*      */ import com.itextpdf.text.Element;
/*      */ import com.itextpdf.text.ExceptionConverter;
/*      */ import com.itextpdf.text.Font;
/*      */ import com.itextpdf.text.Image;
/*      */ import com.itextpdf.text.ListBody;
/*      */ import com.itextpdf.text.ListItem;
/*      */ import com.itextpdf.text.ListLabel;
/*      */ import com.itextpdf.text.Paragraph;
/*      */ import com.itextpdf.text.Phrase;
/*      */ import com.itextpdf.text.Rectangle;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.log.Logger;
/*      */ import com.itextpdf.text.log.LoggerFactory;
/*      */ import com.itextpdf.text.pdf.draw.DrawInterface;
/*      */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.LinkedList;
/*      */ import java.util.Stack;
/*      */ 
/*      */ public class ColumnText
/*      */ {
/*   88 */   private final Logger LOGGER = LoggerFactory.getLogger(ColumnText.class);
/*      */   public static final int AR_NOVOWEL = 1;
/*      */   public static final int AR_COMPOSEDTASHKEEL = 4;
/*      */   public static final int AR_LIG = 8;
/*      */   public static final int DIGITS_EN2AN = 32;
/*      */   public static final int DIGITS_AN2EN = 64;
/*      */   public static final int DIGITS_EN2AN_INIT_LR = 96;
/*      */   public static final int DIGITS_EN2AN_INIT_AL = 128;
/*      */   public static final int DIGIT_TYPE_AN = 0;
/*      */   public static final int DIGIT_TYPE_AN_EXTENDED = 256;
/*  138 */   protected int runDirection = 0;
/*      */   public static final float GLOBAL_SPACE_CHAR_RATIO = 0.0F;
/*      */   public static final int START_COLUMN = 0;
/*      */   public static final int NO_MORE_TEXT = 1;
/*      */   public static final int NO_MORE_COLUMN = 2;
/*      */   protected static final int LINE_STATUS_OK = 0;
/*      */   protected static final int LINE_STATUS_OFFLIMITS = 1;
/*      */   protected static final int LINE_STATUS_NOLINE = 2;
/*      */   protected float maxY;
/*      */   protected float minY;
/*      */   protected float leftX;
/*      */   protected float rightX;
/*  172 */   protected int alignment = 0;
/*      */   protected ArrayList<float[]> leftWall;
/*      */   protected ArrayList<float[]> rightWall;
/*      */   protected BidiLine bidiLine;
/*      */   protected float yLine;
/*      */   protected float lastX;
/*  194 */   protected float currentLeading = 16.0F;
/*      */ 
/*  197 */   protected float fixedLeading = 16.0F;
/*      */ 
/*  200 */   protected float multipliedLeading = 0.0F;
/*      */   protected PdfContentByte canvas;
/*      */   protected PdfContentByte[] canvases;
/*      */   protected int lineStatus;
/*  211 */   protected float indent = 0.0F;
/*      */ 
/*  214 */   protected float followingIndent = 0.0F;
/*      */ 
/*  217 */   protected float rightIndent = 0.0F;
/*      */ 
/*  220 */   protected float extraParagraphSpace = 0.0F;
/*      */ 
/*  223 */   protected float rectangularWidth = -1.0F;
/*      */ 
/*  225 */   protected boolean rectangularMode = false;
/*      */ 
/*  227 */   private float spaceCharRatio = 0.0F;
/*      */ 
/*  229 */   private boolean lastWasNewline = true;
/*  230 */   private boolean repeatFirstLineIndent = true;
/*      */   private int linesWritten;
/*      */   private float firstLineY;
/*  236 */   private boolean firstLineYDone = false;
/*      */ 
/*  239 */   private int arabicOptions = 0;
/*      */   protected float descender;
/*  243 */   protected boolean composite = false;
/*      */   protected ColumnText compositeColumn;
/*      */   protected LinkedList<Element> compositeElements;
/*  249 */   protected int listIdx = 0;
/*      */ 
/*  254 */   protected int rowIdx = 0;
/*      */ 
/*  260 */   private int splittedRow = -1;
/*      */   protected Phrase waitPhrase;
/*  265 */   private boolean useAscender = false;
/*      */   private float filledWidth;
/*  270 */   private boolean adjustFirstLine = true;
/*      */ 
/*  275 */   private boolean inheritGraphicState = false;
/*      */ 
/*      */   public ColumnText(PdfContentByte canvas)
/*      */   {
/*  284 */     this.canvas = canvas;
/*      */   }
/*      */ 
/*      */   public static ColumnText duplicate(ColumnText org)
/*      */   {
/*  294 */     ColumnText ct = new ColumnText(null);
/*  295 */     ct.setACopy(org);
/*  296 */     return ct;
/*      */   }
/*      */ 
/*      */   public ColumnText setACopy(ColumnText org)
/*      */   {
/*  306 */     setSimpleVars(org);
/*  307 */     if (org.bidiLine != null)
/*  308 */       this.bidiLine = new BidiLine(org.bidiLine);
/*  309 */     return this;
/*      */   }
/*      */ 
/*      */   protected void setSimpleVars(ColumnText org) {
/*  313 */     this.maxY = org.maxY;
/*  314 */     this.minY = org.minY;
/*  315 */     this.alignment = org.alignment;
/*  316 */     this.leftWall = null;
/*  317 */     if (org.leftWall != null)
/*  318 */       this.leftWall = new ArrayList(org.leftWall);
/*  319 */     this.rightWall = null;
/*  320 */     if (org.rightWall != null)
/*  321 */       this.rightWall = new ArrayList(org.rightWall);
/*  322 */     this.yLine = org.yLine;
/*  323 */     this.currentLeading = org.currentLeading;
/*  324 */     this.fixedLeading = org.fixedLeading;
/*  325 */     this.multipliedLeading = org.multipliedLeading;
/*  326 */     this.canvas = org.canvas;
/*  327 */     this.canvases = org.canvases;
/*  328 */     this.lineStatus = org.lineStatus;
/*  329 */     this.indent = org.indent;
/*  330 */     this.followingIndent = org.followingIndent;
/*  331 */     this.rightIndent = org.rightIndent;
/*  332 */     this.extraParagraphSpace = org.extraParagraphSpace;
/*  333 */     this.rectangularWidth = org.rectangularWidth;
/*  334 */     this.rectangularMode = org.rectangularMode;
/*  335 */     this.spaceCharRatio = org.spaceCharRatio;
/*  336 */     this.lastWasNewline = org.lastWasNewline;
/*  337 */     this.repeatFirstLineIndent = org.repeatFirstLineIndent;
/*  338 */     this.linesWritten = org.linesWritten;
/*  339 */     this.arabicOptions = org.arabicOptions;
/*  340 */     this.runDirection = org.runDirection;
/*  341 */     this.descender = org.descender;
/*  342 */     this.composite = org.composite;
/*  343 */     this.splittedRow = org.splittedRow;
/*  344 */     if (org.composite) {
/*  345 */       this.compositeElements = new LinkedList();
/*  346 */       for (Element element : org.compositeElements) {
/*  347 */         if ((element instanceof PdfPTable))
/*  348 */           this.compositeElements.add(new PdfPTable((PdfPTable)element));
/*      */         else {
/*  350 */           this.compositeElements.add(element);
/*      */         }
/*      */       }
/*  353 */       if (org.compositeColumn != null)
/*  354 */         this.compositeColumn = duplicate(org.compositeColumn);
/*      */     }
/*  356 */     this.listIdx = org.listIdx;
/*  357 */     this.rowIdx = org.rowIdx;
/*  358 */     this.firstLineY = org.firstLineY;
/*  359 */     this.leftX = org.leftX;
/*  360 */     this.rightX = org.rightX;
/*  361 */     this.firstLineYDone = org.firstLineYDone;
/*  362 */     this.waitPhrase = org.waitPhrase;
/*  363 */     this.useAscender = org.useAscender;
/*  364 */     this.filledWidth = org.filledWidth;
/*  365 */     this.adjustFirstLine = org.adjustFirstLine;
/*  366 */     this.inheritGraphicState = org.inheritGraphicState;
/*      */   }
/*      */ 
/*      */   private void addWaitingPhrase() {
/*  370 */     if ((this.bidiLine == null) && (this.waitPhrase != null)) {
/*  371 */       this.bidiLine = new BidiLine();
/*  372 */       for (Chunk c : this.waitPhrase.getChunks()) {
/*  373 */         this.bidiLine.addChunk(new PdfChunk(c, null, this.waitPhrase.getTabSettings()));
/*      */       }
/*  375 */       this.waitPhrase = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addText(Phrase phrase)
/*      */   {
/*  386 */     if ((phrase == null) || (this.composite))
/*  387 */       return;
/*  388 */     addWaitingPhrase();
/*  389 */     if (this.bidiLine == null) {
/*  390 */       this.waitPhrase = phrase;
/*  391 */       return;
/*      */     }
/*  393 */     for (Object element : phrase.getChunks())
/*  394 */       this.bidiLine.addChunk(new PdfChunk((Chunk)element, null, phrase.getTabSettings()));
/*      */   }
/*      */ 
/*      */   public void setText(Phrase phrase)
/*      */   {
/*  405 */     this.bidiLine = null;
/*  406 */     this.composite = false;
/*  407 */     this.compositeColumn = null;
/*  408 */     this.compositeElements = null;
/*  409 */     this.listIdx = 0;
/*  410 */     this.rowIdx = 0;
/*  411 */     this.splittedRow = -1;
/*  412 */     this.waitPhrase = phrase;
/*      */   }
/*      */ 
/*      */   public void addText(Chunk chunk)
/*      */   {
/*  422 */     if ((chunk == null) || (this.composite))
/*  423 */       return;
/*  424 */     addText(new Phrase(chunk));
/*      */   }
/*      */ 
/*      */   public void addElement(Element element)
/*      */   {
/*  438 */     if (element == null)
/*  439 */       return;
/*  440 */     if ((element instanceof Image)) {
/*  441 */       Image img = (Image)element;
/*  442 */       PdfPTable t = new PdfPTable(1);
/*  443 */       float w = img.getWidthPercentage();
/*  444 */       if (w == 0.0F) {
/*  445 */         t.setTotalWidth(img.getScaledWidth());
/*  446 */         t.setLockedWidth(true);
/*      */       }
/*      */       else {
/*  449 */         t.setWidthPercentage(w);
/*  450 */       }t.setSpacingAfter(img.getSpacingAfter());
/*  451 */       t.setSpacingBefore(img.getSpacingBefore());
/*  452 */       switch (img.getAlignment()) {
/*      */       case 0:
/*  454 */         t.setHorizontalAlignment(0);
/*  455 */         break;
/*      */       case 2:
/*  457 */         t.setHorizontalAlignment(2);
/*  458 */         break;
/*      */       default:
/*  460 */         t.setHorizontalAlignment(1);
/*      */       }
/*      */ 
/*  463 */       PdfPCell c = new PdfPCell(img, true);
/*  464 */       c.setPadding(0.0F);
/*  465 */       c.setBorder(img.getBorder());
/*  466 */       c.setBorderColor(img.getBorderColor());
/*  467 */       c.setBorderWidth(img.getBorderWidth());
/*  468 */       c.setBackgroundColor(img.getBackgroundColor());
/*  469 */       t.addCell(c);
/*  470 */       element = t;
/*      */     }
/*  472 */     if (element.type() == 10) {
/*  473 */       element = new Paragraph((Chunk)element);
/*      */     }
/*  475 */     else if (element.type() == 11) {
/*  476 */       element = new Paragraph((Phrase)element);
/*      */     }
/*  478 */     if ((element.type() != 12) && (element.type() != 14) && (element.type() != 23) && (element.type() != 55) && (element.type() != 37))
/*  479 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("element.not.allowed", new Object[0]));
/*  480 */     if (!this.composite) {
/*  481 */       this.composite = true;
/*  482 */       this.compositeElements = new LinkedList();
/*  483 */       this.bidiLine = null;
/*  484 */       this.waitPhrase = null;
/*      */     }
/*  486 */     if (element.type() == 12) {
/*  487 */       Paragraph p = (Paragraph)element;
/*  488 */       this.compositeElements.addAll(p.breakUp());
/*  489 */       return;
/*      */     }
/*  491 */     this.compositeElements.add(element);
/*      */   }
/*      */ 
/*      */   public static boolean isAllowedElement(Element element) {
/*  495 */     int type = element.type();
/*  496 */     if ((type == 10) || (type == 11) || (type == 37) || (type == 12) || (type == 14) || (type == 55) || (type == 23))
/*      */     {
/*  498 */       return true;
/*  499 */     }if ((element instanceof Image)) return true;
/*  500 */     return false;
/*      */   }
/*      */ 
/*      */   protected ArrayList<float[]> convertColumn(float[] cLine)
/*      */   {
/*  514 */     if (cLine.length < 4)
/*  515 */       throw new RuntimeException(MessageLocalization.getComposedMessage("no.valid.column.line.found", new Object[0]));
/*  516 */     ArrayList cc = new ArrayList();
/*  517 */     for (int k = 0; k < cLine.length - 2; k += 2) {
/*  518 */       float x1 = cLine[k];
/*  519 */       float y1 = cLine[(k + 1)];
/*  520 */       float x2 = cLine[(k + 2)];
/*  521 */       float y2 = cLine[(k + 3)];
/*  522 */       if (y1 != y2)
/*      */       {
/*  525 */         float a = (x1 - x2) / (y1 - y2);
/*  526 */         float b = x1 - a * y1;
/*  527 */         float[] r = new float[4];
/*  528 */         r[0] = Math.min(y1, y2);
/*  529 */         r[1] = Math.max(y1, y2);
/*  530 */         r[2] = a;
/*  531 */         r[3] = b;
/*  532 */         cc.add(r);
/*  533 */         this.maxY = Math.max(this.maxY, r[1]);
/*  534 */         this.minY = Math.min(this.minY, r[0]);
/*      */       }
/*      */     }
/*  536 */     if (cc.isEmpty())
/*  537 */       throw new RuntimeException(MessageLocalization.getComposedMessage("no.valid.column.line.found", new Object[0]));
/*  538 */     return cc;
/*      */   }
/*      */ 
/*      */   protected float findLimitsPoint(ArrayList<float[]> wall)
/*      */   {
/*  549 */     this.lineStatus = 0;
/*  550 */     if ((this.yLine < this.minY) || (this.yLine > this.maxY)) {
/*  551 */       this.lineStatus = 1;
/*  552 */       return 0.0F;
/*      */     }
/*  554 */     for (int k = 0; k < wall.size(); k++) {
/*  555 */       float[] r = (float[])wall.get(k);
/*  556 */       if ((this.yLine >= r[0]) && (this.yLine <= r[1]))
/*      */       {
/*  558 */         return r[2] * this.yLine + r[3];
/*      */       }
/*      */     }
/*  560 */     this.lineStatus = 2;
/*  561 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   protected float[] findLimitsOneLine()
/*      */   {
/*  571 */     float x1 = findLimitsPoint(this.leftWall);
/*  572 */     if ((this.lineStatus == 1) || (this.lineStatus == 2))
/*  573 */       return null;
/*  574 */     float x2 = findLimitsPoint(this.rightWall);
/*  575 */     if (this.lineStatus == 2)
/*  576 */       return null;
/*  577 */     return new float[] { x1, x2 };
/*      */   }
/*      */ 
/*      */   protected float[] findLimitsTwoLines()
/*      */   {
/*  588 */     boolean repeat = false;
/*      */     float[] x1;
/*      */     float[] x2;
/*      */     do
/*      */       while (true)
/*      */       {
/*  590 */         if ((repeat) && (this.currentLeading == 0.0F))
/*  591 */           return null;
/*  592 */         repeat = true;
/*  593 */         x1 = findLimitsOneLine();
/*  594 */         if (this.lineStatus == 1)
/*  595 */           return null;
/*  596 */         this.yLine -= this.currentLeading;
/*  597 */         if (this.lineStatus != 2)
/*      */         {
/*  600 */           x2 = findLimitsOneLine();
/*  601 */           if (this.lineStatus == 1)
/*  602 */             return null;
/*  603 */           if (this.lineStatus != 2) break;
/*  604 */           this.yLine -= this.currentLeading;
/*      */         }
/*      */       }
/*  607 */     while ((x1[0] >= x2[1]) || (x2[0] >= x1[1]));
/*      */ 
/*  609 */     return new float[] { x1[0], x1[1], x2[0], x2[1] };
/*      */   }
/*      */ 
/*      */   public void setColumns(float[] leftLine, float[] rightLine)
/*      */   {
/*  622 */     this.maxY = -1.0E+021F;
/*  623 */     this.minY = 1.0E+021F;
/*  624 */     setYLine(Math.max(leftLine[1], leftLine[(leftLine.length - 1)]));
/*  625 */     this.rightWall = convertColumn(rightLine);
/*  626 */     this.leftWall = convertColumn(leftLine);
/*  627 */     this.rectangularWidth = -1.0F;
/*  628 */     this.rectangularMode = false;
/*      */   }
/*      */ 
/*      */   public void setSimpleColumn(Phrase phrase, float llx, float lly, float urx, float ury, float leading, int alignment)
/*      */   {
/*  643 */     addText(phrase);
/*  644 */     setSimpleColumn(llx, lly, urx, ury, leading, alignment);
/*      */   }
/*      */ 
/*      */   public void setSimpleColumn(float llx, float lly, float urx, float ury, float leading, int alignment)
/*      */   {
/*  658 */     setLeading(leading);
/*  659 */     this.alignment = alignment;
/*  660 */     setSimpleColumn(llx, lly, urx, ury);
/*      */   }
/*      */ 
/*      */   public void setSimpleColumn(float llx, float lly, float urx, float ury)
/*      */   {
/*  672 */     this.leftX = Math.min(llx, urx);
/*  673 */     this.maxY = Math.max(lly, ury);
/*  674 */     this.minY = Math.min(lly, ury);
/*  675 */     this.rightX = Math.max(llx, urx);
/*  676 */     this.yLine = this.maxY;
/*  677 */     this.rectangularWidth = (this.rightX - this.leftX);
/*  678 */     if (this.rectangularWidth < 0.0F)
/*  679 */       this.rectangularWidth = 0.0F;
/*  680 */     this.rectangularMode = true;
/*      */   }
/*      */ 
/*      */   public void setSimpleColumn(Rectangle rect)
/*      */   {
/*  688 */     setSimpleColumn(rect.getLeft(), rect.getBottom(), rect.getRight(), rect.getTop());
/*      */   }
/*      */ 
/*      */   public void setLeading(float leading)
/*      */   {
/*  697 */     this.fixedLeading = leading;
/*  698 */     this.multipliedLeading = 0.0F;
/*      */   }
/*      */ 
/*      */   public void setLeading(float fixedLeading, float multipliedLeading)
/*      */   {
/*  710 */     this.fixedLeading = fixedLeading;
/*  711 */     this.multipliedLeading = multipliedLeading;
/*      */   }
/*      */ 
/*      */   public float getLeading()
/*      */   {
/*  720 */     return this.fixedLeading;
/*      */   }
/*      */ 
/*      */   public float getMultipliedLeading()
/*      */   {
/*  729 */     return this.multipliedLeading;
/*      */   }
/*      */ 
/*      */   public void setYLine(float yLine)
/*      */   {
/*  738 */     this.yLine = yLine;
/*      */   }
/*      */ 
/*      */   public float getYLine()
/*      */   {
/*  747 */     return this.yLine;
/*      */   }
/*      */ 
/*      */   public int getRowsDrawn()
/*      */   {
/*  754 */     return this.rowIdx;
/*      */   }
/*      */ 
/*      */   public void setAlignment(int alignment)
/*      */   {
/*  763 */     this.alignment = alignment;
/*      */   }
/*      */ 
/*      */   public int getAlignment()
/*      */   {
/*  772 */     return this.alignment;
/*      */   }
/*      */ 
/*      */   public void setIndent(float indent)
/*      */   {
/*  781 */     setIndent(indent, true);
/*      */   }
/*      */ 
/*      */   public void setIndent(float indent, boolean repeatFirstLineIndent)
/*      */   {
/*  790 */     this.indent = indent;
/*  791 */     this.lastWasNewline = true;
/*  792 */     this.repeatFirstLineIndent = repeatFirstLineIndent;
/*      */   }
/*      */ 
/*      */   public float getIndent()
/*      */   {
/*  801 */     return this.indent;
/*      */   }
/*      */ 
/*      */   public void setFollowingIndent(float indent)
/*      */   {
/*  810 */     this.followingIndent = indent;
/*  811 */     this.lastWasNewline = true;
/*      */   }
/*      */ 
/*      */   public float getFollowingIndent()
/*      */   {
/*  820 */     return this.followingIndent;
/*      */   }
/*      */ 
/*      */   public void setRightIndent(float indent)
/*      */   {
/*  829 */     this.rightIndent = indent;
/*  830 */     this.lastWasNewline = true;
/*      */   }
/*      */ 
/*      */   public float getRightIndent()
/*      */   {
/*  839 */     return this.rightIndent;
/*      */   }
/*      */ 
/*      */   public float getCurrentLeading()
/*      */   {
/*  848 */     return this.currentLeading;
/*      */   }
/*      */ 
/*      */   public boolean getInheritGraphicState() {
/*  852 */     return this.inheritGraphicState;
/*      */   }
/*      */ 
/*      */   public void setInheritGraphicState(boolean inheritGraphicState) {
/*  856 */     this.inheritGraphicState = inheritGraphicState;
/*      */   }
/*      */ 
/*      */   public int go()
/*      */     throws DocumentException
/*      */   {
/*  867 */     return go(false);
/*      */   }
/*      */ 
/*      */   public int go(boolean simulate)
/*      */     throws DocumentException
/*      */   {
/*  878 */     return go(simulate, null);
/*      */   }
/*      */ 
/*      */   public int go(boolean simulate, IAccessibleElement elementToGo) throws DocumentException {
/*  882 */     if (this.composite) {
/*  883 */       return goComposite(simulate);
/*      */     }
/*  885 */     ListBody lBody = null;
/*  886 */     if ((isTagged(this.canvas)) && ((elementToGo instanceof ListItem))) {
/*  887 */       lBody = ((ListItem)elementToGo).getListBody();
/*      */     }
/*      */ 
/*  890 */     addWaitingPhrase();
/*  891 */     if (this.bidiLine == null)
/*  892 */       return 1;
/*  893 */     this.descender = 0.0F;
/*  894 */     this.linesWritten = 0;
/*  895 */     this.lastX = 0.0F;
/*  896 */     boolean dirty = false;
/*  897 */     float ratio = this.spaceCharRatio;
/*  898 */     Object[] currentValues = new Object[2];
/*  899 */     PdfFont currentFont = null;
/*  900 */     Float lastBaseFactor = new Float(0.0F);
/*  901 */     currentValues[1] = lastBaseFactor;
/*  902 */     PdfDocument pdf = null;
/*  903 */     PdfContentByte graphics = null;
/*  904 */     PdfContentByte text = null;
/*  905 */     this.firstLineY = (0.0F / 0.0F);
/*  906 */     int localRunDirection = 1;
/*  907 */     if (this.runDirection != 0)
/*  908 */       localRunDirection = this.runDirection;
/*  909 */     if (this.canvas != null) {
/*  910 */       graphics = this.canvas;
/*  911 */       pdf = this.canvas.getPdfDocument();
/*  912 */       if (!isTagged(this.canvas))
/*  913 */         text = this.canvas.getDuplicate(this.inheritGraphicState);
/*      */       else
/*  915 */         text = this.canvas;
/*      */     }
/*  917 */     else if (!simulate) {
/*  918 */       throw new NullPointerException(MessageLocalization.getComposedMessage("columntext.go.with.simulate.eq.eq.false.and.text.eq.eq.null", new Object[0]));
/*  919 */     }if (!simulate) {
/*  920 */       if (ratio == 0.0F)
/*  921 */         ratio = text.getPdfWriter().getSpaceCharRatio();
/*  922 */       else if (ratio < 0.001F)
/*  923 */         ratio = 0.001F;
/*      */     }
/*  925 */     if (!this.rectangularMode) {
/*  926 */       float max = 0.0F;
/*  927 */       for (PdfChunk c : this.bidiLine.chunks) {
/*  928 */         max = Math.max(max, c.height());
/*      */       }
/*  930 */       this.currentLeading = (this.fixedLeading + max * this.multipliedLeading);
/*      */     }
/*  932 */     float firstIndent = 0.0F;
/*      */ 
/*  935 */     int status = 0;
/*      */     while (true) {
/*  937 */       firstIndent = this.lastWasNewline ? this.indent : this.followingIndent;
/*      */       float x1;
/*      */       float x1;
/*      */       PdfLine line;
/*  938 */       if (this.rectangularMode) {
/*  939 */         if (this.rectangularWidth <= firstIndent + this.rightIndent) {
/*  940 */           status = 2;
/*  941 */           if (!this.bidiLine.isEmpty()) break;
/*  942 */           status |= 1; break;
/*      */         }
/*      */ 
/*  945 */         if (this.bidiLine.isEmpty()) {
/*  946 */           status = 1;
/*  947 */           break;
/*      */         }
/*  949 */         PdfLine line = this.bidiLine.processLine(this.leftX, this.rectangularWidth - firstIndent - this.rightIndent, this.alignment, localRunDirection, this.arabicOptions, this.minY, this.yLine, this.descender);
/*  950 */         if (line == null) {
/*  951 */           status = 1;
/*  952 */           break;
/*      */         }
/*  954 */         float[] maxSize = line.getMaxSize(this.fixedLeading, this.multipliedLeading);
/*  955 */         if ((isUseAscender()) && (Float.isNaN(this.firstLineY)))
/*  956 */           this.currentLeading = line.getAscender();
/*      */         else
/*  958 */           this.currentLeading = Math.max(maxSize[0], maxSize[1] - this.descender);
/*  959 */         if ((this.yLine > this.maxY) || (this.yLine - this.currentLeading < this.minY)) {
/*  960 */           status = 2;
/*  961 */           this.bidiLine.restore();
/*  962 */           break;
/*      */         }
/*  964 */         this.yLine -= this.currentLeading;
/*  965 */         if ((!simulate) && (!dirty)) {
/*  966 */           text.beginText();
/*  967 */           dirty = true;
/*      */         }
/*  969 */         if (Float.isNaN(this.firstLineY))
/*  970 */           this.firstLineY = this.yLine;
/*  971 */         updateFilledWidth(this.rectangularWidth - line.widthLeft());
/*  972 */         x1 = this.leftX;
/*      */       }
/*      */       else {
/*  975 */         float yTemp = this.yLine - this.currentLeading;
/*  976 */         float[] xx = findLimitsTwoLines();
/*  977 */         if (xx == null) {
/*  978 */           status = 2;
/*  979 */           if (this.bidiLine.isEmpty())
/*  980 */             status |= 1;
/*  981 */           this.yLine = yTemp;
/*  982 */           break;
/*      */         }
/*  984 */         if (this.bidiLine.isEmpty()) {
/*  985 */           status = 1;
/*  986 */           this.yLine = yTemp;
/*  987 */           break;
/*      */         }
/*  989 */         x1 = Math.max(xx[0], xx[2]);
/*  990 */         float x2 = Math.min(xx[1], xx[3]);
/*  991 */         if (x2 - x1 <= firstIndent + this.rightIndent)
/*      */           continue;
/*  993 */         if ((!simulate) && (!dirty)) {
/*  994 */           text.beginText();
/*  995 */           dirty = true;
/*      */         }
/*  997 */         line = this.bidiLine.processLine(x1, x2 - x1 - firstIndent - this.rightIndent, this.alignment, localRunDirection, this.arabicOptions, this.minY, this.yLine, this.descender);
/*  998 */         if (line == null) {
/*  999 */           status = 1;
/* 1000 */           this.yLine = yTemp;
/* 1001 */           break;
/*      */         }
/*      */       }
/* 1004 */       if ((isTagged(this.canvas)) && ((elementToGo instanceof ListItem)) && 
/* 1005 */         (!Float.isNaN(this.firstLineY)) && (!this.firstLineYDone)) {
/* 1006 */         if (!simulate) {
/* 1007 */           ListLabel lbl = ((ListItem)elementToGo).getListLabel();
/* 1008 */           this.canvas.openMCBlock(lbl);
/* 1009 */           Chunk symbol = new Chunk(((ListItem)elementToGo).getListSymbol());
/* 1010 */           symbol.setRole(null);
/* 1011 */           showTextAligned(this.canvas, 0, new Phrase(symbol), this.leftX + lbl.getIndentation(), this.firstLineY, 0.0F);
/* 1012 */           this.canvas.closeMCBlock(lbl);
/*      */         }
/* 1014 */         this.firstLineYDone = true;
/*      */       }
/*      */ 
/* 1017 */       if (!simulate) {
/* 1018 */         if (lBody != null) {
/* 1019 */           this.canvas.openMCBlock(lBody);
/* 1020 */           lBody = null;
/*      */         }
/* 1022 */         currentValues[0] = currentFont;
/* 1023 */         text.setTextMatrix(x1 + (line.isRTL() ? this.rightIndent : firstIndent) + line.indentLeft(), this.yLine);
/* 1024 */         this.lastX = pdf.writeLineToContent(line, text, graphics, currentValues, ratio);
/* 1025 */         currentFont = (PdfFont)currentValues[0];
/*      */       }
/* 1027 */       this.lastWasNewline = ((this.repeatFirstLineIndent) && (line.isNewlineSplit()));
/* 1028 */       this.yLine -= (line.isNewlineSplit() ? this.extraParagraphSpace : 0.0F);
/* 1029 */       this.linesWritten += 1;
/* 1030 */       this.descender = line.getDescender();
/*      */     }
/* 1032 */     if (dirty) {
/* 1033 */       text.endText();
/* 1034 */       if (this.canvas != text)
/* 1035 */         this.canvas.add(text);
/*      */     }
/* 1037 */     return status;
/*      */   }
/*      */ 
/*      */   public float getExtraParagraphSpace()
/*      */   {
/* 1046 */     return this.extraParagraphSpace;
/*      */   }
/*      */ 
/*      */   public void setExtraParagraphSpace(float extraParagraphSpace)
/*      */   {
/* 1055 */     this.extraParagraphSpace = extraParagraphSpace;
/*      */   }
/*      */ 
/*      */   public void clearChunks()
/*      */   {
/* 1063 */     if (this.bidiLine != null)
/* 1064 */       this.bidiLine.clearChunks();
/*      */   }
/*      */ 
/*      */   public float getSpaceCharRatio()
/*      */   {
/* 1073 */     return this.spaceCharRatio;
/*      */   }
/*      */ 
/*      */   public void setSpaceCharRatio(float spaceCharRatio)
/*      */   {
/* 1087 */     this.spaceCharRatio = spaceCharRatio;
/*      */   }
/*      */ 
/*      */   public void setRunDirection(int runDirection)
/*      */   {
/* 1096 */     if ((runDirection < 0) || (runDirection > 3))
/* 1097 */       throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.run.direction.1", runDirection));
/* 1098 */     this.runDirection = runDirection;
/*      */   }
/*      */ 
/*      */   public int getRunDirection()
/*      */   {
/* 1107 */     return this.runDirection;
/*      */   }
/*      */ 
/*      */   public int getLinesWritten()
/*      */   {
/* 1116 */     return this.linesWritten;
/*      */   }
/*      */ 
/*      */   public float getLastX()
/*      */   {
/* 1125 */     return this.lastX;
/*      */   }
/*      */ 
/*      */   public int getArabicOptions()
/*      */   {
/* 1134 */     return this.arabicOptions;
/*      */   }
/*      */ 
/*      */   public void setArabicOptions(int arabicOptions)
/*      */   {
/* 1144 */     this.arabicOptions = arabicOptions;
/*      */   }
/*      */ 
/*      */   public float getDescender()
/*      */   {
/* 1153 */     return this.descender;
/*      */   }
/*      */ 
/*      */   public static float getWidth(Phrase phrase, int runDirection, int arabicOptions)
/*      */   {
/* 1166 */     ColumnText ct = new ColumnText(null);
/* 1167 */     ct.addText(phrase);
/* 1168 */     ct.addWaitingPhrase();
/* 1169 */     PdfLine line = ct.bidiLine.processLine(0.0F, 20000.0F, 0, runDirection, arabicOptions, 0.0F, 0.0F, 0.0F);
/* 1170 */     if (line == null) {
/* 1171 */       return 0.0F;
/*      */     }
/* 1173 */     return 20000.0F - line.widthLeft();
/*      */   }
/*      */ 
/*      */   public static float getWidth(Phrase phrase)
/*      */   {
/* 1184 */     return getWidth(phrase, 1, 0);
/*      */   }
/*      */ 
/*      */   public static void showTextAligned(PdfContentByte canvas, int alignment, Phrase phrase, float x, float y, float rotation, int runDirection, int arabicOptions)
/*      */   {
/* 1200 */     if ((alignment != 0) && (alignment != 1) && (alignment != 2))
/*      */     {
/* 1202 */       alignment = 0;
/* 1203 */     }canvas.saveState();
/* 1204 */     ColumnText ct = new ColumnText(canvas);
/* 1205 */     float lly = -1.0F;
/* 1206 */     float ury = 2.0F;
/*      */     float llx;
/*      */     float urx;
/* 1209 */     switch (alignment) {
/*      */     case 0:
/* 1211 */       llx = 0.0F;
/* 1212 */       urx = 20000.0F;
/* 1213 */       break;
/*      */     case 2:
/* 1215 */       llx = -20000.0F;
/* 1216 */       urx = 0.0F;
/* 1217 */       break;
/*      */     default:
/* 1219 */       llx = -20000.0F;
/* 1220 */       urx = 20000.0F;
/*      */     }
/*      */ 
/* 1223 */     if (rotation == 0.0F) {
/* 1224 */       llx += x;
/* 1225 */       lly += y;
/* 1226 */       urx += x;
/* 1227 */       ury += y;
/*      */     }
/*      */     else {
/* 1230 */       double alpha = rotation * 3.141592653589793D / 180.0D;
/* 1231 */       float cos = (float)Math.cos(alpha);
/* 1232 */       float sin = (float)Math.sin(alpha);
/* 1233 */       canvas.concatCTM(cos, sin, -sin, cos, x, y);
/*      */     }
/* 1235 */     ct.setSimpleColumn(phrase, llx, lly, urx, ury, 2.0F, alignment);
/* 1236 */     if (runDirection == 3) {
/* 1237 */       if (alignment == 0)
/* 1238 */         alignment = 2;
/* 1239 */       else if (alignment == 2)
/* 1240 */         alignment = 0;
/*      */     }
/* 1242 */     ct.setAlignment(alignment);
/* 1243 */     ct.setArabicOptions(arabicOptions);
/* 1244 */     ct.setRunDirection(runDirection);
/*      */     try {
/* 1246 */       ct.go();
/*      */     }
/*      */     catch (DocumentException e) {
/* 1249 */       throw new ExceptionConverter(e);
/*      */     }
/* 1251 */     canvas.restoreState();
/*      */   }
/*      */ 
/*      */   public static void showTextAligned(PdfContentByte canvas, int alignment, Phrase phrase, float x, float y, float rotation)
/*      */   {
/* 1265 */     showTextAligned(canvas, alignment, phrase, x, y, rotation, 1, 0);
/*      */   }
/*      */ 
/*      */   public static float fitText(Font font, String text, Rectangle rect, float maxFontSize, int runDirection)
/*      */   {
/*      */     try
/*      */     {
/* 1279 */       ColumnText ct = null;
/* 1280 */       int status = 0;
/* 1281 */       if (maxFontSize <= 0.0F) {
/* 1282 */         int cr = 0;
/* 1283 */         int lf = 0;
/* 1284 */         char[] t = text.toCharArray();
/* 1285 */         for (int k = 0; k < t.length; k++) {
/* 1286 */           if (t[k] == '\n')
/* 1287 */             lf++;
/* 1288 */           else if (t[k] == '\r')
/* 1289 */             cr++;
/*      */         }
/* 1291 */         int minLines = Math.max(cr, lf) + 1;
/* 1292 */         maxFontSize = Math.abs(rect.getHeight()) / minLines - 0.001F;
/*      */       }
/* 1294 */       font.setSize(maxFontSize);
/* 1295 */       Phrase ph = new Phrase(text, font);
/* 1296 */       ct = new ColumnText(null);
/* 1297 */       ct.setSimpleColumn(ph, rect.getLeft(), rect.getBottom(), rect.getRight(), rect.getTop(), maxFontSize, 0);
/* 1298 */       ct.setRunDirection(runDirection);
/* 1299 */       status = ct.go(true);
/* 1300 */       if ((status & 0x1) != 0)
/* 1301 */         return maxFontSize;
/* 1302 */       float precision = 0.1F;
/* 1303 */       float min = 0.0F;
/* 1304 */       float max = maxFontSize;
/* 1305 */       float size = maxFontSize;
/* 1306 */       for (int k = 0; k < 50; k++) {
/* 1307 */         size = (min + max) / 2.0F;
/* 1308 */         ct = new ColumnText(null);
/* 1309 */         font.setSize(size);
/* 1310 */         ct.setSimpleColumn(new Phrase(text, font), rect.getLeft(), rect.getBottom(), rect.getRight(), rect.getTop(), size, 0);
/* 1311 */         ct.setRunDirection(runDirection);
/* 1312 */         status = ct.go(true);
/* 1313 */         if ((status & 0x1) != 0) {
/* 1314 */           if (max - min < size * precision)
/* 1315 */             return size;
/* 1316 */           min = size;
/*      */         }
/*      */         else {
/* 1319 */           max = size;
/*      */         }
/*      */       }
/* 1321 */       return size;
/*      */     }
/*      */     catch (Exception e) {
/* 1324 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected int goComposite(boolean simulate) throws DocumentException
/*      */   {
/* 1330 */     PdfDocument pdf = null;
/* 1331 */     if (this.canvas != null)
/* 1332 */       pdf = this.canvas.pdf;
/* 1333 */     if (!this.rectangularMode)
/* 1334 */       throw new DocumentException(MessageLocalization.getComposedMessage("irregular.columns.are.not.supported.in.composite.mode", new Object[0]));
/* 1335 */     this.linesWritten = 0;
/* 1336 */     this.descender = 0.0F;
/* 1337 */     boolean firstPass = true;
/*      */     while (true)
/*      */     {
/* 1340 */       if (this.compositeElements.isEmpty())
/* 1341 */         return 1;
/* 1342 */       Element element = (Element)this.compositeElements.getFirst();
/* 1343 */       if (element.type() == 12) {
/* 1344 */         Paragraph para = (Paragraph)element;
/* 1345 */         int status = 0;
/* 1346 */         for (int keep = 0; keep < 2; keep++) {
/* 1347 */           float lastY = this.yLine;
/* 1348 */           boolean createHere = false;
/* 1349 */           if (this.compositeColumn == null) {
/* 1350 */             this.compositeColumn = new ColumnText(this.canvas);
/* 1351 */             this.compositeColumn.setAlignment(para.getAlignment());
/* 1352 */             this.compositeColumn.setIndent(para.getIndentationLeft() + para.getFirstLineIndent(), false);
/* 1353 */             this.compositeColumn.setExtraParagraphSpace(para.getExtraParagraphSpace());
/* 1354 */             this.compositeColumn.setFollowingIndent(para.getIndentationLeft());
/* 1355 */             this.compositeColumn.setRightIndent(para.getIndentationRight());
/* 1356 */             this.compositeColumn.setLeading(para.getLeading(), para.getMultipliedLeading());
/* 1357 */             this.compositeColumn.setRunDirection(this.runDirection);
/* 1358 */             this.compositeColumn.setArabicOptions(this.arabicOptions);
/* 1359 */             this.compositeColumn.setSpaceCharRatio(this.spaceCharRatio);
/* 1360 */             this.compositeColumn.addText(para);
/* 1361 */             if ((!firstPass) || (!this.adjustFirstLine)) {
/* 1362 */               this.yLine -= para.getSpacingBefore();
/*      */             }
/* 1364 */             createHere = true;
/*      */           }
/* 1366 */           this.compositeColumn.setUseAscender(((firstPass) || (this.descender == 0.0F)) && (this.adjustFirstLine) ? this.useAscender : false);
/* 1367 */           this.compositeColumn.setInheritGraphicState(this.inheritGraphicState);
/* 1368 */           this.compositeColumn.leftX = this.leftX;
/* 1369 */           this.compositeColumn.rightX = this.rightX;
/* 1370 */           this.compositeColumn.yLine = this.yLine;
/* 1371 */           this.compositeColumn.rectangularWidth = this.rectangularWidth;
/* 1372 */           this.compositeColumn.rectangularMode = this.rectangularMode;
/* 1373 */           this.compositeColumn.minY = this.minY;
/* 1374 */           this.compositeColumn.maxY = this.maxY;
/* 1375 */           boolean keepCandidate = (para.getKeepTogether()) && (createHere) && ((!firstPass) || (!this.adjustFirstLine));
/* 1376 */           boolean s = (simulate) || ((keepCandidate) && (keep == 0));
/* 1377 */           if ((isTagged(this.canvas)) && (!s)) {
/* 1378 */             this.canvas.openMCBlock(para);
/*      */           }
/* 1380 */           status = this.compositeColumn.go(s);
/* 1381 */           if ((isTagged(this.canvas)) && (!s)) {
/* 1382 */             this.canvas.closeMCBlock(para);
/*      */           }
/* 1384 */           this.lastX = this.compositeColumn.getLastX();
/* 1385 */           updateFilledWidth(this.compositeColumn.filledWidth);
/* 1386 */           if (((status & 0x1) == 0) && (keepCandidate)) {
/* 1387 */             this.compositeColumn = null;
/* 1388 */             this.yLine = lastY;
/* 1389 */             return 2;
/*      */           }
/* 1391 */           if ((simulate) || (!keepCandidate))
/*      */             break;
/* 1393 */           if (keep == 0) {
/* 1394 */             this.compositeColumn = null;
/* 1395 */             this.yLine = lastY;
/*      */           }
/*      */         }
/* 1398 */         firstPass = false;
/* 1399 */         if (this.compositeColumn.getLinesWritten() > 0) {
/* 1400 */           this.yLine = this.compositeColumn.yLine;
/* 1401 */           this.linesWritten += this.compositeColumn.linesWritten;
/* 1402 */           this.descender = this.compositeColumn.descender;
/*      */         }
/* 1404 */         this.currentLeading = this.compositeColumn.currentLeading;
/* 1405 */         if ((status & 0x1) != 0) {
/* 1406 */           this.compositeColumn = null;
/* 1407 */           this.compositeElements.removeFirst();
/* 1408 */           this.yLine -= para.getSpacingAfter();
/*      */         }
/* 1410 */         if ((status & 0x2) != 0) {
/* 1411 */           return 2;
/*      */         }
/*      */       }
/* 1414 */       else if (element.type() == 14) {
/* 1415 */         com.itextpdf.text.List list = (com.itextpdf.text.List)element;
/* 1416 */         ArrayList items = list.getItems();
/* 1417 */         ListItem item = null;
/* 1418 */         float listIndentation = list.getIndentationLeft();
/* 1419 */         int count = 0;
/* 1420 */         Stack stack = new Stack();
/* 1421 */         for (int k = 0; k < items.size(); k++) {
/* 1422 */           Object obj = items.get(k);
/* 1423 */           if ((obj instanceof ListItem)) {
/* 1424 */             if (count == this.listIdx) {
/* 1425 */               item = (ListItem)obj;
/* 1426 */               break;
/*      */             }
/* 1428 */             count++;
/*      */           }
/* 1430 */           else if ((obj instanceof com.itextpdf.text.List)) {
/* 1431 */             stack.push(new Object[] { list, Integer.valueOf(k), new Float(listIndentation) });
/* 1432 */             list = (com.itextpdf.text.List)obj;
/* 1433 */             items = list.getItems();
/* 1434 */             listIndentation += list.getIndentationLeft();
/* 1435 */             k = -1;
/* 1436 */             continue;
/*      */           }
/* 1438 */           if ((k == items.size() - 1) && 
/* 1439 */             (!stack.isEmpty())) {
/* 1440 */             Object[] objs = (Object[])stack.pop();
/* 1441 */             list = (com.itextpdf.text.List)objs[0];
/* 1442 */             items = list.getItems();
/* 1443 */             k = ((Integer)objs[1]).intValue();
/* 1444 */             listIndentation = ((Float)objs[2]).floatValue();
/*      */           }
/*      */         }
/*      */ 
/* 1448 */         int status = 0;
/* 1449 */         for (int keep = 0; ; keep++) { if (keep >= 2) break label1621;
/* 1450 */           float lastY = this.yLine;
/* 1451 */           boolean createHere = false;
/* 1452 */           if (this.compositeColumn == null) {
/* 1453 */             if (item == null) {
/* 1454 */               this.listIdx = 0;
/* 1455 */               this.compositeElements.removeFirst();
/* 1456 */               break;
/*      */             }
/* 1458 */             this.compositeColumn = new ColumnText(this.canvas);
/* 1459 */             this.compositeColumn.setUseAscender(((firstPass) || (this.descender == 0.0F)) && (this.adjustFirstLine) ? this.useAscender : false);
/* 1460 */             this.compositeColumn.setInheritGraphicState(this.inheritGraphicState);
/* 1461 */             this.compositeColumn.setAlignment(item.getAlignment());
/* 1462 */             this.compositeColumn.setIndent(item.getIndentationLeft() + listIndentation + item.getFirstLineIndent(), false);
/* 1463 */             this.compositeColumn.setExtraParagraphSpace(item.getExtraParagraphSpace());
/* 1464 */             this.compositeColumn.setFollowingIndent(this.compositeColumn.getIndent());
/* 1465 */             this.compositeColumn.setRightIndent(item.getIndentationRight() + list.getIndentationRight());
/* 1466 */             this.compositeColumn.setLeading(item.getLeading(), item.getMultipliedLeading());
/* 1467 */             this.compositeColumn.setRunDirection(this.runDirection);
/* 1468 */             this.compositeColumn.setArabicOptions(this.arabicOptions);
/* 1469 */             this.compositeColumn.setSpaceCharRatio(this.spaceCharRatio);
/* 1470 */             this.compositeColumn.addText(item);
/* 1471 */             if ((!firstPass) || (!this.adjustFirstLine)) {
/* 1472 */               this.yLine -= item.getSpacingBefore();
/*      */             }
/* 1474 */             createHere = true;
/*      */           }
/* 1476 */           this.compositeColumn.leftX = this.leftX;
/* 1477 */           this.compositeColumn.rightX = this.rightX;
/* 1478 */           this.compositeColumn.yLine = this.yLine;
/* 1479 */           this.compositeColumn.rectangularWidth = this.rectangularWidth;
/* 1480 */           this.compositeColumn.rectangularMode = this.rectangularMode;
/* 1481 */           this.compositeColumn.minY = this.minY;
/* 1482 */           this.compositeColumn.maxY = this.maxY;
/* 1483 */           boolean keepCandidate = (item.getKeepTogether()) && (createHere) && ((!firstPass) || (!this.adjustFirstLine));
/* 1484 */           boolean s = (simulate) || ((keepCandidate) && (keep == 0));
/* 1485 */           if ((isTagged(this.canvas)) && (!s)) {
/* 1486 */             item.getListLabel().setIndentation(listIndentation);
/* 1487 */             if ((list.getFirstItem() == item) || ((this.compositeColumn != null) && (this.compositeColumn.bidiLine != null)))
/* 1488 */               this.canvas.openMCBlock(list);
/* 1489 */             this.canvas.openMCBlock(item);
/*      */           }
/* 1491 */           status = this.compositeColumn.go((simulate) || ((keepCandidate) && (keep == 0)), item);
/* 1492 */           if ((isTagged(this.canvas)) && (!s)) {
/* 1493 */             this.canvas.closeMCBlock(item.getListBody());
/* 1494 */             this.canvas.closeMCBlock(item);
/* 1495 */             if (((list.getLastItem() == item) && ((status & 0x1) != 0)) || ((status & 0x2) != 0))
/* 1496 */               this.canvas.closeMCBlock(list);
/*      */           }
/* 1498 */           this.lastX = this.compositeColumn.getLastX();
/* 1499 */           updateFilledWidth(this.compositeColumn.filledWidth);
/* 1500 */           if (((status & 0x1) == 0) && (keepCandidate)) {
/* 1501 */             this.compositeColumn = null;
/* 1502 */             this.yLine = lastY;
/* 1503 */             return 2;
/*      */           }
/* 1505 */           if ((simulate) || (!keepCandidate))
/*      */             break label1621;
/* 1507 */           if (keep == 0) {
/* 1508 */             this.compositeColumn = null;
/* 1509 */             this.yLine = lastY;
/*      */           }
/*      */         }
/* 1512 */         label1621: firstPass = false;
/* 1513 */         this.yLine = this.compositeColumn.yLine;
/* 1514 */         this.linesWritten += this.compositeColumn.linesWritten;
/* 1515 */         this.descender = this.compositeColumn.descender;
/* 1516 */         this.currentLeading = this.compositeColumn.currentLeading;
/* 1517 */         if ((!isTagged(this.canvas)) && 
/* 1518 */           (!Float.isNaN(this.compositeColumn.firstLineY)) && (!this.compositeColumn.firstLineYDone)) {
/* 1519 */           if (!simulate) {
/* 1520 */             showTextAligned(this.canvas, 0, new Phrase(item.getListSymbol()), this.compositeColumn.leftX + listIndentation, this.compositeColumn.firstLineY, 0.0F);
/*      */           }
/* 1522 */           this.compositeColumn.firstLineYDone = true;
/*      */         }
/*      */ 
/* 1525 */         if ((status & 0x1) != 0) {
/* 1526 */           this.compositeColumn = null;
/* 1527 */           this.listIdx += 1;
/* 1528 */           this.yLine -= item.getSpacingAfter();
/*      */         }
/* 1530 */         if ((status & 0x2) != 0)
/* 1531 */           return 2;
/*      */       }
/* 1533 */       else if (element.type() == 23)
/*      */       {
/* 1538 */         PdfPTable table = (PdfPTable)element;
/*      */ 
/* 1541 */         if (table.size() <= table.getHeaderRows()) {
/* 1542 */           this.compositeElements.removeFirst();
/*      */         }
/*      */         else
/*      */         {
/* 1547 */           float yTemp = this.yLine;
/* 1548 */           yTemp += this.descender;
/* 1549 */           if ((this.rowIdx == 0) && (this.adjustFirstLine)) {
/* 1550 */             yTemp -= table.spacingBefore();
/*      */           }
/*      */ 
/* 1553 */           if ((yTemp < this.minY) || (yTemp > this.maxY)) {
/* 1554 */             return 2;
/*      */           }
/*      */ 
/* 1557 */           float yLineWrite = yTemp;
/* 1558 */           float x1 = this.leftX;
/* 1559 */           this.currentLeading = 0.0F;
/*      */           float tableWidth;
/* 1562 */           if (table.isLockedWidth()) {
/* 1563 */             float tableWidth = table.getTotalWidth();
/* 1564 */             updateFilledWidth(tableWidth);
/*      */           }
/*      */           else {
/* 1567 */             tableWidth = this.rectangularWidth * table.getWidthPercentage() / 100.0F;
/* 1568 */             table.setTotalWidth(tableWidth);
/*      */           }
/*      */ 
/* 1573 */           table.normalizeHeadersFooters();
/* 1574 */           int headerRows = table.getHeaderRows();
/* 1575 */           int footerRows = table.getFooterRows();
/* 1576 */           int realHeaderRows = headerRows - footerRows;
/* 1577 */           float headerHeight = table.getHeaderHeight();
/* 1578 */           float footerHeight = table.getFooterHeight();
/*      */ 
/* 1581 */           boolean skipHeader = (table.isSkipFirstHeader()) && (this.rowIdx <= realHeaderRows) && ((table.isComplete()) || (this.rowIdx != realHeaderRows));
/*      */ 
/* 1583 */           if (!skipHeader) {
/* 1584 */             yTemp -= headerHeight;
/* 1585 */             if ((yTemp < this.minY) || (yTemp > this.maxY)) {
/* 1586 */               return 2;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1593 */           int k = 0;
/* 1594 */           if (this.rowIdx < headerRows) {
/* 1595 */             this.rowIdx = headerRows;
/*      */           }
/*      */ 
/* 1598 */           if (!table.isComplete()) {
/* 1599 */             yTemp -= footerHeight;
/*      */           }
/*      */ 
/* 1602 */           PdfPTable.FittingRows fittingRows = table.getFittingRows(yTemp - this.minY, this.rowIdx);
/* 1603 */           k = fittingRows.lastRow + 1;
/* 1604 */           yTemp -= fittingRows.height;
/*      */ 
/* 1607 */           this.LOGGER.info("Want to split at row " + k);
/* 1608 */           int kTemp = k;
/* 1609 */           while ((kTemp > this.rowIdx) && (kTemp < table.size()) && (table.getRow(kTemp).isMayNotBreak())) {
/* 1610 */             kTemp--;
/*      */           }
/* 1612 */           if (((kTemp > this.rowIdx) && (kTemp < k)) || ((kTemp == 0) && (table.getRow(0).isMayNotBreak()) && (table.isLoopCheck()))) {
/* 1613 */             yTemp = this.minY;
/* 1614 */             k = kTemp;
/* 1615 */             table.setLoopCheck(false);
/*      */           }
/* 1617 */           this.LOGGER.info("Will split at row " + k);
/*      */ 
/* 1620 */           if ((table.isSplitLate()) && (k > 0)) {
/* 1621 */             fittingRows.correctLastRowChosen(table, k - 1);
/*      */           }
/*      */ 
/* 1626 */           if (!table.isComplete()) {
/* 1627 */             yTemp += footerHeight;
/*      */           }
/*      */ 
/* 1630 */           if (!table.isSplitRows()) {
/* 1631 */             this.splittedRow = -1;
/* 1632 */             if (k == this.rowIdx)
/*      */             {
/* 1634 */               if (k == table.size()) {
/* 1635 */                 this.compositeElements.removeFirst();
/* 1636 */                 continue;
/*      */               }
/*      */ 
/* 1640 */               table.getRows().remove(k);
/* 1641 */               return 2;
/*      */             }
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/* 1648 */             if ((table.isSplitLate()) && (this.rowIdx < k)) {
/* 1649 */               this.splittedRow = -1;
/*      */             }
/* 1652 */             else if (k < table.size())
/*      */             {
/* 1656 */               yTemp -= fittingRows.completedRowsHeight - fittingRows.height;
/*      */ 
/* 1659 */               float h = yTemp - this.minY;
/*      */ 
/* 1661 */               PdfPRow newRow = table.getRow(k).splitRow(table, k, h);
/*      */ 
/* 1663 */               if (newRow == null) {
/* 1664 */                 this.LOGGER.info("Didn't split row!");
/* 1665 */                 this.splittedRow = -1;
/* 1666 */                 if (this.rowIdx == k)
/* 1667 */                   return 2;
/*      */               }
/*      */               else
/*      */               {
/* 1671 */                 if (k != this.splittedRow) {
/* 1672 */                   this.splittedRow = (k + 1);
/* 1673 */                   table = new PdfPTable(table);
/* 1674 */                   this.compositeElements.set(0, table);
/* 1675 */                   ArrayList rows = table.getRows();
/* 1676 */                   for (int i = headerRows; i < this.rowIdx; i++)
/* 1677 */                     rows.set(i, null);
/*      */                 }
/* 1679 */                 yTemp = this.minY;
/* 1680 */                 table.getRows().add(++k, newRow);
/* 1681 */                 this.LOGGER.info("Inserting row at position " + k);
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 1686 */             firstPass = false;
/*      */ 
/* 1689 */             if (!simulate)
/*      */             {
/* 1691 */               switch (table.getHorizontalAlignment()) {
/*      */               case 0:
/* 1693 */                 break;
/*      */               case 2:
/* 1695 */                 x1 += this.rectangularWidth - tableWidth;
/* 1696 */                 break;
/*      */               default:
/* 1698 */                 x1 += (this.rectangularWidth - tableWidth) / 2.0F;
/*      */               }
/*      */ 
/* 1701 */               PdfPTable nt = PdfPTable.shallowCopy(table);
/* 1702 */               ArrayList sub = nt.getRows();
/*      */ 
/* 1704 */               if ((!skipHeader) && (realHeaderRows > 0)) {
/* 1705 */                 ArrayList rows = table.getRows(0, realHeaderRows);
/* 1706 */                 if (isTagged(this.canvas))
/* 1707 */                   nt.getHeader().rows = rows;
/* 1708 */                 sub.addAll(rows);
/*      */               }
/*      */               else {
/* 1711 */                 nt.setHeaderRows(footerRows);
/*      */               }
/*      */ 
/* 1715 */               ArrayList rows = table.getRows(this.rowIdx, k);
/* 1716 */               if (isTagged(this.canvas)) {
/* 1717 */                 nt.getBody().rows = rows;
/*      */               }
/* 1719 */               sub.addAll(rows);
/*      */ 
/* 1722 */               boolean showFooter = !table.isSkipLastFooter();
/* 1723 */               boolean newPageFollows = false;
/* 1724 */               if (k < table.size()) {
/* 1725 */                 nt.setComplete(true);
/* 1726 */                 showFooter = true;
/* 1727 */                 newPageFollows = true;
/*      */               }
/*      */ 
/* 1730 */               if ((footerRows > 0) && (nt.isComplete()) && (showFooter)) {
/* 1731 */                 ArrayList rows = table.getRows(realHeaderRows, realHeaderRows + footerRows);
/* 1732 */                 if (isTagged(this.canvas)) {
/* 1733 */                   nt.getFooter().rows = rows;
/*      */                 }
/* 1735 */                 sub.addAll(rows);
/*      */               }
/*      */               else {
/* 1738 */                 footerRows = 0;
/*      */               }
/*      */ 
/* 1742 */               float rowHeight = 0.0F;
/* 1743 */               int lastIdx = sub.size() - 1 - footerRows;
/* 1744 */               PdfPRow last = (PdfPRow)sub.get(lastIdx);
/* 1745 */               if (table.isExtendLastRow(newPageFollows)) {
/* 1746 */                 rowHeight = last.getMaxHeights();
/* 1747 */                 last.setMaxHeights(yTemp - this.minY + rowHeight);
/* 1748 */                 yTemp = this.minY;
/*      */               }
/*      */ 
/* 1752 */               if (newPageFollows) {
/* 1753 */                 PdfPTableEvent tableEvent = table.getTableEvent();
/* 1754 */                 if ((tableEvent instanceof PdfPTableEventSplit)) {
/* 1755 */                   ((PdfPTableEventSplit)tableEvent).splitTable(table);
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/* 1760 */               if (this.canvases != null) {
/* 1761 */                 if (isTagged(this.canvases[3])) {
/* 1762 */                   this.canvases[3].openMCBlock(table);
/*      */                 }
/* 1764 */                 nt.writeSelectedRows(0, -1, 0, -1, x1, yLineWrite, this.canvases, false);
/* 1765 */                 if (isTagged(this.canvases[3]))
/* 1766 */                   this.canvases[3].closeMCBlock(table);
/*      */               }
/*      */               else
/*      */               {
/* 1770 */                 if (isTagged(this.canvas)) {
/* 1771 */                   this.canvas.openMCBlock(table);
/*      */                 }
/* 1773 */                 nt.writeSelectedRows(0, -1, 0, -1, x1, yLineWrite, this.canvas, false);
/* 1774 */                 if (isTagged(this.canvas)) {
/* 1775 */                   this.canvas.closeMCBlock(table);
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/* 1781 */               if ((this.splittedRow == k) && (k < table.size())) {
/* 1782 */                 PdfPRow splitted = (PdfPRow)table.getRows().get(k);
/* 1783 */                 splitted.copyRowContent(nt, lastIdx);
/*      */               }
/* 1786 */               else if ((k > 0) && (k < table.size()))
/*      */               {
/* 1789 */                 PdfPRow row = table.getRow(k);
/* 1790 */                 row.splitRowspans(table, k - 1, nt, lastIdx);
/*      */               }
/*      */ 
/* 1795 */               if (table.isExtendLastRow(newPageFollows)) {
/* 1796 */                 last.setMaxHeights(rowHeight);
/*      */               }
/*      */ 
/* 1801 */               if (newPageFollows) {
/* 1802 */                 PdfPTableEvent tableEvent = table.getTableEvent();
/* 1803 */                 if ((tableEvent instanceof PdfPTableEventAfterSplit)) {
/* 1804 */                   PdfPRow row = table.getRow(k);
/* 1805 */                   ((PdfPTableEventAfterSplit)tableEvent).afterSplitTable(table, row, k);
/*      */                 }
/*      */               }
/*      */ 
/*      */             }
/* 1810 */             else if ((table.isExtendLastRow()) && (this.minY > -1.073742E+009F)) {
/* 1811 */               yTemp = this.minY;
/*      */             }
/*      */ 
/* 1814 */             this.yLine = yTemp;
/* 1815 */             this.descender = 0.0F;
/* 1816 */             this.currentLeading = 0.0F;
/* 1817 */             if ((!skipHeader) && (!table.isComplete()))
/* 1818 */               this.yLine += footerHeight;
/* 1819 */             while ((k < table.size()) && 
/* 1820 */               (table.getRowHeight(k) <= 0.0F) && (!table.hasRowspan(k)))
/*      */             {
/* 1823 */               k++;
/*      */             }
/* 1825 */             if (k >= table.size())
/*      */             {
/* 1827 */               if (this.yLine - table.spacingAfter() < this.minY) {
/* 1828 */                 this.yLine = this.minY;
/*      */               }
/*      */               else {
/* 1831 */                 this.yLine -= table.spacingAfter();
/*      */               }
/* 1833 */               this.compositeElements.removeFirst();
/* 1834 */               this.splittedRow = -1;
/* 1835 */               this.rowIdx = 0;
/*      */             }
/*      */             else {
/* 1838 */               if (this.splittedRow != -1) {
/* 1839 */                 ArrayList rows = table.getRows();
/* 1840 */                 for (int i = this.rowIdx; i < k; i++)
/* 1841 */                   rows.set(i, null);
/*      */               }
/* 1843 */               this.rowIdx = k;
/* 1844 */               return 2;
/*      */             }
/*      */           }
/*      */         } } else if (element.type() == 55) {
/* 1848 */         if (!simulate) {
/* 1849 */           DrawInterface zh = (DrawInterface)element;
/* 1850 */           zh.draw(this.canvas, this.leftX, this.minY, this.rightX, this.maxY, this.yLine);
/*      */         }
/* 1852 */         this.compositeElements.removeFirst();
/* 1853 */       } else if (element.type() == 37) {
/* 1854 */         ArrayList floatingElements = new ArrayList();
/*      */         do {
/* 1856 */           floatingElements.add(element);
/* 1857 */           this.compositeElements.removeFirst();
/* 1858 */           element = !this.compositeElements.isEmpty() ? (Element)this.compositeElements.getFirst() : null;
/* 1859 */         }while ((element != null) && (element.type() == 37));
/*      */ 
/* 1861 */         FloatLayout fl = new FloatLayout(floatingElements, this.useAscender);
/* 1862 */         fl.setSimpleColumn(this.leftX, this.minY, this.rightX, this.yLine);
/* 1863 */         int status = fl.layout(this.canvas, simulate);
/*      */ 
/* 1866 */         this.yLine = fl.getYLine();
/* 1867 */         this.descender = 0.0F;
/* 1868 */         if ((status & 0x1) == 0) {
/* 1869 */           this.compositeElements.addAll(floatingElements);
/* 1870 */           return status;
/*      */         }
/*      */       } else {
/* 1873 */         this.compositeElements.removeFirst();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public PdfContentByte getCanvas()
/*      */   {
/* 1884 */     return this.canvas;
/*      */   }
/*      */ 
/*      */   public void setCanvas(PdfContentByte canvas)
/*      */   {
/* 1894 */     this.canvas = canvas;
/* 1895 */     this.canvases = null;
/* 1896 */     if (this.compositeColumn != null)
/* 1897 */       this.compositeColumn.setCanvas(canvas);
/*      */   }
/*      */ 
/*      */   public void setCanvases(PdfContentByte[] canvases)
/*      */   {
/* 1906 */     this.canvases = canvases;
/* 1907 */     this.canvas = canvases[3];
/* 1908 */     if (this.compositeColumn != null)
/* 1909 */       this.compositeColumn.setCanvases(canvases);
/*      */   }
/*      */ 
/*      */   public PdfContentByte[] getCanvases()
/*      */   {
/* 1918 */     return this.canvases;
/*      */   }
/*      */ 
/*      */   public boolean zeroHeightElement()
/*      */   {
/* 1928 */     return (this.composite) && (!this.compositeElements.isEmpty()) && (((Element)this.compositeElements.getFirst()).type() == 55);
/*      */   }
/*      */ 
/*      */   public java.util.List<Element> getCompositeElements() {
/* 1932 */     return this.compositeElements;
/*      */   }
/*      */ 
/*      */   public boolean isUseAscender()
/*      */   {
/* 1941 */     return this.useAscender;
/*      */   }
/*      */ 
/*      */   public void setUseAscender(boolean useAscender)
/*      */   {
/* 1950 */     this.useAscender = useAscender;
/*      */   }
/*      */ 
/*      */   public static boolean hasMoreText(int status)
/*      */   {
/* 1957 */     return (status & 0x1) == 0;
/*      */   }
/*      */ 
/*      */   public float getFilledWidth()
/*      */   {
/* 1966 */     return this.filledWidth;
/*      */   }
/*      */ 
/*      */   public void setFilledWidth(float filledWidth)
/*      */   {
/* 1976 */     this.filledWidth = filledWidth;
/*      */   }
/*      */ 
/*      */   public void updateFilledWidth(float w)
/*      */   {
/* 1985 */     if (w > this.filledWidth)
/* 1986 */       this.filledWidth = w;
/*      */   }
/*      */ 
/*      */   public boolean isAdjustFirstLine()
/*      */   {
/* 1996 */     return this.adjustFirstLine;
/*      */   }
/*      */ 
/*      */   public void setAdjustFirstLine(boolean adjustFirstLine)
/*      */   {
/* 2010 */     this.adjustFirstLine = adjustFirstLine;
/*      */   }
/*      */ 
/*      */   private static boolean isTagged(PdfContentByte canvas) {
/* 2014 */     return (canvas != null) && (canvas.pdf != null) && (canvas.writer != null) && (canvas.writer.isTagged());
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.ColumnText
 * JD-Core Version:    0.6.2
 */