/*     */ package org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.exceptions.OutlineNotLocalException;
/*     */ import org.apache.pdfbox.pdmodel.PDDestinationNameTreeNode;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureElement;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.PDActionFactory;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDAction;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDActionGoTo;
/*     */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDDestination;
/*     */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDNamedDestination;
/*     */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
/*     */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
/*     */ import org.apache.pdfbox.util.BitFlagHelper;
/*     */ 
/*     */ public class PDOutlineItem extends PDOutlineNode
/*     */ {
/*     */   private static final int ITALIC_FLAG = 1;
/*     */   private static final int BOLD_FLAG = 2;
/*     */ 
/*     */   public PDOutlineItem()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDOutlineItem(COSDictionary dic)
/*     */   {
/*  71 */     super(dic);
/*     */   }
/*     */ 
/*     */   public void insertSiblingAfter(PDOutlineItem item)
/*     */   {
/*  81 */     item.setParent(getParent());
/*  82 */     PDOutlineItem next = getNextSibling();
/*  83 */     setNextSibling(item);
/*  84 */     item.setPreviousSibling(this);
/*  85 */     if (next != null)
/*     */     {
/*  87 */       item.setNextSibling(next);
/*  88 */       next.setPreviousSibling(item);
/*     */     }
/*  90 */     updateParentOpenCount(1);
/*     */   }
/*     */ 
/*     */   public PDOutlineNode getParent()
/*     */   {
/*  98 */     return super.getParent();
/*     */   }
/*     */ 
/*     */   public PDOutlineItem getPreviousSibling()
/*     */   {
/* 108 */     PDOutlineItem last = null;
/* 109 */     COSDictionary lastDic = (COSDictionary)this.node.getDictionaryObject(COSName.PREV);
/* 110 */     if (lastDic != null)
/*     */     {
/* 112 */       last = new PDOutlineItem(lastDic);
/*     */     }
/* 114 */     return last;
/*     */   }
/*     */ 
/*     */   protected void setPreviousSibling(PDOutlineNode outlineNode)
/*     */   {
/* 124 */     this.node.setItem(COSName.PREV, outlineNode);
/*     */   }
/*     */ 
/*     */   public PDOutlineItem getNextSibling()
/*     */   {
/* 134 */     PDOutlineItem last = null;
/* 135 */     COSDictionary lastDic = (COSDictionary)this.node.getDictionaryObject(COSName.NEXT);
/* 136 */     if (lastDic != null)
/*     */     {
/* 138 */       last = new PDOutlineItem(lastDic);
/*     */     }
/* 140 */     return last;
/*     */   }
/*     */ 
/*     */   protected void setNextSibling(PDOutlineNode outlineNode)
/*     */   {
/* 150 */     this.node.setItem(COSName.NEXT, outlineNode);
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 160 */     return this.node.getString(COSName.TITLE);
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 170 */     this.node.setString(COSName.TITLE, title);
/*     */   }
/*     */ 
/*     */   public PDDestination getDestination()
/*     */     throws IOException
/*     */   {
/* 181 */     return PDDestination.create(this.node.getDictionaryObject(COSName.DEST));
/*     */   }
/*     */ 
/*     */   public void setDestination(PDDestination dest)
/*     */   {
/* 191 */     this.node.setItem(COSName.DEST, dest);
/*     */   }
/*     */ 
/*     */   public void setDestination(PDPage page)
/*     */   {
/* 201 */     PDPageXYZDestination dest = null;
/* 202 */     if (page != null)
/*     */     {
/* 204 */       dest = new PDPageXYZDestination();
/* 205 */       dest.setPage(page);
/*     */     }
/* 207 */     setDestination(dest);
/*     */   }
/*     */ 
/*     */   public PDPage findDestinationPage(PDDocument doc)
/*     */     throws IOException
/*     */   {
/* 222 */     PDPage page = null;
/* 223 */     PDDestination rawDest = getDestination();
/* 224 */     if (rawDest == null)
/*     */     {
/* 226 */       PDAction outlineAction = getAction();
/* 227 */       if ((outlineAction instanceof PDActionGoTo))
/*     */       {
/* 229 */         rawDest = ((PDActionGoTo)outlineAction).getDestination();
/*     */       }
/* 231 */       else if (outlineAction != null)
/*     */       {
/* 238 */         throw new OutlineNotLocalException("Error: Outline does not reference a local page.");
/*     */       }
/*     */     }
/*     */ 
/* 242 */     PDPageDestination pageDest = null;
/* 243 */     if ((rawDest instanceof PDNamedDestination))
/*     */     {
/* 246 */       PDNamedDestination namedDest = (PDNamedDestination)rawDest;
/* 247 */       PDDocumentNameDictionary namesDict = doc.getDocumentCatalog().getNames();
/* 248 */       if (namesDict != null)
/*     */       {
/* 250 */         PDDestinationNameTreeNode destsTree = namesDict.getDests();
/* 251 */         if (destsTree != null)
/*     */         {
/* 253 */           pageDest = (PDPageDestination)destsTree.getValue(namedDest.getNamedDestination());
/*     */         }
/*     */       }
/*     */     }
/* 257 */     else if ((rawDest instanceof PDPageDestination))
/*     */     {
/* 259 */       pageDest = (PDPageDestination)rawDest;
/*     */     }
/* 261 */     else if (rawDest != null)
/*     */     {
/* 267 */       throw new IOException("Error: Unknown destination type " + rawDest);
/*     */     }
/*     */ 
/* 270 */     if (pageDest != null)
/*     */     {
/* 272 */       page = pageDest.getPage();
/* 273 */       if (page == null)
/*     */       {
/* 275 */         int pageNumber = pageDest.getPageNumber();
/* 276 */         if (pageNumber != -1)
/*     */         {
/* 278 */           List allPages = doc.getDocumentCatalog().getAllPages();
/* 279 */           page = (PDPage)allPages.get(pageNumber);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 284 */     return page;
/*     */   }
/*     */ 
/*     */   public PDAction getAction()
/*     */   {
/* 294 */     return PDActionFactory.createAction((COSDictionary)this.node.getDictionaryObject(COSName.A));
/*     */   }
/*     */ 
/*     */   public void setAction(PDAction action)
/*     */   {
/* 304 */     this.node.setItem(COSName.A, action);
/*     */   }
/*     */ 
/*     */   public PDStructureElement getStructureElement()
/*     */   {
/* 314 */     PDStructureElement se = null;
/* 315 */     COSDictionary dic = (COSDictionary)this.node.getDictionaryObject(COSName.SE);
/* 316 */     if (dic != null)
/*     */     {
/* 318 */       se = new PDStructureElement(dic);
/*     */     }
/* 320 */     return se;
/*     */   }
/*     */ 
/*     */   public void setStructuredElement(PDStructureElement structureElement)
/*     */   {
/* 330 */     this.node.setItem(COSName.SE, structureElement);
/*     */   }
/*     */ 
/*     */   public PDColorState getTextColor()
/*     */   {
/* 341 */     PDColorState retval = null;
/* 342 */     COSArray csValues = (COSArray)this.node.getDictionaryObject(COSName.C);
/* 343 */     if (csValues == null)
/*     */     {
/* 345 */       csValues = new COSArray();
/* 346 */       csValues.growToSize(3, new COSFloat(0.0F));
/* 347 */       this.node.setItem(COSName.C, csValues);
/*     */     }
/* 349 */     retval = new PDColorState(csValues);
/* 350 */     retval.setColorSpace(PDDeviceRGB.INSTANCE);
/* 351 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setTextColor(PDColorState textColor)
/*     */   {
/* 361 */     this.node.setItem(COSName.C, textColor.getCOSColorSpaceValue());
/*     */   }
/*     */ 
/*     */   public void setTextColor(Color textColor)
/*     */   {
/* 371 */     COSArray array = new COSArray();
/* 372 */     array.add(new COSFloat(textColor.getRed() / 255.0F));
/* 373 */     array.add(new COSFloat(textColor.getGreen() / 255.0F));
/* 374 */     array.add(new COSFloat(textColor.getBlue() / 255.0F));
/* 375 */     this.node.setItem(COSName.C, array);
/*     */   }
/*     */ 
/*     */   public boolean isItalic()
/*     */   {
/* 385 */     return BitFlagHelper.getFlag(this.node, COSName.F, 1);
/*     */   }
/*     */ 
/*     */   public void setItalic(boolean italic)
/*     */   {
/* 395 */     BitFlagHelper.setFlag(this.node, COSName.F, 1, italic);
/*     */   }
/*     */ 
/*     */   public boolean isBold()
/*     */   {
/* 405 */     return BitFlagHelper.getFlag(this.node, COSName.F, 2);
/*     */   }
/*     */ 
/*     */   public void setBold(boolean bold)
/*     */   {
/* 415 */     BitFlagHelper.setFlag(this.node, COSName.F, 2, bold);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem
 * JD-Core Version:    0.6.2
 */