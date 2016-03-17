/*     */ package org.apache.fontbox.cff;
/*     */ 
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D.Float;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class CharStringRenderer extends CharStringHandler
/*     */ {
/*  35 */   private static final Log LOG = LogFactory.getLog(CharStringRenderer.class);
/*     */ 
/*  37 */   private boolean isCharstringType1 = true;
/*  38 */   private boolean isFirstCommand = true;
/*     */ 
/*  40 */   private GeneralPath path = null;
/*  41 */   private Point2D sidebearingPoint = null;
/*  42 */   private Point2D referencePoint = null;
/*  43 */   private int width = 0;
/*  44 */   private boolean hasNonEndCharOp = false;
/*     */ 
/*     */   public CharStringRenderer()
/*     */   {
/*  51 */     this.isCharstringType1 = true;
/*     */   }
/*     */ 
/*     */   public CharStringRenderer(boolean isType1)
/*     */   {
/*  61 */     this.isCharstringType1 = isType1;
/*     */   }
/*     */ 
/*     */   public GeneralPath render(List<Object> sequence)
/*     */   {
/*  71 */     this.path = new GeneralPath();
/*  72 */     this.sidebearingPoint = new Point2D.Float(0.0F, 0.0F);
/*  73 */     this.referencePoint = null;
/*  74 */     setWidth(0);
/*  75 */     handleSequence(sequence);
/*  76 */     return this.path;
/*     */   }
/*     */ 
/*     */   public List<Integer> handleCommand(List<Integer> numbers, CharStringCommand command)
/*     */   {
/*  84 */     if (this.isCharstringType1)
/*     */     {
/*  86 */       handleCommandType1(numbers, command);
/*     */     }
/*     */     else
/*     */     {
/*  90 */       handleCommandType2(numbers, command);
/*     */     }
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */   private void handleCommandType2(List<Integer> numbers, CharStringCommand command)
/*     */   {
/* 102 */     String name = (String)CharStringCommand.TYPE2_VOCABULARY.get(command.getKey());
/*     */ 
/* 104 */     if (!this.hasNonEndCharOp)
/*     */     {
/* 106 */       this.hasNonEndCharOp = (!"endchar".equals(name));
/*     */     }
/* 108 */     if ("vmoveto".equals(name))
/*     */     {
/* 110 */       if (this.path.getCurrentPoint() != null)
/*     */       {
/* 112 */         closePath();
/*     */       }
/* 114 */       if ((this.isFirstCommand) && (numbers.size() == 2))
/*     */       {
/* 116 */         setWidth(((Integer)numbers.get(0)).intValue());
/* 117 */         rmoveTo(Integer.valueOf(0), (Number)numbers.get(1));
/*     */       }
/*     */       else
/*     */       {
/* 121 */         rmoveTo(Integer.valueOf(0), (Number)numbers.get(0));
/*     */       }
/*     */     }
/* 124 */     else if ("rlineto".equals(name))
/*     */     {
/* 126 */       if ((this.isFirstCommand) && (numbers.size() == 3))
/*     */       {
/* 128 */         setWidth(((Integer)numbers.get(0)).intValue());
/*     */       }
/* 130 */       rrlineTo(numbers);
/*     */     }
/* 132 */     else if ("hlineto".equals(name))
/*     */     {
/* 134 */       if ((this.isFirstCommand) && (numbers.size() == 2))
/*     */       {
/* 136 */         setWidth(((Integer)numbers.get(0)).intValue());
/*     */       }
/* 138 */       hlineTo(numbers);
/*     */     }
/* 140 */     else if ("vlineto".equals(name))
/*     */     {
/* 142 */       if ((this.isFirstCommand) && (numbers.size() == 2))
/*     */       {
/* 144 */         setWidth(((Integer)numbers.get(0)).intValue());
/*     */       }
/* 146 */       vlineTo(numbers);
/*     */     }
/* 148 */     else if ("rrcurveto".equals(name))
/*     */     {
/* 150 */       if ((this.isFirstCommand) && (numbers.size() == 7))
/*     */       {
/* 152 */         setWidth(((Integer)numbers.get(0)).intValue());
/*     */       }
/* 154 */       rrCurveTo(numbers);
/*     */     }
/* 156 */     else if ("rlinecurve".equals(name))
/*     */     {
/* 158 */       rlineCurve(numbers);
/*     */     }
/* 160 */     else if ("rcurveline".equals(name))
/*     */     {
/* 162 */       rcurveLine(numbers);
/*     */     }
/* 164 */     else if ("closepath".equals(name))
/*     */     {
/* 166 */       closePath();
/*     */     }
/* 168 */     else if ("rmoveto".equals(name))
/*     */     {
/* 170 */       if (this.path.getCurrentPoint() != null)
/*     */       {
/* 172 */         closePath();
/*     */       }
/* 174 */       if ((this.isFirstCommand) && (numbers.size() == 3))
/*     */       {
/* 176 */         setWidth(((Integer)numbers.get(0)).intValue());
/* 177 */         rmoveTo((Number)numbers.get(1), (Number)numbers.get(2));
/*     */       }
/*     */       else
/*     */       {
/* 181 */         rmoveTo((Number)numbers.get(0), (Number)numbers.get(1));
/*     */       }
/*     */     }
/* 184 */     else if ("hmoveto".equals(name))
/*     */     {
/* 186 */       if (this.path.getCurrentPoint() != null)
/*     */       {
/* 188 */         closePath();
/*     */       }
/* 190 */       if ((this.isFirstCommand) && (numbers.size() == 2))
/*     */       {
/* 192 */         setWidth(((Integer)numbers.get(0)).intValue());
/* 193 */         rmoveTo((Number)numbers.get(1), Integer.valueOf(0));
/*     */       }
/*     */       else
/*     */       {
/* 197 */         rmoveTo((Number)numbers.get(0), Integer.valueOf(0));
/*     */       }
/*     */     }
/* 200 */     else if ("vhcurveto".equals(name))
/*     */     {
/* 202 */       if ((this.isFirstCommand) && (numbers.size() == 5))
/*     */       {
/* 204 */         setWidth(((Integer)numbers.get(0)).intValue());
/*     */       }
/* 206 */       rvhCurveTo(numbers);
/*     */     }
/* 208 */     else if ("hvcurveto".equals(name))
/*     */     {
/* 210 */       if ((this.isFirstCommand) && (numbers.size() == 5))
/*     */       {
/* 212 */         setWidth(((Integer)numbers.get(0)).intValue());
/*     */       }
/* 214 */       rhvCurveTo(numbers);
/*     */     }
/* 216 */     else if ("hhcurveto".equals(name))
/*     */     {
/* 218 */       rhhCurveTo(numbers);
/*     */     }
/* 220 */     else if ("vvcurveto".equals(name))
/*     */     {
/* 222 */       rvvCurveTo(numbers);
/*     */     }
/* 224 */     else if ("hstem".equals(name))
/*     */     {
/* 226 */       if (numbers.size() % 2 == 1)
/*     */       {
/* 228 */         setWidth(((Integer)numbers.get(0)).intValue());
/*     */       }
/*     */     }
/* 231 */     else if ("vstem".equals(name))
/*     */     {
/* 233 */       if (numbers.size() % 2 == 1)
/*     */       {
/* 235 */         setWidth(((Integer)numbers.get(0)).intValue());
/*     */       }
/*     */     }
/* 238 */     else if ("hstemhm".equals(name))
/*     */     {
/* 240 */       if (numbers.size() % 2 == 1)
/*     */       {
/* 242 */         setWidth(((Integer)numbers.get(0)).intValue());
/*     */       }
/*     */     }
/* 245 */     else if ("vstemhm".equals(name))
/*     */     {
/* 247 */       if (numbers.size() % 2 == 1)
/*     */       {
/* 249 */         setWidth(((Integer)numbers.get(0)).intValue());
/*     */       }
/*     */     }
/* 252 */     else if ("cntrmask".equals(name))
/*     */     {
/* 254 */       if (numbers.size() == 1)
/*     */       {
/* 256 */         setWidth(((Integer)numbers.get(0)).intValue());
/*     */       }
/*     */     }
/* 259 */     else if ("hintmask".equals(name))
/*     */     {
/* 261 */       if (numbers.size() == 1)
/*     */       {
/* 263 */         setWidth(((Integer)numbers.get(0)).intValue());
/*     */       }
/*     */     }
/* 266 */     else if ("endchar".equals(name))
/*     */     {
/* 268 */       if (this.hasNonEndCharOp)
/*     */       {
/* 270 */         closePath();
/*     */       }
/* 272 */       if (numbers.size() % 2 == 1)
/*     */       {
/* 274 */         setWidth(((Integer)numbers.get(0)).intValue());
/* 275 */         if (numbers.size() > 1)
/*     */         {
/* 277 */           LOG.debug("endChar: too many numbers left, using the first one, see PDFBOX-1501 for details");
/*     */         }
/*     */       }
/*     */     }
/* 281 */     if (this.isFirstCommand)
/*     */     {
/* 283 */       this.isFirstCommand = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void handleCommandType1(List<Integer> numbers, CharStringCommand command)
/*     */   {
/* 294 */     String name = (String)CharStringCommand.TYPE1_VOCABULARY.get(command.getKey());
/*     */ 
/* 296 */     if ("vmoveto".equals(name))
/*     */     {
/* 298 */       rmoveTo(Integer.valueOf(0), (Number)numbers.get(0));
/*     */     }
/* 300 */     else if ("rlineto".equals(name))
/*     */     {
/* 302 */       rlineTo((Number)numbers.get(0), (Number)numbers.get(1));
/*     */     }
/* 304 */     else if ("hlineto".equals(name))
/*     */     {
/* 306 */       rlineTo((Number)numbers.get(0), Integer.valueOf(0));
/*     */     }
/* 308 */     else if ("vlineto".equals(name))
/*     */     {
/* 310 */       rlineTo(Integer.valueOf(0), (Number)numbers.get(0));
/*     */     }
/* 312 */     else if ("rrcurveto".equals(name))
/*     */     {
/* 314 */       rrcurveTo((Number)numbers.get(0), (Number)numbers.get(1), (Number)numbers.get(2), (Number)numbers.get(3), (Number)numbers.get(4), (Number)numbers.get(5));
/*     */     }
/* 317 */     else if ("closepath".equals(name))
/*     */     {
/* 319 */       closePath();
/*     */     }
/* 321 */     else if ("sbw".equals(name))
/*     */     {
/* 323 */       pointSb((Number)numbers.get(0), (Number)numbers.get(1));
/* 324 */       setWidth(((Integer)numbers.get(2)).intValue());
/*     */     }
/* 326 */     else if ("hsbw".equals(name))
/*     */     {
/* 328 */       pointSb((Number)numbers.get(0), Integer.valueOf(0));
/* 329 */       setWidth(((Integer)numbers.get(1)).intValue());
/*     */     }
/* 331 */     else if ("rmoveto".equals(name))
/*     */     {
/* 333 */       rmoveTo((Number)numbers.get(0), (Number)numbers.get(1));
/*     */     }
/* 335 */     else if ("hmoveto".equals(name))
/*     */     {
/* 337 */       rmoveTo((Number)numbers.get(0), Integer.valueOf(0));
/*     */     }
/* 339 */     else if ("vhcurveto".equals(name))
/*     */     {
/* 341 */       rrcurveTo(Integer.valueOf(0), (Number)numbers.get(0), (Number)numbers.get(1), (Number)numbers.get(2), (Number)numbers.get(3), Integer.valueOf(0));
/*     */     }
/* 344 */     else if ("hvcurveto".equals(name))
/*     */     {
/* 346 */       rrcurveTo((Number)numbers.get(0), Integer.valueOf(0), (Number)numbers.get(1), (Number)numbers.get(2), Integer.valueOf(0), (Number)numbers.get(3));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void rmoveTo(Number dx, Number dy)
/*     */   {
/* 353 */     Point2D point = this.referencePoint;
/* 354 */     if (point == null)
/*     */     {
/* 356 */       point = this.path.getCurrentPoint();
/* 357 */       if (point == null)
/*     */       {
/* 359 */         point = this.sidebearingPoint;
/*     */       }
/*     */     }
/* 362 */     this.referencePoint = null;
/* 363 */     this.path.moveTo((float)(point.getX() + dx.doubleValue()), (float)(point.getY() + dy.doubleValue()));
/*     */   }
/*     */ 
/*     */   private void hlineTo(List<Integer> numbers)
/*     */   {
/* 369 */     for (int i = 0; i < numbers.size(); i++)
/*     */     {
/* 371 */       if (i % 2 == 0)
/*     */       {
/* 373 */         rlineTo((Number)numbers.get(i), Integer.valueOf(0));
/*     */       }
/*     */       else
/*     */       {
/* 377 */         rlineTo(Integer.valueOf(0), (Number)numbers.get(i));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void vlineTo(List<Integer> numbers)
/*     */   {
/* 384 */     for (int i = 0; i < numbers.size(); i++)
/*     */     {
/* 386 */       if (i % 2 == 0)
/*     */       {
/* 388 */         rlineTo(Integer.valueOf(0), (Number)numbers.get(i));
/*     */       }
/*     */       else
/*     */       {
/* 392 */         rlineTo((Number)numbers.get(i), Integer.valueOf(0));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void rlineTo(Number dx, Number dy)
/*     */   {
/* 399 */     Point2D point = this.path.getCurrentPoint();
/* 400 */     this.path.lineTo((float)(point.getX() + dx.doubleValue()), (float)(point.getY() + dy.doubleValue()));
/*     */   }
/*     */ 
/*     */   private void rrlineTo(List<Integer> numbers)
/*     */   {
/* 406 */     for (int i = 0; i < numbers.size(); i += 2)
/*     */     {
/* 408 */       rlineTo((Number)numbers.get(i), (Number)numbers.get(i + 1));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void rrCurveTo(List<Integer> numbers)
/*     */   {
/* 414 */     if (numbers.size() >= 6)
/*     */     {
/* 416 */       for (int i = 0; i < numbers.size(); i += 6)
/*     */       {
/* 418 */         float x1 = ((Integer)numbers.get(i)).intValue();
/* 419 */         float y1 = ((Integer)numbers.get(i + 1)).intValue();
/* 420 */         float x2 = ((Integer)numbers.get(i + 2)).intValue();
/* 421 */         float y2 = ((Integer)numbers.get(i + 3)).intValue();
/* 422 */         float x3 = ((Integer)numbers.get(i + 4)).intValue();
/* 423 */         float y3 = ((Integer)numbers.get(i + 5)).intValue();
/* 424 */         rrcurveTo(Float.valueOf(x1), Float.valueOf(y1), Float.valueOf(x2), Float.valueOf(y2), Float.valueOf(x3), Float.valueOf(y3));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void rrcurveTo(Number dx1, Number dy1, Number dx2, Number dy2, Number dx3, Number dy3)
/*     */   {
/* 432 */     Point2D point = this.path.getCurrentPoint();
/* 433 */     float x1 = (float)point.getX() + dx1.floatValue();
/* 434 */     float y1 = (float)point.getY() + dy1.floatValue();
/* 435 */     float x2 = x1 + dx2.floatValue();
/* 436 */     float y2 = y1 + dy2.floatValue();
/* 437 */     float x3 = x2 + dx3.floatValue();
/* 438 */     float y3 = y2 + dy3.floatValue();
/* 439 */     this.path.curveTo(x1, y1, x2, y2, x3, y3);
/*     */   }
/*     */ 
/*     */   private void rlineCurve(List<Integer> numbers)
/*     */   {
/* 445 */     if (numbers.size() >= 6)
/*     */     {
/* 447 */       if (numbers.size() - 6 > 0)
/*     */       {
/* 449 */         for (int i = 0; i < numbers.size() - 6; i += 2)
/*     */         {
/* 451 */           if (i + 1 >= numbers.size())
/*     */           {
/*     */             break;
/*     */           }
/* 455 */           rlineTo((Number)numbers.get(i), (Number)numbers.get(i + 1));
/*     */         }
/*     */       }
/* 458 */       float x1 = ((Integer)numbers.get(numbers.size() - 6)).intValue();
/* 459 */       float y1 = ((Integer)numbers.get(numbers.size() - 5)).intValue();
/* 460 */       float x2 = ((Integer)numbers.get(numbers.size() - 4)).intValue();
/* 461 */       float y2 = ((Integer)numbers.get(numbers.size() - 3)).intValue();
/* 462 */       float x3 = ((Integer)numbers.get(numbers.size() - 2)).intValue();
/* 463 */       float y3 = ((Integer)numbers.get(numbers.size() - 1)).intValue();
/* 464 */       rrcurveTo(Float.valueOf(x1), Float.valueOf(y1), Float.valueOf(x2), Float.valueOf(y2), Float.valueOf(x3), Float.valueOf(y3));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void rcurveLine(List<Integer> numbers)
/*     */   {
/* 470 */     for (int i = 0; i < numbers.size(); i += 6)
/*     */     {
/* 472 */       if (numbers.size() - i < 6)
/*     */       {
/*     */         break;
/*     */       }
/* 476 */       float x1 = ((Integer)numbers.get(i)).intValue();
/* 477 */       float y1 = ((Integer)numbers.get(i + 1)).intValue();
/* 478 */       float x2 = ((Integer)numbers.get(i + 2)).intValue();
/* 479 */       float y2 = ((Integer)numbers.get(i + 3)).intValue();
/* 480 */       float x3 = ((Integer)numbers.get(i + 4)).intValue();
/* 481 */       float y3 = ((Integer)numbers.get(i + 5)).intValue();
/* 482 */       rrcurveTo(Float.valueOf(x1), Float.valueOf(y1), Float.valueOf(x2), Float.valueOf(y2), Float.valueOf(x3), Float.valueOf(y3));
/* 483 */       if (numbers.size() - (i + 6) == 2)
/*     */       {
/* 485 */         rlineTo((Number)numbers.get(i + 6), (Number)numbers.get(i + 7));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void rvhCurveTo(List<Integer> numbers)
/*     */   {
/* 492 */     boolean smallCase = numbers.size() <= 5;
/* 493 */     boolean odd = numbers.size() % 2 != 0;
/* 494 */     if (!odd ? numbers.size() % 4 == 0 : (numbers.size() - 1) % 4 == 0)
/*     */     {
/* 496 */       float lastY = -1.0F;
/* 497 */       for (int i = 0; i < numbers.size(); i += 4)
/*     */       {
/* 499 */         if (numbers.size() - i < 4)
/*     */         {
/*     */           break;
/*     */         }
/* 503 */         float x1 = lastY != -1.0F ? ((Integer)numbers.get(i)).intValue() : 0.0F;
/* 504 */         float y1 = lastY != -1.0F ? 0.0F : ((Integer)numbers.get(i)).intValue();
/* 505 */         float x2 = ((Integer)numbers.get(i + 1)).intValue();
/* 506 */         float y2 = ((Integer)numbers.get(i + 2)).intValue();
/* 507 */         float x3 = lastY != -1.0F ? 0.0F : ((Integer)numbers.get(i + 3)).intValue();
/* 508 */         float y3 = lastY != -1.0F ? ((Integer)numbers.get(i + 3)).intValue() : 0.0F;
/* 509 */         if ((odd) && (numbers.size() - i == 5))
/*     */         {
/* 511 */           if (smallCase)
/*     */           {
/* 513 */             y3 = ((Integer)numbers.get(i + 4)).intValue();
/*     */           }
/*     */           else
/*     */           {
/* 517 */             x3 = ((Integer)numbers.get(i + 4)).intValue();
/*     */           }
/*     */         }
/* 520 */         rrcurveTo(Float.valueOf(x1), Float.valueOf(y1), Float.valueOf(x2), Float.valueOf(y2), Float.valueOf(x3), Float.valueOf(y3));
/* 521 */         if (lastY == -1.0F)
/*     */         {
/* 523 */           lastY = 0.0F;
/*     */         }
/*     */         else
/*     */         {
/* 527 */           if (numbers.size() - (i + 4) <= 0)
/*     */             break;
/* 529 */           rvhCurveTo(numbers.subList(i + 4, numbers.size())); break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void rhvCurveTo(List<Integer> numbers)
/*     */   {
/* 539 */     boolean smallCase = numbers.size() <= 5;
/* 540 */     boolean odd = numbers.size() % 2 != 0;
/* 541 */     if (!odd ? numbers.size() % 4 == 0 : (numbers.size() - 1) % 4 == 0)
/*     */     {
/* 543 */       float lastX = -1.0F;
/* 544 */       for (int i = 0; i < numbers.size(); i += 4)
/*     */       {
/* 546 */         if (numbers.size() - i < 4)
/*     */         {
/*     */           break;
/*     */         }
/* 550 */         float x1 = lastX != -1.0F ? 0.0F : ((Integer)numbers.get(i)).intValue();
/* 551 */         float y1 = lastX != -1.0F ? ((Integer)numbers.get(i)).intValue() : 0.0F;
/* 552 */         float x2 = ((Integer)numbers.get(i + 1)).intValue();
/* 553 */         float y2 = ((Integer)numbers.get(i + 2)).intValue();
/* 554 */         float x3 = lastX != -1.0F ? ((Integer)numbers.get(i + 3)).intValue() : 0.0F;
/* 555 */         float y3 = lastX != -1.0F ? 0.0F : ((Integer)numbers.get(i + 3)).intValue();
/* 556 */         if ((odd) && (numbers.size() - i == 5))
/*     */         {
/* 558 */           if (smallCase)
/*     */           {
/* 560 */             x3 = ((Integer)numbers.get(i + 4)).intValue();
/*     */           }
/*     */           else
/*     */           {
/* 564 */             y3 = ((Integer)numbers.get(i + 4)).intValue();
/*     */           }
/*     */         }
/* 567 */         rrcurveTo(Float.valueOf(x1), Float.valueOf(y1), Float.valueOf(x2), Float.valueOf(y2), Float.valueOf(x3), Float.valueOf(y3));
/* 568 */         if (lastX == -1.0F)
/*     */         {
/* 570 */           lastX = 0.0F;
/*     */         }
/*     */         else
/*     */         {
/* 574 */           if (numbers.size() - (i + 4) <= 0)
/*     */             break;
/* 576 */           rhvCurveTo(numbers.subList(i + 4, numbers.size())); break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void rhhCurveTo(List<Integer> numbers)
/*     */   {
/* 586 */     boolean odd = numbers.size() % 2 != 0;
/* 587 */     if (!odd ? numbers.size() % 4 == 0 : (numbers.size() - 1) % 4 == 0)
/*     */     {
/* 589 */       float lastY = -1.0F;
/* 590 */       boolean bHandled = false;
/* 591 */       int increment = odd ? 1 : 0;
/* 592 */       for (int i = 0; i < numbers.size(); i += 4)
/*     */       {
/* 594 */         if (numbers.size() - i < 4)
/*     */         {
/*     */           break;
/*     */         }
/* 598 */         float x1 = ((odd) && (!bHandled) ? (Integer)numbers.get(i + increment) : (Integer)numbers.get(i)).intValue();
/* 599 */         float y1 = (odd) && (!bHandled) ? ((Integer)numbers.get(i)).intValue() : lastY != -1.0F ? lastY : 0.0F;
/* 600 */         float x2 = ((Integer)numbers.get(i + 1 + increment)).intValue();
/* 601 */         float y2 = ((Integer)numbers.get(i + 2 + increment)).intValue();
/* 602 */         float x3 = ((Integer)numbers.get(i + 3 + increment)).intValue();
/* 603 */         float y3 = 0.0F;
/* 604 */         rrcurveTo(Float.valueOf(x1), Float.valueOf(y1), Float.valueOf(x2), Float.valueOf(y2), Float.valueOf(x3), Float.valueOf(y3));
/* 605 */         lastY = 0.0F;
/* 606 */         if ((odd) && (!bHandled))
/*     */         {
/* 608 */           i++;
/* 609 */           bHandled = true;
/*     */         }
/* 611 */         increment = 0;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void rvvCurveTo(List<Integer> numbers)
/*     */   {
/* 618 */     boolean odd = numbers.size() % 2 != 0;
/* 619 */     if (!odd ? numbers.size() % 4 == 0 : (numbers.size() - 1) % 4 == 0)
/*     */     {
/* 621 */       boolean bHandled = false;
/* 622 */       int increment = odd ? 1 : 0;
/* 623 */       for (int i = 0; i < numbers.size(); i += 4)
/*     */       {
/* 625 */         if (numbers.size() - i < 4)
/*     */         {
/*     */           break;
/*     */         }
/* 629 */         float x1 = (odd) && (!bHandled) ? ((Integer)numbers.get(i)).intValue() : 0.0F;
/* 630 */         float y1 = ((Integer)numbers.get(i + increment)).intValue();
/* 631 */         float x2 = ((Integer)numbers.get(i + 1 + increment)).intValue();
/* 632 */         float y2 = ((Integer)numbers.get(i + 2 + increment)).intValue();
/* 633 */         float x3 = 0.0F;
/* 634 */         float y3 = ((Integer)numbers.get(i + 3 + increment)).intValue();
/* 635 */         rrcurveTo(Float.valueOf(x1), Float.valueOf(y1), Float.valueOf(x2), Float.valueOf(y2), Float.valueOf(x3), Float.valueOf(y3));
/* 636 */         if ((odd) && (!bHandled))
/*     */         {
/* 638 */           i++;
/* 639 */           bHandled = true;
/*     */         }
/* 641 */         increment = 0;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void closePath()
/*     */   {
/* 648 */     this.referencePoint = this.path.getCurrentPoint();
/* 649 */     this.path.closePath();
/*     */   }
/*     */ 
/*     */   private void pointSb(Number x, Number y)
/*     */   {
/* 654 */     this.sidebearingPoint = new Point2D.Float(x.floatValue(), y.floatValue());
/*     */   }
/*     */ 
/*     */   public Rectangle2D getBounds()
/*     */   {
/* 663 */     return this.path.getBounds2D();
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/* 672 */     return this.width;
/*     */   }
/*     */ 
/*     */   private void setWidth(int aWidth)
/*     */   {
/* 677 */     this.width = aWidth;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.CharStringRenderer
 * JD-Core Version:    0.6.2
 */