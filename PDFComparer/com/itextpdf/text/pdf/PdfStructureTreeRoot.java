/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.pdf.interfaces.IPdfStructureElement;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class PdfStructureTreeRoot extends PdfDictionary
/*     */   implements IPdfStructureElement
/*     */ {
/*  60 */   private HashMap<Integer, PdfObject> parentTree = new HashMap();
/*     */   private PdfIndirectReference reference;
/*  62 */   private PdfDictionary classMap = null;
/*  63 */   protected HashMap<PdfName, PdfObject> classes = null;
/*  64 */   private HashMap<Integer, PdfIndirectReference> numTree = null;
/*     */   private PdfWriter writer;
/*     */ 
/*     */   PdfStructureTreeRoot(PdfWriter writer)
/*     */   {
/*  73 */     super(PdfName.STRUCTTREEROOT);
/*  74 */     this.writer = writer;
/*  75 */     this.reference = writer.getPdfIndirectReference();
/*     */   }
/*     */ 
/*     */   private void createNumTree() throws IOException {
/*  79 */     if (this.numTree != null) return;
/*  80 */     this.numTree = new HashMap();
/*  81 */     for (Integer i : this.parentTree.keySet()) {
/*  82 */       PdfObject obj = (PdfObject)this.parentTree.get(i);
/*  83 */       if (obj.isArray()) {
/*  84 */         PdfArray ar = (PdfArray)obj;
/*  85 */         this.numTree.put(i, this.writer.addToBody(ar).getIndirectReference());
/*  86 */       } else if ((obj instanceof PdfIndirectReference)) {
/*  87 */         this.numTree.put(i, (PdfIndirectReference)obj);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mapRole(PdfName used, PdfName standard)
/*     */   {
/*  99 */     PdfDictionary rm = (PdfDictionary)get(PdfName.ROLEMAP);
/* 100 */     if (rm == null) {
/* 101 */       rm = new PdfDictionary();
/* 102 */       put(PdfName.ROLEMAP, rm);
/*     */     }
/* 104 */     rm.put(used, standard);
/*     */   }
/*     */ 
/*     */   public void mapClass(PdfName name, PdfObject object) {
/* 108 */     if (this.classMap == null) {
/* 109 */       this.classMap = new PdfDictionary();
/* 110 */       this.classes = new HashMap();
/*     */     }
/* 112 */     this.classes.put(name, object);
/*     */   }
/*     */ 
/*     */   public PdfObject getMappedClass(PdfName name) {
/* 116 */     if (this.classes == null)
/* 117 */       return null;
/* 118 */     return (PdfObject)this.classes.get(name);
/*     */   }
/*     */ 
/*     */   public PdfWriter getWriter()
/*     */   {
/* 126 */     return this.writer;
/*     */   }
/*     */ 
/*     */   public HashMap<Integer, PdfIndirectReference> getNumTree() throws IOException {
/* 130 */     if (this.numTree == null) createNumTree();
/* 131 */     return this.numTree;
/*     */   }
/*     */ 
/*     */   public PdfIndirectReference getReference()
/*     */   {
/* 140 */     return this.reference;
/*     */   }
/*     */ 
/*     */   void setPageMark(int page, PdfIndirectReference struc) {
/* 144 */     Integer i = Integer.valueOf(page);
/* 145 */     PdfArray ar = (PdfArray)this.parentTree.get(i);
/* 146 */     if (ar == null) {
/* 147 */       ar = new PdfArray();
/* 148 */       this.parentTree.put(i, ar);
/*     */     }
/* 150 */     ar.add(struc);
/*     */   }
/*     */ 
/*     */   void setAnnotationMark(int structParentIndex, PdfIndirectReference struc) {
/* 154 */     this.parentTree.put(Integer.valueOf(structParentIndex), struc);
/*     */   }
/*     */ 
/*     */   private void nodeProcess(PdfDictionary struc, PdfIndirectReference reference) throws IOException {
/* 158 */     PdfObject obj = struc.get(PdfName.K);
/* 159 */     if ((obj != null) && (obj.isArray())) {
/* 160 */       PdfArray ar = (PdfArray)obj;
/* 161 */       for (int k = 0; k < ar.size(); k++) {
/* 162 */         PdfDictionary dictionary = ar.getAsDict(k);
/* 163 */         if (dictionary != null)
/*     */         {
/* 165 */           if (PdfName.STRUCTELEM.equals(dictionary.get(PdfName.TYPE)))
/*     */           {
/* 167 */             if ((ar.getPdfObject(k) instanceof PdfStructureElement)) {
/* 168 */               PdfStructureElement e = (PdfStructureElement)dictionary;
/* 169 */               ar.set(k, e.getReference());
/* 170 */               nodeProcess(e, e.getReference());
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 174 */     if (reference != null)
/* 175 */       this.writer.addToBody(struc, reference);
/*     */   }
/*     */ 
/*     */   void buildTree() throws IOException {
/* 179 */     createNumTree();
/* 180 */     PdfDictionary dicTree = PdfNumberTree.writeTree(this.numTree, this.writer);
/* 181 */     if (dicTree != null)
/* 182 */       put(PdfName.PARENTTREE, this.writer.addToBody(dicTree).getIndirectReference());
/* 183 */     if ((this.classMap != null) && (!this.classes.isEmpty())) {
/* 184 */       for (Map.Entry entry : this.classes.entrySet()) {
/* 185 */         PdfObject value = (PdfObject)entry.getValue();
/* 186 */         if (value.isDictionary()) {
/* 187 */           this.classMap.put((PdfName)entry.getKey(), this.writer.addToBody(value).getIndirectReference());
/* 188 */         } else if (value.isArray()) {
/* 189 */           PdfArray newArray = new PdfArray();
/* 190 */           PdfArray array = (PdfArray)value;
/* 191 */           for (int i = 0; i < array.size(); i++) {
/* 192 */             if (array.getPdfObject(i).isDictionary())
/* 193 */               newArray.add(this.writer.addToBody(array.getAsDict(i)).getIndirectReference());
/*     */           }
/* 195 */           this.classMap.put((PdfName)entry.getKey(), newArray);
/*     */         }
/*     */       }
/* 198 */       put(PdfName.CLASSMAP, this.writer.addToBody(this.classMap).getIndirectReference());
/*     */     }
/* 200 */     nodeProcess(this, this.reference);
/*     */   }
/*     */ 
/*     */   public PdfObject getAttribute(PdfName name)
/*     */   {
/* 209 */     PdfDictionary attr = getAsDict(PdfName.A);
/* 210 */     if ((attr != null) && 
/* 211 */       (attr.contains(name))) {
/* 212 */       return attr.get(name);
/*     */     }
/* 214 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAttribute(PdfName name, PdfObject obj)
/*     */   {
/* 222 */     PdfDictionary attr = getAsDict(PdfName.A);
/* 223 */     if (attr == null) {
/* 224 */       attr = new PdfDictionary();
/* 225 */       put(PdfName.A, attr);
/*     */     }
/* 227 */     attr.put(name, obj);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfStructureTreeRoot
 * JD-Core Version:    0.6.2
 */