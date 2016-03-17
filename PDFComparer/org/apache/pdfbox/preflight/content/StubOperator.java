/*     */ package org.apache.pdfbox.preflight.content;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.util.PDFOperator;
/*     */ import org.apache.pdfbox.util.operator.OperatorProcessor;
/*     */ 
/*     */ public class StubOperator extends OperatorProcessor
/*     */ {
/*     */   public void process(PDFOperator operator, List<COSBase> arguments)
/*     */     throws IOException
/*     */   {
/*  66 */     String op = operator.getOperation();
/*  67 */     if ("S".equals(op))
/*     */     {
/*  69 */       checkNoOperands(arguments);
/*     */     }
/*  71 */     else if ("B".equals(op))
/*     */     {
/*  73 */       checkNoOperands(arguments);
/*     */     }
/*  75 */     else if ("f".equals(op))
/*     */     {
/*  77 */       checkNoOperands(arguments);
/*     */     }
/*  79 */     else if ("F".equals(op))
/*     */     {
/*  81 */       checkNoOperands(arguments);
/*     */     }
/*  83 */     else if ("f*".equals(op))
/*     */     {
/*  85 */       checkNoOperands(arguments);
/*     */     }
/*  87 */     else if ("b".equals(op))
/*     */     {
/*  89 */       checkNoOperands(arguments);
/*     */     }
/*  91 */     else if ("B*".equals(op))
/*     */     {
/*  93 */       checkNoOperands(arguments);
/*     */     }
/*  95 */     else if ("b*".equals(op))
/*     */     {
/*  97 */       checkNoOperands(arguments);
/*     */     }
/*  99 */     else if ("s".equals(op))
/*     */     {
/* 101 */       checkNoOperands(arguments);
/*     */     }
/* 103 */     else if ("EMC".equals(op))
/*     */     {
/* 105 */       checkNoOperands(arguments);
/*     */     }
/* 107 */     else if ("BMC".equals(op))
/*     */     {
/* 109 */       checkStringOperands(arguments, 1);
/*     */     }
/* 111 */     else if ("BDC".equals(op))
/*     */     {
/* 113 */       checkTagAndPropertyOperands(arguments);
/*     */     }
/* 115 */     else if ("DP".equals(op))
/*     */     {
/* 117 */       checkTagAndPropertyOperands(arguments);
/*     */     }
/* 119 */     else if ("c".equals(op))
/*     */     {
/* 121 */       checkNumberOperands(arguments, 6);
/*     */     }
/* 123 */     else if ("v".equals(op))
/*     */     {
/* 125 */       checkNumberOperands(arguments, 4);
/*     */     }
/* 127 */     else if ("y".equals(op))
/*     */     {
/* 129 */       checkNumberOperands(arguments, 4);
/*     */     }
/* 131 */     else if ("d0".equals(op))
/*     */     {
/* 133 */       checkNumberOperands(arguments, 2);
/*     */     }
/* 135 */     else if ("d1".equals(op))
/*     */     {
/* 137 */       checkNumberOperands(arguments, 6);
/*     */     }
/* 139 */     else if ("g".equals(op))
/*     */     {
/* 141 */       checkNumberOperands(arguments, 1);
/*     */     }
/* 143 */     else if ("G".equals(op))
/*     */     {
/* 145 */       checkNumberOperands(arguments, 1);
/*     */     }
/* 147 */     else if ("gs".equals(op))
/*     */     {
/* 149 */       checkStringOperands(arguments, 1);
/*     */     }
/* 151 */     else if ("h".equals(op))
/*     */     {
/* 153 */       checkNoOperands(arguments);
/*     */     }
/* 155 */     else if ("i".equals(op))
/*     */     {
/* 157 */       checkNumberOperands(arguments, 1);
/*     */     }
/* 159 */     else if ("l".equals(op))
/*     */     {
/* 161 */       checkNumberOperands(arguments, 2);
/*     */     }
/* 163 */     else if ("m".equals(op))
/*     */     {
/* 165 */       checkNumberOperands(arguments, 2);
/*     */     }
/* 167 */     else if ("M".equals(op))
/*     */     {
/* 169 */       checkNumberOperands(arguments, 1);
/*     */     }
/* 171 */     else if ("MP".equals(op))
/*     */     {
/* 173 */       checkStringOperands(arguments, 1);
/*     */     }
/* 175 */     else if ("n".equals(op))
/*     */     {
/* 177 */       checkNoOperands(arguments);
/*     */     }
/* 179 */     else if ("re".equals(op))
/*     */     {
/* 181 */       checkNumberOperands(arguments, 4);
/*     */     }
/* 183 */     else if ("ri".equals(op))
/*     */     {
/* 185 */       checkStringOperands(arguments, 1);
/*     */     }
/* 187 */     else if ("s".equals(op))
/*     */     {
/* 189 */       checkNoOperands(arguments);
/*     */     }
/* 191 */     else if ("S".equals(op))
/*     */     {
/* 193 */       checkNoOperands(arguments);
/*     */     }
/* 195 */     else if ("sh".equals(op))
/*     */     {
/* 197 */       checkStringOperands(arguments, 1);
/*     */     }
/* 199 */     else if ("'".equals(op))
/*     */     {
/* 201 */       checkStringOperands(arguments, 1);
/*     */     }
/* 203 */     else if ("Tj".equals(op))
/*     */     {
/* 205 */       checkStringOperands(arguments, 1);
/*     */     }
/* 207 */     else if ("TJ".equals(op))
/*     */     {
/* 209 */       checkArrayOperands(arguments, 1);
/*     */     }
/* 211 */     else if ("W".equals(op))
/*     */     {
/* 213 */       checkNoOperands(arguments);
/*     */     }
/* 215 */     else if ("W*".equals(op))
/*     */     {
/* 217 */       checkNoOperands(arguments);
/*     */     }
/* 219 */     else if ("\"".equals(op))
/*     */     {
/* 221 */       checkNumberOperands(arguments.subList(0, 2), 2);
/* 222 */       checkStringOperands(arguments.subList(2, arguments.size()), 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkNoOperands(List<COSBase> arguments)
/*     */     throws ContentStreamException
/*     */   {
/* 238 */     if ((arguments != null) && (!arguments.isEmpty()))
/*     */     {
/* 240 */       throw createInvalidArgumentsError();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkStringOperands(List<COSBase> arguments, int length)
/*     */     throws ContentStreamException
/*     */   {
/* 253 */     if ((arguments == null) || (arguments.isEmpty()) || (arguments.size() != length))
/*     */     {
/* 255 */       throw createInvalidArgumentsError();
/*     */     }
/*     */ 
/* 258 */     for (int i = 0; i < length; i++)
/*     */     {
/* 260 */       COSBase arg = (COSBase)arguments.get(i);
/* 261 */       if ((!(arg instanceof COSName)) && (!(arg instanceof COSString)))
/*     */       {
/* 263 */         throw createInvalidArgumentsError();
/*     */       }
/*     */ 
/* 266 */       if (((arg instanceof COSName)) && (((COSName)arg).getName().length() > 127))
/*     */       {
/* 268 */         throw createLimitError("1.0.3", "A Name operand is too long");
/*     */       }
/*     */ 
/* 271 */       if (((arg instanceof COSString)) && (((COSString)arg).getString().getBytes().length > 65535))
/*     */       {
/* 273 */         throw createLimitError("1.0.4", "A String operand is too long");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkArrayOperands(List<COSBase> arguments, int length)
/*     */     throws ContentStreamException
/*     */   {
/* 287 */     if ((arguments == null) || (arguments.isEmpty()) || (arguments.size() != length))
/*     */     {
/* 289 */       throw createInvalidArgumentsError();
/*     */     }
/*     */ 
/* 292 */     for (int i = 0; i < length; i++)
/*     */     {
/* 294 */       COSBase arg = (COSBase)arguments.get(i);
/* 295 */       if (!(arg instanceof COSArray))
/*     */       {
/* 297 */         throw createInvalidArgumentsError();
/*     */       }
/*     */ 
/* 300 */       if (((COSArray)arg).size() > 8191)
/*     */       {
/* 302 */         throw createLimitError("1.0.2", "Array has " + ((COSArray)arg).size() + " elements");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkNumberOperands(List<COSBase> arguments, int length)
/*     */     throws ContentStreamException
/*     */   {
/* 320 */     if ((arguments == null) || (arguments.isEmpty()) || (arguments.size() != length))
/*     */     {
/* 322 */       throw createInvalidArgumentsError();
/*     */     }
/*     */ 
/* 325 */     for (int i = 0; i < length; i++)
/*     */     {
/* 327 */       COSBase arg = (COSBase)arguments.get(i);
/* 328 */       if ((!(arg instanceof COSFloat)) && (!(arg instanceof COSInteger)))
/*     */       {
/* 330 */         throw createInvalidArgumentsError();
/*     */       }
/*     */ 
/* 333 */       if (((arg instanceof COSInteger)) && ((((COSInteger)arg).longValue() > 2147483647L) || (((COSInteger)arg).longValue() < -2147483648L)))
/*     */       {
/* 336 */         throw createLimitError("1.0.6", "Invalid integer range in a Number operands");
/*     */       }
/*     */ 
/* 339 */       if (((arg instanceof COSFloat)) && ((((COSFloat)arg).doubleValue() > 32767.0D) || (((COSFloat)arg).doubleValue() < -32767.0D)))
/*     */       {
/* 342 */         throw createLimitError("1.0.6", "Invalid float range in a Number operands");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkTagAndPropertyOperands(List<COSBase> arguments)
/*     */     throws ContentStreamException
/*     */   {
/* 356 */     if ((arguments == null) || (arguments.isEmpty()) || (arguments.size() != 2))
/*     */     {
/* 358 */       throw createInvalidArgumentsError();
/*     */     }
/*     */ 
/* 361 */     COSBase arg = (COSBase)arguments.get(0);
/* 362 */     if ((!(arg instanceof COSName)) && (!(arg instanceof COSString)))
/*     */     {
/* 364 */       throw createInvalidArgumentsError();
/*     */     }
/*     */ 
/* 367 */     if (((arg instanceof COSName)) && (((COSName)arg).getName().length() > 127))
/*     */     {
/* 369 */       throw createLimitError("1.0.3", "A Name operand is too long");
/*     */     }
/*     */ 
/* 372 */     if (((arg instanceof COSString)) && (((COSString)arg).getString().getBytes().length > 65535))
/*     */     {
/* 374 */       throw createLimitError("1.0.4", "A String operand is too long");
/*     */     }
/*     */ 
/* 377 */     COSBase arg2 = (COSBase)arguments.get(1);
/* 378 */     if ((!(arg2 instanceof COSName)) && (!(arg2 instanceof COSString)) && (!(arg2 instanceof COSDictionary)))
/*     */     {
/* 380 */       throw createInvalidArgumentsError();
/*     */     }
/*     */ 
/* 383 */     if (((arg2 instanceof COSName)) && (((COSName)arg2).getName().length() > 127))
/*     */     {
/* 385 */       throw createLimitError("1.0.3", "A Name operand is too long");
/*     */     }
/*     */ 
/* 388 */     if (((arg2 instanceof COSString)) && (((COSString)arg2).getString().getBytes().length > 65535))
/*     */     {
/* 390 */       throw createLimitError("1.0.4", "A String operand is too long");
/*     */     }
/*     */ 
/* 393 */     if (((arg2 instanceof COSDictionary)) && (((COSDictionary)arg2).size() > 4095))
/*     */     {
/* 395 */       throw createLimitError("1.0.1", "Dictionary has " + ((COSDictionary)arg2).size() + " entries");
/*     */     }
/*     */   }
/*     */ 
/*     */   private ContentStreamException createInvalidArgumentsError()
/*     */   {
/* 407 */     ContentStreamException ex = new ContentStreamException("Invalid arguments");
/* 408 */     ex.setErrorCode("1.2.11");
/* 409 */     return ex;
/*     */   }
/*     */ 
/*     */   private ContentStreamException createLimitError(String errorCode, String details)
/*     */   {
/* 419 */     ContentStreamException ex = new ContentStreamException(details);
/* 420 */     ex.setErrorCode(errorCode);
/* 421 */     return ex;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.content.StubOperator
 * JD-Core Version:    0.6.2
 */