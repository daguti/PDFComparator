/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class TrueTypeFont
/*     */ {
/*  35 */   private final Log log = LogFactory.getLog(TrueTypeFont.class);
/*     */   private float version;
/*  39 */   private int numberOfGlyphs = -1;
/*     */ 
/*  41 */   private int unitsPerEm = -1;
/*     */ 
/*  43 */   private int[] advanceWidths = null;
/*     */ 
/*  45 */   private Map<String, TTFTable> tables = new HashMap();
/*     */   private TTFDataStream data;
/*     */ 
/*     */   TrueTypeFont(TTFDataStream fontData)
/*     */   {
/*  56 */     this.data = fontData;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  66 */     this.data.close();
/*     */   }
/*     */ 
/*     */   public float getVersion()
/*     */   {
/*  74 */     return this.version;
/*     */   }
/*     */ 
/*     */   public void setVersion(float versionValue)
/*     */   {
/*  81 */     this.version = versionValue;
/*     */   }
/*     */ 
/*     */   public void addTable(TTFTable table)
/*     */   {
/*  91 */     this.tables.put(table.getTag(), table);
/*     */   }
/*     */ 
/*     */   public Collection<TTFTable> getTables()
/*     */   {
/* 101 */     return this.tables.values();
/*     */   }
/*     */ 
/*     */   public NamingTable getNaming()
/*     */   {
/* 111 */     NamingTable naming = (NamingTable)this.tables.get("name");
/* 112 */     if ((naming != null) && (!naming.getInitialized()))
/*     */     {
/* 114 */       initializeTable(naming);
/*     */     }
/* 116 */     return naming;
/*     */   }
/*     */ 
/*     */   public PostScriptTable getPostScript()
/*     */   {
/* 126 */     PostScriptTable postscript = (PostScriptTable)this.tables.get("post");
/* 127 */     if ((postscript != null) && (!postscript.getInitialized()))
/*     */     {
/* 129 */       initializeTable(postscript);
/*     */     }
/* 131 */     return postscript;
/*     */   }
/*     */ 
/*     */   public OS2WindowsMetricsTable getOS2Windows()
/*     */   {
/* 141 */     OS2WindowsMetricsTable os2WindowsMetrics = (OS2WindowsMetricsTable)this.tables.get("OS/2");
/* 142 */     if ((os2WindowsMetrics != null) && (!os2WindowsMetrics.getInitialized()))
/*     */     {
/* 144 */       initializeTable(os2WindowsMetrics);
/*     */     }
/* 146 */     return os2WindowsMetrics;
/*     */   }
/*     */ 
/*     */   public MaximumProfileTable getMaximumProfile()
/*     */   {
/* 156 */     MaximumProfileTable maximumProfile = (MaximumProfileTable)this.tables.get("maxp");
/* 157 */     if ((maximumProfile != null) && (!maximumProfile.getInitialized()))
/*     */     {
/* 159 */       initializeTable(maximumProfile);
/*     */     }
/* 161 */     return maximumProfile;
/*     */   }
/*     */ 
/*     */   public HeaderTable getHeader()
/*     */   {
/* 171 */     HeaderTable header = (HeaderTable)this.tables.get("head");
/* 172 */     if ((header != null) && (!header.getInitialized()))
/*     */     {
/* 174 */       initializeTable(header);
/*     */     }
/* 176 */     return header;
/*     */   }
/*     */ 
/*     */   public HorizontalHeaderTable getHorizontalHeader()
/*     */   {
/* 186 */     HorizontalHeaderTable horizontalHeader = (HorizontalHeaderTable)this.tables.get("hhea");
/* 187 */     if ((horizontalHeader != null) && (!horizontalHeader.getInitialized()))
/*     */     {
/* 189 */       initializeTable(horizontalHeader);
/*     */     }
/* 191 */     return horizontalHeader;
/*     */   }
/*     */ 
/*     */   public HorizontalMetricsTable getHorizontalMetrics()
/*     */   {
/* 201 */     HorizontalMetricsTable horizontalMetrics = (HorizontalMetricsTable)this.tables.get("hmtx");
/* 202 */     if ((horizontalMetrics != null) && (!horizontalMetrics.getInitialized()))
/*     */     {
/* 204 */       initializeTable(horizontalMetrics);
/*     */     }
/* 206 */     return horizontalMetrics;
/*     */   }
/*     */ 
/*     */   public IndexToLocationTable getIndexToLocation()
/*     */   {
/* 216 */     IndexToLocationTable indexToLocation = (IndexToLocationTable)this.tables.get("loca");
/* 217 */     if ((indexToLocation != null) && (!indexToLocation.getInitialized()))
/*     */     {
/* 219 */       initializeTable(indexToLocation);
/*     */     }
/* 221 */     return indexToLocation;
/*     */   }
/*     */ 
/*     */   public GlyphTable getGlyph()
/*     */   {
/* 231 */     GlyphTable glyph = (GlyphTable)this.tables.get("glyf");
/* 232 */     if ((glyph != null) && (!glyph.getInitialized()))
/*     */     {
/* 234 */       initializeTable(glyph);
/*     */     }
/* 236 */     return glyph;
/*     */   }
/*     */ 
/*     */   public CMAPTable getCMAP()
/*     */   {
/* 246 */     CMAPTable cmap = (CMAPTable)this.tables.get("cmap");
/* 247 */     if ((cmap != null) && (!cmap.getInitialized()))
/*     */     {
/* 249 */       initializeTable(cmap);
/*     */     }
/* 251 */     return cmap;
/*     */   }
/*     */ 
/*     */   public InputStream getOriginalData()
/*     */     throws IOException
/*     */   {
/* 265 */     return this.data.getOriginalData();
/*     */   }
/*     */ 
/*     */   public void initializeTable(TTFTable table)
/*     */   {
/*     */     try
/*     */     {
/* 278 */       long currentPosition = this.data.getCurrentPosition();
/* 279 */       this.data.seek(table.getOffset());
/* 280 */       table.initData(this, this.data);
/*     */ 
/* 282 */       this.data.seek(currentPosition);
/*     */     }
/*     */     catch (IOException exception)
/*     */     {
/* 286 */       this.log.error("An error occured when reading table " + table.getTag(), exception);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getNumberOfGlyphs()
/*     */   {
/* 297 */     if (this.numberOfGlyphs == -1)
/*     */     {
/* 299 */       MaximumProfileTable maximumProfile = getMaximumProfile();
/* 300 */       if (maximumProfile != null)
/*     */       {
/* 302 */         this.numberOfGlyphs = maximumProfile.getNumGlyphs();
/*     */       }
/*     */       else
/*     */       {
/* 307 */         this.numberOfGlyphs = 0;
/*     */       }
/*     */     }
/* 310 */     return this.numberOfGlyphs;
/*     */   }
/*     */ 
/*     */   public int getUnitsPerEm()
/*     */   {
/* 320 */     if (this.unitsPerEm == -1)
/*     */     {
/* 322 */       HeaderTable header = getHeader();
/* 323 */       if (header != null)
/*     */       {
/* 325 */         this.unitsPerEm = header.getUnitsPerEm();
/*     */       }
/*     */       else
/*     */       {
/* 330 */         this.unitsPerEm = 0;
/*     */       }
/*     */     }
/* 333 */     return this.unitsPerEm;
/*     */   }
/*     */ 
/*     */   public int getAdvanceWidth(int code)
/*     */   {
/* 344 */     if (this.advanceWidths == null)
/*     */     {
/* 346 */       HorizontalMetricsTable hmtx = getHorizontalMetrics();
/* 347 */       if (hmtx != null)
/*     */       {
/* 349 */         this.advanceWidths = hmtx.getAdvanceWidth();
/*     */       }
/*     */       else
/*     */       {
/* 354 */         this.advanceWidths = new int[] { 250 };
/*     */       }
/*     */     }
/* 357 */     if (this.advanceWidths.length > code)
/*     */     {
/* 359 */       return this.advanceWidths[code];
/*     */     }
/*     */ 
/* 365 */     return this.advanceWidths[(this.advanceWidths.length - 1)];
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.TrueTypeFont
 * JD-Core Version:    0.6.2
 */