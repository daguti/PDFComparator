/*     */ package com.itextpdf.xmp.impl.xpath;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class XMPPath
/*     */ {
/*     */   public static final int STRUCT_FIELD_STEP = 1;
/*     */   public static final int QUALIFIER_STEP = 2;
/*     */   public static final int ARRAY_INDEX_STEP = 3;
/*     */   public static final int ARRAY_LAST_STEP = 4;
/*     */   public static final int QUAL_SELECTOR_STEP = 5;
/*     */   public static final int FIELD_SELECTOR_STEP = 6;
/*     */   public static final int SCHEMA_NODE = -2147483648;
/*     */   public static final int STEP_SCHEMA = 0;
/*     */   public static final int STEP_ROOT_PROP = 1;
/*  68 */   private List segments = new ArrayList(5);
/*     */ 
/*     */   public void add(XMPPathSegment segment)
/*     */   {
/*  78 */     this.segments.add(segment);
/*     */   }
/*     */ 
/*     */   public XMPPathSegment getSegment(int index)
/*     */   {
/*  88 */     return (XMPPathSegment)this.segments.get(index);
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/*  97 */     return this.segments.size();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 107 */     StringBuffer result = new StringBuffer();
/* 108 */     int index = 1;
/* 109 */     while (index < size())
/*     */     {
/* 111 */       result.append(getSegment(index));
/* 112 */       if (index < size() - 1)
/*     */       {
/* 114 */         int kind = getSegment(index + 1).getKind();
/* 115 */         if ((kind == 1) || (kind == 2))
/*     */         {
/* 119 */           result.append('/');
/*     */         }
/*     */       }
/* 122 */       index++;
/*     */     }
/*     */ 
/* 125 */     return result.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.impl.xpath.XMPPath
 * JD-Core Version:    0.6.2
 */