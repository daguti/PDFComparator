/*     */ package org.apache.fontbox.cff;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Type2CharStringParser
/*     */ {
/*  31 */   private int hstemCount = 0;
/*  32 */   private int vstemCount = 0;
/*  33 */   private List<Object> sequence = null;
/*     */ 
/*     */   public List<Object> parse(byte[] bytes, IndexData globalSubrIndex, IndexData localSubrIndex)
/*     */     throws IOException
/*     */   {
/*  46 */     return parse(bytes, globalSubrIndex, localSubrIndex, true);
/*     */   }
/*     */ 
/*     */   private List<Object> parse(byte[] bytes, IndexData globalSubrIndex, IndexData localSubrIndex, boolean init) throws IOException
/*     */   {
/*  51 */     if (init)
/*     */     {
/*  53 */       this.hstemCount = 0;
/*  54 */       this.vstemCount = 0;
/*  55 */       this.sequence = new ArrayList();
/*     */     }
/*  57 */     DataInput input = new DataInput(bytes);
/*  58 */     boolean localSubroutineIndexProvided = (localSubrIndex != null) && (localSubrIndex.getCount() > 0);
/*  59 */     boolean globalSubroutineIndexProvided = (globalSubrIndex != null) && (globalSubrIndex.getCount() > 0);
/*     */ 
/*  61 */     while (input.hasRemaining())
/*     */     {
/*  63 */       int b0 = input.readUnsignedByte();
/*  64 */       if ((b0 == 10) && (localSubroutineIndexProvided))
/*     */       {
/*  66 */         Integer operand = (Integer)this.sequence.remove(this.sequence.size() - 1);
/*     */ 
/*  68 */         int bias = 0;
/*  69 */         int nSubrs = localSubrIndex.getCount();
/*     */ 
/*  71 */         if (nSubrs < 1240)
/*     */         {
/*  73 */           bias = 107;
/*     */         }
/*  75 */         else if (nSubrs < 33900)
/*     */         {
/*  77 */           bias = 1131;
/*     */         }
/*     */         else
/*     */         {
/*  81 */           bias = 32768;
/*     */         }
/*  83 */         int subrNumber = bias + operand.intValue();
/*  84 */         if (subrNumber < localSubrIndex.getCount())
/*     */         {
/*  86 */           byte[] subrBytes = localSubrIndex.getBytes(subrNumber);
/*  87 */           parse(subrBytes, globalSubrIndex, localSubrIndex, false);
/*  88 */           Object lastItem = this.sequence.get(this.sequence.size() - 1);
/*  89 */           if (((lastItem instanceof CharStringCommand)) && (((CharStringCommand)lastItem).getKey().getValue()[0] == 11))
/*     */           {
/*  91 */             this.sequence.remove(this.sequence.size() - 1);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*  96 */       else if ((b0 == 29) && (globalSubroutineIndexProvided))
/*     */       {
/*  98 */         Integer operand = (Integer)this.sequence.remove(this.sequence.size() - 1);
/*     */ 
/* 100 */         int bias = 0;
/* 101 */         int nSubrs = globalSubrIndex.getCount();
/*     */ 
/* 103 */         if (nSubrs < 1240)
/*     */         {
/* 105 */           bias = 107;
/*     */         }
/* 107 */         else if (nSubrs < 33900)
/*     */         {
/* 109 */           bias = 1131;
/*     */         }
/*     */         else
/*     */         {
/* 113 */           bias = 32768;
/*     */         }
/*     */ 
/* 116 */         int subrNumber = bias + operand.intValue();
/* 117 */         if (subrNumber < globalSubrIndex.getCount())
/*     */         {
/* 119 */           byte[] subrBytes = globalSubrIndex.getBytes(subrNumber);
/* 120 */           parse(subrBytes, globalSubrIndex, localSubrIndex, false);
/* 121 */           Object lastItem = this.sequence.get(this.sequence.size() - 1);
/* 122 */           if (((lastItem instanceof CharStringCommand)) && (((CharStringCommand)lastItem).getKey().getValue()[0] == 11))
/*     */           {
/* 124 */             this.sequence.remove(this.sequence.size() - 1);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/* 129 */       else if ((b0 >= 0) && (b0 <= 27))
/*     */       {
/* 131 */         this.sequence.add(readCommand(b0, input));
/*     */       }
/* 133 */       else if (b0 == 28)
/*     */       {
/* 135 */         this.sequence.add(readNumber(b0, input));
/*     */       }
/* 137 */       else if ((b0 >= 29) && (b0 <= 31))
/*     */       {
/* 139 */         this.sequence.add(readCommand(b0, input));
/*     */       }
/* 141 */       else if ((b0 >= 32) && (b0 <= 255))
/*     */       {
/* 143 */         this.sequence.add(readNumber(b0, input));
/*     */       }
/*     */       else
/*     */       {
/* 147 */         throw new IllegalArgumentException();
/*     */       }
/*     */     }
/* 150 */     return this.sequence;
/*     */   }
/*     */ 
/*     */   private CharStringCommand readCommand(int b0, DataInput input)
/*     */     throws IOException
/*     */   {
/* 156 */     if ((b0 == 1) || (b0 == 18))
/*     */     {
/* 158 */       this.hstemCount += peekNumbers().size() / 2;
/*     */     }
/* 160 */     else if ((b0 == 3) || (b0 == 19) || (b0 == 20) || (b0 == 23))
/*     */     {
/* 162 */       this.vstemCount += peekNumbers().size() / 2;
/*     */     }
/*     */ 
/* 165 */     if (b0 == 12)
/*     */     {
/* 167 */       int b1 = input.readUnsignedByte();
/*     */ 
/* 169 */       return new CharStringCommand(b0, b1);
/*     */     }
/* 171 */     if ((b0 == 19) || (b0 == 20))
/*     */     {
/* 173 */       int[] value = new int[1 + getMaskLength()];
/* 174 */       value[0] = b0;
/*     */ 
/* 176 */       for (int i = 1; i < value.length; i++)
/*     */       {
/* 178 */         value[i] = input.readUnsignedByte();
/*     */       }
/*     */ 
/* 181 */       return new CharStringCommand(value);
/*     */     }
/*     */ 
/* 184 */     return new CharStringCommand(b0);
/*     */   }
/*     */ 
/*     */   private Integer readNumber(int b0, DataInput input)
/*     */     throws IOException
/*     */   {
/* 190 */     if (b0 == 28)
/*     */     {
/* 192 */       int b1 = input.readUnsignedByte();
/* 193 */       int b2 = input.readUnsignedByte();
/*     */ 
/* 195 */       return Integer.valueOf((short)(b1 << 8 | b2));
/*     */     }
/* 197 */     if ((b0 >= 32) && (b0 <= 246))
/*     */     {
/* 199 */       return Integer.valueOf(b0 - 139);
/*     */     }
/* 201 */     if ((b0 >= 247) && (b0 <= 250))
/*     */     {
/* 203 */       int b1 = input.readUnsignedByte();
/*     */ 
/* 205 */       return Integer.valueOf((b0 - 247) * 256 + b1 + 108);
/*     */     }
/* 207 */     if ((b0 >= 251) && (b0 <= 254))
/*     */     {
/* 209 */       int b1 = input.readUnsignedByte();
/*     */ 
/* 211 */       return Integer.valueOf(-(b0 - 251) * 256 - b1 - 108);
/*     */     }
/* 213 */     if (b0 == 255)
/*     */     {
/* 215 */       int b1 = input.readUnsignedByte();
/* 216 */       int b2 = input.readUnsignedByte();
/*     */ 
/* 219 */       input.readUnsignedByte();
/* 220 */       input.readUnsignedByte();
/* 221 */       return Integer.valueOf((short)(b1 << 8 | b2));
/*     */     }
/*     */ 
/* 225 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   private int getMaskLength()
/*     */   {
/* 231 */     int length = 1;
/*     */ 
/* 233 */     int hintCount = this.hstemCount + this.vstemCount;
/*     */     while (true) { hintCount -= 8; if (hintCount <= 0)
/*     */         break;
/* 236 */       length++;
/*     */     }
/*     */ 
/* 239 */     return length;
/*     */   }
/*     */ 
/*     */   private List<Number> peekNumbers()
/*     */   {
/* 244 */     List numbers = new ArrayList();
/*     */ 
/* 246 */     for (int i = this.sequence.size() - 1; i > -1; i--)
/*     */     {
/* 248 */       Object object = this.sequence.get(i);
/*     */ 
/* 250 */       if ((object instanceof Number))
/*     */       {
/* 252 */         Number number = (Number)object;
/*     */ 
/* 254 */         numbers.add(0, number);
/*     */       }
/*     */       else
/*     */       {
/* 259 */         return numbers;
/*     */       }
/*     */     }
/* 262 */     return numbers;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.Type2CharStringParser
 * JD-Core Version:    0.6.2
 */