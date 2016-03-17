/*     */ package org.apache.pdfbox.pdfviewer;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.io.IOException;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.border.LineBorder;
/*     */ import org.apache.pdfbox.PDFReader;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ 
/*     */ public class PageWrapper
/*     */   implements MouseMotionListener
/*     */ {
/*  37 */   private JPanel pageWrapper = new JPanel();
/*  38 */   private PDFPagePanel pagePanel = null;
/*  39 */   private PDFReader reader = null;
/*     */   private static final int SPACE_AROUND_DOCUMENT = 20;
/*     */ 
/*     */   public PageWrapper(PDFReader aReader)
/*     */     throws IOException
/*     */   {
/*  52 */     this.reader = aReader;
/*  53 */     this.pagePanel = new PDFPagePanel();
/*  54 */     this.pageWrapper.setLayout(null);
/*  55 */     this.pageWrapper.add(this.pagePanel);
/*  56 */     this.pagePanel.setLocation(20, 20);
/*  57 */     this.pageWrapper.setBorder(LineBorder.createBlackLineBorder());
/*  58 */     this.pagePanel.addMouseMotionListener(this);
/*     */   }
/*     */ 
/*     */   public void displayPage(PDPage page)
/*     */   {
/*  68 */     this.pagePanel.setPage(page);
/*  69 */     this.pagePanel.setPreferredSize(this.pagePanel.getSize());
/*  70 */     Dimension d = this.pagePanel.getSize();
/*  71 */     d.width += 40;
/*  72 */     d.height += 40;
/*     */ 
/*  74 */     this.pageWrapper.setPreferredSize(d);
/*  75 */     this.pageWrapper.validate();
/*     */   }
/*     */ 
/*     */   public JPanel getPanel()
/*     */   {
/*  85 */     return this.pageWrapper;
/*     */   }
/*     */ 
/*     */   public void mouseDragged(MouseEvent e)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void mouseMoved(MouseEvent e)
/*     */   {
/* 102 */     this.reader.getBottomStatusPanel().getStatusLabel().setText(e.getX() + "," + e.getY());
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfviewer.PageWrapper
 * JD-Core Version:    0.6.2
 */