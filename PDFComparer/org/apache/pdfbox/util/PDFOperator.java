/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ public class PDFOperator
/*     */ {
/*     */   private String theOperator;
/*     */   private byte[] imageData;
/*     */   private ImageParameters imageParameters;
/*  34 */   private static final ConcurrentHashMap<String, PDFOperator> operators = new ConcurrentHashMap();
/*     */ 
/*     */   private PDFOperator(String aOperator)
/*     */   {
/*  43 */     this.theOperator = aOperator;
/*  44 */     if (aOperator.startsWith("/"))
/*     */     {
/*  46 */       throw new RuntimeException("Operators are not allowed to start with / '" + aOperator + "'");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static PDFOperator getOperator(String operator)
/*     */   {
/*  59 */     PDFOperator operation = null;
/*  60 */     if ((operator.equals("ID")) || (operator.equals("BI")))
/*     */     {
/*  63 */       operation = new PDFOperator(operator);
/*     */     }
/*     */     else
/*     */     {
/*  67 */       operation = (PDFOperator)operators.get(operator);
/*  68 */       if (operation == null)
/*     */       {
/*  72 */         operation = (PDFOperator)operators.putIfAbsent(operator, new PDFOperator(operator));
/*  73 */         if (operation == null)
/*     */         {
/*  75 */           operation = (PDFOperator)operators.get(operator);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  80 */     return operation;
/*     */   }
/*     */ 
/*     */   public String getOperation()
/*     */   {
/*  90 */     return this.theOperator;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 100 */     return "PDFOperator{" + this.theOperator + "}";
/*     */   }
/*     */ 
/*     */   public byte[] getImageData()
/*     */   {
/* 111 */     return this.imageData;
/*     */   }
/*     */ 
/*     */   public void setImageData(byte[] imageDataArray)
/*     */   {
/* 121 */     this.imageData = imageDataArray;
/*     */   }
/*     */ 
/*     */   public ImageParameters getImageParameters()
/*     */   {
/* 131 */     return this.imageParameters;
/*     */   }
/*     */ 
/*     */   public void setImageParameters(ImageParameters params)
/*     */   {
/* 141 */     this.imageParameters = params;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.PDFOperator
 * JD-Core Version:    0.6.2
 */