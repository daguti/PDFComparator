/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.AccessibleElementId;
/*      */ import com.itextpdf.text.Chunk;
/*      */ import com.itextpdf.text.DocumentException;
/*      */ import com.itextpdf.text.ElementListener;
/*      */ import com.itextpdf.text.Image;
/*      */ import com.itextpdf.text.LargeElement;
/*      */ import com.itextpdf.text.Phrase;
/*      */ import com.itextpdf.text.Rectangle;
/*      */ import com.itextpdf.text.api.Spaceable;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.log.Logger;
/*      */ import com.itextpdf.text.log.LoggerFactory;
/*      */ import com.itextpdf.text.pdf.events.PdfPTableEventForwarder;
/*      */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ 
/*      */ public class PdfPTable
/*      */   implements LargeElement, Spaceable, IAccessibleElement
/*      */ {
/*   80 */   private final Logger LOGGER = LoggerFactory.getLogger(PdfPTable.class);
/*      */   public static final int BASECANVAS = 0;
/*      */   public static final int BACKGROUNDCANVAS = 1;
/*      */   public static final int LINECANVAS = 2;
/*      */   public static final int TEXTCANVAS = 3;
/*  101 */   protected ArrayList<PdfPRow> rows = new ArrayList();
/*  102 */   protected float totalHeight = 0.0F;
/*      */   protected PdfPCell[] currentRow;
/*  109 */   protected int currentColIdx = 0;
/*  110 */   protected PdfPCell defaultCell = new PdfPCell((Phrase)null);
/*  111 */   protected float totalWidth = 0.0F;
/*      */   protected float[] relativeWidths;
/*      */   protected float[] absoluteWidths;
/*      */   protected PdfPTableEvent tableEvent;
/*      */   protected int headerRows;
/*  124 */   protected float widthPercentage = 80.0F;
/*      */ 
/*  129 */   private int horizontalAlignment = 1;
/*      */ 
/*  134 */   private boolean skipFirstHeader = false;
/*      */ 
/*  140 */   private boolean skipLastFooter = false;
/*      */ 
/*  142 */   protected boolean isColspan = false;
/*      */ 
/*  144 */   protected int runDirection = 0;
/*      */ 
/*  149 */   private boolean lockedWidth = false;
/*      */ 
/*  154 */   private boolean splitRows = true;
/*      */   protected float spacingBefore;
/*      */   protected float spacingAfter;
/*  169 */   private boolean[] extendLastRow = { false, false };
/*      */   private boolean headersInEvent;
/*  179 */   private boolean splitLate = true;
/*      */   private boolean keepTogether;
/*  192 */   protected boolean complete = true;
/*      */   private int footerRows;
/*  204 */   protected boolean rowCompleted = true;
/*      */ 
/*  206 */   protected boolean loopCheck = true;
/*  207 */   protected boolean rowsNotChecked = true;
/*      */ 
/*  209 */   protected PdfName role = PdfName.TABLE;
/*  210 */   protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
/*  211 */   protected AccessibleElementId id = new AccessibleElementId();
/*  212 */   private PdfPTableHeader header = null;
/*  213 */   private PdfPTableBody body = null;
/*  214 */   private PdfPTableFooter footer = null;
/*      */ 
/*      */   protected PdfPTable()
/*      */   {
/*      */   }
/*      */ 
/*      */   public PdfPTable(float[] relativeWidths)
/*      */   {
/*  226 */     if (relativeWidths == null)
/*  227 */       throw new NullPointerException(MessageLocalization.getComposedMessage("the.widths.array.in.pdfptable.constructor.can.not.be.null", new Object[0]));
/*  228 */     if (relativeWidths.length == 0)
/*  229 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.widths.array.in.pdfptable.constructor.can.not.have.zero.length", new Object[0]));
/*  230 */     this.relativeWidths = new float[relativeWidths.length];
/*  231 */     System.arraycopy(relativeWidths, 0, this.relativeWidths, 0, relativeWidths.length);
/*  232 */     this.absoluteWidths = new float[relativeWidths.length];
/*  233 */     calculateWidths();
/*  234 */     this.currentRow = new PdfPCell[this.absoluteWidths.length];
/*  235 */     this.keepTogether = false;
/*      */   }
/*      */ 
/*      */   public PdfPTable(int numColumns)
/*      */   {
/*  244 */     if (numColumns <= 0)
/*  245 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.number.of.columns.in.pdfptable.constructor.must.be.greater.than.zero", new Object[0]));
/*  246 */     this.relativeWidths = new float[numColumns];
/*  247 */     for (int k = 0; k < numColumns; k++)
/*  248 */       this.relativeWidths[k] = 1.0F;
/*  249 */     this.absoluteWidths = new float[this.relativeWidths.length];
/*  250 */     calculateWidths();
/*  251 */     this.currentRow = new PdfPCell[this.absoluteWidths.length];
/*  252 */     this.keepTogether = false;
/*      */   }
/*      */ 
/*      */   public PdfPTable(PdfPTable table)
/*      */   {
/*  261 */     copyFormat(table);
/*  262 */     for (int k = 0; (k < this.currentRow.length) && 
/*  263 */       (table.currentRow[k] != null); k++)
/*      */     {
/*  265 */       this.currentRow[k] = new PdfPCell(table.currentRow[k]);
/*      */     }
/*  267 */     for (int k = 0; k < table.rows.size(); k++) {
/*  268 */       PdfPRow row = (PdfPRow)table.rows.get(k);
/*  269 */       if (row != null)
/*  270 */         row = new PdfPRow(row);
/*  271 */       this.rows.add(row);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static PdfPTable shallowCopy(PdfPTable table)
/*      */   {
/*  282 */     PdfPTable nt = new PdfPTable();
/*  283 */     nt.copyFormat(table);
/*  284 */     return nt;
/*      */   }
/*      */ 
/*      */   protected void copyFormat(PdfPTable sourceTable)
/*      */   {
/*  294 */     this.rowsNotChecked = sourceTable.rowsNotChecked;
/*  295 */     this.relativeWidths = new float[sourceTable.getNumberOfColumns()];
/*  296 */     this.absoluteWidths = new float[sourceTable.getNumberOfColumns()];
/*  297 */     System.arraycopy(sourceTable.relativeWidths, 0, this.relativeWidths, 0, getNumberOfColumns());
/*  298 */     System.arraycopy(sourceTable.absoluteWidths, 0, this.absoluteWidths, 0, getNumberOfColumns());
/*  299 */     this.totalWidth = sourceTable.totalWidth;
/*  300 */     this.totalHeight = sourceTable.totalHeight;
/*  301 */     this.currentColIdx = 0;
/*  302 */     this.tableEvent = sourceTable.tableEvent;
/*  303 */     this.runDirection = sourceTable.runDirection;
/*  304 */     if ((sourceTable.defaultCell instanceof PdfPHeaderCell))
/*  305 */       this.defaultCell = new PdfPHeaderCell((PdfPHeaderCell)sourceTable.defaultCell);
/*      */     else
/*  307 */       this.defaultCell = new PdfPCell(sourceTable.defaultCell);
/*  308 */     this.currentRow = new PdfPCell[sourceTable.currentRow.length];
/*  309 */     this.isColspan = sourceTable.isColspan;
/*  310 */     this.splitRows = sourceTable.splitRows;
/*  311 */     this.spacingAfter = sourceTable.spacingAfter;
/*  312 */     this.spacingBefore = sourceTable.spacingBefore;
/*  313 */     this.headerRows = sourceTable.headerRows;
/*  314 */     this.footerRows = sourceTable.footerRows;
/*  315 */     this.lockedWidth = sourceTable.lockedWidth;
/*  316 */     this.extendLastRow = sourceTable.extendLastRow;
/*  317 */     this.headersInEvent = sourceTable.headersInEvent;
/*  318 */     this.widthPercentage = sourceTable.widthPercentage;
/*  319 */     this.splitLate = sourceTable.splitLate;
/*  320 */     this.skipFirstHeader = sourceTable.skipFirstHeader;
/*  321 */     this.skipLastFooter = sourceTable.skipLastFooter;
/*  322 */     this.horizontalAlignment = sourceTable.horizontalAlignment;
/*  323 */     this.keepTogether = sourceTable.keepTogether;
/*  324 */     this.complete = sourceTable.complete;
/*  325 */     this.loopCheck = sourceTable.loopCheck;
/*  326 */     this.id = sourceTable.id;
/*  327 */     this.role = sourceTable.role;
/*  328 */     if (sourceTable.accessibleAttributes != null)
/*  329 */       this.accessibleAttributes = new HashMap(sourceTable.accessibleAttributes);
/*  330 */     this.header = sourceTable.getHeader();
/*  331 */     this.body = sourceTable.getBody();
/*  332 */     this.footer = sourceTable.getFooter();
/*      */   }
/*      */ 
/*      */   public void setWidths(float[] relativeWidths)
/*      */     throws DocumentException
/*      */   {
/*  343 */     if (relativeWidths.length != getNumberOfColumns())
/*  344 */       throw new DocumentException(MessageLocalization.getComposedMessage("wrong.number.of.columns", new Object[0]));
/*  345 */     this.relativeWidths = new float[relativeWidths.length];
/*  346 */     System.arraycopy(relativeWidths, 0, this.relativeWidths, 0, relativeWidths.length);
/*  347 */     this.absoluteWidths = new float[relativeWidths.length];
/*  348 */     this.totalHeight = 0.0F;
/*  349 */     calculateWidths();
/*  350 */     calculateHeights();
/*      */   }
/*      */ 
/*      */   public void setWidths(int[] relativeWidths)
/*      */     throws DocumentException
/*      */   {
/*  361 */     float[] tb = new float[relativeWidths.length];
/*  362 */     for (int k = 0; k < relativeWidths.length; k++)
/*  363 */       tb[k] = relativeWidths[k];
/*  364 */     setWidths(tb);
/*      */   }
/*      */ 
/*      */   protected void calculateWidths()
/*      */   {
/*  371 */     if (this.totalWidth <= 0.0F)
/*  372 */       return;
/*  373 */     float total = 0.0F;
/*  374 */     int numCols = getNumberOfColumns();
/*  375 */     for (int k = 0; k < numCols; k++)
/*  376 */       total += this.relativeWidths[k];
/*  377 */     for (int k = 0; k < numCols; k++)
/*  378 */       this.absoluteWidths[k] = (this.totalWidth * this.relativeWidths[k] / total);
/*      */   }
/*      */ 
/*      */   public void setTotalWidth(float totalWidth)
/*      */   {
/*  387 */     if (this.totalWidth == totalWidth)
/*  388 */       return;
/*  389 */     this.totalWidth = totalWidth;
/*  390 */     this.totalHeight = 0.0F;
/*  391 */     calculateWidths();
/*  392 */     calculateHeights();
/*      */   }
/*      */ 
/*      */   public void setTotalWidth(float[] columnWidth)
/*      */     throws DocumentException
/*      */   {
/*  403 */     if (columnWidth.length != getNumberOfColumns())
/*  404 */       throw new DocumentException(MessageLocalization.getComposedMessage("wrong.number.of.columns", new Object[0]));
/*  405 */     this.totalWidth = 0.0F;
/*  406 */     for (int k = 0; k < columnWidth.length; k++)
/*  407 */       this.totalWidth += columnWidth[k];
/*  408 */     setWidths(columnWidth);
/*      */   }
/*      */ 
/*      */   public void setWidthPercentage(float[] columnWidth, Rectangle pageSize)
/*      */     throws DocumentException
/*      */   {
/*  419 */     if (columnWidth.length != getNumberOfColumns())
/*  420 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("wrong.number.of.columns", new Object[0]));
/*  421 */     float totalWidth = 0.0F;
/*  422 */     for (int k = 0; k < columnWidth.length; k++)
/*  423 */       totalWidth += columnWidth[k];
/*  424 */     this.widthPercentage = (totalWidth / (pageSize.getRight() - pageSize.getLeft()) * 100.0F);
/*  425 */     setWidths(columnWidth);
/*      */   }
/*      */ 
/*      */   public float getTotalWidth()
/*      */   {
/*  434 */     return this.totalWidth;
/*      */   }
/*      */ 
/*      */   public float calculateHeights()
/*      */   {
/*  445 */     if (this.totalWidth <= 0.0F)
/*  446 */       return 0.0F;
/*  447 */     this.totalHeight = 0.0F;
/*  448 */     for (int k = 0; k < this.rows.size(); k++) {
/*  449 */       this.totalHeight += getRowHeight(k, true);
/*      */     }
/*  451 */     return this.totalHeight;
/*      */   }
/*      */ 
/*      */   public void resetColumnCount(int newColCount)
/*      */   {
/*  461 */     if (newColCount <= 0)
/*  462 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.number.of.columns.in.pdfptable.constructor.must.be.greater.than.zero", new Object[0]));
/*  463 */     this.relativeWidths = new float[newColCount];
/*  464 */     for (int k = 0; k < newColCount; k++)
/*  465 */       this.relativeWidths[k] = 1.0F;
/*  466 */     this.absoluteWidths = new float[this.relativeWidths.length];
/*  467 */     calculateWidths();
/*  468 */     this.currentRow = new PdfPCell[this.absoluteWidths.length];
/*  469 */     this.totalHeight = 0.0F;
/*      */   }
/*      */ 
/*      */   public PdfPCell getDefaultCell()
/*      */   {
/*  480 */     return this.defaultCell;
/*      */   }
/*      */ 
/*      */   public PdfPCell addCell(PdfPCell cell)
/*      */   {
/*  489 */     this.rowCompleted = false;
/*      */     PdfPCell ncell;
/*      */     PdfPCell ncell;
/*  491 */     if ((cell instanceof PdfPHeaderCell))
/*  492 */       ncell = new PdfPHeaderCell((PdfPHeaderCell)cell);
/*      */     else {
/*  494 */       ncell = new PdfPCell(cell);
/*      */     }
/*  496 */     int colspan = ncell.getColspan();
/*  497 */     colspan = Math.max(colspan, 1);
/*  498 */     colspan = Math.min(colspan, this.currentRow.length - this.currentColIdx);
/*  499 */     ncell.setColspan(colspan);
/*      */ 
/*  501 */     if (colspan != 1)
/*  502 */       this.isColspan = true;
/*  503 */     int rdir = ncell.getRunDirection();
/*  504 */     if (rdir == 0) {
/*  505 */       ncell.setRunDirection(this.runDirection);
/*      */     }
/*  507 */     skipColsWithRowspanAbove();
/*      */ 
/*  509 */     boolean cellAdded = false;
/*  510 */     if (this.currentColIdx < this.currentRow.length) {
/*  511 */       this.currentRow[this.currentColIdx] = ncell;
/*  512 */       this.currentColIdx += colspan;
/*  513 */       cellAdded = true;
/*      */     }
/*      */ 
/*  516 */     skipColsWithRowspanAbove();
/*      */ 
/*  518 */     while (this.currentColIdx >= this.currentRow.length) {
/*  519 */       int numCols = getNumberOfColumns();
/*  520 */       if (this.runDirection == 3) {
/*  521 */         PdfPCell[] rtlRow = new PdfPCell[numCols];
/*  522 */         int rev = this.currentRow.length;
/*  523 */         for (int k = 0; k < this.currentRow.length; k++) {
/*  524 */           PdfPCell rcell = this.currentRow[k];
/*  525 */           int cspan = rcell.getColspan();
/*  526 */           rev -= cspan;
/*  527 */           rtlRow[rev] = rcell;
/*  528 */           k += cspan - 1;
/*      */         }
/*  530 */         this.currentRow = rtlRow;
/*      */       }
/*  532 */       PdfPRow row = new PdfPRow(this.currentRow);
/*  533 */       if (this.totalWidth > 0.0F) {
/*  534 */         row.setWidths(this.absoluteWidths);
/*  535 */         this.totalHeight += row.getMaxHeights();
/*      */       }
/*  537 */       this.rows.add(row);
/*  538 */       this.currentRow = new PdfPCell[numCols];
/*  539 */       this.currentColIdx = 0;
/*  540 */       skipColsWithRowspanAbove();
/*  541 */       this.rowCompleted = true;
/*      */     }
/*      */ 
/*  544 */     if (!cellAdded) {
/*  545 */       this.currentRow[this.currentColIdx] = ncell;
/*  546 */       this.currentColIdx += colspan;
/*      */     }
/*  548 */     return ncell;
/*      */   }
/*      */ 
/*      */   private void skipColsWithRowspanAbove()
/*      */   {
/*  558 */     int direction = 1;
/*  559 */     if (this.runDirection == 3)
/*  560 */       direction = -1;
/*  561 */     while (rowSpanAbove(this.rows.size(), this.currentColIdx))
/*  562 */       this.currentColIdx += direction;
/*      */   }
/*      */ 
/*      */   PdfPCell cellAt(int row, int col)
/*      */   {
/*  573 */     PdfPCell[] cells = ((PdfPRow)this.rows.get(row)).getCells();
/*  574 */     for (int i = 0; i < cells.length; i++) {
/*  575 */       if ((cells[i] != null) && 
/*  576 */         (col >= i) && (col < i + cells[i].getColspan())) {
/*  577 */         return cells[i];
/*      */       }
/*      */     }
/*      */ 
/*  581 */     return null;
/*      */   }
/*      */ 
/*      */   boolean rowSpanAbove(int currRow, int currCol)
/*      */   {
/*  593 */     if ((currCol >= getNumberOfColumns()) || (currCol < 0) || (currRow < 1))
/*      */     {
/*  596 */       return false;
/*  597 */     }int row = currRow - 1;
/*  598 */     PdfPRow aboveRow = (PdfPRow)this.rows.get(row);
/*  599 */     if (aboveRow == null)
/*  600 */       return false;
/*  601 */     PdfPCell aboveCell = cellAt(row, currCol);
/*  602 */     while ((aboveCell == null) && (row > 0)) {
/*  603 */       aboveRow = (PdfPRow)this.rows.get(--row);
/*  604 */       if (aboveRow == null)
/*  605 */         return false;
/*  606 */       aboveCell = cellAt(row, currCol);
/*      */     }
/*      */ 
/*  609 */     int distance = currRow - row;
/*      */ 
/*  611 */     if ((aboveCell.getRowspan() == 1) && (distance > 1)) {
/*  612 */       int col = currCol - 1;
/*  613 */       aboveRow = (PdfPRow)this.rows.get(row + 1);
/*  614 */       distance--;
/*  615 */       aboveCell = aboveRow.getCells()[col];
/*  616 */       while ((aboveCell == null) && (col > 0)) {
/*  617 */         aboveCell = aboveRow.getCells()[(--col)];
/*      */       }
/*      */     }
/*  620 */     return (aboveCell != null) && (aboveCell.getRowspan() > distance);
/*      */   }
/*      */ 
/*      */   public void addCell(String text)
/*      */   {
/*  630 */     addCell(new Phrase(text));
/*      */   }
/*      */ 
/*      */   public void addCell(PdfPTable table)
/*      */   {
/*  639 */     this.defaultCell.setTable(table);
/*  640 */     PdfPCell newCell = addCell(this.defaultCell);
/*  641 */     newCell.id = new AccessibleElementId();
/*  642 */     this.defaultCell.setTable(null);
/*      */   }
/*      */ 
/*      */   public void addCell(Image image)
/*      */   {
/*  652 */     this.defaultCell.setImage(image);
/*  653 */     PdfPCell newCell = addCell(this.defaultCell);
/*  654 */     newCell.id = new AccessibleElementId();
/*  655 */     this.defaultCell.setImage(null);
/*      */   }
/*      */ 
/*      */   public void addCell(Phrase phrase)
/*      */   {
/*  664 */     this.defaultCell.setPhrase(phrase);
/*  665 */     PdfPCell newCell = addCell(this.defaultCell);
/*  666 */     newCell.id = new AccessibleElementId();
/*  667 */     this.defaultCell.setPhrase(null);
/*      */   }
/*      */ 
/*      */   public float writeSelectedRows(int rowStart, int rowEnd, float xPos, float yPos, PdfContentByte[] canvases)
/*      */   {
/*  685 */     return writeSelectedRows(0, -1, rowStart, rowEnd, xPos, yPos, canvases);
/*      */   }
/*      */ 
/*      */   public float writeSelectedRows(int colStart, int colEnd, int rowStart, int rowEnd, float xPos, float yPos, PdfContentByte[] canvases)
/*      */   {
/*  709 */     return writeSelectedRows(colStart, colEnd, rowStart, rowEnd, xPos, yPos, canvases, true);
/*      */   }
/*      */ 
/*      */   public float writeSelectedRows(int colStart, int colEnd, int rowStart, int rowEnd, float xPos, float yPos, PdfContentByte[] canvases, boolean reusable)
/*      */   {
/*  736 */     if (this.totalWidth <= 0.0F) {
/*  737 */       throw new RuntimeException(MessageLocalization.getComposedMessage("the.table.width.must.be.greater.than.zero", new Object[0]));
/*      */     }
/*  739 */     int totalRows = this.rows.size();
/*  740 */     if (rowStart < 0)
/*  741 */       rowStart = 0;
/*  742 */     if (rowEnd < 0)
/*  743 */       rowEnd = totalRows;
/*      */     else
/*  745 */       rowEnd = Math.min(rowEnd, totalRows);
/*  746 */     if (rowStart >= rowEnd) {
/*  747 */       return yPos;
/*      */     }
/*  749 */     int totalCols = getNumberOfColumns();
/*  750 */     if (colStart < 0)
/*  751 */       colStart = 0;
/*      */     else
/*  753 */       colStart = Math.min(colStart, totalCols);
/*  754 */     if (colEnd < 0)
/*  755 */       colEnd = totalCols;
/*      */     else {
/*  757 */       colEnd = Math.min(colEnd, totalCols);
/*      */     }
/*  759 */     this.LOGGER.info(String.format("Writing row %s to %s; column %s to %s", new Object[] { Integer.valueOf(rowStart), Integer.valueOf(rowEnd), Integer.valueOf(colStart), Integer.valueOf(colEnd) }));
/*      */ 
/*  761 */     float yPosStart = yPos;
/*      */ 
/*  763 */     PdfPTableBody currentBlock = null;
/*  764 */     if (this.rowsNotChecked)
/*  765 */       getFittingRows(3.4028235E+38F, rowStart);
/*  766 */     List rows = getRows(rowStart, rowEnd);
/*  767 */     int k = rowStart;
/*  768 */     for (PdfPRow row : rows) {
/*  769 */       if ((getHeader().rows != null) && (getHeader().rows.contains(row)) && (currentBlock == null))
/*  770 */         currentBlock = openTableBlock(getHeader(), canvases[3]);
/*  771 */       else if ((getBody().rows != null) && (getBody().rows.contains(row)) && (currentBlock == null))
/*  772 */         currentBlock = openTableBlock(getBody(), canvases[3]);
/*  773 */       else if ((getFooter().rows != null) && (getFooter().rows.contains(row)) && (currentBlock == null)) {
/*  774 */         currentBlock = openTableBlock(getFooter(), canvases[3]);
/*      */       }
/*  776 */       if (row != null) {
/*  777 */         row.writeCells(colStart, colEnd, xPos, yPos, canvases, reusable);
/*  778 */         yPos -= row.getMaxHeights();
/*      */       }
/*  780 */       if ((getHeader().rows != null) && (getHeader().rows.contains(row)) && ((k == rowEnd - 1) || (!getHeader().rows.contains(rows.get(k + 1)))))
/*  781 */         currentBlock = closeTableBlock(getHeader(), canvases[3]);
/*  782 */       else if ((getBody().rows != null) && (getBody().rows.contains(row)) && ((k == rowEnd - 1) || (!getBody().rows.contains(rows.get(k + 1)))))
/*  783 */         currentBlock = closeTableBlock(getBody(), canvases[3]);
/*  784 */       else if ((getFooter().rows != null) && (getFooter().rows.contains(row)) && ((k == rowEnd - 1) || (!getFooter().rows.contains(rows.get(k + 1))))) {
/*  785 */         currentBlock = closeTableBlock(getFooter(), canvases[3]);
/*      */       }
/*  787 */       k++;
/*      */     }
/*      */ 
/*  790 */     if ((this.tableEvent != null) && (colStart == 0) && (colEnd == totalCols)) {
/*  791 */       float[] heights = new float[rowEnd - rowStart + 1];
/*  792 */       heights[0] = yPosStart;
/*  793 */       for (k = rowStart; k < rowEnd; k++) {
/*  794 */         PdfPRow row = (PdfPRow)rows.get(k);
/*  795 */         float hr = 0.0F;
/*  796 */         if (row != null)
/*  797 */           hr = row.getMaxHeights();
/*  798 */         heights[(k - rowStart + 1)] = (heights[(k - rowStart)] - hr);
/*      */       }
/*  800 */       this.tableEvent.tableLayout(this, getEventWidths(xPos, rowStart, rowEnd, this.headersInEvent), heights, this.headersInEvent ? this.headerRows : 0, rowStart, canvases);
/*      */     }
/*      */ 
/*  803 */     return yPos;
/*      */   }
/*      */ 
/*      */   private PdfPTableBody openTableBlock(PdfPTableBody block, PdfContentByte canvas) {
/*  807 */     if (canvas.writer.getStandardStructElems().contains(block.getRole())) {
/*  808 */       canvas.openMCBlock(block);
/*  809 */       return block;
/*      */     }
/*  811 */     return null;
/*      */   }
/*      */ 
/*      */   private PdfPTableBody closeTableBlock(PdfPTableBody block, PdfContentByte canvas) {
/*  815 */     if (canvas.writer.getStandardStructElems().contains(block.getRole()))
/*  816 */       canvas.closeMCBlock(block);
/*  817 */     return null;
/*      */   }
/*      */ 
/*      */   public float writeSelectedRows(int rowStart, int rowEnd, float xPos, float yPos, PdfContentByte canvas)
/*      */   {
/*  833 */     return writeSelectedRows(0, -1, rowStart, rowEnd, xPos, yPos, canvas);
/*      */   }
/*      */ 
/*      */   public float writeSelectedRows(int colStart, int colEnd, int rowStart, int rowEnd, float xPos, float yPos, PdfContentByte canvas)
/*      */   {
/*  855 */     return writeSelectedRows(colStart, colEnd, rowStart, rowEnd, xPos, yPos, canvas, true);
/*      */   }
/*      */ 
/*      */   public float writeSelectedRows(int colStart, int colEnd, int rowStart, int rowEnd, float xPos, float yPos, PdfContentByte canvas, boolean reusable)
/*      */   {
/*  881 */     int totalCols = getNumberOfColumns();
/*  882 */     if (colStart < 0)
/*  883 */       colStart = 0;
/*      */     else {
/*  885 */       colStart = Math.min(colStart, totalCols);
/*      */     }
/*  887 */     if (colEnd < 0)
/*  888 */       colEnd = totalCols;
/*      */     else {
/*  890 */       colEnd = Math.min(colEnd, totalCols);
/*      */     }
/*  892 */     boolean clip = (colStart != 0) || (colEnd != totalCols);
/*      */ 
/*  894 */     if (clip) {
/*  895 */       float w = 0.0F;
/*  896 */       for (int k = colStart; k < colEnd; k++)
/*  897 */         w += this.absoluteWidths[k];
/*  898 */       canvas.saveState();
/*  899 */       float lx = colStart == 0 ? 10000.0F : 0.0F;
/*  900 */       float rx = colEnd == totalCols ? 10000.0F : 0.0F;
/*  901 */       canvas.rectangle(xPos - lx, -10000.0F, w + lx + rx, 20000.0F);
/*  902 */       canvas.clip();
/*  903 */       canvas.newPath();
/*      */     }
/*      */ 
/*  906 */     PdfContentByte[] canvases = beginWritingRows(canvas);
/*  907 */     float y = writeSelectedRows(colStart, colEnd, rowStart, rowEnd, xPos, yPos, canvases, reusable);
/*  908 */     endWritingRows(canvases);
/*      */ 
/*  910 */     if (clip) {
/*  911 */       canvas.restoreState();
/*      */     }
/*  913 */     return y;
/*      */   }
/*      */ 
/*      */   public static PdfContentByte[] beginWritingRows(PdfContentByte canvas)
/*      */   {
/*  935 */     return new PdfContentByte[] { canvas, canvas.getDuplicate(), canvas.getDuplicate(), canvas.getDuplicate() };
/*      */   }
/*      */ 
/*      */   public static void endWritingRows(PdfContentByte[] canvases)
/*      */   {
/*  949 */     PdfContentByte canvas = canvases[0];
/*  950 */     PdfArtifact artifact = new PdfArtifact();
/*  951 */     canvas.openMCBlock(artifact);
/*  952 */     canvas.saveState();
/*  953 */     canvas.add(canvases[1]);
/*  954 */     canvas.restoreState();
/*  955 */     canvas.saveState();
/*  956 */     canvas.setLineCap(2);
/*  957 */     canvas.resetRGBColorStroke();
/*  958 */     canvas.add(canvases[2]);
/*  959 */     canvas.restoreState();
/*  960 */     canvas.closeMCBlock(artifact);
/*  961 */     canvas.add(canvases[3]);
/*      */   }
/*      */ 
/*      */   public int size()
/*      */   {
/*  970 */     return this.rows.size();
/*      */   }
/*      */ 
/*      */   public float getTotalHeight()
/*      */   {
/*  979 */     return this.totalHeight;
/*      */   }
/*      */ 
/*      */   public float getRowHeight(int idx)
/*      */   {
/*  989 */     return getRowHeight(idx, false);
/*      */   }
/*      */ 
/*      */   protected float getRowHeight(int idx, boolean firsttime)
/*      */   {
/* 1001 */     if ((this.totalWidth <= 0.0F) || (idx < 0) || (idx >= this.rows.size()))
/* 1002 */       return 0.0F;
/* 1003 */     PdfPRow row = (PdfPRow)this.rows.get(idx);
/* 1004 */     if (row == null)
/* 1005 */       return 0.0F;
/* 1006 */     if (firsttime)
/* 1007 */       row.setWidths(this.absoluteWidths);
/* 1008 */     float height = row.getMaxHeights();
/*      */ 
/* 1011 */     for (int i = 0; i < this.relativeWidths.length; i++)
/* 1012 */       if (rowSpanAbove(idx, i))
/*      */       {
/* 1014 */         int rs = 1;
/* 1015 */         while (rowSpanAbove(idx - rs, i)) {
/* 1016 */           rs++;
/*      */         }
/* 1018 */         PdfPRow tmprow = (PdfPRow)this.rows.get(idx - rs);
/* 1019 */         PdfPCell cell = tmprow.getCells()[i];
/* 1020 */         float tmp = 0.0F;
/* 1021 */         if ((cell != null) && (cell.getRowspan() == rs + 1)) {
/* 1022 */           tmp = cell.getMaxHeight();
/* 1023 */           while (rs > 0) {
/* 1024 */             tmp -= getRowHeight(idx - rs);
/* 1025 */             rs--;
/*      */           }
/*      */         }
/* 1028 */         if (tmp > height)
/* 1029 */           height = tmp;
/*      */       }
/* 1031 */     row.setMaxHeights(height);
/* 1032 */     return height;
/*      */   }
/*      */ 
/*      */   public float getRowspanHeight(int rowIndex, int cellIndex)
/*      */   {
/* 1045 */     if ((this.totalWidth <= 0.0F) || (rowIndex < 0) || (rowIndex >= this.rows.size()))
/* 1046 */       return 0.0F;
/* 1047 */     PdfPRow row = (PdfPRow)this.rows.get(rowIndex);
/* 1048 */     if ((row == null) || (cellIndex >= row.getCells().length))
/* 1049 */       return 0.0F;
/* 1050 */     PdfPCell cell = row.getCells()[cellIndex];
/* 1051 */     if (cell == null)
/* 1052 */       return 0.0F;
/* 1053 */     float rowspanHeight = 0.0F;
/* 1054 */     for (int j = 0; j < cell.getRowspan(); j++) {
/* 1055 */       rowspanHeight += getRowHeight(rowIndex + j);
/*      */     }
/* 1057 */     return rowspanHeight;
/*      */   }
/*      */ 
/*      */   public boolean hasRowspan(int rowIdx)
/*      */   {
/* 1066 */     if ((rowIdx < this.rows.size()) && (getRow(rowIdx).hasRowspan())) {
/* 1067 */       return true;
/*      */     }
/* 1069 */     PdfPRow previousRow = rowIdx > 0 ? getRow(rowIdx - 1) : null;
/* 1070 */     if ((previousRow != null) && (previousRow.hasRowspan())) {
/* 1071 */       return true;
/*      */     }
/* 1073 */     for (int i = 0; i < getNumberOfColumns(); i++) {
/* 1074 */       if (rowSpanAbove(rowIdx - 1, i))
/* 1075 */         return true;
/*      */     }
/* 1077 */     return false;
/*      */   }
/*      */ 
/*      */   public void normalizeHeadersFooters()
/*      */   {
/* 1086 */     if (this.footerRows > this.headerRows)
/* 1087 */       this.footerRows = this.headerRows;
/*      */   }
/*      */ 
/*      */   public float getHeaderHeight()
/*      */   {
/* 1097 */     float total = 0.0F;
/* 1098 */     int size = Math.min(this.rows.size(), this.headerRows);
/* 1099 */     for (int k = 0; k < size; k++) {
/* 1100 */       PdfPRow row = (PdfPRow)this.rows.get(k);
/* 1101 */       if (row != null)
/* 1102 */         total += row.getMaxHeights();
/*      */     }
/* 1104 */     return total;
/*      */   }
/*      */ 
/*      */   public float getFooterHeight()
/*      */   {
/* 1115 */     float total = 0.0F;
/* 1116 */     int start = Math.max(0, this.headerRows - this.footerRows);
/* 1117 */     int size = Math.min(this.rows.size(), this.headerRows);
/* 1118 */     for (int k = start; k < size; k++) {
/* 1119 */       PdfPRow row = (PdfPRow)this.rows.get(k);
/* 1120 */       if (row != null)
/* 1121 */         total += row.getMaxHeights();
/*      */     }
/* 1123 */     return total;
/*      */   }
/*      */ 
/*      */   public boolean deleteRow(int rowNumber)
/*      */   {
/* 1133 */     if ((rowNumber < 0) || (rowNumber >= this.rows.size()))
/* 1134 */       return false;
/* 1135 */     if (this.totalWidth > 0.0F) {
/* 1136 */       PdfPRow row = (PdfPRow)this.rows.get(rowNumber);
/* 1137 */       if (row != null)
/* 1138 */         this.totalHeight -= row.getMaxHeights();
/*      */     }
/* 1140 */     this.rows.remove(rowNumber);
/* 1141 */     if (rowNumber < this.headerRows) {
/* 1142 */       this.headerRows -= 1;
/* 1143 */       if (rowNumber >= this.headerRows - this.footerRows)
/* 1144 */         this.footerRows -= 1;
/*      */     }
/* 1146 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean deleteLastRow()
/*      */   {
/* 1155 */     return deleteRow(this.rows.size() - 1);
/*      */   }
/*      */ 
/*      */   public void deleteBodyRows()
/*      */   {
/* 1162 */     ArrayList rows2 = new ArrayList();
/* 1163 */     for (int k = 0; k < this.headerRows; k++)
/* 1164 */       rows2.add(this.rows.get(k));
/* 1165 */     this.rows = rows2;
/* 1166 */     this.totalHeight = 0.0F;
/* 1167 */     if (this.totalWidth > 0.0F)
/* 1168 */       this.totalHeight = getHeaderHeight();
/*      */   }
/*      */ 
/*      */   public int getNumberOfColumns()
/*      */   {
/* 1178 */     return this.relativeWidths.length;
/*      */   }
/*      */ 
/*      */   public int getHeaderRows()
/*      */   {
/* 1187 */     return this.headerRows;
/*      */   }
/*      */ 
/*      */   public void setHeaderRows(int headerRows)
/*      */   {
/* 1198 */     if (headerRows < 0)
/* 1199 */       headerRows = 0;
/* 1200 */     this.headerRows = headerRows;
/*      */   }
/*      */ 
/*      */   public List<Chunk> getChunks()
/*      */   {
/* 1209 */     return new ArrayList();
/*      */   }
/*      */ 
/*      */   public int type()
/*      */   {
/* 1218 */     return 23;
/*      */   }
/*      */ 
/*      */   public boolean isContent()
/*      */   {
/* 1226 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean isNestable()
/*      */   {
/* 1234 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean process(ElementListener listener)
/*      */   {
/*      */     try
/*      */     {
/* 1246 */       return listener.add(this); } catch (DocumentException de) {
/*      */     }
/* 1248 */     return false;
/*      */   }
/*      */ 
/*      */   public float getWidthPercentage()
/*      */   {
/* 1258 */     return this.widthPercentage;
/*      */   }
/*      */ 
/*      */   public void setWidthPercentage(float widthPercentage)
/*      */   {
/* 1267 */     this.widthPercentage = widthPercentage;
/*      */   }
/*      */ 
/*      */   public int getHorizontalAlignment()
/*      */   {
/* 1276 */     return this.horizontalAlignment;
/*      */   }
/*      */ 
/*      */   public void setHorizontalAlignment(int horizontalAlignment)
/*      */   {
/* 1287 */     this.horizontalAlignment = horizontalAlignment;
/*      */   }
/*      */ 
/*      */   public PdfPRow getRow(int idx)
/*      */   {
/* 1297 */     return (PdfPRow)this.rows.get(idx);
/*      */   }
/*      */ 
/*      */   public ArrayList<PdfPRow> getRows()
/*      */   {
/* 1306 */     return this.rows;
/*      */   }
/*      */ 
/*      */   public int getLastCompletedRowIndex()
/*      */   {
/* 1315 */     return this.rows.size() - 1;
/*      */   }
/*      */ 
/*      */   public void setBreakPoints(int[] breakPoints)
/*      */   {
/* 1325 */     keepRowsTogether(0, this.rows.size());
/*      */ 
/* 1327 */     for (int i = 0; i < breakPoints.length; i++)
/* 1328 */       getRow(breakPoints[i]).setMayNotBreak(false);
/*      */   }
/*      */ 
/*      */   public void keepRowsTogether(int[] rows)
/*      */   {
/* 1339 */     for (int i = 0; i < rows.length; i++)
/* 1340 */       getRow(rows[i]).setMayNotBreak(true);
/*      */   }
/*      */ 
/*      */   public void keepRowsTogether(int start, int end)
/*      */   {
/* 1352 */     if (start < end)
/* 1353 */       while (start < end) {
/* 1354 */         getRow(start).setMayNotBreak(true);
/* 1355 */         start++;
/*      */       }
/*      */   }
/*      */ 
/*      */   public void keepRowsTogether(int start)
/*      */   {
/* 1368 */     keepRowsTogether(start, this.rows.size());
/*      */   }
/*      */ 
/*      */   public ArrayList<PdfPRow> getRows(int start, int end)
/*      */   {
/* 1380 */     ArrayList list = new ArrayList();
/* 1381 */     if ((start < 0) || (end > size())) {
/* 1382 */       return list;
/*      */     }
/* 1384 */     for (int i = start; i < end; i++) {
/* 1385 */       list.add(adjustCellsInRow(i, end));
/*      */     }
/* 1387 */     return list;
/*      */   }
/*      */ 
/*      */   protected PdfPRow adjustCellsInRow(int start, int end)
/*      */   {
/* 1398 */     PdfPRow row = getRow(start);
/* 1399 */     if (row.isAdjusted()) return row;
/* 1400 */     row = new PdfPRow(row);
/*      */ 
/* 1402 */     PdfPCell[] cells = row.getCells();
/* 1403 */     for (int i = 0; i < cells.length; i++) {
/* 1404 */       PdfPCell cell = cells[i];
/* 1405 */       if ((cell != null) && (cell.getRowspan() != 1))
/*      */       {
/* 1407 */         int stop = Math.min(end, start + cell.getRowspan());
/* 1408 */         float extra = 0.0F;
/* 1409 */         for (int k = start + 1; k < stop; k++) {
/* 1410 */           extra += getRow(k).getMaxHeights();
/*      */         }
/* 1412 */         row.setExtraHeight(i, extra);
/*      */       }
/*      */     }
/* 1414 */     row.setAdjusted(true);
/* 1415 */     return row;
/*      */   }
/*      */ 
/*      */   public void setTableEvent(PdfPTableEvent event)
/*      */   {
/* 1424 */     if (event == null) {
/* 1425 */       this.tableEvent = null;
/* 1426 */     } else if (this.tableEvent == null) {
/* 1427 */       this.tableEvent = event;
/* 1428 */     } else if ((this.tableEvent instanceof PdfPTableEventForwarder)) {
/* 1429 */       ((PdfPTableEventForwarder)this.tableEvent).addTableEvent(event);
/*      */     } else {
/* 1431 */       PdfPTableEventForwarder forward = new PdfPTableEventForwarder();
/* 1432 */       forward.addTableEvent(this.tableEvent);
/* 1433 */       forward.addTableEvent(event);
/* 1434 */       this.tableEvent = forward;
/*      */     }
/*      */   }
/*      */ 
/*      */   public PdfPTableEvent getTableEvent()
/*      */   {
/* 1444 */     return this.tableEvent;
/*      */   }
/*      */ 
/*      */   public float[] getAbsoluteWidths()
/*      */   {
/* 1453 */     return this.absoluteWidths;
/*      */   }
/*      */ 
/*      */   float[][] getEventWidths(float xPos, int firstRow, int lastRow, boolean includeHeaders) {
/* 1457 */     if (includeHeaders) {
/* 1458 */       firstRow = Math.max(firstRow, this.headerRows);
/* 1459 */       lastRow = Math.max(lastRow, this.headerRows);
/*      */     }
/* 1461 */     float[][] widths = new float[(includeHeaders ? this.headerRows : 0) + lastRow - firstRow][];
/* 1462 */     if (this.isColspan) {
/* 1463 */       int n = 0;
/* 1464 */       if (includeHeaders) {
/* 1465 */         for (int k = 0; k < this.headerRows; k++) {
/* 1466 */           PdfPRow row = (PdfPRow)this.rows.get(k);
/* 1467 */           if (row == null)
/* 1468 */             n++;
/*      */           else
/* 1470 */             widths[(n++)] = row.getEventWidth(xPos, this.absoluteWidths);
/*      */         }
/*      */       }
/* 1473 */       for (; firstRow < lastRow; firstRow++) {
/* 1474 */         PdfPRow row = (PdfPRow)this.rows.get(firstRow);
/* 1475 */         if (row == null)
/* 1476 */           n++;
/*      */         else
/* 1478 */           widths[(n++)] = row.getEventWidth(xPos, this.absoluteWidths);
/*      */       }
/*      */     } else {
/* 1481 */       int numCols = getNumberOfColumns();
/* 1482 */       float[] width = new float[numCols + 1];
/* 1483 */       width[0] = xPos;
/* 1484 */       for (int k = 0; k < numCols; k++)
/* 1485 */         width[(k + 1)] = (width[k] + this.absoluteWidths[k]);
/* 1486 */       for (int k = 0; k < widths.length; k++)
/* 1487 */         widths[k] = width;
/*      */     }
/* 1489 */     return widths;
/*      */   }
/*      */ 
/*      */   public boolean isSkipFirstHeader()
/*      */   {
/* 1500 */     return this.skipFirstHeader;
/*      */   }
/*      */ 
/*      */   public boolean isSkipLastFooter()
/*      */   {
/* 1512 */     return this.skipLastFooter;
/*      */   }
/*      */ 
/*      */   public void setSkipFirstHeader(boolean skipFirstHeader)
/*      */   {
/* 1522 */     this.skipFirstHeader = skipFirstHeader;
/*      */   }
/*      */ 
/*      */   public void setSkipLastFooter(boolean skipLastFooter)
/*      */   {
/* 1533 */     this.skipLastFooter = skipLastFooter;
/*      */   }
/*      */ 
/*      */   public void setRunDirection(int runDirection)
/*      */   {
/* 1544 */     switch (runDirection) {
/*      */     case 0:
/*      */     case 1:
/*      */     case 2:
/*      */     case 3:
/* 1549 */       this.runDirection = runDirection;
/* 1550 */       break;
/*      */     default:
/* 1552 */       throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.run.direction.1", runDirection));
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getRunDirection()
/*      */   {
/* 1564 */     return this.runDirection;
/*      */   }
/*      */ 
/*      */   public boolean isLockedWidth()
/*      */   {
/* 1573 */     return this.lockedWidth;
/*      */   }
/*      */ 
/*      */   public void setLockedWidth(boolean lockedWidth)
/*      */   {
/* 1582 */     this.lockedWidth = lockedWidth;
/*      */   }
/*      */ 
/*      */   public boolean isSplitRows()
/*      */   {
/* 1591 */     return this.splitRows;
/*      */   }
/*      */ 
/*      */   public void setSplitRows(boolean splitRows)
/*      */   {
/* 1602 */     this.splitRows = splitRows;
/*      */   }
/*      */ 
/*      */   public void setSpacingBefore(float spacing)
/*      */   {
/* 1611 */     this.spacingBefore = spacing;
/*      */   }
/*      */ 
/*      */   public void setSpacingAfter(float spacing)
/*      */   {
/* 1620 */     this.spacingAfter = spacing;
/*      */   }
/*      */ 
/*      */   public float spacingBefore()
/*      */   {
/* 1629 */     return this.spacingBefore;
/*      */   }
/*      */ 
/*      */   public float spacingAfter()
/*      */   {
/* 1638 */     return this.spacingAfter;
/*      */   }
/*      */ 
/*      */   public boolean isExtendLastRow()
/*      */   {
/* 1647 */     return this.extendLastRow[0];
/*      */   }
/*      */ 
/*      */   public void setExtendLastRow(boolean extendLastRows)
/*      */   {
/* 1657 */     this.extendLastRow[0] = extendLastRows;
/* 1658 */     this.extendLastRow[1] = extendLastRows;
/*      */   }
/*      */ 
/*      */   public void setExtendLastRow(boolean extendLastRows, boolean extendFinalRow)
/*      */   {
/* 1671 */     this.extendLastRow[0] = extendLastRows;
/* 1672 */     this.extendLastRow[1] = extendFinalRow;
/*      */   }
/*      */ 
/*      */   public boolean isExtendLastRow(boolean newPageFollows)
/*      */   {
/* 1683 */     if (newPageFollows) {
/* 1684 */       return this.extendLastRow[0];
/*      */     }
/* 1686 */     return this.extendLastRow[1];
/*      */   }
/*      */ 
/*      */   public boolean isHeadersInEvent()
/*      */   {
/* 1695 */     return this.headersInEvent;
/*      */   }
/*      */ 
/*      */   public void setHeadersInEvent(boolean headersInEvent)
/*      */   {
/* 1704 */     this.headersInEvent = headersInEvent;
/*      */   }
/*      */ 
/*      */   public boolean isSplitLate()
/*      */   {
/* 1713 */     return this.splitLate;
/*      */   }
/*      */ 
/*      */   public void setSplitLate(boolean splitLate)
/*      */   {
/* 1724 */     this.splitLate = splitLate;
/*      */   }
/*      */ 
/*      */   public void setKeepTogether(boolean keepTogether)
/*      */   {
/* 1735 */     this.keepTogether = keepTogether;
/*      */   }
/*      */ 
/*      */   public boolean getKeepTogether()
/*      */   {
/* 1745 */     return this.keepTogether;
/*      */   }
/*      */ 
/*      */   public int getFooterRows()
/*      */   {
/* 1754 */     return this.footerRows;
/*      */   }
/*      */ 
/*      */   public void setFooterRows(int footerRows)
/*      */   {
/* 1771 */     if (footerRows < 0)
/* 1772 */       footerRows = 0;
/* 1773 */     this.footerRows = footerRows;
/*      */   }
/*      */ 
/*      */   public void completeRow()
/*      */   {
/* 1782 */     while (!this.rowCompleted)
/* 1783 */       addCell(this.defaultCell);
/*      */   }
/*      */ 
/*      */   public void flushContent()
/*      */   {
/* 1792 */     deleteBodyRows();
/* 1793 */     setSkipFirstHeader(true);
/*      */   }
/*      */ 
/*      */   public boolean isComplete()
/*      */   {
/* 1801 */     return this.complete;
/*      */   }
/*      */ 
/*      */   public void setComplete(boolean complete)
/*      */   {
/* 1809 */     this.complete = complete;
/*      */   }
/*      */ 
/*      */   public float getSpacingBefore()
/*      */   {
/* 1816 */     return this.spacingBefore;
/*      */   }
/*      */ 
/*      */   public float getSpacingAfter()
/*      */   {
/* 1823 */     return this.spacingAfter;
/*      */   }
/*      */ 
/*      */   public boolean isLoopCheck() {
/* 1827 */     return this.loopCheck;
/*      */   }
/*      */ 
/*      */   public void setLoopCheck(boolean loopCheck) {
/* 1831 */     this.loopCheck = loopCheck;
/*      */   }
/*      */ 
/*      */   public PdfObject getAccessibleAttribute(PdfName key) {
/* 1835 */     if (this.accessibleAttributes != null) {
/* 1836 */       return (PdfObject)this.accessibleAttributes.get(key);
/*      */     }
/* 1838 */     return null;
/*      */   }
/*      */ 
/*      */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/* 1842 */     if (this.accessibleAttributes == null)
/* 1843 */       this.accessibleAttributes = new HashMap();
/* 1844 */     this.accessibleAttributes.put(key, value);
/*      */   }
/*      */ 
/*      */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/* 1848 */     return this.accessibleAttributes;
/*      */   }
/*      */ 
/*      */   public PdfName getRole() {
/* 1852 */     return this.role;
/*      */   }
/*      */ 
/*      */   public void setRole(PdfName role) {
/* 1856 */     this.role = role;
/*      */   }
/*      */ 
/*      */   public AccessibleElementId getId() {
/* 1860 */     return this.id;
/*      */   }
/*      */ 
/*      */   public void setId(AccessibleElementId id) {
/* 1864 */     this.id = id;
/*      */   }
/*      */ 
/*      */   public boolean isInline() {
/* 1868 */     return false;
/*      */   }
/*      */ 
/*      */   public PdfPTableHeader getHeader() {
/* 1872 */     if (this.header == null)
/* 1873 */       this.header = new PdfPTableHeader();
/* 1874 */     return this.header;
/*      */   }
/*      */ 
/*      */   public PdfPTableBody getBody() {
/* 1878 */     if (this.body == null)
/* 1879 */       this.body = new PdfPTableBody();
/* 1880 */     return this.body;
/*      */   }
/*      */ 
/*      */   public PdfPTableFooter getFooter() {
/* 1884 */     if (this.footer == null)
/* 1885 */       this.footer = new PdfPTableFooter();
/* 1886 */     return this.footer;
/*      */   }
/*      */ 
/*      */   public int getCellStartRowIndex(int rowIdx, int colIdx)
/*      */   {
/* 1898 */     int lastRow = rowIdx;
/* 1899 */     while ((getRow(lastRow).getCells()[colIdx] == null) && (lastRow > 0)) {
/* 1900 */       lastRow--;
/*      */     }
/* 1902 */     return lastRow;
/*      */   }
/*      */ 
/*      */   public FittingRows getFittingRows(float availableHeight, int startIdx)
/*      */   {
/* 1983 */     assert (getRow(startIdx).getCells()[0] != null);
/* 1984 */     int cols = getNumberOfColumns();
/* 1985 */     ColumnMeasurementState[] states = new ColumnMeasurementState[cols];
/* 1986 */     for (int i = 0; i < cols; i++) {
/* 1987 */       states[i] = new ColumnMeasurementState();
/*      */     }
/* 1989 */     float completedRowsHeight = 0.0F;
/*      */ 
/* 1991 */     float totalHeight = 0.0F;
/* 1992 */     Map correctedHeightsForLastRow = new HashMap();
/*      */ 
/* 1994 */     for (int k = startIdx; k < size(); k++) {
/* 1995 */       PdfPRow row = getRow(k);
/* 1996 */       float rowHeight = row.getMaxRowHeightsWithoutCalculating();
/* 1997 */       float maxCompletedRowsHeight = 0.0F;
/* 1998 */       int i = 0;
/* 1999 */       while (i < cols) {
/* 2000 */         PdfPCell cell = row.getCells()[i];
/* 2001 */         ColumnMeasurementState state = states[i];
/* 2002 */         if (cell == null)
/* 2003 */           state.consumeRowspan(completedRowsHeight, rowHeight);
/*      */         else {
/* 2005 */           state.beginCell(cell, completedRowsHeight, rowHeight);
/*      */         }
/* 2007 */         if ((state.cellEnds()) && (state.height > maxCompletedRowsHeight)) {
/* 2008 */           maxCompletedRowsHeight = state.height;
/*      */         }
/* 2010 */         for (int j = 1; j < state.colspan; j++) {
/* 2011 */           states[(i + j)].height = state.height;
/*      */         }
/* 2013 */         i += state.colspan;
/*      */       }
/*      */ 
/* 2016 */       float maxTotalHeight = 0.0F;
/* 2017 */       for (ColumnMeasurementState state : states) {
/* 2018 */         if (state.height > maxTotalHeight) {
/* 2019 */           maxTotalHeight = state.height;
/*      */         }
/*      */       }
/* 2022 */       row.setFinalMaxHeights(maxCompletedRowsHeight - completedRowsHeight);
/*      */ 
/* 2024 */       float remainingHeight = availableHeight - (isSplitLate() ? maxTotalHeight : maxCompletedRowsHeight);
/* 2025 */       if (remainingHeight < 0.0F) {
/*      */         break;
/*      */       }
/* 2028 */       correctedHeightsForLastRow.put(Integer.valueOf(k), Float.valueOf(maxTotalHeight - completedRowsHeight));
/* 2029 */       completedRowsHeight = maxCompletedRowsHeight;
/* 2030 */       totalHeight = maxTotalHeight;
/*      */     }
/* 2032 */     this.rowsNotChecked = false;
/* 2033 */     return new FittingRows(startIdx, k - 1, totalHeight, completedRowsHeight, correctedHeightsForLastRow);
/*      */   }
/*      */ 
/*      */   public static class ColumnMeasurementState
/*      */   {
/* 1948 */     public float height = 0.0F;
/*      */ 
/* 1950 */     public int rowspan = 1; public int colspan = 1;
/*      */ 
/*      */     public void beginCell(PdfPCell cell, float completedRowsHeight, float rowHeight) {
/* 1953 */       this.rowspan = cell.getRowspan();
/* 1954 */       this.colspan = cell.getColspan();
/* 1955 */       this.height = (completedRowsHeight + Math.max(cell.getMaxHeight(), rowHeight));
/*      */     }
/*      */ 
/*      */     public void consumeRowspan(float completedRowsHeight, float rowHeight) {
/* 1959 */       this.rowspan -= 1;
/*      */     }
/*      */ 
/*      */     public boolean cellEnds() {
/* 1963 */       return this.rowspan == 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class FittingRows
/*      */   {
/*      */     public final int firstRow;
/*      */     public final int lastRow;
/*      */     public final float height;
/*      */     public final float completedRowsHeight;
/*      */     private final Map<Integer, Float> correctedHeightsForLastRow;
/*      */ 
/*      */     public FittingRows(int firstRow, int lastRow, float height, float completedRowsHeight, Map<Integer, Float> correctedHeightsForLastRow)
/*      */     {
/* 1920 */       this.firstRow = firstRow;
/* 1921 */       this.lastRow = lastRow;
/* 1922 */       this.height = height;
/* 1923 */       this.completedRowsHeight = completedRowsHeight;
/* 1924 */       this.correctedHeightsForLastRow = correctedHeightsForLastRow;
/*      */     }
/*      */ 
/*      */     public void correctLastRowChosen(PdfPTable table, int k)
/*      */     {
/* 1933 */       PdfPRow row = table.getRow(k);
/* 1934 */       Float value = (Float)this.correctedHeightsForLastRow.get(Integer.valueOf(k));
/* 1935 */       if (value != null)
/* 1936 */         row.setFinalMaxHeights(value.floatValue());
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPTable
 * JD-Core Version:    0.6.2
 */