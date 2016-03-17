/*     */ package com.itextpdf.testutils;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.pdf.PRIndirectReference;
/*     */ import com.itextpdf.text.pdf.PRStream;
/*     */ import com.itextpdf.text.pdf.PdfAnnotation.PdfImportedLink;
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfBoolean;
/*     */ import com.itextpdf.text.pdf.PdfContentByte;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfReader;
/*     */ import com.itextpdf.text.pdf.PdfStamper;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ import com.itextpdf.text.pdf.RefKey;
/*     */ import com.itextpdf.text.pdf.parser.ImageRenderInfo;
/*     */ import com.itextpdf.text.pdf.parser.PdfContentStreamProcessor;
/*     */ import com.itextpdf.text.pdf.parser.RenderListener;
/*     */ import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
/*     */ import com.itextpdf.text.pdf.parser.TaggedPdfReaderTool;
/*     */ import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
/*     */ import com.itextpdf.text.pdf.parser.TextRenderInfo;
/*     */ import com.itextpdf.text.xml.XMLUtil;
/*     */ import com.itextpdf.xmp.XMPException;
/*     */ import com.itextpdf.xmp.XMPMeta;
/*     */ import com.itextpdf.xmp.XMPMetaFactory;
/*     */ import com.itextpdf.xmp.XMPUtils;
/*     */ import com.itextpdf.xmp.options.SerializeOptions;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class CompareTool
/*     */ {
/*     */   private String gsExec;
/*     */   private String compareExec;
/*  74 */   private final String gsParams = " -dNOPAUSE -dBATCH -sDEVICE=png16m -r150 -sOutputFile=<outputfile> <inputfile>";
/*  75 */   private final String compareParams = " <image1> <image2> <difference>";
/*     */   private static final String cannotOpenTargetDirectory = "Cannot open target directory for <filename>.";
/*     */   private static final String gsFailed = "GhostScript failed for <filename>.";
/*     */   private static final String unexpectedNumberOfPages = "Unexpected number of pages for <filename>.";
/*     */   private static final String differentPages = "File <filename> differs on page <pagenumber>.";
/*     */   private static final String undefinedGsPath = "Path to GhostScript is not specified. Please use -DgsExec=<path_to_ghostscript> (e.g. -DgsExec=\"C:/Program Files/gs/gs9.14/bin/gswin32c.exe\")";
/*     */   private static final String ignoredAreasPrefix = "ignored_areas_";
/*     */   private String cmpPdf;
/*     */   private String cmpPdfName;
/*     */   private String cmpImage;
/*     */   private String outPdf;
/*     */   private String outPdfName;
/*     */   private String outImage;
/*     */   List<PdfDictionary> outPages;
/*     */   List<RefKey> outPagesRef;
/*     */   List<PdfDictionary> cmpPages;
/*     */   List<RefKey> cmpPagesRef;
/*     */ 
/*     */   public CompareTool(String outPdf, String cmpPdf)
/*     */   {
/*  94 */     init(outPdf, cmpPdf);
/*  95 */     this.gsExec = System.getProperty("gsExec");
/*  96 */     this.compareExec = System.getProperty("compareExec");
/*     */   }
/*     */ 
/*     */   public String compare(String outPath, String differenceImagePrefix) throws IOException, InterruptedException, DocumentException {
/* 100 */     return compare(outPath, differenceImagePrefix, null);
/*     */   }
/*     */ 
/*     */   public String compare(String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas) throws IOException, InterruptedException, DocumentException {
/* 104 */     return compare(outPath, differenceImagePrefix, ignoredAreas, null);
/*     */   }
/*     */ 
/*     */   protected String compare(String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas, List<Integer> equalPages) throws IOException, InterruptedException, DocumentException
/*     */   {
/* 109 */     if (this.gsExec == null)
/* 110 */       return "Path to GhostScript is not specified. Please use -DgsExec=<path_to_ghostscript> (e.g. -DgsExec=\"C:/Program Files/gs/gs9.14/bin/gswin32c.exe\")";
/* 111 */     if (!new File(this.gsExec).exists()) {
/* 112 */       return new File(this.gsExec).getAbsolutePath() + " does not exist";
/*     */     }
/* 114 */     if (!outPath.endsWith("/"))
/* 115 */       outPath = outPath + "/";
/* 116 */     File targetDir = new File(outPath);
/*     */ 
/* 120 */     if (!targetDir.exists()) {
/* 121 */       targetDir.mkdir();
/*     */     } else {
/* 123 */       File[] imageFiles = targetDir.listFiles(new PngFileFilter());
/* 124 */       for (File file : imageFiles) {
/* 125 */         file.delete();
/*     */       }
/* 127 */       File[] cmpImageFiles = targetDir.listFiles(new CmpPngFileFilter());
/* 128 */       for (File file : cmpImageFiles) {
/* 129 */         file.delete();
/*     */       }
/*     */     }
/*     */ 
/* 133 */     File diffFile = new File(outPath + differenceImagePrefix);
/* 134 */     if (diffFile.exists()) {
/* 135 */       diffFile.delete();
/*     */     }
/*     */ 
/* 138 */     if ((ignoredAreas != null) && (!ignoredAreas.isEmpty())) {
/* 139 */       PdfReader cmpReader = new PdfReader(this.cmpPdf);
/* 140 */       PdfReader outReader = new PdfReader(this.outPdf);
/* 141 */       PdfStamper outStamper = new PdfStamper(outReader, new FileOutputStream(outPath + "ignored_areas_" + this.outPdfName));
/* 142 */       PdfStamper cmpStamper = new PdfStamper(cmpReader, new FileOutputStream(outPath + "ignored_areas_" + this.cmpPdfName));
/*     */ 
/* 144 */       for (Map.Entry entry : ignoredAreas.entrySet()) {
/* 145 */         int pageNumber = ((Integer)entry.getKey()).intValue();
/* 146 */         List rectangles = (List)entry.getValue();
/*     */ 
/* 148 */         if ((rectangles != null) && (!rectangles.isEmpty())) {
/* 149 */           outCB = outStamper.getOverContent(pageNumber);
/* 150 */           cmpCB = cmpStamper.getOverContent(pageNumber);
/*     */ 
/* 152 */           for (Rectangle rect : rectangles) {
/* 153 */             rect.setBackgroundColor(BaseColor.BLACK);
/* 154 */             outCB.rectangle(rect);
/* 155 */             cmpCB.rectangle(rect);
/*     */           }
/*     */         }
/*     */       }
/*     */       PdfContentByte outCB;
/*     */       PdfContentByte cmpCB;
/* 160 */       outStamper.close();
/* 161 */       cmpStamper.close();
/*     */ 
/* 163 */       outReader.close();
/* 164 */       cmpReader.close();
/*     */ 
/* 166 */       init(outPath + "ignored_areas_" + this.outPdfName, outPath + "ignored_areas_" + this.cmpPdfName);
/*     */     }
/*     */     File[] cmpImageFiles;
/*     */     File[] imageFiles;
/* 169 */     if (targetDir.exists()) {
/* 170 */       getClass(); String gsParams = " -dNOPAUSE -dBATCH -sDEVICE=png16m -r150 -sOutputFile=<outputfile> <inputfile>".replace("<outputfile>", outPath + this.cmpImage).replace("<inputfile>", this.cmpPdf);
/* 171 */       Process p = Runtime.getRuntime().exec(this.gsExec + gsParams);
/* 172 */       BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
/* 173 */       BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
/*     */       String line;
/* 175 */       while ((line = bri.readLine()) != null) {
/* 176 */         System.out.println(line);
/*     */       }
/* 178 */       bri.close();
/* 179 */       while ((line = bre.readLine()) != null) {
/* 180 */         System.out.println(line);
/*     */       }
/* 182 */       bre.close();
/*     */       File[] cmpImageFiles;
/*     */       File[] imageFiles;
/* 183 */       if (p.waitFor() == 0) {
/* 184 */         getClass(); gsParams = " -dNOPAUSE -dBATCH -sDEVICE=png16m -r150 -sOutputFile=<outputfile> <inputfile>".replace("<outputfile>", outPath + this.outImage).replace("<inputfile>", this.outPdf);
/* 185 */         p = Runtime.getRuntime().exec(this.gsExec + gsParams);
/* 186 */         bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
/* 187 */         bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
/* 188 */         while ((line = bri.readLine()) != null) {
/* 189 */           System.out.println(line);
/*     */         }
/* 191 */         bri.close();
/* 192 */         while ((line = bre.readLine()) != null) {
/* 193 */           System.out.println(line);
/*     */         }
/* 195 */         bre.close();
/* 196 */         int exitValue = p.waitFor();
/*     */ 
/* 198 */         if (exitValue == 0) {
/* 199 */           File[] imageFiles = targetDir.listFiles(new PngFileFilter());
/* 200 */           File[] cmpImageFiles = targetDir.listFiles(new CmpPngFileFilter());
/* 201 */           boolean bUnexpectedNumberOfPages = false;
/* 202 */           if (imageFiles.length != cmpImageFiles.length) {
/* 203 */             bUnexpectedNumberOfPages = true;
/*     */           }
/* 205 */           int cnt = Math.min(imageFiles.length, cmpImageFiles.length);
/* 206 */           if (cnt < 1) {
/* 207 */             return "No files for comparing!!!\nThe result or sample pdf file is not processed by GhostScript.";
/*     */           }
/* 209 */           Arrays.sort(imageFiles, new ImageNameComparator());
/* 210 */           Arrays.sort(cmpImageFiles, new ImageNameComparator());
/* 211 */           String differentPagesFail = null;
/* 212 */           for (int i = 0; i < cnt; i++)
/* 213 */             if ((equalPages == null) || (!equalPages.contains(Integer.valueOf(i))))
/*     */             {
/* 215 */               System.out.print("Comparing page " + Integer.toString(i + 1) + " (" + imageFiles[i].getAbsolutePath() + ")...");
/* 216 */               FileInputStream is1 = new FileInputStream(imageFiles[i]);
/* 217 */               FileInputStream is2 = new FileInputStream(cmpImageFiles[i]);
/* 218 */               boolean cmpResult = compareStreams(is1, is2);
/* 219 */               is1.close();
/* 220 */               is2.close();
/* 221 */               if (!cmpResult) {
/* 222 */                 if ((this.compareExec != null) && (new File(this.compareExec).exists())) {
/* 223 */                   getClass(); String compareParams = " <image1> <image2> <difference>".replace("<image1>", imageFiles[i].getAbsolutePath()).replace("<image2>", cmpImageFiles[i].getAbsolutePath()).replace("<difference>", outPath + differenceImagePrefix + Integer.toString(i + 1) + ".png");
/* 224 */                   p = Runtime.getRuntime().exec(this.compareExec + compareParams);
/* 225 */                   bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
/* 226 */                   while ((line = bre.readLine()) != null) {
/* 227 */                     System.out.println(line);
/*     */                   }
/* 229 */                   bre.close();
/* 230 */                   int cmpExitValue = p.waitFor();
/* 231 */                   if (cmpExitValue == 0) {
/* 232 */                     if (differentPagesFail == null) {
/* 233 */                       differentPagesFail = "File <filename> differs on page <pagenumber>.".replace("<filename>", this.outPdf).replace("<pagenumber>", Integer.toString(i + 1));
/* 234 */                       differentPagesFail = differentPagesFail + "\nPlease, examine " + outPath + differenceImagePrefix + Integer.toString(i + 1) + ".png for more details.";
/*     */                     } else {
/* 236 */                       differentPagesFail = "File " + this.outPdf + " differs.\nPlease, examine difference images for more details.";
/*     */                     }
/*     */                   }
/*     */                   else
/* 240 */                     differentPagesFail = "File <filename> differs on page <pagenumber>.".replace("<filename>", this.outPdf).replace("<pagenumber>", Integer.toString(i + 1));
/*     */                 }
/*     */                 else {
/* 243 */                   differentPagesFail = "File <filename> differs on page <pagenumber>.".replace("<filename>", this.outPdf).replace("<pagenumber>", Integer.toString(i + 1));
/* 244 */                   differentPagesFail = differentPagesFail + "\nYou can optionally specify path to ImageMagick compare tool (e.g. -DcompareExec=\"C:/Program Files/ImageMagick-6.5.4-2/compare.exe\") to visualize differences.";
/* 245 */                   break;
/*     */                 }
/* 247 */                 System.out.println(differentPagesFail);
/*     */               } else {
/* 249 */                 System.out.println("done.");
/*     */               }
/*     */             }
/* 252 */           if (differentPagesFail != null) {
/* 253 */             return differentPagesFail;
/*     */           }
/* 255 */           if (bUnexpectedNumberOfPages)
/* 256 */             return "Unexpected number of pages for <filename>.".replace("<filename>", this.outPdf);
/*     */         }
/*     */         else {
/* 259 */           return "GhostScript failed for <filename>.".replace("<filename>", this.outPdf);
/*     */         }
/*     */       } else {
/* 262 */         return "GhostScript failed for <filename>.".replace("<filename>", this.cmpPdf);
/*     */       }
/*     */     } else {
/* 265 */       return "Cannot open target directory for <filename>.".replace("<filename>", this.outPdf);
/*     */     }
/*     */     File[] cmpImageFiles;
/*     */     File[] imageFiles;
/* 268 */     return null;
/*     */   }
/*     */ 
/*     */   public String compare(String outPdf, String cmpPdf, String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas) throws IOException, InterruptedException, DocumentException {
/* 272 */     init(outPdf, cmpPdf);
/* 273 */     return compare(outPath, differenceImagePrefix, ignoredAreas);
/*     */   }
/*     */ 
/*     */   public String compare(String outPdf, String cmpPdf, String outPath, String differenceImagePrefix) throws IOException, InterruptedException, DocumentException {
/* 277 */     return compare(outPdf, cmpPdf, outPath, differenceImagePrefix, null);
/*     */   }
/*     */ 
/*     */   public String compareByContent(String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas)
/*     */     throws DocumentException, InterruptedException, IOException
/*     */   {
/* 287 */     System.out.print("[itext] INFO  Comparing by content..........");
/* 288 */     PdfReader outReader = new PdfReader(this.outPdf);
/* 289 */     this.outPages = new ArrayList();
/* 290 */     this.outPagesRef = new ArrayList();
/* 291 */     loadPagesFromReader(outReader, this.outPages, this.outPagesRef);
/*     */ 
/* 293 */     PdfReader cmpReader = new PdfReader(this.cmpPdf);
/* 294 */     this.cmpPages = new ArrayList();
/* 295 */     this.cmpPagesRef = new ArrayList();
/* 296 */     loadPagesFromReader(cmpReader, this.cmpPages, this.cmpPagesRef);
/*     */ 
/* 298 */     if (this.outPages.size() != this.cmpPages.size()) {
/* 299 */       return compare(outPath, differenceImagePrefix, ignoredAreas);
/*     */     }
/* 301 */     List equalPages = new ArrayList(this.cmpPages.size());
/* 302 */     for (int i = 0; i < this.cmpPages.size(); i++) {
/* 303 */       if (objectsIsEquals((PdfDictionary)this.outPages.get(i), (PdfDictionary)this.cmpPages.get(i)))
/* 304 */         equalPages.add(Integer.valueOf(i));
/*     */     }
/* 306 */     outReader.close();
/* 307 */     cmpReader.close();
/*     */ 
/* 310 */     if (equalPages.size() == this.cmpPages.size()) {
/* 311 */       System.out.println("OK");
/* 312 */       System.out.flush();
/* 313 */       return null;
/*     */     }
/* 315 */     System.out.println("Fail");
/* 316 */     System.out.flush();
/* 317 */     String message = compare(outPath, differenceImagePrefix, ignoredAreas, equalPages);
/* 318 */     if ((message == null) || (message.length() == 0))
/* 319 */       return "Compare by content fails. No visual differences";
/* 320 */     return message;
/*     */   }
/*     */ 
/*     */   public String compareByContent(String outPath, String differenceImagePrefix) throws DocumentException, InterruptedException, IOException
/*     */   {
/* 325 */     return compareByContent(outPath, differenceImagePrefix, null);
/*     */   }
/*     */ 
/*     */   public String compareByContent(String outPdf, String cmpPdf, String outPath, String differenceImagePrefix, Map<Integer, List<Rectangle>> ignoredAreas) throws DocumentException, InterruptedException, IOException {
/* 329 */     init(outPdf, cmpPdf);
/* 330 */     return compareByContent(outPath, differenceImagePrefix, ignoredAreas);
/*     */   }
/*     */ 
/*     */   public String compareByContent(String outPdf, String cmpPdf, String outPath, String differenceImagePrefix) throws DocumentException, InterruptedException, IOException {
/* 334 */     return compareByContent(outPdf, cmpPdf, outPath, differenceImagePrefix, null);
/*     */   }
/*     */ 
/*     */   private void loadPagesFromReader(PdfReader reader, List<PdfDictionary> pages, List<RefKey> pagesRef) {
/* 338 */     PdfObject pagesDict = reader.getCatalog().get(PdfName.PAGES);
/* 339 */     addPagesFromDict(pagesDict, pages, pagesRef);
/*     */   }
/*     */ 
/*     */   private void addPagesFromDict(PdfObject dictRef, List<PdfDictionary> pages, List<RefKey> pagesRef) {
/* 343 */     PdfDictionary dict = (PdfDictionary)PdfReader.getPdfObject(dictRef);
/* 344 */     if (dict.isPages()) {
/* 345 */       PdfArray kids = dict.getAsArray(PdfName.KIDS);
/* 346 */       if (kids == null) return;
/* 347 */       for (PdfObject kid : kids)
/* 348 */         addPagesFromDict(kid, pages, pagesRef);
/*     */     }
/* 350 */     else if (dict.isPage()) {
/* 351 */       pages.add(dict);
/* 352 */       pagesRef.add(new RefKey((PRIndirectReference)dictRef));
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean objectsIsEquals(PdfDictionary outDict, PdfDictionary cmpDict) throws IOException {
/* 357 */     for (PdfName key : cmpDict.getKeys())
/* 358 */       if (key.compareTo(PdfName.PARENT) != 0)
/* 359 */         if ((key.compareTo(PdfName.BASEFONT) == 0) || (key.compareTo(PdfName.FONTNAME) == 0)) {
/* 360 */           PdfObject cmpObj = cmpDict.getDirectObject(key);
/* 361 */           if ((cmpObj.isName()) && (cmpObj.toString().indexOf('+') > 0)) {
/* 362 */             PdfObject outObj = outDict.getDirectObject(key);
/* 363 */             if ((!outObj.isName()) || (outObj.toString().indexOf('+') == -1))
/* 364 */               return false;
/* 365 */             String cmpName = cmpObj.toString().substring(cmpObj.toString().indexOf('+'));
/* 366 */             String outName = outObj.toString().substring(outObj.toString().indexOf('+'));
/* 367 */             if (cmpName.equals(outName)) continue;
/* 368 */             return false;
/*     */           }
/*     */ 
/*     */         }
/* 372 */         else if (!objectsIsEquals(outDict.get(key), cmpDict.get(key))) {
/* 373 */           return false;
/*     */         }
/* 375 */     return true;
/*     */   }
/*     */ 
/*     */   private boolean objectsIsEquals(PdfObject outObj, PdfObject cmpObj) throws IOException {
/* 379 */     PdfObject outDirectObj = PdfReader.getPdfObject(outObj);
/* 380 */     PdfObject cmpDirectObj = PdfReader.getPdfObject(cmpObj);
/*     */ 
/* 382 */     if ((outDirectObj == null) || (cmpDirectObj.type() != outDirectObj.type()))
/* 383 */       return false;
/* 384 */     if (cmpDirectObj.isDictionary()) {
/* 385 */       PdfDictionary cmpDict = (PdfDictionary)cmpDirectObj;
/* 386 */       PdfDictionary outDict = (PdfDictionary)outDirectObj;
/* 387 */       if (cmpDict.isPage()) {
/* 388 */         if (!outDict.isPage())
/* 389 */           return false;
/* 390 */         RefKey cmpRefKey = new RefKey((PRIndirectReference)cmpObj);
/* 391 */         RefKey outRefKey = new RefKey((PRIndirectReference)outObj);
/* 392 */         if ((this.cmpPagesRef.contains(cmpRefKey)) && (this.cmpPagesRef.indexOf(cmpRefKey) == this.outPagesRef.indexOf(outRefKey)))
/* 393 */           return true;
/* 394 */         return false;
/*     */       }
/* 396 */       if (!objectsIsEquals(outDict, cmpDict))
/* 397 */         return false;
/* 398 */     } else if (cmpDirectObj.isStream()) {
/* 399 */       if (!objectsIsEquals((PRStream)outDirectObj, (PRStream)cmpDirectObj))
/* 400 */         return false;
/* 401 */     } else if (cmpDirectObj.isArray()) {
/* 402 */       if (!objectsIsEquals((PdfArray)outDirectObj, (PdfArray)cmpDirectObj))
/* 403 */         return false;
/* 404 */     } else if (cmpDirectObj.isName()) {
/* 405 */       if (!objectsIsEquals((PdfName)outDirectObj, (PdfName)cmpDirectObj))
/* 406 */         return false;
/* 407 */     } else if (cmpDirectObj.isNumber()) {
/* 408 */       if (!objectsIsEquals((PdfNumber)outDirectObj, (PdfNumber)cmpDirectObj))
/* 409 */         return false;
/* 410 */     } else if (cmpDirectObj.isString()) {
/* 411 */       if (!objectsIsEquals((PdfString)outDirectObj, (PdfString)cmpDirectObj))
/* 412 */         return false;
/* 413 */     } else if (cmpDirectObj.isBoolean()) {
/* 414 */       if (!objectsIsEquals((PdfBoolean)outDirectObj, (PdfBoolean)cmpDirectObj))
/* 415 */         return false;
/*     */     } else {
/* 417 */       throw new UnsupportedOperationException();
/*     */     }
/* 419 */     return true;
/*     */   }
/*     */ 
/*     */   private boolean objectsIsEquals(PRStream outStream, PRStream cmpStream) throws IOException {
/* 423 */     return Arrays.equals(PdfReader.getStreamBytesRaw(outStream), PdfReader.getStreamBytesRaw(cmpStream));
/*     */   }
/*     */ 
/*     */   private boolean objectsIsEquals(PdfArray outArray, PdfArray cmpArray) throws IOException {
/* 427 */     if ((outArray == null) || (outArray.size() != cmpArray.size()))
/* 428 */       return false;
/* 429 */     for (int i = 0; i < cmpArray.size(); i++) {
/* 430 */       if (!objectsIsEquals(outArray.getPdfObject(i), cmpArray.getPdfObject(i))) {
/* 431 */         return false;
/*     */       }
/*     */     }
/* 434 */     return true;
/*     */   }
/*     */ 
/*     */   private boolean objectsIsEquals(PdfName outName, PdfName cmpName) {
/* 438 */     return cmpName.compareTo(outName) == 0;
/*     */   }
/*     */ 
/*     */   private boolean objectsIsEquals(PdfNumber outNumber, PdfNumber cmpNumber) {
/* 442 */     return cmpNumber.doubleValue() == outNumber.doubleValue();
/*     */   }
/*     */ 
/*     */   private boolean objectsIsEquals(PdfString outString, PdfString cmpString) {
/* 446 */     return Arrays.equals(cmpString.getBytes(), outString.getBytes());
/*     */   }
/*     */ 
/*     */   private boolean objectsIsEquals(PdfBoolean outBoolean, PdfBoolean cmpBoolean) {
/* 450 */     return Arrays.equals(cmpBoolean.getBytes(), outBoolean.getBytes());
/*     */   }
/*     */ 
/*     */   public String compareXmp() {
/* 454 */     return compareXmp(false);
/*     */   }
/*     */ 
/*     */   public String compareXmp(boolean ignoreDateAndProducerProperties) {
/* 458 */     PdfReader cmpReader = null;
/* 459 */     PdfReader outReader = null;
/*     */     try {
/* 461 */       cmpReader = new PdfReader(this.cmpPdf);
/* 462 */       outReader = new PdfReader(this.outPdf);
/* 463 */       byte[] cmpBytes = cmpReader.getMetadata(); outBytes = outReader.getMetadata();
/*     */       XMPMeta xmpMeta;
/* 464 */       if (ignoreDateAndProducerProperties) {
/* 465 */         xmpMeta = XMPMetaFactory.parseFromBuffer(cmpBytes);
/*     */ 
/* 467 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/xap/1.0/", "CreateDate", true, true);
/* 468 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/xap/1.0/", "ModifyDate", true, true);
/* 469 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/xap/1.0/", "MetadataDate", true, true);
/* 470 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/pdf/1.3/", "Producer", true, true);
/*     */ 
/* 472 */         cmpBytes = XMPMetaFactory.serializeToBuffer(xmpMeta, new SerializeOptions(8192));
/*     */ 
/* 474 */         xmpMeta = XMPMetaFactory.parseFromBuffer(outBytes);
/* 475 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/xap/1.0/", "CreateDate", true, true);
/* 476 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/xap/1.0/", "ModifyDate", true, true);
/* 477 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/xap/1.0/", "MetadataDate", true, true);
/* 478 */         XMPUtils.removeProperties(xmpMeta, "http://ns.adobe.com/pdf/1.3/", "Producer", true, true);
/*     */ 
/* 480 */         outBytes = XMPMetaFactory.serializeToBuffer(xmpMeta, new SerializeOptions(8192));
/*     */       }
/*     */ 
/* 483 */       if (!compareXmls(cmpBytes, outBytes))
/* 484 */         return "The XMP packages different!";
/*     */     }
/*     */     catch (XMPException xmpExc) {
/* 487 */       return "XMP parsing failure!";
/*     */     } catch (IOException ioExc) {
/* 489 */       return "XMP parsing failure!";
/*     */     } catch (ParserConfigurationException parseExc) {
/* 491 */       return "XMP parsing failure!";
/*     */     }
/*     */     catch (SAXException parseExc)
/*     */     {
/*     */       byte[] outBytes;
/* 493 */       return "XMP parsing failure!";
/*     */     }
/*     */     finally {
/* 496 */       if (cmpReader != null)
/* 497 */         cmpReader.close();
/* 498 */       if (outReader != null)
/* 499 */         outReader.close();
/*     */     }
/* 501 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean compareXmls(byte[] xml1, byte[] xml2) throws ParserConfigurationException, SAXException, IOException {
/* 505 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 506 */     dbf.setNamespaceAware(true);
/* 507 */     dbf.setCoalescing(true);
/* 508 */     dbf.setIgnoringElementContentWhitespace(true);
/* 509 */     dbf.setIgnoringComments(true);
/* 510 */     DocumentBuilder db = dbf.newDocumentBuilder();
/*     */ 
/* 512 */     Document doc1 = db.parse(new ByteArrayInputStream(xml1));
/* 513 */     doc1.normalizeDocument();
/*     */ 
/* 515 */     Document doc2 = db.parse(new ByteArrayInputStream(xml2));
/* 516 */     doc2.normalizeDocument();
/*     */ 
/* 518 */     return doc2.isEqualNode(doc1);
/*     */   }
/*     */ 
/*     */   public String compareDocumentInfo(String outPdf, String cmpPdf) throws IOException {
/* 522 */     System.out.print("[itext] INFO  Comparing document info.......");
/* 523 */     String message = null;
/* 524 */     PdfReader outReader = new PdfReader(outPdf);
/* 525 */     PdfReader cmpReader = new PdfReader(cmpPdf);
/* 526 */     String[] cmpInfo = convertInfo(cmpReader.getInfo());
/* 527 */     String[] outInfo = convertInfo(outReader.getInfo());
/* 528 */     for (int i = 0; i < cmpInfo.length; i++) {
/* 529 */       if (!cmpInfo[i].equals(outInfo[i])) {
/* 530 */         message = "Document info fail";
/* 531 */         break;
/*     */       }
/*     */     }
/* 534 */     outReader.close();
/* 535 */     cmpReader.close();
/*     */ 
/* 537 */     if (message == null)
/* 538 */       System.out.println("OK");
/*     */     else
/* 540 */       System.out.println("Fail");
/* 541 */     System.out.flush();
/* 542 */     return message;
/*     */   }
/*     */ 
/*     */   private boolean linksAreSame(PdfAnnotation.PdfImportedLink cmpLink, PdfAnnotation.PdfImportedLink outLink)
/*     */   {
/* 548 */     if (cmpLink.getDestinationPage() != outLink.getDestinationPage())
/* 549 */       return false;
/* 550 */     if (!cmpLink.getRect().toString().equals(outLink.getRect().toString())) {
/* 551 */       return false;
/*     */     }
/* 553 */     Map cmpParams = cmpLink.getParameters();
/* 554 */     Map outParams = outLink.getParameters();
/* 555 */     if (cmpParams.size() != outParams.size()) {
/* 556 */       return false;
/*     */     }
/* 558 */     for (Map.Entry cmpEntry : cmpParams.entrySet()) {
/* 559 */       PdfObject cmpObj = (PdfObject)cmpEntry.getValue();
/* 560 */       if (!outParams.containsKey(cmpEntry.getKey()))
/* 561 */         return false;
/* 562 */       PdfObject outObj = (PdfObject)outParams.get(cmpEntry.getKey());
/* 563 */       if (cmpObj.type() != outObj.type()) {
/* 564 */         return false;
/*     */       }
/* 566 */       switch (cmpObj.type()) {
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 8:
/* 572 */         if (!cmpObj.toString().equals(outObj.toString()))
/* 573 */           return false; break;
/*     */       case 5:
/*     */       case 6:
/*     */       case 7: } 
/*     */     }
/* 578 */     return true;
/*     */   }
/*     */ 
/*     */   public String compareLinks(String outPdf, String cmpPdf) throws IOException {
/* 582 */     System.out.print("[itext] INFO  Comparing link annotations....");
/* 583 */     String message = null;
/* 584 */     PdfReader outReader = new PdfReader(outPdf);
/* 585 */     PdfReader cmpReader = new PdfReader(cmpPdf);
/* 586 */     for (int i = 0; (i < outReader.getNumberOfPages()) && (i < cmpReader.getNumberOfPages()); i++) {
/* 587 */       List outLinks = outReader.getLinks(i + 1);
/* 588 */       List cmpLinks = cmpReader.getLinks(i + 1);
/* 589 */       if (cmpLinks.size() != outLinks.size()) {
/* 590 */         message = String.format("Different number of links on page %d.", new Object[] { Integer.valueOf(i + 1) });
/* 591 */         break;
/*     */       }
/* 593 */       for (int j = 0; j < cmpLinks.size(); j++) {
/* 594 */         if (!linksAreSame((PdfAnnotation.PdfImportedLink)cmpLinks.get(j), (PdfAnnotation.PdfImportedLink)outLinks.get(j))) {
/* 595 */           message = String.format("Different links on page %d.\n%s\n%s", new Object[] { Integer.valueOf(i + 1), ((PdfAnnotation.PdfImportedLink)cmpLinks.get(j)).toString(), ((PdfAnnotation.PdfImportedLink)outLinks.get(j)).toString() });
/* 596 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 600 */     outReader.close();
/* 601 */     cmpReader.close();
/* 602 */     if (message == null)
/* 603 */       System.out.println("OK");
/*     */     else
/* 605 */       System.out.println("Fail");
/* 606 */     System.out.flush();
/* 607 */     return message;
/*     */   }
/*     */ 
/*     */   public String compareTagStructures(String outPdf, String cmpPdf) throws IOException, ParserConfigurationException, SAXException {
/* 611 */     System.out.print("[itext] INFO  Comparing tag structures......");
/*     */ 
/* 613 */     String outXml = outPdf.replace(".pdf", ".xml");
/* 614 */     String cmpXml = outPdf.replace(".pdf", ".cmp.xml");
/*     */ 
/* 616 */     String message = null;
/* 617 */     PdfReader reader = new PdfReader(outPdf);
/* 618 */     FileOutputStream xmlOut1 = new FileOutputStream(outXml);
/* 619 */     new CmpTaggedPdfReaderTool().convertToXml(reader, xmlOut1);
/* 620 */     reader.close();
/* 621 */     reader = new PdfReader(cmpPdf);
/* 622 */     FileOutputStream xmlOut2 = new FileOutputStream(cmpXml);
/* 623 */     new CmpTaggedPdfReaderTool().convertToXml(reader, xmlOut2);
/* 624 */     reader.close();
/* 625 */     if (!compareXmls(outXml, cmpXml)) {
/* 626 */       message = "The tag structures are different.";
/*     */     }
/* 628 */     xmlOut1.close();
/* 629 */     xmlOut2.close();
/* 630 */     if (message == null)
/* 631 */       System.out.println("OK");
/*     */     else
/* 633 */       System.out.println("Fail");
/* 634 */     System.out.flush();
/* 635 */     return message;
/*     */   }
/*     */ 
/*     */   private String[] convertInfo(HashMap<String, String> info) {
/* 639 */     String[] convertedInfo = { "", "", "", "" };
/* 640 */     for (Map.Entry entry : info.entrySet()) {
/* 641 */       if ("title".equalsIgnoreCase((String)entry.getKey()))
/* 642 */         convertedInfo[0] = ((String)entry.getValue());
/* 643 */       else if ("author".equalsIgnoreCase((String)entry.getKey()))
/* 644 */         convertedInfo[1] = ((String)entry.getValue());
/* 645 */       else if ("subject".equalsIgnoreCase((String)entry.getKey()))
/* 646 */         convertedInfo[2] = ((String)entry.getValue());
/* 647 */       else if ("keywords".equalsIgnoreCase((String)entry.getKey())) {
/* 648 */         convertedInfo[3] = ((String)entry.getValue());
/*     */       }
/*     */     }
/* 651 */     return convertedInfo;
/*     */   }
/*     */ 
/*     */   public boolean compareXmls(String xml1, String xml2) throws ParserConfigurationException, SAXException, IOException {
/* 655 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 656 */     dbf.setNamespaceAware(true);
/* 657 */     dbf.setCoalescing(true);
/* 658 */     dbf.setIgnoringElementContentWhitespace(true);
/* 659 */     dbf.setIgnoringComments(true);
/* 660 */     DocumentBuilder db = dbf.newDocumentBuilder();
/*     */ 
/* 662 */     Document doc1 = db.parse(new File(xml1));
/* 663 */     doc1.normalizeDocument();
/*     */ 
/* 665 */     Document doc2 = db.parse(new File(xml2));
/* 666 */     doc2.normalizeDocument();
/*     */ 
/* 668 */     return doc2.isEqualNode(doc1);
/*     */   }
/*     */ 
/*     */   private void init(String outPdf, String cmpPdf) {
/* 672 */     this.outPdf = outPdf;
/* 673 */     this.cmpPdf = cmpPdf;
/* 674 */     this.outPdfName = new File(outPdf).getName();
/* 675 */     this.cmpPdfName = new File(cmpPdf).getName();
/* 676 */     this.outImage = (this.outPdfName + "-%03d.png");
/* 677 */     if (this.cmpPdfName.startsWith("cmp_")) this.cmpImage = (this.cmpPdfName + "-%03d.png"); else
/* 678 */       this.cmpImage = ("cmp_" + this.cmpPdfName + "-%03d.png");
/*     */   }
/*     */ 
/*     */   private boolean compareStreams(InputStream is1, InputStream is2) throws IOException {
/* 682 */     byte[] buffer1 = new byte[65536];
/* 683 */     byte[] buffer2 = new byte[65536];
/*     */     while (true)
/*     */     {
/* 687 */       int len1 = is1.read(buffer1);
/* 688 */       int len2 = is2.read(buffer2);
/* 689 */       if (len1 != len2)
/* 690 */         return false;
/* 691 */       if (!Arrays.equals(buffer1, buffer2))
/* 692 */         return false;
/* 693 */       if (len1 == -1)
/* 694 */         break;
/*     */     }
/* 696 */     return true;
/*     */   }
/*     */ 
/*     */   class CmpMarkedContentRenderFilter
/*     */     implements RenderListener
/*     */   {
/* 765 */     Map<Integer, TextExtractionStrategy> tagsByMcid = new HashMap();
/*     */ 
/*     */     CmpMarkedContentRenderFilter() {  } 
/* 768 */     public Map<Integer, String> getParsedTagContent() { Map content = new HashMap();
/* 769 */       for (Iterator i$ = this.tagsByMcid.keySet().iterator(); i$.hasNext(); ) { int id = ((Integer)i$.next()).intValue();
/* 770 */         content.put(Integer.valueOf(id), ((TextExtractionStrategy)this.tagsByMcid.get(Integer.valueOf(id))).getResultantText());
/*     */       }
/* 772 */       return content; }
/*     */ 
/*     */     public void beginTextBlock()
/*     */     {
/* 776 */       for (Iterator i$ = this.tagsByMcid.keySet().iterator(); i$.hasNext(); ) { int id = ((Integer)i$.next()).intValue();
/* 777 */         ((TextExtractionStrategy)this.tagsByMcid.get(Integer.valueOf(id))).beginTextBlock(); }
/*     */     }
/*     */ 
/*     */     public void renderText(TextRenderInfo renderInfo)
/*     */     {
/* 782 */       Integer mcid = renderInfo.getMcid();
/* 783 */       if ((mcid != null) && (this.tagsByMcid.containsKey(mcid))) {
/* 784 */         ((TextExtractionStrategy)this.tagsByMcid.get(mcid)).renderText(renderInfo);
/*     */       }
/* 786 */       else if (mcid != null) {
/* 787 */         this.tagsByMcid.put(mcid, new SimpleTextExtractionStrategy());
/* 788 */         ((TextExtractionStrategy)this.tagsByMcid.get(mcid)).renderText(renderInfo);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void endTextBlock() {
/* 793 */       for (Iterator i$ = this.tagsByMcid.keySet().iterator(); i$.hasNext(); ) { int id = ((Integer)i$.next()).intValue();
/* 794 */         ((TextExtractionStrategy)this.tagsByMcid.get(Integer.valueOf(id))).endTextBlock();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void renderImage(ImageRenderInfo renderInfo)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   class CmpTaggedPdfReaderTool extends TaggedPdfReaderTool
/*     */   {
/* 728 */     Map<PdfDictionary, Map<Integer, String>> parsedTags = new HashMap();
/*     */ 
/*     */     CmpTaggedPdfReaderTool() {
/*     */     }
/*     */     public void parseTag(String tag, PdfObject object, PdfDictionary page) throws IOException {
/* 733 */       if ((object instanceof PdfNumber))
/*     */       {
/* 735 */         if (!this.parsedTags.containsKey(page)) {
/* 736 */           CompareTool.CmpMarkedContentRenderFilter listener = new CompareTool.CmpMarkedContentRenderFilter(CompareTool.this);
/*     */ 
/* 738 */           PdfContentStreamProcessor processor = new PdfContentStreamProcessor(listener);
/*     */ 
/* 740 */           processor.processContent(PdfReader.getPageContent(page), page.getAsDict(PdfName.RESOURCES));
/*     */ 
/* 743 */           this.parsedTags.put(page, listener.getParsedTagContent());
/*     */         }
/*     */ 
/* 746 */         String tagContent = "";
/* 747 */         if (((Map)this.parsedTags.get(page)).containsKey(Integer.valueOf(((PdfNumber)object).intValue()))) {
/* 748 */           tagContent = (String)((Map)this.parsedTags.get(page)).get(Integer.valueOf(((PdfNumber)object).intValue()));
/*     */         }
/* 750 */         this.out.print(XMLUtil.escapeXML(tagContent, true));
/*     */       }
/*     */       else {
/* 753 */         super.parseTag(tag, object, page);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void inspectChildDictionary(PdfDictionary k) throws IOException
/*     */     {
/* 759 */       inspectChildDictionary(k, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   class ImageNameComparator
/*     */     implements Comparator<File>
/*     */   {
/*     */     ImageNameComparator()
/*     */     {
/*     */     }
/*     */ 
/*     */     public int compare(File f1, File f2)
/*     */     {
/* 720 */       String f1Name = f1.getAbsolutePath();
/* 721 */       String f2Name = f2.getAbsolutePath();
/* 722 */       return f1Name.compareTo(f2Name);
/*     */     }
/*     */   }
/*     */ 
/*     */   class CmpPngFileFilter
/*     */     implements FileFilter
/*     */   {
/*     */     CmpPngFileFilter()
/*     */     {
/*     */     }
/*     */ 
/*     */     public boolean accept(File pathname)
/*     */     {
/* 711 */       String ap = pathname.getAbsolutePath();
/* 712 */       boolean b1 = ap.endsWith(".png");
/* 713 */       boolean b2 = ap.contains("cmp_");
/* 714 */       return (b1) && (b2) && (ap.contains(CompareTool.this.cmpPdfName));
/*     */     }
/*     */   }
/*     */ 
/*     */   class PngFileFilter
/*     */     implements FileFilter
/*     */   {
/*     */     PngFileFilter()
/*     */     {
/*     */     }
/*     */ 
/*     */     public boolean accept(File pathname)
/*     */     {
/* 702 */       String ap = pathname.getAbsolutePath();
/* 703 */       boolean b1 = ap.endsWith(".png");
/* 704 */       boolean b2 = ap.contains("cmp_");
/* 705 */       return (b1) && (!b2) && (ap.contains(CompareTool.this.outPdfName));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.testutils.CompareTool
 * JD-Core Version:    0.6.2
 */