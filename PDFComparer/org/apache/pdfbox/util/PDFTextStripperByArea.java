/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ 
/*     */ public class PDFTextStripperByArea extends PDFTextStripper
/*     */ {
/*  42 */   private List<String> regions = new ArrayList();
/*  43 */   private Map<String, Rectangle2D> regionArea = new HashMap();
/*  44 */   private Map<String, Vector<ArrayList<TextPosition>>> regionCharacterList = new HashMap();
/*     */ 
/*  46 */   private Map<String, StringWriter> regionText = new HashMap();
/*     */ 
/*     */   public PDFTextStripperByArea()
/*     */     throws IOException
/*     */   {
/*  55 */     setPageSeparator("");
/*     */   }
/*     */ 
/*     */   public PDFTextStripperByArea(Properties props)
/*     */     throws IOException
/*     */   {
/*  73 */     super(props);
/*  74 */     setPageSeparator("");
/*     */   }
/*     */ 
/*     */   public PDFTextStripperByArea(String encoding)
/*     */     throws IOException
/*     */   {
/*  89 */     super(encoding);
/*  90 */     setPageSeparator("");
/*     */   }
/*     */ 
/*     */   public void addRegion(String regionName, Rectangle2D rect)
/*     */   {
/* 101 */     this.regions.add(regionName);
/* 102 */     this.regionArea.put(regionName, rect);
/*     */   }
/*     */ 
/*     */   public List<String> getRegions()
/*     */   {
/* 112 */     return this.regions;
/*     */   }
/*     */ 
/*     */   public String getTextForRegion(String regionName)
/*     */   {
/* 123 */     StringWriter text = (StringWriter)this.regionText.get(regionName);
/* 124 */     return text.toString();
/*     */   }
/*     */ 
/*     */   public void extractRegions(PDPage page)
/*     */     throws IOException
/*     */   {
/* 135 */     Iterator regionIter = this.regions.iterator();
/* 136 */     while (regionIter.hasNext())
/*     */     {
/* 138 */       setStartPage(getCurrentPageNo());
/* 139 */       setEndPage(getCurrentPageNo());
/*     */ 
/* 142 */       String regionName = (String)regionIter.next();
/* 143 */       Vector regionCharactersByArticle = new Vector();
/* 144 */       regionCharactersByArticle.add(new ArrayList());
/* 145 */       this.regionCharacterList.put(regionName, regionCharactersByArticle);
/* 146 */       this.regionText.put(regionName, new StringWriter());
/*     */     }
/*     */ 
/* 149 */     PDStream contentStream = page.getContents();
/* 150 */     if (contentStream != null)
/*     */     {
/* 152 */       COSStream contents = contentStream.getStream();
/* 153 */       processPage(page, contents);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processTextPosition(TextPosition text)
/*     */   {
/* 163 */     Iterator regionIter = this.regionArea.keySet().iterator();
/* 164 */     while (regionIter.hasNext())
/*     */     {
/* 166 */       String region = (String)regionIter.next();
/* 167 */       Rectangle2D rect = (Rectangle2D)this.regionArea.get(region);
/* 168 */       if (rect.contains(text.getX(), text.getY()))
/*     */       {
/* 170 */         this.charactersByArticle = ((Vector)this.regionCharacterList.get(region));
/* 171 */         super.processTextPosition(text);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void writePage()
/*     */     throws IOException
/*     */   {
/* 184 */     Iterator regionIter = this.regionArea.keySet().iterator();
/* 185 */     while (regionIter.hasNext())
/*     */     {
/* 187 */       String region = (String)regionIter.next();
/* 188 */       this.charactersByArticle = ((Vector)this.regionCharacterList.get(region));
/* 189 */       this.output = ((Writer)this.regionText.get(region));
/* 190 */       super.writePage();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.PDFTextStripperByArea
 * JD-Core Version:    0.6.2
 */