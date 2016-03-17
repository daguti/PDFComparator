/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.SequenceInputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.cos.ICOSVisitor;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ import org.apache.pdfbox.io.RandomAccess;
/*     */ import org.apache.pdfbox.pdfparser.PDFStreamParser;
/*     */ 
/*     */ public class COSStreamArray extends COSStream
/*     */ {
/*     */   private COSArray streams;
/*     */   private COSStream firstStream;
/*     */ 
/*     */   public COSStreamArray(COSArray array)
/*     */   {
/*  64 */     super(new COSDictionary(), null);
/*  65 */     this.streams = array;
/*  66 */     if (array.size() > 0)
/*     */     {
/*  68 */       this.firstStream = ((COSStream)array.getObject(0));
/*     */     }
/*     */   }
/*     */ 
/*     */   public COSBase get(int index)
/*     */   {
/*  80 */     return this.streams.get(index);
/*     */   }
/*     */ 
/*     */   public int getStreamCount()
/*     */   {
/*  90 */     return this.streams.size();
/*     */   }
/*     */ 
/*     */   public RandomAccess getScratchFile()
/*     */   {
/* 101 */     return this.firstStream.getScratchFile();
/*     */   }
/*     */ 
/*     */   public COSBase getItem(COSName key)
/*     */   {
/* 113 */     return this.firstStream.getItem(key);
/*     */   }
/*     */ 
/*     */   public COSBase getDictionaryObject(COSName key)
/*     */   {
/* 126 */     return this.firstStream.getDictionaryObject(key);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 134 */     return "COSStream{}";
/*     */   }
/*     */ 
/*     */   public List getStreamTokens()
/*     */     throws IOException
/*     */   {
/* 146 */     List retval = null;
/* 147 */     if (this.streams.size() > 0)
/*     */     {
/* 149 */       PDFStreamParser parser = new PDFStreamParser(this);
/* 150 */       parser.parse();
/* 151 */       retval = parser.getTokens();
/*     */     }
/*     */     else
/*     */     {
/* 155 */       retval = new ArrayList();
/*     */     }
/* 157 */     return retval;
/*     */   }
/*     */ 
/*     */   public COSDictionary getDictionary()
/*     */   {
/* 167 */     return this.firstStream;
/*     */   }
/*     */ 
/*     */   public InputStream getFilteredStream()
/*     */     throws IOException
/*     */   {
/* 179 */     throw new IOException("Error: Not allowed to get filtered stream from array of streams.");
/*     */   }
/*     */ 
/*     */   public InputStream getUnfilteredStream()
/*     */     throws IOException
/*     */   {
/* 191 */     Vector inputStreams = new Vector();
/* 192 */     byte[] inbetweenStreamBytes = "\n".getBytes("ISO-8859-1");
/*     */ 
/* 194 */     for (int i = 0; i < this.streams.size(); i++)
/*     */     {
/* 196 */       COSStream stream = (COSStream)this.streams.getObject(i);
/* 197 */       inputStreams.add(stream.getUnfilteredStream());
/*     */ 
/* 202 */       inputStreams.add(new ByteArrayInputStream(inbetweenStreamBytes));
/*     */     }
/*     */ 
/* 205 */     return new SequenceInputStream(inputStreams.elements());
/*     */   }
/*     */ 
/*     */   public Object accept(ICOSVisitor visitor)
/*     */     throws COSVisitorException
/*     */   {
/* 217 */     return this.streams.accept(visitor);
/*     */   }
/*     */ 
/*     */   public COSBase getFilters()
/*     */   {
/* 232 */     return this.firstStream.getFilters();
/*     */   }
/*     */ 
/*     */   public OutputStream createFilteredStream()
/*     */     throws IOException
/*     */   {
/* 246 */     return this.firstStream.createFilteredStream();
/*     */   }
/*     */ 
/*     */   public OutputStream createFilteredStream(COSBase expectedLength)
/*     */     throws IOException
/*     */   {
/* 262 */     return this.firstStream.createFilteredStream(expectedLength);
/*     */   }
/*     */ 
/*     */   public void setFilters(COSBase filters)
/*     */     throws IOException
/*     */   {
/* 276 */     this.firstStream.setFilters(filters);
/*     */   }
/*     */ 
/*     */   public OutputStream createUnfilteredStream()
/*     */     throws IOException
/*     */   {
/* 288 */     return this.firstStream.createUnfilteredStream();
/*     */   }
/*     */ 
/*     */   public void appendStream(COSStream streamToAppend)
/*     */   {
/* 298 */     this.streams.add(streamToAppend);
/*     */   }
/*     */ 
/*     */   public void insertCOSStream(PDStream streamToBeInserted)
/*     */   {
/* 307 */     COSArray tmp = new COSArray();
/* 308 */     tmp.add(streamToBeInserted);
/* 309 */     tmp.addAll(this.streams);
/* 310 */     this.streams.clear();
/* 311 */     this.streams = tmp;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.COSStreamArray
 * JD-Core Version:    0.6.2
 */