/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ import org.apache.pdfbox.pdfparser.PDFParser;
/*     */ import org.apache.pdfbox.pdfwriter.COSWriter;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ 
/*     */ public class Overlay
/*     */ {
/*     */ 
/*     */   /** @deprecated */
/*  68 */   public static final COSName XOBJECT = COSName.XOBJECT;
/*     */ 
/*     */   /** @deprecated */
/*  73 */   public static final COSName PROC_SET = COSName.PROC_SET;
/*     */ 
/*     */   /** @deprecated */
/*  78 */   public static final COSName EXT_G_STATE = COSName.EXT_G_STATE;
/*     */   private List layoutPages;
/*     */   private PDDocument pdfOverlay;
/*     */   private PDDocument pdfDocument;
/*     */   private int pageCount;
/*     */   private COSStream saveGraphicsStateStream;
/*     */   private COSStream restoreGraphicsStateStream;
/*     */ 
/*  80 */   public Overlay() { this.layoutPages = new ArrayList(10);
/*     */ 
/*  84 */     this.pageCount = 0;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IOException, COSVisitorException
/*     */   {
/* 100 */     if (args.length != 3)
/*     */     {
/* 102 */       usage();
/* 103 */       System.exit(1);
/*     */     }
/*     */     else
/*     */     {
/* 107 */       PDDocument overlay = null;
/* 108 */       PDDocument pdf = null;
/*     */       try
/*     */       {
/* 112 */         overlay = getDocument(args[0]);
/* 113 */         pdf = getDocument(args[1]);
/* 114 */         Overlay overlayer = new Overlay();
/* 115 */         overlayer.overlay(overlay, pdf);
/* 116 */         writeDocument(pdf, args[2]);
/*     */       }
/*     */       finally
/*     */       {
/* 120 */         if (overlay != null)
/*     */         {
/* 122 */           overlay.close();
/*     */         }
/* 124 */         if (pdf != null)
/*     */         {
/* 126 */           pdf.close();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void writeDocument(PDDocument pdf, String filename) throws IOException, COSVisitorException
/*     */   {
/* 134 */     FileOutputStream output = null;
/* 135 */     COSWriter writer = null;
/*     */     try
/*     */     {
/* 138 */       output = new FileOutputStream(filename);
/* 139 */       writer = new COSWriter(output);
/* 140 */       writer.write(pdf);
/*     */     }
/*     */     finally
/*     */     {
/* 144 */       if (writer != null)
/*     */       {
/* 146 */         writer.close();
/*     */       }
/* 148 */       if (output != null)
/*     */       {
/* 150 */         output.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static PDDocument getDocument(String filename) throws IOException
/*     */   {
/* 157 */     FileInputStream input = null;
/* 158 */     PDFParser parser = null;
/* 159 */     PDDocument result = null;
/*     */     try
/*     */     {
/* 162 */       input = new FileInputStream(filename);
/* 163 */       parser = new PDFParser(input);
/* 164 */       parser.parse();
/* 165 */       result = parser.getPDDocument();
/*     */     }
/*     */     finally
/*     */     {
/* 169 */       if (input != null)
/*     */       {
/* 171 */         input.close();
/*     */       }
/*     */     }
/* 174 */     return result;
/*     */   }
/*     */ 
/*     */   private static void usage()
/*     */   {
/* 179 */     System.err.println("usage: java -jar pdfbox-app-x.y.z.jar Overlay <overlay.pdf> <document.pdf> <result.pdf>");
/*     */   }
/*     */ 
/*     */   public PDDocument overlay(PDDocument overlay, PDDocument destination)
/*     */     throws IOException
/*     */   {
/* 220 */     this.pdfOverlay = overlay;
/* 221 */     this.pdfDocument = destination;
/*     */ 
/* 223 */     PDDocumentCatalog overlayCatalog = this.pdfOverlay.getDocumentCatalog();
/* 224 */     collectLayoutPages(overlayCatalog.getAllPages());
/*     */ 
/* 226 */     COSDictionary saveGraphicsStateDic = new COSDictionary();
/* 227 */     this.saveGraphicsStateStream = this.pdfDocument.getDocument().createCOSStream(saveGraphicsStateDic);
/* 228 */     OutputStream saveStream = this.saveGraphicsStateStream.createUnfilteredStream();
/* 229 */     saveStream.write(" q\n".getBytes("ISO-8859-1"));
/* 230 */     saveStream.flush();
/*     */ 
/* 232 */     this.restoreGraphicsStateStream = this.pdfDocument.getDocument().createCOSStream(saveGraphicsStateDic);
/* 233 */     OutputStream restoreStream = this.restoreGraphicsStateStream.createUnfilteredStream();
/* 234 */     restoreStream.write(" Q\n".getBytes("ISO-8859-1"));
/* 235 */     restoreStream.flush();
/*     */ 
/* 238 */     PDDocumentCatalog pdfCatalog = this.pdfDocument.getDocumentCatalog();
/* 239 */     processPages(pdfCatalog.getAllPages());
/*     */ 
/* 241 */     return this.pdfDocument;
/*     */   }
/*     */ 
/*     */   private void collectLayoutPages(List pages) throws IOException
/*     */   {
/* 246 */     Iterator pagesIter = pages.iterator();
/* 247 */     while (pagesIter.hasNext())
/*     */     {
/* 249 */       PDPage page = (PDPage)pagesIter.next();
/* 250 */       COSBase contents = page.getCOSDictionary().getDictionaryObject(COSName.CONTENTS);
/* 251 */       PDResources resources = page.findResources();
/* 252 */       if (resources == null)
/*     */       {
/* 254 */         resources = new PDResources();
/* 255 */         page.setResources(resources);
/*     */       }
/* 257 */       COSDictionary res = resources.getCOSDictionary();
/*     */ 
/* 259 */       if ((contents instanceof COSStream))
/*     */       {
/* 261 */         COSStream stream = (COSStream)contents;
/* 262 */         Map objectNameMap = new TreeMap();
/* 263 */         stream = makeUniqObjectNames(objectNameMap, stream);
/*     */ 
/* 265 */         this.layoutPages.add(new LayoutPage(stream, res, objectNameMap));
/*     */       } else {
/* 267 */         if ((contents instanceof COSArray))
/*     */         {
/* 269 */           throw new UnsupportedOperationException("Layout pages with COSArray currently not supported.");
/*     */         }
/*     */ 
/* 274 */         throw new IOException("Contents are unknown type:" + contents.getClass().getName());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private COSStream makeUniqObjectNames(Map objectNameMap, COSStream stream) throws IOException
/*     */   {
/* 281 */     ByteArrayOutputStream baos = new ByteArrayOutputStream(10240);
/*     */ 
/* 283 */     byte[] buf = new byte[10240];
/*     */ 
/* 285 */     InputStream is = stream.getUnfilteredStream();
/*     */     int read;
/* 286 */     while ((read = is.read(buf)) > -1)
/*     */     {
/* 288 */       baos.write(buf, 0, read);
/*     */     }
/*     */ 
/* 291 */     buf = baos.toByteArray();
/* 292 */     baos = new ByteArrayOutputStream(buf.length + 100);
/* 293 */     StringBuffer sbObjectName = new StringBuffer(10);
/* 294 */     boolean bInObjectIdent = false;
/* 295 */     boolean bInText = false;
/* 296 */     boolean bInEscape = false;
/* 297 */     for (int i = 0; i < buf.length; i++)
/*     */     {
/* 299 */       byte b = buf[i];
/*     */ 
/* 301 */       if (!bInEscape)
/*     */       {
/* 303 */         if ((!bInText) && (b == 40))
/*     */         {
/* 305 */           bInText = true;
/*     */         }
/* 307 */         if ((bInText) && (b == 41))
/*     */         {
/* 309 */           bInText = false;
/*     */         }
/* 311 */         if (b == 92)
/*     */         {
/* 313 */           bInEscape = true;
/*     */         }
/*     */ 
/* 316 */         if ((!bInText) && (!bInEscape))
/*     */         {
/* 318 */           if (b == 47)
/*     */           {
/* 320 */             bInObjectIdent = true;
/*     */           }
/* 322 */           else if ((bInObjectIdent) && (Character.isWhitespace((char)b)))
/*     */           {
/* 324 */             bInObjectIdent = false;
/*     */ 
/* 329 */             String objectName = sbObjectName.toString().substring(1);
/* 330 */             String newObjectName = objectName + "overlay";
/* 331 */             baos.write(47);
/* 332 */             baos.write(newObjectName.getBytes("ISO-8859-1"));
/*     */ 
/* 334 */             objectNameMap.put(objectName, COSName.getPDFName(newObjectName));
/*     */ 
/* 336 */             sbObjectName.delete(0, sbObjectName.length());
/*     */           }
/*     */         }
/*     */ 
/* 340 */         if (bInObjectIdent)
/*     */         {
/* 342 */           sbObjectName.append((char)b);
/* 343 */           continue;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 348 */         bInEscape = false;
/*     */       }
/*     */ 
/* 351 */       baos.write(b);
/*     */     }
/*     */ 
/* 354 */     COSDictionary streamDict = new COSDictionary();
/* 355 */     streamDict.setInt(COSName.LENGTH, baos.size());
/* 356 */     COSStream output = this.pdfDocument.getDocument().createCOSStream(streamDict);
/* 357 */     output.setFilters(stream.getFilters());
/* 358 */     OutputStream os = output.createUnfilteredStream();
/* 359 */     baos.writeTo(os);
/* 360 */     os.close();
/*     */ 
/* 362 */     return output;
/*     */   }
/*     */ 
/*     */   private void processPages(List pages) throws IOException
/*     */   {
/* 367 */     Iterator pageIter = pages.iterator();
/* 368 */     while (pageIter.hasNext())
/*     */     {
/* 370 */       PDPage page = (PDPage)pageIter.next();
/* 371 */       COSDictionary pageDictionary = page.getCOSDictionary();
/* 372 */       COSBase contents = pageDictionary.getDictionaryObject(COSName.CONTENTS);
/* 373 */       if ((contents instanceof COSStream))
/*     */       {
/* 375 */         COSStream contentsStream = (COSStream)contents;
/*     */ 
/* 378 */         COSArray array = new COSArray();
/*     */ 
/* 380 */         array.add(contentsStream);
/*     */ 
/* 382 */         mergePage(array, page);
/*     */ 
/* 384 */         pageDictionary.setItem(COSName.CONTENTS, array);
/*     */       }
/* 386 */       else if ((contents instanceof COSArray))
/*     */       {
/* 388 */         COSArray contentsArray = (COSArray)contents;
/*     */ 
/* 390 */         mergePage(contentsArray, page);
/*     */       }
/*     */       else
/*     */       {
/* 394 */         throw new IOException("Contents are unknown type:" + contents.getClass().getName());
/*     */       }
/* 396 */       this.pageCount += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void mergePage(COSArray array, PDPage page)
/*     */   {
/* 402 */     int layoutPageNum = this.pageCount % this.layoutPages.size();
/* 403 */     LayoutPage layoutPage = (LayoutPage)this.layoutPages.get(layoutPageNum);
/* 404 */     PDResources resources = page.findResources();
/* 405 */     if (resources == null)
/*     */     {
/* 407 */       resources = new PDResources();
/* 408 */       page.setResources(resources);
/*     */     }
/* 410 */     COSDictionary docResDict = resources.getCOSDictionary();
/* 411 */     COSDictionary layoutResDict = layoutPage.res;
/* 412 */     mergeArray(COSName.PROC_SET, docResDict, layoutResDict);
/* 413 */     mergeDictionary(COSName.FONT, docResDict, layoutResDict, layoutPage.objectNameMap);
/* 414 */     mergeDictionary(COSName.XOBJECT, docResDict, layoutResDict, layoutPage.objectNameMap);
/* 415 */     mergeDictionary(COSName.EXT_G_STATE, docResDict, layoutResDict, layoutPage.objectNameMap);
/*     */ 
/* 424 */     array.add(0, this.saveGraphicsStateStream);
/* 425 */     array.add(this.restoreGraphicsStateStream);
/* 426 */     array.add(layoutPage.contents);
/*     */   }
/*     */ 
/*     */   private void mergeDictionary(COSName name, COSDictionary dest, COSDictionary source, Map objectNameMap)
/*     */   {
/* 437 */     COSDictionary destDict = (COSDictionary)dest.getDictionaryObject(name);
/* 438 */     COSDictionary sourceDict = (COSDictionary)source.getDictionaryObject(name);
/*     */ 
/* 440 */     if (destDict == null)
/*     */     {
/* 442 */       destDict = new COSDictionary();
/* 443 */       dest.setItem(name, destDict);
/*     */     }
/* 445 */     if (sourceDict != null)
/*     */     {
/* 448 */       for (Map.Entry entry : sourceDict.entrySet())
/*     */       {
/* 450 */         COSName mappedKey = (COSName)objectNameMap.get(((COSName)entry.getKey()).getName());
/* 451 */         if (mappedKey != null)
/*     */         {
/* 453 */           destDict.setItem(mappedKey, (COSBase)entry.getValue());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void mergeArray(COSName name, COSDictionary dest, COSDictionary source)
/*     */   {
/* 467 */     COSArray destDict = (COSArray)dest.getDictionaryObject(name);
/* 468 */     COSArray sourceDict = (COSArray)source.getDictionaryObject(name);
/*     */ 
/* 470 */     if (destDict == null)
/*     */     {
/* 472 */       destDict = new COSArray();
/* 473 */       dest.setItem(name, destDict);
/*     */     }
/*     */ 
/* 476 */     for (int sourceDictIdx = 0; (sourceDict != null) && (sourceDictIdx < sourceDict.size()); sourceDictIdx++)
/*     */     {
/* 478 */       COSBase key = sourceDict.get(sourceDictIdx);
/* 479 */       if ((key instanceof COSName))
/*     */       {
/* 481 */         COSName keyname = (COSName)key;
/*     */ 
/* 483 */         boolean bFound = false;
/* 484 */         for (int destDictIdx = 0; destDictIdx < destDict.size(); destDictIdx++)
/*     */         {
/* 486 */           COSBase destkey = destDict.get(destDictIdx);
/* 487 */           if ((destkey instanceof COSName))
/*     */           {
/* 489 */             COSName destkeyname = (COSName)destkey;
/* 490 */             if (destkeyname.equals(keyname))
/*     */             {
/* 492 */               bFound = true;
/* 493 */               break;
/*     */             }
/*     */           }
/*     */         }
/* 497 */         if (!bFound)
/*     */         {
/* 499 */           destDict.add(keyname);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class LayoutPage
/*     */   {
/*     */     private final COSBase contents;
/*     */     private final COSDictionary res;
/*     */     private final Map objectNameMap;
/*     */ 
/*     */     public LayoutPage(COSBase contentsValue, COSDictionary resValue, Map objectNameMapValue)
/*     */     {
/* 200 */       this.contents = contentsValue;
/* 201 */       this.res = resValue;
/* 202 */       this.objectNameMap = objectNameMapValue;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.Overlay
 * JD-Core Version:    0.6.2
 */