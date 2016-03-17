/*    */ package com.itextpdf.text.xml.simpleparser.handler;
/*    */ 
/*    */ import com.itextpdf.text.xml.simpleparser.NewLineHandler;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class HTMLNewLineHandler
/*    */   implements NewLineHandler
/*    */ {
/* 61 */   private final Set<String> newLineTags = new HashSet();
/*    */ 
/*    */   public HTMLNewLineHandler()
/*    */   {
/* 69 */     this.newLineTags.add("p");
/* 70 */     this.newLineTags.add("blockquote");
/* 71 */     this.newLineTags.add("br");
/*    */   }
/*    */ 
/*    */   public boolean isNewLineTag(String tag)
/*    */   {
/* 82 */     return this.newLineTags.contains(tag);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.simpleparser.handler.HTMLNewLineHandler
 * JD-Core Version:    0.6.2
 */