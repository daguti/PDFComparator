/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentInformation;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
/*     */ 
/*     */ public class PDFText2HTML extends PDFTextStripper
/*     */ {
/*     */   private static final int INITIAL_PDF_TO_HTML_BYTES = 8192;
/*  40 */   private boolean onFirstPage = true;
/*  41 */   private FontState fontState = new FontState(null);
/*     */ 
/*     */   public PDFText2HTML(String encoding)
/*     */     throws IOException
/*     */   {
/*  50 */     super(encoding);
/*  51 */     setLineSeparator(this.systemLineSeparator);
/*  52 */     setParagraphStart("<p>");
/*  53 */     setParagraphEnd("</p>" + this.systemLineSeparator);
/*  54 */     setPageStart("<div style=\"page-break-before:always; page-break-after:always\">");
/*  55 */     setPageEnd("</div>" + this.systemLineSeparator);
/*  56 */     setArticleStart(this.systemLineSeparator);
/*  57 */     setArticleEnd(this.systemLineSeparator);
/*     */   }
/*     */ 
/*     */   protected void writeHeader()
/*     */     throws IOException
/*     */   {
/*  69 */     StringBuffer buf = new StringBuffer(8192);
/*  70 */     buf.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n\"http://www.w3.org/TR/html4/loose.dtd\">\n");
/*     */ 
/*  72 */     buf.append("<html><head>");
/*  73 */     buf.append("<title>" + escape(getTitle()) + "</title>\n");
/*  74 */     if (this.outputEncoding != null)
/*     */     {
/*  76 */       buf.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + this.outputEncoding + "\">\n");
/*     */     }
/*     */ 
/*  79 */     buf.append("</head>\n");
/*  80 */     buf.append("<body>\n");
/*  81 */     super.writeString(buf.toString());
/*     */   }
/*     */ 
/*     */   protected void writePage()
/*     */     throws IOException
/*     */   {
/*  89 */     if (this.onFirstPage)
/*     */     {
/*  91 */       writeHeader();
/*  92 */       this.onFirstPage = false;
/*     */     }
/*  94 */     super.writePage();
/*     */   }
/*     */ 
/*     */   public void endDocument(PDDocument pdf)
/*     */     throws IOException
/*     */   {
/* 102 */     super.writeString("</body></html>");
/*     */   }
/*     */ 
/*     */   protected String getTitle()
/*     */   {
/* 113 */     String titleGuess = this.document.getDocumentInformation().getTitle();
/* 114 */     if ((titleGuess != null) && (titleGuess.length() > 0))
/*     */     {
/* 116 */       return titleGuess;
/*     */     }
/*     */ 
/* 120 */     Iterator textIter = getCharactersByArticle().iterator();
/* 121 */     float lastFontSize = -1.0F;
/*     */ 
/* 123 */     StringBuffer titleText = new StringBuffer();
/* 124 */     while (textIter.hasNext())
/*     */     {
/* 126 */       Iterator textByArticle = ((List)textIter.next()).iterator();
/* 127 */       while (textByArticle.hasNext())
/*     */       {
/* 129 */         TextPosition position = (TextPosition)textByArticle.next();
/*     */ 
/* 131 */         float currentFontSize = position.getFontSize();
/*     */ 
/* 134 */         if ((currentFontSize != lastFontSize) || (titleText.length() > 64))
/*     */         {
/* 136 */           if (titleText.length() > 0)
/*     */           {
/* 138 */             return titleText.toString();
/*     */           }
/* 140 */           lastFontSize = currentFontSize;
/*     */         }
/* 142 */         if (currentFontSize > 13.0F)
/*     */         {
/* 144 */           titleText.append(position.getCharacter());
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 149 */     return "";
/*     */   }
/*     */ 
/*     */   protected void startArticle(boolean isltr)
/*     */     throws IOException
/*     */   {
/* 163 */     if (isltr)
/*     */     {
/* 165 */       super.writeString("<div>");
/*     */     }
/*     */     else
/*     */     {
/* 169 */       super.writeString("<div dir=\"RTL\">");
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void endArticle()
/*     */     throws IOException
/*     */   {
/* 181 */     super.endArticle();
/* 182 */     super.writeString("</div>");
/*     */   }
/*     */ 
/*     */   protected void writeString(String text, List<TextPosition> textPositions)
/*     */     throws IOException
/*     */   {
/* 195 */     super.writeString(this.fontState.push(text, textPositions));
/*     */   }
/*     */ 
/*     */   protected void writeString(String chars)
/*     */     throws IOException
/*     */   {
/* 207 */     super.writeString(escape(chars));
/*     */   }
/*     */ 
/*     */   protected void writeParagraphEnd()
/*     */     throws IOException
/*     */   {
/* 218 */     super.writeString(this.fontState.clear());
/* 219 */     super.writeParagraphEnd();
/*     */   }
/*     */ 
/*     */   private static String escape(String chars)
/*     */   {
/* 230 */     StringBuilder builder = new StringBuilder(chars.length());
/* 231 */     for (int i = 0; i < chars.length(); i++)
/*     */     {
/* 233 */       appendEscaped(builder, chars.charAt(i));
/*     */     }
/* 235 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   private static void appendEscaped(StringBuilder builder, char character)
/*     */   {
/* 241 */     if ((character < ' ') || (character > '~'))
/*     */     {
/* 243 */       int charAsInt = character;
/* 244 */       builder.append("&#").append(charAsInt).append(";");
/*     */     }
/*     */     else
/*     */     {
/* 248 */       switch (character)
/*     */       {
/*     */       case '"':
/* 251 */         builder.append("&quot;");
/* 252 */         break;
/*     */       case '&':
/* 254 */         builder.append("&amp;");
/* 255 */         break;
/*     */       case '<':
/* 257 */         builder.append("&lt;");
/* 258 */         break;
/*     */       case '>':
/* 260 */         builder.append("&gt;");
/* 261 */         break;
/*     */       default:
/* 263 */         builder.append(String.valueOf(character));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class FontState
/*     */   {
/* 276 */     protected List<String> stateList = new ArrayList();
/* 277 */     protected Set<String> stateSet = new HashSet();
/*     */ 
/*     */     public String push(String text, List<TextPosition> textPositions)
/*     */     {
/* 289 */       StringBuilder buffer = new StringBuilder();
/*     */ 
/* 291 */       if (text.length() == textPositions.size())
/*     */       {
/* 294 */         for (int i = 0; i < text.length(); i++)
/*     */         {
/* 296 */           push(buffer, text.charAt(i), (TextPosition)textPositions.get(i));
/*     */         }
/*     */       }
/* 299 */       else if (text.length() > 0)
/*     */       {
/* 304 */         if (textPositions.isEmpty())
/*     */         {
/* 306 */           return text;
/*     */         }
/* 308 */         push(buffer, text.charAt(0), (TextPosition)textPositions.get(0));
/* 309 */         buffer.append(PDFText2HTML.escape(text.substring(1)));
/*     */       }
/* 311 */       return buffer.toString();
/*     */     }
/*     */ 
/*     */     public String clear()
/*     */     {
/* 320 */       StringBuilder buffer = new StringBuilder();
/* 321 */       closeUntil(buffer, null);
/* 322 */       this.stateList.clear();
/* 323 */       this.stateSet.clear();
/* 324 */       return buffer.toString();
/*     */     }
/*     */ 
/*     */     protected String push(StringBuilder buffer, char character, TextPosition textPosition)
/*     */     {
/* 329 */       boolean bold = false;
/* 330 */       boolean italics = false;
/*     */ 
/* 332 */       PDFontDescriptor descriptor = textPosition.getFont().getFontDescriptor();
/* 333 */       if (descriptor != null)
/*     */       {
/* 335 */         bold = isBold(descriptor);
/* 336 */         italics = isItalic(descriptor);
/*     */       }
/*     */ 
/* 339 */       buffer.append(bold ? open("b") : close("b"));
/* 340 */       buffer.append(italics ? open("i") : close("i"));
/* 341 */       PDFText2HTML.appendEscaped(buffer, character);
/*     */ 
/* 343 */       return buffer.toString();
/*     */     }
/*     */ 
/*     */     private String open(String tag)
/*     */     {
/* 348 */       if (this.stateSet.contains(tag))
/*     */       {
/* 350 */         return "";
/*     */       }
/* 352 */       this.stateList.add(tag);
/* 353 */       this.stateSet.add(tag);
/*     */ 
/* 355 */       return openTag(tag);
/*     */     }
/*     */ 
/*     */     private String close(String tag)
/*     */     {
/* 360 */       if (!this.stateSet.contains(tag))
/*     */       {
/* 362 */         return "";
/*     */       }
/*     */ 
/* 365 */       StringBuilder tagsBuilder = new StringBuilder();
/* 366 */       int index = closeUntil(tagsBuilder, tag);
/*     */ 
/* 369 */       this.stateList.remove(index);
/* 370 */       this.stateSet.remove(tag);
/*     */ 
/* 373 */       for (; index < this.stateList.size(); index++)
/*     */       {
/* 375 */         tagsBuilder.append(openTag((String)this.stateList.get(index)));
/*     */       }
/* 377 */       return tagsBuilder.toString();
/*     */     }
/*     */ 
/*     */     private int closeUntil(StringBuilder tagsBuilder, String endTag)
/*     */     {
/* 382 */       for (int i = this.stateList.size(); i-- > 0; )
/*     */       {
/* 384 */         String tag = (String)this.stateList.get(i);
/* 385 */         tagsBuilder.append(closeTag(tag));
/* 386 */         if ((endTag != null) && (tag.equals(endTag)))
/*     */         {
/* 388 */           return i;
/*     */         }
/*     */       }
/* 391 */       return -1;
/*     */     }
/*     */ 
/*     */     private String openTag(String tag)
/*     */     {
/* 396 */       return "<" + tag + ">";
/*     */     }
/*     */ 
/*     */     private String closeTag(String tag)
/*     */     {
/* 401 */       return "</" + tag + ">";
/*     */     }
/*     */ 
/*     */     private boolean isBold(PDFontDescriptor descriptor)
/*     */     {
/* 406 */       if (descriptor.isForceBold())
/*     */       {
/* 408 */         return true;
/*     */       }
/* 410 */       return descriptor.getFontName().contains("Bold");
/*     */     }
/*     */ 
/*     */     private boolean isItalic(PDFontDescriptor descriptor)
/*     */     {
/* 415 */       if (descriptor.isItalic())
/*     */       {
/* 417 */         return true;
/*     */       }
/* 419 */       return descriptor.getFontName().contains("Italic");
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.PDFText2HTML
 * JD-Core Version:    0.6.2
 */