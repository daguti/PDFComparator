/*     */ package org.apache.pdfbox.pdmodel.common.function.type4;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class Operators
/*     */ {
/*  29 */   private static final Operator ABS = new ArithmeticOperators.Abs();
/*  30 */   private static final Operator ADD = new ArithmeticOperators.Add();
/*  31 */   private static final Operator ATAN = new ArithmeticOperators.Atan();
/*  32 */   private static final Operator CEILING = new ArithmeticOperators.Ceiling();
/*  33 */   private static final Operator COS = new ArithmeticOperators.Cos();
/*  34 */   private static final Operator CVI = new ArithmeticOperators.Cvi();
/*  35 */   private static final Operator CVR = new ArithmeticOperators.Cvr();
/*  36 */   private static final Operator DIV = new ArithmeticOperators.Div();
/*  37 */   private static final Operator EXP = new ArithmeticOperators.Exp();
/*  38 */   private static final Operator FLOOR = new ArithmeticOperators.Floor();
/*  39 */   private static final Operator IDIV = new ArithmeticOperators.IDiv();
/*  40 */   private static final Operator LN = new ArithmeticOperators.Ln();
/*  41 */   private static final Operator LOG = new ArithmeticOperators.Log();
/*  42 */   private static final Operator MOD = new ArithmeticOperators.Mod();
/*  43 */   private static final Operator MUL = new ArithmeticOperators.Mul();
/*  44 */   private static final Operator NEG = new ArithmeticOperators.Neg();
/*  45 */   private static final Operator ROUND = new ArithmeticOperators.Round();
/*  46 */   private static final Operator SIN = new ArithmeticOperators.Sin();
/*  47 */   private static final Operator SQRT = new ArithmeticOperators.Sqrt();
/*  48 */   private static final Operator SUB = new ArithmeticOperators.Sub();
/*  49 */   private static final Operator TRUNCATE = new ArithmeticOperators.Truncate();
/*     */ 
/*  52 */   private static final Operator AND = new BitwiseOperators.And();
/*  53 */   private static final Operator BITSHIFT = new BitwiseOperators.Bitshift();
/*  54 */   private static final Operator EQ = new RelationalOperators.Eq();
/*  55 */   private static final Operator FALSE = new BitwiseOperators.False();
/*  56 */   private static final Operator GE = new RelationalOperators.Ge();
/*  57 */   private static final Operator GT = new RelationalOperators.Gt();
/*  58 */   private static final Operator LE = new RelationalOperators.Le();
/*  59 */   private static final Operator LT = new RelationalOperators.Lt();
/*  60 */   private static final Operator NE = new RelationalOperators.Ne();
/*  61 */   private static final Operator NOT = new BitwiseOperators.Not();
/*  62 */   private static final Operator OR = new BitwiseOperators.Or();
/*  63 */   private static final Operator TRUE = new BitwiseOperators.True();
/*  64 */   private static final Operator XOR = new BitwiseOperators.Xor();
/*     */ 
/*  67 */   private static final Operator IF = new ConditionalOperators.If();
/*  68 */   private static final Operator IFELSE = new ConditionalOperators.IfElse();
/*     */ 
/*  71 */   private static final Operator COPY = new StackOperators.Copy();
/*  72 */   private static final Operator DUP = new StackOperators.Dup();
/*  73 */   private static final Operator EXCH = new StackOperators.Exch();
/*  74 */   private static final Operator INDEX = new StackOperators.Index();
/*  75 */   private static final Operator POP = new StackOperators.Pop();
/*  76 */   private static final Operator ROLL = new StackOperators.Roll();
/*     */ 
/*  78 */   private Map<String, Operator> operators = new HashMap();
/*     */ 
/*     */   public Operators()
/*     */   {
/*  85 */     this.operators.put("add", ADD);
/*  86 */     this.operators.put("abs", ABS);
/*  87 */     this.operators.put("atan", ATAN);
/*  88 */     this.operators.put("ceiling", CEILING);
/*  89 */     this.operators.put("cos", COS);
/*  90 */     this.operators.put("cvi", CVI);
/*  91 */     this.operators.put("cvr", CVR);
/*  92 */     this.operators.put("div", DIV);
/*  93 */     this.operators.put("exp", EXP);
/*  94 */     this.operators.put("floor", FLOOR);
/*  95 */     this.operators.put("idiv", IDIV);
/*  96 */     this.operators.put("ln", LN);
/*  97 */     this.operators.put("log", LOG);
/*  98 */     this.operators.put("mod", MOD);
/*  99 */     this.operators.put("mul", MUL);
/* 100 */     this.operators.put("neg", NEG);
/* 101 */     this.operators.put("round", ROUND);
/* 102 */     this.operators.put("sin", SIN);
/* 103 */     this.operators.put("sqrt", SQRT);
/* 104 */     this.operators.put("sub", SUB);
/* 105 */     this.operators.put("truncate", TRUNCATE);
/*     */ 
/* 107 */     this.operators.put("and", AND);
/* 108 */     this.operators.put("bitshift", BITSHIFT);
/* 109 */     this.operators.put("eq", EQ);
/* 110 */     this.operators.put("false", FALSE);
/* 111 */     this.operators.put("ge", GE);
/* 112 */     this.operators.put("gt", GT);
/* 113 */     this.operators.put("le", LE);
/* 114 */     this.operators.put("lt", LT);
/* 115 */     this.operators.put("ne", NE);
/* 116 */     this.operators.put("not", NOT);
/* 117 */     this.operators.put("or", OR);
/* 118 */     this.operators.put("true", TRUE);
/* 119 */     this.operators.put("xor", XOR);
/*     */ 
/* 121 */     this.operators.put("if", IF);
/* 122 */     this.operators.put("ifelse", IFELSE);
/*     */ 
/* 124 */     this.operators.put("copy", COPY);
/* 125 */     this.operators.put("dup", DUP);
/* 126 */     this.operators.put("exch", EXCH);
/* 127 */     this.operators.put("index", INDEX);
/* 128 */     this.operators.put("pop", POP);
/* 129 */     this.operators.put("roll", ROLL);
/*     */   }
/*     */ 
/*     */   public Operator getOperator(String operatorName)
/*     */   {
/* 139 */     return (Operator)this.operators.get(operatorName);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.function.type4.Operators
 * JD-Core Version:    0.6.2
 */