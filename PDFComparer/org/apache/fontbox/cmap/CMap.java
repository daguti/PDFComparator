/*     */ package org.apache.fontbox.cmap;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class CMap
/*     */ {
/*  38 */   private static final Log LOG = LogFactory.getLog(CMap.class);
/*     */ 
/*  40 */   private int wmode = 0;
/*  41 */   private String cmapName = null;
/*  42 */   private String cmapVersion = null;
/*  43 */   private int cmapType = -1;
/*     */ 
/*  45 */   private String registry = null;
/*  46 */   private String ordering = null;
/*  47 */   private int supplement = 0;
/*     */ 
/*  49 */   private List<CodespaceRange> codeSpaceRanges = new ArrayList();
/*  50 */   private Map<Integer, String> singleByteMappings = new HashMap();
/*  51 */   private Map<Integer, String> doubleByteMappings = new HashMap();
/*     */ 
/*  53 */   private final Map<Integer, String> cid2charMappings = new HashMap();
/*  54 */   private final Map<String, Integer> char2CIDMappings = new HashMap();
/*  55 */   private final List<CIDRange> cidRanges = new LinkedList();
/*     */   private static final String SPACE = " ";
/*  58 */   private int spaceMapping = -1;
/*     */ 
/*     */   public boolean hasOneByteMappings()
/*     */   {
/*  75 */     return this.singleByteMappings.size() > 0;
/*     */   }
/*     */ 
/*     */   public boolean hasTwoByteMappings()
/*     */   {
/*  85 */     return this.doubleByteMappings.size() > 0;
/*     */   }
/*     */ 
/*     */   public boolean hasCIDMappings()
/*     */   {
/*  95 */     return (!this.char2CIDMappings.isEmpty()) || (!this.cidRanges.isEmpty());
/*     */   }
/*     */ 
/*     */   public String lookup(byte[] code, int offset, int length)
/*     */   {
/* 109 */     return lookup(getCodeFromArray(code, offset, length), length);
/*     */   }
/*     */ 
/*     */   public String lookup(int code, int length)
/*     */   {
/* 122 */     String result = null;
/* 123 */     if (length == 1)
/*     */     {
/* 125 */       result = (String)this.singleByteMappings.get(Integer.valueOf(code));
/*     */     }
/* 127 */     else if (length == 2)
/*     */     {
/* 129 */       result = (String)this.doubleByteMappings.get(Integer.valueOf(code));
/*     */     }
/* 131 */     return result;
/*     */   }
/*     */ 
/*     */   public String lookupCID(int cid)
/*     */   {
/* 143 */     if (this.cid2charMappings.containsKey(Integer.valueOf(cid)))
/*     */     {
/* 145 */       return (String)this.cid2charMappings.get(Integer.valueOf(cid));
/*     */     }
/*     */ 
/* 149 */     for (CIDRange range : this.cidRanges)
/*     */     {
/* 151 */       int ch = range.unmap(cid);
/* 152 */       if (ch != -1)
/*     */       {
/* 154 */         return Character.toString((char)ch);
/*     */       }
/*     */     }
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   public int lookupCID(byte[] code, int offset, int length)
/*     */   {
/* 172 */     if (isInCodeSpaceRanges(code, offset, length))
/*     */     {
/* 174 */       int codeAsInt = getCodeFromArray(code, offset, length);
/* 175 */       if (this.char2CIDMappings.containsKey(Integer.valueOf(codeAsInt)))
/*     */       {
/* 177 */         return ((Integer)this.char2CIDMappings.get(Integer.valueOf(codeAsInt))).intValue();
/*     */       }
/*     */ 
/* 181 */       for (CIDRange range : this.cidRanges)
/*     */       {
/* 183 */         int ch = range.map((char)codeAsInt);
/* 184 */         if (ch != -1)
/*     */         {
/* 186 */           return ch;
/*     */         }
/*     */       }
/* 189 */       return -1;
/*     */     }
/*     */ 
/* 192 */     return -1;
/*     */   }
/*     */ 
/*     */   private int getCodeFromArray(byte[] data, int offset, int length)
/*     */   {
/* 204 */     int code = 0;
/* 205 */     for (int i = 0; i < length; i++)
/*     */     {
/* 207 */       code <<= 8;
/* 208 */       code |= (data[(offset + i)] + 256) % 256;
/*     */     }
/* 210 */     return code;
/*     */   }
/*     */ 
/*     */   public void addMapping(byte[] src, String dest)
/*     */     throws IOException
/*     */   {
/* 224 */     int srcLength = src.length;
/* 225 */     int intSrc = getCodeFromArray(src, 0, srcLength);
/* 226 */     if (" ".equals(dest))
/*     */     {
/* 228 */       this.spaceMapping = intSrc;
/*     */     }
/* 230 */     if (srcLength == 1)
/*     */     {
/* 232 */       this.singleByteMappings.put(Integer.valueOf(intSrc), dest);
/*     */     }
/* 234 */     else if (srcLength == 2)
/*     */     {
/* 236 */       this.doubleByteMappings.put(Integer.valueOf(intSrc), dest);
/*     */     }
/*     */     else
/*     */     {
/* 241 */       LOG.error("Mapping code should be 1 or two bytes and not " + src.length);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addCIDMapping(int src, String dest)
/*     */     throws IOException
/*     */   {
/* 255 */     this.cid2charMappings.put(Integer.valueOf(src), dest);
/* 256 */     this.char2CIDMappings.put(dest, Integer.valueOf(src));
/*     */   }
/*     */ 
/*     */   public void addCIDRange(char from, char to, int cid)
/*     */   {
/* 269 */     this.cidRanges.add(0, new CIDRange(from, to, cid));
/*     */   }
/*     */ 
/*     */   public void addCodespaceRange(CodespaceRange range)
/*     */   {
/* 279 */     this.codeSpaceRanges.add(range);
/*     */   }
/*     */ 
/*     */   public List<CodespaceRange> getCodeSpaceRanges()
/*     */   {
/* 289 */     return this.codeSpaceRanges;
/*     */   }
/*     */ 
/*     */   public void useCmap(CMap cmap)
/*     */   {
/* 300 */     this.codeSpaceRanges.addAll(cmap.codeSpaceRanges);
/* 301 */     this.singleByteMappings.putAll(cmap.singleByteMappings);
/* 302 */     this.doubleByteMappings.putAll(cmap.doubleByteMappings);
/*     */   }
/*     */ 
/*     */   public boolean isInCodeSpaceRanges(byte[] code)
/*     */   {
/* 314 */     return isInCodeSpaceRanges(code, 0, code.length);
/*     */   }
/*     */ 
/*     */   public boolean isInCodeSpaceRanges(byte[] code, int offset, int length)
/*     */   {
/* 328 */     Iterator it = this.codeSpaceRanges.iterator();
/* 329 */     while (it.hasNext())
/*     */     {
/* 331 */       CodespaceRange range = (CodespaceRange)it.next();
/* 332 */       if ((range != null) && (range.isInRange(code, offset, length)))
/*     */       {
/* 334 */         return true;
/*     */       }
/*     */     }
/* 337 */     return false;
/*     */   }
/*     */ 
/*     */   public int getWMode()
/*     */   {
/* 349 */     return this.wmode;
/*     */   }
/*     */ 
/*     */   public void setWMode(int newWMode)
/*     */   {
/* 359 */     this.wmode = newWMode;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 369 */     return this.cmapName;
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 379 */     this.cmapName = name;
/*     */   }
/*     */ 
/*     */   public String getVersion()
/*     */   {
/* 389 */     return this.cmapVersion;
/*     */   }
/*     */ 
/*     */   public void setVersion(String version)
/*     */   {
/* 399 */     this.cmapVersion = version;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/* 409 */     return this.cmapType;
/*     */   }
/*     */ 
/*     */   public void setType(int type)
/*     */   {
/* 419 */     this.cmapType = type;
/*     */   }
/*     */ 
/*     */   public String getRegistry()
/*     */   {
/* 429 */     return this.registry;
/*     */   }
/*     */ 
/*     */   public void setRegistry(String newRegistry)
/*     */   {
/* 439 */     this.registry = newRegistry;
/*     */   }
/*     */ 
/*     */   public String getOrdering()
/*     */   {
/* 449 */     return this.ordering;
/*     */   }
/*     */ 
/*     */   public void setOrdering(String newOrdering)
/*     */   {
/* 459 */     this.ordering = newOrdering;
/*     */   }
/*     */ 
/*     */   public int getSupplement()
/*     */   {
/* 469 */     return this.supplement;
/*     */   }
/*     */ 
/*     */   public void setSupplement(int newSupplement)
/*     */   {
/* 479 */     this.supplement = newSupplement;
/*     */   }
/*     */ 
/*     */   public int getSpaceMapping()
/*     */   {
/* 489 */     return this.spaceMapping;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cmap.CMap
 * JD-Core Version:    0.6.2
 */