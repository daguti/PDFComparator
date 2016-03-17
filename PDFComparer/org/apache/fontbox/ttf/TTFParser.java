/*    */ package org.apache.fontbox.ttf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class TTFParser extends AbstractTTFParser
/*    */ {
/*    */   public TTFParser()
/*    */   {
/* 32 */     super(false);
/*    */   }
/*    */ 
/*    */   public TTFParser(boolean isEmbedded)
/*    */   {
/* 42 */     super(isEmbedded);
/*    */   }
/*    */ 
/*    */   public TTFParser(boolean isEmbedded, boolean parseOnDemand)
/*    */   {
/* 53 */     super(isEmbedded, parseOnDemand);
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */     throws IOException
/*    */   {
/* 65 */     if (args.length != 1)
/*    */     {
/* 67 */       System.err.println("usage: java org.pdfbox.ttf.TTFParser <ttf-file>");
/* 68 */       System.exit(-1);
/*    */     }
/* 70 */     TTFParser parser = new TTFParser();
/* 71 */     TrueTypeFont font = parser.parseTTF(args[0]);
/* 72 */     System.out.println("Font:" + font);
/*    */   }
/*    */ 
/*    */   protected void parseTables(TrueTypeFont font, TTFDataStream raf)
/*    */     throws IOException
/*    */   {
/* 80 */     super.parseTables(font, raf);
/*    */ 
/* 82 */     if (font.getCMAP() == null)
/* 83 */       throw new IOException("cmap is mandatory");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.TTFParser
 * JD-Core Version:    0.6.2
 */