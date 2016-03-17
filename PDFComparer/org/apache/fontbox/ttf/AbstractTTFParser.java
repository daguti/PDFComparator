/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ abstract class AbstractTTFParser
/*     */ {
/*  26 */   protected boolean isEmbedded = false;
/*  27 */   protected boolean parseOnDemandOnly = false;
/*     */ 
/*     */   public AbstractTTFParser(boolean fontIsEmbedded)
/*     */   {
/*  37 */     this(fontIsEmbedded, false);
/*     */   }
/*     */ 
/*     */   public AbstractTTFParser(boolean fontIsEmbedded, boolean parseOnDemand)
/*     */   {
/*  48 */     this.isEmbedded = fontIsEmbedded;
/*  49 */     this.parseOnDemandOnly = parseOnDemand;
/*     */   }
/*     */ 
/*     */   public TrueTypeFont parseTTF(String ttfFile)
/*     */     throws IOException
/*     */   {
/*  60 */     RAFDataStream raf = new RAFDataStream(ttfFile, "r");
/*  61 */     return parseTTF(raf);
/*     */   }
/*     */ 
/*     */   public TrueTypeFont parseTTF(File ttfFile)
/*     */     throws IOException
/*     */   {
/*  72 */     RAFDataStream raf = new RAFDataStream(ttfFile, "r");
/*  73 */     return parseTTF(raf);
/*     */   }
/*     */ 
/*     */   public TrueTypeFont parseTTF(InputStream ttfData)
/*     */     throws IOException
/*     */   {
/*  84 */     return parseTTF(new MemoryTTFDataStream(ttfData));
/*     */   }
/*     */ 
/*     */   public TrueTypeFont parseTTF(TTFDataStream raf)
/*     */     throws IOException
/*     */   {
/*  95 */     TrueTypeFont font = new TrueTypeFont(raf);
/*  96 */     font.setVersion(raf.read32Fixed());
/*  97 */     int numberOfTables = raf.readUnsignedShort();
/*  98 */     int searchRange = raf.readUnsignedShort();
/*  99 */     int entrySelector = raf.readUnsignedShort();
/* 100 */     int rangeShift = raf.readUnsignedShort();
/* 101 */     for (int i = 0; i < numberOfTables; i++)
/*     */     {
/* 103 */       TTFTable table = readTableDirectory(raf);
/* 104 */       font.addTable(table);
/*     */     }
/*     */ 
/* 107 */     if (!this.parseOnDemandOnly)
/*     */     {
/* 109 */       parseTables(font, raf);
/*     */     }
/* 111 */     return font;
/*     */   }
/*     */ 
/*     */   protected void parseTables(TrueTypeFont font, TTFDataStream raf)
/*     */     throws IOException
/*     */   {
/* 122 */     Iterator iter = font.getTables().iterator();
/* 123 */     while (iter.hasNext())
/*     */     {
/* 125 */       TTFTable table = (TTFTable)iter.next();
/* 126 */       if (!table.getInitialized())
/*     */       {
/* 128 */         font.initializeTable(table);
/*     */       }
/*     */     }
/*     */ 
/* 132 */     HeaderTable head = font.getHeader();
/* 133 */     if (head == null)
/*     */     {
/* 135 */       throw new IOException("head is mandatory");
/*     */     }
/* 137 */     HorizontalHeaderTable hh = font.getHorizontalHeader();
/* 138 */     if (hh == null)
/*     */     {
/* 140 */       throw new IOException("hhead is mandatory");
/*     */     }
/* 142 */     MaximumProfileTable maxp = font.getMaximumProfile();
/* 143 */     if (maxp == null)
/*     */     {
/* 145 */       throw new IOException("maxp is mandatory");
/*     */     }
/* 147 */     PostScriptTable post = font.getPostScript();
/* 148 */     if ((post == null) && (!this.isEmbedded))
/*     */     {
/* 151 */       throw new IOException("post is mandatory");
/*     */     }
/* 153 */     IndexToLocationTable loc = font.getIndexToLocation();
/* 154 */     if (loc == null)
/*     */     {
/* 156 */       throw new IOException("loca is mandatory");
/*     */     }
/*     */ 
/* 159 */     if (font.getGlyph() == null)
/*     */     {
/* 161 */       throw new IOException("glyf is mandatory");
/*     */     }
/* 163 */     if ((font.getNaming() == null) && (!this.isEmbedded))
/*     */     {
/* 165 */       throw new IOException("name is mandatory");
/*     */     }
/* 167 */     if (font.getHorizontalMetrics() == null)
/*     */     {
/* 169 */       throw new IOException("hmtx is mandatory");
/*     */     }
/*     */   }
/*     */ 
/*     */   private TTFTable readTableDirectory(TTFDataStream raf) throws IOException
/*     */   {
/* 175 */     TTFTable retval = null;
/* 176 */     String tag = raf.readString(4);
/* 177 */     if (tag.equals("cmap"))
/*     */     {
/* 179 */       retval = new CMAPTable();
/*     */     }
/* 181 */     else if (tag.equals("glyf"))
/*     */     {
/* 183 */       retval = new GlyphTable();
/*     */     }
/* 185 */     else if (tag.equals("head"))
/*     */     {
/* 187 */       retval = new HeaderTable();
/*     */     }
/* 189 */     else if (tag.equals("hhea"))
/*     */     {
/* 191 */       retval = new HorizontalHeaderTable();
/*     */     }
/* 193 */     else if (tag.equals("hmtx"))
/*     */     {
/* 195 */       retval = new HorizontalMetricsTable();
/*     */     }
/* 197 */     else if (tag.equals("loca"))
/*     */     {
/* 199 */       retval = new IndexToLocationTable();
/*     */     }
/* 201 */     else if (tag.equals("maxp"))
/*     */     {
/* 203 */       retval = new MaximumProfileTable();
/*     */     }
/* 205 */     else if (tag.equals("name"))
/*     */     {
/* 207 */       retval = new NamingTable();
/*     */     }
/* 209 */     else if (tag.equals("OS/2"))
/*     */     {
/* 211 */       retval = new OS2WindowsMetricsTable();
/*     */     }
/* 213 */     else if (tag.equals("post"))
/*     */     {
/* 215 */       retval = new PostScriptTable();
/*     */     }
/* 217 */     else if (tag.equals("DSIG"))
/*     */     {
/* 219 */       retval = new DigitalSignatureTable();
/*     */     }
/*     */     else
/*     */     {
/* 224 */       retval = new TTFTable();
/*     */     }
/* 226 */     retval.setTag(tag);
/* 227 */     retval.setCheckSum(raf.readUnsignedInt());
/* 228 */     retval.setOffset(raf.readUnsignedInt());
/* 229 */     retval.setLength(raf.readUnsignedInt());
/* 230 */     return retval;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.AbstractTTFParser
 * JD-Core Version:    0.6.2
 */