/*     */ package org.apache.fontbox.cff.charset;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class CFFCharset
/*     */ {
/*     */   private List<Entry> entries;
/*     */ 
/*     */   public CFFCharset()
/*     */   {
/*  30 */     this.entries = new ArrayList();
/*     */   }
/*     */ 
/*     */   public boolean isFontSpecific()
/*     */   {
/*  38 */     return false;
/*     */   }
/*     */ 
/*     */   public int getSID(String name)
/*     */   {
/*  48 */     for (Entry entry : this.entries)
/*     */     {
/*  50 */       if (entry.entryName.equals(name))
/*     */       {
/*  52 */         return entry.entrySID;
/*     */       }
/*     */     }
/*  55 */     return -1;
/*     */   }
/*     */ 
/*     */   public String getName(int sid)
/*     */   {
/*  65 */     for (Entry entry : this.entries)
/*     */     {
/*  67 */       if (entry.entrySID == sid)
/*     */       {
/*  69 */         return entry.entryName;
/*     */       }
/*     */     }
/*  72 */     return null;
/*     */   }
/*     */ 
/*     */   public void register(int sid, String name)
/*     */   {
/*  82 */     this.entries.add(new Entry(sid, name));
/*     */   }
/*     */ 
/*     */   public void addEntry(Entry entry)
/*     */   {
/*  91 */     this.entries.add(entry);
/*     */   }
/*     */ 
/*     */   public List<Entry> getEntries()
/*     */   {
/* 100 */     return this.entries;
/*     */   }
/*     */ 
/*     */   public static class Entry
/*     */   {
/*     */     private int entrySID;
/*     */     private String entryName;
/*     */ 
/*     */     protected Entry(int sid, String name)
/*     */     {
/* 119 */       this.entrySID = sid;
/* 120 */       this.entryName = name;
/*     */     }
/*     */ 
/*     */     public int getSID()
/*     */     {
/* 129 */       return this.entrySID;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 138 */       return this.entryName;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 146 */       return "[sid=" + this.entrySID + ", name=" + this.entryName + "]";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.charset.CFFCharset
 * JD-Core Version:    0.6.2
 */