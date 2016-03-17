/*     */ package org.apache.pdfbox.preflight.font;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.encoding.DictionaryEncoding;
/*     */ import org.apache.pdfbox.encoding.Encoding;
/*     */ import org.apache.pdfbox.encoding.EncodingManager;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFontFactory;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.PreflightPath;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.content.ContentStreamException;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.font.container.FontContainer;
/*     */ import org.apache.pdfbox.preflight.font.container.Type3Container;
/*     */ import org.apache.pdfbox.preflight.font.util.GlyphException;
/*     */ import org.apache.pdfbox.preflight.font.util.PDFAType3StreamParser;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ import org.apache.pdfbox.preflight.utils.ContextHelper;
/*     */ 
/*     */ public class Type3FontValidator extends FontValidator<Type3Container>
/*     */ {
/*     */   protected COSDictionary fontDictionary;
/*     */   protected COSDocument cosDocument;
/*     */   protected Encoding encoding;
/*     */ 
/*     */   public Type3FontValidator(PreflightContext context, PDFont font)
/*     */   {
/*  68 */     super(context, font, new Type3Container(font));
/*  69 */     this.cosDocument = context.getDocument().getDocument();
/*  70 */     this.fontDictionary = ((COSDictionary)font.getCOSObject());
/*     */   }
/*     */ 
/*     */   public void validate() throws ValidationException
/*     */   {
/*  75 */     checkMandatoryField();
/*  76 */     checkFontBBox();
/*  77 */     checkFontMatrix();
/*  78 */     checkEncoding();
/*  79 */     checkResources();
/*  80 */     checkCharProcsAndMetrics();
/*  81 */     checkToUnicode();
/*     */   }
/*     */ 
/*     */   protected void checkMandatoryField()
/*     */   {
/*  86 */     boolean areFieldsPResent = this.fontDictionary.containsKey(COSName.FONT_BBOX);
/*  87 */     areFieldsPResent &= this.fontDictionary.containsKey(COSName.FONT_MATRIX);
/*  88 */     areFieldsPResent &= this.fontDictionary.containsKey(COSName.CHAR_PROCS);
/*  89 */     areFieldsPResent &= this.fontDictionary.containsKey(COSName.ENCODING);
/*  90 */     areFieldsPResent &= this.fontDictionary.containsKey(COSName.FIRST_CHAR);
/*  91 */     areFieldsPResent &= this.fontDictionary.containsKey(COSName.LAST_CHAR);
/*  92 */     areFieldsPResent &= this.fontDictionary.containsKey(COSName.WIDTHS);
/*     */ 
/*  94 */     if (!areFieldsPResent)
/*     */     {
/*  96 */       ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.1", "Some required fields are missing from the Font dictionary."));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkFontBBox()
/*     */   {
/* 106 */     COSBase fontBBox = this.fontDictionary.getItem(COSName.FONT_BBOX);
/*     */ 
/* 108 */     if (!COSUtils.isArray(fontBBox, this.cosDocument))
/*     */     {
/* 110 */       ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.1", "The FontBBox element isn't an array"));
/*     */ 
/* 112 */       return;
/*     */     }
/*     */ 
/* 118 */     COSArray bbox = COSUtils.getAsArray(fontBBox, this.cosDocument);
/* 119 */     if (bbox.size() != 4)
/*     */     {
/* 121 */       ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.1", "The FontBBox element is invalid"));
/*     */ 
/* 123 */       return;
/*     */     }
/*     */ 
/* 127 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 129 */       COSBase elt = bbox.get(i);
/* 130 */       if ((!COSUtils.isFloat(elt, this.cosDocument)) && (!COSUtils.isInteger(elt, this.cosDocument)))
/*     */       {
/* 132 */         ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.1", "An element of FontBBox isn't a number"));
/*     */ 
/* 134 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkFontMatrix()
/*     */   {
/* 145 */     COSBase fontMatrix = this.fontDictionary.getItem(COSName.FONT_MATRIX);
/*     */ 
/* 147 */     if (!COSUtils.isArray(fontMatrix, this.cosDocument))
/*     */     {
/* 149 */       ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.1", "The FontMatrix element isn't an array"));
/*     */ 
/* 151 */       return;
/*     */     }
/*     */ 
/* 157 */     COSArray matrix = COSUtils.getAsArray(fontMatrix, this.cosDocument);
/* 158 */     if (matrix.size() != 6)
/*     */     {
/* 160 */       ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.1", "The FontMatrix element is invalid"));
/*     */ 
/* 162 */       return;
/*     */     }
/*     */ 
/* 166 */     for (int i = 0; i < 6; i++)
/*     */     {
/* 168 */       COSBase elt = matrix.get(i);
/* 169 */       if ((!COSUtils.isFloat(elt, this.cosDocument)) && (!COSUtils.isInteger(elt, this.cosDocument)))
/*     */       {
/* 171 */         ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.1", "An element of FontMatrix isn't a number"));
/*     */ 
/* 173 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkEncoding()
/*     */   {
/* 200 */     COSBase fontEncoding = this.fontDictionary.getItem(COSName.ENCODING);
/* 201 */     if (COSUtils.isString(fontEncoding, this.cosDocument))
/*     */     {
/* 203 */       checkEncodingAsString(fontEncoding);
/*     */     }
/* 205 */     else if (COSUtils.isDictionary(fontEncoding, this.cosDocument))
/*     */     {
/* 207 */       checkEncodingAsDictionary(fontEncoding);
/*     */     }
/*     */     else
/*     */     {
/* 212 */       ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.2.4", "The Encoding entry doesn't have the right type"));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkEncodingAsString(COSBase fontEncoding)
/*     */   {
/* 225 */     EncodingManager emng = new EncodingManager();
/*     */ 
/* 227 */     String enc = COSUtils.getAsString(fontEncoding, this.cosDocument);
/*     */     try
/*     */     {
/* 230 */       this.encoding = emng.getEncoding(COSName.getPDFName(enc));
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 235 */       ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.5"));
/* 236 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkEncodingAsDictionary(COSBase fontEncoding)
/*     */   {
/* 252 */     COSDictionary encodingDictionary = COSUtils.getAsDictionary(fontEncoding, this.cosDocument);
/*     */     try
/*     */     {
/* 255 */       this.encoding = new DictionaryEncoding(encodingDictionary);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 260 */       ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.5"));
/* 261 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkCharProcsAndMetrics()
/*     */     throws ValidationException
/*     */   {
/* 279 */     List widths = this.font.getWidths();
/* 280 */     if ((widths == null) || (widths.isEmpty()))
/*     */     {
/* 282 */       ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.1", "The Witdhs array is unreachable"));
/*     */ 
/* 284 */       return;
/*     */     }
/*     */ 
/* 287 */     COSDictionary charProcs = COSUtils.getAsDictionary(this.fontDictionary.getItem(COSName.CHAR_PROCS), this.cosDocument);
/* 288 */     if (charProcs == null)
/*     */     {
/* 290 */       ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.1", "The CharProcs element isn't a dictionary"));
/*     */ 
/* 292 */       return;
/*     */     }
/*     */ 
/* 295 */     int fc = this.font.getFirstChar();
/* 296 */     int lc = this.font.getLastChar();
/*     */ 
/* 303 */     int expectedLength = lc - fc + 1;
/* 304 */     if (widths.size() != expectedLength)
/*     */     {
/* 306 */       ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.1", "The length of Witdhs array is invalid. Expected : \"" + expectedLength + "\" Current : \"" + widths.size() + "\""));
/*     */ 
/* 309 */       return;
/*     */     }
/*     */ 
/* 313 */     PDResources pResources = getPDResources();
/* 314 */     for (int i = 0; i < expectedLength; i++)
/*     */     {
/* 316 */       int cid = fc + i;
/* 317 */       float width = ((Float)widths.get(i)).floatValue();
/*     */ 
/* 319 */       COSStream charStream = getCharacterStreamDescription(cid, charProcs);
/*     */ 
/* 321 */       if (charStream != null)
/*     */       {
/*     */         try
/*     */         {
/* 326 */           float fontProgamWidth = getWidthFromCharacterStream(pResources, charStream);
/* 327 */           if (width == fontProgamWidth)
/*     */           {
/* 330 */             ((Type3Container)this.fontContainer).markCIDAsValid(cid);
/*     */           }
/*     */           else
/*     */           {
/* 334 */             GlyphException glyphEx = new GlyphException("3.1.6", cid, "The character with CID\"" + cid + "\" should have a width equals to " + width);
/*     */ 
/* 336 */             ((Type3Container)this.fontContainer).markCIDAsInvalid(cid, glyphEx);
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (ContentStreamException e)
/*     */         {
/* 343 */           this.context.addValidationError(new ValidationResult.ValidationError(e.getErrorCode(), e.getMessage()));
/*     */ 
/* 345 */           return;
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 349 */           ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.2.4", "The CharProcs references an element which can't be read"));
/*     */ 
/* 351 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private PDResources getPDResources()
/*     */   {
/* 359 */     COSBase res = this.fontDictionary.getItem(COSName.RESOURCES);
/* 360 */     PDResources pResources = null;
/* 361 */     COSDictionary resAsDict = COSUtils.getAsDictionary(res, this.cosDocument);
/* 362 */     if (resAsDict != null)
/*     */     {
/* 364 */       pResources = new PDResources(resAsDict);
/*     */     }
/* 366 */     return pResources;
/*     */   }
/*     */ 
/*     */   private COSStream getCharacterStreamDescription(int cid, COSDictionary charProcs) throws ValidationException
/*     */   {
/* 371 */     String charName = getCharNameFromEncoding(cid);
/* 372 */     COSBase item = charProcs.getItem(COSName.getPDFName(charName));
/* 373 */     COSStream charStream = COSUtils.getAsStream(item, this.cosDocument);
/* 374 */     if (charStream == null)
/*     */     {
/* 380 */       GlyphException glyphEx = new GlyphException("3.1.6", cid, "The CharProcs \"" + charName + "\" doesn't exist");
/*     */ 
/* 382 */       ((Type3Container)this.fontContainer).markCIDAsInvalid(cid, glyphEx);
/*     */     }
/* 384 */     return charStream;
/*     */   }
/*     */ 
/*     */   private String getCharNameFromEncoding(int cid) throws ValidationException
/*     */   {
/*     */     try
/*     */     {
/* 391 */       return this.encoding.getName(cid);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 396 */       throw new ValidationException("Unable to check Widths consistency", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private float getWidthFromCharacterStream(PDResources resources, COSStream charStream)
/*     */     throws IOException
/*     */   {
/* 407 */     PreflightPath vPath = this.context.getValidationPath();
/* 408 */     PDFAType3StreamParser parser = new PDFAType3StreamParser(this.context, (PDPage)vPath.getClosestPathElement(PDPage.class));
/* 409 */     parser.resetEngine();
/* 410 */     parser.processSubStream(null, resources, charStream);
/* 411 */     return parser.getWidth();
/*     */   }
/*     */ 
/*     */   private void checkResources()
/*     */     throws ValidationException
/*     */   {
/* 422 */     COSBase resources = this.fontDictionary.getItem(COSName.RESOURCES);
/*     */     COSDictionary dicFonts;
/* 423 */     if (resources != null)
/*     */     {
/* 426 */       COSDictionary dictionary = COSUtils.getAsDictionary(resources, this.cosDocument);
/* 427 */       if (dictionary == null)
/*     */       {
/* 429 */         ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.1", "The Resources element isn't a dictionary"));
/*     */ 
/* 431 */         return;
/*     */       }
/*     */ 
/* 435 */       ContextHelper.validateElement(this.context, new PDResources(dictionary), "resources-process");
/* 436 */       COSBase cbFont = dictionary.getItem(COSName.FONT);
/*     */ 
/* 438 */       if (cbFont != null)
/*     */       {
/* 443 */         dicFonts = COSUtils.getAsDictionary(cbFont, this.cosDocument);
/* 444 */         Set keyList = dicFonts.keySet();
/* 445 */         for (Object key : keyList)
/*     */         {
/* 448 */           COSBase item = dicFonts.getItem((COSName)key);
/* 449 */           COSDictionary xObjFont = COSUtils.getAsDictionary(item, this.cosDocument);
/*     */           try
/*     */           {
/* 453 */             PDFont aFont = PDFontFactory.createFont(xObjFont);
/* 454 */             FontContainer aContainer = this.context.getFontContainer(aFont.getCOSObject());
/*     */ 
/* 456 */             if (!aContainer.isValid())
/*     */             {
/* 458 */               ((Type3Container)this.fontContainer).push(new ValidationResult.ValidationError("3.2.4", "The Resources dictionary of type 3 font contains invalid font"));
/*     */             }
/*     */ 
/*     */           }
/*     */           catch (IOException e)
/*     */           {
/* 464 */             this.context.addValidationError(new ValidationResult.ValidationError("3.2", "Unable to valid the Type3 : " + e.getMessage()));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.Type3FontValidator
 * JD-Core Version:    0.6.2
 */