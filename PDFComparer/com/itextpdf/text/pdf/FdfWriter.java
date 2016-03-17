/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocWriter;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.log.Counter;
/*     */ import com.itextpdf.text.log.CounterFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class FdfWriter
/*     */ {
/*  65 */   private static final byte[] HEADER_FDF = DocWriter.getISOBytes("%FDF-1.4\n%âãÏÓ\n");
/*  66 */   HashMap<String, Object> fields = new HashMap();
/*  67 */   Wrt wrt = null;
/*     */   private String file;
/*     */   private String statusMessage;
/* 438 */   protected Counter COUNTER = CounterFactory.getCounter(FdfWriter.class);
/*     */ 
/*     */   public FdfWriter()
/*     */   {
/*     */   }
/*     */ 
/*     */   public FdfWriter(OutputStream os)
/*     */     throws IOException
/*     */   {
/*  78 */     this.wrt = new Wrt(os, this);
/*     */   }
/*     */ 
/*     */   public void writeTo(OutputStream os)
/*     */     throws IOException
/*     */   {
/*  86 */     if (this.wrt == null)
/*  87 */       this.wrt = new Wrt(os, this);
/*  88 */     this.wrt.write();
/*     */   }
/*     */ 
/*     */   public void write() throws IOException {
/*  92 */     this.wrt.write();
/*     */   }
/*     */ 
/*     */   public String getStatusMessage() {
/*  96 */     return this.statusMessage;
/*     */   }
/*     */ 
/*     */   public void setStatusMessage(String statusMessage) {
/* 100 */     this.statusMessage = statusMessage;
/*     */   }
/*     */ 
/*     */   boolean setField(String field, PdfObject value)
/*     */   {
/* 105 */     HashMap map = this.fields;
/* 106 */     StringTokenizer tk = new StringTokenizer(field, ".");
/* 107 */     if (!tk.hasMoreTokens())
/* 108 */       return false;
/*     */     while (true) {
/* 110 */       String s = tk.nextToken();
/* 111 */       Object obj = map.get(s);
/* 112 */       if (tk.hasMoreTokens()) {
/* 113 */         if (obj == null) {
/* 114 */           obj = new HashMap();
/* 115 */           map.put(s, obj);
/* 116 */           map = (HashMap)obj;
/*     */         }
/* 119 */         else if ((obj instanceof HashMap)) {
/* 120 */           map = (HashMap)obj;
/*     */         } else {
/* 122 */           return false;
/*     */         }
/*     */       } else {
/* 125 */         if (!(obj instanceof HashMap)) {
/* 126 */           map.put(s, value);
/* 127 */           return true;
/*     */         }
/*     */ 
/* 130 */         return false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void iterateFields(HashMap<String, Object> values, HashMap<String, Object> map, String name)
/*     */   {
/* 137 */     for (Map.Entry entry : map.entrySet()) {
/* 138 */       String s = (String)entry.getKey();
/* 139 */       Object obj = entry.getValue();
/* 140 */       if ((obj instanceof HashMap))
/* 141 */         iterateFields(values, (HashMap)obj, name + "." + s);
/*     */       else
/* 143 */         values.put((name + "." + s).substring(1), obj);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean removeField(String field)
/*     */   {
/* 154 */     HashMap map = this.fields;
/* 155 */     StringTokenizer tk = new StringTokenizer(field, ".");
/* 156 */     if (!tk.hasMoreTokens())
/* 157 */       return false;
/* 158 */     ArrayList hist = new ArrayList();
/*     */     while (true) {
/* 160 */       String s = tk.nextToken();
/* 161 */       Object obj = map.get(s);
/* 162 */       if (obj == null)
/* 163 */         return false;
/* 164 */       hist.add(map);
/* 165 */       hist.add(s);
/* 166 */       if (tk.hasMoreTokens()) {
/* 167 */         if ((obj instanceof HashMap))
/* 168 */           map = (HashMap)obj;
/*     */         else
/* 170 */           return false;
/*     */       }
/*     */       else {
/* 173 */         if (!(obj instanceof HashMap)) break;
/* 174 */         return false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 179 */     for (int k = hist.size() - 2; k >= 0; k -= 2) {
/* 180 */       map = (HashMap)hist.get(k);
/* 181 */       String s = (String)hist.get(k + 1);
/* 182 */       map.remove(s);
/* 183 */       if (!map.isEmpty())
/*     */         break;
/*     */     }
/* 186 */     return true;
/*     */   }
/*     */ 
/*     */   public HashMap<String, Object> getFields()
/*     */   {
/* 194 */     HashMap values = new HashMap();
/* 195 */     iterateFields(values, this.fields, "");
/* 196 */     return values;
/*     */   }
/*     */ 
/*     */   public String getField(String field)
/*     */   {
/* 205 */     HashMap map = this.fields;
/* 206 */     StringTokenizer tk = new StringTokenizer(field, ".");
/* 207 */     if (!tk.hasMoreTokens())
/* 208 */       return null;
/*     */     while (true) {
/* 210 */       String s = tk.nextToken();
/* 211 */       Object obj = map.get(s);
/* 212 */       if (obj == null)
/* 213 */         return null;
/* 214 */       if (tk.hasMoreTokens()) {
/* 215 */         if ((obj instanceof HashMap))
/* 216 */           map = (HashMap)obj;
/*     */         else
/* 218 */           return null;
/*     */       }
/*     */       else {
/* 221 */         if ((obj instanceof HashMap)) {
/* 222 */           return null;
/*     */         }
/* 224 */         if (((PdfObject)obj).isString()) {
/* 225 */           return ((PdfString)obj).toUnicodeString();
/*     */         }
/* 227 */         return PdfName.decodeName(obj.toString());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean setFieldAsName(String field, String value)
/*     */   {
/* 241 */     return setField(field, new PdfName(value));
/*     */   }
/*     */ 
/*     */   public boolean setFieldAsString(String field, String value)
/*     */   {
/* 252 */     return setField(field, new PdfString(value, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */   public boolean setFieldAsAction(String field, PdfAction action)
/*     */   {
/* 268 */     return setField(field, action);
/*     */   }
/*     */ 
/*     */   public boolean setFieldAsTemplate(String field, PdfTemplate template) {
/*     */     try {
/* 273 */       PdfDictionary d = new PdfDictionary();
/* 274 */       if ((template instanceof PdfImportedPage)) {
/* 275 */         d.put(PdfName.N, template.getIndirectReference());
/*     */       } else {
/* 277 */         PdfStream str = template.getFormXObject(0);
/* 278 */         PdfIndirectReference ref = this.wrt.addToBody(str).getIndirectReference();
/* 279 */         d.put(PdfName.N, ref);
/*     */       }
/* 281 */       return setField(field, d);
/*     */     } catch (Exception e) {
/* 283 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean setFieldAsImage(String field, Image image) {
/*     */     try {
/* 289 */       if (Float.isNaN(image.getAbsoluteX()))
/* 290 */         image.setAbsolutePosition(0.0F, image.getAbsoluteY());
/* 291 */       if (Float.isNaN(image.getAbsoluteY()))
/* 292 */         image.setAbsolutePosition(image.getAbsoluteY(), 0.0F);
/* 293 */       PdfTemplate tmpl = PdfTemplate.createTemplate(this.wrt, image.getWidth(), image.getHeight());
/* 294 */       tmpl.addImage(image);
/* 295 */       PdfStream str = tmpl.getFormXObject(0);
/* 296 */       PdfIndirectReference ref = this.wrt.addToBody(str).getIndirectReference();
/* 297 */       PdfDictionary d = new PdfDictionary();
/* 298 */       d.put(PdfName.N, ref);
/* 299 */       return setField(field, d);
/*     */     } catch (Exception de) {
/* 301 */       throw new ExceptionConverter(de);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean setFieldAsJavascript(String field, PdfName jsTrigName, String js) {
/* 306 */     PdfAnnotation dict = new PdfAnnotation(this.wrt, null);
/* 307 */     PdfAction javascript = PdfAction.javaScript(js, this.wrt);
/* 308 */     dict.put(jsTrigName, javascript);
/* 309 */     return setField(field, dict);
/*     */   }
/*     */ 
/*     */   public PdfImportedPage getImportedPage(PdfReader reader, int pageNumber) {
/* 313 */     return this.wrt.getImportedPage(reader, pageNumber);
/*     */   }
/*     */ 
/*     */   public PdfTemplate createTemplate(float width, float height) {
/* 317 */     return PdfTemplate.createTemplate(this.wrt, width, height);
/*     */   }
/*     */ 
/*     */   public void setFields(FdfReader fdf)
/*     */   {
/* 324 */     HashMap map = fdf.getFields();
/* 325 */     for (Map.Entry entry : map.entrySet()) {
/* 326 */       String key = (String)entry.getKey();
/* 327 */       PdfDictionary dic = (PdfDictionary)entry.getValue();
/* 328 */       PdfObject v = dic.get(PdfName.V);
/* 329 */       if (v != null) {
/* 330 */         setField(key, v);
/*     */       }
/* 332 */       v = dic.get(PdfName.A);
/* 333 */       if (v != null)
/* 334 */         setField(key, v);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setFields(PdfReader pdf)
/*     */   {
/* 343 */     setFields(pdf.getAcroFields());
/*     */   }
/*     */ 
/*     */   public void setFields(AcroFields af)
/*     */   {
/* 350 */     for (Map.Entry entry : af.getFields().entrySet()) {
/* 351 */       String fn = (String)entry.getKey();
/* 352 */       AcroFields.Item item = (AcroFields.Item)entry.getValue();
/* 353 */       PdfDictionary dic = item.getMerged(0);
/* 354 */       PdfObject v = PdfReader.getPdfObjectRelease(dic.get(PdfName.V));
/* 355 */       if (v != null)
/*     */       {
/* 357 */         PdfObject ft = PdfReader.getPdfObjectRelease(dic.get(PdfName.FT));
/* 358 */         if ((ft != null) && (!PdfName.SIG.equals(ft)))
/*     */         {
/* 360 */           setField(fn, v);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getFile()
/*     */   {
/* 368 */     return this.file;
/*     */   }
/*     */ 
/*     */   public void setFile(String file)
/*     */   {
/* 376 */     this.file = file;
/*     */   }
/*     */ 
/*     */   protected Counter getCounter()
/*     */   {
/* 440 */     return this.COUNTER;
/*     */   }
/*     */ 
/*     */   static class Wrt extends PdfWriter
/*     */   {
/*     */     private FdfWriter fdf;
/*     */ 
/*     */     Wrt(OutputStream os, FdfWriter fdf)
/*     */       throws IOException
/*     */     {
/* 383 */       super(os);
/* 384 */       this.fdf = fdf;
/* 385 */       this.os.write(FdfWriter.HEADER_FDF);
/* 386 */       this.body = new PdfWriter.PdfBody(this);
/*     */     }
/*     */ 
/*     */     void write() throws IOException {
/* 390 */       for (PdfReaderInstance element : this.readerInstances.values()) {
/* 391 */         this.currentPdfReaderInstance = element;
/* 392 */         this.currentPdfReaderInstance.writeAllPages();
/*     */       }
/*     */ 
/* 395 */       PdfDictionary dic = new PdfDictionary();
/* 396 */       dic.put(PdfName.FIELDS, calculate(this.fdf.fields));
/* 397 */       if (this.fdf.file != null)
/* 398 */         dic.put(PdfName.F, new PdfString(this.fdf.file, "UnicodeBig"));
/* 399 */       if ((this.fdf.statusMessage != null) && (this.fdf.statusMessage.trim().length() != 0))
/* 400 */         dic.put(PdfName.STATUS, new PdfString(this.fdf.statusMessage));
/* 401 */       PdfDictionary fd = new PdfDictionary();
/* 402 */       fd.put(PdfName.FDF, dic);
/* 403 */       PdfIndirectReference ref = addToBody(fd).getIndirectReference();
/* 404 */       this.os.write(getISOBytes("trailer\n"));
/* 405 */       PdfDictionary trailer = new PdfDictionary();
/* 406 */       trailer.put(PdfName.ROOT, ref);
/* 407 */       trailer.toPdf(null, this.os);
/* 408 */       this.os.write(getISOBytes("\n%%EOF\n"));
/* 409 */       this.os.close();
/*     */     }
/*     */ 
/*     */     PdfArray calculate(HashMap<String, Object> map)
/*     */       throws IOException
/*     */     {
/* 415 */       PdfArray ar = new PdfArray();
/* 416 */       for (Map.Entry entry : map.entrySet()) {
/* 417 */         String key = (String)entry.getKey();
/* 418 */         Object v = entry.getValue();
/* 419 */         PdfDictionary dic = new PdfDictionary();
/* 420 */         dic.put(PdfName.T, new PdfString(key, "UnicodeBig"));
/* 421 */         if ((v instanceof HashMap))
/* 422 */           dic.put(PdfName.KIDS, calculate((HashMap)v));
/* 423 */         else if ((v instanceof PdfAction))
/* 424 */           dic.put(PdfName.A, (PdfAction)v);
/* 425 */         else if ((v instanceof PdfAnnotation))
/* 426 */           dic.put(PdfName.AA, (PdfAnnotation)v);
/* 427 */         else if (((v instanceof PdfDictionary)) && (((PdfDictionary)v).size() == 1) && (((PdfDictionary)v).contains(PdfName.N)))
/* 428 */           dic.put(PdfName.AP, (PdfDictionary)v);
/*     */         else {
/* 430 */           dic.put(PdfName.V, (PdfObject)v);
/*     */         }
/* 432 */         ar.add(dic);
/*     */       }
/* 434 */       return ar;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.FdfWriter
 * JD-Core Version:    0.6.2
 */