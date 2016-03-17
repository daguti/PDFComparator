/*     */ package com.itextpdf.text.pdf.codec.wmf;
/*     */ 
/*     */ import com.itextpdf.text.Document;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Font;
/*     */ import com.itextpdf.text.FontFactory;
/*     */ import com.itextpdf.text.pdf.BaseFont;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ public class MetaFont extends MetaObject
/*     */ {
/*  56 */   static final String[] fontNames = { "Courier", "Courier-Bold", "Courier-Oblique", "Courier-BoldOblique", "Helvetica", "Helvetica-Bold", "Helvetica-Oblique", "Helvetica-BoldOblique", "Times-Roman", "Times-Bold", "Times-Italic", "Times-BoldItalic", "Symbol", "ZapfDingbats" };
/*     */   static final int MARKER_BOLD = 1;
/*     */   static final int MARKER_ITALIC = 2;
/*     */   static final int MARKER_COURIER = 0;
/*     */   static final int MARKER_HELVETICA = 4;
/*     */   static final int MARKER_TIMES = 8;
/*     */   static final int MARKER_SYMBOL = 12;
/*     */   static final int DEFAULT_PITCH = 0;
/*     */   static final int FIXED_PITCH = 1;
/*     */   static final int VARIABLE_PITCH = 2;
/*     */   static final int FF_DONTCARE = 0;
/*     */   static final int FF_ROMAN = 1;
/*     */   static final int FF_SWISS = 2;
/*     */   static final int FF_MODERN = 3;
/*     */   static final int FF_SCRIPT = 4;
/*     */   static final int FF_DECORATIVE = 5;
/*     */   static final int BOLDTHRESHOLD = 600;
/*     */   static final int nameSize = 32;
/*     */   static final int ETO_OPAQUE = 2;
/*     */   static final int ETO_CLIPPED = 4;
/*     */   int height;
/*     */   float angle;
/*     */   int bold;
/*     */   int italic;
/*     */   boolean underline;
/*     */   boolean strikeout;
/*     */   int charset;
/*     */   int pitchAndFamily;
/*  91 */   String faceName = "arial";
/*  92 */   BaseFont font = null;
/*     */ 
/*     */   public MetaFont() {
/*  95 */     this.type = 3;
/*     */   }
/*     */ 
/*     */   public void init(InputMeta in) throws IOException {
/*  99 */     this.height = Math.abs(in.readShort());
/* 100 */     in.skip(2);
/* 101 */     this.angle = ((float)(in.readShort() / 1800.0D * 3.141592653589793D));
/* 102 */     in.skip(2);
/* 103 */     this.bold = (in.readShort() >= 600 ? 1 : 0);
/* 104 */     this.italic = (in.readByte() != 0 ? 2 : 0);
/* 105 */     this.underline = (in.readByte() != 0);
/* 106 */     this.strikeout = (in.readByte() != 0);
/* 107 */     this.charset = in.readByte();
/* 108 */     in.skip(3);
/* 109 */     this.pitchAndFamily = in.readByte();
/* 110 */     byte[] name = new byte[32];
/*     */ 
/* 112 */     for (int k = 0; k < 32; k++) {
/* 113 */       int c = in.readByte();
/* 114 */       if (c == 0) {
/*     */         break;
/*     */       }
/* 117 */       name[k] = ((byte)c);
/*     */     }
/*     */     try {
/* 120 */       this.faceName = new String(name, 0, k, "Cp1252");
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/* 123 */       this.faceName = new String(name, 0, k);
/*     */     }
/* 125 */     this.faceName = this.faceName.toLowerCase();
/*     */   }
/*     */ 
/*     */   public BaseFont getFont() {
/* 129 */     if (this.font != null)
/* 130 */       return this.font;
/* 131 */     Font ff2 = FontFactory.getFont(this.faceName, "Cp1252", true, 10.0F, (this.italic != 0 ? 2 : 0) | (this.bold != 0 ? 1 : 0));
/* 132 */     this.font = ff2.getBaseFont();
/* 133 */     if (this.font != null)
/* 134 */       return this.font;
/*     */     String fontName;
/*     */     String fontName;
/* 136 */     if ((this.faceName.indexOf("courier") != -1) || (this.faceName.indexOf("terminal") != -1) || (this.faceName.indexOf("fixedsys") != -1))
/*     */     {
/* 138 */       fontName = fontNames[(0 + this.italic + this.bold)];
/*     */     }
/*     */     else
/*     */     {
/*     */       String fontName;
/* 140 */       if ((this.faceName.indexOf("ms sans serif") != -1) || (this.faceName.indexOf("arial") != -1) || (this.faceName.indexOf("system") != -1))
/*     */       {
/* 142 */         fontName = fontNames[(4 + this.italic + this.bold)];
/*     */       }
/*     */       else
/*     */       {
/*     */         String fontName;
/* 144 */         if (this.faceName.indexOf("arial black") != -1) {
/* 145 */           fontName = fontNames[(4 + this.italic + 1)];
/*     */         }
/*     */         else
/*     */         {
/*     */           String fontName;
/* 147 */           if ((this.faceName.indexOf("times") != -1) || (this.faceName.indexOf("ms serif") != -1) || (this.faceName.indexOf("roman") != -1))
/*     */           {
/* 149 */             fontName = fontNames[(8 + this.italic + this.bold)];
/*     */           }
/*     */           else
/*     */           {
/*     */             String fontName;
/* 151 */             if (this.faceName.indexOf("symbol") != -1) {
/* 152 */               fontName = fontNames[12];
/*     */             }
/*     */             else {
/* 155 */               int pitch = this.pitchAndFamily & 0x3;
/* 156 */               int family = this.pitchAndFamily >> 4 & 0x7;
/* 157 */               switch (family) {
/*     */               case 3:
/* 159 */                 fontName = fontNames[(0 + this.italic + this.bold)];
/* 160 */                 break;
/*     */               case 1:
/* 162 */                 fontName = fontNames[(8 + this.italic + this.bold)];
/* 163 */                 break;
/*     */               case 2:
/*     */               case 4:
/*     */               case 5:
/* 167 */                 fontName = fontNames[(4 + this.italic + this.bold)];
/* 168 */                 break;
/*     */               default:
/* 171 */                 switch (pitch) {
/*     */                 case 1:
/* 173 */                   fontName = fontNames[(0 + this.italic + this.bold)];
/* 174 */                   break;
/*     */                 default:
/* 176 */                   fontName = fontNames[(4 + this.italic + this.bold)]; } break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     try { this.font = BaseFont.createFont(fontName, "Cp1252", false);
/*     */     } catch (Exception e)
/*     */     {
/* 186 */       throw new ExceptionConverter(e);
/*     */     }
/*     */ 
/* 189 */     return this.font;
/*     */   }
/*     */ 
/*     */   public float getAngle() {
/* 193 */     return this.angle;
/*     */   }
/*     */ 
/*     */   public boolean isUnderline() {
/* 197 */     return this.underline;
/*     */   }
/*     */ 
/*     */   public boolean isStrikeout() {
/* 201 */     return this.strikeout;
/*     */   }
/*     */ 
/*     */   public float getFontSize(MetaState state) {
/* 205 */     return Math.abs(state.transformY(this.height) - state.transformY(0)) * Document.wmfFontCorrection;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.wmf.MetaFont
 * JD-Core Version:    0.6.2
 */