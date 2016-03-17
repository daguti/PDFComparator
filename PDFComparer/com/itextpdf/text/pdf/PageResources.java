/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ class PageResources
/*     */ {
/*  52 */   protected PdfDictionary fontDictionary = new PdfDictionary();
/*  53 */   protected PdfDictionary xObjectDictionary = new PdfDictionary();
/*  54 */   protected PdfDictionary colorDictionary = new PdfDictionary();
/*  55 */   protected PdfDictionary patternDictionary = new PdfDictionary();
/*  56 */   protected PdfDictionary shadingDictionary = new PdfDictionary();
/*  57 */   protected PdfDictionary extGStateDictionary = new PdfDictionary();
/*  58 */   protected PdfDictionary propertyDictionary = new PdfDictionary();
/*     */   protected HashSet<PdfName> forbiddenNames;
/*     */   protected PdfDictionary originalResources;
/*  61 */   protected int[] namePtr = { 0 };
/*     */   protected HashMap<PdfName, PdfName> usedNames;
/*     */ 
/*     */   void setOriginalResources(PdfDictionary resources, int[] newNamePtr)
/*     */   {
/*  68 */     if (newNamePtr != null)
/*  69 */       this.namePtr = newNamePtr;
/*  70 */     this.forbiddenNames = new HashSet();
/*  71 */     this.usedNames = new HashMap();
/*  72 */     if (resources == null)
/*  73 */       return;
/*  74 */     this.originalResources = new PdfDictionary();
/*  75 */     this.originalResources.merge(resources);
/*  76 */     for (Object element : resources.getKeys()) {
/*  77 */       PdfName key = (PdfName)element;
/*  78 */       PdfObject sub = PdfReader.getPdfObject(resources.get(key));
/*  79 */       if ((sub != null) && (sub.isDictionary())) {
/*  80 */         PdfDictionary dic = (PdfDictionary)sub;
/*  81 */         for (PdfName element2 : dic.getKeys()) {
/*  82 */           this.forbiddenNames.add(element2);
/*     */         }
/*  84 */         PdfDictionary dic2 = new PdfDictionary();
/*  85 */         dic2.merge(dic);
/*  86 */         this.originalResources.put(key, dic2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   PdfName translateName(PdfName name) {
/*  92 */     PdfName translated = name;
/*  93 */     if (this.forbiddenNames != null) {
/*  94 */       translated = (PdfName)this.usedNames.get(name);
/*  95 */       if (translated == null)
/*     */       {
/*     */         while (true)
/*     */         {
/*     */           int tmp46_45 = 0;
/*     */           int[] tmp46_42 = this.namePtr;
/*     */           int tmp48_47 = tmp46_42[tmp46_45]; tmp46_42[tmp46_45] = (tmp48_47 + 1); translated = new PdfName("Xi" + tmp48_47);
/*  98 */           if (!this.forbiddenNames.contains(translated))
/*  99 */             break;
/*     */         }
/* 101 */         this.usedNames.put(name, translated);
/*     */       }
/*     */     }
/* 104 */     return translated;
/*     */   }
/*     */ 
/*     */   PdfName addFont(PdfName name, PdfIndirectReference reference) {
/* 108 */     name = translateName(name);
/* 109 */     this.fontDictionary.put(name, reference);
/* 110 */     return name;
/*     */   }
/*     */ 
/*     */   PdfName addXObject(PdfName name, PdfIndirectReference reference) {
/* 114 */     name = translateName(name);
/* 115 */     this.xObjectDictionary.put(name, reference);
/* 116 */     return name;
/*     */   }
/*     */ 
/*     */   PdfName addColor(PdfName name, PdfIndirectReference reference) {
/* 120 */     name = translateName(name);
/* 121 */     this.colorDictionary.put(name, reference);
/* 122 */     return name;
/*     */   }
/*     */ 
/*     */   void addDefaultColor(PdfName name, PdfObject obj) {
/* 126 */     if ((obj == null) || (obj.isNull()))
/* 127 */       this.colorDictionary.remove(name);
/*     */     else
/* 129 */       this.colorDictionary.put(name, obj);
/*     */   }
/*     */ 
/*     */   void addDefaultColor(PdfDictionary dic) {
/* 133 */     this.colorDictionary.merge(dic);
/*     */   }
/*     */ 
/*     */   void addDefaultColorDiff(PdfDictionary dic) {
/* 137 */     this.colorDictionary.mergeDifferent(dic);
/*     */   }
/*     */ 
/*     */   PdfName addShading(PdfName name, PdfIndirectReference reference) {
/* 141 */     name = translateName(name);
/* 142 */     this.shadingDictionary.put(name, reference);
/* 143 */     return name;
/*     */   }
/*     */ 
/*     */   PdfName addPattern(PdfName name, PdfIndirectReference reference) {
/* 147 */     name = translateName(name);
/* 148 */     this.patternDictionary.put(name, reference);
/* 149 */     return name;
/*     */   }
/*     */ 
/*     */   PdfName addExtGState(PdfName name, PdfIndirectReference reference) {
/* 153 */     name = translateName(name);
/* 154 */     this.extGStateDictionary.put(name, reference);
/* 155 */     return name;
/*     */   }
/*     */ 
/*     */   PdfName addProperty(PdfName name, PdfIndirectReference reference) {
/* 159 */     name = translateName(name);
/* 160 */     this.propertyDictionary.put(name, reference);
/* 161 */     return name;
/*     */   }
/*     */ 
/*     */   PdfDictionary getResources() {
/* 165 */     PdfResources resources = new PdfResources();
/* 166 */     if (this.originalResources != null)
/* 167 */       resources.putAll(this.originalResources);
/* 168 */     resources.add(PdfName.FONT, this.fontDictionary);
/* 169 */     resources.add(PdfName.XOBJECT, this.xObjectDictionary);
/* 170 */     resources.add(PdfName.COLORSPACE, this.colorDictionary);
/* 171 */     resources.add(PdfName.PATTERN, this.patternDictionary);
/* 172 */     resources.add(PdfName.SHADING, this.shadingDictionary);
/* 173 */     resources.add(PdfName.EXTGSTATE, this.extGStateDictionary);
/* 174 */     resources.add(PdfName.PROPERTIES, this.propertyDictionary);
/* 175 */     return resources;
/*     */   }
/*     */ 
/*     */   boolean hasResources() {
/* 179 */     return (this.fontDictionary.size() > 0) || (this.xObjectDictionary.size() > 0) || (this.colorDictionary.size() > 0) || (this.patternDictionary.size() > 0) || (this.shadingDictionary.size() > 0) || (this.extGStateDictionary.size() > 0) || (this.propertyDictionary.size() > 0);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PageResources
 * JD-Core Version:    0.6.2
 */