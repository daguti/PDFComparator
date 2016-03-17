/*    */ package org.apache.pdfbox;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.apache.pdfbox.util.PDFMergerUtility;
/*    */ 
/*    */ public class PDFMerger
/*    */ {
/*    */   public static void main(String[] args)
/*    */     throws Exception
/*    */   {
/* 43 */     PDFMerger merge = new PDFMerger();
/* 44 */     merge.merge(args);
/*    */   }
/*    */ 
/*    */   private void merge(String[] args) throws Exception
/*    */   {
/* 49 */     String destinationFileName = "";
/*    */ 
/* 52 */     boolean nonSeq = false;
/* 53 */     int firstFileArgPos = 0;
/* 54 */     if ((args.length > 0) && (args[0].equals("-nonSeq")))
/*    */     {
/* 56 */       nonSeq = true;
/* 57 */       firstFileArgPos = 1;
/*    */     }
/*    */ 
/* 60 */     if (args.length - firstFileArgPos < 3)
/*    */     {
/* 62 */       usage();
/*    */     }
/*    */ 
/* 65 */     PDFMergerUtility merger = new PDFMergerUtility();
/* 66 */     for (int i = firstFileArgPos; i < args.length - 1; i++)
/*    */     {
/* 68 */       String sourceFileName = args[i];
/* 69 */       merger.addSource(sourceFileName);
/*    */     }
/*    */ 
/* 72 */     destinationFileName = args[(args.length - 1)];
/*    */ 
/* 74 */     merger.setDestinationFileName(destinationFileName);
/*    */ 
/* 76 */     if (nonSeq)
/*    */     {
/* 78 */       merger.mergeDocumentsNonSeq(null);
/*    */     }
/*    */     else
/*    */     {
/* 82 */       merger.mergeDocuments();
/*    */     }
/*    */   }
/*    */ 
/*    */   private static void usage()
/*    */   {
/* 91 */     System.err.println("Usage: java -jar pdfbox-app-x.y.z.jar PDFMerger [-nonSeq] <Source PDF File 2..n> <Destination PDF File>\n  -nonSeq                      use the non-sequential parser\n  <Source PDF File 2..n>       2 or more source PDF documents to merge\n  <Destination PDF File>       The PDF document to save the merged documents to\n");
/*    */ 
/* 96 */     System.exit(1);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.PDFMerger
 * JD-Core Version:    0.6.2
 */