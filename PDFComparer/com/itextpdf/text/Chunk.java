/*      */ package com.itextpdf.text;
/*      */ 
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.pdf.BaseFont;
/*      */ import com.itextpdf.text.pdf.HyphenationEvent;
/*      */ import com.itextpdf.text.pdf.PdfAction;
/*      */ import com.itextpdf.text.pdf.PdfAnnotation;
/*      */ import com.itextpdf.text.pdf.PdfName;
/*      */ import com.itextpdf.text.pdf.PdfObject;
/*      */ import com.itextpdf.text.pdf.PdfString;
/*      */ import com.itextpdf.text.pdf.draw.DrawInterface;
/*      */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ 
/*      */ public class Chunk
/*      */   implements Element, IAccessibleElement
/*      */ {
/*      */   public static final String OBJECT_REPLACEMENT_CHARACTER = "￼";
/*   87 */   public static final Chunk NEWLINE = new Chunk("\n");
/*      */   public static final Chunk NEXTPAGE;
/*   98 */   public static final Chunk TABBING = new Chunk(Float.valueOf((0.0F / 0.0F)), false);
/*      */ 
/*  100 */   public static final Chunk SPACETABBING = new Chunk(Float.valueOf((0.0F / 0.0F)), true);
/*      */ 
/*  105 */   protected StringBuffer content = null;
/*      */ 
/*  108 */   protected Font font = null;
/*      */ 
/*  111 */   protected HashMap<String, Object> attributes = null;
/*      */ 
/*  113 */   protected PdfName role = null;
/*  114 */   protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
/*  115 */   private AccessibleElementId id = null;
/*      */   public static final String SEPARATOR = "SEPARATOR";
/*      */   public static final String TAB = "TAB";
/*      */   public static final String TABSETTINGS = "TABSETTINGS";
/*  259 */   private String contentWithNoTabs = null;
/*      */   public static final String HSCALE = "HSCALE";
/*      */   public static final String UNDERLINE = "UNDERLINE";
/*      */   public static final String SUBSUPSCRIPT = "SUBSUPSCRIPT";
/*      */   public static final String SKEW = "SKEW";
/*      */   public static final String BACKGROUND = "BACKGROUND";
/*      */   public static final String TEXTRENDERMODE = "TEXTRENDERMODE";
/*      */   public static final String SPLITCHARACTER = "SPLITCHARACTER";
/*      */   public static final String HYPHENATION = "HYPHENATION";
/*      */   public static final String REMOTEGOTO = "REMOTEGOTO";
/*      */   public static final String LOCALGOTO = "LOCALGOTO";
/*      */   public static final String LOCALDESTINATION = "LOCALDESTINATION";
/*      */   public static final String GENERICTAG = "GENERICTAG";
/*      */   public static final String LINEHEIGHT = "LINEHEIGHT";
/*      */   public static final String IMAGE = "IMAGE";
/*      */   public static final String ACTION = "ACTION";
/*      */   public static final String NEWPAGE = "NEWPAGE";
/*      */   public static final String PDFANNOTATION = "PDFANNOTATION";
/*      */   public static final String COLOR = "COLOR";
/*      */   public static final String ENCODING = "ENCODING";
/*      */   public static final String CHAR_SPACING = "CHAR_SPACING";
/*      */   public static final String WORD_SPACING = "WORD_SPACING";
/*      */   public static final String WHITESPACE = "WHITESPACE";
/*      */ 
/*      */   public Chunk()
/*      */   {
/*  123 */     this.content = new StringBuffer();
/*  124 */     this.font = new Font();
/*  125 */     this.role = PdfName.SPAN;
/*      */   }
/*      */ 
/*      */   public Chunk(Chunk ck)
/*      */   {
/*  133 */     if (ck.content != null) {
/*  134 */       this.content = new StringBuffer(ck.content.toString());
/*      */     }
/*  136 */     if (ck.font != null) {
/*  137 */       this.font = new Font(ck.font);
/*      */     }
/*  139 */     if (ck.attributes != null) {
/*  140 */       this.attributes = new HashMap(ck.attributes);
/*      */     }
/*  142 */     this.role = ck.role;
/*  143 */     if (ck.accessibleAttributes != null) {
/*  144 */       this.accessibleAttributes = new HashMap(ck.accessibleAttributes);
/*      */     }
/*  146 */     this.id = ck.getId();
/*      */   }
/*      */ 
/*      */   public Chunk(String content, Font font)
/*      */   {
/*  159 */     this.content = new StringBuffer(content);
/*  160 */     this.font = font;
/*  161 */     this.role = PdfName.SPAN;
/*      */   }
/*      */ 
/*      */   public Chunk(String content)
/*      */   {
/*  172 */     this(content, new Font());
/*      */   }
/*      */ 
/*      */   public Chunk(char c, Font font)
/*      */   {
/*  184 */     this.content = new StringBuffer();
/*  185 */     this.content.append(c);
/*  186 */     this.font = font;
/*  187 */     this.role = PdfName.SPAN;
/*      */   }
/*      */ 
/*      */   public Chunk(char c)
/*      */   {
/*  198 */     this(c, new Font());
/*      */   }
/*      */ 
/*      */   public Chunk(Image image, float offsetX, float offsetY)
/*      */   {
/*  212 */     this("￼", new Font());
/*  213 */     Image copyImage = Image.getInstance(image);
/*  214 */     copyImage.setAbsolutePosition((0.0F / 0.0F), (0.0F / 0.0F));
/*  215 */     setAttribute("IMAGE", new Object[] { copyImage, new Float(offsetX), new Float(offsetY), Boolean.FALSE });
/*      */ 
/*  217 */     this.role = null;
/*      */   }
/*      */ 
/*      */   public Chunk(DrawInterface separator)
/*      */   {
/*  233 */     this(separator, false);
/*      */   }
/*      */ 
/*      */   public Chunk(DrawInterface separator, boolean vertical)
/*      */   {
/*  244 */     this("￼", new Font());
/*  245 */     setAttribute("SEPARATOR", new Object[] { separator, Boolean.valueOf(vertical) });
/*  246 */     this.role = null;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public Chunk(DrawInterface separator, float tabPosition)
/*      */   {
/*  270 */     this(separator, tabPosition, false);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public Chunk(DrawInterface separator, float tabPosition, boolean newline)
/*      */   {
/*  283 */     this("￼", new Font());
/*  284 */     if (tabPosition < 0.0F) {
/*  285 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("a.tab.position.may.not.be.lower.than.0.yours.is.1", new Object[] { String.valueOf(tabPosition) }));
/*      */     }
/*  287 */     setAttribute("TAB", new Object[] { separator, new Float(tabPosition), Boolean.valueOf(newline), new Float(0.0F) });
/*  288 */     this.role = PdfName.ARTIFACT;
/*      */   }
/*      */ 
/*      */   private Chunk(Float tabInterval, boolean isWhitespace)
/*      */   {
/*  299 */     this("￼", new Font());
/*  300 */     if (tabInterval.floatValue() < 0.0F) {
/*  301 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("a.tab.position.may.not.be.lower.than.0.yours.is.1", new Object[] { String.valueOf(tabInterval) }));
/*      */     }
/*  303 */     setAttribute("TAB", new Object[] { tabInterval, Boolean.valueOf(isWhitespace) });
/*  304 */     setAttribute("SPLITCHARACTER", TabSplitCharacter.TAB);
/*      */ 
/*  306 */     setAttribute("TABSETTINGS", null);
/*  307 */     this.role = PdfName.ARTIFACT;
/*      */   }
/*      */ 
/*      */   public Chunk(Image image, float offsetX, float offsetY, boolean changeLeading)
/*      */   {
/*  324 */     this("￼", new Font());
/*  325 */     setAttribute("IMAGE", new Object[] { image, new Float(offsetX), new Float(offsetY), Boolean.valueOf(changeLeading) });
/*      */ 
/*  327 */     this.role = PdfName.ARTIFACT;
/*      */   }
/*      */ 
/*      */   public boolean process(ElementListener listener)
/*      */   {
/*      */     try
/*      */     {
/*  342 */       return listener.add(this); } catch (DocumentException de) {
/*      */     }
/*  344 */     return false;
/*      */   }
/*      */ 
/*      */   public int type()
/*      */   {
/*  354 */     return 10;
/*      */   }
/*      */ 
/*      */   public List<Chunk> getChunks()
/*      */   {
/*  363 */     List tmp = new ArrayList();
/*  364 */     tmp.add(this);
/*  365 */     return tmp;
/*      */   }
/*      */ 
/*      */   public StringBuffer append(String string)
/*      */   {
/*  378 */     this.contentWithNoTabs = null;
/*  379 */     return this.content.append(string);
/*      */   }
/*      */ 
/*      */   public void setFont(Font font)
/*      */   {
/*  389 */     this.font = font;
/*      */   }
/*      */ 
/*      */   public Font getFont()
/*      */   {
/*  400 */     return this.font;
/*      */   }
/*      */ 
/*      */   public String getContent()
/*      */   {
/*  409 */     if (this.contentWithNoTabs == null)
/*  410 */       this.contentWithNoTabs = this.content.toString().replaceAll("\t", "");
/*  411 */     return this.contentWithNoTabs;
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/*  421 */     return getContent();
/*      */   }
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/*  431 */     return (this.content.toString().trim().length() == 0) && (this.content.toString().indexOf("\n") == -1) && (this.attributes == null);
/*      */   }
/*      */ 
/*      */   public float getWidthPoint()
/*      */   {
/*  442 */     if (getImage() != null) {
/*  443 */       return getImage().getScaledWidth();
/*      */     }
/*  445 */     return this.font.getCalculatedBaseFont(true).getWidthPoint(getContent(), this.font.getCalculatedSize()) * getHorizontalScaling();
/*      */   }
/*      */ 
/*      */   public boolean hasAttributes()
/*      */   {
/*  459 */     return this.attributes != null;
/*      */   }
/*      */ 
/*      */   public HashMap<String, Object> getAttributes()
/*      */   {
/*  471 */     return this.attributes;
/*      */   }
/*      */ 
/*      */   public void setAttributes(HashMap<String, Object> attributes)
/*      */   {
/*  479 */     this.attributes = attributes;
/*      */   }
/*      */ 
/*      */   private Chunk setAttribute(String name, Object obj)
/*      */   {
/*  493 */     if (this.attributes == null)
/*  494 */       this.attributes = new HashMap();
/*  495 */     this.attributes.put(name, obj);
/*  496 */     return this;
/*      */   }
/*      */ 
/*      */   public Chunk setHorizontalScaling(float scale)
/*      */   {
/*  513 */     return setAttribute("HSCALE", new Float(scale));
/*      */   }
/*      */ 
/*      */   public float getHorizontalScaling()
/*      */   {
/*  522 */     if (this.attributes == null)
/*  523 */       return 1.0F;
/*  524 */     Float f = (Float)this.attributes.get("HSCALE");
/*  525 */     if (f == null)
/*  526 */       return 1.0F;
/*  527 */     return f.floatValue();
/*      */   }
/*      */ 
/*      */   public Chunk setUnderline(float thickness, float yPosition)
/*      */   {
/*  546 */     return setUnderline(null, thickness, 0.0F, yPosition, 0.0F, 0);
/*      */   }
/*      */ 
/*      */   public Chunk setUnderline(BaseColor color, float thickness, float thicknessMul, float yPosition, float yPositionMul, int cap)
/*      */   {
/*  575 */     if (this.attributes == null)
/*  576 */       this.attributes = new HashMap();
/*  577 */     Object[] obj = { color, { thickness, thicknessMul, yPosition, yPositionMul, cap } };
/*      */ 
/*  580 */     Object[][] unders = Utilities.addToArray((Object[][])this.attributes.get("UNDERLINE"), obj);
/*      */ 
/*  582 */     return setAttribute("UNDERLINE", unders);
/*      */   }
/*      */ 
/*      */   public Chunk setTextRise(float rise)
/*      */   {
/*  600 */     return setAttribute("SUBSUPSCRIPT", new Float(rise));
/*      */   }
/*      */ 
/*      */   public float getTextRise()
/*      */   {
/*  609 */     if ((this.attributes != null) && (this.attributes.containsKey("SUBSUPSCRIPT"))) {
/*  610 */       Float f = (Float)this.attributes.get("SUBSUPSCRIPT");
/*  611 */       return f.floatValue();
/*      */     }
/*  613 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   public Chunk setSkew(float alpha, float beta)
/*      */   {
/*  630 */     alpha = (float)Math.tan(alpha * 3.141592653589793D / 180.0D);
/*  631 */     beta = (float)Math.tan(beta * 3.141592653589793D / 180.0D);
/*  632 */     return setAttribute("SKEW", new float[] { alpha, beta });
/*      */   }
/*      */ 
/*      */   public Chunk setBackground(BaseColor color)
/*      */   {
/*  646 */     return setBackground(color, 0.0F, 0.0F, 0.0F, 0.0F);
/*      */   }
/*      */ 
/*      */   public Chunk setBackground(BaseColor color, float extraLeft, float extraBottom, float extraRight, float extraTop)
/*      */   {
/*  666 */     return setAttribute("BACKGROUND", new Object[] { color, { extraLeft, extraBottom, extraRight, extraTop } });
/*      */   }
/*      */ 
/*      */   public Chunk setTextRenderMode(int mode, float strokeWidth, BaseColor strokeColor)
/*      */   {
/*  694 */     return setAttribute("TEXTRENDERMODE", new Object[] { Integer.valueOf(mode), new Float(strokeWidth), strokeColor });
/*      */   }
/*      */ 
/*      */   public Chunk setSplitCharacter(SplitCharacter splitCharacter)
/*      */   {
/*  710 */     return setAttribute("SPLITCHARACTER", splitCharacter);
/*      */   }
/*      */ 
/*      */   public Chunk setHyphenation(HyphenationEvent hyphenation)
/*      */   {
/*  724 */     return setAttribute("HYPHENATION", hyphenation);
/*      */   }
/*      */ 
/*      */   public Chunk setRemoteGoto(String filename, String name)
/*      */   {
/*  741 */     return setAttribute("REMOTEGOTO", new Object[] { filename, name });
/*      */   }
/*      */ 
/*      */   public Chunk setRemoteGoto(String filename, int page)
/*      */   {
/*  755 */     return setAttribute("REMOTEGOTO", new Object[] { filename, Integer.valueOf(page) });
/*      */   }
/*      */ 
/*      */   public Chunk setLocalGoto(String name)
/*      */   {
/*  773 */     return setAttribute("LOCALGOTO", name);
/*      */   }
/*      */ 
/*      */   public Chunk setLocalDestination(String name)
/*      */   {
/*  787 */     return setAttribute("LOCALDESTINATION", name);
/*      */   }
/*      */ 
/*      */   public Chunk setGenericTag(String text)
/*      */   {
/*  804 */     return setAttribute("GENERICTAG", text);
/*      */   }
/*      */ 
/*      */   public Chunk setLineHeight(float lineheight)
/*      */   {
/*  817 */     return setAttribute("LINEHEIGHT", Float.valueOf(lineheight));
/*      */   }
/*      */ 
/*      */   public Image getImage()
/*      */   {
/*  831 */     if (this.attributes == null)
/*  832 */       return null;
/*  833 */     Object[] obj = (Object[])this.attributes.get("IMAGE");
/*  834 */     if (obj == null) {
/*  835 */       return null;
/*      */     }
/*  837 */     return (Image)obj[0];
/*      */   }
/*      */ 
/*      */   public Chunk setAction(PdfAction action)
/*      */   {
/*  853 */     setRole(PdfName.LINK);
/*  854 */     return setAttribute("ACTION", action);
/*      */   }
/*      */ 
/*      */   public Chunk setAnchor(URL url)
/*      */   {
/*  866 */     setRole(PdfName.LINK);
/*  867 */     String urlStr = url.toExternalForm();
/*  868 */     setAccessibleAttribute(PdfName.ALT, new PdfString(urlStr));
/*  869 */     return setAttribute("ACTION", new PdfAction(urlStr));
/*      */   }
/*      */ 
/*      */   public Chunk setAnchor(String url)
/*      */   {
/*  881 */     setRole(PdfName.LINK);
/*  882 */     setAccessibleAttribute(PdfName.ALT, new PdfString(url));
/*  883 */     return setAttribute("ACTION", new PdfAction(url));
/*      */   }
/*      */ 
/*      */   public Chunk setNewPage()
/*      */   {
/*  896 */     return setAttribute("NEWPAGE", null);
/*      */   }
/*      */ 
/*      */   public Chunk setAnnotation(PdfAnnotation annotation)
/*      */   {
/*  910 */     return setAttribute("PDFANNOTATION", annotation);
/*      */   }
/*      */ 
/*      */   public boolean isContent()
/*      */   {
/*  918 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean isNestable()
/*      */   {
/*  926 */     return true;
/*      */   }
/*      */ 
/*      */   public HyphenationEvent getHyphenation()
/*      */   {
/*  935 */     if (this.attributes == null) return null;
/*  936 */     return (HyphenationEvent)this.attributes.get("HYPHENATION");
/*      */   }
/*      */ 
/*      */   public Chunk setCharacterSpacing(float charSpace)
/*      */   {
/*  959 */     return setAttribute("CHAR_SPACING", new Float(charSpace));
/*      */   }
/*      */ 
/*      */   public float getCharacterSpacing()
/*      */   {
/*  968 */     if ((this.attributes != null) && (this.attributes.containsKey("CHAR_SPACING"))) {
/*  969 */       Float f = (Float)this.attributes.get("CHAR_SPACING");
/*  970 */       return f.floatValue();
/*      */     }
/*  972 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   public Chunk setWordSpacing(float wordSpace)
/*      */   {
/*  987 */     return setAttribute("WORD_SPACING", new Float(wordSpace));
/*      */   }
/*      */ 
/*      */   public float getWordSpacing()
/*      */   {
/*  996 */     if ((this.attributes != null) && (this.attributes.containsKey("WORD_SPACING"))) {
/*  997 */       Float f = (Float)this.attributes.get("WORD_SPACING");
/*  998 */       return f.floatValue();
/*      */     }
/* 1000 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   public static Chunk createWhitespace(String content)
/*      */   {
/* 1006 */     return createWhitespace(content, false);
/*      */   }
/*      */ 
/*      */   public static Chunk createWhitespace(String content, boolean preserve) {
/* 1010 */     Chunk whitespace = null;
/* 1011 */     if (!preserve) {
/* 1012 */       whitespace = new Chunk(' ');
/* 1013 */       whitespace.setAttribute("WHITESPACE", content);
/*      */     } else {
/* 1015 */       whitespace = new Chunk(content);
/*      */     }
/*      */ 
/* 1018 */     return whitespace;
/*      */   }
/*      */ 
/*      */   public boolean isWhitespace() {
/* 1022 */     return (this.attributes != null) && (this.attributes.containsKey("WHITESPACE"));
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public static Chunk createTabspace() {
/* 1027 */     return createTabspace(60.0F);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public static Chunk createTabspace(float spacing) {
/* 1032 */     Chunk tabspace = new Chunk(Float.valueOf(spacing), true);
/* 1033 */     return tabspace;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public boolean isTabspace() {
/* 1038 */     return (this.attributes != null) && (this.attributes.containsKey("TAB"));
/*      */   }
/*      */ 
/*      */   public PdfObject getAccessibleAttribute(PdfName key) {
/* 1042 */     if (getImage() != null)
/* 1043 */       return getImage().getAccessibleAttribute(key);
/* 1044 */     if (this.accessibleAttributes != null) {
/* 1045 */       return (PdfObject)this.accessibleAttributes.get(key);
/*      */     }
/* 1047 */     return null;
/*      */   }
/*      */ 
/*      */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/* 1051 */     if (getImage() != null) {
/* 1052 */       getImage().setAccessibleAttribute(key, value);
/*      */     } else {
/* 1054 */       if (this.accessibleAttributes == null)
/* 1055 */         this.accessibleAttributes = new HashMap();
/* 1056 */       this.accessibleAttributes.put(key, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/* 1061 */     if (getImage() != null) {
/* 1062 */       return getImage().getAccessibleAttributes();
/*      */     }
/* 1064 */     return this.accessibleAttributes;
/*      */   }
/*      */ 
/*      */   public PdfName getRole() {
/* 1068 */     if (getImage() != null) {
/* 1069 */       return getImage().getRole();
/*      */     }
/* 1071 */     return this.role;
/*      */   }
/*      */ 
/*      */   public void setRole(PdfName role) {
/* 1075 */     if (getImage() != null)
/* 1076 */       getImage().setRole(role);
/*      */     else
/* 1078 */       this.role = role;
/*      */   }
/*      */ 
/*      */   public AccessibleElementId getId() {
/* 1082 */     if (this.id == null)
/* 1083 */       this.id = new AccessibleElementId();
/* 1084 */     return this.id;
/*      */   }
/*      */ 
/*      */   public void setId(AccessibleElementId id) {
/* 1088 */     this.id = id;
/*      */   }
/*      */ 
/*      */   public boolean isInline() {
/* 1092 */     return true;
/*      */   }
/*      */ 
/*      */   public String getTextExpansion() {
/* 1096 */     PdfObject o = getAccessibleAttribute(PdfName.E);
/* 1097 */     if ((o instanceof PdfString))
/* 1098 */       return ((PdfString)o).toUnicodeString();
/* 1099 */     return null;
/*      */   }
/*      */ 
/*      */   public void setTextExpansion(String value)
/*      */   {
/* 1108 */     setAccessibleAttribute(PdfName.E, new PdfString(value));
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*   89 */     NEWLINE.setRole(PdfName.P);
/*      */ 
/*   93 */     NEXTPAGE = new Chunk("");
/*      */ 
/*   95 */     NEXTPAGE.setNewPage();
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Chunk
 * JD-Core Version:    0.6.2
 */