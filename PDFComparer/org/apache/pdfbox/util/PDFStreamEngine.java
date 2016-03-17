/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.Stack;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.exceptions.WrappedIOException;
/*     */ import org.apache.pdfbox.pdfparser.PDFStreamParser;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.PDMatrix;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.pdmodel.font.PDType3Font;
/*     */ import org.apache.pdfbox.pdmodel.graphics.PDExtendedGraphicsState;
/*     */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
/*     */ import org.apache.pdfbox.pdmodel.text.PDTextState;
/*     */ import org.apache.pdfbox.util.operator.OperatorProcessor;
/*     */ 
/*     */ public class PDFStreamEngine
/*     */ {
/*  69 */   private static final Log LOG = LogFactory.getLog(PDFStreamEngine.class);
/*     */ 
/*  74 */   private final Set<String> unsupportedOperators = new HashSet();
/*     */ 
/*  76 */   private PDGraphicsState graphicsState = null;
/*     */ 
/*  78 */   private Matrix textMatrix = null;
/*  79 */   private Matrix textLineMatrix = null;
/*  80 */   private Stack<PDGraphicsState> graphicsStack = new Stack();
/*     */ 
/*  82 */   private Map<String, OperatorProcessor> operators = new HashMap();
/*     */ 
/*  84 */   private Stack<PDResources> streamResourcesStack = new Stack();
/*     */   private PDPage page;
/*     */   private int validCharCnt;
/*     */   private int totalCharCnt;
/*  94 */   private boolean forceParsing = false;
/*     */ 
/*     */   public PDFStreamEngine()
/*     */   {
/* 102 */     this.validCharCnt = 0;
/* 103 */     this.totalCharCnt = 0;
/*     */   }
/*     */ 
/*     */   public PDFStreamEngine(Properties properties)
/*     */     throws IOException
/*     */   {
/* 119 */     if (properties == null)
/*     */     {
/* 121 */       throw new NullPointerException("properties cannot be null");
/*     */     }
/* 123 */     Enumeration names = properties.propertyNames();
/* 124 */     for (Iterator i$ = Collections.list(names).iterator(); i$.hasNext(); ) { Object name = i$.next();
/*     */ 
/* 126 */       String operator = name.toString();
/* 127 */       String processorClassName = properties.getProperty(operator);
/* 128 */       if ("".equals(processorClassName))
/*     */       {
/* 130 */         this.unsupportedOperators.add(operator);
/*     */       }
/*     */       else
/*     */       {
/*     */         try
/*     */         {
/* 136 */           Class klass = Class.forName(processorClassName);
/* 137 */           OperatorProcessor processor = (OperatorProcessor)klass.newInstance();
/*     */ 
/* 139 */           registerOperatorProcessor(operator, processor);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 143 */           throw new WrappedIOException("OperatorProcessor class " + processorClassName + " could not be instantiated", e);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 149 */     this.validCharCnt = 0;
/* 150 */     this.totalCharCnt = 0;
/*     */   }
/*     */ 
/*     */   public boolean isForceParsing()
/*     */   {
/* 160 */     return this.forceParsing;
/*     */   }
/*     */ 
/*     */   public void setForceParsing(boolean forceParsingValue)
/*     */   {
/* 170 */     this.forceParsing = forceParsingValue;
/*     */   }
/*     */ 
/*     */   public void registerOperatorProcessor(String operator, OperatorProcessor op)
/*     */   {
/* 181 */     op.setContext(this);
/* 182 */     this.operators.put(operator, op);
/*     */   }
/*     */ 
/*     */   public void resetEngine()
/*     */   {
/* 194 */     this.validCharCnt = 0;
/* 195 */     this.totalCharCnt = 0;
/*     */   }
/*     */ 
/*     */   public void processStream(PDPage aPage, PDResources resources, COSStream cosStream)
/*     */     throws IOException
/*     */   {
/* 210 */     this.graphicsState = new PDGraphicsState(aPage.findCropBox());
/* 211 */     this.textMatrix = null;
/* 212 */     this.textLineMatrix = null;
/* 213 */     this.graphicsStack.clear();
/* 214 */     this.streamResourcesStack.clear();
/* 215 */     processSubStream(aPage, resources, cosStream);
/*     */   }
/*     */ 
/*     */   public void processSubStream(PDPage aPage, PDResources resources, COSStream cosStream)
/*     */     throws IOException
/*     */   {
/* 229 */     this.page = aPage;
/* 230 */     if (resources != null)
/*     */     {
/* 232 */       this.streamResourcesStack.push(resources);
/*     */       try
/*     */       {
/* 235 */         processSubStream(cosStream);
/*     */       }
/*     */       finally
/*     */       {
/* 239 */         ((PDResources)this.streamResourcesStack.pop()).clear();
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 244 */       processSubStream(cosStream);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processSubStream(COSStream cosStream) throws IOException
/*     */   {
/* 250 */     List arguments = new ArrayList();
/* 251 */     PDFStreamParser parser = new PDFStreamParser(cosStream, this.forceParsing);
/*     */     try
/*     */     {
/* 254 */       Iterator iter = parser.getTokenIterator();
/* 255 */       while (iter.hasNext())
/*     */       {
/* 257 */         Object next = iter.next();
/* 258 */         if (LOG.isDebugEnabled())
/*     */         {
/* 260 */           LOG.debug("processing substream token: " + next);
/*     */         }
/* 262 */         if ((next instanceof COSObject))
/*     */         {
/* 264 */           arguments.add(((COSObject)next).getObject());
/*     */         }
/* 266 */         else if ((next instanceof PDFOperator))
/*     */         {
/* 268 */           processOperator((PDFOperator)next, arguments);
/* 269 */           arguments = new ArrayList();
/*     */         }
/*     */         else
/*     */         {
/* 273 */           arguments.add((COSBase)next);
/*     */         }
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 279 */       parser.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processTextPosition(TextPosition text)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected String inspectFontEncoding(String str)
/*     */   {
/* 303 */     return str;
/*     */   }
/*     */ 
/*     */   public void processEncodedText(byte[] string)
/*     */     throws IOException
/*     */   {
/* 323 */     float fontSizeText = this.graphicsState.getTextState().getFontSize();
/* 324 */     float horizontalScalingText = this.graphicsState.getTextState().getHorizontalScalingPercent() / 100.0F;
/*     */ 
/* 326 */     float riseText = this.graphicsState.getTextState().getRise();
/* 327 */     float wordSpacingText = this.graphicsState.getTextState().getWordSpacing();
/* 328 */     float characterSpacingText = this.graphicsState.getTextState().getCharacterSpacing();
/*     */ 
/* 335 */     PDFont font = this.graphicsState.getTextState().getFont();
/*     */ 
/* 337 */     float fontMatrixXScaling = 0.001F;
/* 338 */     float fontMatrixYScaling = 0.001F;
/* 339 */     float glyphSpaceToTextSpaceFactor = 0.001F;
/*     */ 
/* 341 */     if ((font instanceof PDType3Font))
/*     */     {
/* 343 */       PDMatrix fontMatrix = font.getFontMatrix();
/* 344 */       fontMatrixXScaling = fontMatrix.getValue(0, 0);
/* 345 */       fontMatrixYScaling = fontMatrix.getValue(1, 1);
/*     */ 
/* 348 */       glyphSpaceToTextSpaceFactor = 1.0F / fontMatrix.getValue(0, 0);
/*     */     }
/* 350 */     float spaceWidthText = 0.0F;
/*     */     try
/*     */     {
/* 355 */       spaceWidthText = font.getSpaceWidth() * glyphSpaceToTextSpaceFactor;
/*     */     }
/*     */     catch (Throwable exception)
/*     */     {
/* 359 */       LOG.warn(exception, exception);
/*     */     }
/*     */ 
/* 362 */     if (spaceWidthText == 0.0F)
/*     */     {
/* 364 */       spaceWidthText = font.getAverageFontWidth() * glyphSpaceToTextSpaceFactor;
/*     */ 
/* 367 */       spaceWidthText *= 0.8F;
/*     */     }
/* 369 */     if (spaceWidthText == 0.0F)
/*     */     {
/* 371 */       spaceWidthText = 1.0F;
/*     */     }
/* 373 */     float maxVerticalDisplacementText = 0.0F;
/*     */ 
/* 375 */     Matrix textStateParameters = new Matrix();
/* 376 */     textStateParameters.setValue(0, 0, fontSizeText * horizontalScalingText);
/* 377 */     textStateParameters.setValue(1, 1, fontSizeText);
/* 378 */     textStateParameters.setValue(2, 1, riseText);
/*     */ 
/* 380 */     int pageRotation = this.page.findRotation();
/* 381 */     float pageHeight = this.page.findCropBox().getHeight();
/* 382 */     float pageWidth = this.page.findCropBox().getWidth();
/*     */ 
/* 384 */     Matrix ctm = getGraphicsState().getCurrentTransformationMatrix();
/* 385 */     Matrix textXctm = new Matrix();
/* 386 */     Matrix textMatrixEnd = new Matrix();
/* 387 */     Matrix td = new Matrix();
/* 388 */     Matrix tempMatrix = new Matrix();
/*     */ 
/* 390 */     int codeLength = 1;
/* 391 */     for (int i = 0; i < string.length; i += codeLength)
/*     */     {
/* 394 */       codeLength = 1;
/* 395 */       String c = font.encode(string, i, codeLength);
/* 396 */       int[] codePoints = null;
/* 397 */       if ((c == null) && (i + 1 < string.length))
/*     */       {
/* 400 */         codeLength++;
/* 401 */         c = font.encode(string, i, codeLength);
/* 402 */         codePoints = new int[] { font.getCodeFromArray(string, i, codeLength) };
/*     */       }
/*     */ 
/* 406 */       float spaceWidthDisp = spaceWidthText * fontSizeText * horizontalScalingText * this.textMatrix.getXScale() * ctm.getXScale();
/*     */ 
/* 411 */       float characterHorizontalDisplacementText = font.getFontWidth(string, i, codeLength);
/* 412 */       float characterVerticalDisplacementText = font.getFontHeight(string, i, codeLength);
/*     */ 
/* 415 */       characterHorizontalDisplacementText *= fontMatrixXScaling;
/* 416 */       characterVerticalDisplacementText *= fontMatrixYScaling;
/*     */ 
/* 418 */       maxVerticalDisplacementText = Math.max(maxVerticalDisplacementText, characterVerticalDisplacementText);
/*     */ 
/* 441 */       float spacingText = 0.0F;
/* 442 */       if ((string[i] == 32) && (codeLength == 1))
/*     */       {
/* 444 */         spacingText += wordSpacingText;
/*     */       }
/* 446 */       this.textMatrix.multiply(ctm, textXctm);
/*     */ 
/* 449 */       Matrix textMatrixStart = textStateParameters.multiply(textXctm);
/*     */ 
/* 453 */       float tx = characterHorizontalDisplacementText * fontSizeText * horizontalScalingText;
/* 454 */       float ty = 0.0F;
/*     */ 
/* 456 */       td.reset();
/* 457 */       td.setValue(2, 0, tx);
/* 458 */       td.setValue(2, 1, ty);
/*     */ 
/* 465 */       textStateParameters.multiply(td, tempMatrix);
/* 466 */       tempMatrix.multiply(textXctm, textMatrixEnd);
/* 467 */       float endXPosition = textMatrixEnd.getXPosition();
/* 468 */       float endYPosition = textMatrixEnd.getYPosition();
/*     */ 
/* 471 */       tx = (characterHorizontalDisplacementText * fontSizeText + characterSpacingText + spacingText) * horizontalScalingText;
/*     */ 
/* 473 */       td.setValue(2, 0, tx);
/* 474 */       td.multiply(this.textMatrix, this.textMatrix);
/*     */ 
/* 478 */       float startXPosition = textMatrixStart.getXPosition();
/* 479 */       float widthText = endXPosition - startXPosition;
/*     */ 
/* 484 */       if (c != null)
/*     */       {
/* 486 */         this.validCharCnt += 1;
/*     */       }
/*     */       else
/*     */       {
/* 492 */         c = "?";
/*     */       }
/* 494 */       this.totalCharCnt += 1;
/*     */ 
/* 496 */       float totalVerticalDisplacementDisp = maxVerticalDisplacementText * fontSizeText * textXctm.getYScale();
/*     */ 
/* 499 */       processTextPosition(new TextPosition(pageRotation, pageWidth, pageHeight, textMatrixStart, endXPosition, endYPosition, totalVerticalDisplacementDisp, widthText, spaceWidthDisp, c, codePoints, font, fontSizeText, (int)(fontSizeText * this.textMatrix.getXScale())));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void processOperator(String operation, List<COSBase> arguments)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 531 */       PDFOperator oper = PDFOperator.getOperator(operation);
/* 532 */       processOperator(oper, arguments);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 536 */       LOG.warn(e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processOperator(PDFOperator operator, List<COSBase> arguments)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 552 */       String operation = operator.getOperation();
/* 553 */       OperatorProcessor processor = (OperatorProcessor)this.operators.get(operation);
/* 554 */       if (processor != null)
/*     */       {
/* 556 */         processor.setContext(this);
/* 557 */         processor.process(operator, arguments);
/*     */       }
/* 561 */       else if (!this.unsupportedOperators.contains(operation))
/*     */       {
/* 563 */         LOG.info("unsupported/disabled operation: " + operation);
/* 564 */         this.unsupportedOperators.add(operation);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 570 */       LOG.warn(e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map<String, PDColorSpace> getColorSpaces()
/*     */   {
/* 579 */     return ((PDResources)this.streamResourcesStack.peek()).getColorSpaces();
/*     */   }
/*     */ 
/*     */   public Map<String, PDXObject> getXObjects()
/*     */   {
/* 587 */     return ((PDResources)this.streamResourcesStack.peek()).getXObjects();
/*     */   }
/*     */ 
/*     */   public void setColorSpaces(Map<String, PDColorSpace> value)
/*     */   {
/* 595 */     ((PDResources)this.streamResourcesStack.peek()).setColorSpaces(value);
/*     */   }
/*     */ 
/*     */   public Map<String, PDFont> getFonts()
/*     */   {
/* 602 */     if (this.streamResourcesStack.isEmpty())
/*     */     {
/* 604 */       return Collections.emptyMap();
/*     */     }
/*     */ 
/* 607 */     return ((PDResources)this.streamResourcesStack.peek()).getFonts();
/*     */   }
/*     */ 
/*     */   public void setFonts(Map<String, PDFont> value)
/*     */   {
/* 614 */     ((PDResources)this.streamResourcesStack.peek()).setFonts(value);
/*     */   }
/*     */ 
/*     */   public Stack<PDGraphicsState> getGraphicsStack()
/*     */   {
/* 621 */     return this.graphicsStack;
/*     */   }
/*     */ 
/*     */   public void setGraphicsStack(Stack<PDGraphicsState> value)
/*     */   {
/* 628 */     this.graphicsStack = value;
/*     */   }
/*     */ 
/*     */   public PDGraphicsState getGraphicsState()
/*     */   {
/* 635 */     return this.graphicsState;
/*     */   }
/*     */ 
/*     */   public void setGraphicsState(PDGraphicsState value)
/*     */   {
/* 642 */     this.graphicsState = value;
/*     */   }
/*     */ 
/*     */   public Map<String, PDExtendedGraphicsState> getGraphicsStates()
/*     */   {
/* 649 */     return ((PDResources)this.streamResourcesStack.peek()).getGraphicsStates();
/*     */   }
/*     */ 
/*     */   public void setGraphicsStates(Map<String, PDExtendedGraphicsState> value)
/*     */   {
/* 656 */     ((PDResources)this.streamResourcesStack.peek()).setGraphicsStates(value);
/*     */   }
/*     */ 
/*     */   public Matrix getTextLineMatrix()
/*     */   {
/* 663 */     return this.textLineMatrix;
/*     */   }
/*     */ 
/*     */   public void setTextLineMatrix(Matrix value)
/*     */   {
/* 670 */     this.textLineMatrix = value;
/*     */   }
/*     */ 
/*     */   public Matrix getTextMatrix()
/*     */   {
/* 677 */     return this.textMatrix;
/*     */   }
/*     */ 
/*     */   public void setTextMatrix(Matrix value)
/*     */   {
/* 684 */     this.textMatrix = value;
/*     */   }
/*     */ 
/*     */   public PDResources getResources()
/*     */   {
/* 691 */     return (PDResources)this.streamResourcesStack.peek();
/*     */   }
/*     */ 
/*     */   public PDPage getCurrentPage()
/*     */   {
/* 701 */     return this.page;
/*     */   }
/*     */ 
/*     */   public int getValidCharCnt()
/*     */   {
/* 711 */     return this.validCharCnt;
/*     */   }
/*     */ 
/*     */   public int getTotalCharCnt()
/*     */   {
/* 721 */     return this.totalCharCnt;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.PDFStreamEngine
 * JD-Core Version:    0.6.2
 */