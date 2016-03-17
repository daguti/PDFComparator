/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class MarkedObject
/*     */   implements Element
/*     */ {
/*     */   protected Element element;
/*  64 */   protected Properties markupAttributes = new Properties();
/*     */ 
/*     */   protected MarkedObject()
/*     */   {
/*  70 */     this.element = null;
/*     */   }
/*     */ 
/*     */   public MarkedObject(Element element)
/*     */   {
/*  78 */     this.element = element;
/*     */   }
/*     */ 
/*     */   public List<Chunk> getChunks()
/*     */   {
/*  87 */     return this.element.getChunks();
/*     */   }
/*     */ 
/*     */   public boolean process(ElementListener listener)
/*     */   {
/*     */     try
/*     */     {
/*  99 */       return listener.add(this.element);
/*     */     } catch (DocumentException de) {
/*     */     }
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 112 */     return 50;
/*     */   }
/*     */ 
/*     */   public boolean isContent()
/*     */   {
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isNestable()
/*     */   {
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */   public Properties getMarkupAttributes()
/*     */   {
/* 136 */     return this.markupAttributes;
/*     */   }
/*     */ 
/*     */   public void setMarkupAttribute(String key, String value)
/*     */   {
/* 145 */     this.markupAttributes.setProperty(key, value);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.MarkedObject
 * JD-Core Version:    0.6.2
 */