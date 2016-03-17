/*     */ package com.itextpdf.text.html.simpleparser;
/*     */ 
/*     */ import com.itextpdf.text.Chunk;
/*     */ import com.itextpdf.text.DocListener;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Element;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.FontProvider;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.ListItem;
/*     */ import com.itextpdf.text.Paragraph;
/*     */ import com.itextpdf.text.Phrase;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.TextElementArray;
/*     */ import com.itextpdf.text.html.HtmlUtilities;
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.log.LoggerFactory;
/*     */ import com.itextpdf.text.pdf.PdfPTable;
/*     */ import com.itextpdf.text.pdf.draw.LineSeparator;
/*     */ import com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler;
/*     */ import com.itextpdf.text.xml.simpleparser.SimpleXMLParser;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ 
/*     */ @Deprecated
/*     */ public class HTMLWorker
/*     */   implements SimpleXMLDocHandler, DocListener
/*     */ {
/*  86 */   private static Logger LOGGER = LoggerFactory.getLogger(HTMLWorker.class);
/*     */   protected DocListener document;
/*     */   protected Map<String, HTMLTagProcessor> tags;
/* 103 */   private StyleSheet style = new StyleSheet();
/*     */ 
/* 163 */   protected Stack<Element> stack = new Stack();
/*     */   protected Paragraph currentParagraph;
/* 175 */   private final ChainedProperties chain = new ChainedProperties();
/*     */   public static final String IMG_PROVIDER = "img_provider";
/*     */   public static final String IMG_PROCESSOR = "img_interface";
/*     */   public static final String IMG_STORE = "img_static";
/*     */   public static final String IMG_BASEURL = "img_baseurl";
/*     */   public static final String FONT_PROVIDER = "font_factory";
/*     */   public static final String LINK_PROVIDER = "alink_interface";
/* 378 */   private Map<String, Object> providers = new HashMap();
/*     */ 
/* 403 */   private final ElementFactory factory = new ElementFactory();
/*     */ 
/* 660 */   private final Stack<boolean[]> tableState = new Stack();
/*     */ 
/* 663 */   private boolean pendingTR = false;
/*     */ 
/* 666 */   private boolean pendingTD = false;
/*     */ 
/* 669 */   private boolean pendingLI = false;
/*     */ 
/* 675 */   private boolean insidePRE = false;
/*     */ 
/* 681 */   protected boolean skipText = false;
/*     */   protected java.util.List<Element> objectList;
/*     */ 
/*     */   public HTMLWorker(DocListener document)
/*     */   {
/* 110 */     this(document, null, null);
/*     */   }
/*     */ 
/*     */   public HTMLWorker(DocListener document, Map<String, HTMLTagProcessor> tags, StyleSheet style)
/*     */   {
/* 121 */     this.document = document;
/* 122 */     setSupportedTags(tags);
/* 123 */     setStyleSheet(style);
/*     */   }
/*     */ 
/*     */   public void setSupportedTags(Map<String, HTMLTagProcessor> tags)
/*     */   {
/* 132 */     if (tags == null)
/* 133 */       tags = new HTMLTagProcessors();
/* 134 */     this.tags = tags;
/*     */   }
/*     */ 
/*     */   public void setStyleSheet(StyleSheet style)
/*     */   {
/* 142 */     if (style == null)
/* 143 */       style = new StyleSheet();
/* 144 */     this.style = style;
/*     */   }
/*     */ 
/*     */   public void parse(Reader reader)
/*     */     throws IOException
/*     */   {
/* 153 */     LOGGER.info("Please note, there is a more extended version of the HTMLWorker available in the iText XMLWorker");
/* 154 */     SimpleXMLParser.parse(this, null, reader, true);
/*     */   }
/*     */ 
/*     */   public void startDocument()
/*     */   {
/* 181 */     HashMap attrs = new HashMap();
/* 182 */     this.style.applyStyle("body", attrs);
/* 183 */     this.chain.addToChain("body", attrs);
/*     */   }
/*     */ 
/*     */   public void startElement(String tag, Map<String, String> attrs)
/*     */   {
/* 190 */     HTMLTagProcessor htmlTag = (HTMLTagProcessor)this.tags.get(tag);
/* 191 */     if (htmlTag == null) {
/* 192 */       return;
/*     */     }
/*     */ 
/* 195 */     this.style.applyStyle(tag, attrs);
/*     */ 
/* 197 */     StyleSheet.resolveStyleAttribute(attrs, this.chain);
/*     */     try
/*     */     {
/* 200 */       htmlTag.startElement(this, tag, attrs);
/*     */     } catch (DocumentException e) {
/* 202 */       throw new ExceptionConverter(e);
/*     */     } catch (IOException e) {
/* 204 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void text(String content)
/*     */   {
/* 212 */     if (this.skipText)
/* 213 */       return;
/* 214 */     if (this.currentParagraph == null) {
/* 215 */       this.currentParagraph = createParagraph();
/*     */     }
/* 217 */     if (!this.insidePRE)
/*     */     {
/* 219 */       if ((content.trim().length() == 0) && (content.indexOf(' ') < 0)) {
/* 220 */         return;
/*     */       }
/* 222 */       content = HtmlUtilities.eliminateWhiteSpace(content);
/*     */     }
/* 224 */     Chunk chunk = createChunk(content);
/* 225 */     this.currentParagraph.add(chunk);
/*     */   }
/*     */ 
/*     */   public void endElement(String tag)
/*     */   {
/* 232 */     HTMLTagProcessor htmlTag = (HTMLTagProcessor)this.tags.get(tag);
/* 233 */     if (htmlTag == null) {
/* 234 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 238 */       htmlTag.endElement(this, tag);
/*     */     } catch (DocumentException e) {
/* 240 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void endDocument()
/*     */   {
/*     */     try
/*     */     {
/* 250 */       for (int k = 0; k < this.stack.size(); k++) {
/* 251 */         this.document.add((Element)this.stack.elementAt(k));
/*     */       }
/* 253 */       if (this.currentParagraph != null)
/* 254 */         this.document.add(this.currentParagraph);
/* 255 */       this.currentParagraph = null;
/*     */     } catch (Exception e) {
/* 257 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void newLine()
/*     */   {
/* 268 */     if (this.currentParagraph == null) {
/* 269 */       this.currentParagraph = new Paragraph();
/*     */     }
/* 271 */     this.currentParagraph.add(createChunk("\n"));
/*     */   }
/*     */ 
/*     */   public void carriageReturn()
/*     */     throws DocumentException
/*     */   {
/* 282 */     if (this.currentParagraph == null)
/* 283 */       return;
/* 284 */     if (this.stack.empty()) {
/* 285 */       this.document.add(this.currentParagraph);
/*     */     } else {
/* 287 */       Element obj = (Element)this.stack.pop();
/* 288 */       if ((obj instanceof TextElementArray)) {
/* 289 */         TextElementArray current = (TextElementArray)obj;
/* 290 */         current.add(this.currentParagraph);
/*     */       }
/* 292 */       this.stack.push(obj);
/*     */     }
/* 294 */     this.currentParagraph = null;
/*     */   }
/*     */ 
/*     */   public void flushContent()
/*     */   {
/* 303 */     pushToStack(this.currentParagraph);
/* 304 */     this.currentParagraph = new Paragraph();
/*     */   }
/*     */ 
/*     */   public void pushToStack(Element element)
/*     */   {
/* 313 */     if (element != null)
/* 314 */       this.stack.push(element);
/*     */   }
/*     */ 
/*     */   public void updateChain(String tag, Map<String, String> attrs)
/*     */   {
/* 324 */     this.chain.addToChain(tag, attrs);
/*     */   }
/*     */ 
/*     */   public void updateChain(String tag)
/*     */   {
/* 333 */     this.chain.removeChain(tag);
/*     */   }
/*     */ 
/*     */   public void setProviders(Map<String, Object> providers)
/*     */   {
/* 387 */     if (providers == null)
/* 388 */       return;
/* 389 */     this.providers = providers;
/* 390 */     FontProvider ff = null;
/* 391 */     if (providers != null)
/* 392 */       ff = (FontProvider)providers.get("font_factory");
/* 393 */     if (ff != null)
/* 394 */       this.factory.setFontProvider(ff);
/*     */   }
/*     */ 
/*     */   public Chunk createChunk(String content)
/*     */   {
/* 412 */     return this.factory.createChunk(content, this.chain);
/*     */   }
/*     */ 
/*     */   public Paragraph createParagraph()
/*     */   {
/* 420 */     return this.factory.createParagraph(this.chain);
/*     */   }
/*     */ 
/*     */   public com.itextpdf.text.List createList(String tag)
/*     */   {
/* 429 */     return this.factory.createList(tag, this.chain);
/*     */   }
/*     */ 
/*     */   public ListItem createListItem()
/*     */   {
/* 437 */     return this.factory.createListItem(this.chain);
/*     */   }
/*     */ 
/*     */   public LineSeparator createLineSeparator(Map<String, String> attrs)
/*     */   {
/* 446 */     return this.factory.createLineSeparator(attrs, this.currentParagraph.getLeading() / 2.0F);
/*     */   }
/*     */ 
/*     */   public Image createImage(Map<String, String> attrs)
/*     */     throws DocumentException, IOException
/*     */   {
/* 458 */     String src = (String)attrs.get("src");
/* 459 */     if (src == null)
/* 460 */       return null;
/* 461 */     Image img = this.factory.createImage(src, attrs, this.chain, this.document, (ImageProvider)this.providers.get("img_provider"), (ImageStore)this.providers.get("img_static"), (String)this.providers.get("img_baseurl"));
/*     */ 
/* 466 */     return img;
/*     */   }
/*     */ 
/*     */   public CellWrapper createCell(String tag)
/*     */   {
/* 476 */     return new CellWrapper(tag, this.chain);
/*     */   }
/*     */ 
/*     */   public void processLink()
/*     */   {
/* 486 */     if (this.currentParagraph == null) {
/* 487 */       this.currentParagraph = new Paragraph();
/*     */     }
/*     */ 
/* 490 */     LinkProcessor i = (LinkProcessor)this.providers.get("alink_interface");
/*     */     String href;
/* 491 */     if ((i == null) || (!i.process(this.currentParagraph, this.chain)))
/*     */     {
/* 493 */       href = this.chain.getProperty("href");
/* 494 */       if (href != null) {
/* 495 */         for (Chunk ck : this.currentParagraph.getChunks()) {
/* 496 */           ck.setAnchor(href);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 501 */     if (this.stack.isEmpty())
/*     */     {
/* 503 */       Paragraph tmp = new Paragraph(new Phrase(this.currentParagraph));
/* 504 */       this.currentParagraph = tmp;
/*     */     } else {
/* 506 */       Paragraph tmp = (Paragraph)this.stack.pop();
/* 507 */       tmp.add(new Phrase(this.currentParagraph));
/* 508 */       this.currentParagraph = tmp;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void processList()
/*     */     throws DocumentException
/*     */   {
/* 520 */     if (this.stack.empty())
/* 521 */       return;
/* 522 */     Element obj = (Element)this.stack.pop();
/* 523 */     if (!(obj instanceof com.itextpdf.text.List)) {
/* 524 */       this.stack.push(obj);
/* 525 */       return;
/*     */     }
/* 527 */     if (this.stack.empty())
/* 528 */       this.document.add(obj);
/*     */     else
/* 530 */       ((TextElementArray)this.stack.peek()).add(obj);
/*     */   }
/*     */ 
/*     */   public void processListItem()
/*     */     throws DocumentException
/*     */   {
/* 540 */     if (this.stack.empty())
/* 541 */       return;
/* 542 */     Element obj = (Element)this.stack.pop();
/* 543 */     if (!(obj instanceof ListItem)) {
/* 544 */       this.stack.push(obj);
/* 545 */       return;
/*     */     }
/* 547 */     if (this.stack.empty()) {
/* 548 */       this.document.add(obj);
/* 549 */       return;
/*     */     }
/* 551 */     ListItem item = (ListItem)obj;
/* 552 */     Element list = (Element)this.stack.pop();
/* 553 */     if (!(list instanceof com.itextpdf.text.List)) {
/* 554 */       this.stack.push(list);
/* 555 */       return;
/*     */     }
/* 557 */     ((com.itextpdf.text.List)list).add(item);
/* 558 */     item.adjustListSymbolFont();
/* 559 */     this.stack.push(list);
/*     */   }
/*     */ 
/*     */   public void processImage(Image img, Map<String, String> attrs)
/*     */     throws DocumentException
/*     */   {
/* 570 */     ImageProcessor processor = (ImageProcessor)this.providers.get("img_interface");
/* 571 */     if ((processor == null) || (!processor.process(img, attrs, this.chain, this.document))) {
/* 572 */       String align = (String)attrs.get("align");
/* 573 */       if (align != null) {
/* 574 */         carriageReturn();
/*     */       }
/* 576 */       if (this.currentParagraph == null) {
/* 577 */         this.currentParagraph = createParagraph();
/*     */       }
/* 579 */       this.currentParagraph.add(new Chunk(img, 0.0F, 0.0F, true));
/* 580 */       this.currentParagraph.setAlignment(HtmlUtilities.alignmentValue(align));
/* 581 */       if (align != null)
/* 582 */         carriageReturn();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void processTable()
/*     */     throws DocumentException
/*     */   {
/* 593 */     TableWrapper table = (TableWrapper)this.stack.pop();
/* 594 */     PdfPTable tb = table.createTable();
/* 595 */     tb.setSplitRows(true);
/* 596 */     if (this.stack.empty())
/* 597 */       this.document.add(tb);
/*     */     else
/* 599 */       ((TextElementArray)this.stack.peek()).add(tb);
/*     */   }
/*     */ 
/*     */   public void processRow()
/*     */   {
/* 607 */     ArrayList row = new ArrayList();
/* 608 */     ArrayList cellWidths = new ArrayList();
/* 609 */     boolean percentage = false;
/*     */ 
/* 611 */     float totalWidth = 0.0F;
/* 612 */     int zeroWidth = 0;
/* 613 */     TableWrapper table = null;
/*     */     while (true) {
/* 615 */       Element obj = (Element)this.stack.pop();
/* 616 */       if ((obj instanceof CellWrapper)) {
/* 617 */         CellWrapper cell = (CellWrapper)obj;
/* 618 */         float width = cell.getWidth();
/* 619 */         cellWidths.add(new Float(width));
/* 620 */         percentage |= cell.isPercentage();
/* 621 */         if (width == 0.0F) {
/* 622 */           zeroWidth++;
/*     */         }
/*     */         else {
/* 625 */           totalWidth += width;
/*     */         }
/* 627 */         row.add(cell.getCell());
/*     */       }
/* 629 */       if ((obj instanceof TableWrapper)) {
/* 630 */         table = (TableWrapper)obj;
/* 631 */         break;
/*     */       }
/*     */     }
/* 634 */     table.addRow(row);
/* 635 */     if (cellWidths.size() > 0)
/*     */     {
/* 637 */       totalWidth = 100.0F - totalWidth;
/* 638 */       Collections.reverse(cellWidths);
/* 639 */       float[] widths = new float[cellWidths.size()];
/* 640 */       boolean hasZero = false;
/* 641 */       for (int i = 0; i < widths.length; i++) {
/* 642 */         widths[i] = ((Float)cellWidths.get(i)).floatValue();
/* 643 */         if ((widths[i] == 0.0F) && (percentage) && (zeroWidth > 0)) {
/* 644 */           widths[i] = (totalWidth / zeroWidth);
/*     */         }
/* 646 */         if (widths[i] == 0.0F) {
/* 647 */           hasZero = true;
/* 648 */           break;
/*     */         }
/*     */       }
/* 651 */       if (!hasZero)
/* 652 */         table.setColWidths(widths);
/*     */     }
/* 654 */     this.stack.push(table);
/*     */   }
/*     */ 
/*     */   public void pushTableState()
/*     */   {
/* 689 */     this.tableState.push(new boolean[] { this.pendingTR, this.pendingTD });
/*     */   }
/*     */ 
/*     */   public void popTableState()
/*     */   {
/* 698 */     boolean[] state = (boolean[])this.tableState.pop();
/* 699 */     this.pendingTR = state[0];
/* 700 */     this.pendingTD = state[1];
/*     */   }
/*     */ 
/*     */   public boolean isPendingTR()
/*     */   {
/* 708 */     return this.pendingTR;
/*     */   }
/*     */ 
/*     */   public void setPendingTR(boolean pendingTR)
/*     */   {
/* 716 */     this.pendingTR = pendingTR;
/*     */   }
/*     */ 
/*     */   public boolean isPendingTD()
/*     */   {
/* 724 */     return this.pendingTD;
/*     */   }
/*     */ 
/*     */   public void setPendingTD(boolean pendingTD)
/*     */   {
/* 732 */     this.pendingTD = pendingTD;
/*     */   }
/*     */ 
/*     */   public boolean isPendingLI()
/*     */   {
/* 740 */     return this.pendingLI;
/*     */   }
/*     */ 
/*     */   public void setPendingLI(boolean pendingLI)
/*     */   {
/* 748 */     this.pendingLI = pendingLI;
/*     */   }
/*     */ 
/*     */   public boolean isInsidePRE()
/*     */   {
/* 756 */     return this.insidePRE;
/*     */   }
/*     */ 
/*     */   public void setInsidePRE(boolean insidePRE)
/*     */   {
/* 764 */     this.insidePRE = insidePRE;
/*     */   }
/*     */ 
/*     */   public boolean isSkipText()
/*     */   {
/* 772 */     return this.skipText;
/*     */   }
/*     */ 
/*     */   public void setSkipText(boolean skipText)
/*     */   {
/* 780 */     this.skipText = skipText;
/*     */   }
/*     */ 
/*     */   public static java.util.List<Element> parseToList(Reader reader, StyleSheet style)
/*     */     throws IOException
/*     */   {
/* 797 */     return parseToList(reader, style, null);
/*     */   }
/*     */ 
/*     */   public static java.util.List<Element> parseToList(Reader reader, StyleSheet style, HashMap<String, Object> providers)
/*     */     throws IOException
/*     */   {
/* 810 */     return parseToList(reader, style, null, providers);
/*     */   }
/*     */ 
/*     */   public static java.util.List<Element> parseToList(Reader reader, StyleSheet style, Map<String, HTMLTagProcessor> tags, HashMap<String, Object> providers)
/*     */     throws IOException
/*     */   {
/* 825 */     HTMLWorker worker = new HTMLWorker(null, tags, style);
/* 826 */     worker.document = worker;
/* 827 */     worker.setProviders(providers);
/* 828 */     worker.objectList = new ArrayList();
/* 829 */     worker.parse(reader);
/* 830 */     return worker.objectList;
/*     */   }
/*     */ 
/*     */   public boolean add(Element element)
/*     */     throws DocumentException
/*     */   {
/* 839 */     this.objectList.add(element);
/* 840 */     return true;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean newPage()
/*     */   {
/* 853 */     return true;
/*     */   }
/*     */ 
/*     */   public void open()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void resetPageCount()
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean setMarginMirroring(boolean marginMirroring)
/*     */   {
/* 872 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean setMarginMirroringTopBottom(boolean marginMirroring)
/*     */   {
/* 880 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean setMargins(float marginLeft, float marginRight, float marginTop, float marginBottom)
/*     */   {
/* 888 */     return true;
/*     */   }
/*     */ 
/*     */   public void setPageCount(int pageN)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean setPageSize(Rectangle pageSize)
/*     */   {
/* 901 */     return true;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void setInterfaceProps(HashMap<String, Object> providers)
/*     */   {
/* 912 */     setProviders(providers);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Map<String, Object> getInterfaceProps()
/*     */   {
/* 920 */     return this.providers;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.html.simpleparser.HTMLWorker
 * JD-Core Version:    0.6.2
 */