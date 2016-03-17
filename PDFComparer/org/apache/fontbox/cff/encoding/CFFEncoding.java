/*     */ package org.apache.fontbox.cff.encoding;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class CFFEncoding
/*     */ {
/*     */   private List<Entry> entries;
/*     */ 
/*     */   public CFFEncoding()
/*     */   {
/*  31 */     this.entries = new ArrayList();
/*     */   }
/*     */ 
/*     */   public boolean isFontSpecific()
/*     */   {
/*  39 */     return false;
/*     */   }
/*     */ 
/*     */   public int getCode(int sid)
/*     */   {
/*  49 */     for (Entry entry : this.entries)
/*     */     {
/*  51 */       if (entry.entrySID == sid)
/*     */       {
/*  53 */         return entry.entryCode;
/*     */       }
/*     */     }
/*  56 */     return -1;
/*     */   }
/*     */ 
/*     */   public int getSID(int code)
/*     */   {
/*  66 */     for (Entry entry : this.entries)
/*     */     {
/*  68 */       if (entry.entryCode == code)
/*     */       {
/*  70 */         return entry.entrySID;
/*     */       }
/*     */     }
/*  73 */     return -1;
/*     */   }
/*     */ 
/*     */   public void register(int code, int sid)
/*     */   {
/*  83 */     this.entries.add(new Entry(code, sid));
/*     */   }
/*     */ 
/*     */   public void addEntry(Entry entry)
/*     */   {
/*  92 */     this.entries.add(entry);
/*     */   }
/*     */ 
/*     */   public List<Entry> getEntries()
/*     */   {
/* 101 */     return this.entries;
/*     */   }
/*     */ 
/*     */   public static class Entry
/*     */   {
/*     */     private int entryCode;
/*     */     private int entrySID;
/*     */ 
/*     */     protected Entry(int code, int sid)
/*     */     {
/* 120 */       this.entryCode = code;
/* 121 */       this.entrySID = sid;
/*     */     }
/*     */ 
/*     */     public int getCode()
/*     */     {
/* 130 */       return this.entryCode;
/*     */     }
/*     */ 
/*     */     public int getSID()
/*     */     {
/* 139 */       return this.entrySID;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 147 */       return "[code=" + this.entryCode + ", sid=" + this.entrySID + "]";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.encoding.CFFEncoding
 * JD-Core Version:    0.6.2
 */