/*     */ package org.apache.pdfbox.pdmodel.fdf;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.util.Calendar;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.util.BitFlagHelper;
/*     */ import org.apache.pdfbox.util.DateConverter;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public abstract class FDFAnnotation
/*     */   implements COSObjectable
/*     */ {
/*  45 */   private static final Log LOG = LogFactory.getLog(FDFAnnotation.class);
/*     */   protected COSDictionary annot;
/*     */ 
/*     */   public FDFAnnotation()
/*     */   {
/*  56 */     this.annot = new COSDictionary();
/*  57 */     this.annot.setItem(COSName.TYPE, COSName.ANNOT);
/*     */   }
/*     */ 
/*     */   public FDFAnnotation(COSDictionary a)
/*     */   {
/*  67 */     this.annot = a;
/*     */   }
/*     */ 
/*     */   public FDFAnnotation(Element element)
/*     */     throws IOException
/*     */   {
/*  79 */     this();
/*     */ 
/*  81 */     String page = element.getAttribute("page");
/*  82 */     if (page != null)
/*     */     {
/*  84 */       setPage(Integer.parseInt(page));
/*     */     }
/*     */ 
/*  87 */     String color = element.getAttribute("color");
/*  88 */     if (color != null)
/*     */     {
/*  90 */       if ((color.length() == 7) && (color.charAt(0) == '#'))
/*     */       {
/*  92 */         int colorValue = Integer.parseInt(color.substring(1, 7), 16);
/*  93 */         setColor(new Color(colorValue));
/*     */       }
/*     */     }
/*     */ 
/*  97 */     setDate(element.getAttribute("date"));
/*     */ 
/*  99 */     String flags = element.getAttribute("flags");
/* 100 */     if (flags != null)
/*     */     {
/* 102 */       String[] flagTokens = flags.split(",");
/* 103 */       for (int i = 0; i < flagTokens.length; i++)
/*     */       {
/* 105 */         if (flagTokens[i].equals("invisible"))
/*     */         {
/* 107 */           setInvisible(true);
/*     */         }
/* 109 */         else if (flagTokens[i].equals("hidden"))
/*     */         {
/* 111 */           setHidden(true);
/*     */         }
/* 113 */         else if (flagTokens[i].equals("print"))
/*     */         {
/* 115 */           setPrinted(true);
/*     */         }
/* 117 */         else if (flagTokens[i].equals("nozoom"))
/*     */         {
/* 119 */           setNoZoom(true);
/*     */         }
/* 121 */         else if (flagTokens[i].equals("norotate"))
/*     */         {
/* 123 */           setNoRotate(true);
/*     */         }
/* 125 */         else if (flagTokens[i].equals("noview"))
/*     */         {
/* 127 */           setNoView(true);
/*     */         }
/* 129 */         else if (flagTokens[i].equals("readonly"))
/*     */         {
/* 131 */           setReadOnly(true);
/*     */         }
/* 133 */         else if (flagTokens[i].equals("locked"))
/*     */         {
/* 135 */           setLocked(true);
/*     */         }
/* 137 */         else if (flagTokens[i].equals("togglenoview"))
/*     */         {
/* 139 */           setToggleNoView(true);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 145 */     setName(element.getAttribute("name"));
/*     */ 
/* 147 */     String rect = element.getAttribute("rect");
/* 148 */     if (rect != null)
/*     */     {
/* 150 */       String[] rectValues = rect.split(",");
/* 151 */       float[] values = new float[rectValues.length];
/* 152 */       for (int i = 0; i < rectValues.length; i++)
/*     */       {
/* 154 */         values[i] = Float.parseFloat(rectValues[i]);
/*     */       }
/* 156 */       COSArray array = new COSArray();
/* 157 */       array.setFloatArray(values);
/* 158 */       setRectangle(new PDRectangle(array));
/*     */     }
/*     */ 
/* 161 */     setName(element.getAttribute("title"));
/* 162 */     setCreationDate(DateConverter.toCalendar(element.getAttribute("creationdate")));
/* 163 */     String opac = element.getAttribute("opacity");
/* 164 */     if (opac != null)
/*     */     {
/* 166 */       setOpacity(Float.parseFloat(opac));
/*     */     }
/* 168 */     setSubject(element.getAttribute("subject"));
/*     */   }
/*     */ 
/*     */   public static FDFAnnotation create(COSDictionary fdfDic)
/*     */     throws IOException
/*     */   {
/* 182 */     FDFAnnotation retval = null;
/* 183 */     if (fdfDic != null)
/*     */     {
/* 185 */       if ("Text".equals(fdfDic.getNameAsString(COSName.SUBTYPE)))
/*     */       {
/* 187 */         retval = new FDFAnnotationText(fdfDic);
/*     */       }
/*     */       else
/*     */       {
/* 191 */         LOG.warn("Unknown annotation type '" + fdfDic.getNameAsString(COSName.SUBTYPE) + "'");
/*     */       }
/*     */     }
/* 194 */     return retval;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 204 */     return this.annot;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/* 214 */     return this.annot;
/*     */   }
/*     */ 
/*     */   public Integer getPage()
/*     */   {
/* 224 */     Integer retval = null;
/* 225 */     COSNumber page = (COSNumber)this.annot.getDictionaryObject(COSName.PAGE);
/* 226 */     if (page != null)
/*     */     {
/* 228 */       retval = new Integer(page.intValue());
/*     */     }
/* 230 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setPage(int page)
/*     */   {
/* 240 */     this.annot.setInt("Page", page);
/*     */   }
/*     */ 
/*     */   public Color getColor()
/*     */   {
/* 250 */     Color retval = null;
/* 251 */     COSArray array = (COSArray)this.annot.getDictionaryObject("color");
/* 252 */     if (array != null)
/*     */     {
/* 254 */       float[] rgb = array.toFloatArray();
/* 255 */       if (rgb.length >= 3)
/*     */       {
/* 257 */         retval = new Color(rgb[0], rgb[1], rgb[2]);
/*     */       }
/*     */     }
/* 260 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setColor(Color c)
/*     */   {
/* 270 */     COSArray color = null;
/* 271 */     if (c != null)
/*     */     {
/* 273 */       float[] colors = c.getRGBColorComponents(null);
/* 274 */       color = new COSArray();
/* 275 */       color.setFloatArray(colors);
/*     */     }
/* 277 */     this.annot.setItem("color", color);
/*     */   }
/*     */ 
/*     */   public String getDate()
/*     */   {
/* 287 */     return this.annot.getString(COSName.DATE);
/*     */   }
/*     */ 
/*     */   public void setDate(String date)
/*     */   {
/* 297 */     this.annot.setString(COSName.DATE, date);
/*     */   }
/*     */ 
/*     */   public boolean isInvisible()
/*     */   {
/* 307 */     return BitFlagHelper.getFlag(this.annot, COSName.F, 1);
/*     */   }
/*     */ 
/*     */   public void setInvisible(boolean invisible)
/*     */   {
/* 317 */     BitFlagHelper.setFlag(this.annot, COSName.F, 1, invisible);
/*     */   }
/*     */ 
/*     */   public boolean isHidden()
/*     */   {
/* 327 */     return BitFlagHelper.getFlag(this.annot, COSName.F, 2);
/*     */   }
/*     */ 
/*     */   public void setHidden(boolean hidden)
/*     */   {
/* 337 */     BitFlagHelper.setFlag(this.annot, COSName.F, 2, hidden);
/*     */   }
/*     */ 
/*     */   public boolean isPrinted()
/*     */   {
/* 347 */     return BitFlagHelper.getFlag(this.annot, COSName.F, 4);
/*     */   }
/*     */ 
/*     */   public void setPrinted(boolean printed)
/*     */   {
/* 357 */     BitFlagHelper.setFlag(this.annot, COSName.F, 4, printed);
/*     */   }
/*     */ 
/*     */   public boolean isNoZoom()
/*     */   {
/* 367 */     return BitFlagHelper.getFlag(this.annot, COSName.F, 8);
/*     */   }
/*     */ 
/*     */   public void setNoZoom(boolean noZoom)
/*     */   {
/* 377 */     BitFlagHelper.setFlag(this.annot, COSName.F, 8, noZoom);
/*     */   }
/*     */ 
/*     */   public boolean isNoRotate()
/*     */   {
/* 387 */     return BitFlagHelper.getFlag(this.annot, COSName.F, 16);
/*     */   }
/*     */ 
/*     */   public void setNoRotate(boolean noRotate)
/*     */   {
/* 397 */     BitFlagHelper.setFlag(this.annot, COSName.F, 16, noRotate);
/*     */   }
/*     */ 
/*     */   public boolean isNoView()
/*     */   {
/* 407 */     return BitFlagHelper.getFlag(this.annot, COSName.F, 32);
/*     */   }
/*     */ 
/*     */   public void setNoView(boolean noView)
/*     */   {
/* 417 */     BitFlagHelper.setFlag(this.annot, COSName.F, 32, noView);
/*     */   }
/*     */ 
/*     */   public boolean isReadOnly()
/*     */   {
/* 427 */     return BitFlagHelper.getFlag(this.annot, COSName.F, 64);
/*     */   }
/*     */ 
/*     */   public void setReadOnly(boolean readOnly)
/*     */   {
/* 437 */     BitFlagHelper.setFlag(this.annot, COSName.F, 64, readOnly);
/*     */   }
/*     */ 
/*     */   public boolean isLocked()
/*     */   {
/* 447 */     return BitFlagHelper.getFlag(this.annot, COSName.F, 128);
/*     */   }
/*     */ 
/*     */   public void setLocked(boolean locked)
/*     */   {
/* 457 */     BitFlagHelper.setFlag(this.annot, COSName.F, 128, locked);
/*     */   }
/*     */ 
/*     */   public boolean isToggleNoView()
/*     */   {
/* 467 */     return BitFlagHelper.getFlag(this.annot, COSName.F, 256);
/*     */   }
/*     */ 
/*     */   public void setToggleNoView(boolean toggleNoView)
/*     */   {
/* 477 */     BitFlagHelper.setFlag(this.annot, COSName.F, 256, toggleNoView);
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 487 */     this.annot.setString(COSName.NM, name);
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 497 */     return this.annot.getString(COSName.NM);
/*     */   }
/*     */ 
/*     */   public void setRectangle(PDRectangle rectangle)
/*     */   {
/* 507 */     this.annot.setItem(COSName.RECT, rectangle);
/*     */   }
/*     */ 
/*     */   public PDRectangle getRectangle()
/*     */   {
/* 517 */     PDRectangle retval = null;
/* 518 */     COSArray rectArray = (COSArray)this.annot.getDictionaryObject(COSName.RECT);
/* 519 */     if (rectArray != null)
/*     */     {
/* 521 */       retval = new PDRectangle(rectArray);
/*     */     }
/* 523 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 533 */     this.annot.setString(COSName.T, title);
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 543 */     return this.annot.getString(COSName.T);
/*     */   }
/*     */ 
/*     */   public Calendar getCreationDate()
/*     */     throws IOException
/*     */   {
/* 555 */     return this.annot.getDate(COSName.CREATION_DATE);
/*     */   }
/*     */ 
/*     */   public void setCreationDate(Calendar date)
/*     */   {
/* 565 */     this.annot.setDate(COSName.CREATION_DATE, date);
/*     */   }
/*     */ 
/*     */   public void setOpacity(float opacity)
/*     */   {
/* 575 */     this.annot.setFloat(COSName.CA, opacity);
/*     */   }
/*     */ 
/*     */   public float getOpacity()
/*     */   {
/* 585 */     return this.annot.getFloat(COSName.CA, 1.0F);
/*     */   }
/*     */ 
/*     */   public void setSubject(String subject)
/*     */   {
/* 596 */     this.annot.setString(COSName.SUBJ, subject);
/*     */   }
/*     */ 
/*     */   public String getSubject()
/*     */   {
/* 606 */     return this.annot.getString(COSName.SUBJ);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotation
 * JD-Core Version:    0.6.2
 */