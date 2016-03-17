/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDDefaultAttributeObject extends PDAttributeObject
/*     */ {
/*     */   public PDDefaultAttributeObject()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDDefaultAttributeObject(COSDictionary dictionary)
/*     */   {
/*  51 */     super(dictionary);
/*     */   }
/*     */ 
/*     */   public List<String> getAttributeNames()
/*     */   {
/*  62 */     List attrNames = new ArrayList();
/*  63 */     for (Map.Entry entry : getCOSDictionary().entrySet())
/*     */     {
/*  65 */       COSName key = (COSName)entry.getKey();
/*  66 */       if (!COSName.O.equals(key))
/*     */       {
/*  68 */         attrNames.add(key.getName());
/*     */       }
/*     */     }
/*  71 */     return attrNames;
/*     */   }
/*     */ 
/*     */   public COSBase getAttributeValue(String attrName)
/*     */   {
/*  82 */     return getCOSDictionary().getDictionaryObject(attrName);
/*     */   }
/*     */ 
/*     */   protected COSBase getAttributeValue(String attrName, COSBase defaultValue)
/*     */   {
/*  94 */     COSBase value = getCOSDictionary().getDictionaryObject(attrName);
/*  95 */     if (value == null)
/*     */     {
/*  97 */       return defaultValue;
/*     */     }
/*  99 */     return value;
/*     */   }
/*     */ 
/*     */   public void setAttribute(String attrName, COSBase attrValue)
/*     */   {
/* 110 */     COSBase old = getAttributeValue(attrName);
/* 111 */     getCOSDictionary().setItem(COSName.getPDFName(attrName), attrValue);
/* 112 */     potentiallyNotifyChanged(old, attrValue);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 118 */     StringBuilder sb = new StringBuilder().append(super.toString()).append(", attributes={");
/*     */ 
/* 120 */     Iterator it = getAttributeNames().iterator();
/* 121 */     while (it.hasNext())
/*     */     {
/* 123 */       String name = (String)it.next();
/* 124 */       sb.append(name).append('=').append(getAttributeValue(name));
/* 125 */       if (it.hasNext())
/*     */       {
/* 127 */         sb.append(", ");
/*     */       }
/*     */     }
/* 130 */     return '}';
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDDefaultAttributeObject
 * JD-Core Version:    0.6.2
 */