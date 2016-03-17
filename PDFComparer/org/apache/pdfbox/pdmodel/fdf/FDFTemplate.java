/*     */ package org.apache.pdfbox.pdmodel.fdf;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class FDFTemplate
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary template;
/*     */ 
/*     */   public FDFTemplate()
/*     */   {
/*  44 */     this.template = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public FDFTemplate(COSDictionary t)
/*     */   {
/*  54 */     this.template = t;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  64 */     return this.template;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  74 */     return this.template;
/*     */   }
/*     */ 
/*     */   public FDFNamedPageReference getTemplateReference()
/*     */   {
/*  84 */     FDFNamedPageReference retval = null;
/*  85 */     COSDictionary dict = (COSDictionary)this.template.getDictionaryObject("TRef");
/*  86 */     if (dict != null)
/*     */     {
/*  88 */       retval = new FDFNamedPageReference(dict);
/*     */     }
/*  90 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setTemplateReference(FDFNamedPageReference tRef)
/*     */   {
/* 100 */     this.template.setItem("TRef", tRef);
/*     */   }
/*     */ 
/*     */   public List getFields()
/*     */   {
/* 110 */     List retval = null;
/* 111 */     COSArray array = (COSArray)this.template.getDictionaryObject("Fields");
/* 112 */     if (array != null)
/*     */     {
/* 114 */       List fields = new ArrayList();
/* 115 */       for (int i = 0; i < array.size(); i++)
/*     */       {
/* 117 */         fields.add(new FDFField((COSDictionary)array.getObject(i)));
/*     */       }
/* 119 */       retval = new COSArrayList(fields, array);
/*     */     }
/* 121 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFields(List fields)
/*     */   {
/* 131 */     this.template.setItem("Fields", COSArrayList.converterToCOSArray(fields));
/*     */   }
/*     */ 
/*     */   public boolean shouldRename()
/*     */   {
/* 141 */     return this.template.getBoolean("Rename", false);
/*     */   }
/*     */ 
/*     */   public void setRename(boolean value)
/*     */   {
/* 151 */     this.template.setBoolean("Rename", value);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFTemplate
 * JD-Core Version:    0.6.2
 */