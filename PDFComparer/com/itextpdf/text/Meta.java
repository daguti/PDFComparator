/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Meta
/*     */   implements Element
/*     */ {
/*     */   private final int type;
/*     */   private final StringBuffer content;
/*     */   public static final String UNKNOWN = "unknown";
/*     */   public static final String PRODUCER = "producer";
/*     */   public static final String CREATIONDATE = "creationdate";
/*     */   public static final String AUTHOR = "author";
/*     */   public static final String KEYWORDS = "keywords";
/*     */   public static final String SUBJECT = "subject";
/*     */   public static final String TITLE = "title";
/*     */ 
/*     */   Meta(int type, String content)
/*     */   {
/* 124 */     this.type = type;
/* 125 */     this.content = new StringBuffer(content);
/*     */   }
/*     */ 
/*     */   public Meta(String tag, String content)
/*     */   {
/* 135 */     this.type = getType(tag);
/* 136 */     this.content = new StringBuffer(content);
/*     */   }
/*     */ 
/*     */   public boolean process(ElementListener listener)
/*     */   {
/*     */     try
/*     */     {
/* 150 */       return listener.add(this);
/*     */     } catch (DocumentException de) {
/*     */     }
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 163 */     return this.type;
/*     */   }
/*     */ 
/*     */   public List<Chunk> getChunks()
/*     */   {
/* 172 */     return new ArrayList();
/*     */   }
/*     */ 
/*     */   public boolean isContent()
/*     */   {
/* 180 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isNestable()
/*     */   {
/* 188 */     return false;
/*     */   }
/*     */ 
/*     */   public StringBuffer append(String string)
/*     */   {
/* 200 */     return this.content.append(string);
/*     */   }
/*     */ 
/*     */   public String getContent()
/*     */   {
/* 211 */     return this.content.toString();
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 221 */     switch (this.type) {
/*     */     case 2:
/* 223 */       return "subject";
/*     */     case 3:
/* 225 */       return "keywords";
/*     */     case 4:
/* 227 */       return "author";
/*     */     case 1:
/* 229 */       return "title";
/*     */     case 5:
/* 231 */       return "producer";
/*     */     case 6:
/* 233 */       return "creationdate";
/*     */     }
/* 235 */     return "unknown";
/*     */   }
/*     */ 
/*     */   public static int getType(String tag)
/*     */   {
/* 246 */     if ("subject".equals(tag)) {
/* 247 */       return 2;
/*     */     }
/* 249 */     if ("keywords".equals(tag)) {
/* 250 */       return 3;
/*     */     }
/* 252 */     if ("author".equals(tag)) {
/* 253 */       return 4;
/*     */     }
/* 255 */     if ("title".equals(tag)) {
/* 256 */       return 1;
/*     */     }
/* 258 */     if ("producer".equals(tag)) {
/* 259 */       return 5;
/*     */     }
/* 261 */     if ("creationdate".equals(tag)) {
/* 262 */       return 6;
/*     */     }
/* 264 */     return 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Meta
 * JD-Core Version:    0.6.2
 */