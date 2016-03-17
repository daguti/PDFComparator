/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ 
/*     */ public class PDTableAttributeObject extends PDStandardAttributeObject
/*     */ {
/*     */   public static final String OWNER_TABLE = "Table";
/*     */   protected static final String ROW_SPAN = "RowSpan";
/*     */   protected static final String COL_SPAN = "ColSpan";
/*     */   protected static final String HEADERS = "Headers";
/*     */   protected static final String SCOPE = "Scope";
/*     */   protected static final String SUMMARY = "Summary";
/*     */   public static final String SCOPE_BOTH = "Both";
/*     */   public static final String SCOPE_COLUMN = "Column";
/*     */   public static final String SCOPE_ROW = "Row";
/*     */ 
/*     */   public PDTableAttributeObject()
/*     */   {
/*  61 */     setOwner("Table");
/*     */   }
/*     */ 
/*     */   public PDTableAttributeObject(COSDictionary dictionary)
/*     */   {
/*  71 */     super(dictionary);
/*     */   }
/*     */ 
/*     */   public int getRowSpan()
/*     */   {
/*  83 */     return getInteger("RowSpan", 1);
/*     */   }
/*     */ 
/*     */   public void setRowSpan(int rowSpan)
/*     */   {
/*  94 */     setInteger("RowSpan", rowSpan);
/*     */   }
/*     */ 
/*     */   public int getColSpan()
/*     */   {
/* 105 */     return getInteger("ColSpan", 1);
/*     */   }
/*     */ 
/*     */   public void setColSpan(int colSpan)
/*     */   {
/* 116 */     setInteger("ColSpan", colSpan);
/*     */   }
/*     */ 
/*     */   public String[] getHeaders()
/*     */   {
/* 129 */     return getArrayOfString("Headers");
/*     */   }
/*     */ 
/*     */   public void setHeaders(String[] headers)
/*     */   {
/* 142 */     setArrayOfString("Headers", headers);
/*     */   }
/*     */ 
/*     */   public String getScope()
/*     */   {
/* 154 */     return getName("Scope");
/*     */   }
/*     */ 
/*     */   public void setScope(String scope)
/*     */   {
/* 172 */     setName("Scope", scope);
/*     */   }
/*     */ 
/*     */   public String getSummary()
/*     */   {
/* 182 */     return getString("Summary");
/*     */   }
/*     */ 
/*     */   public void setSummary(String summary)
/*     */   {
/* 192 */     setString("Summary", summary);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 198 */     StringBuilder sb = new StringBuilder().append(super.toString());
/* 199 */     if (isSpecified("RowSpan"))
/*     */     {
/* 201 */       sb.append(", RowSpan=").append(String.valueOf(getRowSpan()));
/*     */     }
/* 203 */     if (isSpecified("ColSpan"))
/*     */     {
/* 205 */       sb.append(", ColSpan=").append(String.valueOf(getColSpan()));
/*     */     }
/* 207 */     if (isSpecified("Headers"))
/*     */     {
/* 209 */       sb.append(", Headers=").append(arrayToString(getHeaders()));
/*     */     }
/* 211 */     if (isSpecified("Scope"))
/*     */     {
/* 213 */       sb.append(", Scope=").append(getScope());
/*     */     }
/* 215 */     if (isSpecified("Summary"))
/*     */     {
/* 217 */       sb.append(", Summary=").append(getSummary());
/*     */     }
/* 219 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf.PDTableAttributeObject
 * JD-Core Version:    0.6.2
 */