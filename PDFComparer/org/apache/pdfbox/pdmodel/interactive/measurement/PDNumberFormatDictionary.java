/*     */ package org.apache.pdfbox.pdmodel.interactive.measurement;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class PDNumberFormatDictionary
/*     */   implements COSObjectable
/*     */ {
/*     */   public static final String TYPE = "NumberFormat";
/*     */   public static final String LABEL_SUFFIX_TO_VALUE = "S";
/*     */   public static final String LABEL_PREFIX_TO_VALUE = "P";
/*     */   public static final String FRACTIONAL_DISPLAY_DECIMAL = "D";
/*     */   public static final String FRACTIONAL_DISPLAY_FRACTION = "F";
/*     */   public static final String FRACTIONAL_DISPLAY_ROUND = "R";
/*     */   public static final String FRACTIONAL_DISPLAY_TRUNCATE = "T";
/*     */   private COSDictionary numberFormatDictionary;
/*     */ 
/*     */   public PDNumberFormatDictionary()
/*     */   {
/*  71 */     this.numberFormatDictionary = new COSDictionary();
/*  72 */     this.numberFormatDictionary.setName(COSName.TYPE, "NumberFormat");
/*     */   }
/*     */ 
/*     */   public PDNumberFormatDictionary(COSDictionary dictionary)
/*     */   {
/*  82 */     this.numberFormatDictionary = dictionary;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  90 */     return this.numberFormatDictionary;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/* 100 */     return this.numberFormatDictionary;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 111 */     return "NumberFormat";
/*     */   }
/*     */ 
/*     */   public String getUnits()
/*     */   {
/* 121 */     return getDictionary().getString("U");
/*     */   }
/*     */ 
/*     */   public void setUnits(String units)
/*     */   {
/* 131 */     getDictionary().setString("U", units);
/*     */   }
/*     */ 
/*     */   public float getConversionFactor()
/*     */   {
/* 141 */     return getDictionary().getFloat("C");
/*     */   }
/*     */ 
/*     */   public void setConversionFactor(float conversionFactor)
/*     */   {
/* 151 */     getDictionary().setFloat("C", conversionFactor);
/*     */   }
/*     */ 
/*     */   public String getFractionalDisplay()
/*     */   {
/* 161 */     return getDictionary().getString("F", "D");
/*     */   }
/*     */ 
/*     */   public void setFractionalDisplay(String fractionalDisplay)
/*     */   {
/* 171 */     if ((fractionalDisplay == null) || ("D".equals(fractionalDisplay)) || ("F".equals(fractionalDisplay)) || ("R".equals(fractionalDisplay)) || ("T".equals(fractionalDisplay)))
/*     */     {
/* 177 */       getDictionary().setString("F", fractionalDisplay);
/*     */     }
/*     */     else
/*     */     {
/* 181 */       throw new IllegalArgumentException("Value must be \"D\", \"F\", \"R\", or \"T\", (or null).");
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getDenominator()
/*     */   {
/* 192 */     return getDictionary().getInt("D");
/*     */   }
/*     */ 
/*     */   public void setDenominator(int denominator)
/*     */   {
/* 202 */     getDictionary().setInt("D", denominator);
/*     */   }
/*     */ 
/*     */   public boolean isFD()
/*     */   {
/* 212 */     return getDictionary().getBoolean("FD", false);
/*     */   }
/*     */ 
/*     */   public void setFD(boolean fd)
/*     */   {
/* 222 */     getDictionary().setBoolean("FD", fd);
/*     */   }
/*     */ 
/*     */   public String getThousandsSeparator()
/*     */   {
/* 232 */     return getDictionary().getString("RT", ",");
/*     */   }
/*     */ 
/*     */   public void setThousandsSeparator(String thousandsSeparator)
/*     */   {
/* 242 */     getDictionary().setString("RT", thousandsSeparator);
/*     */   }
/*     */ 
/*     */   public String getDecimalSeparator()
/*     */   {
/* 252 */     return getDictionary().getString("RD", ".");
/*     */   }
/*     */ 
/*     */   public void setDecimalSeparator(String decimalSeparator)
/*     */   {
/* 262 */     getDictionary().setString("RD", decimalSeparator);
/*     */   }
/*     */ 
/*     */   public String getLabelPrefixString()
/*     */   {
/* 271 */     return getDictionary().getString("PS", " ");
/*     */   }
/*     */ 
/*     */   public void setLabelPrefixString(String labelPrefixString)
/*     */   {
/* 280 */     getDictionary().setString("PS", labelPrefixString);
/*     */   }
/*     */ 
/*     */   public String getLabelSuffixString()
/*     */   {
/* 290 */     return getDictionary().getString("SS", " ");
/*     */   }
/*     */ 
/*     */   public void setLabelSuffixString(String labelSuffixString)
/*     */   {
/* 300 */     getDictionary().setString("SS", labelSuffixString);
/*     */   }
/*     */ 
/*     */   public String getLabelPositionToValue()
/*     */   {
/* 310 */     return getDictionary().getString("O", "S");
/*     */   }
/*     */ 
/*     */   public void setLabelPositionToValue(String labelPositionToValue)
/*     */   {
/* 321 */     if ((labelPositionToValue == null) || ("P".equals(labelPositionToValue)) || ("S".equals(labelPositionToValue)))
/*     */     {
/* 325 */       getDictionary().setString("O", labelPositionToValue);
/*     */     }
/*     */     else
/*     */     {
/* 329 */       throw new IllegalArgumentException("Value must be \"S\", or \"P\" (or null).");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.measurement.PDNumberFormatDictionary
 * JD-Core Version:    0.6.2
 */