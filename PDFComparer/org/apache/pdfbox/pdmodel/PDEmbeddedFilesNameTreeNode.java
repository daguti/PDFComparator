/*    */ package org.apache.pdfbox.pdmodel;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*    */ import org.apache.pdfbox.pdmodel.common.PDNameTreeNode;
/*    */ import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
/*    */ 
/*    */ public class PDEmbeddedFilesNameTreeNode extends PDNameTreeNode
/*    */ {
/*    */   public PDEmbeddedFilesNameTreeNode()
/*    */   {
/* 40 */     super(PDComplexFileSpecification.class);
/*    */   }
/*    */ 
/*    */   public PDEmbeddedFilesNameTreeNode(COSDictionary dic)
/*    */   {
/* 50 */     super(dic, PDComplexFileSpecification.class);
/*    */   }
/*    */ 
/*    */   protected COSObjectable convertCOSToPD(COSBase base)
/*    */     throws IOException
/*    */   {
/* 58 */     return new PDComplexFileSpecification((COSDictionary)base);
/*    */   }
/*    */ 
/*    */   protected PDNameTreeNode createChildNode(COSDictionary dic)
/*    */   {
/* 66 */     return new PDEmbeddedFilesNameTreeNode(dic);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode
 * JD-Core Version:    0.6.2
 */