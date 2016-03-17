/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class LocationTextExtractionStrategy
/*     */   implements TextExtractionStrategy
/*     */ {
/*  75 */   static boolean DUMP_STATE = false;
/*     */ 
/*  78 */   private final List<TextChunk> locationalResult = new ArrayList();
/*     */ 
/*     */   public void beginTextBlock()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endTextBlock()
/*     */   {
/*     */   }
/*     */ 
/*     */   private boolean startsWithSpace(String str)
/*     */   {
/* 103 */     if (str.length() == 0) return false;
/* 104 */     return str.charAt(0) == ' ';
/*     */   }
/*     */ 
/*     */   private boolean endsWithSpace(String str)
/*     */   {
/* 112 */     if (str.length() == 0) return false;
/* 113 */     return str.charAt(str.length() - 1) == ' ';
/*     */   }
/*     */ 
/*     */   private List<TextChunk> filterTextChunks(List<TextChunk> textChunks, TextChunkFilter filter)
/*     */   {
/* 124 */     if (filter == null) {
/* 125 */       return textChunks;
/*     */     }
/* 127 */     List filtered = new ArrayList();
/* 128 */     for (TextChunk textChunk : textChunks) {
/* 129 */       if (filter.accept(textChunk))
/* 130 */         filtered.add(textChunk);
/*     */     }
/* 132 */     return filtered;
/*     */   }
/*     */ 
/*     */   protected boolean isChunkAtWordBoundary(TextChunk chunk, TextChunk previousChunk)
/*     */   {
/* 146 */     float dist = chunk.distanceFromEndOf(previousChunk);
/*     */ 
/* 148 */     if ((dist < -chunk.getCharSpaceWidth()) || (dist > chunk.getCharSpaceWidth() / 2.0F)) {
/* 149 */       return true;
/*     */     }
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */   public String getResultantText(TextChunkFilter chunkFilter)
/*     */   {
/* 163 */     if (DUMP_STATE) dumpState();
/*     */ 
/* 165 */     List filteredTextChunks = filterTextChunks(this.locationalResult, chunkFilter);
/* 166 */     Collections.sort(filteredTextChunks);
/*     */ 
/* 168 */     StringBuffer sb = new StringBuffer();
/* 169 */     TextChunk lastChunk = null;
/* 170 */     for (TextChunk chunk : filteredTextChunks)
/*     */     {
/* 172 */       if (lastChunk == null) {
/* 173 */         sb.append(chunk.text);
/*     */       }
/* 175 */       else if (chunk.sameLine(lastChunk))
/*     */       {
/* 177 */         if ((isChunkAtWordBoundary(chunk, lastChunk)) && (!startsWithSpace(chunk.text)) && (!endsWithSpace(lastChunk.text))) {
/* 178 */           sb.append(' ');
/*     */         }
/* 180 */         sb.append(chunk.text);
/*     */       } else {
/* 182 */         sb.append('\n');
/* 183 */         sb.append(chunk.text);
/*     */       }
/*     */ 
/* 186 */       lastChunk = chunk;
/*     */     }
/*     */ 
/* 189 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String getResultantText()
/*     */   {
/* 198 */     return getResultantText(null);
/*     */   }
/*     */ 
/*     */   private void dumpState()
/*     */   {
/* 204 */     for (Iterator iterator = this.locationalResult.iterator(); iterator.hasNext(); ) {
/* 205 */       TextChunk location = (TextChunk)iterator.next();
/*     */ 
/* 207 */       location.printDiagnostics();
/*     */ 
/* 209 */       System.out.println();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void renderText(TextRenderInfo renderInfo)
/*     */   {
/* 219 */     LineSegment segment = renderInfo.getBaseline();
/* 220 */     if (renderInfo.getRise() != 0.0F) {
/* 221 */       Matrix riseOffsetTransform = new Matrix(0.0F, -renderInfo.getRise());
/* 222 */       segment = segment.transformBy(riseOffsetTransform);
/*     */     }
/* 224 */     TextChunk location = new TextChunk(renderInfo.getText(), segment.getStartPoint(), segment.getEndPoint(), renderInfo.getSingleSpaceWidth());
/* 225 */     this.locationalResult.add(location);
/*     */   }
/*     */ 
/*     */   public void renderImage(ImageRenderInfo renderInfo) {
/*     */   }
/*     */ 
/*     */   public static abstract interface TextChunkFilter {
/*     */     public abstract boolean accept(LocationTextExtractionStrategy.TextChunk paramTextChunk);
/*     */   }
/*     */ 
/*     */   public static class TextChunk implements Comparable<TextChunk> {
/*     */     private final String text;
/*     */     private final Vector startLocation;
/*     */     private final Vector endLocation;
/*     */     private final Vector orientationVector;
/*     */     private final int orientationMagnitude;
/*     */     private final int distPerpendicular;
/*     */     private final float distParallelStart;
/*     */     private final float distParallelEnd;
/*     */     private final float charSpaceWidth;
/*     */ 
/*     */     public TextChunk(String string, Vector startLocation, Vector endLocation, float charSpaceWidth) {
/* 255 */       this.text = string;
/* 256 */       this.startLocation = startLocation;
/* 257 */       this.endLocation = endLocation;
/* 258 */       this.charSpaceWidth = charSpaceWidth;
/*     */ 
/* 260 */       Vector oVector = endLocation.subtract(startLocation);
/* 261 */       if (oVector.length() == 0.0F) {
/* 262 */         oVector = new Vector(1.0F, 0.0F, 0.0F);
/*     */       }
/* 264 */       this.orientationVector = oVector.normalize();
/* 265 */       this.orientationMagnitude = ((int)(Math.atan2(this.orientationVector.get(1), this.orientationVector.get(0)) * 1000.0D));
/*     */ 
/* 270 */       Vector origin = new Vector(0.0F, 0.0F, 1.0F);
/* 271 */       this.distPerpendicular = ((int)startLocation.subtract(origin).cross(this.orientationVector).get(2));
/*     */ 
/* 273 */       this.distParallelStart = this.orientationVector.dot(startLocation);
/* 274 */       this.distParallelEnd = this.orientationVector.dot(endLocation);
/*     */     }
/*     */ 
/*     */     public Vector getStartLocation()
/*     */     {
/* 281 */       return this.startLocation;
/*     */     }
/*     */ 
/*     */     public Vector getEndLocation()
/*     */     {
/* 287 */       return this.endLocation;
/*     */     }
/*     */ 
/*     */     public String getText()
/*     */     {
/* 294 */       return this.text;
/*     */     }
/*     */ 
/*     */     public float getCharSpaceWidth()
/*     */     {
/* 301 */       return this.charSpaceWidth;
/*     */     }
/*     */ 
/*     */     private void printDiagnostics() {
/* 305 */       System.out.println("Text (@" + this.startLocation + " -> " + this.endLocation + "): " + this.text);
/* 306 */       System.out.println("orientationMagnitude: " + this.orientationMagnitude);
/* 307 */       System.out.println("distPerpendicular: " + this.distPerpendicular);
/* 308 */       System.out.println("distParallel: " + this.distParallelStart);
/*     */     }
/*     */ 
/*     */     public boolean sameLine(TextChunk as)
/*     */     {
/* 316 */       if (this.orientationMagnitude != as.orientationMagnitude) return false;
/* 317 */       if (this.distPerpendicular != as.distPerpendicular) return false;
/* 318 */       return true;
/*     */     }
/*     */ 
/*     */     public float distanceFromEndOf(TextChunk other)
/*     */     {
/* 330 */       float distance = this.distParallelStart - other.distParallelEnd;
/* 331 */       return distance;
/*     */     }
/*     */ 
/*     */     public int compareTo(TextChunk rhs)
/*     */     {
/* 339 */       if (this == rhs) return 0;
/*     */ 
/* 342 */       int rslt = compareInts(this.orientationMagnitude, rhs.orientationMagnitude);
/* 343 */       if (rslt != 0) return rslt;
/*     */ 
/* 345 */       rslt = compareInts(this.distPerpendicular, rhs.distPerpendicular);
/* 346 */       if (rslt != 0) return rslt;
/*     */ 
/* 348 */       return Float.compare(this.distParallelStart, rhs.distParallelStart);
/*     */     }
/*     */ 
/*     */     private static int compareInts(int int1, int int2)
/*     */     {
/* 358 */       return int1 < int2 ? -1 : int1 == int2 ? 0 : 1;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy
 * JD-Core Version:    0.6.2
 */