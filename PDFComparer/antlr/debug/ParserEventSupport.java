/*     */ package antlr.debug;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class ParserEventSupport
/*     */ {
/*     */   private Object source;
/*     */   private Hashtable doneListeners;
/*     */   private Vector matchListeners;
/*     */   private Vector messageListeners;
/*     */   private Vector tokenListeners;
/*     */   private Vector traceListeners;
/*     */   private Vector semPredListeners;
/*     */   private Vector synPredListeners;
/*     */   private Vector newLineListeners;
/*     */   private ParserMatchEvent matchEvent;
/*     */   private MessageEvent messageEvent;
/*     */   private ParserTokenEvent tokenEvent;
/*     */   private SemanticPredicateEvent semPredEvent;
/*     */   private SyntacticPredicateEvent synPredEvent;
/*     */   private TraceEvent traceEvent;
/*     */   private NewLineEvent newLineEvent;
/*     */   private ParserController controller;
/*     */   protected static final int CONSUME = 0;
/*     */   protected static final int ENTER_RULE = 1;
/*     */   protected static final int EXIT_RULE = 2;
/*     */   protected static final int LA = 3;
/*     */   protected static final int MATCH = 4;
/*     */   protected static final int MATCH_NOT = 5;
/*     */   protected static final int MISMATCH = 6;
/*     */   protected static final int MISMATCH_NOT = 7;
/*     */   protected static final int REPORT_ERROR = 8;
/*     */   protected static final int REPORT_WARNING = 9;
/*     */   protected static final int SEMPRED = 10;
/*     */   protected static final int SYNPRED_FAILED = 11;
/*     */   protected static final int SYNPRED_STARTED = 12;
/*     */   protected static final int SYNPRED_SUCCEEDED = 13;
/*     */   protected static final int NEW_LINE = 14;
/*     */   protected static final int DONE_PARSING = 15;
/*  50 */   private int ruleDepth = 0;
/*     */ 
/*     */   public ParserEventSupport(Object paramObject)
/*     */   {
/*  54 */     this.matchEvent = new ParserMatchEvent(paramObject);
/*  55 */     this.messageEvent = new MessageEvent(paramObject);
/*  56 */     this.tokenEvent = new ParserTokenEvent(paramObject);
/*  57 */     this.traceEvent = new TraceEvent(paramObject);
/*  58 */     this.semPredEvent = new SemanticPredicateEvent(paramObject);
/*  59 */     this.synPredEvent = new SyntacticPredicateEvent(paramObject);
/*  60 */     this.newLineEvent = new NewLineEvent(paramObject);
/*  61 */     this.source = paramObject;
/*     */   }
/*     */   public void addDoneListener(ListenerBase paramListenerBase) {
/*  64 */     if (this.doneListeners == null) this.doneListeners = new Hashtable();
/*  65 */     Integer localInteger = (Integer)this.doneListeners.get(paramListenerBase);
/*     */     int i;
/*  67 */     if (localInteger != null)
/*  68 */       i = localInteger.intValue() + 1;
/*     */     else
/*  70 */       i = 1;
/*  71 */     this.doneListeners.put(paramListenerBase, new Integer(i));
/*     */   }
/*     */   public void addMessageListener(MessageListener paramMessageListener) {
/*  74 */     if (this.messageListeners == null) this.messageListeners = new Vector();
/*  75 */     this.messageListeners.addElement(paramMessageListener);
/*  76 */     addDoneListener(paramMessageListener);
/*     */   }
/*     */   public void addNewLineListener(NewLineListener paramNewLineListener) {
/*  79 */     if (this.newLineListeners == null) this.newLineListeners = new Vector();
/*  80 */     this.newLineListeners.addElement(paramNewLineListener);
/*  81 */     addDoneListener(paramNewLineListener);
/*     */   }
/*     */   public void addParserListener(ParserListener paramParserListener) {
/*  84 */     if ((paramParserListener instanceof ParserController)) {
/*  85 */       ((ParserController)paramParserListener).setParserEventSupport(this);
/*  86 */       this.controller = ((ParserController)paramParserListener);
/*     */     }
/*  88 */     addParserMatchListener(paramParserListener);
/*  89 */     addParserTokenListener(paramParserListener);
/*     */ 
/*  91 */     addMessageListener(paramParserListener);
/*  92 */     addTraceListener(paramParserListener);
/*  93 */     addSemanticPredicateListener(paramParserListener);
/*  94 */     addSyntacticPredicateListener(paramParserListener);
/*     */   }
/*     */   public void addParserMatchListener(ParserMatchListener paramParserMatchListener) {
/*  97 */     if (this.matchListeners == null) this.matchListeners = new Vector();
/*  98 */     this.matchListeners.addElement(paramParserMatchListener);
/*  99 */     addDoneListener(paramParserMatchListener);
/*     */   }
/*     */   public void addParserTokenListener(ParserTokenListener paramParserTokenListener) {
/* 102 */     if (this.tokenListeners == null) this.tokenListeners = new Vector();
/* 103 */     this.tokenListeners.addElement(paramParserTokenListener);
/* 104 */     addDoneListener(paramParserTokenListener);
/*     */   }
/*     */   public void addSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener) {
/* 107 */     if (this.semPredListeners == null) this.semPredListeners = new Vector();
/* 108 */     this.semPredListeners.addElement(paramSemanticPredicateListener);
/* 109 */     addDoneListener(paramSemanticPredicateListener);
/*     */   }
/*     */   public void addSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener) {
/* 112 */     if (this.synPredListeners == null) this.synPredListeners = new Vector();
/* 113 */     this.synPredListeners.addElement(paramSyntacticPredicateListener);
/* 114 */     addDoneListener(paramSyntacticPredicateListener);
/*     */   }
/*     */   public void addTraceListener(TraceListener paramTraceListener) {
/* 117 */     if (this.traceListeners == null) this.traceListeners = new Vector();
/* 118 */     this.traceListeners.addElement(paramTraceListener);
/* 119 */     addDoneListener(paramTraceListener);
/*     */   }
/*     */   public void fireConsume(int paramInt) {
/* 122 */     this.tokenEvent.setValues(ParserTokenEvent.CONSUME, 1, paramInt);
/* 123 */     fireEvents(0, this.tokenListeners);
/*     */   }
/*     */   public void fireDoneParsing() {
/* 126 */     this.traceEvent.setValues(TraceEvent.DONE_PARSING, 0, 0, 0);
/*     */ 
/* 128 */     Hashtable localHashtable = null;
/*     */ 
/* 130 */     ListenerBase localListenerBase = null;
/*     */ 
/* 132 */     synchronized (this) {
/* 133 */       if (this.doneListeners == null) return;
/* 134 */       localHashtable = (Hashtable)this.doneListeners.clone();
/*     */     }
/*     */ 
/* 137 */     if (localHashtable != null) {
/* 138 */       ??? = localHashtable.keys();
/* 139 */       while (((Enumeration)???).hasMoreElements()) {
/* 140 */         localListenerBase = (ListenerBase)((Enumeration)???).nextElement();
/* 141 */         fireEvent(15, localListenerBase);
/*     */       }
/*     */     }
/* 144 */     if (this.controller != null)
/* 145 */       this.controller.checkBreak(); 
/*     */   }
/*     */ 
/* 148 */   public void fireEnterRule(int paramInt1, int paramInt2, int paramInt3) { this.ruleDepth += 1;
/* 149 */     this.traceEvent.setValues(TraceEvent.ENTER, paramInt1, paramInt2, paramInt3);
/* 150 */     fireEvents(1, this.traceListeners); }
/*     */ 
/*     */   public void fireEvent(int paramInt, ListenerBase paramListenerBase) {
/* 153 */     switch (paramInt) { case 0:
/* 154 */       ((ParserTokenListener)paramListenerBase).parserConsume(this.tokenEvent); break;
/*     */     case 3:
/* 155 */       ((ParserTokenListener)paramListenerBase).parserLA(this.tokenEvent); break;
/*     */     case 1:
/* 157 */       ((TraceListener)paramListenerBase).enterRule(this.traceEvent); break;
/*     */     case 2:
/* 158 */       ((TraceListener)paramListenerBase).exitRule(this.traceEvent); break;
/*     */     case 4:
/* 160 */       ((ParserMatchListener)paramListenerBase).parserMatch(this.matchEvent); break;
/*     */     case 5:
/* 161 */       ((ParserMatchListener)paramListenerBase).parserMatchNot(this.matchEvent); break;
/*     */     case 6:
/* 162 */       ((ParserMatchListener)paramListenerBase).parserMismatch(this.matchEvent); break;
/*     */     case 7:
/* 163 */       ((ParserMatchListener)paramListenerBase).parserMismatchNot(this.matchEvent); break;
/*     */     case 10:
/* 165 */       ((SemanticPredicateListener)paramListenerBase).semanticPredicateEvaluated(this.semPredEvent); break;
/*     */     case 12:
/* 167 */       ((SyntacticPredicateListener)paramListenerBase).syntacticPredicateStarted(this.synPredEvent); break;
/*     */     case 11:
/* 168 */       ((SyntacticPredicateListener)paramListenerBase).syntacticPredicateFailed(this.synPredEvent); break;
/*     */     case 13:
/* 169 */       ((SyntacticPredicateListener)paramListenerBase).syntacticPredicateSucceeded(this.synPredEvent); break;
/*     */     case 8:
/* 171 */       ((MessageListener)paramListenerBase).reportError(this.messageEvent); break;
/*     */     case 9:
/* 172 */       ((MessageListener)paramListenerBase).reportWarning(this.messageEvent); break;
/*     */     case 15:
/* 174 */       paramListenerBase.doneParsing(this.traceEvent); break;
/*     */     case 14:
/* 175 */       ((NewLineListener)paramListenerBase).hitNewLine(this.newLineEvent); break;
/*     */     default:
/* 178 */       throw new IllegalArgumentException("bad type " + paramInt + " for fireEvent()"); }
/*     */   }
/*     */ 
/*     */   public void fireEvents(int paramInt, Vector paramVector) {
/* 182 */     ListenerBase localListenerBase = null;
/*     */ 
/* 184 */     if (paramVector != null)
/* 185 */       for (int i = 0; i < paramVector.size(); i++) {
/* 186 */         localListenerBase = (ListenerBase)paramVector.elementAt(i);
/* 187 */         fireEvent(paramInt, localListenerBase);
/*     */       }
/* 189 */     if (this.controller != null)
/* 190 */       this.controller.checkBreak(); 
/*     */   }
/*     */ 
/* 193 */   public void fireExitRule(int paramInt1, int paramInt2, int paramInt3) { this.traceEvent.setValues(TraceEvent.EXIT, paramInt1, paramInt2, paramInt3);
/* 194 */     fireEvents(2, this.traceListeners);
/* 195 */     this.ruleDepth -= 1;
/* 196 */     if (this.ruleDepth == 0)
/* 197 */       fireDoneParsing(); }
/*     */ 
/*     */   public void fireLA(int paramInt1, int paramInt2) {
/* 200 */     this.tokenEvent.setValues(ParserTokenEvent.LA, paramInt1, paramInt2);
/* 201 */     fireEvents(3, this.tokenListeners);
/*     */   }
/*     */   public void fireMatch(char paramChar, int paramInt) {
/* 204 */     this.matchEvent.setValues(ParserMatchEvent.CHAR, paramChar, new Character(paramChar), null, paramInt, false, true);
/* 205 */     fireEvents(4, this.matchListeners);
/*     */   }
/*     */   public void fireMatch(char paramChar, BitSet paramBitSet, int paramInt) {
/* 208 */     this.matchEvent.setValues(ParserMatchEvent.CHAR_BITSET, paramChar, paramBitSet, null, paramInt, false, true);
/* 209 */     fireEvents(4, this.matchListeners);
/*     */   }
/*     */   public void fireMatch(char paramChar, String paramString, int paramInt) {
/* 212 */     this.matchEvent.setValues(ParserMatchEvent.CHAR_RANGE, paramChar, paramString, null, paramInt, false, true);
/* 213 */     fireEvents(4, this.matchListeners);
/*     */   }
/*     */   public void fireMatch(int paramInt1, BitSet paramBitSet, String paramString, int paramInt2) {
/* 216 */     this.matchEvent.setValues(ParserMatchEvent.BITSET, paramInt1, paramBitSet, paramString, paramInt2, false, true);
/* 217 */     fireEvents(4, this.matchListeners);
/*     */   }
/*     */   public void fireMatch(int paramInt1, String paramString, int paramInt2) {
/* 220 */     this.matchEvent.setValues(ParserMatchEvent.TOKEN, paramInt1, new Integer(paramInt1), paramString, paramInt2, false, true);
/* 221 */     fireEvents(4, this.matchListeners);
/*     */   }
/*     */   public void fireMatch(String paramString, int paramInt) {
/* 224 */     this.matchEvent.setValues(ParserMatchEvent.STRING, 0, paramString, null, paramInt, false, true);
/* 225 */     fireEvents(4, this.matchListeners);
/*     */   }
/*     */   public void fireMatchNot(char paramChar1, char paramChar2, int paramInt) {
/* 228 */     this.matchEvent.setValues(ParserMatchEvent.CHAR, paramChar1, new Character(paramChar2), null, paramInt, true, true);
/* 229 */     fireEvents(5, this.matchListeners);
/*     */   }
/*     */   public void fireMatchNot(int paramInt1, int paramInt2, String paramString, int paramInt3) {
/* 232 */     this.matchEvent.setValues(ParserMatchEvent.TOKEN, paramInt1, new Integer(paramInt2), paramString, paramInt3, true, true);
/* 233 */     fireEvents(5, this.matchListeners);
/*     */   }
/*     */   public void fireMismatch(char paramChar1, char paramChar2, int paramInt) {
/* 236 */     this.matchEvent.setValues(ParserMatchEvent.CHAR, paramChar1, new Character(paramChar2), null, paramInt, false, false);
/* 237 */     fireEvents(6, this.matchListeners);
/*     */   }
/*     */   public void fireMismatch(char paramChar, BitSet paramBitSet, int paramInt) {
/* 240 */     this.matchEvent.setValues(ParserMatchEvent.CHAR_BITSET, paramChar, paramBitSet, null, paramInt, false, true);
/* 241 */     fireEvents(6, this.matchListeners);
/*     */   }
/*     */   public void fireMismatch(char paramChar, String paramString, int paramInt) {
/* 244 */     this.matchEvent.setValues(ParserMatchEvent.CHAR_RANGE, paramChar, paramString, null, paramInt, false, true);
/* 245 */     fireEvents(6, this.matchListeners);
/*     */   }
/*     */   public void fireMismatch(int paramInt1, int paramInt2, String paramString, int paramInt3) {
/* 248 */     this.matchEvent.setValues(ParserMatchEvent.TOKEN, paramInt1, new Integer(paramInt2), paramString, paramInt3, false, false);
/* 249 */     fireEvents(6, this.matchListeners);
/*     */   }
/*     */   public void fireMismatch(int paramInt1, BitSet paramBitSet, String paramString, int paramInt2) {
/* 252 */     this.matchEvent.setValues(ParserMatchEvent.BITSET, paramInt1, paramBitSet, paramString, paramInt2, false, true);
/* 253 */     fireEvents(6, this.matchListeners);
/*     */   }
/*     */   public void fireMismatch(String paramString1, String paramString2, int paramInt) {
/* 256 */     this.matchEvent.setValues(ParserMatchEvent.STRING, 0, paramString2, paramString1, paramInt, false, true);
/* 257 */     fireEvents(6, this.matchListeners);
/*     */   }
/*     */   public void fireMismatchNot(char paramChar1, char paramChar2, int paramInt) {
/* 260 */     this.matchEvent.setValues(ParserMatchEvent.CHAR, paramChar1, new Character(paramChar2), null, paramInt, true, true);
/* 261 */     fireEvents(7, this.matchListeners);
/*     */   }
/*     */   public void fireMismatchNot(int paramInt1, int paramInt2, String paramString, int paramInt3) {
/* 264 */     this.matchEvent.setValues(ParserMatchEvent.TOKEN, paramInt1, new Integer(paramInt2), paramString, paramInt3, true, true);
/* 265 */     fireEvents(7, this.matchListeners);
/*     */   }
/*     */   public void fireNewLine(int paramInt) {
/* 268 */     this.newLineEvent.setValues(paramInt);
/* 269 */     fireEvents(14, this.newLineListeners);
/*     */   }
/*     */   public void fireReportError(Exception paramException) {
/* 272 */     this.messageEvent.setValues(MessageEvent.ERROR, paramException.toString());
/* 273 */     fireEvents(8, this.messageListeners);
/*     */   }
/*     */   public void fireReportError(String paramString) {
/* 276 */     this.messageEvent.setValues(MessageEvent.ERROR, paramString);
/* 277 */     fireEvents(8, this.messageListeners);
/*     */   }
/*     */   public void fireReportWarning(String paramString) {
/* 280 */     this.messageEvent.setValues(MessageEvent.WARNING, paramString);
/* 281 */     fireEvents(9, this.messageListeners);
/*     */   }
/*     */   public boolean fireSemanticPredicateEvaluated(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3) {
/* 284 */     this.semPredEvent.setValues(paramInt1, paramInt2, paramBoolean, paramInt3);
/* 285 */     fireEvents(10, this.semPredListeners);
/* 286 */     return paramBoolean;
/*     */   }
/*     */   public void fireSyntacticPredicateFailed(int paramInt) {
/* 289 */     this.synPredEvent.setValues(0, paramInt);
/* 290 */     fireEvents(11, this.synPredListeners);
/*     */   }
/*     */   public void fireSyntacticPredicateStarted(int paramInt) {
/* 293 */     this.synPredEvent.setValues(0, paramInt);
/* 294 */     fireEvents(12, this.synPredListeners);
/*     */   }
/*     */   public void fireSyntacticPredicateSucceeded(int paramInt) {
/* 297 */     this.synPredEvent.setValues(0, paramInt);
/* 298 */     fireEvents(13, this.synPredListeners);
/*     */   }
/*     */ 
/*     */   protected void refresh(Vector paramVector)
/*     */   {
/*     */     Vector localVector;
/* 302 */     synchronized (paramVector) {
/* 303 */       localVector = (Vector)paramVector.clone();
/*     */     }
/* 305 */     if (localVector != null)
/* 306 */       for (int i = 0; i < localVector.size(); i++)
/* 307 */         ((ListenerBase)localVector.elementAt(i)).refresh(); 
/*     */   }
/*     */ 
/* 310 */   public void refreshListeners() { refresh(this.matchListeners);
/* 311 */     refresh(this.messageListeners);
/* 312 */     refresh(this.tokenListeners);
/* 313 */     refresh(this.traceListeners);
/* 314 */     refresh(this.semPredListeners);
/* 315 */     refresh(this.synPredListeners); }
/*     */ 
/*     */   public void removeDoneListener(ListenerBase paramListenerBase) {
/* 318 */     if (this.doneListeners == null) return;
/* 319 */     Integer localInteger = (Integer)this.doneListeners.get(paramListenerBase);
/* 320 */     int i = 0;
/* 321 */     if (localInteger != null) {
/* 322 */       i = localInteger.intValue() - 1;
/*     */     }
/* 324 */     if (i == 0)
/* 325 */       this.doneListeners.remove(paramListenerBase);
/*     */     else
/* 327 */       this.doneListeners.put(paramListenerBase, new Integer(i)); 
/*     */   }
/*     */ 
/* 330 */   public void removeMessageListener(MessageListener paramMessageListener) { if (this.messageListeners != null)
/* 331 */       this.messageListeners.removeElement(paramMessageListener);
/* 332 */     removeDoneListener(paramMessageListener); }
/*     */ 
/*     */   public void removeNewLineListener(NewLineListener paramNewLineListener) {
/* 335 */     if (this.newLineListeners != null)
/* 336 */       this.newLineListeners.removeElement(paramNewLineListener);
/* 337 */     removeDoneListener(paramNewLineListener);
/*     */   }
/*     */   public void removeParserListener(ParserListener paramParserListener) {
/* 340 */     removeParserMatchListener(paramParserListener);
/* 341 */     removeMessageListener(paramParserListener);
/* 342 */     removeParserTokenListener(paramParserListener);
/* 343 */     removeTraceListener(paramParserListener);
/* 344 */     removeSemanticPredicateListener(paramParserListener);
/* 345 */     removeSyntacticPredicateListener(paramParserListener);
/*     */   }
/*     */   public void removeParserMatchListener(ParserMatchListener paramParserMatchListener) {
/* 348 */     if (this.matchListeners != null)
/* 349 */       this.matchListeners.removeElement(paramParserMatchListener);
/* 350 */     removeDoneListener(paramParserMatchListener);
/*     */   }
/*     */   public void removeParserTokenListener(ParserTokenListener paramParserTokenListener) {
/* 353 */     if (this.tokenListeners != null)
/* 354 */       this.tokenListeners.removeElement(paramParserTokenListener);
/* 355 */     removeDoneListener(paramParserTokenListener);
/*     */   }
/*     */   public void removeSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener) {
/* 358 */     if (this.semPredListeners != null)
/* 359 */       this.semPredListeners.removeElement(paramSemanticPredicateListener);
/* 360 */     removeDoneListener(paramSemanticPredicateListener);
/*     */   }
/*     */   public void removeSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener) {
/* 363 */     if (this.synPredListeners != null)
/* 364 */       this.synPredListeners.removeElement(paramSyntacticPredicateListener);
/* 365 */     removeDoneListener(paramSyntacticPredicateListener);
/*     */   }
/*     */   public void removeTraceListener(TraceListener paramTraceListener) {
/* 368 */     if (this.traceListeners != null)
/* 369 */       this.traceListeners.removeElement(paramTraceListener);
/* 370 */     removeDoneListener(paramTraceListener);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.ParserEventSupport
 * JD-Core Version:    0.6.2
 */