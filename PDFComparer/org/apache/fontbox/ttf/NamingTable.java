/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class NamingTable extends TTFTable
/*     */ {
/*     */   public static final String TAG = "name";
/*  37 */   private List<NameRecord> nameRecords = new ArrayList();
/*     */ 
/*  39 */   private String fontFamily = null;
/*  40 */   private String fontSubFamily = null;
/*  41 */   private String psName = null;
/*     */ 
/*     */   public void initData(TrueTypeFont ttf, TTFDataStream data)
/*     */     throws IOException
/*     */   {
/*  53 */     int formatSelector = data.readUnsignedShort();
/*  54 */     int numberOfNameRecords = data.readUnsignedShort();
/*  55 */     int offsetToStartOfStringStorage = data.readUnsignedShort();
/*  56 */     for (int i = 0; i < numberOfNameRecords; i++)
/*     */     {
/*  58 */       NameRecord nr = new NameRecord();
/*  59 */       nr.initData(ttf, data);
/*  60 */       this.nameRecords.add(nr);
/*     */     }
/*  62 */     for (int i = 0; i < numberOfNameRecords; i++)
/*     */     {
/*  64 */       NameRecord nr = (NameRecord)this.nameRecords.get(i);
/*  65 */       data.seek(getOffset() + 6L + numberOfNameRecords * 2 * 6 + nr.getStringOffset());
/*  66 */       int platform = nr.getPlatformId();
/*  67 */       int encoding = nr.getPlatformEncodingId();
/*  68 */       String charset = "ISO-8859-1";
/*  69 */       boolean isPlatform310 = false;
/*  70 */       boolean isPlatform10 = false;
/*  71 */       if ((platform == 3) && ((encoding == 1) || (encoding == 0)))
/*     */       {
/*  73 */         charset = "UTF-16";
/*  74 */         isPlatform310 = true;
/*     */       }
/*  76 */       else if (platform == 2)
/*     */       {
/*  78 */         if (encoding == 0)
/*     */         {
/*  80 */           charset = "US-ASCII";
/*     */         }
/*  82 */         else if (encoding == 1)
/*     */         {
/*  85 */           charset = "ISO-10646-1";
/*     */         }
/*  87 */         else if (encoding == 2)
/*     */         {
/*  89 */           charset = "ISO-8859-1";
/*     */         }
/*     */       }
/*  92 */       else if ((platform == 1) && (encoding == 0))
/*     */       {
/*  94 */         isPlatform10 = true;
/*     */       }
/*  96 */       String string = data.readString(nr.getStringLength(), charset);
/*  97 */       nr.setString(string);
/*  98 */       int nameID = nr.getNameId();
/*  99 */       if (nameID == 1)
/*     */       {
/* 102 */         if ((isPlatform310) || ((isPlatform10) && (this.fontFamily == null)))
/*     */         {
/* 104 */           this.fontFamily = string;
/*     */         }
/*     */       }
/* 107 */       else if (nameID == 2)
/*     */       {
/* 110 */         if ((isPlatform310) || ((isPlatform10) && (this.fontSubFamily == null)))
/*     */         {
/* 112 */           this.fontSubFamily = string;
/*     */         }
/*     */       }
/* 115 */       else if (nameID == 6)
/*     */       {
/* 118 */         if ((isPlatform310) || ((isPlatform10) && (this.psName == null)))
/*     */         {
/* 120 */           this.psName = string;
/*     */         }
/*     */       }
/*     */     }
/* 124 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */   public List<NameRecord> getNameRecords()
/*     */   {
/* 134 */     return this.nameRecords;
/*     */   }
/*     */ 
/*     */   public String getFontFamily()
/*     */   {
/* 144 */     return this.fontFamily;
/*     */   }
/*     */ 
/*     */   public String getFontSubFamily()
/*     */   {
/* 154 */     return this.fontSubFamily;
/*     */   }
/*     */ 
/*     */   public String getPSName()
/*     */   {
/* 164 */     return this.psName;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.NamingTable
 * JD-Core Version:    0.6.2
 */