/*    */ package antlr.debug;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class ParserReporter extends Tracer
/*    */   implements ParserListener
/*    */ {
/*    */   public void parserConsume(ParserTokenEvent paramParserTokenEvent)
/*    */   {
/*  7 */     System.out.println(this.indent + paramParserTokenEvent);
/*    */   }
/*    */   public void parserLA(ParserTokenEvent paramParserTokenEvent) {
/* 10 */     System.out.println(this.indent + paramParserTokenEvent);
/*    */   }
/*    */   public void parserMatch(ParserMatchEvent paramParserMatchEvent) {
/* 13 */     System.out.println(this.indent + paramParserMatchEvent);
/*    */   }
/*    */   public void parserMatchNot(ParserMatchEvent paramParserMatchEvent) {
/* 16 */     System.out.println(this.indent + paramParserMatchEvent);
/*    */   }
/*    */   public void parserMismatch(ParserMatchEvent paramParserMatchEvent) {
/* 19 */     System.out.println(this.indent + paramParserMatchEvent);
/*    */   }
/*    */   public void parserMismatchNot(ParserMatchEvent paramParserMatchEvent) {
/* 22 */     System.out.println(this.indent + paramParserMatchEvent);
/*    */   }
/*    */   public void reportError(MessageEvent paramMessageEvent) {
/* 25 */     System.out.println(this.indent + paramMessageEvent);
/*    */   }
/*    */   public void reportWarning(MessageEvent paramMessageEvent) {
/* 28 */     System.out.println(this.indent + paramMessageEvent);
/*    */   }
/*    */   public void semanticPredicateEvaluated(SemanticPredicateEvent paramSemanticPredicateEvent) {
/* 31 */     System.out.println(this.indent + paramSemanticPredicateEvent);
/*    */   }
/*    */   public void syntacticPredicateFailed(SyntacticPredicateEvent paramSyntacticPredicateEvent) {
/* 34 */     System.out.println(this.indent + paramSyntacticPredicateEvent);
/*    */   }
/*    */   public void syntacticPredicateStarted(SyntacticPredicateEvent paramSyntacticPredicateEvent) {
/* 37 */     System.out.println(this.indent + paramSyntacticPredicateEvent);
/*    */   }
/*    */   public void syntacticPredicateSucceeded(SyntacticPredicateEvent paramSyntacticPredicateEvent) {
/* 40 */     System.out.println(this.indent + paramSyntacticPredicateEvent);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.ParserReporter
 * JD-Core Version:    0.6.2
 */