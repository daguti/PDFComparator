/*     */ package org.apache.pdfbox.pdmodel.interactive.form;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDCheckbox extends PDChoiceButton
/*     */ {
/*  34 */   private static final COSName KEY = COSName.getPDFName("AS");
/*  35 */   private static final COSName OFF_VALUE = COSName.getPDFName("Off");
/*     */   private COSName value;
/*     */ 
/*     */   public PDCheckbox(PDAcroForm theAcroForm, COSDictionary field)
/*     */   {
/*  47 */     super(theAcroForm, field);
/*  48 */     COSDictionary ap = (COSDictionary)field.getDictionaryObject(COSName.getPDFName("AP"));
/*  49 */     if (ap != null)
/*     */     {
/*  51 */       COSBase n = ap.getDictionaryObject(COSName.getPDFName("N"));
/*     */ 
/*  53 */       if ((n instanceof COSDictionary))
/*     */       {
/*  55 */         for (COSName name : ((COSDictionary)n).keySet())
/*     */         {
/*  57 */           if (!name.equals(OFF_VALUE))
/*     */           {
/*  59 */             this.value = name;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*  67 */       this.value = ((COSName)getDictionary().getDictionaryObject("V"));
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isChecked()
/*     */   {
/*  78 */     boolean retval = false;
/*  79 */     String onValue = getOnValue();
/*  80 */     COSName radioValue = (COSName)getDictionary().getDictionaryObject(KEY);
/*  81 */     if ((radioValue != null) && (this.value != null) && (radioValue.getName().equals(onValue)))
/*     */     {
/*  83 */       retval = true;
/*     */     }
/*     */ 
/*  86 */     return retval;
/*     */   }
/*     */ 
/*     */   public void check()
/*     */   {
/*  94 */     getDictionary().setItem(KEY, this.value);
/*     */   }
/*     */ 
/*     */   public void unCheck()
/*     */   {
/* 102 */     getDictionary().setItem(KEY, OFF_VALUE);
/*     */   }
/*     */ 
/*     */   public void setValue(String newValue)
/*     */   {
/* 110 */     getDictionary().setName("V", newValue);
/* 111 */     if (newValue == null)
/*     */     {
/* 113 */       getDictionary().setItem(KEY, OFF_VALUE);
/*     */     }
/*     */     else
/*     */     {
/* 117 */       getDictionary().setName(KEY, newValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getOffValue()
/*     */   {
/* 128 */     return OFF_VALUE.getName();
/*     */   }
/*     */ 
/*     */   public String getOnValue()
/*     */   {
/* 138 */     String retval = null;
/* 139 */     COSDictionary ap = (COSDictionary)getDictionary().getDictionaryObject(COSName.getPDFName("AP"));
/* 140 */     COSBase n = ap.getDictionaryObject(COSName.getPDFName("N"));
/*     */ 
/* 143 */     if ((n instanceof COSDictionary))
/*     */     {
/* 145 */       for (COSName key : ((COSDictionary)n).keySet())
/*     */       {
/* 147 */         if (!key.equals(OFF_VALUE))
/*     */         {
/* 149 */           retval = key.getName();
/*     */         }
/*     */       }
/*     */     }
/* 153 */     return retval;
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */     throws IOException
/*     */   {
/* 165 */     return getDictionary().getNameAsString("V");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDCheckbox
 * JD-Core Version:    0.6.2
 */