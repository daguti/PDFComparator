/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class PRAcroForm extends PdfDictionary
/*     */ {
/*     */   ArrayList<FieldInformation> fields;
/*     */   ArrayList<PdfDictionary> stack;
/*     */   HashMap<String, FieldInformation> fieldByName;
/*     */   PdfReader reader;
/*     */ 
/*     */   public PRAcroForm(PdfReader reader)
/*     */   {
/* 110 */     this.reader = reader;
/* 111 */     this.fields = new ArrayList();
/* 112 */     this.fieldByName = new HashMap();
/* 113 */     this.stack = new ArrayList();
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 121 */     return this.fields.size();
/*     */   }
/*     */ 
/*     */   public ArrayList<FieldInformation> getFields() {
/* 125 */     return this.fields;
/*     */   }
/*     */ 
/*     */   public FieldInformation getField(String name) {
/* 129 */     return (FieldInformation)this.fieldByName.get(name);
/*     */   }
/*     */ 
/*     */   public PRIndirectReference getRefByName(String name)
/*     */   {
/* 138 */     FieldInformation fi = (FieldInformation)this.fieldByName.get(name);
/* 139 */     if (fi == null) return null;
/* 140 */     return fi.getRef();
/*     */   }
/*     */ 
/*     */   public void readAcroForm(PdfDictionary root)
/*     */   {
/* 147 */     if (root == null)
/* 148 */       return;
/* 149 */     this.hashMap = root.hashMap;
/* 150 */     pushAttrib(root);
/* 151 */     PdfArray fieldlist = (PdfArray)PdfReader.getPdfObjectRelease(root.get(PdfName.FIELDS));
/* 152 */     if (fieldlist != null)
/* 153 */       iterateFields(fieldlist, null, null);
/*     */   }
/*     */ 
/*     */   protected void iterateFields(PdfArray fieldlist, PRIndirectReference fieldDict, String parentPath)
/*     */   {
/* 164 */     for (Iterator it = fieldlist.listIterator(); it.hasNext(); ) {
/* 165 */       PRIndirectReference ref = (PRIndirectReference)it.next();
/* 166 */       PdfDictionary dict = (PdfDictionary)PdfReader.getPdfObjectRelease(ref);
/*     */ 
/* 169 */       PRIndirectReference myFieldDict = fieldDict;
/* 170 */       String fullPath = parentPath;
/* 171 */       PdfString tField = (PdfString)dict.get(PdfName.T);
/* 172 */       boolean isFieldDict = tField != null;
/*     */ 
/* 174 */       if (isFieldDict) {
/* 175 */         myFieldDict = ref;
/* 176 */         if (parentPath == null) {
/* 177 */           fullPath = tField.toString();
/*     */         }
/*     */         else {
/* 180 */           fullPath = parentPath + '.' + tField.toString();
/*     */         }
/*     */       }
/*     */ 
/* 184 */       PdfArray kids = (PdfArray)dict.get(PdfName.KIDS);
/* 185 */       if (kids != null) {
/* 186 */         pushAttrib(dict);
/* 187 */         iterateFields(kids, myFieldDict, fullPath);
/* 188 */         this.stack.remove(this.stack.size() - 1);
/*     */       }
/* 191 */       else if (myFieldDict != null) {
/* 192 */         PdfDictionary mergedDict = (PdfDictionary)this.stack.get(this.stack.size() - 1);
/* 193 */         if (isFieldDict) {
/* 194 */           mergedDict = mergeAttrib(mergedDict, dict);
/*     */         }
/* 196 */         mergedDict.put(PdfName.T, new PdfString(fullPath));
/* 197 */         FieldInformation fi = new FieldInformation(fullPath, mergedDict, myFieldDict);
/* 198 */         this.fields.add(fi);
/* 199 */         this.fieldByName.put(fullPath, fi);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected PdfDictionary mergeAttrib(PdfDictionary parent, PdfDictionary child)
/*     */   {
/* 211 */     PdfDictionary targ = new PdfDictionary();
/* 212 */     if (parent != null) targ.putAll(parent);
/*     */ 
/* 214 */     for (Object element : child.getKeys()) {
/* 215 */       PdfName key = (PdfName)element;
/* 216 */       if ((key.equals(PdfName.DR)) || (key.equals(PdfName.DA)) || (key.equals(PdfName.Q)) || (key.equals(PdfName.FF)) || (key.equals(PdfName.DV)) || (key.equals(PdfName.V)) || (key.equals(PdfName.FT)) || (key.equals(PdfName.NM)) || (key.equals(PdfName.F)))
/*     */       {
/* 221 */         targ.put(key, child.get(key));
/*     */       }
/*     */     }
/* 224 */     return targ;
/*     */   }
/*     */ 
/*     */   protected void pushAttrib(PdfDictionary dict)
/*     */   {
/* 230 */     PdfDictionary dic = null;
/* 231 */     if (!this.stack.isEmpty()) {
/* 232 */       dic = (PdfDictionary)this.stack.get(this.stack.size() - 1);
/*     */     }
/* 234 */     dic = mergeAttrib(dic, dict);
/* 235 */     this.stack.add(dic);
/*     */   }
/*     */ 
/*     */   public static class FieldInformation
/*     */   {
/*     */     String fieldName;
/*     */     PdfDictionary info;
/*     */     PRIndirectReference ref;
/*     */ 
/*     */     FieldInformation(String fieldName, PdfDictionary info, PRIndirectReference ref)
/*     */     {
/*  68 */       this.fieldName = fieldName;
/*  69 */       this.info = info;
/*  70 */       this.ref = ref;
/*     */     }
/*     */ 
/*     */     public String getWidgetName()
/*     */     {
/*  78 */       PdfObject name = this.info.get(PdfName.NM);
/*  79 */       if (name != null)
/*  80 */         return name.toString();
/*  81 */       return null;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/*  89 */       return this.fieldName;
/*     */     }
/*     */ 
/*     */     public PdfDictionary getInfo() {
/*  93 */       return this.info;
/*     */     }
/*     */ 
/*     */     public PRIndirectReference getRef() {
/*  97 */       return this.ref;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PRAcroForm
 * JD-Core Version:    0.6.2
 */