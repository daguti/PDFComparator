/*    */ package org.apache.pdfbox.preflight.utils;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public class RenderingIntents
/*    */ {
/* 54 */   private static List<String> RENDERING_INTENTS = Collections.unmodifiableList(al);
/*    */ 
/*    */   public static boolean contains(Object riArg)
/*    */   {
/* 59 */     return RENDERING_INTENTS.contains(riArg);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 49 */     ArrayList al = new ArrayList(4);
/* 50 */     al.add("RelativeColorimetric");
/* 51 */     al.add("AbsoluteColorimetric");
/* 52 */     al.add("Perceptual");
/* 53 */     al.add("Saturation");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.utils.RenderingIntents
 * JD-Core Version:    0.6.2
 */