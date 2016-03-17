/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.pdfparser.PDFStreamParser;
/*     */ import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.util.PDFOperator;
/*     */ 
/*     */ public class ConvertColorspace
/*     */ {
/*     */   private static final String PASSWORD = "-password";
/*     */   private static final String CONVERSION = "-equiv";
/*     */   private static final String DEST_COLORSPACE = "-toColorspace";
/*     */ 
/*     */   private void replaceColors(PDDocument inputFile, Hashtable colorEquivalents, String destColorspace)
/*     */     throws IOException
/*     */   {
/*  71 */     if (!destColorspace.equals("CMYK"))
/*     */     {
/*  73 */       throw new IOException("Error: Unknown colorspace " + destColorspace);
/*     */     }
/*  75 */     List pagesList = inputFile.getDocumentCatalog().getAllPages();
/*     */ 
/*  77 */     PDPage currentPage = null;
/*  78 */     PDFStreamParser parser = null;
/*  79 */     List pageTokens = null;
/*  80 */     List editedPageTokens = null;
/*     */ 
/*  82 */     for (int pageCounter = 0; pageCounter < pagesList.size(); pageCounter++)
/*     */     {
/*  84 */       currentPage = (PDPage)pagesList.get(pageCounter);
/*     */ 
/*  86 */       parser = new PDFStreamParser(currentPage.getContents().getStream());
/*  87 */       parser.parse();
/*  88 */       pageTokens = parser.getTokens();
/*  89 */       editedPageTokens = new ArrayList();
/*     */ 
/*  91 */       for (int counter = 0; counter < pageTokens.size(); counter++)
/*     */       {
/*  93 */         Object token = pageTokens.get(counter);
/*  94 */         if ((token instanceof PDFOperator))
/*     */         {
/*  96 */           PDFOperator tokenOperator = (PDFOperator)token;
/*     */ 
/*  98 */           if (tokenOperator.getOperation().equals("rg"))
/*     */           {
/* 100 */             if (destColorspace.equals("CMYK"))
/*     */             {
/* 102 */               replaceRGBTokensWithCMYKTokens(editedPageTokens, pageTokens, counter, colorEquivalents);
/* 103 */               editedPageTokens.add(PDFOperator.getOperator("k"));
/*     */             }
/*     */           }
/* 106 */           else if (tokenOperator.getOperation().equals("RG"))
/*     */           {
/* 108 */             if (destColorspace.equals("CMYK"))
/*     */             {
/* 110 */               replaceRGBTokensWithCMYKTokens(editedPageTokens, pageTokens, counter, colorEquivalents);
/* 111 */               editedPageTokens.add(PDFOperator.getOperator("K"));
/*     */             }
/*     */           }
/* 114 */           else if (tokenOperator.getOperation().equals("g"))
/*     */           {
/* 116 */             if (destColorspace.equals("CMYK"))
/*     */             {
/* 118 */               replaceGrayTokensWithCMYKTokens(editedPageTokens, pageTokens, counter, colorEquivalents);
/* 119 */               editedPageTokens.add(PDFOperator.getOperator("k"));
/*     */             }
/*     */           }
/* 122 */           else if (tokenOperator.getOperation().equals("G"))
/*     */           {
/* 124 */             if (destColorspace.equals("CMYK"))
/*     */             {
/* 126 */               replaceGrayTokensWithCMYKTokens(editedPageTokens, pageTokens, counter, colorEquivalents);
/* 127 */               editedPageTokens.add(PDFOperator.getOperator("K"));
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 132 */             editedPageTokens.add(token);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 137 */           editedPageTokens.add(token);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 142 */       PDStream updatedPageContents = new PDStream(inputFile);
/* 143 */       ContentStreamWriter contentWriter = new ContentStreamWriter(updatedPageContents.createOutputStream());
/* 144 */       contentWriter.writeTokens(editedPageTokens);
/* 145 */       currentPage.setContents(updatedPageContents);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void replaceRGBTokensWithCMYKTokens(List editedPageTokens, List pageTokens, int counter, Hashtable colorEquivalents)
/*     */   {
/* 156 */     float red = ((COSNumber)pageTokens.get(counter - 3)).floatValue();
/* 157 */     float green = ((COSNumber)pageTokens.get(counter - 2)).floatValue();
/* 158 */     float blue = ((COSNumber)pageTokens.get(counter - 1)).floatValue();
/*     */ 
/* 160 */     int intRed = Math.round(red * 255.0F);
/* 161 */     int intGreen = Math.round(green * 255.0F);
/* 162 */     int intBlue = Math.round(blue * 255.0F);
/*     */ 
/* 164 */     ColorSpaceInstance rgbColor = new ColorSpaceInstance(null);
/* 165 */     rgbColor.colorspace = "RGB";
/* 166 */     rgbColor.colorspaceValues = new int[] { intRed, intGreen, intBlue };
/* 167 */     ColorSpaceInstance cmykColor = (ColorSpaceInstance)colorEquivalents.get(rgbColor);
/* 168 */     float[] cmyk = null;
/*     */ 
/* 170 */     if (cmykColor != null)
/*     */     {
/* 172 */       cmyk = new float[] { cmykColor.colorspaceValues[0] / 100.0F, cmykColor.colorspaceValues[1] / 100.0F, cmykColor.colorspaceValues[2] / 100.0F, cmykColor.colorspaceValues[3] / 100.0F };
/*     */     }
/*     */     else
/*     */     {
/* 181 */       cmyk = convertRGBToCMYK(red, green, blue);
/*     */     }
/*     */ 
/* 185 */     editedPageTokens.remove(editedPageTokens.size() - 1);
/* 186 */     editedPageTokens.remove(editedPageTokens.size() - 1);
/* 187 */     editedPageTokens.remove(editedPageTokens.size() - 1);
/*     */ 
/* 190 */     editedPageTokens.add(new COSFloat(cmyk[0]));
/* 191 */     editedPageTokens.add(new COSFloat(cmyk[1]));
/* 192 */     editedPageTokens.add(new COSFloat(cmyk[2]));
/* 193 */     editedPageTokens.add(new COSFloat(cmyk[3]));
/*     */   }
/*     */ 
/*     */   private void replaceGrayTokensWithCMYKTokens(List editedPageTokens, List pageTokens, int counter, Hashtable colorEquivalents)
/*     */   {
/* 202 */     float gray = ((COSNumber)pageTokens.get(counter - 1)).floatValue();
/*     */ 
/* 204 */     ColorSpaceInstance grayColor = new ColorSpaceInstance(null);
/* 205 */     grayColor.colorspace = "Grayscale";
/* 206 */     grayColor.colorspaceValues = new int[] { Math.round(gray * 100.0F) };
/* 207 */     ColorSpaceInstance cmykColor = (ColorSpaceInstance)colorEquivalents.get(grayColor);
/* 208 */     float[] cmyk = null;
/*     */ 
/* 210 */     if (cmykColor != null)
/*     */     {
/* 212 */       cmyk = new float[] { cmykColor.colorspaceValues[0] / 100.0F, cmykColor.colorspaceValues[1] / 100.0F, cmykColor.colorspaceValues[2] / 100.0F, cmykColor.colorspaceValues[3] / 100.0F };
/*     */     }
/*     */     else
/*     */     {
/* 221 */       cmyk = new float[] { 0.0F, 0.0F, 0.0F, gray };
/*     */     }
/*     */ 
/* 225 */     editedPageTokens.remove(editedPageTokens.size() - 1);
/*     */ 
/* 228 */     editedPageTokens.add(new COSFloat(cmyk[0]));
/* 229 */     editedPageTokens.add(new COSFloat(cmyk[1]));
/* 230 */     editedPageTokens.add(new COSFloat(cmyk[2]));
/* 231 */     editedPageTokens.add(new COSFloat(cmyk[3]));
/*     */   }
/*     */ 
/*     */   private static float[] convertRGBToCMYK(float red, float green, float blue)
/*     */   {
/* 240 */     float c = 1.0F - red;
/* 241 */     float m = 1.0F - green;
/* 242 */     float y = 1.0F - blue;
/* 243 */     float k = 1.0F;
/*     */ 
/* 245 */     k = Math.min(Math.min(Math.min(c, k), m), y);
/*     */ 
/* 247 */     c = (c - k) / (1.0F - k);
/* 248 */     m = (m - k) / (1.0F - k);
/* 249 */     y = (y - k) / (1.0F - k);
/* 250 */     return new float[] { c, m, y, k };
/*     */   }
/*     */ 
/*     */   private static int[] stringToIntArray(String string)
/*     */   {
/* 255 */     String[] ints = string.split(",");
/* 256 */     int[] retval = new int[ints.length];
/* 257 */     for (int i = 0; i < ints.length; i++)
/*     */     {
/* 259 */       retval[i] = Integer.parseInt(ints[i]);
/*     */     }
/* 261 */     return retval;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/* 273 */     String password = "";
/* 274 */     String inputFile = null;
/* 275 */     String outputFile = null;
/* 276 */     String destColorspace = "CMYK";
/*     */ 
/* 278 */     Pattern colorEquivalentPattern = Pattern.compile("^(.*):\\((.*)\\)=(.*):\\((.*)\\)$");
/*     */ 
/* 281 */     Matcher colorEquivalentMatcher = null;
/*     */ 
/* 284 */     Hashtable colorEquivalents = new Hashtable();
/*     */ 
/* 286 */     for (int i = 0; i < args.length; i++)
/*     */     {
/* 288 */       if (args[i].equals("-password"))
/*     */       {
/* 290 */         i++;
/* 291 */         if (i >= args.length)
/*     */         {
/* 293 */           usage();
/*     */         }
/* 295 */         password = args[i];
/*     */       }
/* 297 */       if (args[i].equals("-toColorspace"))
/*     */       {
/* 299 */         i++;
/* 300 */         if (i >= args.length)
/*     */         {
/* 302 */           usage();
/*     */         }
/* 304 */         destColorspace = args[i];
/*     */       }
/* 306 */       if (args[i].equals("-equiv"))
/*     */       {
/* 308 */         i++;
/* 309 */         if (i >= args.length)
/*     */         {
/* 311 */           usage();
/*     */         }
/*     */ 
/* 314 */         colorEquivalentMatcher = colorEquivalentPattern.matcher(args[i]);
/* 315 */         if (!colorEquivalentMatcher.matches())
/*     */         {
/* 317 */           usage();
/*     */         }
/* 319 */         String srcColorSpace = colorEquivalentMatcher.group(1);
/* 320 */         String srcColorvalues = colorEquivalentMatcher.group(2);
/* 321 */         String destColorSpace = colorEquivalentMatcher.group(3);
/* 322 */         String destColorvalues = colorEquivalentMatcher.group(4);
/*     */ 
/* 324 */         ColorSpaceInstance source = new ColorSpaceInstance(null);
/* 325 */         source.colorspace = srcColorSpace;
/* 326 */         source.colorspaceValues = stringToIntArray(srcColorvalues);
/*     */ 
/* 328 */         ColorSpaceInstance dest = new ColorSpaceInstance(null);
/* 329 */         dest.colorspace = destColorSpace;
/* 330 */         dest.colorspaceValues = stringToIntArray(destColorvalues);
/*     */ 
/* 332 */         colorEquivalents.put(source, dest);
/*     */       }
/* 337 */       else if (inputFile == null)
/*     */       {
/* 339 */         inputFile = args[i];
/*     */       }
/*     */       else
/*     */       {
/* 343 */         outputFile = args[i];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 348 */     if (inputFile == null)
/*     */     {
/* 350 */       usage();
/*     */     }
/*     */ 
/* 353 */     if ((outputFile == null) || (outputFile.equals(inputFile)))
/*     */     {
/* 355 */       usage();
/*     */     }
/*     */ 
/* 358 */     PDDocument doc = null;
/*     */     try
/*     */     {
/* 361 */       doc = PDDocument.load(inputFile);
/* 362 */       if (doc.isEncrypted())
/*     */       {
/* 364 */         doc.decrypt(password);
/*     */       }
/* 366 */       ConvertColorspace converter = new ConvertColorspace();
/* 367 */       converter.replaceColors(doc, colorEquivalents, destColorspace);
/* 368 */       doc.save(outputFile);
/*     */     }
/*     */     finally
/*     */     {
/* 372 */       if (doc != null)
/*     */       {
/* 374 */         doc.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 387 */     System.err.println("Usage: java org.apache.pdfbox.ConvertColorspace [OPTIONS] <PDF Input file> <PDF Output File>\n  -password  <password>                Password to decrypt document\n  -equiv <color equivalent>            Color equivalent to use for conversion.\n  -destColorspace <color equivalent>   The destination colorspace, CMYK is the only 'supported colorspace.  \n The equiv format is : <source colorspace>:(colorspace value)=<dest colorspace>:(colorspace value) This option can be used as many times as necessary\n The supported equiv colorspaces are RGB and CMYK.\n RGB color values are integers between 0 and 255 CMYK color values are integer between 0 and 100.\n Example: java org.apache.pdfbox.ConvertColorspace -equiv RGB:(255,0,0)=CMYK(0,99,100,0) input.pdf output.pdf\n  <PDF Input file>             The PDF document to use\n  <PDF Output file>            The PDF file to write the result to. Must be different of input file\n");
/*     */ 
/* 404 */     System.exit(1);
/*     */   }
/*     */ 
/*     */   private static class ColorSpaceInstance
/*     */   {
/* 413 */     private String colorspace = null;
/* 414 */     private int[] colorspaceValues = null;
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 421 */       int code = this.colorspace.hashCode();
/* 422 */       for (int i = 0; i < this.colorspaceValues.length; i++)
/*     */       {
/* 424 */         code += this.colorspaceValues[i];
/*     */       }
/* 426 */       return code;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object o)
/*     */     {
/* 434 */       boolean retval = false;
/* 435 */       if ((o instanceof ColorSpaceInstance))
/*     */       {
/* 437 */         ColorSpaceInstance other = (ColorSpaceInstance)o;
/* 438 */         if ((this.colorspace.equals(other.colorspace)) && (this.colorspaceValues.length == other.colorspaceValues.length))
/*     */         {
/* 441 */           retval = true;
/* 442 */           for (int i = 0; (i < this.colorspaceValues.length) && (retval); i++)
/*     */           {
/* 444 */             retval = (retval) && (this.colorspaceValues[i] == other.colorspaceValues[i]);
/*     */           }
/*     */         }
/*     */       }
/* 448 */       return retval;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.ConvertColorspace
 * JD-Core Version:    0.6.2
 */