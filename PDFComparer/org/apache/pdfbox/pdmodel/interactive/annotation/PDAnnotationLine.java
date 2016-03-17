/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDGamma;
/*     */ 
/*     */ public class PDAnnotationLine extends PDAnnotationMarkup
/*     */ {
/*     */   public static final String IT_LINE_ARROW = "LineArrow";
/*     */   public static final String IT_LINE_DIMENSION = "LineDimension";
/*     */   public static final String LE_SQUARE = "Square";
/*     */   public static final String LE_CIRCLE = "Circle";
/*     */   public static final String LE_DIAMOND = "Diamond";
/*     */   public static final String LE_OPEN_ARROW = "OpenArrow";
/*     */   public static final String LE_CLOSED_ARROW = "ClosedArrow";
/*     */   public static final String LE_NONE = "None";
/*     */   public static final String LE_BUTT = "Butt";
/*     */   public static final String LE_R_OPEN_ARROW = "ROpenArrow";
/*     */   public static final String LE_R_CLOSED_ARROW = "RClosedArrow";
/*     */   public static final String LE_SLASH = "Slash";
/*     */   public static final String SUB_TYPE = "Line";
/*     */ 
/*     */   public PDAnnotationLine()
/*     */   {
/* 117 */     getDictionary().setItem(COSName.SUBTYPE, COSName.getPDFName("Line"));
/*     */ 
/* 119 */     setLine(new float[] { 0.0F, 0.0F, 0.0F, 0.0F });
/*     */   }
/*     */ 
/*     */   public PDAnnotationLine(COSDictionary field)
/*     */   {
/* 132 */     super(field);
/*     */   }
/*     */ 
/*     */   public void setLine(float[] l)
/*     */   {
/* 145 */     COSArray newL = new COSArray();
/* 146 */     newL.setFloatArray(l);
/* 147 */     getDictionary().setItem("L", newL);
/*     */   }
/*     */ 
/*     */   public float[] getLine()
/*     */   {
/* 159 */     COSArray l = (COSArray)getDictionary().getDictionaryObject("L");
/* 160 */     return l.toFloatArray();
/*     */   }
/*     */ 
/*     */   public void setStartPointEndingStyle(String style)
/*     */   {
/* 171 */     if (style == null)
/*     */     {
/* 173 */       style = "None";
/*     */     }
/* 175 */     COSArray array = (COSArray)getDictionary().getDictionaryObject("LE");
/* 176 */     if (array == null)
/*     */     {
/* 178 */       array = new COSArray();
/* 179 */       array.add(COSName.getPDFName(style));
/* 180 */       array.add(COSName.getPDFName("None"));
/* 181 */       getDictionary().setItem("LE", array);
/*     */     }
/*     */     else
/*     */     {
/* 185 */       array.setName(0, style);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getStartPointEndingStyle()
/*     */   {
/* 197 */     String retval = "None";
/* 198 */     COSArray array = (COSArray)getDictionary().getDictionaryObject("LE");
/* 199 */     if (array != null)
/*     */     {
/* 201 */       retval = array.getName(0);
/*     */     }
/*     */ 
/* 204 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setEndPointEndingStyle(String style)
/*     */   {
/* 215 */     if (style == null)
/*     */     {
/* 217 */       style = "None";
/*     */     }
/* 219 */     COSArray array = (COSArray)getDictionary().getDictionaryObject("LE");
/* 220 */     if (array == null)
/*     */     {
/* 222 */       array = new COSArray();
/* 223 */       array.add(COSName.getPDFName("None"));
/* 224 */       array.add(COSName.getPDFName(style));
/* 225 */       getDictionary().setItem("LE", array);
/*     */     }
/*     */     else
/*     */     {
/* 229 */       array.setName(1, style);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getEndPointEndingStyle()
/*     */   {
/* 241 */     String retval = "None";
/* 242 */     COSArray array = (COSArray)getDictionary().getDictionaryObject("LE");
/* 243 */     if (array != null)
/*     */     {
/* 245 */       retval = array.getName(1);
/*     */     }
/*     */ 
/* 248 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setInteriorColour(PDGamma ic)
/*     */   {
/* 261 */     getDictionary().setItem("IC", ic);
/*     */   }
/*     */ 
/*     */   public PDGamma getInteriorColour()
/*     */   {
/* 275 */     COSArray ic = (COSArray)getDictionary().getDictionaryObject("IC");
/* 276 */     if (ic != null)
/*     */     {
/* 278 */       return new PDGamma(ic);
/*     */     }
/*     */ 
/* 282 */     return null;
/*     */   }
/*     */ 
/*     */   public void setCaption(boolean cap)
/*     */   {
/* 294 */     getDictionary().setBoolean("Cap", cap);
/*     */   }
/*     */ 
/*     */   public boolean getCaption()
/*     */   {
/* 304 */     return getDictionary().getBoolean("Cap", false);
/*     */   }
/*     */ 
/*     */   public void setBorderStyle(PDBorderStyleDictionary bs)
/*     */   {
/* 316 */     getDictionary().setItem("BS", bs);
/*     */   }
/*     */ 
/*     */   public PDBorderStyleDictionary getBorderStyle()
/*     */   {
/* 327 */     COSDictionary bs = (COSDictionary)getDictionary().getItem(COSName.getPDFName("BS"));
/*     */ 
/* 329 */     if (bs != null)
/*     */     {
/* 331 */       return new PDBorderStyleDictionary(bs);
/*     */     }
/*     */ 
/* 335 */     return null;
/*     */   }
/*     */ 
/*     */   public float getLeaderLineLength()
/*     */   {
/* 346 */     return getDictionary().getFloat("LL");
/*     */   }
/*     */ 
/*     */   public void setLeaderLineLength(float leaderLineLength)
/*     */   {
/* 356 */     getDictionary().setFloat("LL", leaderLineLength);
/*     */   }
/*     */ 
/*     */   public float getLeaderLineExtensionLength()
/*     */   {
/* 366 */     return getDictionary().getFloat("LLE");
/*     */   }
/*     */ 
/*     */   public void setLeaderLineExtensionLength(float leaderLineExtensionLength)
/*     */   {
/* 376 */     getDictionary().setFloat("LLE", leaderLineExtensionLength);
/*     */   }
/*     */ 
/*     */   public float getLeaderLineOffsetLength()
/*     */   {
/* 386 */     return getDictionary().getFloat("LLO");
/*     */   }
/*     */ 
/*     */   public void setLeaderLineOffsetLength(float leaderLineOffsetLength)
/*     */   {
/* 396 */     getDictionary().setFloat("LLO", leaderLineOffsetLength);
/*     */   }
/*     */ 
/*     */   public String getCaptionPositioning()
/*     */   {
/* 406 */     return getDictionary().getString("CP");
/*     */   }
/*     */ 
/*     */   public void setCaptionPositioning(String captionPositioning)
/*     */   {
/* 417 */     getDictionary().setString("CP", captionPositioning);
/*     */   }
/*     */ 
/*     */   public void setCaptionHorizontalOffset(float offset)
/*     */   {
/* 427 */     COSArray array = (COSArray)getDictionary().getDictionaryObject("CO");
/* 428 */     if (array == null)
/*     */     {
/* 430 */       array = new COSArray();
/* 431 */       array.setFloatArray(new float[] { offset, 0.0F });
/* 432 */       getDictionary().setItem("CO", array);
/*     */     }
/*     */     else
/*     */     {
/* 436 */       array.set(0, new COSFloat(offset));
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getCaptionHorizontalOffset()
/*     */   {
/* 447 */     float retval = 0.0F;
/* 448 */     COSArray array = (COSArray)getDictionary().getDictionaryObject("CO");
/* 449 */     if (array != null)
/*     */     {
/* 451 */       retval = array.toFloatArray()[0];
/*     */     }
/*     */ 
/* 454 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setCaptionVerticalOffset(float offset)
/*     */   {
/* 464 */     COSArray array = (COSArray)getDictionary().getDictionaryObject("CO");
/* 465 */     if (array == null)
/*     */     {
/* 467 */       array = new COSArray();
/* 468 */       array.setFloatArray(new float[] { 0.0F, offset });
/* 469 */       getDictionary().setItem("CO", array);
/*     */     }
/*     */     else
/*     */     {
/* 473 */       array.set(1, new COSFloat(offset));
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getCaptionVerticalOffset()
/*     */   {
/* 484 */     float retval = 0.0F;
/* 485 */     COSArray array = (COSArray)getDictionary().getDictionaryObject("CO");
/* 486 */     if (array != null)
/*     */     {
/* 488 */       retval = array.toFloatArray()[1];
/*     */     }
/* 490 */     return retval;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLine
 * JD-Core Version:    0.6.2
 */