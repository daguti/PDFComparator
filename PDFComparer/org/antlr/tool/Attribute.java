/*     */ package org.antlr.tool;
/*     */ 
/*     */ public class Attribute
/*     */ {
/*     */   public String decl;
/*     */   public String type;
/*     */   public String name;
/*     */   public String initValue;
/*     */ 
/*     */   public Attribute(String decl)
/*     */   {
/*  48 */     extractAttribute(decl);
/*     */   }
/*     */ 
/*     */   public Attribute(String name, String decl) {
/*  52 */     this.name = name;
/*  53 */     this.decl = decl;
/*     */   }
/*     */ 
/*     */   protected void extractAttribute(String decl)
/*     */   {
/*  65 */     if (decl == null) {
/*  66 */       return;
/*     */     }
/*  68 */     boolean inID = false;
/*  69 */     int start = -1;
/*  70 */     int rightEdgeOfDeclarator = decl.length() - 1;
/*  71 */     int equalsIndex = decl.indexOf('=');
/*  72 */     if (equalsIndex > 0)
/*     */     {
/*  74 */       this.initValue = decl.substring(equalsIndex + 1, decl.length());
/*  75 */       rightEdgeOfDeclarator = equalsIndex - 1;
/*     */     }
/*     */ 
/*  78 */     for (int i = rightEdgeOfDeclarator; i >= 0; i--)
/*     */     {
/*  80 */       if ((!inID) && (Character.isLetterOrDigit(decl.charAt(i)))) {
/*  81 */         inID = true;
/*     */       }
/*  83 */       else if ((inID) && (!Character.isLetterOrDigit(decl.charAt(i))) && (decl.charAt(i) != '_'))
/*     */       {
/*  86 */         start = i + 1;
/*  87 */         break;
/*     */       }
/*     */     }
/*  90 */     if ((start < 0) && (inID)) {
/*  91 */       start = 0;
/*     */     }
/*  93 */     if (start < 0) {
/*  94 */       ErrorManager.error(104, decl);
/*     */     }
/*     */ 
/*  97 */     int stop = -1;
/*  98 */     for (int i = start; i <= rightEdgeOfDeclarator; i++)
/*     */     {
/* 100 */       if ((!Character.isLetterOrDigit(decl.charAt(i))) && (decl.charAt(i) != '_'))
/*     */       {
/* 103 */         stop = i;
/* 104 */         break;
/*     */       }
/* 106 */       if (i == rightEdgeOfDeclarator) {
/* 107 */         stop = i + 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 112 */     this.name = decl.substring(start, stop);
/*     */ 
/* 115 */     this.type = decl.substring(0, start);
/* 116 */     if (stop <= rightEdgeOfDeclarator) {
/* 117 */       this.type += decl.substring(stop, rightEdgeOfDeclarator + 1);
/*     */     }
/* 119 */     this.type = this.type.trim();
/* 120 */     if (this.type.length() == 0) {
/* 121 */       this.type = null;
/*     */     }
/*     */ 
/* 124 */     this.decl = decl;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 128 */     if (this.initValue != null) {
/* 129 */       return this.type + " " + this.name + "=" + this.initValue;
/*     */     }
/* 131 */     return this.type + " " + this.name;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.Attribute
 * JD-Core Version:    0.6.2
 */