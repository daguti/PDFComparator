/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class GlyfCompositeDescript extends GlyfDescript
/*     */ {
/*  36 */   private List<GlyfCompositeComp> components = new ArrayList();
/*  37 */   private GlyphData[] glyphs = null;
/*  38 */   private boolean beingResolved = false;
/*  39 */   private boolean resolved = false;
/*     */ 
/*     */   public GlyfCompositeDescript(TTFDataStream bais, GlyphTable glyphTable)
/*     */     throws IOException
/*     */   {
/*  50 */     super((short)-1, bais);
/*     */ 
/*  52 */     this.glyphs = glyphTable.getGlyphs();
/*     */     GlyfCompositeComp comp;
/*     */     do
/*     */     {
/*  58 */       comp = new GlyfCompositeComp(bais);
/*  59 */       this.components.add(comp);
/*  60 */     }while ((comp.getFlags() & 0x20) != 0);
/*     */ 
/*  63 */     if ((comp.getFlags() & 0x100) != 0)
/*     */     {
/*  65 */       readInstructions(bais, bais.readUnsignedShort());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void resolve()
/*     */   {
/*  74 */     if (this.resolved)
/*     */     {
/*  76 */       return;
/*     */     }
/*  78 */     if (this.beingResolved)
/*     */     {
/*  80 */       System.err.println("Circular reference in GlyfCompositeDesc");
/*  81 */       return;
/*     */     }
/*  83 */     this.beingResolved = true;
/*     */ 
/*  85 */     int firstIndex = 0;
/*  86 */     int firstContour = 0;
/*     */ 
/*  88 */     Iterator i = this.components.iterator();
/*  89 */     while (i.hasNext())
/*     */     {
/*  91 */       GlyfCompositeComp comp = (GlyfCompositeComp)i.next();
/*  92 */       comp.setFirstIndex(firstIndex);
/*  93 */       comp.setFirstContour(firstContour);
/*     */ 
/*  96 */       GlyphDescription desc = getGlypDescription(comp.getGlyphIndex());
/*  97 */       if (desc != null)
/*     */       {
/*  99 */         desc.resolve();
/* 100 */         firstIndex += desc.getPointCount();
/* 101 */         firstContour += desc.getContourCount();
/*     */       }
/*     */     }
/* 104 */     this.resolved = true;
/* 105 */     this.beingResolved = false;
/*     */   }
/*     */ 
/*     */   public int getEndPtOfContours(int i)
/*     */   {
/* 113 */     GlyfCompositeComp c = getCompositeCompEndPt(i);
/* 114 */     if (c != null)
/*     */     {
/* 116 */       GlyphDescription gd = getGlypDescription(c.getGlyphIndex());
/* 117 */       return gd.getEndPtOfContours(i - c.getFirstContour()) + c.getFirstIndex();
/*     */     }
/* 119 */     return 0;
/*     */   }
/*     */ 
/*     */   public byte getFlags(int i)
/*     */   {
/* 127 */     GlyfCompositeComp c = getCompositeComp(i);
/* 128 */     if (c != null)
/*     */     {
/* 130 */       GlyphDescription gd = getGlypDescription(c.getGlyphIndex());
/* 131 */       return gd.getFlags(i - c.getFirstIndex());
/*     */     }
/* 133 */     return 0;
/*     */   }
/*     */ 
/*     */   public short getXCoordinate(int i)
/*     */   {
/* 141 */     GlyfCompositeComp c = getCompositeComp(i);
/* 142 */     if (c != null)
/*     */     {
/* 144 */       GlyphDescription gd = getGlypDescription(c.getGlyphIndex());
/* 145 */       int n = i - c.getFirstIndex();
/* 146 */       int x = gd.getXCoordinate(n);
/* 147 */       int y = gd.getYCoordinate(n);
/* 148 */       short x1 = (short)c.scaleX(x, y);
/* 149 */       x1 = (short)(x1 + c.getXTranslate());
/* 150 */       return x1;
/*     */     }
/* 152 */     return 0;
/*     */   }
/*     */ 
/*     */   public short getYCoordinate(int i)
/*     */   {
/* 160 */     GlyfCompositeComp c = getCompositeComp(i);
/* 161 */     if (c != null)
/*     */     {
/* 163 */       GlyphDescription gd = getGlypDescription(c.getGlyphIndex());
/* 164 */       int n = i - c.getFirstIndex();
/* 165 */       int x = gd.getXCoordinate(n);
/* 166 */       int y = gd.getYCoordinate(n);
/* 167 */       short y1 = (short)c.scaleY(x, y);
/* 168 */       y1 = (short)(y1 + c.getYTranslate());
/* 169 */       return y1;
/*     */     }
/* 171 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean isComposite()
/*     */   {
/* 179 */     return true;
/*     */   }
/*     */ 
/*     */   public int getPointCount()
/*     */   {
/* 187 */     if (!this.resolved)
/*     */     {
/* 189 */       System.err.println("getPointCount called on unresolved GlyfCompositeDescript");
/*     */     }
/* 191 */     GlyfCompositeComp c = (GlyfCompositeComp)this.components.get(this.components.size() - 1);
/* 192 */     return c.getFirstIndex() + getGlypDescription(c.getGlyphIndex()).getPointCount();
/*     */   }
/*     */ 
/*     */   public int getContourCount()
/*     */   {
/* 200 */     if (!this.resolved)
/*     */     {
/* 202 */       System.err.println("getContourCount called on unresolved GlyfCompositeDescript");
/*     */     }
/* 204 */     GlyfCompositeComp c = (GlyfCompositeComp)this.components.get(this.components.size() - 1);
/* 205 */     return c.getFirstContour() + getGlypDescription(c.getGlyphIndex()).getContourCount();
/*     */   }
/*     */ 
/*     */   public int getComponentCount()
/*     */   {
/* 215 */     return this.components.size();
/*     */   }
/*     */ 
/*     */   private GlyfCompositeComp getCompositeComp(int i)
/*     */   {
/* 221 */     for (int n = 0; n < this.components.size(); n++)
/*     */     {
/* 223 */       GlyfCompositeComp c = (GlyfCompositeComp)this.components.get(n);
/* 224 */       GlyphDescription gd = getGlypDescription(c.getGlyphIndex());
/* 225 */       if ((c.getFirstIndex() <= i) && (i < c.getFirstIndex() + gd.getPointCount()))
/*     */       {
/* 227 */         return c;
/*     */       }
/*     */     }
/* 230 */     return null;
/*     */   }
/*     */ 
/*     */   private GlyfCompositeComp getCompositeCompEndPt(int i)
/*     */   {
/* 236 */     for (int j = 0; j < this.components.size(); j++)
/*     */     {
/* 238 */       GlyfCompositeComp c = (GlyfCompositeComp)this.components.get(j);
/* 239 */       GlyphDescription gd = getGlypDescription(c.getGlyphIndex());
/* 240 */       if ((c.getFirstContour() <= i) && (i < c.getFirstContour() + gd.getContourCount()))
/*     */       {
/* 242 */         return c;
/*     */       }
/*     */     }
/* 245 */     return null;
/*     */   }
/*     */ 
/*     */   private GlyphDescription getGlypDescription(int index)
/*     */   {
/* 250 */     if ((this.glyphs != null) && (index < this.glyphs.length))
/*     */     {
/* 252 */       GlyphData glyph = this.glyphs[index];
/* 253 */       if (glyph != null)
/*     */       {
/* 255 */         return glyph.getDescription();
/*     */       }
/*     */     }
/* 258 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.GlyfCompositeDescript
 * JD-Core Version:    0.6.2
 */