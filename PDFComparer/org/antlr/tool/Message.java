/*     */ package org.antlr.tool;
/*     */ 
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ 
/*     */ public abstract class Message
/*     */ {
/*     */   public StringTemplate msgST;
/*     */   public StringTemplate locationST;
/*     */   public StringTemplate reportST;
/*     */   public StringTemplate messageFormatST;
/*     */   public int msgID;
/*     */   public Object arg;
/*     */   public Object arg2;
/*     */   public Throwable e;
/*     */   public String file;
/*  55 */   public int line = -1;
/*  56 */   public int column = -1;
/*     */ 
/*     */   public Message() {
/*     */   }
/*     */ 
/*     */   public Message(int msgID) {
/*  62 */     this(msgID, null, null);
/*     */   }
/*     */ 
/*     */   public Message(int msgID, Object arg, Object arg2) {
/*  66 */     setMessageID(msgID);
/*  67 */     this.arg = arg;
/*  68 */     this.arg2 = arg2;
/*     */   }
/*     */ 
/*     */   public void setLine(int line) {
/*  72 */     this.line = line;
/*     */   }
/*     */ 
/*     */   public void setColumn(int column) {
/*  76 */     this.column = column;
/*     */   }
/*     */ 
/*     */   public void setMessageID(int msgID) {
/*  80 */     this.msgID = msgID;
/*  81 */     this.msgST = ErrorManager.getMessage(msgID);
/*     */   }
/*     */ 
/*     */   public StringTemplate getMessageTemplate()
/*     */   {
/*  88 */     return this.msgST.getInstanceOf();
/*     */   }
/*     */ 
/*     */   public StringTemplate getLocationTemplate()
/*     */   {
/*  95 */     return this.locationST.getInstanceOf();
/*     */   }
/*     */ 
/*     */   public String toString(StringTemplate messageST)
/*     */   {
/* 100 */     this.locationST = ErrorManager.getLocationFormat();
/* 101 */     this.reportST = ErrorManager.getReportFormat();
/* 102 */     this.messageFormatST = ErrorManager.getMessageFormat();
/* 103 */     boolean locationValid = false;
/* 104 */     if (this.line != -1) {
/* 105 */       this.locationST.setAttribute("line", this.line);
/* 106 */       locationValid = true;
/*     */     }
/* 108 */     if (this.column != -1) {
/* 109 */       this.locationST.setAttribute("column", this.column);
/* 110 */       locationValid = true;
/*     */     }
/* 112 */     if (this.file != null) {
/* 113 */       this.locationST.setAttribute("file", this.file);
/* 114 */       locationValid = true;
/*     */     }
/*     */ 
/* 117 */     this.messageFormatST.setAttribute("id", this.msgID);
/* 118 */     this.messageFormatST.setAttribute("text", messageST);
/*     */ 
/* 120 */     if (locationValid) {
/* 121 */       this.reportST.setAttribute("location", this.locationST);
/*     */     }
/* 123 */     this.reportST.setAttribute("message", this.messageFormatST);
/* 124 */     this.reportST.setAttribute("type", ErrorManager.getMessageType(this.msgID));
/*     */ 
/* 126 */     return this.reportST.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.Message
 * JD-Core Version:    0.6.2
 */