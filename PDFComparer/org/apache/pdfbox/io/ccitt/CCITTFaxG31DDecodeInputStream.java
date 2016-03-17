/*     */ package org.apache.pdfbox.io.ccitt;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class CCITTFaxG31DDecodeInputStream extends InputStream
/*     */   implements CCITTFaxConstants
/*     */ {
/*     */   private static final int CODE_WORD = 0;
/*     */   private static final int SIGNAL_EOD = -1;
/*     */   private static final int SIGNAL_EOL = -2;
/*     */   private InputStream source;
/*     */   private int columns;
/*     */   private int rows;
/*     */   private boolean encodedByteAlign;
/*     */   private int bits;
/*  43 */   private int bitPos = 8;
/*     */   private PackedBitArray decodedLine;
/*     */   private int decodedWritePos;
/*     */   private int decodedReadPos;
/*  51 */   private int y = -1;
/*     */   private int accumulatedRunLength;
/*  58 */   private static final NonLeafLookupTreeNode WHITE_LOOKUP_TREE_ROOT = new NonLeafLookupTreeNode(null);
/*  59 */   private static final NonLeafLookupTreeNode BLACK_LOOKUP_TREE_ROOT = new NonLeafLookupTreeNode(null);
/*     */ 
/* 206 */   private static final int[] BIT_POS_MASKS = { 128, 64, 32, 16, 8, 4, 2, 1 };
/*     */   private static final short EOL_STARTER = 2816;
/*     */ 
/*     */   public CCITTFaxG31DDecodeInputStream(InputStream source, int columns, int rows, boolean encodedByteAlign)
/*     */   {
/*  73 */     this.source = source;
/*  74 */     this.columns = columns;
/*  75 */     this.rows = rows;
/*  76 */     this.decodedLine = new PackedBitArray(columns);
/*  77 */     this.decodedReadPos = this.decodedLine.getByteCount();
/*  78 */     this.encodedByteAlign = encodedByteAlign;
/*     */   }
/*     */ 
/*     */   public CCITTFaxG31DDecodeInputStream(InputStream source, int columns, boolean encodedByteAlign)
/*     */   {
/*  90 */     this(source, columns, 0, encodedByteAlign);
/*     */   }
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 102 */     if (this.decodedReadPos >= this.decodedLine.getByteCount())
/*     */     {
/* 104 */       boolean hasLine = decodeLine();
/* 105 */       if (!hasLine)
/*     */       {
/* 107 */         return -1;
/*     */       }
/*     */     }
/* 110 */     byte data = this.decodedLine.getData()[(this.decodedReadPos++)];
/*     */ 
/* 113 */     return data & 0xFF;
/*     */   }
/*     */ 
/*     */   private boolean decodeLine()
/*     */     throws IOException
/*     */   {
/* 120 */     if ((this.encodedByteAlign) && (this.bitPos != 0))
/*     */     {
/* 122 */       readByte();
/*     */     }
/* 124 */     if (this.bits < 0)
/*     */     {
/* 126 */       return false;
/*     */     }
/* 128 */     this.y += 1;
/*     */ 
/* 130 */     int x = 0;
/* 131 */     if ((this.rows > 0) && (this.y >= this.rows))
/*     */     {
/* 133 */       return false;
/*     */     }
/* 135 */     this.decodedLine.clear();
/* 136 */     this.decodedWritePos = 0;
/* 137 */     int expectRTC = 6;
/* 138 */     boolean white = true;
/* 139 */     while ((x < this.columns) || (this.accumulatedRunLength > 0))
/*     */     {
/* 142 */       LookupTreeNode root = white ? WHITE_LOOKUP_TREE_ROOT : BLACK_LOOKUP_TREE_ROOT;
/* 143 */       CodeWord code = root.getNextCodeWord(this);
/* 144 */       if (code == null)
/*     */       {
/* 147 */         if (x > 0)
/*     */         {
/* 150 */           this.decodedReadPos = 0;
/* 151 */           return true;
/*     */         }
/*     */ 
/* 155 */         return false;
/*     */       }
/*     */ 
/* 158 */       if (code.getType() == -2)
/*     */       {
/* 160 */         expectRTC--;
/* 161 */         if (expectRTC == 0)
/*     */         {
/* 164 */           return false;
/*     */         }
/*     */ 
/* 166 */         if (x != 0);
/*     */       }
/*     */       else
/*     */       {
/* 174 */         expectRTC = -1;
/* 175 */         x += code.execute(this);
/* 176 */         if (this.accumulatedRunLength == 0)
/*     */         {
/* 179 */           white = !white;
/*     */         }
/*     */       }
/*     */     }
/* 183 */     this.decodedReadPos = 0;
/* 184 */     return true;
/*     */   }
/*     */ 
/*     */   private void writeRun(int bit, int length)
/*     */   {
/* 189 */     this.accumulatedRunLength += length;
/*     */ 
/* 192 */     if (bit != 0)
/*     */     {
/* 194 */       this.decodedLine.setBits(this.decodedWritePos, this.accumulatedRunLength);
/*     */     }
/* 196 */     this.decodedWritePos += this.accumulatedRunLength;
/* 197 */     this.accumulatedRunLength = 0;
/*     */   }
/*     */ 
/*     */   private void writeNonTerminating(int length)
/*     */   {
/* 203 */     this.accumulatedRunLength += length;
/*     */   }
/*     */ 
/*     */   private int readBit()
/*     */     throws IOException
/*     */   {
/* 211 */     if (this.bitPos >= 8)
/*     */     {
/* 213 */       readByte();
/* 214 */       if (this.bits < 0)
/*     */       {
/* 216 */         return -1;
/*     */       }
/*     */     }
/* 219 */     int bit = (this.bits & BIT_POS_MASKS[(this.bitPos++)]) == 0 ? 0 : 1;
/*     */ 
/* 221 */     return bit;
/*     */   }
/*     */ 
/*     */   private void readByte() throws IOException
/*     */   {
/* 226 */     this.bits = this.source.read();
/* 227 */     this.bitPos = 0;
/*     */   }
/*     */ 
/*     */   private static void buildLookupTree()
/*     */   {
/* 234 */     buildUpTerminating(WHITE_TERMINATING, WHITE_LOOKUP_TREE_ROOT, true);
/* 235 */     buildUpTerminating(BLACK_TERMINATING, BLACK_LOOKUP_TREE_ROOT, false);
/* 236 */     buildUpMakeUp(WHITE_MAKE_UP, WHITE_LOOKUP_TREE_ROOT);
/* 237 */     buildUpMakeUp(BLACK_MAKE_UP, BLACK_LOOKUP_TREE_ROOT);
/* 238 */     buildUpMakeUpLong(LONG_MAKE_UP, WHITE_LOOKUP_TREE_ROOT);
/* 239 */     buildUpMakeUpLong(LONG_MAKE_UP, BLACK_LOOKUP_TREE_ROOT);
/* 240 */     LookupTreeNode eolNode = new EndOfLineTreeNode(null);
/* 241 */     addLookupTreeNode((short)2816, WHITE_LOOKUP_TREE_ROOT, eolNode);
/* 242 */     addLookupTreeNode((short)2816, BLACK_LOOKUP_TREE_ROOT, eolNode);
/*     */   }
/*     */ 
/*     */   private static void buildUpTerminating(short[] codes, NonLeafLookupTreeNode root, boolean white)
/*     */   {
/* 247 */     int len = 0; for (int c = codes.length; len < c; len++)
/*     */     {
/* 249 */       LookupTreeNode leaf = new RunLengthTreeNode(white ? 0 : 1, len);
/* 250 */       addLookupTreeNode(codes[len], root, leaf);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void buildUpMakeUp(short[] codes, NonLeafLookupTreeNode root)
/*     */   {
/* 256 */     int len = 0; for (int c = codes.length; len < c; len++)
/*     */     {
/* 258 */       LookupTreeNode leaf = new MakeUpTreeNode((len + 1) * 64);
/* 259 */       addLookupTreeNode(codes[len], root, leaf);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void buildUpMakeUpLong(short[] codes, NonLeafLookupTreeNode root)
/*     */   {
/* 265 */     int len = 0; for (int c = codes.length; len < c; len++)
/*     */     {
/* 267 */       LookupTreeNode leaf = new MakeUpTreeNode((len + 28) * 64);
/* 268 */       addLookupTreeNode(codes[len], root, leaf);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void addLookupTreeNode(short code, NonLeafLookupTreeNode root, LookupTreeNode leaf)
/*     */   {
/* 275 */     int codeLength = code >> 8;
/* 276 */     int pattern = code & 0xFF;
/* 277 */     NonLeafLookupTreeNode node = root;
/* 278 */     for (int p = codeLength - 1; p > 0; p--)
/*     */     {
/* 280 */       int bit = pattern >> p & 0x1;
/* 281 */       LookupTreeNode child = node.get(bit);
/* 282 */       if (child == null)
/*     */       {
/* 284 */         child = new NonLeafLookupTreeNode(null);
/* 285 */         node.set(bit, child);
/*     */       }
/* 287 */       if ((child instanceof NonLeafLookupTreeNode))
/*     */       {
/* 289 */         node = (NonLeafLookupTreeNode)child;
/*     */       }
/*     */       else
/*     */       {
/* 293 */         throw new IllegalStateException("NonLeafLookupTreeNode expected, was " + child.getClass().getName());
/*     */       }
/*     */     }
/*     */ 
/* 297 */     int bit = pattern & 0x1;
/* 298 */     if (node.get(bit) != null)
/*     */     {
/* 300 */       throw new IllegalStateException("Two codes conflicting in lookup tree");
/*     */     }
/* 302 */     node.set(bit, leaf);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  60 */     buildLookupTree();
/*     */   }
/*     */ 
/*     */   private static class EndOfLineTreeNode extends CCITTFaxG31DDecodeInputStream.LookupTreeNode
/*     */     implements CCITTFaxG31DDecodeInputStream.CodeWord
/*     */   {
/*     */     private EndOfLineTreeNode()
/*     */     {
/* 436 */       super();
/*     */     }
/*     */ 
/*     */     public CCITTFaxG31DDecodeInputStream.CodeWord getNextCodeWord(CCITTFaxG31DDecodeInputStream decoder)
/*     */       throws IOException
/*     */     {
/*     */       int bit;
/*     */       do
/* 444 */         bit = decoder.readBit();
/* 445 */       while (bit == 0);
/* 446 */       if (bit < 0)
/*     */       {
/* 448 */         return null;
/*     */       }
/* 450 */       return this;
/*     */     }
/*     */ 
/*     */     public int execute(CCITTFaxG31DDecodeInputStream decoder)
/*     */       throws IOException
/*     */     {
/* 456 */       return 0;
/*     */     }
/*     */ 
/*     */     public int getType()
/*     */     {
/* 461 */       return -2;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 466 */       return "EOL";
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class MakeUpTreeNode extends CCITTFaxG31DDecodeInputStream.LookupTreeNode
/*     */     implements CCITTFaxG31DDecodeInputStream.CodeWord
/*     */   {
/*     */     private final int length;
/*     */ 
/*     */     public MakeUpTreeNode(int length)
/*     */     {
/* 408 */       super();
/* 409 */       this.length = length;
/*     */     }
/*     */ 
/*     */     public CCITTFaxG31DDecodeInputStream.CodeWord getNextCodeWord(CCITTFaxG31DDecodeInputStream decoder) throws IOException
/*     */     {
/* 414 */       return this;
/*     */     }
/*     */ 
/*     */     public int execute(CCITTFaxG31DDecodeInputStream decoder) throws IOException
/*     */     {
/* 419 */       decoder.writeNonTerminating(this.length);
/* 420 */       return this.length;
/*     */     }
/*     */ 
/*     */     public int getType()
/*     */     {
/* 425 */       return 0;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 430 */       return "Make up code for length " + this.length;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class RunLengthTreeNode extends CCITTFaxG31DDecodeInputStream.LookupTreeNode
/*     */     implements CCITTFaxG31DDecodeInputStream.CodeWord
/*     */   {
/*     */     private final int bit;
/*     */     private final int length;
/*     */ 
/*     */     public RunLengthTreeNode(int bit, int length)
/*     */     {
/* 373 */       super();
/* 374 */       this.bit = bit;
/* 375 */       this.length = length;
/*     */     }
/*     */ 
/*     */     public CCITTFaxG31DDecodeInputStream.CodeWord getNextCodeWord(CCITTFaxG31DDecodeInputStream decoder) throws IOException
/*     */     {
/* 380 */       return this;
/*     */     }
/*     */ 
/*     */     public int execute(CCITTFaxG31DDecodeInputStream decoder)
/*     */     {
/* 385 */       decoder.writeRun(this.bit, this.length);
/* 386 */       return this.length;
/*     */     }
/*     */ 
/*     */     public int getType()
/*     */     {
/* 391 */       return 0;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 396 */       return "Run Length for " + this.length + " bits of " + (this.bit == 0 ? "white" : "black");
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class NonLeafLookupTreeNode extends CCITTFaxG31DDecodeInputStream.LookupTreeNode
/*     */   {
/*     */     private CCITTFaxG31DDecodeInputStream.LookupTreeNode zero;
/*     */     private CCITTFaxG31DDecodeInputStream.LookupTreeNode one;
/*     */ 
/*     */     private NonLeafLookupTreeNode()
/*     */     {
/* 324 */       super();
/*     */     }
/*     */ 
/*     */     public void set(int bit, CCITTFaxG31DDecodeInputStream.LookupTreeNode node)
/*     */     {
/* 332 */       if (bit == 0)
/*     */       {
/* 334 */         this.zero = node;
/*     */       }
/*     */       else
/*     */       {
/* 338 */         this.one = node;
/*     */       }
/*     */     }
/*     */ 
/*     */     public CCITTFaxG31DDecodeInputStream.LookupTreeNode get(int bit)
/*     */     {
/* 344 */       return bit == 0 ? this.zero : this.one;
/*     */     }
/*     */ 
/*     */     public CCITTFaxG31DDecodeInputStream.CodeWord getNextCodeWord(CCITTFaxG31DDecodeInputStream decoder)
/*     */       throws IOException
/*     */     {
/* 350 */       int bit = decoder.readBit();
/* 351 */       if (bit < 0)
/*     */       {
/* 353 */         return null;
/*     */       }
/* 355 */       CCITTFaxG31DDecodeInputStream.LookupTreeNode node = get(bit);
/* 356 */       if (node != null)
/*     */       {
/* 358 */         return node.getNextCodeWord(decoder);
/*     */       }
/* 360 */       throw new IOException("Invalid code word encountered");
/*     */     }
/*     */   }
/*     */ 
/*     */   private static abstract interface CodeWord
/*     */   {
/*     */     public abstract int getType();
/*     */ 
/*     */     public abstract int execute(CCITTFaxG31DDecodeInputStream paramCCITTFaxG31DDecodeInputStream)
/*     */       throws IOException;
/*     */   }
/*     */ 
/*     */   private static abstract class LookupTreeNode
/*     */   {
/*     */     public abstract CCITTFaxG31DDecodeInputStream.CodeWord getNextCodeWord(CCITTFaxG31DDecodeInputStream paramCCITTFaxG31DDecodeInputStream)
/*     */       throws IOException;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.ccitt.CCITTFaxG31DDecodeInputStream
 * JD-Core Version:    0.6.2
 */