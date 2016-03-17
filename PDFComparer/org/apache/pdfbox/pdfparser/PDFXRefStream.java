/*     */ package org.apache.pdfbox.pdfparser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.io.RandomAccessBuffer;
/*     */ import org.apache.pdfbox.pdfwriter.COSWriterXRefEntry;
/*     */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*     */ 
/*     */ public class PDFXRefStream
/*     */   implements PDFXRef
/*     */ {
/*     */   private static final int ENTRY_OBJSTREAM = 2;
/*     */   private static final int ENTRY_NORMAL = 1;
/*     */   private static final int ENTRY_FREE = 0;
/*     */   private Map<Integer, Object> streamData;
/*     */   private Set<Integer> objectNumbers;
/*     */   private COSStream stream;
/*  58 */   private long size = -1L;
/*     */ 
/*     */   public PDFXRefStream()
/*     */   {
/*  65 */     this.stream = new COSStream(new COSDictionary(), new RandomAccessBuffer());
/*  66 */     this.streamData = new TreeMap();
/*  67 */     this.objectNumbers = new TreeSet();
/*     */   }
/*     */ 
/*     */   public COSStream getStream()
/*     */     throws IOException
/*     */   {
/*  77 */     this.stream.setItem(COSName.TYPE, COSName.XREF);
/*  78 */     if (this.size == -1L)
/*     */     {
/*  80 */       throw new IllegalArgumentException("size is not set in xrefstream");
/*     */     }
/*  82 */     this.stream.setLong(COSName.SIZE, getSizeEntry());
/*  83 */     this.stream.setFilters(COSName.FLATE_DECODE);
/*     */ 
/*  86 */     List indexEntry = getIndexEntry();
/*  87 */     COSArray indexAsArray = new COSArray();
/*  88 */     for (Integer i : indexEntry)
/*     */     {
/*  90 */       indexAsArray.add(COSInteger.get(i.intValue()));
/*     */     }
/*  92 */     this.stream.setItem(COSName.INDEX, indexAsArray);
/*     */ 
/*  95 */     int[] wEntry = getWEntry();
/*  96 */     COSArray wAsArray = new COSArray();
/*  97 */     for (int i = 0; i < wEntry.length; i++)
/*     */     {
/*  99 */       int j = wEntry[i];
/* 100 */       wAsArray.add(COSInteger.get(j));
/*     */     }
/* 102 */     this.stream.setItem(COSName.W, wAsArray);
/* 103 */     OutputStream unfilteredStream = this.stream.createUnfilteredStream();
/* 104 */     writeStreamData(unfilteredStream, wEntry);
/*     */ 
/* 106 */     Set keySet = this.stream.keySet();
/* 107 */     for (COSName cosName : keySet)
/*     */     {
/* 109 */       COSBase dictionaryObject = this.stream.getDictionaryObject(cosName);
/* 110 */       dictionaryObject.setDirect(true);
/*     */     }
/* 112 */     return this.stream;
/*     */   }
/*     */ 
/*     */   public void addTrailerInfo(COSDictionary trailerDict)
/*     */   {
/* 122 */     Set entrySet = trailerDict.entrySet();
/* 123 */     for (Map.Entry entry : entrySet)
/*     */     {
/* 125 */       COSName key = (COSName)entry.getKey();
/* 126 */       if ((COSName.INFO.equals(key)) || (COSName.ROOT.equals(key)) || (COSName.ENCRYPT.equals(key)) || (COSName.ID.equals(key)) || (COSName.PREV.equals(key)))
/*     */       {
/* 129 */         this.stream.setItem(key, (COSBase)entry.getValue());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addEntry(COSWriterXRefEntry entry)
/*     */   {
/* 141 */     this.objectNumbers.add(Integer.valueOf((int)entry.getKey().getNumber()));
/* 142 */     if (entry.isFree())
/*     */     {
/* 145 */       FreeReference value = new FreeReference();
/* 146 */       value.nextGenNumber = entry.getKey().getGeneration();
/* 147 */       value.nextFree = entry.getKey().getNumber();
/* 148 */       this.streamData.put(Integer.valueOf((int)value.nextFree), value);
/*     */     }
/*     */     else
/*     */     {
/* 154 */       NormalReference value = new NormalReference();
/* 155 */       value.genNumber = entry.getKey().getGeneration();
/* 156 */       value.offset = entry.getOffset();
/* 157 */       this.streamData.put(Integer.valueOf((int)entry.getKey().getNumber()), value);
/*     */     }
/*     */   }
/*     */ 
/*     */   private int[] getWEntry()
/*     */   {
/* 168 */     long[] wMax = new long[3];
/* 169 */     for (Iterator i$ = this.streamData.values().iterator(); i$.hasNext(); ) { Object entry = i$.next();
/*     */ 
/* 171 */       if ((entry instanceof FreeReference))
/*     */       {
/* 173 */         FreeReference free = (FreeReference)entry;
/* 174 */         wMax[0] = Math.max(wMax[0], 0L);
/* 175 */         wMax[1] = Math.max(wMax[1], free.nextFree);
/* 176 */         wMax[2] = Math.max(wMax[2], free.nextGenNumber);
/*     */       }
/* 178 */       else if ((entry instanceof NormalReference))
/*     */       {
/* 180 */         NormalReference ref = (NormalReference)entry;
/* 181 */         wMax[0] = Math.max(wMax[0], 1L);
/* 182 */         wMax[1] = Math.max(wMax[1], ref.offset);
/* 183 */         wMax[2] = Math.max(wMax[2], ref.genNumber);
/*     */       }
/* 185 */       else if ((entry instanceof ObjectStreamReference))
/*     */       {
/* 187 */         ObjectStreamReference objStream = (ObjectStreamReference)entry;
/* 188 */         wMax[0] = Math.max(wMax[0], 2L);
/* 189 */         wMax[1] = Math.max(wMax[1], objStream.offset);
/* 190 */         wMax[2] = Math.max(wMax[2], objStream.objectNumberOfObjectStream);
/*     */       }
/*     */       else
/*     */       {
/* 195 */         throw new RuntimeException("unexpected reference type");
/*     */       }
/*     */     }
/*     */ 
/* 199 */     int[] w = new int[3];
/* 200 */     for (int i = 0; i < w.length; i++)
/*     */     {
/* 202 */       while (wMax[i] > 0L)
/*     */       {
/* 204 */         w[i] += 1;
/* 205 */         wMax[i] >>= 8;
/*     */       }
/*     */     }
/* 208 */     return w;
/*     */   }
/*     */ 
/*     */   private long getSizeEntry()
/*     */   {
/* 213 */     return this.size;
/*     */   }
/*     */ 
/*     */   public void setSize(long streamSize)
/*     */   {
/* 223 */     this.size = streamSize;
/*     */   }
/*     */ 
/*     */   private List<Integer> getIndexEntry()
/*     */   {
/* 228 */     LinkedList linkedList = new LinkedList();
/* 229 */     Integer first = null;
/* 230 */     Integer length = null;
/*     */ 
/* 232 */     for (Integer objNumber : this.objectNumbers)
/*     */     {
/* 234 */       if (first == null)
/*     */       {
/* 236 */         first = objNumber;
/* 237 */         length = Integer.valueOf(1);
/*     */       }
/* 239 */       if (first.intValue() + length.intValue() == objNumber.intValue())
/*     */       {
/* 241 */         length = Integer.valueOf(length.intValue() + 1);
/*     */       }
/* 243 */       if (first.intValue() + length.intValue() < objNumber.intValue())
/*     */       {
/* 245 */         linkedList.add(first);
/* 246 */         linkedList.add(length);
/* 247 */         first = objNumber;
/* 248 */         length = Integer.valueOf(1);
/*     */       }
/*     */     }
/* 251 */     linkedList.add(first);
/* 252 */     linkedList.add(length);
/*     */ 
/* 254 */     return linkedList;
/*     */   }
/*     */ 
/*     */   private void writeNumber(OutputStream os, long number, int bytes) throws IOException
/*     */   {
/* 259 */     byte[] buffer = new byte[bytes];
/* 260 */     for (int i = 0; i < bytes; i++)
/*     */     {
/* 262 */       buffer[i] = ((byte)(int)(number & 0xFF));
/* 263 */       number >>= 8;
/*     */     }
/*     */ 
/* 266 */     for (int i = 0; i < bytes; i++)
/*     */     {
/* 268 */       os.write(buffer[(bytes - i - 1)]);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeStreamData(OutputStream os, int[] w)
/*     */     throws IOException
/*     */   {
/* 275 */     for (Iterator i$ = this.streamData.values().iterator(); i$.hasNext(); ) { Object entry = i$.next();
/*     */ 
/* 277 */       if ((entry instanceof FreeReference))
/*     */       {
/* 279 */         FreeReference free = (FreeReference)entry;
/* 280 */         writeNumber(os, 0L, w[0]);
/* 281 */         writeNumber(os, free.nextFree, w[1]);
/* 282 */         writeNumber(os, free.nextGenNumber, w[2]);
/*     */       }
/* 284 */       else if ((entry instanceof NormalReference))
/*     */       {
/* 286 */         NormalReference ref = (NormalReference)entry;
/* 287 */         writeNumber(os, 1L, w[0]);
/* 288 */         writeNumber(os, ref.offset, w[1]);
/* 289 */         writeNumber(os, ref.genNumber, w[2]);
/*     */       }
/* 291 */       else if ((entry instanceof ObjectStreamReference))
/*     */       {
/* 293 */         ObjectStreamReference objStream = (ObjectStreamReference)entry;
/* 294 */         writeNumber(os, 2L, w[0]);
/* 295 */         writeNumber(os, objStream.offset, w[1]);
/* 296 */         writeNumber(os, objStream.objectNumberOfObjectStream, w[2]);
/*     */       }
/*     */       else
/*     */       {
/* 301 */         throw new RuntimeException("unexpected reference type");
/*     */       }
/*     */     }
/* 304 */     os.flush();
/* 305 */     os.close();
/*     */   }
/*     */ 
/*     */   public COSObject getObject(int objectNumber)
/*     */   {
/* 343 */     return null;
/*     */   }
/*     */ 
/*     */   class FreeReference
/*     */   {
/*     */     long nextGenNumber;
/*     */     long nextFree;
/*     */ 
/*     */     FreeReference()
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   class NormalReference
/*     */   {
/*     */     long genNumber;
/*     */     long offset;
/*     */ 
/*     */     NormalReference()
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   class ObjectStreamReference
/*     */   {
/*     */     long objectNumberOfObjectStream;
/*     */     long offset;
/*     */ 
/*     */     ObjectStreamReference()
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfparser.PDFXRefStream
 * JD-Core Version:    0.6.2
 */