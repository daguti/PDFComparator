/*     */ package org.apache.pdfbox.pdmodel.interactive.form;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.util.BitFlagHelper;
/*     */ 
/*     */ public abstract class PDVariableText extends PDField
/*     */ {
/*     */   public static final int FLAG_MULTILINE = 4096;
/*     */   public static final int FLAG_PASSWORD = 8192;
/*     */   public static final int FLAG_FILE_SELECT = 1048576;
/*     */   public static final int FLAG_DO_NOT_SPELL_CHECK = 4194304;
/*     */   public static final int FLAG_DO_NOT_SCROLL = 8388608;
/*     */   public static final int FLAG_COMB = 16777216;
/*     */   public static final int FLAG_RICH_TEXT = 33554432;
/*     */   private COSString da;
/*     */   private PDAppearance appearance;
/*     */   public static final int QUADDING_LEFT = 0;
/*     */   public static final int QUADDING_CENTERED = 1;
/*     */   public static final int QUADDING_RIGHT = 2;
/*     */ 
/*     */   public PDVariableText(PDAcroForm theAcroForm)
/*     */   {
/*  96 */     super(theAcroForm);
/*     */   }
/*     */ 
/*     */   public PDVariableText(PDAcroForm theAcroForm, COSDictionary field)
/*     */   {
/* 107 */     super(theAcroForm, field);
/* 108 */     this.da = ((COSString)field.getDictionaryObject(COSName.DA));
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */     throws IOException
/*     */   {
/* 120 */     COSString fieldValue = new COSString(value);
/* 121 */     getDictionary().setItem(COSName.V, fieldValue);
/*     */ 
/* 127 */     if (this.appearance == null)
/*     */     {
/* 129 */       this.appearance = new PDAppearance(getAcroForm(), this);
/*     */     }
/* 131 */     this.appearance.setAppearanceValue(value);
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */     throws IOException
/*     */   {
/* 143 */     return getDictionary().getString(COSName.V);
/*     */   }
/*     */ 
/*     */   public boolean isMultiline()
/*     */   {
/* 151 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 4096);
/*     */   }
/*     */ 
/*     */   public void setMultiline(boolean multiline)
/*     */   {
/* 161 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 4096, multiline);
/*     */   }
/*     */ 
/*     */   public boolean isPassword()
/*     */   {
/* 169 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 8192);
/*     */   }
/*     */ 
/*     */   public void setPassword(boolean password)
/*     */   {
/* 179 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 8192, password);
/*     */   }
/*     */ 
/*     */   public boolean isFileSelect()
/*     */   {
/* 187 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 1048576);
/*     */   }
/*     */ 
/*     */   public void setFileSelect(boolean fileSelect)
/*     */   {
/* 197 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 1048576, fileSelect);
/*     */   }
/*     */ 
/*     */   public boolean doNotSpellCheck()
/*     */   {
/* 205 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 4194304);
/*     */   }
/*     */ 
/*     */   public void setDoNotSpellCheck(boolean doNotSpellCheck)
/*     */   {
/* 215 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 4194304, doNotSpellCheck);
/*     */   }
/*     */ 
/*     */   public boolean doNotScroll()
/*     */   {
/* 223 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 8388608);
/*     */   }
/*     */ 
/*     */   public void setDoNotScroll(boolean doNotScroll)
/*     */   {
/* 233 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 8388608, doNotScroll);
/*     */   }
/*     */ 
/*     */   public boolean shouldComb()
/*     */   {
/* 241 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 16777216);
/*     */   }
/*     */ 
/*     */   public void setComb(boolean comb)
/*     */   {
/* 251 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 16777216, comb);
/*     */   }
/*     */ 
/*     */   public boolean isRichText()
/*     */   {
/* 259 */     return BitFlagHelper.getFlag(getDictionary(), COSName.FF, 33554432);
/*     */   }
/*     */ 
/*     */   public void setRichText(boolean richText)
/*     */   {
/* 269 */     BitFlagHelper.setFlag(getDictionary(), COSName.FF, 33554432, richText);
/*     */   }
/*     */ 
/*     */   protected COSString getDefaultAppearance()
/*     */   {
/* 277 */     return this.da;
/*     */   }
/*     */ 
/*     */   public int getQ()
/*     */   {
/* 291 */     int retval = 0;
/* 292 */     COSNumber number = (COSNumber)getDictionary().getDictionaryObject(COSName.Q);
/* 293 */     if (number != null)
/*     */     {
/* 295 */       retval = number.intValue();
/*     */     }
/* 297 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setQ(int q)
/*     */   {
/* 307 */     getDictionary().setInt(COSName.Q, q);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDVariableText
 * JD-Core Version:    0.6.2
 */