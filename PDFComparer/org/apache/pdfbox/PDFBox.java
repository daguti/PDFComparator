/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class PDFBox
/*     */ {
/*     */   public static void main(String[] args)
/*     */   {
/*  36 */     if (args.length > 0)
/*     */     {
/*  38 */       String command = args[0];
/*  39 */       String[] arguments = new String[args.length - 1];
/*  40 */       System.arraycopy(args, 1, arguments, 0, arguments.length);
/*  41 */       boolean exitAfterCallingMain = true;
/*     */       try
/*     */       {
/*  44 */         if (command.equals("ConvertColorspace"))
/*     */         {
/*  46 */           ConvertColorspace.main(arguments);
/*     */         }
/*  48 */         else if (command.equals("Decrypt"))
/*     */         {
/*  50 */           Decrypt.main(arguments);
/*     */         }
/*  52 */         else if (command.equals("Encrypt"))
/*     */         {
/*  54 */           Encrypt.main(arguments);
/*     */         }
/*  56 */         else if (command.equals("ExtractText"))
/*     */         {
/*  58 */           ExtractText.main(arguments);
/*     */         }
/*  60 */         else if (command.equals("ExtractImages"))
/*     */         {
/*  62 */           ExtractImages.main(arguments);
/*     */         }
/*  64 */         else if (command.equals("Overlay"))
/*     */         {
/*  66 */           Overlay.main(arguments);
/*     */         }
/*  68 */         else if (command.equals("OverlayPDF"))
/*     */         {
/*  70 */           OverlayPDF.main(arguments);
/*     */         }
/*  72 */         else if (command.equals("PrintPDF"))
/*     */         {
/*  74 */           PrintPDF.main(arguments);
/*     */         }
/*  76 */         else if (command.equals("PDFDebugger"))
/*     */         {
/*  78 */           PDFDebugger.main(arguments);
/*  79 */           exitAfterCallingMain = false;
/*     */         }
/*  81 */         else if (command.equals("PDFMerger"))
/*     */         {
/*  83 */           PDFMerger.main(arguments);
/*     */         }
/*  85 */         else if (command.equals("PDFReader"))
/*     */         {
/*  87 */           PDFReader.main(arguments);
/*  88 */           exitAfterCallingMain = false;
/*     */         }
/*  90 */         else if (command.equals("PDFSplit"))
/*     */         {
/*  92 */           PDFSplit.main(arguments);
/*     */         }
/*  94 */         else if (command.equals("PDFToImage"))
/*     */         {
/*  96 */           PDFToImage.main(arguments);
/*     */         }
/*  98 */         else if (command.equals("TextToPDF"))
/*     */         {
/* 100 */           TextToPDF.main(arguments);
/*     */         }
/* 102 */         else if (command.equals("WriteDecodedDoc"))
/*     */         {
/* 104 */           WriteDecodedDoc.main(arguments);
/*     */         }
/*     */         else
/*     */         {
/* 108 */           showMessageAndExit();
/*     */         }
/* 110 */         if (exitAfterCallingMain)
/*     */         {
/* 112 */           System.exit(0);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 117 */         System.err.println(command + " failed with the following exception:");
/*     */ 
/* 119 */         e.printStackTrace();
/* 120 */         System.exit(1);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 125 */       showMessageAndExit();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void showMessageAndExit()
/*     */   {
/* 131 */     System.err.println("PDFBox version: \"" + Version.getVersion() + "\"");
/* 132 */     System.err.println("\nUsage: java pdfbox-app-x.y.z.jar <command> <args..>");
/* 133 */     System.err.println("\nPossible commands are:\n");
/* 134 */     System.err.println("  ConvertColorspace");
/* 135 */     System.err.println("  Decrypt");
/* 136 */     System.err.println("  Encrypt");
/* 137 */     System.err.println("  ExtractText");
/* 138 */     System.err.println("  ExtractImages");
/* 139 */     System.err.println("  Overlay");
/* 140 */     System.err.println("  OverlayPDF");
/* 141 */     System.err.println("  PrintPDF");
/* 142 */     System.err.println("  PDFDebugger");
/* 143 */     System.err.println("  PDFMerger");
/* 144 */     System.err.println("  PDFReader");
/* 145 */     System.err.println("  PDFSplit");
/* 146 */     System.err.println("  PDFToImage");
/* 147 */     System.err.println("  TextToPDF");
/* 148 */     System.err.println("  WriteDecodedDoc");
/* 149 */     System.exit(1);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.PDFBox
 * JD-Core Version:    0.6.2
 */