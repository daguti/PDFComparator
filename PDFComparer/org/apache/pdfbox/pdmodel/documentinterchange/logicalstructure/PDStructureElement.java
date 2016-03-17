/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.markedcontent.PDMarkedContent;
/*     */ 
/*     */ public class PDStructureElement extends PDStructureNode
/*     */ {
/*     */   public static final String TYPE = "StructElem";
/*     */ 
/*     */   public PDStructureElement(String structureType, PDStructureNode parent)
/*     */   {
/*  51 */     super("StructElem");
/*  52 */     setStructureType(structureType);
/*  53 */     setParent(parent);
/*     */   }
/*     */ 
/*     */   public PDStructureElement(COSDictionary dic)
/*     */   {
/*  63 */     super(dic);
/*     */   }
/*     */ 
/*     */   public String getStructureType()
/*     */   {
/*  74 */     return getCOSDictionary().getNameAsString(COSName.S);
/*     */   }
/*     */ 
/*     */   public void setStructureType(String structureType)
/*     */   {
/*  84 */     getCOSDictionary().setName(COSName.S, structureType);
/*     */   }
/*     */ 
/*     */   public PDStructureNode getParent()
/*     */   {
/*  94 */     COSDictionary p = (COSDictionary)getCOSDictionary().getDictionaryObject(COSName.P);
/*     */ 
/*  96 */     if (p == null)
/*     */     {
/*  98 */       return null;
/*     */     }
/* 100 */     return PDStructureNode.create(p);
/*     */   }
/*     */ 
/*     */   public void setParent(PDStructureNode structureNode)
/*     */   {
/* 110 */     getCOSDictionary().setItem(COSName.P, structureNode);
/*     */   }
/*     */ 
/*     */   public String getElementIdentifier()
/*     */   {
/* 120 */     return getCOSDictionary().getString(COSName.ID);
/*     */   }
/*     */ 
/*     */   public void setElementIdentifier(String id)
/*     */   {
/* 130 */     getCOSDictionary().setString(COSName.ID, id);
/*     */   }
/*     */ 
/*     */   public PDPage getPage()
/*     */   {
/* 142 */     COSDictionary pageDic = (COSDictionary)getCOSDictionary().getDictionaryObject(COSName.PG);
/*     */ 
/* 144 */     if (pageDic == null)
/*     */     {
/* 146 */       return null;
/*     */     }
/* 148 */     return new PDPage(pageDic);
/*     */   }
/*     */ 
/*     */   public void setPage(PDPage page)
/*     */   {
/* 159 */     getCOSDictionary().setItem(COSName.PG, page);
/*     */   }
/*     */ 
/*     */   public Revisions<PDAttributeObject> getAttributes()
/*     */   {
/* 169 */     Revisions attributes = new Revisions();
/*     */ 
/* 171 */     COSBase a = getCOSDictionary().getDictionaryObject(COSName.A);
/* 172 */     if ((a instanceof COSArray))
/*     */     {
/* 174 */       COSArray aa = (COSArray)a;
/* 175 */       Iterator it = aa.iterator();
/* 176 */       PDAttributeObject ao = null;
/* 177 */       while (it.hasNext())
/*     */       {
/* 179 */         COSBase item = (COSBase)it.next();
/* 180 */         if ((item instanceof COSDictionary))
/*     */         {
/* 182 */           ao = PDAttributeObject.create((COSDictionary)item);
/* 183 */           ao.setStructureElement(this);
/* 184 */           attributes.addObject(ao, 0);
/*     */         }
/* 186 */         else if ((item instanceof COSInteger))
/*     */         {
/* 188 */           attributes.setRevisionNumber(ao, ((COSInteger)item).intValue());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 193 */     if ((a instanceof COSDictionary))
/*     */     {
/* 195 */       PDAttributeObject ao = PDAttributeObject.create((COSDictionary)a);
/* 196 */       ao.setStructureElement(this);
/* 197 */       attributes.addObject(ao, 0);
/*     */     }
/* 199 */     return attributes;
/*     */   }
/*     */ 
/*     */   public void setAttributes(Revisions<PDAttributeObject> attributes)
/*     */   {
/* 209 */     COSName key = COSName.A;
/* 210 */     if ((attributes.size() == 1) && (attributes.getRevisionNumber(0) == 0))
/*     */     {
/* 212 */       PDAttributeObject attributeObject = (PDAttributeObject)attributes.getObject(0);
/* 213 */       attributeObject.setStructureElement(this);
/* 214 */       getCOSDictionary().setItem(key, attributeObject);
/* 215 */       return;
/*     */     }
/* 217 */     COSArray array = new COSArray();
/* 218 */     for (int i = 0; i < attributes.size(); i++)
/*     */     {
/* 220 */       PDAttributeObject attributeObject = (PDAttributeObject)attributes.getObject(i);
/* 221 */       attributeObject.setStructureElement(this);
/* 222 */       int revisionNumber = attributes.getRevisionNumber(i);
/* 223 */       if (revisionNumber < 0);
/* 227 */       array.add(attributeObject);
/* 228 */       array.add(COSInteger.get(revisionNumber));
/*     */     }
/* 230 */     getCOSDictionary().setItem(key, array);
/*     */   }
/*     */ 
/*     */   public void addAttribute(PDAttributeObject attributeObject)
/*     */   {
/* 240 */     COSName key = COSName.A;
/* 241 */     attributeObject.setStructureElement(this);
/* 242 */     COSBase a = getCOSDictionary().getDictionaryObject(key);
/* 243 */     COSArray array = null;
/* 244 */     if ((a instanceof COSArray))
/*     */     {
/* 246 */       array = (COSArray)a;
/*     */     }
/*     */     else
/*     */     {
/* 250 */       array = new COSArray();
/* 251 */       if (a != null)
/*     */       {
/* 253 */         array.add(a);
/* 254 */         array.add(COSInteger.get(0L));
/*     */       }
/*     */     }
/* 257 */     getCOSDictionary().setItem(key, array);
/* 258 */     array.add(attributeObject);
/* 259 */     array.add(COSInteger.get(getRevisionNumber()));
/*     */   }
/*     */ 
/*     */   public void removeAttribute(PDAttributeObject attributeObject)
/*     */   {
/* 269 */     COSName key = COSName.A;
/* 270 */     COSBase a = getCOSDictionary().getDictionaryObject(key);
/* 271 */     if ((a instanceof COSArray))
/*     */     {
/* 273 */       COSArray array = (COSArray)a;
/* 274 */       array.remove(attributeObject.getCOSObject());
/* 275 */       if ((array.size() == 2) && (array.getInt(1) == 0))
/*     */       {
/* 277 */         getCOSDictionary().setItem(key, array.getObject(0));
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 282 */       COSBase directA = a;
/* 283 */       if ((a instanceof COSObject))
/*     */       {
/* 285 */         directA = ((COSObject)a).getObject();
/*     */       }
/* 287 */       if (attributeObject.getCOSObject().equals(directA))
/*     */       {
/* 289 */         getCOSDictionary().setItem(key, null);
/*     */       }
/*     */     }
/* 292 */     attributeObject.setStructureElement(null);
/*     */   }
/*     */ 
/*     */   public void attributeChanged(PDAttributeObject attributeObject)
/*     */   {
/* 302 */     COSName key = COSName.A;
/* 303 */     COSBase a = getCOSDictionary().getDictionaryObject(key);
/* 304 */     if ((a instanceof COSArray))
/*     */     {
/* 306 */       COSArray array = (COSArray)a;
/* 307 */       for (int i = 0; i < array.size(); i++)
/*     */       {
/* 309 */         COSBase entry = array.getObject(i);
/* 310 */         if (entry.equals(attributeObject.getCOSObject()))
/*     */         {
/* 312 */           COSBase next = array.get(i + 1);
/* 313 */           if ((next instanceof COSInteger))
/*     */           {
/* 315 */             array.set(i + 1, COSInteger.get(getRevisionNumber()));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 322 */       COSArray array = new COSArray();
/* 323 */       array.add(a);
/* 324 */       array.add(COSInteger.get(getRevisionNumber()));
/* 325 */       getCOSDictionary().setItem(key, array);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Revisions<String> getClassNames()
/*     */   {
/* 336 */     COSName key = COSName.C;
/* 337 */     Revisions classNames = new Revisions();
/* 338 */     COSBase c = getCOSDictionary().getDictionaryObject(key);
/* 339 */     if ((c instanceof COSName))
/*     */     {
/* 341 */       classNames.addObject(((COSName)c).getName(), 0);
/*     */     }
/* 343 */     if ((c instanceof COSArray))
/*     */     {
/* 345 */       COSArray array = (COSArray)c;
/* 346 */       Iterator it = array.iterator();
/* 347 */       String className = null;
/* 348 */       while (it.hasNext())
/*     */       {
/* 350 */         COSBase item = (COSBase)it.next();
/* 351 */         if ((item instanceof COSName))
/*     */         {
/* 353 */           className = ((COSName)item).getName();
/* 354 */           classNames.addObject(className, 0);
/*     */         }
/* 356 */         else if ((item instanceof COSInteger))
/*     */         {
/* 358 */           classNames.setRevisionNumber(className, ((COSInteger)item).intValue());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 363 */     return classNames;
/*     */   }
/*     */ 
/*     */   public void setClassNames(Revisions<String> classNames)
/*     */   {
/* 373 */     if (classNames == null)
/*     */     {
/* 375 */       return;
/*     */     }
/* 377 */     COSName key = COSName.C;
/* 378 */     if ((classNames.size() == 1) && (classNames.getRevisionNumber(0) == 0))
/*     */     {
/* 380 */       String className = (String)classNames.getObject(0);
/* 381 */       getCOSDictionary().setName(key, className);
/* 382 */       return;
/*     */     }
/* 384 */     COSArray array = new COSArray();
/* 385 */     for (int i = 0; i < classNames.size(); i++)
/*     */     {
/* 387 */       String className = (String)classNames.getObject(i);
/* 388 */       int revisionNumber = classNames.getRevisionNumber(i);
/* 389 */       if (revisionNumber < 0);
/* 393 */       array.add(COSName.getPDFName(className));
/* 394 */       array.add(COSInteger.get(revisionNumber));
/*     */     }
/* 396 */     getCOSDictionary().setItem(key, array);
/*     */   }
/*     */ 
/*     */   public void addClassName(String className)
/*     */   {
/* 406 */     if (className == null)
/*     */     {
/* 408 */       return;
/*     */     }
/* 410 */     COSName key = COSName.C;
/* 411 */     COSBase c = getCOSDictionary().getDictionaryObject(key);
/* 412 */     COSArray array = null;
/* 413 */     if ((c instanceof COSArray))
/*     */     {
/* 415 */       array = (COSArray)c;
/*     */     }
/*     */     else
/*     */     {
/* 419 */       array = new COSArray();
/* 420 */       if (c != null)
/*     */       {
/* 422 */         array.add(c);
/* 423 */         array.add(COSInteger.get(0L));
/*     */       }
/*     */     }
/* 426 */     getCOSDictionary().setItem(key, array);
/* 427 */     array.add(COSName.getPDFName(className));
/* 428 */     array.add(COSInteger.get(getRevisionNumber()));
/*     */   }
/*     */ 
/*     */   public void removeClassName(String className)
/*     */   {
/* 438 */     if (className == null)
/*     */     {
/* 440 */       return;
/*     */     }
/* 442 */     COSName key = COSName.C;
/* 443 */     COSBase c = getCOSDictionary().getDictionaryObject(key);
/* 444 */     COSName name = COSName.getPDFName(className);
/* 445 */     if ((c instanceof COSArray))
/*     */     {
/* 447 */       COSArray array = (COSArray)c;
/* 448 */       array.remove(name);
/* 449 */       if ((array.size() == 2) && (array.getInt(1) == 0))
/*     */       {
/* 451 */         getCOSDictionary().setItem(key, array.getObject(0));
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 456 */       COSBase directC = c;
/* 457 */       if ((c instanceof COSObject))
/*     */       {
/* 459 */         directC = ((COSObject)c).getObject();
/*     */       }
/* 461 */       if (name.equals(directC))
/*     */       {
/* 463 */         getCOSDictionary().setItem(key, null);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getRevisionNumber()
/*     */   {
/* 475 */     return getCOSDictionary().getInt(COSName.R, 0);
/*     */   }
/*     */ 
/*     */   public void setRevisionNumber(int revisionNumber)
/*     */   {
/* 485 */     if (revisionNumber < 0);
/* 489 */     getCOSDictionary().setInt(COSName.R, revisionNumber);
/*     */   }
/*     */ 
/*     */   public void incrementRevisionNumber()
/*     */   {
/* 497 */     setRevisionNumber(getRevisionNumber() + 1);
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 507 */     return getCOSDictionary().getString(COSName.T);
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 517 */     getCOSDictionary().setString(COSName.T, title);
/*     */   }
/*     */ 
/*     */   public String getLanguage()
/*     */   {
/* 527 */     return getCOSDictionary().getString(COSName.LANG);
/*     */   }
/*     */ 
/*     */   public void setLanguage(String language)
/*     */   {
/* 537 */     getCOSDictionary().setString(COSName.LANG, language);
/*     */   }
/*     */ 
/*     */   public String getAlternateDescription()
/*     */   {
/* 547 */     return getCOSDictionary().getString(COSName.ALT);
/*     */   }
/*     */ 
/*     */   public void setAlternateDescription(String alternateDescription)
/*     */   {
/* 557 */     getCOSDictionary().setString(COSName.ALT, alternateDescription);
/*     */   }
/*     */ 
/*     */   public String getExpandedForm()
/*     */   {
/* 567 */     return getCOSDictionary().getString(COSName.E);
/*     */   }
/*     */ 
/*     */   public void setExpandedForm(String expandedForm)
/*     */   {
/* 577 */     getCOSDictionary().setString(COSName.E, expandedForm);
/*     */   }
/*     */ 
/*     */   public String getActualText()
/*     */   {
/* 587 */     return getCOSDictionary().getString(COSName.ACTUAL_TEXT);
/*     */   }
/*     */ 
/*     */   public void setActualText(String actualText)
/*     */   {
/* 597 */     getCOSDictionary().setString(COSName.ACTUAL_TEXT, actualText);
/*     */   }
/*     */ 
/*     */   public String getStandardStructureType()
/*     */   {
/* 608 */     String type = getStructureType();
/* 609 */     Map roleMap = getRoleMap();
/* 610 */     if (roleMap.containsKey(type))
/*     */     {
/* 612 */       Object mappedValue = getRoleMap().get(type);
/* 613 */       if ((mappedValue instanceof String))
/*     */       {
/* 615 */         type = (String)mappedValue;
/*     */       }
/*     */     }
/* 618 */     return type;
/*     */   }
/*     */ 
/*     */   public void appendKid(PDMarkedContent markedContent)
/*     */   {
/* 628 */     if (markedContent == null)
/*     */     {
/* 630 */       return;
/*     */     }
/* 632 */     appendKid(COSInteger.get(markedContent.getMCID()));
/*     */   }
/*     */ 
/*     */   public void appendKid(PDMarkedContentReference markedContentReference)
/*     */   {
/* 642 */     appendObjectableKid(markedContentReference);
/*     */   }
/*     */ 
/*     */   public void appendKid(PDObjectReference objectReference)
/*     */   {
/* 652 */     appendObjectableKid(objectReference);
/*     */   }
/*     */ 
/*     */   public void insertBefore(COSInteger markedContentIdentifier, Object refKid)
/*     */   {
/* 663 */     insertBefore(markedContentIdentifier, refKid);
/*     */   }
/*     */ 
/*     */   public void insertBefore(PDMarkedContentReference markedContentReference, Object refKid)
/*     */   {
/* 675 */     insertObjectableBefore(markedContentReference, refKid);
/*     */   }
/*     */ 
/*     */   public void insertBefore(PDObjectReference objectReference, Object refKid)
/*     */   {
/* 686 */     insertObjectableBefore(objectReference, refKid);
/*     */   }
/*     */ 
/*     */   public void removeKid(COSInteger markedContentIdentifier)
/*     */   {
/* 696 */     removeKid(markedContentIdentifier);
/*     */   }
/*     */ 
/*     */   public void removeKid(PDMarkedContentReference markedContentReference)
/*     */   {
/* 706 */     removeObjectableKid(markedContentReference);
/*     */   }
/*     */ 
/*     */   public void removeKid(PDObjectReference objectReference)
/*     */   {
/* 716 */     removeObjectableKid(objectReference);
/*     */   }
/*     */ 
/*     */   private PDStructureTreeRoot getStructureTreeRoot()
/*     */   {
/* 727 */     PDStructureNode parent = getParent();
/* 728 */     while ((parent instanceof PDStructureElement))
/*     */     {
/* 730 */       parent = ((PDStructureElement)parent).getParent();
/*     */     }
/* 732 */     if ((parent instanceof PDStructureTreeRoot))
/*     */     {
/* 734 */       return (PDStructureTreeRoot)parent;
/*     */     }
/* 736 */     return null;
/*     */   }
/*     */ 
/*     */   private Map<String, Object> getRoleMap()
/*     */   {
/* 746 */     PDStructureTreeRoot root = getStructureTreeRoot();
/* 747 */     if (root != null)
/*     */     {
/* 749 */       return root.getRoleMap();
/*     */     }
/* 751 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureElement
 * JD-Core Version:    0.6.2
 */