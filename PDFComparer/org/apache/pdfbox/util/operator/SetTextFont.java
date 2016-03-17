/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.cos.COSNumber;
/*    */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*    */ import org.apache.pdfbox.pdmodel.font.PDFontFactory;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.text.PDTextState;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetTextFont extends OperatorProcessor
/*    */ {
/* 39 */   private static final Log LOG = LogFactory.getLog(SetTextFont.class);
/*    */ 
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 52 */     if (arguments.size() >= 2)
/*    */     {
/* 55 */       COSName fontName = (COSName)arguments.get(0);
/* 56 */       float fontSize = ((COSNumber)arguments.get(1)).floatValue();
/* 57 */       this.context.getGraphicsState().getTextState().setFontSize(fontSize);
/*    */ 
/* 59 */       PDFont font = (PDFont)this.context.getFonts().get(fontName.getName());
/* 60 */       if (font == null)
/*    */       {
/* 62 */         LOG.error("Could not find font(" + fontName + ") in map=" + this.context.getFonts() + ", creating default font");
/* 63 */         font = PDFontFactory.createDefaultFont();
/*    */       }
/* 65 */       this.context.getGraphicsState().getTextState().setFont(font);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetTextFont
 * JD-Core Version:    0.6.2
 */