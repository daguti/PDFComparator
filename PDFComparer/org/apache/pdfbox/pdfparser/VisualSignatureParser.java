/*     */ package org.apache.pdfbox.pdfparser;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.io.PushBackInputStream;
/*     */ import org.apache.pdfbox.pdfwriter.COSWriter;
/*     */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*     */ 
/*     */ public class VisualSignatureParser extends BaseParser
/*     */ {
/*  38 */   private static final Log LOG = LogFactory.getLog(PDFParser.class);
/*     */ 
/*     */   public VisualSignatureParser(InputStream input)
/*     */     throws IOException
/*     */   {
/*  49 */     super(input);
/*     */   }
/*     */ 
/*     */   public void parse()
/*     */     throws IOException
/*     */   {
/*  59 */     this.document = new COSDocument();
/*  60 */     skipToNextObj();
/*     */ 
/*  62 */     boolean wasLastParsedObjectEOF = false;
/*     */     try
/*     */     {
/*  65 */       while (!wasLastParsedObjectEOF)
/*     */       {
/*  67 */         if (this.pdfSource.isEOF())
/*     */         {
/*     */           break;
/*     */         }
/*     */         try
/*     */         {
/*  73 */           wasLastParsedObjectEOF = parseObject();
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/*  81 */           LOG.warn("Parsing Error, Skipping Object", e);
/*  82 */           skipToNextObj();
/*     */         }
/*  84 */         skipSpaces();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  93 */       if (!wasLastParsedObjectEOF)
/*     */       {
/*  95 */         throw e;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void skipToNextObj() throws IOException
/*     */   {
/* 102 */     byte[] b = new byte[16];
/* 103 */     Pattern p = Pattern.compile("\\d+\\s+\\d+\\s+obj.*", 32);
/*     */ 
/* 109 */     while (!this.pdfSource.isEOF())
/*     */     {
/* 111 */       int l = this.pdfSource.read(b);
/* 112 */       if (l < 1)
/*     */       {
/*     */         break;
/*     */       }
/* 116 */       String s = new String(b, "US-ASCII");
/* 117 */       if ((s.startsWith("trailer")) || (s.startsWith("xref")) || (s.startsWith("startxref")) || (s.startsWith("stream")) || (p.matcher(s).matches()))
/*     */       {
/* 123 */         this.pdfSource.unread(b);
/* 124 */         break;
/*     */       }
/*     */ 
/* 128 */       this.pdfSource.unread(b, 1, l - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean parseObject()
/*     */     throws IOException
/*     */   {
/* 135 */     boolean isEndOfFile = false;
/* 136 */     skipSpaces();
/*     */ 
/* 138 */     char peekedChar = (char)this.pdfSource.peek();
/*     */ 
/* 141 */     while (peekedChar == 'e')
/*     */     {
/* 145 */       readString();
/* 146 */       skipSpaces();
/* 147 */       peekedChar = (char)this.pdfSource.peek();
/*     */     }
/* 149 */     if (!this.pdfSource.isEOF())
/*     */     {
/* 153 */       if (peekedChar == 'x')
/*     */       {
/* 156 */         return true;
/*     */       }
/* 158 */       if ((peekedChar == 't') || (peekedChar == 's'))
/*     */       {
/* 161 */         if (peekedChar == 't')
/*     */         {
/* 163 */           return true;
/*     */         }
/* 165 */         if (peekedChar == 's')
/*     */         {
/* 167 */           skipToNextObj();
/*     */ 
/* 169 */           String eof = readExpectedString("%%EOF");
/* 170 */           if ((eof.indexOf("%%EOF") == -1) && (!this.pdfSource.isEOF()))
/*     */           {
/* 172 */             throw new IOException("expected='%%EOF' actual='" + eof + "' next=" + readString() + " next=" + readString());
/*     */           }
/*     */ 
/* 175 */           isEndOfFile = true;
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 181 */         long number = -1L;
/* 182 */         int genNum = -1;
/* 183 */         String objectKey = null;
/* 184 */         boolean missingObjectNumber = false;
/*     */         try
/*     */         {
/* 187 */           char peeked = (char)this.pdfSource.peek();
/* 188 */           if (peeked == '<')
/*     */           {
/* 190 */             missingObjectNumber = true;
/*     */           }
/*     */           else
/*     */           {
/* 194 */             number = readObjectNumber();
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 203 */           number = readObjectNumber();
/*     */         }
/* 205 */         if (!missingObjectNumber)
/*     */         {
/* 207 */           skipSpaces();
/* 208 */           genNum = readGenerationNumber();
/*     */ 
/* 210 */           objectKey = readString(3);
/*     */ 
/* 213 */           if (!objectKey.equals("obj"))
/*     */           {
/* 215 */             throw new IOException("expected='obj' actual='" + objectKey + "' " + this.pdfSource);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 220 */           number = -1L;
/* 221 */           genNum = -1;
/*     */         }
/*     */ 
/* 224 */         skipSpaces();
/* 225 */         COSBase pb = parseDirObject();
/* 226 */         String endObjectKey = readString();
/*     */ 
/* 228 */         if (endObjectKey.equals("stream"))
/*     */         {
/* 230 */           this.pdfSource.unread(endObjectKey.getBytes());
/* 231 */           this.pdfSource.unread(32);
/* 232 */           if ((pb instanceof COSDictionary))
/*     */           {
/* 234 */             pb = parseCOSStream((COSDictionary)pb, getDocument().getScratchFile());
/*     */           }
/*     */           else
/*     */           {
/* 241 */             throw new IOException("stream not preceded by dictionary");
/*     */           }
/* 243 */           endObjectKey = readString();
/*     */         }
/*     */ 
/* 246 */         COSObjectKey key = new COSObjectKey(number, genNum);
/* 247 */         COSObject pdfObject = this.document.getObjectFromPool(key);
/* 248 */         pb.setNeedToBeUpdate(true);
/* 249 */         pdfObject.setObject(pb);
/*     */ 
/* 251 */         if (!endObjectKey.equals("endobj"))
/*     */         {
/* 253 */           if (endObjectKey.startsWith("endobj"))
/*     */           {
/* 261 */             this.pdfSource.unread(endObjectKey.substring(6).getBytes());
/*     */           }
/* 263 */           else if (!this.pdfSource.isEOF())
/*     */           {
/*     */             try
/*     */             {
/* 269 */               Float.parseFloat(endObjectKey);
/* 270 */               this.pdfSource.unread(COSWriter.SPACE);
/* 271 */               this.pdfSource.unread(endObjectKey.getBytes());
/*     */             }
/*     */             catch (NumberFormatException e)
/*     */             {
/* 277 */               String secondEndObjectKey = readString();
/* 278 */               if (!secondEndObjectKey.equals("endobj"))
/*     */               {
/* 280 */                 if (isClosing())
/*     */                 {
/* 286 */                   this.pdfSource.read();
/*     */                 }
/* 288 */                 skipSpaces();
/* 289 */                 String thirdPossibleEndObj = readString();
/* 290 */                 if (!thirdPossibleEndObj.equals("endobj"))
/*     */                 {
/* 292 */                   throw new IOException("expected='endobj' firstReadAttempt='" + endObjectKey + "' " + "secondReadAttempt='" + secondEndObjectKey + "' " + this.pdfSource);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 299 */         skipSpaces();
/*     */       }
/*     */     }
/* 301 */     return isEndOfFile;
/*     */   }
/*     */ 
/*     */   public COSDocument getDocument()
/*     */     throws IOException
/*     */   {
/* 313 */     if (this.document == null)
/*     */     {
/* 315 */       throw new IOException("You must call parse() before calling getDocument()");
/*     */     }
/* 317 */     return this.document;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfparser.VisualSignatureParser
 * JD-Core Version:    0.6.2
 */