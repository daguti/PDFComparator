/*     */ package org.apache.pdfbox.preflight.content;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
/*     */ import org.apache.pdfbox.pdmodel.text.PDTextState;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.font.container.FontContainer;
/*     */ import org.apache.pdfbox.preflight.font.util.GlyphException;
/*     */ import org.apache.pdfbox.util.PDFOperator;
/*     */ import org.apache.pdfbox.util.operator.OperatorProcessor;
/*     */ 
/*     */ public class ContentStreamWrapper extends ContentStreamEngine
/*     */ {
/*     */   public ContentStreamWrapper(PreflightContext _context, PDPage _page)
/*     */   {
/*  59 */     super(_context, _page);
/*     */   }
/*     */ 
/*     */   public void validPageContentStream()
/*     */     throws ValidationException
/*     */   {
/*     */     try
/*     */     {
/*  72 */       PDStream pstream = this.processeedPage.getContents();
/*  73 */       if (pstream != null)
/*     */       {
/*  75 */         processStream(this.processeedPage, this.processeedPage.findResources(), pstream.getStream());
/*     */       }
/*     */     }
/*     */     catch (ContentStreamException e)
/*     */     {
/*  80 */       this.context.addValidationError(new ValidationResult.ValidationError(e.getErrorCode(), e.getMessage()));
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  84 */       throw new ValidationException("Unable to check the ContentStream : " + e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void validXObjContentStream(PDXObjectForm xobj)
/*     */     throws ValidationException
/*     */   {
/*     */     try
/*     */     {
/*  99 */       resetEnginContext();
/* 100 */       processSubStream(this.processeedPage, xobj.getResources(), xobj.getCOSStream());
/*     */     }
/*     */     catch (ContentStreamException e)
/*     */     {
/* 104 */       this.context.addValidationError(new ValidationResult.ValidationError(e.getErrorCode(), e.getMessage()));
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 108 */       throw new ValidationException("Unable to check the ContentStream : " + e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void validPatternContentStream(COSStream pattern)
/*     */     throws ValidationException
/*     */   {
/*     */     try
/*     */     {
/* 123 */       COSDictionary res = (COSDictionary)pattern.getDictionaryObject(COSName.RESOURCES);
/* 124 */       resetEnginContext();
/* 125 */       processSubStream(this.processeedPage, new PDResources(res), pattern);
/*     */     }
/*     */     catch (ContentStreamException e)
/*     */     {
/* 129 */       this.context.addValidationError(new ValidationResult.ValidationError(e.getErrorCode(), e.getMessage()));
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 133 */       throw new ValidationException("Unable to check the ContentStream : " + e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void resetEnginContext()
/*     */   {
/* 139 */     setGraphicsState(new PDGraphicsState());
/* 140 */     setTextMatrix(null);
/* 141 */     setTextLineMatrix(null);
/* 142 */     getGraphicsStack().clear();
/*     */   }
/*     */ 
/*     */   protected void processOperator(PDFOperator operator, List arguments)
/*     */     throws IOException
/*     */   {
/* 157 */     String operation = operator.getOperation();
/* 158 */     OperatorProcessor processor = (OperatorProcessor)this.contentStreamEngineOperators.get(operation);
/* 159 */     if (processor != null)
/*     */     {
/* 161 */       processor.setContext(this);
/* 162 */       processor.process(operator, arguments);
/*     */     }
/*     */     else
/*     */     {
/* 166 */       registerError("The operator \"" + operation + "\" isn't supported.", "1.2.10");
/*     */ 
/* 168 */       return;
/*     */     }
/*     */ 
/* 174 */     if ("BI".equals(operation))
/*     */     {
/* 176 */       validImageFilter(operator);
/* 177 */       validImageColorSpace(operator);
/*     */     }
/*     */ 
/* 180 */     checkShowTextOperators(operator, arguments);
/* 181 */     checkColorOperators(operation);
/* 182 */     validRenderingIntent(operator, arguments);
/* 183 */     checkSetColorSpaceOperators(operator, arguments);
/* 184 */     validNumberOfGraphicStates(operator);
/*     */   }
/*     */ 
/*     */   protected void checkShowTextOperators(PDFOperator operator, List<?> arguments)
/*     */     throws ContentStreamException, IOException
/*     */   {
/* 200 */     String op = operator.getOperation();
/* 201 */     if (("Tj".equals(op)) || ("'".equals(op)) || ("\"".equals(op)))
/*     */     {
/* 203 */       validStringDefinition(operator, arguments);
/*     */     }
/*     */ 
/* 206 */     if ("TJ".equals(op))
/*     */     {
/* 208 */       validStringArray(operator, arguments);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void validStringDefinition(PDFOperator operator, List<?> arguments)
/*     */     throws ContentStreamException, IOException
/*     */   {
/* 229 */     if ("\"".equals(operator.getOperation()))
/*     */     {
/* 231 */       if (arguments.size() != 3)
/*     */       {
/* 233 */         registerError("Invalid argument for the operator : " + operator.getOperation(), "1.2.11");
/*     */ 
/* 235 */         return;
/*     */       }
/* 237 */       Object arg0 = arguments.get(0);
/* 238 */       Object arg1 = arguments.get(1);
/* 239 */       Object arg2 = arguments.get(2);
/* 240 */       if (((!(arg0 instanceof COSInteger)) && (!(arg0 instanceof COSFloat))) || ((!(arg1 instanceof COSInteger)) && (!(arg1 instanceof COSFloat))))
/*     */       {
/* 243 */         registerError("Invalid argument for the operator : " + operator.getOperation(), "1.2.11");
/*     */ 
/* 245 */         return;
/*     */       }
/*     */ 
/* 248 */       if ((arg2 instanceof COSString))
/*     */       {
/* 250 */         validText(((COSString)arg2).getBytes());
/*     */       }
/*     */       else
/*     */       {
/* 254 */         registerError("Invalid argument for the operator : " + operator.getOperation(), "1.2.11");
/*     */ 
/* 256 */         return;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 261 */       Object objStr = arguments.get(0);
/* 262 */       if ((objStr instanceof COSString))
/*     */       {
/* 264 */         validText(((COSString)objStr).getBytes());
/*     */       }
/* 266 */       else if (!(objStr instanceof COSInteger))
/*     */       {
/* 268 */         registerError("Invalid argument for the operator : " + operator.getOperation(), "1.2.11");
/*     */ 
/* 270 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void validStringArray(PDFOperator operator, List<?> arguments)
/*     */     throws ContentStreamException, IOException
/*     */   {
/* 288 */     for (Iterator i$ = arguments.iterator(); i$.hasNext(); ) { Object object = i$.next();
/*     */ 
/* 290 */       if ((object instanceof COSArray))
/*     */       {
/* 292 */         validStringArray(operator, ((COSArray)object).toList());
/*     */       }
/* 294 */       else if ((object instanceof COSString))
/*     */       {
/* 296 */         validText(((COSString)object).getBytes());
/*     */       }
/* 298 */       else if ((!(object instanceof COSInteger)) && (!(object instanceof COSFloat)))
/*     */       {
/* 300 */         registerError("Invalid argument for the operator : " + operator.getOperation(), "1.2.11");
/*     */ 
/* 302 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void validText(byte[] string)
/*     */     throws IOException
/*     */   {
/* 321 */     PDTextState textState = getGraphicsState().getTextState();
/* 322 */     int renderingMode = textState.getRenderingMode();
/* 323 */     PDFont font = textState.getFont();
/* 324 */     if (font == null)
/*     */     {
/* 327 */       registerError("Text operator can't be process without Font", "3.3.2");
/* 328 */       return;
/*     */     }
/*     */ 
/* 331 */     FontContainer fontContainer = this.context.getFontContainer(font.getCOSObject());
/* 332 */     if ((renderingMode == 3) && ((fontContainer == null) || (!fontContainer.isEmbeddedFont())))
/*     */     {
/* 335 */       return;
/*     */     }
/* 337 */     if (fontContainer == null)
/*     */     {
/* 340 */       registerError(font.getBaseFont() + " is unknown wasn't found by the FontHelperValdiator", "3.3.2");
/*     */ 
/* 342 */       return;
/*     */     }
/* 344 */     if ((!fontContainer.isValid()) && (!fontContainer.errorsAleadyMerged()))
/*     */     {
/* 346 */       this.context.addValidationErrors(fontContainer.getAllErrors());
/* 347 */       fontContainer.setErrorsAleadyMerged(true);
/* 348 */       return;
/*     */     }
/* 350 */     if ((!fontContainer.isValid()) && (fontContainer.errorsAleadyMerged()))
/*     */     {
/* 353 */       return;
/*     */     }
/*     */ 
/* 356 */     int codeLength = 1;
/* 357 */     for (int i = 0; i < string.length; i += codeLength)
/*     */     {
/* 360 */       int cid = -1;
/* 361 */       codeLength = 1;
/*     */       try
/*     */       {
/* 365 */         cid = font.encodeToCID(string, i, codeLength);
/* 366 */         if ((cid == -1) && (i + 1 < string.length))
/*     */         {
/* 369 */           codeLength++;
/* 370 */           cid = font.encodeToCID(string, i, codeLength);
/*     */         }
/* 372 */         fontContainer.checkGlyphWith(cid);
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 376 */         registerError("Encoding can't interpret the character code", "3.1.12");
/* 377 */         return;
/*     */       }
/*     */       catch (GlyphException e)
/*     */       {
/* 381 */         if (renderingMode != 3)
/*     */         {
/* 383 */           registerError(e.getMessage(), e.getErrorCode());
/* 384 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.content.ContentStreamWrapper
 * JD-Core Version:    0.6.2
 */