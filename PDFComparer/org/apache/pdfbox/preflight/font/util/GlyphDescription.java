/*    */ package org.apache.pdfbox.preflight.font.util;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.fontbox.cff.CharStringCommand;
/*    */ 
/*    */ public class GlyphDescription
/*    */ {
/* 30 */   private List<Object> operations = null;
/*    */ 
/* 32 */   private Integer glyphWidth = null;
/*    */ 
/*    */   GlyphDescription(List<Object> operations)
/*    */   {
/* 36 */     this.operations = operations;
/*    */   }
/*    */ 
/*    */   public int getGlyphWidth()
/*    */   {
/* 41 */     if (this.glyphWidth != null)
/*    */     {
/* 43 */       return this.glyphWidth.intValue();
/*    */     }
/*    */ 
/* 46 */     this.glyphWidth = Integer.valueOf(searchWidth());
/* 47 */     return this.glyphWidth.intValue();
/*    */   }
/*    */ 
/*    */   private int searchWidth()
/*    */   {
/* 52 */     for (int i = 0; (this.operations != null) && (i < this.operations.size()); i++)
/*    */     {
/* 54 */       Object obj = this.operations.get(i);
/* 55 */       if ((obj instanceof CharStringCommand))
/*    */       {
/* 57 */         CharStringCommand csCmd = (CharStringCommand)obj;
/* 58 */         if ("hsbw".equals(CharStringCommand.TYPE1_VOCABULARY.get(csCmd.getKey())))
/*    */         {
/* 62 */           if ((this.operations.get(i - 1) instanceof CharStringCommand))
/*    */           {
/* 64 */             CharStringCommand div = (CharStringCommand)this.operations.get(i - 1);
/* 65 */             if ("div".equals(CharStringCommand.TYPE1_VOCABULARY.get(div.getKey())))
/*    */             {
/* 67 */               return ((Integer)this.operations.get(i - 3)).intValue() / ((Integer)this.operations.get(i - 2)).intValue();
/*    */             }
/*    */           }
/*    */           else
/*    */           {
/* 72 */             return ((Integer)this.operations.get(i - 1)).intValue();
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */ 
/* 78 */     return 0;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.util.GlyphDescription
 * JD-Core Version:    0.6.2
 */