/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.log.Counter;
/*     */ import com.itextpdf.text.log.CounterFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class FdfReader extends PdfReader
/*     */ {
/*     */   HashMap<String, PdfDictionary> fields;
/*     */   String fileSpec;
/*     */   PdfName encoding;
/*  95 */   protected static Counter COUNTER = CounterFactory.getCounter(FdfReader.class);
/*     */ 
/*     */   public FdfReader(String filename)
/*     */     throws IOException
/*     */   {
/*  67 */     super(filename);
/*     */   }
/*     */ 
/*     */   public FdfReader(byte[] pdfIn)
/*     */     throws IOException
/*     */   {
/*  75 */     super(pdfIn);
/*     */   }
/*     */ 
/*     */   public FdfReader(URL url)
/*     */     throws IOException
/*     */   {
/*  83 */     super(url);
/*     */   }
/*     */ 
/*     */   public FdfReader(InputStream is)
/*     */     throws IOException
/*     */   {
/*  92 */     super(is);
/*     */   }
/*     */ 
/*     */   protected Counter getCounter()
/*     */   {
/*  97 */     return COUNTER;
/*     */   }
/*     */ 
/*     */   protected void readPdf() throws IOException
/*     */   {
/* 102 */     this.fields = new HashMap();
/* 103 */     this.tokens.checkFdfHeader();
/* 104 */     rebuildXref();
/* 105 */     readDocObj();
/* 106 */     readFields();
/*     */   }
/*     */ 
/*     */   protected void kidNode(PdfDictionary merged, String name) {
/* 110 */     PdfArray kids = merged.getAsArray(PdfName.KIDS);
/* 111 */     if ((kids == null) || (kids.isEmpty())) {
/* 112 */       if (name.length() > 0)
/* 113 */         name = name.substring(1);
/* 114 */       this.fields.put(name, merged);
/*     */     }
/*     */     else {
/* 117 */       merged.remove(PdfName.KIDS);
/* 118 */       for (int k = 0; k < kids.size(); k++) {
/* 119 */         PdfDictionary dic = new PdfDictionary();
/* 120 */         dic.merge(merged);
/* 121 */         PdfDictionary newDic = kids.getAsDict(k);
/* 122 */         PdfString t = newDic.getAsString(PdfName.T);
/* 123 */         String newName = name;
/* 124 */         if (t != null)
/* 125 */           newName = newName + "." + t.toUnicodeString();
/* 126 */         dic.merge(newDic);
/* 127 */         dic.remove(PdfName.T);
/* 128 */         kidNode(dic, newName);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void readFields() {
/* 134 */     this.catalog = this.trailer.getAsDict(PdfName.ROOT);
/* 135 */     PdfDictionary fdf = this.catalog.getAsDict(PdfName.FDF);
/* 136 */     if (fdf == null)
/* 137 */       return;
/* 138 */     PdfString fs = fdf.getAsString(PdfName.F);
/* 139 */     if (fs != null)
/* 140 */       this.fileSpec = fs.toUnicodeString();
/* 141 */     PdfArray fld = fdf.getAsArray(PdfName.FIELDS);
/* 142 */     if (fld == null)
/* 143 */       return;
/* 144 */     this.encoding = fdf.getAsName(PdfName.ENCODING);
/* 145 */     PdfDictionary merged = new PdfDictionary();
/* 146 */     merged.put(PdfName.KIDS, fld);
/* 147 */     kidNode(merged, "");
/*     */   }
/*     */ 
/*     */   public HashMap<String, PdfDictionary> getFields()
/*     */   {
/* 156 */     return this.fields;
/*     */   }
/*     */ 
/*     */   public PdfDictionary getField(String name)
/*     */   {
/* 164 */     return (PdfDictionary)this.fields.get(name);
/*     */   }
/*     */ 
/*     */   public byte[] getAttachedFile(String name)
/*     */     throws IOException
/*     */   {
/* 175 */     PdfDictionary field = (PdfDictionary)this.fields.get(name);
/* 176 */     if (field != null) {
/* 177 */       PdfIndirectReference ir = (PRIndirectReference)field.get(PdfName.V);
/* 178 */       PdfDictionary filespec = (PdfDictionary)getPdfObject(ir.getNumber());
/* 179 */       PdfDictionary ef = filespec.getAsDict(PdfName.EF);
/* 180 */       ir = (PRIndirectReference)ef.get(PdfName.F);
/* 181 */       PRStream stream = (PRStream)getPdfObject(ir.getNumber());
/* 182 */       return getStreamBytes(stream);
/*     */     }
/* 184 */     return new byte[0];
/*     */   }
/*     */ 
/*     */   public String getFieldValue(String name)
/*     */   {
/* 194 */     PdfDictionary field = (PdfDictionary)this.fields.get(name);
/* 195 */     if (field == null)
/* 196 */       return null;
/* 197 */     PdfObject v = getPdfObject(field.get(PdfName.V));
/* 198 */     if (v == null)
/* 199 */       return null;
/* 200 */     if (v.isName())
/* 201 */       return PdfName.decodeName(((PdfName)v).toString());
/* 202 */     if (v.isString()) {
/* 203 */       PdfString vs = (PdfString)v;
/* 204 */       if ((this.encoding == null) || (vs.getEncoding() != null))
/* 205 */         return vs.toUnicodeString();
/* 206 */       byte[] b = vs.getBytes();
/* 207 */       if ((b.length >= 2) && (b[0] == -2) && (b[1] == -1))
/* 208 */         return vs.toUnicodeString();
/*     */       try {
/* 210 */         if (this.encoding.equals(PdfName.SHIFT_JIS))
/* 211 */           return new String(b, "SJIS");
/* 212 */         if (this.encoding.equals(PdfName.UHC))
/* 213 */           return new String(b, "MS949");
/* 214 */         if (this.encoding.equals(PdfName.GBK))
/* 215 */           return new String(b, "GBK");
/* 216 */         if (this.encoding.equals(PdfName.BIGFIVE))
/* 217 */           return new String(b, "Big5");
/* 218 */         if (this.encoding.equals(PdfName.UTF_8))
/* 219 */           return new String(b, "UTF8");
/*     */       }
/*     */       catch (Exception e) {
/*     */       }
/* 223 */       return vs.toUnicodeString();
/*     */     }
/* 225 */     return null;
/*     */   }
/*     */ 
/*     */   public String getFileSpec()
/*     */   {
/* 232 */     return this.fileSpec;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.FdfReader
 * JD-Core Version:    0.6.2
 */