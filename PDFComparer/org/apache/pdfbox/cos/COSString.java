/*     */ package org.apache.pdfbox.cos;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.encoding.PDFDocEncodingCharset;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ 
/*     */ public class COSString extends COSBase
/*     */ {
/*  43 */   private static final Log LOG = LogFactory.getLog(COSString.class);
/*     */ 
/*  48 */   public static final byte[] STRING_OPEN = { 40 };
/*     */ 
/*  52 */   public static final byte[] STRING_CLOSE = { 41 };
/*     */ 
/*  56 */   public static final byte[] HEX_STRING_OPEN = { 60 };
/*     */ 
/*  60 */   public static final byte[] HEX_STRING_CLOSE = { 62 };
/*     */ 
/*  64 */   public static final byte[] ESCAPE = { 92 };
/*     */ 
/*  69 */   public static final byte[] CR_ESCAPE = { 92, 114 };
/*     */ 
/*  73 */   public static final byte[] LF_ESCAPE = { 92, 110 };
/*     */ 
/*  77 */   public static final byte[] HT_ESCAPE = { 92, 116 };
/*     */ 
/*  81 */   public static final byte[] BS_ESCAPE = { 92, 98 };
/*     */ 
/*  85 */   public static final byte[] FF_ESCAPE = { 92, 102 };
/*     */ 
/*  87 */   private ByteArrayOutputStream out = null;
/*  88 */   private String str = null;
/*     */ 
/*  93 */   private boolean forceHexForm = false;
/*     */ 
/*     */   public COSString()
/*     */   {
/* 100 */     this.out = new ByteArrayOutputStream();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public COSString(boolean isDictionaryValue)
/*     */   {
/* 112 */     this();
/*     */   }
/*     */ 
/*     */   public COSString(String value)
/*     */   {
/*     */     try
/*     */     {
/* 125 */       boolean unicode16 = false;
/* 126 */       char[] chars = value.toCharArray();
/* 127 */       int length = chars.length;
/* 128 */       for (int i = 0; i < length; i++)
/*     */       {
/* 130 */         if (chars[i] > 'ÿ')
/*     */         {
/* 132 */           unicode16 = true;
/* 133 */           break;
/*     */         }
/*     */       }
/* 136 */       if (unicode16)
/*     */       {
/* 138 */         byte[] data = value.getBytes("UTF-16BE");
/* 139 */         this.out = new ByteArrayOutputStream(data.length + 2);
/* 140 */         this.out.write(254);
/* 141 */         this.out.write(255);
/* 142 */         this.out.write(data);
/*     */       }
/*     */       else
/*     */       {
/* 146 */         byte[] data = value.getBytes("ISO-8859-1");
/* 147 */         this.out = new ByteArrayOutputStream(data.length);
/* 148 */         this.out.write(data);
/*     */       }
/*     */     }
/*     */     catch (IOException ignore)
/*     */     {
/* 153 */       LOG.error(ignore, ignore);
/*     */     }
/*     */   }
/*     */ 
/*     */   public COSString(byte[] value)
/*     */   {
/*     */     try
/*     */     {
/* 168 */       this.out = new ByteArrayOutputStream(value.length);
/* 169 */       this.out.write(value);
/*     */     }
/*     */     catch (IOException ignore)
/*     */     {
/* 173 */       LOG.error(ignore, ignore);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setForceLiteralForm(boolean v)
/*     */   {
/* 188 */     this.forceHexForm = (!v);
/*     */   }
/*     */ 
/*     */   public void setForceHexForm(boolean v)
/*     */   {
/* 201 */     this.forceHexForm = v;
/*     */   }
/*     */ 
/*     */   public static COSString createFromHexString(String hex)
/*     */     throws IOException
/*     */   {
/* 215 */     return createFromHexString(hex, false);
/*     */   }
/*     */ 
/*     */   public static COSString createFromHexString(String hex, boolean force)
/*     */     throws IOException
/*     */   {
/* 231 */     COSString retval = new COSString();
/* 232 */     StringBuilder hexBuffer = new StringBuilder(hex.trim());
/*     */ 
/* 234 */     if (hexBuffer.length() % 2 != 0)
/*     */     {
/* 236 */       hexBuffer.append('0');
/*     */     }
/* 238 */     int length = hexBuffer.length();
/* 239 */     for (int i = 0; i < length; i += 2)
/*     */     {
/*     */       try
/*     */       {
/* 243 */         retval.append(Integer.parseInt(hexBuffer.substring(i, i + 2), 16));
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 247 */         if (force)
/*     */         {
/* 249 */           retval.append(63);
/*     */         }
/*     */         else
/*     */         {
/* 253 */           IOException exception = new IOException("Invalid hex string: " + hex);
/* 254 */           exception.initCause(e);
/* 255 */           throw exception;
/*     */         }
/*     */       }
/*     */     }
/* 259 */     return retval;
/*     */   }
/*     */ 
/*     */   public String getHexString()
/*     */   {
/* 269 */     StringBuilder retval = new StringBuilder(this.out.size() * 2);
/* 270 */     byte[] data = getBytes();
/* 271 */     int length = data.length;
/* 272 */     for (int i = 0; i < length; i++)
/*     */     {
/* 274 */       retval.append(org.apache.pdfbox.persistence.util.COSHEXTable.HEX_TABLE[((data[i] + 256) % 256)]);
/*     */     }
/*     */ 
/* 277 */     return retval.toString();
/*     */   }
/*     */ 
/*     */   public String getString()
/*     */   {
/* 287 */     if (this.str != null)
/*     */     {
/* 289 */       return this.str;
/*     */     }
/*     */ 
/* 292 */     Charset charset = PDFDocEncodingCharset.INSTANCE;
/* 293 */     byte[] data = getBytes();
/* 294 */     int start = 0;
/* 295 */     if (data.length > 2)
/*     */     {
/* 297 */       if ((data[0] == -1) && (data[1] == -2))
/*     */       {
/* 299 */         charset = Charset.forName("UTF-16LE");
/* 300 */         start = 2;
/*     */       }
/* 302 */       else if ((data[0] == -2) && (data[1] == -1))
/*     */       {
/* 304 */         charset = Charset.forName("UTF-16BE");
/* 305 */         start = 2;
/*     */       }
/*     */     }
/*     */ 
/* 309 */     String retval = toString(data, start, data.length - start, charset);
/* 310 */     this.str = retval;
/* 311 */     return retval;
/*     */   }
/*     */ 
/*     */   private static String toString(byte[] data, int offset, int length, Charset charset)
/*     */   {
/* 318 */     CharBuffer charBuffer = charset.decode(ByteBuffer.wrap(data, offset, length));
/* 319 */     return charBuffer.toString();
/*     */   }
/*     */ 
/*     */   public void append(byte[] data)
/*     */     throws IOException
/*     */   {
/* 333 */     this.out.write(data);
/* 334 */     this.str = null;
/*     */   }
/*     */ 
/*     */   public void append(int in)
/*     */     throws IOException
/*     */   {
/* 348 */     this.out.write(in);
/* 349 */     this.str = null;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 357 */     this.out.reset();
/* 358 */     this.str = null;
/*     */   }
/*     */ 
/*     */   public byte[] getBytes()
/*     */   {
/* 368 */     return this.out.toByteArray();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 377 */     return "COSString{" + getString() + "}";
/*     */   }
/*     */ 
/*     */   public void writePDF(OutputStream output)
/*     */     throws IOException
/*     */   {
/* 390 */     boolean outsideASCII = false;
/*     */ 
/* 392 */     byte[] bytes = getBytes();
/* 393 */     int length = bytes.length;
/* 394 */     for (int i = 0; (i < length) && (!outsideASCII); i++)
/*     */     {
/* 398 */       outsideASCII = bytes[i] < 0;
/*     */     }
/* 400 */     if ((!outsideASCII) && (!this.forceHexForm))
/*     */     {
/* 402 */       output.write(STRING_OPEN);
/* 403 */       for (int i = 0; i < length; i++)
/*     */       {
/* 405 */         int b = (bytes[i] + 256) % 256;
/* 406 */         switch (b)
/*     */         {
/*     */         case 40:
/*     */         case 41:
/*     */         case 92:
/* 412 */           output.write(ESCAPE);
/* 413 */           output.write((byte)b);
/* 414 */           break;
/*     */         case 10:
/* 418 */           output.write(LF_ESCAPE);
/* 419 */           break;
/*     */         case 13:
/* 423 */           output.write(CR_ESCAPE);
/* 424 */           break;
/*     */         case 9:
/* 428 */           output.write(HT_ESCAPE);
/* 429 */           break;
/*     */         case 8:
/* 433 */           output.write(BS_ESCAPE);
/* 434 */           break;
/*     */         case 12:
/* 438 */           output.write(FF_ESCAPE);
/* 439 */           break;
/*     */         default:
/* 443 */           output.write((byte)b);
/*     */         }
/*     */       }
/*     */ 
/* 447 */       output.write(STRING_CLOSE);
/*     */     }
/*     */     else
/*     */     {
/* 451 */       output.write(HEX_STRING_OPEN);
/* 452 */       for (int i = 0; i < length; i++)
/*     */       {
/* 454 */         output.write(org.apache.pdfbox.persistence.util.COSHEXTable.TABLE[((bytes[i] + 256) % 256)]);
/*     */       }
/* 456 */       output.write(HEX_STRING_CLOSE);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object accept(ICOSVisitor visitor)
/*     */     throws COSVisitorException
/*     */   {
/* 472 */     return visitor.visitFromString(this);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 481 */     if ((obj instanceof COSString))
/*     */     {
/* 483 */       COSString strObj = (COSString)obj;
/* 484 */       return (getString().equals(strObj.getString())) && (this.forceHexForm == strObj.forceHexForm);
/*     */     }
/* 486 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 495 */     int result = getString().hashCode();
/* 496 */     return result += (this.forceHexForm ? 17 : 0);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSString
 * JD-Core Version:    0.6.2
 */