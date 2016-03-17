/*    */ package org.apache.pdfbox.pdfparser;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import org.apache.pdfbox.cos.COSArray;
/*    */ import org.apache.pdfbox.cos.COSDocument;
/*    */ import org.apache.pdfbox.cos.COSInteger;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.cos.COSStream;
/*    */ import org.apache.pdfbox.io.PushBackInputStream;
/*    */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*    */ 
/*    */ public class PDFXrefStreamParser extends BaseParser
/*    */ {
/*    */   private COSStream stream;
/*    */   private XrefTrailerResolver xrefTrailerResolver;
/*    */ 
/*    */   public PDFXrefStreamParser(COSStream strm, COSDocument doc, boolean forceParsing, XrefTrailerResolver resolver)
/*    */     throws IOException
/*    */   {
/* 61 */     super(strm.getUnfilteredStream(), forceParsing);
/* 62 */     setDocument(doc);
/* 63 */     this.stream = strm;
/* 64 */     this.xrefTrailerResolver = resolver;
/*    */   }
/*    */ 
/*    */   public void parse()
/*    */     throws IOException
/*    */   {
/*    */     try
/*    */     {
/* 75 */       COSArray xrefFormat = (COSArray)this.stream.getDictionaryObject(COSName.W);
/* 76 */       COSArray indexArray = (COSArray)this.stream.getDictionaryObject(COSName.INDEX);
/*    */ 
/* 80 */       if (indexArray == null)
/*    */       {
/* 82 */         indexArray = new COSArray();
/* 83 */         indexArray.add(COSInteger.ZERO);
/* 84 */         indexArray.add(this.stream.getDictionaryObject(COSName.SIZE));
/*    */       }
/*    */ 
/* 87 */       ArrayList objNums = new ArrayList();
/*    */ 
/* 92 */       Iterator indexIter = indexArray.iterator();
/* 93 */       while (indexIter.hasNext())
/*    */       {
/* 95 */         int objID = ((COSInteger)indexIter.next()).intValue();
/* 96 */         int size = ((COSInteger)indexIter.next()).intValue();
/* 97 */         for (int i = 0; i < size; i++)
/*    */         {
/* 99 */           objNums.add(new Integer(objID + i));
/*    */         }
/*    */       }
/* 102 */       Iterator objIter = objNums.iterator();
/*    */ 
/* 106 */       int w0 = xrefFormat.getInt(0);
/* 107 */       int w1 = xrefFormat.getInt(1);
/* 108 */       int w2 = xrefFormat.getInt(2);
/* 109 */       int lineSize = w0 + w1 + w2;
/*    */ 
/* 111 */       while ((this.pdfSource.available() > 0) && (objIter.hasNext()))
/*    */       {
/* 113 */         byte[] currLine = new byte[lineSize];
/* 114 */         this.pdfSource.read(currLine);
/*    */ 
/* 116 */         int type = 0;
/*    */ 
/* 121 */         for (int i = 0; i < w0; i++)
/*    */         {
/* 123 */           type += ((currLine[i] & 0xFF) << (w0 - i - 1) * 8);
/*    */         }
/*    */ 
/* 126 */         Integer objID = (Integer)objIter.next();
/*    */         COSObjectKey objKey;
/* 130 */         switch (type)
/*    */         {
/*    */         case 0:
/* 136 */           break;
/*    */         case 1:
/* 138 */           int offset = 0;
/* 139 */           for (int i = 0; i < w1; i++)
/*    */           {
/* 141 */             offset += ((currLine[(i + w0)] & 0xFF) << (w1 - i - 1) * 8);
/*    */           }
/* 143 */           int genNum = 0;
/* 144 */           for (int i = 0; i < w2; i++)
/*    */           {
/* 146 */             genNum += ((currLine[(i + w0 + w1)] & 0xFF) << (w2 - i - 1) * 8);
/*    */           }
/* 148 */           objKey = new COSObjectKey(objID.intValue(), genNum);
/* 149 */           this.xrefTrailerResolver.setXRef(objKey, offset);
/* 150 */           break;
/*    */         case 2:
/* 166 */           int objstmObjNr = 0;
/* 167 */           for (int i = 0; i < w1; i++)
/*    */           {
/* 169 */             objstmObjNr += ((currLine[(i + w0)] & 0xFF) << (w1 - i - 1) * 8);
/*    */           }
/* 171 */           objKey = new COSObjectKey(objID.intValue(), 0L);
/* 172 */           this.xrefTrailerResolver.setXRef(objKey, -objstmObjNr);
/*    */         }
/*    */ 
/*    */       }
/*    */ 
/*    */     }
/*    */     finally
/*    */     {
/* 181 */       this.pdfSource.close();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfparser.PDFXrefStreamParser
 * JD-Core Version:    0.6.2
 */