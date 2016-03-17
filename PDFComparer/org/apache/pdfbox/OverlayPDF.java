/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.pdfparser.BaseParser;
/*     */ import org.apache.pdfbox.util.Overlay;
/*     */ import org.apache.pdfbox.util.Overlay.Position;
/*     */ 
/*     */ public class OverlayPDF
/*     */ {
/*  37 */   private static final Log LOG = LogFactory.getLog(BaseParser.class);
/*     */   private static final String POSITION = "-position";
/*     */   private static final String ODD = "-odd";
/*     */   private static final String EVEN = "-even";
/*     */   private static final String FIRST = "-first";
/*     */   private static final String LAST = "-last";
/*     */   private static final String PAGE = "-page";
/*     */   private static final String USEALLPAGES = "-useAllPages";
/*     */   private static final String NONSEQ = "-nonSeq";
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  57 */     Overlay overlayer = new Overlay();
/*  58 */     Map specificPageOverlayFile = new HashMap();
/*  59 */     boolean useNonSeqParser = false;
/*     */ 
/*  61 */     for (int i = 0; i < args.length; i++)
/*     */     {
/*  63 */       String arg = args[i].trim();
/*  64 */       if (i == 0)
/*     */       {
/*  66 */         overlayer.setInputFile(arg);
/*     */       }
/*  68 */       else if (i == args.length - 1)
/*     */       {
/*  70 */         overlayer.setOutputFile(arg);
/*     */       }
/*  72 */       else if ((arg.equals("-position")) && (i + 1 < args.length))
/*     */       {
/*  74 */         if (Overlay.Position.FOREGROUND.toString().equalsIgnoreCase(args[(i + 1)].trim()))
/*     */         {
/*  76 */           overlayer.setOverlayPosition(Overlay.Position.FOREGROUND);
/*     */         }
/*  78 */         else if (Overlay.Position.BACKGROUND.toString().equalsIgnoreCase(args[(i + 1)].trim()))
/*     */         {
/*  80 */           overlayer.setOverlayPosition(Overlay.Position.BACKGROUND);
/*     */         }
/*     */         else
/*     */         {
/*  84 */           usage();
/*     */         }
/*  86 */         i++;
/*     */       }
/*  88 */       else if ((arg.equals("-odd")) && (i + 1 < args.length))
/*     */       {
/*  90 */         overlayer.setOddPageOverlayFile(args[(i + 1)].trim());
/*  91 */         i++;
/*     */       }
/*  93 */       else if ((arg.equals("-even")) && (i + 1 < args.length))
/*     */       {
/*  95 */         overlayer.setEvenPageOverlayFile(args[(i + 1)].trim());
/*  96 */         i++;
/*     */       }
/*  98 */       else if ((arg.equals("-first")) && (i + 1 < args.length))
/*     */       {
/* 100 */         overlayer.setFirstPageOverlayFile(args[(i + 1)].trim());
/* 101 */         i++;
/*     */       }
/* 103 */       else if ((arg.equals("-last")) && (i + 1 < args.length))
/*     */       {
/* 105 */         overlayer.setLastPageOverlayFile(args[(i + 1)].trim());
/* 106 */         i++;
/*     */       }
/* 108 */       else if ((arg.equals("-useAllPages")) && (i + 1 < args.length))
/*     */       {
/* 110 */         overlayer.setAllPagesOverlayFile(args[(i + 1)].trim());
/* 111 */         i++;
/*     */       }
/* 113 */       else if ((arg.equals("-page")) && (i + 2 < args.length) && (isInteger(args[(i + 1)].trim())))
/*     */       {
/* 115 */         specificPageOverlayFile.put(Integer.valueOf(Integer.parseInt(args[(i + 1)].trim())), args[(i + 2)].trim());
/* 116 */         i += 2;
/*     */       }
/* 118 */       else if (args[i].equals("-nonSeq"))
/*     */       {
/* 120 */         useNonSeqParser = true;
/*     */       }
/* 122 */       else if (overlayer.getDefaultOverlayFile() == null)
/*     */       {
/* 124 */         overlayer.setDefaultOverlayFile(arg);
/*     */       }
/*     */       else
/*     */       {
/* 128 */         usage();
/*     */       }
/*     */     }
/*     */ 
/* 132 */     if ((overlayer.getInputFile() == null) || (overlayer.getOutputFile() == null))
/*     */     {
/* 134 */       usage();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 139 */       overlayer.overlay(specificPageOverlayFile, useNonSeqParser);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 143 */       LOG.error("Overlay failed: " + e.getMessage(), e);
/* 144 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 150 */     StringBuilder message = new StringBuilder();
/* 151 */     message.append("usage: java -jar pdfbox-app-x.y.z.jar OverlayPDF <input.pdf> [OPTIONS] <output.pdf>\n");
/* 152 */     message.append("  <input.pdf>                                        input file\n");
/* 153 */     message.append("  <defaultOverlay.pdf>                               default overlay file\n");
/* 154 */     message.append("  -odd <oddPageOverlay.pdf>                          overlay file used for odd pages\n");
/* 155 */     message.append("  -even <evenPageOverlay.pdf>                        overlay file used for even pages\n");
/* 156 */     message.append("  -first <firstPageOverlay.pdf>                      overlay file used for the first page\n");
/* 157 */     message.append("  -last <lastPageOverlay.pdf>                        overlay file used for the last page\n");
/* 158 */     message.append("  -useAllPages <allPagesOverlay.pdf>                 overlay file used for overlay, all pages are used by simply repeating them\n");
/*     */ 
/* 160 */     message.append("  -page <pageNumber> <specificPageOverlay.pdf>       overlay file used for the given page number, may occur more than once\n");
/*     */ 
/* 162 */     message.append("  -position foreground|background                    where to put the overlay file: foreground or background\n");
/*     */ 
/* 164 */     message.append("  -nonSeq                                            enables the new non-sequential parser\n");
/* 165 */     message.append("  <output.pdf>                                       output file\n");
/* 166 */     System.err.println(message.toString());
/* 167 */     System.exit(1);
/*     */   }
/*     */ 
/*     */   private static boolean isInteger(String str)
/*     */   {
/*     */     try
/*     */     {
/* 174 */       Integer.parseInt(str);
/*     */     }
/*     */     catch (NumberFormatException nfe)
/*     */     {
/* 178 */       return false;
/*     */     }
/* 180 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.OverlayPDF
 * JD-Core Version:    0.6.2
 */