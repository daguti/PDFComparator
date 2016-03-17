/*     */ package org.apache.pdfbox.pdmodel.interactive.form;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
/*     */ import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSeedValue;
/*     */ import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
/*     */ 
/*     */ public class PDSignatureField extends PDField
/*     */ {
/*     */   public PDSignatureField(PDAcroForm theAcroForm, COSDictionary field)
/*     */     throws IOException
/*     */   {
/*  50 */     super(theAcroForm, field);
/*     */ 
/*  52 */     getDictionary().setItem(COSName.TYPE, COSName.ANNOT);
/*  53 */     getDictionary().setName(COSName.SUBTYPE, "Widget");
/*     */   }
/*     */ 
/*     */   public PDSignatureField(PDAcroForm theAcroForm)
/*     */     throws IOException
/*     */   {
/*  65 */     super(theAcroForm);
/*  66 */     getDictionary().setItem(COSName.FT, COSName.SIG);
/*  67 */     getWidget().setLocked(true);
/*  68 */     getWidget().setPrinted(true);
/*  69 */     setPartialName(generatePartialName());
/*  70 */     getDictionary().setItem(COSName.TYPE, COSName.ANNOT);
/*  71 */     getDictionary().setName(COSName.SUBTYPE, "Widget");
/*     */   }
/*     */ 
/*     */   private String generatePartialName()
/*     */     throws IOException
/*     */   {
/*  81 */     PDAcroForm acroForm = getAcroForm();
/*  82 */     List fields = acroForm.getFields();
/*     */ 
/*  84 */     String fieldName = "Signature";
/*  85 */     int i = 1;
/*     */ 
/*  87 */     Set sigNames = new HashSet();
/*     */ 
/*  89 */     for (Iterator i$ = fields.iterator(); i$.hasNext(); ) { Object object = i$.next();
/*     */ 
/*  91 */       if ((object instanceof PDSignatureField))
/*     */       {
/*  93 */         sigNames.add(((PDSignatureField)object).getPartialName());
/*     */       }
/*     */     }
/*     */ 
/*  97 */     while (sigNames.contains(fieldName + i))
/*     */     {
/*  99 */       i++;
/*     */     }
/* 101 */     return fieldName + i;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void setValue(String value)
/*     */     throws IOException
/*     */   {
/* 116 */     throw new RuntimeException("Can't set signature as String, use setSignature(PDSignature) instead");
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String getValue()
/*     */     throws IOException
/*     */   {
/* 131 */     throw new RuntimeException("Can't get signature as String, use getSignature() instead.");
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 142 */     return "PDSignature";
/*     */   }
/*     */ 
/*     */   public void setSignature(PDSignature value)
/*     */   {
/* 152 */     getDictionary().setItem(COSName.V, value);
/*     */   }
/*     */ 
/*     */   public PDSignature getSignature()
/*     */   {
/* 163 */     COSBase dictionary = getDictionary().getDictionaryObject(COSName.V);
/* 164 */     if (dictionary == null)
/*     */     {
/* 166 */       return null;
/*     */     }
/* 168 */     return new PDSignature((COSDictionary)dictionary);
/*     */   }
/*     */ 
/*     */   public PDSeedValue getSeedValue()
/*     */   {
/* 180 */     COSDictionary dict = (COSDictionary)getDictionary().getDictionaryObject(COSName.SV);
/* 181 */     PDSeedValue sv = null;
/* 182 */     if (dict != null)
/*     */     {
/* 184 */       sv = new PDSeedValue(dict);
/*     */     }
/* 186 */     return sv;
/*     */   }
/*     */ 
/*     */   public void setSeedValue(PDSeedValue sv)
/*     */   {
/* 198 */     if (sv != null)
/*     */     {
/* 200 */       getDictionary().setItem(COSName.SV, sv.getCOSObject());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField
 * JD-Core Version:    0.6.2
 */