/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ 
/*     */ public class PDObjectStream extends PDStream
/*     */ {
/*     */   public PDObjectStream(COSStream str)
/*     */   {
/*  43 */     super(str);
/*     */   }
/*     */ 
/*     */   public static PDObjectStream createStream(PDDocument document)
/*     */   {
/*  54 */     COSStream cosStream = document.getDocument().createCOSStream();
/*  55 */     PDObjectStream strm = new PDObjectStream(cosStream);
/*  56 */     strm.getStream().setName(COSName.TYPE, "ObjStm");
/*  57 */     return strm;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  67 */     return getStream().getNameAsString(COSName.TYPE);
/*     */   }
/*     */ 
/*     */   public int getNumberOfObjects()
/*     */   {
/*  77 */     return getStream().getInt(COSName.N, 0);
/*     */   }
/*     */ 
/*     */   public void setNumberOfObjects(int n)
/*     */   {
/*  87 */     getStream().setInt(COSName.N, n);
/*     */   }
/*     */ 
/*     */   public int getFirstByteOffset()
/*     */   {
/*  97 */     return getStream().getInt(COSName.FIRST, 0);
/*     */   }
/*     */ 
/*     */   public void setFirstByteOffset(int n)
/*     */   {
/* 107 */     getStream().setInt(COSName.FIRST, n);
/*     */   }
/*     */ 
/*     */   public PDObjectStream getExtends()
/*     */   {
/* 118 */     PDObjectStream retval = null;
/* 119 */     COSStream stream = (COSStream)getStream().getDictionaryObject(COSName.EXTENDS);
/* 120 */     if (stream != null)
/*     */     {
/* 122 */       retval = new PDObjectStream(stream);
/*     */     }
/* 124 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setExtends(PDObjectStream stream)
/*     */   {
/* 136 */     getStream().setItem(COSName.EXTENDS, stream);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDObjectStream
 * JD-Core Version:    0.6.2
 */