/*      */ package org.apache.pdfbox.pdmodel.interactive.form;
/*      */ 
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import org.apache.pdfbox.cos.COSArray;
/*      */ import org.apache.pdfbox.cos.COSBase;
/*      */ import org.apache.pdfbox.cos.COSDictionary;
/*      */ import org.apache.pdfbox.cos.COSDocument;
/*      */ import org.apache.pdfbox.cos.COSFloat;
/*      */ import org.apache.pdfbox.cos.COSInteger;
/*      */ import org.apache.pdfbox.cos.COSName;
/*      */ import org.apache.pdfbox.cos.COSNumber;
/*      */ import org.apache.pdfbox.cos.COSStream;
/*      */ import org.apache.pdfbox.cos.COSString;
/*      */ import org.apache.pdfbox.pdfparser.PDFStreamParser;
/*      */ import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
/*      */ import org.apache.pdfbox.pdmodel.PDDocument;
/*      */ import org.apache.pdfbox.pdmodel.PDResources;
/*      */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*      */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*      */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*      */ import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
/*      */ import org.apache.pdfbox.pdmodel.font.PDSimpleFont;
/*      */ import org.apache.pdfbox.pdmodel.interactive.action.PDFormFieldAdditionalActions;
/*      */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
/*      */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
/*      */ import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
/*      */ import org.apache.pdfbox.util.PDFOperator;
/*      */ 
/*      */ public class PDAppearance
/*      */ {
/*      */   private final PDVariableText parent;
/*      */   private String value;
/*      */   private final COSString defaultAppearance;
/*      */   private final PDAcroForm acroForm;
/*   71 */   private List<COSObjectable> widgets = new ArrayList();
/*      */   private static final String HIGHLIGHT_COLOR = "0.600006 0.756866 0.854904 rg";
/*      */   private static final int DEFAULT_PADDING = 1;
/*   99 */   private PDRectangle paddingEdge = null;
/*      */ 
/*  109 */   private PDRectangle contentArea = null;
/*      */ 
/*      */   public PDAppearance(PDAcroForm theAcroForm, PDVariableText field)
/*      */     throws IOException
/*      */   {
/*  123 */     this.acroForm = theAcroForm;
/*  124 */     this.parent = field;
/*      */ 
/*  126 */     this.widgets = field.getKids();
/*  127 */     if (this.widgets == null)
/*      */     {
/*  129 */       this.widgets = new ArrayList();
/*  130 */       this.widgets.add(field.getWidget());
/*      */     }
/*      */ 
/*  133 */     this.defaultAppearance = getDefaultAppearance();
/*      */   }
/*      */ 
/*      */   private COSString getDefaultAppearance()
/*      */   {
/*  146 */     COSString dap = this.parent.getDefaultAppearance();
/*  147 */     if (dap == null)
/*      */     {
/*  149 */       COSArray kids = (COSArray)this.parent.getDictionary().getDictionaryObject(COSName.KIDS);
/*  150 */       if ((kids != null) && (kids.size() > 0))
/*      */       {
/*  152 */         COSDictionary firstKid = (COSDictionary)kids.getObject(0);
/*  153 */         dap = (COSString)firstKid.getDictionaryObject(COSName.DA);
/*      */       }
/*  155 */       if (dap == null)
/*      */       {
/*  157 */         dap = (COSString)this.acroForm.getDictionary().getDictionaryObject(COSName.DA);
/*      */       }
/*      */     }
/*  160 */     return dap;
/*      */   }
/*      */ 
/*      */   private int getQ()
/*      */   {
/*  165 */     int q = this.parent.getQ();
/*  166 */     if (this.parent.getDictionary().getDictionaryObject(COSName.Q) == null)
/*      */     {
/*  168 */       COSArray kids = (COSArray)this.parent.getDictionary().getDictionaryObject(COSName.KIDS);
/*  169 */       if ((kids != null) && (kids.size() > 0))
/*      */       {
/*  171 */         COSDictionary firstKid = (COSDictionary)kids.getObject(0);
/*  172 */         COSNumber qNum = (COSNumber)firstKid.getDictionaryObject(COSName.Q);
/*  173 */         if (qNum != null)
/*      */         {
/*  175 */           q = qNum.intValue();
/*      */         }
/*      */       }
/*      */     }
/*  179 */     return q;
/*      */   }
/*      */ 
/*      */   private List getStreamTokens(PDAppearanceStream appearanceStream)
/*      */     throws IOException
/*      */   {
/*  189 */     List tokens = null;
/*  190 */     if (appearanceStream != null)
/*      */     {
/*  192 */       tokens = getStreamTokens(appearanceStream.getStream());
/*      */     }
/*  194 */     return tokens;
/*      */   }
/*      */ 
/*      */   private List getStreamTokens(COSString string)
/*      */     throws IOException
/*      */   {
/*  201 */     List tokens = null;
/*  202 */     if (string != null)
/*      */     {
/*  204 */       ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes());
/*  205 */       PDFStreamParser parser = new PDFStreamParser(stream, this.acroForm.getDocument().getDocument().getScratchFile());
/*  206 */       parser.parse();
/*  207 */       tokens = parser.getTokens();
/*      */     }
/*  209 */     return tokens;
/*      */   }
/*      */ 
/*      */   private List getStreamTokens(COSStream stream)
/*      */     throws IOException
/*      */   {
/*  216 */     List tokens = null;
/*  217 */     if (stream != null)
/*      */     {
/*  219 */       PDFStreamParser parser = new PDFStreamParser(stream);
/*  220 */       parser.parse();
/*  221 */       tokens = parser.getTokens();
/*      */     }
/*  223 */     return tokens;
/*      */   }
/*      */ 
/*      */   private boolean containsMarkedContent(List stream)
/*      */   {
/*  233 */     return stream.contains(PDFOperator.getOperator("BMC"));
/*      */   }
/*      */ 
/*      */   private PDRectangle applyPadding(PDRectangle bbox, float padding)
/*      */   {
/*  245 */     PDRectangle area = new PDRectangle(bbox.getCOSArray());
/*      */ 
/*  247 */     area.setLowerLeftX(area.getLowerLeftX() + padding);
/*  248 */     area.setLowerLeftY(area.getLowerLeftY() + padding);
/*  249 */     area.setUpperRightX(area.getUpperRightX() - padding);
/*  250 */     area.setUpperRightY(area.getUpperRightY() - padding);
/*      */ 
/*  252 */     return area;
/*      */   }
/*      */ 
/*      */   public void setAppearanceValue(String apValue)
/*      */     throws IOException
/*      */   {
/*  266 */     this.value = apValue;
/*      */ 
/*  268 */     Iterator widgetIter = this.widgets.iterator();
/*  269 */     label643: while (widgetIter.hasNext())
/*      */     {
/*  271 */       COSObjectable next = (COSObjectable)widgetIter.next();
/*  272 */       PDField field = null;
/*      */       PDAnnotationWidget widget;
/*      */       PDAnnotationWidget widget;
/*  274 */       if ((next instanceof PDField))
/*      */       {
/*  276 */         field = (PDField)next;
/*  277 */         widget = field.getWidget();
/*      */       }
/*      */       else
/*      */       {
/*  281 */         widget = (PDAnnotationWidget)next;
/*      */       }
/*  283 */       PDFormFieldAdditionalActions actions = null;
/*  284 */       if (field != null)
/*      */       {
/*  286 */         actions = field.getActions();
/*      */       }
/*  288 */       if ((actions == null) || (actions.getF() == null) || (widget.getDictionary().getDictionaryObject(COSName.AP) != null))
/*      */       {
/*  297 */         PDAppearanceDictionary appearance = widget.getAppearance();
/*  298 */         if (appearance == null)
/*      */         {
/*  300 */           appearance = new PDAppearanceDictionary();
/*  301 */           widget.setAppearance(appearance);
/*      */         }
/*      */ 
/*  304 */         Map normalAppearance = appearance.getNormalAppearance();
/*  305 */         PDAppearanceStream appearanceStream = (PDAppearanceStream)normalAppearance.get("default");
/*  306 */         if (appearanceStream == null)
/*      */         {
/*  308 */           COSStream cosStream = this.acroForm.getDocument().getDocument().createCOSStream();
/*  309 */           appearanceStream = new PDAppearanceStream(cosStream);
/*  310 */           appearanceStream.setBoundingBox(widget.getRectangle().createRetranslatedRectangle());
/*  311 */           appearance.setNormalAppearance(appearanceStream);
/*      */         }
/*      */ 
/*  314 */         List tokens = getStreamTokens(appearanceStream);
/*  315 */         List daTokens = getStreamTokens(getDefaultAppearance());
/*  316 */         PDFont pdFont = getFontAndUpdateResources(tokens, appearanceStream);
/*      */ 
/*  320 */         if ((this.parent instanceof PDChoiceField)) { ((PDChoiceField)this.parent); if ((this.parent.getFieldFlags() & 0x20000) == 0)
/*      */           {
/*  323 */             generateListboxAppearance(widget, pdFont, tokens, daTokens, appearanceStream, this.value); break label643;
/*      */           }
/*      */         }
/*      */ 
/*  327 */         if (!containsMarkedContent(tokens))
/*      */         {
/*  329 */           ByteArrayOutputStream output = new ByteArrayOutputStream();
/*      */ 
/*  334 */           ContentStreamWriter writer = new ContentStreamWriter(output);
/*  335 */           writer.writeTokens(tokens);
/*      */ 
/*  337 */           output.write(" /Tx BMC\n".getBytes("ISO-8859-1"));
/*  338 */           insertGeneratedAppearance(widget, output, pdFont, tokens, appearanceStream);
/*  339 */           output.write(" EMC".getBytes("ISO-8859-1"));
/*  340 */           writeToStream(output.toByteArray(), appearanceStream);
/*      */         }
/*  344 */         else if (tokens != null)
/*      */         {
/*  346 */           if (daTokens != null)
/*      */           {
/*  348 */             int bmcIndex = tokens.indexOf(PDFOperator.getOperator("BMC"));
/*  349 */             int emcIndex = tokens.indexOf(PDFOperator.getOperator("EMC"));
/*  350 */             if ((bmcIndex != -1) && (emcIndex != -1) && (emcIndex == bmcIndex + 1))
/*      */             {
/*  354 */               tokens.addAll(emcIndex, daTokens);
/*      */             }
/*      */           }
/*  357 */           ByteArrayOutputStream output = new ByteArrayOutputStream();
/*  358 */           ContentStreamWriter writer = new ContentStreamWriter(output);
/*  359 */           float fontSize = calculateFontSize(pdFont, appearanceStream.getBoundingBox(), tokens, daTokens);
/*  360 */           boolean foundString = false;
/*  361 */           int indexOfString = -1;
/*      */ 
/*  363 */           int setFontIndex = tokens.indexOf(PDFOperator.getOperator("Tf"));
/*  364 */           tokens.set(setFontIndex - 1, new COSFloat(fontSize));
/*      */ 
/*  366 */           int bmcIndex = tokens.indexOf(PDFOperator.getOperator("BMC"));
/*  367 */           int emcIndex = tokens.indexOf(PDFOperator.getOperator("EMC"));
/*      */ 
/*  369 */           if (bmcIndex != -1)
/*      */           {
/*  371 */             writer.writeTokens(tokens, 0, bmcIndex + 1);
/*      */           }
/*      */           else
/*      */           {
/*  375 */             writer.writeTokens(tokens);
/*      */           }
/*  377 */           output.write("\n".getBytes("ISO-8859-1"));
/*  378 */           insertGeneratedAppearance(widget, output, pdFont, tokens, appearanceStream);
/*  379 */           if (emcIndex != -1)
/*      */           {
/*  381 */             writer.writeTokens(tokens, emcIndex, tokens.size());
/*      */           }
/*      */ 
/*  384 */           writeToStream(output.toByteArray(), appearanceStream);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void generateListboxAppearance(PDAnnotationWidget fieldWidget, PDFont pdFont, List tokens, List daTokens, PDAppearanceStream appearanceStream, String fieldValue)
/*      */     throws IOException
/*      */   {
/*  404 */     this.paddingEdge = applyPadding(appearanceStream.getBoundingBox(), 1.0F);
/*  405 */     this.contentArea = applyPadding(this.paddingEdge, 1.0F);
/*      */ 
/*  407 */     if (!containsMarkedContent(tokens))
/*      */     {
/*  409 */       ByteArrayOutputStream output = new ByteArrayOutputStream();
/*      */ 
/*  414 */       ContentStreamWriter writer = new ContentStreamWriter(output);
/*  415 */       writer.writeTokens(tokens);
/*      */ 
/*  417 */       output.write(" /Tx BMC\n".getBytes("ISO-8859-1"));
/*  418 */       insertGeneratedListboxAppearance(fieldWidget, output, pdFont, tokens, appearanceStream);
/*  419 */       output.write(" EMC".getBytes("ISO-8859-1"));
/*  420 */       writeToStream(output.toByteArray(), appearanceStream);
/*      */     }
/*  424 */     else if (tokens != null)
/*      */     {
/*  426 */       if (daTokens != null)
/*      */       {
/*  428 */         int bmcIndex = tokens.indexOf(PDFOperator.getOperator("BMC"));
/*  429 */         int emcIndex = tokens.indexOf(PDFOperator.getOperator("EMC"));
/*  430 */         if ((bmcIndex != -1) && (emcIndex != -1) && (emcIndex == bmcIndex + 1))
/*      */         {
/*  434 */           tokens.addAll(emcIndex, daTokens);
/*      */         }
/*      */       }
/*      */ 
/*  438 */       ByteArrayOutputStream output = new ByteArrayOutputStream();
/*  439 */       ContentStreamWriter writer = new ContentStreamWriter(output);
/*  440 */       float fontSize = calculateListboxFontSize(pdFont, appearanceStream.getBoundingBox(), tokens, daTokens);
/*  441 */       boolean foundString = false;
/*      */ 
/*  443 */       int setFontIndex = tokens.indexOf(PDFOperator.getOperator("Tf"));
/*  444 */       tokens.set(setFontIndex - 1, new COSFloat(fontSize));
/*      */ 
/*  446 */       int bmcIndex = tokens.indexOf(PDFOperator.getOperator("BMC"));
/*      */ 
/*  455 */       int beginTextIndex = tokens.indexOf(PDFOperator.getOperator("BT"));
/*  456 */       if (beginTextIndex != -1)
/*      */       {
/*  459 */         ListIterator innerTokens = tokens.listIterator(bmcIndex);
/*      */ 
/*  461 */         while (innerTokens.hasNext())
/*      */         {
/*  463 */           if ((innerTokens.next() == PDFOperator.getOperator("re")) && (innerTokens.next() == PDFOperator.getOperator("W")))
/*      */           {
/*  467 */             COSArray array = new COSArray();
/*  468 */             array.add((COSNumber)tokens.get(innerTokens.previousIndex() - 5));
/*  469 */             array.add((COSNumber)tokens.get(innerTokens.previousIndex() - 4));
/*  470 */             array.add((COSNumber)tokens.get(innerTokens.previousIndex() - 3));
/*  471 */             array.add((COSNumber)tokens.get(innerTokens.previousIndex() - 2));
/*      */ 
/*  473 */             this.paddingEdge = new PDRectangle(array);
/*      */ 
/*  477 */             this.paddingEdge.setUpperRightX(this.paddingEdge.getLowerLeftX() + this.paddingEdge.getUpperRightX());
/*  478 */             this.paddingEdge.setUpperRightY(this.paddingEdge.getLowerLeftY() + this.paddingEdge.getUpperRightY());
/*      */ 
/*  480 */             this.contentArea = applyPadding(this.paddingEdge, this.paddingEdge.getLowerLeftX() - appearanceStream.getBoundingBox().getLowerLeftX());
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  489 */       int emcIndex = tokens.indexOf(PDFOperator.getOperator("EMC"));
/*      */ 
/*  491 */       if (bmcIndex != -1)
/*      */       {
/*  493 */         writer.writeTokens(tokens, 0, bmcIndex + 1);
/*      */       }
/*      */       else
/*      */       {
/*  497 */         writer.writeTokens(tokens);
/*      */       }
/*  499 */       output.write("\n".getBytes("ISO-8859-1"));
/*  500 */       insertGeneratedListboxAppearance(fieldWidget, output, pdFont, tokens, appearanceStream);
/*  501 */       if (emcIndex != -1)
/*      */       {
/*  503 */         writer.writeTokens(tokens, emcIndex, tokens.size());
/*      */       }
/*      */ 
/*  506 */       writeToStream(output.toByteArray(), appearanceStream);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void insertGeneratedAppearance(PDAnnotationWidget fieldWidget, OutputStream output, PDFont pdFont, List tokens, PDAppearanceStream appearanceStream)
/*      */     throws IOException
/*      */   {
/*  518 */     PrintWriter printWriter = new PrintWriter(output, true);
/*  519 */     float fontSize = 0.0F;
/*  520 */     PDRectangle boundingBox = appearanceStream.getBoundingBox();
/*  521 */     if (boundingBox == null)
/*      */     {
/*  523 */       boundingBox = fieldWidget.getRectangle().createRetranslatedRectangle();
/*      */     }
/*      */ 
/*  529 */     if (this.parent.shouldComb()) {
/*  530 */       insertGeneratedPaddingEdge(printWriter, appearanceStream);
/*      */     }
/*      */ 
/*  533 */     printWriter.println("BT");
/*      */ 
/*  535 */     if (this.defaultAppearance != null)
/*      */     {
/*  537 */       String daString = this.defaultAppearance.getString();
/*  538 */       PDFStreamParser daParser = new PDFStreamParser(new ByteArrayInputStream(daString.getBytes("ISO-8859-1")), null);
/*      */ 
/*  540 */       daParser.parse();
/*  541 */       List daTokens = daParser.getTokens();
/*  542 */       fontSize = calculateFontSize(pdFont, boundingBox, tokens, daTokens);
/*  543 */       int fontIndex = daTokens.indexOf(PDFOperator.getOperator("Tf"));
/*  544 */       if (fontIndex != -1)
/*      */       {
/*  546 */         daTokens.set(fontIndex - 1, new COSFloat(fontSize));
/*      */       }
/*  548 */       ContentStreamWriter daWriter = new ContentStreamWriter(output);
/*  549 */       daWriter.writeTokens(daTokens);
/*      */     }
/*      */ 
/*  555 */     if ((this.parent.shouldComb()) && (this.parent.getDictionary().getInt("MaxLen") != -1)) {
/*  556 */       insertGeneratedCombAppearance(printWriter, pdFont, appearanceStream, fontSize);
/*      */     } else {
/*  558 */       printWriter.println(getTextPosition(boundingBox, pdFont, fontSize, tokens));
/*  559 */       int q = getQ();
/*  560 */       if (q != 0)
/*      */       {
/*  564 */         if ((q == 1) || (q == 2))
/*      */         {
/*  566 */           float fieldWidth = boundingBox.getWidth();
/*  567 */           float stringWidth = pdFont.getStringWidth(this.value) / 1000.0F * fontSize;
/*  568 */           float adjustAmount = fieldWidth - stringWidth - 4.0F;
/*      */ 
/*  570 */           if (q == 1)
/*      */           {
/*  572 */             adjustAmount /= 2.0F;
/*      */           }
/*      */ 
/*  575 */           printWriter.println(adjustAmount + " 0 Td");
/*      */         }
/*      */         else
/*      */         {
/*  579 */           throw new IOException("Error: Unknown justification value:" + q);
/*      */         }
/*      */       }
/*  582 */       if (!isMultiLineValue(this.value))
/*      */       {
/*  584 */         printWriter.println("<" + new COSString(this.value).getHexString() + "> Tj");
/*      */       }
/*      */       else
/*      */       {
/*  588 */         String[] lines = this.value.split("\n");
/*  589 */         for (int i = 0; i < lines.length; i++)
/*      */         {
/*  591 */           boolean lastLine = i == lines.length - 1;
/*  592 */           String endingTag = lastLine ? "> Tj\n" : "> Tj 0 -13 Td";
/*  593 */           printWriter.print("<" + new COSString(lines[i]).getHexString() + endingTag);
/*      */         }
/*      */       }
/*      */     }
/*  597 */     printWriter.println("ET");
/*  598 */     printWriter.flush();
/*      */   }
/*      */ 
/*      */   private void insertGeneratedPaddingEdge(PrintWriter printWriter, PDAppearanceStream appearanceStream)
/*      */   {
/*  605 */     this.paddingEdge = applyPadding(appearanceStream.getBoundingBox(), 1.0F);
/*      */ 
/*  608 */     printWriter.println("q");
/*      */ 
/*  610 */     printWriter.println(this.paddingEdge.getLowerLeftX() + " " + this.paddingEdge.getLowerLeftY() + " " + this.paddingEdge.getWidth() + " " + this.paddingEdge.getHeight() + " " + " re");
/*      */ 
/*  612 */     printWriter.println("W");
/*  613 */     printWriter.println("n");
/*      */   }
/*      */ 
/*      */   private void insertGeneratedCombAppearance(PrintWriter printWriter, PDFont pdFont, PDAppearanceStream appearanceStream, float fontSize)
/*      */     throws IOException
/*      */   {
/*  623 */     int maxLen = this.parent.getDictionary().getInt("MaxLen");
/*      */ 
/*  625 */     int numChars = maxLen;
/*      */ 
/*  627 */     if (this.value.length() < maxLen)
/*      */     {
/*  629 */       numChars = this.value.length();
/*      */     }
/*      */ 
/*  632 */     float combWidth = appearanceStream.getBoundingBox().getWidth() / maxLen;
/*  633 */     float ascentAtFontSize = pdFont.getFontDescriptor().getAscent() / 1000.0F * fontSize;
/*  634 */     float baselineOffset = this.paddingEdge.getLowerLeftY() + (appearanceStream.getBoundingBox().getHeight() - ascentAtFontSize) / 2.0F;
/*      */ 
/*  637 */     float prevCharWidth = 0.0F;
/*  638 */     float currCharWidth = 0.0F;
/*      */ 
/*  640 */     float xOffset = combWidth / 2.0F;
/*      */ 
/*  642 */     String combString = "";
/*      */ 
/*  644 */     for (int i = 0; i < numChars; i++)
/*      */     {
/*  646 */       combString = this.value.substring(i, i + 1);
/*  647 */       currCharWidth = pdFont.getStringWidth(combString) / 1000.0F * fontSize / 2.0F;
/*      */ 
/*  649 */       xOffset = xOffset + prevCharWidth / 2.0F - currCharWidth / 2.0F;
/*      */ 
/*  651 */       printWriter.println(xOffset + " " + baselineOffset + " Td");
/*  652 */       printWriter.println("<" + new COSString(combString).getHexString() + "> Tj");
/*      */ 
/*  654 */       baselineOffset = 0.0F;
/*  655 */       prevCharWidth = currCharWidth;
/*  656 */       xOffset = combWidth;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void insertGeneratedListboxAppearance(PDAnnotationWidget fieldWidget, OutputStream output, PDFont pdFont, List tokens, PDAppearanceStream appearanceStream)
/*      */     throws IOException
/*      */   {
/*  663 */     PrintWriter printWriter = new PrintWriter(output, true);
/*  664 */     float fontSize = 0.0F;
/*  665 */     PDRectangle boundingBox = appearanceStream.getBoundingBox();
/*  666 */     if (boundingBox == null)
/*      */     {
/*  668 */       boundingBox = fieldWidget.getRectangle().createRetranslatedRectangle();
/*      */     }
/*      */ 
/*  671 */     List daTokens = null;
/*      */ 
/*  673 */     if (this.defaultAppearance != null)
/*      */     {
/*  675 */       String daString = this.defaultAppearance.getString();
/*  676 */       PDFStreamParser daParser = new PDFStreamParser(new ByteArrayInputStream(daString.getBytes("ISO-8859-1")), null);
/*      */ 
/*  678 */       daParser.parse();
/*  679 */       daTokens = daParser.getTokens();
/*      */ 
/*  681 */       fontSize = calculateListboxFontSize(pdFont, this.contentArea, tokens, daTokens);
/*  682 */       int fontIndex = daTokens.indexOf(PDFOperator.getOperator("Tf"));
/*  683 */       if (fontIndex != -1)
/*      */       {
/*  685 */         daTokens.set(fontIndex - 1, new COSFloat(fontSize));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  690 */     printWriter.println("q");
/*      */ 
/*  692 */     printWriter.println(this.paddingEdge.getLowerLeftX() + " " + this.paddingEdge.getLowerLeftY() + " " + this.paddingEdge.getWidth() + " " + this.paddingEdge.getHeight() + " " + " re");
/*      */ 
/*  694 */     printWriter.println("W");
/*  695 */     printWriter.println("n");
/*      */ 
/*  698 */     printWriter.println("0.600006 0.756866 0.854904 rg");
/*      */ 
/*  704 */     COSArray indexEntries = ((PDChoiceField)this.parent).getSelectedOptions();
/*      */ 
/*  706 */     int selectedIndex = ((COSInteger)indexEntries.get(0)).intValue();
/*      */ 
/*  711 */     int topIndex = ((PDChoiceField)this.parent).getTopIndex();
/*      */ 
/*  713 */     float highlightBoxHeight = pdFont.getFontBoundingBox().getHeight() / 1000.0F * fontSize;
/*      */ 
/*  715 */     printWriter.println(this.paddingEdge.getLowerLeftX() + " " + (this.paddingEdge.getUpperRightY() - highlightBoxHeight * (selectedIndex - topIndex + 1)) + " " + this.paddingEdge.getWidth() + " " + highlightBoxHeight + " re");
/*      */ 
/*  718 */     printWriter.println("f");
/*  719 */     printWriter.println("0 g");
/*  720 */     printWriter.println("0 G");
/*  721 */     printWriter.println("1 w");
/*      */ 
/*  724 */     printWriter.println("BT");
/*      */ 
/*  726 */     if (this.defaultAppearance != null)
/*      */     {
/*  728 */       ContentStreamWriter daWriter = new ContentStreamWriter(output);
/*  729 */       daWriter.writeTokens(daTokens);
/*      */     }
/*      */ 
/*  732 */     int q = getQ();
/*  733 */     if (q != 0)
/*      */     {
/*  737 */       if ((q == 1) || (q == 2))
/*      */       {
/*  739 */         float fieldWidth = boundingBox.getWidth();
/*  740 */         float stringWidth = pdFont.getStringWidth(this.value) / 1000.0F * fontSize;
/*  741 */         float adjustAmount = fieldWidth - stringWidth - 4.0F;
/*      */ 
/*  743 */         if (q == 1)
/*      */         {
/*  745 */           adjustAmount /= 2.0F;
/*      */         }
/*      */ 
/*  748 */         printWriter.println(adjustAmount + " 0 Td");
/*      */       }
/*      */       else
/*      */       {
/*  752 */         throw new IOException("Error: Unknown justification value:" + q);
/*      */       }
/*      */     }
/*  755 */     COSArray options = ((PDChoiceField)this.parent).getOptions();
/*      */ 
/*  757 */     float yTextPos = this.contentArea.getUpperRightY();
/*      */ 
/*  759 */     for (int i = topIndex; i < options.size(); i++)
/*      */     {
/*  761 */       COSBase option = options.getObject(i);
/*  762 */       COSArray optionPair = (COSArray)option;
/*  763 */       COSString optionKey = (COSString)optionPair.getObject(0);
/*  764 */       COSString optionValue = (COSString)optionPair.getObject(1);
/*      */ 
/*  766 */       if (i == topIndex)
/*      */       {
/*  768 */         yTextPos -= pdFont.getFontDescriptor().getAscent() / 1000.0F * fontSize;
/*      */       }
/*      */       else
/*      */       {
/*  772 */         yTextPos -= pdFont.getFontBoundingBox().getHeight() / 1000.0F * fontSize;
/*  773 */         printWriter.println("BT");
/*      */       }
/*      */ 
/*  776 */       printWriter.println(this.contentArea.getLowerLeftX() + " " + yTextPos + " Td");
/*  777 */       printWriter.println("<" + optionValue.getHexString() + "> Tj");
/*      */ 
/*  779 */       if (i - topIndex != options.size() - 1)
/*      */       {
/*  781 */         printWriter.println("ET");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  786 */     printWriter.println("ET");
/*  787 */     printWriter.println("Q");
/*  788 */     printWriter.flush();
/*      */   }
/*      */ 
/*      */   private PDFont getFontAndUpdateResources(List tokens, PDAppearanceStream appearanceStream) throws IOException
/*      */   {
/*  793 */     PDFont retval = null;
/*  794 */     PDResources streamResources = appearanceStream.getResources();
/*  795 */     PDResources formResources = this.acroForm.getDefaultResources();
/*  796 */     if (formResources != null)
/*      */     {
/*  798 */       if (streamResources == null)
/*      */       {
/*  800 */         streamResources = new PDResources();
/*  801 */         appearanceStream.setResources(streamResources);
/*      */       }
/*      */ 
/*  804 */       COSString da = getDefaultAppearance();
/*  805 */       if (da != null)
/*      */       {
/*  807 */         String data = da.getString();
/*  808 */         PDFStreamParser streamParser = new PDFStreamParser(new ByteArrayInputStream(data.getBytes("ISO-8859-1")), null);
/*      */ 
/*  810 */         streamParser.parse();
/*  811 */         tokens = streamParser.getTokens();
/*      */       }
/*      */ 
/*  814 */       int setFontIndex = tokens.indexOf(PDFOperator.getOperator("Tf"));
/*  815 */       COSName cosFontName = (COSName)tokens.get(setFontIndex - 2);
/*  816 */       String fontName = cosFontName.getName();
/*  817 */       retval = (PDFont)streamResources.getFonts().get(fontName);
/*  818 */       if (retval == null)
/*      */       {
/*  820 */         retval = (PDFont)formResources.getFonts().get(fontName);
/*  821 */         streamResources.addFont(retval, fontName);
/*      */       }
/*      */     }
/*  824 */     return retval;
/*      */   }
/*      */ 
/*      */   private boolean isMultiLineValue(String value)
/*      */   {
/*  829 */     return (this.parent.isMultiline()) && (value.contains("\n"));
/*      */   }
/*      */ 
/*      */   private void writeToStream(byte[] data, PDAppearanceStream appearanceStream)
/*      */     throws IOException
/*      */   {
/*  840 */     OutputStream out = appearanceStream.getStream().createUnfilteredStream();
/*  841 */     out.write(data);
/*  842 */     out.flush();
/*      */   }
/*      */ 
/*      */   private float getLineWidth(List tokens)
/*      */   {
/*  853 */     float retval = 1.0F;
/*  854 */     if (tokens != null)
/*      */     {
/*  856 */       int btIndex = tokens.indexOf(PDFOperator.getOperator("BT"));
/*  857 */       int wIndex = tokens.indexOf(PDFOperator.getOperator("w"));
/*      */ 
/*  859 */       if ((wIndex > 0) && (wIndex < btIndex))
/*      */       {
/*  861 */         retval = ((COSNumber)tokens.get(wIndex - 1)).floatValue();
/*      */       }
/*      */     }
/*  864 */     return retval;
/*      */   }
/*      */ 
/*      */   private PDRectangle getSmallestDrawnRectangle(PDRectangle boundingBox, List tokens)
/*      */   {
/*  869 */     PDRectangle smallest = boundingBox;
/*  870 */     for (int i = 0; i < tokens.size(); i++)
/*      */     {
/*  872 */       Object next = tokens.get(i);
/*  873 */       if (next == PDFOperator.getOperator("re"))
/*      */       {
/*  875 */         COSNumber x = (COSNumber)tokens.get(i - 4);
/*  876 */         COSNumber y = (COSNumber)tokens.get(i - 3);
/*  877 */         COSNumber width = (COSNumber)tokens.get(i - 2);
/*  878 */         COSNumber height = (COSNumber)tokens.get(i - 1);
/*  879 */         PDRectangle potentialSmallest = new PDRectangle();
/*  880 */         potentialSmallest.setLowerLeftX(x.floatValue());
/*  881 */         potentialSmallest.setLowerLeftY(y.floatValue());
/*  882 */         potentialSmallest.setUpperRightX(x.floatValue() + width.floatValue());
/*  883 */         potentialSmallest.setUpperRightY(y.floatValue() + height.floatValue());
/*  884 */         if ((smallest == null) || (smallest.getLowerLeftX() < potentialSmallest.getLowerLeftX()) || (smallest.getUpperRightY() > potentialSmallest.getUpperRightY()))
/*      */         {
/*  887 */           smallest = potentialSmallest;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  892 */     return smallest;
/*      */   }
/*      */ 
/*      */   private float calculateFontSize(PDFont pdFont, PDRectangle boundingBox, List<Object> tokens, List<Object> daTokens)
/*      */     throws IOException
/*      */   {
/*  906 */     float fontSize = 0.0F;
/*  907 */     if (daTokens != null)
/*      */     {
/*  911 */       int fontIndex = daTokens.indexOf(PDFOperator.getOperator("Tf"));
/*  912 */       if (fontIndex != -1)
/*      */       {
/*  914 */         fontSize = ((COSNumber)daTokens.get(fontIndex - 1)).floatValue();
/*      */       }
/*      */     }
/*      */ 
/*  918 */     float widthBasedFontSize = 3.4028235E+38F;
/*      */ 
/*  920 */     if (this.parent.doNotScroll())
/*      */     {
/*  923 */       float widthAtFontSize1 = pdFont.getStringWidth(this.value) / 1000.0F;
/*  924 */       float availableWidth = getAvailableWidth(boundingBox, getLineWidth(tokens));
/*  925 */       widthBasedFontSize = availableWidth / widthAtFontSize1;
/*      */     }
/*  927 */     else if (fontSize == 0.0F)
/*      */     {
/*  929 */       float lineWidth = getLineWidth(tokens);
/*  930 */       float stringWidth = pdFont.getStringWidth(this.value);
/*  931 */       float height = 0.0F;
/*  932 */       if ((pdFont instanceof PDSimpleFont))
/*      */       {
/*  934 */         height = ((PDSimpleFont)pdFont).getFontBoundingBox().getHeight();
/*      */       }
/*      */       else
/*      */       {
/*  940 */         height = pdFont.getAverageFontWidth();
/*      */       }
/*  942 */       height /= 1000.0F;
/*      */ 
/*  944 */       float availHeight = getAvailableHeight(boundingBox, lineWidth);
/*  945 */       fontSize = Math.min(availHeight / height, widthBasedFontSize);
/*      */     }
/*  947 */     return fontSize;
/*      */   }
/*      */ 
/*      */   private float calculateListboxFontSize(PDFont pdFont, PDRectangle contentArea, List tokens, List daTokens)
/*      */     throws IOException
/*      */   {
/*  961 */     float fontSize = 0.0F;
/*  962 */     if (daTokens != null)
/*      */     {
/*  966 */       int fontIndex = daTokens.indexOf(PDFOperator.getOperator("Tf"));
/*  967 */       if (fontIndex != -1)
/*      */       {
/*  969 */         fontSize = ((COSNumber)daTokens.get(fontIndex - 1)).floatValue();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  976 */     if (fontSize == 0.0F)
/*      */     {
/*  979 */       COSArray options = ((PDChoiceField)this.parent).getOptions();
/*      */ 
/*  981 */       float maxOptWidth = 0.0F;
/*      */ 
/*  983 */       for (int i = 0; i < options.size(); i++)
/*      */       {
/*  986 */         COSBase option = options.getObject(i);
/*  987 */         COSArray optionPair = (COSArray)option;
/*  988 */         COSString optionValue = (COSString)optionPair.getObject(1);
/*  989 */         maxOptWidth = Math.max(pdFont.getStringWidth(optionValue.getString()) / 1000.0F, maxOptWidth);
/*      */       }
/*      */ 
/*  992 */       float availableWidth = getAvailableWidth(contentArea, getLineWidth(tokens));
/*  993 */       fontSize = availableWidth / maxOptWidth;
/*      */     }
/*      */ 
/*  996 */     return fontSize;
/*      */   }
/*      */ 
/*      */   private String getTextPosition(PDRectangle boundingBox, PDFont pdFont, float fontSize, List tokens)
/*      */     throws IOException
/*      */   {
/* 1011 */     float lineWidth = getLineWidth(tokens);
/* 1012 */     float pos = 0.0F;
/* 1013 */     if (this.parent.isMultiline())
/*      */     {
/* 1015 */       int rows = (int)(getAvailableHeight(boundingBox, lineWidth) / (int)fontSize);
/* 1016 */       pos = rows * fontSize - fontSize;
/*      */     }
/* 1020 */     else if ((pdFont instanceof PDSimpleFont))
/*      */     {
/* 1029 */       PDFontDescriptor fd = ((PDSimpleFont)pdFont).getFontDescriptor();
/* 1030 */       float bBoxHeight = boundingBox.getHeight();
/* 1031 */       float fontHeight = fd.getFontBoundingBox().getHeight() + 2.0F * fd.getDescent();
/* 1032 */       fontHeight = fontHeight / 1000.0F * fontSize;
/* 1033 */       pos = (bBoxHeight - fontHeight) / 2.0F;
/*      */     }
/*      */     else
/*      */     {
/* 1037 */       throw new IOException("Error: Don't know how to calculate the position for non-simple fonts");
/*      */     }
/*      */ 
/* 1040 */     PDRectangle innerBox = getSmallestDrawnRectangle(boundingBox, tokens);
/* 1041 */     float xInset = 2.0F + 2.0F * (boundingBox.getWidth() - innerBox.getWidth());
/* 1042 */     return Math.round(xInset) + " " + pos + " Td";
/*      */   }
/*      */ 
/*      */   private float getAvailableWidth(PDRectangle boundingBox, float lineWidth)
/*      */   {
/* 1052 */     return boundingBox.getWidth() - 2.0F * lineWidth;
/*      */   }
/*      */ 
/*      */   private float getAvailableHeight(PDRectangle boundingBox, float lineWidth)
/*      */   {
/* 1062 */     return boundingBox.getHeight() - 2.0F * lineWidth;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.form.PDAppearance
 * JD-Core Version:    0.6.2
 */