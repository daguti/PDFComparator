/*     */ package org.apache.pdfbox.pdmodel;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ 
/*     */ public class PDPageNode
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary page;
/*  50 */   private static final Log log = LogFactory.getLog(PDPageNode.class);
/*     */ 
/*     */   public PDPageNode()
/*     */   {
/*  57 */     this.page = new COSDictionary();
/*  58 */     this.page.setItem(COSName.TYPE, COSName.PAGES);
/*  59 */     this.page.setItem(COSName.KIDS, new COSArray());
/*  60 */     this.page.setItem(COSName.COUNT, COSInteger.ZERO);
/*     */   }
/*     */ 
/*     */   public PDPageNode(COSDictionary pages)
/*     */   {
/*  70 */     this.page = pages;
/*     */   }
/*     */ 
/*     */   public long updateCount()
/*     */   {
/*  83 */     long totalCount = 0L;
/*  84 */     List kids = getKids();
/*  85 */     Iterator kidIter = kids.iterator();
/*  86 */     while (kidIter.hasNext())
/*     */     {
/*  88 */       Object next = kidIter.next();
/*  89 */       if ((next instanceof PDPage))
/*     */       {
/*  91 */         totalCount += 1L;
/*     */       }
/*     */       else
/*     */       {
/*  95 */         PDPageNode node = (PDPageNode)next;
/*  96 */         totalCount += node.updateCount();
/*     */       }
/*     */     }
/*  99 */     this.page.setLong(COSName.COUNT, totalCount);
/* 100 */     return totalCount;
/*     */   }
/*     */ 
/*     */   public long getCount()
/*     */   {
/* 110 */     if (this.page == null)
/*     */     {
/* 112 */       return 0L;
/*     */     }
/* 114 */     COSBase num = this.page.getDictionaryObject(COSName.COUNT);
/* 115 */     if (num == null)
/*     */     {
/* 117 */       return 0L;
/*     */     }
/* 119 */     return ((COSNumber)num).intValue();
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/* 129 */     return this.page;
/*     */   }
/*     */ 
/*     */   public PDPageNode getParent()
/*     */   {
/* 139 */     PDPageNode parent = null;
/* 140 */     COSDictionary parentDic = (COSDictionary)this.page.getDictionaryObject(COSName.PARENT, COSName.P);
/* 141 */     if (parentDic != null)
/*     */     {
/* 143 */       parent = new PDPageNode(parentDic);
/*     */     }
/* 145 */     return parent;
/*     */   }
/*     */ 
/*     */   public void setParent(PDPageNode parent)
/*     */   {
/* 155 */     this.page.setItem(COSName.PARENT, parent.getDictionary());
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 163 */     return this.page;
/*     */   }
/*     */ 
/*     */   public List getKids()
/*     */   {
/* 173 */     List actuals = new ArrayList();
/* 174 */     COSArray kids = getAllKids(actuals, this.page, false);
/* 175 */     return new COSArrayList(actuals, kids);
/*     */   }
/*     */ 
/*     */   public void getAllKids(List result)
/*     */   {
/* 185 */     getAllKids(result, this.page, true);
/*     */   }
/*     */ 
/*     */   private static COSArray getAllKids(List result, COSDictionary page, boolean recurse)
/*     */   {
/* 197 */     if (page == null)
/* 198 */       return null;
/* 199 */     COSArray kids = (COSArray)page.getDictionaryObject(COSName.KIDS);
/* 200 */     if (kids == null)
/*     */     {
/* 202 */       log.error("No Kids found in getAllKids(). Probably a malformed pdf.");
/* 203 */       return null;
/*     */     }
/* 205 */     HashSet seen = new HashSet();
/* 206 */     for (int i = 0; i < kids.size(); i++)
/*     */     {
/* 209 */       if (!seen.contains(kids.get(i)))
/*     */       {
/* 211 */         COSBase obj = kids.getObject(i);
/* 212 */         if ((obj instanceof COSDictionary))
/*     */         {
/* 214 */           COSDictionary kid = (COSDictionary)obj;
/* 215 */           if (COSName.PAGE.equals(kid.getDictionaryObject(COSName.TYPE)))
/*     */           {
/* 217 */             result.add(new PDPage(kid));
/*     */           }
/* 221 */           else if (recurse)
/*     */           {
/* 223 */             getAllKids(result, kid, recurse);
/*     */           }
/*     */           else
/*     */           {
/* 227 */             result.add(new PDPageNode(kid));
/*     */           }
/*     */         }
/*     */ 
/* 231 */         seen.add(kids.get(i));
/*     */       }
/*     */     }
/* 234 */     return kids;
/*     */   }
/*     */ 
/*     */   public PDResources getResources()
/*     */   {
/* 246 */     PDResources retval = null;
/* 247 */     COSDictionary resources = (COSDictionary)this.page.getDictionaryObject(COSName.RESOURCES);
/* 248 */     if (resources != null)
/*     */     {
/* 250 */       retval = new PDResources(resources);
/*     */     }
/* 252 */     return retval;
/*     */   }
/*     */ 
/*     */   public PDResources findResources()
/*     */   {
/* 263 */     PDResources retval = getResources();
/* 264 */     PDPageNode parent = getParent();
/* 265 */     if ((retval == null) && (parent != null))
/*     */     {
/* 267 */       retval = parent.findResources();
/*     */     }
/* 269 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setResources(PDResources resources)
/*     */   {
/* 279 */     if (resources == null)
/*     */     {
/* 281 */       this.page.removeItem(COSName.RESOURCES);
/*     */     }
/*     */     else
/*     */     {
/* 285 */       this.page.setItem(COSName.RESOURCES, resources.getCOSDictionary());
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDRectangle getMediaBox()
/*     */   {
/* 298 */     PDRectangle retval = null;
/* 299 */     COSArray array = (COSArray)this.page.getDictionaryObject(COSName.MEDIA_BOX);
/* 300 */     if (array != null)
/*     */     {
/* 302 */       retval = new PDRectangle(array);
/*     */     }
/* 304 */     return retval;
/*     */   }
/*     */ 
/*     */   public PDRectangle findMediaBox()
/*     */   {
/* 315 */     PDRectangle retval = getMediaBox();
/* 316 */     PDPageNode parent = getParent();
/* 317 */     if ((retval == null) && (parent != null))
/*     */     {
/* 319 */       retval = parent.findMediaBox();
/*     */     }
/* 321 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setMediaBox(PDRectangle mediaBox)
/*     */   {
/* 331 */     if (mediaBox == null)
/*     */     {
/* 333 */       this.page.removeItem(COSName.MEDIA_BOX);
/*     */     }
/*     */     else
/*     */     {
/* 337 */       this.page.setItem(COSName.MEDIA_BOX, mediaBox.getCOSArray());
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDRectangle getCropBox()
/*     */   {
/* 350 */     PDRectangle retval = null;
/* 351 */     COSArray array = (COSArray)this.page.getDictionaryObject(COSName.CROP_BOX);
/* 352 */     if (array != null)
/*     */     {
/* 354 */       retval = new PDRectangle(array);
/*     */     }
/* 356 */     return retval;
/*     */   }
/*     */ 
/*     */   public PDRectangle findCropBox()
/*     */   {
/* 367 */     PDRectangle retval = getCropBox();
/* 368 */     PDPageNode parent = getParent();
/* 369 */     if ((retval == null) && (parent != null))
/*     */     {
/* 371 */       retval = findParentCropBox(parent);
/*     */     }
/*     */ 
/* 375 */     if (retval == null)
/*     */     {
/* 377 */       retval = findMediaBox();
/*     */     }
/* 379 */     return retval;
/*     */   }
/*     */ 
/*     */   private PDRectangle findParentCropBox(PDPageNode node)
/*     */   {
/* 390 */     PDRectangle rect = node.getCropBox();
/* 391 */     PDPageNode parent = node.getParent();
/* 392 */     if ((rect == null) && (parent != null))
/*     */     {
/* 394 */       rect = findParentCropBox(node);
/*     */     }
/* 396 */     return rect;
/*     */   }
/*     */ 
/*     */   public void setCropBox(PDRectangle cropBox)
/*     */   {
/* 406 */     if (cropBox == null)
/*     */     {
/* 408 */       this.page.removeItem(COSName.CROP_BOX);
/*     */     }
/*     */     else
/*     */     {
/* 412 */       this.page.setItem(COSName.CROP_BOX, cropBox.getCOSArray());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Integer getRotation()
/*     */   {
/* 430 */     Integer retval = null;
/* 431 */     COSNumber value = (COSNumber)this.page.getDictionaryObject(COSName.ROTATE);
/* 432 */     if (value != null)
/*     */     {
/* 434 */       retval = new Integer(value.intValue());
/*     */     }
/* 436 */     return retval;
/*     */   }
/*     */ 
/*     */   public int findRotation()
/*     */   {
/* 447 */     int retval = 0;
/* 448 */     Integer rotation = getRotation();
/* 449 */     if (rotation != null)
/*     */     {
/* 451 */       retval = rotation.intValue();
/*     */     }
/*     */     else
/*     */     {
/* 455 */       PDPageNode parent = getParent();
/* 456 */       if (parent != null)
/*     */       {
/* 458 */         retval = parent.findRotation();
/*     */       }
/*     */     }
/*     */ 
/* 462 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setRotation(int rotation)
/*     */   {
/* 472 */     this.page.setInt(COSName.ROTATE, rotation);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.PDPageNode
 * JD-Core Version:    0.6.2
 */