/*     */ package org.apache.pdfbox.pdmodel.interactive.form;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.fdf.FDFCatalog;
/*     */ import org.apache.pdfbox.pdmodel.fdf.FDFDictionary;
/*     */ import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
/*     */ import org.apache.pdfbox.pdmodel.fdf.FDFField;
/*     */ 
/*     */ public class PDAcroForm
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary acroForm;
/*     */   private PDDocument document;
/*     */   private Map fieldCache;
/*     */ 
/*     */   public PDAcroForm(PDDocument doc)
/*     */   {
/*  63 */     this.document = doc;
/*  64 */     this.acroForm = new COSDictionary();
/*  65 */     COSArray fields = new COSArray();
/*  66 */     this.acroForm.setItem(COSName.getPDFName("Fields"), fields);
/*     */   }
/*     */ 
/*     */   public PDAcroForm(PDDocument doc, COSDictionary form)
/*     */   {
/*  77 */     this.document = doc;
/*  78 */     this.acroForm = form;
/*     */   }
/*     */ 
/*     */   public PDDocument getDocument()
/*     */   {
/*  88 */     return this.document;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/*  98 */     return this.acroForm;
/*     */   }
/*     */ 
/*     */   public void importFDF(FDFDocument fdf)
/*     */     throws IOException
/*     */   {
/* 111 */     List fields = fdf.getCatalog().getFDF().getFields();
/* 112 */     if (fields != null)
/*     */     {
/* 114 */       for (int i = 0; i < fields.size(); i++)
/*     */       {
/* 116 */         FDFField fdfField = (FDFField)fields.get(i);
/* 117 */         PDField docField = getField(fdfField.getPartialFieldName());
/* 118 */         if (docField != null)
/*     */         {
/* 120 */           docField.importFDF(fdfField);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public FDFDocument exportFDF()
/*     */     throws IOException
/*     */   {
/* 134 */     FDFDocument fdf = new FDFDocument();
/* 135 */     FDFCatalog catalog = fdf.getCatalog();
/* 136 */     FDFDictionary fdfDict = new FDFDictionary();
/* 137 */     catalog.setFDF(fdfDict);
/*     */ 
/* 139 */     List fdfFields = new ArrayList();
/* 140 */     List fields = getFields();
/* 141 */     Iterator fieldIter = fields.iterator();
/* 142 */     while (fieldIter.hasNext())
/*     */     {
/* 144 */       PDField docField = (PDField)fieldIter.next();
/* 145 */       addFieldAndChildren(docField, fdfFields);
/*     */     }
/* 147 */     fdfDict.setID(this.document.getDocument().getDocumentID());
/* 148 */     if (fdfFields.size() > 0)
/*     */     {
/* 150 */       fdfDict.setFields(fdfFields);
/*     */     }
/* 152 */     return fdf;
/*     */   }
/*     */ 
/*     */   private void addFieldAndChildren(PDField docField, List fdfFields) throws IOException
/*     */   {
/* 157 */     Object fieldValue = docField.getValue();
/* 158 */     FDFField fdfField = new FDFField();
/* 159 */     fdfField.setPartialFieldName(docField.getPartialName());
/* 160 */     fdfField.setValue(fieldValue);
/* 161 */     List kids = docField.getKids();
/* 162 */     List childFDFFields = new ArrayList();
/* 163 */     if (kids != null)
/*     */     {
/* 166 */       for (int i = 0; i < kids.size(); i++)
/*     */       {
/* 168 */         addFieldAndChildren((PDField)kids.get(i), childFDFFields);
/*     */       }
/* 170 */       if (childFDFFields.size() > 0)
/*     */       {
/* 172 */         fdfField.setKids(childFDFFields);
/*     */       }
/*     */     }
/* 175 */     if ((fieldValue != null) || (childFDFFields.size() > 0))
/*     */     {
/* 177 */       fdfFields.add(fdfField);
/*     */     }
/*     */   }
/*     */ 
/*     */   public List getFields()
/*     */     throws IOException
/*     */   {
/* 190 */     List retval = null;
/* 191 */     COSArray fields = (COSArray)this.acroForm.getDictionaryObject(COSName.getPDFName("Fields"));
/*     */ 
/* 195 */     if (fields != null)
/*     */     {
/* 197 */       List actuals = new ArrayList();
/* 198 */       for (int i = 0; i < fields.size(); i++)
/*     */       {
/* 200 */         COSDictionary element = (COSDictionary)fields.getObject(i);
/* 201 */         if (element != null)
/*     */         {
/* 203 */           PDField field = PDFieldFactory.createField(this, element);
/* 204 */           if (field != null)
/*     */           {
/* 206 */             actuals.add(field);
/*     */           }
/*     */         }
/*     */       }
/* 210 */       retval = new COSArrayList(actuals, fields);
/*     */     }
/* 212 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFields(List fields)
/*     */   {
/* 222 */     this.acroForm.setItem("Fields", COSArrayList.converterToCOSArray(fields));
/*     */   }
/*     */ 
/*     */   public void setCacheFields(boolean cache)
/*     */     throws IOException
/*     */   {
/* 236 */     if (cache)
/*     */     {
/* 238 */       this.fieldCache = new HashMap();
/* 239 */       List fields = getFields();
/* 240 */       Iterator fieldIter = fields.iterator();
/* 241 */       while (fieldIter.hasNext())
/*     */       {
/* 243 */         PDField next = (PDField)fieldIter.next();
/* 244 */         this.fieldCache.put(next.getFullyQualifiedName(), next);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 249 */       this.fieldCache = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isCachingFields()
/*     */   {
/* 260 */     return this.fieldCache != null;
/*     */   }
/*     */ 
/*     */   public PDField getField(String name)
/*     */     throws IOException
/*     */   {
/* 274 */     PDField retval = null;
/* 275 */     if (this.fieldCache != null)
/*     */     {
/* 277 */       retval = (PDField)this.fieldCache.get(name);
/*     */     }
/*     */     else
/*     */     {
/* 281 */       String[] nameSubSection = name.split("\\.");
/* 282 */       COSArray fields = (COSArray)this.acroForm.getDictionaryObject(COSName.getPDFName("Fields"));
/*     */ 
/* 286 */       for (int i = 0; (i < fields.size()) && (retval == null); i++)
/*     */       {
/* 288 */         COSDictionary element = (COSDictionary)fields.getObject(i);
/* 289 */         if (element != null)
/*     */         {
/* 291 */           COSString fieldName = (COSString)element.getDictionaryObject(COSName.getPDFName("T"));
/*     */ 
/* 293 */           if ((fieldName.getString().equals(name)) || (fieldName.getString().equals(nameSubSection[0])))
/*     */           {
/* 296 */             PDField root = PDFieldFactory.createField(this, element);
/*     */ 
/* 298 */             if (nameSubSection.length > 1)
/*     */             {
/* 300 */               PDField kid = root.findKid(nameSubSection, 1);
/* 301 */               if (kid != null)
/*     */               {
/* 303 */                 retval = kid;
/*     */               }
/*     */               else
/*     */               {
/* 307 */                 retval = root;
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 312 */               retval = root;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 318 */     return retval;
/*     */   }
/*     */ 
/*     */   public PDResources getDefaultResources()
/*     */   {
/* 328 */     PDResources retval = null;
/* 329 */     COSDictionary dr = (COSDictionary)this.acroForm.getDictionaryObject(COSName.getPDFName("DR"));
/* 330 */     if (dr != null)
/*     */     {
/* 332 */       retval = new PDResources(dr);
/*     */     }
/* 334 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setDefaultResources(PDResources dr)
/*     */   {
/* 344 */     COSDictionary drDict = null;
/* 345 */     if (dr != null)
/*     */     {
/* 347 */       drDict = dr.getCOSDictionary();
/*     */     }
/* 349 */     this.acroForm.setItem(COSName.getPDFName("DR"), drDict);
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 357 */     return this.acroForm;
/*     */   }
/*     */ 
/*     */   public PDXFA getXFA()
/*     */   {
/* 367 */     PDXFA xfa = null;
/* 368 */     COSBase base = this.acroForm.getDictionaryObject("XFA");
/* 369 */     if (base != null)
/*     */     {
/* 371 */       xfa = new PDXFA(base);
/*     */     }
/* 373 */     return xfa;
/*     */   }
/*     */ 
/*     */   public void setXFA(PDXFA xfa)
/*     */   {
/* 383 */     this.acroForm.setItem("XFA", xfa);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm
 * JD-Core Version:    0.6.2
 */