/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.lang.reflect.Field;
/*     */ 
/*     */ public class PageSize
/*     */ {
/*  61 */   public static final Rectangle LETTER = new RectangleReadOnly(612.0F, 792.0F);
/*     */ 
/*  64 */   public static final Rectangle NOTE = new RectangleReadOnly(540.0F, 720.0F);
/*     */ 
/*  67 */   public static final Rectangle LEGAL = new RectangleReadOnly(612.0F, 1008.0F);
/*     */ 
/*  70 */   public static final Rectangle TABLOID = new RectangleReadOnly(792.0F, 1224.0F);
/*     */ 
/*  73 */   public static final Rectangle EXECUTIVE = new RectangleReadOnly(522.0F, 756.0F);
/*     */ 
/*  76 */   public static final Rectangle POSTCARD = new RectangleReadOnly(283.0F, 416.0F);
/*     */ 
/*  79 */   public static final Rectangle A0 = new RectangleReadOnly(2384.0F, 3370.0F);
/*     */ 
/*  82 */   public static final Rectangle A1 = new RectangleReadOnly(1684.0F, 2384.0F);
/*     */ 
/*  85 */   public static final Rectangle A2 = new RectangleReadOnly(1191.0F, 1684.0F);
/*     */ 
/*  88 */   public static final Rectangle A3 = new RectangleReadOnly(842.0F, 1191.0F);
/*     */ 
/*  91 */   public static final Rectangle A4 = new RectangleReadOnly(595.0F, 842.0F);
/*     */ 
/*  94 */   public static final Rectangle A5 = new RectangleReadOnly(420.0F, 595.0F);
/*     */ 
/*  97 */   public static final Rectangle A6 = new RectangleReadOnly(297.0F, 420.0F);
/*     */ 
/* 100 */   public static final Rectangle A7 = new RectangleReadOnly(210.0F, 297.0F);
/*     */ 
/* 103 */   public static final Rectangle A8 = new RectangleReadOnly(148.0F, 210.0F);
/*     */ 
/* 106 */   public static final Rectangle A9 = new RectangleReadOnly(105.0F, 148.0F);
/*     */ 
/* 109 */   public static final Rectangle A10 = new RectangleReadOnly(73.0F, 105.0F);
/*     */ 
/* 112 */   public static final Rectangle B0 = new RectangleReadOnly(2834.0F, 4008.0F);
/*     */ 
/* 115 */   public static final Rectangle B1 = new RectangleReadOnly(2004.0F, 2834.0F);
/*     */ 
/* 118 */   public static final Rectangle B2 = new RectangleReadOnly(1417.0F, 2004.0F);
/*     */ 
/* 121 */   public static final Rectangle B3 = new RectangleReadOnly(1000.0F, 1417.0F);
/*     */ 
/* 124 */   public static final Rectangle B4 = new RectangleReadOnly(708.0F, 1000.0F);
/*     */ 
/* 127 */   public static final Rectangle B5 = new RectangleReadOnly(498.0F, 708.0F);
/*     */ 
/* 130 */   public static final Rectangle B6 = new RectangleReadOnly(354.0F, 498.0F);
/*     */ 
/* 133 */   public static final Rectangle B7 = new RectangleReadOnly(249.0F, 354.0F);
/*     */ 
/* 136 */   public static final Rectangle B8 = new RectangleReadOnly(175.0F, 249.0F);
/*     */ 
/* 139 */   public static final Rectangle B9 = new RectangleReadOnly(124.0F, 175.0F);
/*     */ 
/* 142 */   public static final Rectangle B10 = new RectangleReadOnly(87.0F, 124.0F);
/*     */ 
/* 145 */   public static final Rectangle ARCH_E = new RectangleReadOnly(2592.0F, 3456.0F);
/*     */ 
/* 148 */   public static final Rectangle ARCH_D = new RectangleReadOnly(1728.0F, 2592.0F);
/*     */ 
/* 151 */   public static final Rectangle ARCH_C = new RectangleReadOnly(1296.0F, 1728.0F);
/*     */ 
/* 154 */   public static final Rectangle ARCH_B = new RectangleReadOnly(864.0F, 1296.0F);
/*     */ 
/* 157 */   public static final Rectangle ARCH_A = new RectangleReadOnly(648.0F, 864.0F);
/*     */ 
/* 160 */   public static final Rectangle FLSA = new RectangleReadOnly(612.0F, 936.0F);
/*     */ 
/* 163 */   public static final Rectangle FLSE = new RectangleReadOnly(648.0F, 936.0F);
/*     */ 
/* 166 */   public static final Rectangle HALFLETTER = new RectangleReadOnly(396.0F, 612.0F);
/*     */ 
/* 169 */   public static final Rectangle _11X17 = new RectangleReadOnly(792.0F, 1224.0F);
/*     */ 
/* 172 */   public static final Rectangle ID_1 = new RectangleReadOnly(242.64999F, 153.0F);
/*     */ 
/* 175 */   public static final Rectangle ID_2 = new RectangleReadOnly(297.0F, 210.0F);
/*     */ 
/* 178 */   public static final Rectangle ID_3 = new RectangleReadOnly(354.0F, 249.0F);
/*     */ 
/* 181 */   public static final Rectangle LEDGER = new RectangleReadOnly(1224.0F, 792.0F);
/*     */ 
/* 184 */   public static final Rectangle CROWN_QUARTO = new RectangleReadOnly(535.0F, 697.0F);
/*     */ 
/* 187 */   public static final Rectangle LARGE_CROWN_QUARTO = new RectangleReadOnly(569.0F, 731.0F);
/*     */ 
/* 190 */   public static final Rectangle DEMY_QUARTO = new RectangleReadOnly(620.0F, 782.0F);
/*     */ 
/* 193 */   public static final Rectangle ROYAL_QUARTO = new RectangleReadOnly(671.0F, 884.0F);
/*     */ 
/* 196 */   public static final Rectangle CROWN_OCTAVO = new RectangleReadOnly(348.0F, 527.0F);
/*     */ 
/* 199 */   public static final Rectangle LARGE_CROWN_OCTAVO = new RectangleReadOnly(365.0F, 561.0F);
/*     */ 
/* 202 */   public static final Rectangle DEMY_OCTAVO = new RectangleReadOnly(391.0F, 612.0F);
/*     */ 
/* 205 */   public static final Rectangle ROYAL_OCTAVO = new RectangleReadOnly(442.0F, 663.0F);
/*     */ 
/* 208 */   public static final Rectangle SMALL_PAPERBACK = new RectangleReadOnly(314.0F, 504.0F);
/*     */ 
/* 211 */   public static final Rectangle PENGUIN_SMALL_PAPERBACK = new RectangleReadOnly(314.0F, 513.0F);
/*     */ 
/* 214 */   public static final Rectangle PENGUIN_LARGE_PAPERBACK = new RectangleReadOnly(365.0F, 561.0F);
/*     */ 
/*     */   /** @deprecated */
/* 223 */   public static final Rectangle LETTER_LANDSCAPE = new RectangleReadOnly(612.0F, 792.0F, 90);
/*     */ 
/*     */   /** @deprecated */
/* 230 */   public static final Rectangle LEGAL_LANDSCAPE = new RectangleReadOnly(612.0F, 1008.0F, 90);
/*     */ 
/*     */   /** @deprecated */
/* 237 */   public static final Rectangle A4_LANDSCAPE = new RectangleReadOnly(595.0F, 842.0F, 90);
/*     */ 
/*     */   public static Rectangle getRectangle(String name)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual 2	java/lang/String:trim	()Ljava/lang/String;
/*     */     //   4: invokevirtual 3	java/lang/String:toUpperCase	()Ljava/lang/String;
/*     */     //   7: astore_0
/*     */     //   8: aload_0
/*     */     //   9: bipush 32
/*     */     //   11: invokevirtual 4	java/lang/String:indexOf	(I)I
/*     */     //   14: istore_1
/*     */     //   15: iload_1
/*     */     //   16: iconst_m1
/*     */     //   17: if_icmpne +45 -> 62
/*     */     //   20: ldc_w 5
/*     */     //   23: aload_0
/*     */     //   24: invokevirtual 3	java/lang/String:toUpperCase	()Ljava/lang/String;
/*     */     //   27: invokevirtual 6	java/lang/Class:getDeclaredField	(Ljava/lang/String;)Ljava/lang/reflect/Field;
/*     */     //   30: astore_2
/*     */     //   31: aload_2
/*     */     //   32: aconst_null
/*     */     //   33: invokevirtual 7	java/lang/reflect/Field:get	(Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   36: checkcast 8	com/itextpdf/text/Rectangle
/*     */     //   39: areturn
/*     */     //   40: astore_2
/*     */     //   41: new 10	java/lang/RuntimeException
/*     */     //   44: dup
/*     */     //   45: ldc 11
/*     */     //   47: iconst_1
/*     */     //   48: anewarray 12	java/lang/Object
/*     */     //   51: dup
/*     */     //   52: iconst_0
/*     */     //   53: aload_0
/*     */     //   54: aastore
/*     */     //   55: invokestatic 13	com/itextpdf/text/error_messages/MessageLocalization:getComposedMessage	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   58: invokespecial 14	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
/*     */     //   61: athrow
/*     */     //   62: aload_0
/*     */     //   63: iconst_0
/*     */     //   64: iload_1
/*     */     //   65: invokevirtual 15	java/lang/String:substring	(II)Ljava/lang/String;
/*     */     //   68: astore_2
/*     */     //   69: aload_0
/*     */     //   70: iload_1
/*     */     //   71: iconst_1
/*     */     //   72: iadd
/*     */     //   73: invokevirtual 16	java/lang/String:substring	(I)Ljava/lang/String;
/*     */     //   76: astore_3
/*     */     //   77: new 8	com/itextpdf/text/Rectangle
/*     */     //   80: dup
/*     */     //   81: aload_2
/*     */     //   82: invokestatic 17	java/lang/Float:parseFloat	(Ljava/lang/String;)F
/*     */     //   85: aload_3
/*     */     //   86: invokestatic 17	java/lang/Float:parseFloat	(Ljava/lang/String;)F
/*     */     //   89: invokespecial 18	com/itextpdf/text/Rectangle:<init>	(FF)V
/*     */     //   92: areturn
/*     */     //   93: astore_2
/*     */     //   94: new 10	java/lang/RuntimeException
/*     */     //   97: dup
/*     */     //   98: ldc 19
/*     */     //   100: iconst_2
/*     */     //   101: anewarray 12	java/lang/Object
/*     */     //   104: dup
/*     */     //   105: iconst_0
/*     */     //   106: aload_0
/*     */     //   107: aastore
/*     */     //   108: dup
/*     */     //   109: iconst_1
/*     */     //   110: aload_2
/*     */     //   111: invokevirtual 20	java/lang/Exception:getMessage	()Ljava/lang/String;
/*     */     //   114: aastore
/*     */     //   115: invokestatic 13	com/itextpdf/text/error_messages/MessageLocalization:getComposedMessage	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   118: invokespecial 14	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
/*     */     //   121: athrow
/*     */     //
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   20	39	40	java/lang/Exception
/*     */     //   62	92	93	java/lang/Exception
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.PageSize
 * JD-Core Version:    0.6.2
 */