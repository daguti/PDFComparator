/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class GlyfSimpleDescript extends GlyfDescript
/*     */ {
/*  35 */   private static final Log LOG = LogFactory.getLog(GlyfSimpleDescript.class);
/*     */   private int[] endPtsOfContours;
/*     */   private byte[] flags;
/*     */   private short[] xCoordinates;
/*     */   private short[] yCoordinates;
/*     */   private int pointCount;
/*     */ 
/*     */   public GlyfSimpleDescript(short numberOfContours, TTFDataStream bais)
/*     */     throws IOException
/*     */   {
/*  52 */     super(numberOfContours, bais);
/*     */ 
/*  59 */     if (numberOfContours == 0)
/*     */     {
/*  61 */       this.pointCount = 0;
/*  62 */       return;
/*     */     }
/*     */ 
/*  66 */     this.endPtsOfContours = new int[numberOfContours];
/*  67 */     this.endPtsOfContours = bais.readUnsignedShortArray(numberOfContours);
/*     */ 
/*  70 */     this.pointCount = (this.endPtsOfContours[(numberOfContours - 1)] + 1);
/*     */ 
/*  72 */     this.flags = new byte[this.pointCount];
/*  73 */     this.xCoordinates = new short[this.pointCount];
/*  74 */     this.yCoordinates = new short[this.pointCount];
/*     */ 
/*  76 */     int instructionCount = bais.readUnsignedShort();
/*  77 */     readInstructions(bais, instructionCount);
/*  78 */     readFlags(this.pointCount, bais);
/*  79 */     readCoords(this.pointCount, bais);
/*     */   }
/*     */ 
/*     */   public int getEndPtOfContours(int i)
/*     */   {
/*  87 */     return this.endPtsOfContours[i];
/*     */   }
/*     */ 
/*     */   public byte getFlags(int i)
/*     */   {
/*  95 */     return this.flags[i];
/*     */   }
/*     */ 
/*     */   public short getXCoordinate(int i)
/*     */   {
/* 103 */     return this.xCoordinates[i];
/*     */   }
/*     */ 
/*     */   public short getYCoordinate(int i)
/*     */   {
/* 111 */     return this.yCoordinates[i];
/*     */   }
/*     */ 
/*     */   public boolean isComposite()
/*     */   {
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */   public int getPointCount()
/*     */   {
/* 127 */     return this.pointCount;
/*     */   }
/*     */ 
/*     */   private void readCoords(int count, TTFDataStream bais)
/*     */     throws IOException
/*     */   {
/* 135 */     short x = 0;
/* 136 */     short y = 0;
/* 137 */     for (int i = 0; i < count; i++)
/*     */     {
/* 139 */       if ((this.flags[i] & 0x10) != 0)
/*     */       {
/* 141 */         if ((this.flags[i] & 0x2) != 0)
/*     */         {
/* 143 */           x = (short)(x + (short)bais.readUnsignedByte());
/*     */         }
/*     */ 
/*     */       }
/* 148 */       else if ((this.flags[i] & 0x2) != 0)
/*     */       {
/* 150 */         x = (short)(x + (short)-(short)bais.readUnsignedByte());
/*     */       }
/*     */       else
/*     */       {
/* 154 */         x = (short)(x + bais.readSignedShort());
/*     */       }
/*     */ 
/* 157 */       this.xCoordinates[i] = x;
/*     */     }
/*     */ 
/* 160 */     for (int i = 0; i < count; i++)
/*     */     {
/* 162 */       if ((this.flags[i] & 0x20) != 0)
/*     */       {
/* 164 */         if ((this.flags[i] & 0x4) != 0)
/*     */         {
/* 166 */           y = (short)(y + (short)bais.readUnsignedByte());
/*     */         }
/*     */ 
/*     */       }
/* 171 */       else if ((this.flags[i] & 0x4) != 0)
/*     */       {
/* 173 */         y = (short)(y + (short)-(short)bais.readUnsignedByte());
/*     */       }
/*     */       else
/*     */       {
/* 177 */         y = (short)(y + bais.readSignedShort());
/*     */       }
/*     */ 
/* 180 */       this.yCoordinates[i] = y;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void readFlags(int flagCount, TTFDataStream bais)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 191 */       for (int index = 0; index < flagCount; index++)
/*     */       {
/* 193 */         this.flags[index] = ((byte)bais.readUnsignedByte());
/* 194 */         if ((this.flags[index] & 0x8) != 0)
/*     */         {
/* 196 */           int repeats = bais.readUnsignedByte();
/* 197 */           for (int i = 1; i <= repeats; i++)
/*     */           {
/* 199 */             this.flags[(index + i)] = this.flags[index];
/*     */           }
/* 201 */           index += repeats;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (ArrayIndexOutOfBoundsException e)
/*     */     {
/* 207 */       LOG.error("error: array index out of bounds", e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.GlyfSimpleDescript
 * JD-Core Version:    0.6.2
 */