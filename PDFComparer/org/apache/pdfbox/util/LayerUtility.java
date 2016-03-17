/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.fontbox.util.BoundingBox;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
/*     */ import org.apache.pdfbox.pdmodel.graphics.optionalcontent.PDOptionalContentGroup;
/*     */ import org.apache.pdfbox.pdmodel.graphics.optionalcontent.PDOptionalContentProperties;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
/*     */ import org.apache.pdfbox.pdmodel.markedcontent.PDPropertyList;
/*     */ 
/*     */ public class LayerUtility
/*     */ {
/*     */   private static final boolean DEBUG = true;
/*     */   private PDDocument targetDoc;
/*     */   private PDFCloneUtility cloner;
/* 138 */   private static final Set<String> PAGE_TO_FORM_FILTER = new HashSet(Arrays.asList(new String[] { "Group", "LastModified", "Metadata" }));
/*     */ 
/*     */   public LayerUtility(PDDocument document)
/*     */   {
/*  63 */     this.targetDoc = document;
/*  64 */     this.cloner = new PDFCloneUtility(document);
/*     */   }
/*     */ 
/*     */   public PDDocument getDocument()
/*     */   {
/*  73 */     return this.targetDoc;
/*     */   }
/*     */ 
/*     */   public void wrapInSaveRestore(PDPage page)
/*     */     throws IOException
/*     */   {
/*  85 */     COSDictionary saveGraphicsStateDic = new COSDictionary();
/*  86 */     COSStream saveGraphicsStateStream = getDocument().getDocument().createCOSStream(saveGraphicsStateDic);
/*  87 */     OutputStream saveStream = saveGraphicsStateStream.createUnfilteredStream();
/*  88 */     saveStream.write("q\n".getBytes("ISO-8859-1"));
/*  89 */     saveStream.flush();
/*     */ 
/*  91 */     COSStream restoreGraphicsStateStream = getDocument().getDocument().createCOSStream(saveGraphicsStateDic);
/*  92 */     OutputStream restoreStream = restoreGraphicsStateStream.createUnfilteredStream();
/*  93 */     restoreStream.write("Q\n".getBytes("ISO-8859-1"));
/*  94 */     restoreStream.flush();
/*     */ 
/*  98 */     COSDictionary pageDictionary = page.getCOSDictionary();
/*  99 */     COSBase contents = pageDictionary.getDictionaryObject(COSName.CONTENTS);
/* 100 */     if ((contents instanceof COSStream))
/*     */     {
/* 102 */       COSStream contentsStream = (COSStream)contents;
/*     */ 
/* 104 */       COSArray array = new COSArray();
/* 105 */       array.add(saveGraphicsStateStream);
/* 106 */       array.add(contentsStream);
/* 107 */       array.add(restoreGraphicsStateStream);
/*     */ 
/* 109 */       pageDictionary.setItem(COSName.CONTENTS, array);
/*     */     }
/* 111 */     else if ((contents instanceof COSArray))
/*     */     {
/* 113 */       COSArray contentsArray = (COSArray)contents;
/*     */ 
/* 115 */       contentsArray.add(0, saveGraphicsStateStream);
/* 116 */       contentsArray.add(restoreGraphicsStateStream);
/*     */     }
/*     */     else
/*     */     {
/* 120 */       throw new IOException("Contents are unknown type: " + contents.getClass().getName());
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDXObjectForm importPageAsForm(PDDocument sourceDoc, int pageNumber)
/*     */     throws IOException
/*     */   {
/* 134 */     PDPage page = (PDPage)sourceDoc.getDocumentCatalog().getAllPages().get(pageNumber);
/* 135 */     return importPageAsForm(sourceDoc, page);
/*     */   }
/*     */ 
/*     */   public PDXObjectForm importPageAsForm(PDDocument sourceDoc, PDPage page)
/*     */     throws IOException
/*     */   {
/* 151 */     COSStream pageStream = (COSStream)page.getContents().getCOSObject();
/* 152 */     PDStream newStream = new PDStream(this.targetDoc, pageStream.getUnfilteredStream(), false);
/*     */ 
/* 154 */     PDXObjectForm form = new PDXObjectForm(newStream);
/*     */ 
/* 157 */     PDResources pageRes = page.findResources();
/* 158 */     PDResources formRes = new PDResources();
/* 159 */     this.cloner.cloneMerge(pageRes, formRes);
/* 160 */     form.setResources(formRes);
/*     */ 
/* 163 */     transferDict(page.getCOSDictionary(), form.getCOSStream(), PAGE_TO_FORM_FILTER, true);
/*     */ 
/* 165 */     Matrix matrix = form.getMatrix();
/* 166 */     AffineTransform at = matrix != null ? matrix.createAffineTransform() : new AffineTransform();
/* 167 */     PDRectangle mediaBox = page.findMediaBox();
/* 168 */     PDRectangle cropBox = page.findCropBox();
/* 169 */     PDRectangle viewBox = cropBox != null ? cropBox : mediaBox;
/*     */ 
/* 172 */     int rotation = getNormalizedRotation(page);
/*     */ 
/* 176 */     at.translate(mediaBox.getLowerLeftX() - viewBox.getLowerLeftX(), mediaBox.getLowerLeftY() - viewBox.getLowerLeftY());
/*     */ 
/* 178 */     switch (rotation)
/*     */     {
/*     */     case 90:
/* 181 */       at.scale(viewBox.getWidth() / viewBox.getHeight(), viewBox.getHeight() / viewBox.getWidth());
/* 182 */       at.translate(0.0D, viewBox.getWidth());
/* 183 */       at.rotate(-1.570796326794897D);
/* 184 */       break;
/*     */     case 180:
/* 186 */       at.translate(viewBox.getWidth(), viewBox.getHeight());
/* 187 */       at.rotate(-3.141592653589793D);
/* 188 */       break;
/*     */     case 270:
/* 190 */       at.scale(viewBox.getWidth() / viewBox.getHeight(), viewBox.getHeight() / viewBox.getWidth());
/* 191 */       at.translate(viewBox.getHeight(), 0.0D);
/* 192 */       at.rotate(-4.71238898038469D);
/*     */     }
/*     */ 
/* 197 */     at.translate(-viewBox.getLowerLeftX(), -viewBox.getLowerLeftY());
/* 198 */     if (!at.isIdentity())
/*     */     {
/* 200 */       form.setMatrix(at);
/*     */     }
/*     */ 
/* 203 */     BoundingBox bbox = new BoundingBox();
/* 204 */     bbox.setLowerLeftX(viewBox.getLowerLeftX());
/* 205 */     bbox.setLowerLeftY(viewBox.getLowerLeftY());
/* 206 */     bbox.setUpperRightX(viewBox.getUpperRightX());
/* 207 */     bbox.setUpperRightY(viewBox.getUpperRightY());
/* 208 */     form.setBBox(new PDRectangle(bbox));
/*     */ 
/* 210 */     return form;
/*     */   }
/*     */ 
/*     */   public PDOptionalContentGroup appendFormAsLayer(PDPage targetPage, PDXObjectForm form, AffineTransform transform, String layerName)
/*     */     throws IOException
/*     */   {
/* 229 */     PDDocumentCatalog catalog = this.targetDoc.getDocumentCatalog();
/* 230 */     PDOptionalContentProperties ocprops = catalog.getOCProperties();
/* 231 */     if (ocprops == null)
/*     */     {
/* 233 */       ocprops = new PDOptionalContentProperties();
/* 234 */       catalog.setOCProperties(ocprops);
/*     */     }
/* 236 */     if (ocprops.hasGroup(layerName))
/*     */     {
/* 238 */       throw new IllegalArgumentException("Optional group (layer) already exists: " + layerName);
/*     */     }
/*     */ 
/* 241 */     PDOptionalContentGroup layer = new PDOptionalContentGroup(layerName);
/* 242 */     ocprops.addGroup(layer);
/*     */ 
/* 244 */     PDResources resources = targetPage.findResources();
/* 245 */     PDPropertyList props = resources.getProperties();
/* 246 */     if (props == null)
/*     */     {
/* 248 */       props = new PDPropertyList();
/* 249 */       resources.setProperties(props);
/*     */     }
/*     */ 
/* 253 */     int index = 0;
/*     */     COSName resourceName;
/*     */     PDOptionalContentGroup ocg;
/*     */     do {
/* 258 */       resourceName = COSName.getPDFName("MC" + index);
/* 259 */       ocg = props.getOptionalContentGroup(resourceName);
/* 260 */       index++;
/* 261 */     }while (ocg != null);
/*     */ 
/* 263 */     props.putMapping(resourceName, layer);
/*     */ 
/* 265 */     PDPageContentStream contentStream = new PDPageContentStream(this.targetDoc, targetPage, true, false);
/*     */ 
/* 267 */     contentStream.beginMarkedContentSequence(COSName.OC, resourceName);
/* 268 */     contentStream.drawXObject(form, transform);
/* 269 */     contentStream.endMarkedContentSequence();
/* 270 */     contentStream.close();
/*     */ 
/* 272 */     return layer;
/*     */   }
/*     */ 
/*     */   private void transferDict(COSDictionary orgDict, COSDictionary targetDict, Set<String> filter, boolean inclusive)
/*     */     throws IOException
/*     */   {
/* 278 */     for (Map.Entry entry : orgDict.entrySet())
/*     */     {
/* 280 */       COSName key = (COSName)entry.getKey();
/* 281 */       if (((!inclusive) || (filter.contains(key.getName()))) && (
/* 285 */         (inclusive) || (!filter.contains(key.getName()))))
/*     */       {
/* 289 */         targetDict.setItem(key, this.cloner.cloneForNewDocument(entry.getValue()));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static int getNormalizedRotation(PDPage page)
/*     */   {
/* 297 */     int rotation = page.findRotation();
/* 298 */     while (rotation >= 360)
/*     */     {
/* 300 */       rotation -= 360;
/*     */     }
/* 302 */     if (rotation < 0)
/*     */     {
/* 304 */       rotation = 0;
/*     */     }
/* 306 */     switch (rotation)
/*     */     {
/*     */     case 90:
/*     */     case 180:
/*     */     case 270:
/* 311 */       return rotation;
/*     */     }
/* 313 */     return 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.LayerUtility
 * JD-Core Version:    0.6.2
 */