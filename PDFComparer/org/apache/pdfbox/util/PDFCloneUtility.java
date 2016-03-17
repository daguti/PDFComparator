/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.common.COSStreamArray;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ 
/*     */ public class PDFCloneUtility
/*     */ {
/*     */   private PDDocument destination;
/*  44 */   private Map<Object, COSBase> clonedVersion = new HashMap();
/*     */ 
/*     */   public PDFCloneUtility(PDDocument dest)
/*     */   {
/*  52 */     this.destination = dest;
/*     */   }
/*     */ 
/*     */   public PDDocument getDestination()
/*     */   {
/*  61 */     return this.destination;
/*     */   }
/*     */ 
/*     */   public COSBase cloneForNewDocument(Object base)
/*     */     throws IOException
/*     */   {
/*  73 */     if (base == null)
/*     */     {
/*  75 */       return null;
/*     */     }
/*  77 */     COSBase retval = (COSBase)this.clonedVersion.get(base);
/*  78 */     if (retval == null)
/*     */     {
/*  82 */       if ((base instanceof List))
/*     */       {
/*  84 */         COSArray array = new COSArray();
/*  85 */         List list = (List)base;
/*  86 */         for (int i = 0; i < list.size(); i++)
/*     */         {
/*  88 */           array.add(cloneForNewDocument(list.get(i)));
/*     */         }
/*  90 */         retval = array;
/*     */       }
/*  92 */       else if (((base instanceof COSObjectable)) && (!(base instanceof COSBase)))
/*     */       {
/*  94 */         retval = cloneForNewDocument(((COSObjectable)base).getCOSObject());
/*  95 */         this.clonedVersion.put(base, retval);
/*     */       }
/*  97 */       else if ((base instanceof COSObject))
/*     */       {
/*  99 */         COSObject object = (COSObject)base;
/* 100 */         retval = cloneForNewDocument(object.getObject());
/* 101 */         this.clonedVersion.put(base, retval);
/*     */       }
/* 103 */       else if ((base instanceof COSArray))
/*     */       {
/* 105 */         COSArray newArray = new COSArray();
/* 106 */         COSArray array = (COSArray)base;
/* 107 */         for (int i = 0; i < array.size(); i++)
/*     */         {
/* 109 */           newArray.add(cloneForNewDocument(array.get(i)));
/*     */         }
/* 111 */         retval = newArray;
/* 112 */         this.clonedVersion.put(base, retval);
/*     */       }
/* 114 */       else if ((base instanceof COSStreamArray))
/*     */       {
/* 116 */         COSStreamArray originalStream = (COSStreamArray)base;
/*     */ 
/* 118 */         if (originalStream.size() > 0)
/*     */         {
/* 120 */           throw new IllegalStateException("Cannot close stream array with items next to the streams.");
/*     */         }
/*     */ 
/* 123 */         COSArray array = new COSArray();
/* 124 */         for (int i = 0; i < originalStream.getStreamCount(); i++)
/*     */         {
/* 126 */           COSBase base2 = originalStream.get(i);
/* 127 */           COSBase cloneForNewDocument = cloneForNewDocument(base2);
/* 128 */           array.add(cloneForNewDocument);
/*     */         }
/* 130 */         retval = new COSStreamArray(array);
/* 131 */         this.clonedVersion.put(base, retval);
/*     */       }
/* 133 */       else if ((base instanceof COSStream))
/*     */       {
/* 135 */         COSStream originalStream = (COSStream)base;
/* 136 */         PDStream stream = new PDStream(this.destination, originalStream.getFilteredStream(), true);
/* 137 */         this.clonedVersion.put(base, stream.getStream());
/* 138 */         for (Map.Entry entry : originalStream.entrySet())
/*     */         {
/* 140 */           stream.getStream().setItem((COSName)entry.getKey(), cloneForNewDocument(entry.getValue()));
/*     */         }
/*     */ 
/* 144 */         retval = stream.getStream();
/*     */       }
/* 146 */       else if ((base instanceof COSDictionary))
/*     */       {
/* 148 */         COSDictionary dic = (COSDictionary)base;
/* 149 */         retval = new COSDictionary();
/* 150 */         this.clonedVersion.put(base, retval);
/* 151 */         for (Map.Entry entry : dic.entrySet())
/*     */         {
/* 153 */           ((COSDictionary)retval).setItem((COSName)entry.getKey(), cloneForNewDocument(entry.getValue()));
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 160 */         retval = (COSBase)base;
/*     */       }
/*     */     }
/* 162 */     this.clonedVersion.put(base, retval);
/* 163 */     return retval;
/*     */   }
/*     */ 
/*     */   public void cloneMerge(COSObjectable base, COSObjectable target)
/*     */     throws IOException
/*     */   {
/* 177 */     if (base == null)
/*     */     {
/* 179 */       return;
/*     */     }
/* 181 */     COSBase retval = (COSBase)this.clonedVersion.get(base);
/* 182 */     if (retval != null)
/*     */     {
/* 184 */       return;
/*     */     }
/*     */ 
/* 187 */     if ((base instanceof List))
/*     */     {
/* 189 */       COSArray array = new COSArray();
/* 190 */       List list = (List)base;
/* 191 */       for (int i = 0; i < list.size(); i++)
/*     */       {
/* 193 */         array.add(cloneForNewDocument(list.get(i)));
/*     */       }
/* 195 */       ((List)target).add(array);
/*     */     }
/* 197 */     else if (((base instanceof COSObjectable)) && (!(base instanceof COSBase)))
/*     */     {
/* 199 */       cloneMerge(base.getCOSObject(), target.getCOSObject());
/* 200 */       this.clonedVersion.put(base, retval);
/*     */     }
/* 202 */     else if ((base instanceof COSObject))
/*     */     {
/* 204 */       if ((target instanceof COSObject))
/*     */       {
/* 206 */         cloneMerge(((COSObject)base).getObject(), ((COSObject)target).getObject());
/*     */       }
/* 208 */       else if ((target instanceof COSDictionary))
/*     */       {
/* 210 */         cloneMerge(((COSObject)base).getObject(), (COSDictionary)target);
/*     */       }
/* 212 */       this.clonedVersion.put(base, retval);
/*     */     }
/* 214 */     else if ((base instanceof COSArray))
/*     */     {
/* 216 */       COSArray array = (COSArray)base;
/* 217 */       for (int i = 0; i < array.size(); i++)
/*     */       {
/* 219 */         ((COSArray)target).add(cloneForNewDocument(array.get(i)));
/*     */       }
/* 221 */       this.clonedVersion.put(base, retval);
/*     */     }
/* 223 */     else if ((base instanceof COSStream))
/*     */     {
/* 226 */       COSStream originalStream = (COSStream)base;
/* 227 */       PDStream stream = new PDStream(this.destination, originalStream.getFilteredStream(), true);
/* 228 */       this.clonedVersion.put(base, stream.getStream());
/* 229 */       for (Map.Entry entry : originalStream.entrySet())
/*     */       {
/* 231 */         stream.getStream().setItem((COSName)entry.getKey(), cloneForNewDocument(entry.getValue()));
/*     */       }
/*     */ 
/* 235 */       retval = stream.getStream();
/* 236 */       target = retval;
/*     */     }
/* 238 */     else if ((base instanceof COSDictionary))
/*     */     {
/* 240 */       COSDictionary dic = (COSDictionary)base;
/* 241 */       this.clonedVersion.put(base, retval);
/* 242 */       for (Map.Entry entry : dic.entrySet())
/*     */       {
/* 244 */         COSName key = (COSName)entry.getKey();
/* 245 */         COSBase value = (COSBase)entry.getValue();
/* 246 */         if (((COSDictionary)target).getItem(key) != null)
/*     */         {
/* 248 */           cloneMerge(value, ((COSDictionary)target).getItem(key));
/*     */         }
/*     */         else
/*     */         {
/* 252 */           ((COSDictionary)target).setItem(key, cloneForNewDocument(value));
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 258 */       retval = (COSBase)base;
/*     */     }
/* 260 */     this.clonedVersion.put(base, retval);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.PDFCloneUtility
 * JD-Core Version:    0.6.2
 */