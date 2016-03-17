/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.AST;
/*     */ import antlr.collections.ASTEnumeration;
/*     */ import antlr.collections.impl.ASTEnumerator;
/*     */ import antlr.collections.impl.Vector;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.io.Writer;
/*     */ 
/*     */ public abstract class BaseAST
/*     */   implements AST, Serializable
/*     */ {
/*     */   protected BaseAST down;
/*     */   protected BaseAST right;
/*  48 */   private static boolean verboseStringConversion = false;
/*  49 */   private static String[] tokenNames = null;
/*     */ 
/*     */   public void addChild(AST paramAST)
/*     */   {
/*  53 */     if (paramAST == null) return;
/*  54 */     BaseAST localBaseAST = this.down;
/*  55 */     if (localBaseAST != null) {
/*  56 */       while (localBaseAST.right != null) {
/*  57 */         localBaseAST = localBaseAST.right;
/*     */       }
/*  59 */       localBaseAST.right = ((BaseAST)paramAST);
/*     */     }
/*     */     else {
/*  62 */       this.down = ((BaseAST)paramAST);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getNumberOfChildren()
/*     */   {
/*  68 */     BaseAST localBaseAST = this.down;
/*  69 */     int i = 0;
/*  70 */     if (localBaseAST != null) {
/*  71 */       i = 1;
/*  72 */       while (localBaseAST.right != null) {
/*  73 */         localBaseAST = localBaseAST.right;
/*  74 */         i++;
/*     */       }
/*  76 */       return i;
/*     */     }
/*  78 */     return i;
/*     */   }
/*     */ 
/*     */   private static void doWorkForFindAll(AST paramAST1, Vector paramVector, AST paramAST2, boolean paramBoolean)
/*     */   {
/*  87 */     for (AST localAST = paramAST1; localAST != null; localAST = localAST.getNextSibling())
/*     */     {
/*  89 */       if (((paramBoolean) && (localAST.equalsTreePartial(paramAST2))) || ((!paramBoolean) && (localAST.equalsTree(paramAST2))))
/*     */       {
/*  91 */         paramVector.appendElement(localAST);
/*     */       }
/*     */ 
/*  94 */       if (localAST.getFirstChild() != null)
/*  95 */         doWorkForFindAll(localAST.getFirstChild(), paramVector, paramAST2, paramBoolean);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(AST paramAST)
/*     */   {
/* 102 */     if (paramAST == null) return false;
/* 103 */     if (((getText() == null) && (paramAST.getText() != null)) || ((getText() != null) && (paramAST.getText() == null)))
/*     */     {
/* 106 */       return false;
/*     */     }
/* 108 */     if ((getText() == null) && (paramAST.getText() == null)) {
/* 109 */       return getType() == paramAST.getType();
/*     */     }
/* 111 */     return (getText().equals(paramAST.getText())) && (getType() == paramAST.getType());
/*     */   }
/*     */ 
/*     */   public boolean equalsList(AST paramAST)
/*     */   {
/* 122 */     if (paramAST == null) {
/* 123 */       return false;
/*     */     }
/*     */ 
/* 127 */     Object localObject = this;
/*     */ 
/* 129 */     for (; (localObject != null) && (paramAST != null); 
/* 129 */       paramAST = paramAST.getNextSibling())
/*     */     {
/* 132 */       if (!((Serializable)localObject).equals(paramAST)) {
/* 133 */         return false;
/*     */       }
/*     */ 
/* 136 */       if (((Serializable)localObject).getFirstChild() != null) {
/* 137 */         if (!((Serializable)localObject).getFirstChild().equalsList(paramAST.getFirstChild())) {
/* 138 */           return false;
/*     */         }
/*     */ 
/*     */       }
/* 142 */       else if (paramAST.getFirstChild() != null)
/* 143 */         return false;
/* 129 */       localObject = ((Serializable)localObject).getNextSibling();
/*     */     }
/*     */ 
/* 146 */     if ((localObject == null) && (paramAST == null)) {
/* 147 */       return true;
/*     */     }
/*     */ 
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean equalsListPartial(AST paramAST)
/*     */   {
/* 160 */     if (paramAST == null) {
/* 161 */       return true;
/*     */     }
/*     */ 
/* 165 */     Object localObject = this;
/*     */ 
/* 167 */     for (; (localObject != null) && (paramAST != null); 
/* 167 */       paramAST = paramAST.getNextSibling())
/*     */     {
/* 169 */       if (!((Serializable)localObject).equals(paramAST)) return false;
/*     */ 
/* 171 */       if ((((Serializable)localObject).getFirstChild() != null) && 
/* 172 */         (!((Serializable)localObject).getFirstChild().equalsListPartial(paramAST.getFirstChild()))) return false;
/* 167 */       localObject = ((Serializable)localObject).getNextSibling();
/*     */     }
/*     */ 
/* 175 */     if ((localObject == null) && (paramAST != null))
/*     */     {
/* 177 */       return false;
/*     */     }
/*     */ 
/* 180 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean equalsTree(AST paramAST)
/*     */   {
/* 188 */     if (!equals(paramAST)) return false;
/*     */ 
/* 190 */     if (getFirstChild() != null) {
/* 191 */       if (!getFirstChild().equalsList(paramAST.getFirstChild())) return false;
/*     */ 
/*     */     }
/* 194 */     else if (paramAST.getFirstChild() != null) {
/* 195 */       return false;
/*     */     }
/* 197 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean equalsTreePartial(AST paramAST)
/*     */   {
/* 205 */     if (paramAST == null) {
/* 206 */       return true;
/*     */     }
/*     */ 
/* 210 */     if (!equals(paramAST)) return false;
/*     */ 
/* 212 */     if ((getFirstChild() != null) && 
/* 213 */       (!getFirstChild().equalsListPartial(paramAST.getFirstChild()))) return false;
/*     */ 
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */   public ASTEnumeration findAll(AST paramAST)
/*     */   {
/* 223 */     Vector localVector = new Vector(10);
/*     */ 
/* 227 */     if (paramAST == null) {
/* 228 */       return null;
/*     */     }
/*     */ 
/* 231 */     doWorkForFindAll(this, localVector, paramAST, false);
/*     */ 
/* 233 */     return new ASTEnumerator(localVector);
/*     */   }
/*     */ 
/*     */   public ASTEnumeration findAllPartial(AST paramAST)
/*     */   {
/* 241 */     Vector localVector = new Vector(10);
/*     */ 
/* 245 */     if (paramAST == null) {
/* 246 */       return null;
/*     */     }
/*     */ 
/* 249 */     doWorkForFindAll(this, localVector, paramAST, true);
/*     */ 
/* 251 */     return new ASTEnumerator(localVector);
/*     */   }
/*     */ 
/*     */   public AST getFirstChild()
/*     */   {
/* 256 */     return this.down;
/*     */   }
/*     */ 
/*     */   public AST getNextSibling()
/*     */   {
/* 261 */     return this.right;
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/* 266 */     return "";
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/* 271 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getLine() {
/* 275 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getColumn() {
/* 279 */     return 0;
/*     */   }
/*     */ 
/*     */   public abstract void initialize(int paramInt, String paramString);
/*     */ 
/*     */   public abstract void initialize(AST paramAST);
/*     */ 
/*     */   public abstract void initialize(Token paramToken);
/*     */ 
/*     */   public void removeChildren()
/*     */   {
/* 290 */     this.down = null;
/*     */   }
/*     */ 
/*     */   public void setFirstChild(AST paramAST) {
/* 294 */     this.down = ((BaseAST)paramAST);
/*     */   }
/*     */ 
/*     */   public void setNextSibling(AST paramAST) {
/* 298 */     this.right = ((BaseAST)paramAST);
/*     */   }
/*     */ 
/*     */   public void setText(String paramString)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setType(int paramInt)
/*     */   {
/*     */   }
/*     */ 
/*     */   public static void setVerboseStringConversion(boolean paramBoolean, String[] paramArrayOfString) {
/* 310 */     verboseStringConversion = paramBoolean;
/* 311 */     tokenNames = paramArrayOfString;
/*     */   }
/*     */ 
/*     */   public static String[] getTokenNames()
/*     */   {
/* 316 */     return tokenNames;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 320 */     StringBuffer localStringBuffer = new StringBuffer();
/*     */ 
/* 322 */     if ((verboseStringConversion) && (getText() != null) && (!getText().equalsIgnoreCase(tokenNames[getType()])) && (!getText().equalsIgnoreCase(StringUtils.stripFrontBack(tokenNames[getType()], "\"", "\""))))
/*     */     {
/* 326 */       localStringBuffer.append('[');
/* 327 */       localStringBuffer.append(getText());
/* 328 */       localStringBuffer.append(",<");
/* 329 */       localStringBuffer.append(tokenNames[getType()]);
/* 330 */       localStringBuffer.append(">]");
/* 331 */       return localStringBuffer.toString();
/*     */     }
/* 333 */     return getText();
/*     */   }
/*     */ 
/*     */   public String toStringList()
/*     */   {
/* 338 */     BaseAST localBaseAST = this;
/* 339 */     String str = "";
/* 340 */     if (localBaseAST.getFirstChild() != null) str = str + " (";
/* 341 */     str = str + " " + toString();
/* 342 */     if (localBaseAST.getFirstChild() != null) {
/* 343 */       str = str + ((BaseAST)localBaseAST.getFirstChild()).toStringList();
/*     */     }
/* 345 */     if (localBaseAST.getFirstChild() != null) str = str + " )";
/* 346 */     if (localBaseAST.getNextSibling() != null) {
/* 347 */       str = str + ((BaseAST)localBaseAST.getNextSibling()).toStringList();
/*     */     }
/* 349 */     return str;
/*     */   }
/*     */ 
/*     */   public String toStringTree() {
/* 353 */     BaseAST localBaseAST = this;
/* 354 */     String str = "";
/* 355 */     if (localBaseAST.getFirstChild() != null) str = str + " (";
/* 356 */     str = str + " " + toString();
/* 357 */     if (localBaseAST.getFirstChild() != null) {
/* 358 */       str = str + ((BaseAST)localBaseAST.getFirstChild()).toStringList();
/*     */     }
/* 360 */     if (localBaseAST.getFirstChild() != null) str = str + " )";
/* 361 */     return str;
/*     */   }
/*     */ 
/*     */   public static String decode(String paramString)
/*     */   {
/* 366 */     StringBuffer localStringBuffer = new StringBuffer();
/* 367 */     for (int i1 = 0; i1 < paramString.length(); i1++) {
/* 368 */       char c = paramString.charAt(i1);
/* 369 */       if (c == '&') {
/* 370 */         int i = paramString.charAt(i1 + 1);
/* 371 */         int j = paramString.charAt(i1 + 2);
/* 372 */         int k = paramString.charAt(i1 + 3);
/* 373 */         int m = paramString.charAt(i1 + 4);
/* 374 */         int n = paramString.charAt(i1 + 5);
/*     */ 
/* 376 */         if ((i == 97) && (j == 109) && (k == 112) && (m == 59)) {
/* 377 */           localStringBuffer.append("&");
/* 378 */           i1 += 5;
/*     */         }
/* 380 */         else if ((i == 108) && (j == 116) && (k == 59)) {
/* 381 */           localStringBuffer.append("<");
/* 382 */           i1 += 4;
/*     */         }
/* 384 */         else if ((i == 103) && (j == 116) && (k == 59)) {
/* 385 */           localStringBuffer.append(">");
/* 386 */           i1 += 4;
/*     */         }
/* 388 */         else if ((i == 113) && (j == 117) && (k == 111) && (m == 116) && (n == 59))
/*     */         {
/* 390 */           localStringBuffer.append("\"");
/* 391 */           i1 += 6;
/*     */         }
/* 393 */         else if ((i == 97) && (j == 112) && (k == 111) && (m == 115) && (n == 59))
/*     */         {
/* 395 */           localStringBuffer.append("'");
/* 396 */           i1 += 6;
/*     */         }
/*     */         else {
/* 399 */           localStringBuffer.append("&");
/*     */         }
/*     */       } else {
/* 402 */         localStringBuffer.append(c);
/*     */       }
/*     */     }
/* 404 */     return new String(localStringBuffer);
/*     */   }
/*     */ 
/*     */   public static String encode(String paramString)
/*     */   {
/* 409 */     StringBuffer localStringBuffer = new StringBuffer();
/* 410 */     for (int i = 0; i < paramString.length(); i++) {
/* 411 */       char c = paramString.charAt(i);
/* 412 */       switch (c)
/*     */       {
/*     */       case '&':
/* 415 */         localStringBuffer.append("&amp;");
/* 416 */         break;
/*     */       case '<':
/* 420 */         localStringBuffer.append("&lt;");
/* 421 */         break;
/*     */       case '>':
/* 425 */         localStringBuffer.append("&gt;");
/* 426 */         break;
/*     */       case '"':
/* 430 */         localStringBuffer.append("&quot;");
/* 431 */         break;
/*     */       case '\'':
/* 435 */         localStringBuffer.append("&apos;");
/* 436 */         break;
/*     */       default:
/* 440 */         localStringBuffer.append(c);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 445 */     return new String(localStringBuffer);
/*     */   }
/*     */ 
/*     */   public void xmlSerializeNode(Writer paramWriter) throws IOException
/*     */   {
/* 450 */     StringBuffer localStringBuffer = new StringBuffer(100);
/* 451 */     localStringBuffer.append("<");
/* 452 */     localStringBuffer.append(getClass().getName() + " ");
/* 453 */     localStringBuffer.append("text=\"" + encode(getText()) + "\" type=\"" + getType() + "\"/>");
/*     */ 
/* 455 */     paramWriter.write(localStringBuffer.toString());
/*     */   }
/*     */ 
/*     */   public void xmlSerializeRootOpen(Writer paramWriter) throws IOException
/*     */   {
/* 460 */     StringBuffer localStringBuffer = new StringBuffer(100);
/* 461 */     localStringBuffer.append("<");
/* 462 */     localStringBuffer.append(getClass().getName() + " ");
/* 463 */     localStringBuffer.append("text=\"" + encode(getText()) + "\" type=\"" + getType() + "\">\n");
/*     */ 
/* 465 */     paramWriter.write(localStringBuffer.toString());
/*     */   }
/*     */ 
/*     */   public void xmlSerializeRootClose(Writer paramWriter) throws IOException
/*     */   {
/* 470 */     paramWriter.write("</" + getClass().getName() + ">\n");
/*     */   }
/*     */ 
/*     */   public void xmlSerialize(Writer paramWriter) throws IOException
/*     */   {
/* 475 */     for (Object localObject = this; 
/* 476 */       localObject != null; 
/* 477 */       localObject = ((Serializable)localObject).getNextSibling())
/* 478 */       if (((Serializable)localObject).getFirstChild() == null)
/*     */       {
/* 480 */         ((BaseAST)localObject).xmlSerializeNode(paramWriter);
/*     */       }
/*     */       else {
/* 483 */         ((BaseAST)localObject).xmlSerializeRootOpen(paramWriter);
/*     */ 
/* 486 */         ((BaseAST)((Serializable)localObject).getFirstChild()).xmlSerialize(paramWriter);
/*     */ 
/* 489 */         ((BaseAST)localObject).xmlSerializeRootClose(paramWriter);
/*     */       }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.BaseAST
 * JD-Core Version:    0.6.2
 */