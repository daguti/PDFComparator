/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.pdf.draw.DrawInterface;
/*     */ 
/*     */ public class TabStop
/*     */ {
/*     */   protected float position;
/*  67 */   protected Alignment alignment = Alignment.LEFT;
/*     */   protected DrawInterface leader;
/*  69 */   protected char anchorChar = '.';
/*     */ 
/*     */   public static TabStop newInstance(float currentPosition, float tabInterval)
/*     */   {
/*  52 */     currentPosition = Math.round(currentPosition * 1000.0F) / 1000.0F;
/*  53 */     tabInterval = Math.round(tabInterval * 1000.0F) / 1000.0F;
/*     */ 
/*  55 */     TabStop tabStop = new TabStop(currentPosition + tabInterval - currentPosition % tabInterval);
/*  56 */     return tabStop;
/*     */   }
/*     */ 
/*     */   public TabStop(float position)
/*     */   {
/*  72 */     this(position, Alignment.LEFT);
/*     */   }
/*     */ 
/*     */   public TabStop(float position, DrawInterface leader) {
/*  76 */     this(position, leader, Alignment.LEFT);
/*     */   }
/*     */ 
/*     */   public TabStop(float position, Alignment alignment) {
/*  80 */     this(position, null, alignment);
/*     */   }
/*     */ 
/*     */   public TabStop(float position, Alignment alignment, char anchorChar) {
/*  84 */     this(position, null, alignment, anchorChar);
/*     */   }
/*     */ 
/*     */   public TabStop(float position, DrawInterface leader, Alignment alignment) {
/*  88 */     this(position, leader, alignment, '.');
/*     */   }
/*     */ 
/*     */   public TabStop(float position, DrawInterface leader, Alignment alignment, char anchorChar) {
/*  92 */     this.position = position;
/*  93 */     this.leader = leader;
/*  94 */     this.alignment = alignment;
/*  95 */     this.anchorChar = anchorChar;
/*     */   }
/*     */ 
/*     */   public TabStop(TabStop tabStop) {
/*  99 */     this(tabStop.getPosition(), tabStop.getLeader(), tabStop.getAlignment(), tabStop.getAnchorChar());
/*     */   }
/*     */ 
/*     */   public float getPosition() {
/* 103 */     return this.position;
/*     */   }
/*     */ 
/*     */   public void setPosition(float position) {
/* 107 */     this.position = position;
/*     */   }
/*     */ 
/*     */   public Alignment getAlignment() {
/* 111 */     return this.alignment;
/*     */   }
/*     */ 
/*     */   public void setAlignment(Alignment alignment) {
/* 115 */     this.alignment = alignment;
/*     */   }
/*     */ 
/*     */   public DrawInterface getLeader() {
/* 119 */     return this.leader;
/*     */   }
/*     */ 
/*     */   public void setLeader(DrawInterface leader) {
/* 123 */     this.leader = leader;
/*     */   }
/*     */ 
/*     */   public char getAnchorChar() {
/* 127 */     return this.anchorChar;
/*     */   }
/*     */ 
/*     */   public void setAnchorChar(char anchorChar) {
/* 131 */     this.anchorChar = anchorChar;
/*     */   }
/*     */ 
/*     */   public float getPosition(float tabPosition, float currentPosition, float anchorPosition) {
/* 135 */     float newPosition = this.position;
/* 136 */     float textWidth = currentPosition - tabPosition;
/* 137 */     switch (1.$SwitchMap$com$itextpdf$text$TabStop$Alignment[this.alignment.ordinal()]) {
/*     */     case 1:
/* 139 */       if (tabPosition + textWidth < this.position)
/* 140 */         newPosition = this.position - textWidth;
/*     */       else {
/* 142 */         newPosition = tabPosition;
/*     */       }
/* 144 */       break;
/*     */     case 2:
/* 146 */       if (tabPosition + textWidth / 2.0F < this.position)
/* 147 */         newPosition = this.position - textWidth / 2.0F;
/*     */       else {
/* 149 */         newPosition = tabPosition;
/*     */       }
/* 151 */       break;
/*     */     case 3:
/* 153 */       if (!Float.isNaN(anchorPosition)) {
/* 154 */         if (anchorPosition < this.position)
/* 155 */           newPosition = this.position - (anchorPosition - tabPosition);
/*     */         else {
/* 157 */           newPosition = tabPosition;
/*     */         }
/*     */       }
/* 160 */       else if (tabPosition + textWidth < this.position)
/* 161 */         newPosition = this.position - textWidth;
/*     */       else {
/* 163 */         newPosition = tabPosition;
/*     */       }
/*     */       break;
/*     */     }
/*     */ 
/* 168 */     return newPosition;
/*     */   }
/*     */ 
/*     */   public static enum Alignment
/*     */   {
/*  60 */     LEFT, 
/*  61 */     RIGHT, 
/*  62 */     CENTER, 
/*  63 */     ANCHOR;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.TabStop
 * JD-Core Version:    0.6.2
 */