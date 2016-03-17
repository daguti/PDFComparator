/*     */ package org.apache.pdfbox.pdmodel.fdf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.filespecification.PDFileSpecification;
/*     */ import org.apache.pdfbox.pdmodel.common.filespecification.PDSimpleFileSpecification;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class FDFDictionary
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary fdf;
/*     */ 
/*     */   public FDFDictionary()
/*     */   {
/*  54 */     this.fdf = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public FDFDictionary(COSDictionary fdfDictionary)
/*     */   {
/*  64 */     this.fdf = fdfDictionary;
/*     */   }
/*     */ 
/*     */   public FDFDictionary(Element fdfXML)
/*     */     throws IOException
/*     */   {
/*  75 */     this();
/*  76 */     NodeList nodeList = fdfXML.getChildNodes();
/*  77 */     for (int i = 0; i < nodeList.getLength(); i++)
/*     */     {
/*  79 */       Node node = nodeList.item(i);
/*  80 */       if ((node instanceof Element))
/*     */       {
/*  82 */         Element child = (Element)node;
/*  83 */         if (child.getTagName().equals("f"))
/*     */         {
/*  85 */           PDSimpleFileSpecification fs = new PDSimpleFileSpecification();
/*  86 */           fs.setFile(child.getAttribute("href"));
/*  87 */           setFile(fs);
/*     */         }
/*  90 */         else if (child.getTagName().equals("ids"))
/*     */         {
/*  92 */           COSArray ids = new COSArray();
/*  93 */           String original = child.getAttribute("original");
/*  94 */           String modified = child.getAttribute("modified");
/*  95 */           ids.add(COSString.createFromHexString(original));
/*  96 */           ids.add(COSString.createFromHexString(modified));
/*  97 */           setID(ids);
/*     */         }
/*  99 */         else if (child.getTagName().equals("fields"))
/*     */         {
/* 101 */           NodeList fields = child.getChildNodes();
/* 102 */           List fieldList = new ArrayList();
/* 103 */           for (int f = 0; f < fields.getLength(); f++)
/*     */           {
/* 105 */             Node currentNode = fields.item(f);
/* 106 */             if ((currentNode instanceof Element))
/*     */             {
/* 108 */               if (((Element)currentNode).getTagName().equals("field"))
/*     */               {
/* 110 */                 fieldList.add(new FDFField((Element)fields.item(f)));
/*     */               }
/*     */             }
/*     */           }
/* 114 */           setFields(fieldList);
/*     */         }
/* 116 */         else if (child.getTagName().equals("annots"))
/*     */         {
/* 118 */           NodeList annots = child.getChildNodes();
/* 119 */           List annotList = new ArrayList();
/* 120 */           for (int j = 0; j < annots.getLength(); j++)
/*     */           {
/* 122 */             Node annotNode = annots.item(i);
/* 123 */             if ((annotNode instanceof Element))
/*     */             {
/* 125 */               Element annot = (Element)annotNode;
/* 126 */               if (annot.getNodeName().equals("text"))
/*     */               {
/* 128 */                 annotList.add(new FDFAnnotationText(annot));
/*     */               }
/*     */               else
/*     */               {
/* 132 */                 throw new IOException("Error: Unknown annotation type '" + annot.getNodeName());
/*     */               }
/*     */             }
/*     */           }
/* 136 */           setAnnotations(annotList);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeXML(Writer output)
/*     */     throws IOException
/*     */   {
/* 151 */     PDFileSpecification fs = getFile();
/* 152 */     if (fs != null)
/*     */     {
/* 154 */       output.write("<f href=\"" + fs.getFile() + "\" />\n");
/*     */     }
/* 156 */     COSArray ids = getID();
/* 157 */     if (ids != null)
/*     */     {
/* 159 */       COSString original = (COSString)ids.getObject(0);
/* 160 */       COSString modified = (COSString)ids.getObject(1);
/* 161 */       output.write("<ids original=\"" + original.getHexString() + "\" ");
/* 162 */       output.write("modified=\"" + modified.getHexString() + "\" />\n");
/*     */     }
/* 164 */     List fields = getFields();
/* 165 */     if ((fields != null) && (fields.size() > 0))
/*     */     {
/* 167 */       output.write("<fields>\n");
/* 168 */       for (int i = 0; i < fields.size(); i++)
/*     */       {
/* 170 */         ((FDFField)fields.get(i)).writeXML(output);
/*     */       }
/* 172 */       output.write("</fields>\n");
/*     */     }
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 183 */     return this.fdf;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/* 193 */     return this.fdf;
/*     */   }
/*     */ 
/*     */   public PDFileSpecification getFile()
/*     */     throws IOException
/*     */   {
/* 206 */     return PDFileSpecification.createFS(this.fdf.getDictionaryObject(COSName.F));
/*     */   }
/*     */ 
/*     */   public void setFile(PDFileSpecification fs)
/*     */   {
/* 216 */     this.fdf.setItem(COSName.F, fs);
/*     */   }
/*     */ 
/*     */   public COSArray getID()
/*     */   {
/* 226 */     return (COSArray)this.fdf.getDictionaryObject(COSName.ID);
/*     */   }
/*     */ 
/*     */   public void setID(COSArray id)
/*     */   {
/* 236 */     this.fdf.setItem(COSName.ID, id);
/*     */   }
/*     */ 
/*     */   public List getFields()
/*     */   {
/* 247 */     List retval = null;
/* 248 */     COSArray fieldArray = (COSArray)this.fdf.getDictionaryObject(COSName.FIELDS);
/* 249 */     if (fieldArray != null)
/*     */     {
/* 251 */       List fields = new ArrayList();
/* 252 */       for (int i = 0; i < fieldArray.size(); i++)
/*     */       {
/* 254 */         fields.add(new FDFField((COSDictionary)fieldArray.getObject(i)));
/*     */       }
/* 256 */       retval = new COSArrayList(fields, fieldArray);
/*     */     }
/* 258 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFields(List fields)
/*     */   {
/* 268 */     this.fdf.setItem(COSName.FIELDS, COSArrayList.converterToCOSArray(fields));
/*     */   }
/*     */ 
/*     */   public String getStatus()
/*     */   {
/* 279 */     return this.fdf.getString(COSName.STATUS);
/*     */   }
/*     */ 
/*     */   public void setStatus(String status)
/*     */   {
/* 289 */     this.fdf.setString(COSName.STATUS, status);
/*     */   }
/*     */ 
/*     */   public List getPages()
/*     */   {
/* 299 */     List retval = null;
/* 300 */     COSArray pageArray = (COSArray)this.fdf.getDictionaryObject(COSName.PAGES);
/* 301 */     if (pageArray != null)
/*     */     {
/* 303 */       List pages = new ArrayList();
/* 304 */       for (int i = 0; i < pageArray.size(); i++)
/*     */       {
/* 306 */         pages.add(new FDFPage((COSDictionary)pageArray.get(i)));
/*     */       }
/* 308 */       retval = new COSArrayList(pages, pageArray);
/*     */     }
/* 310 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setPages(List pages)
/*     */   {
/* 321 */     this.fdf.setItem(COSName.PAGES, COSArrayList.converterToCOSArray(pages));
/*     */   }
/*     */ 
/*     */   public String getEncoding()
/*     */   {
/* 332 */     String encoding = this.fdf.getNameAsString(COSName.ENCODING);
/* 333 */     if (encoding == null)
/*     */     {
/* 335 */       encoding = "PDFDocEncoding";
/*     */     }
/* 337 */     return encoding;
/*     */   }
/*     */ 
/*     */   public void setEncoding(String encoding)
/*     */   {
/* 348 */     this.fdf.setName(COSName.ENCODING, encoding);
/*     */   }
/*     */ 
/*     */   public List getAnnotations()
/*     */     throws IOException
/*     */   {
/* 361 */     List retval = null;
/* 362 */     COSArray annotArray = (COSArray)this.fdf.getDictionaryObject(COSName.ANNOTS);
/* 363 */     if (annotArray != null)
/*     */     {
/* 365 */       List annots = new ArrayList();
/* 366 */       for (int i = 0; i < annotArray.size(); i++)
/*     */       {
/* 368 */         annots.add(FDFAnnotation.create((COSDictionary)annotArray.getObject(i)));
/*     */       }
/* 370 */       retval = new COSArrayList(annots, annotArray);
/*     */     }
/* 372 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setAnnotations(List annots)
/*     */   {
/* 383 */     this.fdf.setItem(COSName.ANNOTS, COSArrayList.converterToCOSArray(annots));
/*     */   }
/*     */ 
/*     */   public COSStream getDifferences()
/*     */   {
/* 393 */     return (COSStream)this.fdf.getDictionaryObject(COSName.DIFFERENCES);
/*     */   }
/*     */ 
/*     */   public void setDifferences(COSStream diff)
/*     */   {
/* 403 */     this.fdf.setItem(COSName.DIFFERENCES, diff);
/*     */   }
/*     */ 
/*     */   public String getTarget()
/*     */   {
/* 413 */     return this.fdf.getString(COSName.TARGET);
/*     */   }
/*     */ 
/*     */   public void setTarget(String target)
/*     */   {
/* 423 */     this.fdf.setString(COSName.TARGET, target);
/*     */   }
/*     */ 
/*     */   public List getEmbeddedFDFs()
/*     */     throws IOException
/*     */   {
/* 436 */     List retval = null;
/* 437 */     COSArray embeddedArray = (COSArray)this.fdf.getDictionaryObject(COSName.EMBEDDED_FDFS);
/* 438 */     if (embeddedArray != null)
/*     */     {
/* 440 */       List embedded = new ArrayList();
/* 441 */       for (int i = 0; i < embeddedArray.size(); i++)
/*     */       {
/* 443 */         embedded.add(PDFileSpecification.createFS(embeddedArray.get(i)));
/*     */       }
/* 445 */       retval = new COSArrayList(embedded, embeddedArray);
/*     */     }
/* 447 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setEmbeddedFDFs(List embedded)
/*     */   {
/* 459 */     this.fdf.setItem(COSName.EMBEDDED_FDFS, COSArrayList.converterToCOSArray(embedded));
/*     */   }
/*     */ 
/*     */   public FDFJavaScript getJavaScript()
/*     */   {
/* 469 */     FDFJavaScript fs = null;
/* 470 */     COSDictionary dic = (COSDictionary)this.fdf.getDictionaryObject(COSName.JAVA_SCRIPT);
/* 471 */     if (dic != null)
/*     */     {
/* 473 */       fs = new FDFJavaScript(dic);
/*     */     }
/* 475 */     return fs;
/*     */   }
/*     */ 
/*     */   public void setJavaScript(FDFJavaScript js)
/*     */   {
/* 485 */     this.fdf.setItem(COSName.JAVA_SCRIPT, js);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFDictionary
 * JD-Core Version:    0.6.2
 */