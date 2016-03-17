/*    */ package org.apache.pdfbox;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.apache.pdfbox.cos.COSDocument;
/*    */ import org.apache.pdfbox.cos.COSObject;
/*    */ import org.apache.pdfbox.cos.COSStream;
/*    */ import org.apache.pdfbox.pdfparser.PDFObjectStreamParser;
/*    */ import org.apache.pdfbox.pdmodel.PDDocument;
/*    */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*    */ 
/*    */ public class PdfDecompressor
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 42 */     if (args.length < 1) {
/* 43 */       usage();
/*    */     }
/* 45 */     String inputFilename = args[0];
/*    */     String outputFilename;
/*    */     String outputFilename;
/* 47 */     if (args.length > 1) {
/* 48 */       outputFilename = args[1];
/*    */     }
/*    */     else
/*    */     {
/*    */       String outputFilename;
/* 50 */       if (inputFilename.matches(".*\\.[pP][dD][fF]$"))
/* 51 */         outputFilename = inputFilename.replaceAll("\\.[pP][dD][fF]$", ".unc.pdf");
/*    */       else {
/* 53 */         outputFilename = inputFilename + ".unc.pdf";
/*    */       }
/*    */     }
/* 56 */     PDDocument doc = null;
/*    */     try {
/* 58 */       doc = PDDocument.load(inputFilename);
/* 59 */       for (COSObject objStream : doc.getDocument().getObjectsByType("ObjStm")) {
/* 60 */         COSStream stream = (COSStream)objStream.getObject();
/* 61 */         PDFObjectStreamParser sp = new PDFObjectStreamParser(stream, doc.getDocument());
/* 62 */         sp.parse();
/* 63 */         for (COSObject next : sp.getObjects()) {
/* 64 */           COSObjectKey key = new COSObjectKey(next);
/* 65 */           COSObject obj = doc.getDocument().getObjectFromPool(key);
/* 66 */           obj.setObject(next.getObject());
/*    */         }
/* 68 */         doc.getDocument().removeObject(new COSObjectKey(objStream));
/*    */       }
/* 70 */       doc.save(outputFilename);
/*    */     } catch (Exception e) {
/* 72 */       System.out.println("Error processing file: " + e.getMessage());
/*    */     } finally {
/* 74 */       if (doc != null) try {
/* 75 */           doc.close();
/*    */         }
/*    */         catch (Exception e)
/*    */         {
/*    */         } 
/*    */     }
/*    */   }
/*    */ 
/* 83 */   private static void usage() { System.err.println("Usage: java -cp /path/to/pdfbox.jar;/path/to/commons-logging-api.jar org.apache.pdfbox.PdfDecompressor <input PDF File> [<Output PDF File>]\n  <input PDF File>       The PDF document to decompress\n  <output PDF File>      The output filename (default is to replace .pdf with .unc.pdf)");
/*    */ 
/* 87 */     System.exit(1);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.PdfDecompressor
 * JD-Core Version:    0.6.2
 */