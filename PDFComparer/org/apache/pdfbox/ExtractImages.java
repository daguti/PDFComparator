/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.io.IOUtils;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
/*     */ import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
/*     */ 
/*     */ public class ExtractImages
/*     */ {
/*  50 */   private int imageCounter = 1;
/*     */   private static final String PASSWORD = "-password";
/*     */   private static final String PREFIX = "-prefix";
/*     */   private static final String ADDKEY = "-addkey";
/*     */   private static final String NONSEQ = "-nonSeq";
/*     */   private static final String DIRECTJPEG = "-directJPEG";
/*  58 */   private static final List<String> DCT_FILTERS = new ArrayList();
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  79 */     ExtractImages extractor = new ExtractImages();
/*  80 */     extractor.extractImages(args);
/*     */   }
/*     */ 
/*     */   private void extractImages(String[] args) throws Exception
/*     */   {
/*  85 */     if ((args.length < 1) || (args.length > 4))
/*     */     {
/*  87 */       usage();
/*     */     }
/*     */     else
/*     */     {
/*  91 */       String pdfFile = null;
/*  92 */       String password = "";
/*  93 */       String prefix = null;
/*  94 */       boolean addKey = false;
/*  95 */       boolean useNonSeqParser = false;
/*  96 */       boolean directJPEG = false;
/*  97 */       for (int i = 0; i < args.length; i++)
/*     */       {
/*  99 */         if (args[i].equals("-password"))
/*     */         {
/* 101 */           i++;
/* 102 */           if (i >= args.length)
/*     */           {
/* 104 */             usage();
/*     */           }
/* 106 */           password = args[i];
/*     */         }
/* 108 */         else if (args[i].equals("-prefix"))
/*     */         {
/* 110 */           i++;
/* 111 */           if (i >= args.length)
/*     */           {
/* 113 */             usage();
/*     */           }
/* 115 */           prefix = args[i];
/*     */         }
/* 117 */         else if (args[i].equals("-addkey"))
/*     */         {
/* 119 */           addKey = true;
/*     */         }
/* 121 */         else if (args[i].equals("-nonSeq"))
/*     */         {
/* 123 */           useNonSeqParser = true;
/*     */         }
/* 125 */         else if (args[i].equals("-directJPEG"))
/*     */         {
/* 127 */           directJPEG = true;
/*     */         }
/* 131 */         else if (pdfFile == null)
/*     */         {
/* 133 */           pdfFile = args[i];
/*     */         }
/*     */       }
/*     */ 
/* 137 */       if (pdfFile == null)
/*     */       {
/* 139 */         usage();
/*     */       }
/*     */       else
/*     */       {
/* 143 */         if ((prefix == null) && (pdfFile.length() > 4))
/*     */         {
/* 145 */           prefix = pdfFile.substring(0, pdfFile.length() - 4);
/*     */         }
/*     */ 
/* 148 */         PDDocument document = null;
/*     */         try
/*     */         {
/* 152 */           if (useNonSeqParser)
/*     */           {
/* 154 */             document = PDDocument.loadNonSeq(new File(pdfFile), null, password);
/*     */           }
/*     */           else
/*     */           {
/* 158 */             document = PDDocument.load(pdfFile);
/*     */ 
/* 160 */             if (document.isEncrypted())
/*     */             {
/* 162 */               StandardDecryptionMaterial spm = new StandardDecryptionMaterial(password);
/* 163 */               document.openProtection(spm);
/*     */             }
/*     */           }
/* 166 */           AccessPermission ap = document.getCurrentAccessPermission();
/* 167 */           if (!ap.canExtractContent())
/*     */           {
/* 169 */             throw new IOException("Error: You do not have permission to extract images.");
/*     */           }
/*     */ 
/* 173 */           List pages = document.getDocumentCatalog().getAllPages();
/* 174 */           Iterator iter = pages.iterator();
/* 175 */           while (iter.hasNext())
/*     */           {
/* 177 */             PDPage page = (PDPage)iter.next();
/* 178 */             PDResources resources = page.getResources();
/*     */ 
/* 180 */             processResources(resources, prefix, addKey, directJPEG);
/*     */           }
/*     */         }
/*     */         finally
/*     */         {
/* 185 */           if (document != null)
/*     */           {
/* 187 */             document.close();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeJpeg2file(PDJpeg image, String filename) throws IOException
/*     */   {
/* 196 */     FileOutputStream out = null;
/*     */     try
/*     */     {
/* 200 */       out = new FileOutputStream(filename + ".jpg");
/* 201 */       InputStream data = image.getPDStream().getPartiallyFilteredStream(DCT_FILTERS);
/* 202 */       byte[] buf = new byte[1024];
/*     */       int amountRead;
/* 204 */       while ((amountRead = data.read(buf)) != -1)
/*     */       {
/* 206 */         out.write(buf, 0, amountRead);
/*     */       }
/* 208 */       IOUtils.closeQuietly(data);
/* 209 */       out.flush();
/*     */     }
/*     */     finally
/*     */     {
/* 213 */       if (out != null)
/*     */       {
/* 215 */         out.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processResources(PDResources resources, String prefix, boolean addKey, boolean directJPEG)
/*     */     throws IOException
/*     */   {
/* 223 */     if (resources == null)
/*     */     {
/* 225 */       return;
/*     */     }
/* 227 */     Map xobjects = resources.getXObjects();
/* 228 */     if (xobjects != null)
/*     */     {
/* 230 */       Iterator xobjectIter = xobjects.keySet().iterator();
/* 231 */       while (xobjectIter.hasNext())
/*     */       {
/* 233 */         String key = (String)xobjectIter.next();
/* 234 */         PDXObject xobject = (PDXObject)xobjects.get(key);
/*     */ 
/* 236 */         if ((xobject instanceof PDXObjectImage))
/*     */         {
/* 238 */           PDXObjectImage image = (PDXObjectImage)xobject;
/* 239 */           String name = null;
/* 240 */           if (addKey)
/*     */           {
/* 242 */             name = getUniqueFileName(prefix + "_" + key, image.getSuffix());
/*     */           }
/*     */           else
/*     */           {
/* 246 */             name = getUniqueFileName(prefix, image.getSuffix());
/*     */           }
/* 248 */           System.out.println("Writing image:" + name);
/* 249 */           if ((directJPEG) && ("jpg".equals(image.getSuffix())))
/*     */           {
/* 251 */             writeJpeg2file((PDJpeg)image, name);
/*     */           }
/*     */           else
/*     */           {
/* 255 */             image.write2file(name);
/*     */           }
/* 257 */           image.clear();
/*     */         }
/* 260 */         else if ((xobject instanceof PDXObjectForm))
/*     */         {
/* 262 */           PDXObjectForm xObjectForm = (PDXObjectForm)xobject;
/* 263 */           PDResources formResources = xObjectForm.getResources();
/* 264 */           processResources(formResources, prefix, addKey, directJPEG);
/*     */         }
/*     */       }
/*     */     }
/* 268 */     resources.clear();
/*     */   }
/*     */ 
/*     */   private String getUniqueFileName(String prefix, String suffix)
/*     */   {
/* 273 */     String uniqueName = null;
/* 274 */     File f = null;
/* 275 */     while ((f == null) || (f.exists()))
/*     */     {
/* 277 */       uniqueName = prefix + "-" + this.imageCounter;
/* 278 */       f = new File(uniqueName + "." + suffix);
/* 279 */       this.imageCounter += 1;
/*     */     }
/* 281 */     return uniqueName;
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 289 */     System.err.println("Usage: java org.apache.pdfbox.ExtractImages [OPTIONS] <PDF file>\n  -password  <password>        Password to decrypt document\n  -prefix  <image-prefix>      Image prefix(default to pdf name)\n  -addkey                      add the internal image key to the file name\n  -nonSeq                      Enables the new non-sequential parser\n  -directJPEG                  Forces the direct extraction of JPEG images regardless of colorspace\n  <PDF file>                   The PDF document to use\n");
/*     */ 
/* 297 */     System.exit(1);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  62 */     DCT_FILTERS.add(COSName.DCT_DECODE.getName());
/*  63 */     DCT_FILTERS.add(COSName.DCT_DECODE_ABBREVIATION.getName());
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.ExtractImages
 * JD-Core Version:    0.6.2
 */