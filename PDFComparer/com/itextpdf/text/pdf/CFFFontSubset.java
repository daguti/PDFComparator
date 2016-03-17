/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ 
/*      */ public class CFFFontSubset extends CFFFont
/*      */ {
/*   69 */   static final String[] SubrsFunctions = { "RESERVED_0", "hstem", "RESERVED_2", "vstem", "vmoveto", "rlineto", "hlineto", "vlineto", "rrcurveto", "RESERVED_9", "callsubr", "return", "escape", "RESERVED_13", "endchar", "RESERVED_15", "RESERVED_16", "RESERVED_17", "hstemhm", "hintmask", "cntrmask", "rmoveto", "hmoveto", "vstemhm", "rcurveline", "rlinecurve", "vvcurveto", "hhcurveto", "shortint", "callgsubr", "vhcurveto", "hvcurveto" };
/*      */ 
/*   79 */   static final String[] SubrsEscapeFuncs = { "RESERVED_0", "RESERVED_1", "RESERVED_2", "and", "or", "not", "RESERVED_6", "RESERVED_7", "RESERVED_8", "abs", "add", "sub", "div", "RESERVED_13", "neg", "eq", "RESERVED_16", "RESERVED_17", "drop", "RESERVED_19", "put", "get", "ifelse", "random", "mul", "RESERVED_25", "sqrt", "dup", "exch", "index", "roll", "RESERVED_31", "RESERVED_32", "RESERVED_33", "hflex", "flex", "hflex1", "flex1", "RESERVED_REST" };
/*      */   static final byte ENDCHAR_OP = 14;
/*      */   static final byte RETURN_OP = 11;
/*      */   HashMap<Integer, int[]> GlyphsUsed;
/*      */   ArrayList<Integer> glyphsInList;
/*  105 */   HashSet<Integer> FDArrayUsed = new HashSet();
/*      */   HashMap<Integer, int[]>[] hSubrsUsed;
/*      */   ArrayList<Integer>[] lSubrsUsed;
/*  117 */   HashMap<Integer, int[]> hGSubrsUsed = new HashMap();
/*      */ 
/*  121 */   ArrayList<Integer> lGSubrsUsed = new ArrayList();
/*      */ 
/*  125 */   HashMap<Integer, int[]> hSubrsUsedNonCID = new HashMap();
/*      */ 
/*  129 */   ArrayList<Integer> lSubrsUsedNonCID = new ArrayList();
/*      */   byte[][] NewLSubrsIndex;
/*      */   byte[] NewSubrsIndexNonCID;
/*      */   byte[] NewGSubrsIndex;
/*      */   byte[] NewCharStringsIndex;
/*  150 */   int GBias = 0;
/*      */   LinkedList<CFFFont.Item> OutputList;
/*  160 */   int NumOfHints = 0;
/*      */ 
/*      */   public CFFFontSubset(RandomAccessFileOrArray rf, HashMap<Integer, int[]> GlyphsUsed)
/*      */   {
/*  170 */     super(rf);
/*  171 */     this.GlyphsUsed = GlyphsUsed;
/*      */ 
/*  173 */     this.glyphsInList = new ArrayList(GlyphsUsed.keySet());
/*      */ 
/*  176 */     for (int i = 0; i < this.fonts.length; i++)
/*      */     {
/*  179 */       seek(this.fonts[i].charstringsOffset);
/*  180 */       this.fonts[i].nglyphs = getCard16();
/*      */ 
/*  183 */       seek(this.stringIndexOffset);
/*  184 */       this.fonts[i].nstrings = (getCard16() + standardStrings.length);
/*      */ 
/*  187 */       this.fonts[i].charstringsOffsets = getIndex(this.fonts[i].charstringsOffset);
/*      */ 
/*  190 */       if (this.fonts[i].fdselectOffset >= 0)
/*      */       {
/*  193 */         readFDSelect(i);
/*      */ 
/*  195 */         BuildFDArrayUsed(i);
/*      */       }
/*  197 */       if (this.fonts[i].isCID)
/*      */       {
/*  199 */         ReadFDArray(i);
/*      */       }
/*  201 */       this.fonts[i].CharsetLength = CountCharset(this.fonts[i].charsetOffset, this.fonts[i].nglyphs);
/*      */     }
/*      */   }
/*      */ 
/*      */   int CountCharset(int Offset, int NumofGlyphs)
/*      */   {
/*  213 */     int Length = 0;
/*  214 */     seek(Offset);
/*      */ 
/*  216 */     int format = getCard8();
/*      */ 
/*  218 */     switch (format) {
/*      */     case 0:
/*  220 */       Length = 1 + 2 * NumofGlyphs;
/*  221 */       break;
/*      */     case 1:
/*  223 */       Length = 1 + 3 * CountRange(NumofGlyphs, 1);
/*  224 */       break;
/*      */     case 2:
/*  226 */       Length = 1 + 4 * CountRange(NumofGlyphs, 2);
/*  227 */       break;
/*      */     }
/*      */ 
/*  231 */     return Length;
/*      */   }
/*      */ 
/*      */   int CountRange(int NumofGlyphs, int Type)
/*      */   {
/*  241 */     int num = 0;
/*      */ 
/*  243 */     int i = 1;
/*  244 */     while (i < NumofGlyphs) {
/*  245 */       num++;
/*  246 */       char Sid = getCard16();
/*      */       int nLeft;
/*      */       int nLeft;
/*  247 */       if (Type == 1)
/*  248 */         nLeft = getCard8();
/*      */       else
/*  250 */         nLeft = getCard16();
/*  251 */       i += nLeft + 1;
/*      */     }
/*  253 */     return num;
/*      */   }
/*      */ 
/*      */   protected void readFDSelect(int Font)
/*      */   {
/*  264 */     int NumOfGlyphs = this.fonts[Font].nglyphs;
/*  265 */     int[] FDSelect = new int[NumOfGlyphs];
/*      */ 
/*  267 */     seek(this.fonts[Font].fdselectOffset);
/*      */ 
/*  269 */     this.fonts[Font].FDSelectFormat = getCard8();
/*      */ 
/*  271 */     switch (this.fonts[Font].FDSelectFormat)
/*      */     {
/*      */     case 0:
/*  275 */       for (int i = 0; i < NumOfGlyphs; i++)
/*      */       {
/*  277 */         FDSelect[i] = getCard8();
/*      */       }
/*      */ 
/*  281 */       this.fonts[Font].FDSelectLength = (this.fonts[Font].nglyphs + 1);
/*  282 */       break;
/*      */     case 3:
/*  286 */       int nRanges = getCard16();
/*  287 */       int l = 0;
/*      */ 
/*  289 */       int first = getCard16();
/*  290 */       for (int i = 0; i < nRanges; i++)
/*      */       {
/*  293 */         int fd = getCard8();
/*      */ 
/*  295 */         int last = getCard16();
/*      */ 
/*  297 */         int steps = last - first;
/*  298 */         for (int k = 0; k < steps; k++)
/*      */         {
/*  300 */           FDSelect[l] = fd;
/*  301 */           l++;
/*      */         }
/*      */ 
/*  304 */         first = last;
/*      */       }
/*      */ 
/*  307 */       this.fonts[Font].FDSelectLength = (3 + nRanges * 3 + 2);
/*  308 */       break;
/*      */     }
/*      */ 
/*  313 */     this.fonts[Font].FDSelect = FDSelect;
/*      */   }
/*      */ 
/*      */   protected void BuildFDArrayUsed(int Font)
/*      */   {
/*  322 */     int[] FDSelect = this.fonts[Font].FDSelect;
/*      */ 
/*  324 */     for (int i = 0; i < this.glyphsInList.size(); i++)
/*      */     {
/*  327 */       int glyph = ((Integer)this.glyphsInList.get(i)).intValue();
/*      */ 
/*  329 */       int FD = FDSelect[glyph];
/*      */ 
/*  331 */       this.FDArrayUsed.add(Integer.valueOf(FD));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void ReadFDArray(int Font)
/*      */   {
/*  341 */     seek(this.fonts[Font].fdarrayOffset);
/*  342 */     this.fonts[Font].FDArrayCount = getCard16();
/*  343 */     this.fonts[Font].FDArrayOffsize = getCard8();
/*      */ 
/*  346 */     if (this.fonts[Font].FDArrayOffsize < 4)
/*  347 */       this.fonts[Font].FDArrayOffsize += 1;
/*  348 */     this.fonts[Font].FDArrayOffsets = getIndex(this.fonts[Font].fdarrayOffset);
/*      */   }
/*      */ 
/*      */   public byte[] Process(String fontName)
/*      */     throws IOException
/*      */   {
/*      */     try
/*      */     {
/*  363 */       this.buf.reOpen();
/*      */ 
/*  366 */       for (int j = 0; (j < this.fonts.length) && 
/*  367 */         (!fontName.equals(this.fonts[j].name)); j++);
/*  368 */       if (j == this.fonts.length) return null;
/*      */ 
/*  371 */       if (this.gsubrIndexOffset >= 0) {
/*  372 */         this.GBias = CalcBias(this.gsubrIndexOffset, j);
/*      */       }
/*      */ 
/*  375 */       BuildNewCharString(j);
/*      */ 
/*  377 */       BuildNewLGSubrs(j);
/*      */ 
/*  379 */       byte[] Ret = BuildNewFile(j);
/*  380 */       return Ret;
/*      */     }
/*      */     finally {
/*      */       try {
/*  384 */         this.buf.close();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected int CalcBias(int Offset, int Font)
/*      */   {
/*  401 */     seek(Offset);
/*  402 */     int nSubrs = getCard16();
/*      */ 
/*  404 */     if (this.fonts[Font].CharstringType == 1) {
/*  405 */       return 0;
/*      */     }
/*  407 */     if (nSubrs < 1240)
/*  408 */       return 107;
/*  409 */     if (nSubrs < 33900) {
/*  410 */       return 1131;
/*      */     }
/*  412 */     return 32768;
/*      */   }
/*      */ 
/*      */   protected void BuildNewCharString(int FontIndex)
/*      */     throws IOException
/*      */   {
/*  422 */     this.NewCharStringsIndex = BuildNewIndex(this.fonts[FontIndex].charstringsOffsets, this.GlyphsUsed, (byte)14);
/*      */   }
/*      */ 
/*      */   protected void BuildNewLGSubrs(int Font)
/*      */     throws IOException
/*      */   {
/*  436 */     if (this.fonts[Font].isCID)
/*      */     {
/*  440 */       this.hSubrsUsed = new HashMap[this.fonts[Font].fdprivateOffsets.length];
/*  441 */       this.lSubrsUsed = new ArrayList[this.fonts[Font].fdprivateOffsets.length];
/*      */ 
/*  443 */       this.NewLSubrsIndex = new byte[this.fonts[Font].fdprivateOffsets.length][];
/*      */ 
/*  445 */       this.fonts[Font].PrivateSubrsOffset = new int[this.fonts[Font].fdprivateOffsets.length];
/*      */ 
/*  447 */       this.fonts[Font].PrivateSubrsOffsetsArray = new int[this.fonts[Font].fdprivateOffsets.length][];
/*      */ 
/*  450 */       ArrayList FDInList = new ArrayList(this.FDArrayUsed);
/*      */ 
/*  452 */       for (int j = 0; j < FDInList.size(); j++)
/*      */       {
/*  455 */         int FD = ((Integer)FDInList.get(j)).intValue();
/*  456 */         this.hSubrsUsed[FD] = new HashMap();
/*  457 */         this.lSubrsUsed[FD] = new ArrayList();
/*      */ 
/*  460 */         BuildFDSubrsOffsets(Font, FD);
/*      */ 
/*  462 */         if (this.fonts[Font].PrivateSubrsOffset[FD] >= 0)
/*      */         {
/*  466 */           BuildSubrUsed(Font, FD, this.fonts[Font].PrivateSubrsOffset[FD], this.fonts[Font].PrivateSubrsOffsetsArray[FD], this.hSubrsUsed[FD], this.lSubrsUsed[FD]);
/*      */ 
/*  468 */           this.NewLSubrsIndex[FD] = BuildNewIndex(this.fonts[Font].PrivateSubrsOffsetsArray[FD], this.hSubrsUsed[FD], 11);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*  473 */     else if (this.fonts[Font].privateSubrs >= 0)
/*      */     {
/*  476 */       this.fonts[Font].SubrsOffsets = getIndex(this.fonts[Font].privateSubrs);
/*      */ 
/*  479 */       BuildSubrUsed(Font, -1, this.fonts[Font].privateSubrs, this.fonts[Font].SubrsOffsets, this.hSubrsUsedNonCID, this.lSubrsUsedNonCID);
/*      */     }
/*      */ 
/*  483 */     BuildGSubrsUsed(Font);
/*  484 */     if (this.fonts[Font].privateSubrs >= 0)
/*      */     {
/*  486 */       this.NewSubrsIndexNonCID = BuildNewIndex(this.fonts[Font].SubrsOffsets, this.hSubrsUsedNonCID, (byte)11);
/*      */     }
/*  488 */     this.NewGSubrsIndex = BuildNewIndex(this.gsubrOffsets, this.hGSubrsUsed, (byte)11);
/*      */   }
/*      */ 
/*      */   protected void BuildFDSubrsOffsets(int Font, int FD)
/*      */   {
/*  500 */     this.fonts[Font].PrivateSubrsOffset[FD] = -1;
/*      */ 
/*  502 */     seek(this.fonts[Font].fdprivateOffsets[FD]);
/*      */ 
/*  504 */     while (getPosition() < this.fonts[Font].fdprivateOffsets[FD] + this.fonts[Font].fdprivateLengths[FD])
/*      */     {
/*  506 */       getDictItem();
/*      */ 
/*  508 */       if (this.key == "Subrs") {
/*  509 */         this.fonts[Font].PrivateSubrsOffset[FD] = (((Integer)this.args[0]).intValue() + this.fonts[Font].fdprivateOffsets[FD]);
/*      */       }
/*      */     }
/*  512 */     if (this.fonts[Font].PrivateSubrsOffset[FD] >= 0)
/*  513 */       this.fonts[Font].PrivateSubrsOffsetsArray[FD] = getIndex(this.fonts[Font].PrivateSubrsOffset[FD]);
/*      */   }
/*      */ 
/*      */   protected void BuildSubrUsed(int Font, int FD, int SubrOffset, int[] SubrsOffsets, HashMap<Integer, int[]> hSubr, ArrayList<Integer> lSubr)
/*      */   {
/*  531 */     int LBias = CalcBias(SubrOffset, Font);
/*      */ 
/*  534 */     for (int i = 0; i < this.glyphsInList.size(); i++)
/*      */     {
/*  536 */       int glyph = ((Integer)this.glyphsInList.get(i)).intValue();
/*  537 */       int Start = this.fonts[Font].charstringsOffsets[glyph];
/*  538 */       int End = this.fonts[Font].charstringsOffsets[(glyph + 1)];
/*      */ 
/*  541 */       if (FD >= 0)
/*      */       {
/*  543 */         EmptyStack();
/*  544 */         this.NumOfHints = 0;
/*      */ 
/*  546 */         int GlyphFD = this.fonts[Font].FDSelect[glyph];
/*      */ 
/*  548 */         if (GlyphFD == FD)
/*      */         {
/*  550 */           ReadASubr(Start, End, this.GBias, LBias, hSubr, lSubr, SubrsOffsets);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  555 */         ReadASubr(Start, End, this.GBias, LBias, hSubr, lSubr, SubrsOffsets);
/*      */       }
/*      */     }
/*  558 */     for (int i = 0; i < lSubr.size(); i++)
/*      */     {
/*  561 */       int Subr = ((Integer)lSubr.get(i)).intValue();
/*      */ 
/*  563 */       if ((Subr < SubrsOffsets.length - 1) && (Subr >= 0))
/*      */       {
/*  566 */         int Start = SubrsOffsets[Subr];
/*  567 */         int End = SubrsOffsets[(Subr + 1)];
/*  568 */         ReadASubr(Start, End, this.GBias, LBias, hSubr, lSubr, SubrsOffsets);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void BuildGSubrsUsed(int Font)
/*      */   {
/*  580 */     int LBias = 0;
/*  581 */     int SizeOfNonCIDSubrsUsed = 0;
/*  582 */     if (this.fonts[Font].privateSubrs >= 0)
/*      */     {
/*  584 */       LBias = CalcBias(this.fonts[Font].privateSubrs, Font);
/*  585 */       SizeOfNonCIDSubrsUsed = this.lSubrsUsedNonCID.size();
/*      */     }
/*      */ 
/*  589 */     for (int i = 0; i < this.lGSubrsUsed.size(); i++)
/*      */     {
/*  592 */       int Subr = ((Integer)this.lGSubrsUsed.get(i)).intValue();
/*  593 */       if ((Subr < this.gsubrOffsets.length - 1) && (Subr >= 0))
/*      */       {
/*  596 */         int Start = this.gsubrOffsets[Subr];
/*  597 */         int End = this.gsubrOffsets[(Subr + 1)];
/*      */ 
/*  599 */         if (this.fonts[Font].isCID) {
/*  600 */           ReadASubr(Start, End, this.GBias, 0, this.hGSubrsUsed, this.lGSubrsUsed, null);
/*      */         }
/*      */         else {
/*  603 */           ReadASubr(Start, End, this.GBias, LBias, this.hSubrsUsedNonCID, this.lSubrsUsedNonCID, this.fonts[Font].SubrsOffsets);
/*  604 */           if (SizeOfNonCIDSubrsUsed < this.lSubrsUsedNonCID.size())
/*      */           {
/*  606 */             for (int j = SizeOfNonCIDSubrsUsed; j < this.lSubrsUsedNonCID.size(); j++)
/*      */             {
/*  609 */               int LSubr = ((Integer)this.lSubrsUsedNonCID.get(j)).intValue();
/*  610 */               if ((LSubr < this.fonts[Font].SubrsOffsets.length - 1) && (LSubr >= 0))
/*      */               {
/*  613 */                 int LStart = this.fonts[Font].SubrsOffsets[LSubr];
/*  614 */                 int LEnd = this.fonts[Font].SubrsOffsets[(LSubr + 1)];
/*  615 */                 ReadASubr(LStart, LEnd, this.GBias, LBias, this.hSubrsUsedNonCID, this.lSubrsUsedNonCID, this.fonts[Font].SubrsOffsets);
/*      */               }
/*      */             }
/*  618 */             SizeOfNonCIDSubrsUsed = this.lSubrsUsedNonCID.size();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void ReadASubr(int begin, int end, int GBias, int LBias, HashMap<Integer, int[]> hSubr, ArrayList<Integer> lSubr, int[] LSubrsOffsets)
/*      */   {
/*  639 */     EmptyStack();
/*  640 */     this.NumOfHints = 0;
/*      */ 
/*  642 */     seek(begin);
/*  643 */     while (getPosition() < end)
/*      */     {
/*  646 */       ReadCommand();
/*  647 */       int pos = getPosition();
/*  648 */       Object TopElement = null;
/*  649 */       if (this.arg_count > 0)
/*  650 */         TopElement = this.args[(this.arg_count - 1)];
/*  651 */       int NumOfArgs = this.arg_count;
/*      */ 
/*  653 */       HandelStack();
/*      */ 
/*  655 */       if (this.key == "callsubr")
/*      */       {
/*  658 */         if (NumOfArgs > 0)
/*      */         {
/*  661 */           int Subr = ((Integer)TopElement).intValue() + LBias;
/*      */ 
/*  663 */           if (!hSubr.containsKey(Integer.valueOf(Subr)))
/*      */           {
/*  665 */             hSubr.put(Integer.valueOf(Subr), null);
/*  666 */             lSubr.add(Integer.valueOf(Subr));
/*      */           }
/*  668 */           CalcHints(LSubrsOffsets[Subr], LSubrsOffsets[(Subr + 1)], LBias, GBias, LSubrsOffsets);
/*  669 */           seek(pos);
/*      */         }
/*      */ 
/*      */       }
/*  673 */       else if (this.key == "callgsubr")
/*      */       {
/*  676 */         if (NumOfArgs > 0)
/*      */         {
/*  679 */           int Subr = ((Integer)TopElement).intValue() + GBias;
/*      */ 
/*  681 */           if (!this.hGSubrsUsed.containsKey(Integer.valueOf(Subr)))
/*      */           {
/*  683 */             this.hGSubrsUsed.put(Integer.valueOf(Subr), null);
/*  684 */             this.lGSubrsUsed.add(Integer.valueOf(Subr));
/*      */           }
/*  686 */           CalcHints(this.gsubrOffsets[Subr], this.gsubrOffsets[(Subr + 1)], LBias, GBias, LSubrsOffsets);
/*  687 */           seek(pos);
/*      */         }
/*      */ 
/*      */       }
/*  691 */       else if ((this.key == "hstem") || (this.key == "vstem") || (this.key == "hstemhm") || (this.key == "vstemhm"))
/*      */       {
/*  693 */         this.NumOfHints += NumOfArgs / 2;
/*      */       }
/*  695 */       else if ((this.key == "hintmask") || (this.key == "cntrmask"))
/*      */       {
/*  698 */         int SizeOfMask = this.NumOfHints / 8;
/*  699 */         if ((this.NumOfHints % 8 != 0) || (SizeOfMask == 0)) {
/*  700 */           SizeOfMask++;
/*      */         }
/*  702 */         for (int i = 0; i < SizeOfMask; i++)
/*  703 */           getCard8();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void HandelStack()
/*      */   {
/*  715 */     int StackHandel = StackOpp();
/*  716 */     if (StackHandel < 2)
/*      */     {
/*  719 */       if (StackHandel == 1) {
/*  720 */         PushStack();
/*      */       }
/*      */       else
/*      */       {
/*  725 */         StackHandel *= -1;
/*  726 */         for (int i = 0; i < StackHandel; i++) {
/*  727 */           PopStack();
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  733 */       EmptyStack();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected int StackOpp()
/*      */   {
/*  742 */     if (this.key == "ifelse")
/*  743 */       return -3;
/*  744 */     if ((this.key == "roll") || (this.key == "put"))
/*  745 */       return -2;
/*  746 */     if ((this.key == "callsubr") || (this.key == "callgsubr") || (this.key == "add") || (this.key == "sub") || (this.key == "div") || (this.key == "mul") || (this.key == "drop") || (this.key == "and") || (this.key == "or") || (this.key == "eq"))
/*      */     {
/*  749 */       return -1;
/*  750 */     }if ((this.key == "abs") || (this.key == "neg") || (this.key == "sqrt") || (this.key == "exch") || (this.key == "index") || (this.key == "get") || (this.key == "not") || (this.key == "return"))
/*      */     {
/*  752 */       return 0;
/*  753 */     }if ((this.key == "random") || (this.key == "dup"))
/*  754 */       return 1;
/*  755 */     return 2;
/*      */   }
/*      */ 
/*      */   protected void EmptyStack()
/*      */   {
/*  765 */     for (int i = 0; i < this.arg_count; i++) this.args[i] = null;
/*  766 */     this.arg_count = 0;
/*      */   }
/*      */ 
/*      */   protected void PopStack()
/*      */   {
/*  775 */     if (this.arg_count > 0)
/*      */     {
/*  777 */       this.args[(this.arg_count - 1)] = null;
/*  778 */       this.arg_count -= 1;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void PushStack()
/*      */   {
/*  788 */     this.arg_count += 1;
/*      */   }
/*      */ 
/*      */   protected void ReadCommand()
/*      */   {
/*  796 */     this.key = null;
/*  797 */     boolean gotKey = false;
/*      */ 
/*  799 */     while (!gotKey)
/*      */     {
/*  801 */       char b0 = getCard8();
/*      */ 
/*  803 */       if (b0 == '\034')
/*      */       {
/*  805 */         int first = getCard8();
/*  806 */         int second = getCard8();
/*  807 */         this.args[this.arg_count] = Integer.valueOf(first << 8 | second);
/*  808 */         this.arg_count += 1;
/*      */       }
/*  811 */       else if ((b0 >= ' ') && (b0 <= 'ö'))
/*      */       {
/*  813 */         this.args[this.arg_count] = Integer.valueOf(b0 - '');
/*  814 */         this.arg_count += 1;
/*      */       }
/*  817 */       else if ((b0 >= '÷') && (b0 <= 'ú'))
/*      */       {
/*  819 */         int w = getCard8();
/*  820 */         this.args[this.arg_count] = Integer.valueOf((b0 - '÷') * 256 + w + 108);
/*  821 */         this.arg_count += 1;
/*      */       }
/*  824 */       else if ((b0 >= 'û') && (b0 <= 'þ'))
/*      */       {
/*  826 */         int w = getCard8();
/*  827 */         this.args[this.arg_count] = Integer.valueOf(-(b0 - 'û') * 256 - w - 108);
/*  828 */         this.arg_count += 1;
/*      */       }
/*  831 */       else if (b0 == 'ÿ')
/*      */       {
/*  833 */         int first = getCard8();
/*  834 */         int second = getCard8();
/*  835 */         int third = getCard8();
/*  836 */         int fourth = getCard8();
/*  837 */         this.args[this.arg_count] = Integer.valueOf(first << 24 | second << 16 | third << 8 | fourth);
/*  838 */         this.arg_count += 1;
/*      */       }
/*  841 */       else if ((b0 <= '\037') && (b0 != '\034'))
/*      */       {
/*  843 */         gotKey = true;
/*      */ 
/*  846 */         if (b0 == '\f')
/*      */         {
/*  848 */           int b1 = getCard8();
/*  849 */           if (b1 > SubrsEscapeFuncs.length - 1)
/*  850 */             b1 = SubrsEscapeFuncs.length - 1;
/*  851 */           this.key = SubrsEscapeFuncs[b1];
/*      */         }
/*      */         else {
/*  854 */           this.key = SubrsFunctions[b0];
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected int CalcHints(int begin, int end, int LBias, int GBias, int[] LSubrsOffsets)
/*      */   {
/*  873 */     seek(begin);
/*  874 */     while (getPosition() < end)
/*      */     {
/*  877 */       ReadCommand();
/*  878 */       int pos = getPosition();
/*  879 */       Object TopElement = null;
/*  880 */       if (this.arg_count > 0)
/*  881 */         TopElement = this.args[(this.arg_count - 1)];
/*  882 */       int NumOfArgs = this.arg_count;
/*      */ 
/*  884 */       HandelStack();
/*      */ 
/*  886 */       if (this.key == "callsubr")
/*      */       {
/*  888 */         if (NumOfArgs > 0)
/*      */         {
/*  890 */           int Subr = ((Integer)TopElement).intValue() + LBias;
/*  891 */           CalcHints(LSubrsOffsets[Subr], LSubrsOffsets[(Subr + 1)], LBias, GBias, LSubrsOffsets);
/*  892 */           seek(pos);
/*      */         }
/*      */ 
/*      */       }
/*  896 */       else if (this.key == "callgsubr")
/*      */       {
/*  898 */         if (NumOfArgs > 0)
/*      */         {
/*  900 */           int Subr = ((Integer)TopElement).intValue() + GBias;
/*  901 */           CalcHints(this.gsubrOffsets[Subr], this.gsubrOffsets[(Subr + 1)], LBias, GBias, LSubrsOffsets);
/*  902 */           seek(pos);
/*      */         }
/*      */ 
/*      */       }
/*  906 */       else if ((this.key == "hstem") || (this.key == "vstem") || (this.key == "hstemhm") || (this.key == "vstemhm"))
/*      */       {
/*  908 */         this.NumOfHints += NumOfArgs / 2;
/*      */       }
/*  910 */       else if ((this.key == "hintmask") || (this.key == "cntrmask"))
/*      */       {
/*  913 */         int SizeOfMask = this.NumOfHints / 8;
/*  914 */         if ((this.NumOfHints % 8 != 0) || (SizeOfMask == 0)) {
/*  915 */           SizeOfMask++;
/*      */         }
/*  917 */         for (int i = 0; i < SizeOfMask; i++)
/*  918 */           getCard8();
/*      */       }
/*      */     }
/*  921 */     return this.NumOfHints;
/*      */   }
/*      */ 
/*      */   protected byte[] BuildNewIndex(int[] Offsets, HashMap<Integer, int[]> Used, byte OperatorForUnusedEntries)
/*      */     throws IOException
/*      */   {
/*  936 */     int unusedCount = 0;
/*  937 */     int Offset = 0;
/*  938 */     int[] NewOffsets = new int[Offsets.length];
/*      */ 
/*  940 */     for (int i = 0; i < Offsets.length; i++)
/*      */     {
/*  942 */       NewOffsets[i] = Offset;
/*      */ 
/*  945 */       if (Used.containsKey(Integer.valueOf(i))) {
/*  946 */         Offset += Offsets[(i + 1)] - Offsets[i];
/*      */       }
/*      */       else {
/*  949 */         unusedCount++;
/*      */       }
/*      */     }
/*      */ 
/*  953 */     byte[] NewObjects = new byte[Offset + unusedCount];
/*      */ 
/*  955 */     int unusedOffset = 0;
/*  956 */     for (int i = 0; i < Offsets.length - 1; i++)
/*      */     {
/*  958 */       int start = NewOffsets[i];
/*  959 */       int end = NewOffsets[(i + 1)];
/*  960 */       NewOffsets[i] = (start + unusedOffset);
/*      */ 
/*  963 */       if (start != end)
/*      */       {
/*  967 */         this.buf.seek(Offsets[i]);
/*      */ 
/*  969 */         this.buf.readFully(NewObjects, start + unusedOffset, end - start);
/*      */       } else {
/*  971 */         NewObjects[(start + unusedOffset)] = OperatorForUnusedEntries;
/*  972 */         unusedOffset++;
/*      */       }
/*      */     }
/*  975 */     NewOffsets[(Offsets.length - 1)] += unusedOffset;
/*      */ 
/*  977 */     return AssembleIndex(NewOffsets, NewObjects);
/*      */   }
/*      */ 
/*      */   protected byte[] AssembleIndex(int[] NewOffsets, byte[] NewObjects)
/*      */   {
/*  990 */     char Count = (char)(NewOffsets.length - 1);
/*      */ 
/*  992 */     int Size = NewOffsets[(NewOffsets.length - 1)];
/*      */     byte Offsize;
/*      */     byte Offsize;
/*  995 */     if (Size <= 255) { Offsize = 1; }
/*      */     else
/*      */     {
/*  996 */       byte Offsize;
/*  996 */       if (Size <= 65535) { Offsize = 2; }
/*      */       else
/*      */       {
/*  997 */         byte Offsize;
/*  997 */         if (Size <= 16777215) Offsize = 3; else
/*  998 */           Offsize = 4;
/*      */       }
/*      */     }
/* 1001 */     byte[] NewIndex = new byte[3 + Offsize * (Count + '\001') + NewObjects.length];
/*      */ 
/* 1003 */     int Place = 0;
/*      */ 
/* 1005 */     NewIndex[(Place++)] = ((byte)(Count >>> '\b' & 0xFF));
/* 1006 */     NewIndex[(Place++)] = ((byte)(Count >>> '\000' & 0xFF));
/*      */ 
/* 1008 */     NewIndex[(Place++)] = Offsize;
/*      */ 
/* 1010 */     for (int newOffset : NewOffsets)
/*      */     {
/* 1012 */       int Num = newOffset - NewOffsets[0] + 1;
/*      */ 
/* 1014 */       switch (Offsize) {
/*      */       case 4:
/* 1016 */         NewIndex[(Place++)] = ((byte)(Num >>> 24 & 0xFF));
/*      */       case 3:
/* 1018 */         NewIndex[(Place++)] = ((byte)(Num >>> 16 & 0xFF));
/*      */       case 2:
/* 1020 */         NewIndex[(Place++)] = ((byte)(Num >>> 8 & 0xFF));
/*      */       case 1:
/* 1022 */         NewIndex[(Place++)] = ((byte)(Num >>> 0 & 0xFF));
/*      */       }
/*      */     }
/*      */ 
/* 1026 */     for (byte newObject : NewObjects) {
/* 1027 */       NewIndex[(Place++)] = newObject;
/*      */     }
/*      */ 
/* 1030 */     return NewIndex;
/*      */   }
/*      */ 
/*      */   protected byte[] BuildNewFile(int Font)
/*      */   {
/* 1041 */     this.OutputList = new LinkedList();
/*      */ 
/* 1044 */     CopyHeader();
/*      */ 
/* 1047 */     BuildIndexHeader(1, 1, 1);
/* 1048 */     this.OutputList.addLast(new CFFFont.UInt8Item((char)(1 + this.fonts[Font].name.length())));
/* 1049 */     this.OutputList.addLast(new CFFFont.StringItem(this.fonts[Font].name));
/*      */ 
/* 1052 */     BuildIndexHeader(1, 2, 1);
/* 1053 */     CFFFont.OffsetItem topdictIndex1Ref = new CFFFont.IndexOffsetItem(2);
/* 1054 */     this.OutputList.addLast(topdictIndex1Ref);
/* 1055 */     CFFFont.IndexBaseItem topdictBase = new CFFFont.IndexBaseItem();
/* 1056 */     this.OutputList.addLast(topdictBase);
/*      */ 
/* 1059 */     CFFFont.OffsetItem charsetRef = new CFFFont.DictOffsetItem();
/* 1060 */     CFFFont.OffsetItem charstringsRef = new CFFFont.DictOffsetItem();
/* 1061 */     CFFFont.OffsetItem fdarrayRef = new CFFFont.DictOffsetItem();
/* 1062 */     CFFFont.OffsetItem fdselectRef = new CFFFont.DictOffsetItem();
/* 1063 */     CFFFont.OffsetItem privateRef = new CFFFont.DictOffsetItem();
/*      */ 
/* 1066 */     if (!this.fonts[Font].isCID)
/*      */     {
/* 1068 */       this.OutputList.addLast(new CFFFont.DictNumberItem(this.fonts[Font].nstrings));
/* 1069 */       this.OutputList.addLast(new CFFFont.DictNumberItem(this.fonts[Font].nstrings + 1));
/* 1070 */       this.OutputList.addLast(new CFFFont.DictNumberItem(0));
/* 1071 */       this.OutputList.addLast(new CFFFont.UInt8Item('\f'));
/* 1072 */       this.OutputList.addLast(new CFFFont.UInt8Item('\036'));
/*      */ 
/* 1074 */       this.OutputList.addLast(new CFFFont.DictNumberItem(this.fonts[Font].nglyphs));
/* 1075 */       this.OutputList.addLast(new CFFFont.UInt8Item('\f'));
/* 1076 */       this.OutputList.addLast(new CFFFont.UInt8Item('"'));
/*      */     }
/*      */ 
/* 1082 */     seek(this.topdictOffsets[Font]);
/*      */ 
/* 1084 */     while (getPosition() < this.topdictOffsets[(Font + 1)]) {
/* 1085 */       int p1 = getPosition();
/* 1086 */       getDictItem();
/* 1087 */       int p2 = getPosition();
/*      */ 
/* 1089 */       if ((this.key != "Encoding") && (this.key != "Private") && (this.key != "FDSelect") && (this.key != "FDArray") && (this.key != "charset") && (this.key != "CharStrings"))
/*      */       {
/* 1099 */         this.OutputList.add(new CFFFont.RangeItem(this.buf, p1, p2 - p1));
/*      */       }
/*      */     }
/*      */ 
/* 1103 */     CreateKeys(fdarrayRef, fdselectRef, charsetRef, charstringsRef);
/*      */ 
/* 1106 */     this.OutputList.addLast(new CFFFont.IndexMarkerItem(topdictIndex1Ref, topdictBase));
/*      */ 
/* 1110 */     if (this.fonts[Font].isCID) {
/* 1111 */       this.OutputList.addLast(getEntireIndexRange(this.stringIndexOffset));
/*      */     }
/*      */     else
/*      */     {
/* 1116 */       CreateNewStringIndex(Font);
/*      */     }
/*      */ 
/* 1119 */     this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(this.NewGSubrsIndex), 0, this.NewGSubrsIndex.length));
/*      */ 
/* 1123 */     if (this.fonts[Font].isCID)
/*      */     {
/* 1128 */       this.OutputList.addLast(new CFFFont.MarkerItem(fdselectRef));
/*      */ 
/* 1130 */       if (this.fonts[Font].fdselectOffset >= 0) {
/* 1131 */         this.OutputList.addLast(new CFFFont.RangeItem(this.buf, this.fonts[Font].fdselectOffset, this.fonts[Font].FDSelectLength));
/*      */       }
/*      */       else {
/* 1134 */         CreateFDSelect(fdselectRef, this.fonts[Font].nglyphs);
/*      */       }
/*      */ 
/* 1138 */       this.OutputList.addLast(new CFFFont.MarkerItem(charsetRef));
/* 1139 */       this.OutputList.addLast(new CFFFont.RangeItem(this.buf, this.fonts[Font].charsetOffset, this.fonts[Font].CharsetLength));
/*      */ 
/* 1143 */       if (this.fonts[Font].fdarrayOffset >= 0)
/*      */       {
/* 1146 */         this.OutputList.addLast(new CFFFont.MarkerItem(fdarrayRef));
/*      */ 
/* 1148 */         Reconstruct(Font);
/*      */       }
/*      */       else
/*      */       {
/* 1152 */         CreateFDArray(fdarrayRef, privateRef, Font);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1159 */       CreateFDSelect(fdselectRef, this.fonts[Font].nglyphs);
/*      */ 
/* 1161 */       CreateCharset(charsetRef, this.fonts[Font].nglyphs);
/*      */ 
/* 1163 */       CreateFDArray(fdarrayRef, privateRef, Font);
/*      */     }
/*      */ 
/* 1167 */     if (this.fonts[Font].privateOffset >= 0)
/*      */     {
/* 1170 */       CFFFont.IndexBaseItem PrivateBase = new CFFFont.IndexBaseItem();
/* 1171 */       this.OutputList.addLast(PrivateBase);
/* 1172 */       this.OutputList.addLast(new CFFFont.MarkerItem(privateRef));
/*      */ 
/* 1174 */       CFFFont.OffsetItem Subr = new CFFFont.DictOffsetItem();
/*      */ 
/* 1176 */       CreateNonCIDPrivate(Font, Subr);
/*      */ 
/* 1178 */       CreateNonCIDSubrs(Font, PrivateBase, Subr);
/*      */     }
/*      */ 
/* 1182 */     this.OutputList.addLast(new CFFFont.MarkerItem(charstringsRef));
/*      */ 
/* 1185 */     this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(this.NewCharStringsIndex), 0, this.NewCharStringsIndex.length));
/*      */ 
/* 1188 */     int[] currentOffset = new int[1];
/* 1189 */     currentOffset[0] = 0;
/*      */ 
/* 1191 */     Iterator listIter = this.OutputList.iterator();
/* 1192 */     while (listIter.hasNext()) {
/* 1193 */       CFFFont.Item item = (CFFFont.Item)listIter.next();
/* 1194 */       item.increment(currentOffset);
/*      */     }
/*      */ 
/* 1197 */     listIter = this.OutputList.iterator();
/* 1198 */     while (listIter.hasNext()) {
/* 1199 */       CFFFont.Item item = (CFFFont.Item)listIter.next();
/* 1200 */       item.xref();
/*      */     }
/*      */ 
/* 1203 */     int size = currentOffset[0];
/* 1204 */     byte[] b = new byte[size];
/*      */ 
/* 1207 */     listIter = this.OutputList.iterator();
/* 1208 */     while (listIter.hasNext()) {
/* 1209 */       CFFFont.Item item = (CFFFont.Item)listIter.next();
/* 1210 */       item.emit(b);
/*      */     }
/*      */ 
/* 1213 */     return b;
/*      */   }
/*      */ 
/*      */   protected void CopyHeader()
/*      */   {
/* 1221 */     seek(0);
/* 1222 */     int major = getCard8();
/* 1223 */     int minor = getCard8();
/* 1224 */     int hdrSize = getCard8();
/* 1225 */     int offSize = getCard8();
/* 1226 */     this.nextIndexOffset = hdrSize;
/* 1227 */     this.OutputList.addLast(new CFFFont.RangeItem(this.buf, 0, hdrSize));
/*      */   }
/*      */ 
/*      */   protected void BuildIndexHeader(int Count, int Offsize, int First)
/*      */   {
/* 1239 */     this.OutputList.addLast(new CFFFont.UInt16Item((char)Count));
/*      */ 
/* 1241 */     this.OutputList.addLast(new CFFFont.UInt8Item((char)Offsize));
/*      */ 
/* 1243 */     switch (Offsize) {
/*      */     case 1:
/* 1245 */       this.OutputList.addLast(new CFFFont.UInt8Item((char)First));
/* 1246 */       break;
/*      */     case 2:
/* 1248 */       this.OutputList.addLast(new CFFFont.UInt16Item((char)First));
/* 1249 */       break;
/*      */     case 3:
/* 1251 */       this.OutputList.addLast(new CFFFont.UInt24Item((char)First));
/* 1252 */       break;
/*      */     case 4:
/* 1254 */       this.OutputList.addLast(new CFFFont.UInt32Item((char)First));
/* 1255 */       break;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void CreateKeys(CFFFont.OffsetItem fdarrayRef, CFFFont.OffsetItem fdselectRef, CFFFont.OffsetItem charsetRef, CFFFont.OffsetItem charstringsRef)
/*      */   {
/* 1271 */     this.OutputList.addLast(fdarrayRef);
/* 1272 */     this.OutputList.addLast(new CFFFont.UInt8Item('\f'));
/* 1273 */     this.OutputList.addLast(new CFFFont.UInt8Item('$'));
/*      */ 
/* 1275 */     this.OutputList.addLast(fdselectRef);
/* 1276 */     this.OutputList.addLast(new CFFFont.UInt8Item('\f'));
/* 1277 */     this.OutputList.addLast(new CFFFont.UInt8Item('%'));
/*      */ 
/* 1279 */     this.OutputList.addLast(charsetRef);
/* 1280 */     this.OutputList.addLast(new CFFFont.UInt8Item('\017'));
/*      */ 
/* 1282 */     this.OutputList.addLast(charstringsRef);
/* 1283 */     this.OutputList.addLast(new CFFFont.UInt8Item('\021'));
/*      */   }
/*      */ 
/*      */   protected void CreateNewStringIndex(int Font)
/*      */   {
/* 1293 */     String fdFontName = this.fonts[Font].name + "-OneRange";
/* 1294 */     if (fdFontName.length() > 127)
/* 1295 */       fdFontName = fdFontName.substring(0, 127);
/* 1296 */     String extraStrings = "AdobeIdentity" + fdFontName;
/*      */ 
/* 1298 */     int origStringsLen = this.stringOffsets[(this.stringOffsets.length - 1)] - this.stringOffsets[0];
/*      */ 
/* 1300 */     int stringsBaseOffset = this.stringOffsets[0] - 1;
/*      */     byte stringsIndexOffSize;
/*      */     byte stringsIndexOffSize;
/* 1303 */     if (origStringsLen + extraStrings.length() <= 255) { stringsIndexOffSize = 1; }
/*      */     else
/*      */     {
/* 1304 */       byte stringsIndexOffSize;
/* 1304 */       if (origStringsLen + extraStrings.length() <= 65535) { stringsIndexOffSize = 2; }
/*      */       else
/*      */       {
/* 1305 */         byte stringsIndexOffSize;
/* 1305 */         if (origStringsLen + extraStrings.length() <= 16777215) stringsIndexOffSize = 3; else
/* 1306 */           stringsIndexOffSize = 4; 
/*      */       }
/*      */     }
/* 1308 */     this.OutputList.addLast(new CFFFont.UInt16Item((char)(this.stringOffsets.length - 1 + 3)));
/* 1309 */     this.OutputList.addLast(new CFFFont.UInt8Item((char)stringsIndexOffSize));
/* 1310 */     for (int stringOffset : this.stringOffsets) {
/* 1311 */       this.OutputList.addLast(new CFFFont.IndexOffsetItem(stringsIndexOffSize, stringOffset - stringsBaseOffset));
/*      */     }
/* 1313 */     int currentStringsOffset = this.stringOffsets[(this.stringOffsets.length - 1)] - stringsBaseOffset;
/*      */ 
/* 1316 */     currentStringsOffset += "Adobe".length();
/* 1317 */     this.OutputList.addLast(new CFFFont.IndexOffsetItem(stringsIndexOffSize, currentStringsOffset));
/* 1318 */     currentStringsOffset += "Identity".length();
/* 1319 */     this.OutputList.addLast(new CFFFont.IndexOffsetItem(stringsIndexOffSize, currentStringsOffset));
/* 1320 */     currentStringsOffset += fdFontName.length();
/* 1321 */     this.OutputList.addLast(new CFFFont.IndexOffsetItem(stringsIndexOffSize, currentStringsOffset));
/*      */ 
/* 1323 */     this.OutputList.addLast(new CFFFont.RangeItem(this.buf, this.stringOffsets[0], origStringsLen));
/* 1324 */     this.OutputList.addLast(new CFFFont.StringItem(extraStrings));
/*      */   }
/*      */ 
/*      */   protected void CreateFDSelect(CFFFont.OffsetItem fdselectRef, int nglyphs)
/*      */   {
/* 1335 */     this.OutputList.addLast(new CFFFont.MarkerItem(fdselectRef));
/* 1336 */     this.OutputList.addLast(new CFFFont.UInt8Item('\003'));
/* 1337 */     this.OutputList.addLast(new CFFFont.UInt16Item('\001'));
/*      */ 
/* 1339 */     this.OutputList.addLast(new CFFFont.UInt16Item('\000'));
/* 1340 */     this.OutputList.addLast(new CFFFont.UInt8Item('\000'));
/*      */ 
/* 1342 */     this.OutputList.addLast(new CFFFont.UInt16Item((char)nglyphs));
/*      */   }
/*      */ 
/*      */   protected void CreateCharset(CFFFont.OffsetItem charsetRef, int nglyphs)
/*      */   {
/* 1353 */     this.OutputList.addLast(new CFFFont.MarkerItem(charsetRef));
/* 1354 */     this.OutputList.addLast(new CFFFont.UInt8Item('\002'));
/* 1355 */     this.OutputList.addLast(new CFFFont.UInt16Item('\001'));
/* 1356 */     this.OutputList.addLast(new CFFFont.UInt16Item((char)(nglyphs - 1)));
/*      */   }
/*      */ 
/*      */   protected void CreateFDArray(CFFFont.OffsetItem fdarrayRef, CFFFont.OffsetItem privateRef, int Font)
/*      */   {
/* 1369 */     this.OutputList.addLast(new CFFFont.MarkerItem(fdarrayRef));
/*      */ 
/* 1371 */     BuildIndexHeader(1, 1, 1);
/*      */ 
/* 1374 */     CFFFont.OffsetItem privateIndex1Ref = new CFFFont.IndexOffsetItem(1);
/* 1375 */     this.OutputList.addLast(privateIndex1Ref);
/* 1376 */     CFFFont.IndexBaseItem privateBase = new CFFFont.IndexBaseItem();
/*      */ 
/* 1378 */     this.OutputList.addLast(privateBase);
/*      */ 
/* 1381 */     int NewSize = this.fonts[Font].privateLength;
/*      */ 
/* 1383 */     int OrgSubrsOffsetSize = CalcSubrOffsetSize(this.fonts[Font].privateOffset, this.fonts[Font].privateLength);
/*      */ 
/* 1385 */     if (OrgSubrsOffsetSize != 0)
/* 1386 */       NewSize += 5 - OrgSubrsOffsetSize;
/* 1387 */     this.OutputList.addLast(new CFFFont.DictNumberItem(NewSize));
/* 1388 */     this.OutputList.addLast(privateRef);
/* 1389 */     this.OutputList.addLast(new CFFFont.UInt8Item('\022'));
/*      */ 
/* 1391 */     this.OutputList.addLast(new CFFFont.IndexMarkerItem(privateIndex1Ref, privateBase));
/*      */   }
/*      */ 
/*      */   void Reconstruct(int Font)
/*      */   {
/* 1401 */     CFFFont.OffsetItem[] fdPrivate = new CFFFont.DictOffsetItem[this.fonts[Font].FDArrayOffsets.length - 1];
/* 1402 */     CFFFont.IndexBaseItem[] fdPrivateBase = new CFFFont.IndexBaseItem[this.fonts[Font].fdprivateOffsets.length];
/* 1403 */     CFFFont.OffsetItem[] fdSubrs = new CFFFont.DictOffsetItem[this.fonts[Font].fdprivateOffsets.length];
/*      */ 
/* 1405 */     ReconstructFDArray(Font, fdPrivate);
/* 1406 */     ReconstructPrivateDict(Font, fdPrivate, fdPrivateBase, fdSubrs);
/* 1407 */     ReconstructPrivateSubrs(Font, fdPrivateBase, fdSubrs);
/*      */   }
/*      */ 
/*      */   void ReconstructFDArray(int Font, CFFFont.OffsetItem[] fdPrivate)
/*      */   {
/* 1418 */     BuildIndexHeader(this.fonts[Font].FDArrayCount, this.fonts[Font].FDArrayOffsize, 1);
/*      */ 
/* 1421 */     CFFFont.OffsetItem[] fdOffsets = new CFFFont.IndexOffsetItem[this.fonts[Font].FDArrayOffsets.length - 1];
/* 1422 */     for (int i = 0; i < this.fonts[Font].FDArrayOffsets.length - 1; i++)
/*      */     {
/* 1424 */       fdOffsets[i] = new CFFFont.IndexOffsetItem(this.fonts[Font].FDArrayOffsize);
/* 1425 */       this.OutputList.addLast(fdOffsets[i]);
/*      */     }
/*      */ 
/* 1429 */     CFFFont.IndexBaseItem fdArrayBase = new CFFFont.IndexBaseItem();
/* 1430 */     this.OutputList.addLast(fdArrayBase);
/*      */ 
/* 1436 */     for (int k = 0; k < this.fonts[Font].FDArrayOffsets.length - 1; k++)
/*      */     {
/* 1440 */       seek(this.fonts[Font].FDArrayOffsets[k]);
/* 1441 */       while (getPosition() < this.fonts[Font].FDArrayOffsets[(k + 1)])
/*      */       {
/* 1443 */         int p1 = getPosition();
/* 1444 */         getDictItem();
/* 1445 */         int p2 = getPosition();
/*      */ 
/* 1448 */         if (this.key == "Private")
/*      */         {
/* 1450 */           int NewSize = ((Integer)this.args[0]).intValue();
/*      */ 
/* 1452 */           int OrgSubrsOffsetSize = CalcSubrOffsetSize(this.fonts[Font].fdprivateOffsets[k], this.fonts[Font].fdprivateLengths[k]);
/*      */ 
/* 1454 */           if (OrgSubrsOffsetSize != 0) {
/* 1455 */             NewSize += 5 - OrgSubrsOffsetSize;
/*      */           }
/* 1457 */           this.OutputList.addLast(new CFFFont.DictNumberItem(NewSize));
/* 1458 */           fdPrivate[k] = new CFFFont.DictOffsetItem();
/* 1459 */           this.OutputList.addLast(fdPrivate[k]);
/* 1460 */           this.OutputList.addLast(new CFFFont.UInt8Item('\022'));
/*      */ 
/* 1462 */           seek(p2);
/*      */         }
/*      */         else
/*      */         {
/* 1466 */           this.OutputList.addLast(new CFFFont.RangeItem(this.buf, p1, p2 - p1));
/*      */         }
/*      */       }
/*      */ 
/* 1470 */       this.OutputList.addLast(new CFFFont.IndexMarkerItem(fdOffsets[k], fdArrayBase));
/*      */     }
/*      */   }
/*      */ 
/*      */   void ReconstructPrivateDict(int Font, CFFFont.OffsetItem[] fdPrivate, CFFFont.IndexBaseItem[] fdPrivateBase, CFFFont.OffsetItem[] fdSubrs)
/*      */   {
/* 1487 */     for (int i = 0; i < this.fonts[Font].fdprivateOffsets.length; i++)
/*      */     {
/* 1492 */       this.OutputList.addLast(new CFFFont.MarkerItem(fdPrivate[i]));
/* 1493 */       fdPrivateBase[i] = new CFFFont.IndexBaseItem();
/* 1494 */       this.OutputList.addLast(fdPrivateBase[i]);
/*      */ 
/* 1496 */       seek(this.fonts[Font].fdprivateOffsets[i]);
/* 1497 */       while (getPosition() < this.fonts[Font].fdprivateOffsets[i] + this.fonts[Font].fdprivateLengths[i])
/*      */       {
/* 1499 */         int p1 = getPosition();
/* 1500 */         getDictItem();
/* 1501 */         int p2 = getPosition();
/*      */ 
/* 1504 */         if (this.key == "Subrs") {
/* 1505 */           fdSubrs[i] = new CFFFont.DictOffsetItem();
/* 1506 */           this.OutputList.addLast(fdSubrs[i]);
/* 1507 */           this.OutputList.addLast(new CFFFont.UInt8Item('\023'));
/*      */         }
/*      */         else
/*      */         {
/* 1511 */           this.OutputList.addLast(new CFFFont.RangeItem(this.buf, p1, p2 - p1));
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void ReconstructPrivateSubrs(int Font, CFFFont.IndexBaseItem[] fdPrivateBase, CFFFont.OffsetItem[] fdSubrs)
/*      */   {
/* 1528 */     for (int i = 0; i < this.fonts[Font].fdprivateLengths.length; i++)
/*      */     {
/* 1532 */       if ((fdSubrs[i] != null) && (this.fonts[Font].PrivateSubrsOffset[i] >= 0))
/*      */       {
/* 1534 */         this.OutputList.addLast(new CFFFont.SubrMarkerItem(fdSubrs[i], fdPrivateBase[i]));
/* 1535 */         if (this.NewLSubrsIndex[i] != null)
/* 1536 */           this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(this.NewLSubrsIndex[i]), 0, this.NewLSubrsIndex[i].length));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   int CalcSubrOffsetSize(int Offset, int Size)
/*      */   {
/* 1551 */     int OffsetSize = 0;
/*      */ 
/* 1553 */     seek(Offset);
/*      */ 
/* 1555 */     while (getPosition() < Offset + Size)
/*      */     {
/* 1557 */       int p1 = getPosition();
/* 1558 */       getDictItem();
/* 1559 */       int p2 = getPosition();
/*      */ 
/* 1561 */       if (this.key == "Subrs")
/*      */       {
/* 1563 */         OffsetSize = p2 - p1 - 1;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1568 */     return OffsetSize;
/*      */   }
/*      */ 
/*      */   protected int countEntireIndexRange(int indexOffset)
/*      */   {
/* 1579 */     seek(indexOffset);
/*      */ 
/* 1581 */     int count = getCard16();
/*      */ 
/* 1583 */     if (count == 0) {
/* 1584 */       return 2;
/*      */     }
/*      */ 
/* 1588 */     int indexOffSize = getCard8();
/*      */ 
/* 1590 */     seek(indexOffset + 2 + 1 + count * indexOffSize);
/*      */ 
/* 1592 */     int size = getOffset(indexOffSize) - 1;
/*      */ 
/* 1594 */     return 3 + (count + 1) * indexOffSize + size;
/*      */   }
/*      */ 
/*      */   void CreateNonCIDPrivate(int Font, CFFFont.OffsetItem Subr)
/*      */   {
/* 1607 */     seek(this.fonts[Font].privateOffset);
/* 1608 */     while (getPosition() < this.fonts[Font].privateOffset + this.fonts[Font].privateLength)
/*      */     {
/* 1610 */       int p1 = getPosition();
/* 1611 */       getDictItem();
/* 1612 */       int p2 = getPosition();
/*      */ 
/* 1615 */       if (this.key == "Subrs") {
/* 1616 */         this.OutputList.addLast(Subr);
/* 1617 */         this.OutputList.addLast(new CFFFont.UInt8Item('\023'));
/*      */       }
/*      */       else
/*      */       {
/* 1621 */         this.OutputList.addLast(new CFFFont.RangeItem(this.buf, p1, p2 - p1));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void CreateNonCIDSubrs(int Font, CFFFont.IndexBaseItem PrivateBase, CFFFont.OffsetItem Subrs)
/*      */   {
/* 1635 */     this.OutputList.addLast(new CFFFont.SubrMarkerItem(Subrs, PrivateBase));
/*      */ 
/* 1637 */     if (this.NewSubrsIndexNonCID != null)
/* 1638 */       this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(this.NewSubrsIndexNonCID), 0, this.NewSubrsIndexNonCID.length));
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.CFFFontSubset
 * JD-Core Version:    0.6.2
 */