/*     */ package org.apache.pdfbox.pdmodel.interactive.form;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
/*     */ 
/*     */ public class PDFieldFactory
/*     */ {
/*     */   private static final int RADIO_BITMASK = 32768;
/*     */   private static final int PUSHBUTTON_BITMASK = 65536;
/*     */   private static final int RADIOS_IN_UNISON_BITMASK = 33554432;
/*     */   private static final String FIELD_TYPE_BTN = "Btn";
/*     */   private static final String FIELD_TYPE_TX = "Tx";
/*     */   private static final String FIELD_TYPE_CH = "Ch";
/*     */   private static final String FIELD_TYPE_SIG = "Sig";
/*     */ 
/*     */   public static PDField createField(PDAcroForm acroForm, COSDictionary field)
/*     */     throws IOException
/*     */   {
/*  67 */     PDField pdField = new PDUnknownField(acroForm, field);
/*  68 */     if (isButton(pdField))
/*     */     {
/*  70 */       int flags = pdField.getFieldFlags();
/*     */ 
/*  75 */       COSArray kids = (COSArray)field.getDictionaryObject(COSName.getPDFName("Kids"));
/*  76 */       if ((kids != null) || (isRadio(flags)))
/*     */       {
/*  78 */         pdField = new PDRadioCollection(acroForm, field);
/*     */       }
/*  80 */       else if (isPushButton(flags))
/*     */       {
/*  82 */         pdField = new PDPushButton(acroForm, field);
/*     */       }
/*     */       else
/*     */       {
/*  86 */         pdField = new PDCheckbox(acroForm, field);
/*     */       }
/*     */ 
/*     */     }
/*  90 */     else if (isChoiceField(pdField))
/*     */     {
/*  92 */       pdField = new PDChoiceField(acroForm, field);
/*     */     }
/*  94 */     else if (isTextbox(pdField))
/*     */     {
/*  96 */       pdField = new PDTextbox(acroForm, field);
/*     */     }
/*  98 */     else if (isSignature(pdField))
/*     */     {
/* 100 */       pdField = new PDSignatureField(acroForm, field);
/*     */     }
/*     */ 
/* 106 */     return pdField;
/*     */   }
/*     */ 
/*     */   private static boolean isRadio(int flags)
/*     */   {
/* 119 */     return (flags & 0x8000) > 0;
/*     */   }
/*     */ 
/*     */   private static boolean isPushButton(int flags)
/*     */   {
/* 132 */     return (flags & 0x10000) > 0;
/*     */   }
/*     */ 
/*     */   private static boolean isChoiceField(PDField field)
/*     */     throws IOException
/*     */   {
/* 144 */     return "Ch".equals(field.findFieldType());
/*     */   }
/*     */ 
/*     */   private static boolean isButton(PDField field)
/*     */     throws IOException
/*     */   {
/* 157 */     String ft = field.findFieldType();
/* 158 */     boolean retval = "Btn".equals(ft);
/* 159 */     List kids = field.getKids();
/* 160 */     if ((ft == null) && (kids != null) && (kids.size() > 0))
/*     */     {
/* 164 */       Object obj = kids.get(0);
/* 165 */       COSDictionary kidDict = null;
/* 166 */       if ((obj instanceof PDField))
/*     */       {
/* 168 */         kidDict = ((PDField)obj).getDictionary();
/*     */       }
/* 170 */       else if ((obj instanceof PDAnnotationWidget))
/*     */       {
/* 172 */         kidDict = ((PDAnnotationWidget)obj).getDictionary();
/*     */       }
/*     */       else
/*     */       {
/* 176 */         throw new IOException("Error:Unexpected type of kids field:" + obj);
/*     */       }
/* 178 */       retval = isButton(new PDUnknownField(field.getAcroForm(), kidDict));
/*     */     }
/* 180 */     return retval;
/*     */   }
/*     */ 
/*     */   private static boolean isSignature(PDField field)
/*     */     throws IOException
/*     */   {
/* 191 */     return "Sig".equals(field.findFieldType());
/*     */   }
/*     */ 
/*     */   private static boolean isTextbox(PDField field)
/*     */     throws IOException
/*     */   {
/* 202 */     return "Tx".equals(field.findFieldType());
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDFieldFactory
 * JD-Core Version:    0.6.2
 */