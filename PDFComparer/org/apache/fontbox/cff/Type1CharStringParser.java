/*     */ package org.apache.fontbox.cff;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Type1CharStringParser
/*     */ {
/*  31 */   private DataInput input = null;
/*  32 */   private List<Object> sequence = null;
/*     */ 
/*     */   public List<Object> parse(byte[] bytes, IndexData localSubrIndex)
/*     */     throws IOException
/*     */   {
/*  44 */     return parse(bytes, localSubrIndex, true);
/*     */   }
/*     */ 
/*     */   private List<Object> parse(byte[] bytes, IndexData localSubrIndex, boolean init) throws IOException
/*     */   {
/*  49 */     if (init)
/*     */     {
/*  51 */       this.sequence = new ArrayList();
/*     */     }
/*  53 */     this.input = new DataInput(bytes);
/*  54 */     boolean localSubroutineIndexProvided = (localSubrIndex != null) && (localSubrIndex.getCount() > 0);
/*  55 */     while (this.input.hasRemaining())
/*     */     {
/*  57 */       int b0 = this.input.readUnsignedByte();
/*     */ 
/*  59 */       if ((b0 == 10) && (localSubroutineIndexProvided))
/*     */       {
/*  61 */         Integer operand = (Integer)this.sequence.remove(this.sequence.size() - 1);
/*     */ 
/*  63 */         int bias = 0;
/*  64 */         int nSubrs = localSubrIndex.getCount();
/*     */ 
/*  66 */         if (nSubrs < 1240)
/*     */         {
/*  68 */           bias = 107;
/*     */         }
/*  70 */         else if (nSubrs < 33900)
/*     */         {
/*  72 */           bias = 1131;
/*     */         }
/*     */         else
/*     */         {
/*  76 */           bias = 32768;
/*     */         }
/*  78 */         int subrNumber = bias + operand.intValue();
/*  79 */         if (subrNumber < localSubrIndex.getCount())
/*     */         {
/*  81 */           byte[] subrBytes = localSubrIndex.getBytes(subrNumber);
/*  82 */           parse(subrBytes, localSubrIndex, false);
/*  83 */           Object lastItem = this.sequence.get(this.sequence.size() - 1);
/*  84 */           if (((lastItem instanceof CharStringCommand)) && (((CharStringCommand)lastItem).getKey().getValue()[0] == 11))
/*     */           {
/*  86 */             this.sequence.remove(this.sequence.size() - 1);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*  91 */       else if ((b0 >= 0) && (b0 <= 31))
/*     */       {
/*  93 */         this.sequence.add(readCommand(b0));
/*     */       }
/*  95 */       else if ((b0 >= 32) && (b0 <= 255))
/*     */       {
/*  97 */         this.sequence.add(readNumber(b0));
/*     */       }
/*     */       else
/*     */       {
/* 101 */         throw new IllegalArgumentException();
/*     */       }
/*     */     }
/* 104 */     return this.sequence;
/*     */   }
/*     */ 
/*     */   private CharStringCommand readCommand(int b0) throws IOException
/*     */   {
/* 109 */     if (b0 == 12)
/*     */     {
/* 111 */       int b1 = this.input.readUnsignedByte();
/* 112 */       return new CharStringCommand(b0, b1);
/*     */     }
/* 114 */     return new CharStringCommand(b0);
/*     */   }
/*     */ 
/*     */   private Integer readNumber(int b0) throws IOException
/*     */   {
/* 119 */     if ((b0 >= 32) && (b0 <= 246))
/*     */     {
/* 121 */       return Integer.valueOf(b0 - 139);
/*     */     }
/* 123 */     if ((b0 >= 247) && (b0 <= 250))
/*     */     {
/* 125 */       int b1 = this.input.readUnsignedByte();
/* 126 */       return Integer.valueOf((b0 - 247) * 256 + b1 + 108);
/*     */     }
/* 128 */     if ((b0 >= 251) && (b0 <= 254))
/*     */     {
/* 130 */       int b1 = this.input.readUnsignedByte();
/* 131 */       return Integer.valueOf(-(b0 - 251) * 256 - b1 - 108);
/*     */     }
/* 133 */     if (b0 == 255)
/*     */     {
/* 135 */       int b1 = this.input.readUnsignedByte();
/* 136 */       int b2 = this.input.readUnsignedByte();
/* 137 */       int b3 = this.input.readUnsignedByte();
/* 138 */       int b4 = this.input.readUnsignedByte();
/*     */ 
/* 140 */       return Integer.valueOf(b1 << 24 | b2 << 16 | b3 << 8 | b4);
/*     */     }
/*     */ 
/* 144 */     throw new IllegalArgumentException();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.Type1CharStringParser
 * JD-Core Version:    0.6.2
 */