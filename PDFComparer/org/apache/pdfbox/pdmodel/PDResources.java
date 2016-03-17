/*     */ package org.apache.pdfbox.pdmodel;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSDictionaryMap;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFontFactory;
/*     */ import org.apache.pdfbox.pdmodel.graphics.PDExtendedGraphicsState;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpaceFactory;
/*     */ import org.apache.pdfbox.pdmodel.graphics.pattern.PDPatternResources;
/*     */ import org.apache.pdfbox.pdmodel.graphics.shading.PDShadingResources;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
/*     */ import org.apache.pdfbox.pdmodel.markedcontent.PDPropertyList;
/*     */ import org.apache.pdfbox.util.MapUtil;
/*     */ 
/*     */ public class PDResources
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary resources;
/*  53 */   private Map<String, PDFont> fonts = null;
/*  54 */   private Map<PDFont, String> fontMappings = new HashMap();
/*  55 */   private Map<String, PDColorSpace> colorspaces = null;
/*  56 */   private Map<String, PDXObject> xobjects = null;
/*  57 */   private Map<PDXObject, String> xobjectMappings = null;
/*  58 */   private HashMap<String, PDXObjectImage> images = null;
/*  59 */   private Map<String, PDExtendedGraphicsState> graphicsStates = null;
/*  60 */   private Map<String, PDPatternResources> patterns = null;
/*  61 */   private Map<String, PDShadingResources> shadings = null;
/*     */ 
/*  66 */   private static final Log LOG = LogFactory.getLog(PDResources.class);
/*     */ 
/*     */   public PDResources()
/*     */   {
/*  73 */     this.resources = new COSDictionary();
/*     */   }
/*     */ 
/*     */   public PDResources(COSDictionary resourceDictionary)
/*     */   {
/*  83 */     this.resources = resourceDictionary;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  93 */     return this.resources;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 103 */     return this.resources;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 112 */     if (this.fonts != null)
/*     */     {
/* 114 */       for (PDFont font : this.fonts.values())
/*     */       {
/* 116 */         font.clear();
/*     */       }
/* 118 */       this.fonts.clear();
/* 119 */       this.fonts = null;
/*     */     }
/* 121 */     if (this.fontMappings != null)
/*     */     {
/* 123 */       this.fontMappings.clear();
/* 124 */       this.fontMappings = null;
/*     */     }
/* 126 */     if (this.colorspaces != null)
/*     */     {
/* 128 */       this.colorspaces.clear();
/* 129 */       this.colorspaces = null;
/*     */     }
/* 131 */     if (this.xobjects != null)
/*     */     {
/* 133 */       for (PDXObject xobject : this.xobjects.values())
/*     */       {
/* 135 */         xobject.clear();
/*     */       }
/* 137 */       this.xobjects.clear();
/* 138 */       this.xobjects = null;
/*     */     }
/* 140 */     if (this.xobjectMappings != null)
/*     */     {
/* 142 */       this.xobjectMappings.clear();
/* 143 */       this.xobjectMappings = null;
/*     */     }
/* 145 */     if (this.images != null)
/*     */     {
/* 147 */       this.images.clear();
/* 148 */       this.images = null;
/*     */     }
/* 150 */     if (this.graphicsStates != null)
/*     */     {
/* 152 */       this.graphicsStates.clear();
/* 153 */       this.graphicsStates = null;
/*     */     }
/* 155 */     if (this.patterns != null)
/*     */     {
/* 157 */       this.patterns.clear();
/* 158 */       this.patterns = null;
/*     */     }
/* 160 */     if (this.shadings != null)
/*     */     {
/* 162 */       this.shadings.clear();
/* 163 */       this.shadings = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Map<String, PDFont> getFonts(Map<String, PDFont> fontCache)
/*     */     throws IOException
/*     */   {
/* 180 */     return getFonts();
/*     */   }
/*     */ 
/*     */   public Map<String, PDFont> getFonts()
/*     */   {
/* 190 */     if (this.fonts == null)
/*     */     {
/* 194 */       this.fonts = new HashMap();
/* 195 */       COSDictionary fontsDictionary = (COSDictionary)this.resources.getDictionaryObject(COSName.FONT);
/* 196 */       if (fontsDictionary == null)
/*     */       {
/* 198 */         fontsDictionary = new COSDictionary();
/* 199 */         this.resources.setItem(COSName.FONT, fontsDictionary);
/*     */       }
/*     */       else
/*     */       {
/* 203 */         for (COSName fontName : fontsDictionary.keySet())
/*     */         {
/* 205 */           COSBase font = fontsDictionary.getDictionaryObject(fontName);
/*     */ 
/* 208 */           if ((font instanceof COSDictionary))
/*     */           {
/* 210 */             PDFont newFont = null;
/*     */             try
/*     */             {
/* 213 */               newFont = PDFontFactory.createFont((COSDictionary)font);
/*     */             }
/*     */             catch (IOException exception)
/*     */             {
/* 217 */               LOG.error("error while creating a font", exception);
/*     */             }
/* 219 */             if (newFont != null)
/*     */             {
/* 221 */               this.fonts.put(fontName.getName(), newFont);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 226 */       setFonts(this.fonts);
/*     */     }
/* 228 */     return this.fonts;
/*     */   }
/*     */ 
/*     */   public Map<String, PDXObject> getXObjects()
/*     */   {
/* 238 */     if (this.xobjects == null)
/*     */     {
/* 242 */       this.xobjects = new HashMap();
/* 243 */       COSDictionary xobjectsDictionary = (COSDictionary)this.resources.getDictionaryObject(COSName.XOBJECT);
/* 244 */       if (xobjectsDictionary == null)
/*     */       {
/* 246 */         xobjectsDictionary = new COSDictionary();
/* 247 */         this.resources.setItem(COSName.XOBJECT, xobjectsDictionary);
/*     */       }
/*     */       else
/*     */       {
/* 251 */         this.xobjects = new HashMap();
/* 252 */         for (COSName objName : xobjectsDictionary.keySet())
/*     */         {
/* 254 */           PDXObject xobject = null;
/*     */           try
/*     */           {
/* 257 */             xobject = PDXObject.createXObject(xobjectsDictionary.getDictionaryObject(objName));
/*     */           }
/*     */           catch (IOException exception)
/*     */           {
/* 261 */             LOG.error("error while creating a xobject", exception);
/*     */           }
/* 263 */           if (xobject != null)
/*     */           {
/* 265 */             this.xobjects.put(objName.getName(), xobject);
/*     */           }
/*     */         }
/*     */       }
/* 269 */       setXObjects(this.xobjects);
/*     */     }
/* 271 */     return this.xobjects;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Map<String, PDXObjectImage> getImages()
/*     */     throws IOException
/*     */   {
/* 285 */     if (this.images == null)
/*     */     {
/* 287 */       Map allXObjects = getXObjects();
/* 288 */       this.images = new HashMap();
/* 289 */       for (Map.Entry entry : allXObjects.entrySet())
/*     */       {
/* 291 */         PDXObject xobject = (PDXObject)entry.getValue();
/* 292 */         if ((xobject instanceof PDXObjectImage))
/*     */         {
/* 294 */           this.images.put(entry.getKey(), (PDXObjectImage)xobject);
/*     */         }
/*     */       }
/*     */     }
/* 298 */     return this.images;
/*     */   }
/*     */ 
/*     */   public void setFonts(Map<String, PDFont> fontsValue)
/*     */   {
/* 308 */     this.fonts = fontsValue;
/* 309 */     if (fontsValue != null)
/*     */     {
/* 311 */       this.resources.setItem(COSName.FONT, COSDictionaryMap.convert(fontsValue));
/* 312 */       this.fontMappings = reverseMap(fontsValue, PDFont.class);
/*     */     }
/*     */     else
/*     */     {
/* 316 */       this.resources.removeItem(COSName.FONT);
/* 317 */       this.fontMappings = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setXObjects(Map<String, PDXObject> xobjectsValue)
/*     */   {
/* 328 */     this.xobjects = xobjectsValue;
/* 329 */     if (xobjectsValue != null)
/*     */     {
/* 331 */       this.resources.setItem(COSName.XOBJECT, COSDictionaryMap.convert(xobjectsValue));
/* 332 */       this.xobjectMappings = reverseMap(this.xobjects, PDXObject.class);
/*     */     }
/*     */     else
/*     */     {
/* 336 */       this.resources.removeItem(COSName.XOBJECT);
/* 337 */       this.xobjectMappings = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map<String, PDColorSpace> getColorSpaces()
/*     */   {
/*     */     COSDictionary csDictionary;
/* 349 */     if (this.colorspaces == null)
/*     */     {
/* 351 */       csDictionary = (COSDictionary)this.resources.getDictionaryObject(COSName.COLORSPACE);
/* 352 */       if (csDictionary != null)
/*     */       {
/* 354 */         this.colorspaces = new HashMap();
/* 355 */         for (COSName csName : csDictionary.keySet())
/*     */         {
/* 357 */           COSBase cs = csDictionary.getDictionaryObject(csName);
/* 358 */           PDColorSpace colorspace = null;
/*     */           try
/*     */           {
/* 361 */             colorspace = PDColorSpaceFactory.createColorSpace(cs);
/*     */           }
/*     */           catch (IOException exception)
/*     */           {
/* 365 */             LOG.error("error while creating a colorspace", exception);
/*     */           }
/* 367 */           if (colorspace != null)
/*     */           {
/* 369 */             this.colorspaces.put(csName.getName(), colorspace);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 374 */     return this.colorspaces;
/*     */   }
/*     */ 
/*     */   public void setColorSpaces(Map<String, PDColorSpace> csValue)
/*     */   {
/* 384 */     this.colorspaces = csValue;
/* 385 */     if (csValue != null)
/*     */     {
/* 387 */       this.resources.setItem(COSName.COLORSPACE, COSDictionaryMap.convert(csValue));
/*     */     }
/*     */     else
/*     */     {
/* 391 */       this.resources.removeItem(COSName.COLORSPACE);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map<String, PDExtendedGraphicsState> getGraphicsStates()
/*     */   {
/*     */     COSDictionary states;
/* 404 */     if (this.graphicsStates == null)
/*     */     {
/* 406 */       states = (COSDictionary)this.resources.getDictionaryObject(COSName.EXT_G_STATE);
/* 407 */       if (states != null)
/*     */       {
/* 409 */         this.graphicsStates = new HashMap();
/* 410 */         for (COSName name : states.keySet())
/*     */         {
/* 412 */           COSDictionary dictionary = (COSDictionary)states.getDictionaryObject(name);
/* 413 */           this.graphicsStates.put(name.getName(), new PDExtendedGraphicsState(dictionary));
/*     */         }
/*     */       }
/*     */     }
/* 417 */     return this.graphicsStates;
/*     */   }
/*     */ 
/*     */   public void setGraphicsStates(Map<String, PDExtendedGraphicsState> states)
/*     */   {
/* 427 */     this.graphicsStates = states;
/* 428 */     if (states != null)
/*     */     {
/* 430 */       Iterator iter = states.keySet().iterator();
/* 431 */       COSDictionary dic = new COSDictionary();
/* 432 */       while (iter.hasNext())
/*     */       {
/* 434 */         String name = (String)iter.next();
/* 435 */         PDExtendedGraphicsState state = (PDExtendedGraphicsState)states.get(name);
/* 436 */         dic.setItem(COSName.getPDFName(name), state.getCOSObject());
/*     */       }
/* 438 */       this.resources.setItem(COSName.EXT_G_STATE, dic);
/*     */     }
/*     */     else
/*     */     {
/* 442 */       this.resources.removeItem(COSName.EXT_G_STATE);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDPropertyList getProperties()
/*     */   {
/* 453 */     PDPropertyList retval = null;
/* 454 */     COSDictionary props = (COSDictionary)this.resources.getDictionaryObject(COSName.PROPERTIES);
/*     */ 
/* 456 */     if (props != null)
/*     */     {
/* 458 */       retval = new PDPropertyList(props);
/*     */     }
/* 460 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setProperties(PDPropertyList props)
/*     */   {
/* 470 */     this.resources.setItem(COSName.PROPERTIES, props.getCOSObject());
/*     */   }
/*     */ 
/*     */   public Map<String, PDPatternResources> getPatterns()
/*     */     throws IOException
/*     */   {
/*     */     COSDictionary patternsDictionary;
/* 483 */     if (this.patterns == null)
/*     */     {
/* 485 */       patternsDictionary = (COSDictionary)this.resources.getDictionaryObject(COSName.PATTERN);
/* 486 */       if (patternsDictionary != null)
/*     */       {
/* 488 */         this.patterns = new HashMap();
/* 489 */         for (COSName name : patternsDictionary.keySet())
/*     */         {
/* 491 */           COSDictionary dictionary = (COSDictionary)patternsDictionary.getDictionaryObject(name);
/* 492 */           this.patterns.put(name.getName(), PDPatternResources.create(dictionary));
/*     */         }
/*     */       }
/*     */     }
/* 496 */     return this.patterns;
/*     */   }
/*     */ 
/*     */   public void setPatterns(Map<String, PDPatternResources> patternsValue)
/*     */   {
/* 506 */     this.patterns = patternsValue;
/* 507 */     if (patternsValue != null)
/*     */     {
/* 509 */       Iterator iter = patternsValue.keySet().iterator();
/* 510 */       COSDictionary dic = new COSDictionary();
/* 511 */       while (iter.hasNext())
/*     */       {
/* 513 */         String name = (String)iter.next();
/* 514 */         PDPatternResources pattern = (PDPatternResources)patternsValue.get(name);
/* 515 */         dic.setItem(COSName.getPDFName(name), pattern.getCOSObject());
/*     */       }
/* 517 */       this.resources.setItem(COSName.PATTERN, dic);
/*     */     }
/*     */     else
/*     */     {
/* 521 */       this.resources.removeItem(COSName.PATTERN);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map<String, PDShadingResources> getShadings()
/*     */     throws IOException
/*     */   {
/*     */     COSDictionary shadingsDictionary;
/* 535 */     if (this.shadings == null)
/*     */     {
/* 537 */       shadingsDictionary = (COSDictionary)this.resources.getDictionaryObject(COSName.SHADING);
/* 538 */       if (shadingsDictionary != null)
/*     */       {
/* 540 */         this.shadings = new HashMap();
/* 541 */         for (COSName name : shadingsDictionary.keySet())
/*     */         {
/* 543 */           COSDictionary dictionary = (COSDictionary)shadingsDictionary.getDictionaryObject(name);
/* 544 */           this.shadings.put(name.getName(), PDShadingResources.create(dictionary));
/*     */         }
/*     */       }
/*     */     }
/* 548 */     return this.shadings;
/*     */   }
/*     */ 
/*     */   public void setShadings(Map<String, PDShadingResources> shadingsValue)
/*     */   {
/* 558 */     this.shadings = shadingsValue;
/* 559 */     if (shadingsValue != null)
/*     */     {
/* 561 */       Iterator iter = shadingsValue.keySet().iterator();
/* 562 */       COSDictionary dic = new COSDictionary();
/* 563 */       while (iter.hasNext())
/*     */       {
/* 565 */         String name = (String)iter.next();
/* 566 */         PDShadingResources shading = (PDShadingResources)shadingsValue.get(name);
/* 567 */         dic.setItem(COSName.getPDFName(name), shading.getCOSObject());
/*     */       }
/* 569 */       this.resources.setItem(COSName.SHADING, dic);
/*     */     }
/*     */     else
/*     */     {
/* 573 */       this.resources.removeItem(COSName.SHADING);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String addFont(PDFont font)
/*     */   {
/* 586 */     return addFont(font, MapUtil.getNextUniqueKey(getFonts(), "F"));
/*     */   }
/*     */ 
/*     */   public String addFont(PDFont font, String fontKey)
/*     */   {
/* 598 */     if (this.fonts == null)
/*     */     {
/* 601 */       getFonts();
/*     */     }
/*     */ 
/* 604 */     String fontMapping = (String)this.fontMappings.get(font);
/* 605 */     if (fontMapping == null)
/*     */     {
/* 607 */       fontMapping = fontKey;
/* 608 */       this.fontMappings.put(font, fontMapping);
/* 609 */       this.fonts.put(fontMapping, font);
/* 610 */       addFontToDictionary(font, fontMapping);
/*     */     }
/* 612 */     return fontMapping;
/*     */   }
/*     */ 
/*     */   private void addFontToDictionary(PDFont font, String fontName)
/*     */   {
/* 617 */     COSDictionary fontsDictionary = (COSDictionary)this.resources.getDictionaryObject(COSName.FONT);
/* 618 */     fontsDictionary.setItem(fontName, font);
/*     */   }
/*     */ 
/*     */   public String addXObject(PDXObject xobject, String prefix)
/*     */   {
/* 631 */     if (this.xobjects == null)
/*     */     {
/* 634 */       getXObjects();
/*     */     }
/* 636 */     String objMapping = (String)this.xobjectMappings.get(xobject);
/* 637 */     if (objMapping == null)
/*     */     {
/* 639 */       objMapping = MapUtil.getNextUniqueKey(this.xobjects, prefix);
/* 640 */       this.xobjectMappings.put(xobject, objMapping);
/* 641 */       this.xobjects.put(objMapping, xobject);
/* 642 */       addXObjectToDictionary(xobject, objMapping);
/*     */     }
/* 644 */     return objMapping;
/*     */   }
/*     */ 
/*     */   private void addXObjectToDictionary(PDXObject xobject, String xobjectName)
/*     */   {
/* 649 */     COSDictionary xobjectsDictionary = (COSDictionary)this.resources.getDictionaryObject(COSName.XOBJECT);
/* 650 */     xobjectsDictionary.setItem(xobjectName, xobject);
/*     */   }
/*     */ 
/*     */   private <T> Map<T, String> reverseMap(Map<String, T> map, Class<T> keyClass)
/*     */   {
/* 655 */     Map reversed = new HashMap();
/* 656 */     for (Map.Entry entry : map.entrySet())
/*     */     {
/* 658 */       reversed.put(keyClass.cast(entry.getValue()), (String)entry.getKey());
/*     */     }
/* 660 */     return reversed;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.PDResources
 * JD-Core Version:    0.6.2
 */