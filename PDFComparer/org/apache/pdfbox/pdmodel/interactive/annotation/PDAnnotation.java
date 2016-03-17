/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDGamma;
/*     */ import org.apache.pdfbox.util.BitFlagHelper;
/*     */ 
/*     */ public abstract class PDAnnotation
/*     */   implements COSObjectable
/*     */ {
/*  45 */   private static final Log LOG = LogFactory.getLog(PDAnnotation.class);
/*     */   public static final int FLAG_INVISIBLE = 1;
/*     */   public static final int FLAG_HIDDEN = 2;
/*     */   public static final int FLAG_PRINTED = 4;
/*     */   public static final int FLAG_NO_ZOOM = 8;
/*     */   public static final int FLAG_NO_ROTATE = 16;
/*     */   public static final int FLAG_NO_VIEW = 32;
/*     */   public static final int FLAG_READ_ONLY = 64;
/*     */   public static final int FLAG_LOCKED = 128;
/*     */   public static final int FLAG_TOGGLE_NO_VIEW = 256;
/*     */   private final COSDictionary dictionary;
/*     */ 
/*     */   public static PDAnnotation createAnnotation(COSBase base)
/*     */     throws IOException
/*     */   {
/*  95 */     PDAnnotation annot = null;
/*  96 */     if ((base instanceof COSDictionary))
/*     */     {
/*  98 */       COSDictionary annotDic = (COSDictionary)base;
/*  99 */       String subtype = annotDic.getNameAsString(COSName.SUBTYPE);
/* 100 */       if ("FileAttachment".equals(subtype))
/*     */       {
/* 102 */         annot = new PDAnnotationFileAttachment(annotDic);
/*     */       }
/* 104 */       else if ("Line".equals(subtype))
/*     */       {
/* 106 */         annot = new PDAnnotationLine(annotDic);
/*     */       }
/* 108 */       else if ("Link".equals(subtype))
/*     */       {
/* 110 */         annot = new PDAnnotationLink(annotDic);
/*     */       }
/* 112 */       else if ("Popup".equals(subtype))
/*     */       {
/* 114 */         annot = new PDAnnotationPopup(annotDic);
/*     */       }
/* 116 */       else if ("Stamp".equals(subtype))
/*     */       {
/* 118 */         annot = new PDAnnotationRubberStamp(annotDic);
/*     */       }
/* 120 */       else if (("Square".equals(subtype)) || ("Circle".equals(subtype)))
/*     */       {
/* 123 */         annot = new PDAnnotationSquareCircle(annotDic);
/*     */       }
/* 125 */       else if ("Text".equals(subtype))
/*     */       {
/* 127 */         annot = new PDAnnotationText(annotDic);
/*     */       }
/* 129 */       else if (("Highlight".equals(subtype)) || ("Underline".equals(subtype)) || ("Squiggly".equals(subtype)) || ("StrikeOut".equals(subtype)))
/*     */       {
/* 134 */         annot = new PDAnnotationTextMarkup(annotDic);
/*     */       }
/* 136 */       else if ("Link".equals(subtype))
/*     */       {
/* 138 */         annot = new PDAnnotationLink(annotDic);
/*     */       }
/* 140 */       else if ("Widget".equals(subtype))
/*     */       {
/* 142 */         annot = new PDAnnotationWidget(annotDic);
/*     */       }
/* 144 */       else if (("FreeText".equals(subtype)) || ("Polygon".equals(subtype)) || ("PolyLine".equals(subtype)) || ("Caret".equals(subtype)) || ("Ink".equals(subtype)) || ("Sound".equals(subtype)))
/*     */       {
/* 151 */         annot = new PDAnnotationMarkup(annotDic);
/*     */       }
/*     */       else
/*     */       {
/* 157 */         annot = new PDAnnotationUnknown(annotDic);
/* 158 */         LOG.debug("Unknown or unsupported annotation subtype " + subtype);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 163 */       throw new IOException("Error: Unknown annotation type " + base);
/*     */     }
/*     */ 
/* 166 */     return annot;
/*     */   }
/*     */ 
/*     */   public PDAnnotation()
/*     */   {
/* 174 */     this.dictionary = new COSDictionary();
/* 175 */     this.dictionary.setItem(COSName.TYPE, COSName.ANNOT);
/*     */   }
/*     */ 
/*     */   public PDAnnotation(COSDictionary dict)
/*     */   {
/* 185 */     this.dictionary = dict;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/* 195 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public PDRectangle getRectangle()
/*     */   {
/* 207 */     COSArray rectArray = (COSArray)this.dictionary.getDictionaryObject(COSName.RECT);
/* 208 */     PDRectangle rectangle = null;
/* 209 */     if (rectArray != null)
/*     */     {
/* 211 */       if ((rectArray.size() == 4) && ((rectArray.get(0) instanceof COSNumber)) && ((rectArray.get(1) instanceof COSNumber)) && ((rectArray.get(2) instanceof COSNumber)) && ((rectArray.get(3) instanceof COSNumber)))
/*     */       {
/* 217 */         rectangle = new PDRectangle(rectArray);
/*     */       }
/*     */       else
/*     */       {
/* 221 */         LOG.warn(rectArray + " is not a rectangle array, returning null");
/*     */       }
/*     */     }
/* 224 */     return rectangle;
/*     */   }
/*     */ 
/*     */   public void setRectangle(PDRectangle rectangle)
/*     */   {
/* 234 */     this.dictionary.setItem(COSName.RECT, rectangle.getCOSArray());
/*     */   }
/*     */ 
/*     */   public int getAnnotationFlags()
/*     */   {
/* 244 */     return getDictionary().getInt(COSName.F, 0);
/*     */   }
/*     */ 
/*     */   public void setAnnotationFlags(int flags)
/*     */   {
/* 254 */     getDictionary().setInt(COSName.F, flags);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 264 */     return getDictionary();
/*     */   }
/*     */ 
/*     */   public String getAppearanceStream()
/*     */   {
/* 274 */     String retval = null;
/* 275 */     COSName name = (COSName)getDictionary().getDictionaryObject(COSName.AS);
/* 276 */     if (name != null)
/*     */     {
/* 278 */       retval = name.getName();
/*     */     }
/* 280 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setAppearanceStream(String as)
/*     */   {
/* 290 */     if (as == null)
/*     */     {
/* 292 */       getDictionary().removeItem(COSName.AS);
/*     */     }
/*     */     else
/*     */     {
/* 296 */       getDictionary().setItem(COSName.AS, COSName.getPDFName(as));
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDAppearanceDictionary getAppearance()
/*     */   {
/* 307 */     PDAppearanceDictionary ap = null;
/* 308 */     COSDictionary apDic = (COSDictionary)this.dictionary.getDictionaryObject(COSName.AP);
/* 309 */     if (apDic != null)
/*     */     {
/* 311 */       ap = new PDAppearanceDictionary(apDic);
/*     */     }
/* 313 */     return ap;
/*     */   }
/*     */ 
/*     */   public void setAppearance(PDAppearanceDictionary appearance)
/*     */   {
/* 323 */     COSDictionary ap = null;
/* 324 */     if (appearance != null)
/*     */     {
/* 326 */       ap = appearance.getDictionary();
/*     */     }
/* 328 */     this.dictionary.setItem(COSName.AP, ap);
/*     */   }
/*     */ 
/*     */   public boolean isInvisible()
/*     */   {
/* 338 */     return BitFlagHelper.getFlag(getDictionary(), COSName.F, 1);
/*     */   }
/*     */ 
/*     */   public void setInvisible(boolean invisible)
/*     */   {
/* 348 */     BitFlagHelper.setFlag(getDictionary(), COSName.F, 1, invisible);
/*     */   }
/*     */ 
/*     */   public boolean isHidden()
/*     */   {
/* 358 */     return BitFlagHelper.getFlag(getDictionary(), COSName.F, 2);
/*     */   }
/*     */ 
/*     */   public void setHidden(boolean hidden)
/*     */   {
/* 368 */     BitFlagHelper.setFlag(getDictionary(), COSName.F, 2, hidden);
/*     */   }
/*     */ 
/*     */   public boolean isPrinted()
/*     */   {
/* 378 */     return BitFlagHelper.getFlag(getDictionary(), COSName.F, 4);
/*     */   }
/*     */ 
/*     */   public void setPrinted(boolean printed)
/*     */   {
/* 388 */     BitFlagHelper.setFlag(getDictionary(), COSName.F, 4, printed);
/*     */   }
/*     */ 
/*     */   public boolean isNoZoom()
/*     */   {
/* 398 */     return BitFlagHelper.getFlag(getDictionary(), COSName.F, 8);
/*     */   }
/*     */ 
/*     */   public void setNoZoom(boolean noZoom)
/*     */   {
/* 408 */     BitFlagHelper.setFlag(getDictionary(), COSName.F, 8, noZoom);
/*     */   }
/*     */ 
/*     */   public boolean isNoRotate()
/*     */   {
/* 418 */     return BitFlagHelper.getFlag(getDictionary(), COSName.F, 16);
/*     */   }
/*     */ 
/*     */   public void setNoRotate(boolean noRotate)
/*     */   {
/* 428 */     BitFlagHelper.setFlag(getDictionary(), COSName.F, 16, noRotate);
/*     */   }
/*     */ 
/*     */   public boolean isNoView()
/*     */   {
/* 438 */     return BitFlagHelper.getFlag(getDictionary(), COSName.F, 32);
/*     */   }
/*     */ 
/*     */   public void setNoView(boolean noView)
/*     */   {
/* 448 */     BitFlagHelper.setFlag(getDictionary(), COSName.F, 32, noView);
/*     */   }
/*     */ 
/*     */   public boolean isReadOnly()
/*     */   {
/* 458 */     return BitFlagHelper.getFlag(getDictionary(), COSName.F, 64);
/*     */   }
/*     */ 
/*     */   public void setReadOnly(boolean readOnly)
/*     */   {
/* 468 */     BitFlagHelper.setFlag(getDictionary(), COSName.F, 64, readOnly);
/*     */   }
/*     */ 
/*     */   public boolean isLocked()
/*     */   {
/* 478 */     return BitFlagHelper.getFlag(getDictionary(), COSName.F, 128);
/*     */   }
/*     */ 
/*     */   public void setLocked(boolean locked)
/*     */   {
/* 488 */     BitFlagHelper.setFlag(getDictionary(), COSName.F, 128, locked);
/*     */   }
/*     */ 
/*     */   public boolean isToggleNoView()
/*     */   {
/* 498 */     return BitFlagHelper.getFlag(getDictionary(), COSName.F, 256);
/*     */   }
/*     */ 
/*     */   public void setToggleNoView(boolean toggleNoView)
/*     */   {
/* 508 */     BitFlagHelper.setFlag(getDictionary(), COSName.F, 256, toggleNoView);
/*     */   }
/*     */ 
/*     */   public String getContents()
/*     */   {
/* 518 */     return this.dictionary.getString(COSName.CONTENTS);
/*     */   }
/*     */ 
/*     */   public void setContents(String value)
/*     */   {
/* 528 */     this.dictionary.setString(COSName.CONTENTS, value);
/*     */   }
/*     */ 
/*     */   public String getModifiedDate()
/*     */   {
/* 538 */     return getDictionary().getString(COSName.M);
/*     */   }
/*     */ 
/*     */   public void setModifiedDate(String m)
/*     */   {
/* 548 */     getDictionary().setString(COSName.M, m);
/*     */   }
/*     */ 
/*     */   public String getAnnotationName()
/*     */   {
/* 559 */     return getDictionary().getString(COSName.NM);
/*     */   }
/*     */ 
/*     */   public void setAnnotationName(String nm)
/*     */   {
/* 570 */     getDictionary().setString(COSName.NM, nm);
/*     */   }
/*     */ 
/*     */   public int getStructParent()
/*     */   {
/* 580 */     return getDictionary().getInt(COSName.STRUCT_PARENT, 0);
/*     */   }
/*     */ 
/*     */   public void setStructParent(int structParent)
/*     */   {
/* 590 */     getDictionary().setInt(COSName.STRUCT_PARENT, structParent);
/*     */   }
/*     */ 
/*     */   public void setColour(PDGamma c)
/*     */   {
/* 604 */     getDictionary().setItem(COSName.C, c);
/*     */   }
/*     */ 
/*     */   public PDGamma getColour()
/*     */   {
/* 618 */     COSArray c = (COSArray)getDictionary().getItem(COSName.C);
/* 619 */     if (c != null)
/*     */     {
/* 621 */       return new PDGamma(c);
/*     */     }
/*     */ 
/* 625 */     return null;
/*     */   }
/*     */ 
/*     */   public String getSubtype()
/*     */   {
/* 636 */     return getDictionary().getNameAsString(COSName.SUBTYPE);
/*     */   }
/*     */ 
/*     */   public void setPage(PDPage page)
/*     */   {
/* 646 */     getDictionary().setItem(COSName.P, page);
/*     */   }
/*     */ 
/*     */   public PDPage getPage()
/*     */   {
/* 656 */     COSDictionary p = (COSDictionary)getDictionary().getDictionaryObject(COSName.P);
/* 657 */     if (p != null)
/*     */     {
/* 659 */       return new PDPage(p);
/*     */     }
/* 661 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation
 * JD-Core Version:    0.6.2
 */