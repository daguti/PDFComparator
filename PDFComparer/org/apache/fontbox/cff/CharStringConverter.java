/*     */ package org.apache.fontbox.cff;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class CharStringConverter extends CharStringHandler
/*     */ {
/*  33 */   private int defaultWidthX = 0;
/*  34 */   private int nominalWidthX = 0;
/*  35 */   private List<Object> sequence = null;
/*  36 */   private int pathCount = 0;
/*     */ 
/*     */   /** @deprecated */
/*     */   public CharStringConverter(int defaultWidth, int nominalWidth, IndexData fontGlobalSubrIndex, IndexData fontLocalSubrIndex)
/*     */   {
/*  48 */     this.defaultWidthX = defaultWidth;
/*  49 */     this.nominalWidthX = nominalWidth;
/*     */   }
/*     */ 
/*     */   public CharStringConverter(int defaultWidth, int nominalWidth)
/*     */   {
/*  61 */     this.defaultWidthX = defaultWidth;
/*  62 */     this.nominalWidthX = nominalWidth;
/*     */   }
/*     */ 
/*     */   public List<Object> convert(List<Object> commandSequence)
/*     */   {
/*  71 */     this.sequence = new ArrayList();
/*  72 */     this.pathCount = 0;
/*  73 */     handleSequence(commandSequence);
/*  74 */     return this.sequence;
/*     */   }
/*     */ 
/*     */   public List<Integer> handleCommand(List<Integer> numbers, CharStringCommand command)
/*     */   {
/*  84 */     if (CharStringCommand.TYPE1_VOCABULARY.containsKey(command.getKey()))
/*     */     {
/*  86 */       return handleType1Command(numbers, command);
/*     */     }
/*     */ 
/*  90 */     return handleType2Command(numbers, command);
/*     */   }
/*     */ 
/*     */   private List<Integer> handleType1Command(List<Integer> numbers, CharStringCommand command)
/*     */   {
/*  97 */     String name = (String)CharStringCommand.TYPE1_VOCABULARY.get(command.getKey());
/*     */ 
/*  99 */     if ("hstem".equals(name))
/*     */     {
/* 101 */       numbers = clearStack(numbers, numbers.size() % 2 != 0);
/* 102 */       expandStemHints(numbers, true);
/*     */     }
/* 104 */     else if ("vstem".equals(name))
/*     */     {
/* 106 */       numbers = clearStack(numbers, numbers.size() % 2 != 0);
/* 107 */       expandStemHints(numbers, false);
/*     */     }
/* 109 */     else if ("vmoveto".equals(name))
/*     */     {
/* 111 */       numbers = clearStack(numbers, numbers.size() > 1);
/* 112 */       markPath();
/* 113 */       addCommand(numbers, command);
/*     */     }
/* 115 */     else if ("rlineto".equals(name))
/*     */     {
/* 117 */       addCommandList(split(numbers, 2), command);
/*     */     }
/* 119 */     else if ("hlineto".equals(name))
/*     */     {
/* 121 */       drawAlternatingLine(numbers, true);
/*     */     }
/* 123 */     else if ("vlineto".equals(name))
/*     */     {
/* 125 */       drawAlternatingLine(numbers, false);
/*     */     }
/* 127 */     else if ("rrcurveto".equals(name))
/*     */     {
/* 129 */       addCommandList(split(numbers, 6), command);
/*     */     }
/* 131 */     else if ("endchar".equals(name))
/*     */     {
/* 133 */       numbers = clearStack(numbers, numbers.size() > 0);
/* 134 */       closePath();
/* 135 */       addCommand(numbers, command);
/*     */     }
/* 137 */     else if ("rmoveto".equals(name))
/*     */     {
/* 139 */       numbers = clearStack(numbers, numbers.size() > 2);
/* 140 */       markPath();
/* 141 */       addCommand(numbers, command);
/*     */     }
/* 143 */     else if ("hmoveto".equals(name))
/*     */     {
/* 145 */       numbers = clearStack(numbers, numbers.size() > 1);
/* 146 */       markPath();
/* 147 */       addCommand(numbers, command);
/*     */     }
/* 149 */     else if ("vhcurveto".equals(name))
/*     */     {
/* 151 */       drawAlternatingCurve(numbers, false);
/*     */     }
/* 153 */     else if ("hvcurveto".equals(name))
/*     */     {
/* 155 */       drawAlternatingCurve(numbers, true);
/*     */     } else {
/* 157 */       if ("return".equals(name))
/*     */       {
/* 159 */         return numbers;
/*     */       }
/*     */ 
/* 163 */       addCommand(numbers, command);
/*     */     }
/* 165 */     return null;
/*     */   }
/*     */ 
/*     */   private List<Integer> handleType2Command(List<Integer> numbers, CharStringCommand command)
/*     */   {
/* 172 */     String name = (String)CharStringCommand.TYPE2_VOCABULARY.get(command.getKey());
/* 173 */     if ("hflex".equals(name))
/*     */     {
/* 175 */       List first = Arrays.asList(new Integer[] { (Integer)numbers.get(0), Integer.valueOf(0), (Integer)numbers.get(1), (Integer)numbers.get(2), (Integer)numbers.get(3), Integer.valueOf(0) });
/*     */ 
/* 177 */       List second = Arrays.asList(new Integer[] { (Integer)numbers.get(4), Integer.valueOf(0), (Integer)numbers.get(5), Integer.valueOf(-((Integer)numbers.get(2)).intValue()), (Integer)numbers.get(6), Integer.valueOf(0) });
/*     */ 
/* 180 */       addCommandList(Arrays.asList(new List[] { first, second }), new CharStringCommand(8));
/*     */     }
/* 182 */     else if ("flex".equals(name))
/*     */     {
/* 184 */       List first = numbers.subList(0, 6);
/* 185 */       List second = numbers.subList(6, 12);
/* 186 */       addCommandList(Arrays.asList(new List[] { first, second }), new CharStringCommand(8));
/*     */     }
/* 188 */     else if ("hflex1".equals(name))
/*     */     {
/* 190 */       List first = Arrays.asList(new Integer[] { (Integer)numbers.get(0), (Integer)numbers.get(1), (Integer)numbers.get(2), (Integer)numbers.get(3), (Integer)numbers.get(4), Integer.valueOf(0) });
/*     */ 
/* 192 */       List second = Arrays.asList(new Integer[] { (Integer)numbers.get(5), Integer.valueOf(0), (Integer)numbers.get(6), (Integer)numbers.get(7), (Integer)numbers.get(8), Integer.valueOf(0) });
/*     */ 
/* 194 */       addCommandList(Arrays.asList(new List[] { first, second }), new CharStringCommand(8));
/*     */     }
/* 196 */     else if ("flex1".equals(name))
/*     */     {
/* 198 */       int dx = 0;
/* 199 */       int dy = 0;
/* 200 */       for (int i = 0; i < 5; i++) {
/* 201 */         dx += ((Integer)numbers.get(i * 2)).intValue();
/* 202 */         dy += ((Integer)numbers.get(i * 2 + 1)).intValue();
/*     */       }
/* 204 */       List first = numbers.subList(0, 6);
/* 205 */       List second = Arrays.asList(new Integer[] { (Integer)numbers.get(6), (Integer)numbers.get(7), (Integer)numbers.get(8), (Integer)numbers.get(9), Math.abs(dx) > Math.abs(dy) ? (Integer)numbers.get(10) : Integer.valueOf(-dx), Math.abs(dx) > Math.abs(dy) ? Integer.valueOf(-dy) : (Integer)numbers.get(10) });
/*     */ 
/* 208 */       addCommandList(Arrays.asList(new List[] { first, second }), new CharStringCommand(8));
/*     */     }
/* 210 */     else if ("hstemhm".equals(name))
/*     */     {
/* 212 */       numbers = clearStack(numbers, numbers.size() % 2 != 0);
/* 213 */       expandStemHints(numbers, true);
/*     */     }
/* 215 */     else if (("hintmask".equals(name)) || ("cntrmask".equals(name)))
/*     */     {
/* 217 */       numbers = clearStack(numbers, numbers.size() % 2 != 0);
/* 218 */       if (numbers.size() > 0)
/*     */       {
/* 220 */         expandStemHints(numbers, false);
/*     */       }
/*     */     }
/* 223 */     else if ("vstemhm".equals(name))
/*     */     {
/* 225 */       numbers = clearStack(numbers, numbers.size() % 2 != 0);
/* 226 */       expandStemHints(numbers, false);
/*     */     }
/* 228 */     else if ("rcurveline".equals(name))
/*     */     {
/* 230 */       addCommandList(split(numbers.subList(0, numbers.size() - 2), 6), new CharStringCommand(8));
/*     */ 
/* 232 */       addCommand(numbers.subList(numbers.size() - 2, numbers.size()), new CharStringCommand(5));
/*     */     }
/* 235 */     else if ("rlinecurve".equals(name))
/*     */     {
/* 237 */       addCommandList(split(numbers.subList(0, numbers.size() - 6), 2), new CharStringCommand(5));
/*     */ 
/* 239 */       addCommand(numbers.subList(numbers.size() - 6, numbers.size()), new CharStringCommand(8));
/*     */     }
/* 242 */     else if ("vvcurveto".equals(name))
/*     */     {
/* 244 */       drawCurve(numbers, false);
/*     */     }
/* 246 */     else if ("hhcurveto".equals(name))
/*     */     {
/* 248 */       drawCurve(numbers, true);
/*     */     }
/*     */     else
/*     */     {
/* 252 */       addCommand(numbers, command);
/*     */     }
/* 254 */     return null;
/*     */   }
/*     */ 
/*     */   private List<Integer> clearStack(List<Integer> numbers, boolean flag)
/*     */   {
/* 260 */     if (this.sequence.size() == 0)
/*     */     {
/* 262 */       if (flag)
/*     */       {
/* 264 */         addCommand(Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(((Integer)numbers.get(0)).intValue() + this.nominalWidthX) }), new CharStringCommand(13));
/*     */ 
/* 268 */         numbers = numbers.subList(1, numbers.size());
/*     */       }
/*     */       else
/*     */       {
/* 272 */         addCommand(Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(this.defaultWidthX) }), new CharStringCommand(13));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 277 */     return numbers;
/*     */   }
/*     */ 
/*     */   private void expandStemHints(List<Integer> numbers, boolean horizontal)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void markPath()
/*     */   {
/* 287 */     if (this.pathCount > 0)
/*     */     {
/* 289 */       closePath();
/*     */     }
/* 291 */     this.pathCount += 1;
/*     */   }
/*     */ 
/*     */   private void closePath()
/*     */   {
/* 296 */     CharStringCommand command = this.pathCount > 0 ? (CharStringCommand)this.sequence.get(this.sequence.size() - 1) : null;
/*     */ 
/* 300 */     CharStringCommand closepathCommand = new CharStringCommand(9);
/* 301 */     if ((command != null) && (!closepathCommand.equals(command)))
/*     */     {
/* 303 */       addCommand(Collections.emptyList(), closepathCommand);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void drawAlternatingLine(List<Integer> numbers, boolean horizontal)
/*     */   {
/* 309 */     while (numbers.size() > 0)
/*     */     {
/* 311 */       addCommand(numbers.subList(0, 1), new CharStringCommand(horizontal ? 6 : 7));
/*     */ 
/* 313 */       numbers = numbers.subList(1, numbers.size());
/* 314 */       horizontal = !horizontal;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void drawAlternatingCurve(List<Integer> numbers, boolean horizontal)
/*     */   {
/* 320 */     while (numbers.size() > 0)
/*     */     {
/* 322 */       boolean last = numbers.size() == 5;
/* 323 */       if (horizontal)
/*     */       {
/* 325 */         addCommand(Arrays.asList(new Integer[] { (Integer)numbers.get(0), Integer.valueOf(0), (Integer)numbers.get(1), (Integer)numbers.get(2), last ? (Integer)numbers.get(4) : Integer.valueOf(0), (Integer)numbers.get(3) }), new CharStringCommand(8));
/*     */       }
/*     */       else
/*     */       {
/* 332 */         addCommand(Arrays.asList(new Integer[] { Integer.valueOf(0), (Integer)numbers.get(0), (Integer)numbers.get(1), (Integer)numbers.get(2), (Integer)numbers.get(3), last ? (Integer)numbers.get(4) : Integer.valueOf(0) }), new CharStringCommand(8));
/*     */       }
/*     */ 
/* 337 */       numbers = numbers.subList(last ? 5 : 4, numbers.size());
/* 338 */       horizontal = !horizontal;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void drawCurve(List<Integer> numbers, boolean horizontal)
/*     */   {
/* 344 */     while (numbers.size() > 0)
/*     */     {
/* 346 */       boolean first = numbers.size() % 4 == 1;
/*     */ 
/* 348 */       if (horizontal)
/*     */       {
/* 350 */         addCommand(Arrays.asList(new Integer[] { (Integer)numbers.get(first ? 1 : 0), first ? (Integer)numbers.get(0) : Integer.valueOf(0), (Integer)numbers.get(first ? 2 : 1), (Integer)numbers.get(first ? 3 : 2), (Integer)numbers.get(first ? 4 : 3), Integer.valueOf(0) }), new CharStringCommand(8));
/*     */       }
/*     */       else
/*     */       {
/* 358 */         addCommand(Arrays.asList(new Integer[] { first ? (Integer)numbers.get(0) : Integer.valueOf(0), (Integer)numbers.get(first ? 1 : 0), (Integer)numbers.get(first ? 2 : 1), (Integer)numbers.get(first ? 3 : 2), Integer.valueOf(0), (Integer)numbers.get(first ? 4 : 3) }), new CharStringCommand(8));
/*     */       }
/*     */ 
/* 364 */       numbers = numbers.subList(first ? 5 : 4, numbers.size());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addCommandList(List<List<Integer>> numbers, CharStringCommand command)
/*     */   {
/* 371 */     for (int i = 0; i < numbers.size(); i++)
/*     */     {
/* 373 */       addCommand((List)numbers.get(i), command);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addCommand(List<Integer> numbers, CharStringCommand command)
/*     */   {
/* 379 */     this.sequence.addAll(numbers);
/* 380 */     this.sequence.add(command);
/*     */   }
/*     */ 
/*     */   private static <E> List<List<E>> split(List<E> list, int size)
/*     */   {
/* 385 */     List result = new ArrayList();
/* 386 */     for (int i = 0; i < list.size() / size; i++)
/*     */     {
/* 388 */       result.add(list.subList(i * size, (i + 1) * size));
/*     */     }
/* 390 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.CharStringConverter
 * JD-Core Version:    0.6.2
 */