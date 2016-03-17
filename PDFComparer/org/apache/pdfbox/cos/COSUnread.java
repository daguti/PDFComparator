/*    */ package org.apache.pdfbox.cos;
/*    */ 
/*    */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*    */ import org.apache.pdfbox.pdfparser.ConformingPDFParser;
/*    */ 
/*    */ public class COSUnread extends COSBase
/*    */ {
/*    */   private long objectNumber;
/*    */   private long generation;
/*    */   private ConformingPDFParser parser;
/*    */ 
/*    */   public COSUnread()
/*    */   {
/*    */   }
/*    */ 
/*    */   public COSUnread(long objectNumber, long generation)
/*    */   {
/* 37 */     this();
/* 38 */     this.objectNumber = objectNumber;
/* 39 */     this.generation = generation;
/*    */   }
/*    */ 
/*    */   public COSUnread(long objectNumber, long generation, ConformingPDFParser parser) {
/* 43 */     this(objectNumber, generation);
/* 44 */     this.parser = parser;
/*    */   }
/*    */ 
/*    */   public Object accept(ICOSVisitor visitor)
/*    */     throws COSVisitorException
/*    */   {
/* 50 */     throw new UnsupportedOperationException("COSUnread can not be written/visited.");
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 55 */     return "COSUnread{" + this.objectNumber + "," + this.generation + "}";
/*    */   }
/*    */ 
/*    */   public long getObjectNumber()
/*    */   {
/* 62 */     return this.objectNumber;
/*    */   }
/*    */ 
/*    */   public void setObjectNumber(long objectNumber)
/*    */   {
/* 69 */     this.objectNumber = objectNumber;
/*    */   }
/*    */ 
/*    */   public long getGeneration()
/*    */   {
/* 76 */     return this.generation;
/*    */   }
/*    */ 
/*    */   public void setGeneration(long generation)
/*    */   {
/* 83 */     this.generation = generation;
/*    */   }
/*    */ 
/*    */   public ConformingPDFParser getParser()
/*    */   {
/* 90 */     return this.parser;
/*    */   }
/*    */ 
/*    */   public void setParser(ConformingPDFParser parser)
/*    */   {
/* 97 */     this.parser = parser;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSUnread
 * JD-Core Version:    0.6.2
 */