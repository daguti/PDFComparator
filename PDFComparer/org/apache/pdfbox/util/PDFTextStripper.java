/*      */ package org.apache.pdfbox.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.StringWriter;
/*      */ import java.io.Writer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ import java.util.TreeMap;
/*      */ import java.util.TreeSet;
/*      */ import java.util.Vector;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.apache.pdfbox.cos.COSDocument;
/*      */ import org.apache.pdfbox.cos.COSStream;
/*      */ import org.apache.pdfbox.pdmodel.PDDocument;
/*      */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*      */ import org.apache.pdfbox.pdmodel.PDPage;
/*      */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*      */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*      */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*      */ import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
/*      */ import org.apache.pdfbox.pdmodel.interactive.pagenavigation.PDThreadBead;
/*      */ 
/*      */ public class PDFTextStripper extends PDFStreamEngine
/*      */ {
/*   64 */   private static final String thisClassName = PDFTextStripper.class.getSimpleName().toLowerCase();
/*      */ 
/*   66 */   private static float DEFAULT_INDENT_THRESHOLD = 2.0F;
/*   67 */   private static float DEFAULT_DROP_THRESHOLD = 2.5F;
/*      */ 
/*  131 */   private static final boolean useCustomQuicksort = !is16orLess;
/*      */ 
/*  137 */   protected final String systemLineSeparator = System.getProperty("line.separator");
/*      */ 
/*  139 */   private String lineSeparator = this.systemLineSeparator;
/*  140 */   private String pageSeparator = this.systemLineSeparator;
/*  141 */   private String wordSeparator = " ";
/*  142 */   private String paragraphStart = "";
/*  143 */   private String paragraphEnd = "";
/*  144 */   private String pageStart = "";
/*  145 */   private String pageEnd = this.pageSeparator;
/*  146 */   private String articleStart = "";
/*  147 */   private String articleEnd = "";
/*      */ 
/*  149 */   private int currentPageNo = 0;
/*  150 */   private int startPage = 1;
/*  151 */   private int endPage = 2147483647;
/*  152 */   private PDOutlineItem startBookmark = null;
/*  153 */   private int startBookmarkPageNumber = -1;
/*  154 */   private PDOutlineItem endBookmark = null;
/*  155 */   private int endBookmarkPageNumber = -1;
/*  156 */   private boolean suppressDuplicateOverlappingText = true;
/*  157 */   private boolean shouldSeparateByBeads = true;
/*  158 */   private boolean sortByPosition = false;
/*  159 */   private boolean addMoreFormatting = false;
/*      */ 
/*  161 */   private float indentThreshold = DEFAULT_INDENT_THRESHOLD;
/*  162 */   private float dropThreshold = DEFAULT_DROP_THRESHOLD;
/*      */ 
/*  166 */   private float spacingTolerance = 0.5F;
/*  167 */   private float averageCharTolerance = 0.3F;
/*      */ 
/*  169 */   private List<PDThreadBead> pageArticles = null;
/*      */ 
/*  185 */   protected Vector<List<TextPosition>> charactersByArticle = new Vector();
/*      */ 
/*  187 */   private Map<String, TreeMap<Float, TreeSet<Float>>> characterListMapping = new HashMap();
/*      */   protected String outputEncoding;
/*      */   protected PDDocument document;
/*      */   protected Writer output;
/*  208 */   private TextNormalize normalize = null;
/*      */   private boolean inParagraph;
/*      */   private static final float ENDOFLASTTEXTX_RESET_VALUE = -1.0F;
/*      */   private static final float MAXYFORLINE_RESET_VALUE = -3.402824E+038F;
/*      */   private static final float EXPECTEDSTARTOFNEXTWORDX_RESET_VALUE = -3.402824E+038F;
/*      */   private static final float MAXHEIGHTFORLINE_RESET_VALUE = -1.0F;
/*      */   private static final float MINYTOPFORLINE_RESET_VALUE = 3.4028235E+38F;
/*      */   private static final float LASTWORDSPACING_RESET_VALUE = -1.0F;
/* 1816 */   private static final String[] LIST_ITEM_EXPRESSIONS = { "\\.", "\\d+\\.", "\\[\\d+\\]", "\\d+\\)", "[A-Z]\\.", "[a-z]\\.", "[A-Z]\\)", "[a-z]\\)", "[IVXL]+\\.", "[ivxl]+\\." };
/*      */ 
/* 1830 */   private List<Pattern> listOfPatterns = null;
/*      */ 
/*      */   public PDFTextStripper()
/*      */     throws IOException
/*      */   {
/*  226 */     super(ResourceLoader.loadProperties("org/apache/pdfbox/resources/PDFTextStripper.properties", true));
/*      */ 
/*  228 */     this.outputEncoding = null;
/*  229 */     this.normalize = new TextNormalize(this.outputEncoding);
/*      */   }
/*      */ 
/*      */   public PDFTextStripper(Properties props)
/*      */     throws IOException
/*      */   {
/*  244 */     super(props);
/*  245 */     this.outputEncoding = null;
/*  246 */     this.normalize = new TextNormalize(this.outputEncoding);
/*      */   }
/*      */ 
/*      */   public PDFTextStripper(String encoding)
/*      */     throws IOException
/*      */   {
/*  258 */     super(ResourceLoader.loadProperties("org/apache/pdfbox/resources/PDFTextStripper.properties", true));
/*      */ 
/*  260 */     this.outputEncoding = encoding;
/*  261 */     this.normalize = new TextNormalize(this.outputEncoding);
/*      */   }
/*      */ 
/*      */   public String getText(PDDocument doc)
/*      */     throws IOException
/*      */   {
/*  274 */     StringWriter outputStream = new StringWriter();
/*  275 */     writeText(doc, outputStream);
/*  276 */     return outputStream.toString();
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public String getText(COSDocument doc)
/*      */     throws IOException
/*      */   {
/*  288 */     return getText(new PDDocument(doc));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void writeText(COSDocument doc, Writer outputStream)
/*      */     throws IOException
/*      */   {
/*  300 */     writeText(new PDDocument(doc), outputStream);
/*      */   }
/*      */ 
/*      */   public void resetEngine()
/*      */   {
/*  308 */     super.resetEngine();
/*  309 */     this.currentPageNo = 0;
/*  310 */     this.document = null;
/*  311 */     if (this.charactersByArticle != null)
/*      */     {
/*  313 */       this.charactersByArticle.clear();
/*      */     }
/*  315 */     if (this.characterListMapping != null)
/*      */     {
/*  317 */       this.characterListMapping.clear();
/*      */     }
/*  319 */     this.startBookmark = null;
/*  320 */     this.endBookmark = null;
/*      */   }
/*      */ 
/*      */   public void writeText(PDDocument doc, Writer outputStream)
/*      */     throws IOException
/*      */   {
/*  333 */     resetEngine();
/*  334 */     this.document = doc;
/*  335 */     this.output = outputStream;
/*  336 */     if (getAddMoreFormatting())
/*      */     {
/*  338 */       this.paragraphEnd = this.lineSeparator;
/*  339 */       this.pageStart = this.lineSeparator;
/*  340 */       this.articleStart = this.lineSeparator;
/*  341 */       this.articleEnd = this.lineSeparator;
/*      */     }
/*  343 */     startDocument(this.document);
/*  344 */     processPages(this.document.getDocumentCatalog().getAllPages());
/*  345 */     endDocument(this.document);
/*      */   }
/*      */ 
/*      */   protected void processPages(List<COSObjectable> pages)
/*      */     throws IOException
/*      */   {
/*  357 */     if (this.startBookmark != null)
/*      */     {
/*  359 */       this.startBookmarkPageNumber = getPageNumber(this.startBookmark, pages);
/*      */     }
/*  361 */     if (this.endBookmark != null)
/*      */     {
/*  363 */       this.endBookmarkPageNumber = getPageNumber(this.endBookmark, pages);
/*      */     }
/*      */ 
/*  366 */     if ((this.startBookmarkPageNumber == -1) && (this.startBookmark != null) && (this.endBookmarkPageNumber == -1) && (this.endBookmark != null) && (this.startBookmark.getCOSObject() == this.endBookmark.getCOSObject()))
/*      */     {
/*  373 */       this.startBookmarkPageNumber = 0;
/*  374 */       this.endBookmarkPageNumber = 0;
/*      */     }
/*  376 */     Iterator pageIter = pages.iterator();
/*  377 */     while (pageIter.hasNext())
/*      */     {
/*  379 */       PDPage nextPage = (PDPage)pageIter.next();
/*  380 */       PDStream contentStream = nextPage.getContents();
/*  381 */       this.currentPageNo += 1;
/*  382 */       if (contentStream != null)
/*      */       {
/*  384 */         COSStream contents = contentStream.getStream();
/*  385 */         processPage(nextPage, contents);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private int getPageNumber(PDOutlineItem bookmark, List<COSObjectable> allPages) throws IOException
/*      */   {
/*  392 */     int pageNumber = -1;
/*  393 */     PDPage page = bookmark.findDestinationPage(this.document);
/*  394 */     if (page != null)
/*      */     {
/*  396 */       pageNumber = allPages.indexOf(page) + 1;
/*      */     }
/*  398 */     return pageNumber;
/*      */   }
/*      */ 
/*      */   protected void startDocument(PDDocument pdf)
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void endDocument(PDDocument pdf)
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void processPage(PDPage page, COSStream content)
/*      */     throws IOException
/*      */   {
/*  435 */     if ((this.currentPageNo >= this.startPage) && (this.currentPageNo <= this.endPage) && ((this.startBookmarkPageNumber == -1) || (this.currentPageNo >= this.startBookmarkPageNumber)) && ((this.endBookmarkPageNumber == -1) || (this.currentPageNo <= this.endBookmarkPageNumber)))
/*      */     {
/*  439 */       startPage(page);
/*  440 */       this.pageArticles = page.getThreadBeads();
/*  441 */       int numberOfArticleSections = 1 + this.pageArticles.size() * 2;
/*  442 */       if (!this.shouldSeparateByBeads)
/*      */       {
/*  444 */         numberOfArticleSections = 1;
/*      */       }
/*  446 */       int originalSize = this.charactersByArticle.size();
/*  447 */       this.charactersByArticle.setSize(numberOfArticleSections);
/*  448 */       for (int i = 0; i < numberOfArticleSections; i++)
/*      */       {
/*  450 */         if (numberOfArticleSections < originalSize)
/*      */         {
/*  452 */           ((List)this.charactersByArticle.get(i)).clear();
/*      */         }
/*      */         else
/*      */         {
/*  456 */           this.charactersByArticle.set(i, new ArrayList());
/*      */         }
/*      */       }
/*  459 */       this.characterListMapping.clear();
/*  460 */       processStream(page, page.findResources(), content);
/*  461 */       writePage();
/*  462 */       endPage(page);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void startArticle()
/*      */     throws IOException
/*      */   {
/*  477 */     startArticle(true);
/*      */   }
/*      */ 
/*      */   protected void startArticle(boolean isltr)
/*      */     throws IOException
/*      */   {
/*  491 */     this.output.write(getArticleStart());
/*      */   }
/*      */ 
/*      */   protected void endArticle()
/*      */     throws IOException
/*      */   {
/*  502 */     this.output.write(getArticleEnd());
/*      */   }
/*      */ 
/*      */   protected void startPage(PDPage page)
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void endPage(PDPage page)
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void writePage()
/*      */     throws IOException
/*      */   {
/*  548 */     float maxYForLine = -3.402824E+038F;
/*  549 */     float minYTopForLine = 3.4028235E+38F;
/*  550 */     float endOfLastTextX = -1.0F;
/*  551 */     float lastWordSpacing = -1.0F;
/*  552 */     float maxHeightForLine = -1.0F;
/*  553 */     PositionWrapper lastPosition = null;
/*  554 */     PositionWrapper lastLineStartPosition = null;
/*      */ 
/*  556 */     boolean startOfPage = true;
/*  557 */     boolean startOfArticle = true;
/*  558 */     if (this.charactersByArticle.size() > 0)
/*      */     {
/*  560 */       writePageStart();
/*      */     }
/*      */ 
/*  563 */     for (int i = 0; i < this.charactersByArticle.size(); i++)
/*      */     {
/*  565 */       List textList = (List)this.charactersByArticle.get(i);
/*  566 */       if (getSortByPosition())
/*      */       {
/*  568 */         TextPositionComparator comparator = new TextPositionComparator();
/*      */ 
/*  572 */         if (useCustomQuicksort)
/*      */         {
/*  574 */           QuickSort.sort(textList, comparator);
/*      */         }
/*      */         else
/*      */         {
/*  578 */           Collections.sort(textList, comparator);
/*      */         }
/*      */       }
/*  581 */       Iterator textIter = textList.iterator();
/*      */ 
/*  597 */       int ltrCnt = 0;
/*  598 */       int rtlCnt = 0;
/*      */ 
/*  600 */       while (textIter.hasNext())
/*      */       {
/*  602 */         TextPosition position = (TextPosition)textIter.next();
/*  603 */         String stringValue = position.getCharacter();
/*  604 */         for (int a = 0; a < stringValue.length(); a++)
/*      */         {
/*  606 */           byte dir = Character.getDirectionality(stringValue.charAt(a));
/*  607 */           if ((dir == 0) || (dir == 14) || (dir == 15))
/*      */           {
/*  611 */             ltrCnt++;
/*      */           }
/*  613 */           else if ((dir == 1) || (dir == 2) || (dir == 16) || (dir == 17))
/*      */           {
/*  618 */             rtlCnt++;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  623 */       boolean isRtlDominant = rtlCnt > ltrCnt;
/*      */ 
/*  625 */       startArticle(!isRtlDominant);
/*  626 */       startOfArticle = true;
/*      */ 
/*  628 */       boolean hasRtl = rtlCnt > 0;
/*      */ 
/*  634 */       List line = new ArrayList();
/*      */ 
/*  636 */       textIter = textList.iterator();
/*      */ 
/*  645 */       float previousAveCharWidth = -1.0F;
/*  646 */       while (textIter.hasNext())
/*      */       {
/*  648 */         TextPosition position = (TextPosition)textIter.next();
/*  649 */         PositionWrapper current = new PositionWrapper(position);
/*  650 */         String characterValue = position.getCharacter();
/*      */ 
/*  654 */         if ((lastPosition != null) && ((position.getFont() != lastPosition.getTextPosition().getFont()) || (position.getFontSize() != lastPosition.getTextPosition().getFontSize())))
/*      */         {
/*  657 */           previousAveCharWidth = -1.0F;
/*      */         }
/*      */         float positionHeight;
/*      */         float positionX;
/*      */         float positionY;
/*      */         float positionWidth;
/*      */         float positionHeight;
/*  667 */         if (getSortByPosition())
/*      */         {
/*  669 */           float positionX = position.getXDirAdj();
/*  670 */           float positionY = position.getYDirAdj();
/*  671 */           float positionWidth = position.getWidthDirAdj();
/*  672 */           positionHeight = position.getHeightDir();
/*      */         }
/*      */         else
/*      */         {
/*  676 */           positionX = position.getX();
/*  677 */           positionY = position.getY();
/*  678 */           positionWidth = position.getWidth();
/*  679 */           positionHeight = position.getHeight();
/*      */         }
/*      */ 
/*  683 */         int wordCharCount = position.getIndividualWidths().length;
/*      */ 
/*  687 */         float wordSpacing = position.getWidthOfSpace();
/*  688 */         float deltaSpace = 0.0F;
/*  689 */         if ((wordSpacing == 0.0F) || (wordSpacing == (0.0F / 0.0F)))
/*      */         {
/*  691 */           deltaSpace = 3.4028235E+38F;
/*      */         }
/*  695 */         else if (lastWordSpacing < 0.0F)
/*      */         {
/*  697 */           deltaSpace = wordSpacing * getSpacingTolerance();
/*      */         }
/*      */         else
/*      */         {
/*  701 */           deltaSpace = (wordSpacing + lastWordSpacing) / 2.0F * getSpacingTolerance();
/*      */         }
/*      */ 
/*  710 */         float averageCharWidth = -1.0F;
/*  711 */         if (previousAveCharWidth < 0.0F)
/*      */         {
/*  713 */           averageCharWidth = positionWidth / wordCharCount;
/*      */         }
/*      */         else
/*      */         {
/*  717 */           averageCharWidth = (previousAveCharWidth + positionWidth / wordCharCount) / 2.0F;
/*      */         }
/*  719 */         float deltaCharWidth = averageCharWidth * getAverageCharTolerance();
/*      */ 
/*  723 */         float expectedStartOfNextWordX = -3.402824E+038F;
/*  724 */         if (endOfLastTextX != -1.0F)
/*      */         {
/*  726 */           if (deltaCharWidth > deltaSpace)
/*      */           {
/*  728 */             expectedStartOfNextWordX = endOfLastTextX + deltaSpace;
/*      */           }
/*      */           else
/*      */           {
/*  732 */             expectedStartOfNextWordX = endOfLastTextX + deltaCharWidth;
/*      */           }
/*      */         }
/*      */ 
/*  736 */         if (lastPosition != null)
/*      */         {
/*  738 */           if (startOfArticle)
/*      */           {
/*  740 */             lastPosition.setArticleStart();
/*  741 */             startOfArticle = false;
/*      */           }
/*      */ 
/*  752 */           if (!overlap(positionY, positionHeight, maxYForLine, maxHeightForLine))
/*      */           {
/*  754 */             writeLine(normalize(line, isRtlDominant, hasRtl), isRtlDominant);
/*  755 */             line.clear();
/*  756 */             lastLineStartPosition = handleLineSeparation(current, lastPosition, lastLineStartPosition, maxHeightForLine);
/*      */ 
/*  758 */             endOfLastTextX = -1.0F;
/*  759 */             expectedStartOfNextWordX = -3.402824E+038F;
/*  760 */             maxYForLine = -3.402824E+038F;
/*  761 */             maxHeightForLine = -1.0F;
/*  762 */             minYTopForLine = 3.4028235E+38F;
/*      */           }
/*      */ 
/*  765 */           if ((expectedStartOfNextWordX != -3.402824E+038F) && (expectedStartOfNextWordX < positionX) && (lastPosition.getTextPosition().getCharacter() != null) && (!lastPosition.getTextPosition().getCharacter().endsWith(" ")))
/*      */           {
/*  771 */             line.add(WordSeparator.getSeparator());
/*      */           }
/*      */         }
/*  774 */         if (positionY >= maxYForLine)
/*      */         {
/*  776 */           maxYForLine = positionY;
/*      */         }
/*      */ 
/*  780 */         endOfLastTextX = positionX + positionWidth;
/*      */ 
/*  783 */         if (characterValue != null)
/*      */         {
/*  785 */           if ((startOfPage) && (lastPosition == null))
/*      */           {
/*  787 */             writeParagraphStart();
/*      */           }
/*  789 */           line.add(position);
/*      */         }
/*  791 */         maxHeightForLine = Math.max(maxHeightForLine, positionHeight);
/*  792 */         minYTopForLine = Math.min(minYTopForLine, positionY - positionHeight);
/*  793 */         lastPosition = current;
/*  794 */         if (startOfPage)
/*      */         {
/*  796 */           lastPosition.setParagraphStart();
/*  797 */           lastPosition.setLineStart();
/*  798 */           lastLineStartPosition = lastPosition;
/*  799 */           startOfPage = false;
/*      */         }
/*  801 */         lastWordSpacing = wordSpacing;
/*  802 */         previousAveCharWidth = averageCharWidth;
/*      */       }
/*      */ 
/*  805 */       if (line.size() > 0)
/*      */       {
/*  807 */         writeLine(normalize(line, isRtlDominant, hasRtl), isRtlDominant);
/*  808 */         writeParagraphEnd();
/*      */       }
/*  810 */       endArticle();
/*      */     }
/*  812 */     writePageEnd();
/*      */   }
/*      */ 
/*      */   private boolean overlap(float y1, float height1, float y2, float height2)
/*      */   {
/*  817 */     return (within(y1, y2, 0.1F)) || ((y2 <= y1) && (y2 >= y1 - height1)) || ((y1 <= y2) && (y1 >= y2 - height2));
/*      */   }
/*      */ 
/*      */   protected void writePageSeperator()
/*      */     throws IOException
/*      */   {
/*  830 */     this.output.write(getPageSeparator());
/*  831 */     this.output.flush();
/*      */   }
/*      */ 
/*      */   protected void writeLineSeparator()
/*      */     throws IOException
/*      */   {
/*  841 */     this.output.write(getLineSeparator());
/*      */   }
/*      */ 
/*      */   protected void writeWordSeparator()
/*      */     throws IOException
/*      */   {
/*  852 */     this.output.write(getWordSeparator());
/*      */   }
/*      */ 
/*      */   protected void writeCharacters(TextPosition text)
/*      */     throws IOException
/*      */   {
/*  863 */     this.output.write(text.getCharacter());
/*      */   }
/*      */ 
/*      */   protected void writeString(String text, List<TextPosition> textPositions)
/*      */     throws IOException
/*      */   {
/*  876 */     writeString(text);
/*      */   }
/*      */ 
/*      */   protected void writeString(String text)
/*      */     throws IOException
/*      */   {
/*  887 */     this.output.write(text);
/*      */   }
/*      */ 
/*      */   private boolean within(float first, float second, float variance)
/*      */   {
/*  899 */     return (second < first + variance) && (second > first - variance);
/*      */   }
/*      */ 
/*      */   protected void processTextPosition(TextPosition text)
/*      */   {
/*  911 */     boolean showCharacter = true;
/*  912 */     if (this.suppressDuplicateOverlappingText)
/*      */     {
/*  914 */       showCharacter = false;
/*  915 */       String textCharacter = text.getCharacter();
/*  916 */       float textX = text.getX();
/*  917 */       float textY = text.getY();
/*  918 */       TreeMap sameTextCharacters = (TreeMap)this.characterListMapping.get(textCharacter);
/*  919 */       if (sameTextCharacters == null)
/*      */       {
/*  921 */         sameTextCharacters = new TreeMap();
/*  922 */         this.characterListMapping.put(textCharacter, sameTextCharacters);
/*      */       }
/*      */ 
/*  935 */       boolean suppressCharacter = false;
/*  936 */       float tolerance = text.getWidth() / textCharacter.length() / 3.0F;
/*      */ 
/*  938 */       SortedMap xMatches = sameTextCharacters.subMap(Float.valueOf(textX - tolerance), Float.valueOf(textX + tolerance));
/*      */ 
/*  940 */       for (TreeSet xMatch : xMatches.values())
/*      */       {
/*  942 */         SortedSet yMatches = xMatch.subSet(Float.valueOf(textY - tolerance), Float.valueOf(textY + tolerance));
/*      */ 
/*  944 */         if (!yMatches.isEmpty())
/*      */         {
/*  946 */           suppressCharacter = true;
/*  947 */           break;
/*      */         }
/*      */       }
/*  950 */       if (!suppressCharacter)
/*      */       {
/*  952 */         TreeSet ySet = (TreeSet)sameTextCharacters.get(Float.valueOf(textX));
/*  953 */         if (ySet == null)
/*      */         {
/*  955 */           ySet = new TreeSet();
/*  956 */           sameTextCharacters.put(Float.valueOf(textX), ySet);
/*      */         }
/*  958 */         ySet.add(Float.valueOf(textY));
/*  959 */         showCharacter = true;
/*      */       }
/*      */     }
/*  962 */     if (showCharacter)
/*      */     {
/*  966 */       int foundArticleDivisionIndex = -1;
/*  967 */       int notFoundButFirstLeftAndAboveArticleDivisionIndex = -1;
/*  968 */       int notFoundButFirstLeftArticleDivisionIndex = -1;
/*  969 */       int notFoundButFirstAboveArticleDivisionIndex = -1;
/*  970 */       float x = text.getX();
/*  971 */       float y = text.getY();
/*  972 */       if (this.shouldSeparateByBeads)
/*      */       {
/*  974 */         for (int i = 0; (i < this.pageArticles.size()) && (foundArticleDivisionIndex == -1); i++)
/*      */         {
/*  976 */           PDThreadBead bead = (PDThreadBead)this.pageArticles.get(i);
/*  977 */           if (bead != null)
/*      */           {
/*  979 */             PDRectangle rect = bead.getRectangle();
/*  980 */             if (rect.contains(x, y))
/*      */             {
/*  982 */               foundArticleDivisionIndex = i * 2 + 1;
/*      */             }
/*  984 */             else if (((x < rect.getLowerLeftX()) || (y < rect.getUpperRightY())) && (notFoundButFirstLeftAndAboveArticleDivisionIndex == -1))
/*      */             {
/*  988 */               notFoundButFirstLeftAndAboveArticleDivisionIndex = i * 2;
/*      */             }
/*  990 */             else if ((x < rect.getLowerLeftX()) && (notFoundButFirstLeftArticleDivisionIndex == -1))
/*      */             {
/*  993 */               notFoundButFirstLeftArticleDivisionIndex = i * 2;
/*      */             }
/*  995 */             else if ((y < rect.getUpperRightY()) && (notFoundButFirstAboveArticleDivisionIndex == -1))
/*      */             {
/*  998 */               notFoundButFirstAboveArticleDivisionIndex = i * 2;
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 1003 */             foundArticleDivisionIndex = 0;
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1009 */         foundArticleDivisionIndex = 0;
/*      */       }
/* 1011 */       int articleDivisionIndex = -1;
/* 1012 */       if (foundArticleDivisionIndex != -1)
/*      */       {
/* 1014 */         articleDivisionIndex = foundArticleDivisionIndex;
/*      */       }
/* 1016 */       else if (notFoundButFirstLeftAndAboveArticleDivisionIndex != -1)
/*      */       {
/* 1018 */         articleDivisionIndex = notFoundButFirstLeftAndAboveArticleDivisionIndex;
/*      */       }
/* 1020 */       else if (notFoundButFirstLeftArticleDivisionIndex != -1)
/*      */       {
/* 1022 */         articleDivisionIndex = notFoundButFirstLeftArticleDivisionIndex;
/*      */       }
/* 1024 */       else if (notFoundButFirstAboveArticleDivisionIndex != -1)
/*      */       {
/* 1026 */         articleDivisionIndex = notFoundButFirstAboveArticleDivisionIndex;
/*      */       }
/*      */       else
/*      */       {
/* 1030 */         articleDivisionIndex = this.charactersByArticle.size() - 1;
/*      */       }
/*      */ 
/* 1033 */       List textList = (List)this.charactersByArticle.get(articleDivisionIndex);
/*      */ 
/* 1041 */       if (textList.isEmpty())
/*      */       {
/* 1043 */         textList.add(text);
/*      */       }
/*      */       else
/*      */       {
/* 1051 */         TextPosition previousTextPosition = (TextPosition)textList.get(textList.size() - 1);
/* 1052 */         if ((text.isDiacritic()) && (previousTextPosition.contains(text)))
/*      */         {
/* 1054 */           previousTextPosition.mergeDiacritic(text, this.normalize);
/*      */         }
/* 1058 */         else if ((previousTextPosition.isDiacritic()) && (text.contains(previousTextPosition)))
/*      */         {
/* 1060 */           text.mergeDiacritic(previousTextPosition, this.normalize);
/* 1061 */           textList.remove(textList.size() - 1);
/* 1062 */           textList.add(text);
/*      */         }
/*      */         else
/*      */         {
/* 1066 */           textList.add(text);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getStartPage()
/*      */   {
/* 1082 */     return this.startPage;
/*      */   }
/*      */ 
/*      */   public void setStartPage(int startPageValue)
/*      */   {
/* 1092 */     this.startPage = startPageValue;
/*      */   }
/*      */ 
/*      */   public int getEndPage()
/*      */   {
/* 1105 */     return this.endPage;
/*      */   }
/*      */ 
/*      */   public void setEndPage(int endPageValue)
/*      */   {
/* 1115 */     this.endPage = endPageValue;
/*      */   }
/*      */ 
/*      */   public void setLineSeparator(String separator)
/*      */   {
/* 1127 */     this.lineSeparator = separator;
/*      */   }
/*      */ 
/*      */   public String getLineSeparator()
/*      */   {
/* 1137 */     return this.lineSeparator;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public void setPageSeparator(String separator)
/*      */   {
/* 1151 */     this.pageSeparator = separator;
/*      */   }
/*      */ 
/*      */   public String getWordSeparator()
/*      */   {
/* 1161 */     return this.wordSeparator;
/*      */   }
/*      */ 
/*      */   public void setWordSeparator(String separator)
/*      */   {
/* 1175 */     this.wordSeparator = separator;
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public String getPageSeparator()
/*      */   {
/* 1187 */     return this.pageSeparator;
/*      */   }
/*      */ 
/*      */   public boolean getSuppressDuplicateOverlappingText()
/*      */   {
/* 1194 */     return this.suppressDuplicateOverlappingText;
/*      */   }
/*      */ 
/*      */   protected int getCurrentPageNo()
/*      */   {
/* 1204 */     return this.currentPageNo;
/*      */   }
/*      */ 
/*      */   protected Writer getOutput()
/*      */   {
/* 1214 */     return this.output;
/*      */   }
/*      */ 
/*      */   protected Vector<List<TextPosition>> getCharactersByArticle()
/*      */   {
/* 1226 */     return this.charactersByArticle;
/*      */   }
/*      */ 
/*      */   public void setSuppressDuplicateOverlappingText(boolean suppressDuplicateOverlappingTextValue)
/*      */   {
/* 1240 */     this.suppressDuplicateOverlappingText = suppressDuplicateOverlappingTextValue;
/*      */   }
/*      */ 
/*      */   public boolean getSeparateByBeads()
/*      */   {
/* 1250 */     return this.shouldSeparateByBeads;
/*      */   }
/*      */ 
/*      */   public void setShouldSeparateByBeads(boolean aShouldSeparateByBeads)
/*      */   {
/* 1260 */     this.shouldSeparateByBeads = aShouldSeparateByBeads;
/*      */   }
/*      */ 
/*      */   public PDOutlineItem getEndBookmark()
/*      */   {
/* 1270 */     return this.endBookmark;
/*      */   }
/*      */ 
/*      */   public void setEndBookmark(PDOutlineItem aEndBookmark)
/*      */   {
/* 1280 */     this.endBookmark = aEndBookmark;
/*      */   }
/*      */ 
/*      */   public PDOutlineItem getStartBookmark()
/*      */   {
/* 1290 */     return this.startBookmark;
/*      */   }
/*      */ 
/*      */   public void setStartBookmark(PDOutlineItem aStartBookmark)
/*      */   {
/* 1300 */     this.startBookmark = aStartBookmark;
/*      */   }
/*      */ 
/*      */   public boolean getAddMoreFormatting()
/*      */   {
/* 1309 */     return this.addMoreFormatting;
/*      */   }
/*      */ 
/*      */   public void setAddMoreFormatting(boolean newAddMoreFormatting)
/*      */   {
/* 1319 */     this.addMoreFormatting = newAddMoreFormatting;
/*      */   }
/*      */ 
/*      */   public boolean getSortByPosition()
/*      */   {
/* 1330 */     return this.sortByPosition;
/*      */   }
/*      */ 
/*      */   public void setSortByPosition(boolean newSortByPosition)
/*      */   {
/* 1348 */     this.sortByPosition = newSortByPosition;
/*      */   }
/*      */ 
/*      */   public float getSpacingTolerance()
/*      */   {
/* 1360 */     return this.spacingTolerance;
/*      */   }
/*      */ 
/*      */   public void setSpacingTolerance(float spacingToleranceValue)
/*      */   {
/* 1373 */     this.spacingTolerance = spacingToleranceValue;
/*      */   }
/*      */ 
/*      */   public float getAverageCharTolerance()
/*      */   {
/* 1385 */     return this.averageCharTolerance;
/*      */   }
/*      */ 
/*      */   public void setAverageCharTolerance(float averageCharToleranceValue)
/*      */   {
/* 1398 */     this.averageCharTolerance = averageCharToleranceValue;
/*      */   }
/*      */ 
/*      */   public float getIndentThreshold()
/*      */   {
/* 1413 */     return this.indentThreshold;
/*      */   }
/*      */ 
/*      */   public void setIndentThreshold(float indentThresholdValue)
/*      */   {
/* 1428 */     this.indentThreshold = indentThresholdValue;
/*      */   }
/*      */ 
/*      */   public float getDropThreshold()
/*      */   {
/* 1442 */     return this.dropThreshold;
/*      */   }
/*      */ 
/*      */   public void setDropThreshold(float dropThresholdValue)
/*      */   {
/* 1457 */     this.dropThreshold = dropThresholdValue;
/*      */   }
/*      */ 
/*      */   public String getParagraphStart()
/*      */   {
/* 1466 */     return this.paragraphStart;
/*      */   }
/*      */ 
/*      */   public void setParagraphStart(String s)
/*      */   {
/* 1475 */     this.paragraphStart = s;
/*      */   }
/*      */ 
/*      */   public String getParagraphEnd()
/*      */   {
/* 1484 */     return this.paragraphEnd;
/*      */   }
/*      */ 
/*      */   public void setParagraphEnd(String s)
/*      */   {
/* 1493 */     this.paragraphEnd = s;
/*      */   }
/*      */ 
/*      */   public String getPageStart()
/*      */   {
/* 1503 */     return this.pageStart;
/*      */   }
/*      */ 
/*      */   public void setPageStart(String pageStartValue)
/*      */   {
/* 1512 */     this.pageStart = pageStartValue;
/*      */   }
/*      */ 
/*      */   public String getPageEnd()
/*      */   {
/* 1521 */     return this.pageEnd;
/*      */   }
/*      */ 
/*      */   public void setPageEnd(String pageEndValue)
/*      */   {
/* 1530 */     this.pageEnd = pageEndValue;
/*      */   }
/*      */ 
/*      */   public String getArticleStart()
/*      */   {
/* 1539 */     return this.articleStart;
/*      */   }
/*      */ 
/*      */   public void setArticleStart(String articleStartValue)
/*      */   {
/* 1548 */     this.articleStart = articleStartValue;
/*      */   }
/*      */ 
/*      */   public String getArticleEnd()
/*      */   {
/* 1557 */     return this.articleEnd;
/*      */   }
/*      */ 
/*      */   public void setArticleEnd(String articleEndValue)
/*      */   {
/* 1566 */     this.articleEnd = articleEndValue;
/*      */   }
/*      */ 
/*      */   public String inspectFontEncoding(String str)
/*      */   {
/* 1584 */     if ((!this.sortByPosition) || (str == null) || (str.length() < 2))
/*      */     {
/* 1586 */       return str;
/*      */     }
/* 1588 */     for (int i = 0; i < str.length(); i++)
/*      */     {
/* 1590 */       if (Character.getDirectionality(str.charAt(i)) != 2)
/*      */       {
/* 1593 */         return str;
/*      */       }
/*      */     }
/* 1596 */     StringBuilder reversed = new StringBuilder(str.length());
/* 1597 */     for (int i = str.length() - 1; i >= 0; i--)
/*      */     {
/* 1599 */       reversed.append(str.charAt(i));
/*      */     }
/* 1601 */     return reversed.toString();
/*      */   }
/*      */ 
/*      */   protected PositionWrapper handleLineSeparation(PositionWrapper current, PositionWrapper lastPosition, PositionWrapper lastLineStartPosition, float maxHeightForLine)
/*      */     throws IOException
/*      */   {
/* 1619 */     current.setLineStart();
/* 1620 */     isParagraphSeparation(current, lastPosition, lastLineStartPosition, maxHeightForLine);
/* 1621 */     lastLineStartPosition = current;
/* 1622 */     if (current.isParagraphStart())
/*      */     {
/* 1624 */       if (lastPosition.isArticleStart())
/*      */       {
/* 1626 */         writeParagraphStart();
/*      */       }
/*      */       else
/*      */       {
/* 1630 */         writeLineSeparator();
/* 1631 */         writeParagraphSeparator();
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1636 */       writeLineSeparator();
/*      */     }
/* 1638 */     return lastLineStartPosition;
/*      */   }
/*      */ 
/*      */   protected void isParagraphSeparation(PositionWrapper position, PositionWrapper lastPosition, PositionWrapper lastLineStartPosition, float maxHeightForLine)
/*      */   {
/* 1669 */     boolean result = false;
/* 1670 */     if (lastLineStartPosition == null)
/*      */     {
/* 1672 */       result = true;
/*      */     }
/*      */     else
/*      */     {
/* 1676 */       float yGap = Math.abs(position.getTextPosition().getYDirAdj() - lastPosition.getTextPosition().getYDirAdj());
/*      */ 
/* 1678 */       float xGap = position.getTextPosition().getXDirAdj() - lastLineStartPosition.getTextPosition().getXDirAdj();
/*      */ 
/* 1680 */       if (yGap > getDropThreshold() * maxHeightForLine)
/*      */       {
/* 1682 */         result = true;
/*      */       }
/* 1684 */       else if (xGap > getIndentThreshold() * position.getTextPosition().getWidthOfSpace())
/*      */       {
/* 1687 */         if (!lastLineStartPosition.isParagraphStart())
/*      */         {
/* 1689 */           result = true;
/*      */         }
/*      */         else
/*      */         {
/* 1693 */           position.setHangingIndent();
/*      */         }
/*      */       }
/* 1696 */       else if (xGap < -position.getTextPosition().getWidthOfSpace())
/*      */       {
/* 1699 */         if (!lastLineStartPosition.isParagraphStart())
/*      */         {
/* 1701 */           result = true;
/*      */         }
/*      */       }
/* 1704 */       else if (Math.abs(xGap) < 0.25D * position.getTextPosition().getWidth())
/*      */       {
/* 1708 */         if (lastLineStartPosition.isHangingIndent())
/*      */         {
/* 1710 */           position.setHangingIndent();
/*      */         }
/* 1712 */         else if (lastLineStartPosition.isParagraphStart())
/*      */         {
/* 1716 */           Pattern liPattern = matchListItemPattern(lastLineStartPosition);
/* 1717 */           if (liPattern != null)
/*      */           {
/* 1719 */             Pattern currentPattern = matchListItemPattern(position);
/* 1720 */             if (liPattern == currentPattern)
/*      */             {
/* 1722 */               result = true;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1728 */     if (result)
/*      */     {
/* 1730 */       position.setParagraphStart();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void writeParagraphSeparator()
/*      */     throws IOException
/*      */   {
/* 1740 */     writeParagraphEnd();
/* 1741 */     writeParagraphStart();
/*      */   }
/*      */ 
/*      */   protected void writeParagraphStart()
/*      */     throws IOException
/*      */   {
/* 1750 */     if (this.inParagraph)
/*      */     {
/* 1752 */       writeParagraphEnd();
/* 1753 */       this.inParagraph = false;
/*      */     }
/* 1755 */     this.output.write(getParagraphStart());
/* 1756 */     this.inParagraph = true;
/*      */   }
/*      */ 
/*      */   protected void writeParagraphEnd()
/*      */     throws IOException
/*      */   {
/* 1765 */     if (!this.inParagraph)
/*      */     {
/* 1767 */       writeParagraphStart();
/*      */     }
/* 1769 */     this.output.write(getParagraphEnd());
/* 1770 */     this.inParagraph = false;
/*      */   }
/*      */ 
/*      */   protected void writePageStart()
/*      */     throws IOException
/*      */   {
/* 1779 */     this.output.write(getPageStart());
/*      */   }
/*      */ 
/*      */   protected void writePageEnd()
/*      */     throws IOException
/*      */   {
/* 1788 */     this.output.write(getPageEnd());
/*      */   }
/*      */ 
/*      */   protected Pattern matchListItemPattern(PositionWrapper pw)
/*      */   {
/* 1805 */     TextPosition tp = pw.getTextPosition();
/* 1806 */     String txt = tp.getCharacter();
/* 1807 */     return matchPattern(txt, getListItemPatterns());
/*      */   }
/*      */ 
/*      */   protected void setListItemPatterns(List<Pattern> patterns)
/*      */   {
/* 1839 */     this.listOfPatterns = patterns;
/*      */   }
/*      */ 
/*      */   protected List<Pattern> getListItemPatterns()
/*      */   {
/* 1863 */     if (this.listOfPatterns == null)
/*      */     {
/* 1865 */       this.listOfPatterns = new ArrayList();
/* 1866 */       for (String expression : LIST_ITEM_EXPRESSIONS)
/*      */       {
/* 1868 */         Pattern p = Pattern.compile(expression);
/* 1869 */         this.listOfPatterns.add(p);
/*      */       }
/*      */     }
/* 1872 */     return this.listOfPatterns;
/*      */   }
/*      */ 
/*      */   protected static final Pattern matchPattern(String string, List<Pattern> patterns)
/*      */   {
/* 1891 */     Pattern matchedPattern = null;
/* 1892 */     for (Pattern p : patterns)
/*      */     {
/* 1894 */       if (p.matcher(string).matches())
/*      */       {
/* 1896 */         return p;
/*      */       }
/*      */     }
/* 1899 */     return matchedPattern;
/*      */   }
/*      */ 
/*      */   private void writeLine(List<WordWithTextPositions> line, boolean isRtlDominant)
/*      */     throws IOException
/*      */   {
/* 1910 */     int numberOfStrings = line.size();
/* 1911 */     for (int i = 0; i < numberOfStrings; i++)
/*      */     {
/* 1913 */       WordWithTextPositions word = (WordWithTextPositions)line.get(i);
/* 1914 */       writeString(word.getText(), word.getTextPositions());
/* 1915 */       if (i < numberOfStrings - 1)
/*      */       {
/* 1917 */         writeWordSeparator();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private List<WordWithTextPositions> normalize(List<TextPosition> line, boolean isRtlDominant, boolean hasRtl)
/*      */   {
/* 1931 */     LinkedList normalized = new LinkedList();
/* 1932 */     StringBuilder lineBuilder = new StringBuilder();
/* 1933 */     List wordPositions = new ArrayList();
/*      */ 
/* 1935 */     if (isRtlDominant)
/*      */     {
/* 1937 */       int numberOfPositions = line.size();
/* 1938 */       for (int i = numberOfPositions - 1; i >= 0; i--)
/*      */       {
/* 1940 */         lineBuilder = normalizeAdd(normalized, lineBuilder, wordPositions, (TextPosition)line.get(i));
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1945 */       for (TextPosition text : line)
/*      */       {
/* 1947 */         lineBuilder = normalizeAdd(normalized, lineBuilder, wordPositions, text);
/*      */       }
/*      */     }
/* 1950 */     if (lineBuilder.length() > 0)
/*      */     {
/* 1952 */       normalized.add(createWord(lineBuilder.toString(), wordPositions));
/*      */     }
/* 1954 */     return normalized;
/*      */   }
/*      */ 
/*      */   private WordWithTextPositions createWord(String word, List<TextPosition> wordPositions)
/*      */   {
/* 1963 */     return new WordWithTextPositions(this.normalize.normalizePres(word), wordPositions);
/*      */   }
/*      */ 
/*      */   private StringBuilder normalizeAdd(LinkedList<WordWithTextPositions> normalized, StringBuilder lineBuilder, List<TextPosition> wordPositions, TextPosition text)
/*      */   {
/* 1973 */     if ((text instanceof WordSeparator))
/*      */     {
/* 1975 */       normalized.add(createWord(lineBuilder.toString(), new ArrayList(wordPositions)));
/* 1976 */       lineBuilder = new StringBuilder();
/* 1977 */       wordPositions.clear();
/*      */     }
/*      */     else
/*      */     {
/* 1981 */       lineBuilder.append(text.getCharacter());
/* 1982 */       wordPositions.add(text);
/*      */     }
/* 1984 */     return lineBuilder;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*   77 */     String sdrop = null; String sindent = null;
/*      */     try
/*      */     {
/*   80 */       String prop = thisClassName + ".indent";
/*   81 */       sindent = System.getProperty(prop);
/*   82 */       prop = thisClassName + ".drop";
/*   83 */       sdrop = System.getProperty(prop);
/*      */     }
/*      */     catch (SecurityException e)
/*      */     {
/*      */     }
/*      */ 
/*   90 */     if ((sindent != null) && (sindent.length() > 0))
/*      */     {
/*      */       try
/*      */       {
/*   94 */         float f = Float.parseFloat(sindent);
/*   95 */         DEFAULT_INDENT_THRESHOLD = f;
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*      */       }
/*      */     }
/*      */ 
/*  102 */     if ((sdrop != null) && (sdrop.length() > 0))
/*      */     {
/*      */       try
/*      */       {
/*  106 */         float f = Float.parseFloat(sdrop);
/*  107 */         DEFAULT_DROP_THRESHOLD = f;
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  118 */     boolean is16orLess = false;
/*      */     try
/*      */     {
/*  121 */       String[] versionComponents = System.getProperty("java.version").split("\\.");
/*  122 */       int javaMajorVersion = Integer.parseInt(versionComponents[0]);
/*  123 */       int javaMinorVersion = Integer.parseInt(versionComponents[1]);
/*  124 */       is16orLess = (javaMajorVersion == 1) && (javaMinorVersion <= 6);
/*      */     }
/*      */     catch (SecurityException e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   private static final class WordWithTextPositions
/*      */   {
/*      */     protected String text;
/*      */     protected List<TextPosition> textPositions;
/*      */ 
/*      */     public WordWithTextPositions(String word, List<TextPosition> positions)
/*      */     {
/* 2021 */       this.text = word;
/* 2022 */       this.textPositions = positions;
/*      */     }
/*      */ 
/*      */     public String getText()
/*      */     {
/* 2027 */       return this.text;
/*      */     }
/*      */ 
/*      */     public List<TextPosition> getTextPositions()
/*      */     {
/* 2032 */       return this.textPositions;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static final class WordSeparator extends TextPosition
/*      */   {
/* 1995 */     private static final WordSeparator separator = new WordSeparator();
/*      */ 
/*      */     public static final WordSeparator getSeparator()
/*      */     {
/* 2003 */       return separator;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.PDFTextStripper
 * JD-Core Version:    0.6.2
 */