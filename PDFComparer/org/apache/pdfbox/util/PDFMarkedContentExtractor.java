/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Stack;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.documentinterchange.markedcontent.PDMarkedContent;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
/*     */ 
/*     */ public class PDFMarkedContentExtractor extends PDFStreamEngine
/*     */ {
/*  39 */   private boolean suppressDuplicateOverlappingText = true;
/*  40 */   private List<PDMarkedContent> markedContents = new ArrayList();
/*  41 */   private Stack<PDMarkedContent> currentMarkedContents = new Stack();
/*     */ 
/*  43 */   private Map<String, List<TextPosition>> characterListMapping = new HashMap();
/*     */   protected String outputEncoding;
/*  55 */   private TextNormalize normalize = null;
/*     */ 
/*     */   public PDFMarkedContentExtractor()
/*     */     throws IOException
/*     */   {
/*  67 */     super(ResourceLoader.loadProperties("org/apache/pdfbox/resources/PDFMarkedContentExtractor.properties", true));
/*     */ 
/*  69 */     this.outputEncoding = null;
/*  70 */     this.normalize = new TextNormalize(this.outputEncoding);
/*     */   }
/*     */ 
/*     */   public PDFMarkedContentExtractor(Properties props)
/*     */     throws IOException
/*     */   {
/*  86 */     super(props);
/*  87 */     this.outputEncoding = null;
/*  88 */     this.normalize = new TextNormalize(this.outputEncoding);
/*     */   }
/*     */ 
/*     */   public PDFMarkedContentExtractor(String encoding)
/*     */     throws IOException
/*     */   {
/* 101 */     super(ResourceLoader.loadProperties("org/apache/pdfbox/resources/PDFMarkedContentExtractor.properties", true));
/*     */ 
/* 103 */     this.outputEncoding = encoding;
/* 104 */     this.normalize = new TextNormalize(this.outputEncoding);
/*     */   }
/*     */ 
/*     */   private boolean within(float first, float second, float variance)
/*     */   {
/* 117 */     return (second > first - variance) && (second < first + variance);
/*     */   }
/*     */ 
/*     */   public void beginMarkedContentSequence(COSName tag, COSDictionary properties)
/*     */   {
/* 123 */     PDMarkedContent markedContent = PDMarkedContent.create(tag, properties);
/* 124 */     if (this.currentMarkedContents.isEmpty())
/*     */     {
/* 126 */       this.markedContents.add(markedContent);
/*     */     }
/*     */     else
/*     */     {
/* 130 */       PDMarkedContent currentMarkedContent = (PDMarkedContent)this.currentMarkedContents.peek();
/*     */ 
/* 132 */       if (currentMarkedContent != null)
/*     */       {
/* 134 */         currentMarkedContent.addMarkedContent(markedContent);
/*     */       }
/*     */     }
/* 137 */     this.currentMarkedContents.push(markedContent);
/*     */   }
/*     */ 
/*     */   public void endMarkedContentSequence()
/*     */   {
/* 142 */     if (!this.currentMarkedContents.isEmpty())
/*     */     {
/* 144 */       this.currentMarkedContents.pop();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void xobject(PDXObject xobject)
/*     */   {
/* 150 */     if (!this.currentMarkedContents.isEmpty())
/*     */     {
/* 152 */       ((PDMarkedContent)this.currentMarkedContents.peek()).addXObject(xobject);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processTextPosition(TextPosition text)
/*     */   {
/* 166 */     boolean showCharacter = true;
/* 167 */     if (this.suppressDuplicateOverlappingText)
/*     */     {
/* 169 */       showCharacter = false;
/* 170 */       String textCharacter = text.getCharacter();
/* 171 */       float textX = text.getX();
/* 172 */       float textY = text.getY();
/* 173 */       List sameTextCharacters = (List)this.characterListMapping.get(textCharacter);
/* 174 */       if (sameTextCharacters == null)
/*     */       {
/* 176 */         sameTextCharacters = new ArrayList();
/* 177 */         this.characterListMapping.put(textCharacter, sameTextCharacters);
/*     */       }
/*     */ 
/* 191 */       boolean suppressCharacter = false;
/* 192 */       float tolerance = text.getWidth() / textCharacter.length() / 3.0F;
/* 193 */       for (int i = 0; i < sameTextCharacters.size(); i++)
/*     */       {
/* 195 */         TextPosition character = (TextPosition)sameTextCharacters.get(i);
/* 196 */         String charCharacter = character.getCharacter();
/* 197 */         float charX = character.getX();
/* 198 */         float charY = character.getY();
/*     */ 
/* 201 */         if ((charCharacter != null) && (within(charX, textX, tolerance)) && (within(charY, textY, tolerance)))
/*     */         {
/* 208 */           suppressCharacter = true;
/* 209 */           break;
/*     */         }
/*     */       }
/* 212 */       if (!suppressCharacter)
/*     */       {
/* 214 */         sameTextCharacters.add(text);
/* 215 */         showCharacter = true;
/*     */       }
/*     */     }
/*     */ 
/* 219 */     if (showCharacter)
/*     */     {
/* 221 */       List textList = new ArrayList();
/*     */ 
/* 229 */       if (textList.isEmpty())
/*     */       {
/* 231 */         textList.add(text);
/*     */       }
/*     */       else
/*     */       {
/* 239 */         TextPosition previousTextPosition = (TextPosition)textList.get(textList.size() - 1);
/* 240 */         if ((text.isDiacritic()) && (previousTextPosition.contains(text)))
/*     */         {
/* 242 */           previousTextPosition.mergeDiacritic(text, this.normalize);
/*     */         }
/* 246 */         else if ((previousTextPosition.isDiacritic()) && (text.contains(previousTextPosition)))
/*     */         {
/* 248 */           text.mergeDiacritic(previousTextPosition, this.normalize);
/* 249 */           textList.remove(textList.size() - 1);
/* 250 */           textList.add(text);
/*     */         }
/*     */         else
/*     */         {
/* 254 */           textList.add(text);
/*     */         }
/*     */       }
/* 257 */       if (!this.currentMarkedContents.isEmpty())
/*     */       {
/* 259 */         ((PDMarkedContent)this.currentMarkedContents.peek()).addText(text);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<PDMarkedContent> getMarkedContents()
/*     */   {
/* 267 */     return this.markedContents;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.PDFMarkedContentExtractor
 * JD-Core Version:    0.6.2
 */