/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Toolkit;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import javax.imageio.ImageIO;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.util.PDFImageWriter;
/*     */ 
/*     */ public class PDFToImage
/*     */ {
/*     */   private static final String PASSWORD = "-password";
/*     */   private static final String START_PAGE = "-startPage";
/*     */   private static final String END_PAGE = "-endPage";
/*     */   private static final String IMAGE_FORMAT = "-imageType";
/*     */   private static final String OUTPUT_PREFIX = "-outputPrefix";
/*     */   private static final String COLOR = "-color";
/*     */   private static final String RESOLUTION = "-resolution";
/*     */   private static final String CROPBOX = "-cropbox";
/*     */   private static final String NONSEQ = "-nonSeq";
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  68 */     boolean useNonSeqParser = false;
/*  69 */     String password = "";
/*  70 */     String pdfFile = null;
/*  71 */     String outputPrefix = null;
/*  72 */     String imageFormat = "jpg";
/*  73 */     int startPage = 1;
/*  74 */     int endPage = 2147483647;
/*  75 */     String color = "rgb";
/*     */ 
/*  77 */     float cropBoxLowerLeftX = 0.0F;
/*  78 */     float cropBoxLowerLeftY = 0.0F;
/*  79 */     float cropBoxUpperRightX = 0.0F;
/*  80 */     float cropBoxUpperRightY = 0.0F;
/*     */     int resolution;
/*     */     try
/*     */     {
/*  83 */       resolution = Toolkit.getDefaultToolkit().getScreenResolution();
/*     */     }
/*     */     catch (HeadlessException e)
/*     */     {
/*  87 */       resolution = 96;
/*     */     }
/*  89 */     for (int i = 0; i < args.length; i++)
/*     */     {
/*  91 */       if (args[i].equals("-password"))
/*     */       {
/*  93 */         i++;
/*  94 */         if (i >= args.length)
/*     */         {
/*  96 */           usage();
/*     */         }
/*  98 */         password = args[i];
/*     */       }
/* 100 */       else if (args[i].equals("-startPage"))
/*     */       {
/* 102 */         i++;
/* 103 */         if (i >= args.length)
/*     */         {
/* 105 */           usage();
/*     */         }
/* 107 */         startPage = Integer.parseInt(args[i]);
/*     */       }
/* 109 */       else if (args[i].equals("-endPage"))
/*     */       {
/* 111 */         i++;
/* 112 */         if (i >= args.length)
/*     */         {
/* 114 */           usage();
/*     */         }
/* 116 */         endPage = Integer.parseInt(args[i]);
/*     */       }
/* 118 */       else if (args[i].equals("-imageType"))
/*     */       {
/* 120 */         i++;
/* 121 */         imageFormat = args[i];
/*     */       }
/* 123 */       else if (args[i].equals("-outputPrefix"))
/*     */       {
/* 125 */         i++;
/* 126 */         outputPrefix = args[i];
/*     */       }
/* 128 */       else if (args[i].equals("-color"))
/*     */       {
/* 130 */         i++;
/* 131 */         color = args[i];
/*     */       }
/* 133 */       else if (args[i].equals("-resolution"))
/*     */       {
/* 135 */         i++;
/* 136 */         resolution = Integer.parseInt(args[i]);
/*     */       }
/* 138 */       else if (args[i].equals("-cropbox"))
/*     */       {
/* 140 */         i++;
/* 141 */         cropBoxLowerLeftX = Float.valueOf(args[i]).floatValue();
/* 142 */         i++;
/* 143 */         cropBoxLowerLeftY = Float.valueOf(args[i]).floatValue();
/* 144 */         i++;
/* 145 */         cropBoxUpperRightX = Float.valueOf(args[i]).floatValue();
/* 146 */         i++;
/* 147 */         cropBoxUpperRightY = Float.valueOf(args[i]).floatValue();
/*     */       }
/* 149 */       else if (args[i].equals("-nonSeq"))
/*     */       {
/* 151 */         useNonSeqParser = true;
/*     */       }
/* 155 */       else if (pdfFile == null)
/*     */       {
/* 157 */         pdfFile = args[i];
/*     */       }
/*     */     }
/*     */ 
/* 161 */     if (pdfFile == null)
/*     */     {
/* 163 */       usage();
/*     */     }
/*     */     else
/*     */     {
/* 167 */       if (outputPrefix == null)
/*     */       {
/* 169 */         outputPrefix = pdfFile.substring(0, pdfFile.lastIndexOf('.'));
/*     */       }
/*     */ 
/* 172 */       PDDocument document = null;
/*     */       try
/*     */       {
/* 175 */         if (useNonSeqParser)
/*     */         {
/* 177 */           document = PDDocument.loadNonSeq(new File(pdfFile), null, password);
/*     */         }
/*     */         else
/*     */         {
/* 181 */           document = PDDocument.load(pdfFile);
/* 182 */           if (document.isEncrypted())
/*     */           {
/* 184 */             document.decrypt(password);
/*     */           }
/*     */         }
/* 187 */         int imageType = 24;
/* 188 */         if ("bilevel".equalsIgnoreCase(color))
/*     */         {
/* 190 */           imageType = 12;
/*     */         }
/* 192 */         else if ("indexed".equalsIgnoreCase(color))
/*     */         {
/* 194 */           imageType = 13;
/*     */         }
/* 196 */         else if ("gray".equalsIgnoreCase(color))
/*     */         {
/* 198 */           imageType = 10;
/*     */         }
/* 200 */         else if ("rgb".equalsIgnoreCase(color))
/*     */         {
/* 202 */           imageType = 1;
/*     */         }
/* 204 */         else if ("rgba".equalsIgnoreCase(color))
/*     */         {
/* 206 */           imageType = 2;
/*     */         }
/*     */         else
/*     */         {
/* 210 */           System.err.println("Error: the number of bits per pixel must be 1, 8 or 24.");
/* 211 */           System.exit(2);
/*     */         }
/*     */ 
/* 216 */         if ((cropBoxLowerLeftX != 0.0F) || (cropBoxLowerLeftY != 0.0F) || (cropBoxUpperRightX != 0.0F) || (cropBoxUpperRightY != 0.0F))
/*     */         {
/* 219 */           changeCropBoxes(document, cropBoxLowerLeftX, cropBoxLowerLeftY, cropBoxUpperRightX, cropBoxUpperRightY);
/*     */         }
/*     */ 
/* 225 */         PDFImageWriter imageWriter = new PDFImageWriter();
/* 226 */         boolean success = imageWriter.writeImage(document, imageFormat, password, startPage, endPage, outputPrefix, imageType, resolution);
/*     */ 
/* 228 */         if (!success)
/*     */         {
/* 230 */           System.err.println("Error: no writer found for image format '" + imageFormat + "'");
/*     */ 
/* 232 */           System.exit(1);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 237 */         System.err.println(e);
/*     */       }
/*     */       finally
/*     */       {
/* 241 */         if (document != null)
/*     */         {
/* 243 */           document.close();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 254 */     System.err.println("Usage: java -jar pdfbox-app-x.y.z.jar PDFToImage [OPTIONS] <PDF file>\n  -password  <password>          Password to decrypt document\n  -imageType <image type>        (" + getImageFormats() + ")\n" + "  -outputPrefix <output prefix>  Filename prefix for image files\n" + "  -startPage <number>            The first page to start extraction(1 based)\n" + "  -endPage <number>              The last page to extract(inclusive)\n" + "  -color <string>                The color depth (valid: bilevel, indexed, gray, rgb, rgba)\n" + "  -resolution <number>           The bitmap resolution in dpi\n" + "  -cropbox <number> <number> <number> <number> The page area to export\n" + "  -nonSeq                        Enables the new non-sequential parser\n" + "  <PDF file>                     The PDF document to use\n");
/*     */ 
/* 266 */     System.exit(1);
/*     */   }
/*     */ 
/*     */   private static String getImageFormats()
/*     */   {
/* 271 */     StringBuffer retval = new StringBuffer();
/* 272 */     String[] formats = ImageIO.getReaderFormatNames();
/* 273 */     for (int i = 0; i < formats.length; i++)
/*     */     {
/* 275 */       retval.append(formats[i]);
/* 276 */       if (i + 1 < formats.length)
/*     */       {
/* 278 */         retval.append(",");
/*     */       }
/*     */     }
/* 281 */     return retval.toString();
/*     */   }
/*     */ 
/*     */   private static void changeCropBoxes(PDDocument document, float a, float b, float c, float d)
/*     */   {
/* 286 */     List pages = document.getDocumentCatalog().getAllPages();
/* 287 */     for (int i = 0; i < pages.size(); i++)
/*     */     {
/* 289 */       System.out.println("resizing page");
/* 290 */       PDPage page = (PDPage)pages.get(i);
/* 291 */       PDRectangle rectangle = new PDRectangle();
/* 292 */       rectangle.setLowerLeftX(a);
/* 293 */       rectangle.setLowerLeftY(b);
/* 294 */       rectangle.setUpperRightX(c);
/* 295 */       rectangle.setUpperRightY(d);
/* 296 */       page.setMediaBox(rectangle);
/* 297 */       page.setCropBox(rectangle);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.PDFToImage
 * JD-Core Version:    0.6.2
 */