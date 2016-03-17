/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.AccessibleElementId;
/*      */ import com.itextpdf.text.Anchor;
/*      */ import com.itextpdf.text.Annotation;
/*      */ import com.itextpdf.text.BaseColor;
/*      */ import com.itextpdf.text.Chunk;
/*      */ import com.itextpdf.text.Document;
/*      */ import com.itextpdf.text.DocumentException;
/*      */ import com.itextpdf.text.Element;
/*      */ import com.itextpdf.text.ExceptionConverter;
/*      */ import com.itextpdf.text.Font;
/*      */ import com.itextpdf.text.Image;
/*      */ import com.itextpdf.text.List;
/*      */ import com.itextpdf.text.ListItem;
/*      */ import com.itextpdf.text.ListLabel;
/*      */ import com.itextpdf.text.MarkedObject;
/*      */ import com.itextpdf.text.MarkedSection;
/*      */ import com.itextpdf.text.Meta;
/*      */ import com.itextpdf.text.Paragraph;
/*      */ import com.itextpdf.text.Phrase;
/*      */ import com.itextpdf.text.Rectangle;
/*      */ import com.itextpdf.text.Section;
/*      */ import com.itextpdf.text.TabSettings;
/*      */ import com.itextpdf.text.TabStop;
/*      */ import com.itextpdf.text.Version;
/*      */ import com.itextpdf.text.api.WriterOperation;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.pdf.collection.PdfCollection;
/*      */ import com.itextpdf.text.pdf.draw.DrawInterface;
/*      */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*      */ import com.itextpdf.text.pdf.internal.PdfAnnotationsImp;
/*      */ import com.itextpdf.text.pdf.internal.PdfVersionImp;
/*      */ import com.itextpdf.text.pdf.internal.PdfViewerPreferencesImp;
/*      */ import java.io.IOException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Stack;
/*      */ import java.util.TreeMap;
/*      */ 
/*      */ public class PdfDocument extends Document
/*      */ {
/*      */   protected PdfWriter writer;
/*  302 */   protected HashMap<AccessibleElementId, PdfStructureElement> structElements = new HashMap();
/*      */ 
/*  304 */   protected boolean openMCDocument = false;
/*      */ 
/*  306 */   protected HashMap<Object, int[]> structParentIndices = new HashMap();
/*      */ 
/*  308 */   protected HashMap<Object, Integer> markPoints = new HashMap();
/*      */   protected PdfContentByte text;
/*      */   protected PdfContentByte graphics;
/*  337 */   protected float leading = 0.0F;
/*      */ 
/*  358 */   protected int alignment = 0;
/*      */ 
/*  361 */   protected float currentHeight = 0.0F;
/*      */ 
/*  367 */   protected boolean isSectionTitle = false;
/*      */ 
/*  370 */   protected PdfAction anchorAction = null;
/*      */   protected TabSettings tabSettings;
/*  384 */   private Stack<Float> leadingStack = new Stack();
/*      */   protected int textEmptySize;
/*      */   protected float nextMarginLeft;
/*      */   protected float nextMarginRight;
/*      */   protected float nextMarginTop;
/*      */   protected float nextMarginBottom;
/* 1135 */   protected boolean firstPageEvent = true;
/*      */ 
/* 1203 */   protected PdfLine line = null;
/*      */ 
/* 1205 */   protected ArrayList<PdfLine> lines = new ArrayList();
/*      */ 
/* 1290 */   protected int lastElementType = -1;
/*      */   static final String hangingPunctuation = ".,;:'";
/* 1805 */   protected Indentation indentation = new Indentation();
/*      */ 
/* 1911 */   protected PdfInfo info = new PdfInfo();
/*      */   protected PdfOutline rootOutline;
/*      */   protected PdfOutline currentOutline;
/* 2089 */   protected PdfViewerPreferencesImp viewerPreferences = new PdfViewerPreferencesImp();
/*      */   protected PdfPageLabels pageLabels;
/* 2171 */   protected TreeMap<String, Destination> localDestinations = new TreeMap();
/*      */   int jsCounter;
/* 2218 */   protected HashMap<String, PdfObject> documentLevelJS = new HashMap();
/* 2219 */   protected static final DecimalFormat SIXTEEN_DIGITS = new DecimalFormat("0000000000000000");
/*      */ 
/* 2245 */   protected HashMap<String, PdfObject> documentFileAttachment = new HashMap();
/*      */   protected String openActionName;
/*      */   protected PdfAction openActionAction;
/*      */   protected PdfDictionary additionalActions;
/*      */   protected PdfCollection collection;
/*      */   PdfAnnotationsImp annotationsImp;
/*      */   protected PdfString language;
/* 2347 */   protected Rectangle nextPageSize = null;
/*      */ 
/* 2350 */   protected HashMap<String, PdfRectangle> thisBoxSize = new HashMap();
/*      */ 
/* 2354 */   protected HashMap<String, PdfRectangle> boxSize = new HashMap();
/*      */ 
/* 2411 */   private boolean pageEmpty = true;
/*      */ 
/* 2444 */   protected PdfDictionary pageAA = null;
/*      */   protected PageResources pageResources;
/* 2470 */   protected boolean strictImageSequence = false;
/*      */ 
/* 2489 */   protected float imageEnd = -1.0F;
/*      */ 
/* 2537 */   protected Image imageWait = null;
/*      */ 
/* 2657 */   private ArrayList<Element> floatingElements = new ArrayList();
/*      */ 
/*      */   public PdfDocument()
/*      */   {
/*  295 */     addProducer();
/*  296 */     addCreationDate();
/*      */   }
/*      */ 
/*      */   public void addWriter(PdfWriter writer)
/*      */     throws DocumentException
/*      */   {
/*  318 */     if (this.writer == null) {
/*  319 */       this.writer = writer;
/*  320 */       this.annotationsImp = new PdfAnnotationsImp(writer);
/*  321 */       return;
/*      */     }
/*  323 */     throw new DocumentException(MessageLocalization.getComposedMessage("you.can.only.add.a.writer.to.a.pdfdocument.once", new Object[0]));
/*      */   }
/*      */ 
/*      */   public float getLeading()
/*      */   {
/*  345 */     return this.leading;
/*      */   }
/*      */ 
/*      */   void setLeading(float leading)
/*      */   {
/*  354 */     this.leading = leading;
/*      */   }
/*      */ 
/*      */   protected void pushLeading()
/*      */   {
/*  390 */     this.leadingStack.push(Float.valueOf(this.leading));
/*      */   }
/*      */ 
/*      */   protected void popLeading()
/*      */   {
/*  397 */     this.leading = ((Float)this.leadingStack.pop()).floatValue();
/*  398 */     if (this.leadingStack.size() > 0)
/*  399 */       this.leading = ((Float)this.leadingStack.peek()).floatValue();
/*      */   }
/*      */ 
/*      */   public TabSettings getTabSettings()
/*      */   {
/*  407 */     return this.tabSettings;
/*      */   }
/*      */ 
/*      */   public void setTabSettings(TabSettings tabSettings)
/*      */   {
/*  416 */     this.tabSettings = tabSettings;
/*      */   }
/*      */ 
/*      */   public boolean add(Element element)
/*      */     throws DocumentException
/*      */   {
/*  428 */     if ((this.writer != null) && (this.writer.isPaused()))
/*  429 */       return false;
/*      */     try
/*      */     {
/*  432 */       if (element.type() != 37) {
/*  433 */         flushFloatingElements();
/*      */       }
/*      */ 
/*  436 */       switch (element.type())
/*      */       {
/*      */       case 0:
/*  439 */         this.info.addkey(((Meta)element).getName(), ((Meta)element).getContent());
/*  440 */         break;
/*      */       case 1:
/*  442 */         this.info.addTitle(((Meta)element).getContent());
/*  443 */         break;
/*      */       case 2:
/*  445 */         this.info.addSubject(((Meta)element).getContent());
/*  446 */         break;
/*      */       case 3:
/*  448 */         this.info.addKeywords(((Meta)element).getContent());
/*  449 */         break;
/*      */       case 4:
/*  451 */         this.info.addAuthor(((Meta)element).getContent());
/*  452 */         break;
/*      */       case 7:
/*  454 */         this.info.addCreator(((Meta)element).getContent());
/*  455 */         break;
/*      */       case 8:
/*  457 */         setLanguage(((Meta)element).getContent());
/*  458 */         break;
/*      */       case 5:
/*  461 */         this.info.addProducer();
/*  462 */         break;
/*      */       case 6:
/*  465 */         this.info.addCreationDate();
/*  466 */         break;
/*      */       case 10:
/*  470 */         if (this.line == null) {
/*  471 */           carriageReturn();
/*      */         }
/*      */ 
/*  475 */         PdfChunk chunk = new PdfChunk((Chunk)element, this.anchorAction, this.tabSettings);
/*      */         PdfChunk overflow;
/*  479 */         while ((overflow = this.line.add(chunk)) != null) {
/*  480 */           carriageReturn();
/*  481 */           boolean newlineSplit = chunk.isNewlineSplit();
/*  482 */           chunk = overflow;
/*  483 */           if (!newlineSplit) {
/*  484 */             chunk.trimFirstSpace();
/*      */           }
/*      */         }
/*      */ 
/*  488 */         this.pageEmpty = false;
/*  489 */         if (chunk.isAttribute("NEWPAGE"))
/*  490 */           newPage(); break;
/*      */       case 17:
/*  495 */         Anchor anchor = (Anchor)element;
/*  496 */         String url = anchor.getReference();
/*  497 */         this.leading = anchor.getLeading();
/*  498 */         pushLeading();
/*  499 */         if (url != null) {
/*  500 */           this.anchorAction = new PdfAction(url);
/*      */         }
/*      */ 
/*  503 */         element.process(this);
/*  504 */         this.anchorAction = null;
/*  505 */         popLeading();
/*  506 */         break;
/*      */       case 29:
/*  509 */         if (this.line == null) {
/*  510 */           carriageReturn();
/*      */         }
/*  512 */         Annotation annot = (Annotation)element;
/*  513 */         Rectangle rect = new Rectangle(0.0F, 0.0F);
/*  514 */         if (this.line != null)
/*  515 */           rect = new Rectangle(annot.llx(indentRight() - this.line.widthLeft()), annot.ury(indentTop() - this.currentHeight - 20.0F), annot.urx(indentRight() - this.line.widthLeft() + 20.0F), annot.lly(indentTop() - this.currentHeight));
/*  516 */         PdfAnnotation an = PdfAnnotationsImp.convertAnnotation(this.writer, annot, rect);
/*  517 */         this.annotationsImp.addPlainAnnotation(an);
/*  518 */         this.pageEmpty = false;
/*  519 */         break;
/*      */       case 11:
/*  522 */         TabSettings backupTabSettings = this.tabSettings;
/*  523 */         if (((Phrase)element).getTabSettings() != null) {
/*  524 */           this.tabSettings = ((Phrase)element).getTabSettings();
/*      */         }
/*  526 */         this.leading = ((Phrase)element).getTotalLeading();
/*  527 */         pushLeading();
/*      */ 
/*  529 */         element.process(this);
/*  530 */         this.tabSettings = backupTabSettings;
/*  531 */         popLeading();
/*  532 */         break;
/*      */       case 12:
/*  535 */         TabSettings backupTabSettings = this.tabSettings;
/*  536 */         if (((Phrase)element).getTabSettings() != null) {
/*  537 */           this.tabSettings = ((Phrase)element).getTabSettings();
/*      */         }
/*  539 */         Paragraph paragraph = (Paragraph)element;
/*  540 */         if (isTagged(this.writer)) {
/*  541 */           flushLines();
/*  542 */           this.text.openMCBlock(paragraph);
/*      */         }
/*  544 */         addSpacing(paragraph.getSpacingBefore(), this.leading, paragraph.getFont());
/*      */ 
/*  547 */         this.alignment = paragraph.getAlignment();
/*  548 */         this.leading = paragraph.getTotalLeading();
/*  549 */         pushLeading();
/*  550 */         carriageReturn();
/*      */ 
/*  553 */         if (this.currentHeight + calculateLineHeight() > indentTop() - indentBottom()) {
/*  554 */           newPage();
/*      */         }
/*      */ 
/*  557 */         this.indentation.indentLeft += paragraph.getIndentationLeft();
/*  558 */         this.indentation.indentRight += paragraph.getIndentationRight();
/*  559 */         carriageReturn();
/*      */ 
/*  561 */         PdfPageEvent pageEvent = this.writer.getPageEvent();
/*  562 */         if ((pageEvent != null) && (!this.isSectionTitle)) {
/*  563 */           pageEvent.onParagraph(this.writer, this, indentTop() - this.currentHeight);
/*      */         }
/*      */ 
/*  566 */         if (paragraph.getKeepTogether()) {
/*  567 */           carriageReturn();
/*  568 */           PdfPTable table = new PdfPTable(1);
/*  569 */           table.setKeepTogether(paragraph.getKeepTogether());
/*  570 */           table.setWidthPercentage(100.0F);
/*  571 */           PdfPCell cell = new PdfPCell();
/*  572 */           cell.addElement(paragraph);
/*  573 */           cell.setBorder(0);
/*  574 */           cell.setPadding(0.0F);
/*  575 */           table.addCell(cell);
/*  576 */           this.indentation.indentLeft -= paragraph.getIndentationLeft();
/*  577 */           this.indentation.indentRight -= paragraph.getIndentationRight();
/*  578 */           add(table);
/*  579 */           this.indentation.indentLeft += paragraph.getIndentationLeft();
/*  580 */           this.indentation.indentRight += paragraph.getIndentationRight();
/*      */         }
/*      */         else {
/*  583 */           this.line.setExtraIndent(paragraph.getFirstLineIndent());
/*  584 */           element.process(this);
/*  585 */           carriageReturn();
/*  586 */           addSpacing(paragraph.getSpacingAfter(), paragraph.getTotalLeading(), paragraph.getFont());
/*      */         }
/*      */ 
/*  589 */         if ((pageEvent != null) && (!this.isSectionTitle)) {
/*  590 */           pageEvent.onParagraphEnd(this.writer, this, indentTop() - this.currentHeight);
/*      */         }
/*  592 */         this.alignment = 0;
/*  593 */         this.indentation.indentLeft -= paragraph.getIndentationLeft();
/*  594 */         this.indentation.indentRight -= paragraph.getIndentationRight();
/*  595 */         carriageReturn();
/*  596 */         this.tabSettings = backupTabSettings;
/*  597 */         popLeading();
/*  598 */         if (isTagged(this.writer)) {
/*  599 */           flushLines();
/*  600 */           this.text.closeMCBlock(paragraph); } break;
/*      */       case 13:
/*      */       case 16:
/*  608 */         Section section = (Section)element;
/*  609 */         PdfPageEvent pageEvent = this.writer.getPageEvent();
/*      */ 
/*  611 */         boolean hasTitle = (section.isNotAddedYet()) && (section.getTitle() != null);
/*      */ 
/*  615 */         if (section.isTriggerNewPage()) {
/*  616 */           newPage();
/*      */         }
/*      */ 
/*  619 */         if (hasTitle) {
/*  620 */           float fith = indentTop() - this.currentHeight;
/*  621 */           int rotation = this.pageSize.getRotation();
/*  622 */           if ((rotation == 90) || (rotation == 180))
/*  623 */             fith = this.pageSize.getHeight() - fith;
/*  624 */           PdfDestination destination = new PdfDestination(2, fith);
/*  625 */           while (this.currentOutline.level() >= section.getDepth()) {
/*  626 */             this.currentOutline = this.currentOutline.parent();
/*      */           }
/*  628 */           PdfOutline outline = new PdfOutline(this.currentOutline, destination, section.getBookmarkTitle(), section.isBookmarkOpen());
/*  629 */           this.currentOutline = outline;
/*      */         }
/*      */ 
/*  633 */         carriageReturn();
/*  634 */         this.indentation.sectionIndentLeft += section.getIndentationLeft();
/*  635 */         this.indentation.sectionIndentRight += section.getIndentationRight();
/*      */ 
/*  637 */         if ((section.isNotAddedYet()) && (pageEvent != null)) {
/*  638 */           if (element.type() == 16)
/*  639 */             pageEvent.onChapter(this.writer, this, indentTop() - this.currentHeight, section.getTitle());
/*      */           else {
/*  641 */             pageEvent.onSection(this.writer, this, indentTop() - this.currentHeight, section.getDepth(), section.getTitle());
/*      */           }
/*      */         }
/*  644 */         if (hasTitle) {
/*  645 */           this.isSectionTitle = true;
/*  646 */           add(section.getTitle());
/*  647 */           this.isSectionTitle = false;
/*      */         }
/*  649 */         this.indentation.sectionIndentLeft += section.getIndentation();
/*      */ 
/*  651 */         element.process(this);
/*  652 */         flushLines();
/*      */ 
/*  654 */         this.indentation.sectionIndentLeft -= section.getIndentationLeft() + section.getIndentation();
/*  655 */         this.indentation.sectionIndentRight -= section.getIndentationRight();
/*      */ 
/*  657 */         if ((section.isComplete()) && (pageEvent != null))
/*  658 */           if (element.type() == 16)
/*  659 */             pageEvent.onChapterEnd(this.writer, this, indentTop() - this.currentHeight);
/*      */           else
/*  661 */             pageEvent.onSectionEnd(this.writer, this, indentTop() - this.currentHeight); 
/*  661 */         break;
/*      */       case 14:
/*  667 */         List list = (List)element;
/*  668 */         if (isTagged(this.writer)) {
/*  669 */           flushLines();
/*  670 */           this.text.openMCBlock(list);
/*      */         }
/*  672 */         if (list.isAlignindent()) {
/*  673 */           list.normalizeIndentation();
/*      */         }
/*      */ 
/*  676 */         this.indentation.listIndentLeft += list.getIndentationLeft();
/*  677 */         this.indentation.indentRight += list.getIndentationRight();
/*      */ 
/*  679 */         element.process(this);
/*      */ 
/*  682 */         this.indentation.listIndentLeft -= list.getIndentationLeft();
/*  683 */         this.indentation.indentRight -= list.getIndentationRight();
/*  684 */         carriageReturn();
/*  685 */         if (isTagged(this.writer)) {
/*  686 */           flushLines();
/*  687 */           this.text.closeMCBlock(list); } break;
/*      */       case 15:
/*  693 */         ListItem listItem = (ListItem)element;
/*  694 */         if (isTagged(this.writer)) {
/*  695 */           flushLines();
/*  696 */           this.text.openMCBlock(listItem);
/*      */         }
/*      */ 
/*  699 */         addSpacing(listItem.getSpacingBefore(), this.leading, listItem.getFont());
/*      */ 
/*  702 */         this.alignment = listItem.getAlignment();
/*  703 */         this.indentation.listIndentLeft += listItem.getIndentationLeft();
/*  704 */         this.indentation.indentRight += listItem.getIndentationRight();
/*  705 */         this.leading = listItem.getTotalLeading();
/*  706 */         pushLeading();
/*  707 */         carriageReturn();
/*      */ 
/*  710 */         this.line.setListItem(listItem);
/*      */ 
/*  712 */         element.process(this);
/*  713 */         addSpacing(listItem.getSpacingAfter(), listItem.getTotalLeading(), listItem.getFont());
/*      */ 
/*  716 */         if (this.line.hasToBeJustified()) {
/*  717 */           this.line.resetAlignment();
/*      */         }
/*      */ 
/*  720 */         carriageReturn();
/*  721 */         this.indentation.listIndentLeft -= listItem.getIndentationLeft();
/*  722 */         this.indentation.indentRight -= listItem.getIndentationRight();
/*  723 */         popLeading();
/*  724 */         if (isTagged(this.writer)) {
/*  725 */           flushLines();
/*  726 */           this.text.closeMCBlock(listItem.getListBody());
/*  727 */           this.text.closeMCBlock(listItem); } break;
/*      */       case 30:
/*  732 */         Rectangle rectangle = (Rectangle)element;
/*  733 */         this.graphics.rectangle(rectangle);
/*  734 */         this.pageEmpty = false;
/*  735 */         break;
/*      */       case 23:
/*  738 */         PdfPTable ptable = (PdfPTable)element;
/*  739 */         if (ptable.size() > ptable.getHeaderRows())
/*      */         {
/*  743 */           ensureNewLine();
/*  744 */           flushLines();
/*      */ 
/*  746 */           addPTable(ptable);
/*  747 */           this.pageEmpty = false;
/*  748 */           newLine();
/*  749 */         }break;
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*  757 */         if ((isTagged(this.writer)) && (!((Image)element).isImgTemplate())) {
/*  758 */           flushLines();
/*  759 */           this.text.openMCBlock((Image)element);
/*      */         }
/*  761 */         add((Image)element);
/*  762 */         if ((isTagged(this.writer)) && (!((Image)element).isImgTemplate())) {
/*  763 */           flushLines();
/*  764 */           this.text.closeMCBlock((Image)element); } break;
/*      */       case 55:
/*  769 */         DrawInterface zh = (DrawInterface)element;
/*  770 */         zh.draw(this.graphics, indentLeft(), indentBottom(), indentRight(), indentTop(), indentTop() - this.currentHeight - (this.leadingStack.size() > 0 ? this.leading : 0.0F));
/*  771 */         this.pageEmpty = false;
/*  772 */         break;
/*      */       case 50:
/*  776 */         if ((element instanceof MarkedSection)) {
/*  777 */           MarkedObject mo = ((MarkedSection)element).getTitle();
/*  778 */           if (mo != null) {
/*  779 */             mo.process(this);
/*      */           }
/*      */         }
/*  782 */         MarkedObject mo = (MarkedObject)element;
/*  783 */         mo.process(this);
/*  784 */         break;
/*      */       case 666:
/*  787 */         if (null != this.writer)
/*  788 */           ((WriterOperation)element).write(this.writer, this); break;
/*      */       case 37:
/*  792 */         ensureNewLine();
/*  793 */         flushLines();
/*  794 */         addDiv((PdfDiv)element);
/*  795 */         this.pageEmpty = false;
/*      */ 
/*  797 */         break;
/*      */       default:
/*  799 */         return false;
/*      */       }
/*  801 */       this.lastElementType = element.type();
/*  802 */       return true;
/*      */     }
/*      */     catch (Exception e) {
/*  805 */       throw new DocumentException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void open()
/*      */   {
/*  819 */     if (!this.open) {
/*  820 */       super.open();
/*  821 */       this.writer.open();
/*  822 */       this.rootOutline = new PdfOutline(this.writer);
/*  823 */       this.currentOutline = this.rootOutline;
/*      */     }
/*      */     try {
/*  826 */       initPage();
/*  827 */       if (isTagged(this.writer))
/*  828 */         this.openMCDocument = true;
/*      */     }
/*      */     catch (DocumentException de)
/*      */     {
/*  832 */       throw new ExceptionConverter(de);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void close()
/*      */   {
/*  846 */     if (this.close)
/*  847 */       return;
/*      */     try
/*      */     {
/*  850 */       if (isTagged(this.writer)) {
/*  851 */         flushFloatingElements();
/*  852 */         flushLines();
/*  853 */         this.writer.getDirectContent().closeMCBlock(this);
/*  854 */         this.writer.flushAcroFields();
/*  855 */         this.writer.flushTaggedObjects();
/*  856 */         if (isPageEmpty()) {
/*  857 */           int pageReferenceCount = this.writer.pageReferences.size();
/*  858 */           if ((pageReferenceCount > 0) && (this.writer.currentPageNumber == pageReferenceCount))
/*  859 */             this.writer.pageReferences.remove(pageReferenceCount - 1);
/*      */         }
/*      */       }
/*      */       else {
/*  863 */         this.writer.flushAcroFields();
/*  864 */       }boolean wasImage = this.imageWait != null;
/*  865 */       newPage();
/*  866 */       if ((this.imageWait != null) || (wasImage)) newPage();
/*  867 */       if (this.annotationsImp.hasUnusedAnnotations())
/*  868 */         throw new RuntimeException(MessageLocalization.getComposedMessage("not.all.annotations.could.be.added.to.the.document.the.document.doesn.t.have.enough.pages", new Object[0]));
/*  869 */       PdfPageEvent pageEvent = this.writer.getPageEvent();
/*  870 */       if (pageEvent != null)
/*  871 */         pageEvent.onCloseDocument(this.writer, this);
/*  872 */       super.close();
/*      */ 
/*  874 */       this.writer.addLocalDestinations(this.localDestinations);
/*  875 */       calculateOutlineCount();
/*  876 */       writeOutlines();
/*      */     }
/*      */     catch (Exception e) {
/*  879 */       throw ExceptionConverter.convertException(e);
/*      */     }
/*      */ 
/*  882 */     this.writer.close();
/*      */   }
/*      */ 
/*      */   public void setXmpMetadata(byte[] xmpMetadata)
/*      */     throws IOException
/*      */   {
/*  895 */     PdfStream xmp = new PdfStream(xmpMetadata);
/*  896 */     xmp.put(PdfName.TYPE, PdfName.METADATA);
/*  897 */     xmp.put(PdfName.SUBTYPE, PdfName.XML);
/*  898 */     PdfEncryption crypto = this.writer.getEncryption();
/*  899 */     if ((crypto != null) && (!crypto.isMetadataEncrypted())) {
/*  900 */       PdfArray ar = new PdfArray();
/*  901 */       ar.add(PdfName.CRYPT);
/*  902 */       xmp.put(PdfName.FILTER, ar);
/*      */     }
/*  904 */     this.writer.addPageDictEntry(PdfName.METADATA, this.writer.addToBody(xmp).getIndirectReference());
/*      */   }
/*      */ 
/*      */   public boolean newPage()
/*      */   {
/*      */     try
/*      */     {
/*  915 */       flushFloatingElements();
/*      */     }
/*      */     catch (DocumentException de) {
/*  918 */       throw new ExceptionConverter(de);
/*      */     }
/*  920 */     this.lastElementType = -1;
/*  921 */     if (isPageEmpty()) {
/*  922 */       setNewPageSizeAndMargins();
/*  923 */       return false;
/*      */     }
/*  925 */     if ((!this.open) || (this.close)) {
/*  926 */       throw new RuntimeException(MessageLocalization.getComposedMessage("the.document.is.not.open", new Object[0]));
/*      */     }
/*  928 */     PdfPageEvent pageEvent = this.writer.getPageEvent();
/*  929 */     if (pageEvent != null) {
/*  930 */       pageEvent.onEndPage(this.writer, this);
/*      */     }
/*      */ 
/*  933 */     super.newPage();
/*      */ 
/*  936 */     this.indentation.imageIndentLeft = 0.0F;
/*  937 */     this.indentation.imageIndentRight = 0.0F;
/*      */     try
/*      */     {
/*  941 */       flushLines();
/*      */ 
/*  946 */       int rotation = this.pageSize.getRotation();
/*      */ 
/*  949 */       if (this.writer.isPdfIso()) {
/*  950 */         if ((this.thisBoxSize.containsKey("art")) && (this.thisBoxSize.containsKey("trim")))
/*  951 */           throw new PdfXConformanceException(MessageLocalization.getComposedMessage("only.one.of.artbox.or.trimbox.can.exist.in.the.page", new Object[0]));
/*  952 */         if ((!this.thisBoxSize.containsKey("art")) && (!this.thisBoxSize.containsKey("trim"))) {
/*  953 */           if (this.thisBoxSize.containsKey("crop"))
/*  954 */             this.thisBoxSize.put("trim", this.thisBoxSize.get("crop"));
/*      */           else {
/*  956 */             this.thisBoxSize.put("trim", new PdfRectangle(this.pageSize, this.pageSize.getRotation()));
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  961 */       this.pageResources.addDefaultColorDiff(this.writer.getDefaultColorspace());
/*  962 */       if (this.writer.isRgbTransparencyBlending()) {
/*  963 */         PdfDictionary dcs = new PdfDictionary();
/*  964 */         dcs.put(PdfName.CS, PdfName.DEVICERGB);
/*  965 */         this.pageResources.addDefaultColorDiff(dcs);
/*      */       }
/*  967 */       PdfDictionary resources = this.pageResources.getResources();
/*      */ 
/*  971 */       PdfPage page = new PdfPage(new PdfRectangle(this.pageSize, rotation), this.thisBoxSize, resources, rotation);
/*  972 */       if (isTagged(this.writer))
/*  973 */         page.put(PdfName.TABS, PdfName.S);
/*      */       else {
/*  975 */         page.put(PdfName.TABS, this.writer.getTabs());
/*      */       }
/*  977 */       page.putAll(this.writer.getPageDictEntries());
/*  978 */       this.writer.resetPageDictEntries();
/*      */ 
/*  983 */       if (this.pageAA != null) {
/*  984 */         page.put(PdfName.AA, this.writer.addToBody(this.pageAA).getIndirectReference());
/*  985 */         this.pageAA = null;
/*      */       }
/*      */ 
/*  989 */       if (this.annotationsImp.hasUnusedAnnotations()) {
/*  990 */         PdfArray array = this.annotationsImp.rotateAnnotations(this.writer, this.pageSize);
/*  991 */         if (array.size() != 0) {
/*  992 */           page.put(PdfName.ANNOTS, array);
/*      */         }
/*      */       }
/*      */ 
/*  996 */       if (isTagged(this.writer)) {
/*  997 */         page.put(PdfName.STRUCTPARENTS, new PdfNumber(getStructParentIndex(this.writer.getCurrentPage())));
/*      */       }
/*  999 */       if ((this.text.size() > this.textEmptySize) || (isTagged(this.writer)))
/* 1000 */         this.text.endText();
/*      */       else {
/* 1002 */         this.text = null;
/*      */       }
/* 1004 */       ArrayList mcBlocks = null;
/* 1005 */       if (isTagged(this.writer)) {
/* 1006 */         mcBlocks = this.writer.getDirectContent().saveMCBlocks();
/*      */       }
/* 1008 */       this.writer.add(page, new PdfContents(this.writer.getDirectContentUnder(), this.graphics, !isTagged(this.writer) ? this.text : null, this.writer.getDirectContent(), this.pageSize));
/*      */ 
/* 1010 */       initPage();
/*      */ 
/* 1012 */       if (isTagged(this.writer)) {
/* 1013 */         this.writer.getDirectContentUnder().restoreMCBlocks(mcBlocks);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (DocumentException de)
/*      */     {
/* 1019 */       throw new ExceptionConverter(de);
/*      */     }
/*      */     catch (IOException ioe) {
/* 1022 */       throw new ExceptionConverter(ioe);
/*      */     }
/* 1024 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean setPageSize(Rectangle pageSize)
/*      */   {
/* 1037 */     if ((this.writer != null) && (this.writer.isPaused())) {
/* 1038 */       return false;
/*      */     }
/* 1040 */     this.nextPageSize = new Rectangle(pageSize);
/* 1041 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean setMargins(float marginLeft, float marginRight, float marginTop, float marginBottom)
/*      */   {
/* 1069 */     if ((this.writer != null) && (this.writer.isPaused())) {
/* 1070 */       return false;
/*      */     }
/* 1072 */     this.nextMarginLeft = marginLeft;
/* 1073 */     this.nextMarginRight = marginRight;
/* 1074 */     this.nextMarginTop = marginTop;
/* 1075 */     this.nextMarginBottom = marginBottom;
/* 1076 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean setMarginMirroring(boolean MarginMirroring)
/*      */   {
/* 1086 */     if ((this.writer != null) && (this.writer.isPaused())) {
/* 1087 */       return false;
/*      */     }
/* 1089 */     return super.setMarginMirroring(MarginMirroring);
/*      */   }
/*      */ 
/*      */   public boolean setMarginMirroringTopBottom(boolean MarginMirroringTopBottom)
/*      */   {
/* 1098 */     if ((this.writer != null) && (this.writer.isPaused())) {
/* 1099 */       return false;
/*      */     }
/* 1101 */     return super.setMarginMirroringTopBottom(MarginMirroringTopBottom);
/*      */   }
/*      */ 
/*      */   public void setPageCount(int pageN)
/*      */   {
/* 1113 */     if ((this.writer != null) && (this.writer.isPaused())) {
/* 1114 */       return;
/*      */     }
/* 1116 */     super.setPageCount(pageN);
/*      */   }
/*      */ 
/*      */   public void resetPageCount()
/*      */   {
/* 1126 */     if ((this.writer != null) && (this.writer.isPaused())) {
/* 1127 */       return;
/*      */     }
/* 1129 */     super.resetPageCount();
/*      */   }
/*      */ 
/*      */   protected void initPage()
/*      */     throws DocumentException
/*      */   {
/* 1145 */     this.pageN += 1;
/*      */ 
/* 1148 */     this.annotationsImp.resetAnnotations();
/* 1149 */     this.pageResources = new PageResources();
/*      */ 
/* 1151 */     this.writer.resetContent();
/* 1152 */     if (isTagged(this.writer)) {
/* 1153 */       this.graphics = this.writer.getDirectContentUnder().getDuplicate();
/* 1154 */       this.writer.getDirectContent().duplicatedFrom = this.graphics;
/*      */     } else {
/* 1156 */       this.graphics = new PdfContentByte(this.writer);
/*      */     }
/*      */ 
/* 1159 */     setNewPageSizeAndMargins();
/* 1160 */     this.imageEnd = -1.0F;
/* 1161 */     this.indentation.imageIndentRight = 0.0F;
/* 1162 */     this.indentation.imageIndentLeft = 0.0F;
/* 1163 */     this.indentation.indentBottom = 0.0F;
/* 1164 */     this.indentation.indentTop = 0.0F;
/* 1165 */     this.currentHeight = 0.0F;
/*      */ 
/* 1168 */     this.thisBoxSize = new HashMap(this.boxSize);
/* 1169 */     if ((this.pageSize.getBackgroundColor() != null) || (this.pageSize.hasBorders()) || (this.pageSize.getBorderColor() != null))
/*      */     {
/* 1172 */       add(this.pageSize);
/*      */     }
/*      */ 
/* 1175 */     float oldleading = this.leading;
/* 1176 */     int oldAlignment = this.alignment;
/* 1177 */     this.pageEmpty = true;
/*      */     try
/*      */     {
/* 1180 */       if (this.imageWait != null) {
/* 1181 */         add(this.imageWait);
/* 1182 */         this.imageWait = null;
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1186 */       throw new ExceptionConverter(e);
/*      */     }
/* 1188 */     this.leading = oldleading;
/* 1189 */     this.alignment = oldAlignment;
/* 1190 */     carriageReturn();
/*      */ 
/* 1192 */     PdfPageEvent pageEvent = this.writer.getPageEvent();
/* 1193 */     if (pageEvent != null) {
/* 1194 */       if (this.firstPageEvent) {
/* 1195 */         pageEvent.onOpenDocument(this.writer, this);
/*      */       }
/* 1197 */       pageEvent.onStartPage(this.writer, this);
/*      */     }
/* 1199 */     this.firstPageEvent = false;
/*      */   }
/*      */ 
/*      */   protected void newLine()
/*      */     throws DocumentException
/*      */   {
/* 1212 */     this.lastElementType = -1;
/* 1213 */     carriageReturn();
/* 1214 */     if ((this.lines != null) && (!this.lines.isEmpty())) {
/* 1215 */       this.lines.add(this.line);
/* 1216 */       this.currentHeight += this.line.height();
/*      */     }
/* 1218 */     this.line = new PdfLine(indentLeft(), indentRight(), this.alignment, this.leading);
/*      */   }
/*      */ 
/*      */   protected float calculateLineHeight()
/*      */   {
/* 1229 */     float tempHeight = this.line.height();
/*      */ 
/* 1231 */     if (tempHeight != this.leading) {
/* 1232 */       tempHeight += this.leading;
/*      */     }
/*      */ 
/* 1235 */     return tempHeight;
/*      */   }
/*      */ 
/*      */   protected void carriageReturn()
/*      */   {
/* 1244 */     if (this.lines == null) {
/* 1245 */       this.lines = new ArrayList();
/*      */     }
/*      */ 
/* 1248 */     if ((this.line != null) && (this.line.size() > 0))
/*      */     {
/* 1250 */       if (this.currentHeight + calculateLineHeight() > indentTop() - indentBottom())
/*      */       {
/* 1254 */         PdfLine overflowLine = this.line;
/* 1255 */         this.line = null;
/* 1256 */         newPage();
/* 1257 */         this.line = overflowLine;
/*      */ 
/* 1259 */         overflowLine.left = indentLeft();
/*      */       }
/* 1261 */       this.currentHeight += this.line.height();
/* 1262 */       this.lines.add(this.line);
/* 1263 */       this.pageEmpty = false;
/*      */     }
/* 1265 */     if ((this.imageEnd > -1.0F) && (this.currentHeight > this.imageEnd)) {
/* 1266 */       this.imageEnd = -1.0F;
/* 1267 */       this.indentation.imageIndentRight = 0.0F;
/* 1268 */       this.indentation.imageIndentLeft = 0.0F;
/*      */     }
/*      */ 
/* 1271 */     this.line = new PdfLine(indentLeft(), indentRight(), this.alignment, this.leading);
/*      */   }
/*      */ 
/*      */   public float getVerticalPosition(boolean ensureNewLine)
/*      */   {
/* 1283 */     if (ensureNewLine) {
/* 1284 */       ensureNewLine();
/*      */     }
/* 1286 */     return top() - this.currentHeight - this.indentation.indentTop;
/*      */   }
/*      */ 
/*      */   protected void ensureNewLine()
/*      */   {
/*      */     try
/*      */     {
/* 1297 */       if ((this.lastElementType == 11) || (this.lastElementType == 10))
/*      */       {
/* 1299 */         newLine();
/* 1300 */         flushLines();
/*      */       }
/*      */     } catch (DocumentException ex) {
/* 1303 */       throw new ExceptionConverter(ex);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected float flushLines()
/*      */     throws DocumentException
/*      */   {
/* 1315 */     if (this.lines == null) {
/* 1316 */       return 0.0F;
/*      */     }
/*      */ 
/* 1319 */     if ((this.line != null) && (this.line.size() > 0)) {
/* 1320 */       this.lines.add(this.line);
/* 1321 */       this.line = new PdfLine(indentLeft(), indentRight(), this.alignment, this.leading);
/*      */     }
/*      */ 
/* 1325 */     if (this.lines.isEmpty()) {
/* 1326 */       return 0.0F;
/*      */     }
/*      */ 
/* 1330 */     Object[] currentValues = new Object[2];
/* 1331 */     PdfFont currentFont = null;
/* 1332 */     float displacement = 0.0F;
/* 1333 */     Float lastBaseFactor = new Float(0.0F);
/* 1334 */     currentValues[1] = lastBaseFactor;
/*      */ 
/* 1336 */     for (PdfLine l : this.lines) {
/* 1337 */       float moveTextX = l.indentLeft() - indentLeft() + this.indentation.indentLeft + this.indentation.listIndentLeft + this.indentation.sectionIndentLeft;
/* 1338 */       this.text.moveText(moveTextX, -l.height());
/*      */ 
/* 1340 */       l.flush();
/*      */ 
/* 1342 */       if (l.listSymbol() != null) {
/* 1343 */         ListLabel lbl = null;
/* 1344 */         Chunk symbol = l.listSymbol();
/* 1345 */         if (isTagged(this.writer)) {
/* 1346 */           lbl = l.listItem().getListLabel();
/* 1347 */           this.graphics.openMCBlock(lbl);
/* 1348 */           symbol = new Chunk(symbol);
/* 1349 */           symbol.setRole(null);
/*      */         }
/* 1351 */         ColumnText.showTextAligned(this.graphics, 0, new Phrase(symbol), this.text.getXTLM() - l.listIndent(), this.text.getYTLM(), 0.0F);
/* 1352 */         if (lbl != null) {
/* 1353 */           this.graphics.closeMCBlock(lbl);
/*      */         }
/*      */       }
/*      */ 
/* 1357 */       currentValues[0] = currentFont;
/*      */ 
/* 1359 */       if ((isTagged(this.writer)) && (l.listItem() != null)) {
/* 1360 */         this.text.openMCBlock(l.listItem().getListBody());
/*      */       }
/* 1362 */       writeLineToContent(l, this.text, this.graphics, currentValues, this.writer.getSpaceCharRatio());
/*      */ 
/* 1364 */       currentFont = (PdfFont)currentValues[0];
/* 1365 */       displacement += l.height();
/* 1366 */       this.text.moveText(-moveTextX, 0.0F);
/*      */     }
/*      */ 
/* 1369 */     this.lines = new ArrayList();
/* 1370 */     return displacement;
/*      */   }
/*      */ 
/*      */   float writeLineToContent(PdfLine line, PdfContentByte text, PdfContentByte graphics, Object[] currentValues, float ratio)
/*      */     throws DocumentException
/*      */   {
/* 1390 */     PdfFont currentFont = (PdfFont)currentValues[0];
/* 1391 */     float lastBaseFactor = ((Float)currentValues[1]).floatValue();
/*      */ 
/* 1396 */     float hangingCorrection = 0.0F;
/* 1397 */     float hScale = 1.0F;
/* 1398 */     float lastHScale = (0.0F / 0.0F);
/* 1399 */     float baseWordSpacing = 0.0F;
/* 1400 */     float baseCharacterSpacing = 0.0F;
/* 1401 */     float glueWidth = 0.0F;
/* 1402 */     float lastX = text.getXTLM() + line.getOriginalWidth();
/* 1403 */     int numberOfSpaces = line.numberOfSpaces();
/* 1404 */     int lineLen = line.getLineLengthUtf32();
/*      */ 
/* 1406 */     boolean isJustified = (line.hasToBeJustified()) && ((numberOfSpaces != 0) || (lineLen > 1));
/* 1407 */     int separatorCount = line.getSeparatorCount();
/* 1408 */     if (separatorCount > 0) {
/* 1409 */       glueWidth = line.widthLeft() / separatorCount;
/*      */     }
/* 1411 */     else if ((isJustified) && (separatorCount == 0)) {
/* 1412 */       if ((line.isNewlineSplit()) && (line.widthLeft() >= lastBaseFactor * (ratio * numberOfSpaces + lineLen - 1.0F))) {
/* 1413 */         if (line.isRTL()) {
/* 1414 */           text.moveText(line.widthLeft() - lastBaseFactor * (ratio * numberOfSpaces + lineLen - 1.0F), 0.0F);
/*      */         }
/* 1416 */         baseWordSpacing = ratio * lastBaseFactor;
/* 1417 */         baseCharacterSpacing = lastBaseFactor;
/*      */       }
/*      */       else {
/* 1420 */         float width = line.widthLeft();
/* 1421 */         PdfChunk last = line.getChunk(line.size() - 1);
/* 1422 */         if (last != null) {
/* 1423 */           String s = last.toString();
/*      */           char c;
/* 1425 */           if ((s.length() > 0) && (".,;:'".indexOf(c = s.charAt(s.length() - 1)) >= 0)) {
/* 1426 */             float oldWidth = width;
/* 1427 */             width += last.font().width(c) * 0.4F;
/* 1428 */             hangingCorrection = width - oldWidth;
/*      */           }
/*      */         }
/* 1431 */         float baseFactor = width / (ratio * numberOfSpaces + lineLen - 1.0F);
/* 1432 */         baseWordSpacing = ratio * baseFactor;
/* 1433 */         baseCharacterSpacing = baseFactor;
/* 1434 */         lastBaseFactor = baseFactor;
/*      */       }
/*      */     }
/* 1437 */     else if ((line.alignment == 0) || (line.alignment == -1)) {
/* 1438 */       lastX -= line.widthLeft();
/*      */     }
/*      */ 
/* 1441 */     int lastChunkStroke = line.getLastStrokeChunk();
/* 1442 */     int chunkStrokeIdx = 0;
/* 1443 */     float xMarker = text.getXTLM();
/* 1444 */     float baseXMarker = xMarker;
/* 1445 */     float yMarker = text.getYTLM();
/* 1446 */     boolean adjustMatrix = false;
/* 1447 */     float tabPosition = 0.0F;
/*      */ 
/* 1450 */     for (Iterator j = line.iterator(); j.hasNext(); ) {
/* 1451 */       PdfChunk chunk = (PdfChunk)j.next();
/* 1452 */       if ((isTagged(this.writer)) && (chunk.accessibleElement != null)) {
/* 1453 */         text.openMCBlock(chunk.accessibleElement);
/*      */       }
/* 1455 */       BaseColor color = chunk.color();
/* 1456 */       float fontSize = chunk.font().size();
/*      */       float descender;
/*      */       float ascender;
/*      */       float descender;
/* 1459 */       if (chunk.isImage()) {
/* 1460 */         float ascender = chunk.height();
/* 1461 */         descender = 0.0F;
/*      */       } else {
/* 1463 */         ascender = chunk.font().getFont().getFontDescriptor(1, fontSize);
/* 1464 */         descender = chunk.font().getFont().getFontDescriptor(3, fontSize);
/*      */       }
/* 1466 */       hScale = 1.0F;
/*      */ 
/* 1468 */       if (chunkStrokeIdx <= lastChunkStroke)
/*      */       {
/*      */         float width;
/*      */         float width;
/* 1470 */         if (isJustified) {
/* 1471 */           width = chunk.getWidthCorrected(baseCharacterSpacing, baseWordSpacing);
/*      */         }
/*      */         else {
/* 1474 */           width = chunk.width();
/*      */         }
/* 1476 */         if (chunk.isStroked()) {
/* 1477 */           PdfChunk nextChunk = line.getChunk(chunkStrokeIdx + 1);
/* 1478 */           if (chunk.isSeparator()) {
/* 1479 */             width = glueWidth;
/* 1480 */             Object[] sep = (Object[])chunk.getAttribute("SEPARATOR");
/* 1481 */             DrawInterface di = (DrawInterface)sep[0];
/* 1482 */             Boolean vertical = (Boolean)sep[1];
/* 1483 */             if (vertical.booleanValue()) {
/* 1484 */               di.draw(graphics, baseXMarker, yMarker + descender, baseXMarker + line.getOriginalWidth(), ascender - descender, yMarker);
/*      */             }
/*      */             else {
/* 1487 */               di.draw(graphics, xMarker, yMarker + descender, xMarker + width, ascender - descender, yMarker);
/*      */             }
/*      */           }
/* 1490 */           if (chunk.isTab()) {
/* 1491 */             if (chunk.isAttribute("TABSETTINGS")) {
/* 1492 */               TabStop tabStop = chunk.getTabStop();
/* 1493 */               if (tabStop != null) {
/* 1494 */                 tabPosition = tabStop.getPosition() + baseXMarker;
/* 1495 */                 if (tabStop.getLeader() != null)
/* 1496 */                   tabStop.getLeader().draw(graphics, xMarker, yMarker + descender, tabPosition, ascender - descender, yMarker);
/*      */               } else {
/* 1498 */                 tabPosition = xMarker;
/*      */               }
/*      */             }
/*      */             else {
/* 1502 */               Object[] tab = (Object[])chunk.getAttribute("TAB");
/* 1503 */               DrawInterface di = (DrawInterface)tab[0];
/* 1504 */               tabPosition = ((Float)tab[1]).floatValue() + ((Float)tab[3]).floatValue();
/* 1505 */               if (tabPosition > xMarker) {
/* 1506 */                 di.draw(graphics, xMarker, yMarker + descender, tabPosition, ascender - descender, yMarker);
/*      */               }
/*      */             }
/* 1509 */             float tmp = xMarker;
/* 1510 */             xMarker = tabPosition;
/* 1511 */             tabPosition = tmp;
/*      */           }
/* 1513 */           if (chunk.isAttribute("BACKGROUND")) {
/* 1514 */             boolean inText = graphics.getInText();
/* 1515 */             if ((inText) && (isTagged(this.writer))) {
/* 1516 */               graphics.endText();
/*      */             }
/* 1518 */             float subtract = lastBaseFactor;
/* 1519 */             if ((nextChunk != null) && (nextChunk.isAttribute("BACKGROUND")))
/* 1520 */               subtract = 0.0F;
/* 1521 */             if (nextChunk == null)
/* 1522 */               subtract += hangingCorrection;
/* 1523 */             Object[] bgr = (Object[])chunk.getAttribute("BACKGROUND");
/* 1524 */             graphics.setColorFill((BaseColor)bgr[0]);
/* 1525 */             float[] extra = (float[])bgr[1];
/* 1526 */             graphics.rectangle(xMarker - extra[0], yMarker + descender - extra[1] + chunk.getTextRise(), width - subtract + extra[0] + extra[2], ascender - descender + extra[1] + extra[3]);
/*      */ 
/* 1530 */             graphics.fill();
/* 1531 */             graphics.setGrayFill(0.0F);
/* 1532 */             if ((inText) && (isTagged(this.writer))) {
/* 1533 */               graphics.beginText(true);
/*      */             }
/*      */           }
/* 1536 */           if ((chunk.isAttribute("UNDERLINE")) && (!chunk.isNewlineSplit())) {
/* 1537 */             boolean inText = graphics.getInText();
/* 1538 */             if ((inText) && (isTagged(this.writer))) {
/* 1539 */               graphics.endText();
/*      */             }
/* 1541 */             float subtract = lastBaseFactor;
/* 1542 */             if ((nextChunk != null) && (nextChunk.isAttribute("UNDERLINE")))
/* 1543 */               subtract = 0.0F;
/* 1544 */             if (nextChunk == null)
/* 1545 */               subtract += hangingCorrection;
/* 1546 */             Object[][] unders = (Object[][])chunk.getAttribute("UNDERLINE");
/* 1547 */             BaseColor scolor = null;
/* 1548 */             for (int k = 0; k < unders.length; k++) {
/* 1549 */               Object[] obj = unders[k];
/* 1550 */               scolor = (BaseColor)obj[0];
/* 1551 */               float[] ps = (float[])obj[1];
/* 1552 */               if (scolor == null)
/* 1553 */                 scolor = color;
/* 1554 */               if (scolor != null)
/* 1555 */                 graphics.setColorStroke(scolor);
/* 1556 */               graphics.setLineWidth(ps[0] + fontSize * ps[1]);
/* 1557 */               float shift = ps[2] + fontSize * ps[3];
/* 1558 */               int cap2 = (int)ps[4];
/* 1559 */               if (cap2 != 0)
/* 1560 */                 graphics.setLineCap(cap2);
/* 1561 */               graphics.moveTo(xMarker, yMarker + shift);
/* 1562 */               graphics.lineTo(xMarker + width - subtract, yMarker + shift);
/* 1563 */               graphics.stroke();
/* 1564 */               if (scolor != null)
/* 1565 */                 graphics.resetGrayStroke();
/* 1566 */               if (cap2 != 0)
/* 1567 */                 graphics.setLineCap(0);
/*      */             }
/* 1569 */             graphics.setLineWidth(1.0F);
/* 1570 */             if ((inText) && (isTagged(this.writer))) {
/* 1571 */               graphics.beginText(true);
/*      */             }
/*      */           }
/* 1574 */           if (chunk.isAttribute("ACTION")) {
/* 1575 */             float subtract = lastBaseFactor;
/* 1576 */             if ((nextChunk != null) && (nextChunk.isAttribute("ACTION")))
/* 1577 */               subtract = 0.0F;
/* 1578 */             if (nextChunk == null)
/* 1579 */               subtract += hangingCorrection;
/* 1580 */             PdfAnnotation annot = null;
/* 1581 */             if (chunk.isImage()) {
/* 1582 */               annot = new PdfAnnotation(this.writer, xMarker, yMarker + chunk.getImageOffsetY(), xMarker + width - subtract, yMarker + chunk.getImageHeight() + chunk.getImageOffsetY(), (PdfAction)chunk.getAttribute("ACTION"));
/*      */             }
/*      */             else {
/* 1585 */               annot = new PdfAnnotation(this.writer, xMarker, yMarker + descender + chunk.getTextRise(), xMarker + width - subtract, yMarker + ascender + chunk.getTextRise(), (PdfAction)chunk.getAttribute("ACTION"));
/*      */             }
/* 1587 */             text.addAnnotation(annot, true);
/* 1588 */             if ((isTagged(this.writer)) && (chunk.accessibleElement != null)) {
/* 1589 */               PdfStructureElement strucElem = (PdfStructureElement)this.structElements.get(chunk.accessibleElement.getId());
/* 1590 */               if (strucElem != null) {
/* 1591 */                 int structParent = getStructParentIndex(annot);
/* 1592 */                 annot.put(PdfName.STRUCTPARENT, new PdfNumber(structParent));
/* 1593 */                 strucElem.setAnnotation(annot, this.writer.getCurrentPage());
/* 1594 */                 this.writer.getStructureTreeRoot().setAnnotationMark(structParent, strucElem.getReference());
/*      */               }
/*      */             }
/*      */           }
/* 1598 */           if (chunk.isAttribute("REMOTEGOTO")) {
/* 1599 */             float subtract = lastBaseFactor;
/* 1600 */             if ((nextChunk != null) && (nextChunk.isAttribute("REMOTEGOTO")))
/* 1601 */               subtract = 0.0F;
/* 1602 */             if (nextChunk == null)
/* 1603 */               subtract += hangingCorrection;
/* 1604 */             Object[] obj = (Object[])chunk.getAttribute("REMOTEGOTO");
/* 1605 */             String filename = (String)obj[0];
/* 1606 */             if ((obj[1] instanceof String))
/* 1607 */               remoteGoto(filename, (String)obj[1], xMarker, yMarker + descender + chunk.getTextRise(), xMarker + width - subtract, yMarker + ascender + chunk.getTextRise());
/*      */             else
/* 1609 */               remoteGoto(filename, ((Integer)obj[1]).intValue(), xMarker, yMarker + descender + chunk.getTextRise(), xMarker + width - subtract, yMarker + ascender + chunk.getTextRise());
/*      */           }
/* 1611 */           if (chunk.isAttribute("LOCALGOTO")) {
/* 1612 */             float subtract = lastBaseFactor;
/* 1613 */             if ((nextChunk != null) && (nextChunk.isAttribute("LOCALGOTO")))
/* 1614 */               subtract = 0.0F;
/* 1615 */             if (nextChunk == null)
/* 1616 */               subtract += hangingCorrection;
/* 1617 */             localGoto((String)chunk.getAttribute("LOCALGOTO"), xMarker, yMarker, xMarker + width - subtract, yMarker + fontSize);
/*      */           }
/* 1619 */           if (chunk.isAttribute("LOCALDESTINATION"))
/*      */           {
/* 1625 */             localDestination((String)chunk.getAttribute("LOCALDESTINATION"), new PdfDestination(0, xMarker, yMarker + fontSize, 0.0F));
/*      */           }
/* 1627 */           if (chunk.isAttribute("GENERICTAG")) {
/* 1628 */             float subtract = lastBaseFactor;
/* 1629 */             if ((nextChunk != null) && (nextChunk.isAttribute("GENERICTAG")))
/* 1630 */               subtract = 0.0F;
/* 1631 */             if (nextChunk == null)
/* 1632 */               subtract += hangingCorrection;
/* 1633 */             Rectangle rect = new Rectangle(xMarker, yMarker, xMarker + width - subtract, yMarker + fontSize);
/* 1634 */             PdfPageEvent pev = this.writer.getPageEvent();
/* 1635 */             if (pev != null)
/* 1636 */               pev.onGenericTag(this.writer, this, rect, (String)chunk.getAttribute("GENERICTAG"));
/*      */           }
/* 1638 */           if (chunk.isAttribute("PDFANNOTATION")) {
/* 1639 */             float subtract = lastBaseFactor;
/* 1640 */             if ((nextChunk != null) && (nextChunk.isAttribute("PDFANNOTATION")))
/* 1641 */               subtract = 0.0F;
/* 1642 */             if (nextChunk == null)
/* 1643 */               subtract += hangingCorrection;
/* 1644 */             PdfAnnotation annot = PdfFormField.shallowDuplicate((PdfAnnotation)chunk.getAttribute("PDFANNOTATION"));
/* 1645 */             annot.put(PdfName.RECT, new PdfRectangle(xMarker, yMarker + descender, xMarker + width - subtract, yMarker + ascender));
/* 1646 */             text.addAnnotation(annot, true);
/*      */           }
/* 1648 */           float[] params = (float[])chunk.getAttribute("SKEW");
/* 1649 */           Float hs = (Float)chunk.getAttribute("HSCALE");
/* 1650 */           if ((params != null) || (hs != null)) {
/* 1651 */             float b = 0.0F; float c = 0.0F;
/* 1652 */             if (params != null) {
/* 1653 */               b = params[0];
/* 1654 */               c = params[1];
/*      */             }
/* 1656 */             if (hs != null)
/* 1657 */               hScale = hs.floatValue();
/* 1658 */             text.setTextMatrix(hScale, b, c, 1.0F, xMarker, yMarker);
/*      */           }
/* 1660 */           if (!isJustified) {
/* 1661 */             if (chunk.isAttribute("WORD_SPACING")) {
/* 1662 */               Float ws = (Float)chunk.getAttribute("WORD_SPACING");
/* 1663 */               text.setWordSpacing(ws.floatValue());
/*      */             }
/*      */ 
/* 1666 */             if (chunk.isAttribute("CHAR_SPACING")) {
/* 1667 */               Float cs = (Float)chunk.getAttribute("CHAR_SPACING");
/* 1668 */               text.setCharacterSpacing(cs.floatValue());
/*      */             }
/*      */           }
/* 1671 */           if (chunk.isImage()) {
/* 1672 */             Image image = chunk.getImage();
/* 1673 */             width = chunk.getImageWidth();
/* 1674 */             float[] matrix = image.matrix(chunk.getImageScalePercentage());
/* 1675 */             matrix[4] = (xMarker + chunk.getImageOffsetX() - matrix[4]);
/* 1676 */             matrix[5] = (yMarker + chunk.getImageOffsetY() - matrix[5]);
/* 1677 */             graphics.addImage(image, matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);
/* 1678 */             text.moveText(xMarker + lastBaseFactor + chunk.getImageWidth() - text.getXTLM(), 0.0F);
/*      */           }
/*      */         }
/*      */ 
/* 1682 */         xMarker += width;
/* 1683 */         chunkStrokeIdx++;
/*      */       }
/*      */ 
/* 1686 */       if ((!chunk.isImage()) && (chunk.font().compareTo(currentFont) != 0)) {
/* 1687 */         currentFont = chunk.font();
/* 1688 */         text.setFontAndSize(currentFont.getFont(), currentFont.size());
/*      */       }
/* 1690 */       float rise = 0.0F;
/* 1691 */       Object[] textRender = (Object[])chunk.getAttribute("TEXTRENDERMODE");
/* 1692 */       int tr = 0;
/* 1693 */       float strokeWidth = 1.0F;
/* 1694 */       BaseColor strokeColor = null;
/* 1695 */       Float fr = (Float)chunk.getAttribute("SUBSUPSCRIPT");
/* 1696 */       if (textRender != null) {
/* 1697 */         tr = ((Integer)textRender[0]).intValue() & 0x3;
/* 1698 */         if (tr != 0)
/* 1699 */           text.setTextRenderingMode(tr);
/* 1700 */         if ((tr == 1) || (tr == 2)) {
/* 1701 */           strokeWidth = ((Float)textRender[1]).floatValue();
/* 1702 */           if (strokeWidth != 1.0F)
/* 1703 */             text.setLineWidth(strokeWidth);
/* 1704 */           strokeColor = (BaseColor)textRender[2];
/* 1705 */           if (strokeColor == null)
/* 1706 */             strokeColor = color;
/* 1707 */           if (strokeColor != null)
/* 1708 */             text.setColorStroke(strokeColor);
/*      */         }
/*      */       }
/* 1711 */       if (fr != null)
/* 1712 */         rise = fr.floatValue();
/* 1713 */       if (color != null)
/* 1714 */         text.setColorFill(color);
/* 1715 */       if (rise != 0.0F)
/* 1716 */         text.setTextRise(rise);
/* 1717 */       if (chunk.isImage()) {
/* 1718 */         adjustMatrix = true;
/*      */       }
/* 1720 */       else if (chunk.isHorizontalSeparator()) {
/* 1721 */         PdfTextArray array = new PdfTextArray();
/* 1722 */         array.add(-glueWidth * 1000.0F / chunk.font.size() / hScale);
/* 1723 */         text.showText(array);
/*      */       }
/* 1725 */       else if ((chunk.isTab()) && (tabPosition != xMarker)) {
/* 1726 */         PdfTextArray array = new PdfTextArray();
/* 1727 */         array.add((tabPosition - xMarker) * 1000.0F / chunk.font.size() / hScale);
/* 1728 */         text.showText(array);
/*      */       }
/* 1732 */       else if ((isJustified) && (numberOfSpaces > 0) && (chunk.isSpecialEncoding())) {
/* 1733 */         if (hScale != lastHScale) {
/* 1734 */           lastHScale = hScale;
/* 1735 */           text.setWordSpacing(baseWordSpacing / hScale);
/* 1736 */           text.setCharacterSpacing(baseCharacterSpacing / hScale + text.getCharacterSpacing());
/*      */         }
/* 1738 */         String s = chunk.toString();
/* 1739 */         int idx = s.indexOf(' ');
/* 1740 */         if (idx < 0) {
/* 1741 */           text.showText(s);
/*      */         } else {
/* 1743 */           float spaceCorrection = -baseWordSpacing * 1000.0F / chunk.font.size() / hScale;
/* 1744 */           PdfTextArray textArray = new PdfTextArray(s.substring(0, idx));
/* 1745 */           int lastIdx = idx;
/* 1746 */           while ((idx = s.indexOf(' ', lastIdx + 1)) >= 0) {
/* 1747 */             textArray.add(spaceCorrection);
/* 1748 */             textArray.add(s.substring(lastIdx, idx));
/* 1749 */             lastIdx = idx;
/*      */           }
/* 1751 */           textArray.add(spaceCorrection);
/* 1752 */           textArray.add(s.substring(lastIdx));
/* 1753 */           text.showText(textArray);
/*      */         }
/*      */       }
/*      */       else {
/* 1757 */         if ((isJustified) && (hScale != lastHScale)) {
/* 1758 */           lastHScale = hScale;
/* 1759 */           text.setWordSpacing(baseWordSpacing / hScale);
/* 1760 */           text.setCharacterSpacing(baseCharacterSpacing / hScale + text.getCharacterSpacing());
/*      */         }
/* 1762 */         text.showText(chunk.toString());
/*      */       }
/*      */ 
/* 1765 */       if (rise != 0.0F)
/* 1766 */         text.setTextRise(0.0F);
/* 1767 */       if (color != null)
/* 1768 */         text.resetRGBColorFill();
/* 1769 */       if (tr != 0)
/* 1770 */         text.setTextRenderingMode(0);
/* 1771 */       if (strokeColor != null)
/* 1772 */         text.resetRGBColorStroke();
/* 1773 */       if (strokeWidth != 1.0F)
/* 1774 */         text.setLineWidth(1.0F);
/* 1775 */       if ((chunk.isAttribute("SKEW")) || (chunk.isAttribute("HSCALE"))) {
/* 1776 */         adjustMatrix = true;
/* 1777 */         text.setTextMatrix(xMarker, yMarker);
/*      */       }
/* 1779 */       if (!isJustified) {
/* 1780 */         if (chunk.isAttribute("CHAR_SPACING")) {
/* 1781 */           text.setCharacterSpacing(baseCharacterSpacing);
/*      */         }
/* 1783 */         if (chunk.isAttribute("WORD_SPACING")) {
/* 1784 */           text.setWordSpacing(baseWordSpacing);
/*      */         }
/*      */       }
/* 1787 */       if ((isTagged(this.writer)) && (chunk.accessibleElement != null)) {
/* 1788 */         text.closeMCBlock(chunk.accessibleElement);
/*      */       }
/*      */     }
/*      */ 
/* 1792 */     if (isJustified) {
/* 1793 */       text.setWordSpacing(0.0F);
/* 1794 */       text.setCharacterSpacing(0.0F);
/* 1795 */       if (line.isNewlineSplit())
/* 1796 */         lastBaseFactor = 0.0F;
/*      */     }
/* 1798 */     if (adjustMatrix)
/* 1799 */       text.moveText(baseXMarker - text.getXTLM(), 0.0F);
/* 1800 */     currentValues[0] = currentFont;
/* 1801 */     currentValues[1] = new Float(lastBaseFactor);
/* 1802 */     return lastX;
/*      */   }
/*      */ 
/*      */   protected float indentLeft()
/*      */   {
/* 1847 */     return left(this.indentation.indentLeft + this.indentation.listIndentLeft + this.indentation.imageIndentLeft + this.indentation.sectionIndentLeft);
/*      */   }
/*      */ 
/*      */   protected float indentRight()
/*      */   {
/* 1857 */     return right(this.indentation.indentRight + this.indentation.sectionIndentRight + this.indentation.imageIndentRight);
/*      */   }
/*      */ 
/*      */   protected float indentTop()
/*      */   {
/* 1867 */     return top(this.indentation.indentTop);
/*      */   }
/*      */ 
/*      */   float indentBottom()
/*      */   {
/* 1877 */     return bottom(this.indentation.indentBottom);
/*      */   }
/*      */ 
/*      */   protected void addSpacing(float extraspace, float oldleading, Font f)
/*      */   {
/* 1885 */     if (extraspace == 0.0F) return;
/* 1886 */     if (this.pageEmpty) return;
/*      */ 
/* 1888 */     if (this.currentHeight + calculateLineHeight() > indentTop() - indentBottom()) {
/* 1889 */       newPage();
/* 1890 */       return;
/*      */     }
/*      */ 
/* 1893 */     this.leading = extraspace;
/* 1894 */     carriageReturn();
/* 1895 */     if ((f.isUnderlined()) || (f.isStrikethru())) {
/* 1896 */       f = new Font(f);
/* 1897 */       int style = f.getStyle();
/* 1898 */       style &= -5;
/* 1899 */       style &= -9;
/* 1900 */       f.setStyle(style);
/*      */     }
/* 1902 */     Chunk space = new Chunk(" ", f);
/* 1903 */     space.process(this);
/* 1904 */     carriageReturn();
/* 1905 */     this.leading = oldleading;
/*      */   }
/*      */ 
/*      */   PdfInfo getInfo()
/*      */   {
/* 1920 */     return this.info;
/*      */   }
/*      */ 
/*      */   PdfCatalog getCatalog(PdfIndirectReference pages)
/*      */   {
/* 1931 */     PdfCatalog catalog = new PdfCatalog(pages, this.writer);
/*      */ 
/* 1934 */     if (this.rootOutline.getKids().size() > 0) {
/* 1935 */       catalog.put(PdfName.PAGEMODE, PdfName.USEOUTLINES);
/* 1936 */       catalog.put(PdfName.OUTLINES, this.rootOutline.indirectReference());
/*      */     }
/*      */ 
/* 1940 */     this.writer.getPdfVersion().addToCatalog(catalog);
/*      */ 
/* 1943 */     this.viewerPreferences.addToCatalog(catalog);
/*      */ 
/* 1946 */     if (this.pageLabels != null) {
/* 1947 */       catalog.put(PdfName.PAGELABELS, this.pageLabels.getDictionary(this.writer));
/*      */     }
/*      */ 
/* 1951 */     catalog.addNames(this.localDestinations, getDocumentLevelJS(), this.documentFileAttachment, this.writer);
/*      */ 
/* 1954 */     if (this.openActionName != null) {
/* 1955 */       PdfAction action = getLocalGotoAction(this.openActionName);
/* 1956 */       catalog.setOpenAction(action);
/*      */     }
/* 1958 */     else if (this.openActionAction != null) {
/* 1959 */       catalog.setOpenAction(this.openActionAction);
/* 1960 */     }if (this.additionalActions != null) {
/* 1961 */       catalog.setAdditionalActions(this.additionalActions);
/*      */     }
/*      */ 
/* 1965 */     if (this.collection != null) {
/* 1966 */       catalog.put(PdfName.COLLECTION, this.collection);
/*      */     }
/*      */ 
/* 1970 */     if (this.annotationsImp.hasValidAcroForm()) {
/*      */       try {
/* 1972 */         catalog.put(PdfName.ACROFORM, this.writer.addToBody(this.annotationsImp.getAcroForm()).getIndirectReference());
/*      */       }
/*      */       catch (IOException e) {
/* 1975 */         throw new ExceptionConverter(e);
/*      */       }
/*      */     }
/*      */ 
/* 1979 */     if (this.language != null) {
/* 1980 */       catalog.put(PdfName.LANG, this.language);
/*      */     }
/*      */ 
/* 1983 */     return catalog;
/*      */   }
/*      */ 
/*      */   void addOutline(PdfOutline outline, String name)
/*      */   {
/* 2000 */     localDestination(name, outline.getPdfDestination());
/*      */   }
/*      */ 
/*      */   public PdfOutline getRootOutline()
/*      */   {
/* 2009 */     return this.rootOutline;
/*      */   }
/*      */ 
/*      */   void calculateOutlineCount()
/*      */   {
/* 2017 */     if (this.rootOutline.getKids().size() == 0)
/* 2018 */       return;
/* 2019 */     traverseOutlineCount(this.rootOutline);
/*      */   }
/*      */ 
/*      */   void traverseOutlineCount(PdfOutline outline)
/*      */   {
/* 2026 */     ArrayList kids = outline.getKids();
/* 2027 */     PdfOutline parent = outline.parent();
/* 2028 */     if (kids.isEmpty()) {
/* 2029 */       if (parent != null)
/* 2030 */         parent.setCount(parent.getCount() + 1);
/*      */     }
/*      */     else
/*      */     {
/* 2034 */       for (int k = 0; k < kids.size(); k++) {
/* 2035 */         traverseOutlineCount((PdfOutline)kids.get(k));
/*      */       }
/* 2037 */       if (parent != null)
/* 2038 */         if (outline.isOpen()) {
/* 2039 */           parent.setCount(outline.getCount() + parent.getCount() + 1);
/*      */         }
/*      */         else {
/* 2042 */           parent.setCount(parent.getCount() + 1);
/* 2043 */           outline.setCount(-outline.getCount());
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   void writeOutlines()
/*      */     throws IOException
/*      */   {
/* 2053 */     if (this.rootOutline.getKids().size() == 0)
/* 2054 */       return;
/* 2055 */     outlineTree(this.rootOutline);
/* 2056 */     this.writer.addToBody(this.rootOutline, this.rootOutline.indirectReference());
/*      */   }
/*      */ 
/*      */   void outlineTree(PdfOutline outline)
/*      */     throws IOException
/*      */   {
/* 2063 */     outline.setIndirectReference(this.writer.getPdfIndirectReference());
/* 2064 */     if (outline.parent() != null)
/* 2065 */       outline.put(PdfName.PARENT, outline.parent().indirectReference());
/* 2066 */     ArrayList kids = outline.getKids();
/* 2067 */     int size = kids.size();
/* 2068 */     for (int k = 0; k < size; k++)
/* 2069 */       outlineTree((PdfOutline)kids.get(k));
/* 2070 */     for (int k = 0; k < size; k++) {
/* 2071 */       if (k > 0)
/* 2072 */         ((PdfOutline)kids.get(k)).put(PdfName.PREV, ((PdfOutline)kids.get(k - 1)).indirectReference());
/* 2073 */       if (k < size - 1)
/* 2074 */         ((PdfOutline)kids.get(k)).put(PdfName.NEXT, ((PdfOutline)kids.get(k + 1)).indirectReference());
/*      */     }
/* 2076 */     if (size > 0) {
/* 2077 */       outline.put(PdfName.FIRST, ((PdfOutline)kids.get(0)).indirectReference());
/* 2078 */       outline.put(PdfName.LAST, ((PdfOutline)kids.get(size - 1)).indirectReference());
/*      */     }
/* 2080 */     for (int k = 0; k < size; k++) {
/* 2081 */       PdfOutline kid = (PdfOutline)kids.get(k);
/* 2082 */       this.writer.addToBody(kid, kid.indirectReference());
/*      */     }
/*      */   }
/*      */ 
/*      */   void setViewerPreferences(int preferences)
/*      */   {
/* 2092 */     this.viewerPreferences.setViewerPreferences(preferences);
/*      */   }
/*      */ 
/*      */   void addViewerPreference(PdfName key, PdfObject value)
/*      */   {
/* 2097 */     this.viewerPreferences.addViewerPreference(key, value);
/*      */   }
/*      */ 
/*      */   void setPageLabels(PdfPageLabels pageLabels)
/*      */   {
/* 2108 */     this.pageLabels = pageLabels;
/*      */   }
/*      */ 
/*      */   public PdfPageLabels getPageLabels() {
/* 2112 */     return this.pageLabels;
/*      */   }
/*      */ 
/*      */   void localGoto(String name, float llx, float lly, float urx, float ury)
/*      */   {
/* 2127 */     PdfAction action = getLocalGotoAction(name);
/* 2128 */     this.annotationsImp.addPlainAnnotation(new PdfAnnotation(this.writer, llx, lly, urx, ury, action));
/*      */   }
/*      */ 
/*      */   void remoteGoto(String filename, String name, float llx, float lly, float urx, float ury)
/*      */   {
/* 2141 */     this.annotationsImp.addPlainAnnotation(new PdfAnnotation(this.writer, llx, lly, urx, ury, new PdfAction(filename, name)));
/*      */   }
/*      */ 
/*      */   void remoteGoto(String filename, int page, float llx, float lly, float urx, float ury)
/*      */   {
/* 2154 */     addAnnotation(new PdfAnnotation(this.writer, llx, lly, urx, ury, new PdfAction(filename, page)));
/*      */   }
/*      */ 
/*      */   void setAction(PdfAction action, float llx, float lly, float urx, float ury)
/*      */   {
/* 2165 */     addAnnotation(new PdfAnnotation(this.writer, llx, lly, urx, ury, action));
/*      */   }
/*      */ 
/*      */   PdfAction getLocalGotoAction(String name)
/*      */   {
/* 2175 */     Destination dest = (Destination)this.localDestinations.get(name);
/* 2176 */     if (dest == null)
/* 2177 */       dest = new Destination();
/*      */     PdfAction action;
/* 2178 */     if (dest.action == null) {
/* 2179 */       if (dest.reference == null) {
/* 2180 */         dest.reference = this.writer.getPdfIndirectReference();
/*      */       }
/* 2182 */       PdfAction action = new PdfAction(dest.reference);
/* 2183 */       dest.action = action;
/* 2184 */       this.localDestinations.put(name, dest);
/*      */     }
/*      */     else {
/* 2187 */       action = dest.action;
/*      */     }
/* 2189 */     return action;
/*      */   }
/*      */ 
/*      */   boolean localDestination(String name, PdfDestination destination)
/*      */   {
/* 2202 */     Destination dest = (Destination)this.localDestinations.get(name);
/* 2203 */     if (dest == null)
/* 2204 */       dest = new Destination();
/* 2205 */     if (dest.destination != null)
/* 2206 */       return false;
/* 2207 */     dest.destination = destination;
/* 2208 */     this.localDestinations.put(name, dest);
/* 2209 */     if (!destination.hasPage())
/* 2210 */       destination.addPage(this.writer.getCurrentPage());
/* 2211 */     return true;
/*      */   }
/*      */ 
/*      */   void addJavaScript(PdfAction js)
/*      */   {
/* 2221 */     if (js.get(PdfName.JS) == null)
/* 2222 */       throw new RuntimeException(MessageLocalization.getComposedMessage("only.javascript.actions.are.allowed", new Object[0]));
/*      */     try {
/* 2224 */       this.documentLevelJS.put(SIXTEEN_DIGITS.format(this.jsCounter++), this.writer.addToBody(js).getIndirectReference());
/*      */     }
/*      */     catch (IOException e) {
/* 2227 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/* 2231 */   void addJavaScript(String name, PdfAction js) { if (js.get(PdfName.JS) == null)
/* 2232 */       throw new RuntimeException(MessageLocalization.getComposedMessage("only.javascript.actions.are.allowed", new Object[0]));
/*      */     try {
/* 2234 */       this.documentLevelJS.put(name, this.writer.addToBody(js).getIndirectReference());
/*      */     }
/*      */     catch (IOException e) {
/* 2237 */       throw new ExceptionConverter(e);
/*      */     } }
/*      */ 
/*      */   HashMap<String, PdfObject> getDocumentLevelJS()
/*      */   {
/* 2242 */     return this.documentLevelJS;
/*      */   }
/*      */ 
/*      */   void addFileAttachment(String description, PdfFileSpecification fs)
/*      */     throws IOException
/*      */   {
/* 2248 */     if (description == null) {
/* 2249 */       PdfString desc = (PdfString)fs.get(PdfName.DESC);
/* 2250 */       if (desc == null) {
/* 2251 */         description = "";
/*      */       }
/*      */       else {
/* 2254 */         description = PdfEncodings.convertToString(desc.getBytes(), null);
/*      */       }
/*      */     }
/* 2257 */     fs.addDescription(description, true);
/* 2258 */     if (description.length() == 0)
/* 2259 */       description = "Unnamed";
/* 2260 */     String fn = PdfEncodings.convertToString(new PdfString(description, "UnicodeBig").getBytes(), null);
/* 2261 */     int k = 0;
/* 2262 */     while (this.documentFileAttachment.containsKey(fn)) {
/* 2263 */       k++;
/* 2264 */       fn = PdfEncodings.convertToString(new PdfString(description + " " + k, "UnicodeBig").getBytes(), null);
/*      */     }
/* 2266 */     this.documentFileAttachment.put(fn, fs.getReference());
/*      */   }
/*      */ 
/*      */   HashMap<String, PdfObject> getDocumentFileAttachment() {
/* 2270 */     return this.documentFileAttachment;
/*      */   }
/*      */ 
/*      */   void setOpenAction(String name)
/*      */   {
/* 2278 */     this.openActionName = name;
/* 2279 */     this.openActionAction = null;
/*      */   }
/*      */ 
/*      */   void setOpenAction(PdfAction action)
/*      */   {
/* 2284 */     this.openActionAction = action;
/* 2285 */     this.openActionName = null;
/*      */   }
/*      */ 
/*      */   void addAdditionalAction(PdfName actionType, PdfAction action)
/*      */   {
/* 2290 */     if (this.additionalActions == null) {
/* 2291 */       this.additionalActions = new PdfDictionary();
/*      */     }
/* 2293 */     if (action == null)
/* 2294 */       this.additionalActions.remove(actionType);
/*      */     else
/* 2296 */       this.additionalActions.put(actionType, action);
/* 2297 */     if (this.additionalActions.size() == 0)
/* 2298 */       this.additionalActions = null;
/*      */   }
/*      */ 
/*      */   public void setCollection(PdfCollection collection)
/*      */   {
/* 2310 */     this.collection = collection;
/*      */   }
/*      */ 
/*      */   PdfAcroForm getAcroForm()
/*      */   {
/* 2322 */     return this.annotationsImp.getAcroForm();
/*      */   }
/*      */ 
/*      */   void setSigFlags(int f) {
/* 2326 */     this.annotationsImp.setSigFlags(f);
/*      */   }
/*      */ 
/*      */   void addCalculationOrder(PdfFormField formField) {
/* 2330 */     this.annotationsImp.addCalculationOrder(formField);
/*      */   }
/*      */ 
/*      */   void addAnnotation(PdfAnnotation annot) {
/* 2334 */     this.pageEmpty = false;
/* 2335 */     this.annotationsImp.addAnnotation(annot);
/*      */   }
/*      */ 
/*      */   void setLanguage(String language)
/*      */   {
/* 2340 */     this.language = new PdfString(language);
/*      */   }
/*      */ 
/*      */   void setCropBoxSize(Rectangle crop)
/*      */   {
/* 2357 */     setBoxSize("crop", crop);
/*      */   }
/*      */ 
/*      */   void setBoxSize(String boxName, Rectangle size) {
/* 2361 */     if (size == null)
/* 2362 */       this.boxSize.remove(boxName);
/*      */     else
/* 2364 */       this.boxSize.put(boxName, new PdfRectangle(size));
/*      */   }
/*      */ 
/*      */   protected void setNewPageSizeAndMargins() {
/* 2368 */     this.pageSize = this.nextPageSize;
/* 2369 */     if ((this.marginMirroring) && ((getPageNumber() & 0x1) == 0)) {
/* 2370 */       this.marginRight = this.nextMarginLeft;
/* 2371 */       this.marginLeft = this.nextMarginRight;
/*      */     }
/*      */     else {
/* 2374 */       this.marginLeft = this.nextMarginLeft;
/* 2375 */       this.marginRight = this.nextMarginRight;
/*      */     }
/* 2377 */     if ((this.marginMirroringTopBottom) && ((getPageNumber() & 0x1) == 0)) {
/* 2378 */       this.marginTop = this.nextMarginBottom;
/* 2379 */       this.marginBottom = this.nextMarginTop;
/*      */     }
/*      */     else {
/* 2382 */       this.marginTop = this.nextMarginTop;
/* 2383 */       this.marginBottom = this.nextMarginBottom;
/*      */     }
/* 2385 */     if (!isTagged(this.writer)) {
/* 2386 */       this.text = new PdfContentByte(this.writer);
/* 2387 */       this.text.reset();
/*      */     } else {
/* 2389 */       this.text = this.graphics;
/*      */     }
/* 2391 */     this.text.beginText();
/*      */ 
/* 2393 */     this.text.moveText(left(), top());
/* 2394 */     if (isTagged(this.writer))
/* 2395 */       this.textEmptySize = this.text.size();
/*      */   }
/*      */ 
/*      */   Rectangle getBoxSize(String boxName)
/*      */   {
/* 2403 */     PdfRectangle r = (PdfRectangle)this.thisBoxSize.get(boxName);
/* 2404 */     if (r != null) return r.getRectangle();
/* 2405 */     return null;
/*      */   }
/*      */ 
/*      */   void setPageEmpty(boolean pageEmpty)
/*      */   {
/* 2414 */     this.pageEmpty = pageEmpty;
/*      */   }
/*      */ 
/*      */   boolean isPageEmpty() {
/* 2418 */     if (isTagged(this.writer)) {
/* 2419 */       return (this.writer == null) || ((this.writer.getDirectContent().size(false) == 0) && (this.writer.getDirectContentUnder().size(false) == 0) && (this.text.size(false) - this.textEmptySize == 0) && ((this.pageEmpty) || (this.writer.isPaused())));
/*      */     }
/*      */ 
/* 2422 */     return (this.writer == null) || ((this.writer.getDirectContent().size() == 0) && (this.writer.getDirectContentUnder().size() == 0) && ((this.pageEmpty) || (this.writer.isPaused())));
/*      */   }
/*      */ 
/*      */   void setDuration(int seconds)
/*      */   {
/* 2432 */     if (seconds > 0)
/* 2433 */       this.writer.addPageDictEntry(PdfName.DUR, new PdfNumber(seconds));
/*      */   }
/*      */ 
/*      */   void setTransition(PdfTransition transition)
/*      */   {
/* 2441 */     this.writer.addPageDictEntry(PdfName.TRANS, transition.getTransitionDictionary());
/*      */   }
/*      */ 
/*      */   void setPageAction(PdfName actionType, PdfAction action)
/*      */   {
/* 2446 */     if (this.pageAA == null) {
/* 2447 */       this.pageAA = new PdfDictionary();
/*      */     }
/* 2449 */     this.pageAA.put(actionType, action);
/*      */   }
/*      */ 
/*      */   void setThumbnail(Image image)
/*      */     throws PdfException, DocumentException
/*      */   {
/* 2455 */     this.writer.addPageDictEntry(PdfName.THUMB, this.writer.getImageReference(this.writer.addDirectImageSimple(image)));
/*      */   }
/*      */ 
/*      */   PageResources getPageResources()
/*      */   {
/* 2464 */     return this.pageResources;
/*      */   }
/*      */ 
/*      */   boolean isStrictImageSequence()
/*      */   {
/* 2477 */     return this.strictImageSequence;
/*      */   }
/*      */ 
/*      */   void setStrictImageSequence(boolean strictImageSequence)
/*      */   {
/* 2485 */     this.strictImageSequence = strictImageSequence;
/*      */   }
/*      */ 
/*      */   public void clearTextWrap()
/*      */   {
/* 2495 */     float tmpHeight = this.imageEnd - this.currentHeight;
/* 2496 */     if (this.line != null) {
/* 2497 */       tmpHeight += this.line.height();
/*      */     }
/* 2499 */     if ((this.imageEnd > -1.0F) && (tmpHeight > 0.0F)) {
/* 2500 */       carriageReturn();
/* 2501 */       this.currentHeight += tmpHeight;
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getStructParentIndex(Object obj) {
/* 2506 */     int[] i = (int[])this.structParentIndices.get(obj);
/* 2507 */     if (i == null) {
/* 2508 */       i = new int[] { this.structParentIndices.size(), 0 };
/* 2509 */       this.structParentIndices.put(obj, i);
/*      */     }
/* 2511 */     return i[0];
/*      */   }
/*      */ 
/*      */   public int getNextMarkPoint(Object obj) {
/* 2515 */     int[] i = (int[])this.structParentIndices.get(obj);
/* 2516 */     if (i == null) {
/* 2517 */       i = new int[] { this.structParentIndices.size(), 0 };
/* 2518 */       this.structParentIndices.put(obj, i);
/*      */     }
/* 2520 */     int markPoint = i[1];
/* 2521 */     i[1] += 1;
/* 2522 */     return markPoint;
/*      */   }
/*      */ 
/*      */   public int[] getStructParentIndexAndNextMarkPoint(Object obj) {
/* 2526 */     int[] i = (int[])this.structParentIndices.get(obj);
/* 2527 */     if (i == null) {
/* 2528 */       i = new int[] { this.structParentIndices.size(), 0 };
/* 2529 */       this.structParentIndices.put(obj, i);
/*      */     }
/* 2531 */     int markPoint = i[1];
/* 2532 */     i[1] += 1;
/* 2533 */     return new int[] { i[0], markPoint };
/*      */   }
/*      */ 
/*      */   protected void add(Image image)
/*      */     throws PdfException, DocumentException
/*      */   {
/* 2548 */     if (image.hasAbsoluteY()) {
/* 2549 */       this.graphics.addImage(image);
/* 2550 */       this.pageEmpty = false;
/* 2551 */       return;
/*      */     }
/*      */ 
/* 2555 */     if ((this.currentHeight != 0.0F) && (indentTop() - this.currentHeight - image.getScaledHeight() < indentBottom())) {
/* 2556 */       if ((!this.strictImageSequence) && (this.imageWait == null)) {
/* 2557 */         this.imageWait = image;
/* 2558 */         return;
/*      */       }
/* 2560 */       newPage();
/* 2561 */       if ((this.currentHeight != 0.0F) && (indentTop() - this.currentHeight - image.getScaledHeight() < indentBottom())) {
/* 2562 */         this.imageWait = image;
/* 2563 */         return;
/*      */       }
/*      */     }
/* 2566 */     this.pageEmpty = false;
/*      */ 
/* 2568 */     if (image == this.imageWait)
/* 2569 */       this.imageWait = null;
/* 2570 */     boolean textwrap = ((image.getAlignment() & 0x4) == 4) && ((image.getAlignment() & 0x1) != 1);
/*      */ 
/* 2572 */     boolean underlying = (image.getAlignment() & 0x8) == 8;
/* 2573 */     float diff = this.leading / 2.0F;
/* 2574 */     if (textwrap) {
/* 2575 */       diff += this.leading;
/*      */     }
/* 2577 */     float lowerleft = indentTop() - this.currentHeight - image.getScaledHeight() - diff;
/* 2578 */     float[] mt = image.matrix();
/* 2579 */     float startPosition = indentLeft() - mt[4];
/* 2580 */     if ((image.getAlignment() & 0x2) == 2) startPosition = indentRight() - image.getScaledWidth() - mt[4];
/* 2581 */     if ((image.getAlignment() & 0x1) == 1) startPosition = indentLeft() + (indentRight() - indentLeft() - image.getScaledWidth()) / 2.0F - mt[4];
/* 2582 */     if (image.hasAbsoluteX()) startPosition = image.getAbsoluteX();
/* 2583 */     if (textwrap) {
/* 2584 */       if ((this.imageEnd < 0.0F) || (this.imageEnd < this.currentHeight + image.getScaledHeight() + diff)) {
/* 2585 */         this.imageEnd = (this.currentHeight + image.getScaledHeight() + diff);
/*      */       }
/* 2587 */       if ((image.getAlignment() & 0x2) == 2)
/*      */       {
/* 2589 */         this.indentation.imageIndentRight += image.getScaledWidth() + image.getIndentationLeft();
/*      */       }
/*      */       else
/*      */       {
/* 2593 */         this.indentation.imageIndentLeft += image.getScaledWidth() + image.getIndentationRight();
/*      */       }
/*      */ 
/*      */     }
/* 2597 */     else if ((image.getAlignment() & 0x2) == 2) { startPosition -= image.getIndentationRight();
/* 2598 */     } else if ((image.getAlignment() & 0x1) == 1) { startPosition += image.getIndentationLeft() - image.getIndentationRight(); } else {
/* 2599 */       startPosition += image.getIndentationLeft();
/*      */     }
/* 2601 */     this.graphics.addImage(image, mt[0], mt[1], mt[2], mt[3], startPosition, lowerleft - mt[5]);
/* 2602 */     if ((!textwrap) && (!underlying)) {
/* 2603 */       this.currentHeight += image.getScaledHeight() + diff;
/* 2604 */       flushLines();
/* 2605 */       this.text.moveText(0.0F, -(image.getScaledHeight() + diff));
/* 2606 */       newLine();
/*      */     }
/*      */   }
/*      */ 
/*      */   void addPTable(PdfPTable ptable)
/*      */     throws DocumentException
/*      */   {
/* 2617 */     ColumnText ct = new ColumnText(isTagged(this.writer) ? this.text : this.writer.getDirectContent());
/*      */ 
/* 2620 */     if ((ptable.getKeepTogether()) && (!fitsPage(ptable, 0.0F)) && (this.currentHeight > 0.0F)) {
/* 2621 */       newPage();
/*      */     }
/* 2623 */     if (this.currentHeight == 0.0F) {
/* 2624 */       ct.setAdjustFirstLine(false);
/*      */     }
/* 2626 */     ct.addElement(ptable);
/* 2627 */     boolean he = ptable.isHeadersInEvent();
/* 2628 */     ptable.setHeadersInEvent(true);
/* 2629 */     int loop = 0;
/*      */     while (true) {
/* 2631 */       ct.setSimpleColumn(indentLeft(), indentBottom(), indentRight(), indentTop() - this.currentHeight);
/* 2632 */       int status = ct.go();
/* 2633 */       if ((status & 0x1) != 0) {
/* 2634 */         if (isTagged(this.writer))
/* 2635 */           this.text.setTextMatrix(indentLeft(), ct.getYLine());
/*      */         else {
/* 2637 */           this.text.moveText(0.0F, ct.getYLine() - indentTop() + this.currentHeight);
/*      */         }
/* 2639 */         this.currentHeight = (indentTop() - ct.getYLine());
/* 2640 */         break;
/*      */       }
/* 2642 */       if (indentTop() - this.currentHeight == ct.getYLine())
/* 2643 */         loop++;
/*      */       else
/* 2645 */         loop = 0;
/* 2646 */       if (loop == 3) {
/* 2647 */         throw new DocumentException(MessageLocalization.getComposedMessage("infinite.table.loop", new Object[0]));
/*      */       }
/* 2649 */       newPage();
/* 2650 */       if (isTagged(this.writer)) {
/* 2651 */         ct.setCanvas(this.text);
/*      */       }
/*      */     }
/* 2654 */     ptable.setHeadersInEvent(he);
/*      */   }
/*      */ 
/*      */   private void addDiv(PdfDiv div)
/*      */     throws DocumentException
/*      */   {
/* 2660 */     if (this.floatingElements == null) {
/* 2661 */       this.floatingElements = new ArrayList();
/*      */     }
/* 2663 */     this.floatingElements.add(div);
/*      */   }
/*      */ 
/*      */   private void flushFloatingElements() throws DocumentException {
/* 2667 */     if ((this.floatingElements != null) && (!this.floatingElements.isEmpty())) {
/* 2668 */       ArrayList cachedFloatingElements = this.floatingElements;
/* 2669 */       this.floatingElements = null;
/* 2670 */       FloatLayout fl = new FloatLayout(cachedFloatingElements, false);
/* 2671 */       int loop = 0;
/*      */       while (true) {
/* 2673 */         fl.setSimpleColumn(indentLeft(), indentBottom(), indentRight(), indentTop() - this.currentHeight);
/*      */         try {
/* 2675 */           int status = fl.layout(isTagged(this.writer) ? this.text : this.writer.getDirectContent(), false);
/* 2676 */           if ((status & 0x1) != 0) {
/* 2677 */             if (isTagged(this.writer))
/* 2678 */               this.text.setTextMatrix(indentLeft(), fl.getYLine());
/*      */             else {
/* 2680 */               this.text.moveText(0.0F, fl.getYLine() - indentTop() + this.currentHeight);
/*      */             }
/* 2682 */             this.currentHeight = (indentTop() - fl.getYLine());
/* 2683 */             break;
/*      */           }
/*      */         } catch (Exception exc) {
/* 2686 */           return;
/*      */         }
/* 2688 */         if ((indentTop() - this.currentHeight == fl.getYLine()) || (isPageEmpty()))
/* 2689 */           loop++;
/*      */         else {
/* 2691 */           loop = 0;
/*      */         }
/* 2693 */         if (loop == 2) {
/* 2694 */           return;
/*      */         }
/* 2696 */         newPage();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   boolean fitsPage(PdfPTable table, float margin)
/*      */   {
/* 2710 */     if (!table.isLockedWidth()) {
/* 2711 */       float totalWidth = (indentRight() - indentLeft()) * table.getWidthPercentage() / 100.0F;
/* 2712 */       table.setTotalWidth(totalWidth);
/*      */     }
/*      */ 
/* 2715 */     ensureNewLine();
/* 2716 */     Float spaceNeeded = Float.valueOf(table.isSkipFirstHeader() ? table.getTotalHeight() - table.getHeaderHeight() : table.getTotalHeight());
/* 2717 */     return spaceNeeded.floatValue() + (this.currentHeight > 0.0F ? table.spacingBefore() : 0.0F) <= indentTop() - this.currentHeight - indentBottom() - margin;
/*      */   }
/*      */ 
/*      */   private static boolean isTagged(PdfWriter writer) {
/* 2721 */     return (writer != null) && (writer.isTagged());
/*      */   }
/*      */ 
/*      */   private PdfLine getLastLine() {
/* 2725 */     if (this.lines.size() > 0) {
/* 2726 */       return (PdfLine)this.lines.get(this.lines.size() - 1);
/*      */     }
/* 2728 */     return null;
/*      */   }
/*      */ 
/*      */   public class Destination
/*      */   {
/*      */     public PdfAction action;
/*      */     public PdfIndirectReference reference;
/*      */     public PdfDestination destination;
/*      */ 
/*      */     public Destination()
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class Indentation
/*      */   {
/* 1813 */     float indentLeft = 0.0F;
/*      */ 
/* 1816 */     float sectionIndentLeft = 0.0F;
/*      */ 
/* 1819 */     float listIndentLeft = 0.0F;
/*      */ 
/* 1822 */     float imageIndentLeft = 0.0F;
/*      */ 
/* 1825 */     float indentRight = 0.0F;
/*      */ 
/* 1828 */     float sectionIndentRight = 0.0F;
/*      */ 
/* 1831 */     float imageIndentRight = 0.0F;
/*      */ 
/* 1834 */     float indentTop = 0.0F;
/*      */ 
/* 1837 */     float indentBottom = 0.0F;
/*      */   }
/*      */ 
/*      */   static class PdfCatalog extends PdfDictionary
/*      */   {
/*      */     PdfWriter writer;
/*      */ 
/*      */     PdfCatalog(PdfIndirectReference pages, PdfWriter writer)
/*      */     {
/*  217 */       super();
/*  218 */       this.writer = writer;
/*  219 */       put(PdfName.PAGES, pages);
/*      */     }
/*      */ 
/*      */     void addNames(TreeMap<String, PdfDocument.Destination> localDestinations, HashMap<String, PdfObject> documentLevelJS, HashMap<String, PdfObject> documentFileAttachment, PdfWriter writer)
/*      */     {
/*  230 */       if ((localDestinations.isEmpty()) && (documentLevelJS.isEmpty()) && (documentFileAttachment.isEmpty()))
/*  231 */         return;
/*      */       try {
/*  233 */         PdfDictionary names = new PdfDictionary();
/*  234 */         if (!localDestinations.isEmpty()) {
/*  235 */           PdfArray ar = new PdfArray();
/*  236 */           for (Map.Entry entry : localDestinations.entrySet()) {
/*  237 */             String name = (String)entry.getKey();
/*  238 */             PdfDocument.Destination dest = (PdfDocument.Destination)entry.getValue();
/*  239 */             if (dest.destination != null)
/*      */             {
/*  241 */               PdfIndirectReference ref = dest.reference;
/*  242 */               ar.add(new PdfString(name, "UnicodeBig"));
/*  243 */               ar.add(ref);
/*      */             }
/*      */           }
/*  245 */           if (ar.size() > 0) {
/*  246 */             PdfDictionary dests = new PdfDictionary();
/*  247 */             dests.put(PdfName.NAMES, ar);
/*  248 */             names.put(PdfName.DESTS, writer.addToBody(dests).getIndirectReference());
/*      */           }
/*      */         }
/*  251 */         if (!documentLevelJS.isEmpty()) {
/*  252 */           PdfDictionary tree = PdfNameTree.writeTree(documentLevelJS, writer);
/*  253 */           names.put(PdfName.JAVASCRIPT, writer.addToBody(tree).getIndirectReference());
/*      */         }
/*  255 */         if (!documentFileAttachment.isEmpty()) {
/*  256 */           names.put(PdfName.EMBEDDEDFILES, writer.addToBody(PdfNameTree.writeTree(documentFileAttachment, writer)).getIndirectReference());
/*      */         }
/*  258 */         if (names.size() > 0)
/*  259 */           put(PdfName.NAMES, writer.addToBody(names).getIndirectReference());
/*      */       }
/*      */       catch (IOException e) {
/*  262 */         throw new ExceptionConverter(e);
/*      */       }
/*      */     }
/*      */ 
/*      */     void setOpenAction(PdfAction action)
/*      */     {
/*  271 */       put(PdfName.OPENACTION, action);
/*      */     }
/*      */ 
/*      */     void setAdditionalActions(PdfDictionary actions)
/*      */     {
/*      */       try
/*      */       {
/*  281 */         put(PdfName.AA, this.writer.addToBody(actions).getIndirectReference());
/*      */       } catch (Exception e) {
/*  283 */         throw new ExceptionConverter(e);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class PdfInfo extends PdfDictionary
/*      */   {
/*      */     PdfInfo()
/*      */     {
/*   96 */       addProducer();
/*   97 */       addCreationDate();
/*      */     }
/*      */ 
/*      */     PdfInfo(String author, String title, String subject)
/*      */     {
/*  109 */       this();
/*  110 */       addTitle(title);
/*  111 */       addSubject(subject);
/*  112 */       addAuthor(author);
/*      */     }
/*      */ 
/*      */     void addTitle(String title)
/*      */     {
/*  122 */       put(PdfName.TITLE, new PdfString(title, "UnicodeBig"));
/*      */     }
/*      */ 
/*      */     void addSubject(String subject)
/*      */     {
/*  132 */       put(PdfName.SUBJECT, new PdfString(subject, "UnicodeBig"));
/*      */     }
/*      */ 
/*      */     void addKeywords(String keywords)
/*      */     {
/*  142 */       put(PdfName.KEYWORDS, new PdfString(keywords, "UnicodeBig"));
/*      */     }
/*      */ 
/*      */     void addAuthor(String author)
/*      */     {
/*  152 */       put(PdfName.AUTHOR, new PdfString(author, "UnicodeBig"));
/*      */     }
/*      */ 
/*      */     void addCreator(String creator)
/*      */     {
/*  162 */       put(PdfName.CREATOR, new PdfString(creator, "UnicodeBig"));
/*      */     }
/*      */ 
/*      */     void addProducer()
/*      */     {
/*  170 */       put(PdfName.PRODUCER, new PdfString(Version.getInstance().getVersion()));
/*      */     }
/*      */ 
/*      */     void addCreationDate()
/*      */     {
/*  178 */       PdfString date = new PdfDate();
/*  179 */       put(PdfName.CREATIONDATE, date);
/*  180 */       put(PdfName.MODDATE, date);
/*      */     }
/*      */ 
/*      */     void addkey(String key, String value) {
/*  184 */       if ((key.equals("Producer")) || (key.equals("CreationDate")))
/*  185 */         return;
/*  186 */       put(new PdfName(key), new PdfString(value, "UnicodeBig"));
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfDocument
 * JD-Core Version:    0.6.2
 */