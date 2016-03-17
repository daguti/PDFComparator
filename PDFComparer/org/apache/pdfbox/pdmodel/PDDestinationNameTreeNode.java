/*    */ package org.apache.pdfbox.pdmodel;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*    */ import org.apache.pdfbox.pdmodel.common.PDNameTreeNode;
/*    */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDDestination;
/*    */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
/*    */ 
/*    */ public class PDDestinationNameTreeNode extends PDNameTreeNode
/*    */ {
/*    */   public PDDestinationNameTreeNode()
/*    */   {
/* 43 */     super(PDPageDestination.class);
/*    */   }
/*    */ 
/*    */   public PDDestinationNameTreeNode(COSDictionary dic)
/*    */   {
/* 53 */     super(dic, PDPageDestination.class);
/*    */   }
/*    */ 
/*    */   protected COSObjectable convertCOSToPD(COSBase base)
/*    */     throws IOException
/*    */   {
/* 61 */     COSBase destination = base;
/* 62 */     if ((base instanceof COSDictionary))
/*    */     {
/* 67 */       destination = ((COSDictionary)base).getDictionaryObject(COSName.D);
/*    */     }
/* 69 */     return PDDestination.create(destination);
/*    */   }
/*    */ 
/*    */   protected PDNameTreeNode createChildNode(COSDictionary dic)
/*    */   {
/* 77 */     return new PDDestinationNameTreeNode(dic);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.PDDestinationNameTreeNode
 * JD-Core Version:    0.6.2
 */