/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.AccessibleElementId;
/*      */ import com.itextpdf.text.Chunk;
/*      */ import com.itextpdf.text.DocumentException;
/*      */ import com.itextpdf.text.Element;
/*      */ import com.itextpdf.text.ExceptionConverter;
/*      */ import com.itextpdf.text.Image;
/*      */ import com.itextpdf.text.Phrase;
/*      */ import com.itextpdf.text.Rectangle;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.pdf.events.PdfPCellEventForwarder;
/*      */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ 
/*      */ public class PdfPCell extends Rectangle
/*      */   implements IAccessibleElement
/*      */ {
/*   62 */   private ColumnText column = new ColumnText(null);
/*      */ 
/*   65 */   private int verticalAlignment = 4;
/*      */ 
/*   68 */   private float paddingLeft = 2.0F;
/*      */ 
/*   71 */   private float paddingRight = 2.0F;
/*      */ 
/*   74 */   private float paddingTop = 2.0F;
/*      */ 
/*   77 */   private float paddingBottom = 2.0F;
/*      */ 
/*   80 */   private float fixedHeight = 0.0F;
/*      */   private float minimumHeight;
/*   86 */   private boolean noWrap = false;
/*      */   private PdfPTable table;
/*   92 */   private int colspan = 1;
/*      */ 
/*   98 */   private int rowspan = 1;
/*      */   private Image image;
/*      */   private PdfPCellEvent cellEvent;
/*  107 */   private boolean useDescender = false;
/*      */ 
/*  110 */   private boolean useBorderPadding = false;
/*      */   protected Phrase phrase;
/*      */   private int rotation;
/*  121 */   protected PdfName role = PdfName.TD;
/*  122 */   protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
/*  123 */   protected AccessibleElementId id = new AccessibleElementId();
/*      */ 
/*  125 */   protected ArrayList<PdfPHeaderCell> headers = null;
/*      */ 
/*      */   public PdfPCell()
/*      */   {
/*  132 */     super(0.0F, 0.0F, 0.0F, 0.0F);
/*  133 */     this.borderWidth = 0.5F;
/*  134 */     this.border = 15;
/*  135 */     this.column.setLeading(0.0F, 1.0F);
/*      */   }
/*      */ 
/*      */   public PdfPCell(Phrase phrase)
/*      */   {
/*  145 */     super(0.0F, 0.0F, 0.0F, 0.0F);
/*  146 */     this.borderWidth = 0.5F;
/*  147 */     this.border = 15;
/*  148 */     this.column.addText(this.phrase = phrase);
/*  149 */     this.column.setLeading(0.0F, 1.0F);
/*      */   }
/*      */ 
/*      */   public PdfPCell(Image image)
/*      */   {
/*  159 */     this(image, false);
/*      */   }
/*      */ 
/*      */   public PdfPCell(Image image, boolean fit)
/*      */   {
/*  170 */     super(0.0F, 0.0F, 0.0F, 0.0F);
/*  171 */     this.borderWidth = 0.5F;
/*  172 */     this.border = 15;
/*  173 */     this.column.setLeading(0.0F, 1.0F);
/*  174 */     if (fit) {
/*  175 */       this.image = image;
/*  176 */       setPadding(this.borderWidth / 2.0F);
/*      */     }
/*      */     else {
/*  179 */       image.setScaleToFitLineWhenOverflow(false);
/*  180 */       this.column.addText(this.phrase = new Phrase(new Chunk(image, 0.0F, 0.0F, true)));
/*  181 */       setPadding(0.0F);
/*      */     }
/*      */   }
/*      */ 
/*      */   public PdfPCell(PdfPTable table)
/*      */   {
/*  193 */     this(table, null);
/*      */   }
/*      */ 
/*      */   public PdfPCell(PdfPTable table, PdfPCell style)
/*      */   {
/*  205 */     super(0.0F, 0.0F, 0.0F, 0.0F);
/*  206 */     this.borderWidth = 0.5F;
/*  207 */     this.border = 15;
/*  208 */     this.column.setLeading(0.0F, 1.0F);
/*  209 */     this.table = table;
/*  210 */     table.setWidthPercentage(100.0F);
/*  211 */     table.setExtendLastRow(true);
/*  212 */     this.column.addElement(table);
/*  213 */     if (style != null) {
/*  214 */       cloneNonPositionParameters(style);
/*  215 */       this.verticalAlignment = style.verticalAlignment;
/*  216 */       this.paddingLeft = style.paddingLeft;
/*  217 */       this.paddingRight = style.paddingRight;
/*  218 */       this.paddingTop = style.paddingTop;
/*  219 */       this.paddingBottom = style.paddingBottom;
/*  220 */       this.colspan = style.colspan;
/*  221 */       this.rowspan = style.rowspan;
/*  222 */       this.cellEvent = style.cellEvent;
/*  223 */       this.useDescender = style.useDescender;
/*  224 */       this.useBorderPadding = style.useBorderPadding;
/*  225 */       this.rotation = style.rotation;
/*      */     }
/*      */     else {
/*  228 */       setPadding(0.0F);
/*      */     }
/*      */   }
/*      */ 
/*      */   public PdfPCell(PdfPCell cell)
/*      */   {
/*  237 */     super(cell.llx, cell.lly, cell.urx, cell.ury);
/*  238 */     cloneNonPositionParameters(cell);
/*  239 */     this.verticalAlignment = cell.verticalAlignment;
/*  240 */     this.paddingLeft = cell.paddingLeft;
/*  241 */     this.paddingRight = cell.paddingRight;
/*  242 */     this.paddingTop = cell.paddingTop;
/*  243 */     this.paddingBottom = cell.paddingBottom;
/*  244 */     this.phrase = cell.phrase;
/*  245 */     this.fixedHeight = cell.fixedHeight;
/*  246 */     this.minimumHeight = cell.minimumHeight;
/*  247 */     this.noWrap = cell.noWrap;
/*  248 */     this.colspan = cell.colspan;
/*  249 */     this.rowspan = cell.rowspan;
/*  250 */     if (cell.table != null)
/*  251 */       this.table = new PdfPTable(cell.table);
/*  252 */     this.image = Image.getInstance(cell.image);
/*  253 */     this.cellEvent = cell.cellEvent;
/*  254 */     this.useDescender = cell.useDescender;
/*  255 */     this.column = ColumnText.duplicate(cell.column);
/*  256 */     this.useBorderPadding = cell.useBorderPadding;
/*  257 */     this.rotation = cell.rotation;
/*  258 */     this.id = cell.id;
/*  259 */     this.role = cell.role;
/*  260 */     if (cell.accessibleAttributes != null)
/*  261 */       this.accessibleAttributes = new HashMap(cell.accessibleAttributes);
/*  262 */     this.headers = cell.headers;
/*      */   }
/*      */ 
/*      */   public void addElement(Element element)
/*      */   {
/*  271 */     if (this.table != null) {
/*  272 */       this.table = null;
/*  273 */       this.column.setText(null);
/*      */     }
/*  275 */     if ((element instanceof PdfPTable))
/*  276 */       ((PdfPTable)element).setSplitLate(false);
/*  277 */     else if ((element instanceof PdfDiv)) {
/*  278 */       for (Element divChildElement : ((PdfDiv)element).getContent()) {
/*  279 */         if ((divChildElement instanceof PdfPTable)) {
/*  280 */           ((PdfPTable)divChildElement).setSplitLate(false);
/*      */         }
/*      */       }
/*      */     }
/*  284 */     this.column.addElement(element);
/*      */   }
/*      */ 
/*      */   public Phrase getPhrase()
/*      */   {
/*  293 */     return this.phrase;
/*      */   }
/*      */ 
/*      */   public void setPhrase(Phrase phrase)
/*      */   {
/*  302 */     this.table = null;
/*  303 */     this.image = null;
/*  304 */     this.column.setText(this.phrase = phrase);
/*      */   }
/*      */ 
/*      */   public int getHorizontalAlignment()
/*      */   {
/*  313 */     return this.column.getAlignment();
/*      */   }
/*      */ 
/*      */   public void setHorizontalAlignment(int horizontalAlignment)
/*      */   {
/*  323 */     this.column.setAlignment(horizontalAlignment);
/*      */   }
/*      */ 
/*      */   public int getVerticalAlignment()
/*      */   {
/*  332 */     return this.verticalAlignment;
/*      */   }
/*      */ 
/*      */   public void setVerticalAlignment(int verticalAlignment)
/*      */   {
/*  342 */     if (this.table != null)
/*  343 */       this.table.setExtendLastRow(verticalAlignment == 4);
/*  344 */     this.verticalAlignment = verticalAlignment;
/*      */   }
/*      */ 
/*      */   public float getEffectivePaddingLeft()
/*      */   {
/*  355 */     if (isUseBorderPadding()) {
/*  356 */       float border = getBorderWidthLeft() / (isUseVariableBorders() ? 1.0F : 2.0F);
/*  357 */       return this.paddingLeft + border;
/*      */     }
/*  359 */     return this.paddingLeft;
/*      */   }
/*      */ 
/*      */   public float getPaddingLeft()
/*      */   {
/*  366 */     return this.paddingLeft;
/*      */   }
/*      */ 
/*      */   public void setPaddingLeft(float paddingLeft)
/*      */   {
/*  375 */     this.paddingLeft = paddingLeft;
/*      */   }
/*      */ 
/*      */   public float getEffectivePaddingRight()
/*      */   {
/*  385 */     if (isUseBorderPadding()) {
/*  386 */       float border = getBorderWidthRight() / (isUseVariableBorders() ? 1.0F : 2.0F);
/*  387 */       return this.paddingRight + border;
/*      */     }
/*  389 */     return this.paddingRight;
/*      */   }
/*      */ 
/*      */   public float getPaddingRight()
/*      */   {
/*  398 */     return this.paddingRight;
/*      */   }
/*      */ 
/*      */   public void setPaddingRight(float paddingRight)
/*      */   {
/*  407 */     this.paddingRight = paddingRight;
/*      */   }
/*      */ 
/*      */   public float getEffectivePaddingTop()
/*      */   {
/*  417 */     if (isUseBorderPadding()) {
/*  418 */       float border = getBorderWidthTop() / (isUseVariableBorders() ? 1.0F : 2.0F);
/*  419 */       return this.paddingTop + border;
/*      */     }
/*  421 */     return this.paddingTop;
/*      */   }
/*      */ 
/*      */   public float getPaddingTop()
/*      */   {
/*  430 */     return this.paddingTop;
/*      */   }
/*      */ 
/*      */   public void setPaddingTop(float paddingTop)
/*      */   {
/*  439 */     this.paddingTop = paddingTop;
/*      */   }
/*      */ 
/*      */   public float getEffectivePaddingBottom()
/*      */   {
/*  450 */     if (isUseBorderPadding()) {
/*  451 */       float border = getBorderWidthBottom() / (isUseVariableBorders() ? 1.0F : 2.0F);
/*  452 */       return this.paddingBottom + border;
/*      */     }
/*  454 */     return this.paddingBottom;
/*      */   }
/*      */ 
/*      */   public float getPaddingBottom()
/*      */   {
/*  463 */     return this.paddingBottom;
/*      */   }
/*      */ 
/*      */   public void setPaddingBottom(float paddingBottom)
/*      */   {
/*  472 */     this.paddingBottom = paddingBottom;
/*      */   }
/*      */ 
/*      */   public void setPadding(float padding)
/*      */   {
/*  481 */     this.paddingBottom = padding;
/*  482 */     this.paddingTop = padding;
/*  483 */     this.paddingLeft = padding;
/*  484 */     this.paddingRight = padding;
/*      */   }
/*      */ 
/*      */   public boolean isUseBorderPadding()
/*      */   {
/*  493 */     return this.useBorderPadding;
/*      */   }
/*      */ 
/*      */   public void setUseBorderPadding(boolean use)
/*      */   {
/*  502 */     this.useBorderPadding = use;
/*      */   }
/*      */ 
/*      */   public void setLeading(float fixedLeading, float multipliedLeading)
/*      */   {
/*  515 */     this.column.setLeading(fixedLeading, multipliedLeading);
/*      */   }
/*      */ 
/*      */   public float getLeading()
/*      */   {
/*  524 */     return this.column.getLeading();
/*      */   }
/*      */ 
/*      */   public float getMultipliedLeading()
/*      */   {
/*  533 */     return this.column.getMultipliedLeading();
/*      */   }
/*      */ 
/*      */   public void setIndent(float indent)
/*      */   {
/*  542 */     this.column.setIndent(indent);
/*      */   }
/*      */ 
/*      */   public float getIndent()
/*      */   {
/*  551 */     return this.column.getIndent();
/*      */   }
/*      */ 
/*      */   public float getExtraParagraphSpace()
/*      */   {
/*  560 */     return this.column.getExtraParagraphSpace();
/*      */   }
/*      */ 
/*      */   public void setExtraParagraphSpace(float extraParagraphSpace)
/*      */   {
/*  569 */     this.column.setExtraParagraphSpace(extraParagraphSpace);
/*      */   }
/*      */ 
/*      */   public void setFixedHeight(float fixedHeight)
/*      */   {
/*  579 */     this.fixedHeight = fixedHeight;
/*  580 */     this.minimumHeight = 0.0F;
/*      */   }
/*      */ 
/*      */   public float getFixedHeight()
/*      */   {
/*  589 */     return this.fixedHeight;
/*      */   }
/*      */ 
/*      */   public boolean hasFixedHeight()
/*      */   {
/*  599 */     return getFixedHeight() > 0.0F;
/*      */   }
/*      */ 
/*      */   public void setMinimumHeight(float minimumHeight)
/*      */   {
/*  609 */     this.minimumHeight = minimumHeight;
/*  610 */     this.fixedHeight = 0.0F;
/*      */   }
/*      */ 
/*      */   public float getMinimumHeight()
/*      */   {
/*  619 */     return this.minimumHeight;
/*      */   }
/*      */ 
/*      */   public boolean hasMinimumHeight()
/*      */   {
/*  629 */     return getMinimumHeight() > 0.0F;
/*      */   }
/*      */ 
/*      */   public boolean isNoWrap()
/*      */   {
/*  638 */     return this.noWrap;
/*      */   }
/*      */ 
/*      */   public void setNoWrap(boolean noWrap)
/*      */   {
/*  647 */     this.noWrap = noWrap;
/*      */   }
/*      */ 
/*      */   public PdfPTable getTable()
/*      */   {
/*  657 */     return this.table;
/*      */   }
/*      */ 
/*      */   void setTable(PdfPTable table) {
/*  661 */     this.table = table;
/*  662 */     this.column.setText(null);
/*  663 */     this.image = null;
/*  664 */     if (table != null) {
/*  665 */       table.setExtendLastRow(this.verticalAlignment == 4);
/*  666 */       this.column.addElement(table);
/*  667 */       table.setWidthPercentage(100.0F);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getColspan()
/*      */   {
/*  677 */     return this.colspan;
/*      */   }
/*      */ 
/*      */   public void setColspan(int colspan)
/*      */   {
/*  686 */     this.colspan = colspan;
/*      */   }
/*      */ 
/*      */   public int getRowspan()
/*      */   {
/*  696 */     return this.rowspan;
/*      */   }
/*      */ 
/*      */   public void setRowspan(int rowspan)
/*      */   {
/*  706 */     this.rowspan = rowspan;
/*      */   }
/*      */ 
/*      */   public void setFollowingIndent(float indent)
/*      */   {
/*  715 */     this.column.setFollowingIndent(indent);
/*      */   }
/*      */ 
/*      */   public float getFollowingIndent()
/*      */   {
/*  724 */     return this.column.getFollowingIndent();
/*      */   }
/*      */ 
/*      */   public void setRightIndent(float indent)
/*      */   {
/*  733 */     this.column.setRightIndent(indent);
/*      */   }
/*      */ 
/*      */   public float getRightIndent()
/*      */   {
/*  742 */     return this.column.getRightIndent();
/*      */   }
/*      */ 
/*      */   public float getSpaceCharRatio()
/*      */   {
/*  751 */     return this.column.getSpaceCharRatio();
/*      */   }
/*      */ 
/*      */   public void setSpaceCharRatio(float spaceCharRatio)
/*      */   {
/*  764 */     this.column.setSpaceCharRatio(spaceCharRatio);
/*      */   }
/*      */ 
/*      */   public void setRunDirection(int runDirection)
/*      */   {
/*  775 */     this.column.setRunDirection(runDirection);
/*      */   }
/*      */ 
/*      */   public int getRunDirection()
/*      */   {
/*  786 */     return this.column.getRunDirection();
/*      */   }
/*      */ 
/*      */   public Image getImage()
/*      */   {
/*  795 */     return this.image;
/*      */   }
/*      */ 
/*      */   public void setImage(Image image)
/*      */   {
/*  804 */     this.column.setText(null);
/*  805 */     this.table = null;
/*  806 */     this.image = image;
/*      */   }
/*      */ 
/*      */   public PdfPCellEvent getCellEvent()
/*      */   {
/*  815 */     return this.cellEvent;
/*      */   }
/*      */ 
/*      */   public void setCellEvent(PdfPCellEvent cellEvent)
/*      */   {
/*  824 */     if (cellEvent == null) {
/*  825 */       this.cellEvent = null;
/*  826 */     } else if (this.cellEvent == null) {
/*  827 */       this.cellEvent = cellEvent;
/*  828 */     } else if ((this.cellEvent instanceof PdfPCellEventForwarder)) {
/*  829 */       ((PdfPCellEventForwarder)this.cellEvent).addCellEvent(cellEvent);
/*      */     } else {
/*  831 */       PdfPCellEventForwarder forward = new PdfPCellEventForwarder();
/*  832 */       forward.addCellEvent(this.cellEvent);
/*  833 */       forward.addCellEvent(cellEvent);
/*  834 */       this.cellEvent = forward;
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getArabicOptions()
/*      */   {
/*  844 */     return this.column.getArabicOptions();
/*      */   }
/*      */ 
/*      */   public void setArabicOptions(int arabicOptions)
/*      */   {
/*  854 */     this.column.setArabicOptions(arabicOptions);
/*      */   }
/*      */ 
/*      */   public boolean isUseAscender()
/*      */   {
/*  863 */     return this.column.isUseAscender();
/*      */   }
/*      */ 
/*      */   public void setUseAscender(boolean useAscender)
/*      */   {
/*  872 */     this.column.setUseAscender(useAscender);
/*      */   }
/*      */ 
/*      */   public boolean isUseDescender()
/*      */   {
/*  882 */     return this.useDescender;
/*      */   }
/*      */ 
/*      */   public void setUseDescender(boolean useDescender)
/*      */   {
/*  891 */     this.useDescender = useDescender;
/*      */   }
/*      */ 
/*      */   public ColumnText getColumn()
/*      */   {
/*  900 */     return this.column;
/*      */   }
/*      */ 
/*      */   public List<Element> getCompositeElements()
/*      */   {
/*  910 */     return getColumn().compositeElements;
/*      */   }
/*      */ 
/*      */   public void setColumn(ColumnText column)
/*      */   {
/*  919 */     this.column = column;
/*      */   }
/*      */ 
/*      */   public int getRotation()
/*      */   {
/*  929 */     return this.rotation;
/*      */   }
/*      */ 
/*      */   public void setRotation(int rotation)
/*      */   {
/*  939 */     rotation %= 360;
/*  940 */     if (rotation < 0)
/*  941 */       rotation += 360;
/*  942 */     if (rotation % 90 != 0)
/*  943 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("rotation.must.be.a.multiple.of.90", new Object[0]));
/*  944 */     this.rotation = rotation;
/*      */   }
/*      */ 
/*      */   public float getMaxHeight()
/*      */   {
/*  953 */     boolean pivoted = (getRotation() == 90) || (getRotation() == 270);
/*  954 */     Image img = getImage();
/*  955 */     if (img != null) {
/*  956 */       img.scalePercent(100.0F);
/*  957 */       float refWidth = pivoted ? img.getScaledHeight() : img.getScaledWidth();
/*  958 */       float scale = (getRight() - getEffectivePaddingRight() - getEffectivePaddingLeft() - getLeft()) / refWidth;
/*      */ 
/*  960 */       img.scalePercent(scale * 100.0F);
/*  961 */       float refHeight = pivoted ? img.getScaledWidth() : img.getScaledHeight();
/*  962 */       setBottom(getTop() - getEffectivePaddingTop() - getEffectivePaddingBottom() - refHeight);
/*      */     }
/*  965 */     else if (((pivoted) && (hasFixedHeight())) || (getColumn() == null)) {
/*  966 */       setBottom(getTop() - getFixedHeight());
/*      */     } else {
/*  968 */       ColumnText ct = ColumnText.duplicate(getColumn());
/*      */       float bottom;
/*      */       float right;
/*      */       float top;
/*      */       float left;
/*      */       float bottom;
/*  970 */       if (pivoted) {
/*  971 */         float right = 20000.0F;
/*  972 */         float top = getRight() - getEffectivePaddingRight();
/*  973 */         float left = 0.0F;
/*  974 */         bottom = getLeft() + getEffectivePaddingLeft();
/*      */       }
/*      */       else {
/*  977 */         right = isNoWrap() ? 20000.0F : getRight() - getEffectivePaddingRight();
/*  978 */         top = getTop() - getEffectivePaddingTop();
/*  979 */         left = getLeft() + getEffectivePaddingLeft();
/*  980 */         bottom = hasFixedHeight() ? getTop() + getEffectivePaddingBottom() - getFixedHeight() : -1.073742E+009F;
/*      */       }
/*  982 */       PdfPRow.setColumn(ct, left, bottom, right, top);
/*      */       try {
/*  984 */         ct.go(true);
/*      */       } catch (DocumentException e) {
/*  986 */         throw new ExceptionConverter(e);
/*      */       }
/*  988 */       if (pivoted) {
/*  989 */         setBottom(getTop() - getEffectivePaddingTop() - getEffectivePaddingBottom() - ct.getFilledWidth());
/*      */       } else {
/*  991 */         float yLine = ct.getYLine();
/*  992 */         if (isUseDescender())
/*  993 */           yLine += ct.getDescender();
/*  994 */         setBottom(yLine - getEffectivePaddingBottom());
/*      */       }
/*      */     }
/*      */ 
/*  998 */     float height = getHeight();
/*  999 */     if (height == getEffectivePaddingTop() + getEffectivePaddingBottom())
/* 1000 */       height = 0.0F;
/* 1001 */     if (hasFixedHeight())
/* 1002 */       height = getFixedHeight();
/* 1003 */     else if ((hasMinimumHeight()) && (height < getMinimumHeight()))
/* 1004 */       height = getMinimumHeight();
/* 1005 */     return height;
/*      */   }
/*      */ 
/*      */   public PdfObject getAccessibleAttribute(PdfName key) {
/* 1009 */     if (this.accessibleAttributes != null) {
/* 1010 */       return (PdfObject)this.accessibleAttributes.get(key);
/*      */     }
/* 1012 */     return null;
/*      */   }
/*      */ 
/*      */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/* 1016 */     if (this.accessibleAttributes == null)
/* 1017 */       this.accessibleAttributes = new HashMap();
/* 1018 */     this.accessibleAttributes.put(key, value);
/*      */   }
/*      */ 
/*      */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/* 1022 */     return this.accessibleAttributes;
/*      */   }
/*      */ 
/*      */   public PdfName getRole() {
/* 1026 */     return this.role;
/*      */   }
/*      */ 
/*      */   public void setRole(PdfName role) {
/* 1030 */     this.role = role;
/*      */   }
/*      */ 
/*      */   public AccessibleElementId getId() {
/* 1034 */     return this.id;
/*      */   }
/*      */ 
/*      */   public void setId(AccessibleElementId id) {
/* 1038 */     this.id = id;
/*      */   }
/*      */ 
/*      */   public boolean isInline() {
/* 1042 */     return false;
/*      */   }
/*      */ 
/*      */   public void addHeader(PdfPHeaderCell header) {
/* 1046 */     if (this.headers == null)
/* 1047 */       this.headers = new ArrayList();
/* 1048 */     this.headers.add(header);
/*      */   }
/*      */ 
/*      */   public ArrayList<PdfPHeaderCell> getHeaders() {
/* 1052 */     return this.headers;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPCell
 * JD-Core Version:    0.6.2
 */