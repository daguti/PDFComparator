/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ import org.apache.pdfbox.io.RandomAccessBuffer;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
/*     */ 
/*     */ public class Overlay
/*     */ {
/*     */   private static final String XOBJECT_PREFIX = "OL";
/*     */   private LayoutPage defaultOverlayPage;
/*     */   private LayoutPage firstPageOverlayPage;
/*     */   private LayoutPage lastPageOverlayPage;
/*     */   private LayoutPage oddPageOverlayPage;
/*     */   private LayoutPage evenPageOverlayPage;
/*     */   private Map<Integer, PDDocument> specificPageOverlay;
/*     */   private Map<Integer, LayoutPage> specificPageOverlayPage;
/*     */   private Position position;
/*     */   private String inputFileName;
/*     */   private PDDocument inputPDFDocument;
/*     */   private String outputFilename;
/*     */   private String defaultOverlayFilename;
/*     */   private PDDocument defaultOverlay;
/*     */   private String firstPageOverlayFilename;
/*     */   private PDDocument firstPageOverlay;
/*     */   private String lastPageOverlayFilename;
/*     */   private PDDocument lastPageOverlay;
/*     */   private String allPagesOverlayFilename;
/*     */   private PDDocument allPagesOverlay;
/*     */   private String oddPageOverlayFilename;
/*     */   private PDDocument oddPageOverlay;
/*     */   private String evenPageOverlayFilename;
/*     */   private PDDocument evenPageOverlay;
/*     */   private int numberOfOverlayPages;
/*     */   private boolean useAllOverlayPages;
/*     */ 
/*     */   public Overlay()
/*     */   {
/*  68 */     this.specificPageOverlay = new HashMap();
/*  69 */     this.specificPageOverlayPage = new HashMap();
/*     */ 
/*  71 */     this.position = Position.BACKGROUND;
/*     */ 
/*  73 */     this.inputFileName = null;
/*  74 */     this.inputPDFDocument = null;
/*     */ 
/*  76 */     this.outputFilename = null;
/*     */ 
/*  78 */     this.defaultOverlayFilename = null;
/*  79 */     this.defaultOverlay = null;
/*     */ 
/*  81 */     this.firstPageOverlayFilename = null;
/*  82 */     this.firstPageOverlay = null;
/*     */ 
/*  84 */     this.lastPageOverlayFilename = null;
/*  85 */     this.lastPageOverlay = null;
/*     */ 
/*  87 */     this.allPagesOverlayFilename = null;
/*  88 */     this.allPagesOverlay = null;
/*     */ 
/*  90 */     this.oddPageOverlayFilename = null;
/*  91 */     this.oddPageOverlay = null;
/*     */ 
/*  93 */     this.evenPageOverlayFilename = null;
/*  94 */     this.evenPageOverlay = null;
/*     */ 
/*  97 */     this.numberOfOverlayPages = 0;
/*  98 */     this.useAllOverlayPages = false;
/*     */   }
/*     */ 
/*     */   public void overlay(Map<Integer, String> specificPageOverlayFile, boolean useNonSeqParser)
/*     */     throws IOException, COSVisitorException
/*     */   {
/*     */     try
/*     */     {
/* 113 */       loadPDFs(useNonSeqParser);
/* 114 */       for (Map.Entry e : specificPageOverlayFile.entrySet())
/*     */       {
/* 116 */         PDDocument doc = loadPDF((String)e.getValue(), useNonSeqParser);
/* 117 */         this.specificPageOverlay.put(e.getKey(), doc);
/* 118 */         this.specificPageOverlayPage.put(e.getKey(), getLayoutPage(doc));
/*     */       }
/* 120 */       PDDocumentCatalog pdfCatalog = this.inputPDFDocument.getDocumentCatalog();
/* 121 */       processPages(pdfCatalog.getAllPages());
/*     */ 
/* 123 */       this.inputPDFDocument.save(this.outputFilename);
/*     */     }
/*     */     finally
/*     */     {
/* 127 */       if (this.inputPDFDocument != null)
/*     */       {
/* 129 */         this.inputPDFDocument.close();
/*     */       }
/* 131 */       if (this.defaultOverlay != null)
/*     */       {
/* 133 */         this.defaultOverlay.close();
/*     */       }
/* 135 */       if (this.firstPageOverlay != null)
/*     */       {
/* 137 */         this.firstPageOverlay.close();
/*     */       }
/* 139 */       if (this.lastPageOverlay != null)
/*     */       {
/* 141 */         this.lastPageOverlay.close();
/*     */       }
/* 143 */       if (this.allPagesOverlay != null)
/*     */       {
/* 145 */         this.allPagesOverlay.close();
/*     */       }
/* 147 */       if (this.oddPageOverlay != null)
/*     */       {
/* 149 */         this.oddPageOverlay.close();
/*     */       }
/* 151 */       if (this.evenPageOverlay != null)
/*     */       {
/* 153 */         this.evenPageOverlay.close();
/*     */       }
/* 155 */       for (Map.Entry e : this.specificPageOverlay.entrySet())
/*     */       {
/* 157 */         ((PDDocument)e.getValue()).close();
/*     */       }
/* 159 */       this.specificPageOverlay.clear();
/* 160 */       this.specificPageOverlayPage.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void loadPDFs(boolean useNonSeqParser)
/*     */     throws IOException
/*     */   {
/* 167 */     if (this.inputFileName != null)
/*     */     {
/* 169 */       this.inputPDFDocument = loadPDF(this.inputFileName, useNonSeqParser);
/*     */     }
/*     */ 
/* 172 */     if (this.defaultOverlayFilename != null)
/*     */     {
/* 174 */       this.defaultOverlay = loadPDF(this.defaultOverlayFilename, useNonSeqParser);
/*     */     }
/* 176 */     if (this.defaultOverlay != null)
/*     */     {
/* 178 */       this.defaultOverlayPage = getLayoutPage(this.defaultOverlay);
/*     */     }
/*     */ 
/* 181 */     if (this.firstPageOverlayFilename != null)
/*     */     {
/* 183 */       this.firstPageOverlay = loadPDF(this.firstPageOverlayFilename, useNonSeqParser);
/*     */     }
/* 185 */     if (this.firstPageOverlay != null)
/*     */     {
/* 187 */       this.firstPageOverlayPage = getLayoutPage(this.firstPageOverlay);
/*     */     }
/*     */ 
/* 190 */     if (this.lastPageOverlayFilename != null)
/*     */     {
/* 192 */       this.lastPageOverlay = loadPDF(this.lastPageOverlayFilename, useNonSeqParser);
/*     */     }
/* 194 */     if (this.lastPageOverlay != null)
/*     */     {
/* 196 */       this.lastPageOverlayPage = getLayoutPage(this.lastPageOverlay);
/*     */     }
/*     */ 
/* 199 */     if (this.oddPageOverlayFilename != null)
/*     */     {
/* 201 */       this.oddPageOverlay = loadPDF(this.oddPageOverlayFilename, useNonSeqParser);
/*     */     }
/* 203 */     if (this.oddPageOverlay != null)
/*     */     {
/* 205 */       this.oddPageOverlayPage = getLayoutPage(this.oddPageOverlay);
/*     */     }
/*     */ 
/* 208 */     if (this.evenPageOverlayFilename != null)
/*     */     {
/* 210 */       this.evenPageOverlay = loadPDF(this.evenPageOverlayFilename, useNonSeqParser);
/*     */     }
/* 212 */     if (this.evenPageOverlay != null)
/*     */     {
/* 214 */       this.evenPageOverlayPage = getLayoutPage(this.evenPageOverlay);
/*     */     }
/*     */ 
/* 217 */     if (this.allPagesOverlayFilename != null)
/*     */     {
/* 219 */       this.allPagesOverlay = loadPDF(this.allPagesOverlayFilename, useNonSeqParser);
/*     */     }
/* 221 */     if (this.allPagesOverlay != null)
/*     */     {
/* 223 */       this.specificPageOverlayPage = getLayoutPages(this.allPagesOverlay);
/* 224 */       this.useAllOverlayPages = true;
/* 225 */       this.numberOfOverlayPages = this.specificPageOverlayPage.size();
/*     */     }
/*     */   }
/*     */ 
/*     */   private PDDocument loadPDF(String pdfName, boolean useNonSeqParser) throws IOException
/*     */   {
/* 231 */     PDDocument pdf = null;
/* 232 */     if (useNonSeqParser)
/*     */     {
/* 234 */       pdf = PDDocument.loadNonSeq(new File(pdfName), null);
/*     */     }
/*     */     else
/*     */     {
/* 238 */       pdf = PDDocument.load(pdfName);
/*     */     }
/* 240 */     return pdf;
/*     */   }
/*     */ 
/*     */   private LayoutPage getLayoutPage(PDDocument doc)
/*     */     throws IOException
/*     */   {
/* 262 */     PDDocumentCatalog catalog = doc.getDocumentCatalog();
/* 263 */     PDPage page = (PDPage)catalog.getAllPages().get(0);
/* 264 */     COSBase contents = page.getCOSDictionary().getDictionaryObject(COSName.CONTENTS);
/* 265 */     PDResources resources = page.findResources();
/* 266 */     if (resources == null)
/*     */     {
/* 268 */       resources = new PDResources();
/*     */     }
/* 270 */     return new LayoutPage(page.getMediaBox(), createContentStream(contents), resources.getCOSDictionary(), null);
/*     */   }
/*     */ 
/*     */   private HashMap<Integer, LayoutPage> getLayoutPages(PDDocument doc) throws IOException
/*     */   {
/* 275 */     PDDocumentCatalog catalog = doc.getDocumentCatalog();
/* 276 */     int numberOfPages = doc.getNumberOfPages();
/* 277 */     HashMap layoutPages = new HashMap(numberOfPages);
/* 278 */     for (int i = 0; i < numberOfPages; i++)
/*     */     {
/* 280 */       PDPage page = (PDPage)catalog.getAllPages().get(i);
/* 281 */       COSBase contents = page.getCOSDictionary().getDictionaryObject(COSName.CONTENTS);
/* 282 */       PDResources resources = page.findResources();
/* 283 */       if (resources == null)
/*     */       {
/* 285 */         resources = new PDResources();
/*     */       }
/* 287 */       layoutPages.put(Integer.valueOf(i), new LayoutPage(page.getMediaBox(), createContentStream(contents), resources.getCOSDictionary(), null));
/*     */     }
/*     */ 
/* 290 */     return layoutPages;
/*     */   }
/*     */ 
/*     */   private COSStream createContentStream(COSBase contents) throws IOException
/*     */   {
/* 295 */     List contentStreams = createContentStreamList(contents);
/*     */ 
/* 297 */     COSStream concatStream = new COSStream(new RandomAccessBuffer());
/* 298 */     OutputStream out = concatStream.createUnfilteredStream();
/* 299 */     for (COSStream contentStream : contentStreams)
/*     */     {
/* 301 */       InputStream in = contentStream.getUnfilteredStream();
/* 302 */       byte[] buf = new byte[2048];
/*     */       int n;
/* 304 */       while ((n = in.read(buf)) > 0)
/*     */       {
/* 306 */         out.write(buf, 0, n);
/*     */       }
/* 308 */       out.flush();
/*     */     }
/* 310 */     out.close();
/* 311 */     concatStream.setFilters(COSName.FLATE_DECODE);
/* 312 */     return concatStream;
/*     */   }
/*     */ 
/*     */   private List<COSStream> createContentStreamList(COSBase contents) throws IOException
/*     */   {
/* 317 */     List contentStreams = new ArrayList();
/* 318 */     if ((contents instanceof COSStream))
/*     */     {
/* 320 */       contentStreams.add((COSStream)contents);
/*     */     }
/* 322 */     else if ((contents instanceof COSArray))
/*     */     {
/* 324 */       for (COSBase item : (COSArray)contents)
/*     */       {
/* 326 */         contentStreams.addAll(createContentStreamList(item));
/*     */       }
/*     */     }
/* 329 */     else if ((contents instanceof COSObject))
/*     */     {
/* 331 */       contentStreams.addAll(createContentStreamList(((COSObject)contents).getObject()));
/*     */     }
/*     */     else
/*     */     {
/* 335 */       throw new IOException("Contents are unknown type:" + contents.getClass().getName());
/*     */     }
/* 337 */     return contentStreams;
/*     */   }
/*     */ 
/*     */   private void processPages(List<PDPage> pages) throws IOException
/*     */   {
/* 342 */     int pageCount = 0;
/* 343 */     for (PDPage page : pages)
/*     */     {
/* 345 */       COSDictionary pageDictionary = page.getCOSDictionary();
/* 346 */       COSBase contents = pageDictionary.getDictionaryObject(COSName.CONTENTS);
/* 347 */       COSArray contentArray = new COSArray();
/* 348 */       switch (1.$SwitchMap$org$apache$pdfbox$util$Overlay$Position[this.position.ordinal()])
/*     */       {
/*     */       case 1:
/* 352 */         contentArray.add(createStream("q\n"));
/*     */ 
/* 354 */         if ((contents instanceof COSStream))
/*     */         {
/* 356 */           contentArray.add(contents);
/*     */         }
/* 358 */         else if ((contents instanceof COSArray))
/*     */         {
/* 360 */           contentArray.addAll((COSArray)contents);
/*     */         }
/*     */         else
/*     */         {
/* 364 */           throw new IOException("Unknown content type:" + contents.getClass().getName());
/*     */         }
/*     */ 
/* 367 */         contentArray.add(createStream("Q\n"));
/*     */ 
/* 369 */         overlayPage(contentArray, page, pageCount + 1, pages.size());
/* 370 */         break;
/*     */       case 2:
/* 373 */         overlayPage(contentArray, page, pageCount + 1, pages.size());
/*     */ 
/* 375 */         if ((contents instanceof COSStream))
/*     */         {
/* 377 */           contentArray.add(contents);
/*     */         }
/* 379 */         else if ((contents instanceof COSArray))
/*     */         {
/* 381 */           contentArray.addAll((COSArray)contents);
/*     */         }
/*     */         else
/*     */         {
/* 385 */           throw new IOException("Unknown content type:" + contents.getClass().getName());
/*     */         }
/*     */         break;
/*     */       default:
/* 389 */         throw new IOException("Unknown type of position:" + this.position);
/*     */       }
/* 391 */       pageDictionary.setItem(COSName.CONTENTS, contentArray);
/* 392 */       pageCount++;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void overlayPage(COSArray array, PDPage page, int pageNumber, int numberOfPages) throws IOException
/*     */   {
/* 398 */     LayoutPage layoutPage = null;
/* 399 */     if ((!this.useAllOverlayPages) && (this.specificPageOverlayPage.containsKey(Integer.valueOf(pageNumber))))
/*     */     {
/* 401 */       layoutPage = (LayoutPage)this.specificPageOverlayPage.get(Integer.valueOf(pageNumber));
/*     */     }
/* 403 */     else if ((pageNumber == 1) && (this.firstPageOverlayPage != null))
/*     */     {
/* 405 */       layoutPage = this.firstPageOverlayPage;
/*     */     }
/* 407 */     else if ((pageNumber == numberOfPages) && (this.lastPageOverlayPage != null))
/*     */     {
/* 409 */       layoutPage = this.lastPageOverlayPage;
/*     */     }
/* 411 */     else if ((pageNumber % 2 == 1) && (this.oddPageOverlayPage != null))
/*     */     {
/* 413 */       layoutPage = this.oddPageOverlayPage;
/*     */     }
/* 415 */     else if ((pageNumber % 2 == 0) && (this.evenPageOverlayPage != null))
/*     */     {
/* 417 */       layoutPage = this.evenPageOverlayPage;
/*     */     }
/* 419 */     else if (this.defaultOverlayPage != null)
/*     */     {
/* 421 */       layoutPage = this.defaultOverlayPage;
/*     */     }
/* 423 */     else if (this.useAllOverlayPages)
/*     */     {
/* 425 */       int usePageNum = (pageNumber - 1) % this.numberOfOverlayPages;
/* 426 */       layoutPage = (LayoutPage)this.specificPageOverlayPage.get(Integer.valueOf(usePageNum));
/*     */     }
/* 428 */     if (layoutPage != null)
/*     */     {
/* 430 */       PDResources resources = page.findResources();
/* 431 */       if (resources == null)
/*     */       {
/* 433 */         resources = new PDResources();
/* 434 */         page.setResources(resources);
/*     */       }
/* 436 */       String xObjectId = createOverlayXObject(page, layoutPage, layoutPage.overlayContentStream);
/* 437 */       array.add(createOverlayStream(page, layoutPage, xObjectId));
/*     */     }
/*     */   }
/*     */ 
/*     */   private String createOverlayXObject(PDPage page, LayoutPage layoutPage, COSStream contentStream)
/*     */   {
/* 443 */     PDXObjectForm xobjForm = new PDXObjectForm(contentStream);
/* 444 */     xobjForm.setResources(new PDResources(layoutPage.overlayResources));
/* 445 */     xobjForm.setFormType(1);
/* 446 */     xobjForm.setBBox(layoutPage.overlayMediaBox.createRetranslatedRectangle());
/* 447 */     xobjForm.setMatrix(new AffineTransform());
/* 448 */     PDResources resources = page.findResources();
/* 449 */     return resources.addXObject(xobjForm, "OL");
/*     */   }
/*     */ 
/*     */   private COSStream createOverlayStream(PDPage page, LayoutPage layoutPage, String xObjectId)
/*     */     throws IOException
/*     */   {
/* 455 */     PDRectangle pageMediaBox = page.getMediaBox();
/* 456 */     float scale = 1.0F;
/* 457 */     float hShift = (pageMediaBox.getWidth() - layoutPage.overlayMediaBox.getWidth()) / 2.0F;
/* 458 */     float vShift = (pageMediaBox.getHeight() - layoutPage.overlayMediaBox.getHeight()) / 2.0F;
/* 459 */     return createStream("q\nq " + scale + " 0 0 " + scale + " " + hShift + " " + vShift + " cm /" + xObjectId + " Do Q\nQ\n");
/*     */   }
/*     */ 
/*     */   private COSStream createStream(String content)
/*     */     throws IOException
/*     */   {
/* 465 */     COSStream stream = new COSStream(new RandomAccessBuffer());
/* 466 */     OutputStream out = stream.createUnfilteredStream();
/* 467 */     out.write(content.getBytes("ISO-8859-1"));
/* 468 */     out.close();
/* 469 */     stream.setFilters(COSName.FLATE_DECODE);
/* 470 */     return stream;
/*     */   }
/*     */ 
/*     */   public void setOverlayPosition(Position overlayPosition)
/*     */   {
/* 480 */     this.position = overlayPosition;
/*     */   }
/*     */ 
/*     */   public void setInputFile(String inputFile)
/*     */   {
/* 490 */     this.inputFileName = inputFile;
/*     */   }
/*     */ 
/*     */   public void setInputPDF(PDDocument inputPDF)
/*     */   {
/* 500 */     this.inputPDFDocument = inputPDF;
/*     */   }
/*     */ 
/*     */   public String getInputFile()
/*     */   {
/* 510 */     return this.inputFileName;
/*     */   }
/*     */ 
/*     */   public void setOutputFile(String outputFile)
/*     */   {
/* 520 */     this.outputFilename = outputFile;
/*     */   }
/*     */ 
/*     */   public String getOutputFile()
/*     */   {
/* 530 */     return this.outputFilename;
/*     */   }
/*     */ 
/*     */   public void setDefaultOverlayFile(String defaultOverlayFile)
/*     */   {
/* 540 */     this.defaultOverlayFilename = defaultOverlayFile;
/*     */   }
/*     */ 
/*     */   public void setDefaultOverlayPDF(PDDocument defaultOverlayPDF)
/*     */   {
/* 550 */     this.defaultOverlay = defaultOverlayPDF;
/*     */   }
/*     */ 
/*     */   public String getDefaultOverlayFile()
/*     */   {
/* 560 */     return this.defaultOverlayFilename;
/*     */   }
/*     */ 
/*     */   public void setFirstPageOverlayFile(String firstPageOverlayFile)
/*     */   {
/* 570 */     this.firstPageOverlayFilename = firstPageOverlayFile;
/*     */   }
/*     */ 
/*     */   public void setFirstPageOverlayPDF(PDDocument firstPageOverlayPDF)
/*     */   {
/* 580 */     this.firstPageOverlay = firstPageOverlayPDF;
/*     */   }
/*     */ 
/*     */   public void setLastPageOverlayFile(String lastPageOverlayFile)
/*     */   {
/* 590 */     this.lastPageOverlayFilename = lastPageOverlayFile;
/*     */   }
/*     */ 
/*     */   public void setLastPageOverlayPDF(PDDocument lastPageOverlayPDF)
/*     */   {
/* 600 */     this.lastPageOverlay = lastPageOverlayPDF;
/*     */   }
/*     */ 
/*     */   public void setAllPagesOverlayFile(String allPagesOverlayFile)
/*     */   {
/* 610 */     this.allPagesOverlayFilename = allPagesOverlayFile;
/*     */   }
/*     */ 
/*     */   public void setAllPagesOverlayPDF(PDDocument allPagesOverlayPDF)
/*     */   {
/* 620 */     this.allPagesOverlay = allPagesOverlayPDF;
/*     */   }
/*     */ 
/*     */   public void setOddPageOverlayFile(String oddPageOverlayFile)
/*     */   {
/* 630 */     this.oddPageOverlayFilename = oddPageOverlayFile;
/*     */   }
/*     */ 
/*     */   public void setOddPageOverlayPDF(PDDocument oddPageOverlayPDF)
/*     */   {
/* 640 */     this.oddPageOverlay = oddPageOverlayPDF;
/*     */   }
/*     */ 
/*     */   public void setEvenPageOverlayFile(String evenPageOverlayFile)
/*     */   {
/* 650 */     this.evenPageOverlayFilename = evenPageOverlayFile;
/*     */   }
/*     */ 
/*     */   public void setEvenPageOverlayPDF(PDDocument evenPageOverlayPDF)
/*     */   {
/* 660 */     this.evenPageOverlay = evenPageOverlayPDF;
/*     */   }
/*     */ 
/*     */   private static class LayoutPage
/*     */   {
/*     */     private final PDRectangle overlayMediaBox;
/*     */     private final COSStream overlayContentStream;
/*     */     private final COSDictionary overlayResources;
/*     */ 
/*     */     private LayoutPage(PDRectangle mediaBox, COSStream contentStream, COSDictionary resources)
/*     */     {
/* 254 */       this.overlayMediaBox = mediaBox;
/* 255 */       this.overlayContentStream = contentStream;
/* 256 */       this.overlayResources = resources;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum Position
/*     */   {
/*  57 */     FOREGROUND, BACKGROUND;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.Overlay
 * JD-Core Version:    0.6.2
 */