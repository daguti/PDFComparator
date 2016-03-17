/*    */ package org.apache.pdfbox.pdmodel;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSStream;
/*    */ import org.apache.pdfbox.cos.COSString;
/*    */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*    */ import org.apache.pdfbox.pdmodel.common.PDNameTreeNode;
/*    */ import org.apache.pdfbox.pdmodel.common.PDTextStream;
/*    */ 
/*    */ public class PDJavascriptNameTreeNode extends PDNameTreeNode
/*    */ {
/*    */   public PDJavascriptNameTreeNode()
/*    */   {
/* 42 */     super(PDTextStream.class);
/*    */   }
/*    */ 
/*    */   public PDJavascriptNameTreeNode(COSDictionary dic)
/*    */   {
/* 52 */     super(dic, PDTextStream.class);
/*    */   }
/*    */ 
/*    */   protected COSObjectable convertCOSToPD(COSBase base)
/*    */     throws IOException
/*    */   {
/* 60 */     PDTextStream stream = null;
/* 61 */     if ((base instanceof COSString))
/*    */     {
/* 63 */       stream = new PDTextStream((COSString)base);
/*    */     }
/* 65 */     else if ((base instanceof COSStream))
/*    */     {
/* 67 */       stream = new PDTextStream((COSStream)base);
/*    */     }
/*    */     else
/*    */     {
/* 71 */       throw new IOException("Error creating Javascript object, expected either COSString or COSStream and not " + base);
/*    */     }
/*    */ 
/* 74 */     return stream;
/*    */   }
/*    */ 
/*    */   protected PDNameTreeNode createChildNode(COSDictionary dic)
/*    */   {
/* 82 */     return new PDJavascriptNameTreeNode(dic);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.PDJavascriptNameTreeNode
 * JD-Core Version:    0.6.2
 */