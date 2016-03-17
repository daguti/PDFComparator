/*     */ package org.antlr.runtime;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CommonToken
/*     */   implements Token, Serializable
/*     */ {
/*     */   protected int type;
/*     */   protected int line;
/*  35 */   protected int charPositionInLine = -1;
/*  36 */   protected int channel = 0;
/*     */   protected transient CharStream input;
/*     */   protected String text;
/*  46 */   protected int index = -1;
/*     */   protected int start;
/*     */   protected int stop;
/*     */ 
/*     */   public CommonToken(int type)
/*     */   {
/*  55 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public CommonToken(CharStream input, int type, int channel, int start, int stop) {
/*  59 */     this.input = input;
/*  60 */     this.type = type;
/*  61 */     this.channel = channel;
/*  62 */     this.start = start;
/*  63 */     this.stop = stop;
/*     */   }
/*     */ 
/*     */   public CommonToken(int type, String text) {
/*  67 */     this.type = type;
/*  68 */     this.channel = 0;
/*  69 */     this.text = text;
/*     */   }
/*     */ 
/*     */   public CommonToken(Token oldToken) {
/*  73 */     this.text = oldToken.getText();
/*  74 */     this.type = oldToken.getType();
/*  75 */     this.line = oldToken.getLine();
/*  76 */     this.index = oldToken.getTokenIndex();
/*  77 */     this.charPositionInLine = oldToken.getCharPositionInLine();
/*  78 */     this.channel = oldToken.getChannel();
/*  79 */     this.input = oldToken.getInputStream();
/*  80 */     if ((oldToken instanceof CommonToken)) {
/*  81 */       this.start = ((CommonToken)oldToken).start;
/*  82 */       this.stop = ((CommonToken)oldToken).stop;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getType() {
/*  87 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setLine(int line) {
/*  91 */     this.line = line;
/*     */   }
/*     */ 
/*     */   public String getText() {
/*  95 */     if (this.text != null) {
/*  96 */       return this.text;
/*     */     }
/*  98 */     if (this.input == null) {
/*  99 */       return null;
/*     */     }
/* 101 */     if ((this.start < this.input.size()) && (this.stop < this.input.size())) {
/* 102 */       this.text = this.input.substring(this.start, this.stop);
/*     */     }
/*     */     else {
/* 105 */       this.text = "<EOF>";
/*     */     }
/* 107 */     return this.text;
/*     */   }
/*     */ 
/*     */   public void setText(String text)
/*     */   {
/* 116 */     this.text = text;
/*     */   }
/*     */ 
/*     */   public int getLine() {
/* 120 */     return this.line;
/*     */   }
/*     */ 
/*     */   public int getCharPositionInLine() {
/* 124 */     return this.charPositionInLine;
/*     */   }
/*     */ 
/*     */   public void setCharPositionInLine(int charPositionInLine) {
/* 128 */     this.charPositionInLine = charPositionInLine;
/*     */   }
/*     */ 
/*     */   public int getChannel() {
/* 132 */     return this.channel;
/*     */   }
/*     */ 
/*     */   public void setChannel(int channel) {
/* 136 */     this.channel = channel;
/*     */   }
/*     */ 
/*     */   public void setType(int type) {
/* 140 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public int getStartIndex() {
/* 144 */     return this.start;
/*     */   }
/*     */ 
/*     */   public void setStartIndex(int start) {
/* 148 */     this.start = start;
/*     */   }
/*     */ 
/*     */   public int getStopIndex() {
/* 152 */     return this.stop;
/*     */   }
/*     */ 
/*     */   public void setStopIndex(int stop) {
/* 156 */     this.stop = stop;
/*     */   }
/*     */ 
/*     */   public int getTokenIndex() {
/* 160 */     return this.index;
/*     */   }
/*     */ 
/*     */   public void setTokenIndex(int index) {
/* 164 */     this.index = index;
/*     */   }
/*     */ 
/*     */   public CharStream getInputStream() {
/* 168 */     return this.input;
/*     */   }
/*     */ 
/*     */   public void setInputStream(CharStream input) {
/* 172 */     this.input = input;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 176 */     String channelStr = "";
/* 177 */     if (this.channel > 0) {
/* 178 */       channelStr = ",channel=" + this.channel;
/*     */     }
/* 180 */     String txt = getText();
/* 181 */     if (txt != null) {
/* 182 */       txt = txt.replaceAll("\n", "\\\\n");
/* 183 */       txt = txt.replaceAll("\r", "\\\\r");
/* 184 */       txt = txt.replaceAll("\t", "\\\\t");
/*     */     }
/*     */     else {
/* 187 */       txt = "<no text>";
/*     */     }
/* 189 */     return "[@" + getTokenIndex() + "," + this.start + ":" + this.stop + "='" + txt + "',<" + this.type + ">" + channelStr + "," + this.line + ":" + getCharPositionInLine() + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.CommonToken
 * JD-Core Version:    0.6.2
 */