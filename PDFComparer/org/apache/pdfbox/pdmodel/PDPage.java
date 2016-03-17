/*     */ package org.apache.pdfbox.pdmodel;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Printable;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.awt.print.PrinterIOException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdfviewer.PageDrawer;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.PDMetadata;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.interactive.action.PDPageAdditionalActions;
/*     */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
/*     */ import org.apache.pdfbox.pdmodel.interactive.pagenavigation.PDThreadBead;
/*     */ 
/*     */ public class PDPage
/*     */   implements COSObjectable, Printable
/*     */ {
/*  70 */   private static final Log LOG = LogFactory.getLog(PDPage.class);
/*     */   private static final int DEFAULT_USER_SPACE_UNIT_DPI = 72;
/*     */   private static final float MM_TO_UNITS = 2.834646F;
/*  79 */   private static final Color TRANSPARENT_WHITE = new Color(255, 255, 255, 0);
/*     */   private COSDictionary page;
/*     */   private PDResources pageResources;
/*  88 */   public static final PDRectangle PAGE_SIZE_LETTER = new PDRectangle(612.0F, 792.0F);
/*     */ 
/*  93 */   public static final PDRectangle PAGE_SIZE_A0 = new PDRectangle(2383.937F, 3370.3938F);
/*     */ 
/*  97 */   public static final PDRectangle PAGE_SIZE_A1 = new PDRectangle(1683.7795F, 2383.937F);
/*     */ 
/* 101 */   public static final PDRectangle PAGE_SIZE_A2 = new PDRectangle(1190.5513F, 1683.7795F);
/*     */ 
/* 105 */   public static final PDRectangle PAGE_SIZE_A3 = new PDRectangle(841.88977F, 1190.5513F);
/*     */ 
/* 109 */   public static final PDRectangle PAGE_SIZE_A4 = new PDRectangle(595.27563F, 841.88977F);
/*     */ 
/* 113 */   public static final PDRectangle PAGE_SIZE_A5 = new PDRectangle(419.52756F, 595.27563F);
/*     */ 
/* 117 */   public static final PDRectangle PAGE_SIZE_A6 = new PDRectangle(297.63782F, 419.52756F);
/*     */ 
/* 193 */   private PDPageNode parent = null;
/*     */ 
/* 326 */   private PDRectangle mediaBox = null;
/*     */ 
/*     */   public PDPage()
/*     */   {
/* 124 */     this.page = new COSDictionary();
/* 125 */     this.page.setItem(COSName.TYPE, COSName.PAGE);
/* 126 */     setMediaBox(PAGE_SIZE_LETTER);
/*     */   }
/*     */ 
/*     */   public PDPage(PDRectangle size)
/*     */   {
/* 136 */     this.page = new COSDictionary();
/* 137 */     this.page.setItem(COSName.TYPE, COSName.PAGE);
/* 138 */     setMediaBox(size);
/*     */   }
/*     */ 
/*     */   public PDPage(COSDictionary pageDic)
/*     */   {
/* 150 */     this.page = pageDic;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 160 */     return this.page;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/* 170 */     return this.page;
/*     */   }
/*     */ 
/*     */   public PDPageNode getParent()
/*     */   {
/* 182 */     if (this.parent == null)
/*     */     {
/* 184 */       COSDictionary parentDic = (COSDictionary)this.page.getDictionaryObject(COSName.PARENT, COSName.P);
/* 185 */       if (parentDic != null)
/*     */       {
/* 187 */         this.parent = new PDPageNode(parentDic);
/*     */       }
/*     */     }
/* 190 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public void setParent(PDPageNode parentNode)
/*     */   {
/* 202 */     this.parent = parentNode;
/* 203 */     this.page.setItem(COSName.PARENT, this.parent.getDictionary());
/*     */   }
/*     */ 
/*     */   public void updateLastModified()
/*     */   {
/* 211 */     this.page.setDate(COSName.LAST_MODIFIED, new GregorianCalendar());
/*     */   }
/*     */ 
/*     */   public Calendar getLastModified()
/*     */     throws IOException
/*     */   {
/* 224 */     return this.page.getDate(COSName.LAST_MODIFIED);
/*     */   }
/*     */ 
/*     */   public PDResources getResources()
/*     */   {
/* 236 */     if (this.pageResources == null)
/*     */     {
/* 238 */       COSDictionary resources = (COSDictionary)this.page.getDictionaryObject(COSName.RESOURCES);
/* 239 */       if (resources != null)
/*     */       {
/* 241 */         this.pageResources = new PDResources(resources);
/*     */       }
/*     */     }
/* 244 */     return this.pageResources;
/*     */   }
/*     */ 
/*     */   public PDResources findResources()
/*     */   {
/* 255 */     PDResources retval = getResources();
/* 256 */     PDPageNode parentNode = getParent();
/* 257 */     if ((retval == null) && (parentNode != null))
/*     */     {
/* 259 */       retval = parentNode.findResources();
/*     */     }
/* 261 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setResources(PDResources resources)
/*     */   {
/* 271 */     this.pageResources = resources;
/* 272 */     if (resources != null)
/*     */     {
/* 274 */       this.page.setItem(COSName.RESOURCES, resources);
/*     */     }
/*     */     else
/*     */     {
/* 278 */       this.page.removeItem(COSName.RESOURCES);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getStructParents()
/*     */   {
/* 289 */     return this.page.getInt(COSName.STRUCT_PARENTS, 0);
/*     */   }
/*     */ 
/*     */   public void setStructParents(int structParents)
/*     */   {
/* 299 */     this.page.setInt(COSName.STRUCT_PARENTS, structParents);
/*     */   }
/*     */ 
/*     */   public PDRectangle getMediaBox()
/*     */   {
/* 315 */     if (this.mediaBox == null)
/*     */     {
/* 317 */       COSArray array = (COSArray)this.page.getDictionaryObject(COSName.MEDIA_BOX);
/* 318 */       if (array != null)
/*     */       {
/* 320 */         this.mediaBox = new PDRectangle(array);
/*     */       }
/*     */     }
/* 323 */     return this.mediaBox;
/*     */   }
/*     */ 
/*     */   public PDRectangle findMediaBox()
/*     */   {
/* 336 */     PDRectangle retval = getMediaBox();
/* 337 */     if ((retval == null) && (getParent() != null))
/*     */     {
/* 339 */       retval = getParent().findMediaBox();
/*     */     }
/* 341 */     if (retval == null)
/*     */     {
/* 343 */       LOG.debug("Can't find MediaBox, using LETTER as default pagesize!");
/* 344 */       retval = PAGE_SIZE_LETTER;
/*     */     }
/* 346 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setMediaBox(PDRectangle mediaBoxValue)
/*     */   {
/* 356 */     this.mediaBox = mediaBoxValue;
/* 357 */     if (mediaBoxValue == null)
/*     */     {
/* 359 */       this.page.removeItem(COSName.MEDIA_BOX);
/*     */     }
/*     */     else
/*     */     {
/* 363 */       this.page.setItem(COSName.MEDIA_BOX, mediaBoxValue.getCOSArray());
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDRectangle getCropBox()
/*     */   {
/* 382 */     PDRectangle retval = null;
/* 383 */     COSArray array = (COSArray)this.page.getDictionaryObject(COSName.CROP_BOX);
/* 384 */     if (array != null)
/*     */     {
/* 386 */       retval = new PDRectangle(array);
/*     */     }
/* 388 */     return retval;
/*     */   }
/*     */ 
/*     */   public PDRectangle findCropBox()
/*     */   {
/* 399 */     PDRectangle retval = getCropBox();
/* 400 */     PDPageNode parentNode = getParent();
/* 401 */     if ((retval == null) && (parentNode != null))
/*     */     {
/* 403 */       retval = findParentCropBox(parentNode);
/*     */     }
/*     */ 
/* 407 */     if (retval == null)
/*     */     {
/* 409 */       retval = findMediaBox();
/*     */     }
/* 411 */     return retval;
/*     */   }
/*     */ 
/*     */   private PDRectangle findParentCropBox(PDPageNode node)
/*     */   {
/* 422 */     PDRectangle rect = node.getCropBox();
/* 423 */     PDPageNode parentNode = node.getParent();
/* 424 */     if ((rect == null) && (parentNode != null))
/*     */     {
/* 426 */       rect = findParentCropBox(parentNode);
/*     */     }
/* 428 */     return rect;
/*     */   }
/*     */ 
/*     */   public void setCropBox(PDRectangle cropBox)
/*     */   {
/* 438 */     if (cropBox == null)
/*     */     {
/* 440 */       this.page.removeItem(COSName.CROP_BOX);
/*     */     }
/*     */     else
/*     */     {
/* 444 */       this.page.setItem(COSName.CROP_BOX, cropBox.getCOSArray());
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDRectangle getBleedBox()
/*     */   {
/* 458 */     COSArray array = (COSArray)this.page.getDictionaryObject(COSName.BLEED_BOX);
/*     */     PDRectangle retval;
/*     */     PDRectangle retval;
/* 459 */     if (array != null)
/*     */     {
/* 461 */       retval = new PDRectangle(array);
/*     */     }
/*     */     else
/*     */     {
/* 465 */       retval = findCropBox();
/*     */     }
/* 467 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setBleedBox(PDRectangle bleedBox)
/*     */   {
/* 477 */     if (bleedBox == null)
/*     */     {
/* 479 */       this.page.removeItem(COSName.BLEED_BOX);
/*     */     }
/*     */     else
/*     */     {
/* 483 */       this.page.setItem(COSName.BLEED_BOX, bleedBox.getCOSArray());
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDRectangle getTrimBox()
/*     */   {
/* 497 */     COSArray array = (COSArray)this.page.getDictionaryObject(COSName.TRIM_BOX);
/*     */     PDRectangle retval;
/*     */     PDRectangle retval;
/* 498 */     if (array != null)
/*     */     {
/* 500 */       retval = new PDRectangle(array);
/*     */     }
/*     */     else
/*     */     {
/* 504 */       retval = findCropBox();
/*     */     }
/* 506 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setTrimBox(PDRectangle trimBox)
/*     */   {
/* 516 */     if (trimBox == null)
/*     */     {
/* 518 */       this.page.removeItem(COSName.TRIM_BOX);
/*     */     }
/*     */     else
/*     */     {
/* 522 */       this.page.setItem(COSName.TRIM_BOX, trimBox.getCOSArray());
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDRectangle getArtBox()
/*     */   {
/* 536 */     COSArray array = (COSArray)this.page.getDictionaryObject(COSName.ART_BOX);
/*     */     PDRectangle retval;
/*     */     PDRectangle retval;
/* 537 */     if (array != null)
/*     */     {
/* 539 */       retval = new PDRectangle(array);
/*     */     }
/*     */     else
/*     */     {
/* 543 */       retval = findCropBox();
/*     */     }
/* 545 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setArtBox(PDRectangle artBox)
/*     */   {
/* 555 */     if (artBox == null)
/*     */     {
/* 557 */       this.page.removeItem(COSName.ART_BOX);
/*     */     }
/*     */     else
/*     */     {
/* 561 */       this.page.setItem(COSName.ART_BOX, artBox.getCOSArray());
/*     */     }
/*     */   }
/*     */ 
/*     */   public Integer getRotation()
/*     */   {
/* 583 */     Integer retval = null;
/* 584 */     COSNumber value = (COSNumber)this.page.getDictionaryObject(COSName.ROTATE);
/* 585 */     if (value != null)
/*     */     {
/* 587 */       retval = new Integer(value.intValue());
/*     */     }
/* 589 */     return retval;
/*     */   }
/*     */ 
/*     */   public int findRotation()
/*     */   {
/* 600 */     int retval = 0;
/* 601 */     Integer rotation = getRotation();
/* 602 */     if (rotation != null)
/*     */     {
/* 604 */       retval = rotation.intValue();
/*     */     }
/*     */     else
/*     */     {
/* 608 */       PDPageNode parentNode = getParent();
/* 609 */       if (parentNode != null)
/*     */       {
/* 611 */         retval = parentNode.findRotation();
/*     */       }
/*     */     }
/*     */ 
/* 615 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setRotation(int rotation)
/*     */   {
/* 625 */     this.page.setInt(COSName.ROTATE, rotation);
/*     */   }
/*     */ 
/*     */   public PDStream getContents()
/*     */     throws IOException
/*     */   {
/* 639 */     return PDStream.createFromCOS(this.page.getDictionaryObject(COSName.CONTENTS));
/*     */   }
/*     */ 
/*     */   public void setContents(PDStream contents)
/*     */   {
/* 649 */     this.page.setItem(COSName.CONTENTS, contents);
/*     */   }
/*     */ 
/*     */   public List<PDThreadBead> getThreadBeads()
/*     */   {
/* 660 */     COSArray beads = (COSArray)this.page.getDictionaryObject(COSName.B);
/* 661 */     if (beads == null)
/*     */     {
/* 663 */       beads = new COSArray();
/*     */     }
/* 665 */     List pdObjects = new ArrayList();
/* 666 */     for (int i = 0; i < beads.size(); i++)
/*     */     {
/* 668 */       COSDictionary beadDic = (COSDictionary)beads.getObject(i);
/* 669 */       PDThreadBead bead = null;
/*     */ 
/* 671 */       if (beadDic != null)
/*     */       {
/* 673 */         bead = new PDThreadBead(beadDic);
/*     */       }
/* 675 */       pdObjects.add(bead);
/*     */     }
/* 677 */     return new COSArrayList(pdObjects, beads);
/*     */   }
/*     */ 
/*     */   public void setThreadBeads(List<PDThreadBead> beads)
/*     */   {
/* 688 */     this.page.setItem(COSName.B, COSArrayList.converterToCOSArray(beads));
/*     */   }
/*     */ 
/*     */   public PDMetadata getMetadata()
/*     */   {
/* 699 */     PDMetadata retval = null;
/* 700 */     COSStream stream = (COSStream)this.page.getDictionaryObject(COSName.METADATA);
/* 701 */     if (stream != null)
/*     */     {
/* 703 */       retval = new PDMetadata(stream);
/*     */     }
/* 705 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setMetadata(PDMetadata meta)
/*     */   {
/* 715 */     this.page.setItem(COSName.METADATA, meta);
/*     */   }
/*     */ 
/*     */   public BufferedImage convertToImage()
/*     */     throws IOException
/*     */   {
/* 732 */     return convertToImage(1, 144);
/*     */   }
/*     */ 
/*     */   public BufferedImage convertToImage(int imageType, int resolution)
/*     */     throws IOException
/*     */   {
/* 746 */     PDRectangle cropBox = findCropBox();
/* 747 */     float widthPt = cropBox.getWidth();
/* 748 */     float heightPt = cropBox.getHeight();
/* 749 */     float scaling = resolution / 72.0F;
/* 750 */     int widthPx = Math.round(widthPt * scaling);
/* 751 */     int heightPx = Math.round(heightPt * scaling);
/*     */ 
/* 753 */     Dimension pageDimension = new Dimension((int)widthPt, (int)heightPt);
/* 754 */     int rotationAngle = findRotation();
/*     */ 
/* 756 */     if (rotationAngle < 0)
/*     */     {
/* 758 */       rotationAngle += 360;
/*     */     }
/* 760 */     else if (rotationAngle >= 360)
/*     */     {
/* 762 */       rotationAngle -= 360;
/*     */     }
/*     */     BufferedImage retval;
/*     */     BufferedImage retval;
/* 766 */     if ((rotationAngle == 90) || (rotationAngle == 270))
/*     */     {
/* 768 */       retval = new BufferedImage(heightPx, widthPx, imageType);
/*     */     }
/*     */     else
/*     */     {
/* 772 */       retval = new BufferedImage(widthPx, heightPx, imageType);
/*     */     }
/* 774 */     Graphics2D graphics = (Graphics2D)retval.getGraphics();
/* 775 */     graphics.setBackground(TRANSPARENT_WHITE);
/* 776 */     graphics.clearRect(0, 0, retval.getWidth(), retval.getHeight());
/* 777 */     if (rotationAngle != 0)
/*     */     {
/* 779 */       int translateX = 0;
/* 780 */       int translateY = 0;
/* 781 */       switch (rotationAngle)
/*     */       {
/*     */       case 90:
/* 784 */         translateX = retval.getWidth();
/* 785 */         break;
/*     */       case 270:
/* 787 */         translateY = retval.getHeight();
/* 788 */         break;
/*     */       case 180:
/* 790 */         translateX = retval.getWidth();
/* 791 */         translateY = retval.getHeight();
/* 792 */         break;
/*     */       }
/*     */ 
/* 796 */       graphics.translate(translateX, translateY);
/* 797 */       graphics.rotate((float)Math.toRadians(rotationAngle));
/*     */     }
/* 799 */     graphics.scale(scaling, scaling);
/* 800 */     PageDrawer drawer = new PageDrawer();
/* 801 */     drawer.drawPage(graphics, this, pageDimension);
/* 802 */     drawer.dispose();
/* 803 */     graphics.dispose();
/* 804 */     return retval;
/*     */   }
/*     */ 
/*     */   public PDPageAdditionalActions getActions()
/*     */   {
/* 814 */     COSDictionary addAct = (COSDictionary)this.page.getDictionaryObject(COSName.AA);
/* 815 */     if (addAct == null)
/*     */     {
/* 817 */       addAct = new COSDictionary();
/* 818 */       this.page.setItem(COSName.AA, addAct);
/*     */     }
/* 820 */     return new PDPageAdditionalActions(addAct);
/*     */   }
/*     */ 
/*     */   public void setActions(PDPageAdditionalActions actions)
/*     */   {
/* 830 */     this.page.setItem(COSName.AA, actions);
/*     */   }
/*     */ 
/*     */   public List<PDAnnotation> getAnnotations()
/*     */     throws IOException
/*     */   {
/* 843 */     COSArray annots = (COSArray)this.page.getDictionaryObject(COSName.ANNOTS);
/*     */     COSArrayList retval;
/*     */     COSArrayList retval;
/* 844 */     if (annots == null)
/*     */     {
/* 846 */       annots = new COSArray();
/* 847 */       this.page.setItem(COSName.ANNOTS, annots);
/* 848 */       retval = new COSArrayList(new ArrayList(), annots);
/*     */     }
/*     */     else
/*     */     {
/* 852 */       List actuals = new ArrayList();
/* 853 */       for (int i = 0; i < annots.size(); i++)
/*     */       {
/* 855 */         COSBase item = annots.getObject(i);
/* 856 */         if (item == null)
/*     */         {
/* 858 */           LOG.debug("Skipped annotation due to a null reference.");
/*     */         }
/*     */         else
/* 861 */           actuals.add(PDAnnotation.createAnnotation(item));
/*     */       }
/* 863 */       retval = new COSArrayList(actuals, annots);
/*     */     }
/* 865 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setAnnotations(List<PDAnnotation> annots)
/*     */   {
/* 875 */     this.page.setItem(COSName.ANNOTS, COSArrayList.converterToCOSArray(annots));
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
/*     */     throws PrinterException
/*     */   {
/*     */     try
/*     */     {
/* 887 */       PageDrawer drawer = new PageDrawer();
/* 888 */       PDRectangle cropBox = findCropBox();
/* 889 */       drawer.drawPage(graphics, this, cropBox.createDimension());
/* 890 */       drawer.dispose();
/* 891 */       return 0;
/*     */     }
/*     */     catch (IOException io)
/*     */     {
/* 895 */       throw new PrinterIOException(io);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(Object other)
/*     */   {
/* 904 */     return ((other instanceof PDPage)) && (((PDPage)other).getCOSObject() == getCOSObject());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 912 */     return getCOSDictionary().hashCode();
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 921 */     if (this.pageResources != null)
/*     */     {
/* 923 */       this.pageResources.clear();
/*     */     }
/* 925 */     this.mediaBox = null;
/* 926 */     this.parent = null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.PDPage
 * JD-Core Version:    0.6.2
 */