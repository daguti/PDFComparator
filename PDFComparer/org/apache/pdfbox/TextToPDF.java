/*     */ package org.apache.pdfbox;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Reader;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
/*     */ import org.apache.pdfbox.pdmodel.font.PDSimpleFont;
/*     */ import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
/*     */ import org.apache.pdfbox.pdmodel.font.PDType1Font;
/*     */ 
/*     */ public class TextToPDF
/*     */ {
/*  42 */   private int fontSize = 10;
/*  43 */   private PDSimpleFont font = PDType1Font.HELVETICA;
/*     */ 
/*     */   public PDDocument createPDFFromText(Reader text)
/*     */     throws IOException
/*     */   {
/*  56 */     PDDocument doc = null;
/*     */     try
/*     */     {
/*  60 */       int margin = 40;
/*  61 */       float height = this.font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000.0F;
/*     */ 
/*  64 */       height = height * this.fontSize * 1.05F;
/*  65 */       doc = new PDDocument();
/*  66 */       BufferedReader data = new BufferedReader(text);
/*  67 */       String nextLine = null;
/*  68 */       PDPage page = new PDPage();
/*  69 */       PDPageContentStream contentStream = null;
/*  70 */       float y = -1.0F;
/*  71 */       float maxStringLength = page.getMediaBox().getWidth() - 80.0F;
/*     */ 
/*  74 */       boolean textIsEmpty = true;
/*     */ 
/*  76 */       while ((nextLine = data.readLine()) != null)
/*     */       {
/*  82 */         textIsEmpty = false;
/*     */ 
/*  84 */         String[] lineWords = nextLine.trim().split(" ");
/*  85 */         int lineIndex = 0;
/*  86 */         while (lineIndex < lineWords.length)
/*     */         {
/*  88 */           StringBuffer nextLineToDraw = new StringBuffer();
/*  89 */           float lengthIfUsingNextWord = 0.0F;
/*     */           do
/*     */           {
/*  92 */             nextLineToDraw.append(lineWords[lineIndex]);
/*  93 */             nextLineToDraw.append(" ");
/*  94 */             lineIndex++;
/*  95 */             if (lineIndex < lineWords.length)
/*     */             {
/*  97 */               String lineWithNextWord = nextLineToDraw.toString() + lineWords[lineIndex];
/*  98 */               lengthIfUsingNextWord = this.font.getStringWidth(lineWithNextWord) / 1000.0F * this.fontSize;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 103 */           while ((lineIndex < lineWords.length) && (lengthIfUsingNextWord < maxStringLength));
/* 104 */           if (y < 40.0F)
/*     */           {
/* 108 */             page = new PDPage();
/* 109 */             doc.addPage(page);
/* 110 */             if (contentStream != null)
/*     */             {
/* 112 */               contentStream.endText();
/* 113 */               contentStream.close();
/*     */             }
/* 115 */             contentStream = new PDPageContentStream(doc, page);
/* 116 */             contentStream.setFont(this.font, this.fontSize);
/* 117 */             contentStream.beginText();
/* 118 */             y = page.getMediaBox().getHeight() - 40.0F + height;
/* 119 */             contentStream.moveTextPositionByAmount(40.0F, y);
/*     */           }
/*     */ 
/* 125 */           if (contentStream == null)
/*     */           {
/* 127 */             throw new IOException("Error:Expected non-null content stream.");
/*     */           }
/* 129 */           contentStream.moveTextPositionByAmount(0.0F, -height);
/* 130 */           y -= height;
/* 131 */           contentStream.drawString(nextLineToDraw.toString());
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 140 */       if (textIsEmpty)
/*     */       {
/* 142 */         doc.addPage(page);
/*     */       }
/*     */ 
/* 145 */       if (contentStream != null)
/*     */       {
/* 147 */         contentStream.endText();
/* 148 */         contentStream.close();
/*     */       }
/*     */     }
/*     */     catch (IOException io)
/*     */     {
/* 153 */       if (doc != null)
/*     */       {
/* 155 */         doc.close();
/*     */       }
/* 157 */       throw io;
/*     */     }
/* 159 */     return doc;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IOException
/*     */   {
/* 173 */     TextToPDF app = new TextToPDF();
/* 174 */     PDDocument doc = null;
/*     */     try
/*     */     {
/* 177 */       if (args.length < 2)
/*     */       {
/* 179 */         app.usage();
/*     */       }
/*     */       else
/*     */       {
/* 183 */         for (int i = 0; i < args.length - 2; i++)
/*     */         {
/* 185 */           if (args[i].equals("-standardFont"))
/*     */           {
/* 187 */             i++;
/* 188 */             app.setFont(PDType1Font.getStandardFont(args[i]));
/*     */           }
/* 190 */           else if (args[i].equals("-ttf"))
/*     */           {
/* 192 */             i++;
/* 193 */             PDTrueTypeFont font = PDTrueTypeFont.loadTTF(doc, new File(args[i]));
/* 194 */             app.setFont(font);
/*     */           }
/* 196 */           else if (args[i].equals("-fontSize"))
/*     */           {
/* 198 */             i++;
/* 199 */             app.setFontSize(Integer.parseInt(args[i]));
/*     */           }
/*     */           else
/*     */           {
/* 203 */             throw new IOException("Unknown argument:" + args[i]);
/*     */           }
/*     */         }
/* 206 */         doc = app.createPDFFromText(new FileReader(args[(args.length - 1)]));
/* 207 */         doc.save(args[(args.length - 2)]);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 212 */       e.printStackTrace();
/*     */     }
/*     */     finally
/*     */     {
/* 216 */       if (doc != null)
/*     */       {
/* 218 */         doc.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void usage()
/*     */   {
/* 228 */     String[] std14 = PDType1Font.getStandard14Names();
/* 229 */     System.err.println("usage: jar -jar pdfbox-app-x.y.z.jar TextToPDF [options] <output-file> <text-file>");
/* 230 */     System.err.println("    -standardFont <name>    default:" + PDType1Font.HELVETICA.getBaseFont());
/* 231 */     for (int i = 0; i < std14.length; i++)
/*     */     {
/* 233 */       System.err.println("                                    " + std14[i]);
/*     */     }
/* 235 */     System.err.println("    -ttf <ttf file>         The TTF font to use.");
/* 236 */     System.err.println("    -fontSize <fontSize>    default:10");
/*     */   }
/*     */ 
/*     */   public PDSimpleFont getFont()
/*     */   {
/* 245 */     return this.font;
/*     */   }
/*     */ 
/*     */   public void setFont(PDSimpleFont aFont)
/*     */   {
/* 252 */     this.font = aFont;
/*     */   }
/*     */ 
/*     */   public int getFontSize()
/*     */   {
/* 259 */     return this.fontSize;
/*     */   }
/*     */ 
/*     */   public void setFontSize(int aFontSize)
/*     */   {
/* 266 */     this.fontSize = aFontSize;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.TextToPDF
 * JD-Core Version:    0.6.2
 */