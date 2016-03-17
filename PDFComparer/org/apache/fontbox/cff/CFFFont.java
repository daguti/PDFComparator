/*     */ package org.apache.fontbox.cff;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.fontbox.cff.charset.CFFCharset;
/*     */ import org.apache.fontbox.cff.charset.CFFCharset.Entry;
/*     */ import org.apache.fontbox.cff.encoding.CFFEncoding;
/*     */ import org.apache.fontbox.cff.encoding.CFFEncoding.Entry;
/*     */ 
/*     */ public class CFFFont
/*     */ {
/*     */   private String fontname;
/*     */   private Map<String, Object> topDict;
/*     */   private Map<String, Object> privateDict;
/*     */   private CFFEncoding fontEncoding;
/*     */   private CFFCharset fontCharset;
/*     */   private Map<String, byte[]> charStringsDict;
/*     */   private IndexData globalSubrIndex;
/*     */   private IndexData localSubrIndex;
/*     */ 
/*     */   public CFFFont()
/*     */   {
/*  41 */     this.fontname = null;
/*  42 */     this.topDict = new LinkedHashMap();
/*  43 */     this.privateDict = new LinkedHashMap();
/*  44 */     this.fontEncoding = null;
/*  45 */     this.fontCharset = null;
/*  46 */     this.charStringsDict = new LinkedHashMap();
/*  47 */     this.globalSubrIndex = null;
/*  48 */     this.localSubrIndex = null;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  56 */     return this.fontname;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  65 */     this.fontname = name;
/*     */   }
/*     */ 
/*     */   public Object getProperty(String name)
/*     */   {
/*  75 */     Object topDictValue = this.topDict.get(name);
/*  76 */     if (topDictValue != null)
/*     */     {
/*  78 */       return topDictValue;
/*     */     }
/*  80 */     Object privateDictValue = this.privateDict.get(name);
/*  81 */     if (privateDictValue != null)
/*     */     {
/*  83 */       return privateDictValue;
/*     */     }
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */   public void addValueToTopDict(String name, Object value)
/*     */   {
/*  95 */     if (value != null)
/*     */     {
/*  97 */       this.topDict.put(name, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map<String, Object> getTopDict()
/*     */   {
/* 106 */     return this.topDict;
/*     */   }
/*     */ 
/*     */   public void addValueToPrivateDict(String name, Object value)
/*     */   {
/* 116 */     if (value != null)
/*     */     {
/* 118 */       this.privateDict.put(name, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map<String, Object> getPrivateDict()
/*     */   {
/* 127 */     return this.privateDict;
/*     */   }
/*     */ 
/*     */   public Collection<Mapping> getMappings()
/*     */   {
/* 136 */     List mappings = new ArrayList();
/* 137 */     Set mappedNames = new HashSet();
/* 138 */     for (CFFEncoding.Entry entry : this.fontEncoding.getEntries())
/*     */     {
/* 140 */       String charName = this.fontCharset.getName(entry.getSID());
/*     */ 
/* 142 */       if (charName != null)
/*     */       {
/* 146 */         byte[] bytes = (byte[])this.charStringsDict.get(charName);
/* 147 */         if (bytes != null)
/*     */         {
/* 151 */           Mapping mapping = new Mapping();
/* 152 */           mapping.setCode(entry.getCode());
/* 153 */           mapping.setSID(entry.getSID());
/* 154 */           mapping.setName(charName);
/* 155 */           mapping.setBytes(bytes);
/* 156 */           mappings.add(mapping);
/* 157 */           mappedNames.add(charName);
/*     */         }
/*     */       }
/*     */     }
/* 159 */     if ((this.fontEncoding instanceof CFFParser.EmbeddedEncoding))
/*     */     {
/* 161 */       CFFParser.EmbeddedEncoding embeddedEncoding = (CFFParser.EmbeddedEncoding)this.fontEncoding;
/*     */ 
/* 163 */       for (CFFParser.EmbeddedEncoding.Supplement supplement : embeddedEncoding.getSupplements())
/*     */       {
/* 165 */         String charName = this.fontCharset.getName(supplement.getGlyph());
/* 166 */         if (charName != null)
/*     */         {
/* 170 */           byte[] bytes = (byte[])this.charStringsDict.get(charName);
/* 171 */           if (bytes != null)
/*     */           {
/* 175 */             Mapping mapping = new Mapping();
/* 176 */             mapping.setCode(supplement.getCode());
/* 177 */             mapping.setSID(supplement.getGlyph());
/* 178 */             mapping.setName(charName);
/* 179 */             mapping.setBytes(bytes);
/* 180 */             mappings.add(mapping);
/* 181 */             mappedNames.add(charName);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 185 */     int code = 256;
/* 186 */     for (CFFCharset.Entry entry : this.fontCharset.getEntries())
/*     */     {
/* 188 */       String name = entry.getName();
/* 189 */       if (!mappedNames.contains(name))
/*     */       {
/* 193 */         byte[] bytes = (byte[])this.charStringsDict.get(name);
/* 194 */         if (bytes != null)
/*     */         {
/* 198 */           Mapping mapping = new Mapping();
/* 199 */           mapping.setCode(code++);
/* 200 */           mapping.setSID(entry.getSID());
/* 201 */           mapping.setName(name);
/* 202 */           mapping.setBytes(bytes);
/*     */ 
/* 204 */           mappings.add(mapping);
/*     */ 
/* 206 */           mappedNames.add(name);
/*     */         }
/*     */       }
/*     */     }
/* 208 */     return mappings;
/*     */   }
/*     */ 
/*     */   public int getWidth(int SID)
/*     */     throws IOException
/*     */   {
/* 219 */     int nominalWidth = this.privateDict.containsKey("nominalWidthX") ? ((Number)this.privateDict.get("nominalWidthX")).intValue() : 0;
/* 220 */     int defaultWidth = this.privateDict.containsKey("defaultWidthX") ? ((Number)this.privateDict.get("defaultWidthX")).intValue() : 1000;
/*     */ 
/* 222 */     for (Mapping m : getMappings()) {
/* 223 */       if (m.getSID() == SID)
/*     */       {
/* 225 */         CharStringRenderer csr = null;
/* 226 */         if (((Number)getProperty("CharstringType")).intValue() == 2) {
/* 227 */           List lSeq = m.toType2Sequence();
/* 228 */           csr = new CharStringRenderer(false);
/* 229 */           csr.render(lSeq);
/*     */         } else {
/* 231 */           List lSeq = m.toType1Sequence();
/* 232 */           csr = new CharStringRenderer();
/* 233 */           csr.render(lSeq);
/*     */         }
/*     */ 
/* 238 */         return csr.getWidth() != 0 ? csr.getWidth() + nominalWidth : defaultWidth;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 243 */     return getNotDefWidth(defaultWidth, nominalWidth);
/*     */   }
/*     */ 
/*     */   protected int getNotDefWidth(int defaultWidth, int nominalWidth) throws IOException
/*     */   {
/* 248 */     byte[] glyphDesc = (byte[])getCharStringsDict().get(".notdef");
/*     */     CharStringRenderer csr;
/* 249 */     if (((Number)getProperty("CharstringType")).intValue() == 2) {
/* 250 */       Type2CharStringParser parser = new Type2CharStringParser();
/* 251 */       List lSeq = parser.parse(glyphDesc, getGlobalSubrIndex(), getLocalSubrIndex());
/* 252 */       CharStringRenderer csr = new CharStringRenderer(false);
/* 253 */       csr.render(lSeq);
/*     */     } else {
/* 255 */       Type1CharStringParser parser = new Type1CharStringParser();
/* 256 */       List lSeq = parser.parse(glyphDesc, getLocalSubrIndex());
/* 257 */       csr = new CharStringRenderer();
/* 258 */       csr.render(lSeq);
/*     */     }
/* 260 */     return csr.getWidth() != 0 ? csr.getWidth() + nominalWidth : defaultWidth;
/*     */   }
/*     */ 
/*     */   public CFFEncoding getEncoding()
/*     */   {
/* 269 */     return this.fontEncoding;
/*     */   }
/*     */ 
/*     */   public void setEncoding(CFFEncoding encoding)
/*     */   {
/* 278 */     this.fontEncoding = encoding;
/*     */   }
/*     */ 
/*     */   public CFFCharset getCharset()
/*     */   {
/* 287 */     return this.fontCharset;
/*     */   }
/*     */ 
/*     */   public void setCharset(CFFCharset charset)
/*     */   {
/* 296 */     this.fontCharset = charset;
/*     */   }
/*     */ 
/*     */   public Map<String, byte[]> getCharStringsDict()
/*     */   {
/* 305 */     return this.charStringsDict;
/*     */   }
/*     */ 
/*     */   public CharStringConverter createConverter()
/*     */   {
/* 314 */     Number defaultWidthX = (Number)getProperty("defaultWidthX");
/* 315 */     Number nominalWidthX = (Number)getProperty("nominalWidthX");
/* 316 */     return new CharStringConverter(defaultWidthX.intValue(), nominalWidthX.intValue());
/*     */   }
/*     */ 
/*     */   public CharStringRenderer createRenderer()
/*     */   {
/* 325 */     return new CharStringRenderer();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 333 */     return getClass().getName() + "[name=" + this.fontname + ", topDict=" + this.topDict + ", privateDict=" + this.privateDict + ", encoding=" + this.fontEncoding + ", charset=" + this.fontCharset + ", charStringsDict=" + this.charStringsDict + "]";
/*     */   }
/*     */ 
/*     */   public void setGlobalSubrIndex(IndexData globalSubrIndex)
/*     */   {
/* 345 */     this.globalSubrIndex = globalSubrIndex;
/*     */   }
/*     */ 
/*     */   public IndexData getGlobalSubrIndex()
/*     */   {
/* 353 */     return this.globalSubrIndex;
/*     */   }
/*     */ 
/*     */   public IndexData getLocalSubrIndex()
/*     */   {
/* 361 */     return this.localSubrIndex;
/*     */   }
/*     */ 
/*     */   public void setLocalSubrIndex(IndexData localSubrIndex)
/*     */   {
/* 369 */     this.localSubrIndex = localSubrIndex;
/*     */   }
/*     */ 
/*     */   public class Mapping
/*     */   {
/*     */     private int mappedCode;
/*     */     private int mappedSID;
/*     */     private String mappedName;
/*     */     private byte[] mappedBytes;
/*     */ 
/*     */     public Mapping()
/*     */     {
/*     */     }
/*     */ 
/*     */     public List<Object> toType1Sequence()
/*     */       throws IOException
/*     */     {
/* 390 */       CharStringConverter converter = CFFFont.this.createConverter();
/* 391 */       return converter.convert(toType2Sequence());
/*     */     }
/*     */ 
/*     */     public List<Object> toType2Sequence()
/*     */       throws IOException
/*     */     {
/* 401 */       Type2CharStringParser parser = new Type2CharStringParser();
/* 402 */       return parser.parse(getBytes(), CFFFont.this.getGlobalSubrIndex(), CFFFont.this.getLocalSubrIndex());
/*     */     }
/*     */ 
/*     */     public int getCode()
/*     */     {
/* 411 */       return this.mappedCode;
/*     */     }
/*     */ 
/*     */     private void setCode(int code)
/*     */     {
/* 416 */       this.mappedCode = code;
/*     */     }
/*     */ 
/*     */     public int getSID()
/*     */     {
/* 425 */       return this.mappedSID;
/*     */     }
/*     */ 
/*     */     private void setSID(int sid)
/*     */     {
/* 430 */       this.mappedSID = sid;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 439 */       return this.mappedName;
/*     */     }
/*     */ 
/*     */     private void setName(String name)
/*     */     {
/* 444 */       this.mappedName = name;
/*     */     }
/*     */ 
/*     */     public byte[] getBytes()
/*     */     {
/* 453 */       return this.mappedBytes;
/*     */     }
/*     */ 
/*     */     private void setBytes(byte[] bytes)
/*     */     {
/* 458 */       this.mappedBytes = bytes;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.CFFFont
 * JD-Core Version:    0.6.2
 */