/*      */ package com.itextpdf.text.pdf.parser;
/*      */ 
/*      */ import com.itextpdf.text.BaseColor;
/*      */ import com.itextpdf.text.ExceptionConverter;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.io.RandomAccessSourceFactory;
/*      */ import com.itextpdf.text.pdf.CMYKColor;
/*      */ import com.itextpdf.text.pdf.CMapAwareDocumentFont;
/*      */ import com.itextpdf.text.pdf.GrayColor;
/*      */ import com.itextpdf.text.pdf.PRIndirectReference;
/*      */ import com.itextpdf.text.pdf.PRTokeniser;
/*      */ import com.itextpdf.text.pdf.PdfArray;
/*      */ import com.itextpdf.text.pdf.PdfContentParser;
/*      */ import com.itextpdf.text.pdf.PdfDictionary;
/*      */ import com.itextpdf.text.pdf.PdfIndirectReference;
/*      */ import com.itextpdf.text.pdf.PdfLiteral;
/*      */ import com.itextpdf.text.pdf.PdfName;
/*      */ import com.itextpdf.text.pdf.PdfNumber;
/*      */ import com.itextpdf.text.pdf.PdfObject;
/*      */ import com.itextpdf.text.pdf.PdfStream;
/*      */ import com.itextpdf.text.pdf.PdfString;
/*      */ import com.itextpdf.text.pdf.RandomAccessFileOrArray;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Stack;
/*      */ 
/*      */ public class PdfContentStreamProcessor
/*      */ {
/*      */   public static final String DEFAULTOPERATOR = "DefaultOperator";
/*      */   private final Map<String, ContentOperator> operators;
/*      */   private ResourceDictionary resources;
/*   92 */   private final Stack<GraphicsState> gsStack = new Stack();
/*      */   private Matrix textMatrix;
/*      */   private Matrix textLineMatrix;
/*      */   private final RenderListener renderListener;
/*      */   private final Map<PdfName, XObjectDoHandler> xobjectDoHandlers;
/*  106 */   private final Map<Integer, CMapAwareDocumentFont> cachedFonts = new HashMap();
/*      */ 
/*  111 */   private final Stack<MarkedContentInfo> markedContentStack = new Stack();
/*      */ 
/*      */   public PdfContentStreamProcessor(RenderListener renderListener)
/*      */   {
/*  120 */     this.renderListener = renderListener;
/*  121 */     this.operators = new HashMap();
/*  122 */     populateOperators();
/*  123 */     this.xobjectDoHandlers = new HashMap();
/*  124 */     populateXObjectDoHandlers();
/*  125 */     reset();
/*      */   }
/*      */ 
/*      */   private void populateXObjectDoHandlers() {
/*  129 */     registerXObjectDoHandler(PdfName.DEFAULT, new IgnoreXObjectDoHandler(null));
/*  130 */     registerXObjectDoHandler(PdfName.FORM, new FormXObjectDoHandler(null));
/*  131 */     registerXObjectDoHandler(PdfName.IMAGE, new ImageXObjectDoHandler(null));
/*      */   }
/*      */ 
/*      */   public XObjectDoHandler registerXObjectDoHandler(PdfName xobjectSubType, XObjectDoHandler handler)
/*      */   {
/*  145 */     return (XObjectDoHandler)this.xobjectDoHandlers.put(xobjectSubType, handler);
/*      */   }
/*      */ 
/*      */   private CMapAwareDocumentFont getFont(PRIndirectReference ind)
/*      */   {
/*  155 */     Integer n = Integer.valueOf(ind.getNumber());
/*  156 */     CMapAwareDocumentFont font = (CMapAwareDocumentFont)this.cachedFonts.get(n);
/*  157 */     if (font == null) {
/*  158 */       font = new CMapAwareDocumentFont(ind);
/*  159 */       this.cachedFonts.put(n, font);
/*      */     }
/*  161 */     return font;
/*      */   }
/*      */ 
/*      */   private CMapAwareDocumentFont getFont(PdfDictionary fontResource) {
/*  165 */     return new CMapAwareDocumentFont(fontResource);
/*      */   }
/*      */ 
/*      */   private void populateOperators()
/*      */   {
/*  173 */     registerContentOperator("DefaultOperator", new IgnoreOperatorContentOperator(null));
/*      */ 
/*  175 */     registerContentOperator("q", new PushGraphicsState(null));
/*  176 */     registerContentOperator("Q", new PopGraphicsState(null));
/*  177 */     registerContentOperator("g", new SetGrayFill(null));
/*  178 */     registerContentOperator("G", new SetGrayStroke(null));
/*  179 */     registerContentOperator("rg", new SetRGBFill(null));
/*  180 */     registerContentOperator("RG", new SetRGBStroke(null));
/*  181 */     registerContentOperator("k", new SetCMYKFill(null));
/*  182 */     registerContentOperator("K", new SetCMYKStroke(null));
/*  183 */     registerContentOperator("cs", new SetColorSpaceFill(null));
/*  184 */     registerContentOperator("CS", new SetColorSpaceStroke(null));
/*  185 */     registerContentOperator("sc", new SetColorFill(null));
/*  186 */     registerContentOperator("SC", new SetColorStroke(null));
/*  187 */     registerContentOperator("scn", new SetColorFill(null));
/*  188 */     registerContentOperator("SCN", new SetColorStroke(null));
/*  189 */     registerContentOperator("cm", new ModifyCurrentTransformationMatrix(null));
/*  190 */     registerContentOperator("gs", new ProcessGraphicsStateResource(null));
/*      */ 
/*  192 */     SetTextCharacterSpacing tcOperator = new SetTextCharacterSpacing(null);
/*  193 */     registerContentOperator("Tc", tcOperator);
/*  194 */     SetTextWordSpacing twOperator = new SetTextWordSpacing(null);
/*  195 */     registerContentOperator("Tw", twOperator);
/*  196 */     registerContentOperator("Tz", new SetTextHorizontalScaling(null));
/*  197 */     SetTextLeading tlOperator = new SetTextLeading(null);
/*  198 */     registerContentOperator("TL", tlOperator);
/*  199 */     registerContentOperator("Tf", new SetTextFont(null));
/*  200 */     registerContentOperator("Tr", new SetTextRenderMode(null));
/*  201 */     registerContentOperator("Ts", new SetTextRise(null));
/*      */ 
/*  203 */     registerContentOperator("BT", new BeginText(null));
/*  204 */     registerContentOperator("ET", new EndText(null));
/*  205 */     registerContentOperator("BMC", new BeginMarkedContent(null));
/*  206 */     registerContentOperator("BDC", new BeginMarkedContentDictionary(null));
/*  207 */     registerContentOperator("EMC", new EndMarkedContent(null));
/*      */ 
/*  209 */     TextMoveStartNextLine tdOperator = new TextMoveStartNextLine(null);
/*  210 */     registerContentOperator("Td", tdOperator);
/*  211 */     registerContentOperator("TD", new TextMoveStartNextLineWithLeading(tdOperator, tlOperator));
/*  212 */     registerContentOperator("Tm", new TextSetTextMatrix(null));
/*  213 */     TextMoveNextLine tstarOperator = new TextMoveNextLine(tdOperator);
/*  214 */     registerContentOperator("T*", tstarOperator);
/*      */ 
/*  216 */     ShowText tjOperator = new ShowText(null);
/*  217 */     registerContentOperator("Tj", tjOperator);
/*  218 */     MoveNextLineAndShowText tickOperator = new MoveNextLineAndShowText(tstarOperator, tjOperator);
/*  219 */     registerContentOperator("'", tickOperator);
/*  220 */     registerContentOperator("\"", new MoveNextLineAndShowTextWithSpacing(twOperator, tcOperator, tickOperator));
/*  221 */     registerContentOperator("TJ", new ShowTextArray(null));
/*      */ 
/*  223 */     registerContentOperator("Do", new Do(null));
/*      */   }
/*      */ 
/*      */   public ContentOperator registerContentOperator(String operatorString, ContentOperator operator)
/*      */   {
/*  237 */     return (ContentOperator)this.operators.put(operatorString, operator);
/*      */   }
/*      */ 
/*      */   public void reset()
/*      */   {
/*  244 */     this.gsStack.removeAllElements();
/*  245 */     this.gsStack.add(new GraphicsState());
/*  246 */     this.textMatrix = null;
/*  247 */     this.textLineMatrix = null;
/*  248 */     this.resources = new ResourceDictionary();
/*      */   }
/*      */ 
/*      */   private GraphicsState gs()
/*      */   {
/*  256 */     return (GraphicsState)this.gsStack.peek();
/*      */   }
/*      */ 
/*      */   private void invokeOperator(PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     throws Exception
/*      */   {
/*  265 */     ContentOperator op = (ContentOperator)this.operators.get(operator.toString());
/*  266 */     if (op == null)
/*  267 */       op = (ContentOperator)this.operators.get("DefaultOperator");
/*  268 */     op.invoke(this, operator, operands);
/*      */   }
/*      */ 
/*      */   private void beginMarkedContent(PdfName tag, PdfDictionary dict)
/*      */   {
/*  278 */     this.markedContentStack.push(new MarkedContentInfo(tag, dict));
/*      */   }
/*      */ 
/*      */   private void endMarkedContent()
/*      */   {
/*  286 */     this.markedContentStack.pop();
/*      */   }
/*      */ 
/*      */   private String decode(PdfString in)
/*      */   {
/*  297 */     byte[] bytes = in.getBytes();
/*  298 */     return gs().font.decode(bytes, 0, bytes.length);
/*      */   }
/*      */ 
/*      */   private void beginText()
/*      */   {
/*  305 */     this.renderListener.beginTextBlock();
/*      */   }
/*      */ 
/*      */   private void endText()
/*      */   {
/*  312 */     this.renderListener.endTextBlock();
/*      */   }
/*      */ 
/*      */   private void displayPdfString(PdfString string)
/*      */   {
/*  321 */     String unicode = decode(string);
/*      */ 
/*  323 */     TextRenderInfo renderInfo = new TextRenderInfo(unicode, gs(), this.textMatrix, this.markedContentStack);
/*      */ 
/*  325 */     this.renderListener.renderText(renderInfo);
/*      */ 
/*  327 */     this.textMatrix = new Matrix(renderInfo.getUnscaledWidth(), 0.0F).multiply(this.textMatrix);
/*      */   }
/*      */ 
/*      */   private void displayXObject(PdfName xobjectName)
/*      */     throws IOException
/*      */   {
/*  339 */     PdfDictionary xobjects = this.resources.getAsDict(PdfName.XOBJECT);
/*  340 */     PdfObject xobject = xobjects.getDirectObject(xobjectName);
/*  341 */     PdfStream xobjectStream = (PdfStream)xobject;
/*      */ 
/*  343 */     PdfName subType = xobjectStream.getAsName(PdfName.SUBTYPE);
/*  344 */     if (xobject.isStream()) {
/*  345 */       XObjectDoHandler handler = (XObjectDoHandler)this.xobjectDoHandlers.get(subType);
/*  346 */       if (handler == null)
/*  347 */         handler = (XObjectDoHandler)this.xobjectDoHandlers.get(PdfName.DEFAULT);
/*  348 */       handler.handleXObject(this, xobjectStream, xobjects.getAsIndirectObject(xobjectName));
/*      */     } else {
/*  350 */       throw new IllegalStateException(MessageLocalization.getComposedMessage("XObject.1.is.not.a.stream", new Object[] { xobjectName }));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void applyTextAdjust(float tj)
/*      */   {
/*  360 */     float adjustBy = -tj / 1000.0F * gs().fontSize * gs().horizontalScaling;
/*      */ 
/*  362 */     this.textMatrix = new Matrix(adjustBy, 0.0F).multiply(this.textMatrix);
/*      */   }
/*      */ 
/*      */   public void processContent(byte[] contentBytes, PdfDictionary resources)
/*      */   {
/*  376 */     this.resources.push(resources);
/*      */     try {
/*  378 */       PRTokeniser tokeniser = new PRTokeniser(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(contentBytes)));
/*  379 */       PdfContentParser ps = new PdfContentParser(tokeniser);
/*  380 */       ArrayList operands = new ArrayList();
/*  381 */       while (ps.parse(operands).size() > 0) {
/*  382 */         PdfLiteral operator = (PdfLiteral)operands.get(operands.size() - 1);
/*  383 */         if ("BI".equals(operator.toString()))
/*      */         {
/*  385 */           PdfDictionary colorSpaceDic = resources != null ? resources.getAsDict(PdfName.COLORSPACE) : null;
/*  386 */           handleInlineImage(InlineImageUtils.parseInlineImage(ps, colorSpaceDic), colorSpaceDic);
/*      */         } else {
/*  388 */           invokeOperator(operator, operands);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  394 */       throw new ExceptionConverter(e);
/*      */     }
/*  396 */     this.resources.pop();
/*      */   }
/*      */ 
/*      */   protected void handleInlineImage(InlineImageInfo info, PdfDictionary colorSpaceDic)
/*      */   {
/*  406 */     ImageRenderInfo renderInfo = ImageRenderInfo.createForEmbeddedImage(gs().ctm, info, colorSpaceDic);
/*  407 */     this.renderListener.renderImage(renderInfo);
/*      */   }
/*      */ 
/*      */   private static BaseColor getColor(PdfName colorSpace, List<PdfObject> operands)
/*      */   {
/*  739 */     if (PdfName.DEVICEGRAY.equals(colorSpace)) {
/*  740 */       return getColor(1, operands);
/*      */     }
/*  742 */     if (PdfName.DEVICERGB.equals(colorSpace)) {
/*  743 */       return getColor(3, operands);
/*      */     }
/*  745 */     if (PdfName.DEVICECMYK.equals(colorSpace)) {
/*  746 */       return getColor(4, operands);
/*      */     }
/*  748 */     return null;
/*      */   }
/*      */ 
/*      */   private static BaseColor getColor(int nOperands, List<PdfObject> operands)
/*      */   {
/*  755 */     float[] c = new float[nOperands];
/*  756 */     for (int i = 0; i < nOperands; i++) {
/*  757 */       c[i] = ((PdfNumber)operands.get(i)).floatValue();
/*      */     }
/*  759 */     switch (nOperands) {
/*      */     case 1:
/*  761 */       return new GrayColor(c[0]);
/*      */     case 3:
/*  763 */       return new BaseColor(c[0], c[1], c[2]);
/*      */     case 4:
/*  765 */       return new CMYKColor(c[0], c[1], c[2], c[3]);
/*      */     case 2:
/*  767 */     }return null;
/*      */   }
/*      */ 
/*      */   private static class IgnoreXObjectDoHandler
/*      */     implements XObjectDoHandler
/*      */   {
/*      */     public void handleXObject(PdfContentStreamProcessor processor, PdfStream xobjectStream, PdfIndirectReference ref)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ImageXObjectDoHandler
/*      */     implements XObjectDoHandler
/*      */   {
/*      */     public void handleXObject(PdfContentStreamProcessor processor, PdfStream xobjectStream, PdfIndirectReference ref)
/*      */     {
/*  999 */       PdfDictionary colorSpaceDic = processor.resources.getAsDict(PdfName.COLORSPACE);
/* 1000 */       ImageRenderInfo renderInfo = ImageRenderInfo.createForXObject(processor.gs().ctm, ref, colorSpaceDic);
/* 1001 */       processor.renderListener.renderImage(renderInfo);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class FormXObjectDoHandler
/*      */     implements XObjectDoHandler
/*      */   {
/*      */     public void handleXObject(PdfContentStreamProcessor processor, PdfStream stream, PdfIndirectReference ref)
/*      */     {
/*  958 */       PdfDictionary resources = stream.getAsDict(PdfName.RESOURCES);
/*      */       byte[] contentBytes;
/*      */       try
/*      */       {
/*  965 */         contentBytes = ContentByteUtils.getContentBytesFromContentObject(stream);
/*      */       } catch (IOException e1) {
/*  967 */         throw new ExceptionConverter(e1);
/*      */       }
/*  969 */       PdfArray matrix = stream.getAsArray(PdfName.MATRIX);
/*      */ 
/*  971 */       new PdfContentStreamProcessor.PushGraphicsState(null).invoke(processor, null, null);
/*      */ 
/*  973 */       if (matrix != null) {
/*  974 */         float a = matrix.getAsNumber(0).floatValue();
/*  975 */         float b = matrix.getAsNumber(1).floatValue();
/*  976 */         float c = matrix.getAsNumber(2).floatValue();
/*  977 */         float d = matrix.getAsNumber(3).floatValue();
/*  978 */         float e = matrix.getAsNumber(4).floatValue();
/*  979 */         float f = matrix.getAsNumber(5).floatValue();
/*  980 */         Matrix formMatrix = new Matrix(a, b, c, d, e, f);
/*      */ 
/*  982 */         processor.gs().ctm = formMatrix.multiply(processor.gs().ctm);
/*      */       }
/*      */ 
/*  985 */       processor.processContent(contentBytes, resources);
/*      */ 
/*  987 */       new PdfContentStreamProcessor.PopGraphicsState(null).invoke(processor, null, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class Do
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */       throws IOException
/*      */     {
/*  946 */       PdfName xobjectName = (PdfName)operands.get(0);
/*  947 */       processor.displayXObject(xobjectName);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class EndMarkedContent
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */       throws Exception
/*      */     {
/*  937 */       processor.endMarkedContent();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class BeginMarkedContentDictionary
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */       throws Exception
/*      */     {
/*  915 */       PdfObject properties = (PdfObject)operands.get(1);
/*      */ 
/*  917 */       processor.beginMarkedContent((PdfName)operands.get(0), getPropertiesDictionary(properties, processor.resources));
/*      */     }
/*      */ 
/*      */     private PdfDictionary getPropertiesDictionary(PdfObject operand1, PdfContentStreamProcessor.ResourceDictionary resources) {
/*  921 */       if (operand1.isDictionary()) {
/*  922 */         return (PdfDictionary)operand1;
/*      */       }
/*  924 */       PdfName dictionaryName = (PdfName)operand1;
/*  925 */       return resources.getAsDict(dictionaryName);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class BeginMarkedContent
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */       throws Exception
/*      */     {
/*  900 */       processor.beginMarkedContent((PdfName)operands.get(0), new PdfDictionary());
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class EndText
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  885 */       processor.textMatrix = null;
/*  886 */       processor.textLineMatrix = null;
/*  887 */       processor.endText();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class BeginText
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  874 */       processor.textMatrix = new Matrix();
/*  875 */       processor.textLineMatrix = processor.textMatrix;
/*  876 */       processor.beginText();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class PopGraphicsState
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  865 */       processor.gsStack.pop();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetColorStroke
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  856 */       processor.gs().strokeColor = PdfContentStreamProcessor.getColor(PdfContentStreamProcessor.access$3700(processor).colorSpaceStroke, operands);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetColorFill
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  847 */       processor.gs().fillColor = PdfContentStreamProcessor.getColor(PdfContentStreamProcessor.access$3700(processor).colorSpaceFill, operands);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetColorSpaceStroke
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  838 */       processor.gs().colorSpaceStroke = ((PdfName)operands.get(0));
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetColorSpaceFill
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  829 */       processor.gs().colorSpaceFill = ((PdfName)operands.get(0));
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetCMYKStroke
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  820 */       processor.gs().strokeColor = PdfContentStreamProcessor.getColor(4, operands);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetCMYKFill
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  811 */       processor.gs().fillColor = PdfContentStreamProcessor.getColor(4, operands);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetRGBStroke
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  802 */       processor.gs().strokeColor = PdfContentStreamProcessor.getColor(3, operands);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetRGBFill
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  793 */       processor.gs().fillColor = PdfContentStreamProcessor.getColor(3, operands);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetGrayStroke
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  784 */       processor.gs().strokeColor = PdfContentStreamProcessor.getColor(1, operands);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetGrayFill
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  775 */       processor.gs().fillColor = PdfContentStreamProcessor.getColor(1, operands);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ModifyCurrentTransformationMatrix
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  723 */       float a = ((PdfNumber)operands.get(0)).floatValue();
/*  724 */       float b = ((PdfNumber)operands.get(1)).floatValue();
/*  725 */       float c = ((PdfNumber)operands.get(2)).floatValue();
/*  726 */       float d = ((PdfNumber)operands.get(3)).floatValue();
/*  727 */       float e = ((PdfNumber)operands.get(4)).floatValue();
/*  728 */       float f = ((PdfNumber)operands.get(5)).floatValue();
/*  729 */       Matrix matrix = new Matrix(a, b, c, d, e, f);
/*  730 */       GraphicsState gs = (GraphicsState)processor.gsStack.peek();
/*  731 */       gs.ctm = matrix.multiply(gs.ctm);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class PushGraphicsState
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  712 */       GraphicsState gs = (GraphicsState)processor.gsStack.peek();
/*  713 */       GraphicsState copy = new GraphicsState(gs);
/*  714 */       processor.gsStack.push(copy);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ProcessGraphicsStateResource
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  687 */       PdfName dictionaryName = (PdfName)operands.get(0);
/*  688 */       PdfDictionary extGState = processor.resources.getAsDict(PdfName.EXTGSTATE);
/*  689 */       if (extGState == null)
/*  690 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("resources.do.not.contain.extgstate.entry.unable.to.process.operator.1", new Object[] { operator }));
/*  691 */       PdfDictionary gsDic = extGState.getAsDict(dictionaryName);
/*  692 */       if (gsDic == null) {
/*  693 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("1.is.an.unknown.graphics.state.dictionary", new Object[] { dictionaryName }));
/*      */       }
/*      */ 
/*  696 */       PdfArray fontParameter = gsDic.getAsArray(PdfName.FONT);
/*  697 */       if (fontParameter != null) {
/*  698 */         CMapAwareDocumentFont font = processor.getFont((PRIndirectReference)fontParameter.getPdfObject(0));
/*  699 */         float size = fontParameter.getAsNumber(1).floatValue();
/*      */ 
/*  701 */         processor.gs().font = font;
/*  702 */         processor.gs().fontSize = size;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetTextWordSpacing
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  676 */       PdfNumber wordSpace = (PdfNumber)operands.get(0);
/*  677 */       processor.gs().wordSpacing = wordSpace.floatValue();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetTextCharacterSpacing
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  666 */       PdfNumber charSpace = (PdfNumber)operands.get(0);
/*  667 */       processor.gs().characterSpacing = charSpace.floatValue();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetTextHorizontalScaling
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  656 */       PdfNumber scale = (PdfNumber)operands.get(0);
/*  657 */       processor.gs().horizontalScaling = (scale.floatValue() / 100.0F);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetTextLeading
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  646 */       PdfNumber leading = (PdfNumber)operands.get(0);
/*  647 */       processor.gs().leading = leading.floatValue();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetTextRise
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  636 */       PdfNumber rise = (PdfNumber)operands.get(0);
/*  637 */       processor.gs().rise = rise.floatValue();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetTextRenderMode
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  626 */       PdfNumber render = (PdfNumber)operands.get(0);
/*  627 */       processor.gs().renderMode = render.intValue();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class SetTextFont
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  604 */       PdfName fontResourceName = (PdfName)operands.get(0);
/*  605 */       float size = ((PdfNumber)operands.get(1)).floatValue();
/*      */ 
/*  607 */       PdfDictionary fontsDictionary = processor.resources.getAsDict(PdfName.FONT);
/*      */ 
/*  609 */       PdfObject fontObject = fontsDictionary.get(fontResourceName);
/*      */       CMapAwareDocumentFont font;
/*      */       CMapAwareDocumentFont font;
/*  610 */       if ((fontObject instanceof PdfDictionary))
/*  611 */         font = processor.getFont((PdfDictionary)fontObject);
/*      */       else {
/*  613 */         font = processor.getFont((PRIndirectReference)fontObject);
/*      */       }
/*  615 */       processor.gs().font = font;
/*  616 */       processor.gs().fontSize = size;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class TextMoveStartNextLine
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  590 */       float tx = ((PdfNumber)operands.get(0)).floatValue();
/*  591 */       float ty = ((PdfNumber)operands.get(1)).floatValue();
/*      */ 
/*  593 */       Matrix translationMatrix = new Matrix(tx, ty);
/*  594 */       processor.textMatrix = translationMatrix.multiply(processor.textLineMatrix);
/*  595 */       processor.textLineMatrix = processor.textMatrix;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class TextMoveStartNextLineWithLeading
/*      */     implements ContentOperator
/*      */   {
/*      */     private final PdfContentStreamProcessor.TextMoveStartNextLine moveStartNextLine;
/*      */     private final PdfContentStreamProcessor.SetTextLeading setTextLeading;
/*      */ 
/*      */     public TextMoveStartNextLineWithLeading(PdfContentStreamProcessor.TextMoveStartNextLine moveStartNextLine, PdfContentStreamProcessor.SetTextLeading setTextLeading)
/*      */     {
/*  572 */       this.moveStartNextLine = moveStartNextLine;
/*  573 */       this.setTextLeading = setTextLeading;
/*      */     }
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands) {
/*  576 */       float ty = ((PdfNumber)operands.get(1)).floatValue();
/*      */ 
/*  578 */       ArrayList tlOperands = new ArrayList(1);
/*  579 */       tlOperands.add(0, new PdfNumber(-ty));
/*  580 */       this.setTextLeading.invoke(processor, null, tlOperands);
/*  581 */       this.moveStartNextLine.invoke(processor, null, operands);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class TextSetTextMatrix
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  553 */       float a = ((PdfNumber)operands.get(0)).floatValue();
/*  554 */       float b = ((PdfNumber)operands.get(1)).floatValue();
/*  555 */       float c = ((PdfNumber)operands.get(2)).floatValue();
/*  556 */       float d = ((PdfNumber)operands.get(3)).floatValue();
/*  557 */       float e = ((PdfNumber)operands.get(4)).floatValue();
/*  558 */       float f = ((PdfNumber)operands.get(5)).floatValue();
/*      */ 
/*  560 */       processor.textLineMatrix = new Matrix(a, b, c, d, e, f);
/*  561 */       processor.textMatrix = processor.textLineMatrix;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class TextMoveNextLine
/*      */     implements ContentOperator
/*      */   {
/*      */     private final PdfContentStreamProcessor.TextMoveStartNextLine moveStartNextLine;
/*      */ 
/*      */     public TextMoveNextLine(PdfContentStreamProcessor.TextMoveStartNextLine moveStartNextLine)
/*      */     {
/*  537 */       this.moveStartNextLine = moveStartNextLine;
/*      */     }
/*      */ 
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands) {
/*  541 */       ArrayList tdoperands = new ArrayList(2);
/*  542 */       tdoperands.add(0, new PdfNumber(0));
/*  543 */       tdoperands.add(1, new PdfNumber(-processor.gs().leading));
/*  544 */       this.moveStartNextLine.invoke(processor, null, tdoperands);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ShowText
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  524 */       PdfString string = (PdfString)operands.get(0);
/*      */ 
/*  526 */       processor.displayPdfString(string);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class MoveNextLineAndShowText
/*      */     implements ContentOperator
/*      */   {
/*      */     private final PdfContentStreamProcessor.TextMoveNextLine textMoveNextLine;
/*      */     private final PdfContentStreamProcessor.ShowText showText;
/*      */ 
/*      */     public MoveNextLineAndShowText(PdfContentStreamProcessor.TextMoveNextLine textMoveNextLine, PdfContentStreamProcessor.ShowText showText)
/*      */     {
/*  509 */       this.textMoveNextLine = textMoveNextLine;
/*  510 */       this.showText = showText;
/*      */     }
/*      */ 
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands) {
/*  514 */       this.textMoveNextLine.invoke(processor, null, new ArrayList(0));
/*  515 */       this.showText.invoke(processor, null, operands);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class MoveNextLineAndShowTextWithSpacing
/*      */     implements ContentOperator
/*      */   {
/*      */     private final PdfContentStreamProcessor.SetTextWordSpacing setTextWordSpacing;
/*      */     private final PdfContentStreamProcessor.SetTextCharacterSpacing setTextCharacterSpacing;
/*      */     private final PdfContentStreamProcessor.MoveNextLineAndShowText moveNextLineAndShowText;
/*      */ 
/*      */     public MoveNextLineAndShowTextWithSpacing(PdfContentStreamProcessor.SetTextWordSpacing setTextWordSpacing, PdfContentStreamProcessor.SetTextCharacterSpacing setTextCharacterSpacing, PdfContentStreamProcessor.MoveNextLineAndShowText moveNextLineAndShowText)
/*      */     {
/*  478 */       this.setTextWordSpacing = setTextWordSpacing;
/*  479 */       this.setTextCharacterSpacing = setTextCharacterSpacing;
/*  480 */       this.moveNextLineAndShowText = moveNextLineAndShowText;
/*      */     }
/*      */ 
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands) {
/*  484 */       PdfNumber aw = (PdfNumber)operands.get(0);
/*  485 */       PdfNumber ac = (PdfNumber)operands.get(1);
/*  486 */       PdfString string = (PdfString)operands.get(2);
/*      */ 
/*  488 */       ArrayList twOperands = new ArrayList(1);
/*  489 */       twOperands.add(0, aw);
/*  490 */       this.setTextWordSpacing.invoke(processor, null, twOperands);
/*      */ 
/*  492 */       ArrayList tcOperands = new ArrayList(1);
/*  493 */       tcOperands.add(0, ac);
/*  494 */       this.setTextCharacterSpacing.invoke(processor, null, tcOperands);
/*      */ 
/*  496 */       ArrayList tickOperands = new ArrayList(1);
/*  497 */       tickOperands.add(0, string);
/*  498 */       this.moveNextLineAndShowText.invoke(processor, null, tickOperands);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ShowTextArray
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*  453 */       PdfArray array = (PdfArray)operands.get(0);
/*  454 */       float tj = 0.0F;
/*  455 */       for (Iterator i = array.listIterator(); i.hasNext(); ) {
/*  456 */         PdfObject entryObj = (PdfObject)i.next();
/*  457 */         if ((entryObj instanceof PdfString)) {
/*  458 */           processor.displayPdfString((PdfString)entryObj);
/*  459 */           tj = 0.0F;
/*      */         } else {
/*  461 */           tj = ((PdfNumber)entryObj).floatValue();
/*  462 */           processor.applyTextAdjust(tj);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class IgnoreOperatorContentOperator
/*      */     implements ContentOperator
/*      */   {
/*      */     public void invoke(PdfContentStreamProcessor processor, PdfLiteral operator, ArrayList<PdfObject> operands)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ResourceDictionary extends PdfDictionary
/*      */   {
/*  414 */     private final List<PdfDictionary> resourcesStack = new ArrayList();
/*      */ 
/*      */     public void push(PdfDictionary resources)
/*      */     {
/*  419 */       this.resourcesStack.add(resources);
/*      */     }
/*      */ 
/*      */     public void pop() {
/*  423 */       this.resourcesStack.remove(this.resourcesStack.size() - 1);
/*      */     }
/*      */ 
/*      */     public PdfObject getDirectObject(PdfName key)
/*      */     {
/*  428 */       for (int i = this.resourcesStack.size() - 1; i >= 0; i--) {
/*  429 */         PdfDictionary subResource = (PdfDictionary)this.resourcesStack.get(i);
/*  430 */         if (subResource != null) {
/*  431 */           PdfObject obj = subResource.getDirectObject(key);
/*  432 */           if (obj != null) return obj;
/*      */         }
/*      */       }
/*  435 */       return super.getDirectObject(key);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.PdfContentStreamProcessor
 * JD-Core Version:    0.6.2
 */