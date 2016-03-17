/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class TabSettings
/*     */ {
/*     */   public static final float DEFAULT_TAB_INTERVAL = 36.0F;
/*  59 */   private List<TabStop> tabStops = new ArrayList();
/*  60 */   private float tabInterval = 36.0F;
/*     */ 
/*     */   public static TabStop getTabStopNewInstance(float currentPosition, TabSettings tabSettings)
/*     */   {
/*  54 */     if (tabSettings != null)
/*  55 */       return tabSettings.getTabStopNewInstance(currentPosition);
/*  56 */     return TabStop.newInstance(currentPosition, 36.0F);
/*     */   }
/*     */ 
/*     */   public TabSettings()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TabSettings(List<TabStop> tabStops)
/*     */   {
/*  65 */     this.tabStops = tabStops;
/*     */   }
/*     */ 
/*     */   public TabSettings(float tabInterval) {
/*  69 */     this.tabInterval = tabInterval;
/*     */   }
/*     */ 
/*     */   public TabSettings(List<TabStop> tabStops, float tabInterval) {
/*  73 */     this.tabStops = tabStops;
/*  74 */     this.tabInterval = tabInterval;
/*     */   }
/*     */ 
/*     */   public List<TabStop> getTabStops() {
/*  78 */     return this.tabStops;
/*     */   }
/*     */ 
/*     */   public void setTabStops(List<TabStop> tabStops) {
/*  82 */     this.tabStops = tabStops;
/*     */   }
/*     */ 
/*     */   public float getTabInterval() {
/*  86 */     return this.tabInterval;
/*     */   }
/*     */ 
/*     */   public void setTabInterval(float tabInterval) {
/*  90 */     this.tabInterval = tabInterval;
/*     */   }
/*     */ 
/*     */   public TabStop getTabStopNewInstance(float currentPosition) {
/*  94 */     TabStop tabStop = null;
/*  95 */     if (this.tabStops != null) {
/*  96 */       for (TabStop currentTabStop : this.tabStops) {
/*  97 */         if (currentTabStop.getPosition() - currentPosition > 0.001D) {
/*  98 */           tabStop = new TabStop(currentTabStop);
/*  99 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 104 */     if (tabStop == null) {
/* 105 */       tabStop = TabStop.newInstance(currentPosition, this.tabInterval);
/*     */     }
/*     */ 
/* 108 */     return tabStop;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.TabSettings
 * JD-Core Version:    0.6.2
 */