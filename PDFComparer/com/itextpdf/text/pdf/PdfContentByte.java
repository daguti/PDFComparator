/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.awt.FontMapper;
/*      */ import com.itextpdf.awt.PdfGraphics2D;
/*      */ import com.itextpdf.awt.PdfPrinterGraphics2D;
/*      */ import com.itextpdf.awt.geom.Point2D;
/*      */ import com.itextpdf.awt.geom.Point2D.Float;
/*      */ import com.itextpdf.text.Annotation;
/*      */ import com.itextpdf.text.BaseColor;
/*      */ import com.itextpdf.text.DocumentException;
/*      */ import com.itextpdf.text.ExceptionConverter;
/*      */ import com.itextpdf.text.Image;
/*      */ import com.itextpdf.text.ImgJBIG2;
/*      */ import com.itextpdf.text.Rectangle;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.exceptions.IllegalPdfSyntaxException;
/*      */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*      */ import com.itextpdf.text.pdf.internal.PdfAnnotationsImp;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map.Entry;
/*      */ 
/*      */ public class PdfContentByte
/*      */ {
/*      */   public static final int ALIGN_CENTER = 1;
/*      */   public static final int ALIGN_LEFT = 0;
/*      */   public static final int ALIGN_RIGHT = 2;
/*      */   public static final int LINE_CAP_BUTT = 0;
/*      */   public static final int LINE_CAP_ROUND = 1;
/*      */   public static final int LINE_CAP_PROJECTING_SQUARE = 2;
/*      */   public static final int LINE_JOIN_MITER = 0;
/*      */   public static final int LINE_JOIN_ROUND = 1;
/*      */   public static final int LINE_JOIN_BEVEL = 2;
/*      */   public static final int TEXT_RENDER_MODE_FILL = 0;
/*      */   public static final int TEXT_RENDER_MODE_STROKE = 1;
/*      */   public static final int TEXT_RENDER_MODE_FILL_STROKE = 2;
/*      */   public static final int TEXT_RENDER_MODE_INVISIBLE = 3;
/*      */   public static final int TEXT_RENDER_MODE_FILL_CLIP = 4;
/*      */   public static final int TEXT_RENDER_MODE_STROKE_CLIP = 5;
/*      */   public static final int TEXT_RENDER_MODE_FILL_STROKE_CLIP = 6;
/*      */   public static final int TEXT_RENDER_MODE_CLIP = 7;
/*  195 */   private static final float[] unitRect = { 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F };
/*      */ 
/*  199 */   protected ByteBuffer content = new ByteBuffer();
/*      */ 
/*  201 */   protected int markedContentSize = 0;
/*      */   protected PdfWriter writer;
/*      */   protected PdfDocument pdf;
/*  210 */   protected GraphicState state = new GraphicState();
/*      */ 
/*  213 */   protected ArrayList<GraphicState> stateList = new ArrayList();
/*      */   protected ArrayList<Integer> layerDepth;
/*  220 */   protected int separator = 10;
/*      */ 
/*  222 */   private int mcDepth = 0;
/*  223 */   private boolean inText = false;
/*      */ 
/*  225 */   private static HashMap<PdfName, String> abrev = new HashMap();
/*      */ 
/*  227 */   private ArrayList<IAccessibleElement> mcElements = new ArrayList();
/*      */ 
/*  229 */   protected PdfContentByte duplicatedFrom = null;
/*      */ 
/*      */   public PdfContentByte(PdfWriter wr)
/*      */   {
/*  253 */     if (wr != null) {
/*  254 */       this.writer = wr;
/*  255 */       this.pdf = this.writer.getPdfDocument();
/*      */     }
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/*  269 */     return this.content.toString();
/*      */   }
/*      */ 
/*      */   public boolean isTagged()
/*      */   {
/*  277 */     return (this.writer != null) && (this.writer.isTagged());
/*      */   }
/*      */ 
/*      */   public ByteBuffer getInternalBuffer()
/*      */   {
/*  286 */     return this.content;
/*      */   }
/*      */ 
/*      */   public byte[] toPdf(PdfWriter writer)
/*      */   {
/*  296 */     sanityCheck();
/*  297 */     return this.content.toByteArray();
/*      */   }
/*      */ 
/*      */   public void add(PdfContentByte other)
/*      */   {
/*  309 */     if ((other.writer != null) && (this.writer != other.writer))
/*  310 */       throw new RuntimeException(MessageLocalization.getComposedMessage("inconsistent.writers.are.you.mixing.two.documents", new Object[0]));
/*  311 */     this.content.append(other.content);
/*  312 */     this.markedContentSize += other.markedContentSize;
/*      */   }
/*      */ 
/*      */   public float getXTLM()
/*      */   {
/*  321 */     return this.state.xTLM;
/*      */   }
/*      */ 
/*      */   public float getYTLM()
/*      */   {
/*  330 */     return this.state.yTLM;
/*      */   }
/*      */ 
/*      */   public float getLeading()
/*      */   {
/*  339 */     return this.state.leading;
/*      */   }
/*      */ 
/*      */   public float getCharacterSpacing()
/*      */   {
/*  348 */     return this.state.charSpace;
/*      */   }
/*      */ 
/*      */   public float getWordSpacing()
/*      */   {
/*  357 */     return this.state.wordSpace;
/*      */   }
/*      */ 
/*      */   public float getHorizontalScaling()
/*      */   {
/*  366 */     return this.state.scale;
/*      */   }
/*      */ 
/*      */   public void setFlatness(float flatness)
/*      */   {
/*  379 */     if ((flatness >= 0.0F) && (flatness <= 100.0F))
/*  380 */       this.content.append(flatness).append(" i").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setLineCap(int style)
/*      */   {
/*  395 */     if ((style >= 0) && (style <= 2))
/*  396 */       this.content.append(style).append(" J").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setRenderingIntent(PdfName ri)
/*      */   {
/*  406 */     this.content.append(ri.getBytes()).append(" ri").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setLineDash(float phase)
/*      */   {
/*  421 */     this.content.append("[] ").append(phase).append(" d").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setLineDash(float unitsOn, float phase)
/*      */   {
/*  437 */     this.content.append("[").append(unitsOn).append("] ").append(phase).append(" d").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setLineDash(float unitsOn, float unitsOff, float phase)
/*      */   {
/*  454 */     this.content.append("[").append(unitsOn).append(' ').append(unitsOff).append("] ").append(phase).append(" d").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public final void setLineDash(float[] array, float phase)
/*      */   {
/*  470 */     this.content.append("[");
/*  471 */     for (int i = 0; i < array.length; i++) {
/*  472 */       this.content.append(array[i]);
/*  473 */       if (i < array.length - 1) this.content.append(' ');
/*      */     }
/*  475 */     this.content.append("] ").append(phase).append(" d").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setLineJoin(int style)
/*      */   {
/*  489 */     if ((style >= 0) && (style <= 2))
/*  490 */       this.content.append(style).append(" j").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setLineWidth(float w)
/*      */   {
/*  504 */     this.content.append(w).append(" w").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setMiterLimit(float miterLimit)
/*      */   {
/*  519 */     if (miterLimit > 1.0F)
/*  520 */       this.content.append(miterLimit).append(" M").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void clip()
/*      */   {
/*  531 */     if ((this.inText) && (isTagged())) {
/*  532 */       endText();
/*      */     }
/*  534 */     this.content.append("W").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void eoClip()
/*      */   {
/*  543 */     if ((this.inText) && (isTagged())) {
/*  544 */       endText();
/*      */     }
/*  546 */     this.content.append("W*").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setGrayFill(float gray)
/*      */   {
/*  559 */     saveColor(new GrayColor(gray), true);
/*  560 */     this.content.append(gray).append(" g").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void resetGrayFill()
/*      */   {
/*  568 */     saveColor(new GrayColor(0), true);
/*  569 */     this.content.append("0 g").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setGrayStroke(float gray)
/*      */   {
/*  582 */     saveColor(new GrayColor(gray), false);
/*  583 */     this.content.append(gray).append(" G").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void resetGrayStroke()
/*      */   {
/*  591 */     saveColor(new GrayColor(0), false);
/*  592 */     this.content.append("0 G").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   private void HelperRGB(float red, float green, float blue)
/*      */   {
/*  602 */     if (red < 0.0F)
/*  603 */       red = 0.0F;
/*  604 */     else if (red > 1.0F)
/*  605 */       red = 1.0F;
/*  606 */     if (green < 0.0F)
/*  607 */       green = 0.0F;
/*  608 */     else if (green > 1.0F)
/*  609 */       green = 1.0F;
/*  610 */     if (blue < 0.0F)
/*  611 */       blue = 0.0F;
/*  612 */     else if (blue > 1.0F)
/*  613 */       blue = 1.0F;
/*  614 */     this.content.append(red).append(' ').append(green).append(' ').append(blue);
/*      */   }
/*      */ 
/*      */   public void setRGBColorFillF(float red, float green, float blue)
/*      */   {
/*  632 */     saveColor(new BaseColor(red, green, blue), true);
/*  633 */     HelperRGB(red, green, blue);
/*  634 */     this.content.append(" rg").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void resetRGBColorFill()
/*      */   {
/*  642 */     resetGrayFill();
/*      */   }
/*      */ 
/*      */   public void setRGBColorStrokeF(float red, float green, float blue)
/*      */   {
/*  660 */     saveColor(new BaseColor(red, green, blue), false);
/*  661 */     HelperRGB(red, green, blue);
/*  662 */     this.content.append(" RG").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void resetRGBColorStroke()
/*      */   {
/*  671 */     resetGrayStroke();
/*      */   }
/*      */ 
/*      */   private void HelperCMYK(float cyan, float magenta, float yellow, float black)
/*      */   {
/*  683 */     if (cyan < 0.0F)
/*  684 */       cyan = 0.0F;
/*  685 */     else if (cyan > 1.0F)
/*  686 */       cyan = 1.0F;
/*  687 */     if (magenta < 0.0F)
/*  688 */       magenta = 0.0F;
/*  689 */     else if (magenta > 1.0F)
/*  690 */       magenta = 1.0F;
/*  691 */     if (yellow < 0.0F)
/*  692 */       yellow = 0.0F;
/*  693 */     else if (yellow > 1.0F)
/*  694 */       yellow = 1.0F;
/*  695 */     if (black < 0.0F)
/*  696 */       black = 0.0F;
/*  697 */     else if (black > 1.0F)
/*  698 */       black = 1.0F;
/*  699 */     this.content.append(cyan).append(' ').append(magenta).append(' ').append(yellow).append(' ').append(black);
/*      */   }
/*      */ 
/*      */   public void setCMYKColorFillF(float cyan, float magenta, float yellow, float black)
/*      */   {
/*  718 */     saveColor(new CMYKColor(cyan, magenta, yellow, black), true);
/*  719 */     HelperCMYK(cyan, magenta, yellow, black);
/*  720 */     this.content.append(" k").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void resetCMYKColorFill()
/*      */   {
/*  729 */     saveColor(new CMYKColor(0, 0, 0, 1), true);
/*  730 */     this.content.append("0 0 0 1 k").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setCMYKColorStrokeF(float cyan, float magenta, float yellow, float black)
/*      */   {
/*  749 */     saveColor(new CMYKColor(cyan, magenta, yellow, black), false);
/*  750 */     HelperCMYK(cyan, magenta, yellow, black);
/*  751 */     this.content.append(" K").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void resetCMYKColorStroke()
/*      */   {
/*  760 */     saveColor(new CMYKColor(0, 0, 0, 1), false);
/*  761 */     this.content.append("0 0 0 1 K").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void moveTo(float x, float y)
/*      */   {
/*  772 */     if (this.inText) {
/*  773 */       if (isTagged())
/*  774 */         endText();
/*      */       else {
/*  776 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/*  779 */     this.content.append(x).append(' ').append(y).append(" m").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void lineTo(float x, float y)
/*      */   {
/*  791 */     if (this.inText) {
/*  792 */       if (isTagged())
/*  793 */         endText();
/*      */       else {
/*  795 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/*  798 */     this.content.append(x).append(' ').append(y).append(" l").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void curveTo(float x1, float y1, float x2, float y2, float x3, float y3)
/*      */   {
/*  813 */     if (this.inText) {
/*  814 */       if (isTagged())
/*  815 */         endText();
/*      */       else {
/*  817 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/*  820 */     this.content.append(x1).append(' ').append(y1).append(' ').append(x2).append(' ').append(y2).append(' ').append(x3).append(' ').append(y3).append(" c").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void curveTo(float x2, float y2, float x3, float y3)
/*      */   {
/*  833 */     if (this.inText) {
/*  834 */       if (isTagged())
/*  835 */         endText();
/*      */       else {
/*  837 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/*  840 */     this.content.append(x2).append(' ').append(y2).append(' ').append(x3).append(' ').append(y3).append(" v").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void curveFromTo(float x1, float y1, float x3, float y3)
/*      */   {
/*  853 */     if (this.inText) {
/*  854 */       if (isTagged())
/*  855 */         endText();
/*      */       else {
/*  857 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/*  860 */     this.content.append(x1).append(' ').append(y1).append(' ').append(x3).append(' ').append(y3).append(" y").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void circle(float x, float y, float r)
/*      */   {
/*  870 */     float b = 0.5523F;
/*  871 */     moveTo(x + r, y);
/*  872 */     curveTo(x + r, y + r * b, x + r * b, y + r, x, y + r);
/*  873 */     curveTo(x - r * b, y + r, x - r, y + r * b, x - r, y);
/*  874 */     curveTo(x - r, y - r * b, x - r * b, y - r, x, y - r);
/*  875 */     curveTo(x + r * b, y - r, x + r, y - r * b, x + r, y);
/*      */   }
/*      */ 
/*      */   public void rectangle(float x, float y, float w, float h)
/*      */   {
/*  890 */     if (this.inText) {
/*  891 */       if (isTagged())
/*  892 */         endText();
/*      */       else {
/*  894 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/*  897 */     this.content.append(x).append(' ').append(y).append(' ').append(w).append(' ').append(h).append(" re").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   private boolean compareColors(BaseColor c1, BaseColor c2) {
/*  901 */     if ((c1 == null) && (c2 == null))
/*  902 */       return true;
/*  903 */     if ((c1 == null) || (c2 == null))
/*  904 */       return false;
/*  905 */     if ((c1 instanceof ExtendedColor))
/*  906 */       return c1.equals(c2);
/*  907 */     return c2.equals(c1);
/*      */   }
/*      */ 
/*      */   public void variableRectangle(Rectangle rect)
/*      */   {
/*  917 */     float t = rect.getTop();
/*  918 */     float b = rect.getBottom();
/*  919 */     float r = rect.getRight();
/*  920 */     float l = rect.getLeft();
/*  921 */     float wt = rect.getBorderWidthTop();
/*  922 */     float wb = rect.getBorderWidthBottom();
/*  923 */     float wr = rect.getBorderWidthRight();
/*  924 */     float wl = rect.getBorderWidthLeft();
/*  925 */     BaseColor ct = rect.getBorderColorTop();
/*  926 */     BaseColor cb = rect.getBorderColorBottom();
/*  927 */     BaseColor cr = rect.getBorderColorRight();
/*  928 */     BaseColor cl = rect.getBorderColorLeft();
/*  929 */     saveState();
/*  930 */     setLineCap(0);
/*  931 */     setLineJoin(0);
/*  932 */     float clw = 0.0F;
/*  933 */     boolean cdef = false;
/*  934 */     BaseColor ccol = null;
/*  935 */     boolean cdefi = false;
/*  936 */     BaseColor cfil = null;
/*      */ 
/*  938 */     if (wt > 0.0F) {
/*  939 */       setLineWidth(clw = wt);
/*  940 */       cdef = true;
/*  941 */       if (ct == null)
/*  942 */         resetRGBColorStroke();
/*      */       else
/*  944 */         setColorStroke(ct);
/*  945 */       ccol = ct;
/*  946 */       moveTo(l, t - wt / 2.0F);
/*  947 */       lineTo(r, t - wt / 2.0F);
/*  948 */       stroke();
/*      */     }
/*      */ 
/*  952 */     if (wb > 0.0F) {
/*  953 */       if (wb != clw)
/*  954 */         setLineWidth(clw = wb);
/*  955 */       if ((!cdef) || (!compareColors(ccol, cb))) {
/*  956 */         cdef = true;
/*  957 */         if (cb == null)
/*  958 */           resetRGBColorStroke();
/*      */         else
/*  960 */           setColorStroke(cb);
/*  961 */         ccol = cb;
/*      */       }
/*  963 */       moveTo(r, b + wb / 2.0F);
/*  964 */       lineTo(l, b + wb / 2.0F);
/*  965 */       stroke();
/*      */     }
/*      */ 
/*  969 */     if (wr > 0.0F) {
/*  970 */       if (wr != clw)
/*  971 */         setLineWidth(clw = wr);
/*  972 */       if ((!cdef) || (!compareColors(ccol, cr))) {
/*  973 */         cdef = true;
/*  974 */         if (cr == null)
/*  975 */           resetRGBColorStroke();
/*      */         else
/*  977 */           setColorStroke(cr);
/*  978 */         ccol = cr;
/*      */       }
/*  980 */       boolean bt = compareColors(ct, cr);
/*  981 */       boolean bb = compareColors(cb, cr);
/*  982 */       moveTo(r - wr / 2.0F, bt ? t : t - wt);
/*  983 */       lineTo(r - wr / 2.0F, bb ? b : b + wb);
/*  984 */       stroke();
/*  985 */       if ((!bt) || (!bb)) {
/*  986 */         cdefi = true;
/*  987 */         if (cr == null)
/*  988 */           resetRGBColorFill();
/*      */         else
/*  990 */           setColorFill(cr);
/*  991 */         cfil = cr;
/*  992 */         if (!bt) {
/*  993 */           moveTo(r, t);
/*  994 */           lineTo(r, t - wt);
/*  995 */           lineTo(r - wr, t - wt);
/*  996 */           fill();
/*      */         }
/*  998 */         if (!bb) {
/*  999 */           moveTo(r, b);
/* 1000 */           lineTo(r, b + wb);
/* 1001 */           lineTo(r - wr, b + wb);
/* 1002 */           fill();
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1008 */     if (wl > 0.0F) {
/* 1009 */       if (wl != clw)
/* 1010 */         setLineWidth(wl);
/* 1011 */       if ((!cdef) || (!compareColors(ccol, cl))) {
/* 1012 */         if (cl == null)
/* 1013 */           resetRGBColorStroke();
/*      */         else
/* 1015 */           setColorStroke(cl);
/*      */       }
/* 1017 */       boolean bt = compareColors(ct, cl);
/* 1018 */       boolean bb = compareColors(cb, cl);
/* 1019 */       moveTo(l + wl / 2.0F, bt ? t : t - wt);
/* 1020 */       lineTo(l + wl / 2.0F, bb ? b : b + wb);
/* 1021 */       stroke();
/* 1022 */       if ((!bt) || (!bb)) {
/* 1023 */         if ((!cdefi) || (!compareColors(cfil, cl))) {
/* 1024 */           if (cl == null)
/* 1025 */             resetRGBColorFill();
/*      */           else
/* 1027 */             setColorFill(cl);
/*      */         }
/* 1029 */         if (!bt) {
/* 1030 */           moveTo(l, t);
/* 1031 */           lineTo(l, t - wt);
/* 1032 */           lineTo(l + wl, t - wt);
/* 1033 */           fill();
/*      */         }
/* 1035 */         if (!bb) {
/* 1036 */           moveTo(l, b);
/* 1037 */           lineTo(l, b + wb);
/* 1038 */           lineTo(l + wl, b + wb);
/* 1039 */           fill();
/*      */         }
/*      */       }
/*      */     }
/* 1043 */     restoreState();
/*      */   }
/*      */ 
/*      */   public void rectangle(Rectangle rectangle)
/*      */   {
/* 1054 */     float x1 = rectangle.getLeft();
/* 1055 */     float y1 = rectangle.getBottom();
/* 1056 */     float x2 = rectangle.getRight();
/* 1057 */     float y2 = rectangle.getTop();
/*      */ 
/* 1060 */     BaseColor background = rectangle.getBackgroundColor();
/* 1061 */     if (background != null) {
/* 1062 */       saveState();
/* 1063 */       setColorFill(background);
/* 1064 */       rectangle(x1, y1, x2 - x1, y2 - y1);
/* 1065 */       fill();
/* 1066 */       restoreState();
/*      */     }
/*      */ 
/* 1070 */     if (!rectangle.hasBorders()) {
/* 1071 */       return;
/*      */     }
/*      */ 
/* 1077 */     if (rectangle.isUseVariableBorders()) {
/* 1078 */       variableRectangle(rectangle);
/*      */     }
/*      */     else
/*      */     {
/* 1082 */       if (rectangle.getBorderWidth() != -1.0F) {
/* 1083 */         setLineWidth(rectangle.getBorderWidth());
/*      */       }
/*      */ 
/* 1087 */       BaseColor color = rectangle.getBorderColor();
/* 1088 */       if (color != null) {
/* 1089 */         setColorStroke(color);
/*      */       }
/*      */ 
/* 1093 */       if (rectangle.hasBorder(15)) {
/* 1094 */         rectangle(x1, y1, x2 - x1, y2 - y1);
/*      */       }
/*      */       else
/*      */       {
/* 1098 */         if (rectangle.hasBorder(8)) {
/* 1099 */           moveTo(x2, y1);
/* 1100 */           lineTo(x2, y2);
/*      */         }
/* 1102 */         if (rectangle.hasBorder(4)) {
/* 1103 */           moveTo(x1, y1);
/* 1104 */           lineTo(x1, y2);
/*      */         }
/* 1106 */         if (rectangle.hasBorder(2)) {
/* 1107 */           moveTo(x1, y1);
/* 1108 */           lineTo(x2, y1);
/*      */         }
/* 1110 */         if (rectangle.hasBorder(1)) {
/* 1111 */           moveTo(x1, y2);
/* 1112 */           lineTo(x2, y2);
/*      */         }
/*      */       }
/*      */ 
/* 1116 */       stroke();
/*      */ 
/* 1118 */       if (color != null)
/* 1119 */         resetRGBColorStroke();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void closePath()
/*      */   {
/* 1130 */     if (this.inText) {
/* 1131 */       if (isTagged())
/* 1132 */         endText();
/*      */       else {
/* 1134 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/* 1137 */     this.content.append("h").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void newPath()
/*      */   {
/* 1145 */     if (this.inText) {
/* 1146 */       if (isTagged())
/* 1147 */         endText();
/*      */       else {
/* 1149 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/* 1152 */     this.content.append("n").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void stroke()
/*      */   {
/* 1160 */     if (this.inText) {
/* 1161 */       if (isTagged())
/* 1162 */         endText();
/*      */       else {
/* 1164 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/* 1167 */     PdfWriter.checkPdfIsoConformance(this.writer, 1, this.state.colorStroke);
/* 1168 */     PdfWriter.checkPdfIsoConformance(this.writer, 6, this.state.extGState);
/* 1169 */     this.content.append("S").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void closePathStroke()
/*      */   {
/* 1177 */     if (this.inText) {
/* 1178 */       if (isTagged())
/* 1179 */         endText();
/*      */       else {
/* 1181 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/* 1184 */     PdfWriter.checkPdfIsoConformance(this.writer, 1, this.state.colorStroke);
/* 1185 */     PdfWriter.checkPdfIsoConformance(this.writer, 6, this.state.extGState);
/* 1186 */     this.content.append("s").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void fill()
/*      */   {
/* 1194 */     if (this.inText) {
/* 1195 */       if (isTagged())
/* 1196 */         endText();
/*      */       else {
/* 1198 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/* 1201 */     PdfWriter.checkPdfIsoConformance(this.writer, 1, this.state.colorFill);
/* 1202 */     PdfWriter.checkPdfIsoConformance(this.writer, 6, this.state.extGState);
/* 1203 */     this.content.append("f").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void eoFill()
/*      */   {
/* 1211 */     if (this.inText) {
/* 1212 */       if (isTagged())
/* 1213 */         endText();
/*      */       else {
/* 1215 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/* 1218 */     PdfWriter.checkPdfIsoConformance(this.writer, 1, this.state.colorFill);
/* 1219 */     PdfWriter.checkPdfIsoConformance(this.writer, 6, this.state.extGState);
/* 1220 */     this.content.append("f*").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void fillStroke()
/*      */   {
/* 1228 */     if (this.inText) {
/* 1229 */       if (isTagged())
/* 1230 */         endText();
/*      */       else {
/* 1232 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/* 1235 */     PdfWriter.checkPdfIsoConformance(this.writer, 1, this.state.colorFill);
/* 1236 */     PdfWriter.checkPdfIsoConformance(this.writer, 1, this.state.colorStroke);
/* 1237 */     PdfWriter.checkPdfIsoConformance(this.writer, 6, this.state.extGState);
/* 1238 */     this.content.append("B").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void closePathFillStroke()
/*      */   {
/* 1246 */     if (this.inText) {
/* 1247 */       if (isTagged())
/* 1248 */         endText();
/*      */       else {
/* 1250 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/* 1253 */     PdfWriter.checkPdfIsoConformance(this.writer, 1, this.state.colorFill);
/* 1254 */     PdfWriter.checkPdfIsoConformance(this.writer, 1, this.state.colorStroke);
/* 1255 */     PdfWriter.checkPdfIsoConformance(this.writer, 6, this.state.extGState);
/* 1256 */     this.content.append("b").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void eoFillStroke()
/*      */   {
/* 1264 */     if (this.inText) {
/* 1265 */       if (isTagged())
/* 1266 */         endText();
/*      */       else {
/* 1268 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/* 1271 */     PdfWriter.checkPdfIsoConformance(this.writer, 1, this.state.colorFill);
/* 1272 */     PdfWriter.checkPdfIsoConformance(this.writer, 1, this.state.colorStroke);
/* 1273 */     PdfWriter.checkPdfIsoConformance(this.writer, 6, this.state.extGState);
/* 1274 */     this.content.append("B*").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void closePathEoFillStroke()
/*      */   {
/* 1282 */     if (this.inText) {
/* 1283 */       if (isTagged())
/* 1284 */         endText();
/*      */       else {
/* 1286 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("path.construction.operator.inside.text.object", new Object[0]));
/*      */       }
/*      */     }
/* 1289 */     PdfWriter.checkPdfIsoConformance(this.writer, 1, this.state.colorFill);
/* 1290 */     PdfWriter.checkPdfIsoConformance(this.writer, 1, this.state.colorStroke);
/* 1291 */     PdfWriter.checkPdfIsoConformance(this.writer, 6, this.state.extGState);
/* 1292 */     this.content.append("b*").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void addImage(Image image)
/*      */     throws DocumentException
/*      */   {
/* 1302 */     addImage(image, false);
/*      */   }
/*      */ 
/*      */   public void addImage(Image image, boolean inlineImage)
/*      */     throws DocumentException
/*      */   {
/* 1313 */     if (!image.hasAbsoluteY())
/* 1314 */       throw new DocumentException(MessageLocalization.getComposedMessage("the.image.must.have.absolute.positioning", new Object[0]));
/* 1315 */     float[] matrix = image.matrix();
/* 1316 */     matrix[4] = (image.getAbsoluteX() - matrix[4]);
/* 1317 */     matrix[5] = (image.getAbsoluteY() - matrix[5]);
/* 1318 */     addImage(image, matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5], inlineImage);
/*      */   }
/*      */ 
/*      */   public void addImage(Image image, float a, float b, float c, float d, float e, float f)
/*      */     throws DocumentException
/*      */   {
/* 1335 */     addImage(image, a, b, c, d, e, f, false);
/*      */   }
/*      */ 
/*      */   public void addImage(Image image, com.itextpdf.awt.geom.AffineTransform transform)
/*      */     throws DocumentException
/*      */   {
/* 1344 */     double[] matrix = new double[6];
/* 1345 */     transform.getMatrix(matrix);
/* 1346 */     addImage(image, (float)matrix[0], (float)matrix[1], (float)matrix[2], (float)matrix[3], (float)matrix[4], (float)matrix[5], false);
/*      */   }
/*      */ 
/*      */   public void addImage(Image image, float a, float b, float c, float d, float e, float f, boolean inlineImage)
/*      */     throws DocumentException
/*      */   {
/*      */     try
/*      */     {
/* 1366 */       if (image.getLayer() != null)
/* 1367 */         beginLayer(image.getLayer());
/* 1368 */       if (isTagged()) {
/* 1369 */         if (this.inText)
/* 1370 */           endText();
/* 1371 */         com.itextpdf.awt.geom.AffineTransform transform = new com.itextpdf.awt.geom.AffineTransform(a, b, c, d, e, f);
/* 1372 */         Point2D[] src = { new Point2D.Float(0.0F, 0.0F), new Point2D.Float(1.0F, 0.0F), new Point2D.Float(1.0F, 1.0F), new Point2D.Float(0.0F, 1.0F) };
/* 1373 */         Point2D[] dst = new Point2D.Float[4];
/* 1374 */         transform.transform(src, 0, dst, 0, 4);
/* 1375 */         float left = 3.4028235E+38F;
/* 1376 */         float right = -3.402824E+038F;
/* 1377 */         float bottom = 3.4028235E+38F;
/* 1378 */         float top = -3.402824E+038F;
/* 1379 */         for (int i = 0; i < 4; i++) {
/* 1380 */           if (dst[i].getX() < left)
/* 1381 */             left = (float)dst[i].getX();
/* 1382 */           if (dst[i].getX() > right)
/* 1383 */             right = (float)dst[i].getX();
/* 1384 */           if (dst[i].getY() < bottom)
/* 1385 */             bottom = (float)dst[i].getY();
/* 1386 */           if (dst[i].getY() > top)
/* 1387 */             top = (float)dst[i].getY();
/*      */         }
/* 1389 */         image.setAccessibleAttribute(PdfName.BBOX, new PdfArray(new float[] { left, bottom, right, top }));
/*      */       }
/* 1391 */       if ((this.writer != null) && (image.isImgTemplate())) {
/* 1392 */         this.writer.addDirectImageSimple(image);
/* 1393 */         PdfTemplate template = image.getTemplateData();
/* 1394 */         if (image.getAccessibleAttributes() != null) {
/* 1395 */           for (PdfName key : image.getAccessibleAttributes().keySet()) {
/* 1396 */             template.setAccessibleAttribute(key, image.getAccessibleAttribute(key));
/*      */           }
/*      */         }
/* 1399 */         float w = template.getWidth();
/* 1400 */         float h = template.getHeight();
/* 1401 */         addTemplate(template, a / w, b / w, c / h, d / h, e, f);
/*      */       }
/*      */       else {
/* 1404 */         this.content.append("q ");
/* 1405 */         this.content.append(a).append(' ');
/* 1406 */         this.content.append(b).append(' ');
/* 1407 */         this.content.append(c).append(' ');
/* 1408 */         this.content.append(d).append(' ');
/* 1409 */         this.content.append(e).append(' ');
/* 1410 */         this.content.append(f).append(" cm");
/* 1411 */         if (inlineImage) {
/* 1412 */           this.content.append("\nBI\n");
/* 1413 */           PdfImage pimage = new PdfImage(image, "", null);
/* 1414 */           if ((image instanceof ImgJBIG2)) {
/* 1415 */             byte[] globals = ((ImgJBIG2)image).getGlobalBytes();
/* 1416 */             if (globals != null) {
/* 1417 */               PdfDictionary decodeparms = new PdfDictionary();
/* 1418 */               decodeparms.put(PdfName.JBIG2GLOBALS, this.writer.getReferenceJBIG2Globals(globals));
/* 1419 */               pimage.put(PdfName.DECODEPARMS, decodeparms);
/*      */             }
/*      */           }
/* 1422 */           PdfWriter.checkPdfIsoConformance(this.writer, 17, pimage);
/* 1423 */           for (Object element : pimage.getKeys()) {
/* 1424 */             PdfName key = (PdfName)element;
/* 1425 */             PdfObject value = pimage.get(key);
/* 1426 */             String s = (String)abrev.get(key);
/* 1427 */             if (s != null)
/*      */             {
/* 1429 */               this.content.append(s);
/* 1430 */               boolean check = true;
/* 1431 */               if ((key.equals(PdfName.COLORSPACE)) && (value.isArray())) {
/* 1432 */                 PdfArray ar = (PdfArray)value;
/* 1433 */                 if ((ar.size() == 4) && (PdfName.INDEXED.equals(ar.getAsName(0))) && (ar.getPdfObject(1).isName()) && (ar.getPdfObject(2).isNumber()) && (ar.getPdfObject(3).isString()))
/*      */                 {
/* 1439 */                   check = false;
/*      */                 }
/*      */               }
/*      */ 
/* 1443 */               if ((check) && (key.equals(PdfName.COLORSPACE)) && (!value.isName())) {
/* 1444 */                 PdfName cs = this.writer.getColorspaceName();
/* 1445 */                 PageResources prs = getPageResources();
/* 1446 */                 prs.addColor(cs, this.writer.addToBody(value).getIndirectReference());
/* 1447 */                 value = cs;
/*      */               }
/* 1449 */               value.toPdf(null, this.content);
/* 1450 */               this.content.append('\n');
/*      */             }
/*      */           }
/* 1452 */           ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 1453 */           pimage.writeContent(baos);
/* 1454 */           byte[] imageBytes = baos.toByteArray();
/* 1455 */           this.content.append(String.format("/L %s\n", new Object[] { Integer.valueOf(imageBytes.length) }));
/*      */ 
/* 1461 */           this.content.append("ID\n");
/* 1462 */           this.content.append(imageBytes);
/* 1463 */           this.content.append("\nEI\nQ").append_i(this.separator);
/*      */         }
/*      */         else
/*      */         {
/* 1467 */           PageResources prs = getPageResources();
/* 1468 */           Image maskImage = image.getImageMask();
/* 1469 */           if (maskImage != null) {
/* 1470 */             PdfName name = this.writer.addDirectImageSimple(maskImage);
/* 1471 */             prs.addXObject(name, this.writer.getImageReference(name));
/*      */           }
/* 1473 */           PdfName name = this.writer.addDirectImageSimple(image);
/* 1474 */           name = prs.addXObject(name, this.writer.getImageReference(name));
/* 1475 */           this.content.append(' ').append(name.getBytes()).append(" Do Q").append_i(this.separator);
/*      */         }
/*      */       }
/* 1478 */       if (image.hasBorders()) {
/* 1479 */         saveState();
/* 1480 */         float w = image.getWidth();
/* 1481 */         float h = image.getHeight();
/* 1482 */         concatCTM(a / w, b / w, c / h, d / h, e, f);
/* 1483 */         rectangle(image);
/* 1484 */         restoreState();
/*      */       }
/* 1486 */       if (image.getLayer() != null)
/* 1487 */         endLayer();
/* 1488 */       Annotation annot = image.getAnnotation();
/* 1489 */       if (annot == null)
/* 1490 */         return;
/* 1491 */       float[] r = new float[unitRect.length];
/* 1492 */       for (int k = 0; k < unitRect.length; k += 2) {
/* 1493 */         r[k] = (a * unitRect[k] + c * unitRect[(k + 1)] + e);
/* 1494 */         r[(k + 1)] = (b * unitRect[k] + d * unitRect[(k + 1)] + f);
/*      */       }
/* 1496 */       float llx = r[0];
/* 1497 */       float lly = r[1];
/* 1498 */       float urx = llx;
/* 1499 */       float ury = lly;
/* 1500 */       for (int k = 2; k < r.length; k += 2) {
/* 1501 */         llx = Math.min(llx, r[k]);
/* 1502 */         lly = Math.min(lly, r[(k + 1)]);
/* 1503 */         urx = Math.max(urx, r[k]);
/* 1504 */         ury = Math.max(ury, r[(k + 1)]);
/*      */       }
/* 1506 */       annot = new Annotation(annot);
/* 1507 */       annot.setDimensions(llx, lly, urx, ury);
/* 1508 */       PdfAnnotation an = PdfAnnotationsImp.convertAnnotation(this.writer, annot, new Rectangle(llx, lly, urx, ury));
/* 1509 */       if (an == null)
/* 1510 */         return;
/* 1511 */       addAnnotation(an);
/*      */     }
/*      */     catch (Exception ee) {
/* 1514 */       throw new DocumentException(ee);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void reset()
/*      */   {
/* 1523 */     reset(true);
/*      */   }
/*      */ 
/*      */   public void reset(boolean validateContent)
/*      */   {
/* 1532 */     this.content.reset();
/* 1533 */     this.markedContentSize = 0;
/* 1534 */     if (validateContent) {
/* 1535 */       sanityCheck();
/*      */     }
/* 1537 */     this.state = new GraphicState();
/* 1538 */     this.stateList = new ArrayList();
/*      */   }
/*      */ 
/*      */   protected void beginText(boolean restoreTM)
/*      */   {
/* 1547 */     if (this.inText) {
/* 1548 */       if (!isTagged())
/*      */       {
/* 1551 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("unbalanced.begin.end.text.operators", new Object[0]));
/*      */       }
/*      */     } else {
/* 1554 */       this.inText = true;
/* 1555 */       this.content.append("BT").append_i(this.separator);
/* 1556 */       if (restoreTM) {
/* 1557 */         float xTLM = this.state.xTLM;
/* 1558 */         float tx = this.state.tx;
/* 1559 */         setTextMatrix(this.state.aTLM, this.state.bTLM, this.state.cTLM, this.state.dTLM, this.state.tx, this.state.yTLM);
/* 1560 */         this.state.xTLM = xTLM;
/* 1561 */         this.state.tx = tx;
/*      */       } else {
/* 1563 */         this.state.xTLM = 0.0F;
/* 1564 */         this.state.yTLM = 0.0F;
/* 1565 */         this.state.tx = 0.0F;
/*      */       }
/* 1567 */       if (isTagged())
/*      */         try {
/* 1569 */           restoreColor();
/*      */         }
/*      */         catch (IOException ioe)
/*      */         {
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void beginText()
/*      */   {
/* 1581 */     beginText(false);
/*      */   }
/*      */ 
/*      */   public void endText()
/*      */   {
/* 1588 */     if (!this.inText) {
/* 1589 */       if (!isTagged())
/*      */       {
/* 1592 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("unbalanced.begin.end.text.operators", new Object[0]));
/*      */       }
/*      */     } else {
/* 1595 */       this.inText = false;
/* 1596 */       this.content.append("ET").append_i(this.separator);
/* 1597 */       if (isTagged())
/*      */         try {
/* 1599 */           restoreColor();
/*      */         }
/*      */         catch (IOException ioe)
/*      */         {
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void saveState()
/*      */   {
/* 1613 */     PdfWriter.checkPdfIsoConformance(this.writer, 12, "q");
/* 1614 */     if ((this.inText) && (isTagged())) {
/* 1615 */       endText();
/*      */     }
/* 1617 */     this.content.append("q").append_i(this.separator);
/* 1618 */     this.stateList.add(new GraphicState(this.state));
/*      */   }
/*      */ 
/*      */   public void restoreState()
/*      */   {
/* 1626 */     PdfWriter.checkPdfIsoConformance(this.writer, 12, "Q");
/* 1627 */     if ((this.inText) && (isTagged())) {
/* 1628 */       endText();
/*      */     }
/* 1630 */     this.content.append("Q").append_i(this.separator);
/* 1631 */     int idx = this.stateList.size() - 1;
/* 1632 */     if (idx < 0)
/* 1633 */       throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("unbalanced.save.restore.state.operators", new Object[0]));
/* 1634 */     this.state.restore((GraphicState)this.stateList.get(idx));
/* 1635 */     this.stateList.remove(idx);
/*      */   }
/*      */ 
/*      */   public void setCharacterSpacing(float charSpace)
/*      */   {
/* 1644 */     if ((!this.inText) && (isTagged())) {
/* 1645 */       beginText(true);
/*      */     }
/* 1647 */     this.state.charSpace = charSpace;
/* 1648 */     this.content.append(charSpace).append(" Tc").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setWordSpacing(float wordSpace)
/*      */   {
/* 1657 */     if ((!this.inText) && (isTagged())) {
/* 1658 */       beginText(true);
/*      */     }
/* 1660 */     this.state.wordSpace = wordSpace;
/* 1661 */     this.content.append(wordSpace).append(" Tw").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setHorizontalScaling(float scale)
/*      */   {
/* 1670 */     if ((!this.inText) && (isTagged())) {
/* 1671 */       beginText(true);
/*      */     }
/* 1673 */     this.state.scale = scale;
/* 1674 */     this.content.append(scale).append(" Tz").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setLeading(float leading)
/*      */   {
/* 1686 */     if ((!this.inText) && (isTagged())) {
/* 1687 */       beginText(true);
/*      */     }
/* 1689 */     this.state.leading = leading;
/* 1690 */     this.content.append(leading).append(" TL").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setFontAndSize(BaseFont bf, float size)
/*      */   {
/* 1700 */     if ((!this.inText) && (isTagged())) {
/* 1701 */       beginText(true);
/*      */     }
/* 1703 */     checkWriter();
/* 1704 */     if ((size < 1.0E-004F) && (size > -1.0E-004F))
/* 1705 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("font.size.too.small.1", new Object[] { String.valueOf(size) }));
/* 1706 */     this.state.size = size;
/* 1707 */     this.state.fontDetails = this.writer.addSimple(bf);
/* 1708 */     PageResources prs = getPageResources();
/* 1709 */     PdfName name = this.state.fontDetails.getFontName();
/* 1710 */     name = prs.addFont(name, this.state.fontDetails.getIndirectReference());
/* 1711 */     this.content.append(name.getBytes()).append(' ').append(size).append(" Tf").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setTextRenderingMode(int rendering)
/*      */   {
/* 1720 */     if ((!this.inText) && (isTagged())) {
/* 1721 */       beginText(true);
/*      */     }
/* 1723 */     this.state.textRenderMode = rendering;
/* 1724 */     this.content.append(rendering).append(" Tr").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setTextRise(float rise)
/*      */   {
/* 1735 */     if ((!this.inText) && (isTagged())) {
/* 1736 */       beginText(true);
/*      */     }
/* 1738 */     this.content.append(rise).append(" Ts").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   private void showText2(String text)
/*      */   {
/* 1748 */     if (this.state.fontDetails == null)
/* 1749 */       throw new NullPointerException(MessageLocalization.getComposedMessage("font.and.size.must.be.set.before.writing.any.text", new Object[0]));
/* 1750 */     byte[] b = this.state.fontDetails.convertToBytes(text);
/* 1751 */     escapeString(b, this.content);
/*      */   }
/*      */ 
/*      */   public void showText(String text)
/*      */   {
/* 1760 */     checkState();
/* 1761 */     if ((!this.inText) && (isTagged())) {
/* 1762 */       beginText(true);
/*      */     }
/* 1764 */     showText2(text);
/* 1765 */     updateTx(text, 0.0F);
/* 1766 */     this.content.append("Tj").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public static PdfTextArray getKernArray(String text, BaseFont font)
/*      */   {
/* 1776 */     PdfTextArray pa = new PdfTextArray();
/* 1777 */     StringBuffer acc = new StringBuffer();
/* 1778 */     int len = text.length() - 1;
/* 1779 */     char[] c = text.toCharArray();
/* 1780 */     if (len >= 0)
/* 1781 */       acc.append(c, 0, 1);
/* 1782 */     for (int k = 0; k < len; k++) {
/* 1783 */       char c2 = c[(k + 1)];
/* 1784 */       int kern = font.getKerning(c[k], c2);
/* 1785 */       if (kern == 0) {
/* 1786 */         acc.append(c2);
/*      */       }
/*      */       else {
/* 1789 */         pa.add(acc.toString());
/* 1790 */         acc.setLength(0);
/* 1791 */         acc.append(c, k + 1, 1);
/* 1792 */         pa.add(-kern);
/*      */       }
/*      */     }
/* 1795 */     pa.add(acc.toString());
/* 1796 */     return pa;
/*      */   }
/*      */ 
/*      */   public void showTextKerned(String text)
/*      */   {
/* 1805 */     if (this.state.fontDetails == null)
/* 1806 */       throw new NullPointerException(MessageLocalization.getComposedMessage("font.and.size.must.be.set.before.writing.any.text", new Object[0]));
/* 1807 */     BaseFont bf = this.state.fontDetails.getBaseFont();
/* 1808 */     if (bf.hasKernPairs())
/* 1809 */       showText(getKernArray(text, bf));
/*      */     else
/* 1811 */       showText(text);
/*      */   }
/*      */ 
/*      */   public void newlineShowText(String text)
/*      */   {
/* 1821 */     checkState();
/* 1822 */     if ((!this.inText) && (isTagged())) {
/* 1823 */       beginText(true);
/*      */     }
/* 1825 */     this.state.yTLM -= this.state.leading;
/* 1826 */     showText2(text);
/* 1827 */     this.content.append("'").append_i(this.separator);
/* 1828 */     this.state.tx = this.state.xTLM;
/* 1829 */     updateTx(text, 0.0F);
/*      */   }
/*      */ 
/*      */   public void newlineShowText(float wordSpacing, float charSpacing, String text)
/*      */   {
/* 1840 */     checkState();
/* 1841 */     if ((!this.inText) && (isTagged())) {
/* 1842 */       beginText(true);
/*      */     }
/* 1844 */     this.state.yTLM -= this.state.leading;
/* 1845 */     this.content.append(wordSpacing).append(' ').append(charSpacing);
/* 1846 */     showText2(text);
/* 1847 */     this.content.append("\"").append_i(this.separator);
/*      */ 
/* 1850 */     this.state.charSpace = charSpacing;
/* 1851 */     this.state.wordSpace = wordSpacing;
/* 1852 */     this.state.tx = this.state.xTLM;
/* 1853 */     updateTx(text, 0.0F);
/*      */   }
/*      */ 
/*      */   public void setTextMatrix(float a, float b, float c, float d, float x, float y)
/*      */   {
/* 1869 */     if ((!this.inText) && (isTagged())) {
/* 1870 */       beginText(true);
/*      */     }
/* 1872 */     this.state.xTLM = x;
/* 1873 */     this.state.yTLM = y;
/* 1874 */     this.state.aTLM = a;
/* 1875 */     this.state.bTLM = b;
/* 1876 */     this.state.cTLM = c;
/* 1877 */     this.state.dTLM = d;
/* 1878 */     this.state.tx = this.state.xTLM;
/* 1879 */     this.content.append(a).append(' ').append(b).append_i(32).append(c).append_i(32).append(d).append_i(32).append(x).append_i(32).append(y).append(" Tm").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setTextMatrix(com.itextpdf.awt.geom.AffineTransform transform)
/*      */   {
/* 1890 */     double[] matrix = new double[6];
/* 1891 */     transform.getMatrix(matrix);
/* 1892 */     setTextMatrix((float)matrix[0], (float)matrix[1], (float)matrix[2], (float)matrix[3], (float)matrix[4], (float)matrix[5]);
/*      */   }
/*      */ 
/*      */   public void setTextMatrix(float x, float y)
/*      */   {
/* 1905 */     setTextMatrix(1.0F, 0.0F, 0.0F, 1.0F, x, y);
/*      */   }
/*      */ 
/*      */   public void moveText(float x, float y)
/*      */   {
/* 1915 */     if ((!this.inText) && (isTagged())) {
/* 1916 */       beginText(true);
/*      */     }
/* 1918 */     this.state.xTLM += x;
/* 1919 */     this.state.yTLM += y;
/* 1920 */     if ((isTagged()) && (this.state.xTLM != this.state.tx))
/* 1921 */       setTextMatrix(this.state.aTLM, this.state.bTLM, this.state.cTLM, this.state.dTLM, this.state.xTLM, this.state.yTLM);
/*      */     else
/* 1923 */       this.content.append(x).append(' ').append(y).append(" Td").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void moveTextWithLeading(float x, float y)
/*      */   {
/* 1936 */     if ((!this.inText) && (isTagged())) {
/* 1937 */       beginText(true);
/*      */     }
/* 1939 */     this.state.xTLM += x;
/* 1940 */     this.state.yTLM += y;
/* 1941 */     this.state.leading = (-y);
/* 1942 */     if ((isTagged()) && (this.state.xTLM != this.state.tx))
/* 1943 */       setTextMatrix(this.state.aTLM, this.state.bTLM, this.state.cTLM, this.state.dTLM, this.state.xTLM, this.state.yTLM);
/*      */     else
/* 1945 */       this.content.append(x).append(' ').append(y).append(" TD").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void newlineText()
/*      */   {
/* 1953 */     if ((!this.inText) && (isTagged())) {
/* 1954 */       beginText(true);
/*      */     }
/* 1956 */     if ((isTagged()) && (this.state.xTLM != this.state.tx)) {
/* 1957 */       setTextMatrix(this.state.aTLM, this.state.bTLM, this.state.cTLM, this.state.dTLM, this.state.xTLM, this.state.yTLM);
/*      */     }
/* 1959 */     this.state.yTLM -= this.state.leading;
/* 1960 */     this.content.append("T*").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   int size()
/*      */   {
/* 1969 */     return size(true);
/*      */   }
/*      */ 
/*      */   int size(boolean includeMarkedContentSize) {
/* 1973 */     if (includeMarkedContentSize) {
/* 1974 */       return this.content.size();
/*      */     }
/* 1976 */     return this.content.size() - this.markedContentSize;
/*      */   }
/*      */ 
/*      */   static byte[] escapeString(byte[] b)
/*      */   {
/* 1986 */     ByteBuffer content = new ByteBuffer();
/* 1987 */     escapeString(b, content);
/* 1988 */     return content.toByteArray();
/*      */   }
/*      */ 
/*      */   static void escapeString(byte[] b, ByteBuffer content)
/*      */   {
/* 1998 */     content.append_i(40);
/* 1999 */     for (int k = 0; k < b.length; k++) {
/* 2000 */       byte c = b[k];
/* 2001 */       switch (c) {
/*      */       case 13:
/* 2003 */         content.append("\\r");
/* 2004 */         break;
/*      */       case 10:
/* 2006 */         content.append("\\n");
/* 2007 */         break;
/*      */       case 9:
/* 2009 */         content.append("\\t");
/* 2010 */         break;
/*      */       case 8:
/* 2012 */         content.append("\\b");
/* 2013 */         break;
/*      */       case 12:
/* 2015 */         content.append("\\f");
/* 2016 */         break;
/*      */       case 40:
/*      */       case 41:
/*      */       case 92:
/* 2020 */         content.append_i(92).append_i(c);
/* 2021 */         break;
/*      */       default:
/* 2023 */         content.append_i(c);
/*      */       }
/*      */     }
/* 2026 */     content.append(")");
/*      */   }
/*      */ 
/*      */   public void addOutline(PdfOutline outline, String name)
/*      */   {
/* 2036 */     checkWriter();
/* 2037 */     this.pdf.addOutline(outline, name);
/*      */   }
/*      */ 
/*      */   public PdfOutline getRootOutline()
/*      */   {
/* 2045 */     checkWriter();
/* 2046 */     return this.pdf.getRootOutline();
/*      */   }
/*      */ 
/*      */   public float getEffectiveStringWidth(String text, boolean kerned)
/*      */   {
/* 2060 */     BaseFont bf = this.state.fontDetails.getBaseFont();
/*      */     float w;
/*      */     float w;
/* 2063 */     if (kerned)
/* 2064 */       w = bf.getWidthPointKerned(text, this.state.size);
/*      */     else {
/* 2066 */       w = bf.getWidthPoint(text, this.state.size);
/*      */     }
/* 2068 */     if ((this.state.charSpace != 0.0F) && (text.length() > 1)) {
/* 2069 */       w += this.state.charSpace * (text.length() - 1);
/*      */     }
/*      */ 
/* 2072 */     if ((this.state.wordSpace != 0.0F) && (!bf.isVertical())) {
/* 2073 */       for (int i = 0; i < text.length() - 1; i++) {
/* 2074 */         if (text.charAt(i) == ' ')
/* 2075 */           w += this.state.wordSpace;
/*      */       }
/*      */     }
/* 2078 */     if (this.state.scale != 100.0D) {
/* 2079 */       w = w * this.state.scale / 100.0F;
/*      */     }
/*      */ 
/* 2082 */     return w;
/*      */   }
/*      */ 
/*      */   private float getEffectiveStringWidth(String text, boolean kerned, float kerning)
/*      */   {
/* 2097 */     BaseFont bf = this.state.fontDetails.getBaseFont();
/*      */     float w;
/*      */     float w;
/* 2099 */     if (kerned)
/* 2100 */       w = bf.getWidthPointKerned(text, this.state.size);
/*      */     else
/* 2102 */       w = bf.getWidthPoint(text, this.state.size);
/* 2103 */     if ((this.state.charSpace != 0.0F) && (text.length() > 0)) {
/* 2104 */       w += this.state.charSpace * text.length();
/*      */     }
/* 2106 */     if ((this.state.wordSpace != 0.0F) && (!bf.isVertical())) {
/* 2107 */       for (int i = 0; i < text.length(); i++) {
/* 2108 */         if (text.charAt(i) == ' ')
/* 2109 */           w += this.state.wordSpace;
/*      */       }
/*      */     }
/* 2112 */     w -= kerning / 1000.0F * this.state.size;
/* 2113 */     if (this.state.scale != 100.0D)
/* 2114 */       w = w * this.state.scale / 100.0F;
/* 2115 */     return w;
/*      */   }
/*      */ 
/*      */   public void showTextAligned(int alignment, String text, float x, float y, float rotation)
/*      */   {
/* 2127 */     showTextAligned(alignment, text, x, y, rotation, false);
/*      */   }
/*      */ 
/*      */   private void showTextAligned(int alignment, String text, float x, float y, float rotation, boolean kerned) {
/* 2131 */     if (this.state.fontDetails == null)
/* 2132 */       throw new NullPointerException(MessageLocalization.getComposedMessage("font.and.size.must.be.set.before.writing.any.text", new Object[0]));
/* 2133 */     if (rotation == 0.0F) {
/* 2134 */       switch (alignment) {
/*      */       case 1:
/* 2136 */         x -= getEffectiveStringWidth(text, kerned) / 2.0F;
/* 2137 */         break;
/*      */       case 2:
/* 2139 */         x -= getEffectiveStringWidth(text, kerned);
/*      */       }
/*      */ 
/* 2142 */       setTextMatrix(x, y);
/* 2143 */       if (kerned)
/* 2144 */         showTextKerned(text);
/*      */       else
/* 2146 */         showText(text);
/*      */     }
/*      */     else {
/* 2149 */       double alpha = rotation * 3.141592653589793D / 180.0D;
/* 2150 */       float cos = (float)Math.cos(alpha);
/* 2151 */       float sin = (float)Math.sin(alpha);
/*      */       float len;
/* 2153 */       switch (alignment) {
/*      */       case 1:
/* 2155 */         len = getEffectiveStringWidth(text, kerned) / 2.0F;
/* 2156 */         x -= len * cos;
/* 2157 */         y -= len * sin;
/* 2158 */         break;
/*      */       case 2:
/* 2160 */         len = getEffectiveStringWidth(text, kerned);
/* 2161 */         x -= len * cos;
/* 2162 */         y -= len * sin;
/*      */       }
/*      */ 
/* 2165 */       setTextMatrix(cos, sin, -sin, cos, x, y);
/* 2166 */       if (kerned)
/* 2167 */         showTextKerned(text);
/*      */       else
/* 2169 */         showText(text);
/* 2170 */       setTextMatrix(0.0F, 0.0F);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void showTextAlignedKerned(int alignment, String text, float x, float y, float rotation)
/*      */   {
/* 2183 */     showTextAligned(alignment, text, x, y, rotation, true);
/*      */   }
/*      */ 
/*      */   public void concatCTM(float a, float b, float c, float d, float e, float f)
/*      */   {
/* 2211 */     if ((this.inText) && (isTagged())) {
/* 2212 */       endText();
/*      */     }
/* 2214 */     this.state.CTM.concatenate(new com.itextpdf.awt.geom.AffineTransform(a, b, c, d, e, f));
/* 2215 */     this.content.append(a).append(' ').append(b).append(' ').append(c).append(' ');
/* 2216 */     this.content.append(d).append(' ').append(e).append(' ').append(f).append(" cm").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void concatCTM(com.itextpdf.awt.geom.AffineTransform transform)
/*      */   {
/* 2224 */     double[] matrix = new double[6];
/* 2225 */     transform.getMatrix(matrix);
/* 2226 */     concatCTM((float)matrix[0], (float)matrix[1], (float)matrix[2], (float)matrix[3], (float)matrix[4], (float)matrix[5]);
/*      */   }
/*      */ 
/*      */   public static ArrayList<float[]> bezierArc(float x1, float y1, float x2, float y2, float startAng, float extent)
/*      */   {
/* 2256 */     if (x1 > x2) {
/* 2257 */       float tmp = x1;
/* 2258 */       x1 = x2;
/* 2259 */       x2 = tmp;
/*      */     }
/* 2261 */     if (y2 > y1) {
/* 2262 */       float tmp = y1;
/* 2263 */       y1 = y2;
/* 2264 */       y2 = tmp;
/*      */     }
/*      */     int Nfrag;
/*      */     int Nfrag;
/*      */     float fragAngle;
/* 2269 */     if (Math.abs(extent) <= 90.0F) {
/* 2270 */       float fragAngle = extent;
/* 2271 */       Nfrag = 1;
/*      */     }
/*      */     else {
/* 2274 */       Nfrag = (int)Math.ceil(Math.abs(extent) / 90.0F);
/* 2275 */       fragAngle = extent / Nfrag;
/*      */     }
/* 2277 */     float x_cen = (x1 + x2) / 2.0F;
/* 2278 */     float y_cen = (y1 + y2) / 2.0F;
/* 2279 */     float rx = (x2 - x1) / 2.0F;
/* 2280 */     float ry = (y2 - y1) / 2.0F;
/* 2281 */     float halfAng = (float)(fragAngle * 3.141592653589793D / 360.0D);
/* 2282 */     float kappa = (float)Math.abs(1.333333333333333D * (1.0D - Math.cos(halfAng)) / Math.sin(halfAng));
/* 2283 */     ArrayList pointList = new ArrayList();
/* 2284 */     for (int i = 0; i < Nfrag; i++) {
/* 2285 */       float theta0 = (float)((startAng + i * fragAngle) * 3.141592653589793D / 180.0D);
/* 2286 */       float theta1 = (float)((startAng + (i + 1) * fragAngle) * 3.141592653589793D / 180.0D);
/* 2287 */       float cos0 = (float)Math.cos(theta0);
/* 2288 */       float cos1 = (float)Math.cos(theta1);
/* 2289 */       float sin0 = (float)Math.sin(theta0);
/* 2290 */       float sin1 = (float)Math.sin(theta1);
/* 2291 */       if (fragAngle > 0.0F) {
/* 2292 */         pointList.add(new float[] { x_cen + rx * cos0, y_cen - ry * sin0, x_cen + rx * (cos0 - kappa * sin0), y_cen - ry * (sin0 + kappa * cos0), x_cen + rx * (cos1 + kappa * sin1), y_cen - ry * (sin1 - kappa * cos1), x_cen + rx * cos1, y_cen - ry * sin1 });
/*      */       }
/*      */       else
/*      */       {
/* 2302 */         pointList.add(new float[] { x_cen + rx * cos0, y_cen - ry * sin0, x_cen + rx * (cos0 + kappa * sin0), y_cen - ry * (sin0 - kappa * cos0), x_cen + rx * (cos1 - kappa * sin1), y_cen - ry * (sin1 + kappa * cos1), x_cen + rx * cos1, y_cen - ry * sin1 });
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2312 */     return pointList;
/*      */   }
/*      */ 
/*      */   public void arc(float x1, float y1, float x2, float y2, float startAng, float extent)
/*      */   {
/* 2328 */     ArrayList ar = bezierArc(x1, y1, x2, y2, startAng, extent);
/* 2329 */     if (ar.isEmpty())
/* 2330 */       return;
/* 2331 */     float[] pt = (float[])ar.get(0);
/* 2332 */     moveTo(pt[0], pt[1]);
/* 2333 */     for (int k = 0; k < ar.size(); k++) {
/* 2334 */       pt = (float[])ar.get(k);
/* 2335 */       curveTo(pt[2], pt[3], pt[4], pt[5], pt[6], pt[7]);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void ellipse(float x1, float y1, float x2, float y2)
/*      */   {
/* 2348 */     arc(x1, y1, x2, y2, 0.0F, 360.0F);
/*      */   }
/*      */ 
/*      */   public PdfPatternPainter createPattern(float width, float height, float xstep, float ystep)
/*      */   {
/* 2363 */     checkWriter();
/* 2364 */     if ((xstep == 0.0F) || (ystep == 0.0F))
/* 2365 */       throw new RuntimeException(MessageLocalization.getComposedMessage("xstep.or.ystep.can.not.be.zero", new Object[0]));
/* 2366 */     PdfPatternPainter painter = new PdfPatternPainter(this.writer);
/* 2367 */     painter.setWidth(width);
/* 2368 */     painter.setHeight(height);
/* 2369 */     painter.setXStep(xstep);
/* 2370 */     painter.setYStep(ystep);
/* 2371 */     this.writer.addSimplePattern(painter);
/* 2372 */     return painter;
/*      */   }
/*      */ 
/*      */   public PdfPatternPainter createPattern(float width, float height)
/*      */   {
/* 2383 */     return createPattern(width, height, width, height);
/*      */   }
/*      */ 
/*      */   public PdfPatternPainter createPattern(float width, float height, float xstep, float ystep, BaseColor color)
/*      */   {
/* 2399 */     checkWriter();
/* 2400 */     if ((xstep == 0.0F) || (ystep == 0.0F))
/* 2401 */       throw new RuntimeException(MessageLocalization.getComposedMessage("xstep.or.ystep.can.not.be.zero", new Object[0]));
/* 2402 */     PdfPatternPainter painter = new PdfPatternPainter(this.writer, color);
/* 2403 */     painter.setWidth(width);
/* 2404 */     painter.setHeight(height);
/* 2405 */     painter.setXStep(xstep);
/* 2406 */     painter.setYStep(ystep);
/* 2407 */     this.writer.addSimplePattern(painter);
/* 2408 */     return painter;
/*      */   }
/*      */ 
/*      */   public PdfPatternPainter createPattern(float width, float height, BaseColor color)
/*      */   {
/* 2421 */     return createPattern(width, height, width, height, color);
/*      */   }
/*      */ 
/*      */   public PdfTemplate createTemplate(float width, float height)
/*      */   {
/* 2437 */     return createTemplate(width, height, null);
/*      */   }
/*      */ 
/*      */   PdfTemplate createTemplate(float width, float height, PdfName forcedName) {
/* 2441 */     checkWriter();
/* 2442 */     PdfTemplate template = new PdfTemplate(this.writer);
/* 2443 */     template.setWidth(width);
/* 2444 */     template.setHeight(height);
/* 2445 */     this.writer.addDirectTemplateSimple(template, forcedName);
/* 2446 */     return template;
/*      */   }
/*      */ 
/*      */   public PdfAppearance createAppearance(float width, float height)
/*      */   {
/* 2457 */     return createAppearance(width, height, null);
/*      */   }
/*      */ 
/*      */   PdfAppearance createAppearance(float width, float height, PdfName forcedName) {
/* 2461 */     checkWriter();
/* 2462 */     PdfAppearance template = new PdfAppearance(this.writer);
/* 2463 */     template.setWidth(width);
/* 2464 */     template.setHeight(height);
/* 2465 */     this.writer.addDirectTemplateSimple(template, forcedName);
/* 2466 */     return template;
/*      */   }
/*      */ 
/*      */   public void addPSXObject(PdfPSXObject psobject)
/*      */   {
/* 2475 */     if ((this.inText) && (isTagged())) {
/* 2476 */       endText();
/*      */     }
/* 2478 */     checkWriter();
/* 2479 */     PdfName name = this.writer.addDirectTemplateSimple(psobject, null);
/* 2480 */     PageResources prs = getPageResources();
/* 2481 */     name = prs.addXObject(name, psobject.getIndirectReference());
/* 2482 */     this.content.append(name.getBytes()).append(" Do").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void addTemplate(PdfTemplate template, float a, float b, float c, float d, float e, float f)
/*      */   {
/* 2497 */     addTemplate(template, a, b, c, d, e, f, false);
/*      */   }
/*      */ 
/*      */   public void addTemplate(PdfTemplate template, float a, float b, float c, float d, float e, float f, boolean tagContent)
/*      */   {
/* 2514 */     checkWriter();
/* 2515 */     checkNoPattern(template);
/* 2516 */     PdfWriter.checkPdfIsoConformance(this.writer, 20, template);
/* 2517 */     PdfName name = this.writer.addDirectTemplateSimple(template, null);
/* 2518 */     PageResources prs = getPageResources();
/* 2519 */     name = prs.addXObject(name, template.getIndirectReference());
/* 2520 */     if (isTagged()) {
/* 2521 */       if (this.inText)
/* 2522 */         endText();
/* 2523 */       if ((template.isContentTagged()) || ((template.getPageReference() != null) && (tagContent))) {
/* 2524 */         throw new RuntimeException(MessageLocalization.getComposedMessage("template.with.tagged.could.not.be.used.more.than.once", new Object[0]));
/*      */       }
/*      */ 
/* 2527 */       template.setPageReference(this.writer.getCurrentPage());
/*      */ 
/* 2529 */       if (tagContent) {
/* 2530 */         template.setContentTagged(true);
/* 2531 */         ArrayList allMcElements = getMcElements();
/* 2532 */         if ((allMcElements != null) && (allMcElements.size() > 0))
/* 2533 */           template.getMcElements().add(allMcElements.get(allMcElements.size() - 1));
/*      */       } else {
/* 2535 */         openMCBlock(template);
/*      */       }
/*      */     }
/*      */ 
/* 2539 */     this.content.append("q ");
/* 2540 */     this.content.append(a).append(' ');
/* 2541 */     this.content.append(b).append(' ');
/* 2542 */     this.content.append(c).append(' ');
/* 2543 */     this.content.append(d).append(' ');
/* 2544 */     this.content.append(e).append(' ');
/* 2545 */     this.content.append(f).append(" cm ");
/* 2546 */     this.content.append(name.getBytes()).append(" Do Q").append_i(this.separator);
/*      */ 
/* 2548 */     if ((isTagged()) && (!tagContent)) {
/* 2549 */       closeMCBlock(template);
/* 2550 */       template.setId(null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addFormXObj(PdfStream formXObj, PdfName name, float a, float b, float c, float d, float e, float f)
/*      */     throws IOException
/*      */   {
/* 2567 */     checkWriter();
/* 2568 */     PdfWriter.checkPdfIsoConformance(this.writer, 9, formXObj);
/* 2569 */     PageResources prs = getPageResources();
/* 2570 */     prs.addXObject(name, this.writer.addToBody(formXObj).getIndirectReference());
/* 2571 */     PdfArtifact artifact = null;
/* 2572 */     if (isTagged()) {
/* 2573 */       if (this.inText)
/* 2574 */         endText();
/* 2575 */       artifact = new PdfArtifact();
/* 2576 */       openMCBlock(artifact);
/*      */     }
/*      */ 
/* 2579 */     this.content.append("q ");
/* 2580 */     this.content.append(a).append(' ');
/* 2581 */     this.content.append(b).append(' ');
/* 2582 */     this.content.append(c).append(' ');
/* 2583 */     this.content.append(d).append(' ');
/* 2584 */     this.content.append(e).append(' ');
/* 2585 */     this.content.append(f).append(" cm ");
/* 2586 */     this.content.append(name.getBytes()).append(" Do Q").append_i(this.separator);
/*      */ 
/* 2588 */     if (isTagged())
/* 2589 */       closeMCBlock(artifact);
/*      */   }
/*      */ 
/*      */   public void addTemplate(PdfTemplate template, com.itextpdf.awt.geom.AffineTransform transform)
/*      */   {
/* 2599 */     addTemplate(template, transform, false);
/*      */   }
/*      */ 
/*      */   public void addTemplate(PdfTemplate template, com.itextpdf.awt.geom.AffineTransform transform, boolean tagContent)
/*      */   {
/* 2610 */     double[] matrix = new double[6];
/* 2611 */     transform.getMatrix(matrix);
/* 2612 */     addTemplate(template, (float)matrix[0], (float)matrix[1], (float)matrix[2], (float)matrix[3], (float)matrix[4], (float)matrix[5], tagContent);
/*      */   }
/*      */ 
/*      */   void addTemplateReference(PdfIndirectReference template, PdfName name, float a, float b, float c, float d, float e, float f)
/*      */   {
/* 2617 */     if ((this.inText) && (isTagged())) {
/* 2618 */       endText();
/*      */     }
/* 2620 */     checkWriter();
/* 2621 */     PageResources prs = getPageResources();
/* 2622 */     name = prs.addXObject(name, template);
/* 2623 */     this.content.append("q ");
/* 2624 */     this.content.append(a).append(' ');
/* 2625 */     this.content.append(b).append(' ');
/* 2626 */     this.content.append(c).append(' ');
/* 2627 */     this.content.append(d).append(' ');
/* 2628 */     this.content.append(e).append(' ');
/* 2629 */     this.content.append(f).append(" cm ");
/* 2630 */     this.content.append(name.getBytes()).append(" Do Q").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void addTemplate(PdfTemplate template, float x, float y)
/*      */   {
/* 2641 */     addTemplate(template, 1.0F, 0.0F, 0.0F, 1.0F, x, y);
/*      */   }
/*      */ 
/*      */   public void addTemplate(PdfTemplate template, float x, float y, boolean tagContent) {
/* 2645 */     addTemplate(template, 1.0F, 0.0F, 0.0F, 1.0F, x, y, tagContent);
/*      */   }
/*      */ 
/*      */   public void setCMYKColorFill(int cyan, int magenta, int yellow, int black)
/*      */   {
/* 2667 */     saveColor(new CMYKColor(cyan, magenta, yellow, black), true);
/* 2668 */     this.content.append((cyan & 0xFF) / 255.0F);
/* 2669 */     this.content.append(' ');
/* 2670 */     this.content.append((magenta & 0xFF) / 255.0F);
/* 2671 */     this.content.append(' ');
/* 2672 */     this.content.append((yellow & 0xFF) / 255.0F);
/* 2673 */     this.content.append(' ');
/* 2674 */     this.content.append((black & 0xFF) / 255.0F);
/* 2675 */     this.content.append(" k").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setCMYKColorStroke(int cyan, int magenta, int yellow, int black)
/*      */   {
/* 2695 */     saveColor(new CMYKColor(cyan, magenta, yellow, black), false);
/* 2696 */     this.content.append((cyan & 0xFF) / 255.0F);
/* 2697 */     this.content.append(' ');
/* 2698 */     this.content.append((magenta & 0xFF) / 255.0F);
/* 2699 */     this.content.append(' ');
/* 2700 */     this.content.append((yellow & 0xFF) / 255.0F);
/* 2701 */     this.content.append(' ');
/* 2702 */     this.content.append((black & 0xFF) / 255.0F);
/* 2703 */     this.content.append(" K").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setRGBColorFill(int red, int green, int blue)
/*      */   {
/* 2724 */     saveColor(new BaseColor(red, green, blue), true);
/* 2725 */     HelperRGB((red & 0xFF) / 255.0F, (green & 0xFF) / 255.0F, (blue & 0xFF) / 255.0F);
/* 2726 */     this.content.append(" rg").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setRGBColorStroke(int red, int green, int blue)
/*      */   {
/* 2746 */     saveColor(new BaseColor(red, green, blue), false);
/* 2747 */     HelperRGB((red & 0xFF) / 255.0F, (green & 0xFF) / 255.0F, (blue & 0xFF) / 255.0F);
/* 2748 */     this.content.append(" RG").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setColorStroke(BaseColor color)
/*      */   {
/* 2756 */     int type = ExtendedColor.getType(color);
/* 2757 */     switch (type) {
/*      */     case 1:
/* 2759 */       setGrayStroke(((GrayColor)color).getGray());
/* 2760 */       break;
/*      */     case 2:
/* 2763 */       CMYKColor cmyk = (CMYKColor)color;
/* 2764 */       setCMYKColorStrokeF(cmyk.getCyan(), cmyk.getMagenta(), cmyk.getYellow(), cmyk.getBlack());
/* 2765 */       break;
/*      */     case 3:
/* 2768 */       SpotColor spot = (SpotColor)color;
/* 2769 */       setColorStroke(spot.getPdfSpotColor(), spot.getTint());
/* 2770 */       break;
/*      */     case 4:
/* 2773 */       PatternColor pat = (PatternColor)color;
/* 2774 */       setPatternStroke(pat.getPainter());
/* 2775 */       break;
/*      */     case 5:
/* 2778 */       ShadingColor shading = (ShadingColor)color;
/* 2779 */       setShadingStroke(shading.getPdfShadingPattern());
/* 2780 */       break;
/*      */     case 6:
/* 2783 */       DeviceNColor devicen = (DeviceNColor)color;
/* 2784 */       setColorStroke(devicen.getPdfDeviceNColor(), devicen.getTints());
/* 2785 */       break;
/*      */     case 7:
/* 2788 */       LabColor lab = (LabColor)color;
/* 2789 */       setColorStroke(lab.getLabColorSpace(), lab.getL(), lab.getA(), lab.getB());
/* 2790 */       break;
/*      */     default:
/* 2793 */       setRGBColorStroke(color.getRed(), color.getGreen(), color.getBlue());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setColorFill(BaseColor color)
/*      */   {
/* 2802 */     int type = ExtendedColor.getType(color);
/* 2803 */     switch (type) {
/*      */     case 1:
/* 2805 */       setGrayFill(((GrayColor)color).getGray());
/* 2806 */       break;
/*      */     case 2:
/* 2809 */       CMYKColor cmyk = (CMYKColor)color;
/* 2810 */       setCMYKColorFillF(cmyk.getCyan(), cmyk.getMagenta(), cmyk.getYellow(), cmyk.getBlack());
/* 2811 */       break;
/*      */     case 3:
/* 2814 */       SpotColor spot = (SpotColor)color;
/* 2815 */       setColorFill(spot.getPdfSpotColor(), spot.getTint());
/* 2816 */       break;
/*      */     case 4:
/* 2819 */       PatternColor pat = (PatternColor)color;
/* 2820 */       setPatternFill(pat.getPainter());
/* 2821 */       break;
/*      */     case 5:
/* 2824 */       ShadingColor shading = (ShadingColor)color;
/* 2825 */       setShadingFill(shading.getPdfShadingPattern());
/* 2826 */       break;
/*      */     case 6:
/* 2829 */       DeviceNColor devicen = (DeviceNColor)color;
/* 2830 */       setColorFill(devicen.getPdfDeviceNColor(), devicen.getTints());
/* 2831 */       break;
/*      */     case 7:
/* 2834 */       LabColor lab = (LabColor)color;
/* 2835 */       setColorFill(lab.getLabColorSpace(), lab.getL(), lab.getA(), lab.getB());
/* 2836 */       break;
/*      */     default:
/* 2839 */       setRGBColorFill(color.getRed(), color.getGreen(), color.getBlue());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setColorFill(PdfSpotColor sp, float tint)
/*      */   {
/* 2849 */     checkWriter();
/* 2850 */     this.state.colorDetails = this.writer.addSimple(sp);
/* 2851 */     PageResources prs = getPageResources();
/* 2852 */     PdfName name = this.state.colorDetails.getColorSpaceName();
/* 2853 */     name = prs.addColor(name, this.state.colorDetails.getIndirectReference());
/* 2854 */     saveColor(new SpotColor(sp, tint), true);
/* 2855 */     this.content.append(name.getBytes()).append(" cs ").append(tint).append(" scn").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setColorFill(PdfDeviceNColor dn, float[] tints) {
/* 2859 */     checkWriter();
/* 2860 */     this.state.colorDetails = this.writer.addSimple(dn);
/* 2861 */     PageResources prs = getPageResources();
/* 2862 */     PdfName name = this.state.colorDetails.getColorSpaceName();
/* 2863 */     name = prs.addColor(name, this.state.colorDetails.getIndirectReference());
/* 2864 */     saveColor(new DeviceNColor(dn, tints), true);
/* 2865 */     this.content.append(name.getBytes()).append(" cs ");
/* 2866 */     for (float tint : tints)
/* 2867 */       this.content.append(tint + " ");
/* 2868 */     this.content.append("scn").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setColorFill(PdfLabColor lab, float l, float a, float b) {
/* 2872 */     checkWriter();
/* 2873 */     this.state.colorDetails = this.writer.addSimple(lab);
/* 2874 */     PageResources prs = getPageResources();
/* 2875 */     PdfName name = this.state.colorDetails.getColorSpaceName();
/* 2876 */     name = prs.addColor(name, this.state.colorDetails.getIndirectReference());
/* 2877 */     saveColor(new LabColor(lab, l, a, b), true);
/* 2878 */     this.content.append(name.getBytes()).append(" cs ");
/* 2879 */     this.content.append(l + " " + a + " " + b + " ");
/* 2880 */     this.content.append("scn").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setColorStroke(PdfSpotColor sp, float tint)
/*      */   {
/* 2889 */     checkWriter();
/* 2890 */     this.state.colorDetails = this.writer.addSimple(sp);
/* 2891 */     PageResources prs = getPageResources();
/* 2892 */     PdfName name = this.state.colorDetails.getColorSpaceName();
/* 2893 */     name = prs.addColor(name, this.state.colorDetails.getIndirectReference());
/* 2894 */     saveColor(new SpotColor(sp, tint), false);
/* 2895 */     this.content.append(name.getBytes()).append(" CS ").append(tint).append(" SCN").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setColorStroke(PdfDeviceNColor sp, float[] tints) {
/* 2899 */     checkWriter();
/* 2900 */     this.state.colorDetails = this.writer.addSimple(sp);
/* 2901 */     PageResources prs = getPageResources();
/* 2902 */     PdfName name = this.state.colorDetails.getColorSpaceName();
/* 2903 */     name = prs.addColor(name, this.state.colorDetails.getIndirectReference());
/* 2904 */     saveColor(new DeviceNColor(sp, tints), true);
/* 2905 */     this.content.append(name.getBytes()).append(" CS ");
/* 2906 */     for (float tint : tints)
/* 2907 */       this.content.append(tint + " ");
/* 2908 */     this.content.append("SCN").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setColorStroke(PdfLabColor lab, float l, float a, float b) {
/* 2912 */     checkWriter();
/* 2913 */     this.state.colorDetails = this.writer.addSimple(lab);
/* 2914 */     PageResources prs = getPageResources();
/* 2915 */     PdfName name = this.state.colorDetails.getColorSpaceName();
/* 2916 */     name = prs.addColor(name, this.state.colorDetails.getIndirectReference());
/* 2917 */     saveColor(new LabColor(lab, l, a, b), true);
/* 2918 */     this.content.append(name.getBytes()).append(" CS ");
/* 2919 */     this.content.append(l + " " + a + " " + b + " ");
/* 2920 */     this.content.append("SCN").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setPatternFill(PdfPatternPainter p)
/*      */   {
/* 2928 */     if (p.isStencil()) {
/* 2929 */       setPatternFill(p, p.getDefaultColor());
/* 2930 */       return;
/*      */     }
/* 2932 */     checkWriter();
/* 2933 */     PageResources prs = getPageResources();
/* 2934 */     PdfName name = this.writer.addSimplePattern(p);
/* 2935 */     name = prs.addPattern(name, p.getIndirectReference());
/* 2936 */     saveColor(new PatternColor(p), true);
/* 2937 */     this.content.append(PdfName.PATTERN.getBytes()).append(" cs ").append(name.getBytes()).append(" scn").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   void outputColorNumbers(BaseColor color, float tint)
/*      */   {
/* 2945 */     PdfWriter.checkPdfIsoConformance(this.writer, 1, color);
/* 2946 */     int type = ExtendedColor.getType(color);
/* 2947 */     switch (type) {
/*      */     case 0:
/* 2949 */       this.content.append(color.getRed() / 255.0F);
/* 2950 */       this.content.append(' ');
/* 2951 */       this.content.append(color.getGreen() / 255.0F);
/* 2952 */       this.content.append(' ');
/* 2953 */       this.content.append(color.getBlue() / 255.0F);
/* 2954 */       break;
/*      */     case 1:
/* 2956 */       this.content.append(((GrayColor)color).getGray());
/* 2957 */       break;
/*      */     case 2:
/* 2959 */       CMYKColor cmyk = (CMYKColor)color;
/* 2960 */       this.content.append(cmyk.getCyan()).append(' ').append(cmyk.getMagenta());
/* 2961 */       this.content.append(' ').append(cmyk.getYellow()).append(' ').append(cmyk.getBlack());
/* 2962 */       break;
/*      */     case 3:
/* 2965 */       this.content.append(tint);
/* 2966 */       break;
/*      */     default:
/* 2968 */       throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.color.type", new Object[0]));
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setPatternFill(PdfPatternPainter p, BaseColor color)
/*      */   {
/* 2977 */     if (ExtendedColor.getType(color) == 3)
/* 2978 */       setPatternFill(p, color, ((SpotColor)color).getTint());
/*      */     else
/* 2980 */       setPatternFill(p, color, 0.0F);
/*      */   }
/*      */ 
/*      */   public void setPatternFill(PdfPatternPainter p, BaseColor color, float tint)
/*      */   {
/* 2989 */     checkWriter();
/* 2990 */     if (!p.isStencil())
/* 2991 */       throw new RuntimeException(MessageLocalization.getComposedMessage("an.uncolored.pattern.was.expected", new Object[0]));
/* 2992 */     PageResources prs = getPageResources();
/* 2993 */     PdfName name = this.writer.addSimplePattern(p);
/* 2994 */     name = prs.addPattern(name, p.getIndirectReference());
/* 2995 */     ColorDetails csDetail = this.writer.addSimplePatternColorspace(color);
/* 2996 */     PdfName cName = prs.addColor(csDetail.getColorSpaceName(), csDetail.getIndirectReference());
/* 2997 */     saveColor(new UncoloredPattern(p, color, tint), true);
/* 2998 */     this.content.append(cName.getBytes()).append(" cs").append_i(this.separator);
/* 2999 */     outputColorNumbers(color, tint);
/* 3000 */     this.content.append(' ').append(name.getBytes()).append(" scn").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setPatternStroke(PdfPatternPainter p, BaseColor color)
/*      */   {
/* 3008 */     if (ExtendedColor.getType(color) == 3)
/* 3009 */       setPatternStroke(p, color, ((SpotColor)color).getTint());
/*      */     else
/* 3011 */       setPatternStroke(p, color, 0.0F);
/*      */   }
/*      */ 
/*      */   public void setPatternStroke(PdfPatternPainter p, BaseColor color, float tint)
/*      */   {
/* 3020 */     checkWriter();
/* 3021 */     if (!p.isStencil())
/* 3022 */       throw new RuntimeException(MessageLocalization.getComposedMessage("an.uncolored.pattern.was.expected", new Object[0]));
/* 3023 */     PageResources prs = getPageResources();
/* 3024 */     PdfName name = this.writer.addSimplePattern(p);
/* 3025 */     name = prs.addPattern(name, p.getIndirectReference());
/* 3026 */     ColorDetails csDetail = this.writer.addSimplePatternColorspace(color);
/* 3027 */     PdfName cName = prs.addColor(csDetail.getColorSpaceName(), csDetail.getIndirectReference());
/* 3028 */     saveColor(new UncoloredPattern(p, color, tint), false);
/* 3029 */     this.content.append(cName.getBytes()).append(" CS").append_i(this.separator);
/* 3030 */     outputColorNumbers(color, tint);
/* 3031 */     this.content.append(' ').append(name.getBytes()).append(" SCN").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void setPatternStroke(PdfPatternPainter p)
/*      */   {
/* 3039 */     if (p.isStencil()) {
/* 3040 */       setPatternStroke(p, p.getDefaultColor());
/* 3041 */       return;
/*      */     }
/* 3043 */     checkWriter();
/* 3044 */     PageResources prs = getPageResources();
/* 3045 */     PdfName name = this.writer.addSimplePattern(p);
/* 3046 */     name = prs.addPattern(name, p.getIndirectReference());
/* 3047 */     saveColor(new PatternColor(p), false);
/* 3048 */     this.content.append(PdfName.PATTERN.getBytes()).append(" CS ").append(name.getBytes()).append(" SCN").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void paintShading(PdfShading shading)
/*      */   {
/* 3056 */     this.writer.addSimpleShading(shading);
/* 3057 */     PageResources prs = getPageResources();
/* 3058 */     PdfName name = prs.addShading(shading.getShadingName(), shading.getShadingReference());
/* 3059 */     this.content.append(name.getBytes()).append(" sh").append_i(this.separator);
/* 3060 */     ColorDetails details = shading.getColorDetails();
/* 3061 */     if (details != null)
/* 3062 */       prs.addColor(details.getColorSpaceName(), details.getIndirectReference());
/*      */   }
/*      */ 
/*      */   public void paintShading(PdfShadingPattern shading)
/*      */   {
/* 3070 */     paintShading(shading.getShading());
/*      */   }
/*      */ 
/*      */   public void setShadingFill(PdfShadingPattern shading)
/*      */   {
/* 3078 */     this.writer.addSimpleShadingPattern(shading);
/* 3079 */     PageResources prs = getPageResources();
/* 3080 */     PdfName name = prs.addPattern(shading.getPatternName(), shading.getPatternReference());
/* 3081 */     saveColor(new ShadingColor(shading), true);
/* 3082 */     this.content.append(PdfName.PATTERN.getBytes()).append(" cs ").append(name.getBytes()).append(" scn").append_i(this.separator);
/* 3083 */     ColorDetails details = shading.getColorDetails();
/* 3084 */     if (details != null)
/* 3085 */       prs.addColor(details.getColorSpaceName(), details.getIndirectReference());
/*      */   }
/*      */ 
/*      */   public void setShadingStroke(PdfShadingPattern shading)
/*      */   {
/* 3093 */     this.writer.addSimpleShadingPattern(shading);
/* 3094 */     PageResources prs = getPageResources();
/* 3095 */     PdfName name = prs.addPattern(shading.getPatternName(), shading.getPatternReference());
/* 3096 */     saveColor(new ShadingColor(shading), false);
/* 3097 */     this.content.append(PdfName.PATTERN.getBytes()).append(" CS ").append(name.getBytes()).append(" SCN").append_i(this.separator);
/* 3098 */     ColorDetails details = shading.getColorDetails();
/* 3099 */     if (details != null)
/* 3100 */       prs.addColor(details.getColorSpaceName(), details.getIndirectReference());
/*      */   }
/*      */ 
/*      */   protected void checkWriter()
/*      */   {
/* 3107 */     if (this.writer == null)
/* 3108 */       throw new NullPointerException(MessageLocalization.getComposedMessage("the.writer.in.pdfcontentbyte.is.null", new Object[0]));
/*      */   }
/*      */ 
/*      */   public void showText(PdfTextArray text)
/*      */   {
/* 3116 */     checkState();
/* 3117 */     if ((!this.inText) && (isTagged())) {
/* 3118 */       beginText(true);
/*      */     }
/* 3120 */     if (this.state.fontDetails == null)
/* 3121 */       throw new NullPointerException(MessageLocalization.getComposedMessage("font.and.size.must.be.set.before.writing.any.text", new Object[0]));
/* 3122 */     this.content.append("[");
/* 3123 */     ArrayList arrayList = text.getArrayList();
/* 3124 */     boolean lastWasNumber = false;
/* 3125 */     for (Iterator i$ = arrayList.iterator(); i$.hasNext(); ) { Object obj = i$.next();
/* 3126 */       if ((obj instanceof String)) {
/* 3127 */         showText2((String)obj);
/* 3128 */         updateTx((String)obj, 0.0F);
/* 3129 */         lastWasNumber = false;
/*      */       }
/*      */       else {
/* 3132 */         if (lastWasNumber)
/* 3133 */           this.content.append(' ');
/*      */         else
/* 3135 */           lastWasNumber = true;
/* 3136 */         this.content.append(((Float)obj).floatValue());
/* 3137 */         updateTx("", ((Float)obj).floatValue());
/*      */       }
/*      */     }
/* 3140 */     this.content.append("]TJ").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public PdfWriter getPdfWriter()
/*      */   {
/* 3148 */     return this.writer;
/*      */   }
/*      */ 
/*      */   public PdfDocument getPdfDocument()
/*      */   {
/* 3156 */     return this.pdf;
/*      */   }
/*      */ 
/*      */   public void localGoto(String name, float llx, float lly, float urx, float ury)
/*      */   {
/* 3169 */     this.pdf.localGoto(name, llx, lly, urx, ury);
/*      */   }
/*      */ 
/*      */   public boolean localDestination(String name, PdfDestination destination)
/*      */   {
/* 3182 */     return this.pdf.localDestination(name, destination);
/*      */   }
/*      */ 
/*      */   public PdfContentByte getDuplicate()
/*      */   {
/* 3192 */     PdfContentByte cb = new PdfContentByte(this.writer);
/* 3193 */     cb.duplicatedFrom = this;
/* 3194 */     return cb;
/*      */   }
/*      */ 
/*      */   public PdfContentByte getDuplicate(boolean inheritGraphicState) {
/* 3198 */     PdfContentByte cb = getDuplicate();
/* 3199 */     if (inheritGraphicState) {
/* 3200 */       cb.state = this.state;
/* 3201 */       cb.stateList = this.stateList;
/*      */     }
/* 3203 */     return cb;
/*      */   }
/*      */ 
/*      */   public void inheritGraphicState(PdfContentByte parentCanvas) {
/* 3207 */     this.state = parentCanvas.state;
/* 3208 */     this.stateList = parentCanvas.stateList;
/*      */   }
/*      */ 
/*      */   public void remoteGoto(String filename, String name, float llx, float lly, float urx, float ury)
/*      */   {
/* 3221 */     this.pdf.remoteGoto(filename, name, llx, lly, urx, ury);
/*      */   }
/*      */ 
/*      */   public void remoteGoto(String filename, int page, float llx, float lly, float urx, float ury)
/*      */   {
/* 3234 */     this.pdf.remoteGoto(filename, page, llx, lly, urx, ury);
/*      */   }
/*      */ 
/*      */   public void roundRectangle(float x, float y, float w, float h, float r)
/*      */   {
/* 3246 */     if (w < 0.0F) {
/* 3247 */       x += w;
/* 3248 */       w = -w;
/*      */     }
/* 3250 */     if (h < 0.0F) {
/* 3251 */       y += h;
/* 3252 */       h = -h;
/*      */     }
/* 3254 */     if (r < 0.0F)
/* 3255 */       r = -r;
/* 3256 */     float b = 0.4477F;
/* 3257 */     moveTo(x + r, y);
/* 3258 */     lineTo(x + w - r, y);
/* 3259 */     curveTo(x + w - r * b, y, x + w, y + r * b, x + w, y + r);
/* 3260 */     lineTo(x + w, y + h - r);
/* 3261 */     curveTo(x + w, y + h - r * b, x + w - r * b, y + h, x + w - r, y + h);
/* 3262 */     lineTo(x + r, y + h);
/* 3263 */     curveTo(x + r * b, y + h, x, y + h - r * b, x, y + h - r);
/* 3264 */     lineTo(x, y + r);
/* 3265 */     curveTo(x, y + r * b, x + r * b, y, x + r, y);
/*      */   }
/*      */ 
/*      */   public void setAction(PdfAction action, float llx, float lly, float urx, float ury)
/*      */   {
/* 3276 */     this.pdf.setAction(action, llx, lly, urx, ury);
/*      */   }
/*      */ 
/*      */   public void setLiteral(String s)
/*      */   {
/* 3283 */     this.content.append(s);
/*      */   }
/*      */ 
/*      */   public void setLiteral(char c)
/*      */   {
/* 3290 */     this.content.append(c);
/*      */   }
/*      */ 
/*      */   public void setLiteral(float n)
/*      */   {
/* 3297 */     this.content.append(n);
/*      */   }
/*      */ 
/*      */   void checkNoPattern(PdfTemplate t)
/*      */   {
/* 3304 */     if (t.getType() == 3)
/* 3305 */       throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.use.of.a.pattern.a.template.was.expected", new Object[0]));
/*      */   }
/*      */ 
/*      */   public void drawRadioField(float llx, float lly, float urx, float ury, boolean on)
/*      */   {
/* 3317 */     if (llx > urx) { float x = llx; llx = urx; urx = x; }
/* 3318 */     if (lly > ury) { float y = lly; lly = ury; ury = y; }
/* 3319 */     saveState();
/*      */ 
/* 3321 */     setLineWidth(1.0F);
/* 3322 */     setLineCap(1);
/* 3323 */     setColorStroke(new BaseColor(192, 192, 192));
/* 3324 */     arc(llx + 1.0F, lly + 1.0F, urx - 1.0F, ury - 1.0F, 0.0F, 360.0F);
/* 3325 */     stroke();
/*      */ 
/* 3327 */     setLineWidth(1.0F);
/* 3328 */     setLineCap(1);
/* 3329 */     setColorStroke(new BaseColor(160, 160, 160));
/* 3330 */     arc(llx + 0.5F, lly + 0.5F, urx - 0.5F, ury - 0.5F, 45.0F, 180.0F);
/* 3331 */     stroke();
/*      */ 
/* 3333 */     setLineWidth(1.0F);
/* 3334 */     setLineCap(1);
/* 3335 */     setColorStroke(new BaseColor(0, 0, 0));
/* 3336 */     arc(llx + 1.5F, lly + 1.5F, urx - 1.5F, ury - 1.5F, 45.0F, 180.0F);
/* 3337 */     stroke();
/* 3338 */     if (on)
/*      */     {
/* 3340 */       setLineWidth(1.0F);
/* 3341 */       setLineCap(1);
/* 3342 */       setColorFill(new BaseColor(0, 0, 0));
/* 3343 */       arc(llx + 4.0F, lly + 4.0F, urx - 4.0F, ury - 4.0F, 0.0F, 360.0F);
/* 3344 */       fill();
/*      */     }
/* 3346 */     restoreState();
/*      */   }
/*      */ 
/*      */   public void drawTextField(float llx, float lly, float urx, float ury)
/*      */   {
/* 3357 */     if (llx > urx) { float x = llx; llx = urx; urx = x; }
/* 3358 */     if (lly > ury) { float y = lly; lly = ury; ury = y;
/*      */     }
/* 3360 */     saveState();
/* 3361 */     setColorStroke(new BaseColor(192, 192, 192));
/* 3362 */     setLineWidth(1.0F);
/* 3363 */     setLineCap(0);
/* 3364 */     rectangle(llx, lly, urx - llx, ury - lly);
/* 3365 */     stroke();
/*      */ 
/* 3367 */     setLineWidth(1.0F);
/* 3368 */     setLineCap(0);
/* 3369 */     setColorFill(new BaseColor(255, 255, 255));
/* 3370 */     rectangle(llx + 0.5F, lly + 0.5F, urx - llx - 1.0F, ury - lly - 1.0F);
/* 3371 */     fill();
/*      */ 
/* 3373 */     setColorStroke(new BaseColor(192, 192, 192));
/* 3374 */     setLineWidth(1.0F);
/* 3375 */     setLineCap(0);
/* 3376 */     moveTo(llx + 1.0F, lly + 1.5F);
/* 3377 */     lineTo(urx - 1.5F, lly + 1.5F);
/* 3378 */     lineTo(urx - 1.5F, ury - 1.0F);
/* 3379 */     stroke();
/*      */ 
/* 3381 */     setColorStroke(new BaseColor(160, 160, 160));
/* 3382 */     setLineWidth(1.0F);
/* 3383 */     setLineCap(0);
/* 3384 */     moveTo(llx + 1.0F, lly + 1.0F);
/* 3385 */     lineTo(llx + 1.0F, ury - 1.0F);
/* 3386 */     lineTo(urx - 1.0F, ury - 1.0F);
/* 3387 */     stroke();
/*      */ 
/* 3389 */     setColorStroke(new BaseColor(0, 0, 0));
/* 3390 */     setLineWidth(1.0F);
/* 3391 */     setLineCap(0);
/* 3392 */     moveTo(llx + 2.0F, lly + 2.0F);
/* 3393 */     lineTo(llx + 2.0F, ury - 2.0F);
/* 3394 */     lineTo(urx - 2.0F, ury - 2.0F);
/* 3395 */     stroke();
/* 3396 */     restoreState();
/*      */   }
/*      */ 
/*      */   public void drawButton(float llx, float lly, float urx, float ury, String text, BaseFont bf, float size)
/*      */   {
/* 3410 */     if (llx > urx) { float x = llx; llx = urx; urx = x; }
/* 3411 */     if (lly > ury) { float y = lly; lly = ury; ury = y;
/*      */     }
/* 3413 */     saveState();
/* 3414 */     setColorStroke(new BaseColor(0, 0, 0));
/* 3415 */     setLineWidth(1.0F);
/* 3416 */     setLineCap(0);
/* 3417 */     rectangle(llx, lly, urx - llx, ury - lly);
/* 3418 */     stroke();
/*      */ 
/* 3420 */     setLineWidth(1.0F);
/* 3421 */     setLineCap(0);
/* 3422 */     setColorFill(new BaseColor(192, 192, 192));
/* 3423 */     rectangle(llx + 0.5F, lly + 0.5F, urx - llx - 1.0F, ury - lly - 1.0F);
/* 3424 */     fill();
/*      */ 
/* 3426 */     setColorStroke(new BaseColor(255, 255, 255));
/* 3427 */     setLineWidth(1.0F);
/* 3428 */     setLineCap(0);
/* 3429 */     moveTo(llx + 1.0F, lly + 1.0F);
/* 3430 */     lineTo(llx + 1.0F, ury - 1.0F);
/* 3431 */     lineTo(urx - 1.0F, ury - 1.0F);
/* 3432 */     stroke();
/*      */ 
/* 3434 */     setColorStroke(new BaseColor(160, 160, 160));
/* 3435 */     setLineWidth(1.0F);
/* 3436 */     setLineCap(0);
/* 3437 */     moveTo(llx + 1.0F, lly + 1.0F);
/* 3438 */     lineTo(urx - 1.0F, lly + 1.0F);
/* 3439 */     lineTo(urx - 1.0F, ury - 1.0F);
/* 3440 */     stroke();
/*      */ 
/* 3442 */     resetRGBColorFill();
/* 3443 */     beginText();
/* 3444 */     setFontAndSize(bf, size);
/* 3445 */     showTextAligned(1, text, llx + (urx - llx) / 2.0F, lly + (ury - lly - size) / 2.0F, 0.0F);
/* 3446 */     endText();
/* 3447 */     restoreState();
/*      */   }
/*      */ 
/*      */   PageResources getPageResources() {
/* 3451 */     return this.pdf.getPageResources();
/*      */   }
/*      */ 
/*      */   public void setGState(PdfGState gstate)
/*      */   {
/* 3458 */     PdfObject[] obj = this.writer.addSimpleExtGState(gstate);
/* 3459 */     PageResources prs = getPageResources();
/* 3460 */     PdfName name = prs.addExtGState((PdfName)obj[0], (PdfIndirectReference)obj[1]);
/* 3461 */     this.state.extGState = gstate;
/* 3462 */     this.content.append(name.getBytes()).append(" gs").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void beginLayer(PdfOCG layer)
/*      */   {
/* 3474 */     if (((layer instanceof PdfLayer)) && (((PdfLayer)layer).getTitle() != null))
/* 3475 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("a.title.is.not.a.layer", new Object[0]));
/* 3476 */     if (this.layerDepth == null)
/* 3477 */       this.layerDepth = new ArrayList();
/* 3478 */     if ((layer instanceof PdfLayerMembership)) {
/* 3479 */       this.layerDepth.add(Integer.valueOf(1));
/* 3480 */       beginLayer2(layer);
/* 3481 */       return;
/*      */     }
/* 3483 */     int n = 0;
/* 3484 */     PdfLayer la = (PdfLayer)layer;
/* 3485 */     while (la != null) {
/* 3486 */       if (la.getTitle() == null) {
/* 3487 */         beginLayer2(la);
/* 3488 */         n++;
/*      */       }
/* 3490 */       la = la.getParent();
/*      */     }
/* 3492 */     this.layerDepth.add(Integer.valueOf(n));
/*      */   }
/*      */ 
/*      */   private void beginLayer2(PdfOCG layer) {
/* 3496 */     PdfName name = (PdfName)this.writer.addSimpleProperty(layer, layer.getRef())[0];
/* 3497 */     PageResources prs = getPageResources();
/* 3498 */     name = prs.addProperty(name, layer.getRef());
/* 3499 */     this.content.append("/OC ").append(name.getBytes()).append(" BDC").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void endLayer()
/*      */   {
/* 3506 */     int n = 1;
/* 3507 */     if ((this.layerDepth != null) && (!this.layerDepth.isEmpty())) {
/* 3508 */       n = ((Integer)this.layerDepth.get(this.layerDepth.size() - 1)).intValue();
/* 3509 */       this.layerDepth.remove(this.layerDepth.size() - 1);
/*      */     } else {
/* 3511 */       throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("unbalanced.layer.operators", new Object[0]));
/*      */     }
/* 3513 */     while (n-- > 0)
/* 3514 */       this.content.append("EMC").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   public void transform(com.itextpdf.awt.geom.AffineTransform af)
/*      */   {
/* 3522 */     if ((this.inText) && (isTagged())) {
/* 3523 */       endText();
/*      */     }
/* 3525 */     double[] matrix = new double[6];
/* 3526 */     af.getMatrix(matrix);
/* 3527 */     this.state.CTM.concatenate(af);
/* 3528 */     this.content.append(matrix[0]).append(' ').append(matrix[1]).append(' ').append(matrix[2]).append(' ');
/* 3529 */     this.content.append(matrix[3]).append(' ').append(matrix[4]).append(' ').append(matrix[5]).append(" cm").append_i(this.separator);
/*      */   }
/*      */ 
/*      */   void addAnnotation(PdfAnnotation annot) {
/* 3533 */     boolean needToTag = (isTagged()) && (annot.getRole() != null) && ((!(annot instanceof PdfFormField)) || (((PdfFormField)annot).getKids() == null));
/* 3534 */     if (needToTag) {
/* 3535 */       openMCBlock(annot);
/*      */     }
/* 3537 */     this.writer.addAnnotation(annot);
/* 3538 */     if (needToTag) {
/* 3539 */       PdfStructureElement strucElem = (PdfStructureElement)this.pdf.structElements.get(annot.getId());
/* 3540 */       if (strucElem != null) {
/* 3541 */         int structParent = this.pdf.getStructParentIndex(annot);
/* 3542 */         annot.put(PdfName.STRUCTPARENT, new PdfNumber(structParent));
/* 3543 */         strucElem.setAnnotation(annot, getCurrentPage());
/* 3544 */         this.writer.getStructureTreeRoot().setAnnotationMark(structParent, strucElem.getReference());
/*      */       }
/* 3546 */       closeMCBlock(annot);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addAnnotation(PdfAnnotation annot, boolean applyCTM) {
/* 3551 */     if ((applyCTM) && (this.state.CTM.getType() != 0)) {
/* 3552 */       annot.applyCTM(this.state.CTM);
/*      */     }
/* 3554 */     addAnnotation(annot);
/*      */   }
/*      */ 
/*      */   public void setDefaultColorspace(PdfName name, PdfObject obj)
/*      */   {
/* 3564 */     PageResources prs = getPageResources();
/* 3565 */     prs.addDefaultColor(name, obj);
/*      */   }
/*      */ 
/*      */   public void beginMarkedContentSequence(PdfStructureElement struc)
/*      */   {
/* 3575 */     PdfObject obj = struc.get(PdfName.K);
/* 3576 */     int[] structParentMarkPoint = this.pdf.getStructParentIndexAndNextMarkPoint(getCurrentPage());
/* 3577 */     int structParent = structParentMarkPoint[0];
/* 3578 */     int mark = structParentMarkPoint[1];
/* 3579 */     if (obj != null) {
/* 3580 */       PdfArray ar = null;
/* 3581 */       if (obj.isNumber()) {
/* 3582 */         ar = new PdfArray();
/* 3583 */         ar.add(obj);
/* 3584 */         struc.put(PdfName.K, ar);
/*      */       }
/* 3586 */       else if (obj.isArray()) {
/* 3587 */         ar = (PdfArray)obj;
/*      */       }
/*      */       else {
/* 3590 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("unknown.object.at.k.1", new Object[] { obj.getClass().toString() }));
/* 3591 */       }if (ar.getAsNumber(0) != null) {
/* 3592 */         PdfDictionary dic = new PdfDictionary(PdfName.MCR);
/* 3593 */         dic.put(PdfName.PG, getCurrentPage());
/* 3594 */         dic.put(PdfName.MCID, new PdfNumber(mark));
/* 3595 */         ar.add(dic);
/*      */       }
/* 3597 */       struc.setPageMark(this.pdf.getStructParentIndex(getCurrentPage()), -1);
/*      */     }
/*      */     else {
/* 3600 */       struc.setPageMark(structParent, mark);
/* 3601 */       struc.put(PdfName.PG, getCurrentPage());
/*      */     }
/* 3603 */     setMcDepth(getMcDepth() + 1);
/* 3604 */     int contentSize = this.content.size();
/* 3605 */     this.content.append(struc.get(PdfName.S).getBytes()).append(" <</MCID ").append(mark).append(">> BDC").append_i(this.separator);
/* 3606 */     this.markedContentSize += this.content.size() - contentSize;
/*      */   }
/*      */ 
/*      */   protected PdfIndirectReference getCurrentPage() {
/* 3610 */     return this.writer.getCurrentPage();
/*      */   }
/*      */ 
/*      */   public void endMarkedContentSequence()
/*      */   {
/* 3617 */     if (getMcDepth() == 0) {
/* 3618 */       throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("unbalanced.begin.end.marked.content.operators", new Object[0]));
/*      */     }
/* 3620 */     int contentSize = this.content.size();
/* 3621 */     setMcDepth(getMcDepth() - 1);
/* 3622 */     this.content.append("EMC").append_i(this.separator);
/* 3623 */     this.markedContentSize += this.content.size() - contentSize;
/*      */   }
/*      */ 
/*      */   public void beginMarkedContentSequence(PdfName tag, PdfDictionary property, boolean inline)
/*      */   {
/* 3635 */     int contentSize = this.content.size();
/* 3636 */     if (property == null) {
/* 3637 */       this.content.append(tag.getBytes()).append(" BMC").append_i(this.separator);
/* 3638 */       setMcDepth(getMcDepth() + 1);
/*      */     } else {
/* 3640 */       this.content.append(tag.getBytes()).append(' ');
/* 3641 */       if (inline) {
/*      */         try {
/* 3643 */           property.toPdf(this.writer, this.content);
/*      */         }
/*      */         catch (Exception e) {
/* 3646 */           throw new ExceptionConverter(e);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*      */         PdfObject[] objs;
/*      */         PdfObject[] objs;
/* 3650 */         if (this.writer.propertyExists(property))
/* 3651 */           objs = this.writer.addSimpleProperty(property, null);
/*      */         else
/* 3653 */           objs = this.writer.addSimpleProperty(property, this.writer.getPdfIndirectReference());
/* 3654 */         PdfName name = (PdfName)objs[0];
/* 3655 */         PageResources prs = getPageResources();
/* 3656 */         name = prs.addProperty(name, (PdfIndirectReference)objs[1]);
/* 3657 */         this.content.append(name.getBytes());
/*      */       }
/* 3659 */       this.content.append(" BDC").append_i(this.separator);
/* 3660 */       setMcDepth(getMcDepth() + 1);
/*      */     }
/* 3662 */     this.markedContentSize += this.content.size() - contentSize;
/*      */   }
/*      */ 
/*      */   public void beginMarkedContentSequence(PdfName tag)
/*      */   {
/* 3670 */     beginMarkedContentSequence(tag, null, false);
/*      */   }
/*      */ 
/*      */   public void sanityCheck()
/*      */   {
/* 3685 */     if (getMcDepth() != 0) {
/* 3686 */       throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("unbalanced.marked.content.operators", new Object[0]));
/*      */     }
/* 3688 */     if (this.inText) {
/* 3689 */       if (isTagged())
/* 3690 */         endText();
/*      */       else {
/* 3692 */         throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("unbalanced.begin.end.text.operators", new Object[0]));
/*      */       }
/*      */     }
/* 3695 */     if ((this.layerDepth != null) && (!this.layerDepth.isEmpty())) {
/* 3696 */       throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("unbalanced.layer.operators", new Object[0]));
/*      */     }
/* 3698 */     if (!this.stateList.isEmpty())
/* 3699 */       throw new IllegalPdfSyntaxException(MessageLocalization.getComposedMessage("unbalanced.save.restore.state.operators", new Object[0]));
/*      */   }
/*      */ 
/*      */   public void openMCBlock(IAccessibleElement element)
/*      */   {
/* 3704 */     if (isTagged()) {
/* 3705 */       if (this.pdf.openMCDocument) {
/* 3706 */         this.pdf.openMCDocument = false;
/* 3707 */         this.writer.getDirectContentUnder().openMCBlock(this.pdf);
/*      */       }
/* 3709 */       if ((element != null) && 
/* 3710 */         (!getMcElements().contains(element))) {
/* 3711 */         PdfStructureElement structureElement = openMCBlockInt(element);
/* 3712 */         getMcElements().add(element);
/* 3713 */         if (structureElement != null)
/* 3714 */           this.pdf.structElements.put(element.getId(), structureElement);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private PdfDictionary getParentStructureElement()
/*      */   {
/* 3721 */     PdfDictionary parent = null;
/* 3722 */     if (getMcElements().size() > 0)
/* 3723 */       parent = (PdfDictionary)this.pdf.structElements.get(((IAccessibleElement)getMcElements().get(getMcElements().size() - 1)).getId());
/* 3724 */     if (parent == null) {
/* 3725 */       parent = this.writer.getStructureTreeRoot();
/*      */     }
/* 3727 */     return parent;
/*      */   }
/*      */ 
/*      */   private PdfStructureElement openMCBlockInt(IAccessibleElement element) {
/* 3731 */     PdfStructureElement structureElement = null;
/* 3732 */     if (isTagged()) {
/* 3733 */       IAccessibleElement parent = null;
/* 3734 */       if (getMcElements().size() > 0)
/* 3735 */         parent = (IAccessibleElement)getMcElements().get(getMcElements().size() - 1);
/* 3736 */       this.writer.checkElementRole(element, parent);
/* 3737 */       if (element.getRole() != null) {
/* 3738 */         if (!PdfName.ARTIFACT.equals(element.getRole())) {
/* 3739 */           structureElement = (PdfStructureElement)this.pdf.structElements.get(element.getId());
/* 3740 */           if (structureElement == null) {
/* 3741 */             structureElement = new PdfStructureElement(getParentStructureElement(), element.getRole());
/*      */           }
/*      */         }
/* 3744 */         if (PdfName.ARTIFACT.equals(element.getRole())) {
/* 3745 */           HashMap properties = element.getAccessibleAttributes();
/* 3746 */           PdfDictionary propertiesDict = null;
/* 3747 */           if ((properties != null) && (!properties.isEmpty()))
/*      */           {
/* 3749 */             propertiesDict = new PdfDictionary();
/* 3750 */             for (Map.Entry entry : properties.entrySet()) {
/* 3751 */               propertiesDict.put((PdfName)entry.getKey(), (PdfObject)entry.getValue());
/*      */             }
/*      */           }
/* 3754 */           boolean inTextLocal = this.inText;
/* 3755 */           if (this.inText)
/* 3756 */             endText();
/* 3757 */           beginMarkedContentSequence(element.getRole(), propertiesDict, true);
/* 3758 */           if (inTextLocal)
/* 3759 */             beginText(true);
/*      */         }
/* 3761 */         else if (this.writer.needToBeMarkedInContent(element)) {
/* 3762 */           boolean inTextLocal = this.inText;
/* 3763 */           if (this.inText)
/* 3764 */             endText();
/* 3765 */           beginMarkedContentSequence(structureElement);
/* 3766 */           if (inTextLocal) {
/* 3767 */             beginText(true);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 3772 */     return structureElement;
/*      */   }
/*      */ 
/*      */   public void closeMCBlock(IAccessibleElement element) {
/* 3776 */     if ((isTagged()) && (element != null) && 
/* 3777 */       (getMcElements().contains(element))) {
/* 3778 */       closeMCBlockInt(element);
/* 3779 */       getMcElements().remove(element);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void closeMCBlockInt(IAccessibleElement element)
/*      */   {
/* 3785 */     if ((isTagged()) && (element.getRole() != null)) {
/* 3786 */       PdfStructureElement structureElement = (PdfStructureElement)this.pdf.structElements.get(element.getId());
/* 3787 */       if (structureElement != null) {
/* 3788 */         structureElement.writeAttributes(element);
/*      */       }
/* 3790 */       if (this.writer.needToBeMarkedInContent(element)) {
/* 3791 */         boolean inTextLocal = this.inText;
/* 3792 */         if (this.inText)
/* 3793 */           endText();
/* 3794 */         endMarkedContentSequence();
/* 3795 */         if (inTextLocal)
/* 3796 */           beginText(true);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected ArrayList<IAccessibleElement> saveMCBlocks() {
/* 3802 */     ArrayList mc = new ArrayList();
/* 3803 */     if (isTagged()) {
/* 3804 */       mc = getMcElements();
/* 3805 */       for (int i = 0; i < mc.size(); i++) {
/* 3806 */         closeMCBlockInt((IAccessibleElement)mc.get(i));
/*      */       }
/* 3808 */       setMcElements(new ArrayList());
/*      */     }
/* 3810 */     return mc;
/*      */   }
/*      */ 
/*      */   protected void restoreMCBlocks(ArrayList<IAccessibleElement> mcElements) {
/* 3814 */     if ((isTagged()) && (mcElements != null)) {
/* 3815 */       setMcElements(mcElements);
/* 3816 */       for (int i = 0; i < getMcElements().size(); i++)
/* 3817 */         openMCBlockInt((IAccessibleElement)getMcElements().get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected int getMcDepth()
/*      */   {
/* 3823 */     if (this.duplicatedFrom != null) {
/* 3824 */       return this.duplicatedFrom.getMcDepth();
/*      */     }
/* 3826 */     return this.mcDepth;
/*      */   }
/*      */ 
/*      */   protected void setMcDepth(int value) {
/* 3830 */     if (this.duplicatedFrom != null)
/* 3831 */       this.duplicatedFrom.setMcDepth(value);
/*      */     else
/* 3833 */       this.mcDepth = value;
/*      */   }
/*      */ 
/*      */   protected ArrayList<IAccessibleElement> getMcElements() {
/* 3837 */     if (this.duplicatedFrom != null) {
/* 3838 */       return this.duplicatedFrom.getMcElements();
/*      */     }
/* 3840 */     return this.mcElements;
/*      */   }
/*      */ 
/*      */   protected void setMcElements(ArrayList<IAccessibleElement> value) {
/* 3844 */     if (this.duplicatedFrom != null)
/* 3845 */       this.duplicatedFrom.setMcElements(value);
/*      */     else
/* 3847 */       this.mcElements = value;
/*      */   }
/*      */ 
/*      */   protected void updateTx(String text, float Tj) {
/* 3851 */     this.state.tx += getEffectiveStringWidth(text, false, Tj);
/*      */   }
/*      */ 
/*      */   private void saveColor(BaseColor color, boolean fill) {
/* 3855 */     if (isTagged()) {
/* 3856 */       if (this.inText) {
/* 3857 */         if (fill)
/* 3858 */           this.state.textColorFill = color;
/*      */         else {
/* 3860 */           this.state.textColorStroke = color;
/*      */         }
/*      */       }
/* 3863 */       else if (fill)
/* 3864 */         this.state.colorFill = color;
/*      */       else {
/* 3866 */         this.state.colorStroke = color;
/*      */       }
/*      */ 
/*      */     }
/* 3870 */     else if (fill)
/* 3871 */       this.state.colorFill = color;
/*      */     else
/* 3873 */       this.state.colorStroke = color;
/*      */   }
/*      */ 
/*      */   private void restoreColor(BaseColor color, boolean fill)
/*      */     throws IOException
/*      */   {
/* 3879 */     if (isTagged())
/* 3880 */       if ((color instanceof UncoloredPattern)) {
/* 3881 */         UncoloredPattern c = (UncoloredPattern)color;
/* 3882 */         if (fill)
/* 3883 */           setPatternFill(c.getPainter(), c.color, c.tint);
/*      */         else
/* 3885 */           setPatternStroke(c.getPainter(), c.color, c.tint);
/*      */       }
/* 3887 */       else if (fill) {
/* 3888 */         setColorFill(color);
/*      */       } else {
/* 3890 */         setColorStroke(color);
/*      */       }
/*      */   }
/*      */ 
/*      */   private void restoreColor() throws IOException
/*      */   {
/* 3896 */     if (isTagged())
/* 3897 */       if (this.inText) {
/* 3898 */         if (!this.state.textColorFill.equals(this.state.colorFill)) {
/* 3899 */           restoreColor(this.state.textColorFill, true);
/*      */         }
/* 3901 */         if (!this.state.textColorStroke.equals(this.state.colorStroke))
/* 3902 */           restoreColor(this.state.textColorStroke, false);
/*      */       }
/*      */       else {
/* 3905 */         if (!this.state.textColorFill.equals(this.state.colorFill)) {
/* 3906 */           restoreColor(this.state.colorFill, true);
/*      */         }
/* 3908 */         if (!this.state.textColorStroke.equals(this.state.colorStroke))
/* 3909 */           restoreColor(this.state.colorStroke, false);
/*      */       }
/*      */   }
/*      */ 
/*      */   protected boolean getInText()
/*      */   {
/* 3933 */     return this.inText;
/*      */   }
/*      */ 
/*      */   protected void checkState() {
/* 3937 */     boolean stroke = false;
/* 3938 */     boolean fill = false;
/* 3939 */     if (this.state.textRenderMode == 0) {
/* 3940 */       fill = true;
/* 3941 */     } else if (this.state.textRenderMode == 1) {
/* 3942 */       stroke = true;
/* 3943 */     } else if (this.state.textRenderMode == 2) {
/* 3944 */       fill = true;
/* 3945 */       stroke = true;
/*      */     }
/* 3947 */     if (fill) {
/* 3948 */       PdfWriter.checkPdfIsoConformance(this.writer, 1, isTagged() ? this.state.textColorFill : this.state.colorFill);
/*      */     }
/* 3950 */     if (stroke) {
/* 3951 */       PdfWriter.checkPdfIsoConformance(this.writer, 1, isTagged() ? this.state.textColorStroke : this.state.colorStroke);
/*      */     }
/* 3953 */     PdfWriter.checkPdfIsoConformance(this.writer, 6, this.state.extGState);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Graphics2D createGraphicsShapes(float width, float height)
/*      */   {
/* 3966 */     return new PdfGraphics2D(this, width, height, true);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Graphics2D createPrinterGraphicsShapes(float width, float height, PrinterJob printerJob)
/*      */   {
/* 3978 */     return new PdfPrinterGraphics2D(this, width, height, true, printerJob);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Graphics2D createGraphics(float width, float height)
/*      */   {
/* 3989 */     return new PdfGraphics2D(this, width, height);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Graphics2D createPrinterGraphics(float width, float height, PrinterJob printerJob)
/*      */   {
/* 4001 */     return new PdfPrinterGraphics2D(this, width, height, printerJob);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Graphics2D createGraphics(float width, float height, boolean convertImagesToJPEG, float quality)
/*      */   {
/* 4014 */     return new PdfGraphics2D(this, width, height, null, false, convertImagesToJPEG, quality);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Graphics2D createPrinterGraphics(float width, float height, boolean convertImagesToJPEG, float quality, PrinterJob printerJob)
/*      */   {
/* 4028 */     return new PdfPrinterGraphics2D(this, width, height, null, false, convertImagesToJPEG, quality, printerJob);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Graphics2D createGraphicsShapes(float width, float height, boolean convertImagesToJPEG, float quality)
/*      */   {
/* 4041 */     return new PdfGraphics2D(this, width, height, null, true, convertImagesToJPEG, quality);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Graphics2D createPrinterGraphicsShapes(float width, float height, boolean convertImagesToJPEG, float quality, PrinterJob printerJob)
/*      */   {
/* 4055 */     return new PdfPrinterGraphics2D(this, width, height, null, true, convertImagesToJPEG, quality, printerJob);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Graphics2D createGraphics(float width, float height, FontMapper fontMapper)
/*      */   {
/* 4067 */     return new PdfGraphics2D(this, width, height, fontMapper);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Graphics2D createPrinterGraphics(float width, float height, FontMapper fontMapper, PrinterJob printerJob)
/*      */   {
/* 4080 */     return new PdfPrinterGraphics2D(this, width, height, fontMapper, printerJob);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Graphics2D createGraphics(float width, float height, FontMapper fontMapper, boolean convertImagesToJPEG, float quality)
/*      */   {
/* 4094 */     return new PdfGraphics2D(this, width, height, fontMapper, false, convertImagesToJPEG, quality);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public Graphics2D createPrinterGraphics(float width, float height, FontMapper fontMapper, boolean convertImagesToJPEG, float quality, PrinterJob printerJob)
/*      */   {
/* 4109 */     return new PdfPrinterGraphics2D(this, width, height, fontMapper, false, convertImagesToJPEG, quality, printerJob);
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void addImage(Image image, java.awt.geom.AffineTransform transform)
/*      */     throws DocumentException
/*      */   {
/* 4120 */     double[] matrix = new double[6];
/* 4121 */     transform.getMatrix(matrix);
/* 4122 */     addImage(image, new com.itextpdf.awt.geom.AffineTransform(matrix));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void addTemplate(PdfTemplate template, java.awt.geom.AffineTransform transform)
/*      */   {
/* 4132 */     double[] matrix = new double[6];
/* 4133 */     transform.getMatrix(matrix);
/* 4134 */     addTemplate(template, new com.itextpdf.awt.geom.AffineTransform(matrix));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void concatCTM(java.awt.geom.AffineTransform transform)
/*      */   {
/* 4143 */     double[] matrix = new double[6];
/* 4144 */     transform.getMatrix(matrix);
/* 4145 */     concatCTM(new com.itextpdf.awt.geom.AffineTransform(matrix));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setTextMatrix(java.awt.geom.AffineTransform transform)
/*      */   {
/* 4155 */     double[] matrix = new double[6];
/* 4156 */     transform.getMatrix(matrix);
/* 4157 */     setTextMatrix(new com.itextpdf.awt.geom.AffineTransform(matrix));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void transform(java.awt.geom.AffineTransform af)
/*      */   {
/* 4166 */     double[] matrix = new double[6];
/* 4167 */     af.getMatrix(matrix);
/* 4168 */     transform(new com.itextpdf.awt.geom.AffineTransform(matrix));
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  232 */     abrev.put(PdfName.BITSPERCOMPONENT, "/BPC ");
/*  233 */     abrev.put(PdfName.COLORSPACE, "/CS ");
/*  234 */     abrev.put(PdfName.DECODE, "/D ");
/*  235 */     abrev.put(PdfName.DECODEPARMS, "/DP ");
/*  236 */     abrev.put(PdfName.FILTER, "/F ");
/*  237 */     abrev.put(PdfName.HEIGHT, "/H ");
/*  238 */     abrev.put(PdfName.IMAGEMASK, "/IM ");
/*  239 */     abrev.put(PdfName.INTENT, "/Intent ");
/*  240 */     abrev.put(PdfName.INTERPOLATE, "/I ");
/*  241 */     abrev.put(PdfName.WIDTH, "/W ");
/*      */   }
/*      */ 
/*      */   static class UncoloredPattern extends PatternColor
/*      */   {
/*      */     protected BaseColor color;
/*      */     protected float tint;
/*      */ 
/*      */     protected UncoloredPattern(PdfPatternPainter p, BaseColor color, float tint)
/*      */     {
/* 3920 */       super();
/* 3921 */       this.color = color;
/* 3922 */       this.tint = tint;
/*      */     }
/*      */ 
/*      */     public boolean equals(Object obj)
/*      */     {
/* 3927 */       return ((obj instanceof UncoloredPattern)) && (((UncoloredPattern)obj).painter.equals(this.painter)) && (((UncoloredPattern)obj).color.equals(this.color)) && (((UncoloredPattern)obj).tint == this.tint);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class GraphicState
/*      */   {
/*      */     FontDetails fontDetails;
/*      */     ColorDetails colorDetails;
/*      */     float size;
/*   88 */     protected float xTLM = 0.0F;
/*      */ 
/*   90 */     protected float yTLM = 0.0F;
/*      */ 
/*   92 */     protected float aTLM = 1.0F;
/*   93 */     protected float bTLM = 0.0F;
/*   94 */     protected float cTLM = 0.0F;
/*   95 */     protected float dTLM = 1.0F;
/*      */ 
/*   97 */     protected float tx = 0.0F;
/*      */ 
/*  100 */     protected float leading = 0.0F;
/*      */ 
/*  103 */     protected float scale = 100.0F;
/*      */ 
/*  106 */     protected float charSpace = 0.0F;
/*      */ 
/*  109 */     protected float wordSpace = 0.0F;
/*      */ 
/*  111 */     protected BaseColor textColorFill = new GrayColor(0);
/*  112 */     protected BaseColor colorFill = new GrayColor(0);
/*  113 */     protected BaseColor textColorStroke = new GrayColor(0);
/*  114 */     protected BaseColor colorStroke = new GrayColor(0);
/*  115 */     protected int textRenderMode = 0;
/*  116 */     protected com.itextpdf.awt.geom.AffineTransform CTM = new com.itextpdf.awt.geom.AffineTransform();
/*  117 */     protected PdfObject extGState = null;
/*      */ 
/*      */     GraphicState() {
/*      */     }
/*      */ 
/*      */     GraphicState(GraphicState cp) {
/*  123 */       copyParameters(cp);
/*      */     }
/*      */ 
/*      */     void copyParameters(GraphicState cp) {
/*  127 */       this.fontDetails = cp.fontDetails;
/*  128 */       this.colorDetails = cp.colorDetails;
/*  129 */       this.size = cp.size;
/*  130 */       this.xTLM = cp.xTLM;
/*  131 */       this.yTLM = cp.yTLM;
/*  132 */       this.aTLM = cp.aTLM;
/*  133 */       this.bTLM = cp.bTLM;
/*  134 */       this.cTLM = cp.cTLM;
/*  135 */       this.dTLM = cp.dTLM;
/*  136 */       this.tx = cp.tx;
/*  137 */       this.leading = cp.leading;
/*  138 */       this.scale = cp.scale;
/*  139 */       this.charSpace = cp.charSpace;
/*  140 */       this.wordSpace = cp.wordSpace;
/*  141 */       this.textColorFill = cp.textColorFill;
/*  142 */       this.colorFill = cp.colorFill;
/*  143 */       this.textColorStroke = cp.textColorStroke;
/*  144 */       this.colorStroke = cp.colorStroke;
/*  145 */       this.CTM = new com.itextpdf.awt.geom.AffineTransform(cp.CTM);
/*  146 */       this.textRenderMode = cp.textRenderMode;
/*  147 */       this.extGState = cp.extGState;
/*      */     }
/*      */ 
/*      */     void restore(GraphicState restore) {
/*  151 */       copyParameters(restore);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfContentByte
 * JD-Core Version:    0.6.2
 */