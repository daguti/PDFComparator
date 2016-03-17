/*     */ package org.apache.fontbox.cff.charset;
/*     */ 
/*     */ public class CFFExpertSubsetCharset extends CFFCharset
/*     */ {
/*  42 */   private static final CFFExpertSubsetCharset INSTANCE = new CFFExpertSubsetCharset();
/*     */ 
/*     */   public static CFFExpertSubsetCharset getInstance()
/*     */   {
/*  39 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  46 */     INSTANCE.register(1, "space");
/*  47 */     INSTANCE.register(13, "comma");
/*  48 */     INSTANCE.register(14, "hyphen");
/*  49 */     INSTANCE.register(15, "period");
/*  50 */     INSTANCE.register(27, "colon");
/*  51 */     INSTANCE.register(28, "semicolon");
/*  52 */     INSTANCE.register(99, "fraction");
/*  53 */     INSTANCE.register(109, "fi");
/*  54 */     INSTANCE.register(110, "fl");
/*  55 */     INSTANCE.register(150, "onesuperior");
/*  56 */     INSTANCE.register(155, "onehalf");
/*  57 */     INSTANCE.register(158, "onequarter");
/*  58 */     INSTANCE.register(163, "threequarters");
/*  59 */     INSTANCE.register(164, "twosuperior");
/*  60 */     INSTANCE.register(169, "threesuperior");
/*  61 */     INSTANCE.register(231, "dollaroldstyle");
/*  62 */     INSTANCE.register(232, "dollarsuperior");
/*  63 */     INSTANCE.register(235, "parenleftsuperior");
/*  64 */     INSTANCE.register(236, "parenrightsuperior");
/*  65 */     INSTANCE.register(237, "twodotenleader");
/*  66 */     INSTANCE.register(238, "onedotenleader");
/*  67 */     INSTANCE.register(239, "zerooldstyle");
/*  68 */     INSTANCE.register(240, "oneoldstyle");
/*  69 */     INSTANCE.register(241, "twooldstyle");
/*  70 */     INSTANCE.register(242, "threeoldstyle");
/*  71 */     INSTANCE.register(243, "fouroldstyle");
/*  72 */     INSTANCE.register(244, "fiveoldstyle");
/*  73 */     INSTANCE.register(245, "sixoldstyle");
/*  74 */     INSTANCE.register(246, "sevenoldstyle");
/*  75 */     INSTANCE.register(247, "eightoldstyle");
/*  76 */     INSTANCE.register(248, "nineoldstyle");
/*  77 */     INSTANCE.register(249, "commasuperior");
/*  78 */     INSTANCE.register(250, "threequartersemdash");
/*  79 */     INSTANCE.register(251, "periodsuperior");
/*  80 */     INSTANCE.register(253, "asuperior");
/*  81 */     INSTANCE.register(254, "bsuperior");
/*  82 */     INSTANCE.register(255, "centsuperior");
/*  83 */     INSTANCE.register(256, "dsuperior");
/*  84 */     INSTANCE.register(257, "esuperior");
/*  85 */     INSTANCE.register(258, "isuperior");
/*  86 */     INSTANCE.register(259, "lsuperior");
/*  87 */     INSTANCE.register(260, "msuperior");
/*  88 */     INSTANCE.register(261, "nsuperior");
/*  89 */     INSTANCE.register(262, "osuperior");
/*  90 */     INSTANCE.register(263, "rsuperior");
/*  91 */     INSTANCE.register(264, "ssuperior");
/*  92 */     INSTANCE.register(265, "tsuperior");
/*  93 */     INSTANCE.register(266, "ff");
/*  94 */     INSTANCE.register(267, "ffi");
/*  95 */     INSTANCE.register(268, "ffl");
/*  96 */     INSTANCE.register(269, "parenleftinferior");
/*  97 */     INSTANCE.register(270, "parenrightinferior");
/*  98 */     INSTANCE.register(272, "hyphensuperior");
/*  99 */     INSTANCE.register(300, "colonmonetary");
/* 100 */     INSTANCE.register(301, "onefitted");
/* 101 */     INSTANCE.register(302, "rupiah");
/* 102 */     INSTANCE.register(305, "centoldstyle");
/* 103 */     INSTANCE.register(314, "figuredash");
/* 104 */     INSTANCE.register(315, "hypheninferior");
/* 105 */     INSTANCE.register(320, "oneeighth");
/* 106 */     INSTANCE.register(321, "threeeighths");
/* 107 */     INSTANCE.register(322, "fiveeighths");
/* 108 */     INSTANCE.register(323, "seveneighths");
/* 109 */     INSTANCE.register(324, "onethird");
/* 110 */     INSTANCE.register(325, "twothirds");
/* 111 */     INSTANCE.register(326, "zerosuperior");
/* 112 */     INSTANCE.register(327, "foursuperior");
/* 113 */     INSTANCE.register(328, "fivesuperior");
/* 114 */     INSTANCE.register(329, "sixsuperior");
/* 115 */     INSTANCE.register(330, "sevensuperior");
/* 116 */     INSTANCE.register(331, "eightsuperior");
/* 117 */     INSTANCE.register(332, "ninesuperior");
/* 118 */     INSTANCE.register(333, "zeroinferior");
/* 119 */     INSTANCE.register(334, "oneinferior");
/* 120 */     INSTANCE.register(335, "twoinferior");
/* 121 */     INSTANCE.register(336, "threeinferior");
/* 122 */     INSTANCE.register(337, "fourinferior");
/* 123 */     INSTANCE.register(338, "fiveinferior");
/* 124 */     INSTANCE.register(339, "sixinferior");
/* 125 */     INSTANCE.register(340, "seveninferior");
/* 126 */     INSTANCE.register(341, "eightinferior");
/* 127 */     INSTANCE.register(342, "nineinferior");
/* 128 */     INSTANCE.register(343, "centinferior");
/* 129 */     INSTANCE.register(344, "dollarinferior");
/* 130 */     INSTANCE.register(345, "periodinferior");
/* 131 */     INSTANCE.register(346, "commainferior");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.charset.CFFExpertSubsetCharset
 * JD-Core Version:    0.6.2
 */