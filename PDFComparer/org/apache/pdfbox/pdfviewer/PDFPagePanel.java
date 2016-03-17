/*    */ package org.apache.pdfbox.pdfviewer;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.io.IOException;
/*    */ import javax.swing.JPanel;
/*    */ import org.apache.pdfbox.pdmodel.PDPage;
/*    */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*    */ 
/*    */ public class PDFPagePanel extends JPanel
/*    */ {
/*    */   private static final long serialVersionUID = -4629033339560890669L;
/*    */   private PDPage page;
/* 43 */   private PageDrawer drawer = null;
/* 44 */   private Dimension pageDimension = null;
/* 45 */   private Dimension drawDimension = null;
/*    */ 
/*    */   public PDFPagePanel()
/*    */     throws IOException
/*    */   {
/* 54 */     this.drawer = new PageDrawer();
/*    */   }
/*    */ 
/*    */   public void setPage(PDPage pdfPage)
/*    */   {
/* 64 */     this.page = pdfPage;
/* 65 */     PDRectangle cropBox = this.page.findCropBox();
/* 66 */     this.drawDimension = cropBox.createDimension();
/* 67 */     int rotation = this.page.findRotation();
/* 68 */     if ((rotation == 90) || (rotation == 270))
/*    */     {
/* 70 */       this.pageDimension = new Dimension(this.drawDimension.height, this.drawDimension.width);
/*    */     }
/*    */     else
/*    */     {
/* 74 */       this.pageDimension = this.drawDimension;
/*    */     }
/* 76 */     setSize(this.pageDimension);
/* 77 */     setBackground(Color.white);
/*    */   }
/*    */ 
/*    */   public void paint(Graphics g)
/*    */   {
/*    */     try
/*    */     {
/* 88 */       g.setColor(getBackground());
/* 89 */       g.fillRect(0, 0, getWidth(), getHeight());
/*    */ 
/* 91 */       int rotation = this.page.findRotation();
/* 92 */       if ((rotation == 90) || (rotation == 270))
/*    */       {
/* 94 */         Graphics2D g2D = (Graphics2D)g;
/* 95 */         g2D.translate(this.pageDimension.getWidth(), 0.0D);
/* 96 */         g2D.rotate(Math.toRadians(rotation));
/*    */       }
/* 98 */       else if (rotation == 180)
/*    */       {
/* 100 */         Graphics2D g2D = (Graphics2D)g;
/* 101 */         g2D.translate(this.pageDimension.getWidth(), this.pageDimension.getHeight());
/* 102 */         g2D.rotate(Math.toRadians(rotation));
/*    */       }
/*    */ 
/* 105 */       this.drawer.drawPage(g, this.page, this.drawDimension);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 109 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfviewer.PDFPagePanel
 * JD-Core Version:    0.6.2
 */