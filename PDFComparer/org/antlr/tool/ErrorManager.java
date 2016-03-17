/*     */ package org.antlr.tool;
/*     */ 
/*     */ import antlr.RecognitionException;
/*     */ import antlr.Token;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.antlr.Tool;
/*     */ import org.antlr.analysis.DFA;
/*     */ import org.antlr.analysis.DFAState;
/*     */ import org.antlr.analysis.DecisionProbe;
/*     */ import org.antlr.misc.BitSet;
/*     */ import org.antlr.misc.IntSet;
/*     */ import org.antlr.stringtemplate.StringTemplate;
/*     */ import org.antlr.stringtemplate.StringTemplateErrorListener;
/*     */ import org.antlr.stringtemplate.StringTemplateGroup;
/*     */ import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;
/*     */ 
/*     */ public class ErrorManager
/*     */ {
/*     */   public static final int MSG_CANNOT_WRITE_FILE = 1;
/*     */   public static final int MSG_CANNOT_CLOSE_FILE = 2;
/*     */   public static final int MSG_CANNOT_FIND_TOKENS_FILE = 3;
/*     */   public static final int MSG_ERROR_READING_TOKENS_FILE = 4;
/*     */   public static final int MSG_DIR_NOT_FOUND = 5;
/*     */   public static final int MSG_OUTPUT_DIR_IS_FILE = 6;
/*     */   public static final int MSG_CANNOT_OPEN_FILE = 7;
/*     */   public static final int MSG_FILE_AND_GRAMMAR_NAME_DIFFER = 8;
/*     */   public static final int MSG_FILENAME_EXTENSION_ERROR = 9;
/*     */   public static final int MSG_INTERNAL_ERROR = 10;
/*     */   public static final int MSG_INTERNAL_WARNING = 11;
/*     */   public static final int MSG_ERROR_CREATING_ARTIFICIAL_RULE = 12;
/*     */   public static final int MSG_TOKENS_FILE_SYNTAX_ERROR = 13;
/*     */   public static final int MSG_CANNOT_GEN_DOT_FILE = 14;
/*     */   public static final int MSG_BAD_AST_STRUCTURE = 15;
/*     */   public static final int MSG_BAD_ACTION_AST_STRUCTURE = 16;
/*     */   public static final int MSG_MISSING_CODE_GEN_TEMPLATES = 20;
/*     */   public static final int MSG_MISSING_CYCLIC_DFA_CODE_GEN_TEMPLATES = 21;
/*     */   public static final int MSG_CODE_GEN_TEMPLATES_INCOMPLETE = 22;
/*     */   public static final int MSG_CANNOT_CREATE_TARGET_GENERATOR = 23;
/*     */   public static final int MSG_SYNTAX_ERROR = 100;
/*     */   public static final int MSG_RULE_REDEFINITION = 101;
/*     */   public static final int MSG_LEXER_RULES_NOT_ALLOWED = 102;
/*     */   public static final int MSG_PARSER_RULES_NOT_ALLOWED = 103;
/*     */   public static final int MSG_CANNOT_FIND_ATTRIBUTE_NAME_IN_DECL = 104;
/*     */   public static final int MSG_NO_TOKEN_DEFINITION = 105;
/*     */   public static final int MSG_UNDEFINED_RULE_REF = 106;
/*     */   public static final int MSG_LITERAL_NOT_ASSOCIATED_WITH_LEXER_RULE = 107;
/*     */   public static final int MSG_CANNOT_ALIAS_TOKENS_IN_LEXER = 108;
/*     */   public static final int MSG_ATTRIBUTE_REF_NOT_IN_RULE = 111;
/*     */   public static final int MSG_INVALID_RULE_SCOPE_ATTRIBUTE_REF = 112;
/*     */   public static final int MSG_UNKNOWN_ATTRIBUTE_IN_SCOPE = 113;
/*     */   public static final int MSG_UNKNOWN_SIMPLE_ATTRIBUTE = 114;
/*     */   public static final int MSG_INVALID_RULE_PARAMETER_REF = 115;
/*     */   public static final int MSG_UNKNOWN_RULE_ATTRIBUTE = 116;
/*     */   public static final int MSG_ISOLATED_RULE_SCOPE = 117;
/*     */   public static final int MSG_SYMBOL_CONFLICTS_WITH_GLOBAL_SCOPE = 118;
/*     */   public static final int MSG_LABEL_CONFLICTS_WITH_RULE = 119;
/*     */   public static final int MSG_LABEL_CONFLICTS_WITH_TOKEN = 120;
/*     */   public static final int MSG_LABEL_CONFLICTS_WITH_RULE_SCOPE_ATTRIBUTE = 121;
/*     */   public static final int MSG_LABEL_CONFLICTS_WITH_RULE_ARG_RETVAL = 122;
/*     */   public static final int MSG_ATTRIBUTE_CONFLICTS_WITH_RULE = 123;
/*     */   public static final int MSG_ATTRIBUTE_CONFLICTS_WITH_RULE_ARG_RETVAL = 124;
/*     */   public static final int MSG_LABEL_TYPE_CONFLICT = 125;
/*     */   public static final int MSG_ARG_RETVAL_CONFLICT = 126;
/*     */   public static final int MSG_NONUNIQUE_REF = 127;
/*     */   public static final int MSG_FORWARD_ELEMENT_REF = 128;
/*     */   public static final int MSG_MISSING_RULE_ARGS = 129;
/*     */   public static final int MSG_RULE_HAS_NO_ARGS = 130;
/*     */   public static final int MSG_ARGS_ON_TOKEN_REF = 131;
/*     */   public static final int MSG_RULE_REF_AMBIG_WITH_RULE_IN_ALT = 132;
/*     */   public static final int MSG_ILLEGAL_OPTION = 133;
/*     */   public static final int MSG_LIST_LABEL_INVALID_UNLESS_RETVAL_STRUCT = 134;
/*     */   public static final int MSG_UNDEFINED_TOKEN_REF_IN_REWRITE = 135;
/*     */   public static final int MSG_REWRITE_ELEMENT_NOT_PRESENT_ON_LHS = 136;
/*     */   public static final int MSG_UNDEFINED_LABEL_REF_IN_REWRITE = 137;
/*     */   public static final int MSG_NO_GRAMMAR_START_RULE = 138;
/*     */   public static final int MSG_EMPTY_COMPLEMENT = 139;
/*     */   public static final int MSG_UNKNOWN_DYNAMIC_SCOPE = 140;
/*     */   public static final int MSG_UNKNOWN_DYNAMIC_SCOPE_ATTRIBUTE = 141;
/*     */   public static final int MSG_ISOLATED_RULE_ATTRIBUTE = 142;
/*     */   public static final int MSG_INVALID_ACTION_SCOPE = 143;
/*     */   public static final int MSG_ACTION_REDEFINITION = 144;
/*     */   public static final int MSG_DOUBLE_QUOTES_ILLEGAL = 145;
/*     */   public static final int MSG_INVALID_TEMPLATE_ACTION = 146;
/*     */   public static final int MSG_MISSING_ATTRIBUTE_NAME = 147;
/*     */   public static final int MSG_ARG_INIT_VALUES_ILLEGAL = 148;
/*     */   public static final int MSG_REWRITE_OR_OP_WITH_NO_OUTPUT_OPTION = 149;
/*     */   public static final int MSG_NO_RULES = 150;
/*     */   public static final int MSG_WRITE_TO_READONLY_ATTR = 151;
/*     */   public static final int MSG_MISSING_AST_TYPE_IN_TREE_GRAMMAR = 152;
/*     */   public static final int MSG_REWRITE_FOR_MULTI_ELEMENT_ALT = 153;
/*     */   public static final int MSG_RULE_INVALID_SET = 154;
/*     */   public static final int MSG_HETERO_ILLEGAL_IN_REWRITE_ALT = 155;
/*     */   public static final int MSG_NO_SUCH_GRAMMAR_SCOPE = 156;
/*     */   public static final int MSG_NO_SUCH_RULE_IN_SCOPE = 157;
/*     */   public static final int MSG_TOKEN_ALIAS_CONFLICT = 158;
/*     */   public static final int MSG_TOKEN_ALIAS_REASSIGNMENT = 159;
/*     */   public static final int MSG_TOKEN_VOCAB_IN_DELEGATE = 160;
/*     */   public static final int MSG_INVALID_IMPORT = 161;
/*     */   public static final int MSG_IMPORTED_TOKENS_RULE_EMPTY = 162;
/*     */   public static final int MSG_IMPORT_NAME_CLASH = 163;
/*     */   public static final int MSG_AST_OP_WITH_NON_AST_OUTPUT_OPTION = 164;
/*     */   public static final int MSG_AST_OP_IN_ALT_WITH_REWRITE = 165;
/*     */   public static final int MSG_WILDCARD_AS_ROOT = 166;
/*     */   public static final int MSG_CONFLICTING_OPTION_IN_TREE_FILTER = 167;
/*     */   public static final int MSG_GRAMMAR_NONDETERMINISM = 200;
/*     */   public static final int MSG_UNREACHABLE_ALTS = 201;
/*     */   public static final int MSG_DANGLING_STATE = 202;
/*     */   public static final int MSG_INSUFFICIENT_PREDICATES = 203;
/*     */   public static final int MSG_DUPLICATE_SET_ENTRY = 204;
/*     */   public static final int MSG_ANALYSIS_ABORTED = 205;
/*     */   public static final int MSG_RECURSION_OVERLOW = 206;
/*     */   public static final int MSG_LEFT_RECURSION = 207;
/*     */   public static final int MSG_UNREACHABLE_TOKENS = 208;
/*     */   public static final int MSG_TOKEN_NONDETERMINISM = 209;
/*     */   public static final int MSG_LEFT_RECURSION_CYCLES = 210;
/*     */   public static final int MSG_NONREGULAR_DECISION = 211;
/*     */   public static final int MSG_CIRCULAR_DEPENDENCY = 212;
/*     */   public static final int MAX_MESSAGE_NUMBER = 212;
/* 208 */   public static final BitSet ERRORS_FORCING_NO_ANALYSIS = new BitSet() { } ;
/*     */ 
/* 225 */   public static final BitSet ERRORS_FORCING_NO_CODEGEN = new BitSet() { } ;
/*     */ 
/* 242 */   public static final Map emitSingleError = new HashMap() { } ;
/*     */   private static Locale locale;
/*     */   private static String formatName;
/* 255 */   private static Map threadToListenerMap = new HashMap();
/*     */ 
/* 273 */   private static Map threadToErrorStateMap = new HashMap();
/*     */ 
/* 279 */   private static Map threadToToolMap = new HashMap();
/*     */   private static StringTemplateGroup messages;
/*     */   private static StringTemplateGroup format;
/* 289 */   private static String[] idToMessageTemplateName = new String['Õ'];
/*     */ 
/* 291 */   static ANTLRErrorListener theDefaultErrorListener = new ANTLRErrorListener() {
/*     */     public void info(String msg) {
/* 293 */       if (ErrorManager.formatWantsSingleLineMessage()) {
/* 294 */         msg = msg.replaceAll("\n", " ");
/*     */       }
/* 296 */       System.err.println(msg);
/*     */     }
/*     */ 
/*     */     public void error(Message msg) {
/* 300 */       String outputMsg = msg.toString();
/* 301 */       if (ErrorManager.formatWantsSingleLineMessage()) {
/* 302 */         outputMsg = outputMsg.replaceAll("\n", " ");
/*     */       }
/* 304 */       System.err.println(outputMsg);
/*     */     }
/*     */ 
/*     */     public void warning(Message msg) {
/* 308 */       String outputMsg = msg.toString();
/* 309 */       if (ErrorManager.formatWantsSingleLineMessage()) {
/* 310 */         outputMsg = outputMsg.replaceAll("\n", " ");
/*     */       }
/* 312 */       System.err.println(outputMsg);
/*     */     }
/*     */ 
/*     */     public void error(ToolMessage msg) {
/* 316 */       String outputMsg = msg.toString();
/* 317 */       if (ErrorManager.formatWantsSingleLineMessage()) {
/* 318 */         outputMsg = outputMsg.replaceAll("\n", " ");
/*     */       }
/* 320 */       System.err.println(outputMsg);
/*     */     }
/* 291 */   };
/*     */ 
/* 327 */   static StringTemplateErrorListener initSTListener = new StringTemplateErrorListener()
/*     */   {
/*     */     public void error(String s, Throwable e) {
/* 330 */       System.err.println("ErrorManager init error: " + s);
/* 331 */       if (e != null)
/* 332 */         System.err.println("exception: " + e);
/*     */     }
/*     */ 
/*     */     public void warning(String s)
/*     */     {
/* 341 */       System.err.println("ErrorManager init warning: " + s);
/*     */     }
/*     */ 
/*     */     public void debug(String s)
/*     */     {
/*     */     }
/* 327 */   };
/*     */ 
/* 350 */   static StringTemplateErrorListener blankSTListener = new StringTemplateErrorListener() { public void error(String s, Throwable e) {  } 
/*     */     public void warning(String s) {  } 
/* 350 */     public void debug(String s) {  }  } ;
/*     */ 
/* 359 */   static StringTemplateErrorListener theDefaultSTListener = new StringTemplateErrorListener()
/*     */   {
/*     */     public void error(String s, Throwable e) {
/* 362 */       if ((e instanceof InvocationTargetException)) {
/* 363 */         e = ((InvocationTargetException)e).getTargetException();
/*     */       }
/* 365 */       ErrorManager.error(10, s, e);
/*     */     }
/*     */     public void warning(String s) {
/* 368 */       ErrorManager.warning(11, s);
/*     */     }
/*     */ 
/*     */     public void debug(String s)
/*     */     {
/*     */     }
/* 359 */   };
/*     */ 
/*     */   public static StringTemplateErrorListener getStringTemplateErrorListener()
/*     */   {
/* 389 */     return theDefaultSTListener;
/*     */   }
/*     */ 
/*     */   public static void setLocale(Locale locale)
/*     */   {
/* 398 */     locale = locale;
/* 399 */     String language = locale.getLanguage();
/* 400 */     String fileName = "org/antlr/tool/templates/messages/languages/" + language + ".stg";
/* 401 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 402 */     InputStream is = cl.getResourceAsStream(fileName);
/* 403 */     if (is == null) {
/* 404 */       cl = ErrorManager.class.getClassLoader();
/* 405 */       is = cl.getResourceAsStream(fileName);
/*     */     }
/* 407 */     if ((is == null) && (language.equals(Locale.US.getLanguage()))) {
/* 408 */       rawError("ANTLR installation corrupted; cannot find English messages file " + fileName);
/* 409 */       panic();
/*     */     }
/* 411 */     else if (is == null)
/*     */     {
/* 413 */       setLocale(Locale.US);
/* 414 */       return;
/*     */     }
/* 416 */     BufferedReader br = null;
/*     */     try {
/* 418 */       br = new BufferedReader(new InputStreamReader(is));
/* 419 */       messages = new StringTemplateGroup(br, AngleBracketTemplateLexer.class, initSTListener);
/*     */ 
/* 422 */       br.close();
/*     */     }
/*     */     catch (IOException ioe) {
/* 425 */       rawError("error reading message file " + fileName, ioe);
/*     */     }
/*     */     finally {
/* 428 */       if (br != null) {
/*     */         try {
/* 430 */           br.close();
/*     */         }
/*     */         catch (IOException ioe) {
/* 433 */           rawError("cannot close message file " + fileName, ioe);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 438 */     messages.setErrorListener(blankSTListener);
/* 439 */     boolean messagesOK = verifyMessages();
/* 440 */     if ((!messagesOK) && (language.equals(Locale.US.getLanguage()))) {
/* 441 */       rawError("ANTLR installation corrupted; English messages file " + language + ".stg incomplete");
/* 442 */       panic();
/*     */     }
/* 444 */     else if (!messagesOK) {
/* 445 */       setLocale(Locale.US);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setFormat(String formatName)
/*     */   {
/* 453 */     formatName = formatName;
/* 454 */     String fileName = "org/antlr/tool/templates/messages/formats/" + formatName + ".stg";
/* 455 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 456 */     InputStream is = cl.getResourceAsStream(fileName);
/* 457 */     if (is == null) {
/* 458 */       cl = ErrorManager.class.getClassLoader();
/* 459 */       is = cl.getResourceAsStream(fileName);
/*     */     }
/* 461 */     if ((is == null) && (formatName.equals("antlr"))) {
/* 462 */       rawError("ANTLR installation corrupted; cannot find ANTLR messages format file " + fileName);
/* 463 */       panic();
/*     */     }
/* 465 */     else if (is == null) {
/* 466 */       rawError("no such message format file " + fileName + " retrying with default ANTLR format");
/* 467 */       setFormat("antlr");
/* 468 */       return;
/*     */     }
/* 470 */     BufferedReader br = null;
/*     */     try {
/* 472 */       br = new BufferedReader(new InputStreamReader(is));
/* 473 */       format = new StringTemplateGroup(br, AngleBracketTemplateLexer.class, initSTListener);
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/* 479 */         if (br != null)
/* 480 */           br.close();
/*     */       }
/*     */       catch (IOException ioe)
/*     */       {
/* 484 */         rawError("cannot close message format file " + fileName, ioe);
/*     */       }
/*     */     }
/*     */ 
/* 488 */     format.setErrorListener(blankSTListener);
/* 489 */     boolean formatOK = verifyFormat();
/* 490 */     if ((!formatOK) && (formatName.equals("antlr"))) {
/* 491 */       rawError("ANTLR installation corrupted; ANTLR messages format file " + formatName + ".stg incomplete");
/* 492 */       panic();
/*     */     }
/* 494 */     else if (!formatOK) {
/* 495 */       setFormat("antlr");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setErrorListener(ANTLRErrorListener listener)
/*     */   {
/* 534 */     threadToListenerMap.put(Thread.currentThread(), listener);
/*     */   }
/*     */ 
/*     */   public static void removeErrorListener() {
/* 538 */     threadToListenerMap.remove(Thread.currentThread());
/*     */   }
/*     */ 
/*     */   public static void setTool(Tool tool) {
/* 542 */     threadToToolMap.put(Thread.currentThread(), tool);
/*     */   }
/*     */ 
/*     */   public static StringTemplate getMessage(int msgID)
/*     */   {
/* 550 */     String msgName = idToMessageTemplateName[msgID];
/* 551 */     return messages.getInstanceOf(msgName);
/*     */   }
/*     */   public static String getMessageType(int msgID) {
/* 554 */     if (getErrorState().warningMsgIDs.member(msgID)) {
/* 555 */       return messages.getInstanceOf("warning").toString();
/*     */     }
/* 557 */     if (getErrorState().errorMsgIDs.member(msgID)) {
/* 558 */       return messages.getInstanceOf("error").toString();
/*     */     }
/* 560 */     assertTrue(false, "Assertion failed! Message ID " + msgID + " created but is not present in errorMsgIDs or warningMsgIDs.");
/* 561 */     return "";
/*     */   }
/*     */ 
/*     */   public static StringTemplate getLocationFormat()
/*     */   {
/* 568 */     return format.getInstanceOf("location");
/*     */   }
/*     */   public static StringTemplate getReportFormat() {
/* 571 */     return format.getInstanceOf("report");
/*     */   }
/*     */   public static StringTemplate getMessageFormat() {
/* 574 */     return format.getInstanceOf("message");
/*     */   }
/*     */   public static boolean formatWantsSingleLineMessage() {
/* 577 */     return format.getInstanceOf("wantsSingleLineMessage").toString().equals("true");
/*     */   }
/*     */ 
/*     */   public static ANTLRErrorListener getErrorListener() {
/* 581 */     ANTLRErrorListener el = (ANTLRErrorListener)threadToListenerMap.get(Thread.currentThread());
/*     */ 
/* 583 */     if (el == null) {
/* 584 */       return theDefaultErrorListener;
/*     */     }
/* 586 */     return el;
/*     */   }
/*     */ 
/*     */   public static ErrorState getErrorState() {
/* 590 */     ErrorState ec = (ErrorState)threadToErrorStateMap.get(Thread.currentThread());
/*     */ 
/* 592 */     if (ec == null) {
/* 593 */       ec = new ErrorState();
/* 594 */       threadToErrorStateMap.put(Thread.currentThread(), ec);
/*     */     }
/* 596 */     return ec;
/*     */   }
/*     */ 
/*     */   public static int getNumErrors() {
/* 600 */     return getErrorState().errors;
/*     */   }
/*     */ 
/*     */   public static void resetErrorState() {
/* 604 */     threadToListenerMap = new HashMap();
/* 605 */     ErrorState ec = new ErrorState();
/* 606 */     threadToErrorStateMap.put(Thread.currentThread(), ec);
/*     */   }
/*     */ 
/*     */   public static void info(String msg) {
/* 610 */     getErrorState().infos += 1;
/* 611 */     getErrorListener().info(msg);
/*     */   }
/*     */ 
/*     */   public static void error(int msgID) {
/* 615 */     getErrorState().errors += 1;
/* 616 */     getErrorState().errorMsgIDs.add(msgID);
/* 617 */     getErrorListener().error(new ToolMessage(msgID));
/*     */   }
/*     */ 
/*     */   public static void error(int msgID, Throwable e) {
/* 621 */     getErrorState().errors += 1;
/* 622 */     getErrorState().errorMsgIDs.add(msgID);
/* 623 */     getErrorListener().error(new ToolMessage(msgID, e));
/*     */   }
/*     */ 
/*     */   public static void error(int msgID, Object arg) {
/* 627 */     getErrorState().errors += 1;
/* 628 */     getErrorState().errorMsgIDs.add(msgID);
/* 629 */     getErrorListener().error(new ToolMessage(msgID, arg));
/*     */   }
/*     */ 
/*     */   public static void error(int msgID, Object arg, Object arg2) {
/* 633 */     getErrorState().errors += 1;
/* 634 */     getErrorState().errorMsgIDs.add(msgID);
/* 635 */     getErrorListener().error(new ToolMessage(msgID, arg, arg2));
/*     */   }
/*     */ 
/*     */   public static void error(int msgID, Object arg, Throwable e) {
/* 639 */     getErrorState().errors += 1;
/* 640 */     getErrorState().errorMsgIDs.add(msgID);
/* 641 */     getErrorListener().error(new ToolMessage(msgID, arg, e));
/*     */   }
/*     */ 
/*     */   public static void warning(int msgID, Object arg) {
/* 645 */     getErrorState().warnings += 1;
/* 646 */     getErrorState().warningMsgIDs.add(msgID);
/* 647 */     getErrorListener().warning(new ToolMessage(msgID, arg));
/*     */   }
/*     */ 
/*     */   public static void nondeterminism(DecisionProbe probe, DFAState d)
/*     */   {
/* 653 */     getErrorState().warnings += 1;
/* 654 */     Message msg = new GrammarNonDeterminismMessage(probe, d);
/* 655 */     getErrorState().warningMsgIDs.add(msg.msgID);
/* 656 */     getErrorListener().warning(msg);
/*     */   }
/*     */ 
/*     */   public static void danglingState(DecisionProbe probe, DFAState d)
/*     */   {
/* 662 */     getErrorState().errors += 1;
/* 663 */     Message msg = new GrammarDanglingStateMessage(probe, d);
/* 664 */     getErrorState().errorMsgIDs.add(msg.msgID);
/* 665 */     Set seen = (Set)emitSingleError.get("danglingState");
/* 666 */     if (!seen.contains(d.dfa.decisionNumber + "|" + d.getAltSet())) {
/* 667 */       getErrorListener().error(msg);
/*     */ 
/* 669 */       seen.add(d.dfa.decisionNumber + "|" + d.getAltSet());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void analysisAborted(DecisionProbe probe)
/*     */   {
/* 675 */     getErrorState().warnings += 1;
/* 676 */     Message msg = new GrammarAnalysisAbortedMessage(probe);
/* 677 */     getErrorState().warningMsgIDs.add(msg.msgID);
/* 678 */     getErrorListener().warning(msg);
/*     */   }
/*     */ 
/*     */   public static void unreachableAlts(DecisionProbe probe, List alts)
/*     */   {
/* 684 */     getErrorState().errors += 1;
/* 685 */     Message msg = new GrammarUnreachableAltsMessage(probe, alts);
/* 686 */     getErrorState().errorMsgIDs.add(msg.msgID);
/* 687 */     getErrorListener().error(msg);
/*     */   }
/*     */ 
/*     */   public static void insufficientPredicates(DecisionProbe probe, DFAState d, Map<Integer, Set<Token>> altToUncoveredLocations)
/*     */   {
/* 694 */     getErrorState().warnings += 1;
/* 695 */     Message msg = new GrammarInsufficientPredicatesMessage(probe, d, altToUncoveredLocations);
/* 696 */     getErrorState().warningMsgIDs.add(msg.msgID);
/* 697 */     getErrorListener().warning(msg);
/*     */   }
/*     */ 
/*     */   public static void nonLLStarDecision(DecisionProbe probe) {
/* 701 */     getErrorState().errors += 1;
/* 702 */     Message msg = new NonRegularDecisionMessage(probe, probe.getNonDeterministicAlts());
/* 703 */     getErrorState().errorMsgIDs.add(msg.msgID);
/* 704 */     getErrorListener().error(msg);
/*     */   }
/*     */ 
/*     */   public static void recursionOverflow(DecisionProbe probe, DFAState sampleBadState, int alt, Collection targetRules, Collection callSiteStates)
/*     */   {
/* 713 */     getErrorState().errors += 1;
/* 714 */     Message msg = new RecursionOverflowMessage(probe, sampleBadState, alt, targetRules, callSiteStates);
/*     */ 
/* 716 */     getErrorState().errorMsgIDs.add(msg.msgID);
/* 717 */     getErrorListener().error(msg);
/*     */   }
/*     */ 
/*     */   public static void leftRecursionCycles(Collection cycles)
/*     */   {
/* 735 */     getErrorState().errors += 1;
/* 736 */     Message msg = new LeftRecursionCyclesMessage(cycles);
/* 737 */     getErrorState().errorMsgIDs.add(msg.msgID);
/* 738 */     getErrorListener().error(msg);
/*     */   }
/*     */ 
/*     */   public static void grammarError(int msgID, Grammar g, Token token, Object arg, Object arg2)
/*     */   {
/* 747 */     getErrorState().errors += 1;
/* 748 */     Message msg = new GrammarSemanticsMessage(msgID, g, token, arg, arg2);
/* 749 */     getErrorState().errorMsgIDs.add(msgID);
/* 750 */     getErrorListener().error(msg);
/*     */   }
/*     */ 
/*     */   public static void grammarError(int msgID, Grammar g, Token token, Object arg)
/*     */   {
/* 758 */     grammarError(msgID, g, token, arg, null);
/*     */   }
/*     */ 
/*     */   public static void grammarError(int msgID, Grammar g, Token token)
/*     */   {
/* 765 */     grammarError(msgID, g, token, null, null);
/*     */   }
/*     */ 
/*     */   public static void grammarWarning(int msgID, Grammar g, Token token, Object arg, Object arg2)
/*     */   {
/* 774 */     getErrorState().warnings += 1;
/* 775 */     Message msg = new GrammarSemanticsMessage(msgID, g, token, arg, arg2);
/* 776 */     getErrorState().warningMsgIDs.add(msgID);
/* 777 */     getErrorListener().warning(msg);
/*     */   }
/*     */ 
/*     */   public static void grammarWarning(int msgID, Grammar g, Token token, Object arg)
/*     */   {
/* 785 */     grammarWarning(msgID, g, token, arg, null);
/*     */   }
/*     */ 
/*     */   public static void grammarWarning(int msgID, Grammar g, Token token)
/*     */   {
/* 792 */     grammarWarning(msgID, g, token, null, null);
/*     */   }
/*     */ 
/*     */   public static void syntaxError(int msgID, Grammar grammar, Token token, Object arg, RecognitionException re)
/*     */   {
/* 801 */     getErrorState().errors += 1;
/* 802 */     getErrorState().errorMsgIDs.add(msgID);
/* 803 */     getErrorListener().error(new GrammarSyntaxMessage(msgID, grammar, token, arg, re));
/*     */   }
/*     */ 
/*     */   public static void internalError(Object error, Throwable e)
/*     */   {
/* 809 */     StackTraceElement location = getLastNonErrorManagerCodeLocation(e);
/* 810 */     String msg = "Exception " + e + "@" + location + ": " + error;
/* 811 */     error(10, msg);
/*     */   }
/*     */ 
/*     */   public static void internalError(Object error) {
/* 815 */     StackTraceElement location = getLastNonErrorManagerCodeLocation(new Exception());
/*     */ 
/* 817 */     String msg = location + ": " + error;
/* 818 */     error(10, msg);
/*     */   }
/*     */ 
/*     */   public static boolean doNotAttemptAnalysis() {
/* 822 */     return !getErrorState().errorMsgIDs.and(ERRORS_FORCING_NO_ANALYSIS).isNil();
/*     */   }
/*     */ 
/*     */   public static boolean doNotAttemptCodeGen() {
/* 826 */     return (doNotAttemptAnalysis()) || (!getErrorState().errorMsgIDs.and(ERRORS_FORCING_NO_CODEGEN).isNil());
/*     */   }
/*     */ 
/*     */   private static StackTraceElement getLastNonErrorManagerCodeLocation(Throwable e)
/*     */   {
/* 832 */     StackTraceElement[] stack = e.getStackTrace();
/* 833 */     for (int i = 0; 
/* 834 */       i < stack.length; i++) {
/* 835 */       StackTraceElement t = stack[i];
/* 836 */       if (t.toString().indexOf("ErrorManager") < 0) {
/*     */         break;
/*     */       }
/*     */     }
/* 840 */     StackTraceElement location = stack[i];
/* 841 */     return location;
/*     */   }
/*     */ 
/*     */   public static void assertTrue(boolean condition, String message)
/*     */   {
/* 847 */     if (!condition)
/* 848 */       internalError(message);
/*     */   }
/*     */ 
/*     */   protected static boolean initIdToMessageNameMapping()
/*     */   {
/* 856 */     for (int i = 0; i < idToMessageTemplateName.length; i++) {
/* 857 */       idToMessageTemplateName[i] = ("INVALID MESSAGE ID: " + i);
/*     */     }
/*     */ 
/* 860 */     Field[] fields = ErrorManager.class.getFields();
/* 861 */     for (int i = 0; i < fields.length; i++) {
/* 862 */       Field f = fields[i];
/* 863 */       String fieldName = f.getName();
/* 864 */       if (fieldName.startsWith("MSG_"))
/*     */       {
/* 867 */         String templateName = fieldName.substring("MSG_".length(), fieldName.length());
/*     */ 
/* 869 */         int msgID = 0;
/*     */         try
/*     */         {
/* 872 */           msgID = f.getInt(ErrorManager.class);
/*     */         }
/*     */         catch (IllegalAccessException iae) {
/* 875 */           System.err.println("cannot get const value for " + f.getName());
/* 876 */           continue;
/*     */         }
/* 878 */         if (fieldName.startsWith("MSG_"))
/* 879 */           idToMessageTemplateName[msgID] = templateName;
/*     */       }
/*     */     }
/* 882 */     return true;
/*     */   }
/*     */ 
/*     */   protected static boolean verifyMessages()
/*     */   {
/* 889 */     boolean ok = true;
/* 890 */     Field[] fields = ErrorManager.class.getFields();
/* 891 */     for (int i = 0; i < fields.length; i++) {
/* 892 */       Field f = fields[i];
/* 893 */       String fieldName = f.getName();
/* 894 */       String templateName = fieldName.substring("MSG_".length(), fieldName.length());
/*     */ 
/* 896 */       if ((fieldName.startsWith("MSG_")) && 
/* 897 */         (!messages.isDefined(templateName))) {
/* 898 */         System.err.println("Message " + templateName + " in locale " + locale + " not found");
/*     */ 
/* 900 */         ok = false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 905 */     if (!messages.isDefined("warning")) {
/* 906 */       System.err.println("Message template 'warning' not found in locale " + locale);
/* 907 */       ok = false;
/*     */     }
/* 909 */     if (!messages.isDefined("error")) {
/* 910 */       System.err.println("Message template 'error' not found in locale " + locale);
/* 911 */       ok = false;
/*     */     }
/* 913 */     return ok;
/*     */   }
/*     */ 
/*     */   protected static boolean verifyFormat()
/*     */   {
/* 918 */     boolean ok = true;
/* 919 */     if (!format.isDefined("location")) {
/* 920 */       System.err.println("Format template 'location' not found in " + formatName);
/* 921 */       ok = false;
/*     */     }
/* 923 */     if (!format.isDefined("message")) {
/* 924 */       System.err.println("Format template 'message' not found in " + formatName);
/* 925 */       ok = false;
/*     */     }
/* 927 */     if (!format.isDefined("report")) {
/* 928 */       System.err.println("Format template 'report' not found in " + formatName);
/* 929 */       ok = false;
/*     */     }
/* 931 */     return ok;
/*     */   }
/*     */ 
/*     */   static void rawError(String msg)
/*     */   {
/* 938 */     System.err.println(msg);
/*     */   }
/*     */ 
/*     */   static void rawError(String msg, Throwable e) {
/* 942 */     rawError(msg);
/* 943 */     e.printStackTrace(System.err);
/*     */   }
/*     */ 
/*     */   public static void panic()
/*     */   {
/* 950 */     Tool tool = (Tool)threadToToolMap.get(Thread.currentThread());
/* 951 */     if (tool == null)
/*     */     {
/* 953 */       throw new Error("ANTLR ErrorManager panic");
/*     */     }
/*     */ 
/* 956 */     tool.panic();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 376 */     initIdToMessageNameMapping();
/*     */ 
/* 381 */     setLocale(Locale.getDefault());
/*     */ 
/* 385 */     setFormat("antlr");
/*     */   }
/*     */ 
/*     */   static class ErrorState
/*     */   {
/*     */     public int errors;
/*     */     public int warnings;
/*     */     public int infos;
/* 264 */     public BitSet errorMsgIDs = new BitSet();
/* 265 */     public BitSet warningMsgIDs = new BitSet();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.ErrorManager
 * JD-Core Version:    0.6.2
 */