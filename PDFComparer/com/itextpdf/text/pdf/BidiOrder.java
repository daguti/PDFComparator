/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ 
/*      */ public final class BidiOrder
/*      */ {
/*      */   private byte[] initialTypes;
/*      */   private byte[] embeddings;
/*  159 */   private byte paragraphEmbeddingLevel = -1;
/*      */   private int textLength;
/*      */   private byte[] resultTypes;
/*      */   private byte[] resultLevels;
/*      */   public static final byte L = 0;
/*      */   public static final byte LRE = 1;
/*      */   public static final byte LRO = 2;
/*      */   public static final byte R = 3;
/*      */   public static final byte AL = 4;
/*      */   public static final byte RLE = 5;
/*      */   public static final byte RLO = 6;
/*      */   public static final byte PDF = 7;
/*      */   public static final byte EN = 8;
/*      */   public static final byte ES = 9;
/*      */   public static final byte ET = 10;
/*      */   public static final byte AN = 11;
/*      */   public static final byte CS = 12;
/*      */   public static final byte NSM = 13;
/*      */   public static final byte BN = 14;
/*      */   public static final byte B = 15;
/*      */   public static final byte S = 16;
/*      */   public static final byte WS = 17;
/*      */   public static final byte ON = 18;
/*      */   public static final byte TYPE_MIN = 0;
/*      */   public static final byte TYPE_MAX = 18;
/* 1139 */   private static final byte[] rtypes = new byte[65536];
/*      */ 
/* 1141 */   private static char[] baseTypes = { '\000', '\b', '\016', '\t', '\t', '\020', '\n', '\n', '\017', '\013', '\013', '\020', '\f', '\f', '\021', '\r', '\r', '\017', '\016', '\033', '\016', '\034', '\036', '\017', '\037', '\037', '\020', ' ', ' ', '\021', '!', '"', '\022', '#', '%', '\n', '&', '*', '\022', '+', '+', '\n', ',', ',', '\f', '-', '-', '\n', '.', '.', '\f', '/', '/', '\t', '0', '9', '\b', ':', ':', '\f', ';', '@', '\022', 'A', 'Z', '\000', '[', '`', '\022', 'a', 'z', '\000', '{', '~', '\022', '', '', '\016', '', '', '\017', '', '', '\016', ' ', ' ', '\f', '¡', '¡', '\022', '¢', '¥', '\n', '¦', '©', '\022', 'ª', 'ª', '\000', '«', '¯', '\022', '°', '±', '\n', '²', '³', '\b', '´', '´', '\022', 'µ', 'µ', '\000', '¶', '¸', '\022', '¹', '¹', '\b', 'º', 'º', '\000', '»', '¿', '\022', 'À', 'Ö', '\000', '×', '×', '\022', 'Ø', 'ö', '\000', '÷', '÷', '\022', 'ø', 'ʸ', '\000', 'ʹ', 'ʺ', '\022', 'ʻ', 'ˁ', '\000', '˂', 'ˏ', '\022', 'ː', 'ˑ', '\000', '˒', '˟', '\022', 'ˠ', 'ˤ', '\000', '˥', '˭', '\022', 'ˮ', 'ˮ', '\000', '˯', '˿', '\022', '̀', '͗', '\r', '͘', '͜', '\000', '͝', 'ͯ', '\r', 'Ͱ', 'ͳ', '\000', 'ʹ', '͵', '\022', 'Ͷ', 'ͽ', '\000', ';', ';', '\022', 'Ϳ', '΃', '\000', '΄', '΅', '\022', 'Ά', 'Ά', '\000', '·', '·', '\022', 'Έ', 'ϵ', '\000', '϶', '϶', '\022', 'Ϸ', '҂', '\000', '҃', '҆', '\r', '҇', '҇', '\000', '҈', '҉', '\r', 'Ҋ', '։', '\000', '֊', '֊', '\022', '֋', '֐', '\000', '֑', '֡', '\r', '֢', '֢', '\000', '֣', 'ֹ', '\r', 'ֺ', 'ֺ', '\000', 'ֻ', 'ֽ', '\r', '־', '־', '\003', 'ֿ', 'ֿ', '\r', '׀', '׀', '\003', 'ׁ', 'ׂ', '\r', '׃', '׃', '\003', 'ׄ', 'ׄ', '\r', 'ׅ', '׏', '\000', 'א', 'ת', '\003', '׫', 'ׯ', '\000', 'װ', '״', '\003', '׵', '׿', '\000', '؀', '؃', '\004', '؄', '؋', '\000', '،', '،', '\f', '؍', '؍', '\004', '؎', '؏', '\022', 'ؐ', 'ؕ', '\r', 'ؖ', 'ؚ', '\000', '؛', '؛', '\004', '؜', '؞', '\000', '؟', '؟', '\004', 'ؠ', 'ؠ', '\000', 'ء', 'غ', '\004', 'ػ', 'ؿ', '\000', 'ـ', 'ي', '\004', 'ً', '٘', '\r', 'ٙ', 'ٟ', '\000', '٠', '٩', '\013', '٪', '٪', '\n', '٫', '٬', '\013', '٭', 'ٯ', '\004', 'ٰ', 'ٰ', '\r', 'ٱ', 'ە', '\004', 'ۖ', 'ۜ', '\r', '۝', '۝', '\004', '۞', 'ۤ', '\r', 'ۥ', 'ۦ', '\004', 'ۧ', 'ۨ', '\r', '۩', '۩', '\022', '۪', 'ۭ', '\r', 'ۮ', 'ۯ', '\004', '۰', '۹', '\b', 'ۺ', '܍', '\004', '܎', '܎', '\000', '܏', '܏', '\016', 'ܐ', 'ܐ', '\004', 'ܑ', 'ܑ', '\r', 'ܒ', 'ܯ', '\004', 'ܰ', '݊', '\r', '݋', '݌', '\000', 'ݍ', 'ݏ', '\004', 'ݐ', 'ݿ', '\000', 'ހ', 'ޥ', '\004', 'ަ', 'ް', '\r', 'ޱ', 'ޱ', '\004', '޲', 'ऀ', '\000', 'ँ', 'ं', '\r', 'ः', 'ऻ', '\000', '़', '़', '\r', 'ऽ', 'ी', '\000', 'ु', 'ै', '\r', 'ॉ', 'ौ', '\000', '्', '्', '\r', 'ॎ', 'ॐ', '\000', '॑', '॔', '\r', 'ॕ', 'ॡ', '\000', 'ॢ', 'ॣ', '\r', '।', 'ঀ', '\000', 'ঁ', 'ঁ', '\r', 'ং', '঻', '\000', '়', '়', '\r', 'ঽ', 'ী', '\000', 'ু', 'ৄ', '\r', '৅', 'ৌ', '\000', '্', '্', '\r', 'ৎ', 'ৡ', '\000', 'ৢ', 'ৣ', '\r', '৤', 'ৱ', '\000', '৲', '৳', '\n', '৴', '਀', '\000', 'ਁ', 'ਂ', '\r', 'ਃ', '਻', '\000', '਼', '਼', '\r', '਽', 'ੀ', '\000', 'ੁ', 'ੂ', '\r', '੃', '੆', '\000', 'ੇ', 'ੈ', '\r', '੉', '੊', '\000', 'ੋ', '੍', '\r', '੎', '੯', '\000', 'ੰ', 'ੱ', '\r', 'ੲ', '઀', '\000', 'ઁ', 'ં', '\r', 'ઃ', '઻', '\000', '઼', '઼', '\r', 'ઽ', 'ી', '\000', 'ુ', 'ૅ', '\r', '૆', '૆', '\000', 'ે', 'ૈ', '\r', 'ૉ', 'ૌ', '\000', '્', '્', '\r', '૎', 'ૡ', '\000', 'ૢ', 'ૣ', '\r', '૤', '૰', '\000', '૱', '૱', '\n', '૲', '଀', '\000', 'ଁ', 'ଁ', '\r', 'ଂ', '଻', '\000', '଼', '଼', '\r', 'ଽ', 'ା', '\000', 'ି', 'ି', '\r', 'ୀ', 'ୀ', '\000', 'ୁ', 'ୃ', '\r', 'ୄ', 'ୌ', '\000', '୍', '୍', '\r', '୎', '୕', '\000', 'ୖ', 'ୖ', '\r', 'ୗ', '஁', '\000', 'ஂ', 'ஂ', '\r', 'ஃ', 'ி', '\000', 'ீ', 'ீ', '\r', 'ு', 'ௌ', '\000', '்', '்', '\r', '௎', '௲', '\000', '௳', '௸', '\022', '௹', '௹', '\n', '௺', '௺', '\022', '௻', 'ఽ', '\000', 'ా', 'ీ', '\r', 'ు', '౅', '\000', 'ె', 'ై', '\r', '౉', '౉', '\000', 'ొ', '్', '\r', '౎', '౔', '\000', 'ౕ', 'ౖ', '\r', '౗', '಻', '\000', '಼', '಼', '\r', 'ಽ', 'ೋ', '\000', 'ೌ', '್', '\r', '೎', 'ീ', '\000', 'ു', 'ൃ', '\r', 'ൄ', 'ൌ', '\000', '്', '്', '\r', 'ൎ', '෉', '\000', '්', '්', '\r', '෋', 'ෑ', '\000', 'ි', 'ු', '\r', '෕', '෕', '\000', 'ූ', 'ූ', '\r', '෗', 'ะ', '\000', 'ั', 'ั', '\r', 'า', 'ำ', '\000', 'ิ', 'ฺ', '\r', '฻', '฾', '\000', '฿', '฿', '\n', 'เ', 'ๆ', '\000', '็', '๎', '\r', '๏', 'ະ', '\000', 'ັ', 'ັ', '\r', 'າ', 'ຳ', '\000', 'ິ', 'ູ', '\r', '຺', '຺', '\000', 'ົ', 'ຼ', '\r', 'ຽ', '໇', '\000', '່', 'ໍ', '\r', '໎', '༗', '\000', '༘', '༙', '\r', '༚', '༴', '\000', '༵', '༵', '\r', '༶', '༶', '\000', '༷', '༷', '\r', '༸', '༸', '\000', '༹', '༹', '\r', '༺', '༽', '\022', '༾', '཰', '\000', 'ཱ', 'ཾ', '\r', 'ཿ', 'ཿ', '\000', 'ྀ', '྄', '\r', '྅', '྅', '\000', '྆', '྇', '\r', 'ྈ', 'ྏ', '\000', 'ྐ', 'ྗ', '\r', '྘', '྘', '\000', 'ྙ', 'ྼ', '\r', '྽', '࿅', '\000', '࿆', '࿆', '\r', '࿇', 'ာ', '\000', 'ိ', 'ူ', '\r', 'ေ', 'ေ', '\000', 'ဲ', 'ဲ', '\r', 'ဳ', 'ဵ', '\000', 'ံ', '့', '\r', 'း', 'း', '\000', '္', '္', '\r', '်', 'ၗ', '\000', 'ၘ', 'ၙ', '\r', 'ၚ', 'ᙿ', '\000', ' ', ' ', '\021', 'ᚁ', 'ᚚ', '\000', '᚛', '᚜', '\022', '᚝', 'ᜑ', '\000', 'ᜒ', '᜔', '\r', '᜕', 'ᜱ', '\000', 'ᜲ', '᜴', '\r', '᜵', 'ᝑ', '\000', 'ᝒ', 'ᝓ', '\r', '᝔', '᝱', '\000', 'ᝲ', 'ᝳ', '\r', '᝴', 'ា', '\000', 'ិ', 'ួ', '\r', 'ើ', 'ៅ', '\000', 'ំ', 'ំ', '\r', 'ះ', 'ៈ', '\000', '៉', '៓', '\r', '។', '៚', '\000', '៛', '៛', '\n', 'ៜ', 'ៜ', '\000', '៝', '៝', '\r', '៞', '៯', '\000', '៰', '៹', '\022', '៺', '៿', '\000', '᠀', '᠊', '\022', '᠋', '᠍', '\r', '᠎', '᠎', '\021', '᠏', 'ᢨ', '\000', 'ᢩ', 'ᢩ', '\r', 'ᢪ', '᤟', '\000', 'ᤠ', 'ᤢ', '\r', 'ᤣ', 'ᤦ', '\000', 'ᤧ', 'ᤫ', '\r', '᤬', 'ᤱ', '\000', 'ᤲ', 'ᤲ', '\r', 'ᤳ', 'ᤸ', '\000', '᤹', '᤻', '\r', '᤼', '᤿', '\000', '᥀', '᥀', '\022', '᥁', '᥃', '\000', '᥄', '᥅', '\022', '᥆', '᧟', '\000', '᧠', '᧿', '\022', 'ᨀ', 'ᾼ', '\000', '᾽', '᾽', '\022', 'ι', 'ι', '\000', '᾿', '῁', '\022', 'ῂ', 'ῌ', '\000', '῍', '῏', '\022', 'ῐ', '῜', '\000', '῝', '῟', '\022', 'ῠ', 'Ῥ', '\000', '῭', '`', '\022', '῰', 'ῼ', '\000', '´', '῾', '\022', '῿', '῿', '\000', ' ', ' ', '\021', '​', '‍', '\016', '‎', '‎', '\000', '‏', '‏', '\003', '‐', '‧', '\022', ' ', ' ', '\021', ' ', ' ', '\017', '‪', '‪', '\001', '‫', '‫', '\005', '‬', '‬', '\007', '‭', '‭', '\002', '‮', '‮', '\006', ' ', ' ', '\021', '‰', '‴', '\n', '‵', '⁔', '\022', '⁕', '⁖', '\000', '⁗', '⁗', '\022', '⁘', '⁞', '\000', ' ', ' ', '\021', '⁠', '⁣', '\016', '⁤', '⁩', '\000', '⁪', '⁯', '\016', '⁰', '⁰', '\b', 'ⁱ', '⁳', '\000', '⁴', '⁹', '\b', '⁺', '⁻', '\n', '⁼', '⁾', '\022', 'ⁿ', 'ⁿ', '\000', '₀', '₉', '\b', '₊', '₋', '\n', '₌', '₎', '\022', '₏', '₟', '\000', '₠', '₱', '\n', '₲', '⃏', '\000', '⃐', '⃪', '\r', '⃫', '⃿', '\000', '℀', '℁', '\022', 'ℂ', 'ℂ', '\000', '℃', '℆', '\022', 'ℇ', 'ℇ', '\000', '℈', '℉', '\022', 'ℊ', 'ℓ', '\000', '℔', '℔', '\022', 'ℕ', 'ℕ', '\000', '№', '℘', '\022', 'ℙ', 'ℝ', '\000', '℞', '℣', '\022', 'ℤ', 'ℤ', '\000', '℥', '℥', '\022', 'Ω', 'Ω', '\000', '℧', '℧', '\022', 'ℨ', 'ℨ', '\000', '℩', '℩', '\022', 'K', 'ℭ', '\000', '℮', '℮', '\n', 'ℯ', 'ℱ', '\000', 'Ⅎ', 'Ⅎ', '\022', 'ℳ', 'ℹ', '\000', '℺', '℻', '\022', 'ℼ', 'ℿ', '\000', '⅀', '⅄', '\022', 'ⅅ', 'ⅉ', '\000', '⅊', '⅋', '\022', '⅌', '⅒', '\000', '⅓', '⅟', '\022', 'Ⅰ', '↏', '\000', '←', '∑', '\022', '−', '∓', '\n', '∔', '⌵', '\022', '⌶', '⍺', '\000', '⍻', '⎔', '\022', '⎕', '⎕', '\000', '⎖', '⏐', '\022', '⏑', '⏿', '\000', '␀', '␦', '\022', '␧', '␿', '\000', '⑀', '⑊', '\022', '⑋', '⑟', '\000', '①', '⒛', '\b', '⒜', 'ⓩ', '\000', '⓪', '⓪', '\b', '⓫', '☗', '\022', '☘', '☘', '\000', '☙', '♽', '\022', '♾', '♿', '\000', '⚀', '⚑', '\022', '⚒', '⚟', '\000', '⚠', '⚡', '\022', '⚢', '✀', '\000', '✁', '✄', '\022', '✅', '✅', '\000', '✆', '✉', '\022', '✊', '✋', '\000', '✌', '✧', '\022', '✨', '✨', '\000', '✩', '❋', '\022', '❌', '❌', '\000', '❍', '❍', '\022', '❎', '❎', '\000', '❏', '❒', '\022', '❓', '❕', '\000', '❖', '❖', '\022', '❗', '❗', '\000', '❘', '❞', '\022', '❟', '❠', '\000', '❡', '➔', '\022', '➕', '➗', '\000', '➘', '➯', '\022', '➰', '➰', '\000', '➱', '➾', '\022', '➿', '⟏', '\000', '⟐', '⟫', '\022', '⟬', '⟯', '\000', '⟰', '⬍', '\022', '⬎', '⹿', '\000', '⺀', '⺙', '\022', '⺚', '⺚', '\000', '⺛', '⻳', '\022', '⻴', '⻿', '\000', '⼀', '⿕', '\022', '⿖', '⿯', '\000', '⿰', '⿻', '\022', '⿼', '⿿', '\000', '　', '　', '\021', '、', '〄', '\022', '々', '〇', '\000', '〈', '〠', '\022', '〡', '〩', '\000', '〪', '〯', '\r', '〰', '〰', '\022', '〱', '〵', '\000', '〶', '〷', '\022', '〸', '〼', '\000', '〽', '〿', '\022', '぀', '゘', '\000', '゙', '゚', '\r', '゛', '゜', '\022', 'ゝ', 'ゟ', '\000', '゠', '゠', '\022', 'ァ', 'ヺ', '\000', '・', '・', '\022', 'ー', '㈜', '\000', '㈝', '㈞', '\022', '㈟', '㉏', '\000', '㉐', '㉟', '\022', '㉠', '㉻', '\000', '㉼', '㉽', '\022', '㉾', '㊰', '\000', '㊱', '㊿', '\022', '㋀', '㋋', '\000', '㋌', '㋏', '\022', '㋐', '㍶', '\000', '㍷', '㍺', '\022', '㍻', '㏝', '\000', '㏞', '㏟', '\022', '㏠', '㏾', '\000', '㏿', '㏿', '\022', '㐀', '䶿', '\000', '䷀', '䷿', '\022', '一', 42127, '\000', 42128, 42182, '\022', 42183, 64284, '\000', 64285, 64285, '\003', 64286, 64286, '\r', 64287, 64296, '\003', 64297, 64297, '\n', 64298, 64310, '\003', 64311, 64311, '\000', 64312, 64316, '\003', 64317, 64317, '\000', 64318, 64318, '\003', 64319, 64319, '\000', 64320, 64321, '\003', 64322, 64322, '\000', 64323, 64324, '\003', 64325, 64325, '\000', 64326, 64335, '\003', 64336, 64433, '\004', 64434, 64466, '\000', 64467, 64829, '\004', 64830, 64831, '\022', 64832, 64847, '\000', 64848, 64911, '\004', 64912, 64913, '\000', 64914, 64967, '\004', 64968, 65007, '\000', 65008, 65020, '\004', 65021, 65021, '\022', 65022, 65023, '\000', 65024, 65039, '\r', 65040, 65055, '\000', 65056, 65059, '\r', 65060, 65071, '\000', 65072, 65103, '\022', 65104, 65104, '\f', 65105, 65105, '\022', 65106, 65106, '\f', 65107, 65107, '\000', 65108, 65108, '\022', 65109, 65109, '\f', 65110, 65118, '\022', 65119, 65119, '\n', 65120, 65121, '\022', 65122, 65123, '\n', 65124, 65126, '\022', 65127, 65127, '\000', 65128, 65128, '\022', 65129, 65130, '\n', 65131, 65131, '\022', 65132, 65135, '\000', 65136, 65140, '\004', 65141, 65141, '\000', 65142, 65276, '\004', 65277, 65278, '\000', 65279, 65279, '\016', 65280, 65280, '\000', 65281, 65282, '\022', 65283, 65285, '\n', 65286, 65290, '\022', 65291, 65291, '\n', 65292, 65292, '\f', 65293, 65293, '\n', 65294, 65294, '\f', 65295, 65295, '\t', 65296, 65305, '\b', 65306, 65306, '\f', 65307, 65312, '\022', 65313, 65338, '\000', 65339, 65344, '\022', 65345, 65370, '\000', 65371, 65381, '\022', 65382, 65503, '\000', 65504, 65505, '\n', 65506, 65508, '\022', 65509, 65510, '\n', 65511, 65511, '\000', 65512, 65518, '\022', 65519, 65528, '\000', 65529, 65531, '\016', 65532, 65533, '\022', 65534, 65535, '\000' };
/*      */ 
/*      */   public BidiOrder(byte[] types)
/*      */   {
/*  241 */     validateTypes(types);
/*      */ 
/*  243 */     this.initialTypes = ((byte[])types.clone());
/*      */ 
/*  245 */     runAlgorithm();
/*      */   }
/*      */ 
/*      */   public BidiOrder(byte[] types, byte paragraphEmbeddingLevel)
/*      */   {
/*  257 */     validateTypes(types);
/*  258 */     validateParagraphEmbeddingLevel(paragraphEmbeddingLevel);
/*      */ 
/*  260 */     this.initialTypes = ((byte[])types.clone());
/*  261 */     this.paragraphEmbeddingLevel = paragraphEmbeddingLevel;
/*      */ 
/*  263 */     runAlgorithm();
/*      */   }
/*      */ 
/*      */   public BidiOrder(char[] text, int offset, int length, byte paragraphEmbeddingLevel) {
/*  267 */     this.initialTypes = new byte[length];
/*  268 */     for (int k = 0; k < length; k++) {
/*  269 */       this.initialTypes[k] = rtypes[text[(offset + k)]];
/*      */     }
/*  271 */     validateParagraphEmbeddingLevel(paragraphEmbeddingLevel);
/*      */ 
/*  273 */     this.paragraphEmbeddingLevel = paragraphEmbeddingLevel;
/*      */ 
/*  275 */     runAlgorithm();
/*      */   }
/*      */ 
/*      */   public static final byte getDirection(char c) {
/*  279 */     return rtypes[c];
/*      */   }
/*      */ 
/*      */   private void runAlgorithm()
/*      */   {
/*  288 */     this.textLength = this.initialTypes.length;
/*      */ 
/*  292 */     this.resultTypes = ((byte[])this.initialTypes.clone());
/*      */ 
/*  299 */     if (this.paragraphEmbeddingLevel == -1) {
/*  300 */       determineParagraphEmbeddingLevel();
/*      */     }
/*      */ 
/*  304 */     this.resultLevels = new byte[this.textLength];
/*  305 */     setLevels(0, this.textLength, this.paragraphEmbeddingLevel);
/*      */ 
/*  309 */     determineExplicitEmbeddingLevels();
/*      */ 
/*  312 */     this.textLength = removeExplicitCodes();
/*      */ 
/*  316 */     byte prevLevel = this.paragraphEmbeddingLevel;
/*  317 */     int start = 0;
/*  318 */     while (start < this.textLength) {
/*  319 */       byte level = this.resultLevels[start];
/*  320 */       byte prevType = typeForLevel(Math.max(prevLevel, level));
/*      */ 
/*  322 */       int limit = start + 1;
/*  323 */       while ((limit < this.textLength) && (this.resultLevels[limit] == level)) {
/*  324 */         limit++;
/*      */       }
/*      */ 
/*  327 */       byte succLevel = limit < this.textLength ? this.resultLevels[limit] : this.paragraphEmbeddingLevel;
/*  328 */       byte succType = typeForLevel(Math.max(succLevel, level));
/*      */ 
/*  332 */       resolveWeakTypes(start, limit, level, prevType, succType);
/*      */ 
/*  336 */       resolveNeutralTypes(start, limit, level, prevType, succType);
/*      */ 
/*  340 */       resolveImplicitLevels(start, limit, level, prevType, succType);
/*      */ 
/*  342 */       prevLevel = level;
/*  343 */       start = limit;
/*      */     }
/*      */ 
/*  351 */     this.textLength = reinsertExplicitCodes(this.textLength);
/*      */   }
/*      */ 
/*      */   private void determineParagraphEmbeddingLevel()
/*      */   {
/*  362 */     byte strongType = -1;
/*      */ 
/*  365 */     for (int i = 0; i < this.textLength; i++) {
/*  366 */       byte t = this.resultTypes[i];
/*  367 */       if ((t == 0) || (t == 4) || (t == 3)) {
/*  368 */         strongType = t;
/*  369 */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  374 */     if (strongType == -1)
/*      */     {
/*  376 */       this.paragraphEmbeddingLevel = 0;
/*  377 */     } else if (strongType == 0)
/*  378 */       this.paragraphEmbeddingLevel = 0;
/*      */     else
/*  380 */       this.paragraphEmbeddingLevel = 1;
/*      */   }
/*      */ 
/*      */   private void determineExplicitEmbeddingLevels()
/*      */   {
/*  393 */     this.embeddings = processEmbeddings(this.resultTypes, this.paragraphEmbeddingLevel);
/*      */ 
/*  395 */     for (int i = 0; i < this.textLength; i++) {
/*  396 */       byte level = this.embeddings[i];
/*  397 */       if ((level & 0x80) != 0) {
/*  398 */         level = (byte)(level & 0x7F);
/*  399 */         this.resultTypes[i] = typeForLevel(level);
/*      */       }
/*  401 */       this.resultLevels[i] = level;
/*      */     }
/*      */   }
/*      */ 
/*      */   private int removeExplicitCodes()
/*      */   {
/*  413 */     int w = 0;
/*  414 */     for (int i = 0; i < this.textLength; i++) {
/*  415 */       byte t = this.initialTypes[i];
/*  416 */       if ((t != 1) && (t != 5) && (t != 2) && (t != 6) && (t != 7) && (t != 14)) {
/*  417 */         this.embeddings[w] = this.embeddings[i];
/*  418 */         this.resultTypes[w] = this.resultTypes[i];
/*  419 */         this.resultLevels[w] = this.resultLevels[i];
/*  420 */         w++;
/*      */       }
/*      */     }
/*  423 */     return w;
/*      */   }
/*      */ 
/*      */   private int reinsertExplicitCodes(int textLength)
/*      */   {
/*  437 */     int i = this.initialTypes.length;
/*      */     while (true) { i--; if (i < 0) break;
/*  438 */       byte t = this.initialTypes[i];
/*  439 */       if ((t == 1) || (t == 5) || (t == 2) || (t == 6) || (t == 7) || (t == 14)) {
/*  440 */         this.embeddings[i] = 0;
/*  441 */         this.resultTypes[i] = t;
/*  442 */         this.resultLevels[i] = -1;
/*      */       } else {
/*  444 */         textLength--;
/*  445 */         this.embeddings[i] = this.embeddings[textLength];
/*  446 */         this.resultTypes[i] = this.resultTypes[textLength];
/*  447 */         this.resultLevels[i] = this.resultLevels[textLength];
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  455 */     if (this.resultLevels[0] == -1) {
/*  456 */       this.resultLevels[0] = this.paragraphEmbeddingLevel;
/*      */     }
/*  458 */     for (int i = 1; i < this.initialTypes.length; i++) {
/*  459 */       if (this.resultLevels[i] == -1) {
/*  460 */         this.resultLevels[i] = this.resultLevels[(i - 1)];
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  467 */     return this.initialTypes.length;
/*      */   }
/*      */ 
/*      */   private static byte[] processEmbeddings(byte[] resultTypes, byte paragraphEmbeddingLevel)
/*      */   {
/*  480 */     int EXPLICIT_LEVEL_LIMIT = 62;
/*      */ 
/*  482 */     int textLength = resultTypes.length;
/*  483 */     byte[] embeddings = new byte[textLength];
/*      */ 
/*  487 */     byte[] embeddingValueStack = new byte[62];
/*  488 */     int stackCounter = 0;
/*      */ 
/*  495 */     int overflowAlmostCounter = 0;
/*      */ 
/*  499 */     int overflowCounter = 0;
/*      */ 
/*  504 */     byte currentEmbeddingLevel = paragraphEmbeddingLevel;
/*  505 */     byte currentEmbeddingValue = paragraphEmbeddingLevel;
/*      */ 
/*  508 */     for (int i = 0; i < textLength; i++)
/*      */     {
/*  510 */       embeddings[i] = currentEmbeddingValue;
/*      */ 
/*  512 */       byte t = resultTypes[i];
/*      */ 
/*  515 */       switch (t)
/*      */       {
/*      */       case 1:
/*      */       case 2:
/*      */       case 5:
/*      */       case 6:
/*  521 */         if (overflowCounter == 0)
/*      */         {
/*      */           byte newLevel;
/*      */           byte newLevel;
/*  523 */           if ((t == 5) || (t == 6))
/*  524 */             newLevel = (byte)(currentEmbeddingLevel + 1 | 0x1);
/*      */           else {
/*  526 */             newLevel = (byte)(currentEmbeddingLevel + 2 & 0xFFFFFFFE);
/*      */           }
/*      */ 
/*  531 */           if (newLevel < 62) {
/*  532 */             embeddingValueStack[stackCounter] = currentEmbeddingValue;
/*  533 */             stackCounter++;
/*      */ 
/*  535 */             currentEmbeddingLevel = newLevel;
/*  536 */             if ((t == 2) || (t == 6))
/*  537 */               currentEmbeddingValue = (byte)(newLevel | 0x80);
/*      */             else {
/*  539 */               currentEmbeddingValue = newLevel;
/*      */             }
/*      */ 
/*  544 */             embeddings[i] = currentEmbeddingValue;
/*  545 */             continue;
/*      */           }
/*      */ 
/*  551 */           if (currentEmbeddingLevel == 60) {
/*  552 */             overflowAlmostCounter++;
/*  553 */             continue;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  558 */         overflowCounter++;
/*  559 */         break;
/*      */       case 7:
/*  568 */         if (overflowCounter > 0) {
/*  569 */           overflowCounter--;
/*  570 */         } else if ((overflowAlmostCounter > 0) && (currentEmbeddingLevel != 61)) {
/*  571 */           overflowAlmostCounter--;
/*  572 */         } else if (stackCounter > 0) {
/*  573 */           stackCounter--;
/*  574 */           currentEmbeddingValue = embeddingValueStack[stackCounter];
/*  575 */           currentEmbeddingLevel = (byte)(currentEmbeddingValue & 0x7F); } break;
/*      */       case 15:
/*  584 */         stackCounter = 0;
/*  585 */         overflowCounter = 0;
/*  586 */         overflowAlmostCounter = 0;
/*  587 */         currentEmbeddingLevel = paragraphEmbeddingLevel;
/*  588 */         currentEmbeddingValue = paragraphEmbeddingLevel;
/*      */ 
/*  590 */         embeddings[i] = paragraphEmbeddingLevel;
/*      */       case 3:
/*      */       case 4:
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*  598 */       case 14: }  } return embeddings;
/*      */   }
/*      */ 
/*      */   private void resolveWeakTypes(int start, int limit, byte level, byte sor, byte eor)
/*      */   {
/*  612 */     byte preceedingCharacterType = sor;
/*  613 */     for (int i = start; i < limit; i++) {
/*  614 */       byte t = this.resultTypes[i];
/*  615 */       if (t == 13)
/*  616 */         this.resultTypes[i] = preceedingCharacterType;
/*      */       else {
/*  618 */         preceedingCharacterType = t;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  624 */     for (int i = start; i < limit; i++) {
/*  625 */       if (this.resultTypes[i] == 8) {
/*  626 */         for (int j = i - 1; j >= start; j--) {
/*  627 */           byte t = this.resultTypes[j];
/*  628 */           if ((t == 0) || (t == 3) || (t == 4)) {
/*  629 */             if (t != 4) break;
/*  630 */             this.resultTypes[i] = 11; break;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  639 */     for (int i = start; i < limit; i++) {
/*  640 */       if (this.resultTypes[i] == 4) {
/*  641 */         this.resultTypes[i] = 3;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  657 */     for (int i = start + 1; i < limit - 1; i++) {
/*  658 */       if ((this.resultTypes[i] == 9) || (this.resultTypes[i] == 12)) {
/*  659 */         byte prevSepType = this.resultTypes[(i - 1)];
/*  660 */         byte succSepType = this.resultTypes[(i + 1)];
/*  661 */         if ((prevSepType == 8) && (succSepType == 8))
/*  662 */           this.resultTypes[i] = 8;
/*  663 */         else if ((this.resultTypes[i] == 12) && (prevSepType == 11) && (succSepType == 11)) {
/*  664 */           this.resultTypes[i] = 11;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  670 */     for (int i = start; i < limit; i++) {
/*  671 */       if (this.resultTypes[i] == 10)
/*      */       {
/*  673 */         int runstart = i;
/*  674 */         int runlimit = findRunLimit(runstart, limit, new byte[] { 10 });
/*      */ 
/*  677 */         byte t = runstart == start ? sor : this.resultTypes[(runstart - 1)];
/*      */ 
/*  679 */         if (t != 8) {
/*  680 */           t = runlimit == limit ? eor : this.resultTypes[runlimit];
/*      */         }
/*      */ 
/*  683 */         if (t == 8) {
/*  684 */           setTypes(runstart, runlimit, (byte)8);
/*      */         }
/*      */ 
/*  688 */         i = runlimit;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  693 */     for (int i = start; i < limit; i++) {
/*  694 */       byte t = this.resultTypes[i];
/*  695 */       if ((t == 9) || (t == 10) || (t == 12)) {
/*  696 */         this.resultTypes[i] = 18;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  701 */     for (int i = start; i < limit; i++)
/*  702 */       if (this.resultTypes[i] == 8)
/*      */       {
/*  704 */         byte prevStrongType = sor;
/*  705 */         for (int j = i - 1; j >= start; j--) {
/*  706 */           byte t = this.resultTypes[j];
/*  707 */           if ((t == 0) || (t == 3)) {
/*  708 */             prevStrongType = t;
/*  709 */             break;
/*      */           }
/*      */         }
/*  712 */         if (prevStrongType == 0)
/*  713 */           this.resultTypes[i] = 0;
/*      */       }
/*      */   }
/*      */ 
/*      */   private void resolveNeutralTypes(int start, int limit, byte level, byte sor, byte eor)
/*      */   {
/*  725 */     for (int i = start; i < limit; i++) {
/*  726 */       byte t = this.resultTypes[i];
/*  727 */       if ((t == 17) || (t == 18) || (t == 15) || (t == 16))
/*      */       {
/*  729 */         int runstart = i;
/*  730 */         int runlimit = findRunLimit(runstart, limit, new byte[] { 15, 16, 17, 18 });
/*      */         byte leadingType;
/*      */         byte leadingType;
/*  736 */         if (runstart == start) {
/*  737 */           leadingType = sor;
/*      */         } else {
/*  739 */           leadingType = this.resultTypes[(runstart - 1)];
/*  740 */           if ((leadingType != 0) && (leadingType != 3))
/*      */           {
/*  742 */             if (leadingType == 11)
/*  743 */               leadingType = 3;
/*  744 */             else if (leadingType == 8)
/*      */             {
/*  747 */               leadingType = 3;
/*      */             }
/*      */           }
/*      */         }
/*      */         byte trailingType;
/*      */         byte trailingType;
/*  751 */         if (runlimit == limit) {
/*  752 */           trailingType = eor;
/*      */         } else {
/*  754 */           trailingType = this.resultTypes[runlimit];
/*  755 */           if ((trailingType != 0) && (trailingType != 3))
/*      */           {
/*  757 */             if (trailingType == 11)
/*  758 */               trailingType = 3;
/*  759 */             else if (trailingType == 8)
/*  760 */               trailingType = 3;
/*      */           }
/*      */         }
/*      */         byte resolvedType;
/*      */         byte resolvedType;
/*  765 */         if (leadingType == trailingType)
/*      */         {
/*  767 */           resolvedType = leadingType;
/*      */         }
/*      */         else
/*      */         {
/*  772 */           resolvedType = typeForLevel(level);
/*      */         }
/*      */ 
/*  775 */         setTypes(runstart, runlimit, resolvedType);
/*      */ 
/*  778 */         i = runlimit;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void resolveImplicitLevels(int start, int limit, byte level, byte sor, byte eor)
/*      */   {
/*  788 */     if ((level & 0x1) == 0)
/*  789 */       for (int i = start; i < limit; i++) {
/*  790 */         byte t = this.resultTypes[i];
/*      */ 
/*  792 */         if (t != 0)
/*      */         {
/*  794 */           if (t == 3)
/*      */           {
/*      */             int tmp44_42 = i;
/*      */             byte[] tmp44_39 = this.resultLevels; tmp44_39[tmp44_42] = ((byte)(tmp44_39[tmp44_42] + 1));
/*      */           }
/*      */           else
/*      */           {
/*      */             int tmp59_57 = i;
/*      */             byte[] tmp59_54 = this.resultLevels; tmp59_54[tmp59_57] = ((byte)(tmp59_54[tmp59_57] + 2));
/*      */           }
/*      */         }
/*      */       }
/*  801 */     else for (int i = start; i < limit; i++) {
/*  802 */         byte t = this.resultTypes[i];
/*      */ 
/*  804 */         if (t != 3)
/*      */         {
/*      */           int tmp107_105 = i;
/*      */           byte[] tmp107_102 = this.resultLevels; tmp107_102[tmp107_105] = ((byte)(tmp107_102[tmp107_105] + 1));
/*      */         }
/*      */       }
/*      */   }
/*      */ 
/*      */   public byte[] getLevels()
/*      */   {
/*  818 */     return getLevels(new int[] { this.textLength });
/*      */   }
/*      */ 
/*      */   public byte[] getLevels(int[] linebreaks)
/*      */   {
/*  849 */     validateLineBreaks(linebreaks, this.textLength);
/*      */ 
/*  851 */     byte[] result = (byte[])this.resultLevels.clone();
/*      */ 
/*  856 */     for (int i = 0; i < result.length; i++) {
/*  857 */       byte t = this.initialTypes[i];
/*  858 */       if ((t == 15) || (t == 16))
/*      */       {
/*  860 */         result[i] = this.paragraphEmbeddingLevel;
/*      */ 
/*  863 */         for (int j = i - 1; (j >= 0) && 
/*  864 */           (isWhitespace(this.initialTypes[j])); j--)
/*      */         {
/*  865 */           result[j] = this.paragraphEmbeddingLevel;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  874 */     int start = 0;
/*  875 */     for (int i = 0; i < linebreaks.length; i++) {
/*  876 */       int limit = linebreaks[i];
/*  877 */       for (int j = limit - 1; (j >= start) && 
/*  878 */         (isWhitespace(this.initialTypes[j])); j--)
/*      */       {
/*  879 */         result[j] = this.paragraphEmbeddingLevel;
/*      */       }
/*      */ 
/*  885 */       start = limit;
/*      */     }
/*      */ 
/*  888 */     return result;
/*      */   }
/*      */ 
/*      */   public int[] getReordering(int[] linebreaks)
/*      */   {
/*  910 */     validateLineBreaks(linebreaks, this.textLength);
/*      */ 
/*  912 */     byte[] levels = getLevels(linebreaks);
/*      */ 
/*  914 */     return computeMultilineReordering(levels, linebreaks);
/*      */   }
/*      */ 
/*      */   private static int[] computeMultilineReordering(byte[] levels, int[] linebreaks)
/*      */   {
/*  922 */     int[] result = new int[levels.length];
/*      */ 
/*  924 */     int start = 0;
/*  925 */     for (int i = 0; i < linebreaks.length; i++) {
/*  926 */       int limit = linebreaks[i];
/*      */ 
/*  928 */       byte[] templevels = new byte[limit - start];
/*  929 */       System.arraycopy(levels, start, templevels, 0, templevels.length);
/*      */ 
/*  931 */       int[] temporder = computeReordering(templevels);
/*  932 */       for (int j = 0; j < temporder.length; j++) {
/*  933 */         result[(start + j)] = (temporder[j] + start);
/*      */       }
/*      */ 
/*  936 */       start = limit;
/*      */     }
/*      */ 
/*  939 */     return result;
/*      */   }
/*      */ 
/*      */   private static int[] computeReordering(byte[] levels)
/*      */   {
/*  949 */     int lineLength = levels.length;
/*      */ 
/*  951 */     int[] result = new int[lineLength];
/*      */ 
/*  954 */     for (int i = 0; i < lineLength; i++) {
/*  955 */       result[i] = i;
/*      */     }
/*      */ 
/*  961 */     byte highestLevel = 0;
/*  962 */     byte lowestOddLevel = 63;
/*  963 */     for (int i = 0; i < lineLength; i++) {
/*  964 */       byte level = levels[i];
/*  965 */       if (level > highestLevel) {
/*  966 */         highestLevel = level;
/*      */       }
/*  968 */       if (((level & 0x1) != 0) && (level < lowestOddLevel)) {
/*  969 */         lowestOddLevel = level;
/*      */       }
/*      */     }
/*      */ 
/*  973 */     for (int level = highestLevel; level >= lowestOddLevel; level--) {
/*  974 */       for (int i = 0; i < lineLength; i++) {
/*  975 */         if (levels[i] >= level)
/*      */         {
/*  977 */           int start = i;
/*  978 */           int limit = i + 1;
/*  979 */           while ((limit < lineLength) && (levels[limit] >= level)) {
/*  980 */             limit++;
/*      */           }
/*      */ 
/*  984 */           int j = start; for (int k = limit - 1; j < k; k--) {
/*  985 */             int temp = result[j];
/*  986 */             result[j] = result[k];
/*  987 */             result[k] = temp;
/*      */ 
/*  984 */             j++;
/*      */           }
/*      */ 
/*  991 */           i = limit;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  996 */     return result;
/*      */   }
/*      */ 
/*      */   public byte getBaseLevel()
/*      */   {
/* 1003 */     return this.paragraphEmbeddingLevel;
/*      */   }
/*      */ 
/*      */   private static boolean isWhitespace(byte biditype)
/*      */   {
/* 1012 */     switch (biditype) {
/*      */     case 1:
/*      */     case 2:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/*      */     case 14:
/*      */     case 17:
/* 1020 */       return true;
/*      */     case 3:
/*      */     case 4:
/*      */     case 8:
/*      */     case 9:
/*      */     case 10:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 15:
/* 1022 */     case 16: } return false;
/*      */   }
/*      */ 
/*      */   private static byte typeForLevel(int level)
/*      */   {
/* 1030 */     return (level & 0x1) == 0 ? 0 : 3;
/*      */   }
/*      */ 
/*      */   private int findRunLimit(int index, int limit, byte[] validSet)
/*      */   {
/* 1038 */     index--;
/*      */ 
/* 1040 */     index++; if (index < limit) {
/* 1041 */       byte t = this.resultTypes[index];
/* 1042 */       for (int i = 0; ; i++) { if (i >= validSet.length) break label47;
/* 1043 */         if (t == validSet[i])
/*      */         {
/*      */           break;
/*      */         }
/*      */       }
/* 1048 */       label47: return index;
/*      */     }
/* 1050 */     return limit;
/*      */   }
/*      */ 
/*      */   private int findRunStart(int index, byte[] validSet)
/*      */   {
/* 1059 */     index--; if (index >= 0) {
/* 1060 */       byte t = this.resultTypes[index];
/* 1061 */       for (int i = 0; ; i++) { if (i >= validSet.length) break label41;
/* 1062 */         if (t == validSet[i]) {
/*      */           break;
/*      */         }
/*      */       }
/* 1066 */       label41: return index + 1;
/*      */     }
/* 1068 */     return 0;
/*      */   }
/*      */ 
/*      */   private void setTypes(int start, int limit, byte newType)
/*      */   {
/* 1075 */     for (int i = start; i < limit; i++)
/* 1076 */       this.resultTypes[i] = newType;
/*      */   }
/*      */ 
/*      */   private void setLevels(int start, int limit, byte newLevel)
/*      */   {
/* 1084 */     for (int i = start; i < limit; i++)
/* 1085 */       this.resultLevels[i] = newLevel;
/*      */   }
/*      */ 
/*      */   private static void validateTypes(byte[] types)
/*      */   {
/* 1095 */     if (types == null) {
/* 1096 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("types.is.null", new Object[0]));
/*      */     }
/* 1098 */     for (int i = 0; i < types.length; i++) {
/* 1099 */       if ((types[i] < 0) || (types[i] > 18)) {
/* 1100 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("illegal.type.value.at.1.2", new Object[] { String.valueOf(i), String.valueOf(types[i]) }));
/*      */       }
/*      */     }
/* 1103 */     for (int i = 0; i < types.length - 1; i++)
/* 1104 */       if (types[i] == 15)
/* 1105 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("b.type.before.end.of.paragraph.at.index.1", i));
/*      */   }
/*      */ 
/*      */   private static void validateParagraphEmbeddingLevel(byte paragraphEmbeddingLevel)
/*      */   {
/* 1115 */     if ((paragraphEmbeddingLevel != -1) && (paragraphEmbeddingLevel != 0) && (paragraphEmbeddingLevel != 1))
/*      */     {
/* 1118 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("illegal.paragraph.embedding.level.1", paragraphEmbeddingLevel));
/*      */     }
/*      */   }
/*      */ 
/*      */   private static void validateLineBreaks(int[] linebreaks, int textLength)
/*      */   {
/* 1126 */     int prev = 0;
/* 1127 */     for (int i = 0; i < linebreaks.length; i++) {
/* 1128 */       int next = linebreaks[i];
/* 1129 */       if (next <= prev) {
/* 1130 */         throw new IllegalArgumentException(MessageLocalization.getComposedMessage("bad.linebreak.1.at.index.2", new Object[] { String.valueOf(next), String.valueOf(i) }));
/*      */       }
/* 1132 */       prev = next;
/*      */     }
/* 1134 */     if (prev != textLength)
/* 1135 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("last.linebreak.must.be.at.1", textLength));
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 1284 */     for (int k = 0; k < baseTypes.length; k++) {
/* 1285 */       int start = baseTypes[k];
/* 1286 */       int end = baseTypes[(++k)];
/* 1287 */       byte b = (byte)baseTypes[(++k)];
/* 1288 */       while (start <= end)
/* 1289 */         rtypes[(start++)] = b;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.BidiOrder
 * JD-Core Version:    0.6.2
 */