/*     */ package org.apache.pdfbox.preflight.content;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDCalGray;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDCalRGB;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpaceFactory;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDICCBased;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDLab;
/*     */ import org.apache.pdfbox.preflight.PreflightConfiguration;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.graphic.ColorSpaceHelper;
/*     */ import org.apache.pdfbox.preflight.graphic.ColorSpaceHelperFactory;
/*     */ import org.apache.pdfbox.preflight.graphic.ColorSpaceHelperFactory.ColorSpaceRestriction;
/*     */ import org.apache.pdfbox.preflight.graphic.ColorSpaces;
/*     */ import org.apache.pdfbox.preflight.graphic.ICCProfileWrapper;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ import org.apache.pdfbox.preflight.utils.FilterHelper;
/*     */ import org.apache.pdfbox.preflight.utils.RenderingIntents;
/*     */ import org.apache.pdfbox.util.ImageParameters;
/*     */ import org.apache.pdfbox.util.PDFOperator;
/*     */ import org.apache.pdfbox.util.PDFStreamEngine;
/*     */ import org.apache.pdfbox.util.operator.BeginText;
/*     */ import org.apache.pdfbox.util.operator.Concatenate;
/*     */ import org.apache.pdfbox.util.operator.EndText;
/*     */ import org.apache.pdfbox.util.operator.GRestore;
/*     */ import org.apache.pdfbox.util.operator.GSave;
/*     */ import org.apache.pdfbox.util.operator.Invoke;
/*     */ import org.apache.pdfbox.util.operator.MoveText;
/*     */ import org.apache.pdfbox.util.operator.MoveTextSetLeading;
/*     */ import org.apache.pdfbox.util.operator.NextLine;
/*     */ import org.apache.pdfbox.util.operator.OperatorProcessor;
/*     */ import org.apache.pdfbox.util.operator.SetCharSpacing;
/*     */ import org.apache.pdfbox.util.operator.SetHorizontalTextScaling;
/*     */ import org.apache.pdfbox.util.operator.SetLineCapStyle;
/*     */ import org.apache.pdfbox.util.operator.SetLineDashPattern;
/*     */ import org.apache.pdfbox.util.operator.SetLineJoinStyle;
/*     */ import org.apache.pdfbox.util.operator.SetLineWidth;
/*     */ import org.apache.pdfbox.util.operator.SetMatrix;
/*     */ import org.apache.pdfbox.util.operator.SetNonStrokingCMYKColor;
/*     */ import org.apache.pdfbox.util.operator.SetNonStrokingColor;
/*     */ import org.apache.pdfbox.util.operator.SetNonStrokingColorSpace;
/*     */ import org.apache.pdfbox.util.operator.SetNonStrokingRGBColor;
/*     */ import org.apache.pdfbox.util.operator.SetStrokingCMYKColor;
/*     */ import org.apache.pdfbox.util.operator.SetStrokingColor;
/*     */ import org.apache.pdfbox.util.operator.SetStrokingColorSpace;
/*     */ import org.apache.pdfbox.util.operator.SetStrokingRGBColor;
/*     */ import org.apache.pdfbox.util.operator.SetTextFont;
/*     */ import org.apache.pdfbox.util.operator.SetTextLeading;
/*     */ import org.apache.pdfbox.util.operator.SetTextRenderingMode;
/*     */ import org.apache.pdfbox.util.operator.SetTextRise;
/*     */ import org.apache.pdfbox.util.operator.SetWordSpacing;
/*     */ 
/*     */ public abstract class ContentStreamEngine extends PDFStreamEngine
/*     */ {
/* 108 */   protected PreflightContext context = null;
/*     */ 
/* 110 */   protected COSDocument cosDocument = null;
/*     */ 
/* 112 */   protected PDPage processeedPage = null;
/*     */ 
/* 114 */   protected Map<String, OperatorProcessor> contentStreamEngineOperators = new HashMap();
/*     */ 
/*     */   public ContentStreamEngine(PreflightContext _context, PDPage _page)
/*     */   {
/* 119 */     this.context = _context;
/* 120 */     this.cosDocument = _context.getDocument().getDocument();
/* 121 */     this.processeedPage = _page;
/*     */ 
/* 124 */     registerOperatorProcessor("w", new SetLineWidth());
/* 125 */     registerOperatorProcessor("cm", new Concatenate());
/*     */ 
/* 127 */     registerOperatorProcessor("CS", new SetStrokingColorSpace());
/* 128 */     registerOperatorProcessor("cs", new SetNonStrokingColorSpace());
/* 129 */     registerOperatorProcessor("d", new SetLineDashPattern());
/* 130 */     registerOperatorProcessor("Do", new Invoke());
/*     */ 
/* 132 */     registerOperatorProcessor("j", new SetLineJoinStyle());
/* 133 */     registerOperatorProcessor("J", new SetLineCapStyle());
/* 134 */     registerOperatorProcessor("K", new SetStrokingCMYKColor());
/* 135 */     registerOperatorProcessor("k", new SetNonStrokingCMYKColor());
/*     */ 
/* 137 */     registerOperatorProcessor("rg", new SetNonStrokingRGBColor());
/* 138 */     registerOperatorProcessor("RG", new SetStrokingRGBColor());
/*     */ 
/* 140 */     registerOperatorProcessor("SC", new SetStrokingColor());
/* 141 */     registerOperatorProcessor("SCN", new SetStrokingColor());
/* 142 */     registerOperatorProcessor("sc", new SetNonStrokingColor());
/* 143 */     registerOperatorProcessor("scn", new SetNonStrokingColor());
/*     */ 
/* 146 */     registerOperatorProcessor("Q", new GRestore());
/* 147 */     registerOperatorProcessor("q", new GSave());
/*     */ 
/* 150 */     registerOperatorProcessor("BT", new BeginText());
/* 151 */     registerOperatorProcessor("ET", new EndText());
/* 152 */     registerOperatorProcessor("Tf", new SetTextFont());
/* 153 */     registerOperatorProcessor("Tr", new SetTextRenderingMode());
/* 154 */     registerOperatorProcessor("Tm", new SetMatrix());
/* 155 */     registerOperatorProcessor("Td", new MoveText());
/* 156 */     registerOperatorProcessor("T*", new NextLine());
/* 157 */     registerOperatorProcessor("TD", new MoveTextSetLeading());
/* 158 */     registerOperatorProcessor("Tc", new SetCharSpacing());
/* 159 */     registerOperatorProcessor("TL", new SetTextLeading());
/* 160 */     registerOperatorProcessor("Ts", new SetTextRise());
/* 161 */     registerOperatorProcessor("Tw", new SetWordSpacing());
/* 162 */     registerOperatorProcessor("Tz", new SetHorizontalTextScaling());
/*     */ 
/* 167 */     StubOperator stubOp = new StubOperator();
/* 168 */     registerOperatorProcessor("l", stubOp);
/* 169 */     registerOperatorProcessor("re", stubOp);
/* 170 */     registerOperatorProcessor("c", stubOp);
/* 171 */     registerOperatorProcessor("y", stubOp);
/* 172 */     registerOperatorProcessor("v", stubOp);
/* 173 */     registerOperatorProcessor("n", stubOp);
/* 174 */     registerOperatorProcessor("BI", stubOp);
/* 175 */     registerOperatorProcessor("EI", stubOp);
/* 176 */     registerOperatorProcessor("m", stubOp);
/* 177 */     registerOperatorProcessor("W*", stubOp);
/* 178 */     registerOperatorProcessor("W", stubOp);
/* 179 */     registerOperatorProcessor("h", stubOp);
/*     */ 
/* 181 */     registerOperatorProcessor("Tj", stubOp);
/* 182 */     registerOperatorProcessor("TJ", stubOp);
/* 183 */     registerOperatorProcessor("'", stubOp);
/* 184 */     registerOperatorProcessor("\"", stubOp);
/*     */ 
/* 186 */     registerOperatorProcessor("b", stubOp);
/* 187 */     registerOperatorProcessor("B", stubOp);
/* 188 */     registerOperatorProcessor("b*", stubOp);
/* 189 */     registerOperatorProcessor("B*", stubOp);
/*     */ 
/* 191 */     registerOperatorProcessor("BDC", stubOp);
/* 192 */     registerOperatorProcessor("BMC", stubOp);
/* 193 */     registerOperatorProcessor("DP", stubOp);
/* 194 */     registerOperatorProcessor("EMC", stubOp);
/*     */ 
/* 196 */     registerOperatorProcessor("d0", stubOp);
/* 197 */     registerOperatorProcessor("d1", stubOp);
/*     */ 
/* 199 */     registerOperatorProcessor("f", stubOp);
/* 200 */     registerOperatorProcessor("F", stubOp);
/* 201 */     registerOperatorProcessor("f*", stubOp);
/*     */ 
/* 203 */     registerOperatorProcessor("g", stubOp);
/* 204 */     registerOperatorProcessor("G", stubOp);
/*     */ 
/* 206 */     registerOperatorProcessor("M", stubOp);
/* 207 */     registerOperatorProcessor("MP", stubOp);
/*     */ 
/* 209 */     registerOperatorProcessor("gs", stubOp);
/* 210 */     registerOperatorProcessor("h", stubOp);
/* 211 */     registerOperatorProcessor("i", stubOp);
/*     */ 
/* 213 */     registerOperatorProcessor("ri", stubOp);
/* 214 */     registerOperatorProcessor("s", stubOp);
/* 215 */     registerOperatorProcessor("S", stubOp);
/* 216 */     registerOperatorProcessor("sh", stubOp);
/*     */   }
/*     */ 
/*     */   public final void registerOperatorProcessor(String operator, OperatorProcessor op)
/*     */   {
/* 221 */     super.registerOperatorProcessor(operator, op);
/* 222 */     this.contentStreamEngineOperators.put(operator, op);
/*     */   }
/*     */ 
/*     */   protected void validRenderingIntent(PDFOperator operator, List arguments)
/*     */     throws ContentStreamException
/*     */   {
/* 238 */     if ("ri".equals(operator.getOperation()))
/*     */     {
/* 240 */       String riArgument0 = "";
/* 241 */       if ((arguments.get(0) instanceof COSName))
/*     */       {
/* 243 */         riArgument0 = ((COSName)arguments.get(0)).getName();
/*     */       }
/* 245 */       else if ((arguments.get(0) instanceof String))
/*     */       {
/* 247 */         riArgument0 = (String)arguments.get(0);
/*     */       }
/*     */ 
/* 250 */       if (!RenderingIntents.contains(riArgument0))
/*     */       {
/* 252 */         registerError("Unexpected value '" + arguments.get(0) + "' for ri operand. ", "2.3.2");
/*     */ 
/* 254 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validNumberOfGraphicStates(PDFOperator operator)
/*     */     throws ContentStreamException
/*     */   {
/* 267 */     if ("q".equals(operator.getOperation()))
/*     */     {
/* 269 */       int numberOfGraphicStates = getGraphicsStack().size();
/* 270 */       if (numberOfGraphicStates > 28)
/*     */       {
/* 272 */         registerError("Too many graphic states", "2.1.8");
/* 273 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validImageFilter(PDFOperator operator)
/*     */     throws ContentStreamException
/*     */   {
/* 287 */     COSDictionary dict = operator.getImageParameters().getDictionary();
/*     */ 
/* 291 */     COSBase filter = dict.getDictionaryObject(COSName.F, COSName.FILTER);
/* 292 */     FilterHelper.isAuthorizedFilter(this.context, COSUtils.getAsString(filter, this.context.getDocument().getDocument()));
/*     */   }
/*     */ 
/*     */   protected void validImageColorSpace(PDFOperator operator)
/*     */     throws ContentStreamException, IOException
/*     */   {
/* 306 */     COSDictionary dict = operator.getImageParameters().getDictionary();
/*     */ 
/* 308 */     COSBase csInlinedBase = dict.getItem(COSName.CS);
/* 309 */     ColorSpaceHelper csHelper = null;
/* 310 */     if (csInlinedBase != null)
/*     */     {
/* 312 */       if (COSUtils.isString(csInlinedBase, this.cosDocument))
/*     */       {
/* 317 */         String colorSpace = COSUtils.getAsString(csInlinedBase, this.cosDocument);
/* 318 */         ColorSpaces cs = null;
/*     */         try
/*     */         {
/* 322 */           cs = ColorSpaces.valueOf(colorSpace);
/*     */         }
/*     */         catch (IllegalArgumentException e)
/*     */         {
/* 328 */           Map colorSpaces = getResources().getColorSpaces();
/* 329 */           if (colorSpaces != null)
/*     */           {
/* 331 */             PDColorSpace pdCS = (PDColorSpace)colorSpaces.get(colorSpace);
/* 332 */             if (pdCS != null)
/*     */             {
/* 334 */               cs = ColorSpaces.valueOf(pdCS.getName());
/* 335 */               PreflightConfiguration cfg = this.context.getConfig();
/* 336 */               ColorSpaceHelperFactory csFact = cfg.getColorSpaceHelperFact();
/* 337 */               csHelper = csFact.getColorSpaceHelper(this.context, pdCS, ColorSpaceHelperFactory.ColorSpaceRestriction.ONLY_DEVICE);
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 342 */         if (cs == null)
/*     */         {
/* 344 */           registerError("The ColorSpace is unknown", "2.3.2");
/* 345 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 349 */       if (csHelper == null)
/*     */       {
/* 351 */         PDColorSpace pdCS = PDColorSpaceFactory.createColorSpace(csInlinedBase);
/* 352 */         PreflightConfiguration cfg = this.context.getConfig();
/* 353 */         ColorSpaceHelperFactory csFact = cfg.getColorSpaceHelperFact();
/* 354 */         csHelper = csFact.getColorSpaceHelper(this.context, pdCS, ColorSpaceHelperFactory.ColorSpaceRestriction.ONLY_DEVICE);
/*     */       }
/*     */ 
/* 357 */       csHelper.validate();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkColorOperators(String operation)
/*     */     throws ContentStreamException
/*     */   {
/* 370 */     PDColorState cs = getColorState(operation);
/*     */ 
/* 372 */     if (("rg".equals(operation)) || ("RG".equals(operation)))
/*     */     {
/* 374 */       if (!validColorSpace(cs, ColorSpaceType.RGB))
/*     */       {
/* 376 */         registerError("The operator \"" + operation + "\" can't be used with CMYK Profile", "2.4.1");
/*     */ 
/* 378 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 382 */     if (("k".equals(operation)) || ("K".equals(operation)))
/*     */     {
/* 384 */       if (!validColorSpace(cs, ColorSpaceType.CMYK))
/*     */       {
/* 386 */         registerError("The operator \"" + operation + "\" can't be used with RGB Profile", "2.4.2");
/*     */ 
/* 388 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 392 */     if (("g".equals(operation)) || ("G".equals(operation)))
/*     */     {
/* 394 */       if (!validColorSpace(cs, ColorSpaceType.ALL))
/*     */       {
/* 396 */         registerError("The operator \"" + operation + "\" can't be used without Color Profile", "2.4.3");
/*     */ 
/* 398 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 402 */     if (("f".equals(operation)) || ("F".equals(operation)) || ("f*".equals(operation)) || ("B".equals(operation)) || ("B*".equals(operation)) || ("b".equals(operation)) || ("b*".equals(operation)))
/*     */     {
/* 405 */       if (!validColorSpace(cs, ColorSpaceType.ALL))
/*     */       {
/* 408 */         registerError("The operator \"" + operation + "\" can't be used without Color Profile", "2.4.3");
/*     */ 
/* 410 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean validColorSpace(PDColorState colorState, ColorSpaceType expectedType) throws ContentStreamException
/*     */   {
/* 417 */     boolean result = true;
/* 418 */     if (colorState == null)
/*     */     {
/* 420 */       result = validColorSpaceDestOutputProfile(expectedType);
/*     */     }
/*     */     else
/*     */     {
/* 424 */       PDColorSpace cs = colorState.getColorSpace();
/* 425 */       if (isDeviceIndependent(cs, expectedType))
/*     */       {
/* 427 */         result = true;
/*     */       }
/*     */       else
/*     */       {
/* 431 */         result = validColorSpaceDestOutputProfile(expectedType);
/*     */       }
/*     */     }
/* 434 */     return result;
/*     */   }
/*     */ 
/*     */   private boolean validColorSpaceDestOutputProfile(ColorSpaceType expectedType)
/*     */     throws ContentStreamException
/*     */   {
/* 446 */     boolean result = false;
/*     */     try
/*     */     {
/* 450 */       ICCProfileWrapper profileWrapper = ICCProfileWrapper.getOrSearchICCProfile(this.context);
/* 451 */       if (profileWrapper != null)
/*     */       {
/* 453 */         switch (1.$SwitchMap$org$apache$pdfbox$preflight$content$ContentStreamEngine$ColorSpaceType[expectedType.ordinal()])
/*     */         {
/*     */         case 1:
/* 456 */           result = profileWrapper.isRGBColorSpace();
/* 457 */           break;
/*     */         case 2:
/* 459 */           result = profileWrapper.isCMYKColorSpace();
/* 460 */           break;
/*     */         default:
/* 462 */           result = true;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (ValidationException e)
/*     */     {
/* 469 */       throw new ContentStreamException(e);
/*     */     }
/* 471 */     return result;
/*     */   }
/*     */ 
/*     */   private boolean isDeviceIndependent(PDColorSpace cs, ColorSpaceType expectedType)
/*     */   {
/* 483 */     boolean result = ((cs instanceof PDCalGray)) || ((cs instanceof PDCalRGB)) || ((cs instanceof PDLab));
/* 484 */     if ((cs instanceof PDICCBased))
/*     */     {
/* 486 */       PDICCBased iccBased = (PDICCBased)cs;
/*     */       try
/*     */       {
/* 489 */         ColorSpace iccColorSpace = iccBased.getJavaColorSpace();
/* 490 */         switch (1.$SwitchMap$org$apache$pdfbox$preflight$content$ContentStreamEngine$ColorSpaceType[expectedType.ordinal()])
/*     */         {
/*     */         case 1:
/* 493 */           result = iccColorSpace.getType() == 5;
/* 494 */           break;
/*     */         case 2:
/* 496 */           result = iccColorSpace.getType() == 9;
/* 497 */           break;
/*     */         default:
/* 499 */           result = true;
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 505 */         result = false;
/*     */       }
/*     */     }
/* 508 */     return result;
/*     */   }
/*     */ 
/*     */   private PDColorState getColorState(String operation)
/*     */   {
/* 519 */     if (getGraphicsState() == null)
/*     */     {
/* 521 */       return null;
/*     */     }
/*     */     PDColorState colorState;
/*     */     PDColorState colorState;
/* 525 */     if ((operation.equals("rg")) || (operation.equals("g")) || (operation.equals("k")) || (operation.equals("f")) || (operation.equals("F")) || (operation.equals("f*")))
/*     */     {
/* 529 */       colorState = getGraphicsState().getNonStrokingColor();
/*     */     }
/*     */     else
/*     */     {
/* 534 */       colorState = getGraphicsState().getStrokingColor();
/*     */     }
/* 536 */     return colorState;
/*     */   }
/*     */ 
/*     */   protected void checkSetColorSpaceOperators(PDFOperator operator, List<?> arguments)
/*     */     throws IOException
/*     */   {
/* 549 */     if ((!"CS".equals(operator.getOperation())) && (!"cs".equals(operator.getOperation())))
/*     */     {
/* 551 */       return;
/*     */     }
/*     */ 
/* 554 */     String colorSpaceName = null;
/* 555 */     if ((arguments.get(0) instanceof String))
/*     */     {
/* 557 */       colorSpaceName = (String)arguments.get(0);
/*     */     }
/* 559 */     else if ((arguments.get(0) instanceof COSString))
/*     */     {
/* 561 */       colorSpaceName = ((COSString)arguments.get(0)).toString();
/*     */     }
/* 563 */     else if ((arguments.get(0) instanceof COSName))
/*     */     {
/* 565 */       colorSpaceName = ((COSName)arguments.get(0)).getName();
/*     */     }
/*     */     else
/*     */     {
/* 569 */       registerError("The operand doesn't have the expected type", "2.3.2");
/* 570 */       return;
/*     */     }
/*     */ 
/* 573 */     ColorSpaceHelper csHelper = null;
/* 574 */     ColorSpaces cs = null;
/*     */     try
/*     */     {
/* 577 */       cs = ColorSpaces.valueOf(colorSpaceName);
/*     */     }
/*     */     catch (IllegalArgumentException e)
/*     */     {
/* 584 */       Map colorSpaces = getResources().getColorSpaces();
/* 585 */       if (colorSpaces != null)
/*     */       {
/* 587 */         PDColorSpace pdCS = (PDColorSpace)colorSpaces.get(colorSpaceName);
/* 588 */         if (pdCS != null)
/*     */         {
/* 590 */           cs = ColorSpaces.valueOf(pdCS.getName());
/* 591 */           PreflightConfiguration cfg = this.context.getConfig();
/* 592 */           ColorSpaceHelperFactory csFact = cfg.getColorSpaceHelperFact();
/* 593 */           csHelper = csFact.getColorSpaceHelper(this.context, pdCS, ColorSpaceHelperFactory.ColorSpaceRestriction.NO_RESTRICTION);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 598 */     if (cs == null)
/*     */     {
/* 600 */       registerError("The ColorSpace is unknown", "2.3.2");
/* 601 */       return;
/*     */     }
/*     */ 
/* 604 */     if (csHelper == null)
/*     */     {
/* 606 */       PDColorSpace pdCS = PDColorSpaceFactory.createColorSpace(COSName.getPDFName(colorSpaceName));
/* 607 */       PreflightConfiguration cfg = this.context.getConfig();
/* 608 */       ColorSpaceHelperFactory csFact = cfg.getColorSpaceHelperFact();
/* 609 */       csHelper = csFact.getColorSpaceHelper(this.context, pdCS, ColorSpaceHelperFactory.ColorSpaceRestriction.NO_RESTRICTION);
/*     */     }
/*     */ 
/* 612 */     csHelper.validate();
/*     */   }
/*     */ 
/*     */   protected void registerError(String msg, String errorCode)
/*     */   {
/* 625 */     ValidationResult.ValidationError error = new ValidationResult.ValidationError(errorCode, msg);
/* 626 */     this.context.addValidationError(error);
/*     */   }
/*     */ 
/*     */   private static enum ColorSpaceType
/*     */   {
/* 105 */     RGB, CMYK, ALL;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.content.ContentStreamEngine
 * JD-Core Version:    0.6.2
 */